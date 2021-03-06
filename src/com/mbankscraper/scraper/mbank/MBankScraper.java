package com.mbankscraper.scraper.mbank;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;
import com.mbankscraper.scraper.BankAccount;
import com.mbankscraper.scraper.BankScraper;
import com.mbankscraper.scraper.LoginCredentials;

import java.util.List;


public class MBankScraper implements BankScraper {

  private final WebClient webClient;

  private final boolean debug = false;

  public MBankScraper() {
    webClient = new WebClient(BrowserVersion.CHROME);
    webClient.getOptions().setJavaScriptEnabled(false);
    if (debug) {
      webClient.getOptions().setUseInsecureSSL(true);
      ProxyConfig proxyConfig = new ProxyConfig("127.0.0.1", 8080);
      webClient.getOptions().setProxyConfig(proxyConfig);
    }
  }

  @Override
  public List<BankAccount> getBankAccounts(LoginCredentials loginCredentials) {
    MBankLoginPage loginPage = new MBankLoginPage(webClient);
    String tabId = loginPage.login(loginCredentials);
    MBankMainPage mainPage = new MBankMainPage(webClient, tabId);
    return mainPage.getAccounts();
  }

  @Override
  public void close() {
    webClient.close();
  }

}
