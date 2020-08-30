package pl.programmersrest.blog.authentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import pl.programmersrest.blog.authentication.controller.response.AuthenticationTokenResponse;
import pl.programmersrest.blog.authentication.security.jwt.TokenUtil;

import java.util.Collection;
import java.util.Iterator;

@Service(value = "tokenService")
public class TokenServiceImp implements TokenService{
    private TokenUtil tokenUtil;

    @Autowired
    public TokenServiceImp(TokenUtil tokenUtil) {
        this.tokenUtil = tokenUtil;
    }

    @Override
    public AuthenticationTokenResponse generateResponseToken(Authentication authentication) {
        return generateTokenResponse(authentication);
    }

    private AuthenticationTokenResponse generateTokenResponse(Authentication authentication){
        String authority = getAuthority(authentication.getAuthorities());
        String username = authentication.getName();

        String authToken = tokenUtil.generateAuthToken(username, authority);
        String refreshToken = tokenUtil.generateRefreshToken(username, authority);
        return new AuthenticationTokenResponse(authToken,refreshToken);
    }

    private String getAuthority(Collection<? extends GrantedAuthority> authorities) {
        //ToDo: figure better solution, to get authority or switch to list of authority instead of single authority
        String authority = "";
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        while (iterator.hasNext()){
            authority = iterator.next().getAuthority();
        }
        return authority;
    }

}
