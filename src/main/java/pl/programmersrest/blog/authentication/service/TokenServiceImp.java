package pl.programmersrest.blog.authentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import pl.programmersrest.blog.authentication.controller.response.AuthenticationTokenResponse;
import pl.programmersrest.blog.authentication.entity.RefreshTokenEntity;
import pl.programmersrest.blog.authentication.repository.RefreshTokenRepository;
import pl.programmersrest.blog.authentication.security.jwt.TokenUtil;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

@Service(value = "tokenService")
public class TokenServiceImp implements TokenService{
    private TokenUtil tokenUtil;
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    public TokenServiceImp(TokenUtil tokenUtil, RefreshTokenRepository refreshTokenRepository) {
        this.tokenUtil = tokenUtil;
        this.refreshTokenRepository = refreshTokenRepository;
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
        saveRefreshTokenInDatabase(refreshToken, username);
        return new AuthenticationTokenResponse(authToken,refreshToken);
    }

    private void saveRefreshTokenInDatabase(String refreshToken, String username){
        Optional<RefreshTokenEntity> tokenEntityOptional = refreshTokenRepository.findRefreshTokenEntitiesByUsername(username);
        if(tokenEntityOptional.isPresent()){
            tokenEntityOptional.get().setToken(refreshToken);
            refreshTokenRepository.save(tokenEntityOptional.get());
        }
        else{
            RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.builder()
                    .token(refreshToken)
                    .username(username)
                    .build();
            refreshTokenRepository.save(refreshTokenEntity);
        }
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
