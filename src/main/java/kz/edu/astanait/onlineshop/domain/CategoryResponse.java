package kz.edu.astanait.onlineshop.domain;

import lombok.Builder;

import java.util.List;

@Builder
public record CategoryResponse(String id,
                               String name,
                               List<ProductResponse> productResponse) {
}
