package com.example.demo.exceptions;

public class FoxFailedToUpdateException extends RuntimeException{
    public FoxFailedToUpdateException(){super("Data update failed");}
}
