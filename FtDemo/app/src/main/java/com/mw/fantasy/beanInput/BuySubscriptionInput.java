package com.mw.fantasy.beanInput;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BuySubscriptionInput implements Serializable {


    @SerializedName("SessionKey")
    private String SessionKey;

    @SerializedName("ID")
    private String ID;

    public String getSessionKey() {
        return SessionKey;
    }

    public void setSessionKey(String sessionKey) {
        SessionKey = sessionKey;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
