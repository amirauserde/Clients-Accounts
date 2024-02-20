package com.mysite.view;
import com.mysite.dto.AccountDto;
import com.mysite.facade.impl.AccountFacadeImpl;
import com.mysite.service.exception.AccountNotFoundException;
import com.mysite.service.exception.ValidationException;

import java.util.List;
import java.util.function.Function;

public class AccountConsole extends BaseConsole {
    AccountFacadeImpl accountFacade;

    AccountConsole() {
        accountFacade = AccountFacadeImpl.getInstance();
    }

    void saveOnExit() {
        accountFacade.saveOnExit();
    }

    public void initData() {
        accountFacade.initData();
    }

    void accountMenu() {
        System.out.println("Account Menu panel");
        boolean menuRun = true;
        while (menuRun) {
            System.out.println("Please choose from the options:");
            String choice = scannerWrapper.getUserInput("""
                    1. Find (- edit) account
                    2. Print accounts
                    3. Deposit
                    4. Withdraw
                    5. Transfer
                    6. Close account
                    7. Save Data
                    8. Load Data
                    9. Add Data
                    10. Back""", Function.identity());

            switch (choice) {
                case "1" -> findMenu();
                case "2" -> printAccounts();
                case "3" -> deposit();
                case "4" -> withdraw();
                case "5" -> transfer();
                case "6" -> closeAccount();
                case "7" -> saveData();
                case "8" -> loadData();
                case "9" -> addData();
                case "10" -> menuRun = false;
                default -> System.out.println("Wrong input!");
            }
        }
    }

    private void transfer() {

        int fromAccountNumber = (scannerWrapper.getUserInput
                ("Enter the withdrawal account: ", Integer::parseInt));
        int toAccountNumber = (scannerWrapper.getUserInput
                ("Enter the account to deposit to: ", Integer::parseInt));
        String fromAccType = null;
        try {
            fromAccType = accountFacade.accountType(fromAccountNumber);
        } catch (AccountNotFoundException e) {
            throw new RuntimeException(e);
        }
        double amount = (scannerWrapper.getUserInput
                ("Enter the amount in %s: ".formatted(fromAccType), Double::parseDouble));
        try {
            accountFacade.transfer(fromAccountNumber, toAccountNumber, amount);
        } catch (AccountNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    private void deposit() {
        double amount = 0.0;
        String type = " ";
        try {
            int accountNumber = (scannerWrapper.getUserInput
                    ("Enter the account number: ", Integer::parseInt));
            type = accountFacade.accountType(accountNumber);
            amount = (scannerWrapper.getUserInput
                    ("Enter the amount in %s: ".formatted(type),
                            Double::parseDouble));
            accountFacade.deposit(accountNumber, amount);
        } catch (ValidationException | AccountNotFoundException e) {
            System.out.println(e.getMessage());
            deposit();
        }
        System.out.println(amount + type + " was deposited into account.");
    }

    private void withdraw() {
        double amount = 0.0;
        String type = " ";
        try {
            int accountNumber = (scannerWrapper.getUserInput
                    ("Enter the account number: ", Integer::parseInt));
            type = accountFacade.accountType(accountNumber);
            amount = (scannerWrapper.getUserInput
                    ("Enter the amount in %s: ".formatted(type),
                            Double::parseDouble));
            accountFacade.withdraw(accountNumber, amount);
        } catch (ValidationException | AccountNotFoundException e) {
            System.out.println(e.getMessage());
            withdraw();
        }
        System.out.println("Please receive " + amount + type + " from the account.");
    }

    private void printAccounts() {
        accountFacade.getActiveAccounts().forEach(System.out::println);
    }

    private void addData() {
        try {
            String name = scannerWrapper.getUserInput("Enter the Json file name: ", Function.identity());
            accountFacade.addData(name);
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
            addData();
        }

    }

    private void loadData() {
        try {
            int fileTypeChoice = (scannerWrapper.getUserInput
                    ("File type:%n1. Serialized%n2. Jason".formatted(), Integer::parseInt));
            String name = scannerWrapper.getUserInput("Enter file name to load: ", Function.identity());
            accountFacade.loadData(fileTypeChoice, name);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            loadData();
        }
    }

    private void saveData() {
        int fileTypeChoice = (scannerWrapper.getUserInput
                ("File type:%n1. Serialized%n2. Jason".formatted(), Integer::parseInt));
        String name = scannerWrapper.getUserInput("Enter file name: ", Function.identity());
        try {
            accountFacade.saveData(fileTypeChoice, name);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            saveData();
        }
    }

    private void findMenu() {
        String search = scannerWrapper.getUserInput("Enter your search", Function.identity());
        List<AccountDto> searchedAccounts =  accountFacade.findAccount(search);
        if(searchedAccounts.isEmpty()) {
            System.out.println("No results found!");
        } else {
            searchedAccounts.forEach(System.out::println);
        }
    }

    void addAccount(int clientID) {

        int accountTypeChoice = (scannerWrapper.getUserInput
                ("Account type:%n1. Euro%n2. Dollar".formatted(), Integer::parseInt));
        try {
            accountFacade.addAccount(accountTypeChoice, clientID);
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
            addAccount(clientID);
        }
    }

    private void closeAccount() {
        int accountNumber = scannerWrapper.
                getUserInput("Please Enter account number: "
                        , Integer::parseInt);
        boolean result = false;
        try {
            result = accountFacade.closeAccount(accountNumber);
        } catch (AccountNotFoundException e) {
            System.out.println(e.getMessage());
            closeAccount();
        }
        System.out.println(result? "Account is already closed!" : "Account was closed!");
    }

}
