package com.application.test;

import com.application.controls.exceptionhandle.MyRestEntityExceptionHandler;
import com.application.exceptions.FoxNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.lang.annotation.Repeatable;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class TestExceptionHandler {
    @Mock
    private WebRequest webRequest;
    @InjectMocks
    private MyRestEntityExceptionHandler exceptionHandler;
    @Test
    public void testNotFound() throws Exception{
        RuntimeException ex = new FoxNotFoundException();
        var handleNotFound = Arrays.stream(MyRestEntityExceptionHandler.class.getDeclaredMethods())
                .toList().stream().filter(x->x.getName().equals("handleNotFound")).findFirst().orElse(null);
        assert handleNotFound != null;
        handleNotFound.setAccessible(true);
        ResponseEntity<Object> responseEntity = (ResponseEntity<Object>) handleNotFound.invoke(exceptionHandler, ex, webRequest);
        assertEquals(ResponseEntity.notFound().build(), responseEntity);
    }
    @Test
    public void testAlreadyExists() throws Exception{
        RuntimeException ex = new RuntimeException("Already exists");
        var handleAlreadyExists = Arrays.stream(MyRestEntityExceptionHandler.class.getDeclaredMethods())
                .toList().stream().filter(x->x.getName().equals("handleAlreadyExists")).findFirst().orElse(null);
        assert handleAlreadyExists != null;
        handleAlreadyExists.setAccessible(true);
        ResponseEntity<Object> responseEntity = (ResponseEntity<Object>) handleAlreadyExists.invoke(exceptionHandler, ex, webRequest);
        assertEquals(ResponseEntity.badRequest().body(ex.getMessage()), responseEntity);
    }
    @Test
    public void testIllegalArgumentException() throws Exception{
        RuntimeException ex = new RuntimeException("Illegal argument");
        var handleIllegalArgumentException = Arrays.stream(MyRestEntityExceptionHandler.class.getDeclaredMethods())
                .toList().stream().filter(x->x.getName().equals("handleNullPointerException")).findFirst().orElse(null);
        assert handleIllegalArgumentException != null;
        handleIllegalArgumentException.setAccessible(true);
        ResponseEntity<Object> responseEntity = (ResponseEntity<Object>) handleIllegalArgumentException.invoke(exceptionHandler, ex, webRequest);
        assertEquals(ResponseEntity.badRequest().body(ex.getMessage()), responseEntity);
    }
    @Test
    public void testFailedToUpdate() throws Exception{
        RuntimeException ex = new RuntimeException("Failed to update");
        var handleFailedToUpdate = Arrays.stream(MyRestEntityExceptionHandler.class.getDeclaredMethods())
                .toList().stream().filter(x->x.getName().equals("handleFailureException")).findFirst().orElse(null);
        assert handleFailedToUpdate != null;
        handleFailedToUpdate.setAccessible(true);
        ResponseEntity<Object> responseEntity = (ResponseEntity<Object>) handleFailedToUpdate.invoke(exceptionHandler, ex, webRequest);
        assertEquals(ResponseEntity.badRequest().body(ex.getMessage()), responseEntity);
    }

}
