package kz.edu.astanait.onlineshop.repository;

import kz.edu.astanait.onlineshop.document.CategoryDocument;
import kz.edu.astanait.onlineshop.document.ProductDocument;
import kz.edu.astanait.onlineshop.exception.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

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

    @Aggregation(pipeline = {
            "{$unwind: '$products'}",
            "{$match: {'products._id': ?0}}",
            "{$replaceRoot: {newRoot: '$products'}}"
    })
    Optional<ProductDocument> findProductById(String id);

    @Query("{ 'products._id': ?0 }")
    Optional<CategoryDocument> findByProductId(String productId);

    @Query(value = "{ 'products._id': ?0 }", exists = true)
    boolean existsProductById(String productId);

    @Aggregation(pipeline = {
            "{ $unwind: '$products' }",
            "{ $match: { 'products._id': { $in: ?0 } } }",
            "{ $replaceRoot: { newRoot: '$products' } }"
    })
    List<ProductDocument> findAllProductsByIds(Iterable<String> productIds);
}
