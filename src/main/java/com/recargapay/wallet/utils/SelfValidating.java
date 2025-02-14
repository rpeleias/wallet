package com.recargapay.wallet.utils;

import javax.xml.validation.Validator;

public abstract class SelfValidating {

    private Validator validator;

    public SelfValidating(Validator validator) {
        this.validator = validator;
    }
}
