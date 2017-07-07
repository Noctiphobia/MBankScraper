package com.mbankscraper;

import java.util.Arrays;

public class LoginCredentials implements AutoCloseable {
    public String userName;
    public char[] password;

    public LoginCredentials() {
    }

    public LoginCredentials(String userName, char[] password) {
        this.userName = userName;
        this.password = password;
    }

    @Override
    public void close() {
        Arrays.fill(password, '\0');
    }
}
