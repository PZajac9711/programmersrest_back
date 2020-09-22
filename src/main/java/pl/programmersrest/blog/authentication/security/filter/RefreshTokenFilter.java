package pl.programmersrest.blog.authentication.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.programmersrest.blog.authentication.models.RefreshToken;
import pl.programmersrest.blog.authentication.security.jwt.TokenDetails;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static pl.programmersrest.blog.authentication.security.jwt.TokenDetails.TOKEN_PREFIX;

public class RefreshTokenFilter extends OncePerRequestFilter {
    private ObjectMapper objectMapper = new ObjectMapper();
    private AuthenticationManager authenticationManager;

    public RefreshTokenFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getHeader("refresh") == null){
            generateException(response, 400, "Token is not present, token should be in refresh header");
            return;
        }
        String refreshToken = request.getHeader("refresh");
        if(!refreshToken.startsWith(TOKEN_PREFIX)){
            generateException(response, 400, "Token didn't start with " + TOKEN_PREFIX);
            return;
        }
        try{
            refreshToken = refreshToken.substring(TOKEN_PREFIX.length());
            Authentication authentication = authenticationManager.authenticate(new RefreshToken(Collections.emptyList(),refreshToken));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request,response);
        }
        catch (AuthenticationException ex){
            generateException(response, 401, ex.getMessage());
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !(request.getRequestURI().equals("/authenticate") && request.getMethod().equals("GET"));
    }

    private void generateException(HttpServletResponse response, int status, String message) throws IOException {
        response.setContentType("json/text");
        response.setStatus(status);
        Map<String,String> errors = new HashMap<>();
        errors.put("Message: ", message);

        response.getOutputStream().print(objectMapper.writeValueAsString(errors));
    }
}
