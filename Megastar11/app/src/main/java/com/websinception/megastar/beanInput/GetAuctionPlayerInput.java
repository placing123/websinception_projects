package com.websinception.megastar.beanInput;

public class GetAuctionPlayerInput {

    /**
     * SeriesGUID : db856266-3f6a-bef6-239c-3f67f3e87019
     * SessionKey : 10036b4e-0832-4875-b2b3-356e4c4be633
     * ContestGUID : 01f8b7a9-dea1-3ba0-f446-3e16d8d1aed3
     * Params : BidSoldCredit,PlayerStatus,PlayerID,PlayerRole,PlayerPic,PlayerCountry,PlayerBornPlace,PlayerBattingStyle,PlayerBowlingStyle,MatchType,MatchNo,MatchDateTime,SeriesName,TeamGUID,PlayerBattingStats,PlayerBowlingStats,IsPlaying,PointsData
     */

    private String MatchGUID;
    private String RoundID;
    private String SessionKey;
    private String ContestGUID;
    private String Params;
    private String MySquadPlayer;
    private String IsAssistant;
    private String UserGUID;
    private String PlayerBidStatus;
    private String AuctionPlayingPlayer;
    private String SeriesGUID;
    private String OrderBy;
    private String Sequence;


    public String getMatchGUID() {
        return MatchGUID;
    }

    public void setMatchGUID(String matchGUID) {
        MatchGUID = matchGUID;
    }

    public String getAuctionPlayingPlayer() {
        return AuctionPlayingPlayer;
    }

    public void setAuctionPlayingPlayer(String auctionPlayingPlayer) {
        AuctionPlayingPlayer = auctionPlayingPlayer;
    }

    public String getRoundID() {
        return RoundID;
    }

    public void setRoundID(String roundID) {
        RoundID = roundID;
    }

    public String getOrderBy() {
        return OrderBy;
    }

    public void setOrderBy(String orderBy) {
        OrderBy = orderBy;
    }

    public String getSequence() {
        return Sequence;
    }

    public void setSequence(String sequence) {
        Sequence = sequence;
    }

    public String getMySquadPlayer() {
        return MySquadPlayer;
    }

    public void setMySquadPlayer(String mySquadPlayer) {
        MySquadPlayer = mySquadPlayer;
    }

    public String getIsAssistant() {
        return IsAssistant;
    }

    public void setIsAssistant(String isAssistant) {
        IsAssistant = isAssistant;
    }

    public String getIsPreTeam() {
        return IsPreTeam;
    }

    public void setIsPreTeam(String isPreTeam) {
        IsPreTeam = isPreTeam;
    }

    private String IsPreTeam;

    /*public String getSeriesGUID() {
        return SeriesGUID;
    }

    public void setSeriesGUID(String SeriesGUID) {
        this.SeriesGUID = SeriesGUID;
    }*/

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

    public String getUserGUID() {
        return UserGUID;
    }

    public void setUserGUID(String userGUID) {
        UserGUID = userGUID;
    }

    public String getPlayerBidStatus() {
        return PlayerBidStatus;
    }

    public void setPlayerBidStatus(String playerBidStatus) {
        PlayerBidStatus = playerBidStatus;
    }

    public String getSeriesGUID() {
        return SeriesGUID;
    }

    public void setSeriesGUID(String seriesGUID) {
        SeriesGUID = seriesGUID;
    }
}
