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
                                  String imageUrl) {
}
