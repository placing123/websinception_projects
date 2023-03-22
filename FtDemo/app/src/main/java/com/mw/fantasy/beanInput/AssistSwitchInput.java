package com.mw.fantasy.beanInput;

public class AssistSwitchInput {

    /**
     * SeriesGUID : db856266-3f6a-bef6-239c-3f67f3e87019
     * SessionKey : 10036b4e-0832-4875-b2b3-356e4c4be633
     * ContestGUID : bccb540c-cc00-7c6b-866e-22c6594b3ea6
     * UserTeamGUID : a1fddeaa-5f06-4711-3a5e-9ccf24fd3514
     * IsAssistant : Yes
     */

    /*private String SeriesGUID;*/
    private String SessionKey;
    private String RoundID;
    private String MatchGUID;
    private String ContestGUID;
    private String UserTeamGUID;
    private String IsAssistant;

    public String getMatchGUID() {
        return MatchGUID;
    }

    public void setMatchGUID(String matchGUID) {
        MatchGUID = matchGUID;
    }


    /* public String getSeriesGUID() {
        return SeriesGUID;
    }

    public void setSeriesGUID(String SeriesGUID) {
        this.SeriesGUID = SeriesGUID;
    }*/

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

    public String getContestGUID() {
        return ContestGUID;
    }

    public void setContestGUID(String ContestGUID) {
        this.ContestGUID = ContestGUID;
    }

    public String getUserTeamGUID() {
        return UserTeamGUID;
    }

    public void setUserTeamGUID(String UserTeamGUID) {
        this.UserTeamGUID = UserTeamGUID;
    }

    public String getIsAssistant() {
        return IsAssistant;
    }

    public void setIsAssistant(String IsAssistant) {
        this.IsAssistant = IsAssistant;
    }
}
