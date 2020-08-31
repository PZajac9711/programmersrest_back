package pl.programmersrest.blog.authentication.security.providers;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import pl.programmersrest.blog.authentication.entity.RefreshTokenEntity;
import pl.programmersrest.blog.authentication.models.RefreshToken;
import pl.programmersrest.blog.authentication.models.SecurityToken;
import pl.programmersrest.blog.authentication.repository.RefreshTokenRepository;
import pl.programmersrest.blog.authentication.security.jwt.TokenUtil;

import java.util.Arrays;
import java.util.Optional;

import static pl.programmersrest.blog.authentication.security.jwt.TokenDetails.SECRET_REFRESH_TOKEN;

@Component(value = "refreshAuthProvider")
public class RefreshTokenProvider implements AuthenticationProvider {
    private TokenUtil tokenUtil;
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    public RefreshTokenProvider(TokenUtil tokenUtil, RefreshTokenRepository refreshTokenRepository) {
        this.tokenUtil = tokenUtil;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String refreshToken = ((RefreshToken) authentication).getToken();
        try{
            Claims claims = tokenUtil.getClaimsFromToken(refreshToken, SECRET_REFRESH_TOKEN);
            Optional<RefreshTokenEntity> refreshTokenEntity = refreshTokenRepository.findRefreshTokenEntitiesByUsername(claims.getSubject());
            if(!refreshTokenEntity.isPresent()){
                throw new BadCredentialsException("No refresh token in database");
            }
            if(!refreshTokenEntity.get().getToken().equals(refreshToken)){
                throw new BadCredentialsException("Token is invalid or it been already used");
            }
            refreshTokenEntity.get().setToken(tokenUtil.generateRefreshToken(claims.getSubject(),claims.get("authority").toString()));
            refreshTokenRepository.save(refreshTokenEntity.get());

            SecurityToken returnSecurityToken = new SecurityToken(Arrays.asList(() -> claims.get("authority").toString()), claims.getSubject());
            returnSecurityToken.setAuthenticated(true);
            return returnSecurityToken;
        }
        catch (Exception e){
            throw new BadCredentialsException(e.getMessage());
        }
    }

    @Override
    public boolean supports(Class<?> supportedClass) {
        return supportedClass.getName().equals(RefreshToken.class.getName());
    }
}
