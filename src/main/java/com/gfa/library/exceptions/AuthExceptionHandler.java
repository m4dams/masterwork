package com.gfa.library.exceptions;

import com.gfa.library.security.SecurityService;
import com.gfa.library.validation.dtos.ErrorDto;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthExceptionHandler implements AuthenticationEntryPoint {
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException {
    final String authorizationHeader = request.getHeader("Authorization");
    String errorMessage = authorizationHeader == null
        ? "No authentication token is provided!"
        : "Authentication token is invalid!";
    ErrorDto errorDto = new ErrorDto(errorMessage);
    response.setStatus(401);
    response.setContentType("application/json");

    PrintWriter printWriter = response.getWriter();
    printWriter.println(SecurityService.objToJson(errorDto));
    printWriter.flush();
  }
}
