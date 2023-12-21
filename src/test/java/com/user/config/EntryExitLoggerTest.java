package com.user.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class EntryExitLoggerTest {

    private EntryExitLogger entryExitLogger;

    @Mock
    private ProceedingJoinPoint joinPoint;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        entryExitLogger = new EntryExitLogger();
    }

    @Test
    void testLogAround() throws Throwable {
        //set
        when(joinPoint.getSignature()).thenReturn(mock(MethodSignature.class));
        when(joinPoint.getSignature().getName()).thenReturn("testMethod");
        when(joinPoint.getArgs()).thenReturn(new Object[]{"arg1", "arg2"});

        //act
        entryExitLogger.logAround(joinPoint);

        //assert
        verify(joinPoint, times(1)).proceed();
    }
}
