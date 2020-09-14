package pl.programmersrest.blog.authentication.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

import static pl.programmersrest.blog.authentication.security.jwt.TokenDetails.*;
@Component
public class TokenUtilsImp implements TokenUtil{
    @Override
    public String generateAuthToken(String username, String authority) {
        return generateToken(SECRET_AUTH_TOKEN, username, authority, EXPIRED_AUTH_TOKEN);
    }

    @Override
    public String generateRefreshToken(String username, String authority) {
        return generateToken(SECRET_REFRESH_TOKEN, username, authority, EXPIRED_REFRESH_TOKEN);
    }

    @Override
    public Claims getClaimsFromToken(String token, String secret) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    private String generateToken(String secret, String username, String authority, int expiredTime){
        long currentTime = System.currentTimeMillis();
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, SIGNATURE_ALGORITHM.getJcaName());

        return Jwts.builder()
                .setSubject(username)
                .claim("authority",authority)
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(currentTime + expiredTime))
                .signWith(SIGNATURE_ALGORITHM, signingKey)
                .compact();
    }
}
