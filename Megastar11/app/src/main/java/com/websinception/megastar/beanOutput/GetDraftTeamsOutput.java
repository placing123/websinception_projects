package com.websinception.megastar.beanOutput;

import java.util.List;

public class GetDraftTeamsOutput {

    /**
     * ResponseCode : 200
     * Message : Team successfully found.
     * Data : {"Records":[{"TeamID":"352209","TeamName":"India","TeamNameShort":"IND"},{"TeamID":"352205","TeamName":"Bangladesh","TeamNameShort":"BDESH"},{"TeamID":"352203","TeamName":"Afghanistan","TeamNameShort":"AFG"},{"TeamID":"352201","TeamName":"Sri Lanka","TeamNameShort":"SL"},{"TeamID":"352200","TeamName":"New Zealand","TeamNameShort":"NZ"},{"TeamID":"352198","TeamName":"West Indies","TeamNameShort":"WI"},{"TeamID":"352197","TeamName":"Pakistan","TeamNameShort":"PAK"},{"TeamID":"352195","TeamName":"South Africa","TeamNameShort":"SA"},{"TeamID":"352187","TeamName":"Australia","TeamNameShort":"AUS"},{"TeamID":"352186","TeamName":"England","TeamNameShort":"ENG"}],"TotalRecords":10}
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
         * Records : [{"TeamID":"352209","TeamName":"India","TeamNameShort":"IND"},{"TeamID":"352205","TeamName":"Bangladesh","TeamNameShort":"BDESH"},{"TeamID":"352203","TeamName":"Afghanistan","TeamNameShort":"AFG"},{"TeamID":"352201","TeamName":"Sri Lanka","TeamNameShort":"SL"},{"TeamID":"352200","TeamName":"New Zealand","TeamNameShort":"NZ"},{"TeamID":"352198","TeamName":"West Indies","TeamNameShort":"WI"},{"TeamID":"352197","TeamName":"Pakistan","TeamNameShort":"PAK"},{"TeamID":"352195","TeamName":"South Africa","TeamNameShort":"SA"},{"TeamID":"352187","TeamName":"Australia","TeamNameShort":"AUS"},{"TeamID":"352186","TeamName":"England","TeamNameShort":"ENG"}]
         * TotalRecords : 10
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
             * TeamID : 352209
             * TeamName : India
             * TeamNameShort : IND
             */

            private String TeamID;
            private String TeamName;
            private String TeamNameShort;

            public String getTeamID() {
                return TeamID;
            }

            public void setTeamID(String TeamID) {
                this.TeamID = TeamID;
            }

            public String getTeamName() {
                return TeamName;
            }

            public void setTeamName(String TeamName) {
                this.TeamName = TeamName;
            }

            public String getTeamNameShort() {
                return TeamNameShort;
            }

            public void setTeamNameShort(String TeamNameShort) {
                this.TeamNameShort = TeamNameShort;
            }

            @Override
            public String toString() {
                return TeamNameShort;
            }
        }
    }
}
