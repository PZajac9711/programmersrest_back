package pl.programmersrest.blog.authentication.security.jwt;

import io.jsonwebtoken.Claims;

/**
 * Provides basic operations with JWT
 * @author Patryk
 */
public interface TokenUtil {
    /**
     * Generate correct JWT for Authorization
     * @param username Specified user for which token should be generate
     * @param authority Specified authority which is assigned to user
     * @return Authorization JWT never null
     * @see pl.programmersrest.blog.model.enums.AuthorityEnum
     */
    String generateAuthToken(String username, String authority);
    /**
     * Generate correct refresh JWT.
     * @param username Specified user for which token should be generate
     * @param authority Specified authority which is assigned to user
     * @return Refresh JWT never null
     * @see pl.programmersrest.blog.model.enums.AuthorityEnum
     */
    String generateRefreshToken(String username, String authority);

    /**
     * Returns claims from JWT
     * @param token Specified JWT.
     * @param secret Specified secret key which jwt was signed.
     * @return claims never null
     */
    Claims getClaimsFromToken(String token, String secret);
}
