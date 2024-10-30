package kz.edu.astanait.onlineshop.service;

import kz.edu.astanait.onlineshop.domain.UserRegistrationRequest;
import kz.edu.astanait.onlineshop.domain.UserResponse;

public interface UserFactory {

    UserResponse create(UserRegistrationRequest request);
}