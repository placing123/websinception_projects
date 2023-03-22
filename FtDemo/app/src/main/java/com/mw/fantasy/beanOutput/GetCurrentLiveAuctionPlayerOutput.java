package com.mw.fantasy.beanOutput;

import java.util.List;

public class GetCurrentLiveAuctionPlayerOutput {

    /**
     * ResponseCode : 200
     * Message : Player successfully in auction.
     * Data : {"PlayerGUID":"e6257050-237d-a88e-2d46-4e5e07aa5440","PlayerName":"Shimron Hetmyer","PlayerID":"353347","PlayerPic":"http://128.199.239.204/520-draft/api/uploads/PlayerPic/0ecdf0db-ff49-985c-7ef4-42c62299ccb4.png","PlayerCountry":"WI","PlayerBattingStyle":"LHB","PlayerBowlingStyle":"Legbreak googly","PlayerBattingStats":{"Test":{"MatchID":0,"InningID":0,"Matches":13,"Innings":25,"NotOut":0,"Runs":754,"Balls":996,"HighestScore":"93","Hundreds":0,"Fifties":5,"Fours":74,"Sixes":27,"Average":"30.16","StrikeRate":"75.70","Catches":6,"Stumpings":0},"ODI":{"MatchID":0,"InningID":0,"Matches":25,"Innings":24,"NotOut":2,"Runs":899,"Balls":815,"HighestScore":"127","Hundreds":4,"Fifties":2,"Fours":62,"Sixes":36,"Average":"40.86","StrikeRate":"110.30","Catches":13,"Stumpings":0},"T20i":{"MatchID":0,"InningID":0,"Matches":11,"Innings":9,"NotOut":0,"Runs":109,"Balls":92,"HighestScore":"26","Hundreds":0,"Fifties":0,"Fours":13,"Sixes":4,"Average":"12.11","StrikeRate":"118.47","Catches":2,"Stumpings":0},"T20":{"MatchID":0,"InningID":0,"Matches":28,"Innings":26,"NotOut":1,"Runs":564,"Balls":418,"HighestScore":"100","Hundreds":1,"Fifties":2,"Fours":52,"Sixes":29,"Average":"22.56","StrikeRate":"134.92","Catches":9,"Stumpings":0},"ListA":{"MatchID":0,"InningID":0,"Matches":41,"Innings":40,"NotOut":3,"Runs":1407,"Balls":1392,"HighestScore":"127","Hundreds":5,"Fifties":5,"Fours":106,"Sixes":54,"Average":"38.02","StrikeRate":"101.07","Catches":25,"Stumpings":0},"FirstClass":{"MatchID":0,"InningID":0,"Matches":34,"Innings":62,"NotOut":3,"Runs":1989,"Balls":2560,"HighestScore":"107","Hundreds":1,"Fifties":12,"Fours":253,"Sixes":49,"Average":"33.71","StrikeRate":"77.69","Catches":27,"Stumpings":0}},"PlayerBowlingStats":{"Test":{"MatchID":0,"InningID":0,"Matches":13,"Innings":0,"Balls":0,"Overs":"","Runs":0,"Wickets":0,"BestInning":"","BestMatch":"","Economy":"","Average":"","StrikeRate":"","FourPlusWicketsInSingleInning":0,"FivePlusWicketsInSingleInning":0,"TenPlusWicketsInSingleInning":0},"ODI":{"MatchID":0,"InningID":0,"Matches":25,"Innings":0,"Balls":0,"Overs":"","Runs":0,"Wickets":0,"BestInning":"","BestMatch":"","Economy":"","Average":"","StrikeRate":"","FourPlusWicketsInSingleInning":0,"FivePlusWicketsInSingleInning":0,"TenPlusWicketsInSingleInning":0},"T20i":{"MatchID":0,"InningID":0,"Matches":11,"Innings":0,"Balls":0,"Overs":"","Runs":0,"Wickets":0,"BestInning":"","BestMatch":"","Economy":"","Average":"","StrikeRate":"","FourPlusWicketsInSingleInning":0,"FivePlusWicketsInSingleInning":0,"TenPlusWicketsInSingleInning":0},"T20":{"MatchID":0,"InningID":0,"Matches":28,"Innings":0,"Balls":0,"Overs":"","Runs":0,"Wickets":0,"BestInning":"","BestMatch":"","Economy":"","Average":"","StrikeRate":"","FourPlusWicketsInSingleInning":0,"FivePlusWicketsInSingleInning":0,"TenPlusWicketsInSingleInning":0},"ListA":{"MatchID":0,"InningID":0,"Matches":41,"Innings":0,"Balls":0,"Overs":"","Runs":0,"Wickets":0,"BestInning":"","BestMatch":"","Economy":"","Average":"","StrikeRate":"","FourPlusWicketsInSingleInning":0,"FivePlusWicketsInSingleInning":0,"TenPlusWicketsInSingleInning":0},"FirstClass":{"MatchID":0,"InningID":0,"Matches":34,"Innings":0,"Balls":0,"Overs":"","Runs":0,"Wickets":0,"BestInning":"","BestMatch":"","Economy":"","Average":"","StrikeRate":"","FourPlusWicketsInSingleInning":0,"FivePlusWicketsInSingleInning":0,"TenPlusWicketsInSingleInning":0}},"PlayerSalary":"9.50","PointsData":[],"PlayerRole":"Batsman"}
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
         * PlayerGUID : e6257050-237d-a88e-2d46-4e5e07aa5440
         * PlayerName : Shimron Hetmyer
         * PlayerID : 353347
         * PlayerPic : http://128.199.239.204/520-draft/api/uploads/PlayerPic/0ecdf0db-ff49-985c-7ef4-42c62299ccb4.png
         * PlayerCountry : WI
         * PlayerBattingStyle : LHB
         * PlayerBowlingStyle : Legbreak googly
         * PlayerBattingStats : {"Test":{"MatchID":0,"InningID":0,"Matches":13,"Innings":25,"NotOut":0,"Runs":754,"Balls":996,"HighestScore":"93","Hundreds":0,"Fifties":5,"Fours":74,"Sixes":27,"Average":"30.16","StrikeRate":"75.70","Catches":6,"Stumpings":0},"ODI":{"MatchID":0,"InningID":0,"Matches":25,"Innings":24,"NotOut":2,"Runs":899,"Balls":815,"HighestScore":"127","Hundreds":4,"Fifties":2,"Fours":62,"Sixes":36,"Average":"40.86","StrikeRate":"110.30","Catches":13,"Stumpings":0},"T20i":{"MatchID":0,"InningID":0,"Matches":11,"Innings":9,"NotOut":0,"Runs":109,"Balls":92,"HighestScore":"26","Hundreds":0,"Fifties":0,"Fours":13,"Sixes":4,"Average":"12.11","StrikeRate":"118.47","Catches":2,"Stumpings":0},"T20":{"MatchID":0,"InningID":0,"Matches":28,"Innings":26,"NotOut":1,"Runs":564,"Balls":418,"HighestScore":"100","Hundreds":1,"Fifties":2,"Fours":52,"Sixes":29,"Average":"22.56","StrikeRate":"134.92","Catches":9,"Stumpings":0},"ListA":{"MatchID":0,"InningID":0,"Matches":41,"Innings":40,"NotOut":3,"Runs":1407,"Balls":1392,"HighestScore":"127","Hundreds":5,"Fifties":5,"Fours":106,"Sixes":54,"Average":"38.02","StrikeRate":"101.07","Catches":25,"Stumpings":0},"FirstClass":{"MatchID":0,"InningID":0,"Matches":34,"Innings":62,"NotOut":3,"Runs":1989,"Balls":2560,"HighestScore":"107","Hundreds":1,"Fifties":12,"Fours":253,"Sixes":49,"Average":"33.71","StrikeRate":"77.69","Catches":27,"Stumpings":0}}
         * PlayerBowlingStats : {"Test":{"MatchID":0,"InningID":0,"Matches":13,"Innings":0,"Balls":0,"Overs":"","Runs":0,"Wickets":0,"BestInning":"","BestMatch":"","Economy":"","Average":"","StrikeRate":"","FourPlusWicketsInSingleInning":0,"FivePlusWicketsInSingleInning":0,"TenPlusWicketsInSingleInning":0},"ODI":{"MatchID":0,"InningID":0,"Matches":25,"Innings":0,"Balls":0,"Overs":"","Runs":0,"Wickets":0,"BestInning":"","BestMatch":"","Economy":"","Average":"","StrikeRate":"","FourPlusWicketsInSingleInning":0,"FivePlusWicketsInSingleInning":0,"TenPlusWicketsInSingleInning":0},"T20i":{"MatchID":0,"InningID":0,"Matches":11,"Innings":0,"Balls":0,"Overs":"","Runs":0,"Wickets":0,"BestInning":"","BestMatch":"","Economy":"","Average":"","StrikeRate":"","FourPlusWicketsInSingleInning":0,"FivePlusWicketsInSingleInning":0,"TenPlusWicketsInSingleInning":0},"T20":{"MatchID":0,"InningID":0,"Matches":28,"Innings":0,"Balls":0,"Overs":"","Runs":0,"Wickets":0,"BestInning":"","BestMatch":"","Economy":"","Average":"","StrikeRate":"","FourPlusWicketsInSingleInning":0,"FivePlusWicketsInSingleInning":0,"TenPlusWicketsInSingleInning":0},"ListA":{"MatchID":0,"InningID":0,"Matches":41,"Innings":0,"Balls":0,"Overs":"","Runs":0,"Wickets":0,"BestInning":"","BestMatch":"","Economy":"","Average":"","StrikeRate":"","FourPlusWicketsInSingleInning":0,"FivePlusWicketsInSingleInning":0,"TenPlusWicketsInSingleInning":0},"FirstClass":{"MatchID":0,"InningID":0,"Matches":34,"Innings":0,"Balls":0,"Overs":"","Runs":0,"Wickets":0,"BestInning":"","BestMatch":"","Economy":"","Average":"","StrikeRate":"","FourPlusWicketsInSingleInning":0,"FivePlusWicketsInSingleInning":0,"TenPlusWicketsInSingleInning":0}}
         * PlayerSalary : 9.50
         * PointsData : []
         * PlayerRole : Batsman
         */

