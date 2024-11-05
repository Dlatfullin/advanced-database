package kz.edu.astanait.onlineshop.graph.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;

@Data
@RelationshipProperties
public class Relationship {

    @RelationshipId
    private Long id;
}
