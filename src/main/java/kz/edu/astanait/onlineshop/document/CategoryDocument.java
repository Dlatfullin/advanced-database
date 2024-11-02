package kz.edu.astanait.onlineshop.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("categories")
public class CategoryDocument {

    @Id
    private String id;
    private String name;
    private List<ProductDocument> products = new ArrayList<>();
}
