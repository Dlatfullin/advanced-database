package kz.edu.astanait.onlineshop.repository;

import kz.edu.astanait.onlineshop.document.ImageDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends MongoRepository<ImageDocument, String> {

    Optional<ImageDocument> findByProductId(String productId);
}
