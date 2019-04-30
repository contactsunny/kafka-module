package com.contactsunny.poc.kafkaModule.exceptions;

public class ValidationException extends Throwable {
    public ValidationException(String errorMessage) {
        super(errorMessage);
    }
}
