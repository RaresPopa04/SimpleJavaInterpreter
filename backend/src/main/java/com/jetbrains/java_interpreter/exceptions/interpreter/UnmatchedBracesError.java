package com.jetbrains.java_interpreter.exceptions.interpreter;


import com.jetbrains.java_interpreter.exceptions.base.CompileError;
import com.jetbrains.java_interpreter.exceptions.base.ErrorType;

public class UnmatchedBracesError extends CompileError {
    public UnmatchedBracesError(int lineNumber, String lineContent) {

        super(ErrorType.UNMATCHED_BRACES, lineNumber, lineContent);
    }
}
