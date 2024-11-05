package kz.edu.astanait.onlineshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.GONE)
public class ProductDeletedException extends RuntimeException {

    public ProductDeletedException(String message) {
        super(message);
    }
}
