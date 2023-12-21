package com.user.exception;

import com.user.model.ErrorResponse;
import com.user.model.ErrorResponseErrorsInner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException exception) {
        ErrorResponse errorResponse = getErrorResponse(exception, HttpStatus.NOT_FOUND.toString(), exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler({OperationErrorException.class})
    public ResponseEntity<ErrorResponse> handleOperationErrorException(OperationErrorException exception) {
        ErrorResponse errorResponse = getErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR.toString(), exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        ErrorResponse errorResponse = getErrorResponse(exception, HttpStatus.BAD_REQUEST.toString(), "Invalid EmpID");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    private ErrorResponse getErrorResponse(Exception exception, String code, String description) {
        ErrorResponseErrorsInner errorResponseErrorsInner = ErrorResponseErrorsInner.builder()
                .code(code)
                .description(description)
                .severity("ERROR")
                .build();
        return ErrorResponse.builder()
                .errors(List.of(errorResponseErrorsInner))
                .build();
    }
}
