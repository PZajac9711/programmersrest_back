package pl.programmersrest.blog.model.exceptions.custom;

public class TagNotFoundException extends RuntimeException{
    public TagNotFoundException(String message) {
        super(message);
    }
}
