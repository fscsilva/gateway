package com.tenantvaluation.trainning.gateway.adapter.movie;

import jakarta.validation.ValidationException;

public class MovieNotFoundException extends ValidationException {

    public MovieNotFoundException(String message) {
        super(message);
    }
}
