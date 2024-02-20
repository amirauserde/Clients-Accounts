package com.mysite.service.exception;

public class FileException extends Exception{
    public FileException() {
        super("There was a problem with the file");
    }
}