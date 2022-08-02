package com.gfa.library.validation.dtos;

import lombok.Data;

@Data
public class ErrorDto {

  private String status;
  private String message;

  public ErrorDto(String message) {
    this.status = "error";
    this.message = message;
  }

}
