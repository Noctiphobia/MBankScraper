package com.mbankscraper.scraper;

import com.gargoylesoftware.htmlunit.WebRequest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class BankRequest {

  public URL url;
  public HttpMethod method = HttpMethod.POST;

  public Map<String, String> headers = new HashMap<>();
  public String body = "";

  public BankRequest(String url) {
    try {
      this.url = new URL(url);
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

  public WebRequest toWebRequest() {
    WebRequest webRequest = new WebRequest(url, getHtmlUnitHttpMethod());
    webRequest.setRequestBody(body);
    webRequest.setAdditionalHeaders(headers);

    return webRequest;
  }

  private com.gargoylesoftware.htmlunit.HttpMethod getHtmlUnitHttpMethod() {
    return com.gargoylesoftware.htmlunit.HttpMethod.valueOf(method.toString());
  }

}
