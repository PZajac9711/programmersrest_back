package pl.programmersrest.blog.model.exceptions.custom;

public class CommentNotFoundException extends RuntimeException{
    public CommentNotFoundException(String message) {
        super(message);
    }
}
