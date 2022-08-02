package com.gfa.library.security;

import com.gfa.library.users.models.entities.User;
import com.gfa.library.users.services.UserServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {


  public static String baseUrl;
  private final UserServiceImpl userService;
  private final SecurityService securityService;

  public JwtRequestFilter(UserServiceImpl userService,
                          SecurityService securityService) {
    this.userService = userService;
    this.securityService = securityService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    setBaseUrl(request);
    if (isPublicEndpoint(request.getRequestURI())) {
      filterChain.doFilter(request, response);
      return;
    }
    final String authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
      filterChain.doFilter(request, response);
      return;
    }
    String jwt = authorizationHeader.substring(7);
    User principal = (User) userService.loadUserByUsername(securityService.extractUsername(jwt));
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
        new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    filterChain.doFilter(request, response);
  }

  private void setBaseUrl(HttpServletRequest request) {
    if (baseUrl == null) {
      baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
    }
  }

  private boolean isPublicEndpoint(String endPoint) {
    for (String privateEndPoint : SecurityConfig.privateEndPoints) {
      Pattern pattern = Pattern.compile(privateEndPoint);
      Matcher matcher = pattern.matcher(endPoint);
      if (matcher.find()) {
        return false;
      }
    }
    return true;
  }
}
