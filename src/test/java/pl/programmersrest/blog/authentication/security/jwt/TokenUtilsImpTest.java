package pl.programmersrest.blog.authentication.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import pl.programmersrest.blog.model.enums.AuthorityEnum;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class TokenUtilsImpTest {
    TokenUtil tokenUtil = new TokenUtilsImp();

    @Test
    public void generateAuthenticateTokenShouldBeSuccessfully(){
        //given
        String username = "admin";
        AuthorityEnum authority = AuthorityEnum.ADMIN;
        //when
        String token = tokenUtil.generateAuthToken(username,authority.getAuthority());
        //then
        Claims claims = Jwts.parser()
                .setSigningKey(TokenDetails.SECRET_AUTH_TOKEN)
                .parseClaimsJws(token)
                .getBody();
        assertEquals(claims.getSubject(), username);
    }

    @Test
    public void generateRefreshTokenShouldBeSuccessfully(){
        //given
        String username = "admin";
        AuthorityEnum authority = AuthorityEnum.ADMIN;
        //when
        String refresh = tokenUtil.generateRefreshToken(username,authority.getAuthority());
        //then
        Claims claims = Jwts.parser()
                .setSigningKey(TokenDetails.SECRET_REFRESH_TOKEN)
                .parseClaimsJws(refresh)
                .getBody();
        assertEquals(claims.getSubject(), username);
    }
}
