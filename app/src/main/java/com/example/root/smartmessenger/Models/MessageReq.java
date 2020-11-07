package com.example.root.smartmessenger.Models;

/**
 * Created by ROOT on 2/3/2018.
 */

public class MessageReq {

    private String session,email,name,password;

    public void setSession(String session) {
        this.session = session;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getSession() {
        return session;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}

