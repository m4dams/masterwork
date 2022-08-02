package com.gfa.library.utils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordServiceImpl implements PasswordService {
  private final PasswordEncoder passwordEncoder;

  public PasswordServiceImpl(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
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
