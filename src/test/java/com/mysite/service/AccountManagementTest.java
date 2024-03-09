package com.mysite.service;

import com.mysite.Model.bankAccounts.Account;
import com.mysite.Model.bankAccounts.Amount;
import com.mysite.service.exception.AccountNotFoundException;
import com.mysite.service.exception.ValidationException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static junit.framework.TestCase.assertEquals;

public class AccountManagementTest {
    private AccountManagement accountManagement;

    @Before
    public void setup() {
        accountManagement = AccountManagement.getInstance();
    }

    @Test
    public void depositCaseA() throws AccountNotFoundException {
        Account account = new Account();
        account.setBalance(new Amount(Currency.getInstance("EUR"), BigDecimal.ZERO));
        accountManagement.addAccount(account);
        int accountNo = account.getAccountNo();
        accountManagement.
                deposit(accountNo, new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(10.0)));
        Account accountById = accountManagement.getAccountByAccNo(accountNo);
        assertEquals(BigDecimal.valueOf(10.00).setScale(2), accountById.getBalance().getValue());
    }

    @Test
    public void depositCaseB() throws AccountNotFoundException {
        Account account = new Account();
        account.setBalance(new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(20.0)));
        accountManagement.addAccount(account);
        int accountNo = account.getAccountNo();
        accountManagement.
                deposit(accountNo, new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(10.0)));
        Account accountById = accountManagement.getAccountByAccNo(accountNo);
        assertEquals(BigDecimal.valueOf(30.00).setScale(2), accountById.getBalance().getValue());
    }

    @Test
    public void depositCaseC() throws AccountNotFoundException {
        Account account = new Account();
        account.setBalance(new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(20.1)));
        accountManagement.addAccount(account);
        int accountNo = account.getAccountNo();
        accountManagement.
                deposit(accountNo, new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(10.2)));
        Account accountById = accountManagement.getAccountByAccNo(accountNo);
        assertEquals(BigDecimal.valueOf(30.30).setScale(2), accountById.getBalance().getValue());
    }

    @Test
    public void depositCaseD() throws AccountNotFoundException {
        Account account = new Account();
        account.setBalance(new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(50.1)));
        accountManagement.addAccount(account);
        int accountNo = account.getAccountNo();
        accountManagement.
                deposit(accountNo, new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(30.2)));
        Account accountById = accountManagement.getAccountByAccNo(accountNo);
        assertEquals(BigDecimal.valueOf(80.30).setScale(2), accountById.getBalance().getValue());
    }

    @Test
    public void depositCaseE() throws AccountNotFoundException {
        Account account = new Account();
        account.setBalance(new Amount(Currency.getInstance("EUR"), BigDecimal.ZERO));
        accountManagement.addAccount(account);
        int accountNo = account.getAccountNo();
        for(int i = 0; i < 10; i++) {
            accountManagement.
                    deposit(accountNo, new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(10.0)));
        }
        Account accountById = accountManagement.getAccountByAccNo(accountNo);
        assertEquals(BigDecimal.valueOf(100.00).setScale(2), accountById.getBalance().getValue());
    }

    @Test
    public void depositCaseF() throws AccountNotFoundException {
        Account account = new Account();
        account.setBalance(new Amount(Currency.getInstance("EUR"), BigDecimal.ZERO));
        accountManagement.addAccount(account);
        int accountNo = account.getAccountNo();

        Runnable depositTask = () -> {
            for(int i = 0; i < 100; i++) {
                try {
                    accountManagement.
                            deposit(accountNo,
                                    new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(10.0)));
                } catch (AccountNotFoundException ignored) {

                }
            }
        };
        depositTask.run();

        Account accountById = accountManagement.getAccountByAccNo(accountNo);
        assertEquals(BigDecimal.valueOf(1000.00).setScale(2), accountById.getBalance().getValue());
    }

    @Test
    public void depositCaseG() throws AccountNotFoundException, InterruptedException {
        Account account = new Account();
        account.setBalance(new Amount(Currency.getInstance("EUR"), BigDecimal.ZERO));
        accountManagement.addAccount(account);
        int accountNo = account.getAccountNo();

        Runnable depositTask = () -> {
            for(int i = 0; i < 100; i++) {
                try {
                    accountManagement.
                            deposit(accountNo,
                                    new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(10.0)));
                } catch (AccountNotFoundException ignored) {

                }
            }
        };
        Thread[] threads = new Thread[1];
        for(int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(depositTask);
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        Account accountById = accountManagement.getAccountByAccNo(accountNo);
        assertEquals(BigDecimal.valueOf(1000.00).setScale(2), accountById.getBalance().getValue());
    }

    @Test
    public void depositCaseH() throws AccountNotFoundException, InterruptedException {
        Account account = new Account();
        account.setBalance(new Amount(Currency.getInstance("EUR"), BigDecimal.ZERO));
        accountManagement.addAccount(account);
        int accountNo = account.getAccountNo();

        Runnable depositTask = () -> {
            for(int i = 0; i < 100; i++) {
                try {
                    accountManagement.
                            deposit(accountNo,
                                    new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(10.0)));
                } catch (AccountNotFoundException ignored) {

                }
            }
        };
        Thread[] threads = new Thread[10];
        for(int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(depositTask);
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        Account accountById = accountManagement.getAccountByAccNo(accountNo);
        assertEquals(BigDecimal.valueOf(10000.00).setScale(2), accountById.getBalance().getValue());
    }

    @Test
    public void withdrawCaseA() throws AccountNotFoundException, ValidationException {
        Account account = new Account();
        account.setBalance(new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(20.3)));
        accountManagement.addAccount(account);
        int accountNo = account.getAccountNo();
        accountManagement.
                withdraw(accountNo, new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(10.1)));
        Account accountById = accountManagement.getAccountByAccNo(accountNo);
        assertEquals(BigDecimal.valueOf(10.20).setScale(2), accountById.getBalance().getValue());
    }

    @Test
    public void withdrawCaseB() throws AccountNotFoundException, InterruptedException {
        Account account = new Account();
        account.setBalance(new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(20000.0)));
        accountManagement.addAccount(account);
        int accountNo = account.getAccountNo();

        Runnable depositTask = () -> {
            for(int i = 0; i < 100; i++) {
                try {
                    accountManagement.
                            withdraw(accountNo,
                                    new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(10.0)));
                } catch (AccountNotFoundException | ValidationException ignored) {

                }
            }
        };
        Thread[] threads = new Thread[10];
        for(int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(depositTask);
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        Account accountById = accountManagement.getAccountByAccNo(accountNo);
        assertEquals(BigDecimal.valueOf(10000.00).setScale(2), accountById.getBalance().getValue());
    }

    @Test
    public void transferCaseX() throws AccountNotFoundException, ValidationException {
        Account accountA = new Account();
        accountA.setBalance(new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(10.1)));
        accountManagement.addAccount(accountA);
        int accountANo = accountA.getAccountNo();

        Account accountB = new Account();
        accountB.setBalance(new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(20.2)));
        accountManagement.addAccount(accountB);
        int accountBNo = accountB.getAccountNo();

        accountManagement.
                transfer(accountANo, accountBNo,
                        new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(10.1)));
        Account accountById = accountManagement.getAccountByAccNo(accountBNo);
        assertEquals(BigDecimal.valueOf(30.30).setScale(2), accountById.getBalance().getValue());
    }
}
