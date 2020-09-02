package pl.programmersrest.blog.authentication.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import pl.programmersrest.blog.authentication.security.AuthEntryPoint;
import pl.programmersrest.blog.authentication.security.filter.ErrorHandlingFilter;
import pl.programmersrest.blog.authentication.security.filter.RefreshTokenFilter;
import pl.programmersrest.blog.authentication.security.filter.TokenAuthFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private UserDetailsService userDetailsService;
    private AuthenticationProvider authenticationProvider;
    private AuthenticationProvider tokenAuthenticationProvider;
    private AuthenticationProvider refreshTokenAuthenticationProvider;
    @Autowired
    public WebSecurityConfig(@Qualifier("userDetailsManager") UserDetailsService userDetailsService,
                             @Qualifier("credentialsProvider") AuthenticationProvider authenticationProvider,
                             @Qualifier("tokenAuthProvider") AuthenticationProvider tokenAuthenticationProvider,
                             @Qualifier("refreshAuthProvider") AuthenticationProvider refreshTokenAuthenticationProvider) {
        this.userDetailsService = userDetailsService;
        this.authenticationProvider = authenticationProvider;
        this.tokenAuthenticationProvider = tokenAuthenticationProvider;
        this.refreshTokenAuthenticationProvider = refreshTokenAuthenticationProvider;
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider)
                .authenticationProvider(tokenAuthenticationProvider)
                .authenticationProvider(refreshTokenAuthenticationProvider);
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().disable();
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/authenticate").permitAll()
                .antMatchers(HttpMethod.POST,"/users").permitAll()
                .antMatchers(HttpMethod.GET,"/posts").permitAll()
                .antMatchers(HttpMethod.GET,"/posts/{id}").permitAll()
                .antMatchers(HttpMethod.DELETE, "/posts/{id}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/posts").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/posts/{id}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/posts/{id}/*").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(new AuthEntryPoint())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(new TokenAuthFilter(authenticationManager()), BasicAuthenticationFilter.class);
        http.addFilterBefore(new RefreshTokenFilter(authenticationManager()), BasicAuthenticationFilter.class);
        http.addFilterBefore(new ErrorHandlingFilter(), TokenAuthFilter.class);
    }
}
