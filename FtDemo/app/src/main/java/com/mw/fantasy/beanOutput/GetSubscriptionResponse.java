package com.mw.fantasy.beanOutput;

import java.util.List;

public class GetSubscriptionResponse {

    private int ResponseCode;
    private String Message;
    private DataBean Data;

    public int getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(int responseCode) {
        ResponseCode = responseCode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean data) {
        Data = data;
    }

    public class DataBean {
        private int TotalRecords;
        private List<RecordBean> Records;
        private boolean SubscriptionsStatus;
        private SubscriptionBean subscription;
        private String SubscriptionEnd;

        public int getTotalRecords() {
            return TotalRecords;
        }

        public void setTotalRecords(int totalRecords) {
            TotalRecords = totalRecords;
        }

        public List<RecordBean> getRecords() {
            return Records;
        }

        public void setRecords(List<RecordBean> records) {
            Records = records;
        }


        public boolean isSubscriptionsStatus() {
            return SubscriptionsStatus;
        }

        public String getSubscriptionEnd() {
            return SubscriptionEnd;
        }

        public void setSubscriptionEnd(String subscriptionEnd) {
            SubscriptionEnd = subscriptionEnd;
        }

        public void setSubscriptionsStatus(boolean subscriptionsStatus) {
            SubscriptionsStatus = subscriptionsStatus;
        }

        public SubscriptionBean getSubscription() {
            return subscription;
        }

        public void setSubscription(SubscriptionBean subscription) {
            this.subscription = subscription;
        }
    }

    public class SubscriptionBean{
        private String ID;
        private String Title;
        private String Days;
        private String Price;
        private String DiscountPrice;
        private String Text;
        private String EntryDate;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getDays() {
            return Days;
        }

        public void setDays(String days) {
            Days = days;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String price) {
            Price = price;
        }

        public String getDiscountPrice() {
            return DiscountPrice;
        }

        public void setDiscountPrice(String discountPrice) {
            DiscountPrice = discountPrice;
        }

        public String getText() {
            return Text;
        }

        public void setText(String text) {
            Text = text;
        }

        public String getEntryDate() {
            return EntryDate;
        }

        public void setEntryDate(String entryDate) {
            EntryDate = entryDate;
        }
    }


    public class RecordBean {
        private String ID;
        private String Title;
        private String Days;
        private String Price;
        private String DiscountPrice;
        private String EntryDate;
        String SubscriptionEnd;
        String SubscriptionsStatus,Text ;

        public String getDiscountPrice() {
            return DiscountPrice;
        }

        public void setDiscountPrice(String discountPrice) {
            DiscountPrice = discountPrice;
        }

        public String getSubscriptionEnd() {
            return SubscriptionEnd;
        }

        public void setSubscriptionEnd(String subscriptionEnd) {
            SubscriptionEnd = subscriptionEnd;
        }

        public String getSubscriptionsStatus() {
            return SubscriptionsStatus;
        }

        public void setSubscriptionsStatus(String subscriptionsStatus) {
            SubscriptionsStatus = subscriptionsStatus;
        }

        public String getText() {
            return Text;
        }

        public void setText(String text) {
            Text = text;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getDays() {
            return Days;
        }

        public void setDays(String days) {
            Days = days;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String price) {
            Price = price;
        }

        public String getEntryDate() {
            return EntryDate;
        }

        public void setEntryDate(String entryDate) {
            EntryDate = entryDate;
        }
    }
}
