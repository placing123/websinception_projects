package com.websinception.megastar.beanOutput;

public class JoinAuctionOutput {


    /**
     * ResponseCode : 200
     * Message : Contest joined successfully.
     * Data : {"WalletAmount":"2101.31","WinningAmount":"0.00","CashBonus":"0.00","TotalCash":"2101.31"}
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
         * WalletAmount : 2101.31
         * WinningAmount : 0.00
         * CashBonus : 0.00
         * TotalCash : 2101.31
         */

        private String WalletAmount;
        private String WinningAmount;
        private String CashBonus;
        private String TotalCash;

        public String getWalletAmount() {
            return WalletAmount;
        }

        public void setWalletAmount(String WalletAmount) {
            this.WalletAmount = WalletAmount;
        }

        public String getWinningAmount() {
            return WinningAmount;
        }

        public void setWinningAmount(String WinningAmount) {
            this.WinningAmount = WinningAmount;
        }

        public String getCashBonus() {
            return CashBonus;
        }

        public void setCashBonus(String CashBonus) {
            this.CashBonus = CashBonus;
        }

        public String getTotalCash() {
            return TotalCash;
        }

        public void setTotalCash(String TotalCash) {
            this.TotalCash = TotalCash;
        }
    }
}
