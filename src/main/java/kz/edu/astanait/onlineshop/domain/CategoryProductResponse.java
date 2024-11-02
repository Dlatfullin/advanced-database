package kz.edu.astanait.onlineshop.domain;

import lombok.Builder;

@Builder
public record CategoryProductResponse(String id,
                                      String title) {
}
