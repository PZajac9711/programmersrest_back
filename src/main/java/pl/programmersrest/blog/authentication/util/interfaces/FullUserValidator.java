package pl.programmersrest.blog.authentication.util.interfaces;

import pl.programmersrest.blog.authentication.controller.request.CreateNewUserRequest;
import pl.programmersrest.blog.model.exceptions.custom.UserRegistrationException;

/**
 * Provides all validators for user registration
 */
public interface FullUserValidator extends EmailValidator,LoginValidator,PasswordValidator{
    /**
     * Checking all user registration credentials
     * @param userRegistrationRequest defines user credentials.
     * @return true when all fields matches their patterns.
     * @throws UserRegistrationException when any field didn't match pattern.
     */
    boolean checkAllPatterns(CreateNewUserRequest userRegistrationRequest) throws UserRegistrationException;
}
