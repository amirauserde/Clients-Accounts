package com.mysite.service.exception;

public class BaseException extends Exception{
    public BaseException(String message){
        super(message);
    }

    public BaseException(){
        super();
    }
}
