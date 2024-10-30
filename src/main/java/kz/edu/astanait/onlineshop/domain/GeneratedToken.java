package kz.edu.astanait.onlineshop.domain;

import java.time.LocalDateTime;

public record GeneratedToken(String token,
                             LocalDateTime expiredAt) {
}
