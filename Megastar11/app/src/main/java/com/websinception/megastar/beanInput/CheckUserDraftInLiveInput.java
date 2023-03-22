package com.websinception.megastar.beanInput;

import com.google.gson.annotations.SerializedName;

public class CheckUserDraftInLiveInput {

    @SerializedName("RoundID")
    private String RoundID;

    @SerializedName("MatchGUID")
    private String MatchGUID;


    @SerializedName("ContestGUID")
    private String ContestGUID;

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

    public String getContestGUID() {
        return ContestGUID;
    }

    public void setContestGUID(String contestGUID) {
        ContestGUID = contestGUID;
    }
}
