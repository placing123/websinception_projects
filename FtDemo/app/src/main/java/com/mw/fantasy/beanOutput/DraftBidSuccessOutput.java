package com.mw.fantasy.beanOutput;

public class DraftBidSuccessOutput {


    /**
     * ContestGUID : efdccd68-c51f-5d83-4ae7-a139bf159479
     * responseData : {"ResponseCode":200,"Message":"Successfully player added","Data":{"Status":1,"Player":{"PlayerRole":"Batsman","PlayerStatus":"Upcoming","PlayerID":"353906","PlayerName":"David Miller"},"User":{"FirstName":"Rahul pareta","UserGUID":"d4f418ea-135e-dca0-5c07-c013c49c919d","UserID":"373319"},"DraftStatus":"Running","SeriesGUID":"4321d2c5-5ccc-bd73-a6d5-4d6a2132d2e6","ContestGUID":"efdccd68-c51f-5d83-4ae7-a139bf159479"}}
     */

    private String ContestGUID;
    private ResponseDataBean responseData;

    public String getContestGUID() {
        return ContestGUID;
    }

    public void setContestGUID(String ContestGUID) {
        this.ContestGUID = ContestGUID;
    }

    public ResponseDataBean getResponseData() {
        return responseData;
    }

    public void setResponseData(ResponseDataBean responseData) {
        this.responseData = responseData;
    }

    public static class ResponseDataBean {
        /**
         * ResponseCode : 200
         * Message : Successfully player added
         * Data : {"Status":1,"Player":{"PlayerRole":"Batsman","PlayerStatus":"Upcoming","PlayerID":"353906","PlayerName":"David Miller"},"User":{"FirstName":"Rahul pareta","UserGUID":"d4f418ea-135e-dca0-5c07-c013c49c919d","UserID":"373319"},"DraftStatus":"Running","SeriesGUID":"4321d2c5-5ccc-bd73-a6d5-4d6a2132d2e6","ContestGUID":"efdccd68-c51f-5d83-4ae7-a139bf159479"}
         */

        private int ResponseCode;
        private String Message;
        private DataBean Data;

        public int getResponseCode() {
            return ResponseCode;
        }

        public void setResponseCode(int ResponseCode) {
            this.ResponseCode = ResponseCode;
        }

        public String getMessage() {
            return Message;
        }

        public void setMessage(String Message) {
            this.Message = Message;
        }

        public DataBean getData() {
            return Data;
        }

        public void setData(DataBean Data) {
            this.Data = Data;
        }

        public static class DataBean {
            /**
             * Status : 1
             * Player : {"PlayerRole":"Batsman","PlayerStatus":"Upcoming","PlayerID":"353906","PlayerName":"David Miller"}
             * User : {"FirstName":"Rahul pareta","UserGUID":"d4f418ea-135e-dca0-5c07-c013c49c919d","UserID":"373319"}
             * DraftStatus : Running
             * SeriesGUID : 4321d2c5-5ccc-bd73-a6d5-4d6a2132d2e6
             * ContestGUID : efdccd68-c51f-5d83-4ae7-a139bf159479
             */

            private int Status;
            private PlayerBean Player;
            private UserBean User;
            private String DraftStatus;
            private String SeriesGUID;
            private String ContestGUID;

            public int getStatus() {
                return Status;
            }

            public void setStatus(int Status) {
                this.Status = Status;
            }

            public PlayerBean getPlayer() {
                return Player;
            }

            public void setPlayer(PlayerBean Player) {
                this.Player = Player;
            }

            public UserBean getUser() {
                return User;
            }

            public void setUser(UserBean User) {
                this.User = User;
            }

            public String getDraftStatus() {
                return DraftStatus;
            }

            public void setDraftStatus(String DraftStatus) {
                this.DraftStatus = DraftStatus;
            }

            public String getSeriesGUID() {
                return SeriesGUID;
            }

            public void setSeriesGUID(String SeriesGUID) {
                this.SeriesGUID = SeriesGUID;
            }

            public String getContestGUID() {
                return ContestGUID;
            }

            public void setContestGUID(String ContestGUID) {
                this.ContestGUID = ContestGUID;
            }

            public static class PlayerBean {
                /**
                 * PlayerRole : Batsman
                 * PlayerStatus : Upcoming
                 * PlayerID : 353906
                 * PlayerName : David Miller
                 */

                private String PlayerRole;
                private String PlayerStatus;
                private String PlayerID;
                private String PlayerName;

                public String getPlayerRole() {
                    return PlayerRole;
                }

                public void setPlayerRole(String PlayerRole) {
                    this.PlayerRole = PlayerRole;
                }

                public String getPlayerStatus() {
                    return PlayerStatus;
                }

                public void setPlayerStatus(String PlayerStatus) {
                    this.PlayerStatus = PlayerStatus;
                }

                public String getPlayerID() {
                    return PlayerID;
                }

                public void setPlayerID(String PlayerID) {
                    this.PlayerID = PlayerID;
                }

                public String getPlayerName() {
                    return PlayerName;
                }

                public void setPlayerName(String PlayerName) {
                    this.PlayerName = PlayerName;
                }
            }

            public static class UserBean {
                /**
                 * FirstName : Rahul pareta
                 * UserGUID : d4f418ea-135e-dca0-5c07-c013c49c919d
                 * UserID : 373319
                 */

                private String FirstName;
                private String UserGUID;
                private String UserID;

                public String getFirstName() {
                    return FirstName;
                }

                public void setFirstName(String FirstName) {
                    this.FirstName = FirstName;
                }

                public String getUserGUID() {
                    return UserGUID;
                }

                public void setUserGUID(String UserGUID) {
                    this.UserGUID = UserGUID;
                }

                public String getUserID() {
                    return UserID;
                }

                public void setUserID(String UserID) {
                    this.UserID = UserID;
                }
            }
        }
    }
}


