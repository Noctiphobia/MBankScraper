package com.mbankscraper.scraper.mbank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.mbankscraper.scraper.BankPage;
import com.mbankscraper.scraper.BankRequest;
import com.mbankscraper.scraper.LoginCredentials;
import com.mbankscraper.scraper.exceptions.InvalidCredentialsException;
import com.mbankscraper.scraper.utils.RegexUtils;

import java.io.IOException;

public class MBankLoginPage extends BankPage {

  public final static String LOGIN_PAGE_URL = "https://online.mbank.pl/pl/Login";

  private final static String SEED_REGEX = "app\\.initialize\\('(.*?)'"; //seed from app.initialize('seed'

  private final static String INVALID_CREDENTIALS_MESSAGE = "Nieprawidłowy identyfikator lub hasło.";

  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class LoginResponse {
    public boolean successful;
    public String tabId;
    public String errorMessageTitle;
  }

  public MBankLoginPage(WebClient webClient) {
    super(webClient);
    setPage(LOGIN_PAGE_URL);
  }

  public String login(LoginCredentials loginCredentials) {
    String seed = RegexUtils.getFirstMatchOrCrash(SEED_REGEX, page.asXml());

    BankRequest request = MBankRequestFactory.loginRequest(loginCredentials, seed);
    WebResponse response = sendRequest(request.toWebRequest());
    LoginResponse parsedResponse = parseResponse(response.getContentAsString());

    if (!parsedResponse.successful) {
      String error = parsedResponse.errorMessageTitle;
      if (error.equals(INVALID_CREDENTIALS_MESSAGE))
        throw new InvalidCredentialsException(parsedResponse.errorMessageTitle);
      throw new RuntimeException(parsedResponse.errorMessageTitle);
    }

    return parsedResponse.tabId;
  }

  private LoginResponse parseResponse(String responseContent) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      LoginResponse response = mapper.readValue(responseContent, LoginResponse.class);
      return response;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
