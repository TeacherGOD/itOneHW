package ru.itone.course_java.core.base_exceptions.exceptions;

public class PersonValidationException extends RuntimeException {
    public PersonValidationException(String message) {
        super(message);
    }
}
