package com.mysite.Model.bankAccounts;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@ToString
public class Account implements Serializable {
    private static final AtomicInteger count = new AtomicInteger(1);
    @JsonIgnore
    private final int accountID;
    private final int accountNo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate accountOpenDate;
    private Amount balance;
    private boolean accountClosed;


    public Account(){
        this.accountID = count.getAndIncrement();
        accountNo = accountID + 1000;
        balance = new Amount();
        accountOpenDate = LocalDate.now();
        accountClosed = false;
    }

    public Account(Currency currency, LocalDate date) {
        accountOpenDate = date;
        balance = new Amount(currency, BigDecimal.ZERO);
        accountID = count.getAndIncrement();
        accountNo = accountID + 1000;
        accountClosed = false;
    }

}
