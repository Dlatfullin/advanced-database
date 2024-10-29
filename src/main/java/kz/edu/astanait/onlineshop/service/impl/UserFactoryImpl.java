package kz.edu.astanait.onlineshop.service.impl;

import kz.edu.astanait.onlineshop.domain.UserRegistrationRequest;
import kz.edu.astanait.onlineshop.domain.UserResponse;
import kz.edu.astanait.onlineshop.exception.AlreadyExistsException;
import kz.edu.astanait.onlineshop.mapper.UserMapper;
import kz.edu.astanait.onlineshop.repository.UserRepository;
import kz.edu.astanait.onlineshop.service.UserFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFactoryImpl implements UserFactory {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse create(final UserRegistrationRequest request) {
        if (userRepository.existsByEmailIgnoreCase(request.email())) {
            throw new AlreadyExistsException("Email is already taken by another user");
        }
        var encodedPassword = passwordEncoder.encode(request.password());
        var user = userRepository.save(userMapper.mapToUserDocument(request, encodedPassword));
        return userMapper.mapToUserResponse(user);
    }
}
