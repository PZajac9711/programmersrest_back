package pl.programmersrest.blog.model.exceptions.custom;

public class DeleteCommentException extends RuntimeException{
    public DeleteCommentException(String message) {
        super(message);
    }
}
