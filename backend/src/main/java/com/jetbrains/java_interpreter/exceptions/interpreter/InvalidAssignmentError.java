package com.jetbrains.java_interpreter.exceptions.interpreter;


import com.jetbrains.java_interpreter.exceptions.base.CompileError;
import com.jetbrains.java_interpreter.exceptions.base.ErrorType;

public class InvalidAssignmentError extends CompileError {
    public InvalidAssignmentError(int lineNumber, String lineContent) {
        super(ErrorType.INVALID_ASSIGNMENT, lineNumber, lineContent);
    }
}
