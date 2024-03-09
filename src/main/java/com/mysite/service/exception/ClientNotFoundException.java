package com.mysite.service.exception;

public class ClientNotFoundException extends BaseException {
    public ClientNotFoundException(){
        super("Client not found exception!");
    }
}
