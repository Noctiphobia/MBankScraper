package com.mbankscraper.mbank;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;
import com.mbankscraper.BankAccount;
import com.mbankscraper.BankScraper;
import com.mbankscraper.LoginDetails;

import java.util.List;


public class MBankScraper implements BankScraper {
    private final WebClient webClient;

    private final boolean debug = false;

    public MBankScraper(){
        webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(false);
        if (debug){
            webClient.getOptions().setUseInsecureSSL(true);
            ProxyConfig proxyConfig = new ProxyConfig("127.0.0.1", 8080);
            webClient.getOptions().setProxyConfig(proxyConfig);
        }
    }


    @Override
    public List<BankAccount> getBankAccounts(LoginDetails loginDetails){
        MBankLoginPage loginPage = new MBankLoginPage(webClient);
        String tabId = loginPage.login(loginDetails);
        MBankMainPage mainPage = new MBankMainPage(webClient, tabId);
        return mainPage.getAccounts();
    }


    @Override
    public void close() {
        webClient.close();
    }
}
