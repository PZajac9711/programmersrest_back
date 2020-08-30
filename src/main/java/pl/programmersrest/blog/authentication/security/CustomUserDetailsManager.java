package pl.programmersrest.blog.authentication.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;
import pl.programmersrest.blog.authentication.models.SecurityUser;
import pl.programmersrest.blog.model.entity.User;
import pl.programmersrest.blog.model.repository.UserRepository;

@Component(value = "userDetailsManager")
public class CustomUserDetailsManager implements UserDetailsManager {
    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(UserDetails userDetails) {

    }

    @Override
    public void updateUser(UserDetails userDetails) {

    }

    @Override
    public void deleteUser(String s) {

    }

    @Override
    public void changePassword(String s, String s1) {

    }

    @Override
    public boolean userExists(String username) {
        return userRepository.findUserByUsername(username).isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new SecurityUser(user);
    }
}
