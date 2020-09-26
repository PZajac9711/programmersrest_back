package pl.programmersrest.blog.authentication.util.interfaces;

import pl.programmersrest.blog.model.exceptions.custom.UserRegistrationException;

/**
 * Defines required pattern for password
 * @author Patryk
 */
public interface PasswordValidator {
    /**
     * Check if password matches with pattern.
     * @param s defines password that need to be checked.
     * @return true when password matches to pattern
     * @throws UserRegistrationException when password did not match the pattern
     */
    boolean checkPasswordPattern(String s) throws UserRegistrationException;
}
