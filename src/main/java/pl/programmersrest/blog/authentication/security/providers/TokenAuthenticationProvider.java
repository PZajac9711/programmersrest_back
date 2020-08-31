package pl.programmersrest.blog.authentication.security.providers;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import pl.programmersrest.blog.authentication.models.SecurityToken;
import pl.programmersrest.blog.authentication.security.jwt.TokenDetails;
import pl.programmersrest.blog.authentication.security.jwt.TokenUtil;

import java.util.Arrays;

import static pl.programmersrest.blog.authentication.security.jwt.TokenDetails.SECRET_AUTH_TOKEN;

@Component(value = "tokenAuthProvider")
public class TokenAuthenticationProvider implements AuthenticationProvider {
    private TokenUtil tokenUtil;

    @Autowired
    public TokenAuthenticationProvider(TokenUtil tokenUtil) {
        this.tokenUtil = tokenUtil;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String securityToken = ((SecurityToken) authentication).getToken();
        try{
            Claims claims = tokenUtil.getClaimsFromToken(securityToken, SECRET_AUTH_TOKEN);
            SecurityToken returnSecurityToken = new SecurityToken(Arrays.asList(() -> claims.get("authority").toString()), claims.getSubject());
            returnSecurityToken.setAuthenticated(true);
            System.out.println(returnSecurityToken.isAuthenticated());
            return returnSecurityToken;
        }
        catch (Exception e){
            throw new BadCredentialsException(e.getMessage());
        }
    }

    @Override
    public boolean supports(Class<?> supportedClass) {
        return supportedClass.getName().equals(SecurityToken.class.getName());
    }
}
