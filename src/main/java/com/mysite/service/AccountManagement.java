package com.mysite.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.mysite.Model.bankAccounts.Account;
import com.mysite.service.exception.AccountNotFoundException;
import com.mysite.service.exception.FileException;
import com.mysite.service.exception.ValidationException;
import com.mysite.util.MapperWrapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AccountManagement {

    private ArrayList<Account> accounts;
    private static AccountManagement INSTANCE;

    private final String fileName = "accountData";

    public static AccountManagement getInstance() {
        if(INSTANCE == null) {
            synchronized (AccountManagement.class) {
                if(INSTANCE == null){
                    INSTANCE = new AccountManagement();
                }
            }
        }
        return INSTANCE;
    }

    private final ObjectMapper objectMapper;

    private AccountManagement() {
        this.accounts = new ArrayList<>();
        objectMapper = MapperWrapper.getInstance();
    }

    public int addAccount(Account account) {
        accounts.add(account);
        return account.getAccountID();
    }

    public boolean closeAccount(int accountNo) throws AccountNotFoundException {
        Account account = getAccountByAccNo(accountNo);
        if(account.isAccountClosed()) return false;
        account.setAccountClosed(true);
        return true;
    }

    public Account getAccountByAccNo(int accountNo) throws AccountNotFoundException {
        return accounts.stream()
                .filter(ac -> ac.getAccountNo() == accountNo)
                .findFirst()
                .orElseThrow(AccountNotFoundException::new);
    }



    public Account getAccountById(int id) throws AccountNotFoundException {
        return accounts.stream()
                .filter(ac -> ac.getAccountID() == id)
                .findFirst()
                .orElseThrow(AccountNotFoundException::new);
    }

    public List<Account> getClosedAccounts() {
        return accounts.stream()
                .filter(Account::isAccountClosed)
                .collect(Collectors.toList());
    }

    public List<Account> getActiveAccounts() {
        return accounts.stream()
                .filter(account -> !account.isAccountClosed())
                .collect(Collectors.toList());
    }

    public List<Account> findAccount(String searchItem) {
        List<Account> results;
        results = accounts.stream().filter(account -> String.valueOf(account.getAccountNo()).equals(searchItem) ||
                        String.valueOf(account.getType()).equalsIgnoreCase(searchItem))
                .collect(Collectors.toList());
        return results;
    }

    public void saveData(int type, String name) throws FileException {
        switch (type) {
            case 1 -> saveSerialized(name);
            default -> saveJason(name);
        }
    }

    private void saveSerialized(String name) throws FileException {
        try {
            File file = new File(name + ".acn");
            if (!file.exists()) {
                file.createNewFile();
            }
            try (FileOutputStream fileOutputStream = new FileOutputStream(file);
                 ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
                objectOutputStream.writeObject(accounts);
            }
        } catch (IOException e) {
            throw new FileException();
        }
    }

    private void saveJason(String name) throws FileException {
        try {
            File file = new File(name + ".jsn");
            if (!file.exists()) {
                file.createNewFile();
            }
            objectMapper.writeValue(file, accounts);

        } catch (IOException e) {
            throw new FileException();
        }
    }

    public void loadData(int type, String name) throws FileException {
        switch (type) {
            case 1 -> loadSerialized(name);
            default -> loadJason(name);
        }
    }

    private void loadSerialized(String name) throws FileException {
        try {
            FileInputStream fileInputStream = new FileInputStream(name + ".acn");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            accounts = (ArrayList<Account>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new FileException();
        }
    }

    private void loadJason(String name) throws FileException {
        try {
            accounts = (ArrayList<Account>) objectMapper.readValue(new File(name + ".jsn"),
                    new TypeReference<ArrayList<Account>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileException();
        }
    }

    public void initData() {
        try {
            loadJason(fileName);
        } catch (FileException ignored) {

        }
    }

    public void saveOnExit() {
        try {
            saveJason(fileName);
        } catch (FileException ignored) {

        }
    }

    public void addData(String name) throws FileException {
        try {
            ArrayList<Account> newClients = (ArrayList<Account>) objectMapper.readValue(new File(name + ".jsn"),
                    new TypeReference<ArrayList<Account>>() {});
            accounts.addAll(newClients);
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileException();
        }
    }

    public void deposit(int accountNumber, Double amount) throws AccountNotFoundException{
        Account account = getAccountByAccNo(accountNumber);
        account.setBalance(account.getBalance() + amount);
    }

    public void withdraw(int accountNumber, double amount) throws AccountNotFoundException, ValidationException {
        Account account = getAccountByAccNo(accountNumber);
        if(amount > account.getBalance()) throw new ValidationException("Account amount can not be less than zero");
        account.setBalance(account.getBalance() - amount);
    }

    public String accountType(int accountNumber) throws AccountNotFoundException {
        return getAccountByAccNo(accountNumber).getType().toString();
    }


    public void transfer(int fromAccountNumber, int toAccountNumber, double amount) throws AccountNotFoundException {
        if (getAccountByAccNo(fromAccountNumber).getType() != getAccountByAccNo(toAccountNumber).getType()) {

        }
    }
}
