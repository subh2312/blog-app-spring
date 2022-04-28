package com.subhankar.blogapp.config.security;

import com.subhankar.blogapp.config.security.jwt.AuthEntryPointJwt;
import com.subhankar.blogapp.config.security.jwt.AuthTokenFilter;
import com.subhankar.blogapp.config.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity //allows Spring to find and automatically apply the class to the global Web Security.
@EnableGlobalMethodSecurity( //provides AOP security on methods. It enables @PreAuthorize, @PostAuthorize, it also supports JSR-250.
                            // You can find more parameters in configuration in Method Security Expressions.
        // securedEnabled = true,
        // jsr250Enabled = true,
        prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /*  WebSecurityConfigurerAdapter is the crux of our security implementation.
    It provides HttpSecurity configurations to configure cors, csrf,
    session management, rules for protected resources.
    We can also extend and customize the default configuration that contains the elements below */
    @Autowired
    UserDetailsServiceImpl userDetailsService; //Spring Security will load User details to perform authentication & authorization.
                                                // So it has UserDetailsService interface that we need to implement.
    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }
    /*
    We override the configure(HttpSecurity http) method from
    WebSecurityConfigurerAdapter interface.
    It tells Spring Security how we configure CORS and CSRF,
    when we want to require all users to be authenticated or not,
    which filter (AuthTokenFilter) and when we want it to work
    (filter before UsernamePasswordAuthenticationFilter),
    which Exception Handler is chosen (AuthEntryPointJwt).
     */
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/auth/**","/v2/api-docs", "/configuration/**", "/swagger-resources/**",  "/swagger-ui.html", "/webjars/**", "/api-docs/**").permitAll()
                .anyRequest().authenticated();
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
