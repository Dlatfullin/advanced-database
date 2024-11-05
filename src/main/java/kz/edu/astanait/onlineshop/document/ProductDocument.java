package kz.edu.astanait.onlineshop.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDocument {

    @Id
    private String id;
    @TextIndexed
    private String title;
    @TextIndexed
    private String description;
    private BigDecimal price;
    private BigDecimal quantity;
    private boolean deleted;
}