        private String PlayerGUID;
        private String PlayerName;
        private String PlayerID;
        private String PlayerPic;
        private String PlayerCountry;
        private String PlayerBattingStyle;
        private String PlayerBowlingStyle;
        private PlayerBattingStatsBean PlayerBattingStats;
        private PlayerBowlingStatsBean PlayerBowlingStats;
        private String PlayerSalary;
        private String PlayerRole;
        private List<?> PointsData;
        private CurrentBidUserBean CurrentBidUser;
        private CurrentBidPlayerTime CurrentBidPlayerTime;


        public DataBean.CurrentBidPlayerTime getCurrentBidPlayerTime() {
            return CurrentBidPlayerTime;
        }

        public void setCurrentBidPlayerTime(DataBean.CurrentBidPlayerTime currentBidPlayerTime) {
            CurrentBidPlayerTime = currentBidPlayerTime;
        }

        public CurrentBidUserBean getCurrentBidUser() {
            return CurrentBidUser;
        }

        public void setCurrentBidUser(CurrentBidUserBean currentBidUser) {
            CurrentBidUser = currentBidUser;
        }

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

        public String getPlayerID() {
            return PlayerID;
        }

        public void setPlayerID(String PlayerID) {
            this.PlayerID = PlayerID;
        }

        public String getPlayerPic() {
            return PlayerPic;
        }

        public void setPlayerPic(String PlayerPic) {
            this.PlayerPic = PlayerPic;
        }

        public String getPlayerCountry() {
            return PlayerCountry;
        }

        public void setPlayerCountry(String PlayerCountry) {
            this.PlayerCountry = PlayerCountry;
        }

        public String getPlayerBattingStyle() {
            return PlayerBattingStyle;
        }

        public void setPlayerBattingStyle(String PlayerBattingStyle) {
            this.PlayerBattingStyle = PlayerBattingStyle;
        }

        public String getPlayerBowlingStyle() {
            return PlayerBowlingStyle;
        }

        public void setPlayerBowlingStyle(String PlayerBowlingStyle) {
            this.PlayerBowlingStyle = PlayerBowlingStyle;
        }

        public PlayerBattingStatsBean getPlayerBattingStats() {
            return PlayerBattingStats;
        }

        public void setPlayerBattingStats(PlayerBattingStatsBean PlayerBattingStats) {
            this.PlayerBattingStats = PlayerBattingStats;
        }

        public PlayerBowlingStatsBean getPlayerBowlingStats() {
            return PlayerBowlingStats;
        }

        public void setPlayerBowlingStats(PlayerBowlingStatsBean PlayerBowlingStats) {
            this.PlayerBowlingStats = PlayerBowlingStats;
        }

        public String getPlayerSalary() {
            return PlayerSalary;
        }

        public void setPlayerSalary(String PlayerSalary) {
            this.PlayerSalary = PlayerSalary;
        }

        public String getPlayerRole() {
            return PlayerRole;
        }

        public void setPlayerRole(String PlayerRole) {
            this.PlayerRole = PlayerRole;
        }

        public List<?> getPointsData() {
            return PointsData;
        }

        public void setPointsData(List<?> PointsData) {
            this.PointsData = PointsData;
        }

        public static class PlayerBattingStatsBean {
            /**
             * Test : {"MatchID":0,"InningID":0,"Matches":13,"Innings":25,"NotOut":0,"Runs":754,"Balls":996,"HighestScore":"93","Hundreds":0,"Fifties":5,"Fours":74,"Sixes":27,"Average":"30.16","StrikeRate":"75.70","Catches":6,"Stumpings":0}
             * ODI : {"MatchID":0,"InningID":0,"Matches":25,"Innings":24,"NotOut":2,"Runs":899,"Balls":815,"HighestScore":"127","Hundreds":4,"Fifties":2,"Fours":62,"Sixes":36,"Average":"40.86","StrikeRate":"110.30","Catches":13,"Stumpings":0}
             * T20i : {"MatchID":0,"InningID":0,"Matches":11,"Innings":9,"NotOut":0,"Runs":109,"Balls":92,"HighestScore":"26","Hundreds":0,"Fifties":0,"Fours":13,"Sixes":4,"Average":"12.11","StrikeRate":"118.47","Catches":2,"Stumpings":0}
             * T20 : {"MatchID":0,"InningID":0,"Matches":28,"Innings":26,"NotOut":1,"Runs":564,"Balls":418,"HighestScore":"100","Hundreds":1,"Fifties":2,"Fours":52,"Sixes":29,"Average":"22.56","StrikeRate":"134.92","Catches":9,"Stumpings":0}
             * ListA : {"MatchID":0,"InningID":0,"Matches":41,"Innings":40,"NotOut":3,"Runs":1407,"Balls":1392,"HighestScore":"127","Hundreds":5,"Fifties":5,"Fours":106,"Sixes":54,"Average":"38.02","StrikeRate":"101.07","Catches":25,"Stumpings":0}
             * FirstClass : {"MatchID":0,"InningID":0,"Matches":34,"Innings":62,"NotOut":3,"Runs":1989,"Balls":2560,"HighestScore":"107","Hundreds":1,"Fifties":12,"Fours":253,"Sixes":49,"Average":"33.71","StrikeRate":"77.69","Catches":27,"Stumpings":0}
             */

