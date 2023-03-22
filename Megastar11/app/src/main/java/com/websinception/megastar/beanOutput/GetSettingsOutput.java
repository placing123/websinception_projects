package com.websinception.megastar.beanOutput;

import java.util.ArrayList;

public class GetSettingsOutput {

    private int ResponseCode;
    private String Message;
    private DataBean Data;


    public int getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(int responseCode) {
        ResponseCode = responseCode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean data) {
        Data = data;
    }

    public class DataBean{
        private String TotalRecords;
        private ArrayList<RecordBean> Records;

        public String getTotalRecords() {
            return TotalRecords;
        }

        public void setTotalRecords(String totalRecords) {
            TotalRecords = totalRecords;
        }

        public ArrayList<RecordBean> getRecords() {
            return Records;
        }

        public void setRecords(ArrayList<RecordBean> records) {
            Records = records;
        }
    }


    public class RecordBean{
        private String ConfigTypeGUID;
        private String ConfigTypeDescprition;
        private String ConfigTypeValue;
        private String Status;


        public String getConfigTypeGUID() {
            return ConfigTypeGUID;
        }

        public void setConfigTypeGUID(String configTypeGUID) {
            ConfigTypeGUID = configTypeGUID;
        }

        public String getConfigTypeDescprition() {
            return ConfigTypeDescprition;
        }

        public void setConfigTypeDescprition(String configTypeDescprition) {
            ConfigTypeDescprition = configTypeDescprition;
        }

        public String getConfigTypeValue() {
            return ConfigTypeValue;
        }

        public void setConfigTypeValue(String configTypeValue) {
            ConfigTypeValue = configTypeValue;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }
    }
}
