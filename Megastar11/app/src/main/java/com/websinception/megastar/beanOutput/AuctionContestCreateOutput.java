package com.websinception.megastar.beanOutput;


import java.io.Serializable;
import java.util.List;

public class AuctionContestCreateOutput {


    /**
     * ResponseCode : 200
     * Message : Contest created successfully.
     * Data : {"ContestGUID":"f24a163a-241d-fbbd-1305-aee9d94e1383","ContestName":"test1","SeriesID":"58843","RoundID":"12","CustomizeWinning":[{"From":"1","Percent":"100","Rank":"1","To":"1","WinningAmount":"1700"}],"ContestFormat":"League","ContestType":"Normal","Privacy":"Yes","IsPaid":"Yes","WinningAmount":"1700","ContestSize":"2","EntryFee":"1000","NoOfWinners":"1","EntryType":"Single","UserInvitationCode":"75mTtg","DraftPlayerSelectionCriteria":{"AllRounder":1,"Batsman":1,"Bowler":1,"WicketKeeper":1},"DraftTeamPlayerLimit":"4","TotalAmountReceived":"0","TotalWinningAmount":"0","IsSeriesMatchStarted":"No"}
     */

    private String ResponseCode;
    private String Message;
    private DataBean Data;

    public String getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(String ResponseCode) {
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
         * ContestGUID : f24a163a-241d-fbbd-1305-aee9d94e1383
         * ContestName : test1
         * SeriesID : 58843
         * RoundID : 12
         * CustomizeWinning : [{"From":"1","Percent":"100","Rank":"1","To":"1","WinningAmount":"1700"}]
         * ContestFormat : League
         * ContestType : Normal
         * Privacy : Yes
         * IsPaid : Yes
         * WinningAmount : 1700
         * ContestSize : 2
         * EntryFee : 1000
         * NoOfWinners : 1
         * EntryType : Single
         * UserInvitationCode : 75mTtg
         * DraftPlayerSelectionCriteria : {"AllRounder":1,"Batsman":1,"Bowler":1,"WicketKeeper":1}
         * DraftTeamPlayerLimit : 4
         * TotalAmountReceived : 0
         * TotalWinningAmount : 0
         * IsSeriesMatchStarted : No
         */

        private String MatchGUID;
        private String ContestGUID;
        private String ContestName;
        private String SeriesID;
        private String RoundID;
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
        private DraftPlayerSelectionCriteriaBean DraftPlayerSelectionCriteria;
        private String DraftTeamPlayerLimit;
        private String TotalAmountReceived;
        private String TotalWinningAmount;
        private String IsSeriesMatchStarted;
        private List<CustomizeWinningBean> CustomizeWinning;

        public String getMatchGUID() {
            return MatchGUID;
        }

        public void setMatchGUID(String matchGUID) {
            MatchGUID = matchGUID;
        }

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

        public String getRoundID() {
            return RoundID;
        }

        public void setRoundID(String RoundID) {
            this.RoundID = RoundID;
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

        public DraftPlayerSelectionCriteriaBean getDraftPlayerSelectionCriteria() {
            return DraftPlayerSelectionCriteria;
        }

        public void setDraftPlayerSelectionCriteria(DraftPlayerSelectionCriteriaBean DraftPlayerSelectionCriteria) {
            this.DraftPlayerSelectionCriteria = DraftPlayerSelectionCriteria;
        }

        public String getDraftTeamPlayerLimit() {
            return DraftTeamPlayerLimit;
        }

        public void setDraftTeamPlayerLimit(String DraftTeamPlayerLimit) {
            this.DraftTeamPlayerLimit = DraftTeamPlayerLimit;
        }

        public String getTotalAmountReceived() {
            return TotalAmountReceived;
        }

        public void setTotalAmountReceived(String TotalAmountReceived) {
            this.TotalAmountReceived = TotalAmountReceived;
        }

        public String getTotalWinningAmount() {
            return TotalWinningAmount;
        }

        public void setTotalWinningAmount(String TotalWinningAmount) {
            this.TotalWinningAmount = TotalWinningAmount;
        }

        public String getIsSeriesMatchStarted() {
            return IsSeriesMatchStarted;
        }

        public void setIsSeriesMatchStarted(String IsSeriesMatchStarted) {
            this.IsSeriesMatchStarted = IsSeriesMatchStarted;
        }

        public List<CustomizeWinningBean> getCustomizeWinning() {
            return CustomizeWinning;
        }

        public void setCustomizeWinning(List<CustomizeWinningBean> CustomizeWinning) {
            this.CustomizeWinning = CustomizeWinning;
        }

        public static class DraftPlayerSelectionCriteriaBean implements Serializable {
            /**
             * Wk : 1
             * Bat : 2
             * Ar : 3
             * Bowl : 4
             */

            private int Wk;
            private int Bat;
            private int Ar;
            private int Bowl;

            public int getWk() {
                return Wk;
            }

            public void setWk(int Wk) {
                this.Wk = Wk;
            }

            public int getBat() {
                return Bat;
            }

            public void setBat(int Bat) {
                this.Bat = Bat;
            }

            public int getAr() {
                return Ar;
            }

            public void setAr(int Ar) {
                this.Ar = Ar;
            }

            public int getBowl() {
                return Bowl;
            }

            public void setBowl(int Bowl) {
                this.Bowl = Bowl;
            }
        }

        public static class CustomizeWinningBean {
            /**
             * From : 1
             * Percent : 100
             * Rank : 1
             * To : 1
             * WinningAmount : 1700
             */

            private String From;
            private String Percent;
            private String Rank;
            private String To;
            private String WinningAmount;

            public String getFrom() {
                return From;
            }

            public void setFrom(String From) {
                this.From = From;
            }

            public String getPercent() {
                return Percent;
            }

            public void setPercent(String Percent) {
                this.Percent = Percent;
            }

            public String getRank() {
                return Rank;
            }

            public void setRank(String Rank) {
                this.Rank = Rank;
            }

            public String getTo() {
                return To;
            }

            public void setTo(String To) {
                this.To = To;
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
