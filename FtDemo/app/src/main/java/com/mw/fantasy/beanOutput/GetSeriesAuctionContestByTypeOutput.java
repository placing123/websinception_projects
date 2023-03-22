package com.mw.fantasy.beanOutput;

import java.io.Serializable;
import java.util.List;

public class GetSeriesAuctionContestByTypeOutput {


    /**
     * ResponseCode : 200
     * Message : Success
     * Data : {"Results":[{"TotalRecords":"1","Records":[{"ContestGUID":"5d9a7894-ff67-7165-9ecc-366bfa1d82a7","ContestName":"test","SeriesID":"44935","RoundID":"13","Privacy":"No","TotalJoined":"0","IsPaid":"No","StatusID":"1","WinningAmount":"0","ContestSize":"2","EntryFee":"0","NoOfWinners":"1","EntryType":"Single","IsJoined":"No","Status":"Pending","ContestFormat":"League","ContestType":"Hot","CustomizeWinning":[{"From":"1","To":"1","Percent":"100","WinningAmount":"0"}],"UserInvitationCode":"Wcmt7n","IsConfirm":"Yes","CashBonusContribution":"0.00","TotalAmountReceived":"0","TotalWinningAmount":"0","IsSeriesMatchStarted":"No"}],"Key":"Hot Contest","TagLine":"Filling Fast. Join Now!"},{"TotalRecords":"2","Records":[{"ContestGUID":"371ad7f0-22f2-05f2-7831-345e5f45a590","ContestName":"test","SeriesID":"44935","RoundID":"13","Privacy":"No","TotalJoined":"0","IsPaid":"No","StatusID":"1","WinningAmount":"0","ContestSize":"2","EntryFee":"0","NoOfWinners":"1","EntryType":"Single","IsJoined":"No","Status":"Pending","ContestFormat":"League","ContestType":"Champion","CustomizeWinning":[{"From":"1","To":"1","Percent":"100","WinningAmount":"0"}],"UserInvitationCode":"SYXUyF","IsConfirm":"Yes","CashBonusContribution":"0.00","TotalAmountReceived":"0","TotalWinningAmount":"0","IsSeriesMatchStarted":"No"},{"ContestGUID":"b9daaf22-9003-dcb1-e3f9-129736ef7f94","ContestName":"test","SeriesID":"44935","RoundID":"13","Privacy":"No","TotalJoined":"0","IsPaid":"Yes","StatusID":"1","WinningAmount":"100","ContestSize":"2","EntryFee":"55","NoOfWinners":"1","EntryType":"Single","IsJoined":"No","Status":"Pending","ContestFormat":"League","ContestType":"Champion","CustomizeWinning":[{"From":"1","To":"1","WinningAmount":"100","percent":"100"}],"UserInvitationCode":"XBwVJ0","IsConfirm":"No","CashBonusContribution":"11.00","TotalAmountReceived":"0","TotalWinningAmount":"0","IsSeriesMatchStarted":"No"}],"Key":"Contests for Champions","TagLine":"High Entry Fees, Intense Competition"},{"TotalRecords":"0","Records":[],"Key":"Head To Head Contest","TagLine":"The Ultimate Face Off"},{"TotalRecords":"0","Records":[],"Key":"Practice Contest","TagLine":"Hone Your Skills"},{"TotalRecords":"0","Records":[],"Key":"More Contest","TagLine":"Keep Winning!"},{"TotalRecords":"0","Records":[],"Key":"Mega Contest","TagLine":"Get ready for mega winnings!"},{"TotalRecords":"0","Records":[],"Key":"Winner Takes All","TagLine":"Everything To Play For"},{"TotalRecords":"0","Records":[],"Key":"Only For Beginners","TagLine":"Play Your First Contest Now"}],"Statics":{"NormalContest":"0","ReverseContest":"0","JoinedContest":"0","TotalTeams":"0","H2HContests":"0"}}
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
         * Results : [{"TotalRecords":"1","Records":[{"ContestGUID":"5d9a7894-ff67-7165-9ecc-366bfa1d82a7","ContestName":"test","SeriesID":"44935","RoundID":"13","Privacy":"No","TotalJoined":"0","IsPaid":"No","StatusID":"1","WinningAmount":"0","ContestSize":"2","EntryFee":"0","NoOfWinners":"1","EntryType":"Single","IsJoined":"No","Status":"Pending","ContestFormat":"League","ContestType":"Hot","CustomizeWinning":[{"From":"1","To":"1","Percent":"100","WinningAmount":"0"}],"UserInvitationCode":"Wcmt7n","IsConfirm":"Yes","CashBonusContribution":"0.00","TotalAmountReceived":"0","TotalWinningAmount":"0","IsSeriesMatchStarted":"No"}],"Key":"Hot Contest","TagLine":"Filling Fast. Join Now!"},{"TotalRecords":"2","Records":[{"ContestGUID":"371ad7f0-22f2-05f2-7831-345e5f45a590","ContestName":"test","SeriesID":"44935","RoundID":"13","Privacy":"No","TotalJoined":"0","IsPaid":"No","StatusID":"1","WinningAmount":"0","ContestSize":"2","EntryFee":"0","NoOfWinners":"1","EntryType":"Single","IsJoined":"No","Status":"Pending","ContestFormat":"League","ContestType":"Champion","CustomizeWinning":[{"From":"1","To":"1","Percent":"100","WinningAmount":"0"}],"UserInvitationCode":"SYXUyF","IsConfirm":"Yes","CashBonusContribution":"0.00","TotalAmountReceived":"0","TotalWinningAmount":"0","IsSeriesMatchStarted":"No"},{"ContestGUID":"b9daaf22-9003-dcb1-e3f9-129736ef7f94","ContestName":"test","SeriesID":"44935","RoundID":"13","Privacy":"No","TotalJoined":"0","IsPaid":"Yes","StatusID":"1","WinningAmount":"100","ContestSize":"2","EntryFee":"55","NoOfWinners":"1","EntryType":"Single","IsJoined":"No","Status":"Pending","ContestFormat":"League","ContestType":"Champion","CustomizeWinning":[{"From":"1","To":"1","WinningAmount":"100","percent":"100"}],"UserInvitationCode":"XBwVJ0","IsConfirm":"No","CashBonusContribution":"11.00","TotalAmountReceived":"0","TotalWinningAmount":"0","IsSeriesMatchStarted":"No"}],"Key":"Contests for Champions","TagLine":"High Entry Fees, Intense Competition"},{"TotalRecords":"0","Records":[],"Key":"Head To Head Contest","TagLine":"The Ultimate Face Off"},{"TotalRecords":"0","Records":[],"Key":"Practice Contest","TagLine":"Hone Your Skills"},{"TotalRecords":"0","Records":[],"Key":"More Contest","TagLine":"Keep Winning!"},{"TotalRecords":"0","Records":[],"Key":"Mega Contest","TagLine":"Get ready for mega winnings!"},{"TotalRecords":"0","Records":[],"Key":"Winner Takes All","TagLine":"Everything To Play For"},{"TotalRecords":"0","Records":[],"Key":"Only For Beginners","TagLine":"Play Your First Contest Now"}]
         * Statics : {"NormalContest":"0","ReverseContest":"0","JoinedContest":"0","TotalTeams":"0","H2HContests":"0"}
         */

