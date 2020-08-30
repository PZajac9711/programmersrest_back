package pl.programmersrest.blog.authentication.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.programmersrest.blog.authentication.security.AuthEntryPoint;
import pl.programmersrest.blog.authentication.security.filter.TokenAuthFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private UserDetailsService userDetailsService;
    private AuthenticationProvider authenticationProvider;
    @Autowired
    public WebSecurityConfig(@Qualifier("userDetailsManager") UserDetailsService userDetailsService,
                             @Qualifier("credentialsProvider") AuthenticationProvider authenticationProvider) {
        this.userDetailsService = userDetailsService;
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/authenticate").permitAll()
                .antMatchers(HttpMethod.GET,"/posts").permitAll()
                .antMatchers(HttpMethod.GET,"/posts/{id}").permitAll()
                .and()
                .exceptionHandling().authenticationEntryPoint(new AuthEntryPoint())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(new TokenAuthFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void init(WebSecurity web) throws Exception {
        super.init(web);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/authenticate")
                .antMatchers(HttpMethod.GET,"/posts")
                .antMatchers(HttpMethod.GET,"/posts/{id}");
    }
}
