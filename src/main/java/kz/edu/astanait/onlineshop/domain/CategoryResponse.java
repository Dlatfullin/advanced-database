package kz.edu.astanait.onlineshop.domain;

import lombok.Builder;

@Builder
public record CategoryResponse(String id,
                               String name) {
}
