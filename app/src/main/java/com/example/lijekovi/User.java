package com.example.lijekovi;

public class User {
    private String email;
    private String lozinka;
    private String oib;
    private String puno_ime;

    public  User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.lozinka = password;
    }

    public User(String email, String lozinka, String oib, String puno_ime) {
        this.email = email;
        this.lozinka = lozinka;
        this.oib = oib;
        this.puno_ime = puno_ime;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return lozinka;
    }

    public String getOib() {
        return oib;
    }

    public String getFullName() {
        return puno_ime;
    }
}
