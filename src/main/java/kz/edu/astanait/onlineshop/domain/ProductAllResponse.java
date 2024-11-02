package kz.edu.astanait.onlineshop.domain;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductAllResponse(String id,
                                 String title,
                                 BigDecimal price,
                                 boolean deleted,
                                 String imageUrl) {
}
