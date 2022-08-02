package com.gfa.library.exceptions;

public class NotFoundException extends RuntimeException {
  public NotFoundException(String message) {
    super(message +" not found");
  }
}