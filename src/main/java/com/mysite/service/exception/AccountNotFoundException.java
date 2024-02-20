package com.mysite.service.exception;

public class AccountNotFoundException extends BaseException {
    public AccountNotFoundException(){
        super("Account not found exception!");
    }
}
