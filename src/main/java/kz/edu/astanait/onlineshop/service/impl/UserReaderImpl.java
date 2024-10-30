package kz.edu.astanait.onlineshop.service.impl;

import kz.edu.astanait.onlineshop.domain.AuthenticatedUser;
import kz.edu.astanait.onlineshop.mapper.UserMapper;
import kz.edu.astanait.onlineshop.repository.UserRepository;
import kz.edu.astanait.onlineshop.service.UserReader;
import kz.edu.astanait.onlineshop.util.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserReaderImpl implements UserReader {

    private final UserRepository repository;
    private final UserMapper userMapper;
    private final Clock clock;

    @Override
    public AuthenticatedUser getByEmail(String email) {
        return repository.findByEmail(email)
            .map(userMapper::mapToAuthenticatedUser)
            .orElseThrow(() -> new UsernameNotFoundException("User with email %s was not found".formatted(email)));
    }

    @Override
    public Optional<AuthenticatedUser> getByToken(final String token) {
        var hashedToken = TokenUtils.hash(token);
        return repository.findByHashedToken(hashedToken, LocalDateTime.now(clock))
            .map(userMapper::mapToAuthenticatedUser);
    }
}
