package pl.programmersrest.blog.model.exceptions.custom;

public class NoAuthException extends RuntimeException{
    public NoAuthException(String message) {
        super(message);
    }
}
