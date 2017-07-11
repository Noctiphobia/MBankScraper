package com.mbankscraper.scraper;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;

public class BankPage {

  protected WebClient webClient;
  protected HtmlPage page;

  public BankPage(WebClient webClient) {
    this.webClient = webClient;
  }

  protected void setPage(String url) {
    try {
      page = webClient.getPage(url);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  protected WebResponse sendRequest(WebRequest request) {
    try {
      return webClient.getPage(request).getWebResponse();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
