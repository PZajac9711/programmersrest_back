package pl.programmersrest.blog.authentication.util.interfaces;

import pl.programmersrest.blog.authentication.controller.request.CreateNewUserRequest;

public interface FullUserValidator extends EmailValidator,LoginValidator,PasswordValidator{
    boolean checkAllPatterns(CreateNewUserRequest userRegistrationRequest);
}
