package com.user.exception;

import com.user.model.ErrorResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler exceptionHandler;

    @Test
    void test_handleUserNotFoundException_returns_expected_values(){
        //set
        UserNotFoundException userNotFoundException = Instancio.create(UserNotFoundException.class);

        //act
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleUserNotFoundException(userNotFoundException);

        //assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void test_handleOperationErrorException_returns_expected_values(){
        //set
        OperationErrorException operationErrorException = Instancio.create(OperationErrorException.class);

        //act
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleOperationErrorException(operationErrorException);

        //assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void test_handleMethodArgumentTypeMismatchException_returns_expected_values(){
        //set
        MethodArgumentTypeMismatchException methodArgumentTypeMismatchException = Instancio.create(MethodArgumentTypeMismatchException.class);

        //act
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleMethodArgumentTypeMismatchException(methodArgumentTypeMismatchException);

        //assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

}
