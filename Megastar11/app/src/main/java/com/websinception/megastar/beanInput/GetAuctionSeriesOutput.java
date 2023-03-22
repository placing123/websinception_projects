package com.websinception.megastar.beanInput;

import java.util.List;

public class GetAuctionSeriesOutput {

    /**
     * ResponseCode : 200
     * Message : Success
     * Data : {"TotalRecords":1,"Records":[{"SeriesGUID":"db856266-3f6a-bef6-239c-3f67f3e87019","SeriesName":"Indian Premier League","StatusID":"2","SeriesStartDate":"2019-03-23 05:30:00","Status":"Active","SeriesID":"371043"}]}
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
         * TotalRecords : 1
         * Records : [{"SeriesGUID":"db856266-3f6a-bef6-239c-3f67f3e87019","SeriesName":"Indian Premier League","StatusID":"2","SeriesStartDate":"2019-03-23 05:30:00","Status":"Active","SeriesID":"371043"}]
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
             * SeriesGUID : db856266-3f6a-bef6-239c-3f67f3e87019
             * SeriesName : Indian Premier League
             * StatusID : 2
             * SeriesStartDate : 2019-03-23 05:30:00
             * Status : Active
             * SeriesID : 371043
             */

            /*private String SeriesGUID;*/
            private String RoundID;
            private String SeriesName;
            private String StatusID;
            private String SeriesStartDate;
            private String SeriesMatchStartDate;
            private String Status;
            private String SeriesID;
            private String SeriesEndDateUTC;

            public String getSeriesEndDateUTC() {
                return SeriesEndDateUTC;
            }

            public void setSeriesEndDateUTC(String seriesEndDateUTC) {
                SeriesEndDateUTC = seriesEndDateUTC;
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

            public String getSeriesName() {
                return SeriesName;
            }

            public void setSeriesName(String SeriesName) {
                this.SeriesName = SeriesName;
            }

            public String getStatusID() {
                return StatusID;
            }

            public void setStatusID(String StatusID) {
                this.StatusID = StatusID;
            }

            public String getSeriesStartDate() {
                return SeriesStartDate;
            }

            public void setSeriesStartDate(String SeriesStartDate) {
                this.SeriesStartDate = SeriesStartDate;
            }

            public String getStatus() {
                return Status;
            }

            public void setStatus(String Status) {
                this.Status = Status;
            }

            public String getSeriesID() {
                return SeriesID;
            }

            public void setSeriesID(String SeriesID) {
                this.SeriesID = SeriesID;
            }

            public String getSeriesMatchStartDate() {
                return SeriesMatchStartDate;
            }

            public void setSeriesMatchStartDate(String seriesMatchStartDate) {
                SeriesMatchStartDate = seriesMatchStartDate;
            }
        }
    }
}