        private StaticsBean Statics;
        private List<ResultsBean> Results;

        public StaticsBean getStatics() {
            return Statics;
        }

        public void setStatics(StaticsBean Statics) {
            this.Statics = Statics;
        }

        public List<ResultsBean> getResults() {
            return Results;
        }

        public void setResults(List<ResultsBean> Results) {
            this.Results = Results;
        }

        public static class StaticsBean {
            /**
             * NormalContest : 0
             * ReverseContest : 0
             * JoinedContest : 0
             * TotalTeams : 0
             * H2HContests : 0
             */

            private String NormalContest;
            private String ReverseContest;
            private String JoinedContest;
            private String TotalTeams;
            private String H2HContests;

            public String getNormalContest() {
                return NormalContest;
            }

            public void setNormalContest(String NormalContest) {
                this.NormalContest = NormalContest;
            }

            public String getReverseContest() {
                return ReverseContest;
            }

            public void setReverseContest(String ReverseContest) {
                this.ReverseContest = ReverseContest;
            }

            public String getJoinedContest() {
                return JoinedContest;
            }

            public void setJoinedContest(String JoinedContest) {
                this.JoinedContest = JoinedContest;
            }

            public String getTotalTeams() {
                return TotalTeams;
            }

            public void setTotalTeams(String TotalTeams) {
                this.TotalTeams = TotalTeams;
            }

