package com.jetbrains.java_interpreter.exceptions.interpreter;


import com.jetbrains.java_interpreter.exceptions.base.CompileError;
import com.jetbrains.java_interpreter.exceptions.base.ErrorType;

public class InvalidSyntaxError extends CompileError {
    public InvalidSyntaxError(int lineNumber, String lineContent) {
        super(ErrorType.INVALID_SYNTAX, lineNumber, lineContent);
    }
}
