package com.mbankscraper;

import com.mbankscraper.scraper.mbank.MBankScraper;
import com.mbankscraper.ui.ScraperUI;

import java.util.logging.Level;

public class Main {

  public static void main(String[] args) {
    //disable logging spam
    java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
    System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");

    try (ScraperUI ui = new ScraperUI(new MBankScraper())) {
      ui.runWithPrompt();
    }
  }

}
