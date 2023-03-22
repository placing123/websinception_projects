<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Utilities extends API_Controller {

    function __construct() {
        parent::__construct();
        $this->load->model(array('Utility_model','Post_model','Sports_model','Contest_model','Users_model','AuctionDrafts_model','PreContest_model','SnakeDrafts_model'));
    }

    /*
      Description: 	get site setting.
      URL: 			/api/utilities/setting/
     */
    function setting_get() {
        $ConfigData = $this->Utility_model->getConfigs(@$this->Post);
        if (!empty($ConfigData)) {
            $this->Return['Data'] = $ConfigData['Data'];
        }
    }

    /*
      Description: 	Use to send email to webadmin.
      URL: 			/api/utilities/contact/
     */
    public function contact_post() {
        /* Validation section */
        $this->form_validation->set_rules('Name', 'Name', 'trim');
        $this->form_validation->set_rules('Email', 'Email', 'trim|required|valid_email');
        $this->form_validation->set_rules('PhoneNumber', 'PhoneNumber', 'trim');
        $this->form_validation->set_rules('Title', 'Title', 'trim');
        $this->form_validation->set_rules('Message', 'Message', 'trim|required');
        $this->form_validation->validation($this); /* Run validation */

        /* Validation - ends */
        send_mail(array(
            'emailTo' => SITE_CONTACT_EMAIL,
            'template_id' => 'd-45f2a1158ad44872832de03817165ff0',
            'Subject' => $this->Post['Name'] . ' filled out the contact form on ' . SITE_NAME,
            "Name" => $this->Post['Name'],
            'Email' => $this->Post['Email'],
            'PhoneNumber' => $this->Post['PhoneNumber'],
            'Title' => $this->Post['Title'],
            'Message' => $this->Post['Message']
        ));
    }

    /*
      Description:  Use execute cron jobs.
      URL:      /api/utilities/getCountries
     */
    public function getCountries_post() {
        $CountryData = $this->Utility_model->getCountries();
        if (!empty($CountryData)) {
            $this->Return['Data'] = $CountryData['Data'];
        }
    }

    public function getStates_post() {
        /* Validation section */
        $this->form_validation->set_rules('CountryCode', 'Country Code', 'trim|required');
        $this->form_validation->validation($this); /* Run validation */
        /* Validation - ends */

        $StateData = $this->Utility_model->getStates(array('CountryCode' => $this->Post['CountryCode'], 'Status' => 2));

        if (!empty($StateData)) {
            $this->Return['Data'] = $StateData['Data'];
        }
    }

    /*
      Description:    Use to get list of random posts.
      URL:            /api/utilities/getPosts
     */
    public function getPosts_post() {
        
        /* Validation section */
        $this->form_validation->set_rules('PageNo', 'PageNo', 'trim|integer');
        $this->form_validation->set_rules('PageSize', 'PageSize', 'trim|integer');
        $this->form_validation->validation($this);  /* Run validation */
        /* Validation - ends */

        $Posts = $this->Post_model->getPosts('
            P.PostGUID,
            P.PostContent,
            P.PostCaption,
            P.Sort,
            ', array(), TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']);
        if ($Posts) {
            $this->Return['Data'] = $Posts['Data'];
        }
    }

    public function sendAppLink_post() {
        $this->form_validation->set_rules('PhoneNumber', 'PhoneNumber', 'trim|required');
        $this->form_validation->validation($this);  /* Run validation */

        $this->Return['CaptchaEnable'] = "No";
        $this->Post['LogType'] = "Invite";
        $this->Users_model->addLoginLogs($this->Post);
        // $this->Utility_model->sendSMS(array(
        //     'PhoneNumber' => $this->Post['PhoneNumber'],
        //     'Text' => "Here is the new " . SITE_NAME . " Android Application! Click on the link to download the App and Start Winning. ".SITE_HOST."android/SportsDemo.apk"
        // ));
        $this->Utility_model->sendSMS_MSG91(array(
                'FlowID'        => SHARE_APP_LINK,
                'PhoneNumber'   => $this->Post['PhoneNumber'],
                'OTP'          => SITE_HOST."android/SportsDemo.apk"
            ));
        $this->Return['Message'] = "Link Sent successfully.";
    }

    /*
      Description:  Use to create pre draft contest
      URL:      /api/utilities/deleteLoginLogs
     */
    public function deleteLoginLogs_get() {
        $this->Users_model->deleteLoginLogsAll();
    }

    /*
      Description:  Use to create pre draft contest
      URL:      /api/utilities/createPreContest
     */

    public function createPreContest_get() {
        $this->PreContest_model->createPreContest();
    }

    /*
      Description:  Use to Delete Reminder Notification
      URL:      /api/utilities/deleteNotifications
     */
    public function deleteNotifications_get() {
      $this->Utility_model->deleteNotifications();
      $this->Users_model->deleteTokens();
      $this->Users_model->deleteLoginLogsAll();
    }

    /*
      Description: 	Cron jobs to get series data.
      URL: 			/api/utilities/getSeriesLive
     */
    public function getSeriesLive_get() {
        $this->Sports_model->getSeriesRoundsLiveCricketAPI();
        $this->Sports_model->updateSeriesANDRoundsStatusByMatch();
        $this->Sports_model->getSeriesLiveCricketAPI();
        echo "Success";
    }

    /*
      Description: 	Cron jobs to get matches data.
      URL: 			/api/utilities/getMatchesLive
     */
    public function getMatchesLive_get() {
        $this->Sports_model->getMatchesLiveCricketApi();
        echo "Success";
    }

    /*
      Description: 	Cron jobs to get matches data.
      URL: 			/api/utilities/getMatchesLiveMatric101
     */

    public function getMatchesLiveMatric101_get() {
        $this->Sports_model->getMatchesLiveCricketApiMatric101();
        echo "Success";
    }

    /*
      Description: 	Cron jobs to get matches data.
      URL: 			/api/utilities/getPlayersLiveMatric101
     */

    public function getPlayersLiveMatric101_get() {
        $this->Sports_model->getPlayersLiveCricketApiMatric101();
        echo "Success";
    }

    /*
      Description: 	Cron jobs to get match live score
      URL: 			/api/utilities/getMatchScoreLiveMatric101
     */

    public function getMatchScoreLiveMatric101_get() {
        $this->Sports_model->getMatchScoreLiveCricketApiMatric101();
        $this->Sports_model->getJoinedContestTeamPoints();
        $this->Sports_model->getDraftJoinedUserTeamsPlayerPoints();
        echo "Success";
    }
    
    /*
      Description: 	Cron jobs to get update match playing11
      URL: 			/api/utilities/getMatchScoreLive
    */
    public function getLivePlaying11MatchPlayerMatric101_get() {
        $this->Sports_model->getLivePlaying11MatchPlayerCricketAPIMatric101();
    }

    /*
      Description: 	Cron jobs to get players data.
      URL: 			/api/utilities/getPlayersLive
     */
    public function getPlayersLive_get() {
        $this->Sports_model->getPlayersLiveCricketApi();
        echo "Success";
    }

    /*
      Description: 	Cron jobs to get player stats data.
      URL: 			/api/utilities/getPlayerStatsLive
     */

    public function getPlayerStatsLive_get() {
        $this->Sports_model->getPlayerStatsLiveCricketApi();
        echo "Success";
    }

    /*
      Description: 	Cron jobs to get match live score
      URL: 			/api/utilities/getMatchScoreLive
     */
    public function getMatchScoreLive_get() {
        $this->Sports_model->getMatchScoreLiveCricketApi();
        $this->Sports_model->getPlayerPointsCricketAPI();
        $this->Sports_model->getJoinedContestTeamPoints();
        echo "Success";
    }

    /*
      Description: 	Cron jobs to get update match playing11
      URL: 			/api/utilities/getLivePlaying11MatchPlayer
    */
    public function getLivePlaying11MatchPlayer_get() {
        $this->Sports_model->getLivePlaying11MatchPlayerCricketAPI();
    }

    public function send_post() {
        sendPushMessage($this->Post['UserID'], $this->Post['Title'], $this->Post['Message'], $this->Post['Data']);
    }

    /*
      Description: 	Cron jobs to auto cancel contest.
      URL: 			/api/utilities/autoCancelContest
     */
    public function autoCancelContest_get() {
        $this->Sports_model->autoCancelContest();
    }

    /*
      Description:  Cron jobs to auto cancel contest refund amount.
      URL:          /api/utilities/refundAmountCancelContest
     */
    public function refundAmountCancelContest_get() {
        $this->Sports_model->refundAmountCancelContest();
    }

    /*
      Description: 	Cron jobs to get player points.
      URL: 			/api/utilities/getPlayerPoints
     */
    public function getPlayerPoints_get() {
        $this->Sports_model->getPlayerPointsCricketAPI();
    }

    /*
      Description: 	Cron jobs to get joined player points.
      URL: 			/api/utilities/getJoinedContestPlayerPoints
     */

    public function getJoinedContestPlayerPoints_get() {
        $this->Sports_model->getJoinedContestTeamPoints();
    }

    /*
      Description:  Cron jobs to transfer joined contest data (MongoDB To MySQL).
      URL:          /api/utilities/tranferJoinedContestData
     */
    public function tranferJoinedContestData_get() {
        $this->Sports_model->tranferJoinedContestData();
    }

    /*
      Description:  Cron jobs to transfer joined contest data (MongoDB To MySQL).
      URL:          /api/utilities/matchNotificationAdmin
     */
    public function matchNotificationAdmin_get() {
        $this->Sports_model->matchNotificationAdmin();
    }

    /*
      Description:  Description: upcoming Matches change role notification 
      URL:          /api/utilities/UpcomingMatchesChangeRoleNotificationAdmin
     */
    public function UpcomingMatchesChangeRoleNotificationAdmin_get() {
        $this->Sports_model->UpcomingMatchesChangeRoleNotificationAdmin();
    }

    /*
      Description: 	Cron jobs to auto set winner.
      URL: 			/api/utilities/setContestWinners
     */
    public function setContestWinners_get() {
        $this->Sports_model->setContestWinners();
    }

    /*
      Description: 	Cron jobs to auto set winner distribue amount.
      URL: 			/api/utilities/amountDistributeContestWinner
     */
    public function amountDistributeContestWinner_get() {
        $this->Sports_model->amountDistributeContestWinner();
        $this->Sports_model->amountDistributeContestWinnerBonus();
    }

    /*
      Description:  Cron jobs to auto send mail to winner.
      URL:          /api/utilities/setContestWinners
     */
    public function sendMailContestWinners_get() {
        $this->Sports_model->ContestWinnersMailSend();
    }

    /*
      Description:  Cron jobs for Notification for upcoming Matches.
      URL:          /api/utilities/notifyUpcomingMatches
     */
    public function notifyUpcomingMatches_get() {
        $this->Sports_model->notifyUpcomingMatches();
    }

    /*
      Description:  Cron jobs for Get Confirm Not Filled Contest.
      URL:          /api/utilities/setWinningAmountForConfirmNotFillContest
     */
    public function setWinningAmountForConfirmNotFillContest_get(){
        $this->Sports_model->setWinningAmountForConfirmNotFillContest();      
    }

    /*
      Description:  Cron jobs for Get Pending PhonePe Transaction Status.
      URL:          /api/utilities/getPhonePeTransactionStatus
     */
    public function getPhonePeTransactionStatus_get(){
        $this->Sports_model->getPhonePeTransactionStatus();      
    }


    public function NotificationBroadcastScheduling_get() {
        $this->Utility_model->NotificationBroadcastScheduling();
    }

    /*
      Description:  get Backup team and user team player.
      URL:          /api/utilities/getBackupMatchPlayer
     */
    public function getBackupMatchPlayer_get() {
        $this->Sports_model->getBackupMatchPlayer();
        $this->Sports_model->getBackupUserTeam();
    }

    /*
      Description:  get Backup team and user team player.
      URL:          /api/utilities/getBackupContest
     */
    public function getBackupContest_get() {
        $this->Sports_model->removePrivateContestUnjoined();
        $this->Sports_model->getBackupContest();
        
    }

    /*
    Description: Use to manage cashfree webhook response
    URL: /api/utilities/cashFreeWebHookResponse
    */
    public function cashFreeWebHookResponse_post(){
        $this->Users_model->cashFreeWebHookResponse($this->input->post());
    }

    /*
      Description: To get statics
     */

    public function dashboardStatics_post() {
        $SiteStatics = new stdClass();
        $SiteStatics = $this->db->query('SELECT
                                            TotalUnverifiedUsers,
                                            TotalWebUsers,
                                            TotalAndoridUsers,
                                            TotalIosUsers,
                                            TotalSubscriptionUsers,
                                            TotalUsers,
                                            TotalContest,
                                            TodayContests,
                                            TotalDeposits,
                                            TotalWithdraw,
                                            TodayDeposit,
                                            NewUsers,
                                            TotalDeposits - TotalWithdraw AS TotalEarning,
                                            PendingWithdraw
                                        FROM
                                            (SELECT
                                              (
                                                    SELECT
                                                        COUNT(U.UserID) AS `TotalUsers`
                                                    FROM
                                                        `tbl_users` U,tbl_entity E
                                                    WHERE E.EntityID=U.UserID AND
                                                        U.`UserTypeID` = 2 AND E.StatusID = 1
                                                ) AS TotalUnverifiedUsers,
                                                (
                                                    SELECT
                                                        COUNT(U.UserID) AS `TotalUsers`
                                                    FROM
                                                        `tbl_users` U,tbl_entity E
                                                    WHERE E.EntityID=U.UserID AND
                                                        U.`UserTypeID` = 2 AND E.StatusID = 2
                                                ) AS TotalUsers,
                                                (
                                                    SELECT
                                                        COUNT(UserID) AS `NewUsers`
                                                    FROM
                                                        `tbl_users` U, `tbl_entity` E
                                                    WHERE
                                                        U.`UserTypeID` = 2 AND U.UserID = E.EntityID AND DATE(E.EntryDate) = "' . date('Y-m-d') . '"
                                                ) AS NewUsers,
                                                (
                                                    SELECT
                                                        COUNT(ContestID) AS `TotalContest`
                                                    FROM
                                                        `sports_contest`
                                                ) AS TotalContest,
                                                (
                                                    SELECT COUNT(DISTINCT(C.ContestID)) FROM `sports_contest` C, `sports_matches` M WHERE C.MatchID = M.MatchID AND DATE(M.MatchStartDateTime) = "' . date('Y-m-d') . '"
                                                ) AS TodayContests,
                                                (
                                                    SELECT
                                                        IFNULL(SUM(`WalletAmount`),0) AS TotalDeposits
                                                    FROM
                                                        `tbl_users_wallet`
                                                    WHERE
                                                        `Narration`= "Deposit Money" AND
                                                        `StatusID` = 5
                                                ) AS TotalDeposits,
                                                (
                                                    SELECT
                                                        IFNULL(SUM(`WalletAmount`),0) AS TodayDeposit
                                                    FROM
                                                        `tbl_users_wallet`
                                                    WHERE
                                                        `Narration`= "Deposit Money" AND
                                                        `StatusID` = 5 AND DATE(EntryDate) = "' . date('Y-m-d') . '"
                                                ) AS TodayDeposit,
                                                (
                                                    SELECT
                                                        IFNULL(SUM(`Amount`),0) AS TotalWithdraw
                                                    FROM
                                                        `tbl_users_wallet`
                                                    WHERE
                                                        `StatusID` = 5 AND Narration = "Withdrawal Request"
                                                ) AS TotalWithdraw,
                                                (
                                                    SELECT
                                                        IFNULL(SUM(`Amount`),0) AS TotalWithdraw
                                                    FROM
                                                        `tbl_users_withdrawal`
                                                    WHERE
                                                        `StatusID` = 1
                                                ) AS PendingWithdraw,
                                                (
                                                    SELECT
                                                        IFNULL(Count(`UserID`),0) AS TotalWebUsers
                                                    FROM
                                                        `tbl_users`
                                                    WHERE
                                                        `LoginType` = "Web"
                                                ) AS TotalWebUsers,
                                                (
                                                    SELECT
                                                        IFNULL(Count(`UserID`),0) AS TotalAndoridUsers
                                                    FROM
                                                        `tbl_users`
                                                    WHERE
                                                        `LoginType` = "Andorid"
                                                ) AS TotalAndoridUsers,
                                                (
                                                    SELECT
                                                        IFNULL(Count(`UserID`),0) AS TotalIosUsers
                                                    FROM
                                                        `tbl_users`
                                                    WHERE
                                                        `LoginType` = "Ios"
                                                ) AS TotalIosUsers,
                                                (
                                                    SELECT
                                                        IFNULL(Count(`UserID`),0) AS TotalSubscriptionUsers
                                                    FROM
                                                        `tbl_users`
                                                    WHERE
                                                        SubscriptionStart <= "'.date('Y-m-d h:i:s').'" 
                                                        AND 
                                                        SubscriptionEnd >= "'.date('Y-m-d h:i:s').'"

                                                ) AS TotalSubscriptionUsers
                                            ) Total'
                )->row();
        $this->Return['Data'] = $SiteStatics;
    }

    /*
      Description: To get Wallet statics
     */

    public function dashboardWalletStatics_post() {
        $SiteStatics = new stdClass();
        $SiteStatics = $this->db->query('SELECT
                                            TotalAvailableWalletAmount,
                                            TotalAvailableCashBonus,
                                            TotalAvailableWinningAmount
                                        FROM
                                            (SELECT                                             
                                                (
                                                  SELECT
                                                      IFNULL(SUM(`WalletAmount`),0) AS TotalAvailableWalletAmount
                                                  FROM
                                                      `tbl_users` U,tbl_entity E
                                                  WHERE E.EntityID=U.UserID AND
                                                        U.`UserTypeID` = 2 AND E.StatusID = 2
                                                ) AS TotalAvailableWalletAmount,
                                                (
                                                  SELECT
                                                      IFNULL(SUM(`CashBonus`),0) AS TotalAvailableCashBonus
                                                  FROM
                                                      `tbl_users` U,tbl_entity E
                                                  WHERE E.EntityID=U.UserID AND
                                                      U.`UserTypeID` = 2 AND E.StatusID = 2
                                                ) AS TotalAvailableCashBonus,
                                                (
                                                  SELECT
                                                      IFNULL(SUM(`WinningAmount`),0) AS TotalAvailableWinningAmount
                                                  FROM
                                                      `tbl_users` U,tbl_entity E
                                                  WHERE E.EntityID=U.UserID AND
                                                      U.`UserTypeID` = 2 AND E.StatusID = 2
                                                ) AS TotalAvailableWinningAmount
                                            ) Total'
                )->row();
        $this->Return['Data'] = $SiteStatics;
    }

    /*
      Name:           getTotalDeposits
      Description:    To get Total Deposits data
      URL:            /Utilites/getTotalDeposits/
     */

    public function getTotalDeposits_post() {
        /* Get Total Deposit Data */
        $WalletDetails = $this->Utility_model->getTotalDeposit(@$this->Post['Params'], $this->Post, TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']);
        if (!empty($WalletDetails)) {
            $this->Return['Data'] = $WalletDetails['Data'];
        }
    }

    /*
      Description:  Use to get app version details
      URL:      /api/utilities/getAppVersionDetails
     */

    public function getAppVersionDetails_post() {
        $this->form_validation->set_rules('SessionKey', 'SessionKey', 'trim|callback_validateSession');
        $this->form_validation->set_rules('UserAppVersion', 'UserAppVersion', 'trim|required');
        $this->form_validation->set_rules('DeviceType', 'Device type', 'trim|required|callback_validateDeviceType');
        $this->form_validation->validation($this); /* Run validation */
        /* Validation - ends */

        $VersionData = $this->Utility_model->getAppVersionDetails();
        if (!empty($VersionData)) {
            $this->Return['Data'] = $VersionData;
        }
    }

    /*
      Description:  Use to get referel amount details.
      URL:      /api/utilities/getReferralDetails
     */

    public function getReferralDetails_post() {
        $ReferByQuery = $this->db->query('SELECT ConfigTypeValue FROM set_site_config WHERE ConfigTypeGUID = "ReferByDepositBonus" AND StatusID = 2 LIMIT 1');
        $ReferToQuery = $this->db->query('SELECT ConfigTypeValue FROM set_site_config WHERE ConfigTypeGUID = "ReferToDepositBonus" AND StatusID = 2 LIMIT 1');
        $this->Return['Data']['ReferByBonus'] = ($ReferByQuery->num_rows() > 0) ? $ReferByQuery->row()->ConfigTypeValue : 0;
        $this->Return['Data']['ReferToBonus'] = ($ReferToQuery->num_rows() > 0) ? $ReferToQuery->row()->ConfigTypeValue : 0;
    }

    /*
      Description:  Use to get referel amount details.
      URL:      /api/utilities/razorpayWebResponse
     */

    public function razorpayWebResponse_post() {

        $Input = file_get_contents("php://input");
        $PayResponse = json_decode($Input, 1);


        $InsertData = array_filter(array(
            "PageGUID" => "RazorPay",
            "Title" => "Test",
            "Content" => json_encode($Input)
        ));
        $this->db->insert('set_pages', $InsertData);

        $payResponse = $PayResponse['payload']['payment']['entity'];
        if ($payResponse['status'] === "authorized") {

            $this->db->trans_start();

            $payment_id = $payResponse['id'];
            /* update profile table */
            $UpdataData = array_filter(
                    array(
                        'PaymentGatewayResponse' => @$Input,
                        'ModifiedDate' => date("Y-m-d H:i:s"),
                        'StatusID' => 5
            ));
            $this->db->where('WalletID', $payResponse['notes']['OrderID']);
            $this->db->where('UserID', $payResponse['notes']['UserID']);
            $this->db->where('StatusID', 1);
            $this->db->limit(1);
            $this->db->update('tbl_users_wallet', $UpdataData);
            if ($this->db->affected_rows() <= 0) return FALSE;

            $Amount = $payResponse['amount'] / 100;
            $this->db->set('WalletAmount', 'WalletAmount+' . $Amount, FALSE);
            $this->db->where('UserID', $payResponse['notes']['UserID']);
            $this->db->limit(1);
            $this->db->update('tbl_users');

            $UserID = $payResponse['notes']['UserID'];
            $this->Notification_model->addNotification('AddCash', 'Cash Added', $UserID, $UserID, '', 'Deposit of ' . DEFAULT_CURRENCY . @$Amount . ' is Successful.');

            $CouponDetails = $this->Users_model->getWallet('CouponDetails', array("WalletID" => $payResponse['notes']['OrderID']));

            /* Check Coupon Details */
            if (!empty($CouponDetails['CouponDetails'])) {
                $WalletData = array(
                    "Amount" => $CouponDetails['CouponDetails']['DiscountedAmount'],
                    "CashBonus" => $CouponDetails['CouponDetails']['DiscountedAmount'],
                    "TransactionType" => 'Cr',
                    "Narration" => 'Coupon Discount',
                    "EntryDate" => date("Y-m-d H:i:s")
                );
                $this->Users_model->addToWallet($WalletData, $UserID, 5);
            }

            $TotalDeposits = $this->db->query('SELECT COUNT(*) TotalDeposits FROM `tbl_users_wallet` WHERE `UserID` = ' . $UserID . ' AND Narration = "Deposit Money" AND StatusID = 5')->row()->TotalDeposits;

            if ($TotalDeposits == 1) { // On First Successful Transaction

                /* Get Deposit Bonus Data */
                $DepositBonusData = $this->db->query('SELECT ConfigTypeValue,StatusID FROM set_site_config WHERE ConfigTypeGUID = "FirstDepositBonus" LIMIT 1');
                if ($DepositBonusData->row()->StatusID == 2) {

                    $MinimumFirstTimeDepositLimit = $this->db->query('SELECT ConfigTypeValue FROM set_site_config WHERE ConfigTypeGUID = "MinimumFirstTimeDepositLimit" LIMIT 1');
                    $MaximumFirstTimeDepositLimit = $this->db->query('SELECT ConfigTypeValue FROM set_site_config WHERE ConfigTypeGUID = "MaximumFirstTimeDepositLimit" LIMIT 1');

                    if ($MinimumFirstTimeDepositLimit->row()->ConfigTypeValue <= @$Amount && $MaximumFirstTimeDepositLimit->row()->ConfigTypeValue >= @$Amount) {
                        /* Update Wallet */
                        $FirstTimeAmount = (@$Amount * $DepositBonusData->row()->ConfigTypeValue) / 100;
                        $WalletData = array(
                            "Amount" => $FirstTimeAmount,
                            "CashBonus" => $FirstTimeAmount,
                            "TransactionType" => 'Cr',
                            "Narration" => 'First Deposit Bonus',
                            "EntryDate" => date("Y-m-d H:i:s")
                        );
                        $this->Users_model->addToWallet($WalletData, $UserID, 5);
                    }
                }

                /* Get User Data */
                $UserData = $this->Users_model->getUsers('ReferredByUserID', array("UserID" => $UserID));
                if (!empty($UserData['ReferredByUserID'])) {

                    /* Get Referral To Bonus Data */
                    $ReferralToBonus = $this->db->query('SELECT ConfigTypeValue,StatusID FROM set_site_config WHERE ConfigTypeGUID = "ReferToDepositBonus" LIMIT 1');
                    if ($ReferralToBonus->row()->StatusID == 2) {

                        /* Update Wallet */
                        $WalletData = array(
                            "Amount" => $ReferralToBonus->row()->ConfigTypeValue,
                            "CashBonus" => $ReferralToBonus->row()->ConfigTypeValue,
                            "TransactionType" => 'Cr',
                            "Narration" => 'Referral Bonus',
                            "EntryDate" => date("Y-m-d H:i:s")
                        );
                        $this->Users_model->addToWallet($WalletData, $UserID, 5);
                        $this->Notification_model->addNotification('ReferralBonus', 'Referred Bonus Added', $UserID, $UserID, '', 'You have received ' . DEFAULT_CURRENCY . @$ReferralToBonus->row()->ConfigTypeValue . ' Cash Bonus for Referred.');
                    }

                    /* Get Referral By Bonus Data */
                    $ReferralByBonus = $this->db->query('SELECT ConfigTypeValue,StatusID FROM set_site_config WHERE ConfigTypeGUID = "ReferByDepositBonus" LIMIT 1');
                    if ($ReferralByBonus->row()->StatusID == 2) {

                        /* Update Wallet */
                        $WalletData = array(
                            "Amount" => $ReferralByBonus->row()->ConfigTypeValue,
                            "CashBonus" => $ReferralByBonus->row()->ConfigTypeValue,
                            "TransactionType" => 'Cr',
                            "Narration" => 'Referral Bonus',
                            "EntryDate" => date("Y-m-d H:i:s")
                        );
                        $this->Users_model->addToWallet($WalletData, $UserData['ReferredByUserID'], 5);
                        $this->Notification_model->addNotification('ReferralBonus', 'Referral Bonus Added', $UserData['ReferredByUserID'], $UserData['ReferredByUserID'], '', 'You have received ' . DEFAULT_CURRENCY . @$ReferralByBonus->row()->ConfigTypeValue . ' Cash Bonus for Successful Referral.');
                    }
                }
            }

            /* MLM Referrals Wallet */
            $MLMISActive = FALSE;
            $FirstLevel = 0;
            $SecondLevel = 0;
            $ThirdLevel = 0;
            $MLMConfigType = $this->db->query('SELECT ConfigTypeGUID,ConfigTypeValue,StatusID FROM set_site_config '
                            . 'WHERE (ConfigTypeGUID = "MlmIsActive" OR ConfigTypeGUID = "MlmFirstLevel" OR ConfigTypeGUID = '
                            . '"MlmSecondLevel" OR ConfigTypeGUID = "MlmThirdLevel")')->result_array();

            if (!empty($MLMConfigType)) {
                foreach ($MLMConfigType as $ConfigValue) {
                    if ($ConfigValue['ConfigTypeGUID'] == "MlmIsActive") {
                        if ($ConfigValue['ConfigTypeValue'] == "Yes" && $ConfigValue['StatusID'] == 2) {
                            $MLMISActive = TRUE;
                        }
                    }
                    if ($ConfigValue['ConfigTypeGUID'] == "MlmFirstLevel" && $ConfigValue['StatusID'] == 2) {
                        $FirstLevel = $ConfigValue['ConfigTypeValue'];
                    }
                    if ($ConfigValue['ConfigTypeGUID'] == "MlmSecondLevel" && $ConfigValue['StatusID'] == 2) {
                        $SecondLevel = $ConfigValue['ConfigTypeValue'];
                    }
                    if ($ConfigValue['ConfigTypeGUID'] == "MlmThirdLevel" && $ConfigValue['StatusID'] == 2) {
                        $ThirdLevel = $ConfigValue['ConfigTypeValue'];
                    }
                }
            }
            if ($MLMISActive) {
                $WalletAmount = $Amount;

                if ($WalletAmount > 0) {
                    /** get first level * */
                    $LevelFirst = $this->Users_model->getUserReferralBy($UserID);

                    if (!empty($LevelFirst['Records'])) {

                        /** get 2.5% on first level * */
                        $LevelFirstRffferID = $LevelFirst['Records']['ReferredByUserID'];
                        if (!empty($LevelFirstRffferID) && $FirstLevel != 0) {
                            $FirstLevelDeposit = ($WalletAmount * $FirstLevel) / 100;
                            /** add to wallet amount * */
                            $WalletData = array(
                                "Amount" => $FirstLevelDeposit,
                                "WinningAmount" => $FirstLevelDeposit,
                                "TransactionType" => 'Cr',
                                "Narration" => 'Referral Deposit',
                                "AmountType" => "Referral",
                                "ReferralGetAmountUserID" => $UserID,
                                "EntryDate" => date("Y-m-d H:i:s")
                            );
                            $this->Users_model->addToWallet($WalletData, $LevelFirstRffferID, 5);


                            /** get second level * */
                            $LevelSecond = $this->Users_model->getUserReferralBy($LevelFirstRffferID);

                            if (!empty($LevelSecond['Records'])) {
                                $LevelSecondRffferID = $LevelSecond['Records']['ReferredByUserID'];

                                if (!empty($LevelSecondRffferID) && $SecondLevel != 0) {
                                    /** get 1.5% on first level * */
                                    $SecondLevelDeposit = ($WalletAmount * $SecondLevel) / 100;
                                    /** add to wallet amount * */
                                    $WalletData = array(
                                        "Amount" => $SecondLevelDeposit,
                                        "WinningAmount" => $SecondLevelDeposit,
                                        "TransactionType" => 'Cr',
                                        "Narration" => 'Referral Deposit',
                                        "AmountType" => "Referral",
                                        "ReferralGetAmountUserID" => $UserID,
                                        "EntryDate" => date("Y-m-d H:i:s")
                                    );
                                    $this->Users_model->addToWallet($WalletData, $LevelSecondRffferID, 5);

                                    /** get third level * */
                                    $LevelThird = $this->Users_model->getUserReferralBy($LevelSecondRffferID);
                                    if (!empty($LevelThird['Records'])) {
                                        $LevelThirdRffferID = $LevelThird['Records']['ReferredByUserID'];
                                        if (!empty($LevelThirdRffferID) && $ThirdLevel != 0) {
                                            /** get 1% on first level * */
                                            $ThirdLevelDeposit = ($WalletAmount * $ThirdLevel) / 100;
                                            /** add to wallet amount * */
                                            $WalletData = array(
                                                "Amount" => $ThirdLevelDeposit,
                                                "WinningAmount" => $ThirdLevelDeposit,
                                                "TransactionType" => 'Cr',
                                                "Narration" => 'Referral Deposit',
                                                "AmountType" => "Referral",
                                                "ReferralGetAmountUserID" => $UserID,
                                                "EntryDate" => date("Y-m-d H:i:s")
                                            );
                                            $this->Users_model->addToWallet($WalletData, $LevelThirdRffferID, 5);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            $this->db->trans_complete();
            if ($this->db->trans_status() === FALSE) {
                return FALSE;
            }
        } else {
            /* if ($payResponse['status'] === "failed") {
              $UpdataData = array_filter(
              array(
              'PaymentGatewayResponse' => @$Input,
              'ModifiedDate' => date("Y-m-d H:i:s"),
              'StatusID' => 3
              ));
              $this->db->where('WalletID', $payResponse['notes']['OrderID']);
              $this->db->where('UserID', $payResponse['notes']['UserID']);
              $this->db->where('StatusID', 1);
              $this->db->limit(1);
              $this->db->update('tbl_users_wallet', $UpdataData);
              if ($this->db->affected_rows() <= 0)
              return FALSE;
              } */
        }
    }

    function clean($string) {
        $string = str_replace(' ', '-', $string); // Replaces all spaces with hyphens.
        $string = preg_replace('/[^A-Za-z0-9\-]/', '', $string); // Removes special chars.

        return trim(preg_replace('/-+/', ' ', $string)); // Replaces multiple hyphens with single one.
    }

    function generateRandomLetters($length) {
        $random = '';
        for ($i = 0; $i < $length; $i++) {
            $random .= chr(rand(ord('a'), ord('z')));
        }
        return $random;
    }

    /*
      Description: 	cashbonus expiry api.
      URL: 			/api/utilities/allCashBonusExpire/
     */
    function allCashBonusExpire_get() {
        $ConfigData = $this->db->query("SELECT ConfigTypeGUID, ConfigTypeValue, StatusID FROM `set_site_config` WHERE ConfigTypeGUID = 'CashBonusExpireTimeInDays'");

        $ConfigDatas = $ConfigData->result_array();
        if (!empty($ConfigData)) {
            if ($ConfigDatas[0]['StatusID']) {
                if ($ConfigDatas[0]['ConfigTypeValue'] > 0 && is_numeric($ConfigDatas[0]['ConfigTypeValue'])) {
                    $prevDate = date('Y-m-d', strtotime('-' . $ConfigDatas[0]['ConfigTypeValue'] . ' day', strtotime(date('Y-m-d'))));
                    $prevDateTime = date('Y-m-d H:i:s', strtotime('-' . $ConfigDatas[0]['ConfigTypeValue'] . ' day', strtotime(date('Y-m-d H:i:s'))));
                    
                    $wallets = $this->db->query("SELECT UserID,WalletAmount,CashBonus,WinningAmount,Email,FirstName FROM `tbl_users` WHERE tbl_users.CashBonus > 0 And UserTypeID != 3 order by BonusExpireTime ASC limit 100 offset 0");
                    //$wallets = $this->db->query("SELECT UserID,WalletAmount,CashBonus,WinningAmount,Email,FirstName FROM `tbl_users` WHERE tbl_users.CashBonus > 0 And UserTypeID != 3 AND BonusExpireTime <= '".$prevDateTime."'  limit 50 offset 0");
                    $cashBonusUser = $wallets->result_array();
                    foreach ($cashBonusUser as $value) {
                        $options = $this->db->query("SELECT SUM(CashBonus) as trans_cash_bonus FROM `tbl_users_wallet` "
                                . "WHERE TransactionType = 'Cr' AND `Narration` IN "
                                . "('Signup Bonus','Admin Cash Bonus','First Deposit Bonus','Verification Bonus','Referral Bonus','Coupon Discount Bonus','Referral Winning') "
                                . "AND `EntryDate` < '" . $prevDate . "' AND UserID = '" . $value['UserID'] . "'");
                        $cashExpireUser = $options->result_array();
                        if (!empty($cashExpireUser[0]['trans_cash_bonus'])) {
                            foreach ($cashExpireUser as $cashExpire) {
                                $options = $this->db->query("SELECT SUM(CashBonus) as trans_total_cash_bonus FROM `tbl_users_wallet`"
                                        . " WHERE TransactionType = 'Cr' AND `Narration` IN "
                                        . "('Signup Bonus','Admin Cash Bonus','First Deposit Bonus','Verification Bonus','Referral Bonus','Coupon Discount Bonus','Referral Winning') AND UserID = '" . $value['UserID'] . "'");
                                $totalBonusExpireUser = $options->result_array();

                                if (!empty($totalBonusExpireUser[0]['trans_total_cash_bonus'])) {
                                    foreach ($totalBonusExpireUser as $key => $totalBonusExpire) {

                                        $expireBonus = $totalBonusExpire['trans_total_cash_bonus'] - $cashExpire['trans_cash_bonus'];

                                        if ($expireBonus >= 0) {

                                            $actaulCashBonusExpireAmount = $value['CashBonus'] - $expireBonus;

                                            if ($actaulCashBonusExpireAmount > 1 && ($value['CashBonus'] - $actaulCashBonusExpireAmount) >= 0) {

                                                if (($value['CashBonus'] - $actaulCashBonusExpireAmount) >= 0 && ($value['WalletAmount'] + $value['WinningAmount'] + $value['CashBonus'] - $actaulCashBonusExpireAmount) > 0) {

                                                    $WalletData = array(
                                                        "Amount" => $actaulCashBonusExpireAmount,
                                                        "CashBonus" => $actaulCashBonusExpireAmount,
                                                        "TransactionType" => 'Dr',
                                                        "Narration" => 'Cash Bonus Expire',
                                                        "EntryDate" => date("Y-m-d H:i:s")
                                                    );
                                                    $this->Users_model->addToWallet($WalletData, $value['UserID'], 5);

                                                    $this->db->where('UserID', $value['UserID']);
                                                    $this->db->limit(1);
                                                    $this->db->update('tbl_users', array('BonusExpireTime'=>date('Y-m-d H:i:s'),"BonusExpireStatus"=> "Yes"));

                                                    $NotificationTitle = "Cash Bonus Expire";
                                                    $NotificationMessage = "Your cash bonus $actaulCashBonusExpireAmount has been expired";

                                                    $this->Notification_model->addNotification('bonus', $NotificationTitle, $value['UserID'], $value['UserID'], '', $NotificationMessage);
                                                    /* $SendMail = send_mail(array(
                                                      'emailTo' => $value['Email'],
                                                      'template_id' => 'd-05b5dc93b6344115a15e808b7d648a3b',
                                                      'Subject' => SITE_NAME . $NotificationTitle,
                                                      "Name" => $value['FirstName'],
                                                      'Message' => "We noticed that you have a Cash Bonus balance in your FSL11 account which was credited 30 days ago. This unused Cash Bonus in your account will be forfeited on " . date('Y-m-d') . '.'
                                                      ));
                                                     */
                                                }else{
                                                    $this->db->where('UserID', $value['UserID']);
                                                    $this->db->limit(1);
                                                    $this->db->update('tbl_users', array('BonusExpireTime'=>date('Y-m-d H:i:s'),"BonusExpireStatus"=> "Yes"));
                                                }
                                            }else{
                                                $this->db->where('UserID', $value['UserID']);
                                                $this->db->limit(1);
                                                $this->db->update('tbl_users', array('BonusExpireTime'=>date('Y-m-d H:i:s'),"BonusExpireStatus"=> "Yes"));
                                           }
                                        }else{
                                            $this->db->where('UserID', $value['UserID']);
                                            $this->db->limit(1);
                                            $this->db->update('tbl_users', array('BonusExpireTime'=>date('Y-m-d H:i:s'),"BonusExpireStatus"=> "Yes"));
                                        }
                                    }
                                }else{
                                    $this->db->where('UserID', $value['UserID']);
                                    $this->db->limit(1);
                                    $this->db->update('tbl_users', array('BonusExpireTime'=>date('Y-m-d H:i:s'),"BonusExpireStatus"=> "Yes"));
                                }
                            }
                        }else{
                            $this->db->where('UserID', $value['UserID']);
                            $this->db->limit(1);
                            $this->db->update('tbl_users', array('BonusExpireTime'=>date('Y-m-d H:i:s'),"BonusExpireStatus"=> "Yes"));
                        }
                    }
                }
            }
        }
    }

    public function check_post() {
        $checkSum = "";
        $paramList = array();
        $paramList['request'] = array('requestType' => 'merchanttxnid',
            'txnType' => 'SALES_TO_USER_CREDIT',
            'txnId' => 'Order2e1d072efa',
            'mId' => PAYTM_MERCHANT_mId);
        $paramList['operationType'] = 'CHECK_TXN_STATUS';
        $paramList['platformName'] = 'PayTM';

        $data_string = json_encode($paramList);
        $checkSum = $this->Users_model->getChecksumFromString($data_string, PAYTM_MERCHANT_KEY_WITHDRAWAL);

        $ch = curl_init(); // initiate curl
        // $url = "https://trust.paytm.in/wallet-web/checkStatus";
        $url = "https://trust.paytm.in/wallet-web/txnStatusList";
        // $url = "https://trust-uat.paytm.in/wallet-web/txnStatusList";

        $headers = array('Content-Type:application/json', 'mId:' . PAYTM_MERCHANT_GUID, 'checksumhash:' . $checkSum);
        $ch = curl_init();  // initiate curl
        curl_setopt($ch, CURLOPT_URL, $url);
        //curl_setopt($ch, CURLOPT_POST, 1);  // tell curl you want to post something
        curl_setopt($ch, CURLOPT_POSTFIELDS, $data_string); // define what you want to post
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true); // return the output in string format
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, false);
        curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
        $output = curl_exec($ch); // execute
        $info = curl_getinfo($ch);

        print_r($output);
        print_r($info);
        //echo $output;
    }

    /** payment authorized and filed check and update * */
    public function paymentCaptureRazorPay_get() {

        $params = array(
            'count' => 100,
            'from' => strtotime(date('d-m-Y', strtotime('-1 day', strtotime(date('d-m-Y'))))),
            'to' => strtotime(date('d-m-Y', strtotime('+1 day', strtotime(date('d-m-Y')))))
        );
        /* Generate Data */
        $RequestData = json_encode($params);
        $HeaderValue = array('Content-Type:application/json');
        /* CURL Request */
        /* Razor pay create contact */
        $CURL = curl_init();
        curl_setopt($CURL, CURLOPT_URL, "https://api.razorpay.com/v1/payments");
        curl_setopt($CURL, CURLOPT_USERPWD, RAZORPAY_KEY_ID . ':' . RAZORPAY_KEY_SECRET);
        curl_setopt($CURL, CURLOPT_CUSTOMREQUEST, "GET");
        curl_setopt($CURL, CURLOPT_POSTFIELDS, $RequestData);
        curl_setopt($CURL, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($CURL, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($CURL, CURLOPT_SSL_VERIFYHOST, false);
        curl_setopt($CURL, CURLOPT_HTTPHEADER, $HeaderValue);
        $Info = curl_getinfo($CURL);
        $PaymentGatewayResponse = @json_decode(curl_exec($CURL), TRUE);
        if (!empty($PaymentGatewayResponse)) {
            foreach ($PaymentGatewayResponse['items'] as $Row) {
                if (strtolower($Row['status']) == "authorized") {
                    $OrderID = $Row['notes']['OrderID'];
                    $UserID = $Row['notes']['UserID'];
                    $Amount = $Row['amount'] / 100;
                    /* Get Joined Contest Users */
                    $this->db->select('W.UserID,W.WalletID,W.Amount,W.StatusID,U.WalletAmount');
                    $this->db->from('tbl_users_wallet W, tbl_users U');
                    $this->db->where("W.UserID", "U.UserID", FALSE);
                    $this->db->where("W.PaymentGateway", "Razorpay");
                    //$this->db->where("W.StatusID", 1);
                    $this->db->where("W.WalletID", $OrderID);
                    $this->db->where("W.UserID", $UserID);
                    $Query = $this->db->get();
                    if ($Query->num_rows() > 0) {
                        $PendingPayments = $Query->row_array();

                        $CURL = curl_init();
                        curl_setopt_array($CURL, array(
                            CURLOPT_URL => "https://" . RAZORPAY_KEY_ID . ":" . RAZORPAY_KEY_SECRET . "@api.razorpay.com/v1/payments/" . $Row['id'] . "/capture",
                            CURLOPT_RETURNTRANSFER => true,
                            CURLOPT_ENCODING => "",
                            CURLOPT_MAXREDIRS => 10,
                            CURLOPT_TIMEOUT => 30,
                            CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
                            CURLOPT_CUSTOMREQUEST => "POST",
                            CURLOPT_POSTFIELDS => "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"amount\"\r\n\r\n" . $Row['amount'] . "\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--",
                            CURLOPT_HTTPHEADER => array(
                                "cache-control: no-cache",
                                "content-type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW",
                            ),
                        ));
                        $Response = curl_exec($CURL);
                        $Err = curl_error($CURL);
                        curl_close($CURL);
                        if ($Err) {
                            $this->fantasydb->a_razorpay_capture_error_logs->insertOne(array(
                                'PaymentType' => "Razorpay",
                                'LogType' => "Error Logs",
                                'WallerID' => $OrderID,
                                'UserID' => $UserID,
                                'Paymentresponse' => $Row,
                                'SuccessResponse' => json_decode($Response, true),
                                'ErrorResponse' => $Err,
                                'EntryDate' => date('Y-m-d H:i:s'),
                            ));
                        } else {
                            $this->fantasydb->a_razorpay_capture_logs->insertOne(array(
                                'PaymentType' => "Razorpay",
                                'LogType' => "Success Logs",
                                'WallerID' => $OrderID,
                                'UserID' => $UserID,
                                'Paymentresponse' => $Row,
                                'SuccessResponse' => json_decode($Response, true),
                                'EntryDate' => date('Y-m-d H:i:s'),
                            ));

                            /* Update user wallet */
                            $this->db->where('UserID', $UserID);
                            $this->db->limit(1);
                            $this->db->update('tbl_users', array('WalletAmount' => $Amount + $PendingPayments['WalletAmount']));

                            /* Update wallet history */
                            $this->db->where('WalletID', $OrderID);
                            $this->db->where('UserID', $UserID);
                            $this->db->limit(1);
                            $this->db->update('tbl_users_wallet', array('StatusID' => 5, 'PaymentGatewayResponse' => json_encode($Row)));
                        }
                    }
                } else if (strtolower($Row['status']) == "failed") {
                    $OrderID = $Row['notes']['OrderID'];
                    $UserID = $Row['notes']['UserID'];
                    /* Get Joined Contest Users */
                    $this->db->select('W.UserID,W.WalletID,W.Amount,W.StatusID');
                    $this->db->from('tbl_users_wallet W, tbl_users U');
                    $this->db->where("W.UserID", "U.UserID", FALSE);
                    $this->db->where("W.PaymentGateway", "Razorpay");
                    $this->db->where("W.StatusID", 1);
                    $this->db->where("W.WalletID", $OrderID);
                    $this->db->where("W.UserID", $UserID);
                    $Query = $this->db->get();
                    if ($Query->num_rows() > 0) {
                        /* Update wallet history */
                        $this->db->where('WalletID', $OrderID);
                        $this->db->where('UserID', $UserID);
                        $this->db->limit(1);
                        $this->db->update('tbl_users_wallet', array('StatusID' => 3, 'PaymentGatewayResponse' => json_encode($Row)));
                    }
                }
            }
        }

        /** get pending wallet history * */
        $this->db->select('W.UserID,W.WalletID,W.Amount,W.StatusID,W.EntryDate');
        $this->db->from('tbl_users_wallet W, tbl_users U');
        $this->db->where("W.UserID", "U.UserID", FALSE);
        $this->db->where("W.PaymentGateway", "Razorpay");
        $this->db->where("W.Narration", "Deposit Money");
        $this->db->where("DATE(W.EntryDate) <=", date('Y-m-d', strtotime('-1 day', strtotime(date('d-m-Y')))));
        $this->db->where("W.StatusID", 1);
        $Query = $this->db->get();
        if ($Query->num_rows() > 0) {
            $PendingPayments = $Query->result_array();
            foreach ($PendingPayments as $Row) {
                /* Update wallet history */
                $this->db->where('WalletID', $Row['WalletID']);
                $this->db->where('UserID', $Row['UserID']);
                $this->db->limit(1);
                $this->db->update('tbl_users_wallet', array('StatusID' => 3, 'PaymentGatewayResponse' => "This transaction cancelled by api because payment process not completed"));
            }
        }
    }

    public function getContestsExportsWithWinning_get() {
        require_once APPPATH . 'third_party/phpexcel/PHPExcel.php';
        $this->excel = new PHPExcel();
        $Params = "MatchLocation,MatchStartDateTime,TeamNameLocal,TeamNameVisitor,MatchType,MatchNo,IsConfirm,GameType,AdminPercent,IsPaid,WinningAmount,ContestSize,EntryFee,NoOfWinners,EntryType,Status,CustomizeWinning,ContestType";
        $ContestData = $this->Contest_model->getContestsExports($Params, array('FromDate' => date('Y-m-d', strtotime($_GET['FromDate'])), 'ToDate' => date('Y-m-d', strtotime($_GET['ToDate'])), 'StatusID' => 5), TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']);
        if ($ContestData['Data']['TotalRecords'] > 0) {
            $styleArray = array(
                'font' => array(
                    'bold' => true,
                    'color' => array('rgb' => '037ffc'),
                    'size' => 10,
                    'name' => 'Verdana'
            ));
            $styleArray1 = array(
                'font' => array(
                    'bold' => true,
                    'color' => array('rgb' => 'fc3903'),
                    'size' => 10,
                    'name' => 'Verdana'
            ));
            foreach ($ContestData['Data']['Records'] as $Key => $Contest) {
                $sheet = $this->excel->getActiveSheet();
                $objWorkSheet = $this->excel->createSheet($Key); //Setting index when creating
                $objWorkSheet->setTitle('Fee-' . $Contest['EntryFee'] . ' ' . $Contest['ContestName']);
                $objWorkSheet->setCellValue('A1', $Contest['TeamNameLocal'] . ' Vs ' . $Contest['TeamNameVisitor'] . ' ' . $Contest['MatchType'] . ' - ' . $Contest['MatchNo'] . ' Date -' . date('d-m-Y H:i:s A', strtotime($Contest['MatchStartDateTime'])) . '- Location -' . $Contest['MatchLocation']);
                $objWorkSheet->setCellValue('A2', $Contest['ContestName'] . '- Size -' . $Contest['ContestSize'] . '- EntryFee -' . $Contest['EntryFee'] . '- WinningAmount -' . $Contest['WinningAmount']);
                $objWorkSheet->getStyle('A1')->applyFromArray($styleArray);
                $objWorkSheet->getStyle('A2')->applyFromArray($styleArray1);

                $objWorkSheet->SetCellValue('A4', 'Username');
                $objWorkSheet->SetCellValue('B4', 'Team');
                $objWorkSheet->SetCellValue('C4', 'TotalPoints');
                $objWorkSheet->SetCellValue('D4', 'UserRank');
                $objWorkSheet->SetCellValue('E4', 'Winning');
                $objWorkSheet->getStyle('A4')->getFont()->setBold(true);
                $objWorkSheet->getStyle('B4')->getFont()->setBold(true);
                $objWorkSheet->getStyle('C4')->getFont()->setBold(true);
                $objWorkSheet->getStyle('D4')->getFont()->setBold(true);
                $objWorkSheet->getStyle('E4')->getFont()->setBold(true);
                $objWorkSheet->getColumnDimension('A')->setWidth(20);

                $Field = "Username,UserID,UserTeamName,TotalPoints,UserRank,UserWinningAmount";
                $JoinedContestData = $this->Contest_model->getContestsExportsWithWinning($Field, array('ContestID' => $Contest['ContestID']));
                $RowCount = 4;
                foreach ($JoinedContestData['Records'] as $Value) {
                    $objWorkSheet->SetCellValue('A' . $RowCount, $Value['Username']);
                    $objWorkSheet->SetCellValue('B' . $RowCount, $Value['UserTeamName']);
                    $objWorkSheet->SetCellValue('C' . $RowCount, $Value['TotalPoints']);
                    $objWorkSheet->SetCellValue('D' . $RowCount, $Value['UserRank']);
                    $objWorkSheet->SetCellValue('E' . $RowCount, $Value['UserWinningAmount']);
                    $RowCount++;
                }
            }
            $filename = 'ContestWinning.xls'; //save our workbook as this file name
            header('Content-Type: application/vnd.ms-excel'); //mime type
            header('Content-Disposition: attachment;filename="' . $filename . '"'); //tell browser what's the file name
            header('Cache-Control: max-age=0'); //no cache
            $objWriter = PHPExcel_IOFactory::createWriter($this->excel, 'Excel5');
            $objWriter->save('php://output');
        }
    }

    public function getSubscription_get(){
      $this->load->model('Subscription_model');
	    $Data = $this->Subscription_model->getSubscriptions('',array_filter(array("ID"=>@$this->Post['ID'])),TRUE,@$this->Post['PageNo'], @$this->Post['PageSize']);
  		if($Data){
  			$this->Return['ResponseCode'] 			=	200;
  			$this->Return['Message']      			=	"Success";
  			$this->Return['Data']      	=	$Data;

  		}else{
  			$this->Return['ResponseCode'] 			=	500;
  			$this->Return['Message']      			=	"Someting went wrong try again later";
  		}
	  }

}