            private TestBean Test;
            private ODIBean ODI;
            private T20iBean T20i;
            private T20Bean T20;
            private ListABean ListA;
            private FirstClassBean FirstClass;

            public TestBean getTest() {
                return Test;
            }

            public void setTest(TestBean Test) {
                this.Test = Test;
            }

            public ODIBean getODI() {
                return ODI;
            }

            public void setODI(ODIBean ODI) {
                this.ODI = ODI;
            }

            public T20iBean getT20i() {
                return T20i;
            }

            public void setT20i(T20iBean T20i) {
                this.T20i = T20i;
            }

            public T20Bean getT20() {
                return T20;
            }

            public void setT20(T20Bean T20) {
                this.T20 = T20;
            }

            public ListABean getListA() {
                return ListA;
            }

            public void setListA(ListABean ListA) {
                this.ListA = ListA;
            }

            public FirstClassBean getFirstClass() {
                return FirstClass;
            }

            public void setFirstClass(FirstClassBean FirstClass) {
                this.FirstClass = FirstClass;
            }

            public static class TestBean {
                /**
                 * MatchID : 0
                 * InningID : 0
                 * Matches : 13
                 * Innings : 25
                 * NotOut : 0
                 * Runs : 754
                 * Balls : 996
                 * HighestScore : 93
                 * Hundreds : 0
                 * Fifties : 5
                 * Fours : 74
                 * Sixes : 27
                 * Average : 30.16
                 * StrikeRate : 75.70
                 * Catches : 6
                 * Stumpings : 0
                 */

                private int MatchID;
                private int InningID;
                private int Matches;
                private int Innings;
                private int NotOut;
                private int Runs;
                private int Balls;
                private String HighestScore;
                private int Hundreds;
                private int Fifties;
                private int Fours;
                private int Sixes;
                private String Average;
                private String StrikeRate;
                private int Catches;
                private int Stumpings;

                public int getMatchID() {
                    return MatchID;
                }

                public void setMatchID(int MatchID) {
                    this.MatchID = MatchID;
                }

                public int getInningID() {
                    return InningID;
                }

                public void setInningID(int InningID) {
                    this.InningID = InningID;
                }

                public int getMatches() {
                    return Matches;
                }

                public void setMatches(int Matches) {
                    this.Matches = Matches;
                }

                public int getInnings() {
                    return Innings;
                }

                public void setInnings(int Innings) {
                    this.Innings = Innings;
                }

                public int getNotOut() {
                    return NotOut;
                }

                public void setNotOut(int NotOut) {
                    this.NotOut = NotOut;
                }

                public int getRuns() {
                    return Runs;
                }

                public void setRuns(int Runs) {
                    this.Runs = Runs;
                }

                public int getBalls() {
                    return Balls;
                }

                public void setBalls(int Balls) {
                    this.Balls = Balls;
                }

                public String getHighestScore() {
                    return HighestScore;
                }

                public void setHighestScore(String HighestScore) {
                    this.HighestScore = HighestScore;
                }

                public int getHundreds() {
                    return Hundreds;
                }

                public void setHundreds(int Hundreds) {
                    this.Hundreds = Hundreds;
                }

                public int getFifties() {
                    return Fifties;
                }

                public void setFifties(int Fifties) {
                    this.Fifties = Fifties;
                }

                public int getFours() {
                    return Fours;
                }

                public void setFours(int Fours) {
                    this.Fours = Fours;
                }

                public int getSixes() {
                    return Sixes;
                }

                public void setSixes(int Sixes) {
                    this.Sixes = Sixes;
                }

                public String getAverage() {
                    return Average;
                }

                public void setAverage(String Average) {
                    this.Average = Average;
                }

                public String getStrikeRate() {
                    return StrikeRate;
                }

                public void setStrikeRate(String StrikeRate) {
                    this.StrikeRate = StrikeRate;
                }

                public int getCatches() {
                    return Catches;
                }

                public void setCatches(int Catches) {
                    this.Catches = Catches;
                }

                public int getStumpings() {
                    return Stumpings;
                }

                public void setStumpings(int Stumpings) {
                    this.Stumpings = Stumpings;
                }
            }

            public static class ODIBean {
                /**
                 * MatchID : 0
                 * InningID : 0
                 * Matches : 25
                 * Innings : 24
                 * NotOut : 2
                 * Runs : 899
                 * Balls : 815
                 * HighestScore : 127
                 * Hundreds : 4
                 * Fifties : 2
                 * Fours : 62
                 * Sixes : 36
                 * Average : 40.86
                 * StrikeRate : 110.30
                 * Catches : 13
                 * Stumpings : 0
                 */

                private int MatchID;
                private int InningID;
                private int Matches;
                private int Innings;
                private int NotOut;
                private int Runs;
                private int Balls;
                private String HighestScore;
                private int Hundreds;
                private int Fifties;
                private int Fours;
                private int Sixes;
                private String Average;
                private String StrikeRate;
                private int Catches;
                private int Stumpings;

                public int getMatchID() {
                    return MatchID;
                }

                public void setMatchID(int MatchID) {
                    this.MatchID = MatchID;
                }

                public int getInningID() {
                    return InningID;
                }

                public void setInningID(int InningID) {
                    this.InningID = InningID;
                }

                public int getMatches() {
                    return Matches;
                }

                public void setMatches(int Matches) {
                    this.Matches = Matches;
                }

                public int getInnings() {
                    return Innings;
                }

                public void setInnings(int Innings) {
                    this.Innings = Innings;
                }

                public int getNotOut() {
                    return NotOut;
                }

                public void setNotOut(int NotOut) {
                    this.NotOut = NotOut;
                }

                public int getRuns() {
                    return Runs;
                }

                public void setRuns(int Runs) {
                    this.Runs = Runs;
                }

                public int getBalls() {
                    return Balls;
                }

                public void setBalls(int Balls) {
                    this.Balls = Balls;
                }

                public String getHighestScore() {
                    return HighestScore;
                }

                public void setHighestScore(String HighestScore) {
                    this.HighestScore = HighestScore;
                }

                public int getHundreds() {
                    return Hundreds;
                }

                public void setHundreds(int Hundreds) {
                    this.Hundreds = Hundreds;
                }

                public int getFifties() {
                    return Fifties;
                }

                public void setFifties(int Fifties) {
                    this.Fifties = Fifties;
                }

                public int getFours() {
                    return Fours;
                }

                public void setFours(int Fours) {
                    this.Fours = Fours;
                }

                public int getSixes() {
                    return Sixes;
                }

                public void setSixes(int Sixes) {
                    this.Sixes = Sixes;
                }

                public String getAverage() {
                    return Average;
                }

                public void setAverage(String Average) {
                    this.Average = Average;
                }

                public String getStrikeRate() {
                    return StrikeRate;
                }

                public void setStrikeRate(String StrikeRate) {
                    this.StrikeRate = StrikeRate;
                }

                public int getCatches() {
                    return Catches;
                }

                public void setCatches(int Catches) {
                    this.Catches = Catches;
                }

                public int getStumpings() {
                    return Stumpings;
                }

                public void setStumpings(int Stumpings) {
                    this.Stumpings = Stumpings;
                }
            }

            public static class T20iBean {
                /**
                 * MatchID : 0
                 * InningID : 0
                 * Matches : 11
                 * Innings : 9
                 * NotOut : 0
                 * Runs : 109
                 * Balls : 92
                 * HighestScore : 26
                 * Hundreds : 0
                 * Fifties : 0
                 * Fours : 13
                 * Sixes : 4
                 * Average : 12.11
                 * StrikeRate : 118.47
                 * Catches : 2
                 * Stumpings : 0
                 */

