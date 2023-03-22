package com.mw.fantasy.beanInput;

import java.util.List;

public class SubmitAuctionsPlayersInput {

    /**
     * SessionKey : 28644d01-c3d0-1257-e8f1-e58d90384102
     * SeriesGUID : 4321d2c5-5ccc-bd73-a6d5-4d6a2132d2e6
     * UserTeamGUID : 7bc8764d-d061-6e03-b0b1-8310af130549
     * UserTeamPlayers : [{"PlayerGUID":"b3cf4f55-b796-fc35-afe8-e86574486052","PlayerName":"Jason Holder","PlayerPosition":"Captain","PlayerID":"353345","BidCredit":"100000"},{"PlayerGUID":"1bda0194-0bfd-881c-f6df-d74e325741bf","PlayerName":"Mujeeb Ur Rahman","PlayerPosition":"ViceCaptain","PlayerID":"353460","BidCredit":"100000"}]
     */

    private String SessionKey;
    private String RoundID;
    private String MatchGUID;
    private String SeriesID;
    private String UserTeamGUID;
    private List<UserTeamPlayersBean> UserTeamPlayers;


    public String getMatchGUID() {
        return MatchGUID;
    }

    public void setMatchGUID(String matchGUID) {
        MatchGUID = matchGUID;
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

    public String getRoundID() {
        return RoundID;
    }

    public void setRoundID(String roundID) {
        RoundID = roundID;
    }

    /* public String getSeriesGUID() {
        return SeriesGUID;
    }

    public void setSeriesGUID(String SeriesGUID) {
        this.SeriesGUID = SeriesGUID;
    }*/

    public String getUserTeamGUID() {
        return UserTeamGUID;
    }

    public void setUserTeamGUID(String UserTeamGUID) {
        this.UserTeamGUID = UserTeamGUID;
    }

    public List<UserTeamPlayersBean> getUserTeamPlayers() {
        return UserTeamPlayers;
    }

    public void setUserTeamPlayers(List<UserTeamPlayersBean> UserTeamPlayers) {
        this.UserTeamPlayers = UserTeamPlayers;
    }

    public static class UserTeamPlayersBean {
        /**
         * PlayerGUID : b3cf4f55-b796-fc35-afe8-e86574486052
         * PlayerName : Jason Holder
         * PlayerPosition : Captain
         * PlayerID : 353345
         * BidCredit : 100000
         */

        private String PlayerGUID;
        private String PlayerName;
        private String PlayerPosition;
        private String PlayerID;
        private String BidCredit;

        public String getPlayerGUID() {
            return PlayerGUID;
        }

        public void setPlayerGUID(String PlayerGUID) {
            this.PlayerGUID = PlayerGUID;
        }

        public String getPlayerName() {
            return PlayerName;
        }

        public void setPlayerName(String PlayerName) {
            this.PlayerName = PlayerName;
        }

        public String getPlayerPosition() {
            return PlayerPosition;
        }

        public void setPlayerPosition(String PlayerPosition) {
            this.PlayerPosition = PlayerPosition;
        }

        public String getPlayerID() {
            return PlayerID;
        }

        public void setPlayerID(String PlayerID) {
            this.PlayerID = PlayerID;
        }

        public String getBidCredit() {
            return BidCredit;
        }

        public void setBidCredit(String BidCredit) {
            this.BidCredit = BidCredit;
        }
    }
}
