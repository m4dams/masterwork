package com.gfa.library.login.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

  @NotNull(message = "Username is required.")
  private String username;

  @NotNull(message = "Password is required.")
  private String password;
}
