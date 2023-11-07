package com.example.demo.controls;

import com.example.demo.exceptions.FoxIllegalArgumentException;
import com.example.demo.exceptions.FoxNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MyRestEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {FoxNotFoundException.class})
    protected ResponseEntity<Object> handleIllegalArgument(RuntimeException ex, WebRequest request){
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(value = {FoxIllegalArgumentException.class})
    protected ResponseEntity<String> handleNullPointerException(RuntimeException ex, WebRequest request){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
