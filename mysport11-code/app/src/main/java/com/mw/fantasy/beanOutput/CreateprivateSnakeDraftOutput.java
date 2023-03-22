package com.mw.fantasy.beanOutput;

import java.util.List;

public class CreateprivateSnakeDraftOutput {

    /**
     * ResponseCode : 200
     * Message : Contest created successfully.
     * Data : {"ContestGUID":"bf91751b-84f4-009b-3ecf-2b601ee62d09","ContestName":"tem","SeriesID":"3480","CustomizeWinning":[{"Rank":"1","From":"1","To":"1","Percent":"40","WinningAmount":"1360"},{"Rank":"2","From":"2","To":"2","Percent":"30","WinningAmount":"1020"},{"Rank":"3","From":"3","To":"3","Percent":"20","WinningAmount":"680"},{"Rank":"4","From":"4","To":"4","Percent":"10","WinningAmount":"340"}],"ContestFormat":"League","ContestType":"Normal","Privacy":"Yes","IsPaid":"Yes","WinningAmount":"3400","ContestSize":"4","EntryFee":"1000","NoOfWinners":"4","EntryType":"Single","UserInvitationCode":"HIoJY3","TotalAmountReceived":0,"TotalWinningAmount":0}
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
         * ContestGUID : bf91751b-84f4-009b-3ecf-2b601ee62d09
         * ContestName : tem
         * SeriesID : 3480
         * CustomizeWinning : [{"Rank":"1","From":"1","To":"1","Percent":"40","WinningAmount":"1360"},{"Rank":"2","From":"2","To":"2","Percent":"30","WinningAmount":"1020"},{"Rank":"3","From":"3","To":"3","Percent":"20","WinningAmount":"680"},{"Rank":"4","From":"4","To":"4","Percent":"10","WinningAmount":"340"}]
         * ContestFormat : League
         * ContestType : Normal
         * Privacy : Yes
         * IsPaid : Yes
         * WinningAmount : 3400
         * ContestSize : 4
         * EntryFee : 1000
         * NoOfWinners : 4
         * EntryType : Single
         * UserInvitationCode : HIoJY3
         * TotalAmountReceived : 0
         * TotalWinningAmount : 0
         */

        private String ContestGUID;
        private String ContestName;
        private String SeriesID;
        private String ContestFormat;
        private String ContestType;
        private String Privacy;
        private String IsPaid;
        private String WinningAmount;
        private String ContestSize;
        private String EntryFee;
        private String NoOfWinners;
        private String EntryType;
        private String UserInvitationCode;
        private int TotalAmountReceived;
        private int TotalWinningAmount;
        private List<CustomizeWinningBean> CustomizeWinning;

        public String getContestGUID() {
            return ContestGUID;
        }

        public void setContestGUID(String ContestGUID) {
            this.ContestGUID = ContestGUID;
        }

        public String getContestName() {
            return ContestName;
        }

        public void setContestName(String ContestName) {
            this.ContestName = ContestName;
        }

        public String getSeriesID() {
            return SeriesID;
        }

        public void setSeriesID(String SeriesID) {
            this.SeriesID = SeriesID;
        }

        public String getContestFormat() {
            return ContestFormat;
        }

        public void setContestFormat(String ContestFormat) {
            this.ContestFormat = ContestFormat;
        }

        public String getContestType() {
            return ContestType;
        }

        public void setContestType(String ContestType) {
            this.ContestType = ContestType;
        }

        public String getPrivacy() {
            return Privacy;
        }

        public void setPrivacy(String Privacy) {
            this.Privacy = Privacy;
        }

        public String getIsPaid() {
            return IsPaid;
        }

        public void setIsPaid(String IsPaid) {
            this.IsPaid = IsPaid;
        }

        public String getWinningAmount() {
            return WinningAmount;
        }

        public void setWinningAmount(String WinningAmount) {
            this.WinningAmount = WinningAmount;
        }

        public String getContestSize() {
            return ContestSize;
        }

        public void setContestSize(String ContestSize) {
            this.ContestSize = ContestSize;
        }

        public String getEntryFee() {
            return EntryFee;
        }

        public void setEntryFee(String EntryFee) {
            this.EntryFee = EntryFee;
        }

        public String getNoOfWinners() {
            return NoOfWinners;
        }

        public void setNoOfWinners(String NoOfWinners) {
            this.NoOfWinners = NoOfWinners;
        }

        public String getEntryType() {
            return EntryType;
        }

        public void setEntryType(String EntryType) {
            this.EntryType = EntryType;
        }

        public String getUserInvitationCode() {
            return UserInvitationCode;
        }

        public void setUserInvitationCode(String UserInvitationCode) {
            this.UserInvitationCode = UserInvitationCode;
        }

        public int getTotalAmountReceived() {
            return TotalAmountReceived;
        }

        public void setTotalAmountReceived(int TotalAmountReceived) {
            this.TotalAmountReceived = TotalAmountReceived;
        }

        public int getTotalWinningAmount() {
            return TotalWinningAmount;
        }

        public void setTotalWinningAmount(int TotalWinningAmount) {
            this.TotalWinningAmount = TotalWinningAmount;
        }

        public List<CustomizeWinningBean> getCustomizeWinning() {
            return CustomizeWinning;
        }

        public void setCustomizeWinning(List<CustomizeWinningBean> CustomizeWinning) {
            this.CustomizeWinning = CustomizeWinning;
        }

        public static class CustomizeWinningBean {
            /**
             * Rank : 1
             * From : 1
             * To : 1
             * Percent : 40
             * WinningAmount : 1360
             */

            private String Rank;
            private String From;
            private String To;
            private String Percent;
            private String WinningAmount;

            public String getRank() {
                return Rank;
            }

            public void setRank(String Rank) {
                this.Rank = Rank;
            }

            public String getFrom() {
                return From;
            }

            public void setFrom(String From) {
                this.From = From;
            }

            public String getTo() {
                return To;
            }

            public void setTo(String To) {
                this.To = To;
            }

            public String getPercent() {
                return Percent;
            }

            public void setPercent(String Percent) {
                this.Percent = Percent;
            }

            public String getWinningAmount() {
                return WinningAmount;
            }

            public void setWinningAmount(String WinningAmount) {
                this.WinningAmount = WinningAmount;
            }
        }
    }
}
