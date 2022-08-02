package com.gfa.library.login.services;

import com.gfa.library.exceptions.AuthenticationException;
import com.gfa.library.exceptions.NotFoundException;
import com.gfa.library.login.models.dtos.LoginDto;
import com.gfa.library.security.SecurityService;
import com.gfa.library.users.models.dtos.UserDto;
import com.gfa.library.users.models.entities.User;
import com.gfa.library.users.services.UserService;
import com.gfa.library.validation.dtos.AuthenticationResponseDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

  private final UserService userService;
  private final SecurityService securityService;
  private final PasswordEncoder passwordEncoder;

  public LoginServiceImpl(UserService userService,
                          SecurityService securityService,
                          PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.securityService = securityService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public AuthenticationResponseDto login(LoginDto credential) {
    User user;
    try {
      user = userService.getByUsername(credential.getUsername());
      if (user != null && !checkPassword(credential.getPassword(), user.getPassword())) {
        throw new AuthenticationException("Username or password is incorrect.");
      }
    } catch (NotFoundException e) {
      throw new AuthenticationException("Username or password is incorrect.");
    }

    String jwsToken = securityService.generateToken(new UserDto(user));
    return new AuthenticationResponseDto(jwsToken);
  }

  @Override
  public String hashPassword(String password) {
    return passwordEncoder.encode(password);
  }

  @Override
  public Boolean checkPassword(String passwordString, String passwordHashed) {
    return passwordEncoder.matches(passwordString, passwordHashed);
  }

}
