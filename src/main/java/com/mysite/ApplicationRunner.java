package com.mysite;

import com.mysite.view.MainConsole;

public class ApplicationRunner {
    public static void main(String[] args) {

        try (MainConsole newConsole = new MainConsole()) {
            newConsole.run();
        } catch (Throwable ex) {
            ex.printStackTrace();
            System.out.println("System error! Sorry we have to close the App!");
        }
    }
}