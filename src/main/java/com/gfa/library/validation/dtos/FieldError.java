package com.gfa.library.validation.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FieldError {

  private String field;
  private List<String> messages;

  public FieldError() {
    messages = new ArrayList<>();
  }

  public void addError(String err) {
    messages.add(err);
  }
}
