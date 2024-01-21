package com.uber.driver.driver.onboarding.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.*;

@ControllerAdvice
public class DriverExceptionHandler {
    @ExceptionHandler(UsernameDuplicateException.class)
    public ResponseEntity<String> handleSignupException(UsernameDuplicateException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnhandledException(Exception ex) {
        return new ResponseEntity<>(Optional.of(ex.getMessage()).orElse(null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String errorMessage = error.getDefaultMessage();
            errors.add(errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler( {UnableToParseJSON.class, InvalidParamException.class})
    public ResponseEntity<String> handleUnableToParseJSONException(UnableToParseJSON ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler( InvalidEnumValueException.class)
    public ResponseEntity<String> handleInvalidEnumValueException(InvalidEnumValueException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(org.springframework.beans.TypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleTypeMismatchException(org.springframework.beans.TypeMismatchException ex) {
        String fieldName = ex.getPropertyName();
        String errorMessage = "Invalid value for '" + fieldName + "'. Please provide a valid boolean value.";

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

}
