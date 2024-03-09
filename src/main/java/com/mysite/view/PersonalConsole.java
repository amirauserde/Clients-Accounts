package com.mysite.view;

import com.mysite.facade.impl.ClientFacadeImpl;
import com.mysite.service.exception.AccountNotFoundException;
import com.mysite.service.exception.ClientNotFoundException;

import java.util.function.Function;

public class PersonalConsole extends BaseConsole {
    final ClientConsole clientConsole;
    final AccountConsole accountConsole;
    final ClientFacadeImpl clientFacade;

    PersonalConsole() {
        clientFacade = ClientFacadeImpl.getInstance();
        clientConsole = new ClientConsole();
        accountConsole = new AccountConsole();
    }


    void entryMenu() {
        System.out.println("Personal Menu panel");
        System.out.println("Welcome");
        login();
    }

    private void login() {
        int clientId = (scannerWrapper.getUserInput
                ("Please Enter your ID: ", Integer::parseInt));
        String password = (scannerWrapper.getUserInput
                ("Please Enter your Password: ", Function.identity()));
        boolean validate = clientFacade.login(clientId, password);
        if(validate) {
            try {
                System.out.println(clientFacade.printClientDetails(clientId));
                System.out.println();
                personalMenu(clientId);
            } catch (AccountNotFoundException | ClientNotFoundException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Id or Password is not correct!");
            login();
        }
    }


    void personalMenu(int clientId) throws ClientNotFoundException, AccountNotFoundException {
        boolean menuRun = true;
        while (menuRun) {
            System.out.println("Please choose from the options:");
            String choice = scannerWrapper.getUserInput("""
                    1. Add a contact
                    2. Add an account
                    3. Deposit
                    4. Withdraw
                    5. Transfer
                    6. Show profile
                    7. Back""", Function.identity());

            switch (choice) {
                case "1" -> clientConsole.addContact(clientId);
                case "2" -> clientConsole.addClientAccount(clientId);
                case "3" -> accountConsole.deposit();
                case "4" -> accountConsole.withdraw();
                case "5" -> accountConsole.transfer();
                case "6" -> System.out.println(clientFacade.printClientDetails(clientId));
                case "7" -> menuRun = false;
                default -> System.out.println("Wrong input!");
            }
        }
    }



}
