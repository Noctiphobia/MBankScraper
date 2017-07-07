package com.mbankscraper.utils;

import org.junit.Assert;
import org.junit.Test;

public class RegexUtilsTests {
    @Test
    public void findsFirstMatchOutOfOne() {
        String expected = "123456789asdfghjkl";
        String text = String.format("{app.initialize('%s', 1, null, ':^)')}", expected);
        String pattern = "app\\.initialize\\('(.*?)'";
        String found = RegexUtils.getFirstMatchOrCrash(pattern, text);
        Assert.assertEquals(expected, found);
    }

    @Test
    public void findsFirstMatchOutOfMany() {
        String expected = "123asd";
        String text = String.format("text.append('%s').append('456fgh').append('789jkl').toString();", expected);
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