            public String getH2HContests() {
                return H2HContests;
            }

            public void setH2HContests(String H2HContests) {
                this.H2HContests = H2HContests;
            }
        }

        public static class ResultsBean {
            /**
             * TotalRecords : 1
             * Records : [{"ContestGUID":"5d9a7894-ff67-7165-9ecc-366bfa1d82a7","ContestName":"test","SeriesID":"44935","RoundID":"13","Privacy":"No","TotalJoined":"0","IsPaid":"No","StatusID":"1","WinningAmount":"0","ContestSize":"2","EntryFee":"0","NoOfWinners":"1","EntryType":"Single","IsJoined":"No","Status":"Pending","ContestFormat":"League","ContestType":"Hot","CustomizeWinning":[{"From":"1","To":"1","Percent":"100","WinningAmount":"0"}],"UserInvitationCode":"Wcmt7n","IsConfirm":"Yes","CashBonusContribution":"0.00","TotalAmountReceived":"0","TotalWinningAmount":"0","IsSeriesMatchStarted":"No"}]
             * Key : Hot Contest
             * TagLine : Filling Fast. Join Now!
             */

            private int TotalJoinedByRound;
            private int TotalRecords;
            private String Key;
            private String TagLine;
            private List<RecordsBean> Records;

            public int getTotalJoinedByRound() {
                return TotalJoinedByRound;
            }

            public void setTotalJoinedByRound(int totalJoinedByRound) {
                TotalJoinedByRound = totalJoinedByRound;
            }

            public int getTotalRecords() {
                return TotalRecords;
            }

            public void setTotalRecords(int TotalRecords) {
                this.TotalRecords = TotalRecords;
            }

            public String getKey() {
                return Key;
            }

            public void setKey(String Key) {
                this.Key = Key;
            }

            public String getTagLine() {
                return TagLine;
            }

            public void setTagLine(String TagLine) {
                this.TagLine = TagLine;
            }

            public List<RecordsBean> getRecords() {
                return Records;
            }

            public void setRecords(List<RecordsBean> Records) {
                this.Records = Records;
            }

            public static class RecordsBean {
                /**
                 * ContestGUID : 5d9a7894-ff67-7165-9ecc-366bfa1d82a7
                 * ContestName : test
                 * SeriesID : 44935
                 * RoundID : 13
                 * Privacy : No
                 * TotalJoined : 0
                 * IsPaid : No
                 * StatusID : 1
                 * WinningAmount : 0
                 * ContestSize : 2
                 * EntryFee : 0
                 * NoOfWinners : 1
                 * EntryType : Single
                 * IsJoined : No
                 * Status : Pending
                 * ContestFormat : League
                 * ContestType : Hot
                 * CustomizeWinning : [{"From":"1","To":"1","Percent":"100","WinningAmount":"0"}]
                 * UserInvitationCode : Wcmt7n
                 * IsConfirm : Yes
                 * CashBonusContribution : 0.00
                 * TotalAmountReceived : 0
                 * TotalWinningAmount : 0
                 * IsSeriesMatchStarted : No
                 */

                private String ContestGUID;
                private String ContestName;
                private String SeriesID;
                private String RoundID;
                private String Privacy;
                private String TotalJoined;
                private String IsPaid;
                private String StatusID;
                private String WinningAmount;
                private String ContestSize;
                private String EntryFee;
                private String NoOfWinners;
                private String EntryType;
                private String IsJoined;
                private String Status;
                private String ContestFormat;
                private String ContestType;
                private String UserInvitationCode;
                private String IsConfirm;
                private String CashBonusContribution;
                private String TotalAmountReceived;
                private String TotalWinningAmount;
                private String IsSeriesMatchStarted;

                private String AuctionStatus;
                private String LeagueJoinDateTime;
                private String LeagueJoinDateTimeUTC;
                private String IsAuctionFinalTeamSubmitted;
                private String DraftTeamPlayerLimit;
                private String IsAssistanceCreated;


                public String getIsAssistanceCreated() {
                    return IsAssistanceCreated;
                }

                public void setIsAssistanceCreated(String isAssistanceCreated) {
                    IsAssistanceCreated = isAssistanceCreated;
                }

                private DraftPlayerSelectionCriteria DraftPlayerSelectionCriteria;

                public String getLeagueJoinDateTimeUTC() {
                    return LeagueJoinDateTimeUTC;
                }