                private int MatchID;
                private int InningID;
                private int Matches;
                private int Innings;
                private int NotOut;
                private int Runs;
                private int Balls;
                private String HighestScore;
                private int Hundreds;
                private int Fifties;
                private int Fours;
                private int Sixes;
                private String Average;
                private String StrikeRate;
                private int Catches;
                private int Stumpings;

                public int getMatchID() {
                    return MatchID;
                }

                public void setMatchID(int MatchID) {
                    this.MatchID = MatchID;
                }

                public int getInningID() {
                    return InningID;
                }

                public void setInningID(int InningID) {
                    this.InningID = InningID;
                }

                public int getMatches() {
                    return Matches;
                }

                public void setMatches(int Matches) {
                    this.Matches = Matches;
                }

                public int getInnings() {
                    return Innings;
                }

                public void setInnings(int Innings) {
                    this.Innings = Innings;
                }

                public int getNotOut() {
                    return NotOut;
                }

                public void setNotOut(int NotOut) {
                    this.NotOut = NotOut;
                }

                public int getRuns() {
                    return Runs;
                }

                public void setRuns(int Runs) {
                    this.Runs = Runs;
                }

                public int getBalls() {
                    return Balls;
                }

                public void setBalls(int Balls) {
                    this.Balls = Balls;
                }

                public String getHighestScore() {
                    return HighestScore;
                }

                public void setHighestScore(String HighestScore) {
                    this.HighestScore = HighestScore;
                }

                public int getHundreds() {
                    return Hundreds;
                }

                public void setHundreds(int Hundreds) {
                    this.Hundreds = Hundreds;
                }

                public int getFifties() {
                    return Fifties;
                }

                public void setFifties(int Fifties) {
                    this.Fifties = Fifties;
                }

                public int getFours() {
                    return Fours;
                }

                public void setFours(int Fours) {
                    this.Fours = Fours;
                }

                public int getSixes() {
                    return Sixes;
                }

                public void setSixes(int Sixes) {
                    this.Sixes = Sixes;
                }

                public String getAverage() {
                    return Average;
                }

                public void setAverage(String Average) {
                    this.Average = Average;
                }

                public String getStrikeRate() {
                    return StrikeRate;
                }

                public void setStrikeRate(String StrikeRate) {
                    this.StrikeRate = StrikeRate;
                }

                public int getCatches() {
                    return Catches;
                }

                public void setCatches(int Catches) {
                    this.Catches = Catches;
                }

                public int getStumpings() {
                    return Stumpings;
                }

                public void setStumpings(int Stumpings) {
                    this.Stumpings = Stumpings;
                }
            }

            public static class T20Bean {
                /**
                 * MatchID : 0
                 * InningID : 0
                 * Matches : 28
                 * Innings : 26
                 * NotOut : 1
                 * Runs : 564
                 * Balls : 418
                 * HighestScore : 100
                 * Hundreds : 1
                 * Fifties : 2
                 * Fours : 52
                 * Sixes : 29
                 * Average : 22.56
                 * StrikeRate : 134.92
                 * Catches : 9
                 * Stumpings : 0
                 */

                private int MatchID;
                private int InningID;
                private int Matches;
                private int Innings;
                private int NotOut;
                private int Runs;
                private int Balls;
                private String HighestScore;
                private int Hundreds;
                private int Fifties;
                private int Fours;
                private int Sixes;
                private String Average;
                private String StrikeRate;
                private int Catches;
                private int Stumpings;

                public int getMatchID() {
                    return MatchID;
                }

                public void setMatchID(int MatchID) {
                    this.MatchID = MatchID;
                }

                public int getInningID() {
                    return InningID;
                }

                public void setInningID(int InningID) {
                    this.InningID = InningID;
                }

                public int getMatches() {
                    return Matches;
                }

                public void setMatches(int Matches) {
                    this.Matches = Matches;
                }

                public int getInnings() {
                    return Innings;
                }

                public void setInnings(int Innings) {
                    this.Innings = Innings;
                }

                public int getNotOut() {
                    return NotOut;
                }

                public void setNotOut(int NotOut) {
                    this.NotOut = NotOut;
                }

                public int getRuns() {
                    return Runs;
                }

                public void setRuns(int Runs) {
                    this.Runs = Runs;
                }

                public int getBalls() {
                    return Balls;
                }

                public void setBalls(int Balls) {
                    this.Balls = Balls;
                }

                public String getHighestScore() {
                    return HighestScore;
                }

                public void setHighestScore(String HighestScore) {
                    this.HighestScore = HighestScore;
                }

                public int getHundreds() {
                    return Hundreds;
                }

                public void setHundreds(int Hundreds) {
                    this.Hundreds = Hundreds;
                }

                public int getFifties() {
                    return Fifties;
                }

                public void setFifties(int Fifties) {
                    this.Fifties = Fifties;
                }

                public int getFours() {
                    return Fours;
                }

                public void setFours(int Fours) {
                    this.Fours = Fours;
                }

                public int getSixes() {
                    return Sixes;
                }

                public void setSixes(int Sixes) {
                    this.Sixes = Sixes;
                }

                public String getAverage() {
                    return Average;
                }

                public void setAverage(String Average) {
                    this.Average = Average;
                }

                public String getStrikeRate() {
                    return StrikeRate;
                }

                public void setStrikeRate(String StrikeRate) {
                    this.StrikeRate = StrikeRate;
                }

                public int getCatches() {
                    return Catches;
                }

                public void setCatches(int Catches) {
                    this.Catches = Catches;
                }

                public int getStumpings() {
                    return Stumpings;
                }

                public void setStumpings(int Stumpings) {
                    this.Stumpings = Stumpings;
                }
            }

            public static class ListABean {
                /**
                 * MatchID : 0
                 * InningID : 0
                 * Matches : 41
                 * Innings : 40
                 * NotOut : 3
                 * Runs : 1407
                 * Balls : 1392
                 * HighestScore : 127
                 * Hundreds : 5
                 * Fifties : 5
                 * Fours : 106
                 * Sixes : 54
                 * Average : 38.02
                 * StrikeRate : 101.07
                 * Catches : 25
                 * Stumpings : 0
                 */

                private int MatchID;
                private int InningID;
                private int Matches;
                private int Innings;
                private int NotOut;
                private int Runs;
                private int Balls;
                private String HighestScore;
                private int Hundreds;
                private int Fifties;
                private int Fours;
                private int Sixes;
                private String Average;
                private String StrikeRate;
                private int Catches;
                private int Stumpings;

                public int getMatchID() {
                    return MatchID;
                }

                public void setMatchID(int MatchID) {
                    this.MatchID = MatchID;
                }

                public int getInningID() {
                    return InningID;
                }

                public void setInningID(int InningID) {
                    this.InningID = InningID;
                }

                public int getMatches() {
                    return Matches;
                }

                public void setMatches(int Matches) {
                    this.Matches = Matches;
                }

                public int getInnings() {
                    return Innings;
                }

                public void setInnings(int Innings) {
                    this.Innings = Innings;
                }

                public int getNotOut() {
                    return NotOut;
                }

                public void setNotOut(int NotOut) {
                    this.NotOut = NotOut;
                }

                public int getRuns() {
                    return Runs;
                }

                public void setRuns(int Runs) {
                    this.Runs = Runs;
                }

                public int getBalls() {
                    return Balls;
                }

                public void setBalls(int Balls) {
                    this.Balls = Balls;
                }

                public String getHighestScore() {
                    return HighestScore;
                }

                public void setHighestScore(String HighestScore) {
                    this.HighestScore = HighestScore;
                }

                public int getHundreds() {
                    return Hundreds;
                }

                public void setHundreds(int Hundreds) {
                    this.Hundreds = Hundreds;
                }

                public int getFifties() {
                    return Fifties;
                }

                public void setFifties(int Fifties) {
                    this.Fifties = Fifties;
                }

