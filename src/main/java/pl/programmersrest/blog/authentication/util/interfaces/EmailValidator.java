package pl.programmersrest.blog.authentication.util.interfaces;

import pl.programmersrest.blog.model.exceptions.custom.UserRegistrationException;

/**
 * Defines required pattern for email
 * @author Patryk
 */
public interface EmailValidator {
    /**
     * Check if email matches with pattern.
     * @param s Defines email that need to be checked.
     * @return true when email matches to pattern
     * @throws UserRegistrationException when email did not mach the pattern
     */
    boolean checkEmailPattern(String s) throws UserRegistrationException;
}
