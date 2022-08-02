package com.gfa.library.validation.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class AuthenticationResponseDto {

  private final String status;
  private String token;

  public AuthenticationResponseDto(String token) {
    this.status = "ok";
    this.token = token;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AuthenticationResponseDto that = (AuthenticationResponseDto) o;
    return Objects.equals(status, that.status) && Objects.equals(token, that.token);
  }

}
