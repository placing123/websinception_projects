package com.mw.fantasy.beanOutput;

public class AddPlayerInAssistantOutput {

    /**
     * ResponseCode : 200
     * Message : Team created successfully.
     * Data : {"UserTeamGUID":"b81862f5-8f04-d811-adcc-e6756b1b3648"}
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
         * UserTeamGUID : b81862f5-8f04-d811-adcc-e6756b1b3648
         */

        private String UserTeamGUID;

        public String getUserTeamGUID() {
            return UserTeamGUID;
        }

        public void setUserTeamGUID(String UserTeamGUID) {
            this.UserTeamGUID = UserTeamGUID;
        }
    }
}
