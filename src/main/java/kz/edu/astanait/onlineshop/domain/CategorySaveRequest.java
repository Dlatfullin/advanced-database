package kz.edu.astanait.onlineshop.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CategorySaveRequest(String id,
                                  @NotNull(message = "Name cannot be null")
                                  @Size(min = 3, message = "Name must be at least 3 characters long")
                                  String name) {
}
