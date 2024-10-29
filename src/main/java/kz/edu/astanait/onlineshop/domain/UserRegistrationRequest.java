package kz.edu.astanait.onlineshop.domain;

public record UserRegistrationRequest(String fullName,
                                      String email,
                                      String password) {
}