                public int getFours() {
                    return Fours;
                }

                public void setFours(int Fours) {
                    this.Fours = Fours;
                }

                public int getSixes() {
                    return Sixes;
                }

                public void setSixes(int Sixes) {
                    this.Sixes = Sixes;
                }

                public String getAverage() {
                    return Average;
                }

                public void setAverage(String Average) {
                    this.Average = Average;
                }

                public String getStrikeRate() {
                    return StrikeRate;
                }

                public void setStrikeRate(String StrikeRate) {
                    this.StrikeRate = StrikeRate;
                }

                public int getCatches() {
                    return Catches;
                }

                public void setCatches(int Catches) {
                    this.Catches = Catches;
                }

                public int getStumpings() {
                    return Stumpings;
                }

                public void setStumpings(int Stumpings) {
                    this.Stumpings = Stumpings;
                }
            }

            public static class FirstClassBean {
                /**
                 * MatchID : 0
                 * InningID : 0
                 * Matches : 34
                 * Innings : 62
                 * NotOut : 3
                 * Runs : 1989
                 * Balls : 2560
                 * HighestScore : 107
                 * Hundreds : 1
                 * Fifties : 12
                 * Fours : 253
                 * Sixes : 49
                 * Average : 33.71
                 * StrikeRate : 77.69
                 * Catches : 27
                 * Stumpings : 0
                 */

                private int MatchID;
                private int InningID;
                private int Matches;
                private int Innings;
                private int NotOut;
                private int Runs;
                private int Balls;
                private String HighestScore;
                private int Hundreds;
                private int Fifties;
                private int Fours;
                private int Sixes;
                private String Average;
                private String StrikeRate;
                private int Catches;
                private int Stumpings;

                public int getMatchID() {
                    return MatchID;
                }

                public void setMatchID(int MatchID) {
                    this.MatchID = MatchID;
                }

                public int getInningID() {
                    return InningID;
                }

                public void setInningID(int InningID) {
                    this.InningID = InningID;
                }

                public int getMatches() {
                    return Matches;
                }

                public void setMatches(int Matches) {
                    this.Matches = Matches;
                }

                public int getInnings() {
                    return Innings;
                }

                public void setInnings(int Innings) {
                    this.Innings = Innings;
                }

                public int getNotOut() {
                    return NotOut;
                }

                public void setNotOut(int NotOut) {
                    this.NotOut = NotOut;
                }

                public int getRuns() {
                    return Runs;
                }

                public void setRuns(int Runs) {
                    this.Runs = Runs;
                }

                public int getBalls() {
                    return Balls;
                }

                public void setBalls(int Balls) {
                    this.Balls = Balls;
                }

                public String getHighestScore() {
                    return HighestScore;
                }

                public void setHighestScore(String HighestScore) {
                    this.HighestScore = HighestScore;
                }

                public int getHundreds() {
                    return Hundreds;
                }

                public void setHundreds(int Hundreds) {
                    this.Hundreds = Hundreds;
                }

                public int getFifties() {
                    return Fifties;
                }

                public void setFifties(int Fifties) {
                    this.Fifties = Fifties;
                }

                public int getFours() {
                    return Fours;
                }

                public void setFours(int Fours) {
                    this.Fours = Fours;
                }

                public int getSixes() {
                    return Sixes;
                }

                public void setSixes(int Sixes) {
                    this.Sixes = Sixes;
                }

                public String getAverage() {
                    return Average;
                }

                public void setAverage(String Average) {
                    this.Average = Average;
                }

                public String getStrikeRate() {
                    return StrikeRate;
                }

                public void setStrikeRate(String StrikeRate) {
                    this.StrikeRate = StrikeRate;
                }

                public int getCatches() {
                    return Catches;
                }

                public void setCatches(int Catches) {
                    this.Catches = Catches;
                }

                public int getStumpings() {
                    return Stumpings;
                }

                public void setStumpings(int Stumpings) {
                    this.Stumpings = Stumpings;
                }
            }
        }

        public static class PlayerBowlingStatsBean {
            /**
             * Test : {"MatchID":0,"InningID":0,"Matches":13,"Innings":0,"Balls":0,"Overs":"","Runs":0,"Wickets":0,"BestInning":"","BestMatch":"","Economy":"","Average":"","StrikeRate":"","FourPlusWicketsInSingleInning":0,"FivePlusWicketsInSingleInning":0,"TenPlusWicketsInSingleInning":0}
             * ODI : {"MatchID":0,"InningID":0,"Matches":25,"Innings":0,"Balls":0,"Overs":"","Runs":0,"Wickets":0,"BestInning":"","BestMatch":"","Economy":"","Average":"","StrikeRate":"","FourPlusWicketsInSingleInning":0,"FivePlusWicketsInSingleInning":0,"TenPlusWicketsInSingleInning":0}
             * T20i : {"MatchID":0,"InningID":0,"Matches":11,"Innings":0,"Balls":0,"Overs":"","Runs":0,"Wickets":0,"BestInning":"","BestMatch":"","Economy":"","Average":"","StrikeRate":"","FourPlusWicketsInSingleInning":0,"FivePlusWicketsInSingleInning":0,"TenPlusWicketsInSingleInning":0}
             * T20 : {"MatchID":0,"InningID":0,"Matches":28,"Innings":0,"Balls":0,"Overs":"","Runs":0,"Wickets":0,"BestInning":"","BestMatch":"","Economy":"","Average":"","StrikeRate":"","FourPlusWicketsInSingleInning":0,"FivePlusWicketsInSingleInning":0,"TenPlusWicketsInSingleInning":0}
             * ListA : {"MatchID":0,"InningID":0,"Matches":41,"Innings":0,"Balls":0,"Overs":"","Runs":0,"Wickets":0,"BestInning":"","BestMatch":"","Economy":"","Average":"","StrikeRate":"","FourPlusWicketsInSingleInning":0,"FivePlusWicketsInSingleInning":0,"TenPlusWicketsInSingleInning":0}
             * FirstClass : {"MatchID":0,"InningID":0,"Matches":34,"Innings":0,"Balls":0,"Overs":"","Runs":0,"Wickets":0,"BestInning":"","BestMatch":"","Economy":"","Average":"","StrikeRate":"","FourPlusWicketsInSingleInning":0,"FivePlusWicketsInSingleInning":0,"TenPlusWicketsInSingleInning":0}
             */

            private TestBeanX Test;
            private ODIBeanX ODI;
            private T20iBeanX T20i;
            private T20BeanX T20;
            private ListABeanX ListA;
            private FirstClassBeanX FirstClass;

            public TestBeanX getTest() {
                return Test;
            }

            public void setTest(TestBeanX Test) {
                this.Test = Test;
            }

            public ODIBeanX getODI() {
                return ODI;
            }

            public void setODI(ODIBeanX ODI) {
                this.ODI = ODI;
            }

            public T20iBeanX getT20i() {
                return T20i;
            }

            public void setT20i(T20iBeanX T20i) {
                this.T20i = T20i;
            }

            public T20BeanX getT20() {
                return T20;
            }

            public void setT20(T20BeanX T20) {
                this.T20 = T20;
            }

            public ListABeanX getListA() {
                return ListA;
            }

            public void setListA(ListABeanX ListA) {
                this.ListA = ListA;
            }

            public FirstClassBeanX getFirstClass() {
                return FirstClass;
            }

            public void setFirstClass(FirstClassBeanX FirstClass) {
                this.FirstClass = FirstClass;
            }

            public static class TestBeanX {
                /**
                 * MatchID : 0
                 * InningID : 0
                 * Matches : 13
                 * Innings : 0
                 * Balls : 0
                 * Overs :
                 * Runs : 0
                 * Wickets : 0
                 * BestInning :
                 * BestMatch :
                 * Economy :
                 * Average :
                 * StrikeRate :
                 * FourPlusWicketsInSingleInning : 0
                 * FivePlusWicketsInSingleInning : 0
                 * TenPlusWicketsInSingleInning : 0
                 */

