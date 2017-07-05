package com.mbankscraper;

import java.util.Arrays;

public class LoginDetails implements AutoCloseable {
    private final static String USERNAME_PROMPT = "User name: ";
    private final static String PASSWORD_PROMPT = "Password: ";

    public String userName;
    public char[] password;

    public void promptDetails(){
        promptUserName();
        promptPassword();
    }

    private void promptUserName(){
        userName = System.console().readLine(USERNAME_PROMPT);
    }

    private void promptPassword(){
        password = System.console().readPassword(PASSWORD_PROMPT);
    }

    public static LoginDetails createAndPrompt(){
        LoginDetails loginDetails = new LoginDetails();
        loginDetails.promptDetails();
        return loginDetails;
    }

    @Override
    public void close() {
        Arrays.fill(password,'\0');
    }
}
