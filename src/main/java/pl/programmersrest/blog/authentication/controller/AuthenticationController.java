package pl.programmersrest.blog.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.programmersrest.blog.authentication.controller.request.AuthenticationRequest;
import pl.programmersrest.blog.authentication.controller.response.AuthenticationTokenResponse;
import pl.programmersrest.blog.authentication.service.TokenService;

@RestController
public class AuthenticationController {
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;
    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<AuthenticationTokenResponse> authenticateUser(@RequestBody AuthenticationRequest credentials) {
        if (credentials.getPassword() == null || credentials.getUsername() == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword())
        );
        return new ResponseEntity<>(tokenService.generateResponseToken(authentication), HttpStatus.OK);
    }
}
