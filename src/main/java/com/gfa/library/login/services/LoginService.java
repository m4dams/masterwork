package com.gfa.library.login.services;

import com.gfa.library.login.models.dtos.LoginDto;
import com.gfa.library.validation.dtos.AuthenticationResponseDto;

public interface LoginService {

  AuthenticationResponseDto login(LoginDto credential);

  String hashPassword(String password);

  Boolean checkPassword(String passwordString, String passwordHashed);
}
