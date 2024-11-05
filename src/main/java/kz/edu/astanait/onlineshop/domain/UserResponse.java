package kz.edu.astanait.onlineshop.domain;

import java.math.BigDecimal;

public record UserResponse(String id,
                           String fullName,
                           String email,
                           BigDecimal balance) {
}
