package pl.programmersrest.blog.authentication.models;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class SecurityToken extends AbstractAuthenticationToken {
    private String username;
    public SecurityToken(Collection<? extends GrantedAuthority> authorities, String username) {
        super(authorities);
    }

    @Override
    public Object getCredentials() {
        return username;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }
}
