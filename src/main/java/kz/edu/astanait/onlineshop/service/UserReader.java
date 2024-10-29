package kz.edu.astanait.onlineshop.service;

import kz.edu.astanait.onlineshop.domain.AuthenticatedUser;

import java.util.Optional;

public interface UserReader {

    AuthenticatedUser getByEmail(String token);

    Optional<AuthenticatedUser> getByToken(String token);
}
