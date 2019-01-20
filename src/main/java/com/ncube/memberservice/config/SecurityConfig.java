package com.ncube.memberservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        String username = "user";
        String password = "qwerty";
        String roles = "USER";
        auth.inMemoryAuthentication()
                .passwordEncoder(encoder)
                .withUser(username)
                .password(encoder.encode(password))
                .roles(roles);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/members/**", "/swagger-ui.html", "/v2/api-docs").hasRole("USER")
                .and()
                .csrf().disable()
                .headers()
                .frameOptions().disable();
    }
}
