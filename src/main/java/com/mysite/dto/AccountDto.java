package com.mysite.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private int accountID;
    private int accountNo;
    @NonNull
    private LocalDate accountOpenDate;
    private AmountDto balance;
    private boolean accountClosed;

    public AccountDto() {
        accountOpenDate = LocalDate.now();
        balance = new AmountDto();
    }

    public AccountDto(LocalDate date, AmountDto balance) {
        accountOpenDate = date;
        this.balance = balance;
    }
}
