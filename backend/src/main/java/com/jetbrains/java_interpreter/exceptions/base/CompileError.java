package com.jetbrains.java_interpreter.exceptions.base;

import lombok.Getter;

import java.util.Objects;

@Getter
public abstract class CompileError {

    private final int lineNumber;
    private final String errorContext;
    private final ErrorType errorType;

    public CompileError(ErrorType errorType, int lineNumber, String errorContext) {
        this.lineNumber = lineNumber;
        this.errorContext = errorContext;
        this.errorType = errorType;
    }

    public String getErrorMessage(){
        return errorType.errorMessage + " at line " + lineNumber + ": " + errorContext;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompileError that)) return false;
        return lineNumber == that.lineNumber && Objects.equals(errorContext, that.errorContext) && errorType == that.errorType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineNumber, errorContext, errorType);
    }

    @Override
    public String toString() {
        return "CompileError{" +
                "lineNumber=" + lineNumber +
                ", errorContext='" + errorContext + '\'' +
                ", errorType=" + errorType +
                '}';
    }
}
