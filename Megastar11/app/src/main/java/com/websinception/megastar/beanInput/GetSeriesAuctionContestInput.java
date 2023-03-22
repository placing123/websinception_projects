package com.websinception.megastar.beanInput;

public class GetSeriesAuctionContestInput {

    /**
     * SessionKey : 75f0213f-00dd-82d0-77b4-0422240a54b3
     * SeriesGUID : 4321d2c5-5ccc-bd73-a6d5-4d6a2132d2e6
     * Params : ContestID,LeagueJoinDateTime,GameType,Privacy,IsPaid,WinningAmount,ContestSize,EntryFee,NoOfWinners,EntryType,Status,TotalJoined,CustomizeWinning,CashBonusContribution
     * PageNo : 1
     * PageSize : 15
     * Keyword :
     * JoinedContestStatusID : Yes
     * MyJoinedContest : Yes
     * Privacy : All
     * LeagueType : Auction
     */

    private String SessionKey;
    /*private String SeriesGUID;*/
    private String RoundID;
    private String MatchGUID;
    private String Params;
    private int PageNo;
    private int PageSize;
    private String Keyword;
    private String JoinedContestStatusID;
    private String MyJoinedContest;
    private String Privacy;
    private String LeagueType;
    private String ContestFull;
    private String Status;
    private String StatusID;
    private String AuctionStatus;
    private String IsSeriesStarted;
    private String TotalJoinedByRound;
    private String ContestType;
    private String IsAssistanceCreated;


    public String getIsAssistanceCreated() {
        return IsAssistanceCreated;
    }

    public void setIsAssistanceCreated(String isAssistanceCreated) {
        IsAssistanceCreated = isAssistanceCreated;
    }

    private String MyStats;

    public String getMatchGUID() {
        return MatchGUID;
    }

    public void setMatchGUID(String matchGUID) {
        MatchGUID = matchGUID;
    }

    public String getMyStats() {
        return MyStats;
    }

    public void setMyStats(String myStats) {
        MyStats = myStats;
    }

    public String getContestType() {
        return ContestType;
    }

    public void setContestType(String contestType) {
        ContestType = contestType;
    }

    public String getTotalJoinedByRound() {
        return TotalJoinedByRound;
    }

    public void setTotalJoinedByRound(String totalJoinedByRound) {
        TotalJoinedByRound = totalJoinedByRound;
    }

    public String getRoundID() {
        return RoundID;
    }

    public void setRoundID(String roundID) {
        RoundID = roundID;
    }

    public String getIsSeriesStarted() {
        return IsSeriesStarted;
    }

    public void setIsSeriesStarted(String isSeriesStarted) {
        IsSeriesStarted = isSeriesStarted;
    }

    public String getSessionKey() {
        return SessionKey;
    }

    public void setSessionKey(String SessionKey) {
        this.SessionKey = SessionKey;
    }

  /*  public String getSeriesGUID() {
        return SeriesGUID;
    }

    public void setSeriesGUID(String SeriesGUID) {
        this.SeriesGUID = SeriesGUID;
    }*/

    public String getParams() {
        return Params;
    }

    public void setParams(String Params) {
        this.Params = Params;
    }

    public int getPageNo() {
        return PageNo;
    }

    public void setPageNo(int PageNo) {
        this.PageNo = PageNo;
    }

    public int getPageSize() {
        return PageSize;
    }

    public void setPageSize(int PageSize) {
        this.PageSize = PageSize;
    }

    public String getKeyword() {
        return Keyword;
    }

    public void setKeyword(String Keyword) {
        this.Keyword = Keyword;
    }

    public String getJoinedContestStatusID() {
        return JoinedContestStatusID;
    }

    public void setJoinedContestStatusID(String JoinedContestStatusID) {
        this.JoinedContestStatusID = JoinedContestStatusID;
    }

    public String getMyJoinedContest() {
        return MyJoinedContest;
    }

    public void setMyJoinedContest(String MyJoinedContest) {
        this.MyJoinedContest = MyJoinedContest;
    }

    public String getPrivacy() {
        return Privacy;
    }

    public void setPrivacy(String Privacy) {
        this.Privacy = Privacy;
    }

    public String getLeagueType() {
        return LeagueType;
    }

    public void setLeagueType(String LeagueType) {
        this.LeagueType = LeagueType;
    }

    public String getContestFull() {
        return ContestFull;
    }

    public void setContestFull(String contestFull) {
        ContestFull = contestFull;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getStatusID() {
        return StatusID;
    }

    public void setStatusID(String statusID) {
        StatusID = statusID;
    }

    public String getAuctionStatus() {
        return AuctionStatus;
    }

    public void setAuctionStatus(String auctionStatus) {
        AuctionStatus = auctionStatus;
    }
}
