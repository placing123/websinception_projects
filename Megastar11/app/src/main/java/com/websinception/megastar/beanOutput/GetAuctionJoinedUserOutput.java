package com.websinception.megastar.beanOutput;

import java.io.Serializable;
import java.util.List;

public class GetAuctionJoinedUserOutput {

    /**
     * ResponseCode : 200
     * Message : Success
     * Data : {"TotalRecords":3,"Records":[{"UserGUID":"7280e6d5-660f-6071-c31b-906858a53419","UserTeamID":"","FirstName":"Austin debich","Username":"AUSTIN123","ProfilePic":"http://128.199.239.204/520-draft/api/uploads/profile/picture/4.png","AuctionTimeBank":"154","AuctionBudget":"991700000","AuctionUserStatus":"Online","UserTeamPlayers":[]},{"UserGUID":"8a1b2bd9-b41f-af49-5f97-397efdd8e4e0","UserTeamID":"","FirstName":"Nikhil","Username":"FCULTX","ProfilePic":"http://128.199.239.204/520-draft/api/uploads/profile/picture/default.jpg","AuctionTimeBank":"169","AuctionBudget":"68900000","AuctionUserStatus":"Offline","UserTeamPlayers":[]},{"UserGUID":"4e1bb14d-3f9e-b41d-1f2d-a02da39d718f","UserTeamID":"","FirstName":"Sriram","Username":"SRIRAM","ProfilePic":"http://128.199.239.204/520-draft/api/uploads/profile/picture/default.jpg","AuctionTimeBank":"159","AuctionBudget":"845100000","AuctionUserStatus":"Offline","UserTeamPlayers":[]}]}
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
         * TotalRecords : 3
         * Records : [{"UserGUID":"7280e6d5-660f-6071-c31b-906858a53419","UserTeamID":"","FirstName":"Austin debich","Username":"AUSTIN123","ProfilePic":"http://128.199.239.204/520-draft/api/uploads/profile/picture/4.png","AuctionTimeBank":"154","AuctionBudget":"991700000","AuctionUserStatus":"Online","UserTeamPlayers":[]},{"UserGUID":"8a1b2bd9-b41f-af49-5f97-397efdd8e4e0","UserTeamID":"","FirstName":"Nikhil","Username":"FCULTX","ProfilePic":"http://128.199.239.204/520-draft/api/uploads/profile/picture/default.jpg","AuctionTimeBank":"169","AuctionBudget":"68900000","AuctionUserStatus":"Offline","UserTeamPlayers":[]},{"UserGUID":"4e1bb14d-3f9e-b41d-1f2d-a02da39d718f","UserTeamID":"","FirstName":"Sriram","Username":"SRIRAM","ProfilePic":"http://128.199.239.204/520-draft/api/uploads/profile/picture/default.jpg","AuctionTimeBank":"159","AuctionBudget":"845100000","AuctionUserStatus":"Offline","UserTeamPlayers":[]}]
         */

        private int TotalRecords;
        private List<RecordsBean> Records;

        public int getTotalRecords() {
            return TotalRecords;
        }

        public void setTotalRecords(int TotalRecords) {
            this.TotalRecords = TotalRecords;
        }

        public List<RecordsBean> getRecords() {
            return Records;
        }

        public void setRecords(List<RecordsBean> Records) {
            this.Records = Records;
        }

        public static class RecordsBean {
            /**
             * UserGUID : 7280e6d5-660f-6071-c31b-906858a53419
             * UserTeamID :
             * FirstName : Austin debich
             * Username : AUSTIN123
             * ProfilePic : http://128.199.239.204/520-draft/api/uploads/profile/picture/4.png
             * AuctionTimeBank : 154
             * AuctionBudget : 991700000
             * AuctionUserStatus : Online
             * UserTeamPlayers : []
             */

            private String AuctionHoldDateTime;
            private String IsHold;
            private String TotalPoints;
            private String UserRank;
            private String UserTeamName;
            private String UserGUID;
            private String UserTeamID;
            private String FirstName;
            private String Username;
            private String ProfilePic;
            private String AuctionTimeBank;
            private String AuctionBudget;
            private String AuctionUserStatus;
            private String UserWinningAmount;
            private String playerSelectedCount;
            private List<UserTeamPlayers> UserTeamPlayers;

            public String getPlayerSelectedCount() {
                return playerSelectedCount;
            }

