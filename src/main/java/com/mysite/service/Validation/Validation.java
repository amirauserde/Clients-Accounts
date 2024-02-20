package com.mysite.service.Validation;

import com.mysite.service.exception.ValidationException;

@FunctionalInterface
public interface Validation <T> {
    void validate(T t) throws ValidationException;
}
