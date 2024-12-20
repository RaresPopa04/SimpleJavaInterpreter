package com.jetbrains.java_interpreter.validator;



import com.jetbrains.java_interpreter.exceptions.base.CompileError;
import com.jetbrains.java_interpreter.exceptions.interpreter.InvalidAssignmentError;
import com.jetbrains.java_interpreter.exceptions.interpreter.InvalidSyntaxError;
import com.jetbrains.java_interpreter.exceptions.interpreter.UnmatchedBracesError;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Validator {

    public static List<CompileError> validateSyntax(String input) {
        List<CompileError> compileErrorList = new ArrayList<>();
        if (input == null || input.isEmpty()) {
            compileErrorList.add(new InvalidSyntaxError(0, "Input cannot be empty"));
            return compileErrorList;
        }
        parse(input, compileErrorList);
        return compileErrorList;

    }

    private static void parse(String program, List<CompileError> compileErrorList) {
        String[] lines = program.split("\\n");
        int scopeCount = 0;
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) continue;
            if (line.startsWith("print")) {
                Optional<CompileError> error = validatePrintStatement(line, i + 1);
                error.ifPresent(compileErrorList::add);
                continue;
            }
            if (line.startsWith("scope {")) {
                Optional<CompileError> error = validateScope(line, i + 1);
                error.ifPresent(compileErrorList::add);
                scopeCount++;
                continue;
            }
            if (line.startsWith("}")) {
                Optional<CompileError> error = validateEndScope(line, i + 1);
                error.ifPresent(compileErrorList::add);
                scopeCount--;


                if (scopeCount < 0) {
                    compileErrorList.add(new UnmatchedBracesError(i + 1, line));
                    return;
                }
                continue;
            }
            if (line.contains("=")) {
                Optional<CompileError> error = validateAssignment(line, i + 1);
                error.ifPresent(compileErrorList::add);
                continue;
            }

            compileErrorList.add(new InvalidSyntaxError(i + 1, line));
        }
        if (scopeCount != 0) compileErrorList.add(new UnmatchedBracesError(lines.length, "End of file"));
    }

    private static Optional<CompileError> validatePrintStatement(String line, int lineNumber) {
        if (!line.startsWith("print ")) return Optional.of(new InvalidSyntaxError(lineNumber, line));
        String[] parts = line.split(" ");
        if (parts.length != 2) return Optional.of(new InvalidSyntaxError(lineNumber, line));
        if (parts[1].contains(" ")) return Optional.of(new InvalidSyntaxError(lineNumber, line));
        return Optional.empty();

    }

    private static Optional<CompileError> validateScope(String line, int lineNumber) {
        if (!line.equals("scope {")) return Optional.of(new InvalidSyntaxError(lineNumber, line));
        return Optional.empty();
    }

    private static Optional<CompileError> validateEndScope(String line, int lineNumber) {
        if (!line.equals("}")) return Optional.of(new InvalidSyntaxError(lineNumber, line));
        return Optional.empty();
    }

    private static Optional<CompileError> validateAssignment(String line, int lineNumber) {
        String[] parts = line.split(" ");
        if (parts.length != 3) return Optional.of(new InvalidSyntaxError(lineNumber, line));
        if (!parts[1].equals("=")) return Optional.of(new InvalidAssignmentError(lineNumber, line));
        if (parts[0].contains(" ")) return Optional.of(new InvalidAssignmentError(lineNumber, line));
        if (parts[2].contains(" ")) return Optional.of(new InvalidAssignmentError(lineNumber, line));
        return Optional.empty();
    }
}
