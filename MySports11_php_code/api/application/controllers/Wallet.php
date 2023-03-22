<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Wallet extends API_Controller_Secure {

    function __construct() {
        parent::__construct();
        $this->load->model('Users_model');
    }

    /*
      Name: 			add
      Description: 	Use to add wallet cash
      URL: 			/wallet/add/
     */

    public function add_post() {
        /* Validation section */
        $this->form_validation->set_rules('RequestSource', 'RequestSource', 'trim|required|in_list[Web,Mobile]');
        $this->form_validation->set_rules('DeviceType', 'DeviceType', 'trim|in_list[iPhone,Android]');
        $this->form_validation->set_rules('CouponGUID', 'CouponGUID', 'trim|callback_validateEntityGUID[Coupon,CouponID]');
        $this->form_validation->set_rules('PaymentGateway', 'PaymentGateway', 'trim|required|in_list[PayUmoney,Paytm,Razorpay,CashFree,PhonePe]');
        $this->form_validation->set_rules('Amount', 'Amount', 'trim|required|numeric|callback_validateMinimumDepositAmount');
        $this->form_validation->set_rules('FirstName', 'FirstName', 'trim');
        $this->form_validation->set_rules('Email', 'Email', 'trim|valid_email');
        $this->form_validation->set_rules('PhoneNumber', 'PhoneNumber', 'trim|numeric');

        $this->form_validation->validation($this);  /* Run validation */
        /* Validation - ends */

        $PaymentResponse = $this->Users_model->add($this->Post, $this->SessionUserID, @$this->CouponID);
        if (empty($PaymentResponse)) {
            $this->Return['ResponseCode'] = 500;
            $this->Return['Message'] = "An error occurred, please try again later.";
        } else {
            $this->Return['Data'] = $PaymentResponse;
            $this->Return['Message'] = "Success.";
        }
    }

    /*
      Name: 			confirm
      Description: 	Use to update payment gateway response
      URL: 			/wallet/confirm/
     */

    public function confirmWeb_post() {
        /* Validation section */
        $this->form_validation->set_rules('PaymentGateway', 'PaymentGateway', 'trim|required|in_list[PayUmoney,Paytm,Razorpay]');
        $this->form_validation->set_rules('PaymentGatewayStatus', 'PaymentGatewayStatus', 'trim|required|in_list[Success,Failed,Cancelled]');
        $this->form_validation->set_rules('WalletID', 'WalletID', 'trim|required|numeric|callback_validateWalletID');
        $this->form_validation->set_rules('PaymentGatewayResponse', 'PaymentGatewayResponse', 'trim');
        $this->form_validation->set_rules('Razor_payment_id', 'Razor_payment_id', 'trim|required');
        $this->form_validation->validation($this);  /* Run validation */
        /* Validation - ends */

        $WalletData = $this->Users_model->confirm($this->Post, $this->SessionUserID);
        if (!$WalletData) {
            $this->Return['ResponseCode'] = 500;
            $this->Return['Message'] = "An error occurred, please try again later.";
        } else {
            $this->Return['Data'] = $WalletData;
            $this->Return['Message'] = "Success.";
        }
    }

    public function confirmApp_post() {
        /* Validation section */
        $this->form_validation->set_rules('PaymentGateway', 'PaymentGateway', 'trim|required|in_list[PayUmoney,Paytm,Razorpay]');
        $this->form_validation->set_rules('PaymentGatewayStatus', 'PaymentGatewayStatus', 'trim|required|in_list[Success,Failed,Cancelled]');
        $this->form_validation->set_rules('WalletID', 'WalletID', 'trim|required|numeric|callback_validateWalletID');
        $this->form_validation->set_rules('PaymentGatewayResponse', 'PaymentGatewayResponse', 'trim');
        $this->form_validation->set_rules('Razor_payment_id', 'Razor_payment_id', 'trim');
        $this->form_validation->validation($this);  /* Run validation */
        /* Validation - ends */

        $WalletData = $this->Users_model->confirmApp($this->Post, $this->SessionUserID);
        /*if ($this->Post['PaymentGateway'] == 'Paytm') {
            $InsertData = array_filter(array(
                    "Response" => (!empty($this->Post['PaymentGatewayResponse'])) ? $this->Post['PaymentGatewayResponse'] : NULL,
                    "WalletID" => $this->Post['WalletID'],
                    "UserID" => $this->SessionUserID,
                    "PayType" => "APP",
                    'AppResponse' => json_encode($this->Post),
                    "EntryDate" => date('Y-m-d H:i:s')
                ));
            $this->db->insert('logs_paytm', $InsertData); 
        }*/
        if (!$WalletData) {
            $this->Return['ResponseCode'] = 500;
            $this->Return['Message'] = "An error occurred, please try again later.";
        } else {
            $this->Return['Data'] = $WalletData;
            $this->Return['Message'] = "Success.";
        }
    }

    /*
      Name:             statusCheckPhonePeForAPP
      Description:  Use to Check payment gateway response
      URL:          /wallet/statusCheckPhonePeForAPP/
     */
    public function statusCheckPhonePeForAPP_post() {
        /* Validation section */
        $this->form_validation->set_rules('transactionId', 'transactionId', 'trim|required');
        $this->form_validation->set_rules('OrderId', 'OrderId', 'trim|required');
        $this->form_validation->validation($this);  /* Run validation */
        /* Validation - ends */

        $StatusString =  "/v3/transaction/".PHONEPE_MERCHANT_ID."/".$this->Post['transactionId'].'/status'. PHONEPE_SALT_KEY;
        $x_Verify   = hash('sha256', $StatusString);

        $CURL_URL = PHONEPE_HOST_URL."/v3/transaction/".PHONEPE_MERCHANT_ID."/".$this->Post['transactionId']."/status";
        $crl = curl_init();      
        curl_setopt($crl, CURLOPT_URL, $CURL_URL);
        curl_setopt($crl, CURLOPT_FRESH_CONNECT, true);
        curl_setopt($crl, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($crl, CURLOPT_HTTPHEADER, array(
                "content-type: application/json",
                "x-verify: " . $x_Verify.'###'.PHONEPE_SALT_INDEX
            ));
        $Response = curl_exec($crl);
        $Error = curl_error($crl);
        curl_close($crl);
        if ($Error) {
            $PaymentResponse['ErrorMsg'] = $Error;
        } else {
            $Result = json_decode($Response,TRUE);
            $this->load->model('Users_model');
            /* Get User ID */
            $UserData = $this->db->query('SELECT `UserID`,`Amount` FROM `tbl_users_wallet` WHERE `WalletID` = ' . $this->Post['OrderId'] . ' LIMIT 1')->row();
            $PaymentResponse['PaymentGateway'] = 'PhonePe';
            $PaymentResponse['PaymentGatewayResponse'] = $Response;
            $PaymentResponse['WalletID'] = $this->Post['OrderId'];
            $InsertData = array_filter(array(
                "Content"       => $Response,
                "OrderID"       => $PaymentResponse['WalletID'],
                "UserID"        => $UserData->UserID,
                "PaymentType"   => "phonePe",
                "CaptureType"   => "API-APP",
                "CreateDate"    => date('Y-m-d H:i:s')
            ));
            //This table use for PhonePe & RazorPay
            $this->db->insert('tbl_test_razorPay', $InsertData);
            /**- validate Amount -**/ 
            if ($UserData->Amount != ($Result['data']['amount'] / 100)) {
                /* Update Transaction */
                $PaymentResponse['PaymentGatewayStatus'] = 'Failed';
                $this->Users_model->confirm($PaymentResponse, $UserData->UserID);
                $this->Return['Message'] = "Failed";
                return;
            }

            if ($Result["success"] == 1 && $Result["code"] == 'PAYMENT_SUCCESS') {
                /* Update Transaction */
                $PaymentResponse['PaymentGatewayStatus'] = 'Success';
                $PaymentResponse['Amount'] = $Result['data']['amount'] / 100;
                $this->Users_model->confirm($PaymentResponse, $UserData->UserID);
                $this->Return['Message'] = "Success";

            } else if ($Result["code"] == 'PAYMENT_PENDING' || $Result["code"] == 'INTERNAL_SERVER_ERROR') {
                /* Update Transaction */
                $this->Return['Message'] = "Pending";
            }  else {
                /* Update Transaction */
                $PaymentResponse['PaymentGatewayStatus'] = 'Failed';
                $this->Users_model->confirm($PaymentResponse, $UserData->UserID);
                $this->Return['Message'] = "Failed";
            } 
        }
    }

    /*
      Name: 			getWallet
      Description: 	To get wallet data
      URL: 			/wallet/getWallet/
     */

    public function getWallet_post() {
        $this->form_validation->set_rules('TransactionMode', 'TransactionMode', 'trim|required|in_list[All,WalletAmount,WinningAmount,CashBonus]');
        $this->form_validation->set_rules('Keyword', 'Search Keyword', 'trim');
        $this->form_validation->set_rules('OrderBy', 'OrderBy', 'trim');
        $this->form_validation->set_rules('Sequence', 'Sequence', 'trim|in_list[ASC,DESC]');
        $this->form_validation->validation($this);  /* Run validation */

        /* Get Wallet Data */
        $WalletDetails = $this->Users_model->getWallet(@$this->Post['Params'], array_merge($this->Post, array('UserID' => $this->SessionUserID)), TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']);
        if (!empty($WalletDetails)) {
            $this->Return['Data'] = $WalletDetails['Data'];
        }
    }

    /*
      Name: 			withdrawal
      Description: 	Use to withdrawal winning amount
      URL: 			/wallet/withdrawal/
     */

    public function withdrawal_post() {
        // $this->Return['ResponseCode'] = 500;
        // $this->Return['Message'] = "Unable to process withdrawal at the moment. Please try again later.";
        // exit;
        /* Validation section */
        $this->form_validation->set_rules('UserGUID', 'UserGUID', 'trim|required' . ($this->Post['PaymentGateway'] == 'Bank' ? '|callback_validateAccountStatus' : '|callback_validateAadharPan'));
        //$this->form_validation->set_rules('UserGUID', 'UserGUID', 'trim|required|callback_validateAccountStatus');
        $this->form_validation->set_rules('PaymentGateway', 'PaymentGateway', 'trim|required|in_list[Paytm,Bank]');
        $this->form_validation->set_rules('PaytmPhoneNumber', 'PaytmPhoneNumber', 'trim' . (!empty($this->Post['PaymentGateway']) && $this->Post['PaymentGateway'] == 'Paytm' ? '|required|callback_validatePhoneStatus' : ''));
        $this->form_validation->set_rules('Amount', 'Amount', 'trim|required|numeric|callback_validateWithdrawalAmount');

        $this->form_validation->validation($this);  /* Run validation */
        /* Validation - ends */

        if (!empty($this->Post['PaymentGateway']) && $this->Post['PaymentGateway'] == 'Paytm') {

            $Query = $this->db->query("SELECT UserID FROM `tbl_users_withdrawal` WHERE `UserID` = " . $this->SessionUserID . " AND DATE(EntryDate) = '".date('Y-m-d')."' AND StatusID=5 LIMIT 1");
            if ($Query->num_rows() > 0) {
                $this->Return['ResponseCode'] = 500;
                $this->Return['Message'] = "Maximum withdrawal limit 1 per day.";
                exit;
            }
            /** temp code by paytm * */
            $WalletData = $this->Users_model->withdrawal($this->Post, $this->SessionUserID);
        } else {
            $WalletData = $this->Users_model->withdrawal($this->Post, $this->SessionUserID);
        }

        if (empty($WalletData) || @$WalletData->paytmResponse->status == 'FAILURE') {
            $this->Return['ResponseCode'] = 500;
            $this->Return['Message'] = "An error occurred, please try again later.";
        } else {
            $this->Return['Data'] = $WalletData;
            $this->Return['Message'] = ($this->Post['PaymentGateway'] == 'Paytm' ? "Withdrawal success." : 'Withdrawal Request had been Sent.');
        }
    }

    /*
      Name:           withdrawal_confirm
      Description:    Use to confirm withdrawal winning amount
      URL:            /wallet/withdrawal_confirm/
     */

    public function withdrawal_confirm_post() {
        /* Validation section */
        $this->form_validation->set_rules('WithdrawalID', 'WithdrawalID', 'trim|required|callback_validateWithdrawalID');
        $this->form_validation->set_rules('OTP', 'OTP', 'trim|required');
        $this->form_validation->validation($this);  /* Run validation */
        /* Validation - ends */
        $WalletData = $this->Users_model->withdrawal($this->Post, $this->SessionUserID);
        if (empty($WalletData)) {
            $this->Return['ResponseCode'] = 500;
            $this->Return['Message'] = "An error occurred, please try again later.";
        } else {
            $this->Return['Data'] = $WalletData;
            $this->Return['Message'] = "Success.";
        }
    }

    /*
      Name: 			getWithdrawals
      Description: 	To get Withdrawal data
      URL: 			/wallet/getWithdrawals/
     */

    public function getWithdrawals_post() {
        $this->form_validation->set_rules('Keyword', 'Search Keyword', 'trim');
        $this->form_validation->set_rules('OrderBy', 'OrderBy', 'trim');
        $this->form_validation->set_rules('Sequence', 'Sequence', 'trim|in_list[ASC,DESC]');
        $this->form_validation->validation($this);  /* Run validation */

        /* Get Withdrawal Data */
        $WithdrawalsData = $this->Users_model->getWithdrawals(@$this->Post['Params'], array_merge($this->Post, array('UserID' => $this->SessionUserID)), TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']);
        if (!empty($WithdrawalsData)) {
            $this->Return['Data'] = $WithdrawalsData['Data'];
        }
    }

    /**
     * Function Name: validateWithdrawalAmount
     * Description:   To validate withdrawal amount
     */
    public function validateWithdrawalAmount($Amount) {


        if ($this->Post['PaymentGateway'] == 'Bank') {
            $MinimumWithdrawalLimit = $this->db->query('SELECT ConfigTypeValue FROM set_site_config WHERE ConfigTypeGUID = "MinimumWithdrawalLimitBank" LIMIT 1')->row()->ConfigTypeValue;
        } else {
            $MinimumWithdrawalLimit = $this->db->query('SELECT ConfigTypeValue FROM set_site_config WHERE ConfigTypeGUID = "MinimumWithdrawalLimitPaytm" LIMIT 1')->row()->ConfigTypeValue;
        }
        if ($Amount < $MinimumWithdrawalLimit) {
            $this->form_validation->set_message('validateWithdrawalAmount', 'Minimum withdrawal amount limit is ' . DEFAULT_CURRENCY . '' . $MinimumWithdrawalLimit);
            return FALSE;
        }

        if ($this->Post['PaymentGateway'] == 'Bank') {
            $MaximumWithdrawalLimit = $this->db->query('SELECT ConfigTypeValue FROM set_site_config WHERE ConfigTypeGUID = "MaximumWithdrawalLimitBank" LIMIT 1')->row()->ConfigTypeValue;
        } else {
            $MaximumWithdrawalLimit = $this->db->query('SELECT ConfigTypeValue FROM set_site_config WHERE ConfigTypeGUID = "MaximumWithdrawalLimitPaytm" LIMIT 1')->row()->ConfigTypeValue;
        }

        if ($Amount > $MaximumWithdrawalLimit) {
            $this->form_validation->set_message('validateWithdrawalAmount', 'Maximum withdrawal amount limit is ' . DEFAULT_CURRENCY . '' . $MaximumWithdrawalLimit);
            return FALSE;
        }
        if ($this->Post['PaymentGateway'] == 'Paytm') {
            $MaximumWithdrawalLimitPaytmPerDay = $this->db->query('SELECT ConfigTypeValue FROM set_site_config WHERE ConfigTypeGUID = "MaximumWithdrawalLimitPaytm" LIMIT 1')->row()->ConfigTypeValue;
            if ($MaximumWithdrawalLimitPaytmPerDay > 0) {
                $TotalWithdrawals = $this->db->query("SELECT SUM(Amount) as TotalWithdrawals FROM tbl_users_withdrawal WHERE UserID = $this->SessionUserID  AND DATE(EntryDate) = '" . date('Y-m-d') . "' AND StatusID = 5 AND PaymentGateway='Paytm' LIMIT 1")->row()->TotalWithdrawals;
                $TotalWithdrawals = round($TotalWithdrawals) + $Amount;
                if ($MaximumWithdrawalLimitPaytmPerDay <= $TotalWithdrawals) {
                    $this->form_validation->set_message('validateWithdrawalAmount', 'Today Paytm withdrawal limit is exceeded.');
                    return FALSE;
                }
            }
        }
        if ($this->Post['PaymentGateway'] == 'Bank') {
            $MaximumWithdrawalLimitPaytmPerDay = $this->db->query('SELECT ConfigTypeValue FROM set_site_config WHERE ConfigTypeGUID = "MaximumWithdrawalLimitBank" LIMIT 1')->row()->ConfigTypeValue;

            if ($MaximumWithdrawalLimitPaytmPerDay > 0) {
                $TotalWithdrawals = $this->db->query("SELECT SUM(Amount) as TotalWithdrawals FROM tbl_users_withdrawal WHERE UserID = $this->SessionUserID  AND DATE(EntryDate) = '" . date('Y-m-d') . "' AND StatusID = 5 AND PaymentGateway='Bank' LIMIT 1")->row()->TotalWithdrawals;
                $TotalWithdrawals = round($TotalWithdrawals) + $Amount;
                if ($MaximumWithdrawalLimitPaytmPerDay < $TotalWithdrawals) {
                    $this->form_validation->set_message('validateWithdrawalAmount', 'Today Bank withdrawal limit is exceeded.');
                    return FALSE;
                }
            }
        }
        /* Validate Winning Amount */
        $UserData = $this->Users_model->getUsers('WinningAmount,isWithdrawal', array('UserID' => $this->SessionUserID));
        if ($Amount > $UserData['WinningAmount']) {
            $this->form_validation->set_message('validateWithdrawalAmount', 'Withdrawal amount can not greater than to winning amount.');
            return FALSE;
        }
        if ($UserData['isWithdrawal'] == 'No') {
            $this->form_validation->set_message('validateWithdrawalAmount', 'You can not withdraw with this account.');
            return FALSE;
        }
        return TRUE;
    }

    /**
     * Function Name: validateMinimumDepositAmount
     * Description:   To validate minimum deposit amount
     */
    public function validateMinimumDepositAmount($Amount) {
        /* Get Minimum Deposit Limit */
        $MinimumDepositLimit = $this->db->query('SELECT ConfigTypeValue FROM set_site_config WHERE ConfigTypeGUID = "MinimumDepositLimit" LIMIT 1')->row()->ConfigTypeValue;
        if ($Amount < $MinimumDepositLimit) {
            $this->form_validation->set_message('validateMinimumDepositAmount', 'Minimum deposit amount limit is ' . DEFAULT_CURRENCY . $MinimumDepositLimit);
            return FALSE;
        } else {
            return TRUE;
        }
    }

    /**
     * Function Name: validateWithdrawalID
     * Description:   To validate withdrawal ID
     */
    public function validateWithdrawalID($WithdrawalID) {
        $Query = $this->db->query("SELECT OTP,IsOTPVerified,Amount,PaymentGateway,StatusID,PaytmPhoneNumber FROM `tbl_users_withdrawal` WHERE `WithdrawalID` = " . $WithdrawalID . " LIMIT 1");
        if ($Query->num_rows() == 0) {
            $this->form_validation->set_message('validateWithdrawalID', 'Invalid {field}.');
            return FALSE;
        } else {

            if ($Query->row()->StatusID != 1) {
                $this->form_validation->set_message('validateWithdrawalID', 'We did not find pending withdrawal request.');
                return FALSE;
            }
            if ($Query->row()->IsOTPVerified == "Yes") {
                $this->form_validation->set_message('validateWithdrawalID', 'OTP already verified.');
                return FALSE;
            }
            if ($Query->row()->OTP != $this->Post['OTP']) {
                $this->form_validation->set_message('validateWithdrawalID', 'Invalid OTP.');
                return FALSE;
            }
            $this->Post['PaytmPhoneNumber'] = $Query->row()->PaytmPhoneNumber;
            $this->Post['Amount'] = $Query->row()->Amount;
            $this->Post['PaymentGateway'] = $Query->row()->PaymentGateway;
            return TRUE;
        }
    }

    /**
     * Function Name: validateWalletID
     * Description:   To validate wallet ID
     */
    public function validateWalletID($WalletID) {
        $WalletData = $this->Users_model->getWallet('Amount,TransactionID,CouponDetails', array('UserID' => $this->SessionUserID, 'WalletID' => $WalletID));
        if (!$WalletData) {
            $this->form_validation->set_message('validateWalletID', 'Invalid {field}.');
            return FALSE;
        } else {
            $this->Post['Amount'] = round($WalletData['Amount'], 1);
            $this->Post['TransactionID'] = $WalletData['TransactionID'];
            $this->Post['CouponDetails'] = $WalletData['CouponDetails'];
            return TRUE;
        }
    }

    /**
     * Function Name: validateAccountStatus
     * Description:   To validate user account status
     */
    public function validateAccountStatus($UserGUID) {
        /* Validate account status */
        $userData = $this->Users_model->getUsers('PanStatus,BankStatus', array('UserID' => $this->SessionUserID));
        if ($userData['BankStatus'] != 'Verified') {
            $this->form_validation->set_message('validateAccountStatus', 'Bank Account details not verified.');
            return FALSE;
        }
        if ($userData['PanStatus'] != 'Verified') {
            $this->form_validation->set_message('validateAccountStatus', 'Pan Card details not verified.');
            return FALSE;
        }

        /* Validate Pending Withdrawal Request */
        if ($this->db->query('SELECT COUNT(*) AS TotalRecords FROM `tbl_users_withdrawal` WHERE `UserID` = ' . $this->SessionUserID . ' AND `StatusID` = 1')->row()->TotalRecords > 0) {
            $this->form_validation->set_message('validateAccountStatus', 'Your withdrawal request already in pending mode.');
            return FALSE;
        }
        return TRUE;
    }
    
        /**
     * Function Name: validateAccountStatus
     * Description:   To validate user account status
     */
    public function validateAadharPan($UserGUID) {
        /* Validate account status */
        $userData = $this->Users_model->getUsers('PanStatus,AadharStatus', array('UserID' => $this->SessionUserID));
        if ($userData['PanStatus'] != 'Verified') {
            $this->form_validation->set_message('validateAadharPan', 'Pan details not verified.');
            return FALSE;
        }
        if ($userData['AadharStatus'] != 'Verified') {
            $this->form_validation->set_message('validateAadharPan', 'Aadhaar Card details not verified.');
            return FALSE;
        }
        /* Validate Pending Withdrawal Request */
        if ($this->db->query('SELECT COUNT(*) AS TotalRecords FROM `tbl_users_withdrawal` WHERE `UserID` = ' . $this->SessionUserID . ' AND `StatusID` = 1')->row()->TotalRecords > 0) {
            $this->form_validation->set_message('validateAccountStatus', 'Your withdrawal request already in pending mode.');
            return FALSE;
        }
        return TRUE;
    }

    /**
     * Function Name: validateAccountStatus
     * Description:   To validate user account status
     */
    public function validatePhoneStatus($PaytmPhoneNumber) {
        /* Validate account status */
        $userData = $this->Users_model->getUsers('PhoneNumber', array('UserID' => $this->SessionUserID));
        if ($userData['PhoneNumber'] != $PaytmPhoneNumber && $userData['PhoneNumber'] != "") {
            $this->form_validation->set_message('validatePhoneStatus', 'PhoneNumber not verified.');
            return FALSE;
        }
        return TRUE;
    }

}
