package kz.edu.astanait.onlineshop.mapper;

import kz.edu.astanait.onlineshop.document.UserDocument;
import kz.edu.astanait.onlineshop.domain.AuthenticatedUser;
import kz.edu.astanait.onlineshop.domain.UserRegistrationRequest;
import kz.edu.astanait.onlineshop.domain.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDocument mapToUserDocument(final UserRegistrationRequest registrationRequest,
                                          final String hashedPassword) {
        var user = new UserDocument();
        user.setFullName(registrationRequest.fullName());
        user.setEmail(registrationRequest.email());
        user.setPassword(hashedPassword);
        user.setRole("ROLE_USER");
        return user;
    }

    public UserResponse mapToUserResponse(final UserDocument userEntity) {
        return new UserResponse(userEntity.getId(), userEntity.getFullName(), userEntity.getEmail());
    }

    public UserResponse mapToUserResponse(final AuthenticatedUser authenticatedUser) {
        return new UserResponse(authenticatedUser.getId(), authenticatedUser.getFullName(), authenticatedUser.getEmail());
    }

    public AuthenticatedUser mapToAuthenticatedUser(final UserDocument userDocument) {
        return new AuthenticatedUser(userDocument.getId(), userDocument.getFullName(), userDocument.getEmail(), userDocument.getPassword(), userDocument.getRole());
    }
}
