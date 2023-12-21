package com.user.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OperationErrorExceptionTest {

    @Test
    void testOperationErrorExceptionConstructor() {
        //set
        String errorMessage = "error message";

        //act
        OperationErrorException operationErrorException = new OperationErrorException(errorMessage);

        //assert
        assertEquals(errorMessage, operationErrorException.getMessage());
    }
}
