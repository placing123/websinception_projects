package com.websinception.megastar.beanOutput;

public class AuctionPlayerStausDataBean {

    /**
     * ResponseCode : 200
     * Message : Auction player status successfully updated.
     * Data : {"Message":"Auction player status successfully updated.","BreakTimeInSec":0,"AuctionTimeBreakAvailable":"No","IsBreakTimeStatus":"No","AuctionStatus":"Running","Status":1}
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
         * Message : Auction player status successfully updated.
         * BreakTimeInSec : 0
         * AuctionTimeBreakAvailable : No
         * IsBreakTimeStatus : No
         * AuctionStatus : Running
         * Status : 1
         */

        private String Message;
        private int BreakTimeInSec;
        private String AuctionTimeBreakAvailable;
        private String IsBreakTimeStatus;
        private String AuctionStatus;
        private int Status;

        public String getMessage() {
            return Message;
        }

        public void setMessage(String Message) {
            this.Message = Message;
        }

        public int getBreakTimeInSec() {
            return BreakTimeInSec;
        }

        public void setBreakTimeInSec(int BreakTimeInSec) {
            this.BreakTimeInSec = BreakTimeInSec;
        }

        public String getAuctionTimeBreakAvailable() {
            return AuctionTimeBreakAvailable;
        }

        public void setAuctionTimeBreakAvailable(String AuctionTimeBreakAvailable) {
            this.AuctionTimeBreakAvailable = AuctionTimeBreakAvailable;
        }

        public String getIsBreakTimeStatus() {
            return IsBreakTimeStatus;
        }

        public void setIsBreakTimeStatus(String IsBreakTimeStatus) {
            this.IsBreakTimeStatus = IsBreakTimeStatus;
        }

        public String getAuctionStatus() {
            return AuctionStatus;
        }

        public void setAuctionStatus(String AuctionStatus) {
            this.AuctionStatus = AuctionStatus;
        }

        public int getStatus() {
            return Status;
        }

        public void setStatus(int Status) {
            this.Status = Status;
        }
    }
}
