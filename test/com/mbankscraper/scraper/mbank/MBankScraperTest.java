package com.mbankscraper.scraper.mbank;


import com.mbankscraper.scraper.BankAccount;
import com.mbankscraper.scraper.BankScraper;
import com.mbankscraper.scraper.LoginCredentials;
import com.mbankscraper.scraper.exceptions.InvalidCredentialsException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Currency;
import java.util.List;

public class MBankScraperTest {

  @Test
  public void getsAtLeastMainAccount() {
    BankScraper scraper = new MBankScraper();
    LoginCredentials loginCredentials = new LoginCredentials("login", "password".toCharArray());
    List<BankAccount> accounts = scraper.getBankAccounts(loginCredentials);
    Assert.assertTrue(accounts.size() >= 1);

    BankAccount account = accounts.get(0);
    Assert.assertEquals(Currency.getInstance("PLN"), account.currency);
  }

  @Test(expected = InvalidCredentialsException.class)
  public void crashesWhenInvalidCredentials() {
    BankScraper scraper = new MBankScraper();
    LoginCredentials loginCredentials = new LoginCredentials("FictionalUser", "WrongPassword".toCharArray());
    scraper.getBankAccounts(loginCredentials);
  }

}
