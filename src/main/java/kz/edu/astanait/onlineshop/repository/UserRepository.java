package kz.edu.astanait.onlineshop.repository;

import kz.edu.astanait.onlineshop.document.PurchaseDocument;
import kz.edu.astanait.onlineshop.document.UserDocument;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<UserDocument, String> {

    Optional<UserDocument> findByEmail(String email);

    @Query(value = "{tokens:  { $elemMatch: { hash: ?0, expiredAt: { $gt: ?1 } } } }")
    Optional<UserDocument> findByHashedToken(byte[] hashedToken, LocalDateTime expiredAt);

    boolean existsByEmailIgnoreCase(String email);

    @Aggregation(pipeline = {
            "{$match: {'_id': ?0}}",
            "{$unwind: '$purchases'}",
            "{$replaceRoot: {newRoot: '$purchases'}}"
    })
    List<PurchaseDocument> findAllPurchasesByUserId(String id);
}
