package kz.edu.astanait.onlineshop.repository;

import kz.edu.astanait.onlineshop.document.CategoryDocument;
import kz.edu.astanait.onlineshop.document.ProductDocument;
import kz.edu.astanait.onlineshop.domain.ProductSaveRequest;
import kz.edu.astanait.onlineshop.exception.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends MongoRepository<CategoryDocument, String> {

    default CategoryDocument findByIdOrElseThrow(String id) {
       return findById(id).orElseThrow(() -> ResourceNotFoundException.categoryNotFoundById(id));
    }

    @Aggregation(pipeline = {
            "{$unwind: '$products'}",
            "{$replaceRoot: {newRoot: '$products'}}"
    }, collation = "{ locale: 'en_US', numericOrdering: true }")
    List<ProductDocument> findAllProducts(Pageable pageable);

    @Query("{ 'products._id': ?0 }")
    Optional<CategoryDocument> findByProductId(String productId);

    @Aggregation(pipeline = {
            "{ $unwind: '$products' }",
            "{ $match: { $or: [ " +
                    "   { 'name': { $regex: ?0, $options: 'i' } }, " +
                    "   { 'products.title': { $regex: ?0, $options: 'i' } }, " +
                    "   { 'products.description': { $regex: ?0, $options: 'i' } } " +
                    "] } }",
            "{ $replaceRoot: { newRoot: '$products' } }"
    })
    List<ProductDocument> findAllBy(String search);

    @Aggregation(pipeline = {
            "{$unwind: '$products'}",
            "{$match: {'products._id': ?0}}",
            "{$replaceRoot: {newRoot: '$products'}}"
    })
    Optional<ProductDocument> findProductById(String id);

    @Query("{'_id': ?0}")
    @Update("{ '$pull': { 'products': { '_id': ?1 } } }")
    void removeProductFromCategory(String categoryId, String productId);

    @Query("{'_id': ?0 }")
    @Update("{ '$push': { 'products': { '_id': ?1, 'title': ?2, 'description': ?3, 'price': ?4, 'quantity': ?5 } } }")
    void addProductToCategory(String categoryId, String productId, String title, String description, BigDecimal price, BigDecimal quantity);
}
