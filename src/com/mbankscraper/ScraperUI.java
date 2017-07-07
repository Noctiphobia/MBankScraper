package com.mbankscraper;

import java.util.List;

public class ScraperUI implements AutoCloseable {
    private final static String USERNAME_PROMPT = "User name: ";
    private final static String PASSWORD_PROMPT = "Password: ";

    private BankScraper scraper;

    public ScraperUI(BankScraper scraper) {
        this.scraper = scraper;
    }

    public void run(LoginCredentials loginCredentials) {
        List<BankAccount> bankAccounts = scraper.getBankAccounts(loginCredentials);
        printAccounts(bankAccounts);
    }

    public void runWithPrompt() {
        try (LoginCredentials loginCredentials = promptLoginDetails()) {
            run(loginCredentials);
        }
    }

    private LoginCredentials promptLoginDetails() {
        String userName = System.console().readLine(USERNAME_PROMPT);
        char[] password = System.console().readPassword(PASSWORD_PROMPT);
        return new LoginCredentials(userName, password);
    }

    private void printAccounts(List<BankAccount> accounts) {
        for (BankAccount bankAccount : accounts)
            System.out.println(bankAccount);
    }

    @Override
    public void close() {
        scraper.close();
    }
}
