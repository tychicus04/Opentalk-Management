package com.tychicus.opentalk.exception;

public class EmployeeAlreadyExistsException extends RuntimeException {
    public EmployeeAlreadyExistsException(String messgage) {
        super(messgage);
    }
}
