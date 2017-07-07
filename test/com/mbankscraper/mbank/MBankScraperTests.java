package com.mbankscraper.mbank;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbankscraper.BankAccount;
import com.mbankscraper.BankScraper;
import com.mbankscraper.LoginCredentials;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MBankScraperTests {
    final static String configPath = "test/com/mbankscraper/mbank/MBankScraperTestsConfig.json";

    private static class Config {
        public LoginCredentials loginCredentials;
        public List<BankAccount> expectedAccounts;
    }

    private Config readConfig() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Config config = mapper.readValue(new File(configPath), Config.class);
            return config;
        } catch (IOException e) {
            throw new RuntimeException(String.format("Error reading %s config file.", configPath), e);
        }
    }

    @Test
    public void getsAllAccounts() {
        BankScraper scraper = new MBankScraper();
        Config config = readConfig();
        List<BankAccount> accounts = scraper.getBankAccounts(config.loginCredentials);
        assertAccountListEquals(config.expectedAccounts, accounts);
    }

    private void assertAccountListEquals(List<BankAccount> expected, List<BankAccount> actual) {
        Assert.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < actual.size(); ++i) {
            Assert.assertEquals(expected.get(i).name, actual.get(i).name);
            Assert.assertEquals(expected.get(i).number, actual.get(i).number);
            Assert.assertEquals(expected.get(i).currency, actual.get(i).currency);
        }
    }

    @Test(expected = RuntimeException.class)
    public void crashesWhenInvalidCredentials() {
        BankScraper scraper = new MBankScraper();
        LoginCredentials loginCredentials = new LoginCredentials("FictionalUser", "WrongPassword".toCharArray());
        scraper.getBankAccounts(loginCredentials);
    }
}