                private int MatchID;
                private int InningID;
                private int Matches;
                private int Innings;
                private int Balls;
                private String Overs;
                private int Runs;
                private int Wickets;
                private String BestInning;
                private String BestMatch;
                private String Economy;
                private String Average;
                private String StrikeRate;
                private int FourPlusWicketsInSingleInning;
                private int FivePlusWicketsInSingleInning;
                private int TenPlusWicketsInSingleInning;

                public int getMatchID() {
                    return MatchID;
                }

                public void setMatchID(int MatchID) {
                    this.MatchID = MatchID;
                }

                public int getInningID() {
                    return InningID;
                }

                public void setInningID(int InningID) {
                    this.InningID = InningID;
                }

                public int getMatches() {
                    return Matches;
                }

                public void setMatches(int Matches) {
                    this.Matches = Matches;
                }

                public int getInnings() {
                    return Innings;
                }

                public void setInnings(int Innings) {
                    this.Innings = Innings;
                }

                public int getBalls() {
                    return Balls;
                }

                public void setBalls(int Balls) {
                    this.Balls = Balls;
                }

                public String getOvers() {
                    return Overs;
                }

                public void setOvers(String Overs) {
                    this.Overs = Overs;
                }

                public int getRuns() {
                    return Runs;
                }

                public void setRuns(int Runs) {
                    this.Runs = Runs;
                }

                public int getWickets() {
                    return Wickets;
                }

                public void setWickets(int Wickets) {
                    this.Wickets = Wickets;
                }

                public String getBestInning() {
                    return BestInning;
                }

                public void setBestInning(String BestInning) {
                    this.BestInning = BestInning;
                }

                public String getBestMatch() {
                    return BestMatch;
                }

                public void setBestMatch(String BestMatch) {
                    this.BestMatch = BestMatch;
                }

                public String getEconomy() {
                    return Economy;
                }

                public void setEconomy(String Economy) {
                    this.Economy = Economy;
                }

                public String getAverage() {
                    return Average;
                }

                public void setAverage(String Average) {
                    this.Average = Average;
                }

                public String getStrikeRate() {
                    return StrikeRate;
                }

                public void setStrikeRate(String StrikeRate) {
                    this.StrikeRate = StrikeRate;
                }

                public int getFourPlusWicketsInSingleInning() {
                    return FourPlusWicketsInSingleInning;
                }

                public void setFourPlusWicketsInSingleInning(int FourPlusWicketsInSingleInning) {
                    this.FourPlusWicketsInSingleInning = FourPlusWicketsInSingleInning;
                }

                public int getFivePlusWicketsInSingleInning() {
                    return FivePlusWicketsInSingleInning;
                }

                public void setFivePlusWicketsInSingleInning(int FivePlusWicketsInSingleInning) {
                    this.FivePlusWicketsInSingleInning = FivePlusWicketsInSingleInning;
                }

                public int getTenPlusWicketsInSingleInning() {
                    return TenPlusWicketsInSingleInning;
                }

                public void setTenPlusWicketsInSingleInning(int TenPlusWicketsInSingleInning) {
                    this.TenPlusWicketsInSingleInning = TenPlusWicketsInSingleInning;
                }
            }

            public static class ODIBeanX {
                /**
                 * MatchID : 0
                 * InningID : 0
                 * Matches : 25
                 * Innings : 0
                 * Balls : 0
                 * Overs :
                 * Runs : 0
                 * Wickets : 0
                 * BestInning :
                 * BestMatch :
                 * Economy :
                 * Average :
                 * StrikeRate :
                 * FourPlusWicketsInSingleInning : 0
                 * FivePlusWicketsInSingleInning : 0
                 * TenPlusWicketsInSingleInning : 0
                 */

                private int MatchID;
                private int InningID;
                private int Matches;
                private int Innings;
                private int Balls;
                private String Overs;
                private int Runs;
                private int Wickets;
                private String BestInning;
                private String BestMatch;
                private String Economy;
                private String Average;
                private String StrikeRate;
                private int FourPlusWicketsInSingleInning;
                private int FivePlusWicketsInSingleInning;
                private int TenPlusWicketsInSingleInning;

                public int getMatchID() {
                    return MatchID;
                }

                public void setMatchID(int MatchID) {
                    this.MatchID = MatchID;
                }

                public int getInningID() {
                    return InningID;
                }

                public void setInningID(int InningID) {
                    this.InningID = InningID;
                }

                public int getMatches() {
                    return Matches;
                }

                public void setMatches(int Matches) {
                    this.Matches = Matches;
                }

                public int getInnings() {
                    return Innings;
                }

                public void setInnings(int Innings) {
                    this.Innings = Innings;
                }

                public int getBalls() {
                    return Balls;
                }

                public void setBalls(int Balls) {
                    this.Balls = Balls;
                }

                public String getOvers() {
                    return Overs;
                }

                public void setOvers(String Overs) {
                    this.Overs = Overs;
                }

                public int getRuns() {
                    return Runs;
                }

                public void setRuns(int Runs) {
                    this.Runs = Runs;
                }

                public int getWickets() {
                    return Wickets;
                }

                public void setWickets(int Wickets) {
                    this.Wickets = Wickets;
                }

                public String getBestInning() {
                    return BestInning;
                }

                public void setBestInning(String BestInning) {
                    this.BestInning = BestInning;
                }

                public String getBestMatch() {
                    return BestMatch;
                }

                public void setBestMatch(String BestMatch) {
                    this.BestMatch = BestMatch;
                }

                public String getEconomy() {
                    return Economy;
                }

                public void setEconomy(String Economy) {
                    this.Economy = Economy;
                }

                public String getAverage() {
                    return Average;
                }

                public void setAverage(String Average) {
                    this.Average = Average;
                }

                public String getStrikeRate() {
                    return StrikeRate;
                }

                public void setStrikeRate(String StrikeRate) {
                    this.StrikeRate = StrikeRate;
                }

                public int getFourPlusWicketsInSingleInning() {
                    return FourPlusWicketsInSingleInning;
                }

                public void setFourPlusWicketsInSingleInning(int FourPlusWicketsInSingleInning) {
                    this.FourPlusWicketsInSingleInning = FourPlusWicketsInSingleInning;
                }

                public int getFivePlusWicketsInSingleInning() {
                    return FivePlusWicketsInSingleInning;
                }

                public void setFivePlusWicketsInSingleInning(int FivePlusWicketsInSingleInning) {
                    this.FivePlusWicketsInSingleInning = FivePlusWicketsInSingleInning;
                }

                public int getTenPlusWicketsInSingleInning() {
                    return TenPlusWicketsInSingleInning;
                }

                public void setTenPlusWicketsInSingleInning(int TenPlusWicketsInSingleInning) {
                    this.TenPlusWicketsInSingleInning = TenPlusWicketsInSingleInning;
                }
            }

            public static class T20iBeanX {
                /**
                 * MatchID : 0
                 * InningID : 0
                 * Matches : 11
                 * Innings : 0
                 * Balls : 0
                 * Overs :
                 * Runs : 0
                 * Wickets : 0
                 * BestInning :
                 * BestMatch :
                 * Economy :
                 * Average :
                 * StrikeRate :
                 * FourPlusWicketsInSingleInning : 0
                 * FivePlusWicketsInSingleInning : 0
                 * TenPlusWicketsInSingleInning : 0
                 */

