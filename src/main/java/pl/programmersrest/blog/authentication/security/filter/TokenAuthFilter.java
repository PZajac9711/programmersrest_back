package pl.programmersrest.blog.authentication.security.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static pl.programmersrest.blog.authentication.security.jwt.TokenDetails.SECRET_AUTH_TOKEN;
import static pl.programmersrest.blog.authentication.security.jwt.TokenDetails.TOKEN_PREFIX;

public class TokenAuthFilter extends OncePerRequestFilter {
    private ObjectMapper objectMapper = new ObjectMapper();
    private TokenUtil tokenUtil = new TokenUtilsImp();
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authToken = request.getHeader("Authorization");
        if(authToken == null || authToken.equals("")){
            authorizationHeaderError(response, "authorization header is not present or is empty");
        }
        else{
            if(!authToken.startsWith(TOKEN_PREFIX)){
                authorizationHeaderError(response, "token not starts with Bearer ");
            }
            else{
                authToken = authToken.substring(TOKEN_PREFIX.length());
                Claims claims = tokenUtil.getClaimsFromToken(authToken, SECRET_AUTH_TOKEN);
                String authority = claims.get("authority").toString();
                SecurityToken securityToken = new SecurityToken(Arrays.asList(() -> authority), claims.getSubject());

                SecurityContextHolder.getContext().setAuthentication(securityToken);
                filterChain.doFilter(request,response);
            }
        }
    }
    private void authorizationHeaderError(HttpServletResponse response,String message) throws IOException {
        response.setStatus(400);
        response.setContentType("json/text");
        Map<String, String> output = new HashMap<>();
        output.put("message", message);

        response.getOutputStream().print(objectMapper.writeValueAsString(output));
    }
}
