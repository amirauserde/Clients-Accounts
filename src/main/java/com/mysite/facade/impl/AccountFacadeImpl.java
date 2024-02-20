package com.mysite.facade.impl;

import com.mysite.Model.bankAccounts.Account;
import com.mysite.Model.bankAccounts.AccountType;
import com.mysite.dto.AccountDto;
import com.mysite.mapper.AccountMapstruct;
import com.mysite.service.AccountManagement;
import com.mysite.service.ClientManagement;
import com.mysite.service.Validation.AccountValidationContext;
import com.mysite.service.exception.AccountNotFoundException;
import com.mysite.service.exception.FileException;
import com.mysite.service.exception.ValidationException;
import org.mapstruct.factory.Mappers;

import java.util.List;

public class AccountFacadeImpl {
    private final AccountManagement accountManagement;
    private final ClientManagement clientManagement;
    private static AccountFacadeImpl INSTANCE;
    private ValidationContext<AccountDto> validationContext;
    private final AccountMapstruct accountMapstruct;


    public static AccountFacadeImpl getInstance() {
        if(INSTANCE == null) {
            synchronized (AccountFacadeImpl.class) {
                if(INSTANCE == null){
                    INSTANCE = new AccountFacadeImpl();
                }
            }
        }
        return INSTANCE;
    }

    private AccountFacadeImpl() {
        accountMapstruct = Mappers.getMapper(AccountMapstruct.class);
        this.accountManagement = AccountManagement.getInstance();
        this.clientManagement = ClientManagement.getInstance();
        validationContext = new AccountValidationContext();
    }

    public AccountDto getAccountDtoById(int id) throws AccountNotFoundException {
        return accountMapstruct.mapToAccountDto(accountManagement.getAccountById(id));
    }

    public void addAccount(int accountType, int clientId) throws ValidationException {
        accountType = (accountType == 1)? 1 : 2;
        if(clientManagement.checkAccounts(clientId, accountType)) {
            AccountDto newAccountDto = new AccountDto(AccountType.fromValue(accountType));
            validationContext.validate(newAccountDto);
            Account account = accountMapstruct.mapToAccount(newAccountDto);
            clientManagement.addAccount(clientId, account);
            accountManagement.addAccount(account);
        } else {
            throw new ValidationException("Client already has this type of account");
        }
    }

    public boolean closeAccount(int accountNo) throws AccountNotFoundException {
        return accountManagement.closeAccount(accountNo);
    }

    public List<AccountDto> getClosedAccounts() {
        return accountMapstruct.mapToAccountDtoList(accountManagement.getClosedAccounts());
    }

    public List<AccountDto> getActiveAccounts() {
        return accountMapstruct.mapToAccountDtoList(accountManagement.getActiveAccounts());
    }

    public List<AccountDto> findAccount(String searchItem) {
        return accountMapstruct.mapToAccountDtoList(accountManagement.findAccount(searchItem));
    }

    public String accountType(int accountNumber) throws AccountNotFoundException {
        return accountManagement.accountType(accountNumber);
    }


    public void saveData(int fileType, String name) throws FileException {
        accountManagement.saveData(fileType, name);
    }

    public void loadData(int fileType, String name) throws FileException {
        accountManagement.loadData(fileType, name);
    }

    public void initData() {
        accountManagement.initData();
    }

    public void saveOnExit() {
        accountManagement.saveOnExit();
    }

    public void addData(String name) throws FileException {
        accountManagement.addData(name);
    }

    public void deposit(int accountNumber, Double amount) throws ValidationException, AccountNotFoundException {
        if(amount < 0) throw new ValidationException("amount must be more than zero!");
        accountManagement.deposit(accountNumber, amount);
    }

    public void withdraw(int accountNumber, double amount) throws ValidationException, AccountNotFoundException {
        accountManagement.withdraw(accountNumber, amount);
    }

    public void transfer(int fromAccountNumber, int toAccountNumber, double amount) throws AccountNotFoundException {
        accountManagement.transfer(fromAccountNumber, toAccountNumber, amount);
    }
}
