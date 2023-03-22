package com.websinception.megastar.beanInput;

public class GetSeriesAuctionContestByTypeInput {

    /**
     * ContestList : Yes
     * Filter : Normal
     * OrderBy : TotalJoined
     * PageNo : 1
     * PageSize : 3
     * Params : Privacy,TotalJoined,IsPaid,StatusID,WinningAmount,ContestSize,EntryFee,NoOfWinners,EntryType,IsJoined,Status,ContestFormat,ContestType,CustomizeWinning,TotalJoined,UserInvitationCode,TeamNameLocal,TeamNameVisitor,IsConfirm,CashBonusContribution,TeamNameShortLocal,TeamNameShortVisitor,MatchGUID,CashBonusContribution,IsPrivacyNameDisplay
     * Privacy : No
     * Sequence : DESC
     * SessionKey : 31b6c467-9dcc-1ef5-b1c7-d1df14996225
     * Status : Pending
     * RoundID : 13
     */

    private String ContestList;
    private String Filter;
    private String OrderBy;
    private int PageNo;
    private int PageSize;
    private String Params;
    private String Privacy;
    private String Sequence;
    private String SessionKey;
    private String Status;
    private String RoundID;
    private String MatchGUID;
    private String TotalJoinedByRound;
    private String ContestFull;
    private String IsAssistanceCreated;

    private String AuctionStatus;


    public String getIsAssistanceCreated() {
        return IsAssistanceCreated;
    }

    public void setIsAssistanceCreated(String isAssistanceCreated) {
        IsAssistanceCreated = isAssistanceCreated;
    }

    public String getContestFull() {
        return ContestFull;
    }

    public void setContestFull(String contestFull) {
        ContestFull = contestFull;
    }

    public String getMatchGUID() {
        return MatchGUID;
    }

    public void setMatchGUID(String matchGUID) {
        this.MatchGUID = matchGUID;
    }

    public String getAuctionStatus() {
        return AuctionStatus;
    }

    public void setAuctionStatus(String auctionStatus) {
        AuctionStatus = auctionStatus;
    }

    public String getTotalJoinedByRound() {
        return TotalJoinedByRound;
    }

    public void setTotalJoinedByRound(String totalJoinedByRound) {
        TotalJoinedByRound = totalJoinedByRound;
    }

    public String getContestList() {
        return ContestList;
    }

    public void setContestList(String ContestList) {
        this.ContestList = ContestList;
    }

    public String getFilter() {
        return Filter;
    }

    public void setFilter(String Filter) {
        this.Filter = Filter;
    }

    public String getOrderBy() {
        return OrderBy;
    }

    public void setOrderBy(String OrderBy) {
        this.OrderBy = OrderBy;
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

    public String getParams() {
        return Params;
    }

    public void setParams(String Params) {
        this.Params = Params;
    }

    public String getPrivacy() {
        return Privacy;
    }

    public void setPrivacy(String Privacy) {
        this.Privacy = Privacy;
    }

    public String getSequence() {
        return Sequence;
    }

    public void setSequence(String Sequence) {
        this.Sequence = Sequence;
    }

    public String getSessionKey() {
        return SessionKey;
    }

    public void setSessionKey(String SessionKey) {
        this.SessionKey = SessionKey;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getRoundID() {
        return RoundID;
    }

    public void setRoundID(String RoundID) {
        this.RoundID = RoundID;
    }
}
