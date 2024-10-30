package kz.edu.astanait.onlineshop.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
public class TokenDocument {

    private byte[] hash;
    private LocalDateTime expiredAt;
}
