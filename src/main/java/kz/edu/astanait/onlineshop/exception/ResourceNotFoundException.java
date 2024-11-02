package kz.edu.astanait.onlineshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(final String message) {
        super(message);
    }

    public static ResourceNotFoundException userNotFoundByEmail(final String email) {
        return new ResourceNotFoundException("User with email %s was not found".formatted(email));
    }

    public static ResourceNotFoundException categoryNotFoundById(final String id) {
        return new ResourceNotFoundException("Category with id %s was not found".formatted(id));
    }

    public static ResourceNotFoundException productNotFoundById(final String id) {
        return new ResourceNotFoundException("Product with id %s was not found".formatted(id));
    }

    public static ResourceNotFoundException imageNotFoundById(final String id) {
        return new ResourceNotFoundException("Image on the product id %s was not found".formatted(id));
    }
}
