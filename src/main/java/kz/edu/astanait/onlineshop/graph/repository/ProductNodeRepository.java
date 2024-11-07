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
        MATCH (user:User {id: $userId })-[r:LIKED]->(product:Product {id: $productId})
        DELETE r
        """)
    void unlikeProduct(String userId, String productId);

    @Query("""
        MATCH (user:User)-[r:LIKED]->(product:Product {id: $productId})
        RETURN COUNT(r)
        """)
    int countProductLikes(String productId);

    @Query("""
        MERGE (user:User {id: $userId })
        MERGE (product:Product {id: $productId})
        MERGE (user)-[r:VIEWED {createdAt: datetime()}]->(product)
        """)
    void viewProduct(String userId, String productId);

    @Query(value = """
        MATCH (user:User {id: $userId })-[r:VIEWED]->(product:Product {id: $productId})
        RETURN r
        """)
    Optional<Relationship> getViewRelationship(String userId, String productId);

    @Query(value = """
        MATCH (user:User {id: $userId })-[r:LIKED]->(product:Product {id: $productId})
        RETURN r
        """)
    Optional<Relationship> getLikeRelationship(String userId, String productId);

    @Query("""
        MATCH (user:User {id: $userId })-[r:VIEWED]->(product:Product)
        ORDER BY r.createdAt DESC
        SKIP $skip LIMIT $limit
        RETURN product
        """)
    List<ProductNode> getViewedProductsByUser(String userId, long skip, int limit);

    @Query("""
        MATCH (user:User {id: $userId })-[r:LIKED]->(product:Product)
        ORDER BY r.createdAt DESC
        SKIP $skip LIMIT $limit
        RETURN product
        """)
    List<ProductNode> getLikedProductsByUser(String userId, long skip, int limit);

    @Query("""
        MATCH (u:User {userId: $userId})-[:LIKED]->(p:Product)
        WITH COLLECT(p) AS likedProducts
        CALL gds.pageRank.stream('userProductGraph')
        YIELD nodeId, score
        WITH gds.util.asNode(nodeId) AS product, score, likedProducts
        WHERE product:Product AND NOT product IN likedProducts
        RETURN product
        ORDER BY score DESC
        LIMIT $limit;
        """)
    List<ProductNode> getRecommendedProductsForUser(String userId, int limit);
}
