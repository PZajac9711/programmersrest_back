package pl.programmersrest.blog.authentication.models;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class RefreshToken extends SecurityToken{
    public RefreshToken(Collection<? extends GrantedAuthority> authorities, String token) {
        super(authorities, token);
    }
}
