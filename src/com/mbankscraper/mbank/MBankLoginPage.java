package com.mbankscraper.mbank;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.mbankscraper.BankPage;
import com.mbankscraper.LoginDetails;
import com.mbankscraper.utils.RegexUtils;

public class MBankLoginPage extends BankPage {
    public final static String LOGIN_PAGE_URL = "https://online.mbank.pl/pl/Login";

    private final static String SEED_REGEX = "app\\.initialize\\('(.*?)'"; //seed from app.initialize('seed'
    private final static String SUCCESS_REGEX = "\"successful\":(true|false)"; //value from "successful":value
    private final static String ERROR_MESSAGE_REGEX = "\"errorMessageTitle\":\"(.*?)\""; //message from "errorMessageTitle":"message"
    private final static String TAB_ID_REGEX = "\"tabId\":\"(.*?)\""; //id from "tabId":"id"

    public MBankLoginPage(WebClient webClient) {
        super(webClient);
        setPage(LOGIN_PAGE_URL);
    }

    public String login(LoginDetails loginDetails) {
        String seed = RegexUtils.getFirstMatchOrCrash(SEED_REGEX, page.asXml());
        WebResponse response = sendRequest(new MBankLoginRequest(loginDetails, seed));
        String responseContent = response.getContentAsString();

        boolean success = Boolean.parseBoolean(RegexUtils.getFirstMatchOrCrash(SUCCESS_REGEX, responseContent));
        if (!success) {
            String errorMessage = RegexUtils.getFirstMatchOrCrash(ERROR_MESSAGE_REGEX, responseContent);
            throw new RuntimeException(errorMessage);
        }

        String tabId = RegexUtils.getFirstMatchOrCrash(TAB_ID_REGEX, responseContent);
        return tabId;
    }
}
