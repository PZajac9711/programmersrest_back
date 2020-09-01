package pl.programmersrest.blog.model.exceptions.custom;

public class UserRegistrationException extends RuntimeException{
    public UserRegistrationException(String message) {
        super(message);
    }
}
