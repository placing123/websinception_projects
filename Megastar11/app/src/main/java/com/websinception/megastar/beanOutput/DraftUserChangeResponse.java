package com.websinception.megastar.beanOutput;

public class DraftUserChangeResponse {

    /**
     * ContestGUID : ca06a811-65f4-6512-283a-eec5d9f6f4d8
     * UserGUID : d4f418ea-135e-dca0-5c07-c013c49c919d
     * getBidPlayerData : {"ResponseCode":200,"Message":"User in live","Data":{"UserLiveInTimeSeconds":0,"ContestID":"409304","SeriesID":"352184","ContestGUID":"ca06a811-65f4-6512-283a-eec5d9f6f4d8","SeriesGUID":"4321d2c5-5ccc-bd73-a6d5-4d6a2132d2e6","DraftLiveRound":"1","DraftNextRound":"1","UserID":"373319","UserGUID":"d4f418ea-135e-dca0-5c07-c013c49c919d","FirstName":"Rahul pareta"}}
     * Datetime : 2019-06-07 09:00:01
     */

    private String ContestGUID;
    private String UserGUID;
    private GetBidPlayerDataBean getBidPlayerData;
    private String Datetime;

    public String getContestGUID() {
        return ContestGUID;
    }

    public void setContestGUID(String ContestGUID) {
        this.ContestGUID = ContestGUID;
    }

    public String getUserGUID() {
        return UserGUID;
    }

    public void setUserGUID(String UserGUID) {
        this.UserGUID = UserGUID;
    }

    public GetBidPlayerDataBean getGetBidPlayerData() {
        return getBidPlayerData;
    }

    public void setGetBidPlayerData(GetBidPlayerDataBean getBidPlayerData) {
        this.getBidPlayerData = getBidPlayerData;
    }

    public String getDatetime() {
        return Datetime;
    }

    public void setDatetime(String Datetime) {
        this.Datetime = Datetime;
    }

    public static class GetBidPlayerDataBean {
        /**
         * ResponseCode : 200
         * Message : User in live
         * Data : {"UserLiveInTimeSeconds":0,"ContestID":"409304","SeriesID":"352184","ContestGUID":"ca06a811-65f4-6512-283a-eec5d9f6f4d8","SeriesGUID":"4321d2c5-5ccc-bd73-a6d5-4d6a2132d2e6","DraftLiveRound":"1","DraftNextRound":"1","UserID":"373319","UserGUID":"d4f418ea-135e-dca0-5c07-c013c49c919d","FirstName":"Rahul pareta"}
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
             * UserLiveInTimeSeconds : 0
             * ContestID : 409304
             * SeriesID : 352184
             * ContestGUID : ca06a811-65f4-6512-283a-eec5d9f6f4d8
             * SeriesGUID : 4321d2c5-5ccc-bd73-a6d5-4d6a2132d2e6
             * DraftLiveRound : 1
             * DraftNextRound : 1
             * UserID : 373319
             * UserGUID : d4f418ea-135e-dca0-5c07-c013c49c919d
             * FirstName : Rahul pareta
             */

            private int UserLiveInTimeSeconds;
            private String ContestID;
            private String SeriesID;
            private String ContestGUID;
            private String SeriesGUID;
            private String DraftLiveRound;
            private String DraftNextRound;
            private String UserID;
            private String UserGUID;
            private String FirstName;

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

            public String getDraftNextRound() {
                return DraftNextRound;
            }

            public void setDraftNextRound(String DraftNextRound) {
                this.DraftNextRound = DraftNextRound;
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

            public String getFirstName() {
                return FirstName;
            }

            public void setFirstName(String FirstName) {
                this.FirstName = FirstName;
            }
        }
    }
}
