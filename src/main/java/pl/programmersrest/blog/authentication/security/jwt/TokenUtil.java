package pl.programmersrest.blog.authentication.security.jwt;

import io.jsonwebtoken.Claims;

public interface TokenUtil {
    String generateAuthToken(String username, String authority);
    String generateRefreshToken(String username, String authority);
    Claims getClaimsFromToken(String token, String secret);
}
