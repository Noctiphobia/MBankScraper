package com.mbankscraper;

import java.util.List;

public interface BankScraper extends AutoCloseable {
    List<BankAccount> getBankAccounts(LoginDetails loginDetails);

    @Override
    void close();
}
