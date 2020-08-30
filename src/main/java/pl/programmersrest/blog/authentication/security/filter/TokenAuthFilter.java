package pl.programmersrest.blog.authentication.security.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TokenAuthFilter extends OncePerRequestFilter {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authToken = request.getHeader("Authorization");
        if(authToken == null || authToken.equals("")){
            authorizationHeaderError(response);
        }
    }
    private void authorizationHeaderError(HttpServletResponse response) throws IOException {
        response.setStatus(400);
        response.setContentType("json/text");
        Map<String, String> output = new HashMap<>();
        output.put("message", "header is not present or is empty");

        response.getOutputStream().print(objectMapper.writeValueAsString(output));
    }
}
