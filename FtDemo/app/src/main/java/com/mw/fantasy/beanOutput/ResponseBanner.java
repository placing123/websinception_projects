package com.mw.fantasy.beanOutput;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mobiweb on 27/2/18.
 */

public class ResponseBanner {



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


        @SerializedName("TotalRecords")
        private int TotalRecords;
        @SerializedName("Records")
        private List<RecordsBean> Records;

        @SerializedName("Announcement")
        private List<AnnouncementBean> Announcement;


        public List<AnnouncementBean> getAnnouncement() {
            return Announcement;
        }

        public void setAnnouncement(List<AnnouncementBean> announcement) {
            Announcement = announcement;
        }

        public int getTotalRecords() {
            return TotalRecords;
        }

        public void setTotalRecords(int TotalRecords) {
            this.TotalRecords = TotalRecords;
        }

        public List<RecordsBean> getRecords() {
            return Records;
        }

        public void setRecords(List<RecordsBean> Records) {
            this.Records = Records;
        }

        public static class RecordsBean {


            @SerializedName("MediaGUID")
            private String MediaGUID;
            @SerializedName("MediaThumbURL")
            private String MediaThumbURL;
            @SerializedName("MediaURL")
            private String MediaURL;
            @SerializedName("MediaCaption")
            private String MediaCaption;

            public String getMediaGUID() {
                return MediaGUID;
            }

            public void setMediaGUID(String MediaGUID) {
                this.MediaGUID = MediaGUID;
            }

            public String getMediaThumbURL() {
                return MediaThumbURL;
            }

            public void setMediaThumbURL(String MediaThumbURL) {
                this.MediaThumbURL = MediaThumbURL;
            }

            public String getMediaURL() {
                return MediaURL;
            }

            public void setMediaURL(String MediaURL) {
                this.MediaURL = MediaURL;
            }

            public String getMediaCaption() {
                return MediaCaption;
            }

            public void setMediaCaption(String MediaCaption) {
                this.MediaCaption = MediaCaption;
            }
        }

        public static class AnnouncementBean {

            @SerializedName("Message")
            private String Message;


            public String getMessage() {
                return Message;
            }

            public void setMessage(String message) {
                Message = message;
            }
        }
    }
}
