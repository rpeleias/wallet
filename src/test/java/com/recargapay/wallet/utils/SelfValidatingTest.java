package com.recargapay.wallet.utils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class SelfValidatingTest {

    @Mock
    private Validator mockValidator;

    private TestClass testClass;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testClass = new TestClass(mockValidator);
    }

    @Test
    void whenNotExistsViolationsThenNotThrowException() {
        when(mockValidator.validate(testClass)).thenReturn(Collections.emptySet());

        testClass.validateSelf();

        assertThat("No exception should be thrown", true, is(true));
    }

    @Test
    void whenExistViolationsThenViolationExceptionShouldBeThrown() {
        ConstraintViolation<TestClass> mockViolation = createMockViolation();
        when(mockValidator.validate(testClass)).thenReturn(Set.of(mockViolation));

        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, testClass::validateSelf);
        assertThat(exception.getConstraintViolations(), hasSize(1));
        assertThat(exception.getConstraintViolations(), contains(mockViolation));
    }

    // Método auxiliar para criar uma violação simulada
    @SuppressWarnings("unchecked")
    private ConstraintViolation<TestClass> createMockViolation() {
        return (ConstraintViolation<TestClass>) org.mockito.Mockito.mock(ConstraintViolation.class);
    }

    // Classe de teste que estende SelfValidating
    private static class TestClass extends SelfValidating<TestClass> {
        public TestClass(Validator validator) {
            super(validator);
        }
    }
}