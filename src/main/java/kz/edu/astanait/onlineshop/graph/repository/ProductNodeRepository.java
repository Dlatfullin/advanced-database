package kz.edu.astanait.onlineshop.graph.repository;

import kz.edu.astanait.onlineshop.graph.entity.ProductNode;
import kz.edu.astanait.onlineshop.graph.entity.Relationship;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;

public interface ProductNodeRepository extends Neo4jRepository<ProductNode, String> {

    @Query("""
        MERGE (user:User {id: $userId })
        MERGE (product:Product {id: $productId})
        MERGE (user)-[r:LIKED {createdAt: datetime()}]->(product)
        """)
    void likeProduct(String userId, String productId);

    @Query("""
        MERGE (user:User {id: $userId })
        MERGE (product:Product {id: $productId})
        MERGE (user)-[r:VIEWED {createdAt: datetime()}]->(product)
        """)
    void viewProduct(String userId, String productId);

    @Query(value = """
        MATCH (user:User {id: $userId })-[r:VIEWED]->(product:Product {id: $productId})
        RETURN r
        """, exists = true)
    Optional<Relationship> getViewRelationship(String userId, String productId);

    @Query(value = """
        MATCH (user:User {id: $userId })-[r:LIKED]->(product:Product {id: $productId})
        RETURN r
        """, exists = true)
    Optional<Relationship> getLikeRelationship(String userId, String productId);

    @Query("""
        MATCH (user:User {id: $userId })-[r:VIEWED]->(product:Product)
        ORDER BY r.createdAt
        SKIP $skip LIMIT $limit
        RETURN product
        """)
    List<ProductNode> getViewedProductsByUser(String userId, long skip, int limit);

    @Query("""
        MATCH (user:User {id: $userId })-[r:LIKED]->(product:Product)
        ORDER BY r.createdAt
        SKIP $skip LIMIT $limit
        RETURN product
        """)
    List<ProductNode> getLikedProductsByUser(String userId, long skip, int limit);

    @Query("""
        MATCH (u1:User {id: $userId })-[r1:LIKED]->(p:Product)<-[r2:LIKED]-(u2:User)-[r3:LIKED]->(product:Product)
        LIMIT $limit
        RETURN product
        """)
    List<ProductNode> getRecommendedProductsForUser(String userId, int limit);
}
