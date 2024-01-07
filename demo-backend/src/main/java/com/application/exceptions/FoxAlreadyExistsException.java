package com.application.exceptions;

public class FoxAlreadyExistsException extends RuntimeException{
    public FoxAlreadyExistsException(){super("Fox already exists");}
}
