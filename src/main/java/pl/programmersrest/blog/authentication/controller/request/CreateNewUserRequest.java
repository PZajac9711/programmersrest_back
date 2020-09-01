package pl.programmersrest.blog.authentication.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateNewUserRequest {
    private String username;
    private String password;
    private String email;
}
