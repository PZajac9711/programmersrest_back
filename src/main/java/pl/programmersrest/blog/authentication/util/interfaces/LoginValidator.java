package pl.programmersrest.blog.authentication.util.interfaces;

import pl.programmersrest.blog.model.exceptions.custom.UserRegistrationException;

/**
 * Defines required pattern for username
 * @author Patryk
 */
public interface LoginValidator {
    /**
     * Check if username matches with pattern.
     * @param s Defines username that need to me checked.
     * @return true when username matches to pattern
     * @throws UserRegistrationException when username did not mach the pattern
     */
    boolean checkLoginPattern(String s) throws UserRegistrationException;
}
