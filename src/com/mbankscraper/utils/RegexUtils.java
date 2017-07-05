package com.mbankscraper.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
    public static String getFirstMatchOrCrash(String regex, String text){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        if (!matcher.find())
            throw new RuntimeException("Pattern not found.");
        return matcher.group(1);
    }

    public static List<String> getAllMatches(String regex, String text){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        List<String> matches = new ArrayList<>();
        while (matcher.find()){
            matches.add(matcher.group(1));
        }
        return matches;
    }
}
