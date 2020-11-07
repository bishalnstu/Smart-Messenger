package com.example.root.smartmessenger.Models;

/**
 * Created by ROOT on 2/3/2018.
 */

public class ResetResponse {

    private boolean status;
    private  String code;

    public boolean getStatus()
    {
        return this.status;
    }

    public String getCode()
    {
        return  this.code;
    }
}