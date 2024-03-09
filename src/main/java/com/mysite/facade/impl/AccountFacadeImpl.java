package com.mysite.facade.impl;

import com.mysite.Model.Client;
import com.mysite.Model.bankAccounts.Account;
import com.mysite.dto.AccountDto;
import com.mysite.dto.AmountDto;
import com.mysite.mapper.AccountMapstruct;
import com.mysite.service.AccountManagement;
import com.mysite.service.ClientManagement;
import com.mysite.service.Validation.AccountValidationContext;
import com.mysite.service.exception.AccountNotFoundException;
import com.mysite.service.exception.ClientNotFoundException;
import com.mysite.service.exception.FileException;
import com.mysite.service.exception.ValidationException;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
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

    AccountFacadeImpl(ClientManagement clientManagement, AccountManagement accountManagement) {
        accountMapstruct = Mappers.getMapper(AccountMapstruct.class);
        this.accountManagement = accountManagement;
        this.clientManagement = clientManagement;
        validationContext = new AccountValidationContext();
    }


    public AccountDto getAccountDtoById(int id) throws AccountNotFoundException {
        return accountMapstruct.mapToAccountDto(accountManagement.getAccountById(id));
    }

    public void addAccount(Currency currency, int clientId)
            throws ValidationException, AccountNotFoundException, ClientNotFoundException {

        if(checkAccounts(clientId, currency)) {
            AccountDto newAccountDto = new AccountDto(LocalDate.now(), new AmountDto(currency, BigDecimal.ZERO));
            validationContext.validate(newAccountDto);
            Account account = accountMapstruct.mapToAccount(newAccountDto);

            accountManagement.addAccount(account);
            clientManagement.addAccount(clientId, account.getAccountNo());
        } else {
            throw new ValidationException("Client already has this type of account");
        }
    }

    public boolean checkAccounts(int clientId, Currency currency)
            throws AccountNotFoundException, ClientNotFoundException {
        Client client = clientManagement.getClientById(clientId);
        if(client.getAccountNos().isEmpty()) return true;
        Account account = accountManagement.getAccountByAccNo((client.getAccountNos().get(0)));
        return(!account.getBalance().getCurrency().equals(currency));
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

    public void deposit(int accountNumber, BigDecimal amount, Currency currency)
            throws ValidationException, AccountNotFoundException {
        AmountDto amountDto =
                new AmountDto(currency, amount);
        if(amountDto.getValue().compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("amount must be more than zero!");
        }
        accountManagement.deposit(accountNumber, accountMapstruct.mapToAmount(amountDto));
    }

    public void withdraw(int accountNumber, BigDecimal amount, Currency currency)
            throws ValidationException, AccountNotFoundException {
        AmountDto amountDto = new AmountDto(currency, amount);
        accountManagement.withdraw(accountNumber, accountMapstruct.mapToAmount(amountDto));
    }

    public void transfer(int fromAccountNumber, int toAccountNumber, BigDecimal amount)
            throws AccountNotFoundException, ValidationException {
        AmountDto amountDto =
                new AmountDto(accountManagement.
                        getAccountByAccNo(fromAccountNumber).getBalance().getCurrency(), amount);
        accountManagement.transfer(fromAccountNumber, toAccountNumber, accountMapstruct.mapToAmount(amountDto));
    }
}
