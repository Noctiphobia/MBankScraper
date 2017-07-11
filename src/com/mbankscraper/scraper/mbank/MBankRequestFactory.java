package com.mbankscraper.scraper.mbank;

import com.mbankscraper.scraper.BankRequest;
import com.mbankscraper.scraper.LoginCredentials;

public class MBankRequestFactory {

  private static BankRequest createCommonRequest(String url) {
    BankRequest request = new BankRequest(url);
    setCommonHeaders(request);
    return request;
  }

  private static void setCommonHeaders(BankRequest request) {
    request.headers.put("Accept", "application/json, text/javascript, */*; q=0.01");
    request.headers.put("Accept-Language", "en-US,en;q=0.5");
    request.headers.put("Content-Type", "application/json;charset=utf-8");
    request.headers.put("X-Requested-With", "XMLHttpRequest");
  }


  private final static String LOGIN_REQUEST_URL = "https://online.mbank.pl/pl/LoginMain/Account/JsonLogin";

  public static BankRequest loginRequest(LoginCredentials loginCredentials, String seed) {
    BankRequest request = createCommonRequest(LOGIN_REQUEST_URL);
    setLoginHeaders(request);
    setLoginBody(request, loginCredentials, seed);
    return request;
  }

  private static void setLoginHeaders(BankRequest request) {
    request.headers.put("X-Tab-Id", "not-found");
    request.headers.put("Referer", MBankLoginPage.LOGIN_PAGE_URL);
  }

  private static void setLoginBody(BankRequest request, LoginCredentials loginCredentials, String seed) {
    request.body =
        String.format("{\"UserName\":\"%s\",\"Password\":\"%s\",\"Seed\":\"%s\",\"Scenario\":\"Default\", " +
                "\"UWAdditionalParams\":{\"InOut\":null,\"ReturnAddress\":null,\"Source\":null},\"Lang\":\"\"}",
            loginCredentials.userName, String.valueOf(loginCredentials.password), seed);
  }

  private final static String ACCOUNTS_REQUEST_URL = "https://online.mbank.pl/pl/MyDesktop/Desktop/GetAccountsList";

  public static BankRequest accountsRequest(String verificationToken, String tabId) {
    BankRequest request = createCommonRequest(ACCOUNTS_REQUEST_URL);
    setAccountsRequestHeaders(request, verificationToken, tabId);
    request.body = "{}";
    return request;
  }

  private static void setAccountsRequestHeaders(BankRequest request, String verificationToken, String tabId) {
    request.headers.put("Referer", MBankLoginPage.LOGIN_PAGE_URL);
    request.headers.put("X-Request-Verification-Token", verificationToken);
    request.headers.put("X-Tab-Id", tabId);
  }

  private MBankRequestFactory() {
  }

}
