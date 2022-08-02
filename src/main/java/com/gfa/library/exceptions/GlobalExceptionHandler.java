package com.gfa.library.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.gfa.library.validation.dtos.ErrorDto;
import com.gfa.library.validation.dtos.FieldError;
import com.gfa.library.validation.dtos.FieldErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

  public static FieldErrorDto setOneCustomErrorDto(String field, String message) {
    List<FieldError> fieldErrors = new ArrayList<>();
    FieldError fieldError = new FieldError();
    fieldError.setField(field);
    fieldError.addError(message);
    fieldErrors.add(fieldError);
    return new FieldErrorDto(fieldErrors);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<FieldErrorDto> handleValidationExceptions(MethodArgumentNotValidException e) {
    List<FieldError> fieldErrors = e.getBindingResult().getAllErrors().stream()
        .map(err -> {
          FieldError fieldError = new FieldError();
          fieldError.setField(err.getCodes()[1].split("\\.")[1]);
          fieldError.addError(err.getDefaultMessage());
          return fieldError;
        })
        .collect(Collectors.toList());

    FieldErrorDto errorDto = rebuildErrorList(fieldErrors);
    return ResponseEntity.status(400).body(errorDto);
  }
  @ExceptionHandler({DateTimeParseException.class, InvalidFormatException.class})
  public ResponseEntity<ErrorDto> handleInvalidFormatExceptions(InvalidFormatException e){
    return ResponseEntity.status(400).body(new ErrorDto(e.getOriginalMessage()));
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorDto> handleEmbeddedObjectConstraintExceptions(RuntimeException e) {
    return ResponseEntity.status(400).body(new ErrorDto(e.getMessage()));
  }

  @ExceptionHandler(AuthorizationException.class)
  public ResponseEntity<ErrorDto> handleAuthorizationExceptions(RuntimeException e) {
    return ResponseEntity.status(403).body(new ErrorDto(e.getMessage()));
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorDto> handleNotFoundExceptions(RuntimeException e) {
    return ResponseEntity.status(404).body(new ErrorDto(e.getMessage()));
  }

  @ExceptionHandler(ConflictException.class)
  public ResponseEntity<ErrorDto> handleConflictExceptions(RuntimeException e) {
    return ResponseEntity.status(409).body(new ErrorDto(e.getMessage()));
  }

  @ExceptionHandler(InternalServerException.class)
  public ResponseEntity<ErrorDto> handleInternalServerExceptions(InternalServerException e) {
    return ResponseEntity.status(500).body(new ErrorDto(e.getMessage()));
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ErrorDto> handleAuthenticationExceptions(AuthenticationException e) {
    return ResponseEntity.status(401).body(new ErrorDto(e.getMessage()));
  }

  private FieldErrorDto rebuildErrorList(List<FieldError> fieldErrors) {
    List<FieldError> fieldErrorsRefactor = new ArrayList<>();
    Set<String> fields = new HashSet<>();
    for (int i = 0; i < fieldErrors.size(); i++) {
      FieldError currentError = fieldErrors.get(i);
      if (fields.contains(currentError.getField())) {
        fieldErrors.stream()
            .filter(fieldError -> fieldError.getField().equals(currentError.getField()))
            .forEach(fieldError -> fieldError.addError(currentError.getMessages().get(0)));

      } else {
        fields.add(currentError.getField());
        fieldErrorsRefactor.add(currentError);
      }
    }

    return new FieldErrorDto(fieldErrorsRefactor);
  }
}
