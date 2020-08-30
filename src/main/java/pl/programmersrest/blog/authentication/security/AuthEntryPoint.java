package pl.programmersrest.blog.authentication.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuthEntryPoint implements AuthenticationEntryPoint {
    ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException, ServletException {
        response.setContentType("json/text");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        //Key(exception name):Value(debug message)
        Map<String,String> output = new HashMap<>();
        output.put("exception",ex.getClass().getName());
        output.put("message",ex.getMessage());

        response.getOutputStream().print(objectMapper.writeValueAsString(output));
    }
}
