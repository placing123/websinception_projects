<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Contest extends API_Controller_Secure {

    function __construct() {
        parent::__construct();
        $this->load->model('Contest_model');
        $this->load->model('Sports_model');
    }

    /*
      Name: 			add
      Description: 	Use to add contest to system.
      URL: 			/api_admin/contest/add/
     */

    public function add_post() {
        /* Validation section */
        $this->form_validation->set_rules('ContestName', 'ContestName', 'trim');
        $this->form_validation->set_rules('ContestFormat', 'Contest Format', 'trim|required|in_list[Head to Head,League]');
        $this->form_validation->set_rules('ContestType', 'Contest Type', 'trim|required|in_list[Hot,Champion,Practice,More,Mega,Smart Pool,Infinity Pool,Winner Takes All,Only For Beginners,Head to Head]');
        $this->form_validation->set_rules('Privacy', 'Privacy', 'trim|required|in_list[Yes,No]');
        // $this->form_validation->set_rules('UserInvitationCode', 'UserInvitationCode', 'trim' .($this->Post['Privacy'] == 'Yes' ? '|required|callback_validateUniqueCode':''));
        $this->form_validation->set_rules('IsPaid', 'IsPaid', 'trim|required|in_list[Yes,No]');
        $this->form_validation->set_rules('ShowJoinedContest', 'ShowJoinedContest', 'trim|required|in_list[Yes,No]');
        $this->form_validation->set_rules('UnfilledWinningPercent', 'UnfilledWinningPercent', 'trim|in_list[GuranteedPool,Fixed]');
        $this->form_validation->set_rules('SmartPoolText', 'SmartPoolText', 'trim'.($this->Post['SmartPool'] == 'Yes' ? '|required':''));
        $this->form_validation->set_rules('WinningRatio', 'Winning Percent', 'trim'.($this->Post['UnfilledWinningPercent'] == 'GuranteedPool' ? '|required':''));
        $this->form_validation->set_rules('WinUpTo', 'Win Up To', 'trim'.($this->Post['UnfilledWinningPercent'] == 'GuranteedPool' ? '|required':''));
        $this->form_validation->set_rules('WinningAmount', 'WinningAmount', 'trim' . (!empty($this->Post['UnfilledWinningPercent']) && $this->Post['UnfilledWinningPercent'] == 'Fixed' && $this->Post['SmartPool'] == 'No' ? '|required|integer' : ''));
        $this->form_validation->set_rules('ContestSize', 'ContestSize', 'trim' . (!empty($this->Post['ContestFormat']) && $this->Post['ContestFormat'] == 'League' && $this->Post['UnfilledWinningPercent'] == 'Fixed' ? '|required|integer' : ''));
        $this->form_validation->set_rules('EntryFee', 'EntryFee', 'trim' . (!empty($this->Post['IsPaid']) && $this->Post['IsPaid'] == 'Yes' ? '|required|numeric' : ''));
        $this->form_validation->set_rules('NoOfWinners', 'NoOfWinners', 'trim' . ((!empty($this->Post['IsPaid']) && $this->Post['IsPaid'] == 'Yes') && $this->Post['UnfilledWinningPercent'] == 'Fixed' ? '|required|integer' : ''));
        $this->form_validation->set_rules('EntryType', 'EntryType', 'trim|required|in_list[Single,Multiple]');
        $this->form_validation->set_rules('UserJoinLimit', 'UserJoinLimit', 'trim' . (!empty($this->Post['EntryType']) && $this->Post['EntryType'] == 'Multiple' ? '|required|integer' : ''));
        $this->form_validation->set_rules('CashBonusContribution', 'CashBonusContribution', 'trim' . (!empty($this->Post['IsPaid']) && $this->Post['IsPaid'] == 'Yes' ? '|required|numeric|regex_match[/^[0-9][0-9]?$|^100$/]' : ''));
        $this->form_validation->set_rules('SeriesGUID', 'SeriesGUID', 'trim|required|callback_validateEntityGUID[Series,SeriesID]');
        $this->form_validation->set_rules('CustomizeWinning', 'Customize Winning', 'trim');
        $this->form_validation->set_rules('MatchGUID[]', 'MatchGUID', 'trim|required|callback_validateEntityGUID[Matches,MatchID]');
        $this->form_validation->set_rules('IsVirtualUserJoined', 'Is Virtual User Joined', 'trim');
        $this->form_validation->set_rules('VirtualUserJoinedPercentage', 'Virtual User Joined Percentage', 'trim' . (!empty($this->Post['IsVirtualUserJoined']) && $this->Post['IsVirtualUserJoined'] == 'Yes' ? '|required|integer' : ''));

        if (!empty($this->Post['CustomizeWinning']) && is_array($this->Post['CustomizeWinning']) && $this->Post['SmartPool'] == 'No') {
            $TotalWinners = $TotalPercent = $TotalWinningAmount = 0;
            foreach ($this->Post['CustomizeWinning'] as $Key => $Value) {
                $this->form_validation->set_rules('CustomizeWinning[' . $Key . '][From]', 'From', 'trim|required|integer');
                $this->form_validation->set_rules('CustomizeWinning[' . $Key . '][To]', 'To', 'trim|required|integer');
                $this->form_validation->set_rules('CustomizeWinning[' . $Key . '][Percent]', 'Percent', 'trim|required|numeric');
                $this->form_validation->set_rules('CustomizeWinning[' . $Key . '][WinningAmount]', 'WinningAmount', 'trim|required|numeric');
                $TotalWinners += ($Value['To'] - $Value['From']) + 1;
                $TotalPercent += $Value['Percent'];
                $TotalWinningAmount += $TotalWinners * $Value['WinningAmount'];
            }

            /* Check Total No Of Winners */
            if ($TotalWinners != $this->Post['NoOfWinners']) {
                $this->Return['ResponseCode'] = 500;
                $this->Return['Message'] = "Customize Winners should be equals to No Of Winners.";
                exit;
            }

            /* Check Total Percent */
            if ($TotalPercent < 100 || $TotalPercent > 100) {
                $this->Return['ResponseCode'] = 500;
                $this->Return['Message'] = "Customize Winners Percent should be 100%.";
                exit;
            }

            /* Check Total Winning Amount */
            if ($TotalWinningAmount != $this->Post['WinningAmount']) {
                $this->Return['ResponseCode'] = 500;
                $this->Return['Message'] = "Customize Winning Amount should be equals to Winning Amount";
                exit;
            }
        }
        $this->form_validation->set_message('regex_match', '{field} value should be between 0 to 100.');
        $this->form_validation->validation($this);  /* Run validation */
        /* Validation - ends */

        
        if($this->Post['SmartPool'] == 'Yes'){
            $this->Post['ContestContribution'] = 0;
        }else{
            if($this->Post['ContestFormat'] == "Head to Head"){
                $total = $this->Post['WinningAmount']/2;
                $Contribution = $total*$this->Post['AdminPercent']/100;
                // print_r($Contribution);exit;
                $this->Post['ContestContribution'] = round($Contribution,2);
            }
            else if($this->Post['UnfilledWinningPercent'] == 'GuranteedPool'){
                $Contribution = $this->Post['EntryFee']*$this->Post['AdminPercent']/100;
                $this->Post['ContestContribution'] = round($Contribution,2);
            }
            else if($this->Post['ContestFormat'] == "League"){
                $total = $this->Post['WinningAmount']/$this->Post['ContestSize'];
                $Contribution = $total*$this->Post['AdminPercent']/100;
                // print_r($Contribution);exit;
                $this->Post['ContestContribution'] = round($Contribution,2);
            }
        }

        $all_matches = count($this->input->post('MatchGUID'));
        for ($i = 0; $i < $all_matches; $i++) {
            $this->Post('MatchGUID')[$i];
            $MatchIDs = $this->Entity_model->getEntity('E.EntityID', array('EntityGUID' => $this->Post('MatchGUID')[$i], 'EntityTypeName' => "Matches"));
            $MatchID = $MatchIDs['EntityID'];
            $insert = $this->Contest_model->addContest($this->Post, $this->SessionUserID, $MatchID, $this->SeriesID);
        }

        if (!$insert) {
            $this->Return['ResponseCode'] = 500;
            $this->Return['Message'] = "An error occurred, please try again later.";
        } else {
            $this->Return['Message'] = "Contest created successfully.";
        }
    }

    /*
      Name: 			edit
      Description: 	Use to update contest to system.
      URL: 			/api_admin/contest/edit/
     */

    public function edit_post() {
        /* Validation section */
        $this->form_validation->set_rules('ContestGUID', 'ContestGUID', 'trim|required|callback_validateEntityGUID[Contest,ContestID]|callback_validateAnyUserJoinedContest[update]');
        $this->form_validation->set_rules('ContestName', 'ContestName', 'trim|required');
        $this->form_validation->set_rules('ContestFormat', 'Contest Format', 'trim|required|in_list[Head to Head,League]');
        $this->form_validation->set_rules('ContestType', 'Contest Type', 'trim|required|in_list[Hot,Champion,Practice,More,Mega,Smart Pool,Infinity Pool,Winner Takes All,Only For Beginners,Head to Head]');
        $this->form_validation->set_rules('Privacy', 'Privacy', 'trim|required|in_list[Yes,No]');
        $this->form_validation->set_rules('UserInvitationCode', 'UserInvitationCode', 'trim' .($this->Post['Privacy'] == 'Yes' ? '|required|callback_validateUniqueCode' : ''));
        $this->form_validation->set_rules('IsPaid', 'IsPaid', 'trim|required|in_list[Yes,No]');
        $this->form_validation->set_rules('UnfilledWinningPercent', 'UnfilledWinningPercent', 'trim|in_list[GuranteedPool,Fixed]');
        $this->form_validation->set_rules('WinningRatio', 'Winning Percent', 'trim'.($this->Post['UnfilledWinningPercent'] == 'GuranteedPool' ? '|required':''));
        $this->form_validation->set_rules('WinUpTo', 'Win Up To', 'trim'.($this->Post['UnfilledWinningPercent'] == 'GuranteedPool' ? '|required':''));
        $this->form_validation->set_rules('ShowJoinedContest', 'ShowJoinedContest', 'trim|required|in_list[Yes,No]');
        $this->form_validation->set_rules('WinningAmount', 'WinningAmount', 'trim' . (!empty($this->Post['UnfilledWinningPercent']) && $this->Post['UnfilledWinningPercent'] == 'Fixed' && $this->Post['SmartPool'] == 'No' ? '|required|integer' : ''));
        $this->form_validation->set_rules('ContestSize', 'ContestSize', 'trim' . (!empty($this->Post['ContestFormat']) && $this->Post['ContestFormat'] == 'League' && $this->Post['UnfilledWinningPercent'] == 'Fixed' ? '|required|integer' : ''));
        $this->form_validation->set_rules('EntryFee', 'EntryFee', 'trim' . (!empty($this->Post['IsPaid']) && $this->Post['IsPaid'] == 'Yes' ? '|required|numeric' : ''));
        $this->form_validation->set_rules('NoOfWinners', 'NoOfWinners', 'trim' . ((!empty($this->Post['IsPaid']) && $this->Post['IsPaid'] == 'Yes') && $this->Post['UnfilledWinningPercent'] == 'Fixed' ? '|required|integer' : ''));
        $this->form_validation->set_rules('EntryType', 'EntryType', 'trim|required|in_list[Single,Multiple]');
        $this->form_validation->set_rules('UserJoinLimit', 'UserJoinLimit', 'trim' . (!empty($this->Post['EntryType']) && $this->Post['EntryType'] == 'Multiple' ? '|required|integer' : ''));
        $this->form_validation->set_rules('CashBonusContribution', 'CashBonusContribution', 'trim' . (!empty($this->Post['IsPaid']) && $this->Post['IsPaid'] == 'Yes' ? '|required|numeric|regex_match[/^[0-9][0-9]?$|^100$/]' : ''));
        $this->form_validation->set_rules('CustomizeWinning', 'Customize Winning', 'trim');
        $this->form_validation->set_rules('IsVirtualUserJoined', 'Is Virtual User Joined', 'trim');
        $this->form_validation->set_rules('VirtualUserJoinedPercentage', 'Virtual User Joined Percentage', 'trim' . (!empty($this->Post['IsVirtualUserJoined']) && $this->Post['IsVirtualUserJoined'] == 'Yes' ? '|required|integer' : ''));
        if (!empty($this->Post['CustomizeWinning']) && is_array($this->Post['CustomizeWinning']) && $this->Post['SmartPool'] == 'No') {
            $TotalWinners = $TotalPercent = $TotalWinningAmount = 0;
            foreach ($this->Post['CustomizeWinning'] as $Key => $Value) {
                $this->form_validation->set_rules('CustomizeWinning[' . $Key . '][From]', 'From', 'trim|required|integer');
                $this->form_validation->set_rules('CustomizeWinning[' . $Key . '][To]', 'To', 'trim|required|integer');
                $this->form_validation->set_rules('CustomizeWinning[' . $Key . '][Percent]', 'Percent', 'trim|required|numeric');
                $this->form_validation->set_rules('CustomizeWinning[' . $Key . '][WinningAmount]', 'WinningAmount', 'trim|required|numeric');
                $TotalWinners += ($Value['To'] - $Value['From']) + 1;
                $TotalPercent += $Value['Percent'];
                $TotalWinningAmount += $TotalWinners * $Value['WinningAmount'];
            }

            /* Check Total No Of Winners */
            if ($TotalWinners != $this->Post['NoOfWinners']) {
                $this->Return['ResponseCode'] = 500;
                $this->Return['Message'] = "Customize Winners should be equals to No Of Winners.";
                exit;
            }

            /* Check Total Percent */
            if ($TotalPercent < 100 || $TotalPercent > 100) {
                $this->Return['ResponseCode'] = 500;
                $this->Return['Message'] = "Customize Winners Percent should be 100%.";
                exit;
            }

            /* Check Total Winning Amount */
            if ($TotalWinningAmount != $this->Post['WinningAmount']) {
                $this->Return['ResponseCode'] = 500;
                $this->Return['Message'] = "Customize Winning Amount should be equals to Winning Amount";
                exit;
            }
        }
        $this->form_validation->set_message('regex_match', '{field} value should be between 0 to 100.');
        $this->form_validation->validation($this);  /* Run validation */
        /* Validation - ends */



        if($this->Post['SmartPool'] == 'Yes'){
            $this->Post['ContestContribution'] = 0;
        }else{
            if($this->Post['ContestFormat'] == "Head to Head"){
                $total = $this->Post['WinningAmount']/2;
                $Contribution = $total*$this->Post['AdminPercent']/100;
                // print_r($Contribution);exit;
                $this->Post['ContestContribution'] = round($Contribution,2);
            }else if($this->Post['UnfilledWinningPercent'] == 'GuranteedPool'){
                $Contribution = $this->Post['EntryFee']*$this->Post['AdminPercent']/100;
                $this->Post['ContestContribution'] = round($Contribution,2);
            }
            else if($this->Post['ContestFormat'] == "League"){
                $total = $this->Post['WinningAmount']/$this->Post['ContestSize'];
                $Contribution = $total*$this->Post['AdminPercent']/100;
                // print_r($Contribution);exit;
                $this->Post['ContestContribution'] = round($Contribution,2);
            }
        }

        $this->Contest_model->updateContest($this->Post, $this->SessionUserID, $this->ContestID);
        $this->Return['Message'] = "Contest updated successfully.";
    }


        /*
      Name:             add
      Description:  Use to add contest to system.
      URL:          /api_admin/contest/add/
     */

    public function addVirtual_post() {
        //print_r($this->Post['CustomizeWinning']);exit;
        /* Validation section */
        $this->form_validation->set_rules('ContestName', 'ContestName', 'trim');
        $this->form_validation->set_rules('ContestFormat', 'Contest Format', 'trim|required|in_list[Head to Head,League]');
        $this->form_validation->set_rules('ContestType', 'Contest Type', 'trim|required|in_list[Hot,Champion,Practice,More,Mega,Smart Pool,Infinity Pool,Winner Takes All,Only For Beginners,Head to Head]');
        $this->form_validation->set_rules('Privacy', 'Privacy', 'trim|required|in_list[Yes,No]');
        $this->form_validation->set_rules('IsPaid', 'IsPaid', 'trim|required|in_list[Yes,No]');
        $this->form_validation->set_rules('ShowJoinedContest', 'ShowJoinedContest', 'trim|required|in_list[Yes,No]');
        $this->form_validation->set_rules('UnfilledWinningPercent', 'UnfilledWinningPercent', 'trim|in_list[GuranteedPool,Fixed]');
        $this->form_validation->set_rules('WinningRatio', 'Winning Percent', 'trim'.($this->Post['UnfilledWinningPercent'] == 'GuranteedPool' ? '|required':''));
        $this->form_validation->set_rules('WinUpTo', 'Win Up To', 'trim'.($this->Post['UnfilledWinningPercent'] == 'GuranteedPool' ? '|required':''));
        $this->form_validation->set_rules('WinningAmount', 'WinningAmount', 'trim' . (!empty($this->Post['UnfilledWinningPercent']) && $this->Post['UnfilledWinningPercent'] == 'Fixed' && $this->Post['SmartPool'] == 'No' ? '|required|integer' : ''));
        $this->form_validation->set_rules('ContestSize', 'ContestSize', 'trim' . (!empty($this->Post['ContestFormat']) && $this->Post['ContestFormat'] == 'League' && $this->Post['UnfilledWinningPercent'] == 'Fixed' ? '|required|integer' : ''));
        $this->form_validation->set_rules('EntryFee', 'EntryFee', 'trim' . (!empty($this->Post['IsPaid']) && $this->Post['IsPaid'] == 'Yes' ? '|required|numeric' : ''));
        $this->form_validation->set_rules('NoOfWinners', 'NoOfWinners', 'trim' . ((!empty($this->Post['IsPaid']) && $this->Post['IsPaid'] == 'Yes') && $this->Post['UnfilledWinningPercent'] == 'Fixed' ? '|required|integer' : ''));
        $this->form_validation->set_rules('EntryType', 'EntryType', 'trim|required|in_list[Single,Multiple]');
        $this->form_validation->set_rules('UserJoinLimit', 'UserJoinLimit', 'trim' . (!empty($this->Post['EntryType']) && $this->Post['EntryType'] == 'Multiple' ? '|required|integer' : ''));
        $this->form_validation->set_rules('CashBonusContribution', 'CashBonusContribution', 'trim' . (!empty($this->Post['IsPaid']) && $this->Post['IsPaid'] == 'Yes' ? '|required|numeric|regex_match[/^[0-9][0-9]?$|^100$/]' : ''));
        $this->form_validation->set_rules('SeriesGUID', 'SeriesGUID', 'trim|required|callback_validateEntityGUID[Series,SeriesID]');
        $this->form_validation->set_rules('CustomizeWinning', 'Customize Winning', 'trim');
        $this->form_validation->set_rules('MatchGUID[]', 'MatchGUID', 'trim|required|callback_validateEntityGUID[Matches,MatchID]');
        $this->form_validation->set_rules('IsVirtualUserJoined', 'Is Virtual User Joined', 'trim');
        $this->form_validation->set_rules('VirtualUserJoinedPercentage', 'Virtual User Joined Percentage', 'trim' . (!empty($this->Post['IsVirtualUserJoined']) && $this->Post['IsVirtualUserJoined'] == 'Yes' ? '|required|integer' : ''));

        if (!empty($this->Post['CustomizeWinning']) && is_array($this->Post['CustomizeWinning']) && $this->Post['SmartPool'] == 'No') {
            $TotalWinners = $TotalPercent = $TotalWinningAmount = 0;
            foreach ($this->Post['CustomizeWinning'] as $Key => $Value) {
                $this->form_validation->set_rules('CustomizeWinning[' . $Key . '][From]', 'From', 'trim|required|integer');
                $this->form_validation->set_rules('CustomizeWinning[' . $Key . '][To]', 'To', 'trim|required|integer');
                $this->form_validation->set_rules('CustomizeWinning[' . $Key . '][Percent]', 'Percent', 'trim|required|numeric');
                $this->form_validation->set_rules('CustomizeWinning[' . $Key . '][WinningAmount]', 'WinningAmount', 'trim|required|numeric');
                $TotalWinners += ($Value['To'] - $Value['From']) + 1;
                $TotalPercent += $Value['Percent'];
                $TotalWinningAmount += $TotalWinners * $Value['WinningAmount'];
            }

            /* Check Total No Of Winners */
            if ($TotalWinners != $this->Post['NoOfWinners']) {
                $this->Return['ResponseCode'] = 500;
                $this->Return['Message'] = "Customize Winners should be equals to No Of Winners.";
                exit;
            }

            /* Check Total Percent */
            if ($TotalPercent < 100 || $TotalPercent > 100) {
                $this->Return['ResponseCode'] = 500;
                $this->Return['Message'] = "Customize Winners Percent should be 100%.";
                exit;
            }

            /* Check Total Winning Amount */
            if ($TotalWinningAmount != $this->Post['WinningAmount']) {
                $this->Return['ResponseCode'] = 500;
                $this->Return['Message'] = "Customize Winning Amount should be equals to Winning Amount";
                exit;
            }
        }
        $this->form_validation->set_message('regex_match', '{field} value should be between 0 to 100.');
        $this->form_validation->validation($this);  /* Run validation */
        /* Validation - ends */

        $all_matches = count($this->input->post('MatchGUID'));
        for ($i = 0; $i < $all_matches; $i++) {
            $this->Post('MatchGUID')[$i];
            $MatchIDs = $this->Entity_model->getEntity('E.EntityID', array('EntityGUID' => $this->Post('MatchGUID')[$i], 'EntityTypeName' => "Matches"));
            $MatchID = $MatchIDs['EntityID'];
            $this->Post['EntryFee'] = 0;
            $insert = $this->Contest_model->addContest($this->Post, $this->SessionUserID, $MatchID, $this->SeriesID);
        }

        if (!$insert) {
            $this->Return['ResponseCode'] = 500;
            $this->Return['Message'] = "An error occurred, please try again later.";
        } else {
            $this->Return['Message'] = "Contest created successfully.";
        }
    }

    /*
      Name:             edit
      Description:  Use to update contest to system.
      URL:          /api_admin/contest/edit/
     */

    public function editVirtual_post() {
        /* Validation section */
        $this->form_validation->set_rules('ContestGUID', 'ContestGUID', 'trim|required|callback_validateEntityGUID[Contest,ContestID]|callback_validateAnyUserJoinedContest[update]');
        $this->form_validation->set_rules('ContestName', 'ContestName', 'trim|required');
        $this->form_validation->set_rules('ContestFormat', 'Contest Format', 'trim|required|in_list[Head to Head,League]');
        $this->form_validation->set_rules('ContestType', 'Contest Type', 'trim|required|in_list[Hot,Champion,Practice,More,Mega,Smart Pool,Infinity Pool,Winner Takes All,Only For Beginners,Head to Head]');
        $this->form_validation->set_rules('Privacy', 'Privacy', 'trim|required|in_list[Yes,No]');
        $this->form_validation->set_rules('IsPaid', 'IsPaid', 'trim|required|in_list[Yes,No]');
        $this->form_validation->set_rules('UnfilledWinningPercent', 'UnfilledWinningPercent', 'trim|in_list[GuranteedPool,Fixed]');
        $this->form_validation->set_rules('WinningRatio', 'Winning Percent', 'trim'.($this->Post['UnfilledWinningPercent'] == 'GuranteedPool' ? '|required':''));
        $this->form_validation->set_rules('WinUpTo', 'Win Up To', 'trim'.($this->Post['UnfilledWinningPercent'] == 'GuranteedPool' ? '|required':''));
        $this->form_validation->set_rules('ShowJoinedContest', 'ShowJoinedContest', 'trim|required|in_list[Yes,No]');
        $this->form_validation->set_rules('WinningAmount', 'WinningAmount', 'trim' . (!empty($this->Post['UnfilledWinningPercent']) && $this->Post['UnfilledWinningPercent'] == 'Fixed' && $this->Post['SmartPool'] == 'No' ? '|required|integer' : ''));
        $this->form_validation->set_rules('ContestSize', 'ContestSize', 'trim' . (!empty($this->Post['ContestFormat']) && $this->Post['ContestFormat'] == 'League' && $this->Post['UnfilledWinningPercent'] == 'Fixed' ? '|required|integer' : ''));
        $this->form_validation->set_rules('EntryFee', 'EntryFee', 'trim' . (!empty($this->Post['IsPaid']) && $this->Post['IsPaid'] == 'Yes' ? '|required|numeric' : ''));
        $this->form_validation->set_rules('NoOfWinners', 'NoOfWinners', 'trim' . ((!empty($this->Post['IsPaid']) && $this->Post['IsPaid'] == 'Yes') && $this->Post['UnfilledWinningPercent'] == 'Fixed' ? '|required|integer' : ''));
        $this->form_validation->set_rules('EntryType', 'EntryType', 'trim|required|in_list[Single,Multiple]');
        $this->form_validation->set_rules('UserJoinLimit', 'UserJoinLimit', 'trim' . (!empty($this->Post['EntryType']) && $this->Post['EntryType'] == 'Multiple' ? '|required|integer' : ''));
        $this->form_validation->set_rules('CashBonusContribution', 'CashBonusContribution', 'trim' . (!empty($this->Post['IsPaid']) && $this->Post['IsPaid'] == 'Yes' ? '|required|numeric|regex_match[/^[0-9][0-9]?$|^100$/]' : ''));
        $this->form_validation->set_rules('CustomizeWinning', 'Customize Winning', 'trim');
        $this->form_validation->set_rules('IsVirtualUserJoined', 'Is Virtual User Joined', 'trim');
        $this->form_validation->set_rules('VirtualUserJoinedPercentage', 'Virtual User Joined Percentage', 'trim' . (!empty($this->Post['IsVirtualUserJoined']) && $this->Post['IsVirtualUserJoined'] == 'Yes' ? '|required|integer' : ''));
        if (!empty($this->Post['CustomizeWinning']) && is_array($this->Post['CustomizeWinning']) && $this->Post['SmartPool'] == 'No') {
            $TotalWinners = $TotalPercent = $TotalWinningAmount = 0;
            foreach ($this->Post['CustomizeWinning'] as $Key => $Value) {
                $this->form_validation->set_rules('CustomizeWinning[' . $Key . '][From]', 'From', 'trim|required|integer');
                $this->form_validation->set_rules('CustomizeWinning[' . $Key . '][To]', 'To', 'trim|required|integer');
                $this->form_validation->set_rules('CustomizeWinning[' . $Key . '][Percent]', 'Percent', 'trim|required|numeric');
                $this->form_validation->set_rules('CustomizeWinning[' . $Key . '][WinningAmount]', 'WinningAmount', 'trim|required|numeric');
                $TotalWinners += ($Value['To'] - $Value['From']) + 1;
                $TotalPercent += $Value['Percent'];
                $TotalWinningAmount += $TotalWinners * $Value['WinningAmount'];
            }

            /* Check Total No Of Winners */
            if ($TotalWinners != $this->Post['NoOfWinners']) {
                $this->Return['ResponseCode'] = 500;
                $this->Return['Message'] = "Customize Winners should be equals to No Of Winners.";
                exit;
            }

            /* Check Total Percent */
            if ($TotalPercent < 100 || $TotalPercent > 100) {
                $this->Return['ResponseCode'] = 500;
                $this->Return['Message'] = "Customize Winners Percent should be 100%.";
                exit;
            }

            /* Check Total Winning Amount */
            if ($TotalWinningAmount != $this->Post['WinningAmount']) {
                $this->Return['ResponseCode'] = 500;
                $this->Return['Message'] = "Customize Winning Amount should be equals to Winning Amount";
                exit;
            }
        }
        $this->form_validation->set_message('regex_match', '{field} value should be between 0 to 100.');
        $this->form_validation->validation($this);  /* Run validation */
        /* Validation - ends */
        $this->Post['EntryFee'] = 0;
        $this->Contest_model->updateContest($this->Post, $this->SessionUserID, $this->ContestID);
        $this->Return['Message'] = "Contest updated successfully.";
    }

    /*
      Description: To get joined contests data
     */

    public function getUserJoinedContests_post() {
        $this->form_validation->set_rules('UserGUID', 'UserGUID', 'trim|required|callback_validateEntityGUID[User,UserID]');
        $this->form_validation->set_rules('Keyword', 'Search Keyword', 'trim');
        $this->form_validation->set_rules('Filter', 'Filter', 'trim|in_list[Normal,Head to Head]');
        $this->form_validation->set_rules('OrderBy', 'OrderBy', 'trim');
        $this->form_validation->set_rules('Sequence', 'Sequence', 'trim|in_list[ASC,DESC]');
        $this->form_validation->validation($this);  /* Run validation */
        /* Get Joined Contests Data */

        /** sql injection keyword*/
        $FindWord=array("sleep","delete","select","update","SLEEP","insert","DELETE","SELECT","UPDATE","INSERT","Sleep","Delete","Select","Update","Insert");
        $ReplaceWord=array("","","");
        $this->Post['Keyword'] = str_replace($FindWord,$ReplaceWord,$this->Post['Keyword']);
        $this->Post['OrderBy'] = str_replace($FindWord,$ReplaceWord,$this->Post['OrderBy']);
        $this->Post['Sequence'] = str_replace($FindWord,$ReplaceWord,$this->Post['Sequence']);

        $JoinedContestData = $this->Contest_model->getJoinedContests(@$this->Post['Params'], array_merge($this->Post, array('SessionUserID' => @$this->UserID)), TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']);

        if (!empty($JoinedContestData)) {
            $this->Return['Data'] = $JoinedContestData['Data'];
        }
    }

    /*
      Description: To get private contest detail
     */

    public function getPrivateContest_post() {
        $this->form_validation->set_rules('SeriesGUID', 'SeriesGUID', 'trim|callback_validateEntityGUID[Series,SeriesID]');
        $this->form_validation->set_rules('MatchGUID', 'MatchGUID', 'trim|callback_validateEntityGUID[Matches,MatchID]');
        $this->form_validation->set_rules('Status', 'Status', 'trim|callback_validateStatus');
        $this->form_validation->set_rules('StatusID', 'StatusID', 'trim');
        $this->form_validation->set_rules('UserGUID', 'UserGUID', 'trim|callback_validateEntityGUID[User,UserID]');
        $this->form_validation->set_rules('Privacy', 'Privacy', 'trim|required|in_list[Yes,No]');
        $this->form_validation->validation($this);  /* Run validation */

        /** sql injection keyword*/
        $FindWord=array("sleep","delete","select","update","SLEEP","insert","DELETE","SELECT","UPDATE","INSERT","Sleep","Delete","Select","Update","Insert");
        $ReplaceWord=array("","","");
        $this->Post['Keyword'] = str_replace($FindWord,$ReplaceWord,$this->Post['Keyword']);
        $this->Post['OrderBy'] = str_replace($FindWord,$ReplaceWord,$this->Post['OrderBy']);
        $this->Post['Sequence'] = str_replace($FindWord,$ReplaceWord,$this->Post['Sequence']);

        /* Get Contests Data */
        $ContestData = $this->Contest_model->getContests(@$this->Post['Params'], array_merge($this->Post, array('MatchID' => @$this->MatchID, 'ContestType' => @$this->Post['ContestType'], 'SeriesID' => @$this->SeriesID, 'UserID' => @$this->UserID, 'StatusID' => @$this->StatusID)), TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']);
        if (!empty($ContestData)) {
            $this->Return['Data'] = $ContestData['Data'];
        }
    }

    /*
      Description: To Cancel Contest
     */

    public function cancel_post() {
        $this->form_validation->set_rules('ContestGUID', 'ContestGUID', 'trim|required|callback_validateEntityGUID[Contest,ContestID]');
        $this->form_validation->validation($this);  /* Run validation */
        /* Cancel Contests */
        $this->Contest_model->cancelContest(@$this->Post, $this->SessionUserID, $this->ContestID);
        $this->Return['Message'] = "Contest cancelled successfully.";
    }

    /*
      Description: To Get Completed Contest Report
     */

    public function completedContestReport_post() {
        $this->form_validation->set_rules('MatchGUID', 'MatchGUID', 'trim|required|callback_validateEntityGUID[Matches,MatchID]');
        $this->form_validation->validation($this);  /* Run validation */

        /* Get Completed Contests Reports Data */
        $ContestData = $this->Contest_model->getContests(@$this->Post['Params'], array_merge($this->Post, array('MatchID' => $this->MatchID, 'StatusID' => 5)), TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']);
        if (!empty($ContestData)) {
            $this->Return['Data'] = $ContestData['Data'];
        }
    }

    /**
     * Function Name: validateAnyUserJoinedContest
     * Description:   To validate if any user joined contest
     */
    public function validateAnyUserJoinedContest($ContestGUID, $Type) {
        $TotalJoinedContest = $this->db->query('SELECT COUNT(*) AS `TotalRecords` FROM `sports_contest_join` WHERE `ContestID` =' . $this->ContestID)->row()->TotalRecords;
        // if ($TotalJoinedContest > 0){
        // 	$this->form_validation->set_message('validateAnyUserJoinedContest', 'You can not '.$Type.' this contest');
        // 	return FALSE;
        // }
        // else{
        return TRUE;
        // }
    }

    /**
     * Function Name: validateContestStatus
     * Description:   To validate contest status
     */
    public function validateContestStatus($ContestGUID) {
        $ContestData = $this->Contest_model->getContests('Status,IsPaid,SeriesName,ContestName,MatchNo,TeamNameLocal,TeamNameVisitor,EntryFee', array('ContestID' => $this->ContestID));
        if ($ContestData['Status'] == 'Pending') {
            $this->Post['IsPaid'] = $ContestData['IsPaid'];
            $this->Post['EntryFee'] = $ContestData['EntryFee'];
            $this->Post['SeriesName'] = $ContestData['SeriesName'];
            $this->Post['ContestName'] = $ContestData['ContestName'];
            $this->Post['MatchNo'] = $ContestData['MatchNo'];
            $this->Post['TeamNameLocal'] = $ContestData['TeamNameLocal'];
            $this->Post['TeamNameVisitor'] = $ContestData['TeamNameVisitor'];
            return TRUE;
        }
        /* else {
          $this->form_validation->set_message('validateContestStatus', 'You can not cancel this contest.');
          return FALSE;
          } */
    }

    /*
      Description: To get contest winning users
     */

    public function getContestWinningUsers_post() {
        $this->form_validation->set_rules('ContestGUID', 'ContestGUID', 'trim|required|callback_validateEntityGUID[Contest,ContestID]');
        $this->form_validation->set_rules('Keyword', 'Search Keyword', 'trim');
        $this->form_validation->set_rules('Filter', 'Filter', 'trim|in_list[Normal]');
        $this->form_validation->set_rules('OrderBy', 'OrderBy', 'trim');
        $this->form_validation->set_rules('Sequence', 'Sequence', 'trim|in_list[ASC,DESC]');
        $this->form_validation->validation($this);  /* Run validation */

        /** sql injection keyword*/
        $FindWord=array("sleep","delete","select","update","SLEEP","insert","DELETE","SELECT","UPDATE","INSERT","Sleep","Delete","Select","Update","Insert");
        $ReplaceWord=array("","","");
        $this->Post['Keyword'] = str_replace($FindWord,$ReplaceWord,$this->Post['Keyword']);
        $this->Post['OrderBy'] = str_replace($FindWord,$ReplaceWord,$this->Post['OrderBy']);
        $this->Post['Sequence'] = str_replace($FindWord,$ReplaceWord,$this->Post['Sequence']);
        
        /* Get Contests Winning Users Data */
        $WinningUsersData = $this->Contest_model->getContestWinningUsers(@$this->Post['Params'], array_merge($this->Post, array('ContestID' => $this->ContestID)), TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']);
        if (!empty($WinningUsersData)) {
            $this->Return['Data'] = $WinningUsersData['Data'];
        }
    }

    /*
      Description: 	Use to update contest status.
      URL: 			/api_admin/entity/changeStatus/
     */

    public function changeStatus_post() {
        /* Validation section */
        $this->form_validation->set_rules('ContestGUID', 'ContestGUID', 'trim|required|callback_validateEntityGUID[Contest,ContestID]');
        $this->form_validation->set_rules('Status', 'Status', 'trim|required|callback_validateStatus');
        $this->form_validation->validation($this);  /* Run validation */
        /* Validation - ends */
        $this->Entity_model->updateEntityInfo($this->ContestID, array("StatusID" => $this->StatusID));

        if (!empty($this->Post['UserInvitationCode'])) {
            $Exist = $this->db->query('SELECT UserInvitationCode FROM `sports_contest` WHERE ContestID NOT IN ('.$this->ContestID.') AND `UserInvitationCode` ="' . $this->Post['UserInvitationCode'] .'" LIMIT 1')->num_rows();
            if ($Exist>0){
                $this->Return['ResponseCode'] = 500;
                $this->Return['Message'] = "Please Enter Unique Invitation Code.";
                return FALSE;
            }
        }

        $this->Contest_model->updateContest($this->Post, $this->SessionUserID, $this->ContestID);

        $this->Return['Data'] = $this->Contest_model->getContests('Privacy,IsPaid,WinningAmount,ContestSize,EntryFee,NoOfWinners,EntryType,SeriesID,MatchID,SeriesGUID,TeamNameLocal,TeamNameVisitor,SeriesName,CustomizeWinning,ContestType,UserInvitationCode', array_merge($this->Post, array('ContestID' => $this->ContestID, 'SessionUserID' => $this->SessionUserID)));
        $this->Return['Message'] = "Data update has been successfully.";
    }


    public function CanacleContest_post() {
        /* Validation section */
        $this->form_validation->set_rules('ContestType', 'Contest Type', 'trim|required|in_list[Hot,Champion,Practice,More,Mega,Smart Pool,Infinity Pool,Winner Takes All,Only For Beginners,Head to Head]');
        $this->form_validation->set_rules('Status', 'Status', 'trim|required|callback_validateStatus');
        $this->form_validation->validation($this);  /* Run validation */
        // /* Validation - ends */
        $this->db->query("SET group_concat_max_len = 100000");
        $Ids = $this->db->query("SELECT Group_concat(EntityID) as IDs FROM sports_contest JOIN tbl_entity on tbl_entity.EntityID = sports_contest.ContestID WHERE ContestType = '".$this->Post['ContestType']."' AND tbl_entity.StatusID ='".$this->StatusID."' AND LeagueType = 'Dfs' AND Privacy = 'No' ")->row()->IDs;
        if($Ids)
        $this->db->query("UPDATE tbl_entity SET StatusID = 3 WHERE EntityID IN ($Ids)");
    }

    /*
      Description: To Get Completed Contest Report
     */

    public function dailyInvoiceReports_post() {

        $InvoiceData=$this->Contest_model->getDailyInvoiceReports($this->Post, TRUE, '', '');
        if (!empty($InvoiceData)) {
            $this->Return['Data'] = $InvoiceData['Data'];
        }
    }

    /**- CallBack Function -**/ 
    public function validateUniqueCode($UserInvitationCode) {
        $Exist = $this->db->query('SELECT UserInvitationCode FROM `sports_contest` WHERE `UserInvitationCode` ="' . $UserInvitationCode .'" LIMIT 1')->num_rows();
        if ($Exist>0){
            $this->form_validation->set_message('validateUniqueCode', 'Please Enter Unique Invitation Code.');
            return FALSE;
        }
        return TRUE;
    }


}

?>