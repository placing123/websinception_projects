<?php

defined('BASEPATH') OR exit('No direct script access allowed');
error_reporting(1);
class Offers extends API_Controller_Secure {

    function __construct() {
        parent::__construct();
        $this->load->model('Offers_model');
    }


     /*
      Name: 			add
      Description: 	Use to add offers to system.
      URL: 			/api_admin/offers/add/
     */

    public function add_post() {
        /* Validation section */
        $this->form_validation->set_rules('OfferType', 'Offer Type', 'trim|required|in_list[Offer-1,Offer-2]');
        $this->form_validation->set_rules('OfferName', 'Offer Name', 'trim|required');
        $this->form_validation->set_rules('OfferPercent', 'Offer Percent', 'trim|required');
        $this->form_validation->set_rules('MatchGUID', 'MatchGUID', 'trim|required|callback_validateEntityGUID[Matches,MatchID]');
        $this->form_validation->set_rules('ContestGUID[]', 'ContestGUID', 'trim|required|callback_validateEntityGUID[Contest,ContestID]');
        $this->form_validation->set_rules('OfferDateTime', 'Offer DateTime', 'trim'.($this->Post['OfferType'] == 'Offer-1' ? '|required':''));
        $this->form_validation->set_rules('NoOfTeams', 'No Of Teams', 'trim|required');
        $this->form_validation->set_rules('UserSelectionType', 'UserSelectionType', 'trim|required|in_list[AllUsers,SelectedUsers,RandomUsers]');
        $this->form_validation->set_rules('NoOfRandomUsers', 'NoOfRandomUsers',  'trim'.($this->Post['UserSelectionType'] == 'RandomUsers' ? '|required':''));
        $this->form_validation->set_rules('UserGUID[]', 'Users', 'trim'.($this->Post['UserSelectionType'] == 'SelectedUsers' ? '|required|callback_validateEntityGUID[User,UserID]':''));

        $this->form_validation->validation($this);  /* Run validation */
        /* Validation - ends */
        $ContestIDs = array();
        $UserIDs = array();
        /* Get all contest ids from GUIDS */
        $AllContests = count($this->input->post('ContestGUID'));
        for ($i = 0; $i < $AllContests; $i++) {
            $this->Post('ContestGUID')[$i];
            $ContestID = $this->Entity_model->getEntity('E.EntityID', array('EntityGUID' => $this->Post('ContestGUID')[$i], 'EntityTypeName' => "Contest"));
            $ContestIDs[] = $ContestID['EntityID'];
        }
         /* Get Selected Users ids from GUIDS */
        if(!empty($this->Post['UserSelectionType']) && $this->Post['UserSelectionType'] == 'SelectedUsers' && !empty($this->Post['UserGUID']))
        {
            $AllUsers = count($this->input->post('UserGUID'));
            for ($i = 0; $i < $AllUsers; $i++) {
                $this->Post('UserGUID')[$i];
                $UserID = $this->Entity_model->getEntity('E.EntityID', array('EntityGUID' => $this->Post('UserGUID')[$i], 'EntityTypeName' => "User"));
                $UserIDs[] = $UserID['EntityID'];
            }
        } 
        /* Get all random user ids from database */
        else if(!empty($this->Post['UserSelectionType']) && $this->Post['UserSelectionType'] == 'RandomUsers')
        {
            $Query = $this->db->query('SELECT UserID FROM tbl_users ORDER BY RAND() LIMIT '.$this->Post['NoOfRandomUsers'].'');
            if($Query->num_rows() > 0)
            {
                foreach($Query->result_array() as $Value) {
                    $UserIDs[] = $Value['UserID'];
                }
            }
        }
        /* Insert offers data into database */
        $Insert = $this->Offers_model->addOffers($this->Post, $this->SessionUserID, $this->MatchID, $ContestIDs, $UserIDs);

        if (!$Insert) {
            $this->Return['ResponseCode'] = 500;
            $this->Return['Message'] = "An error occurred, please try again later.";
        } else {
            /**-- Send Mail & Notification After Creation Offer--**/ 
            if (!empty($ContestIDs) && $this->Post['UserSelectionType']=='SelectedUsers' || $this->Post['UserSelectionType']=='RandomUsers') {
                foreach ($ContestIDs as $key => $ContestVal) {
                    $this->load->model('Contest_model');
                    $ContestData = $this->Contest_model->getContests("SeriesName,TeamNameShortLocal,TeamNameShortVisitor,MatchStartDateTime,EntryFee", array('ContestID' => $ContestVal), FALSE, '', '');
                    foreach ($UserIDs as $key => $userVal) {
                        /*Push Notification*/ 
                        $Title = "Congratulations, You've Got a Discounted Entry";
                        $MSG =nl2br("Congratulations, you have got a discounted entry \n",false);
                        $MSG .=nl2br("Series Name- ".$ContestData['SeriesName']." \n",false);
                        $MSG .=nl2br("Match- ".$ContestData['TeamNameShortLocal']." vs ".$ContestData['TeamNameShortVisitor']."\n",false);
                        $MSG .=nl2br("Match Start Time- ".$ContestData['MatchStartDateTime']."\n",false);
                        $MSG .=nl2br("Contest Name- ".$ContestData['ContestName']."\n",false);
                        $MSG .="Entry Fee- ".$ContestData['EntryFee']." rs. Discounted Only For You";
                        $Message = str_replace("<br>"," ",$MSG);
                        sendPushMessage($userVal, $Title, $Message,'','Dfs');

                        /**-- Send Mail --**/
                        $UserData = $this->db->query('SELECT Email,FirstName,PhoneNumber FROM tbl_users WHERE UserID='.$userVal)->row_array();
                        send_mail(array(
                            'emailTo' => $UserData['Email'],
                            'template_id' => OFFER_NOTIFICATION,
                            'Subject' => "Congratulations, You've Got a Discounted Entry",
                            'Name'          => $UserData['FirstName'],
                            'Message'       => "Congratulations, you have got a discounted entry",
                            'SeriesName'    => $ContestData['SeriesName'],
                            'MatchTeams'    => $ContestData['TeamNameShortLocal']." vs ".$ContestData['TeamNameShortVisitor'],
                            'MatchStartDateTime'=>$ContestData['MatchStartDateTime'],
                            'ContestName'       =>$ContestData['ContestName'],
                            'EntryFee'          =>$ContestData['EntryFee']
                        ));

                        /**-- Send Message --**/ 
                        // $this->db->query('SELECT UserID FROM tbl_users WHERE UserID ='.$userVal)->row();
                        // $this->load->model('Utility_model');
                        // $this->Utility_model->sendBulkSMS(array(
                        //             'PhoneNumber' => 9131344742,
                        //             'Text' => 'KAMLESH' . " - sendBulkSMS2 - " . 'TESTING'
                        //         ));
                    }
                }
            }
            $this->Return['Message'] = "Offer created successfully.";
        }
    }
}

?>