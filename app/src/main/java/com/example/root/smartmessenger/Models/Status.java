package com.example.root.smartmessenger.Models;

/**
 * Created by ROOT on 2/3/2018.
 */

import com.google.gson.annotations.SerializedName;

public class Status {

    @SerializedName("regflag")
    public boolean regflag;
    @SerializedName("session")
    public String session;
    @SerializedName("sendflag")
    private boolean sendflag;
    @SerializedName("code")
    private String code;


    public String getSession() {
        return this.session;
    }

    public boolean  getregflag()
    {
        return this.regflag;
    }

    public boolean getsendflag()
    {
        return this.sendflag;
    }

    public String getCode() {
        return this.code;
    }

}