                private int MatchID;
                private int InningID;
                private int Matches;
                private int Innings;
                private int Balls;
                private String Overs;
                private int Runs;
                private int Wickets;
                private String BestInning;
                private String BestMatch;
                private String Economy;
                private String Average;
                private String StrikeRate;
                private int FourPlusWicketsInSingleInning;
                private int FivePlusWicketsInSingleInning;
                private int TenPlusWicketsInSingleInning;

                public int getMatchID() {
                    return MatchID;
                }

                public void setMatchID(int MatchID) {
                    this.MatchID = MatchID;
                }

                public int getInningID() {
                    return InningID;
                }

                public void setInningID(int InningID) {
                    this.InningID = InningID;
                }

                public int getMatches() {
                    return Matches;
                }

                public void setMatches(int Matches) {
                    this.Matches = Matches;
                }

                public int getInnings() {
                    return Innings;
                }

                public void setInnings(int Innings) {
                    this.Innings = Innings;
                }

                public int getBalls() {
                    return Balls;
                }

                public void setBalls(int Balls) {
                    this.Balls = Balls;
                }

                public String getOvers() {
                    return Overs;
                }

                public void setOvers(String Overs) {
                    this.Overs = Overs;
                }

                public int getRuns() {
                    return Runs;
                }

                public void setRuns(int Runs) {
                    this.Runs = Runs;
                }

                public int getWickets() {
                    return Wickets;
                }

                public void setWickets(int Wickets) {
                    this.Wickets = Wickets;
                }

                public String getBestInning() {
                    return BestInning;
                }

                public void setBestInning(String BestInning) {
                    this.BestInning = BestInning;
                }

                public String getBestMatch() {
                    return BestMatch;
                }

                public void setBestMatch(String BestMatch) {
                    this.BestMatch = BestMatch;
                }

                public String getEconomy() {
                    return Economy;
                }

                public void setEconomy(String Economy) {
                    this.Economy = Economy;
                }

                public String getAverage() {
                    return Average;
                }

                public void setAverage(String Average) {
                    this.Average = Average;
                }

                public String getStrikeRate() {
                    return StrikeRate;
                }

                public void setStrikeRate(String StrikeRate) {
                    this.StrikeRate = StrikeRate;
                }

                public int getFourPlusWicketsInSingleInning() {
                    return FourPlusWicketsInSingleInning;
                }

                public void setFourPlusWicketsInSingleInning(int FourPlusWicketsInSingleInning) {
                    this.FourPlusWicketsInSingleInning = FourPlusWicketsInSingleInning;
                }

                public int getFivePlusWicketsInSingleInning() {
                    return FivePlusWicketsInSingleInning;
                }

                public void setFivePlusWicketsInSingleInning(int FivePlusWicketsInSingleInning) {
                    this.FivePlusWicketsInSingleInning = FivePlusWicketsInSingleInning;
                }

                public int getTenPlusWicketsInSingleInning() {
                    return TenPlusWicketsInSingleInning;
                }

                public void setTenPlusWicketsInSingleInning(int TenPlusWicketsInSingleInning) {
                    this.TenPlusWicketsInSingleInning = TenPlusWicketsInSingleInning;
                }
            }

            public static class T20BeanX {
                /**
                 * MatchID : 0
                 * InningID : 0
                 * Matches : 28
                 * Innings : 0
                 * Balls : 0
                 * Overs :
                 * Runs : 0
                 * Wickets : 0
                 * BestInning :
                 * BestMatch :
                 * Economy :
                 * Average :
                 * StrikeRate :
                 * FourPlusWicketsInSingleInning : 0
                 * FivePlusWicketsInSingleInning : 0
                 * TenPlusWicketsInSingleInning : 0
                 */

                private int MatchID;
                private int InningID;
                private int Matches;
                private int Innings;
                private int Balls;
                private String Overs;
                private int Runs;
                private int Wickets;
                private String BestInning;
                private String BestMatch;
                private String Economy;
                private String Average;
                private String StrikeRate;
                private int FourPlusWicketsInSingleInning;
                private int FivePlusWicketsInSingleInning;
                private int TenPlusWicketsInSingleInning;

                public int getMatchID() {
                    return MatchID;
                }

                public void setMatchID(int MatchID) {
                    this.MatchID = MatchID;
                }

                public int getInningID() {
                    return InningID;
                }

                public void setInningID(int InningID) {
                    this.InningID = InningID;
                }

                public int getMatches() {
                    return Matches;
                }

                public void setMatches(int Matches) {
                    this.Matches = Matches;
                }

                public int getInnings() {
                    return Innings;
                }

                public void setInnings(int Innings) {
                    this.Innings = Innings;
                }

                public int getBalls() {
                    return Balls;
                }

                public void setBalls(int Balls) {
                    this.Balls = Balls;
                }

                public String getOvers() {
                    return Overs;
                }

                public void setOvers(String Overs) {
                    this.Overs = Overs;
                }

                public int getRuns() {
                    return Runs;
                }

                public void setRuns(int Runs) {
                    this.Runs = Runs;
                }

                public int getWickets() {
                    return Wickets;
                }

                public void setWickets(int Wickets) {
                    this.Wickets = Wickets;
                }

                public String getBestInning() {
                    return BestInning;
                }

                public void setBestInning(String BestInning) {
                    this.BestInning = BestInning;
                }

                public String getBestMatch() {
                    return BestMatch;
                }

                public void setBestMatch(String BestMatch) {
                    this.BestMatch = BestMatch;
                }

                public String getEconomy() {
                    return Economy;
                }

                public void setEconomy(String Economy) {
                    this.Economy = Economy;
                }

                public String getAverage() {
                    return Average;
                }

                public void setAverage(String Average) {
                    this.Average = Average;
                }

                public String getStrikeRate() {
                    return StrikeRate;
                }

                public void setStrikeRate(String StrikeRate) {
                    this.StrikeRate = StrikeRate;
                }

                public int getFourPlusWicketsInSingleInning() {
                    return FourPlusWicketsInSingleInning;
                }

                public void setFourPlusWicketsInSingleInning(int FourPlusWicketsInSingleInning) {
                    this.FourPlusWicketsInSingleInning = FourPlusWicketsInSingleInning;
                }

                public int getFivePlusWicketsInSingleInning() {
                    return FivePlusWicketsInSingleInning;
                }

                public void setFivePlusWicketsInSingleInning(int FivePlusWicketsInSingleInning) {
                    this.FivePlusWicketsInSingleInning = FivePlusWicketsInSingleInning;
                }

                public int getTenPlusWicketsInSingleInning() {
                    return TenPlusWicketsInSingleInning;
                }

                public void setTenPlusWicketsInSingleInning(int TenPlusWicketsInSingleInning) {
                    this.TenPlusWicketsInSingleInning = TenPlusWicketsInSingleInning;
                }
            }

            public static class ListABeanX {
                /**
                 * MatchID : 0
                 * InningID : 0
                 * Matches : 41
                 * Innings : 0
                 * Balls : 0
                 * Overs :
                 * Runs : 0
                 * Wickets : 0
                 * BestInning :
                 * BestMatch :
                 * Economy :
                 * Average :
                 * StrikeRate :
                 * FourPlusWicketsInSingleInning : 0
                 * FivePlusWicketsInSingleInning : 0
                 * TenPlusWicketsInSingleInning : 0
                 */

                private int MatchID;
                private int InningID;
                private int Matches;
                private int Innings;
                private int Balls;
                private String Overs;
                private int Runs;
                private int Wickets;
                private String BestInning;
                private String BestMatch;
                private String Economy;
                private String Average;
                private String StrikeRate;
                private int FourPlusWicketsInSingleInning;
                private int FivePlusWicketsInSingleInning;
                private int TenPlusWicketsInSingleInning;

                public int getMatchID() {
                    return MatchID;
                }

                public void setMatchID(int MatchID) {
                    this.MatchID = MatchID;
                }

                public int getInningID() {
                    return InningID;
                }

