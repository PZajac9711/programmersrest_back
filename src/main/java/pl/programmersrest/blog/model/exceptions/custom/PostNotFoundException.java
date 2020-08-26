package pl.programmersrest.blog.model.exceptions.custom;

public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(String message) {
        super(message);
    }
}
