package com.application.controls.exceptionhandle;

import com.application.exceptions.FoxAlreadyExistsException;
import com.application.exceptions.FoxFailedToUpdateException;
import com.application.exceptions.FoxIllegalArgumentException;
import com.application.exceptions.FoxNotFoundException;
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
    @ExceptionHandler(value = {FoxAlreadyExistsException.class})
    protected ResponseEntity<Object> handleAlreadyExists(RuntimeException ex, WebRequest request){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    @ExceptionHandler(value = {FoxIllegalArgumentException.class})
    protected ResponseEntity<String> handleNullPointerException(RuntimeException ex, WebRequest request){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    @ExceptionHandler(value = {FoxFailedToUpdateException.class})
    protected ResponseEntity<String> handleFailureException(RuntimeException ex, WebRequest request){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
