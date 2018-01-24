package com.pereverziev;


import javax.validation.constraints.Size;

public class Account {

    @Size(min = 5, max = 10, message = "Login must be 5 - 10 length")
    private String login;

    @Size(min = 8, max = 20, message = "Password must be 8 - 20 length")
    private String password;

    @Size(min = 5, max = 50, message = "Wrong email")
    private String email;

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return getLogin() + " " + getPassword() + " " + getEmail();
    }
}

class RestorePasswordPOJO {

    @Size(min = 8, max = 20, message = "Min length is 8")
    private String password1;

    @Size(min = 8, max = 20, message = "Min length is 8")
    private String password2;

    public String getPassword1() {
        return password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    boolean checkForEquals() {
        if (password1.equals(password2)) return true;
        return false;
    }

}

class ChangePasswordPOJO {

    private String currentPassword;

    @Size(min = 8,max = 20,message = "Min length is 8")
    private String newPassword1;

    @Size(min = 8,max = 20,message = "Min length is 8")
    private String newPassword2;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public String getNewPassword1() {
        return newPassword1;
    }

    public String getNewPassword2() {
        return newPassword2;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public void setNewPassword1(String newPassword1) {
        this.newPassword1 = newPassword1;
    }

    public void setNewPassword2(String newPassword2) {
        this.newPassword2 = newPassword2;
    }

    boolean checkForEqualsPasswords() {
        if (newPassword1.equals(newPassword2)) return true;
        return false;
    }
}
