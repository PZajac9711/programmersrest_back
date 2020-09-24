package pl.programmersrest.blog.authentication.util.interfaces;

public interface PasswordValidator {
    boolean checkPasswordPattern(String password);
}
