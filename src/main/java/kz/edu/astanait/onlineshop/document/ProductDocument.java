package kz.edu.astanait.onlineshop.document;

import lombok.*;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collation = "products")
public class ProductDocument {

    @Id
    private String id;
    private String title;
    private String description;
    private Double price;
    private Boolean deleted;
    private Binary image;
}
