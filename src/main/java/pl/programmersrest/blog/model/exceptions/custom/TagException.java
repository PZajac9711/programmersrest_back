package pl.programmersrest.blog.model.exceptions.custom;

public class TagException extends RuntimeException{
    public TagException(String message) {
        super(message);
    }
}
