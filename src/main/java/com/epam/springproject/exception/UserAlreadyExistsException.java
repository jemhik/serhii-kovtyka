package com.epam.springproject.exception;

import com.epam.springproject.model.enums.ErrorType;

public class UserAlreadyExistsException extends ServiceException{

    private static final String DEFAULT_MESSAGE = "User already exists!";

    public UserAlreadyExistsException() {
        super(DEFAULT_MESSAGE);
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.VALIDATION_ERROR_TYPE;
    }
}
