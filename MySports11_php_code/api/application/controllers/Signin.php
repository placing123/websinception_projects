<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Signin extends API_Controller {

    function __construct() {
        parent::__construct();
        $this->load->model('Recovery_model');
    }

    /*
      Name: 			Login
      Description: 	Verify login and activate session
      URL: 			/api/signin/
     */

    public function index_post() {
        /* Validation section */
        $this->form_validation->set_rules('Keyword', 'Keyword', 'trim' . (empty($this->Post['Source']) || $this->Post['Source'] == 'Direct' ? '|required' : ''));
        $this->form_validation->set_rules('Password', 'Password', 'trim' . (empty($this->Post['Source']) || $this->Post['Source'] == 'Direct' ? '|required' : ''));

        $this->form_validation->set_rules('PhoneNumber', 'PhoneNumber', 'trim' . (empty($this->Post['Source']) || $this->Post['Source'] == 'Otp' ? '|required' : ''));

        $this->form_validation->set_rules('OTP', 'OTP', 'trim' . (empty($this->Post['Source']) || $this->Post['Source'] == 'Otp' ? '|required' : ''));

        $this->form_validation->set_rules('Source', 'Source', 'trim|required|callback_validateSource');
        $this->form_validation->set_rules('DeviceType', 'Device type', 'trim|required|callback_validateDeviceType');
        $this->form_validation->set_rules('DeviceGUID', 'DeviceGUID', 'trim');
        $this->form_validation->set_rules('DeviceToken', 'DeviceToken', 'trim');
        $this->form_validation->set_rules('IPAddress', 'IPAddress', 'trim|callback_validateIP');
        $this->form_validation->set_rules('Latitude', 'Latitude', 'trim');
        $this->form_validation->set_rules('Longitude', 'Longitude', 'trim');

        $this->form_validation->validation($this);  /* Run validation */

        /** sql injection keyword*/
        $FindWord=array("sleep","delete","select","update","SLEEP","insert","DELETE","SELECT","UPDATE","INSERT","Sleep","Delete","Select","Update","Insert");
        $ReplaceWord=array("","","");
        $this->Post['Keyword'] = str_replace($FindWord,$ReplaceWord,$this->Post['Keyword']);
        $this->Post['DeviceType'] = str_replace($FindWord,$ReplaceWord,$this->Post['DeviceType']);
        $this->Post['Source'] = str_replace($FindWord,$ReplaceWord,$this->Post['Source']);

        $this->Return['CaptchaEnable'] = "No";
        $this->Post['LogType'] = "Login";

        /* Validation - ends */
        if (!empty($this->Post['PhoneNumber']) && !empty($this->Post['OTP'])) {
            /** cpatcha validation check **/
            // $IsValidCaptcha=$this->Users_model->getLoginLogsCount($this->Post);
            // if($IsValidCaptcha > 0){
            //     if($IsValidCaptcha >= 3){
            //         $this->Return['CaptchaEnable'] = "Yes";
            //     }
            //     if($IsValidCaptcha >= 4){
            //         $VerifyCaptcha= $this->Users_model->verifyCaptchaValidation($this->Post,"An error occurred, please try again later.");
            //         if($VerifyCaptcha['ResponseCode'] == 500){
            //             $this->Return['ResponseCode']   =   201;
            //             $this->Return['Message']        =   $VerifyCaptcha['Message'];
            //             exit;
            //         }else{
            //             $this->Return['CaptchaEnable'] = "No";
            //             $this->Users_model->deleteLogs($this->Post);
            //         }
            //     }
            // }
            $UserID = $this->Recovery_model->verifyToken($this->Post['OTP'], 3);
            if ($UserID) {
                $UserData = $this->Users_model->getUsers('Username,PhoneStatus,UserTypeID,UserID,FirstName,MiddleName,LastName,Email,StatusID,ProfilePic,PhoneNumber,WalletAmount,ReferralCode,TotalCash', array('UserID' => $UserID));
            }else{
                $this->Users_model->addLoginLogs($this->Post);
                $this->Return['ResponseCode'] = 500;
                $this->Return['Message'] = "Invalid OTP";
                return false;
            }
        } else {
            /** cpatcha validation check **/
            // $IsValidCaptcha=$this->Users_model->getLoginLogsCount($this->Post);
            // if($IsValidCaptcha > 0){
            //     if($IsValidCaptcha >= 3){
            //         $this->Return['CaptchaEnable'] = "Yes";
            //     }
            //     if($IsValidCaptcha >= 4){
            //         $VerifyCaptcha= $this->Users_model->verifyCaptchaValidation($this->Post,"An error occurred, please try again later.");
            //         if($VerifyCaptcha['ResponseCode'] == 500){
            //             $this->Return['ResponseCode']   =   201;
            //             $this->Return['Message']        =   $VerifyCaptcha['Message'];
            //             exit;
            //         }else{
            //             $this->Return['CaptchaEnable'] = "No";
            //             $this->Users_model->deleteLogs($this->Post);
            //         }
            //     }
            // }

            $UserData = $this->Users_model->getUsers('Username,PhoneStatus,UserTypeID,UserID,FirstName,MiddleName,LastName,Email,StatusID,ProfilePic,PhoneNumber,WalletAmount,ReferralCode,TotalCash', array('LoginKeyword' => @$this->Post['Keyword'], 'Password' => $this->Post['Password'], 'SourceID' => $this->SourceID));
        }

        if(!$UserData){
            $Query = $this->db->query("SELECT * FROM `tbl_users` WHERE `EmailForChange` = ".$this->db->escape(@$this->Post['Keyword'])." AND `PhoneNumber` IS NOT NULL LIMIT 1");
            if ($Query->num_rows() > 0) {
                $IsEmailForChange = TRUE;
            }
        }
        if(!$UserData){
            /** add logs **/
            $this->Post['LogType'] = "Login";
            $this->Users_model->addLoginLogs($this->Post);
        }elseif ($UserData && $UserData['StatusID'] != 2) {
            /** add logs **/
            $this->Post['LogType'] = "Login";
            $this->Users_model->addLoginLogs($this->Post);
        }

        if(!$UserData && !isset($IsEmailForChange)){
            $this->Return['ResponseCode']   =   500;
            $this->Return['Message']        =   "Invalid login credentials.";
        }elseif(!$UserData && isset($IsEmailForChange)){
            $this->Return['ResponseCode']   =   500;
            $this->Return['Message']        =   "Please login using your phone number, your email is not verified.";
        } elseif ($UserData && $UserData['StatusID'] == 1) {
            $this->Return['ResponseCode'] = 501;
            $this->Return['Message'] = "You have not activated your account yet, please verify your email address first.";
        } elseif ($UserData && $UserData['StatusID'] == 3) {
            $this->Return['ResponseCode'] = 500;
            $this->Return['Message'] = "Your account has been deleted. Please contact the Admin for more info.";
        } elseif ($UserData && $UserData['StatusID'] == 4) {
            $this->Return['ResponseCode'] = 500;
            $this->Return['Message'] = "Your account has been blocked. Please contact the Admin for more info.";
        } elseif ($UserData && $UserData['StatusID'] == 6) {
            $this->Return['ResponseCode'] = 500;
            $this->Return['Message'] = "You have deactivated your account, please contact the Admin to reactivate.";
        }
        /*elseif($UserData && isset($OtherLoginSourcesExists)){
            $this->Return['ResponseCode']   =   500;
            $this->Return['Message']        =   "You have registered using ".$SourcesArray[0]['SourceName']." option. Please Signin with ".$SourcesArray[0]['SourceName'];
        }*/
        else {
            /* Create Session */
            $UserData['SessionKey'] = $this->Users_model->createSession($UserData['UserID'], array(
                "IPAddress" => $_SERVER['REMOTE_ADDR'],
                "SourceID" => $this->SourceID,
                "PhoneStatus" => $UserData['PhoneStatus'],
                "DeviceTypeID" => $this->DeviceTypeID,
                "DeviceGUID" => @$this->Post['DeviceGUID'],
                "DeviceToken" => @$this->Post['DeviceToken'],
                "Latitude" => @$this->Post['Latitude'],
                "Longitude" => @$this->Post['Longitude']
            ));
            $this->Return['Data'] = $UserData;
        }
        
        /* unset output parameters */
        unset($this->Return['Data']->UserID);
        unset($this->Return['Data']->StatusID);
        /* unset output parameters - ends */
    }

    /*
      Name: 			OTP for sigin
      Description: 	Signin using OTP
      URL: 			/api/signin/OtpSignIn/
     */

    public function OtpSignIn_post() {
        /* Validation section */
        $this->form_validation->set_rules('PhoneNumber', 'PhoneNumber', 'trim' . (empty($this->Post['Source']) || $this->Post['Source'] == 'Otp' ? '|required' : ''));
        $this->form_validation->set_rules('Source', 'Source', 'trim|required|callback_validateSource');
        $this->form_validation->validation($this);  /* Run validation */
        /* Validation - ends */

        $this->Return['CaptchaEnable'] = "No";
        $this->Post['LogType'] = "OTP";

        /** cpatcha validation check **/
        // $IsValidCaptcha=$this->Users_model->getLoginLogsCount($this->Post);
        // if($IsValidCaptcha > 0){
        //     if($IsValidCaptcha >= 3){
        //         $this->Return['CaptchaEnable'] = "Yes";
        //     }
        //     if($IsValidCaptcha >= 4){
        //         $VerifyCaptcha= $this->Users_model->verifyCaptchaValidation($this->Post,"An error occurred, please try again later.");
        //         if($VerifyCaptcha['ResponseCode'] == 500){
        //             $this->Return['ResponseCode']   =   201;
        //             $this->Return['Message']        =   $VerifyCaptcha['Message'];
        //             exit;
        //         }else{
        //             $this->Return['CaptchaEnable'] = "No";
        //             $this->Users_model->deleteLogs($this->Post);
        //         }
        //     }
        // }

        $this->Users_model->addLoginLogs($this->Post);
        $UserData = $this->Users_model->getUsers('PhoneNumber', array('PhoneNumber' => @$this->Post['PhoneNumber']));
        // print_r($UserData);exit;
        if ($UserData) {
            $Token = $this->Recovery_model->generateToken($UserData['UserID'], 3);
            $this->Utility_model->sendSMS_MSG91(array(
                'FlowID'        => LOGIN_OTP_ID,
                'PhoneNumber'   => $UserData['PhoneNumber'],
                'OTP'          => $Token
            ));
            unset($UserData['UserID']);
            unset($UserData['UserGUID']);
            unset($UserData['FullName']);
            $this->Return['Data'] = $UserData;
        } else if (!$UserData) {
            $this->Return['ResponseCode'] = 500;
            $this->Return['Message'] = "Phone number not found";
        }
    }

    /*
      Name: 			Logout
      Description: 	Delete session
      URL: 			/api/signin/signout/
     */

    public function signout_post() {
        /* Validation section */
        $this->form_validation->set_rules('SessionKey', 'SessionKey', 'trim|required');
        $this->form_validation->validation($this);  /* Run validation */
        /* Validation - ends */

        $this->Users_model->deleteSession($this->Post['SessionKey']); /* Delete session */
    }

}
