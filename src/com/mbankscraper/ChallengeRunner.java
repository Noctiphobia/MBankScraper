package com.mbankscraper;

import java.util.List;

public class ChallengeRunner implements AutoCloseable {
    BankScraper scraper;

    public ChallengeRunner(BankScraper scraper){
        this.scraper = scraper;
    }

    public void run(LoginDetails loginDetails){
        List<BankAccount> bankAccounts = scraper.getBankAccounts(loginDetails);
        printAccounts(bankAccounts);
    }

    public void runWithPrompt(){
        try (LoginDetails loginDetails = LoginDetails.createAndPrompt()) {
            run(loginDetails);
        }
    }

    private void printAccounts(List<BankAccount> accounts){
        for (BankAccount bankAccount : accounts)
            System.out.println(bankAccount);
    }

    @Override
    public void close() {
        scraper.close();
    }
}
