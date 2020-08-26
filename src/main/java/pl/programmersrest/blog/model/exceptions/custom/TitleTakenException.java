package pl.programmersrest.blog.model.exceptions.custom;

public class TitleTakenException extends RuntimeException{
    public TitleTakenException(String message) {
        super(message);
    }
}
