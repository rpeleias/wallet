package com.recargapay.wallet.utils;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

import java.util.Set;

public abstract class SelfValidating<T> {

    private Validator validator;

    public SelfValidating(Validator validator) {
        this.validator = validator;
    }

    protected void validateSelf() {
        Set<ConstraintViolation<T>> violations = validator.validate((T) this);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
