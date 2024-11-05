package kz.edu.astanait.onlineshop.graph.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Data
@Node("Product")
@AllArgsConstructor
public class ProductNode {

    @Id
    private String id;
}
