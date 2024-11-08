package kz.edu.astanait.onlineshop.repository;

import kz.edu.astanait.onlineshop.document.CategoryDocument;
import kz.edu.astanait.onlineshop.document.CategoryProductAggregate;
import kz.edu.astanait.onlineshop.document.ProductDocument;
import kz.edu.astanait.onlineshop.exception.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends MongoRepository<CategoryDocument, String> {

    default CategoryDocument findByIdOrElseThrow(String id) {
       return findById(id).orElseThrow(() -> ResourceNotFoundException.categoryNotFoundById(id));
    }

    @Aggregation(pipeline = {
            "{ $match:  { $text: { $search: ?0 } } }",
            "{ $unwind: '$products' }",
            "{ $replaceRoot: { newRoot: '$products'} }",
            "{ $match: { 'deleted': false } }",
            """
             { $match: { $or: [
                    { 'title': { $regex: '.?0.', $options: 'i' } },
                    { 'description': { $regex: '.?0.', $options: 'i' } }
             ] } }
             """,
            "{ $sort: { score: { $meta: 'textScore' } } }"
    }, collation = "{ locale: 'en_US', numericOrdering: true }")
    List<ProductDocument> findAllProducts(String query, Pageable pageable);

    @Aggregation(pipeline = {
            "{ $unwind: '$products' }",
            "{ $replaceRoot: { newRoot: '$products'} }",
            "{ $match: { 'deleted': false } }",
    }, collation = "{ locale: 'en_US', numericOrdering: true }")
    List<ProductDocument> findAllProducts(Pageable pageable);

    @Query("{'_id': ?0}")
    @Aggregation(pipeline = {
            "{ $unwind: '$products' }",
            "{ $replaceRoot: { newRoot: '$products' } }",
            "{ $match: { 'deleted': false } }"
    }, collation = "{ locale: 'en_US', numericOrdering: true }")
    List<ProductDocument> findProductsByCategoryId(String categoryId, Pageable pageable);

    @Aggregation(pipeline = {
            "{$match: {'products._id': ?0}}",
            "{$unwind: '$products'}",
            "{$match: {'products._id': ?0}}",
            "{$project: {"
                    + "productId: '$products._id', "
                    + "title: '$products.title', "
                    + "description: '$products.description', "
                    + "price: '$products.price', "
                    + "quantity: '$products.quantity', "
                    + "deleted: '$products.deleted', "
                    + "categoryId: '$_id', "
                    + "categoryName: '$name'"
                    + "}}"
    })
    Optional<CategoryProductAggregate> findProductWithCategoryById(String productId);

    @Aggregation(pipeline = {
            "{$unwind: '$products'}",
            "{$match: {'products._id': ?0}}",
            "{$replaceRoot: {newRoot: '$products'}}",
            "{$limit: 1}"
    })
    Optional<ProductDocument> findProductById(String id);

    @Query("{'_id': ?0}")
    @Update("{ '$pull': { 'products': { '_id': ?1 } } }")
    void removeProductFromCategory(String categoryId, String productId);

    @Query("{'_id': ?0 }")
    @Update("{ '$push': { 'products': ?1 } }")
    void addProductToCategory(String categoryId, ProductDocument product);

    @Query("{'products._id': ?0}")
    @Update("{'$set': {'products.$.deleted': true}}")
    void markProductAsDeleted(String productId);

    @Query(value = "{ 'products._id': ?0 }", exists = true)
    boolean existsProductById(String productId);

    @Aggregation(pipeline = {
            "{ $unwind: '$products' }",
            "{ $match: { 'products._id': { $in: ?0 } } }",
            "{ $replaceRoot: { newRoot: '$products' } }"
    })
    List<ProductDocument> findAllProductsByIds(Iterable<String> productIds);

    @Aggregation(pipeline = {
            "{ '$unwind': '$products' }",
            "{ '$match': { 'products.id': ?0 } }",
            "{ '$group': { '_id': '$_id', 'name': { '$first': '$name' }, 'products': { '$push': '$products' } } }"
    })
    Optional<CategoryDocument> findByProductId(String productId);
}
