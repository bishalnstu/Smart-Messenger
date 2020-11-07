package com.example.root.smartmessenger.Models;

/**
 * Created by ROOT on 2/4/2018.
 */

public class Registrationfeedback {

    private boolean flag,exists;
    private String session;

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }


    public boolean getFlag()
    {
        return this.flag;
    }

    public boolean getExists()
    {
        return this.exists;
    }

    public String getSession() {
        return session;
    }
}
