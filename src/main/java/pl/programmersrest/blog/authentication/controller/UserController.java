package pl.programmersrest.blog.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.programmersrest.blog.authentication.controller.request.CreateNewUserRequest;
import pl.programmersrest.blog.authentication.models.CreateNewUserWrapper;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private UserDetailsManager userDetailsManager;

    @Autowired
    public UserController(@Qualifier("userDetailsManager") UserDetailsManager userDetailsManager) {
        this.userDetailsManager = userDetailsManager;
    }

    //ToDo: \/
    //get/{username} - get a specific user
    //put/{username} - update password
    //delete/{username} - delete user
    @PostMapping
    public ResponseEntity<Void> createNewUser(@RequestBody CreateNewUserRequest userDetails){
        if(userDetails.getPassword() == null || userDetails.getUsername() == null || userDetails.getEmail() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userDetailsManager.createUser(new CreateNewUserWrapper(userDetails));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
