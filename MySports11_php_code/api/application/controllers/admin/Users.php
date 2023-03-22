<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Users extends API_Controller_Secure {

    function __construct() {
        parent::__construct();
        $this->load->model('Recovery_model');
    }

    /*
      Description: 	Use to broadcast message.
      URL: 			/api_admin/users/broadcast/
     */

    public function broadcast_post() {
        /* Validation section */
        $this->form_validation->set_rules('SessionKey', 'SessionKey', 'trim|required|callback_validateSession');
        $this->form_validation->set_rules('Redirection', 'Redirection', 'trim');
        $this->form_validation->set_rules('Title', 'Title', 'trim|required');
        $this->form_validation->set_rules('Message', 'Message', 'trim' . ($this->Post['broadcast'] == 1 ? '' : '|required'));
        $this->form_validation->set_rules('selectedUser[]', 'Users', 'trim' . ($this->Post['UserType'] == "Selected" ? '|required' : ''));
        $this->form_validation->set_rules('MediaGUIDs', 'MediaGUIDs', 'trim'); /* Media GUIDs */
        $this->form_validation->validation($this);  /* Run validation */
        /* Validation - ends */

        // api accese for admin
        // $UserTypeID = getUserTypeID($this->SessionUserID);
        // $this->load->model('Admin_model');
        // $PermittedModules = $this->Admin_model->getPermittedModules($UserTypeID);
        // $ModuleData = ValidateUserAccess($PermittedModules,$_SERVER[REQUEST_URI]);
        // if($ModuleData == "Failed") {
        //     $this->Return['ResponseCode']   =   500;
        //     $this->Return['Message']        =   "You do not have permission to access this api.";
        //     exit;
        // }
        // end

        if($this->SessionUserID != 125) {
            $this->Return['ResponseCode']   =   500;
            $this->Return['Message']        =   "You do not have permission to access this api.";
            exit;
        }

        /* check for media present - associate media with this Post - ends */
        if ($this->Post['UserType'] == 'Selected') {
            $UsersData = $this->Users_model->getUsers('
                UserID, 
                Username,   
                Email,
                PhoneNumber         
                ', array('AdminUsers' => 'No', 'UserTypeID' => 2, 'UserArray' => $this->Post['selectedUser']), TRUE, 1, 1000000);
        } else {
            $UsersData = $this->Users_model->getUsers('
                UserID, 
                Username,   
                Email,
                PhoneNumber         
                ', array('AdminUsers' => 'No', 'UserTypeID' => 2), TRUE, 1, 1000000);
        }
        if ($UsersData) {
            if (!empty($this->Post['broadcast']) && $this->Post['broadcast'] == 1) {
                foreach ($UsersData['Data']['Records'] as $value) {
                    if (!empty($value['Email'])) {
                        /* Send Email to User */
                        send_mail(array(
                            'emailTo' => $value['Email'],
                            'template_id' => 'd-07b02214b4974bf98b1769fe1c1b14b2',
                            'Subject' => SITE_NAME . "-" . $this->Post['Title'],
                            'Message' => $this->Post['EmailMessage']
                        ));
                    }
                }
                $this->Return['Message'] = 'Email broadcasted.';
            } elseif (!empty($this->Post['broadcast']) && $this->Post['broadcast'] == 2) {
                $this->Utility_model->sendBulkSMS(array(
                    'PhoneNumber' => implode(',', array_filter(array_column($UsersData['Data']['Records'], 'PhoneNumber'))),
                    'Text' => $this->Post['Title'] . "- " . $this->Post['Message']
                ));
                $this->Return['Message'] = 'SMS broadcasted.';
            } elseif (!empty($this->Post['broadcast']) && $this->Post['broadcast'] == 3) {
                if ((!empty($this->Post['Normal']) && $this->Post['Normal'] == 1) || (!empty($this->Post['both']) && $this->Post['both'] == 1)) {
                    foreach ($UsersData['Data']['Records'] as $Value) {
                        $InsertData[] = array_filter(array(
                            "NotificationPatternID" => 2,
                            "UserID" => $this->SessionUserID,
                            "ToUserID" => $Value['UserID'],
                            "RefrenceID" => "",
                            "NotificationText" => $this->Post['Title'],
                            "NotificationMessage" => $this->Post['Message'],
                            "MediaID" => "",
                            "EntryDate" => date("Y-m-d H:i:s")
                        ));
                    }
                    if (!empty($InsertData)) {
                        $this->db->insert_batch('tbl_notifications', $InsertData);
                    }
                }
                if ((!empty($this->Post['Push']) && $this->Post['Push'] == 1) || (!empty($this->Post['both']) && $this->Post['both'] == 1)) {
                    if ($this->Post['UserType'] == 'Selected') {
                        sendPushMessage($UsersData['Data']['Records'][0]['UserID'], $this->Post['Title'], $this->Post['Message'],'',@$this->Post['Redirection']);
                    } else {
                        pushNotificationAndroidBroadcast($this->Post['Title'], $this->Post['Message'],'',@$this->Post['Redirection']);
                        pushNotificationIphoneBroadcast($this->Post['Title'], $this->Post['Message'],'',@$this->Post['Redirection']);
                    }
                }

                $this->Return['Message'] = 'Notification broadcasted.';
            } else {
                $this->Return['ResponseCode'] = 500;
                $this->Return['Message'] = 'Please Select broadcast Type.';
            }
        }
    }

    /*
      Name: 			getUsers
      Description: 	Use to get users list.
      URL: 			/api_admin/users/getProfile
     */

    public function index_post() {
        /* Validation section */
        $this->form_validation->set_rules('StoreGUID', 'StoreGUID', 'trim|callback_validateEntityGUID[Store,StoreID]');
        $this->form_validation->set_rules('Keyword', 'Search Keyword', 'trim');
        $this->form_validation->set_rules('AdminUsers', 'AdminUsers', 'trim');
        $this->form_validation->set_rules('PageNo', 'PageNo', 'trim|integer');
        $this->form_validation->set_rules('PageSize', 'PageSize', 'trim|integer');
        $this->form_validation->set_rules('Status', 'Status', 'trim|callback_validateStatus');
        $this->form_validation->validation($this);  /* Run validation */
        /* Validation - ends */

        /* $UsersData=$this->Users_model->getUsers('RegisteredOn,LastLoginDate,UserTypeName, FullName, Email, Username, ProfilePic, Gender, BirthDate, PhoneNumber, Status, StatusID',$this->Post, TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']); */

        $UsersData = $this->Users_model->getUsers((!empty($this->Post['Params']) ? $this->Post['Params'] : ''), array_merge($this->Post, array("StatusID" => @$this->StatusID, 'UserTypeID' => @$this->Post['UserTypeID'])), TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']);
        if ($UsersData) {
            $this->Return['Data'] = $UsersData['Data'];
        }
    }

    /*
      Description: 	Use to update user profile info.
      URL: 			/api_admin/entity/changeStatus/
     */

    public function changeStatus_post() {
        /* Validation section */
        $this->form_validation->set_rules('UserGUID', 'UserGUID', 'trim|required|callback_validateEntityGUID[User,UserID]');
        $this->form_validation->set_rules('Status', 'Status', 'trim|required|callback_validateStatus');
        $this->form_validation->validation($this);  /* Run validation */
        /* Validation - ends */

        $check = $this->Users_model->checkReferral($this->Post['ReferralCode'],$this->UserID);

        if(!$check){
            $this->Return['ResponseCode'] = 500;
            $this->Return['Message'] = "This referral code already used try aonther one";
            exit;
        }
       
        $this->Users_model->updateUserInfo($this->UserID, array("IsPrivacyNameDisplay" => $this->Post['IsPrivacyNameDisplay'], "isWithdrawal" => $this->Post['isWithdrawal']));
        $this->Entity_model->updateEntityInfo($this->UserID, array("StatusID" => $this->StatusID));

        $this->Return['Data'] = $this->Users_model->getUsers('FirstName,LastName,Email,ProfilePic,Status', array("UserID" => $this->UserID));
        $this->Return['Message'] = "Status has been changed.";
    }

    /*
      Description: 	Use to update user details as pan and bank details.
      URL: 			/api_admin/entity/changeVerificationStatus/
     */

    public function changeVerificationStatus_post() {
        /* Validation section */
        $this->form_validation->set_rules('UserGUID', 'UserGUID', 'trim|required|callback_validateEntityGUID[User,UserID]');
        $this->form_validation->set_rules('VetificationType', 'VetificationType', 'trim|required');
        if ($this->Post['VetificationType'] == 'PAN') {
            $this->form_validation->set_rules('PanStatus', 'PanStatus', 'trim|required|callback_validateStatus');
        }
        if ($this->Post['VetificationType'] == 'BANK') {
            $this->form_validation->set_rules('BankStatus', 'BankStatus', 'trim|required|callback_validateStatus');
        }
        if ($this->Post['VetificationType'] == 'Aadhar') {
            $this->form_validation->set_rules('AadharStatus', 'AadharStatus', 'trim|required|callback_validateStatus');
        }
        $this->form_validation->validation($this);  /* Run validation */
        // api accese for admin
        // $UserTypeID = getUserTypeID($this->SessionUserID);
        // $this->load->model('Admin_model');
        // $PermittedModules = $this->Admin_model->getPermittedModules($UserTypeID);
        // $ModuleData = ValidateUserAccess($PermittedModules,$_SERVER[REQUEST_URI]);

        $UserTypeData = getUserTypeDetails($this->SessionUserID, $Params='IsAdmin');
        if ($UserTypeData->IsAdmin != 'Yes') {
            $this->Return['ResponseCode']   =   500;
            $this->Return['Message']        =   "You do not have permission to access this api.";
            exit;
        }
        // if($this->SessionUserID != 125) {
        //     $this->Return['ResponseCode']   =   500;
        //     $this->Return['Message']        =   "You do not have permission to access this api.";
        //     exit;
        // }
        // end


                /** sql injection keyword*/
        $FindWord=array("sleep","delete","select","update","SLEEP","insert","DELETE","SELECT","UPDATE","INSERT","Sleep","Delete","Select","Update","Insert");
        $ReplaceWord=array("","","");
        $this->Post['PanStatus'] = str_replace($FindWord,$ReplaceWord,$this->Post['PanStatus']);
        $this->Post['BankStatus'] = str_replace($FindWord,$ReplaceWord,$this->Post['BankStatus']);
        $this->Post['AadharStatus'] = str_replace($FindWord,$ReplaceWord,$this->Post['AadharStatus']);

        /* Validation - ends */
        if ($this->Post['VetificationType'] == 'PAN' && !empty($this->Post['PanStatus'])) {
            $UpdateData = array("PanStatus" => $this->StatusID);
        }
        if ($this->Post['VetificationType'] == 'BANK' && !empty($this->Post['BankStatus'])) {
            $UpdateData = array("BankStatus" => $this->StatusID);
        }
        if ($this->Post['VetificationType'] == 'Aadhar' && !empty($this->Post['AadharStatus'])) {
            $UpdateData = array("AadharStatus" => $this->StatusID);
        }
        if (!empty($this->Post['Comments'])) {
            $UpdateData['Comments'] = $this->Post['Comments'];
        }

        $UsersData = $this->Users_model->getUsers((!empty($this->Post['Params']) ? $this->Post['Params'] : ''), array_merge($this->Post, array("StatusID" => @$this->StatusID)), TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']);

        if ($this->Post['VetificationType'] == 'BANK' && !empty($this->Post['BankStatus'])) {
            //$this->Users_model->updateUserInfoBankStatus($this->UserID, $UpdateData);
            $this->Users_model->updateUserInfo($this->UserID, $UpdateData); 
        }else{
            $this->Users_model->updateUserInfo($this->UserID, $UpdateData); 
        }


        /* Get User Data */
        $UserData = $this->Users_model->getUsers('FirstName,LastName,Email,ProfilePic,Status,PanStatus,BankStatus,AadharStatus,PhoneNumber', array("UserID" => $this->UserID));

        /* Manage Verification Bonus */
        if ($UserData['PanStatus'] == 'Verified' && $UserData['BankStatus'] == 'Verified' && !empty($UserData['PhoneNumber'])) {
            $BonusData = $this->db->query('SELECT ConfigTypeValue,StatusID FROM set_site_config WHERE ConfigTypeGUID = "VerificationBonus" LIMIT 1');
            if ($BonusData->row()->StatusID == 2) {
                $TransactionID = substr(hash('sha256', mt_rand() . microtime()), 0, 20);
                $WalletData = array(
                    "Amount" => $BonusData->row()->ConfigTypeValue,
                    "CashBonus" => $BonusData->row()->ConfigTypeValue,
                    "TransactionType" => 'Cr',
                    "TransactionID" => $TransactionID,
                    "Narration" => 'Verification Bonus',
                    "EntryDate" => date("Y-m-d H:i:s")
                );
                $this->Users_model->addToWallet($WalletData, $this->UserID, 5);
            }
        }
        $this->Return['Data'] = $UserData;
        $this->Return['Message'] = "Status has been changed.";
    }

    /*
      Name: 			updateUserInfo
      Description: 	Use to update user profile info.
      URL: 			/api_admin/updateUserInfo/
     */

    public function updateUserInfo_post() {
        /* Validation section */
        $this->form_validation->set_rules('UserGUID', 'UserGUID', 'trim|required|callback_validateEntityGUID[User,UserID]');
        $this->form_validation->set_rules('Status', 'Status', 'trim|callback_validateStatus');
        $this->form_validation->set_rules('UserTypeID', 'User Type', 'trim');
        $this->form_validation->validation($this);  /* Run validation */
        /* Validation - ends */

                /** sql injection keyword*/
        $FindWord=array("sleep","delete","select","update","SLEEP","insert","DELETE","SELECT","UPDATE","INSERT","Sleep","Delete","Select","Update","Insert");
        $ReplaceWord=array("","","");
        $this->Post['UserTypeID'] = str_replace($FindWord,$ReplaceWord,$this->Post['UserTypeID']);
        $this->Post['Status'] = str_replace($FindWord,$ReplaceWord,$this->Post['Status']);
        $this->Post['ReferralCode'] = str_replace($FindWord,$ReplaceWord,$this->Post['ReferralCode']);

        $this->Users_model->updateUserInfoAdminAccess($this->UserID, array_merge($this->Post, array("StatusID" => @$this->StatusID, "SkipPhoneNoVerification" => true)));
        $this->Return['Data'] = $this->Users_model->getUsers('StatusID,Status,ProfilePic,Email,Username,Gender,BirthDate,PhoneNumber,UserTypeName,RegisteredOn,LastLoginDate', array("UserID" => $this->UserID));
        $this->Return['Message'] = "Successfully updated.";
    }

    /*
      Name:         updateUserReferralCode
      Description:  Use to update user referral code.
      URL:          /api_admin/updateUserReferralCode/
     */

    public function updateUserReferralCode_post() {
        /* Validation section */
        $this->form_validation->set_rules('UserGUID', 'UserGUID', 'trim|required|callback_validateEntityGUID[User,UserID]');
        $this->form_validation->set_rules('ReferralCode', 'ReferralCode', 'trim|required|exact_length[6]|callback_checkUniqueReferralCode');
        $this->form_validation->validation($this);  /* Run validation */
        /* Validation - ends */

        $this->Users_model->updateUserReferralCode($this->UserID, $this->Post['ReferralCode']);
    }

    /*
      Name: 			add
      Description: 	Use to register user to system.
      URL: 			/api_admin/users/add/
     */

    public function add_post() {
        /* Validation section */
        $this->form_validation->set_rules('Email', 'Email', 'trim|required|valid_email|callback_validateEmail');
        $this->form_validation->set_rules('Password', 'Password', 'trim' . (empty($this->Post['Source']) || $this->Post['Source'] == 'Direct' ? '|required' : ''));
        $this->form_validation->set_rules('FirstName', 'FirstName', 'trim|required');
        $this->form_validation->set_rules('LastName', 'LastName', 'trim');
        $this->form_validation->set_rules('UserTypeID', 'UserTypeID', 'trim|required|callback_validateUserTypeId');
        $this->form_validation->set_rules('PhoneNumber', 'PhoneNumber', 'trim|callback_validatePhoneNumber');
        $this->form_validation->set_rules('Source', 'Source', 'trim|required|callback_validateSource');
        $this->form_validation->set_rules('Status', 'Status', 'trim|required|callback_validateStatus');

        $this->form_validation->set_rules('StoreGUID', 'StoreGUID', 'trim|callback_validateEntityGUID[Store,StoreID]');

        $this->form_validation->validation($this);  /* Run validation */
        /* Validation - ends */

        $UserID = $this->Users_model->addUser($this->Post, $this->Post['UserTypeID'], $this->SourceID, $this->StatusID);
        if (!$UserID) {
            $this->Return['ResponseCode'] = 500;
            $this->Return['Message'] = "An error occurred, please try again later.";
        } else {
            /* Send welcome Email to User with login details */
            // sendMail(array(
            // 	'emailTo' 		=> $this->Post['Email'],			
            // 	'emailSubject'	=> "Your Login Credentials - ".SITE_NAME,
            // 	'emailMessage'	=> emailTemplate($this->load->view('emailer/adduser',array("Name" =>  $this->Post['FirstName'], 'Password' => $this->Post['Password']),TRUE)) 
            // ));

            send_mail(array(
                'emailTo' => $this->Post['Email'],
                'template_id' => 'd-c1b4c1ecd3664f94960c099646365087',
                'Subject' => 'Your Login Credentials -' . SITE_NAME,
                "Name" => $this->Post['FirstName'],
                'Password' => $this->Post['Password'],
                'EmailText' => $this->Post['Email']
            ));
            return true;
        }
    }

    /*
      Name: 			getWallet
      Description: 	To get wallet data
      URL: 			/users/getWallet/
     */

    public function getWallet_post() {
        $this->form_validation->set_rules('UserGUID', 'UserGUID', 'trim|required|callback_validateEntityGUID[User,UserID]');
        $this->form_validation->set_rules('TransactionMode', 'TransactionMode', 'trim|required|in_list[All,WalletAmount,WinningAmount,CashBonus]');
        $this->form_validation->set_rules('Keyword', 'Search Keyword', 'trim');
        $this->form_validation->set_rules('OrderBy', 'OrderBy', 'trim');
        $this->form_validation->set_rules('Sequence', 'Sequence', 'trim|in_list[ASC,DESC]');
        $this->form_validation->validation($this);  /* Run validation */

        /** sql injection keyword*/
        $FindWord=array("sleep","delete","select","update","SLEEP","insert","DELETE","SELECT","UPDATE","INSERT","Sleep","Delete","Select","Update","Insert");
        $ReplaceWord=array("","","");
        $this->Post['Keyword'] = str_replace($FindWord,$ReplaceWord,$this->Post['Keyword']);
        $this->Post['OrderBy'] = str_replace($FindWord,$ReplaceWord,$this->Post['OrderBy']);
        $this->Post['Sequence'] = str_replace($FindWord,$ReplaceWord,$this->Post['Sequence']);

        /* Get Wallet Data */
        $WalletDetails = $this->Users_model->getWallet(@$this->Post['Params'], array_merge($this->Post, array('UserID' => $this->UserID)), TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']);
        if (!empty($WalletDetails)) {
            $this->Return['Data'] = $WalletDetails['Data'];
        }
    }

    /*
      Name: 			getWithdrawals
      Description: 	To get all Withdrawal requests
      URL: 			/users/getWithdrawals/
     */

    public function getWithdrawals_post() {
        $this->form_validation->set_rules('Keyword', 'Search Keyword', 'trim');
        $this->form_validation->set_rules('OrderBy', 'OrderBy', 'trim');
        $this->form_validation->set_rules('Sequence', 'Sequence', 'trim|in_list[ASC,DESC]');
        $this->form_validation->set_rules('Status', 'Status', 'trim|callback_validateStatus');
        $this->form_validation->validation($this);  /* Run validation */

        /** sql injection keyword*/
        $FindWord=array("sleep","delete","select","update","SLEEP","insert","DELETE","SELECT","UPDATE","INSERT","Sleep","Delete","Select","Update","Insert");
        $ReplaceWord=array("","","");
        $this->Post['Keyword'] = str_replace($FindWord,$ReplaceWord,$this->Post['Keyword']);
        $this->Post['OrderBy'] = str_replace($FindWord,$ReplaceWord,$this->Post['OrderBy']);
        $this->Post['Sequence'] = str_replace($FindWord,$ReplaceWord,$this->Post['Sequence']);

        /* Get Withdrawal Data */
        $WithdrawalsData = $this->Users_model->getWithdrawals(@$this->Post['Params'], array_merge($this->Post, array("StatusID" => @$this->StatusID)), TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']);
        if (!empty($WithdrawalsData)) {
            $this->Return['Data'] = $WithdrawalsData['Data'];
        }
    }

    /*
      Name: 			getWithdrawal
      Description: 	To get Withdrawal data
      URL: 			/users/getWithdrawals/
     */

    public function getWithdrawal_post() {
        $this->form_validation->set_rules('WithdrawalID', 'WithdrawalID', 'trim|required');
        $this->form_validation->set_rules('Keyword', 'Search Keyword', 'trim');
        $this->form_validation->set_rules('OrderBy', 'OrderBy', 'trim');
        $this->form_validation->set_rules('Sequence', 'Sequence', 'trim|in_list[ASC,DESC]');
        $this->form_validation->validation($this);  /* Run validation */

        /** sql injection keyword*/
        $FindWord=array("sleep","delete","select","update","SLEEP","insert","DELETE","SELECT","UPDATE","INSERT","Sleep","Delete","Select","Update","Insert");
        $ReplaceWord=array("","","");
        $this->Post['Keyword'] = str_replace($FindWord,$ReplaceWord,$this->Post['Keyword']);
        $this->Post['OrderBy'] = str_replace($FindWord,$ReplaceWord,$this->Post['OrderBy']);
        $this->Post['Sequence'] = str_replace($FindWord,$ReplaceWord,$this->Post['Sequence']);
        /* Get Withdrawal Data */
        $WithdrawalsData = $this->Users_model->getWithdrawals(@$this->Post['Params'], array_merge($this->Post, array('WithdrawalID' => @$this->Post['WithdrawalID'])), TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']);
        if (!empty($WithdrawalsData)) {
            $this->Return['Data'] = $WithdrawalsData['Data'];
        }
    }


    public function export_Users_list_csv_post() {
        $this->form_validation->set_rules('Status', 'Status', 'trim|callback_validateStatus');
        $this->form_validation->validation($this);  /* Run validation */

        /* Get Withdrawal Data */
        $UserType = $this->input->post('UserType');
        $from_date = $this->input->post('FromDate');
        $to_date = $this->input->post('ToDate');
        $user_type = 2;
        $Status = array("StatusID" => 2,'UserTypeID'=>array(2));
        if ($UserType == 'pending') {
            $Status = array("StatusID" => 1,'UserTypeID'=>array(2));
        }
        if($this->SessionUserID != 125){
            $this->Return['ResponseCode'] = 500;
            $this->Return['Message'] = "Invalid Authentication";
            exit;
        }
        $requestList = $this->Users_model->getUsers((!empty($this->Post['Params']) ? $this->Post['Params'] : ''), array_merge($this->Post, $Status), TRUE, 0, 0);
        // dump(json_encode( $requestList['Data']['Records']));
        $requestList = $requestList['Data']['Records'];
       
        if ($requestList) {
            $print_array = array();
            foreach ($requestList as $value) {
                if (!empty($value['Email']) || !empty($value['EmailForChange'])) {
                    $print_array[] = array(
                        'Email'     => (empty($value['Email']) ? $value['EmailForChange'] : $value['Email']),
                        'PhoneNumber' => (empty($value['PhoneNumber']) ? $value['PhoneNumberForChange'] : $value['PhoneNumber']),
                        'FirstName' => $value['FirstName'],
                        'Address'   => $value['Address'].' '. $value['Address1'],
                        'City'      => $value['CityName'],
                        'StateName' => $value['StateName'],
                        'Postal'    => $value['Postal'],
                        'Country'   => (empty($value['CountryName']) ? 'India' : $value['CountryName']),
                        'RegisteredOn' => $value['RegisteredOn']
                    );
                }
            }
            //dump($print_array);
            $Name = "uploads/RJPnwsfQjzhYqeYN/UsersList-".date('d-m-Y-H-i-s').'.csv';
            $fp = fopen($Name, 'w');
            $Name1 = "UsersList-".date('d-m-Y-H-i-s').'.csv';
            header('Content-type: application/csv');
            header('Content-Disposition: attachment; filename='.$Name1);
            fputcsv($fp, array('Email','Phone Number', 'Full Name','Address', 'City', 'State', 'Postal Code', 'Country','Registered On'));

            foreach ($print_array as $row) {
                fputcsv($fp, $row);
            }

            $this->Return['ResponseCode'] = 200;
            $this->Return['Message'] = "Successfully Exported";
            $this->Return['Data'] = BASE_URL .$Name;
        } else {
            $this->Return['Message'] = "Something Went Wrong";
        }
    }

    public function export_Withdrawal_list_csv_post() {
        $this->form_validation->set_rules('Status', 'Status', 'trim|callback_validateStatus');
        $this->form_validation->validation($this);  /* Run validation */

        /* Get Withdrawal Data */
        $from_date = $this->input->post('FromDate');
        $to_date = $this->input->post('ToDate');
        $user_type = 2;

        $requestList = $this->Users_model->getWithdrawals(@$this->Post['Params'], array_merge($this->Post, array("StatusID" => @$this->StatusID)), TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']);

        $requestList = $requestList['Data']['Records'];

        if (!empty($requestList)) {
            $print_array = array();
            $i = 1;
            foreach ($requestList as $value) {
                if (strstr($value['MediaBANK']['MediaCaption']->Bank, 'HDFC')) {
                    $TransactionType = 'I';
                }else{
                    $TransactionType = 'N';
                }
                $print_array[] = array(
                    'TransactionType' => $TransactionType,
                    'Blank_1'=>'',
                    'AccountNumber' => $value['MediaBANK']['MediaCaption']->AccountNumber,
                    'Amount'    => $value['Amount'],
                    'FirstName' => $value['MediaBANK']['MediaCaption']->FullName,
                    'Blank_2'=>'',
                    'Blank_3'=>'',
                    'Blank_4'=>'',
                    'Blank_5'=>'',
                    'Blank_6'=>'',
                    'Blank_7'=>'',
                    'Blank_8'=>'',
                    'Blank_9'=>'',
                    'Reference' => "Winnings",
                    'Blank_10'=>'',
                    'Blank_11'=>'',
                    'Blank_12'=>'',
                    'Blank_13'=>'',
                    'Blank_14'=>'',
                    'Blank_15'=>'',
                    'Blank_16'=>'',
                    'Blank_17'=>'',
                    'RequestDate' => $value['EntryDate'],
                    'Blank_18'=>'',
                    'IFSCCode'  => $value['MediaBANK']['MediaCaption']->IFSCCode,
                    'Bank'      => $value['MediaBANK']['MediaCaption']->Bank,
                    'BranchName'=> $value['MediaBANK']['MediaCaption']->BranchName,
                    'Email'=> "Fiktionworks@gmail.com"
                );
                $i++;
            }

            $Name = "uploads/SwyAFwg91XfJ2aL5/WithdrawalList-".date('d-m-Y-H-i-s').'.csv';
            $fp = fopen($Name, 'w');
            $Name1 = "WithdrawalList-".date('d-m-Y-H-i-s').'.csv';
            header('Content-type: application/csv');
            header('Content-Disposition: attachment; filename='.$Name1);
            // fputcsv($fp, array('S.no', 'User Id', 'User Name', 'Email', 'Phone', 'Amount', 'AccountNumber', 'Bank', 'IFSCCode', 'Request Date', 'Status'));
            fputcsv($fp, array('TransactionType','Blank_1','AccountNumber', 'Amount', 'FirstName','Blank_2','Blank_3','Blank_4','Blank_5','Blank_6','Blank_7','Blank_8','Blank_9','Reference','Blank_10','Blank_11','Blank_12','Blank_13','Blank_14','Blank_14','Blank_16','Blank_17','RequestDate','Blank_18','IFSCCode','Bank', 'BranchName', 'Email'));

            foreach ($print_array as $row) {
                fputcsv($fp, $row);
            }

            $this->Return['ResponseCode'] = 200;
            $this->Return['Message'] = "Successfully Exported";
            $this->Return['Data'] = BASE_URL .  $Name;
        } else {
            $this->Return['Message'] = "Something Went Wrong";
        }
    }

    public function export_Transactions_list_post() {
        $this->form_validation->set_rules('UserGUID', 'UserGUID', 'trim|required|callback_validateEntityGUID[User,UserID]');
        $this->form_validation->set_rules('TransactionMode', 'TransactionMode', 'trim|required|in_list[All,WalletAmount,WinningAmount,CashBonus]');
        $this->form_validation->set_rules('Keyword', 'Search Keyword', 'trim');
        $this->form_validation->set_rules('OrderBy', 'OrderBy', 'trim');
        $this->form_validation->set_rules('Sequence', 'Sequence', 'trim|in_list[ASC,DESC]');
        $this->form_validation->validation($this);  /* Run validation */

        $from_date = $this->input->post('FromDate');
        $to_date = $this->input->post('ToDate');
        $user_type = 2;

        /* Get Wallet Data */
        $requestList = $this->Users_model->getWallet(@$this->Post['Params'], array_merge($this->Post, array('UserID' => $this->UserID)), TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']);

        $requestList = $requestList['Data']['Records'];

        if ($requestList) {
            $print_array = array();
            $i = 1;
            foreach ($requestList as $value) {
                $print_array[] = array(
                    's_no' => $i,
                    'TransactionID' => $value['TransactionID'],
                    'Narration' => $value['Narration'],
                    'TransactionType' => $value['TransactionType'],
                    'OpeningBalance' => $value['OpeningBalance'],
                    'Amount' => $value['Amount'],
                    'ClosingBalance' => $value['ClosingBalance'],
                    'AvailableBalance' => ($value['WalletAmount'] + $value['CashBonus']) + $value['WinningAmount'],
                    'EntryDate' => $value['EntryDate'],
                    'Status' => $value['Status']);
                $i++;
            }

            $fp = fopen('TransactionList.csv', 'w');

            header('Content-type: application/csv');
            header('Content-Disposition: attachment; filename=WithdrawalList.csv');
            fputcsv($fp, array('S.no', 'Transaction ID', 'Narration', 'Transaction Type', 'OpeningBalance', 'Amount', 'ClosingBalance', 'AvailableBalance', 'Entry Date', 'Status'));

            foreach ($print_array as $row) {
                fputcsv($fp, $row);
            }

            $this->Return['ResponseCode'] = 200;
            $this->Return['Message'] = "Successfully Exported";
            $this->Return['Data'] = BASE_URL . 'TransactionList.csv';
        } else {
            $this->Return['Message'] = "Something Went Wrong";
        }
    }

    /*
      Description: 	Use to update user profile info.
      URL: 			/api_admin/entity/changeStatus/
     */

    public function changeWithdrawalStatus_post() {
        /* Validation section */
        $this->form_validation->set_rules('WithdrawalID', 'WithdrawalID', 'trim|required');
        $this->form_validation->set_rules('Status', 'Status', 'trim|required|callback_validateStatus');
        $this->form_validation->validation($this);  /* Run validation */
        /* Validation - ends */
        /* print_r($this->Post);
          print_r($this->StatusID);die(); */
        $this->Users_model->updateWithdrawal(@$this->Post['WithdrawalID'], array("StatusID" => $this->StatusID, "Comments" => $this->Post['Comments']));
        $this->Return['Data'] = $this->Users_model->getWithdrawals(@$this->Post['Params'], array("WithdrawalID" => @$this->Post['WithdrawalID']));
        $this->Return['Message'] = "Status has been changed.";
    }

    /*
      Description : To add cash bonus to user

     */

    public function addCashBonus_post() {
        $this->form_validation->set_rules('UserGUID', 'UserGUID', 'trim|required|callback_validateEntityGUID[User,UserID]');
        $this->form_validation->set_rules('Status', 'Status', 'trim|required|callback_validateStatus');
        $this->form_validation->set_rules('Amount', 'Amount', 'trim|required|numeric');
        $this->form_validation->set_rules('Narration', 'Narration', 'trim|required');
        $this->form_validation->validation($this);  /* Run validation */
        $this->Users_model->addToWallet(array_merge($this->Post, array('CashBonus' => $this->Post['Amount'], 'TransactionType' => 'Cr')), $this->UserID, $this->StatusID);


        $this->Notification_model->addNotification('bonus', 'Admin Bonus', $this->UserID, $this->UserID, '', '' . $this->Post['Amount'] . ' has been credited in your bonus Wallet by admin');

        $this->Return['Message'] = "Cash bonus added Successfully.";
    }

    /*
      Description : To add cash deposit to user

     */

    public function addCashDeposit_post() {
        $this->form_validation->set_rules('UserGUID', 'UserGUID', 'trim|required|callback_validateEntityGUID[User,UserID]');
        $this->form_validation->set_rules('Status', 'Status', 'trim|required|callback_validateStatus');
        $this->form_validation->set_rules('Amount', 'Amount', 'trim|required|numeric');
        $this->form_validation->set_rules('Narration', 'Narration', 'trim|required');
        $this->form_validation->validation($this);  /* Run validation */
        $this->Users_model->addToWallet(array_merge($this->Post, array('WalletAmount' => $this->Post['Amount'], 'TransactionType' => 'Cr')), $this->UserID, $this->StatusID);
        $this->Return['Message'] = "Cash added Successfully.";
    }

    /*
      Name: 			getReferredUsers
      Description: 	To get all referred users
      URL: 			/users/getReferredUsers/
     */

    public function getReferredUsers_post() {
        $this->form_validation->set_rules('UserGUID', 'UserGUID', 'trim|required|callback_validateEntityGUID[User,UserID]');
        $this->form_validation->set_rules('OrderBy', 'OrderBy', 'trim');
        $this->form_validation->set_rules('Sequence', 'Sequence', 'trim|in_list[ASC,DESC]');
        $this->form_validation->validation($this);  /* Run validation */

        /** sql injection keyword*/
        $FindWord=array("sleep","delete","select","update","SLEEP","insert","DELETE","SELECT","UPDATE","INSERT","Sleep","Delete","Select","Update","Insert");
        $ReplaceWord=array("","","");
        $this->Post['OrderBy'] = str_replace($FindWord,$ReplaceWord,$this->Post['OrderBy']);
        $this->Post['Sequence'] = str_replace($FindWord,$ReplaceWord,$this->Post['Sequence']);

        /* Get Referred Users Data */
        $ReferredUsersData = $this->Users_model->getUsers(@$this->Post['Params'], array('ReferredByUserID' => $this->UserID, 'OrderBy' => @$this->Post['OrderBy'], 'Sequence' => @$this->Post['Sequence']), TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']);
        if (!empty($ReferredUsersData)) {
            $this->Return['Data'] = $ReferredUsersData['Data'];
        }
    }

    /*
      Name:             changePassword
      Description:  Use to change account login password by admin.
      URL:          /api/users/changePassword
     */

    public function changePassword_post() {
        /* Validation section */
        $this->form_validation->set_rules('Password', 'Password', 'trim|required');
        $this->form_validation->set_rules('UserGUID', 'UserGUID', 'trim|callback_validateEntityGUID[User,UserID]');
        $this->form_validation->validation($this);  /* Run validation */
        /* Validation - ends */
        $Request = $this->Users_model->updateUserLoginInfo($this->UserID, array("Password" => $this->Post['Password']), DEFAULT_SOURCE_ID);
        if ($Request) {
            /* Destroy all Sessions */
            $this->db->query('DELETE FROM tbl_users_session WHERE UserID=' . $this->UserID);
            $this->Return['Message'] = "New password has been set.";
        }
    }


    /* -------------- Callback UserTypeId ------------- */

    function validateUserTypeId($UserTypeID) {
        $ExistUserType = $this->Common_model->getUserTypes('UserTypeID', array("UserTypeID" => $UserTypeID));
        if ($ExistUserType) {
            $this->UserTypeID = $ExistUserType['UserTypeID'];
            return TRUE;
        }
        $this->form_validation->set_message('validateUserTypeId', 'Invalid {field}.');
        return FALSE;
    }

    function checkReferral($Referral,$UserID){
        
    }

    function checkUniqueReferralCode($ReferralCode) {
        $Query = $this->db->query('SELECT UserID FROM tbl_referral_codes WHERE ReferralCode = "'.$this->Post['ReferralCode'].'" LIMIT 1');
        if ($Query->num_rows() > 0 && $Query->row()->UserID != $this->UserID) {
            $this->form_validation->set_message('validateUserTypeId', 'Referral Code already taken.');
            return FALSE;
        }
        return TRUE;
    }

    /* ---------------End----------- */
}
