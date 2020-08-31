package pl.programmersrest.blog.authentication.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.CharConversionException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ErrorHandlingFilter extends OncePerRequestFilter {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        }
        catch (CharConversionException ex){
            ex.printStackTrace();
            generateError(response, "Char conversion exception", 401);
        }
        catch (Exception ex){
            ex.printStackTrace();
            generateError(response, "Unexpected error", 500);
        }
    }
    private void generateError(HttpServletResponse response, String message, int status) throws IOException {
        response.setContentType("json/text");
        response.setStatus(status);
        Map<String,String> errors = new HashMap<>();
        errors.put("Message: ", message);

        response.getOutputStream().print(objectMapper.writeValueAsString(errors));
    }
}
