package com.gfa.library.exceptions;

public class InternalServerException extends RuntimeException {
  public InternalServerException(String message) {
    super(message);
  }
}
