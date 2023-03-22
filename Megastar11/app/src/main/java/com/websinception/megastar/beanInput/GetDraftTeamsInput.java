package com.websinception.megastar.beanInput;

public class GetDraftTeamsInput {

    /**
     * SeriesGUID : db856266-3f6a-bef6-239c-3f67f3e87019
     * SessionKey : 10036b4e-0832-4875-b2b3-356e4c4be633
     */


    private String RoundID;
    private String MatchGUID;
    private String SessionKey;


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

    public String getSessionKey() {
        return SessionKey;
    }

    public void setSessionKey(String SessionKey) {
        this.SessionKey = SessionKey;
    }
}

