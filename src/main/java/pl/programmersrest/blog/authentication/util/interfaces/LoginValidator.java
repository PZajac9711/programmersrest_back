package pl.programmersrest.blog.authentication.util.interfaces;

public interface LoginValidator {
    boolean checkLoginPattern(String login);
}
