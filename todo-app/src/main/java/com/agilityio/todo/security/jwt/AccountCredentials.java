package com.agilityio.todo.security.jwt;

/**
 * Project: toto-app
 * Account credentials
 */
public class AccountCredentials {
    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void getUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
