package pl.programmersrest.blog.authentication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.var;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.programmersrest.blog.authentication.controller.request.AuthenticationRequest;
import pl.programmersrest.blog.authentication.controller.response.AuthenticationTokenResponse;
import pl.programmersrest.blog.authentication.entity.RefreshTokenEntity;
import pl.programmersrest.blog.authentication.repository.RefreshTokenRepository;
import pl.programmersrest.blog.authentication.security.jwt.TokenDetails;
import pl.programmersrest.blog.authentication.security.jwt.TokenUtil;
import pl.programmersrest.blog.model.enums.AuthorityEnum;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    TokenUtil tokenUtil;

    @MockBean
    RefreshTokenRepository refreshTokenRepository;

    ObjectMapper objectMapper;
    AuthenticationRequest credentials;

    @Before
    public void setup() {
        objectMapper = new ObjectMapper();

        credentials = new AuthenticationRequest();
        credentials.setUsername("admin");
        credentials.setPassword("admin");
    }

    //integration test
    @Test
    public void authenticateUserTestShouldBeSuccessfully() throws Exception {
        MvcResult result = mockMvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(credentials)))
                .andExpect(status().isOk())
                .andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        AuthenticationTokenResponse response = objectMapper.readValue(jsonResponse, AuthenticationTokenResponse.class);

        Claims claims = tokenUtil.getClaimsFromToken(response.getToken(), TokenDetails.SECRET_AUTH_TOKEN);
        //then
        assertEquals(claims.getSubject(), credentials.getUsername());
        assertEquals(claims.get("authority").toString(), AuthorityEnum.ADMIN.getAuthority());
    }

    @Test
    public void authenticateUserTestBadRequestUsernameIsNull() throws Exception {
        //given
        credentials.setUsername(null);
        //when
        mockMvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(credentials)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void authenticateUserTestBadRequestPasswordIsNull() throws Exception {
        //given
        credentials.setPassword(null);
        //when
        mockMvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(credentials)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void authenticateWithRefreshTokenShouldBeSuccessfully() throws Exception {
        //given
        String refreshToken = tokenUtil.generateRefreshToken(credentials.getUsername(), AuthorityEnum.ADMIN.getAuthority());
        var refreshTokenEntity = RefreshTokenEntity.builder()
                .username("admin")
                .token(refreshToken)
                .id(1L)
                .build();
        when(refreshTokenRepository.findRefreshTokenEntitiesByUsername("admin")).thenReturn(Optional.of(refreshTokenEntity));
        //when
        MvcResult result = mockMvc.perform(get("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .header("refresh", TokenDetails.TOKEN_PREFIX + refreshToken))
                .andExpect(status().isOk())
                .andReturn();
        //then
        String json = result.getResponse().getContentAsString();
        AuthenticationTokenResponse response = objectMapper.readValue(json, AuthenticationTokenResponse.class);

        Claims claims = tokenUtil.getClaimsFromToken(response.getToken(),TokenDetails.SECRET_AUTH_TOKEN);
        System.out.println(claims.getSubject());
        assertEquals(claims.getSubject(), credentials.getUsername());
    }
    @Test
    public void authenticateWithRefreshTokenShouldThrowBadCredentialsException() throws Exception {
        //given
        String refreshToken = tokenUtil.generateRefreshToken(credentials.getUsername(), AuthorityEnum.ADMIN.getAuthority());
        //when
        MvcResult result = mockMvc.perform(get("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .header("refresh", TokenDetails.TOKEN_PREFIX + refreshToken))
                .andExpect(status().isUnauthorized())
                .andReturn();
        //then
        String json = result.getResponse().getContentAsString();
        assertTrue(json.contains("\"Message: \":\"No refresh token in database\""));
    }
}
