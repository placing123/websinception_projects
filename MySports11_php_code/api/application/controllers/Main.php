<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Main extends MAIN_Controller {

    public function index() {
        echo "This is a sample page.";
    }

    public function logs() {
        $this->load->library('logviewer');
        $this->load->view('logs', $this->logviewer->get_logs());
    }

    public function upload() {
        $this->load->view('upload');
    }

    public function paytmResponse() {
        mongoDBConnection();
        $this->load->model('Users_model');

        /* Get User ID */
        $UserID = $this->db->query('SELECT `UserID` FROM `tbl_users_wallet` WHERE `WalletID` = ' . $_POST["ORDERID"] . ' LIMIT 1')->row()->UserID;
        $PaymentResponse = array();
        $PaymentResponse['WalletID'] = $_POST["ORDERID"];
        $PaymentResponse['PaymentGatewayResponse'] = json_encode($_POST);

        $Insert = $this->fantasydb->payment_logs_paytm->insertOne(array(
            'PaymentType' => "Paytm",
            'Response' => $_POST,
            'EntryDate' => date('Y-m-d H:i:s'),
        ));

        if ($_POST["STATUS"] == "TXN_FAILURE") {

            /* Update Transaction */
            $PaymentResponse['PaymentGatewayStatus'] = 'Failed';
            $this->Users_model->confirm($PaymentResponse, $UserID);
            redirect(SITE_HOST . ROOT_FOLDER . 'myAccount?status=failed');
        } else {

            /* Verify Transaction */
            $IsValidCheckSum = $this->Users_model->verifychecksum_e($_POST, PAYTM_MERCHANT_KEY, $_POST['CHECKSUMHASH']);
            if ($IsValidCheckSum == "TRUE" && $_POST["STATUS"] == "TXN_SUCCESS") {

                /* Update Transaction */
                $PaymentResponse['PaymentGatewayStatus'] = 'Success';
                $PaymentResponse['Amount'] = $_POST['TXNAMOUNT'];
                $this->Users_model->confirm($PaymentResponse, $UserID);
                redirect(SITE_HOST . ROOT_FOLDER . 'myAccount?status=success');
            } else {

                /* Update Transaction */
                $PaymentResponse['PaymentGatewayStatus'] = 'Failed';
                $this->Users_model->confirm($PaymentResponse, $UserID);
                redirect(SITE_HOST . ROOT_FOLDER . 'myAccount?status=failed');
            }
        }
    }

    /**-- get phonePe Payment Status --**/ 
    public function phonePeResponse() {

        $StatusString =  "/v3/transaction/".PHONEPE_MERCHANT_ID."/".$this->input->get('transactionId').'/status'. PHONEPE_SALT_KEY;
        $x_Verify   = hash('sha256', $StatusString);

        $CURL_URL = PHONEPE_HOST_URL."/v3/transaction/".PHONEPE_MERCHANT_ID."/".$this->input->get('transactionId')."/status";
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
            $UserData = $this->db->query('SELECT `UserID`,`Amount` FROM `tbl_users_wallet` WHERE `WalletID` = ' . $this->input->get('OrderId') . ' LIMIT 1')->row();
            $PaymentResponse['PaymentGateway'] = 'PhonePe';
            $PaymentResponse['PaymentGatewayResponse'] = $Response;
            $PaymentResponse['WalletID'] = $this->input->get('OrderId');
            $InsertData = array_filter(array(
                "Content" 		=> $Response,
                "OrderID"       => $PaymentResponse['WalletID'],
                "UserID"        => $UserData->UserID,
                "PaymentType"   => "phonePe",
                "CaptureType"   => "API-WEB",
                "CreateDate"    => date('Y-m-d H:i:s')
            ));
            //This table use for PhonePe & RazorPay
            $this->db->insert('tbl_test_razorPay', $InsertData);
            /**- validate Amount -**/ 
            if ($UserData->Amount != ($Result['data']['amount'] / 100)) {
            	/* Update Transaction */
                $PaymentResponse['PaymentGatewayStatus'] = 'Failed';
                $this->Users_model->confirm($PaymentResponse, $UserData->UserID);
                redirect(SITE_HOST . ROOT_FOLDER . 'myAccount?status=failed');
            }

            if ($Result["success"] == 1 && $Result["code"] == 'PAYMENT_SUCCESS') {
                /* Update Transaction */
                $PaymentResponse['PaymentGatewayStatus'] = 'Success';
                $PaymentResponse['Amount'] = $Result['data']['amount'] / 100;
                $this->Users_model->confirm($PaymentResponse, $UserData->UserID);
                redirect(SITE_HOST . ROOT_FOLDER . 'myAccount?status=success');
            } else if ($Result["code"] == 'PAYMENT_PENDING' || $Result["code"] == 'INTERNAL_SERVER_ERROR') {
                /* Update Transaction */
                redirect(SITE_HOST . ROOT_FOLDER . 'myAccount?status=pending');
            }  else {
                /* Update Transaction */
                $PaymentResponse['PaymentGatewayStatus'] = 'Failed';
                $this->Users_model->confirm($PaymentResponse, $UserData->UserID);
                redirect(SITE_HOST . ROOT_FOLDER . 'myAccount?status=failed');
            } 
        }
    }

}
