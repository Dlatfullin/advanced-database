package kz.edu.astanait.onlineshop.domain;

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

    public ProductByIdResponse getResponseAsLiked() {
        return new ProductByIdResponse(id, category, title, description, price, quantity, deleted, imageUrl, likes, true);
    }
}
