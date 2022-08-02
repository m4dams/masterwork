package com.gfa.library.login.controllers;

import com.gfa.library.login.models.dtos.LoginDto;
import com.gfa.library.login.services.LoginService;
import com.gfa.library.validation.dtos.AuthenticationResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class LoginController {
  private final LoginService loginService;

  public LoginController(LoginService loginService) {
    this.loginService = loginService;
  }

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponseDto> login(@Valid @RequestBody LoginDto credential) {
    AuthenticationResponseDto auth = loginService.login(credential);
    return ResponseEntity.status(200).body(auth);
  }
}
