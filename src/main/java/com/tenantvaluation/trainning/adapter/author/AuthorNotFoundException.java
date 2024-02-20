package com.tenantvaluation.trainning.adapter.author;

import jakarta.validation.ValidationException;

public class AuthorNotFoundException extends ValidationException {

    public AuthorNotFoundException(String message) {
        super(message);
    }
}
