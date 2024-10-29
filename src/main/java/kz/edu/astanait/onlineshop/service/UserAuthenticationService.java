package kz.edu.astanait.onlineshop.service;

import kz.edu.astanait.onlineshop.domain.AuthenticatedUser;
import kz.edu.astanait.onlineshop.domain.GeneratedToken;
import kz.edu.astanait.onlineshop.domain.LoginRequest;
import kz.edu.astanait.onlineshop.domain.UserResponse;

public interface UserAuthenticationService {

    GeneratedToken login(LoginRequest request);

    UserResponse getAuthenticatedUser(AuthenticatedUser user);
}
