package com.jetbrains.java_interpreter.classes;

import lombok.*;


@ToString
@EqualsAndHashCode
@Builder
public class CompiledResult {
    private String result;
    private boolean hasError = false;

    public CompiledResult(String result) {
        this.result = result;
    }

    public CompiledResult(String result, boolean hasError) {
        this.result = result;
        this.hasError = hasError;
    }

    public boolean isHasError() {
        return hasError;
    }

    public String getResult() {
        return result;
    }
}
