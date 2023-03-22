package com.websinception.megastar.beanInput;

public class GetAuctionJoinedUserInput {

    /**
     * SeriesGUID : db856266-3f6a-bef6-239c-3f67f3e87019
     * SessionKey : 10036b4e-0832-4875-b2b3-356e4c4be633
     * ContestGUID : bccb540c-cc00-7c6b-866e-22c6594b3ea6
     * Params : FirstName,Username,UserGUID,ProfilePic,AuctionTimeBank,AuctionBudget,AuctionUserStatus
     */

    /*private String SeriesGUID;*/

    private String MatchGUID;
    private String RoundID;
    private String SeriesID;
    private String SessionKey;
    private String ContestGUID;
    private String Params;

   /* public String getSeriesGUID() {
        return SeriesGUID;
    }

    public void setSeriesGUID(String SeriesGUID) {
        this.SeriesGUID = SeriesGUID;
    }*/


    public String getMatchGUID() {
        return MatchGUID;
    }

    public void setMatchGUID(String matchGUID) {
        MatchGUID = matchGUID;
    }

    public String getRoundID() {
        return RoundID;
    }

    public void setRoundID(String roundID) {
        RoundID = roundID;
    }

    public String getSeriesID() {
        return SeriesID;
    }

    public void setSeriesID(String seriesID) {
        SeriesID = seriesID;
    }

    public String getSessionKey() {
        return SessionKey;
    }

    public void setSessionKey(String SessionKey) {
        this.SessionKey = SessionKey;
    }

    public String getContestGUID() {
        return ContestGUID;
    }

    public void setContestGUID(String ContestGUID) {
        this.ContestGUID = ContestGUID;
    }

    public String getParams() {
        return Params;
    }

    public void setParams(String Params) {
        this.Params = Params;
    }
}
