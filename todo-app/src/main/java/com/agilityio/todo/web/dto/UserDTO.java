package com.agilityio.todo.web.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author thienlelong
 */
public class UserDTO {
    long userId;
    @Email
    @NotEmpty
    private String email;
    @NotEmpty
    private String fullName;
    @NotEmpty
    private String userName;
    @NotEmpty
    private String password;

    public UserDTO(long userId, String email, String fullName, String userName) {
        this.email = email;
        this.fullName = fullName;
        this.userName = userName;
        this.userId = userId;
    }

    public UserDTO() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
