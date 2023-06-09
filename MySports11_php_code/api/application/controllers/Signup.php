<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Signup extends API_Controller {

    function __construct() {
        parent::__construct();
        $this->load->model('Recovery_model');
        $this->load->model('Utility_model');
    }

    /*
      Name: 			Signup
      Description: 	Use to register user to system.
      URL: 			/api/signup/
     */


    public function index_post() {
        /* Validation section */
        $this->form_validation->set_rules('Email', 'Email', 'trim' . (empty($this->Post['Source']) || $this->Post['Source'] == 'Direct' ? '|required' : '') . '|valid_email|callback_validateEmail');
        $this->form_validation->set_rules('Username', 'Username', 'trim|alpha_dash|callback_validateUsername');
        $this->form_validation->set_rules('Password', 'Password', 'trim' . (empty($this->Post['Source']) || $this->Post['Source'] == 'Direct' ? '|required' : ''));
        $this->form_validation->set_rules('FirstName', 'FirstName', 'trim');
        $this->form_validation->set_rules('MiddleName', 'MiddleName', 'trim');
        $this->form_validation->set_rules('LastName', 'LastName', 'trim');
        $this->form_validation->set_rules('UserTypeID', 'UserTypeID', 'trim|required|in_list[2,3]');
        $this->form_validation->set_rules('Gender', 'Gender', 'trim|in_list[Male,Female,Other]');
        $this->form_validation->set_rules('BirthDate', 'BirthDate', 'trim|callback_validateDate');
        $this->form_validation->set_rules('Age', 'Age', 'trim|integer');
        $this->form_validation->set_rules('PhoneNumber', 'PhoneNumber', 'trim|callback_validatePhoneNumber|is_unique[tbl_users.PhoneNumber]');
        $this->form_validation->set_rules('Source', 'Source', 'trim|required|callback_validateSource');

        $this->form_validation->set_rules('SourceGUID', 'SourceGUID', 'trim' . (empty($this->Post['Source']) || $this->Post['Source'] != 'Direct' ? '|required' : '') . '|callback_validateSourceGUID[' . @$this->Post['Source'] . ']');

        $this->form_validation->set_rules('DeviceType', 'Device type', 'trim|required|callback_validateDeviceType');
        $this->form_validation->set_rules('IPAddress', 'IPAddress', 'trim|callback_validateIP');
        $this->form_validation->set_rules('ReferralCode', 'ReferralCode', 'trim|callback_validateReferralCode');
        $this->form_validation->set_rules('LoginType', 'LoginType', 'trim|in_list[Web,Andorid,Ios]');
        $this->form_validation->validation($this); /* Run validation */
        /* Validation - ends */

        // check Domain valid domain
        $DomainCheck  = end(explode("@",$this->Post['Email']));
        $DomainArr = array('gmail.com', 'yahoo.in', 'yahoo.com', 'rediffmail.com', 'rediff.com', 'in.com', 'ymail.com', 'aol.com', 'aol.in', 'webdunia.com', 'live.com', 'outlook.com', 'live.com', 'mail.com', 'outlook.in', 'facebook.com', 'icloud.com', 'fbmail.com', 'hotmail.com');

        if (!in_array($DomainCheck, $DomainArr)){
            $this->Return['ResponseCode'] = 500;
            $this->Return['Message'] = "This email not work, please change the domain.";
            exit;
        }
        if($this->Post['Source'] == "Direct"){
            $this->Post['LogType'] = "Signup";

            /** cpatcha validation check **/
            $this->Return['CaptchaEnable'] = "No";
            // $IsValidCaptcha=$this->Users_model->getLoginLogsCount(array('LogType'=>"Signup"));
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
        }

        $UserID = $this->Users_model->addUser(array_merge($this->Post, array(
            "Referral" => @$this->Referral, 'Username' => $this->randomString()
                )), $this->Post['UserTypeID'], $this->SourceID, ($this->Post['Source'] != 'Direct' ? '2' : '1') /* if source is not Direct Account treated as Verified */);
        if (!$UserID) {
            $this->Return['ResponseCode'] = 500;
            $this->Return['Message'] = "An error occurred, please try again later.";
        } else {
            if (!empty($this->Post['Email'])) {
                /* Send welcome Email to User with Token. (only if source is Direct) */
                $Token = ($this->Post['Source'] == 'Direct' ? $this->Recovery_model->generateToken($UserID, 2) : '');
                $sendSS = send_mail(array(
                    'emailTo' => $this->Post['Email'],
                    'template_id' => 'd-f1754e5f1d044f6e94bf0ef237caf667',
                    'Subject' => 'Thank you for registering at ' . SITE_NAME,
                    "Name" => $this->Post['FirstName'],
                    'Token' => $Token,
                    'DeviceTypeID' => $this->DeviceTypeID
                ));
            }

            /* for update phone number */
            $this->load->model('Utility_model');
            if (!empty($this->Post['PhoneNumber']) && PHONE_NO_VERIFICATION) {
                /* Genrate a Token for PhoneNumber verification and save to tokens table. */
                $this->load->model('Recovery_model');
                $Token = $this->Recovery_model->generateToken($UserID, 3);
                /* Send change phonenumber SMS to User with Token. */
                $this->Utility_model->sendSMS_MSG91(array(
                    'FlowID'        => VERIFY_OTP_ID,
                    'PhoneNumber'   => $this->Post['PhoneNumber'],
                    'OTP'           => $Token
                ));
            }

            /* referal code generate */
            $this->Utility_model->generateReferralCode($UserID);
            /* Send welcome notification */
            $this->Notification_model->addNotification('welcome', 'Welcome to ' . SITE_NAME . '!', $UserID, $UserID, '', 'Hi ' . $this->Post['FirstName'] . ', Verify your Email and PAN Details and Earn more Cash Bonus.');
            $this->Notification_model->addNotification('welcome', $this->Post['FirstName'] . ', got Registered', $UserID, '125');
            /* get user data */
            $UserData = $this->Users_model->getUsers('FirstName,Username,MiddleName,LastName,Email,ProfilePic,UserTypeID,UserTypeName', array(
                'UserID' => $UserID
            ));
            /* create session only if source is not Direct and account treated as Verified. */
            $UserData['SessionKey'] = '';

            $UserData['SessionKey'] = $this->Users_model->createSession($UserID, array(
                "IPAddress" => @$this->Post['IPAddress'],
                "SourceID" => $this->SourceID,
                "DeviceTypeID" => $this->DeviceTypeID,
                "DeviceGUID" => @$this->Post['DeviceGUID'],
                "DeviceToken" => @$this->Post['DeviceToken']
            ));

            $BonusData = $this->db->query('SELECT ConfigTypeValue,StatusID FROM set_site_config WHERE ConfigTypeGUID = "SignupWalletDepositBonus" LIMIT 1');
            if ($BonusData->row()->StatusID == 2) {
                $NotificationMessage= DEFAULT_CURRENCY . $BonusData->row()->ConfigTypeValue . ' has been credited in your bonus Wallet';
                sendPushMessage($UserID, 'Signup Deposit Bonus', $NotificationMessage, $Data=array("RefrenceID"=>'', "NotificationPatternGUID"=>'bonus'));
            }

            $this->Return['Data'] = $UserData;
            $this->Return['Data']['mail'] = $sendSS;
        }
    }

    /*
      Name: 			resendverify
      Description: 	Use to resend OTP for Email address verification.
      URL: 			/api/signup/resendverify
     */

    public function resendverify_post() {
        /* Validation section */
        $this->form_validation->set_rules('Keyword', 'Keyword', 'trim|required|valid_email');
        $this->form_validation->set_rules('DeviceType', 'Device type', 'trim|required|callback_validateDeviceType');
        $this->form_validation->validation($this); /* Run validation */
        /* Validation - ends */


            $Logs['LogType'] = "Verify";
            $Logs['Keyword'] = $this->input->post('Keyword');
            $this->Return['CaptchaEnable'] = "No";
            /** cpatcha validation check **/
            // $IsValidCaptcha=$this->Users_model->getLoginLogsCount($Logs);
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
            //             $this->Users_model->deleteLogs($Logs);
            //         }
            //     }
            // }

            $this->Users_model->addLoginLogs($Logs);


        
        $UserData = $this->Users_model->getUsers('UserID, FirstName, StatusID', array(
            'Email' => $this->Post['Keyword']
        ));

        if (empty($UserData)) {
            $this->Return['ResponseCode'] = 500;
            $this->Return['Message'] = "If your account is registered here you will receive an email from us.";
        } elseif ($UserData && $UserData['StatusID'] == 2) {
            $this->Return['Message'] = "Your account is already verified.";
        } elseif ($UserData && $UserData['StatusID'] == 3) {
            $this->Return['ResponseCode'] = 500;
            $this->Return['Message'] = "Your account has been deleted. Please contact the Admin for more info.";
        } elseif ($UserData && $UserData['StatusID'] == 4) {
            $this->Return['ResponseCode'] = 500;
            $this->Return['Message'] = "Your account has been blocked. Please contact the Admin for more info.";
        } elseif ($UserData && $UserData['StatusID'] == 6) {
            $this->Return['ResponseCode'] = 500;
            $this->Return['Message'] = "You have deactivated your account, please contact the Admin to reactivate.";
        } else {
            send_mail(array(
                'emailTo' => $this->Post['Keyword'],
                'template_id' => 'd-f1754e5f1d044f6e94bf0ef237caf667',
                'Subject' => 'Verify your account ' . SITE_NAME,
                "Name" => $UserData['FirstName'],
                'Token' => $this->Recovery_model->generateToken($UserData['UserID'], 2),
                'DeviceTypeID' => $this->DeviceTypeID
            ));
            $this->Return['Message'] = "Please check your email for instructions.";
        }
    }

    /*
      Name:           SendVerifyEmail
      Description:    Use to send verify Email.
      URL:            /api/signup/resendVerification
     */

    function resendVerification_post() {
        /* Validation section */
        $this->form_validation->set_rules('SessionKey', 'SessionKey', 'trim|required|callback_validateSession');
        $this->form_validation->set_rules('Type', 'Type', 'trim|required|in_list[Email,Phone]');
        $this->form_validation->validation($this);  /* Run validation */
        /* Validation - ends */
        if ($this->input->post('Type') == 'Email') {
            $this->db->select('UserID');
            $this->db->from('tbl_users');
            $this->db->where('Email', $this->input->post('Email'));
            $this->db->where('UserID !=', $this->SessionUserID);
            $this->db->limit(1);
            $Query = $this->db->get();
            if ($Query->num_rows() > 0) {
                $this->Return['ResponseCode']   =   500;
                $this->Return['Message']        =   "Email already registered.";
                exit;
            } 
        }
        if ($this->input->post('UserGUID')) {
            $UserData = $this->Users_model->getUsers('UserID,FirstName,EmailForChange,Email,PhoneNumberForChange,UserTypeID,UserTypeName', array("UserGUID" => $this->input->post('UserGUID')));
            $UserID = $UserData['UserID'];
        } else {
            $UserData = $this->Users_model->getUsers('FirstName,LastName,Email,EmailForChange,PhoneNumberForChange,UserTypeID,UserTypeName', array("UserID" => $this->SessionUserID));
            $UserID = $this->SessionUserID;
        }
        if ($this->input->post('Type') == 'Email') {

            $Logs['LogType'] = "Verify";
            $Logs['Keyword'] = $this->input->post('Email');
            $this->Return['CaptchaEnable'] = "No";
            /** cpatcha validation check **/
            // $IsValidCaptcha=$this->Users_model->getLoginLogsCount($Logs);
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
            //             $this->Users_model->deleteLogs($Logs);
            //         }
            //     }
            // }

            $this->Users_model->addLoginLogs($Logs);

            /* Send welcome Email to User with Token. */
            // $Token = $this->Recovery_model->generateToken($UserID, 2);
            // send_mail(array(
            //     'emailTo' => $this->input->post('Email'),
            //     'template_id' => 'd-9905193491964415b8a92013b2c9ba4b',
            //     'Subject' => 'Verify your Email ' . SITE_NAME,
            //     "Name" => $UserData['FirstName'],
            //     'Token' => $Token
            // ));

            $Token = $this->Recovery_model->generateToken($UserID, 2);
            $sendSS = send_mail(array(
                'emailTo' => $this->input->post('Email'),
                'template_id' => 'd-aeccbfd9c2d14b619ca332b2ec1cbcb9',
                'Subject' => 'Thank you for registering at ' . SITE_NAME,
                "Name" => $this->Post['FirstName'],
                'Token' => $Token,
                // 'DeviceTypeID' => $this->DeviceTypeID
            ));

            $this->db->where('UserID', $this->SessionUserID);
            $this->db->limit(1);
            $this->db->update('tbl_users',array('EmailForChange'=>$this->input->post('Email')));

        } else {

            $Logs['LogType'] = "Verify";
            $Logs['PhoneNumber'] = $UserData['PhoneNumberForChange'];
            $this->Return['CaptchaEnable'] = "No";
            /** cpatcha validation check **/
            // $IsValidCaptcha=$this->Users_model->getLoginLogsCount($Logs);
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
            //             $this->Users_model->deleteLogs($Logs);
            //         }
            //     }
            // }
            $this->Users_model->addLoginLogs($Logs);
            $Token = $this->Recovery_model->generateToken($UserID, 3);
            /* Send change phonenumber SMS to User with Token. */
            $this->Utility_model->sendSMS_MSG91(array(
                'FlowID'        => VERIFY_OTP_ID,
                'PhoneNumber'   => $UserData['PhoneNumberForChange'],
                'OTP'           => $Token
            ));
        }
    }

    function resendVerificationold_post() {
        /* Validation section */
        $this->form_validation->set_rules('SessionKey', 'SessionKey', 'trim|required|callback_validateSession');
        $this->form_validation->set_rules('Type', 'Type', 'trim|required|in_list[Email,Phone]');
        $this->form_validation->validation($this);  /* Run validation */
        /* Validation - ends */
        if ($this->input->post('Type') == 'Email') {
            $this->db->select('UserID');
            $this->db->from('tbl_users');
            $this->db->where('Email', $this->input->post('Email'));
            $this->db->where('UserID !=', $this->SessionUserID);
            $this->db->limit(1);
            $Query = $this->db->get();
            if ($Query->num_rows() > 0) {
                $this->Return['ResponseCode']   =   500;
                $this->Return['Message']        =   "Email already registered.";
                exit;
            } 
        }
        if ($this->input->post('UserGUID')) {
            $UserData = $this->Users_model->getUsers('UserID,FirstName,EmailForChange,Email,PhoneNumberForChange,UserTypeID,UserTypeName', array("UserGUID" => $this->input->post('UserGUID')));
            $UserID = $UserData['UserID'];
        } else {
            $UserData = $this->Users_model->getUsers('FirstName,LastName,Email,EmailForChange,PhoneNumberForChange,UserTypeID,UserTypeName', array("UserID" => $this->SessionUserID));
            $UserID = $this->SessionUserID;
        }
        if ($this->input->post('Type') == 'Email') {

            /* Send welcome Email to User with Token. */
            $Token = $this->Recovery_model->generateToken($UserID, 2);
            send_mail(array(
                'emailTo' => $UserData['EmailForChange'],
                'template_id' => 'd-8cae2914de5c4e3dbbf8d419e8777dbd',
                'Subject' => 'Verify your Email ' . SITE_NAME,
                "Name" => $UserData['FirstName'],
                'Token' => $Token
            ));

            $Logs['LogType'] = "Verify";
            $Logs['Keyword'] = $this->Post['PhoneNumber'];
            $this->Users_model->addLoginLogs($Logs);
        } else {
            $Token = $this->Recovery_model->generateToken($UserID, 3);
            /* Send change phonenumber SMS to User with Token. */
            $this->Utility_model->sendSMS_MSG91(array(
                'FlowID'        => VERIFY_OTP_ID,
                'PhoneNumber'   => $UserData['PhoneNumberForChange'],
                'OTP'           => $Token
            ));
        }
    }

    /*
      Name: 			verifyEmail
      Description: 	Use to verify Email address and activate account by OTP.
      URL: 			/api/signup/verifyEmail
     */

    public function verifyEmail_post() {
        /* Validation section */
        //$this->form_validation->set_rules('OTP', 'OTP', 'trim|required|callback_validateToken[2]');
        $this->form_validation->set_rules('Source', 'Source', 'trim|required|callback_validateSource');
        $this->form_validation->set_rules('DeviceType', 'Device type', 'trim|required|callback_validateDeviceType');
        $this->form_validation->set_rules('DeviceGUID', 'DeviceGUID', 'trim');
        $this->form_validation->set_rules('DeviceToken', 'DeviceToken', 'trim');
        $this->form_validation->set_rules('IPAddress', 'IPAddress', 'trim|callback_validateIP');
        $this->form_validation->set_rules('Latitude', 'Latitude', 'trim');
        $this->form_validation->set_rules('Longitude', 'Longitude', 'trim');
        $this->form_validation->set_rules('OTP', 'OTP', 'trim|required');
        $this->form_validation->set_rules('Email', 'Email', 'trim|required');
        $this->form_validation->validation($this); /* Run validation */
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
        $UserID = $this->Recovery_model->verifyToken($this->Post['OTP'], 2);
        if(empty($UserID)){
            $this->Users_model->addLoginLogs($this->Post);
            $this->Return['ResponseCode'] = 500;
            $this->Return['Message'] = "Sorry, but its an invalid OTP.";
            exit;
        }
        /* check for email update */
        $UserData = $this->Users_model->getUsers('UserTypeID,Username,FirstName,LastName,Email,EmailForChange,StatusID,ProfilePic', array(
            'UserID' => $UserID
        ));

        if (!empty($UserData['Email'])) {
           if($UserData['EmailForChange'] != $this->Post['Email']){
                $this->Users_model->addLoginLogs($this->Post);
                $this->Return['ResponseCode'] = 500;
                $this->Return['Message'] = "Sorry, but its an invalid OTP.";
                exit; 
           } 
        }

        if (!empty($UserData['EmailForChange'])) {
            if ($this->Users_model->updateEmail($UserID, $UserData['EmailForChange'])) {
                send_mail(array(
                    'emailTo' => $UserData['Email'],
                    'template_id' => 'd-c83831923c13476c8169f8d76ad6915f',
                    'Subject' => 'Your' . SITE_NAME . ' email has been updated!',
                    "Name" => $UserData['FirstName']
                ));
            }
        } else {
            /* change entity status to activate */
            $this->Entity_model->updateEntityInfo($UserID, array(
                "StatusID" => 2
            ));
            /* Create Session */
            $UserData['SessionKey'] = $this->Users_model->createSession($UserID, array(
                "IPAddress" => @$this->Post['IPAddress'],
                "SourceID" => $this->SourceID,
                "DeviceTypeID" => $this->DeviceTypeID,
                "DeviceGUID" => @$this->Post['DeviceGUID'],
                "DeviceToken" => @$this->Post['DeviceToken'],
                "Latitude" => @$this->Post['Latitude'],
                "Longitude" => @$this->Post['Longitude']
            ));
            $this->Return['Data'] = $UserData;
            $this->Return['Message'] = "Your account has been successfully verified, please login to get access your account.";
        }

        $this->Recovery_model->deleteToken($this->Post['OTP'], 2); /* delete token in any case */
    }

    /*
      Name: 			verifyEmail
      Description: 	Use to verify Email address and activate account by OTP.
      URL: 			/api/signup/verifyEmail
     */

    public function verifyPhoneNumber_post() {
        /* Validation section */
        $this->form_validation->set_rules('Source', 'Source', 'trim|callback_validateSource');
        $this->form_validation->set_rules('DeviceType', 'Device type', 'trim|callback_validateDeviceType');
        //$this->form_validation->set_rules('OTP', 'OTP', 'trim|required|callback_validateToken[3]');
        $this->form_validation->set_rules('OTP', 'OTP', 'trim|required');
        $this->form_validation->set_rules('PhoneNumber', 'PhoneNumber', 'trim|required');
        $this->form_validation->validation($this); /* Run validation */
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
        $UserID = $this->Recovery_model->verifyToken($this->Post['OTP'], 3);
        if(empty($UserID)){
            $this->Users_model->addLoginLogs($this->Post);
            $this->Return['ResponseCode'] = 500;
            $this->Return['Message'] = "Sorry, but its an invalid OTP.";
            exit; 
        }
        /* check for PhoneNo. update */
        $UserData = $this->Users_model->getUsers('Username,PhoneNumberForChange,UserTypeID,UserID,FirstName,MiddleName,LastName,Email,StatusID,ProfilePic,PhoneNumber,WalletAmount,ReferralCode,TotalCash', array(
            'UserID' => $UserID
        ));

        if (!empty($UserData['PhoneNumberForChange'])) {
           if($UserData['PhoneNumberForChange'] != $this->Post['PhoneNumber']){
                $this->Users_model->addLoginLogs($this->Post);
                $this->Return['ResponseCode'] = 500;
                $this->Return['Message'] = "Sorry, but its an invalid OTP.";
                exit; 
           } 
        }

        if (!empty($UserData['PhoneNumberForChange'])) {
            if (!$this->Users_model->updatePhoneNumber($UserID, $UserData['PhoneNumberForChange'])) {
                $this->Return['ResponseCode'] = 500;
                $this->Return['Message'] = "An error occurred. Please contact the Admin for more info.";
            } else {
                $this->Entity_model->updateEntityInfo($UserID, array("StatusID" => 2));
                /* Create Session */
                $UserData['SessionKey'] = $this->Users_model->createSession($UserData['UserID'], array(
                    "IPAddress" => @$this->Post['IPAddress'],
                    "SourceID" => $this->SourceID,
                    "DeviceTypeID" => $this->DeviceTypeID,
                    "DeviceGUID" => @$this->Post['DeviceGUID'],
                    "DeviceToken" => @$this->Post['DeviceToken'],
                    "Latitude" => @$this->Post['Latitude'],
                    "Longitude" => @$this->Post['Longitude']
                ));
                $this->Return['Data'] = $UserData;
                $this->Return['Message'] = "Successfully verified.";
            }
        }
        $this->Recovery_model->deleteToken($this->Post['OTP'], 3); /* delete token in any case */
    }


        /*
      Name:             verifyEmail
      Description:  Use to verify Email address and activate account by OTP.
      URL:          /api/signup/verifyEmail
     */

    public function verifyPhoneNumberOTP_post() {
        /* Validation section */
        $this->form_validation->set_rules('Source', 'Source', 'trim|callback_validateSource');
        $this->form_validation->set_rules('DeviceType', 'Device type', 'trim|callback_validateDeviceType');
        $this->form_validation->set_rules('PhoneNumber', 'PhoneNumber', 'trim|required');
        $this->form_validation->set_rules('OTP', 'OTP', 'trim|required');
        //$this->form_validation->set_rules('OTP', 'OTP', 'trim|required|callback_validateToken[3]');
        $this->form_validation->validation($this); /* Run validation */
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
        $UserID = $this->Recovery_model->verifyToken($this->Post['OTP'], 3);
        if(empty($UserID)){
            $this->Users_model->addLoginLogs($this->Post);
            $this->Return['ResponseCode'] = 500;
            $this->Return['Message'] = "Sorry, but its an invalid OTP.";
            exit;
        }
        /* check for PhoneNo. update */
        $UserData = $this->Users_model->getUsers('UserID,Username,PhoneStatus,UserTypeID,UserID,FirstName,MiddleName,LastName,Email,StatusID,ProfilePic,PhoneNumber,WalletAmount,ReferralCode,TotalCash,PhoneNumberForChange', array(
            'UserID' => $UserID
        ));

        if (!empty($UserData['PhoneNumberForChange'])) {
           if($UserData['PhoneNumberForChange'] != $this->Post['PhoneNumber']){
                $this->Users_model->addLoginLogs($this->Post);
                $this->Return['ResponseCode'] = 500;
                $this->Return['Message'] = "Sorry, but its an invalid OTP.";
                exit; 
           } 
        }
  
        if (!empty($UserData['PhoneNumberForChange'])) {

            if (!$this->Users_model->updatePhoneNumber($UserID, $UserData['PhoneNumberForChange'])) {
                $this->Return['ResponseCode'] = 500;
                $this->Return['Message'] = "An error occurred. Please contact the Admin for more info.";
            } else {
                $UserData['PhoneStatus'] = "Verified";
                $this->Entity_model->updateEntityInfo($UserID, array("StatusID" => 2));
                /* Create Session */
                $UserData['SessionKey'] = $this->Users_model->createSession($UserData['UserID'], array(
                    "IPAddress" => @$this->Post['IPAddress'],
                    "SourceID" => $this->SourceID,
                    "DeviceTypeID" => $this->DeviceTypeID,
                    "DeviceGUID" => @$this->Post['DeviceGUID'],
                    "DeviceToken" => @$this->Post['DeviceToken'],
                    "Latitude" => @$this->Post['Latitude'],
                    "Longitude" => @$this->Post['Longitude']
                ));
                $this->Return['Data'] = $UserData;
                $this->Return['Message'] = "Successfully verified.";
                $Logs['LogType'] = "Verify";
                $Logs['PhoneNumber'] = $UserData['PhoneNumberForChange'];
                $this->Users_model->deleteLogs($Logs);
            }
        }else{

        }
        //$this->Recovery_model->deleteToken($this->Post['OTP'], 3); /* delete token in any case */
        $this->Return['Data'] = $UserData;
        $this->Return['Message'] = "Successfully verified.";
    }

    public function verify_get() {

        $OTP = @$this->input->get('otp');
        $UserData = array();
        $Msg = '';


        $this->load->model('Recovery_model');
        $UserID = $this->Recovery_model->verifyToken($OTP, 2);
        if (!$UserID) {
            $Msg = "Sorry, but this is an invalid link, or you have already verified your account.";
        } else {
            $UserData = $this->Users_model->getUsers('UserTypeID,FirstName,LastName,Email,EmailForChange,StatusID,ProfilePic', array('UserID' => $UserID));

            /* change entity status to activate */
            //$this->Entity_model->updateEntityInfo($UserID, array("StatusID" => 2));
            $this->Users_model->updateEmail($UserID, $UserData['EmailForChange']);

            $this->Recovery_model->deleteToken($OTP, 2); /* delete token in any case */
        }

        echo $this->load->view('email_verify', array('Error' => $Msg, 'UserData' => $UserData), true);
    }

    function randomString($length = 8) {
        $str = "";
        $characters = array_merge(range('A', 'Z'), range('a', 'z'), range('0', '9'));
        $max = count($characters) - 1;
        for ($i = 0; $i < $length; $i++) {
            $rand = mt_rand(0, $max);
            $str .= $characters[$rand];
        }
        return $str;
    }

    function sendUserMsgMobileVerification_post(){
        $this->Utility_model->sendSMS(
            array(
                'PhoneNumber' => $this->Post['MobileNumber'],
                'Text' => 'Please verify your mobile number '. SITE_HOST
            )
        );
    }

}
