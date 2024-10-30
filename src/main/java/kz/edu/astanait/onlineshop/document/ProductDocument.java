package kz.edu.astanait.onlineshop.document;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
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
    @NotNull(message = "Title cannot be null")
    @Size(min = 3, message = "Title must be more than 3 characters")
    private String title;
    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;
    @NotNull(message = "Price cannot be null")
    @PositiveOrZero(message = "Price must be zero or a positive value")
    private BigDecimal price;
    private boolean deleted;
    private String image;
}
