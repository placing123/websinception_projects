package com.mw.fantasy.beanInput;

public class GetAuctionSeriesInput {

    /**
     * StatusID : 2
     * Params : SeriesName,SeriesGUID,StatusID,SeriesStartDate,Status,SeriesID
     * OrderBy : SeriesStartDate
     * Sequence : ASC
     * DraftAuctionPlay : Yes
     */

    private String SessionKey;

    public String getSessionKey() {
        return SessionKey;
    }

    public void setSessionKey(String sessionKey) {
        SessionKey = sessionKey;
    }

    private String  Status;
    private String  MyJoinedSeries;
    private String Params;
    private String OrderBy;
    private String Sequence;
    private String DraftAuctionPlay;
    private String IsPlayRounds;
    private String IsPlayerOrTimeAvl;
    private String Filter;


    public String getFilter() {
        return Filter;
    }

    public void setFilter(String filter) {
        Filter = filter;
    }

    public String getIsPlayerOrTimeAvl() {
        return IsPlayerOrTimeAvl;
    }

    public void setIsPlayerOrTimeAvl(String isPlayerOrTimeAvl) {
        IsPlayerOrTimeAvl = isPlayerOrTimeAvl;
    }

    public String getIsPlayRounds() {
        return IsPlayRounds;
    }

    public void setIsPlayRounds(String isPlayRounds) {
        IsPlayRounds = isPlayRounds;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMyJoinedSeries() {
        return MyJoinedSeries;
    }

    public void setMyJoinedSeries(String myJoinedSeries) {
        MyJoinedSeries = myJoinedSeries;
    }

    public String getParams() {
        return Params;
    }

    public void setParams(String Params) {
        this.Params = Params;
    }

    public String getOrderBy() {
        return OrderBy;
    }

    public void setOrderBy(String OrderBy) {
        this.OrderBy = OrderBy;
    }

    public String getSequence() {
        return Sequence;
    }

    public void setSequence(String Sequence) {
        this.Sequence = Sequence;
    }

    public String getDraftAuctionPlay() {
        return DraftAuctionPlay;
    }

    public void setDraftAuctionPlay(String DraftAuctionPlay) {
        this.DraftAuctionPlay = DraftAuctionPlay;
    }
}
