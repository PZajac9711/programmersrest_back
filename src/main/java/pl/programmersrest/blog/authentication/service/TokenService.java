package pl.programmersrest.blog.authentication.service;

import org.springframework.security.core.Authentication;
import pl.programmersrest.blog.authentication.controller.response.AuthenticationTokenResponse;

public interface TokenService {
    AuthenticationTokenResponse generateResponseToken(Authentication authentication);
}
