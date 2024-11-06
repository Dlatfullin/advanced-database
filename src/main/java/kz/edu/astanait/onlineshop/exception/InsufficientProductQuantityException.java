package kz.edu.astanait.onlineshop.exception;

public class InsufficientProductQuantityException extends RuntimeException {

    public InsufficientProductQuantityException(String message) {
        super(message);
    }
}
