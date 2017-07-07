package com.mbankscraper.mbank;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlMeta;
import com.mbankscraper.BankAccount;
import com.mbankscraper.BankPage;
import com.mbankscraper.BankRequest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class MBankMainPage extends BankPage {
    public final static String MAIN_PAGE_URL = "https://online.mbank.pl/pl";

    private String tabId;

    public MBankMainPage(WebClient webClient, String tabId) {
        super(webClient);
        setPage(MAIN_PAGE_URL);
        this.tabId = tabId;
    }

    public List<BankAccount> getAccounts() {
        BankRequest request = MBankRequestFactory.accountsRequest(getVerificationToken(), tabId);
        WebResponse response = sendRequest(request.toWebRequest());
        String responseContent = response.getContentAsString();

        List<BankAccount> accounts = parseAccountsResponse(responseContent);
        return accounts;
    }

    private String getVerificationToken() {
        HtmlMeta tokenElement = (HtmlMeta) page.getByXPath("//meta[@name=\"__AjaxRequestVerificationToken\"]").get(0);
        return tokenElement.getContentAttribute();
    }

    private List<BankAccount> parseAccountsResponse(String responseContent) {
        try {
            List<BankAccount> accounts = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readValue(responseContent, JsonNode.class);
            ArrayNode accountsNode = (ArrayNode) rootNode.get("accountDetailsList");
            for (JsonNode accountNode : accountsNode) {
                accounts.add(jsonNodeToBankAccount(accountNode));
            }

            return accounts;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BankAccount jsonNodeToBankAccount(JsonNode node) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.name = node.get("ProductName").asText();
        bankAccount.number = node.get("AccountNumber").asText();
        bankAccount.balance = new BigDecimal(node.get("Balance").asText().replace(',', '.'));
        bankAccount.currency = Currency.getInstance(node.get("Currency").asText());
        return bankAccount;
    }
}
