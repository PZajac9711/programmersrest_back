package pl.programmersrest.blog.authentication.security.jwt;

public interface TokenUtil {
    String generateAuthToken(String username, String authority);
    String generateRefreshToken(String username, String authority);
}
