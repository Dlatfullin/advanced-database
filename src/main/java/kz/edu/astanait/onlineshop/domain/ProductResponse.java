package kz.edu.astanait.onlineshop.domain;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductResponse(String id,
                              String title,
                              String description,
                              BigDecimal price,
                              boolean deleted) {
}
