package com.websinception.megastar.beanInput;

import com.google.gson.annotations.SerializedName;

public class JoinAuctionInput {

    @SerializedName("ContestGUID")
    private String ContestGUID;

   /* @SerializedName("SeriesGUID")
    private String SeriesGUID;*/


    @SerializedName("SessionKey")
    private String SessionKey;

    @SerializedName("UserInvitationCode")
    private String UserInvitationCode;


    @SerializedName("SeriesID")
    private String SeriesID;

    @SerializedName("RoundID")
    private String RoundID;

    @SerializedName("MatchGUID")
    private String matchGUID;

    private String join;

    public String getMatchGUID() {
        return matchGUID;
    }

    public void setMatchGUID(String matchGUID) {
        this.matchGUID = matchGUID;
    }

    public String getContestGUID() {
        return ContestGUID;
    }

    public void setContestGUID(String contestGUID) {
        ContestGUID = contestGUID;
    }

 /*   public String getSeriesGUID() {
        return SeriesGUID;
    }

    public void setSeriesGUID(String seriesGUID) {
        SeriesGUID = seriesGUID;
    }*/

    public String getSessionKey() {
        return SessionKey;
    }

    public void setSessionKey(String sessionKey) {
        SessionKey = sessionKey;
    }

    public String getUserInvitationCode() {
        return UserInvitationCode;
    }

    public void setUserInvitationCode(String userInvitationCode) {
        UserInvitationCode = userInvitationCode;
    }


    public String getSeriesID() {
        return SeriesID;
    }

    public void setSeriesID(String seriesID) {
        SeriesID = seriesID;
    }

    public String getRoundID() {
        return RoundID;
    }

    public void setRoundID(String roundID) {
        RoundID = roundID;
    }

    public String getJoin() {
        return join;
    }

    public void setJoin(String join) {
        this.join = join;
    }
}
