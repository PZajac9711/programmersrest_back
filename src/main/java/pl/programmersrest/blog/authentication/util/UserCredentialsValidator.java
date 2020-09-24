package pl.programmersrest.blog.authentication.util;

import org.springframework.stereotype.Component;
import pl.programmersrest.blog.authentication.controller.request.CreateNewUserRequest;
import pl.programmersrest.blog.authentication.util.interfaces.FullUserValidator;
import pl.programmersrest.blog.model.exceptions.custom.UserRegistrationException;

import java.util.regex.Pattern;

@Component
public class UserCredentialsValidator implements FullUserValidator {
    public boolean checkAllPatterns(CreateNewUserRequest userRegistrationRequest) {
        return checkEmailPattern(userRegistrationRequest.getEmail())
                && checkLoginPattern(userRegistrationRequest.getUsername())
                && checkPasswordPattern(userRegistrationRequest.getPassword());
    }

    public boolean checkLoginPattern(String login) {
        String regex = "^[a-zA-Z0-9]{3,}$";
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(login).matches()) {
            throw new UserRegistrationException("Wrong login");
        }
        return true;
    }

    public boolean checkPasswordPattern(String password) {
        return true;
    }

    public boolean checkEmailPattern(String email) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(email).matches()) {
            throw new UserRegistrationException("Wrong email");
        }
        return true;
    }
}
