package com.mbankscraper.scraper;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;

public class BankAccount {

  public String name;
  public String number;
  public BigDecimal balance;
  public Currency currency;

  @Override
  public String toString() {
    NumberFormat format = NumberFormat.getCurrencyInstance();
    format.setCurrency(currency);
    return String.format("%s (%s): %s", name, number, format.format(balance)).trim(); //trim in case name is empty
  }

}
