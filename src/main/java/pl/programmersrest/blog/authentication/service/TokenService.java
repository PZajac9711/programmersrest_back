package pl.programmersrest.blog.authentication.service;

import org.springframework.security.core.Authentication;
import pl.programmersrest.blog.authentication.controller.response.AuthenticationTokenResponse;

/**
 * Provides logic for generate response token which include authentication token and refresh token
 */
public interface TokenService {
    /**
     * Generate set of tokens (authentication,refresh) and save refresh token in database
     * @param authentication spring security context
     * @return AuthenticationTokenResponse never nulls
     * @see AuthenticationTokenResponse
     */
    AuthenticationTokenResponse generateResponseToken(Authentication authentication);
}
