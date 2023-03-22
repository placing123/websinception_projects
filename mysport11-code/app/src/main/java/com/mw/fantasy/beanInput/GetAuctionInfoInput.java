package com.mw.fantasy.beanInput;

public class GetAuctionInfoInput {

    /**
     * ContestGUID : b1c70bd2-6e68-f285-5139-b8c730b22c09
     * DraftSeriesType : Yes
     * Params : SeriesName,LeagueJoinDateTime,Status,AuctionStatus,LeagueJoinDateTimeUTC,DraftTeamPlayerLimit,DraftPlayerSelectionCriteria,CustomizeWinning,TotalJoined,ContestSize,NoOfWinners,WinningAmount,EntryFee,UserInvitationCode
     * RoundID : 11
     * SessionKey : 34a606a4-5bc6-639d-771c-6bc2ff913476
     * IsSeriesStarted : Yes
     */

    private String ContestGUID;
    private String DraftSeriesType;
    private String Params;
    private String RoundID;
    private String MatchGUID;
    private String SessionKey;
    private String IsSeriesStarted;


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

    public String getDraftSeriesType() {
        return DraftSeriesType;
    }

    public void setDraftSeriesType(String DraftSeriesType) {
        this.DraftSeriesType = DraftSeriesType;
    }

    public String getParams() {
        return Params;
    }

    public void setParams(String Params) {
        this.Params = Params;
    }

    public String getRoundID() {
        return RoundID;
    }

    public void setRoundID(String RoundID) {
        this.RoundID = RoundID;
    }

    public String getSessionKey() {
        return SessionKey;
    }

    public void setSessionKey(String SessionKey) {
        this.SessionKey = SessionKey;
    }

    public String getIsSeriesStarted() {
        return IsSeriesStarted;
    }

    public void setIsSeriesStarted(String IsSeriesStarted) {
        this.IsSeriesStarted = IsSeriesStarted;
    }
}