                public void setLeagueJoinDateTimeUTC(String leagueJoinDateTimeUTC) {
                    LeagueJoinDateTimeUTC = leagueJoinDateTimeUTC;
                }

                public RecordsBean.DraftPlayerSelectionCriteria getDraftPlayerSelectionCriteria() {
                    return DraftPlayerSelectionCriteria;
                }

                public void setDraftPlayerSelectionCriteria(RecordsBean.DraftPlayerSelectionCriteria draftPlayerSelectionCriteria) {
                    DraftPlayerSelectionCriteria = draftPlayerSelectionCriteria;
                }

                public String getDraftTeamPlayerLimit() {
                    return DraftTeamPlayerLimit;
                }

                public void setDraftTeamPlayerLimit(String draftTeamPlayerLimit) {
                    DraftTeamPlayerLimit = draftTeamPlayerLimit;
                }

                public String getAuctionStatus() {
                    return AuctionStatus;
                }

                public void setAuctionStatus(String auctionStatus) {
                    AuctionStatus = auctionStatus;
                }

                public String getLeagueJoinDateTime() {
                    return LeagueJoinDateTime;
                }

                public void setLeagueJoinDateTime(String leagueJoinDateTime) {
                    LeagueJoinDateTime = leagueJoinDateTime;
                }

                public String getIsAuctionFinalTeamSubmitted() {
                    return IsAuctionFinalTeamSubmitted;
                }

                public void setIsAuctionFinalTeamSubmitted(String isAuctionFinalTeamSubmitted) {
                    IsAuctionFinalTeamSubmitted = isAuctionFinalTeamSubmitted;
                }

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

                public String getRoundID() {
                    return RoundID;
                }

                public void setRoundID(String RoundID) {
                    this.RoundID = RoundID;
                }

                public String getPrivacy() {
                    return Privacy;
                }

                public void setPrivacy(String Privacy) {
                    this.Privacy = Privacy;
                }

                public String getTotalJoined() {
                    return TotalJoined;
                }

                public void setTotalJoined(String TotalJoined) {
                    this.TotalJoined = TotalJoined;
                }

                public String getIsPaid() {
                    return IsPaid;
                }

                public void setIsPaid(String IsPaid) {
                    this.IsPaid = IsPaid;
                }

                public String getStatusID() {
                    return StatusID;
                }

                public void setStatusID(String StatusID) {
                    this.StatusID = StatusID;
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

                public String getIsJoined() {
                    return IsJoined;
                }

                public void setIsJoined(String IsJoined) {
                    this.IsJoined = IsJoined;
                }

                public String getStatus() {
                    return Status;
                }

                public void setStatus(String Status) {
                    this.Status = Status;
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

                public String getUserInvitationCode() {
                    return UserInvitationCode;
                }

                public void setUserInvitationCode(String UserInvitationCode) {
                    this.UserInvitationCode = UserInvitationCode;
                }

                public String getIsConfirm() {
                    return IsConfirm;
                }

                public void setIsConfirm(String IsConfirm) {
                    this.IsConfirm = IsConfirm;
                }

                public String getCashBonusContribution() {
                    return CashBonusContribution;
                }

                public void setCashBonusContribution(String CashBonusContribution) {
                    this.CashBonusContribution = CashBonusContribution;
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

                public static class CustomizeWinningBean {
                    /**
                     * From : 1
                     * To : 1
                     * Percent : 100
                     * WinningAmount : 0
                     */

                    private String From;
                    private String To;
                    private String Percent;
                    private String WinningAmount;

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

                public static class DraftPlayerSelectionCriteria implements Serializable {

                    /**
                     * Wk : 1
                     * Bat : 4
                     * Ar : 2
                     * Bowl : 4
                     */

                    private String Wk;
                    private String Bat;
                    private String Ar;
                    private String Bowl;

                    public String getWk() {
                        return Wk;
                    }

                    public void setWk(String Wk) {
                        this.Wk = Wk;
                    }

                    public String getBat() {
                        return Bat;
                    }

                    public void setBat(String Bat) {
                        this.Bat = Bat;
                    }

                    public String getAr() {
                        return Ar;
                    }

                    public void setAr(String Ar) {
                        this.Ar = Ar;
                    }

                    public String getBowl() {
                        return Bowl;
                    }

                    public void setBowl(String Bowl) {
                        this.Bowl = Bowl;
                    }
                }

            }
        }
    }
}
