package kz.edu.astanait.onlineshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.edu.astanait.onlineshop.configuration.OpenApiConfig;
import kz.edu.astanait.onlineshop.domain.AuthenticatedUser;
import kz.edu.astanait.onlineshop.domain.GeneratedToken;
import kz.edu.astanait.onlineshop.domain.LoginRequest;
import kz.edu.astanait.onlineshop.domain.UserRegistrationRequest;
import kz.edu.astanait.onlineshop.domain.UserResponse;
import kz.edu.astanait.onlineshop.service.UserAuthenticationService;
import kz.edu.astanait.onlineshop.service.UserFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Identity")
@RestController
@Validated
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserIdentityController {

    private final UserFactory userFactory;
    private final UserAuthenticationService userAuthenticationService;

    @Operation(summary = "Register as a new user")
    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> register(final @Valid @RequestBody UserRegistrationRequest registrationRequest) {
        var user = userFactory.create(registrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @Operation(summary = "Authenticate as a user")
    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeneratedToken> createToken(final @RequestBody LoginRequest loginRequest) {
        var generatedToken = userAuthenticationService.login(loginRequest);
        return ResponseEntity.ok(generatedToken);
    }

    @Operation(summary = "Get the authenticated user information", security = @SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME))
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> getAuthenticatedUser(final @AuthenticationPrincipal AuthenticatedUser user) {
        var authenticatedUser = userAuthenticationService.getAuthenticatedUser(user);
        return ResponseEntity.ok(authenticatedUser);
    }
}
