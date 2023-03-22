package com.mw.fantasy.beanOutput;

public class AuctionBidSuccessBean {

    /**
     * ContestGUID : 75d07f14-be3d-61ff-57d7-44f524915d19
     * responseData : {"ResponseCode":200,"Message":"Player Bid successfully added.","Data":{"UserGUID":"d4f418ea-135e-dca0-5c07-c013c49c919d","UserID":"373319","FullName":"Rahul pareta","Email":"rahul.mobiwebtech@gmail.com","BidCredit":100000}}
     */

    private String ContestGUID;
    private ResponseDataBean responseData;

    public String getContestGUID() {
        return ContestGUID;
    }

    public void setContestGUID(String ContestGUID) {
        this.ContestGUID = ContestGUID;
    }

    public ResponseDataBean getResponseData() {
        return responseData;
    }

    public void setResponseData(ResponseDataBean responseData) {
        this.responseData = responseData;
    }

    public static class ResponseDataBean {
        /**
         * ResponseCode : 200
         * Message : Player Bid successfully added.
         * Data : {"UserGUID":"d4f418ea-135e-dca0-5c07-c013c49c919d","UserID":"373319","FullName":"Rahul pareta","Email":"rahul.mobiwebtech@gmail.com","BidCredit":100000}
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
             * UserGUID : d4f418ea-135e-dca0-5c07-c013c49c919d
             * UserID : 373319
             * FullName : Rahul pareta
             * Email : rahul.mobiwebtech@gmail.com
             * BidCredit : 100000
             */

            private String UserGUID;
            private String UserID;
            private String FullName;
            private String Email;
            private int BidCredit;
            private String  DateTime;

            public String getDateTime() {
                return DateTime;
            }

            public void setDateTime(String dateTime) {
                DateTime = dateTime;
            }

            public String getUserGUID() {
                return UserGUID;
            }

            public void setUserGUID(String UserGUID) {
                this.UserGUID = UserGUID;
            }

            public String getUserID() {
                return UserID;
            }

            public void setUserID(String UserID) {
                this.UserID = UserID;
            }

            public String getFullName() {
                return FullName;
            }

            public void setFullName(String FullName) {
                this.FullName = FullName;
            }

            public String getEmail() {
                return Email;
            }

            public void setEmail(String Email) {
                this.Email = Email;
            }

            public int getBidCredit() {
                return BidCredit;
            }

            public void setBidCredit(int BidCredit) {
                this.BidCredit = BidCredit;
            }
        }
    }
}
