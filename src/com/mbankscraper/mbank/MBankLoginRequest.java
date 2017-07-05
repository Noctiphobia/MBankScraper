package com.mbankscraper.mbank;

import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.mbankscraper.LoginDetails;

import java.net.MalformedURLException;
import java.net.URL;

public class MBankLoginRequest extends WebRequest {
    private final static String LOGIN_REQUEST_URL = "https://online.mbank.pl/pl/LoginMain/Account/JsonLogin";

    public MBankLoginRequest(LoginDetails loginDetails, String seed) {
        super(getLoginUrl(), HttpMethod.POST);
        setHeaders();
        setBody(loginDetails, seed);
    }

    private static URL getLoginUrl(){
        try {
            return new URL(LOGIN_REQUEST_URL);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setHeaders(){
        setAdditionalHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        setAdditionalHeader("Accept-Language", "en-US,en;q=0.5");
        setAdditionalHeader("X-Tab-Id", "not-found");
        setAdditionalHeader("Content-Type", "application/json;charset=utf-8");
        setAdditionalHeader("X-Requested-With", "XMLHttpRequest");
        setAdditionalHeader("Referer", MBankLoginPage.LOGIN_PAGE_URL);
    }

    private void setBody(LoginDetails loginDetails, String seed){
        setRequestBody(
                String.format("{\"UserName\":\"%s\",\"Password\":\"%s\",\"Seed\":\"%s\",\"Scenario\":\"Default\", " +
                "\"UWAdditionalParams\":{\"InOut\":null,\"ReturnAddress\":null,\"Source\":null},\"Lang\":\"\"}",
                loginDetails.userName, String.valueOf(loginDetails.password), seed));
    }
}
