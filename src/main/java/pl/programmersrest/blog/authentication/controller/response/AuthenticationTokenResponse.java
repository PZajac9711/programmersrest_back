package pl.programmersrest.blog.authentication.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationTokenResponse {
    private String token;
    private String refreshToken;
}
