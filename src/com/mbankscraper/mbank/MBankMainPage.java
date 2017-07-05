package com.mbankscraper.mbank;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlMeta;
import com.mbankscraper.BankAccount;
import com.mbankscraper.BankPage;
import com.mbankscraper.utils.RegexUtils;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class MBankMainPage extends BankPage {
    public final static String MAIN_PAGE_URL = "https://online.mbank.pl/pl";

    private final static String NAME_REGEX = "\"ProductName\":\"(.*?)\""; //product from "ProductName":"product"
    private final static String NUMBER_REGEX = "\"AccountNumber\":\"(.*?)\""; //number from "AccountNumber":"number"
    private final static String BALANCE_REGEX = "\"Balance\":\"(.*?)\""; //balance from "Balance":"balance";
    private final static String CURRENCY_REGEX = "\"Currency\":\"(.*?)\""; //currency from "Currency":"currency"

    private String tabId;

    public MBankMainPage(WebClient webClient, String tabId)
    {
        super(webClient);
        setPage(MAIN_PAGE_URL);
        this.tabId = tabId;
    }

    public List<BankAccount> getAccounts(){
        WebResponse response = sendRequest(new MBankAccountsRequest(getVerificationToken(), tabId));
        String responseContent = response.getContentAsString();

        List<String> names = RegexUtils.getAllMatches(NAME_REGEX, responseContent);
        List<String> numbers = RegexUtils.getAllMatches(NUMBER_REGEX, responseContent);
        List<String> balances = RegexUtils.getAllMatches(BALANCE_REGEX, responseContent);
        List<String> currencies = RegexUtils.getAllMatches(CURRENCY_REGEX, responseContent);

        List<BankAccount> accounts = IntStream.range(0, names.size()).mapToObj(i -> new BankAccount(names.get(i),
                numbers.get(i), new BigDecimal(balances.get(i).replace(',', '.')),
                Currency.getInstance(currencies.get(i)))).collect(toList());
        return accounts;
    }

    private String getVerificationToken(){
        HtmlMeta tokenElement = (HtmlMeta) page.getByXPath("//meta[@name=\"__AjaxRequestVerificationToken\"]").get(0);
        return tokenElement.getContentAttribute();
    }

}
