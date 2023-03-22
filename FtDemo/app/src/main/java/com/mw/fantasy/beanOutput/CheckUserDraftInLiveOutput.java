package com.mw.fantasy.beanOutput;

public class CheckUserDraftInLiveOutput {

    /**
     * ResponseCode : 200
     * Message : Users in live
     * Data : {"UserLiveInTimeSeconds":112,"ContestID":"409310","SeriesID":"352184","ContestGUID":"7e1aa341-a03a-ef43-a8ed-2a2a1842b5db","SeriesGUID":"4321d2c5-5ccc-bd73-a6d5-4d6a2132d2e6","DraftLiveRound":"3","UserID":"373319","UserGUID":"d4f418ea-135e-dca0-5c07-c013c49c919d","DraftUserLiveTime":"2019-06-07 11:35:03","UserStatus":"Live"}
     */

    private int ResponseCode;
    private String Message;
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
         * UserLiveInTimeSeconds : 112
         * ContestID : 409310
         * SeriesID : 352184
         * ContestGUID : 7e1aa341-a03a-ef43-a8ed-2a2a1842b5db
         * SeriesGUID : 4321d2c5-5ccc-bd73-a6d5-4d6a2132d2e6
         * DraftLiveRound : 3
         * UserID : 373319
         * UserGUID : d4f418ea-135e-dca0-5c07-c013c49c919d
         * DraftUserLiveTime : 2019-06-07 11:35:03
         * UserStatus : Live
         */

        private int UserLiveInTimeSeconds;
        private String ContestID;
        private String SeriesID;
        private String ContestGUID;
        private String SeriesGUID;
        private String DraftLiveRound;
        private String UserID;
        private String UserGUID;
        private String DraftUserLiveTime;
        private String UserStatus;

        public int getUserLiveInTimeSeconds() {
            return UserLiveInTimeSeconds;
        }

        public void setUserLiveInTimeSeconds(int UserLiveInTimeSeconds) {
            this.UserLiveInTimeSeconds = UserLiveInTimeSeconds;
        }

        public String getContestID() {
            return ContestID;
        }

        public void setContestID(String ContestID) {
            this.ContestID = ContestID;
        }

        public String getSeriesID() {
            return SeriesID;
        }

        public void setSeriesID(String SeriesID) {
            this.SeriesID = SeriesID;
        }

        public String getContestGUID() {
            return ContestGUID;
        }

        public void setContestGUID(String ContestGUID) {
            this.ContestGUID = ContestGUID;
        }

        public String getSeriesGUID() {
            return SeriesGUID;
        }

        public void setSeriesGUID(String SeriesGUID) {
            this.SeriesGUID = SeriesGUID;
        }

        public String getDraftLiveRound() {
            return DraftLiveRound;
        }

        public void setDraftLiveRound(String DraftLiveRound) {
            this.DraftLiveRound = DraftLiveRound;
        }

        public String getUserID() {
            return UserID;
        }

        public void setUserID(String UserID) {
            this.UserID = UserID;
        }

        public String getUserGUID() {
            return UserGUID;
        }

        public void setUserGUID(String UserGUID) {
            this.UserGUID = UserGUID;
        }

        public String getDraftUserLiveTime() {
            return DraftUserLiveTime;
        }

        public void setDraftUserLiveTime(String DraftUserLiveTime) {
            this.DraftUserLiveTime = DraftUserLiveTime;
        }

        public String getUserStatus() {
            return UserStatus;
        }

        public void setUserStatus(String UserStatus) {
            this.UserStatus = UserStatus;
        }
    }
}
