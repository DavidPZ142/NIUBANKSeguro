package edu.escuelaing.niubank.controller.auth;

public class LoginDto {

    String email;
    String password;

    public LoginDto(){

    }

    public LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public String getEmail() {
        return email;
    }

    public void setMail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
