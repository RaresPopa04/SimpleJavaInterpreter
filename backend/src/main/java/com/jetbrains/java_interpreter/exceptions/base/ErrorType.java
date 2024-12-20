package com.jetbrains.java_interpreter.exceptions.base;

public enum ErrorType {
    INVALID_SYNTAX("Invalid syntax"),
    INVALID_ASSIGNMENT("Invalid variable assignment"),
    INVALID_PRINT("Invalid print statement"),
    UNMATCHED_BRACES("Unmatched braces");

    public final String errorMessage;

    ErrorType(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
