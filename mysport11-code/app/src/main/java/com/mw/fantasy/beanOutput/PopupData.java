package com.mw.fantasy.beanOutput;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PopupData {

    @SerializedName("ResponseCode")
    @Expose
    private String responseCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private PopupBeaData data;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PopupBeaData getData() {
        return data;
    }

    public void setData(PopupBeaData data) {
        this.data = data;
    }

    public class PopupBeaData {
        @SerializedName("TotalRecords")
        @Expose
        private String totalRecords;
        @SerializedName("Records")
        @Expose
        private PopupRecords records;

        public String getTotalRecords() {
            return totalRecords;
        }

        public void setTotalRecords(String totalRecords) {
            this.totalRecords = totalRecords;
        }

        public PopupRecords getRecords() {
            return records;
        }

        public void setRecords(PopupRecords records) {
            this.records = records;
        }
    }

    public class PopupRecords {


        @SerializedName("NotificationID")
        @Expose
        private String notificationID;
        @SerializedName("NotificationText")
        @Expose
        private String notificationText;
        @SerializedName("NotificationMessage")
        @Expose
        private String notificationMessage;

        public String getNotificationID() {
            return notificationID;
        }

        public void setNotificationID(String notificationID) {
            this.notificationID = notificationID;
        }

        public String getNotificationText() {
            return notificationText;
        }

        public void setNotificationText(String notificationText) {
            this.notificationText = notificationText;
        }

        public String getNotificationMessage() {
            return notificationMessage;
        }

        public void setNotificationMessage(String notificationMessage) {
            this.notificationMessage = notificationMessage;
        }
    }
}