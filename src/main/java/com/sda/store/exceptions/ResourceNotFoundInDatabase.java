package com.sda.store.exceptions;

public class ResourceNotFoundInDatabase  extends RuntimeException{
    public ResourceNotFoundInDatabase(String message){
        super(message);
    }
}
