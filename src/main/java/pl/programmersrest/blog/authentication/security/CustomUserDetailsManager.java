package pl.programmersrest.blog.authentication.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;
import pl.programmersrest.blog.authentication.models.CreateNewUserWrapper;
import pl.programmersrest.blog.authentication.models.SecurityUser;
import pl.programmersrest.blog.authentication.util.UserCredentialsValidator;
import pl.programmersrest.blog.model.entity.User;
import pl.programmersrest.blog.model.enums.AuthorityEnum;
import pl.programmersrest.blog.model.exceptions.custom.UserRegistrationException;
import pl.programmersrest.blog.model.repository.UserRepository;

import java.sql.Date;

@Component(value = "userDetailsManager")
public class CustomUserDetailsManager implements UserDetailsManager {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserCredentialsValidator userCredentialsValidator;

    @Autowired
    public CustomUserDetailsManager(UserRepository userRepository, PasswordEncoder passwordEncoder, UserCredentialsValidator userCredentialsValidator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userCredentialsValidator = userCredentialsValidator;
    }

    @Override
    public void createUser(UserDetails userDetails) {
        CreateNewUserWrapper details = (CreateNewUserWrapper) userDetails;
        String email = details.getCreateNewUserRequest().getEmail();
        userCredentialsValidator.checkAllPatterns(details.getCreateNewUserRequest());

        if(userExists(details.getUsername())){
            throw new UserRegistrationException("User already exists");
        }
        if(userRepository.findUserByEmail(email).isPresent()){
            throw new UserRegistrationException("Email is already taken");
        }

        User user = User.builder()
                .username(details.getUsername())
                .password(passwordEncoder.encode(details.getPassword()))
                .email(email)
                .joinDate(new Date(System.currentTimeMillis()))
                .role(AuthorityEnum.USER)
                .build();
        userRepository.save(user);
    }

    @Override
    public void updateUser(UserDetails userDetails) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        //ToDo: Should we check if old and new password are the same ??, it's stupid but why user can't do this.
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = ((SecurityUser) loadUserByUsername(username)).getUser();
        if(passwordEncoder.matches(oldPassword, user.getPassword())){
            user.setPassword(passwordEncoder.encode(newPassword));
        }
        else{
            throw new BadCredentialsException("Wrong password");
        }

        userRepository.save(user);
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