                public void setInningID(int InningID) {
                    this.InningID = InningID;
                }

                public int getMatches() {
                    return Matches;
                }

                public void setMatches(int Matches) {
                    this.Matches = Matches;
                }

                public int getInnings() {
                    return Innings;
                }

                public void setInnings(int Innings) {
                    this.Innings = Innings;
                }

                public int getBalls() {
                    return Balls;
                }

                public void setBalls(int Balls) {
                    this.Balls = Balls;
                }

                public String getOvers() {
                    return Overs;
                }

                public void setOvers(String Overs) {
                    this.Overs = Overs;
                }

                public int getRuns() {
                    return Runs;
                }

                public void setRuns(int Runs) {
                    this.Runs = Runs;
                }

                public int getWickets() {
                    return Wickets;
                }

                public void setWickets(int Wickets) {
                    this.Wickets = Wickets;
                }

                public String getBestInning() {
                    return BestInning;
                }

                public void setBestInning(String BestInning) {
                    this.BestInning = BestInning;
                }

                public String getBestMatch() {
                    return BestMatch;
                }

                public void setBestMatch(String BestMatch) {
                    this.BestMatch = BestMatch;
                }

                public String getEconomy() {
                    return Economy;
                }

                public void setEconomy(String Economy) {
                    this.Economy = Economy;
                }

                public String getAverage() {
                    return Average;
                }

                public void setAverage(String Average) {
                    this.Average = Average;
                }

                public String getStrikeRate() {
                    return StrikeRate;
                }

                public void setStrikeRate(String StrikeRate) {
                    this.StrikeRate = StrikeRate;
                }

                public int getFourPlusWicketsInSingleInning() {
                    return FourPlusWicketsInSingleInning;
                }

                public void setFourPlusWicketsInSingleInning(int FourPlusWicketsInSingleInning) {
                    this.FourPlusWicketsInSingleInning = FourPlusWicketsInSingleInning;
                }

                public int getFivePlusWicketsInSingleInning() {
                    return FivePlusWicketsInSingleInning;
                }

                public void setFivePlusWicketsInSingleInning(int FivePlusWicketsInSingleInning) {
                    this.FivePlusWicketsInSingleInning = FivePlusWicketsInSingleInning;
                }

                public int getTenPlusWicketsInSingleInning() {
                    return TenPlusWicketsInSingleInning;
                }

                public void setTenPlusWicketsInSingleInning(int TenPlusWicketsInSingleInning) {
                    this.TenPlusWicketsInSingleInning = TenPlusWicketsInSingleInning;
                }
            }

            public static class FirstClassBeanX {
                /**
                 * MatchID : 0
                 * InningID : 0
                 * Matches : 34
                 * Innings : 0
                 * Balls : 0
                 * Overs :
                 * Runs : 0
                 * Wickets : 0
                 * BestInning :
                 * BestMatch :
                 * Economy :
                 * Average :
                 * StrikeRate :
                 * FourPlusWicketsInSingleInning : 0
                 * FivePlusWicketsInSingleInning : 0
                 * TenPlusWicketsInSingleInning : 0
                 */

                private int MatchID;
                private int InningID;
                private int Matches;
                private int Innings;
                private int Balls;
                private String Overs;
                private int Runs;
                private int Wickets;
                private String BestInning;
                private String BestMatch;
                private String Economy;
                private String Average;
                private String StrikeRate;
                private int FourPlusWicketsInSingleInning;
                private int FivePlusWicketsInSingleInning;
                private int TenPlusWicketsInSingleInning;

                public int getMatchID() {
                    return MatchID;
                }

                public void setMatchID(int MatchID) {
                    this.MatchID = MatchID;
                }

                public int getInningID() {
                    return InningID;
                }

                public void setInningID(int InningID) {
                    this.InningID = InningID;
                }

                public int getMatches() {
                    return Matches;
                }

                public void setMatches(int Matches) {
                    this.Matches = Matches;
                }

                public int getInnings() {
                    return Innings;
                }

                public void setInnings(int Innings) {
                    this.Innings = Innings;
                }

                public int getBalls() {
                    return Balls;
                }

                public void setBalls(int Balls) {
                    this.Balls = Balls;
                }

                public String getOvers() {
                    return Overs;
                }

                public void setOvers(String Overs) {
                    this.Overs = Overs;
                }

                public int getRuns() {
                    return Runs;
                }

                public void setRuns(int Runs) {
                    this.Runs = Runs;
                }

                public int getWickets() {
                    return Wickets;
                }

                public void setWickets(int Wickets) {
                    this.Wickets = Wickets;
                }

                public String getBestInning() {
                    return BestInning;
                }

                public void setBestInning(String BestInning) {
                    this.BestInning = BestInning;
                }

                public String getBestMatch() {
                    return BestMatch;
                }

                public void setBestMatch(String BestMatch) {
                    this.BestMatch = BestMatch;
                }

                public String getEconomy() {
                    return Economy;
                }

                public void setEconomy(String Economy) {
                    this.Economy = Economy;
                }

                public String getAverage() {
                    return Average;
                }

                public void setAverage(String Average) {
                    this.Average = Average;
                }

                public String getStrikeRate() {
                    return StrikeRate;
                }

                public void setStrikeRate(String StrikeRate) {
                    this.StrikeRate = StrikeRate;
                }

                public int getFourPlusWicketsInSingleInning() {
                    return FourPlusWicketsInSingleInning;
                }

                public void setFourPlusWicketsInSingleInning(int FourPlusWicketsInSingleInning) {
                    this.FourPlusWicketsInSingleInning = FourPlusWicketsInSingleInning;
                }

                public int getFivePlusWicketsInSingleInning() {
                    return FivePlusWicketsInSingleInning;
                }

                public void setFivePlusWicketsInSingleInning(int FivePlusWicketsInSingleInning) {
                    this.FivePlusWicketsInSingleInning = FivePlusWicketsInSingleInning;
                }

                public int getTenPlusWicketsInSingleInning() {
                    return TenPlusWicketsInSingleInning;
                }

                public void setTenPlusWicketsInSingleInning(int TenPlusWicketsInSingleInning) {
                    this.TenPlusWicketsInSingleInning = TenPlusWicketsInSingleInning;
                }
            }
        }



        public static class CurrentBidUserBean{

            /**
             * BidCredit : 100000
             * PlayerID : 353476
             * UserID : 373319
             * DateTime : 2019-05-18 11:13:49
             * FirstName : Rahul pareta
             * UserGUID : d4f418ea-135e-dca0-5c07-c013c49c919d
             */

            private String BidCredit;
            private String PlayerID;
            private String UserID;
            private String DateTime;
            private String FirstName;
            private String UserGUID;

            public String getBidCredit() {
                return BidCredit;
            }

            public void setBidCredit(String BidCredit) {
                this.BidCredit = BidCredit;
            }

            public String getPlayerID() {
                return PlayerID;
            }

            public void setPlayerID(String PlayerID) {
                this.PlayerID = PlayerID;
            }

            public String getUserID() {
                return UserID;
            }

            public void setUserID(String UserID) {
                this.UserID = UserID;
            }

            public String getDateTime() {
                return DateTime;
            }

            public void setDateTime(String DateTime) {
                this.DateTime = DateTime;
            }

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
        }


        public static class CurrentBidPlayerTime{

            /**
             * PlayerID : 55708
             * DateTime : 2019-09-04 06:25:24
             */

            private String PlayerID;
            private String DateTime;

            public String getPlayerID() {
                return PlayerID;
            }

            public void setPlayerID(String PlayerID) {
                this.PlayerID = PlayerID;
            }

            public String getDateTime() {
                return DateTime;
            }

            public void setDateTime(String DateTime) {
                this.DateTime = DateTime;
            }
        }
    }
}
