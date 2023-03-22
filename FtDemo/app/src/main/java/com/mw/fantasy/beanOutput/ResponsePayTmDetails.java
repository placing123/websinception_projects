package com.mw.fantasy.beanOutput;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mobiweb on 13/3/18.
 */

public class ResponsePayTmDetails {


    /**
     * ResponseCode : 200
     * Message : Success.
     * Data : {"MerchantID":"ZYoOYz71086222607993","OrderID":326452,"CustomerID":"CUST344184","IndustryTypeID":"Retail","ChannelID":"WAP","Amount":200,"Website":"APPSTAGING","CallbackURL":"https://securegw-stage.paytm.in/paytmchecksum/paytmCallback.jsp","TransactionURL":"https://securegw-stage.paytm.in/theia/processTransaction","CheckSumHash":"nE8jR91fJmF3ehEhPcZMmqsapNYEnMqHe29cqp/VOmFjA1T9OWsbb7wa0RDRCX1VGGQGr49OgsyB+SKgwlrrNiLpOWTUCg0GRQS6hg701q4="}
     */

    @SerializedName("ResponseCode")
    private int ResponseCode;
    @SerializedName("Message")
    private String Message;
    @SerializedName("Data")
    private DataBean Data;

    public int getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(int ResponseCode) {
        this.ResponseCode = ResponseCode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * MerchantID : ZYoOYz71086222607993
         * OrderID : 326452
         * CustomerID : CUST344184
         * IndustryTypeID : Retail
         * ChannelID : WAP
         * Amount : 200
         * Website : APPSTAGING
         * CallbackURL : https://securegw-stage.paytm.in/paytmchecksum/paytmCallback.jsp
         * TransactionURL : https://securegw-stage.paytm.in/theia/processTransaction
         * CheckSumHash : nE8jR91fJmF3ehEhPcZMmqsapNYEnMqHe29cqp/VOmFjA1T9OWsbb7wa0RDRCX1VGGQGr49OgsyB+SKgwlrrNiLpOWTUCg0GRQS6hg701q4=
         */

        @SerializedName("CustomerName")
        private String CustomerName;

        @SerializedName("NotifyURL")
        private String NotifyURL;

        @SerializedName("CustomerEmail")
        private String CustomerEmail;

        @SerializedName("CustomerPhone")
        private String CustomerPhone;


        @SerializedName("PaymentMode")
        private String PaymentMode;

        @SerializedName("PaymentToken")
        private String PaymentToken;

        @SerializedName("AppID")
        private String AppID;


        @SerializedName("OrderNote")
        private String OrderNote;


        @SerializedName("MerchantID")
        private String MerchantID;
        @SerializedName("OrderID")
        private int OrderID;
        @SerializedName("CustomerID")
        private String CustomerID;
        @SerializedName("IndustryTypeID")
        private String IndustryTypeID;
        @SerializedName("ChannelID")
        private String ChannelID;
        @SerializedName("Amount")
        private int Amount;
        @SerializedName("Website")
        private String Website;
        @SerializedName("CallbackURL")
        private String CallbackURL;
        @SerializedName("TransactionURL")
        private String TransactionURL;
        @SerializedName("CheckSumHash")
        private String CheckSumHash;


        @SerializedName("checksum")
        private String checksum;

        @SerializedName("base64Body")
        private String base64Body;

        @SerializedName("SaltKey")
        private String SaltKey;


        @SerializedName("SaltIndex")
        private String SaltIndex;
        @SerializedName("TransactionID")
        private String TransactionID;
        @SerializedName("x_verify")
        private String x_verify;


        public String getChecksum() {
            return checksum;
        }

        public void setChecksum(String checksum) {
            this.checksum = checksum;
        }

        public String getBase64Body() {
            return base64Body;
        }

        public void setBase64Body(String base64Body) {
            this.base64Body = base64Body;
        }

        public String getSaltKey() {
            return SaltKey;
        }

        public void setSaltKey(String saltKey) {
            SaltKey = saltKey;
        }

        public String getSaltIndex() {
            return SaltIndex;
        }

        public void setSaltIndex(String saltIndex) {
            SaltIndex = saltIndex;
        }

        public String getTransactionID() {
            return TransactionID;
        }

        public void setTransactionID(String transactionID) {
            TransactionID = transactionID;
        }

        public String getX_verify() {
            return x_verify;
        }

        public void setX_verify(String x_verify) {
            this.x_verify = x_verify;
        }

        public String getNotifyURL() {
            return NotifyURL;
        }

        public void setNotifyURL(String notifyURL) {
            NotifyURL = notifyURL;
        }

        public String getMerchantID() {
            return MerchantID;
        }

        public void setMerchantID(String MerchantID) {
            this.MerchantID = MerchantID;
        }

        public int getOrderID() {
            return OrderID;
        }

        public void setOrderID(int OrderID) {
            this.OrderID = OrderID;
        }

        public String getCustomerID() {
            return CustomerID;
        }

        public void setCustomerID(String CustomerID) {
            this.CustomerID = CustomerID;
        }

        public String getIndustryTypeID() {
            return IndustryTypeID;
        }

        public void setIndustryTypeID(String IndustryTypeID) {
            this.IndustryTypeID = IndustryTypeID;
        }

        public String getPaymentMode() {
            return PaymentMode;
        }

        public void setPaymentMode(String paymentMode) {
            PaymentMode = paymentMode;
        }

        public String getPaymentToken() {
            return PaymentToken;
        }

        public void setPaymentToken(String paymentToken) {
            PaymentToken = paymentToken;
        }

        public String getAppID() {
            return AppID;
        }

        public void setAppID(String appID) {
            AppID = appID;
        }

        public String getOrderNote() {
            return OrderNote;
        }

        public String getCustomerName() {
            return CustomerName;
        }

        public void setCustomerName(String customerName) {
            CustomerName = customerName;
        }

        public String getCustomerEmail() {
            return CustomerEmail;
        }

        public void setCustomerEmail(String customerEmail) {
            CustomerEmail = customerEmail;
        }

        public String getCustomerPhone() {
            return CustomerPhone;
        }

        public void setCustomerPhone(String customerPhone) {
            CustomerPhone = customerPhone;
        }

        public void setOrderNote(String orderNote) {
            OrderNote = orderNote;
        }

        public String getChannelID() {
            return ChannelID;
        }

        public void setChannelID(String ChannelID) {
            this.ChannelID = ChannelID;
        }

        public int getAmount() {
            return Amount;
        }

        public void setAmount(int Amount) {
            this.Amount = Amount;
        }

        public String getWebsite() {
            return Website;
        }

        public void setWebsite(String Website) {
            this.Website = Website;
        }

        public String getCallbackURL() {
            return CallbackURL;
        }

        public void setCallbackURL(String CallbackURL) {
            this.CallbackURL = CallbackURL;
        }

        public String getTransactionURL() {
            return TransactionURL;
        }

        public void setTransactionURL(String TransactionURL) {
            this.TransactionURL = TransactionURL;
        }

        public String getCheckSumHash() {
            return CheckSumHash;
        }

        public void setCheckSumHash(String CheckSumHash) {
            this.CheckSumHash = CheckSumHash;
        }
    }
}
