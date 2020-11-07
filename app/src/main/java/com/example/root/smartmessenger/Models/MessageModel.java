package com.example.root.smartmessenger.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ROOT on 2/3/2018.
 */

public class MessageModel {

    @SerializedName("timedate")
    private  String timedate;
    @SerializedName("msg")
    private  String message;

    public String getMessage() {
        return message;
    }

    public String getTimedate() {
        return timedate;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimedate(String timedate) {
        this.timedate = timedate;
    }
}
