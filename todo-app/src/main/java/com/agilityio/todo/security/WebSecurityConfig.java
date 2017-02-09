package com.agilityio.todo.security;

import com.agilityio.todo.config.Http401UnauthorizedEntryPoint;
import com.agilityio.todo.security.jwt.JWTAuthenticationFilter;
import com.agilityio.todo.security.jwt.JWTLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Project: toto-app
 * Config authentication for rest api
 */
@Configuration
@EnableGlobalAuthentication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JWTAuthenticationFilter();
    }

    @Bean
    public JWTLoginFilter jwtLoginFilter() throws Exception {
        return new JWTLoginFilter("/login", authenticationManager());
    }

    @Autowired
    public Http401UnauthorizedEntryPoint http401UnauthorizedEntryPoint;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers("/users/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(http401UnauthorizedEntryPoint);
        httpSecurity.headers().cacheControl().disable();
    }

}
