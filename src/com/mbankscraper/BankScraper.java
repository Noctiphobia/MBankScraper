package com.mbankscraper;

import java.util.List;

public interface BankScraper extends AutoCloseable {
    List<BankAccount> getBankAccounts(LoginCredentials loginCredentials);

    @Override
    void close();
}
