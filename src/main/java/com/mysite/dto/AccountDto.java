package com.mysite.dto;

import com.mysite.Model.bankAccounts.AccountType;
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
    private AccountType type;
    private LocalDate accountOpenDate;
    private double balance;
    private boolean accountClosed;

    public AccountDto() {
        accountOpenDate = LocalDate.now();
    }
}
