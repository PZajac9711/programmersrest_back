package pl.programmersrest.blog.authentication.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.programmersrest.blog.authentication.controller.request.CreateNewUserRequest;
import pl.programmersrest.blog.model.enums.AuthorityEnum;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class CreateNewUserWrapper implements UserDetails {
    private CreateNewUserRequest createNewUserRequest;

    public CreateNewUserWrapper(CreateNewUserRequest createNewUserRequest) {
        this.createNewUserRequest = createNewUserRequest;
    }

    public CreateNewUserRequest getCreateNewUserRequest() {
        return createNewUserRequest;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return createNewUserRequest.getPassword();
    }

    @Override
    public String getUsername() {
        return createNewUserRequest.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
