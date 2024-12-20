package com.jetbrains.java_interpreter.interpreter;

import com.jetbrains.java_interpreter.classes.CompiledResult;
import com.jetbrains.java_interpreter.classes.Scope;
import com.jetbrains.java_interpreter.exceptions.base.CompileError;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import static com.jetbrains.java_interpreter.validator.Validator.validateSyntax;


@Service
public class InterpreterService {
    private final Scope globalScope;
    private final Stack<Scope> openScopes;

    public InterpreterService() {
        this.globalScope = new Scope();
        this.openScopes = new Stack<>();
    }


    private void cleanScope() {
        while (!openScopes.isEmpty()) {
            openScopes.pop();
        }
        globalScope.resetScope();
    }

    private String parseProgram(String program) {
        String[] lines = program.split("\\n");
        openScopes.push(globalScope);

        StringBuilder compilerResult = new StringBuilder();

        for (String line : lines) {
            line = line.trim();

            if (line.isEmpty()) continue;
            if (line.startsWith("print")) {
                String variableName = line.split(" ", 2)[1];
                Integer resolvedVariable = openScopes.peek().resolveVariable(variableName);
                compilerResult.append(resolvedVariable).append("\n");
                continue;
            }

            if (line.startsWith("scope {")) {
                openScopes.push(new Scope(openScopes.peek()));
                continue;
            }

            if (line.startsWith("}")) {
                if (openScopes.size() == 1) {
                    throw new RuntimeException("Cannot close global scope");
                }
                openScopes.pop();
                continue;
            }

            if (line.contains("=")) {
                String[] parts = line.split(" ");
                String destVariable = parts[0];
                String sourceVariable = parts[2];

                if (Character.isDigit(sourceVariable.charAt(0))) {
                    Integer numberValue = Integer.valueOf(sourceVariable);
                    openScopes.peek().setVariableValue(destVariable, numberValue);
                    continue;
                }

                Integer sourceVariableValue = openScopes.peek().resolveVariable(sourceVariable);
                openScopes.peek().setVariableValue(destVariable, sourceVariableValue);
            }


        }
        return compilerResult.toString();
    }

    public CompiledResult interpret(String program) {
        List<CompileError> compileErrorList = validateSyntax(program);
        if (!compileErrorList.isEmpty()) {
            String compileErrorListToString = compileErrorList.stream()
                    .map(CompileError::getErrorMessage)
                    .collect(Collectors.joining("\n"));
            return new CompiledResult(compileErrorListToString, true);
        }
        String result = parseProgram(program);
        cleanScope();
        return new CompiledResult(result);
    }


}