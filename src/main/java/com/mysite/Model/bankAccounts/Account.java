package com.mysite.Model.bankAccounts;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mysite.service.exception.ValidationException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@ToString
public class Account implements Serializable {
    private static final AtomicInteger count = new AtomicInteger(1);
    @JsonIgnore
    private final int accountID;
    private final int accountNo;
    private AccountType type;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate accountOpenDate;
    private double balance;
    private boolean accountClosed;


    public Account(){
        this.accountID = count.getAndIncrement();
        accountNo = accountID + 1000;
        balance = 0;
        accountOpenDate = LocalDate.now();
        accountClosed = false;
    }

    public Account(AccountType type, LocalDate date) {
        this.type = type;
        accountOpenDate = date;
        balance = 0.0;
        accountID = count.getAndIncrement();
        accountNo = accountID + 1000;
        accountClosed = false;
    }

}
