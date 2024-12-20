package com.jetbrains.java_interpreter.validator;


import com.jetbrains.java_interpreter.exceptions.base.CompileError;
import com.jetbrains.java_interpreter.exceptions.interpreter.InvalidSyntaxError;
import com.jetbrains.java_interpreter.exceptions.interpreter.UnmatchedBracesError;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.jetbrains.java_interpreter.validator.Validator.validateSyntax;
import static org.assertj.core.api.Assertions.assertThat;

public class ValidatorTest {

    @Test
    void testEmptyInput() {
        List<CompileError> resultList = validateSyntax("");
        assertThat(resultList).containsExactlyElementsOf(List.of(new InvalidSyntaxError(0, "Input cannot be empty")));
    }

    @Test
    void testNullInput() {
        List<CompileError> resultList = validateSyntax(null);
        assertThat(resultList).containsExactlyElementsOf(List.of(new InvalidSyntaxError(0, "Input cannot be empty")));
    }

    @Test
    void testInputWithInvalidPrintStatement() {
        List<CompileError> resultList = validateSyntax("print");
        assertThat(resultList).containsExactlyElementsOf(List.of(new InvalidSyntaxError(1, "print")));
    }

    @Test
    void testInputWithInvalidScope() {
        List<CompileError> resultList = validateSyntax("scope {");
        assertThat(resultList).containsExactlyElementsOf(List.of(new UnmatchedBracesError(1, "End of file")));
    }

    @Test
    void testInputWithMoreClosingBraces() {
        List<CompileError> resultList = validateSyntax("}");
        assertThat(resultList).containsExactlyElementsOf(List.of(new UnmatchedBracesError(1, "}")));
    }

    @Test
    void testInputWithValidAssignment() {
        List<CompileError> resultList = validateSyntax("a = 1");
        assertThat(resultList).isEmpty();
    }

    @Test
    void testInputWithInvalidAssignment() {
        List<CompileError> resultList = validateSyntax("a 1");
        assertThat(resultList).containsExactlyElementsOf(List.of(new InvalidSyntaxError(1, "a 1")));
    }

    @Test
    void testInputWithValidPrintStatement() {
        List<CompileError> resultList = validateSyntax("print 1");
        assertThat(resultList).isEmpty();
    }

    @Test
    void testInputWithValidScope() {
        List<CompileError> resultList = validateSyntax("scope { \n}");
        assertThat(resultList).isEmpty();
    }

    @Test
    void testInputWithValidNestedScope() {
        List<CompileError> resultList = validateSyntax("scope { \nscope { \n} \n}");
        assertThat(resultList).isEmpty();
    }

    @Test
    void testInputWithInvalidNestedScope() {
        List<CompileError> resultList = validateSyntax("scope { \nscope { \n}");
        assertThat(resultList).containsExactlyElementsOf(List.of(new UnmatchedBracesError(3, "End of file")));
    }

    @Test
    void testInputWithValidNestedScopeAndAssignment() {
        List<CompileError> resultList = validateSyntax("scope { \na = 1 \nscope { \nb = 2 \n} \n}");
        assertThat(resultList).isEmpty();
    }

    @Test
    void testInputWithInvalidNestedScopeAndAssignment() {
        List<CompileError> resultList = validateSyntax("scope { \na = 1 \nscope { \nb 2 \n} \n}");
        assertThat(resultList).containsExactlyElementsOf(List.of(new InvalidSyntaxError(4, "b 2")));
    }

    @Test
    void testInputWithValidNestedScopeAndPrintStatement() {
        List<CompileError> resultList = validateSyntax("scope { \nprint 1 \nscope { \nprint 2 \n} \n}");
        assertThat(resultList).isEmpty();
    }

    @Test
    void testInputWithInvalidNestedScopeAndPrintStatement() {
        List<CompileError> resultList = validateSyntax("scope { \nprint 1 \nscope { \nprint 2 \n}");
        assertThat(resultList).containsExactlyElementsOf(List.of(new UnmatchedBracesError(5, "End of file")));
    }

    @Test
    void testInputWithValidNestedScopeAndAssignmentAndPrintStatement() {
        List<CompileError> resultList = validateSyntax("scope { \na = 1 \nprint a \nscope { \nb = 2 \nprint b \n} \n}");
        assertThat(resultList).isEmpty();
    }

    @Test
    void testInputWithInvalidNestedScopeAndAssignmentAndPrintStatement() {
        List<CompileError> resultList = validateSyntax("scope { \na = 1 \nprint a \nscope { \nb 2 \nprint b \n}");
        assertThat(resultList).containsExactlyElementsOf(List.of(new InvalidSyntaxError(5, "b 2"), new UnmatchedBracesError(7, "End of file")));
    }

    @Test
    void testInputWithValidNestedScopeAndAssignmentAndPrintStatementAndMultipleScopes() {
        List<CompileError> resultList = validateSyntax("scope { \na = 1 \nprint a \nscope { \nb = 2 \nprint b \nscope { \nc = 3 \nprint c \n} \n} \n}");
        assertThat(resultList).isEmpty();
    }

    @Test
    void testInputWithInvalidNestedScopeAndAssignmentAndPrintStatementAndMultipleScopes() {
        List<CompileError> resultList = validateSyntax("scope { \na = 1 \nprint a \nscope { \nb 2 \nprint b \nscope { \nc = 3 \nprint c \n} \n}");
        assertThat(resultList).containsExactlyElementsOf(List.of(new InvalidSyntaxError(5, "b 2"), new UnmatchedBracesError(11, "End of file")));
    }

    @Test
    void testEmptyAssignment() {
        List<CompileError> resultList = validateSyntax("a = ");
        assertThat(resultList).containsExactlyElementsOf(List.of(new InvalidSyntaxError(1, "a =")));
    }

    @Test
    void testEmptyPrintStatement() {
        List<CompileError> resultList = validateSyntax("print ");
        assertThat(resultList).containsExactlyElementsOf(List.of(new InvalidSyntaxError(1, "print")));
    }

    @Test
    void testEmptyScope() {
        List<CompileError> resultList = validateSyntax("scope { \n}");
        assertThat(resultList).isEmpty();
    }

    @Test
    void testEmptyAssignment2() {
        List<CompileError> resultList = validateSyntax(" =");
        assertThat(resultList).containsExactlyElementsOf(List.of(new InvalidSyntaxError(1, "=")));
    }

    @Test
    void testEmptyLine() {
        List<CompileError> resultList = validateSyntax(" \n");
        assertThat(resultList).isEmpty();
    }

    @Test
    void testMultipleErrors() {
        List<CompileError> resultList = validateSyntax("scope { \nprint 1 \nscope { \nprint 2 \n");
        assertThat(resultList).containsExactlyElementsOf(List.of(new UnmatchedBracesError(4, "End of file")));
    }

    @Test
    void testMultipleErrors2() {
        List<CompileError> resultList = validateSyntax("scope { \nprint 1 \nscope { \nprint 2 \n} \n} \n}");
        assertThat(resultList).containsExactlyElementsOf(List.of(new UnmatchedBracesError(7, "}")));
    }

    @Test
    void testAllCodeInOneLine() {
        List<CompileError> resultList = validateSyntax("a = 1 print a scope { b = 2 print b }");
        assertThat(resultList).containsExactlyElementsOf(List.of(new InvalidSyntaxError(1, "a = 1 print a scope { b = 2 print b }")));
    }



}
