package com.jetbrains.java_interpreter.classes;

import java.util.HashMap;

public class Scope {
    private final HashMap<String, Integer> variables;
    private final Scope parent;

    public Scope() {
        this.variables = new HashMap<>();
        this.parent = null;
    }

    public Scope(Scope parent) {
        this.variables = new HashMap<>();
        this.parent = parent;
    }

    public Integer resolveVariable(String variableName) {
        if (variables.containsKey(variableName)) return variables.get(variableName);

        if (parent == null) return null;
        return parent.resolveVariable(variableName);
    }

    public void setVariableValue(String variableName, Integer variableValue) {
        variables.put(variableName, variableValue);
    }

    public void resetScope() {
        variables.clear();
    }
}
