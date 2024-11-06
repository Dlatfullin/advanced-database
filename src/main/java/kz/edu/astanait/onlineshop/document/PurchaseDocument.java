package kz.edu.astanait.onlineshop.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("purchase")
public class PurchaseDocument {

    @Id
    private String id;
    private LocalDateTime date;
    private BigDecimal quantity;
    private ProductDocument product;
}
