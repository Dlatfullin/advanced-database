package kz.edu.astanait.onlineshop.service.impl;

import kz.edu.astanait.onlineshop.document.TokenDocument;
import kz.edu.astanait.onlineshop.domain.GeneratedToken;
import kz.edu.astanait.onlineshop.exception.ResourceNotFoundException;
import kz.edu.astanait.onlineshop.repository.UserRepository;
import kz.edu.astanait.onlineshop.service.TokenFactory;
import kz.edu.astanait.onlineshop.util.TokenUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class TokenFactoryImpl implements TokenFactory {

    private final UserRepository userRepository;
    private final Clock clock;
    private final Duration expireAfter;

    public TokenFactoryImpl(final UserRepository userRepository,
                            final Clock clock,
                            final @Value("${spring.security.token.expire-after}") Duration expireAfter) {
        this.userRepository = userRepository;
        this.clock = clock;
        this.expireAfter = expireAfter;
    }

    @Override
    public GeneratedToken create(final String email) {
        var plainText = TokenUtils.createPlainTextToken();
        var hashedToken = TokenUtils.hash(plainText);
        var user = userRepository.findByEmail(email)
                                 .orElseThrow(() -> ResourceNotFoundException.userNotFoundByEmail(email));
        var expiredAt = LocalDateTime.now(clock).plus(expireAfter);
        user.addToken(new TokenDocument(hashedToken, expiredAt));
        userRepository.save(user);
        return new GeneratedToken(new String(plainText, StandardCharsets.UTF_8), expiredAt);
    }
}
