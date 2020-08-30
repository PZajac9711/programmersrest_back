package pl.programmersrest.blog.authentication.security.jwt;

import io.jsonwebtoken.SignatureAlgorithm;

public final class TokenDetails {
    public final static String SECRET_AUTH_TOKEN = "auth";
    public final static String SECRET_REFRESH_TOKEN = "refresh";
    public final static int EXPIRED_AUTH_TOKEN = 900 * 1000;
    public final static int EXPIRED_REFRESH_TOKEN = 900 * 1000 * 2;
    public final static SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    private TokenDetails() {
    }
}
