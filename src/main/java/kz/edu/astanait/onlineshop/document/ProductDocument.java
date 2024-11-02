package kz.edu.astanait.onlineshop.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("products")
public class ProductDocument {

    @Id
    private String id;
    private String title;
    private String description;
    private BigDecimal price;
    private BigDecimal quantity;
    private boolean deleted;
}
