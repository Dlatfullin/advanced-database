package kz.edu.astanait.onlineshop.document;

import java.math.BigDecimal;

public record CategoryProductAggregate(String id,
                                       String categoryId,
                                       String categoryName,
                                       String title,
                                       String description,
                                       BigDecimal price,
                                       BigDecimal quantity,
                                       boolean deleted) {

    public String imageUrl() {
        return "/v1/products/%s/images".formatted(id);
    }
}
