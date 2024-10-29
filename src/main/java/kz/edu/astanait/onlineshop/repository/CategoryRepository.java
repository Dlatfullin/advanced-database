package kz.edu.astanait.onlineshop.repository;

import kz.edu.astanait.onlineshop.document.CategoryDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends MongoRepository<CategoryDocument, String> {
}
