package com.example.refugeeshelter.exceptions;

import com.example.refugeeshelter.dto.ApiResponse;
import com.example.refugeeshelter.dto.ExceptionResponse;
import com.example.refugeeshelter.dto.ValidationErrorResponse;
import com.example.refugeeshelter.dto.Violation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class RestControllerExceptionHandler {

  @ExceptionHandler({ResourceNotFoundException.class})
  @ResponseBody
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ApiResponse> resolveException(ResourceNotFoundException exception) {
    ApiResponse apiResponse = exception.getApiResponse();
    log.error("Error with message = {}", apiResponse.getMessage());
    return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MissedRequestDataException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ApiResponse> resolveException(MissedRequestDataException exception) {
    ApiResponse apiResponse = exception.getApiResponse();
    log.error("Error with message = {}", apiResponse.getMessage());
    return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(LogicException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.CONFLICT)
  public ResponseEntity<ApiResponse> resolveException(LogicException exception) {
    ApiResponse apiResponse = exception.getApiResponse();
    log.error("Error with message = {}", apiResponse.getMessage());
    return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(UserNotFoundException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ResponseEntity<ApiResponse> resolveException(UserNotFoundException exception) {
    ApiResponse apiResponse = exception.getApiResponse();
    log.error("Error with message = {}", apiResponse.getMessage());
    return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
  }


  @ExceptionHandler(RoomsDeleteLogicException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.CONFLICT)
  public ResponseEntity<ApiResponse> resolveException(RoomsDeleteLogicException exception) {
    ApiResponse apiResponse = exception.getApiResponse();
    log.error("Error with message = {}", apiResponse.getMessage());
    return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(ForbiddenException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ResponseEntity<ApiResponse> resolveException(ForbiddenException exception) {
    ApiResponse apiResponse = exception.getApiResponse();
    log.error("Error with message = {}", apiResponse.getMessage());
    return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);
  }


  @ResponseBody
  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ValidationErrorResponse resolveException(ConstraintViolationException ex) {
    final List<Violation> violations =
        ex.getConstraintViolations().stream()
            .map(
                violation ->
                    new Violation(violation.getPropertyPath().toString(), violation.getMessage()))
            .collect(Collectors.toList());

    return new ValidationErrorResponse(violations);
  }

  @ExceptionHandler( HttpMessageNotReadableException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ExceptionResponse> resolveException(HttpMessageNotReadableException ex) {
    String message = "Please provide Request Body in valid JSON format";
    List<String> messages = new ArrayList<>(1);
    messages.add(message);

    return new ResponseEntity<>(new ExceptionResponse(messages, HttpStatus.BAD_REQUEST.getReasonPhrase(),
            HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
  }
}
