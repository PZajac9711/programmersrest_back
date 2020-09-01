package pl.programmersrest.blog.authentication.security.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.programmersrest.blog.authentication.models.SecurityToken;
import pl.programmersrest.blog.authentication.security.jwt.TokenDetails;
import pl.programmersrest.blog.authentication.security.jwt.TokenUtil;
import pl.programmersrest.blog.authentication.security.jwt.TokenUtilsImp;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static pl.programmersrest.blog.authentication.security.jwt.TokenDetails.SECRET_AUTH_TOKEN;
import static pl.programmersrest.blog.authentication.security.jwt.TokenDetails.TOKEN_PREFIX;

public class TokenAuthFilter extends OncePerRequestFilter {
    private ObjectMapper objectMapper = new ObjectMapper();
    private AuthenticationManager authenticationManager;

    public TokenAuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authToken = request.getHeader("Authorization");
        if(authToken == null || authToken.equals("")){
            authorizationHeaderError(response, "authorization header is not present or is empty",400);
        }
        else{
            if(!authToken.startsWith(TOKEN_PREFIX)){
                authorizationHeaderError(response, "token not starts with Bearer ",400);
            }
            else{
                authToken = authToken.substring(TOKEN_PREFIX.length());
                SecurityToken securityToken = new SecurityToken(Collections.emptyList(), authToken);
                try{
                    Authentication authentication = authenticationManager.authenticate(securityToken);
                    System.out.println(authentication.isAuthenticated());
                    if(authentication.isAuthenticated()){
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        filterChain.doFilter(request,response);
                    }
                    else{
                        authorizationHeaderError(response, "Token is not authenticated",401);
                    }
                }
                catch (AuthenticationException ex){
                    authorizationHeaderError(response, ex.getMessage(), 401);
                }
            }
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/authenticate")
                || (request.getServletPath().equals("/users") && request.getMethod().equals("POST"))
                || (request.getServletPath().equals("/posts") && request.getMethod().equals("GET"))
                || (request.getServletPath().matches("/posts/.{1,4}") && request.getMethod().equals("GET"))
                || (request.getServletPath().matches("/h2-console/*"));
    }

    private void authorizationHeaderError(HttpServletResponse response, String message, int statusCode) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("json/text");
        Map<String, String> output = new HashMap<>();
        output.put("message", message);

        response.getOutputStream().print(objectMapper.writeValueAsString(output));
    }
}
