package pl.programmersrest.blog.authentication.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;
import pl.programmersrest.blog.authentication.controller.request.CreateNewUserRequest;
import pl.programmersrest.blog.authentication.models.CreateNewUserWrapper;
import pl.programmersrest.blog.authentication.models.SecurityUser;
import pl.programmersrest.blog.model.entity.User;
import pl.programmersrest.blog.model.enums.AuthorityEnum;
import pl.programmersrest.blog.model.exceptions.custom.UserRegistrationException;
import pl.programmersrest.blog.model.repository.UserRepository;

import java.sql.Date;
import java.util.regex.Pattern;

@Component(value = "userDetailsManager")
public class CustomUserDetailsManager implements UserDetailsManager {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public CustomUserDetailsManager(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(UserDetails userDetails) {
        CreateNewUserWrapper details = (CreateNewUserWrapper) userDetails;
        String email = details.getCreateNewUserRequest().getEmail();
        checkAllPatterns(details.getCreateNewUserRequest());

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

    private boolean checkAllPatterns(CreateNewUserRequest userRegistrationRequest) {
        return checkEmailPattern(userRegistrationRequest.getEmail())
                && checkLoginPattern(userRegistrationRequest.getUsername())
                && checkPasswordPattern(userRegistrationRequest.getPassword());
    }

    private boolean checkLoginPattern(String login) {
        String regex = "^[a-zA-Z0-9]{3,}$";
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(login).matches()) {
            throw new UserRegistrationException("Wrong login");
        }
        return true;
    }

    private boolean checkPasswordPattern(String password) {
        return true;
    }

    private boolean checkEmailPattern(String email) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(email).matches()) {
            throw new UserRegistrationException("Wrong email");
        }
        return true;
    }
}