            public void setPlayerSelectedCount(String playerSelectedCount) {
                this.playerSelectedCount = playerSelectedCount;
            }

            @Override
            public String toString() {
                return FirstName;
            }


            public String getUserWinningAmount() {
                return UserWinningAmount;
            }

            public void setUserWinningAmount(String userWinningAmount) {
                UserWinningAmount = userWinningAmount;
            }

            public String getIsHold() {
                return IsHold;
            }

            public void setIsHold(String isHold) {
                IsHold = isHold;
            }

            public String getAuctionHoldDateTime() {
                return AuctionHoldDateTime;
            }

            public void setAuctionHoldDateTime(String auctionHoldDateTime) {
                AuctionHoldDateTime = auctionHoldDateTime;
            }

            public String getTotalPoints() {
                return TotalPoints;
            }

            public void setTotalPoints(String totalPoints) {
                TotalPoints = totalPoints;
            }

            public String getUserRank() {
                return UserRank;
            }

            public void setUserRank(String userRank) {
                UserRank = userRank;
            }

            public String getUserTeamName() {
                return UserTeamName;
            }

            public void setUserTeamName(String userTeamName) {
                UserTeamName = userTeamName;
            }

            public String getUserGUID() {
                return UserGUID;
            }

            public void setUserGUID(String UserGUID) {
                this.UserGUID = UserGUID;
            }

            public String getUserTeamID() {
                return UserTeamID;
            }

            public void setUserTeamID(String UserTeamID) {
                this.UserTeamID = UserTeamID;
            }

            public String getFirstName() {
                return FirstName;
            }

            public void setFirstName(String FirstName) {
                this.FirstName = FirstName;
            }

            public String getUsername() {
                return Username;
            }

            public void setUsername(String Username) {
                this.Username = Username;
            }

            public String getProfilePic() {
                return ProfilePic;
            }

            public void setProfilePic(String ProfilePic) {
                this.ProfilePic = ProfilePic;
            }

            public String getAuctionTimeBank() {
                return AuctionTimeBank;
            }

            public void setAuctionTimeBank(String AuctionTimeBank) {
                this.AuctionTimeBank = AuctionTimeBank;
            }

            public String getAuctionBudget() {
                return AuctionBudget;
            }

            public void setAuctionBudget(String AuctionBudget) {
                this.AuctionBudget = AuctionBudget;
            }

            public String getAuctionUserStatus() {
                return AuctionUserStatus;
            }

            public void setAuctionUserStatus(String AuctionUserStatus) {
                this.AuctionUserStatus = AuctionUserStatus;
            }

            public List<UserTeamPlayers> getUserTeamPlayers() {
                return UserTeamPlayers;
            }

            public void setUserTeamPlayers(List<UserTeamPlayers> UserTeamPlayers) {
                this.UserTeamPlayers = UserTeamPlayers;
            }


            public static class UserTeamPlayers implements Serializable {

                private String PlayerGUID;
                private String BidCredit;
                private String Points;
                private String PlayerPosition;
                private String PlayerName;
                private String PlayerPic;
                private String PlayerSalary;
                private String PlayerRole;

                public String getPlayerRole() {
                    return PlayerRole;
                }

                public void setPlayerRole(String playerRole) {
                    PlayerRole = playerRole;
                }

                public String getPlayerGUID() {
                    return PlayerGUID;
                }

                public void setPlayerGUID(String playerGUID) {
                    PlayerGUID = playerGUID;
                }

                public String getBidCredit() {
                    return BidCredit;
                }

                public void setBidCredit(String bidCredit) {
                    BidCredit = bidCredit;
                }

                public String getPoints() {
                    return Points;
                }

                public void setPoints(String points) {
                    Points = points;
                }

                public String getPlayerPosition() {
                    return PlayerPosition;
                }

                public void setPlayerPosition(String playerPosition) {
                    PlayerPosition = playerPosition;
                }

                public String getPlayerName() {
                    return PlayerName;
                }

                public void setPlayerName(String playerName) {
                    PlayerName = playerName;
                }

                public String getPlayerPic() {
                    return PlayerPic;
                }

                public void setPlayerPic(String playerPic) {
                    PlayerPic = playerPic;
                }

                public String getPlayerSalary() {
                    return PlayerSalary;
                }

                public void setPlayerSalary(String playerSalary) {
                    PlayerSalary = playerSalary;
                }
            }
        }
    }
}
