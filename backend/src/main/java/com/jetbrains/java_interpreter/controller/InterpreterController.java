package com.jetbrains.java_interpreter.controller;

import com.jetbrains.java_interpreter.classes.CodeRequest;
import com.jetbrains.java_interpreter.classes.CompiledResult;
import com.jetbrains.java_interpreter.interpreter.InterpreterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class InterpreterController {

    private final InterpreterService interpreterService;

    public InterpreterController(InterpreterService interpreterService) {
        this.interpreterService = interpreterService;
    }


    @PostMapping("/interpret")
    public ResponseEntity<CompiledResult> interpret(@RequestBody CodeRequest codeRequest) {
        CompiledResult compiledResult = interpreterService.interpret(codeRequest.code());
        if(compiledResult.isHasError()) {
            return ResponseEntity.badRequest().body(compiledResult);
        }
        return ResponseEntity.ok(compiledResult);
    }
}
