package com.mysite.service.Validation;

import com.mysite.dto.AccountDto;
import com.mysite.facade.impl.ValidationContext;
import com.mysite.service.exception.ValidationException;

import java.math.BigDecimal;

public class AccountValidationContext extends ValidationContext<AccountDto> {
    public AccountValidationContext() {

        addValidation(accountDto -> {
            BigDecimal balance = accountDto.getBalance().getValue();
            if(balance.compareTo(BigDecimal.ZERO) < 0) {
                throw new ValidationException("Balance can not be less than zero");
            }
        });
    }
}