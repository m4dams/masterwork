package com.gfa.library.security;

import com.gfa.library.exceptions.AuthExceptionHandler;
import com.gfa.library.users.services.UserServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  public static final String[] privateEndPoints = {
      "/users\\S*",
      "/books\\S*"
  };
  private final UserServiceImpl userService;
  private final JwtRequestFilter jwtRequestFilter;
  private final AuthExceptionHandler authExceptionHandler;

  public SecurityConfig(UserServiceImpl userService, JwtRequestFilter jwtRequestFilter,
                        AuthExceptionHandler authExceptionHandler) {
    this.userService = userService;
    this.jwtRequestFilter = jwtRequestFilter;
    this.authExceptionHandler = authExceptionHandler;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
        .and()
        .authorizeRequests()
        .antMatchers("/register", "/login",
            "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
        .anyRequest().authenticated()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .exceptionHandling().authenticationEntryPoint(authExceptionHandler);
    http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
  }
}