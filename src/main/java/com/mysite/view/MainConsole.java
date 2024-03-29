package com.mysite.view;

import java.util.function.Function;

public class MainConsole extends BaseConsole implements AutoCloseable {

    private final ClientConsole clientMenu;
    private final AccountConsole accountMenu;
    private final PersonalConsole personalMenu;

    public MainConsole() {
        super();
        clientMenu = new ClientConsole();
        accountMenu = new AccountConsole();
        personalMenu = new PersonalConsole();
    }

    private void saveOnExit() {
        clientMenu.saveOnExit();
        accountMenu.saveOnExit();
    }

    public void run() {

        clientMenu.initData();
        accountMenu.initData();

        Runtime.getRuntime().addShutdownHook(new Thread(this::saveOnExit));

        System.out.println("Main Menu panel");

        boolean menuRun = true;

        while (menuRun) {
            System.out.println("Please choose from the options:");
            String choice = scannerWrapper.getUserInput("""
                    1. Client Menu
                    2. Accounts Menu
                    3. Personal Menu
                    4. exit""", Function.identity());

            switch (choice) {
                case "1" -> clientMenu.clientMenu();
                case "2" -> accountMenu.accountMenu();
                case "3" -> personalMenu.entryMenu();
                case "4" -> menuRun = false;
                default -> System.out.println("Wrong input!");
            }
        }
    }

    @Override
    public void close() {
        scannerWrapper.close();
    }
}
