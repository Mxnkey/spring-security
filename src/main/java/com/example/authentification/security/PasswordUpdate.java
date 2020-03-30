package com.example.authentification.security;

public class PasswordUpdate {

    private String oldPassword;
    private String newPassword;

    public PasswordUpdate() {
    }
    

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
