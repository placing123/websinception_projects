package com.mw.fantasy.beanOutput;

public class SimpleOutput {

    /**
     * ResponseCode : 200
     * Message : Team updated successfully.
     */

    private int ResponseCode;
    private String Message;

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
}
