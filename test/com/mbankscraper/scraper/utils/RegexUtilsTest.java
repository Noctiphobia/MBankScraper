package com.mbankscraper.scraper.utils;

import org.junit.Assert;
import org.junit.Test;

public class RegexUtilsTest {

  @Test
  public void findsFirstMatchOutOfOne() {
    String expected = "123456789asdfghjkl";
    String text = "{app.initialize('123456789asdfghjkl', 1, null, ':^)')}";
    String pattern = "app\\.initialize\\('(.*?)'";
    String found = RegexUtils.getFirstMatchOrCrash(pattern, text);
    Assert.assertEquals(expected, found);
  }

  @Test
  public void findsFirstMatchOutOfMany() {
    String expected = "123asd";
    String text = "text.append('123asd').append('456fgh').append('789jkl').toString();";
    String pattern = "\\.append\\('(.*?)'";
    String found = RegexUtils.getFirstMatchOrCrash(pattern, text);
    Assert.assertEquals(expected, found);
  }

  @Test(expected = RuntimeException.class)
  public void crashesWhenNoMatchFound() {
    String text = "I am a 'text'";
    String pattern = "He is a '(.*?')";
    RegexUtils.getFirstMatchOrCrash(pattern, text);
  }

}
