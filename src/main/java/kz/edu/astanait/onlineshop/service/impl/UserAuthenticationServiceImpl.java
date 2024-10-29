package kz.edu.astanait.onlineshop.service.impl;

import kz.edu.astanait.onlineshop.domain.AuthenticatedUser;
import kz.edu.astanait.onlineshop.domain.GeneratedToken;
import kz.edu.astanait.onlineshop.domain.LoginRequest;
import kz.edu.astanait.onlineshop.domain.UserResponse;
import kz.edu.astanait.onlineshop.mapper.UserMapper;
import kz.edu.astanait.onlineshop.service.TokenFactory;
import kz.edu.astanait.onlineshop.service.UserAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final TokenFactory tokenFactory;

    @Override
    public GeneratedToken login(final LoginRequest request) {
        var email = request.email();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, request.password()));
        return tokenFactory.create(email);
    }

    @Override
    public UserResponse getAuthenticatedUser(final AuthenticatedUser user) {
        return userMapper.mapToUserResponse(user);
    }
}
