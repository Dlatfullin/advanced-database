package kz.edu.astanait.onlineshop.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ProductSaveRequest(@NotNull(message = "Title cannot be null")
                                 @Size(min = 3, message = "Title must be more than 3 characters")
                                 String title,
                                 @Size(max = 500, message = "Description must be less than 500 characters")
                                 String description,
                                 @NotNull(message = "Price cannot be null")
                                 @PositiveOrZero(message = "Price must be zero or a positive value")
                                 Double price,
                                 @NotNull(message = "Deleted status cannot be null")
                                 Boolean deleted) {
}
