package com.mbankscraper.mbank;

import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebRequest;

import java.net.MalformedURLException;
import java.net.URL;

public class MBankAccountsRequest extends WebRequest {
    private final static String ACCOUNTS_REQUEST_URL = "https://online.mbank.pl/pl/MyDesktop/Desktop/GetAccountsList";

    public MBankAccountsRequest(String verificationToken, String tabId){
        super(getAccountListUrl(), HttpMethod.POST);
        setHeaders(verificationToken, tabId);
        setRequestBody("{}");
    }

    private static URL getAccountListUrl(){
        try {
            return new URL(ACCOUNTS_REQUEST_URL);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setHeaders(String verificationToken, String tabId){
        setAdditionalHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        setAdditionalHeader("Accept-Language", "en-US,en;q=0.5");
        setAdditionalHeader("X-Tab-Id", tabId);
        setAdditionalHeader("Content-Type", "application/json");
        setAdditionalHeader("X-Request-Verification-Token", verificationToken);
        setAdditionalHeader("X-Requested-With", "XMLHttpRequest");
        setAdditionalHeader("Referer", MBankMainPage.MAIN_PAGE_URL);
    }
}
