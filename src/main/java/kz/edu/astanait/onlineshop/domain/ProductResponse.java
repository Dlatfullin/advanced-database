package kz.edu.astanait.onlineshop.domain;

import lombok.Builder;

@Builder
public record ProductResponse(String id, String title, String description,
                              Double price, Boolean deleted) {
}
