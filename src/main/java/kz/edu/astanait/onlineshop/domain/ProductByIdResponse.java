package kz.edu.astanait.onlineshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductByIdResponse(String id,
                                  CategoryResponse category,
                                  String title,
                                  String description,
                                  BigDecimal price,
                                  BigDecimal quantity,
                                  boolean deleted,
                                  String imageUrl,
                                  int likes,
                                  boolean liked) {

    @JsonIgnore
    public ProductByIdResponse asLiked() {
        return new ProductByIdResponse(id, category, title, description, price, quantity, deleted, imageUrl, likes, true);
    }
}
