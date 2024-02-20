package com.mysite.service.Validation;

import com.mysite.dto.AccountDto;
import com.mysite.dto.ClientDto;
import com.mysite.dto.RealClientDto;
import com.mysite.facade.impl.ValidationContext;
import com.mysite.service.exception.ValidationException;

public class AccountValidationContext extends ValidationContext<AccountDto> {
    public AccountValidationContext() {

        addValidation(accountDto -> {
            Double balance = accountDto.getBalance();
            if(balance < 0) {
                throw new ValidationException("Balance can not be less than zero");
            }
        });
    }
}