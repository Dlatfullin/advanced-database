package kz.edu.astanait.onlineshop.domain;

import lombok.Builder;
import org.bson.types.Binary;

@Builder
public record Product(String title, String description, Double price,
                      Boolean deleted, Binary image) {
}
