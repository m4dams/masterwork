package com.gfa.library.validation.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FieldErrorDto {

  private final String status;
  private List<FieldError> errors;

  public FieldErrorDto(List<FieldError> fieldErrors) {
    this.status = "error";
    errors = fieldErrors;
  }
}
