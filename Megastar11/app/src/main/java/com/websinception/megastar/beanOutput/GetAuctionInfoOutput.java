package com.websinception.megastar.beanOutput;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetAuctionInfoOutput {

    /**
     * ResponseCode : 200
     * Message : Success
     * Data : {"ContestGUID":"5193c775-a397-5b10-32f6-394aa40efe62","ContestName":"Auction 1","LeagueJoinDateTime":"2019-05-06 16:50:00","Status":"Pending","AuctionStatus":"Pending","LeagueJoinDateTimeUTC":"2019-05-06 11:20:00","CustomizeWinning":[],"TotalAmountReceived":"28","TotalWinningAmount":"0.00"}
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
         * ContestGUID : 5193c775-a397-5b10-32f6-394aa40efe62
         * ContestName : Auction 1
         * LeagueJoinDateTime : 2019-05-06 16:50:00
         * Status : Pending
         * AuctionStatus : Pending
         * LeagueJoinDateTimeUTC : 2019-05-06 11:20:00
         * CustomizeWinning : []
         * TotalAmountReceived : 28
         * TotalWinningAmount : 0.00
         */

        private String GameTimeLive;
        private String CashBonusContribution;
        private String IsConfirm;
        private String TeamNameVisitor;
        private String TeamNameLocal;
        private String UserInvitationCode;
        private String TotalJoined;
        private String ContestType;
        private String ContestFormat;
        private String IsJoined;
        private String EntryType;
        private String NoOfWinners;
        private String EntryFee;
        private String ContestSize;
        private String WinningAmount;
        private String IsPaid;
        private String Privacy;
        private String GameType;
        private String SeriesName;
        private String ContestGUID;
        private String ContestName;
        private String LeagueJoinDateTime;
        private String IsSeriesMatchStarted;
        private String Status;
        private String AuctionStatus;
        private String LeagueJoinDateTimeUTC;
        private String TotalAmountReceived;
        private String DraftSeriesType;
        private String MatchType;
        private String SeriesGUID;
        private String SeriesID;
        private String TotalWinningAmount;
        private MatchScoreDetailsBean MatchScoreDetails;
        private String DraftLiveRound;
        private String DraftTeamPlayerLimit;
        private List<CustomizeWinningBean> CustomizeWinning;
        private DraftPlayerSelectionCriteria DraftPlayerSelectionCriteria;


        public String getMatchType() {
            return MatchType;
        }

        public void setMatchType(String matchType) {
            MatchType = matchType;
        }

        public String getIsSeriesMatchStarted() {
            return IsSeriesMatchStarted;
        }

        public String getDraftTeamPlayerLimit() {
            return DraftTeamPlayerLimit;
        }

        public void setDraftTeamPlayerLimit(String draftTeamPlayerLimit) {
            DraftTeamPlayerLimit = draftTeamPlayerLimit;
        }

        public String getDraftLiveRound() {
            return DraftLiveRound;
        }

        public void setDraftLiveRound(String draftLiveRound) {
            DraftLiveRound = draftLiveRound;
        }

        public void setIsSeriesMatchStarted(String isSeriesMatchStarted) {
            IsSeriesMatchStarted = isSeriesMatchStarted;
        }

        public DataBean.DraftPlayerSelectionCriteria getDraftPlayerSelectionCriteria() {
            return DraftPlayerSelectionCriteria;
        }

        public void setDraftPlayerSelectionCriteria(DataBean.DraftPlayerSelectionCriteria draftPlayerSelectionCriteria) {
            DraftPlayerSelectionCriteria = draftPlayerSelectionCriteria;
        }

        public String getGameTimeLive() {
            return GameTimeLive;
        }

        public void setGameTimeLive(String gameTimeLive) {
            GameTimeLive = gameTimeLive;
        }

        public String getCashBonusContribution() {
            return CashBonusContribution;
        }

        public void setCashBonusContribution(String cashBonusContribution) {
            CashBonusContribution = cashBonusContribution;
        }

        public String getIsConfirm() {
            return IsConfirm;
        }

        public void setIsConfirm(String isConfirm) {
            IsConfirm = isConfirm;
        }

        public String getTeamNameVisitor() {
            return TeamNameVisitor;
        }

        public void setTeamNameVisitor(String teamNameVisitor) {
            TeamNameVisitor = teamNameVisitor;
        }

        public String getTeamNameLocal() {
            return TeamNameLocal;
        }

        public void setTeamNameLocal(String teamNameLocal) {
            TeamNameLocal = teamNameLocal;
        }

        public String getUserInvitationCode() {
            return UserInvitationCode;
        }

        public void setUserInvitationCode(String userInvitationCode) {
            UserInvitationCode = userInvitationCode;
        }

        public String getTotalJoined() {
            return TotalJoined;
        }

        public void setTotalJoined(String totalJoined) {
            TotalJoined = totalJoined;
        }

        public String getContestType() {
            return ContestType;
        }

        public void setContestType(String contestType) {
            ContestType = contestType;
        }

        public String getContestFormat() {
            return ContestFormat;
        }

        public void setContestFormat(String contestFormat) {
            ContestFormat = contestFormat;
        }

        public String getIsJoined() {
            return IsJoined;
        }

        public void setIsJoined(String isJoined) {
            IsJoined = isJoined;
        }

        public String getEntryType() {
            return EntryType;
        }

        public void setEntryType(String entryType) {
            EntryType = entryType;
        }

        public String getNoOfWinners() {
            return NoOfWinners;
        }

        public void setNoOfWinners(String noOfWinners) {
            NoOfWinners = noOfWinners;
        }

        public String getEntryFee() {
            return EntryFee;
        }

        public void setEntryFee(String entryFee) {
            EntryFee = entryFee;
        }

        public String getContestSize() {
            return ContestSize;
        }

        public void setContestSize(String contestSize) {
            ContestSize = contestSize;
        }

        public String getWinningAmount() {
            return WinningAmount;
        }

        public void setWinningAmount(String winningAmount) {
            WinningAmount = winningAmount;
        }

        public String getIsPaid() {
            return IsPaid;
        }

        public void setIsPaid(String isPaid) {
            IsPaid = isPaid;
        }

        public String getPrivacy() {
            return Privacy;
        }

        public void setPrivacy(String privacy) {
            Privacy = privacy;
        }

        public String getGameType() {
            return GameType;
        }

        public void setGameType(String gameType) {
            GameType = gameType;
        }

        public String getSeriesName() {
            return SeriesName;
        }

        public void setSeriesName(String seriesName) {
            SeriesName = seriesName;
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

        public String getLeagueJoinDateTime() {
            return LeagueJoinDateTime;
        }

        public void setLeagueJoinDateTime(String LeagueJoinDateTime) {
            this.LeagueJoinDateTime = LeagueJoinDateTime;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String Status) {
            this.Status = Status;
        }

        public String getAuctionStatus() {
            return AuctionStatus;
        }

        public void setAuctionStatus(String AuctionStatus) {
            this.AuctionStatus = AuctionStatus;
        }

        public String getLeagueJoinDateTimeUTC() {
            return LeagueJoinDateTimeUTC;
        }

        public void setLeagueJoinDateTimeUTC(String LeagueJoinDateTimeUTC) {
            this.LeagueJoinDateTimeUTC = LeagueJoinDateTimeUTC;
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

        public String getDraftSeriesType() {
            return DraftSeriesType;
        }

        public void setDraftSeriesType(String draftSeriesType) {
            DraftSeriesType = draftSeriesType;
        }

        public List<CustomizeWinningBean> getCustomizeWinning() {
            return CustomizeWinning;
        }

        public void setCustomizeWinning(List<CustomizeWinningBean> CustomizeWinning) {
            this.CustomizeWinning = CustomizeWinning;
        }

        public String getSeriesGUID() {
            return SeriesGUID;
        }

        public void setSeriesGUID(String seriesGUID) {
            SeriesGUID = seriesGUID;
        }

        public String getSeriesID() {
            return SeriesID;
        }

        public void setSeriesID(String seriesID) {
            SeriesID = seriesID;
        }

        public MatchScoreDetailsBean getMatchScoreDetails() {
            return MatchScoreDetails;
        }

        public void setMatchScoreDetails(MatchScoreDetailsBean matchScoreDetails) {
            MatchScoreDetails = matchScoreDetails;
        }


        public static class CustomizeWinningBean {
            /**
             * From : 1
             * To : 1
             * WinningAmount : 1
             * percent : 100
             */

            private int From;
            private String To;
            private String WinningAmount;
            private int percent;

            public int getFrom() {
                return From;
            }

            public void setFrom(int From) {
                this.From = From;
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

            public int getPercent() {
                return percent;
            }

            public void setPercent(int percent) {
                this.percent = percent;
            }
        }

        public static class MatchScoreDetailsBean {
            /**
             * StatusLive : Live
             * StatusNote :
             * TeamScoreLocal : {"Name":"Perth Scorchers","ShortName":"PER","LogoURL":"","Scores":"92/3","Overs":"18.0"}
             * TeamScoreVisitor : {"Name":"Adelaide Strikers","ShortName":"ADS","LogoURL":"","Scores":"88/10","Overs":"17.2"}
             * MatchVenue : Perth Stadium, Perth, Australia
             * Result :
             * Toss : Perth Scorchers won the toss and chose to bowl first
             * ManOfTheMatchPlayer :
             * Innings : [{"Name":"Adelaide Strikers inning","ShortName":"ADS inn.","Scores":"88/10","Status":"","ScoresFull":"88/10 (17.2 ov)","BatsmanData":[{"Name":"Alex Carey","PlayerIDLive":"ale_carey","Role":"WicketKeeper","Runs":11,"BallsFaced":26,"Fours":"","Sixes":"","HowOut":"runout Josh Inglis","IsPlaying":"No","StrikeRate":42.31},{"Name":"Jake Weatherald","PlayerIDLive":"jak_weatherald","Role":"Batsman","Runs":22,"BallsFaced":17,"Fours":3,"Sixes":1,"HowOut":"c AJ Tye b Jason Behrendorff","IsPlaying":"No","StrikeRate":129.41},{"Name":"Colin Ingram","PlayerIDLive":"c_ingram","Role":"Batsman","Runs":2,"BallsFaced":6,"Fours":"","Sixes":"","HowOut":"c AJ Turner b Jason Behrendorff","IsPlaying":"No","StrikeRate":33.33},{"Name":"Matthew Short","PlayerIDLive":"m_short","Role":"AllRounder","Runs":1,"BallsFaced":6,"Fours":"","Sixes":"","HowOut":"bowled b Jhye Richardson","IsPlaying":"No","StrikeRate":16.67},{"Name":"JW Wells","PlayerIDLive":"j_wells","Role":"Batsman","Runs":2,"BallsFaced":6,"Fours":"","Sixes":"","HowOut":"c Josh Inglis b Nathan Coulter-Nile","IsPlaying":"No","StrikeRate":33.33},{"Name":"Jake Lehmann","PlayerIDLive":"j_lehmann","Role":"Batsman","Runs":"","BallsFaced":1,"Fours":"","Sixes":"","HowOut":"c Jason Behrendorff b Jhye Richardson","IsPlaying":"No","StrikeRate":""},{"Name":"Michael Neser","PlayerIDLive":"m_neser","Role":"AllRounder","Runs":1,"BallsFaced":3,"Fours":"","Sixes":"","HowOut":"lbw b Jhye Richardson","IsPlaying":"No","StrikeRate":33.33},{"Name":"Cameron Valente","PlayerIDLive":"cam_valente","Role":"AllRounder","Runs":21,"BallsFaced":18,"Fours":3,"Sixes":"","HowOut":"c Nathan Coulter-Nile b AJ Tye","IsPlaying":"No","StrikeRate":116.67},{"Name":"Rashid Khan","PlayerIDLive":"ra_khan","Role":"Bowler","Runs":21,"BallsFaced":12,"Fours":2,"Sixes":2,"HowOut":"c Nathan Coulter-Nile b David Willey","IsPlaying":"No","StrikeRate":175},{"Name":"Ben Laughlin","PlayerIDLive":"b_laughlin","Role":"Bowler","Runs":2,"BallsFaced":8,"Fours":"","Sixes":"","HowOut":"c M Klinger b AJ Tye","IsPlaying":"No","StrikeRate":25},{"Name":"Billy Stanlake","PlayerIDLive":"b_stanlake","Role":"Bowler","Runs":"","BallsFaced":1,"Fours":"","Sixes":"","HowOut":"","IsPlaying":"Yes","StrikeRate":""}],"BowlersData":[{"Name":"Colin Ingram","PlayerIDLive":"c_ingram","Overs":"1.0","Maidens":"","RunsConceded":7,"Wickets":"","NoBalls":"","Wides":"","Economy":7},{"Name":"Michael Neser","PlayerIDLive":"m_neser","Overs":"4.0","Maidens":"","RunsConceded":23,"Wickets":"","NoBalls":"","Wides":"","Economy":5.75},{"Name":"Cameron Valente","PlayerIDLive":"cam_valente","Overs":"2.0","Maidens":"","RunsConceded":16,"Wickets":1,"NoBalls":"","Wides":"","Economy":8},{"Name":"Rashid Khan","PlayerIDLive":"ra_khan","Overs":"4.0","Maidens":1,"RunsConceded":9,"Wickets":"","NoBalls":"","Wides":"","Economy":2.25},{"Name":"Ben Laughlin","PlayerIDLive":"b_laughlin","Overs":"3.0","Maidens":"","RunsConceded":17,"Wickets":"","NoBalls":"","Wides":"","Economy":5.67},{"Name":"Billy Stanlake","PlayerIDLive":"b_stanlake","Overs":"4.0","Maidens":"","RunsConceded":18,"Wickets":1,"NoBalls":"","Wides":"","Economy":4.5}],"FielderData":[{"Name":"Alex Carey","PlayerIDLive":"ale_carey","Catches":1,"RunOutThrower":"","RunOutCatcher":"","RunOutDirectHit":"","Stumping":""},{"Name":"Ben Laughlin","PlayerIDLive":"b_laughlin","Catches":"","RunOutThrower":1,"RunOutCatcher":1,"RunOutDirectHit":1,"Stumping":""}],"AllPlayingData":{"ale_carey":{"batting":{"Name":"Alex Carey","PlayerIDLive":"ale_carey","Role":"WicketKeeper","Runs":11,"BallsFaced":26,"Fours":"","Sixes":"","HowOut":"runout Josh Inglis","IsPlaying":"No","StrikeRate":42.31},"fielding":{"Name":"Alex Carey","PlayerIDLive":"ale_carey","Catches":1,"RunOutThrower":"","RunOutCatcher":"","RunOutDirectHit":"","Stumping":""}},"jak_weatherald":{"batting":{"Name":"Jake Weatherald","PlayerIDLive":"jak_weatherald","Role":"Batsman","Runs":22,"BallsFaced":17,"Fours":3,"Sixes":1,"HowOut":"c AJ Tye b Jason Behrendorff","IsPlaying":"No","StrikeRate":129.41}},"c_ingram":{"batting":{"Name":"Colin Ingram","PlayerIDLive":"c_ingram","Role":"Batsman","Runs":2,"BallsFaced":6,"Fours":"","Sixes":"","HowOut":"c AJ Turner b Jason Behrendorff","IsPlaying":"No","StrikeRate":33.33},"bowling":{"Name":"Colin Ingram","PlayerIDLive":"c_ingram","Overs":"1.0","Maidens":"","RunsConceded":7,"Wickets":"","NoBalls":"","Wides":"","Economy":7}},"m_short":{"batting":{"Name":"Matthew Short","PlayerIDLive":"m_short","Role":"AllRounder","Runs":1,"BallsFaced":6,"Fours":"","Sixes":"","HowOut":"bowled b Jhye Richardson","IsPlaying":"No","StrikeRate":16.67}},"j_wells":{"batting":{"Name":"JW Wells","PlayerIDLive":"j_wells","Role":"Batsman","Runs":2,"BallsFaced":6,"Fours":"","Sixes":"","HowOut":"c Josh Inglis b Nathan Coulter-Nile","IsPlaying":"No","StrikeRate":33.33}},"j_lehmann":{"batting":{"Name":"Jake Lehmann","PlayerIDLive":"j_lehmann","Role":"Batsman","Runs":"","BallsFaced":1,"Fours":"","Sixes":"","HowOut":"c Jason Behrendorff b Jhye Richardson","IsPlaying":"No","StrikeRate":""}},"m_neser":{"batting":{"Name":"Michael Neser","PlayerIDLive":"m_neser","Role":"AllRounder","Runs":1,"BallsFaced":3,"Fours":"","Sixes":"","HowOut":"lbw b Jhye Richardson","IsPlaying":"No","StrikeRate":33.33},"bowling":{"Name":"Michael Neser","PlayerIDLive":"m_neser","Overs":"4.0","Maidens":"","RunsConceded":23,"Wickets":"","NoBalls":"","Wides":"","Economy":5.75}},"cam_valente":{"batting":{"Name":"Cameron Valente","PlayerIDLive":"cam_valente","Role":"AllRounder","Runs":21,"BallsFaced":18,"Fours":3,"Sixes":"","HowOut":"c Nathan Coulter-Nile b AJ Tye","IsPlaying":"No","StrikeRate":116.67},"bowling":{"Name":"Cameron Valente","PlayerIDLive":"cam_valente","Overs":"2.0","Maidens":"","RunsConceded":16,"Wickets":1,"NoBalls":"","Wides":"","Economy":8}},"ra_khan":{"batting":{"Name":"Rashid Khan","PlayerIDLive":"ra_khan","Role":"Bowler","Runs":21,"BallsFaced":12,"Fours":2,"Sixes":2,"HowOut":"c Nathan Coulter-Nile b David Willey","IsPlaying":"No","StrikeRate":175},"bowling":{"Name":"Rashid Khan","PlayerIDLive":"ra_khan","Overs":"4.0","Maidens":1,"RunsConceded":9,"Wickets":"","NoBalls":"","Wides":"","Economy":2.25}},"b_laughlin":{"batting":{"Name":"Ben Laughlin","PlayerIDLive":"b_laughlin","Role":"Bowler","Runs":2,"BallsFaced":8,"Fours":"","Sixes":"","HowOut":"c M Klinger b AJ Tye","IsPlaying":"No","StrikeRate":25},"bowling":{"Name":"Ben Laughlin","PlayerIDLive":"b_laughlin","Overs":"3.0","Maidens":"","RunsConceded":17,"Wickets":"","NoBalls":"","Wides":"","Economy":5.67},"fielding":{"Name":"Ben Laughlin","PlayerIDLive":"b_laughlin","Catches":"","RunOutThrower":1,"RunOutCatcher":1,"RunOutDirectHit":1,"Stumping":""}},"b_stanlake":{"batting":{"Name":"Billy Stanlake","PlayerIDLive":"b_stanlake","Role":"Bowler","Runs":"","BallsFaced":1,"Fours":"","Sixes":"","HowOut":"","IsPlaying":"Yes","StrikeRate":""},"bowling":{"Name":"Billy Stanlake","PlayerIDLive":"b_stanlake","Overs":"4.0","Maidens":"","RunsConceded":18,"Wickets":1,"NoBalls":"","Wides":"","Economy":4.5}}},"ExtraRuns":{"Byes":5,"LegByes":5,"Wides":3,"NoBalls":0}},{"Name":"Perth Scorchers inning","ShortName":"PER inn.","Scores":"92/3","Status":"","ScoresFull":"92/3 (18.0 ov)","BatsmanData":[{"Name":"Josh Inglis","PlayerIDLive":"jo_inglis","Role":"WicketKeeper","Runs":22,"BallsFaced":21,"Fours":4,"Sixes":"","HowOut":"c Alex Carey b Billy Stanlake","IsPlaying":"No","StrikeRate":104.76},{"Name":"M Klinger","PlayerIDLive":"m_klinger","Role":"Batsman","Runs":2,"BallsFaced":7,"Fours":"","Sixes":"","HowOut":"runout Ben Laughlin","IsPlaying":"No","StrikeRate":28.57},{"Name":"William Bosisto","PlayerIDLive":"w_bosisto","Role":"Batsman","Runs":36,"BallsFaced":34,"Fours":3,"Sixes":"","HowOut":"","IsPlaying":"Yes","StrikeRate":105.88},{"Name":"AJ Turner","PlayerIDLive":"a_turner","Role":"Batsman","Runs":24,"BallsFaced":46,"Fours":2,"Sixes":"","HowOut":"bowled b Cameron Valente","IsPlaying":"No","StrikeRate":52.17},{"Name":"Hilton Cartwright","PlayerIDLive":"h_cartwright","Role":"AllRounder","Runs":1,"BallsFaced":1,"Fours":"","Sixes":"","HowOut":"","IsPlaying":"Yes","StrikeRate":100}],"BowlersData":[{"Name":"Jason Behrendorff","PlayerIDLive":"j_behrendorff","Overs":"4.0","Maidens":"","RunsConceded":16,"Wickets":2,"NoBalls":"","Wides":"","Economy":4},{"Name":"Nathan Coulter-Nile","PlayerIDLive":"c_nile","Overs":"4.0","Maidens":"","RunsConceded":33,"Wickets":1,"NoBalls":"","Wides":"","Economy":8.25},{"Name":"David Willey","PlayerIDLive":"d_willey","Overs":"4.0","Maidens":"","RunsConceded":21,"Wickets":1,"NoBalls":"","Wides":"","Economy":5.25},{"Name":"Jhye Richardson","PlayerIDLive":"j_richardson","Overs":"3.0","Maidens":1,"RunsConceded":7,"Wickets":3,"NoBalls":"","Wides":"","Economy":2.33},{"Name":"AJ Tye","PlayerIDLive":"a_tye","Overs":"2.2","Maidens":1,"RunsConceded":9,"Wickets":2,"NoBalls":"","Wides":"","Economy":3.86}],"FielderData":[{"Name":"Josh Inglis","PlayerIDLive":"jo_inglis","Catches":1,"RunOutThrower":1,"RunOutCatcher":1,"RunOutDirectHit":1,"Stumping":""},{"Name":"M Klinger","PlayerIDLive":"m_klinger","Catches":1,"RunOutThrower":"","RunOutCatcher":"","RunOutDirectHit":"","Stumping":""},{"Name":"AJ Turner","PlayerIDLive":"a_turner","Catches":1,"RunOutThrower":"","RunOutCatcher":"","RunOutDirectHit":"","Stumping":""},{"Name":"Jason Behrendorff","PlayerIDLive":"j_behrendorff","Catches":1,"RunOutThrower":"","RunOutCatcher":"","RunOutDirectHit":"","Stumping":""},{"Name":"Nathan Coulter-Nile","PlayerIDLive":"c_nile","Catches":2,"RunOutThrower":"","RunOutCatcher":"","RunOutDirectHit":"","Stumping":""},{"Name":"AJ Tye","PlayerIDLive":"a_tye","Catches":1,"RunOutThrower":"","RunOutCatcher":"","RunOutDirectHit":"","Stumping":""}],"AllPlayingData":{"jo_inglis":{"batting":{"Name":"Josh Inglis","PlayerIDLive":"jo_inglis","Role":"WicketKeeper","Runs":22,"BallsFaced":21,"Fours":4,"Sixes":"","HowOut":"c Alex Carey b Billy Stanlake","IsPlaying":"No","StrikeRate":104.76},"fielding":{"Name":"Josh Inglis","PlayerIDLive":"jo_inglis","Catches":1,"RunOutThrower":1,"RunOutCatcher":1,"RunOutDirectHit":1,"Stumping":""}},"m_klinger":{"batting":{"Name":"M Klinger","PlayerIDLive":"m_klinger","Role":"Batsman","Runs":2,"BallsFaced":7,"Fours":"","Sixes":"","HowOut":"runout Ben Laughlin","IsPlaying":"No","StrikeRate":28.57},"fielding":{"Name":"M Klinger","PlayerIDLive":"m_klinger","Catches":1,"RunOutThrower":"","RunOutCatcher":"","RunOutDirectHit":"","Stumping":""}},"w_bosisto":{"batting":{"Name":"William Bosisto","PlayerIDLive":"w_bosisto","Role":"Batsman","Runs":36,"BallsFaced":34,"Fours":3,"Sixes":"","HowOut":"","IsPlaying":"Yes","StrikeRate":105.88}},"a_turner":{"batting":{"Name":"AJ Turner","PlayerIDLive":"a_turner","Role":"Batsman","Runs":24,"BallsFaced":46,"Fours":2,"Sixes":"","HowOut":"bowled b Cameron Valente","IsPlaying":"No","StrikeRate":52.17},"fielding":{"Name":"AJ Turner","PlayerIDLive":"a_turner","Catches":1,"RunOutThrower":"","RunOutCatcher":"","RunOutDirectHit":"","Stumping":""}},"h_cartwright":{"batting":{"Name":"Hilton Cartwright","PlayerIDLive":"h_cartwright","Role":"AllRounder","Runs":1,"BallsFaced":1,"Fours":"","Sixes":"","HowOut":"","IsPlaying":"Yes","StrikeRate":100}},"j_behrendorff":{"bowling":{"Name":"Jason Behrendorff","PlayerIDLive":"j_behrendorff","Overs":"4.0","Maidens":"","RunsConceded":16,"Wickets":2,"NoBalls":"","Wides":"","Economy":4},"fielding":{"Name":"Jason Behrendorff","PlayerIDLive":"j_behrendorff","Catches":1,"RunOutThrower":"","RunOutCatcher":"","RunOutDirectHit":"","Stumping":""}},"c_nile":{"bowling":{"Name":"Nathan Coulter-Nile","PlayerIDLive":"c_nile","Overs":"4.0","Maidens":"","RunsConceded":33,"Wickets":1,"NoBalls":"","Wides":"","Economy":8.25},"fielding":{"Name":"Nathan Coulter-Nile","PlayerIDLive":"c_nile","Catches":2,"RunOutThrower":"","RunOutCatcher":"","RunOutDirectHit":"","Stumping":""}},"d_willey":{"bowling":{"Name":"David Willey","PlayerIDLive":"d_willey","Overs":"4.0","Maidens":"","RunsConceded":21,"Wickets":1,"NoBalls":"","Wides":"","Economy":5.25}},"j_richardson":{"bowling":{"Name":"Jhye Richardson","PlayerIDLive":"j_richardson","Overs":"3.0","Maidens":1,"RunsConceded":7,"Wickets":3,"NoBalls":"","Wides":"","Economy":2.33}},"a_tye":{"bowling":{"Name":"AJ Tye","PlayerIDLive":"a_tye","Overs":"2.2","Maidens":1,"RunsConceded":9,"Wickets":2,"NoBalls":"","Wides":"","Economy":3.86},"fielding":{"Name":"AJ Tye","PlayerIDLive":"a_tye","Catches":1,"RunOutThrower":"","RunOutCatcher":"","RunOutDirectHit":"","Stumping":""}}},"ExtraRuns":{"Byes":7,"LegByes":7,"Wides":4,"NoBalls":1}}]
             */


            @SerializedName("Innings")
            private List<InningsBean> Innings;


            public List<InningsBean> getInnings() {
                return Innings;
            }

            public void setInnings(List<InningsBean> Innings) {
                this.Innings = Innings;
            }



            public static class InningsBean {
                /**
                 * Name : Adelaide Strikers inning
                 * ShortName : ADS inn.
                 * Scores : 88/10
                 * Status :
                 * ScoresFull : 88/10 (17.2 ov)
                 * BatsmanData : [{"Name":"Alex Carey","PlayerIDLive":"ale_carey","Role":"WicketKeeper","Runs":11,"BallsFaced":26,"Fours":"","Sixes":"","HowOut":"runout Josh Inglis","IsPlaying":"No","StrikeRate":42.31},{"Name":"Jake Weatherald","PlayerIDLive":"jak_weatherald","Role":"Batsman","Runs":22,"BallsFaced":17,"Fours":3,"Sixes":1,"HowOut":"c AJ Tye b Jason Behrendorff","IsPlaying":"No","StrikeRate":129.41},{"Name":"Colin Ingram","PlayerIDLive":"c_ingram","Role":"Batsman","Runs":2,"BallsFaced":6,"Fours":"","Sixes":"","HowOut":"c AJ Turner b Jason Behrendorff","IsPlaying":"No","StrikeRate":33.33},{"Name":"Matthew Short","PlayerIDLive":"m_short","Role":"AllRounder","Runs":1,"BallsFaced":6,"Fours":"","Sixes":"","HowOut":"bowled b Jhye Richardson","IsPlaying":"No","StrikeRate":16.67},{"Name":"JW Wells","PlayerIDLive":"j_wells","Role":"Batsman","Runs":2,"BallsFaced":6,"Fours":"","Sixes":"","HowOut":"c Josh Inglis b Nathan Coulter-Nile","IsPlaying":"No","StrikeRate":33.33},{"Name":"Jake Lehmann","PlayerIDLive":"j_lehmann","Role":"Batsman","Runs":"","BallsFaced":1,"Fours":"","Sixes":"","HowOut":"c Jason Behrendorff b Jhye Richardson","IsPlaying":"No","StrikeRate":""},{"Name":"Michael Neser","PlayerIDLive":"m_neser","Role":"AllRounder","Runs":1,"BallsFaced":3,"Fours":"","Sixes":"","HowOut":"lbw b Jhye Richardson","IsPlaying":"No","StrikeRate":33.33},{"Name":"Cameron Valente","PlayerIDLive":"cam_valente","Role":"AllRounder","Runs":21,"BallsFaced":18,"Fours":3,"Sixes":"","HowOut":"c Nathan Coulter-Nile b AJ Tye","IsPlaying":"No","StrikeRate":116.67},{"Name":"Rashid Khan","PlayerIDLive":"ra_khan","Role":"Bowler","Runs":21,"BallsFaced":12,"Fours":2,"Sixes":2,"HowOut":"c Nathan Coulter-Nile b David Willey","IsPlaying":"No","StrikeRate":175},{"Name":"Ben Laughlin","PlayerIDLive":"b_laughlin","Role":"Bowler","Runs":2,"BallsFaced":8,"Fours":"","Sixes":"","HowOut":"c M Klinger b AJ Tye","IsPlaying":"No","StrikeRate":25},{"Name":"Billy Stanlake","PlayerIDLive":"b_stanlake","Role":"Bowler","Runs":"","BallsFaced":1,"Fours":"","Sixes":"","HowOut":"","IsPlaying":"Yes","StrikeRate":""}]
                 * BowlersData : [{"Name":"Colin Ingram","PlayerIDLive":"c_ingram","Overs":"1.0","Maidens":"","RunsConceded":7,"Wickets":"","NoBalls":"","Wides":"","Economy":7},{"Name":"Michael Neser","PlayerIDLive":"m_neser","Overs":"4.0","Maidens":"","RunsConceded":23,"Wickets":"","NoBalls":"","Wides":"","Economy":5.75},{"Name":"Cameron Valente","PlayerIDLive":"cam_valente","Overs":"2.0","Maidens":"","RunsConceded":16,"Wickets":1,"NoBalls":"","Wides":"","Economy":8},{"Name":"Rashid Khan","PlayerIDLive":"ra_khan","Overs":"4.0","Maidens":1,"RunsConceded":9,"Wickets":"","NoBalls":"","Wides":"","Economy":2.25},{"Name":"Ben Laughlin","PlayerIDLive":"b_laughlin","Overs":"3.0","Maidens":"","RunsConceded":17,"Wickets":"","NoBalls":"","Wides":"","Economy":5.67},{"Name":"Billy Stanlake","PlayerIDLive":"b_stanlake","Overs":"4.0","Maidens":"","RunsConceded":18,"Wickets":1,"NoBalls":"","Wides":"","Economy":4.5}]
                 * FielderData : [{"Name":"Alex Carey","PlayerIDLive":"ale_carey","Catches":1,"RunOutThrower":"","RunOutCatcher":"","RunOutDirectHit":"","Stumping":""},{"Name":"Ben Laughlin","PlayerIDLive":"b_laughlin","Catches":"","RunOutThrower":1,"RunOutCatcher":1,"RunOutDirectHit":1,"Stumping":""}]
                 * AllPlayingData : {"ale_carey":{"batting":{"Name":"Alex Carey","PlayerIDLive":"ale_carey","Role":"WicketKeeper","Runs":11,"BallsFaced":26,"Fours":"","Sixes":"","HowOut":"runout Josh Inglis","IsPlaying":"No","StrikeRate":42.31},"fielding":{"Name":"Alex Carey","PlayerIDLive":"ale_carey","Catches":1,"RunOutThrower":"","RunOutCatcher":"","RunOutDirectHit":"","Stumping":""}},"jak_weatherald":{"batting":{"Name":"Jake Weatherald","PlayerIDLive":"jak_weatherald","Role":"Batsman","Runs":22,"BallsFaced":17,"Fours":3,"Sixes":1,"HowOut":"c AJ Tye b Jason Behrendorff","IsPlaying":"No","StrikeRate":129.41}},"c_ingram":{"batting":{"Name":"Colin Ingram","PlayerIDLive":"c_ingram","Role":"Batsman","Runs":2,"BallsFaced":6,"Fours":"","Sixes":"","HowOut":"c AJ Turner b Jason Behrendorff","IsPlaying":"No","StrikeRate":33.33},"bowling":{"Name":"Colin Ingram","PlayerIDLive":"c_ingram","Overs":"1.0","Maidens":"","RunsConceded":7,"Wickets":"","NoBalls":"","Wides":"","Economy":7}},"m_short":{"batting":{"Name":"Matthew Short","PlayerIDLive":"m_short","Role":"AllRounder","Runs":1,"BallsFaced":6,"Fours":"","Sixes":"","HowOut":"bowled b Jhye Richardson","IsPlaying":"No","StrikeRate":16.67}},"j_wells":{"batting":{"Name":"JW Wells","PlayerIDLive":"j_wells","Role":"Batsman","Runs":2,"BallsFaced":6,"Fours":"","Sixes":"","HowOut":"c Josh Inglis b Nathan Coulter-Nile","IsPlaying":"No","StrikeRate":33.33}},"j_lehmann":{"batting":{"Name":"Jake Lehmann","PlayerIDLive":"j_lehmann","Role":"Batsman","Runs":"","BallsFaced":1,"Fours":"","Sixes":"","HowOut":"c Jason Behrendorff b Jhye Richardson","IsPlaying":"No","StrikeRate":""}},"m_neser":{"batting":{"Name":"Michael Neser","PlayerIDLive":"m_neser","Role":"AllRounder","Runs":1,"BallsFaced":3,"Fours":"","Sixes":"","HowOut":"lbw b Jhye Richardson","IsPlaying":"No","StrikeRate":33.33},"bowling":{"Name":"Michael Neser","PlayerIDLive":"m_neser","Overs":"4.0","Maidens":"","RunsConceded":23,"Wickets":"","NoBalls":"","Wides":"","Economy":5.75}},"cam_valente":{"batting":{"Name":"Cameron Valente","PlayerIDLive":"cam_valente","Role":"AllRounder","Runs":21,"BallsFaced":18,"Fours":3,"Sixes":"","HowOut":"c Nathan Coulter-Nile b AJ Tye","IsPlaying":"No","StrikeRate":116.67},"bowling":{"Name":"Cameron Valente","PlayerIDLive":"cam_valente","Overs":"2.0","Maidens":"","RunsConceded":16,"Wickets":1,"NoBalls":"","Wides":"","Economy":8}},"ra_khan":{"batting":{"Name":"Rashid Khan","PlayerIDLive":"ra_khan","Role":"Bowler","Runs":21,"BallsFaced":12,"Fours":2,"Sixes":2,"HowOut":"c Nathan Coulter-Nile b David Willey","IsPlaying":"No","StrikeRate":175},"bowling":{"Name":"Rashid Khan","PlayerIDLive":"ra_khan","Overs":"4.0","Maidens":1,"RunsConceded":9,"Wickets":"","NoBalls":"","Wides":"","Economy":2.25}},"b_laughlin":{"batting":{"Name":"Ben Laughlin","PlayerIDLive":"b_laughlin","Role":"Bowler","Runs":2,"BallsFaced":8,"Fours":"","Sixes":"","HowOut":"c M Klinger b AJ Tye","IsPlaying":"No","StrikeRate":25},"bowling":{"Name":"Ben Laughlin","PlayerIDLive":"b_laughlin","Overs":"3.0","Maidens":"","RunsConceded":17,"Wickets":"","NoBalls":"","Wides":"","Economy":5.67},"fielding":{"Name":"Ben Laughlin","PlayerIDLive":"b_laughlin","Catches":"","RunOutThrower":1,"RunOutCatcher":1,"RunOutDirectHit":1,"Stumping":""}},"b_stanlake":{"batting":{"Name":"Billy Stanlake","PlayerIDLive":"b_stanlake","Role":"Bowler","Runs":"","BallsFaced":1,"Fours":"","Sixes":"","HowOut":"","IsPlaying":"Yes","StrikeRate":""},"bowling":{"Name":"Billy Stanlake","PlayerIDLive":"b_stanlake","Overs":"4.0","Maidens":"","RunsConceded":18,"Wickets":1,"NoBalls":"","Wides":"","Economy":4.5}}}
                 * ExtraRuns : {"Byes":5,"LegByes":5,"Wides":3,"NoBalls":0}
                 */


                @SerializedName("ShortName")
                private String ShortName;
                @SerializedName("Scores")
                private String Scores;
                @SerializedName("ScoresFull")
                private String ScoresFull;




                public String getShortName() {
                    return ShortName;
                }

                public void setShortName(String ShortName) {
                    this.ShortName = ShortName;
                }

                public String getScores() {
                    return Scores;
                }

                public void setScores(String Scores) {
                    this.Scores = Scores;
                }


                public String getScoresFull() {
                    return ScoresFull;
                }

                public void setScoresFull(String scoresFull) {
                    ScoresFull = scoresFull;
                }
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
