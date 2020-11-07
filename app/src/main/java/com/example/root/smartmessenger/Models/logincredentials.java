package com.example.root.smartmessenger.Models;

/**
 * Created by ROOT on 2/27/2018.
 */

public class logincredentials {

    private String session,email,password;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getSession() {
        return session;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
