package com.mw.fantasy.beanInput;

import com.google.gson.annotations.SerializedName;

public class CheckPhonePeTransactionStatusInput {


    @SerializedName("SessionKey")
    private String SessionKey;

    @SerializedName("transactionId")
    private String transactionId;

    @SerializedName("OrderId")
    private String OrderId;


    public String getSessionKey() {
        return SessionKey;
    }

    public void setSessionKey(String sessionKey) {
        SessionKey = sessionKey;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }
}
