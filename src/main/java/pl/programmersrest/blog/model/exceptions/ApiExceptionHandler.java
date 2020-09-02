package pl.programmersrest.blog.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.programmersrest.blog.model.exceptions.custom.*;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<Object> handlePostNotFoundException(PostNotFoundException ex){
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex, "There's no post with this id");
        return buildApiError(apiError);
    }
    @ExceptionHandler(TitleTakenException.class)
    public ResponseEntity<Object> handleTitleTakenException(TitleTakenException ex){
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,ex,"Title need to bo unique");
        return buildApiError(apiError);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex){
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,ex,"Bad credentials");
        return buildApiError(apiError);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UsernameNotFoundException ex){
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,ex,"User not found");
        return buildApiError(apiError);
    }
    @ExceptionHandler(UserRegistrationException.class)
    public ResponseEntity<Object> handleUserRegistrationException(UserRegistrationException ex){
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex, "Error while creating user");
        return buildApiError(apiError);
    }
    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<Object> handleCommentNotFoundException(CommentNotFoundException ex){
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex, "No comment with this id for post");
        return buildApiError(apiError);
    }
    @ExceptionHandler(DeleteCommentException.class)
    public ResponseEntity<Object> handleDeleteCommentException(DeleteCommentException ex){
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex, "Error while deleting comment");
        return buildApiError(apiError);
    }

    private ResponseEntity<Object> buildApiError(ApiError apiError){
        return new ResponseEntity<>(apiError, apiError.getStatusCode());
    }
}
