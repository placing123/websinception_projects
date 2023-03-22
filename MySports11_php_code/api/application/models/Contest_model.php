    <?php

if (!defined('BASEPATH')) exit('No direct script access allowed');

class Contest_model extends CI_Model {

    public function __construct() {
        parent::__construct();
        $this->load->model('Sports_model');
    }

    /*
      Description:    ADD contest to system.
     */

    function addContest($Input = array(), $SessionUserID, $MatchID, $SeriesID, $StatusID = 1) {
        $this->db->trans_start();
        $EntityGUID = get_guid();
        /* Add contest to entity table and get EntityID. */
        $EntityID = $this->Entity_model->addEntity($EntityGUID, array("EntityTypeID" => 11, "UserID" => $SessionUserID, "StatusID" => $StatusID));
        $ContestName = $Input['ContestName'];
        if (empty($Input['ContestName'])) {
            if (($Input['IsPaid'] == 'Yes')) {
                $ContestName = "Win " . @$Input['WinningAmount'];
            } else {
                $ContestName = "Win Skill";
            }
        }
        if ($Input['UnfilledWinningPercent'] == 'GuranteedPool') {
            $Input['ContestSize'] = 0;
            $Input['SmartPool'] = "No";
            $Input['WinningAmount'] = 0;
        }
        if ($Input['SmartPool'] == 'Yes') {
            $Input['WinningAmount'] = 0;
            $Input['AdminPercent'] = 0;
        }

        $defaultCustomizeWinningObj = new stdClass();
        $defaultCustomizeWinningObj->From = 1;
        $defaultCustomizeWinningObj->To = 1;
        $defaultCustomizeWinningObj->Percent = 100;
        $defaultCustomizeWinningObj->WinningAmount = (int) @$Input['WinningAmount'];

        /* Add contest to contest table . */
        $InsertData = array_filter(array(
            "ContestID" => $EntityID,
            "ContestGUID" => $EntityGUID,
            "UserID" => ($Input['Privacy'] == 'Yes') ? $SessionUserID : null,
            "GameTimeLive" => @$Input['GameTimeLive'],
            "GameType" => @$Input['GameType'],
            "PreContestID" => @$Input['PreContestID'],
            "ContestName" => $ContestName,
            "ContestFormat" => @$Input['ContestFormat'],
            "ContestType" => @$Input['ContestType'],
            "AdminPercent" => @$Input['AdminPercent'],
            "ContestContribution" => @$Input['ContestContribution'],
            "Privacy" => @$Input['Privacy'],
            "SmartPoolText" => @$Input['SmartPoolText'],
            "IsPaid" => @$Input['IsPaid'],
            "IsConfirm" => (@$Input['Privacy'] == 'Yes') ? 'No' : @$Input['IsConfirm'],
            "IsAutoCreate" => @$Input['IsAutoCreate'],
            "UnfilledWinningPercent" => (@$Input['ContestFormat'] == 'Head to Head' ? 'Fixed' : @$Input['UnfilledWinningPercent']),
            "WinUpTo" => @$Input['WinUpTo'],
            "WinningRatio" => @$Input['WinningRatio'],
            "SmartPool" => @$Input['SmartPool'],
            "ShowJoinedContest" => @$Input['ShowJoinedContest'],
            "WinningAmount" => @$Input['WinningAmount'],
            "WinningType" => @$Input['WinningType'],
            "ContestSize" => (@$Input['ContestFormat'] == 'Head to Head') ? 2 : @$Input['ContestSize'],
            "EntryFee" => (@$Input['IsPaid'] == 'Yes') ? @$Input['EntryFee'] : 0,
            "NoOfWinners" => (@$Input['IsPaid'] == 'Yes') ? @$Input['NoOfWinners'] : 1,
            "EntryType" => @$Input['EntryType'],
            "UserJoinLimit" => (@$Input['EntryType'] == 'Multiple') ? @$Input['UserJoinLimit'] : 1,
            "CashBonusContribution" => @$Input['CashBonusContribution'],
            "IsPrivacyNameDisplay" => @$Input['IsPrivacyNameDisplay'],
            "CustomizeWinning" => (!empty(@$Input['CustomizeWinning'])) ? ((@$Input['ContestFormat'] == 'Head to Head' && @$Input['SmartPool'] == 'No') ? json_encode(array(array('From' => 1, 'To' => 1, 'Percent' => 100, 'WinningAmount' => @$Input['WinningAmount']))) : @$Input['CustomizeWinning']) : json_encode(array($defaultCustomizeWinningObj)),
            "SeriesID" => @$SeriesID,
            "MatchID" => @$MatchID,
            "UserInvitationCode" => (!empty($Input['UserInvitationCode'])) ? $Input['UserInvitationCode'] : "MYSKL-".random_string('alnum', 6),
            "IsVirtualUserJoined" => @$Input['IsVirtualUserJoined'],
            "VirtualUserJoinedPercentage" => @$Input['VirtualUserJoinedPercentage']
        ));
        $this->db->insert('sports_contest', $InsertData);
        $this->db->trans_complete();
        if ($this->db->trans_status() === FALSE) {
            return FALSE;
        }
        return $EntityID;
    }

    /*
      Description: Update contest to system.
     */

    function updateContest($Input = array(), $SessionUserID, $ContestID, $StatusID = 1) {
        $defaultCustomizeWinningObj = new stdClass();
        $defaultCustomizeWinningObj->From = 1;
        $defaultCustomizeWinningObj->To = 1;
        $defaultCustomizeWinningObj->Percent = "100";
        $defaultCustomizeWinningObj->WinningAmount = @$Input['WinningAmount'];

        if ($Input['UnfilledWinningPercent'] == 'GuranteedPool') {
            $Input['ContestSize'] = 0;
            $Input['NoOfWinners'] = 0;
            $Input['WinningAmount'] = 0;
            $Input['SmartPool'] = "No";
        }

        if ($Input['SmartPool'] == 'Yes') {
            $Input['WinningAmount'] = 0;
            $Input['AdminPercent'] = 0;
        }

        /* Add contest to contest table . */
        $UpdateData = array_filter(array(
            "GameType" => @$Input['GameType'],
            "GameTimeLive" => @$Input['GameTimeLive'],
            "ContestName" => @$Input['ContestName'],
            "ContestFormat" => @$Input['ContestFormat'],
            "ContestType" => @$Input['ContestType'],
            "Privacy" => @$Input['Privacy'],
            "AdminPercent" => @$Input['AdminPercent'],
            "ContestContribution" => @$Input['ContestContribution'],
            "IsPaid" => @$Input['IsPaid'],
            "IsConfirm" => @$Input['IsConfirm'],
            "IsAutoCreate" => @$Input['IsAutoCreate'],
            "UnfilledWinningPercent" => @$Input['UnfilledWinningPercent'],
            "WinUpTo" => @$Input['WinUpTo'],
            "WinningRatio" => @$Input['WinningRatio'],
            "ShowJoinedContest" => @$Input['ShowJoinedContest'],
             "WinningType" => @$Input['WinningType'],
            //"WinningAmount" => @$Input['WinningAmount'],
            "ContestSize" => (@$Input['ContestFormat'] == 'Head to Head') ? 2 : @$Input['ContestSize'],
            "EntryFee" => (@$Input['IsPaid'] == 'Yes') ? @$Input['EntryFee'] : 0,
            "NoOfWinners" => (@$Input['IsPaid'] == 'Yes') ? @$Input['NoOfWinners'] : 1,
            "EntryType" => @$Input['EntryType'],
            "UserJoinLimit" => (@$Input['EntryType'] == 'Multiple') ? @$Input['UserJoinLimit'] : 1,
            "CashBonusContribution" => @$Input['CashBonusContribution'],
            "IsPrivacyNameDisplay" => @$Input['IsPrivacyNameDisplay'],
            "IsVirtualUserJoined" => @$Input['IsVirtualUserJoined'],
            "VirtualUserJoinedPercentage" => @$Input['VirtualUserJoinedPercentage'],
            "UserInvitationCode" => (!empty($Input['UserInvitationCode'])) ? $Input['UserInvitationCode'] : "MYSKL-".random_string('alnum', 6),
                //"CustomizeWinning" => (@$Input['IsPaid'] == 'Yes' && !empty($Input['CustomizeWinning'])) ? @$Input['CustomizeWinning'] : json_encode(array($defaultCustomizeWinningObj)),
        ));
        if ($Input['CashBonusContribution'] == 0) {
            $UpdateData['CashBonusContribution'] = @$Input['CashBonusContribution'];
        }
        if ($Input['UnfilledWinningPercent'] != 'GuranteedPool') {
            $UpdateData['WinningAmount'] = @$Input['WinningAmount'];
            $UpdateData['CustomizeWinning'] = (@$Input['IsPaid'] == 'Yes' && !empty($Input['CustomizeWinning'])) ? @$Input['CustomizeWinning'] : json_encode(array($defaultCustomizeWinningObj));
        }
        $this->db->where('ContestID', $ContestID);
        $this->db->limit(1);
        $this->db->update('sports_contest', $UpdateData);
    }

    /*
      Description: Delete contest to system.
     */

    function deleteContest($SessionUserID, $ContestID) {
        $this->db->where('ContestID', $ContestID);
        $this->db->limit(1);
        $this->db->delete('sports_contest');
    }

    /*
      Description: To get contest
     */

    function getContests($Field = '', $Where = array(), $multiRecords = FALSE, $PageNo = 1, $PageSize = 15) {
        $Params = array();
        if (!empty($Field)) {
            $Params = array_map('trim', explode(',', $Field));
            $Field = '';
            $FieldArray = array(
                'UserID' => 'C.UserID',
                'MatchID' => 'M.MatchID',
                'MatchGUID' => 'M.MatchGUID',
                'StatusID' => 'E.StatusID',
                'MatchIDLive' => 'M.MatchIDLive',
                'MatchTypeID' => 'M.MatchTypeID',
                'MatchNo' => 'M.MatchNo',
                'MatchTypeByApi' => 'M.MatchTypeByApi',
                'MatchStartDateTime' => 'CONVERT_TZ(M.MatchStartDateTime,"+00:00","' . DEFAULT_TIMEZONE . '") AS MatchStartDateTime',
                'MatchStartDateTimeUTC' => 'M.MatchStartDateTime as MatchStartDateTimeUTC',
                'MatchScoreDetails' => 'M.MatchScoreDetails',
                'AdminPercent' => 'C.AdminPercent',
                'ContestID' => 'C.ContestID',
                'GameTimeLive' => 'C.GameTimeLive',
                'LeagueType' => 'C.LeagueType',
                'GameType' => 'C.GameType',
                'ContestContribution' => 'C.ContestContribution',
                'Privacy' => 'C.Privacy',
                'UnfilledWinningPercent' => 'C.UnfilledWinningPercent',
                'WinUpTo' => 'C.WinUpTo',
                'WinningRatio' => 'C.WinningRatio',
                'SmartPool' => 'C.SmartPool',
                'SmartPoolText' => 'C.SmartPoolText',
                'IsPaid' => 'C.IsPaid',
                'IsConfirm' => 'C.IsConfirm',
                "IsAutoCreate" => 'C.IsAutoCreate',
                "IsVirtualUserJoined" => 'C.IsVirtualUserJoined',
                "VirtualUserJoinedPercentage" => 'C.VirtualUserJoinedPercentage',
                'ShowJoinedContest' => 'C.ShowJoinedContest',
                'WinningAmount' => 'C.WinningAmount',
                'ContestSize' => 'C.ContestSize',
                'ContestFormat' => 'C.ContestFormat',
                'ContestType' => 'C.ContestType',
                'CustomizeWinning' => 'C.CustomizeWinning',
                'EntryFee' => 'C.EntryFee',
                'IsDummyJoined' => 'C.IsDummyJoined',
                'NoOfWinners' => 'C.NoOfWinners',
                'EntryType' => 'C.EntryType',
                'WinningType' => 'C.WinningType',
                'UserJoinLimit' => 'C.UserJoinLimit',
                'CashBonusContribution' => 'C.CashBonusContribution',
                'EntryType' => 'C.EntryType',
                'IsWinningDistributed' => 'C.IsWinningDistributed',
                'IsWinningDistributeAmount' => 'C.IsWinningDistributeAmount',
                'UserInvitationCode' => 'C.UserInvitationCode',
                'IsPrivacyNameDisplay' => 'C.IsPrivacyNameDisplay',
                'SeriesID' => 'M.SeriesID',
                'TeamNameLocal' => 'TL.TeamName AS TeamNameLocal',
                'TeamGUIDLocal' => 'TL.TeamGUID AS TeamGUIDLocal',
                'TeamGUIDVisitor' => 'TV.TeamGUID AS TeamGUIDVisitor',
                'TeamNameVisitor' => 'TV.TeamName AS TeamNameVisitor',
                'TeamNameShortLocal' => 'TL.TeamNameShort AS TeamNameShortLocal',
                'TeamNameShortVisitor' => 'TV.TeamNameShort AS TeamNameShortVisitor',
                'TeamFlagLocal' => 'CONCAT("' . BASE_URL . '","uploads/TeamFlag/",TL.TeamFlag) as TeamFlagLocal',
                'TeamFlagVisitor' => 'CONCAT("' . BASE_URL . '","uploads/TeamFlag/",TV.TeamFlag) as TeamFlagVisitor',
                'StatusID' => 'E.StatusID',
                'SeriesName' => 'S.SeriesName',
                'IsJoined' => '(SELECT IF( EXISTS(SELECT EntryDate FROM sports_contest_join
                                                        WHERE sports_contest_join.ContestID =  C.ContestID AND UserID = ' . @$Where['SessionUserID'] . ' LIMIT 1), "Yes", "No")) AS IsJoined',
                'TotalJoined' => '(SELECT COUNT(*)
                                                        FROM sports_contest_join
                                                        WHERE ContestID =  C.ContestID ) AS TotalJoined',
                'UserTotalJoinedInMatch' => '(SELECT COUNT(*)
                                                FROM sports_contest_join,tbl_entity
                                                WHERE sports_contest_join.MatchID =  M.MatchID AND sports_contest_join.ContestID = tbl_entity.EntityID AND tbl_entity.StatusID != 3 AND sports_contest_join.UserID= ' . @$Where['SessionUserID'] . ') AS UserTotalJoinedInMatch',
                'Status' => 'CASE E.StatusID
                                                    when "1" then "Pending"
                                                    when "2" then "Running"
                                                    when "3" then "Cancelled"
                                                    when "5" then "Completed"
                                                    END as Status',
                'TotalCashBonusUsed' => '(SELECT SUM(`JoinCashBonus`)
                                    FROM sports_contest_join
                                    WHERE ContestID =  C.ContestID ) AS TotalCashBonusUsed',
                'MatchType' => 'MT.MatchTypeName AS MatchType',
                'CurrentDateTime' => 'DATE_FORMAT(CONVERT_TZ(Now(),"+00:00","' . DEFAULT_TIMEZONE . '"), "' . DATE_FORMAT . ' ") CurrentDateTime',
                'MatchDate' => 'DATE_FORMAT(CONVERT_TZ(M.MatchStartDateTime,"+00:00","' . DEFAULT_TIMEZONE . '"), "%Y-%m-%d") MatchDate',
                'MatchTime' => 'DATE_FORMAT(CONVERT_TZ(M.MatchStartDateTime,"+00:00","' . DEFAULT_TIMEZONE . '"), "%H:%i:%s") MatchTime',
            );
            if ($Params) {
                foreach ($Params as $Param) {
                    $Field .= (!empty($FieldArray[$Param]) ? ',' . $FieldArray[$Param] : '');
                }
            }
        }
        $this->db->select('C.ContestGUID,C.ContestName,C.ContestSize,C.ContestID');
        if (!empty($Field)) $this->db->select($Field, FALSE);
        $this->db->from('tbl_entity E, sports_contest C, sports_matches M, sports_teams TL, sports_teams TV,sports_series S,sports_set_match_types MT');
        $this->db->where("C.ContestID", "E.EntityID", FALSE);
        $this->db->where("M.MatchID", "C.MatchID", FALSE);
        $this->db->where("S.SeriesID", "C.SeriesID", FALSE);
        $this->db->where("M.TeamIDLocal", "TL.TeamID", FALSE);
        $this->db->where("M.TeamIDVisitor", "TV.TeamID", FALSE);
        $this->db->where("M.MatchTypeID", "MT.MatchTypeID", FALSE);
        $this->db->where("C.LeagueType", "Dfs");
        if (!empty($Where['Keyword'])) {
            if (is_array(json_decode($Where['Keyword'], true))) {
                $Where['Keyword'] = json_decode($Where['Keyword'], true);

                if (isset($Where['Keyword']['ContestName'])) {
                    $this->db->like("C.ContestName", @$Where['Keyword']['ContestName']);
                }
                if (isset($Where['Keyword']['ContestType'])) {
                    $this->db->where("C.ContestType", @$Where['Keyword']['ContestType']);
                }
                if (isset($Where['Keyword']['GameType'])) {
                    $this->db->where("C.GameType", @$Where['Keyword']['GameType']);
                }
                if (isset($Where['Keyword']['ContestSize'])) {
                    $ContestSize = explode("-", $Where['Keyword']['ContestSize']);

                    if (count($ContestSize) > 1) {
                        $this->db->where("C.ContestSize >=", @$ContestSize[0]);
                        $this->db->where("C.ContestSize <=", @$ContestSize[1]);
                    } else {
                        $this->db->where("C.ContestSize >=", @$ContestSize[0]);
                    }
                }
                if (isset($Where['Keyword']['EntryFee'])) {

                    $EntryFee = explode("-", $Where['Keyword']['EntryFee']);
                    if (count($EntryFee) > 1) {
                        $this->db->where("C.EntryFee >=", $EntryFee[0]);
                        $this->db->where("C.EntryFee <=", $EntryFee[1]);
                    } else {
                        $this->db->where("C.EntryFee >=", $EntryFee[0]);
                    }
                }
            } else {
                $this->db->group_start();
                $this->db->like("C.ContestName", $Where['Keyword']);
                $this->db->or_like("C.GameType", $Where['Keyword']);
                $this->db->or_like("C.WinningAmount", $Where['Keyword']);
                $this->db->or_like("C.ContestSize", $Where['Keyword']);
                $this->db->or_like("C.EntryFee", $Where['Keyword']);
                $this->db->or_like("M.MatchLocation", $Where['Keyword']);
                $this->db->or_like("M.MatchNo", $Where['Keyword']);
                $this->db->group_end();
            }
        }

        if (!empty($Where['AdvanceSafeValidate'])) {
            $this->db->where("M.MatchStartDateTime >= (UTC_TIMESTAMP() + INTERVAL C.GameTimeLive MINUTE)");
        }

        if (!empty($Where['ContestID'])) {
            $this->db->where("C.ContestID", $Where['ContestID']);
        }
        if (!empty($Where['ContestGUID'])) {
            $this->db->where("C.ContestGUID", $Where['ContestGUID']);
        }
        if (!empty($Where['AuctionStatusID'])) {
            $this->db->where("C.AuctionStatusID", $Where['AuctionStatusID']);
        }
        if (!empty($Where['IsVirtualUserJoined']) && $Where['IsVirtualUserJoined'] == 'Yes') {
            $this->db->where("C.IsVirtualUserJoined", $Where['IsVirtualUserJoined']);
        }
        if (!empty($Where['SeriesID'])) {
            $this->db->where("C.SeriesID", $Where['SeriesID']);
        }
        if (!empty($Where['WinningType'])) {
            $this->db->where("C.WinningType", $Where['WinningType']);
        }
        if (!empty($Where['UserID'])) {
            $this->db->where("C.UserID", $Where['UserID']);
        }
        if (!empty($Where['Filter']) && $Where['Filter'] == 'Today') {
            $this->db->where("DATE(M.MatchStartDateTime)", date('Y-m-d'));
        }
        if (!empty($Where['Filter']) && $Where['Filter'] == 'MatchLive') {
            $this->db->where("M.MatchStartDateTime <=", date('Y-m-d H:i:s'));
        }
        if (!empty($Where['IsWinningAmount']) && $Where['IsWinningAmount'] == 'Yes') {
            $this->db->where("C.WinningAmount >", 0);
        }
        if (!empty($Where['Filter']) && $Where['Filter'] == 'Yesterday') {
            $this->db->where("DATE(M.MatchStartDateTime) <=", date('Y-m-d'));
        }
        if (!empty($Where['GameType'])) {
            $this->db->where("C.GameType", $Where['GameType']);
        }
        if (!empty($Where['LeagueType'])) {
            $this->db->where("C.LeagueType", $Where['LeagueType']);
        }
        if (!empty($Where['Privacy']) && $Where['Privacy'] != 'All') {
            $this->db->where("C.Privacy", $Where['Privacy']);
        }
        if (!empty($Where['ContestType'])) {
            $this->db->where("C.ContestType", $Where['ContestType']);
        }
        if (!empty($Where['EntryStartFrom'])) {
            $this->db->where("C.EntryFee >=", $Where['EntryStartFrom']);
        }
        if (!empty($Where['EntryEndTo'])) {
            $this->db->where("C.EntryFee <=", $Where['EntryEndTo']);
        }
        if (!empty($Where['WinningStartFrom'])) {
            $this->db->where("C.WinningAmount >=", $Where['WinningStartFrom']);
        }
        if (!empty($Where['WinningEndTo'])) {
            $this->db->where("C.WinningAmount <=", $Where['WinningEndTo']);
        }
        if (!empty($Where['ContestSizeStartFrom'])) {
            $this->db->where("C.ContestSize >=", $Where['ContestSizeStartFrom']);
        }
        if (!empty($Where['ContestSizeEndTo'])) {
            $this->db->where("C.ContestSize <=", $Where['ContestSizeEndTo']);
        }
        if (!empty($Where['IsRefund'])) {
            $this->db->where("C.IsRefund", $Where['IsRefund']);
        }
        if (!empty($Where['IsWinningDistributeAmount'])) {
            $this->db->where("C.IsWinningDistributeAmount", $Where['IsWinningDistributeAmount']);
        }
        if (!empty($Where['isMailSent'])) {
            $this->db->where("C.isMailSent", $Where['isMailSent']);
        }
        if (!empty($Where['ContestFormat'])) {
            $this->db->where("C.ContestFormat", $Where['ContestFormat']);
        }
        if (!empty($Where['IsPaid'])) {
            $this->db->where("C.IsPaid", $Where['IsPaid']);
        }
        if (!empty($Where['IsConfirm'])) {
            $this->db->where("C.IsConfirm", $Where['IsConfirm']);
        }
        if (!empty($Where['WinningAmount'])) {
            $this->db->where("C.WinningAmount >=", $Where['WinningAmount']);
        }
        if (!empty($Where['ContestSize'])) {
            $this->db->where("C.ContestSize", $Where['ContestSize']);
        }
        if (!empty($Where['FromDate'])) {
            $this->db->where("DATE(M.MatchStartDateTime) >=", $Where['FromDate']);
        }
        if (!empty($Where['ToDate'])) {
            $this->db->where("DATE(M.MatchStartDateTime) <=", $Where['ToDate']);
        }
        if (!empty($Where['EntryFee'])) {
            $this->db->where("C.EntryFee", $Where['EntryFee']);
        }
        if (!empty($Where['SmartPool'])) {
            $this->db->where("C.SmartPool", $Where['SmartPool']);
        }
        if (!empty($Where['NoOfWinners'])) {
            $this->db->where("C.NoOfWinners", $Where['NoOfWinners']);
        }
        if (!empty($Where['EntryType'])) {
            $this->db->where("C.EntryType", $Where['EntryType']);
        }
        if (!empty($Where['MatchID'])) {
            $this->db->where("C.MatchID", $Where['MatchID']);
        }
        if (!empty($Where['SeriesID'])) {
            $this->db->where("M.SeriesID", $Where['SeriesID']);
        }
        if (!empty($Where['StatusID'])) {
            $this->db->where_in("E.StatusID", $Where['StatusID']);
        }
        if (!empty($Where['MyJoinedContest']) && $Where['MyJoinedContest'] == "Yes") {
            $this->db->where('EXISTS (select ContestID from sports_contest_join JE where JE.ContestID = C.ContestID AND JE.UserID=' . @$Where['SessionUserID'] . ')');
        }
        if (!empty($Where['UserInvitationCode'])) {
            $this->db->where("C.UserInvitationCode", $Where['UserInvitationCode']);
        }
        if (!empty($Where['IsWinningDistributed'])) {
            $this->db->where("C.IsWinningDistributed", $Where['IsWinningDistributed']);
        }
        if (!empty($Where['ContestSizeRange'])) {
            $Range = explode('-', $Where['ContestSizeRange']);
            if (!empty($Range) && count($Range) == 2) {
                $this->db->where("C.ContestSize >=", $Range[0]);
                $this->db->where("C.ContestSize <=", $Range[1]);
            } else if (!empty($Range) && count($Range) == 1) {
                $this->db->where("C.ContestSize >=", $Range[0]);
            }
        }
        if (!empty($Where['EntryFeeRange'])) {
            $Range = explode('-', $Where['EntryFeeRange']);
            if (!empty($Range) && count($Range) == 2) {
                $this->db->where("C.EntryFee >=", $Range[0]);
                $this->db->where("C.EntryFee <=", $Range[1]);
            } else if (!empty($Range) && count($Range) == 1) {
                $this->db->where("C.EntryFee >=", $Range[0]);
            }
        }
        if (!empty($Where['ContestFull']) && $Where['ContestFull'] == 'Yes') {
            $this->db->having("TotalJoined", 'C.ContestSize', FALSE);
        }
        if (!empty($Where['OrderBy']) && !empty($Where['Sequence'])) {
            if (!empty($Where['ContestList']) && $Where['ContestList'] == 'Yes') {
                $this->db->order_by('TotalJoined - C.ContestSize !=0', 'DESC', FALSE);
            }
            $this->db->order_by($Where['OrderBy'], $Where['Sequence']);
            $this->db->order_by('WinningAmount', 'DESC', FALSE);
        } else {
            if (!empty($Where['OrderByToday']) && $Where['OrderByToday'] == 'Yes') {
                if ($Where['StatusID'] == 5 || $Where['StatusID'] == 3) {
                    $this->db->order_by('M.MatchStartDateTime', 'DESC');
                } else {
                    $this->db->order_by('DATE(M.MatchStartDateTime)="' . date('Y-m-d') . '" DESC', null, FALSE);
                    $this->db->order_by('E.StatusID=2 DESC', null, FALSE);
                    $this->db->order_by('E.StatusID=1 DESC', null, FALSE);
                }
            } else if (!empty($Where['OrderByList']) && $Where['OrderByList'] == 'Yes') {
                $this->db->order_by('M.MatchStartDateTime', 'DESC');
            } else {
                $this->db->order_by('M.MatchStartDateTime', 'ASC');
            }
        }

        /* Total records count only if want to get multiple records */
        if ($multiRecords) {
            $TempOBJ = clone $this->db;
            $TempQ = $TempOBJ->get();
            $Return['Data']['TotalRecords'] = $TempQ->num_rows();
            if ($PageNo != 0) {
                $this->db->limit($PageSize, paginationOffset($PageNo, $PageSize)); /* for pagination */
            }
        } else {
            $this->db->limit(1);
        }
        $Query = $this->db->get();
        // echo $this->db->last_query();die;
        if ($Query->num_rows() > 0) {
            date_default_timezone_set('Asia/Calcutta'); 
            $CurrentDateTime =  date('Y-m-d H:i:s');
            date_default_timezone_set('UTC');
            if ($multiRecords) {
                $Records = array();
                // print_r($Query->result_array());die;
                foreach ($Query->result_array() as $key => $Record) {
                    $Records[] = $Record;
                    if (in_array('CreatedBy',  $Params) && $Where['Privacy'] == 'Yes') {
                        if (!empty($Record['UserID'])) {
                            $Records[$key]['CreatedBy'] =  "User ".$this->db->query('SELECT FirstName FROM tbl_users WHERE UserID = '.$Record['UserID'].' LIMIT 1')->row()->FirstName;
                        }else{
                            $Records[$key]['CreatedBy'] = "Admin";
                        }

                    }
                    $Records[$key]['Offer1'] = new stdClass();
                    $Records[$key]['Offer2'] = new stdClass();
                    /**-- Get Latest Offer --**/
                    $LatestOfferForAll = $this->db->query('SELECT * FROM tbl_offers WHERE JSON_SEARCH(`ContestIDs`,"one", "'.$Record['ContestID'].'") IS NOT NULL ORDER BY ID DESC LIMIT 1')->row();
                    // print_r($LatestOfferForAll);die;
                    $ExistFirstOffer = '';
                    $ExistSecOffer = '';
                    if (!empty($LatestOfferForAll) && $LatestOfferForAll->UserSelectionType == 'AllUsers' && $LatestOfferForAll->OfferType == 'Offer-1') {
                        /**Work By kamlesh**/ 
                        /*-Apply Offer 1 for ALl Users-*/
                        $ExistFirstOffer = 'Offer-1';
                        $Records[$key]['Offer1'] = $LatestOfferForAll;
                        $Records[$key]['Offer1']->OfferPrize = ($Record['EntryFee'] - (int) floor(($Record['EntryFee'] * $LatestOfferForAll->OfferPercent) / 100));
                        $Records[$key]['Offer1']->CurrentDateTime = $CurrentDateTime;
                    }

                    if (!empty($LatestOfferForAll) && $LatestOfferForAll->UserSelectionType == 'AllUsers' && $LatestOfferForAll->OfferType == 'Offer-2') {
                        /*-Apply Offer 2 for ALl Users-*/ 
                        $ExistSecOffer = 'Offer-2';
                        $Records[$key]['Offer2'] = $LatestOfferForAll;
                        $Records[$key]['Offer2']->OfferPrize = ($Record['EntryFee'] - (int) floor(($Record['EntryFee'] * $LatestOfferForAll->OfferPercent) / 100));
                        $Records[$key]['Offer2']->CurrentDateTime = $CurrentDateTime;
                    }

                    $OfferOneQuery = $this->db->query('SELECT * FROM tbl_offers WHERE JSON_SEARCH(`ContestIDs`,"one", "'.$Record['ContestID'].'") IS NOT NULL AND JSON_SEARCH(`UserIDs`,"one", "'.$Where['SessionUserID'].'") IS NOT NULL AND OfferType = "Offer-1" ORDER BY ID DESC LIMIT 1')->row();
                    if(!empty($OfferOneQuery) && $ExistFirstOffer=='')
                    {
                        $Records[$key]['Offer1'] = $OfferOneQuery;
                        $Records[$key]['Offer1']->OfferPrize = ($Record['EntryFee'] - (int) floor(($Record['EntryFee'] * $OfferOneQuery->OfferPercent) / 100));
                        $Records[$key]['Offer1']->CurrentDateTime = $CurrentDateTime;
                    }
                    $OfferTwoQuery = $this->db->query('SELECT * FROM tbl_offers WHERE JSON_SEARCH(`ContestIDs`,"one", "'.$Record['ContestID'].'") IS NOT NULL AND JSON_SEARCH(`UserIDs`,"one", "'.$Where['SessionUserID'].'") IS NOT NULL AND OfferType = "Offer-2" ORDER BY ID DESC LIMIT 1')->row();
                    if(!empty($OfferTwoQuery) && $ExistSecOffer=='')
                    {
                        $Records[$key]['Offer2'] = $OfferTwoQuery;
                        $Records[$key]['Offer2']->OfferPrize = ($Record['EntryFee'] - (int) floor(($Record['EntryFee'] * $OfferTwoQuery->OfferPercent) / 100));
                        $Records[$key]['Offer2']->CurrentDateTime = $CurrentDateTime;
                    }

                    $Records[$key]['CustomizeWinning'] = (!empty($Record['CustomizeWinning'])) ? json_decode($Record['CustomizeWinning'], true) : array();
                    $Records[$key]['MatchScoreDetails'] = (!empty($Record['MatchScoreDetails'])) ? json_decode($Record['MatchScoreDetails'], TRUE) : new stdClass();
                    $TotalAmountReceived = $this->getTotalContestCollections($Record['ContestGUID']);
                    $Records[$key]['TotalAmountReceived'] = ($TotalAmountReceived) ? (int) $TotalAmountReceived : 0;
                    $TotalWinningAmount = $this->getTotalWinningAmount($Record['ContestGUID']);
                    $Records[$key]['TotalWinningAmount'] = ($TotalWinningAmount) ? (int) $TotalWinningAmount : 0;
                    $Records[$key]['NoOfWinners'] = ($Record['NoOfWinners'] == 0 ) ? 1 : $Record['NoOfWinners'];
                    $Records[$key]['ContestSize'] = ($Record['ContestSize'] == 0 ) ? 'Unlimited' : $Record['ContestSize'];
                    if (in_array('IsJoined', $Params)) {
                        if ($Record['IsJoined'] == 'Yes') {
                            $UserTeamDetails = $this->getUserTeams('TotalPoints', array('ContestID' => @$Record['ContestID'], 'UserID' => $Where['SessionUserID'], 'ValidateAdvanceSafe' => "No"), true, 0);
                            $Records[$key]['UserTeamDetails'] = ($UserTeamDetails) ? $UserTeamDetails['Data']['Records'] : array();
                        } else {
                            $Records[$key]['UserTeamDetails'] = array();
                        }
                        unset($Records[$key]['ContestID']);
                    }
                    if (in_array('UserWinningAmount', $Params)) {
                        /* update user time break . */
                        if ($Record['Status'] == "Running" || $Record['Status'] == "Completed" || $Record['Status'] == "Reviewing" || $Record['Status'] == "Review") {
                            $ContestCollection = $this->fantasydb->{'Contest_' . $Record['ContestID']};
                            $JoinedTeams = $ContestCollection->findOne(["UserID" => $Where['SessionUserID']], ['projection' => ['_id' => 0, 'UserGUID' => 1, 'UserTeamName' => 1, 'UserTeamGUID' => 1, 'Username' => 1, 'TotalPoints' => 1, 'UserRank' => 1, 'UserWinningAmount' => 1, "SmartPoolWinning" => 1, 'SmartPool' => 1], 'sort' => ['UserRank' => 1]]);
                            $JoinedContestsUsers = (!empty($JoinedTeams)) ? iterator_to_array($JoinedTeams) : array();
                            if (count($JoinedContestsUsers) > 0) {
                                $Records[$key]['UserWinningAmount'] = (String) $JoinedContestsUsers['UserWinningAmount'];
                                $Records[$key]['SmartPoolWinning'] = (isset($JoinedContestsUsers['SmartPoolWinning'])) ? $JoinedContestsUsers['SmartPoolWinning'] : "";
                                $Records[$key]['TotalPoints'] = (String) $JoinedContestsUsers['TotalPoints'];
                                $Records[$key]['UserRank'] = (String) $JoinedContestsUsers['UserRank'];
                                $Records[$key]['UserTeamName'] = (String) $JoinedContestsUsers['UserTeamName'];
                            } else {
                                $Query = $this->db->query('SELECT JC.UserWinningAmount,JC.SmartPoolWinning,JC.TotalPoints,JC.UserRank,UT.UserTeamName FROM sports_contest_join JC,sports_users_teams UT WHERE UT.UserTeamID = JC.UserTeamID AND JC.ContestID = "' . $Record['ContestID'] . '" AND JC.UserID = "' . $Where['SessionUserID'] . '"');
                                $UserWinningAmount = $Query->row_array();
                                if (!empty($UserWinningAmount)) {
                                    $Records[$key]['UserWinningAmount'] = $UserWinningAmount['UserWinningAmount'];
                                    $Records[$key]['SmartPoolWinning'] = $UserWinningAmount['SmartPoolWinning'];
                                    $Records[$key]['TotalPoints'] = $UserWinningAmount['TotalPoints'];
                                    $Records[$key]['UserRank'] = $UserWinningAmount['UserRank'];
                                    $Records[$key]['UserTeamName'] = $UserWinningAmount['UserTeamName'];
                                }
                            }
                        } else {
                            $Query = $this->db->query('SELECT JC.UserWinningAmount,JC.SmartPoolWinning,JC.TotalPoints,JC.UserRank,UT.UserTeamName FROM sports_contest_join JC,sports_users_teams UT WHERE UT.UserTeamID = JC.UserTeamID AND JC.ContestID = "' . $Record['ContestID'] . '" AND JC.UserID = "' . $Where['SessionUserID'] . '"');
                            $UserWinningAmount = $Query->row_array();
                            if (!empty($UserWinningAmount)) {
                                $Records[$key]['UserWinningAmount'] = $UserWinningAmount['UserWinningAmount'];
                                $Records[$key]['SmartPoolWinning'] = $UserWinningAmount['SmartPoolWinning'];
                                $Records[$key]['TotalPoints'] = $UserWinningAmount['TotalPoints'];
                                $Records[$key]['UserRank'] = $UserWinningAmount['UserRank'];
                                $Records[$key]['UserTeamName'] = $UserWinningAmount['UserTeamName'];
                            }
                        }
                    }
                }
                $Return['Data']['Records'] = $Records;
                // print_r($Return['Data']['Records']);die;
            } else {
                $Record = $Query->row_array();
                if (in_array('CreatedBy',  $Params) && $Where['Privacy'] == 'Yes') {
                    if (!empty($Record['UserID'])) {
                        $Record['CreatedBy'] =  "User ".$this->db->query('SELECT FirstName FROM tbl_users WHERE UserID = '.$Record['UserID'].' LIMIT 1')->row()->FirstName;
                    }else{
                        $Record['CreatedBy'] = "Admin";
                    }
                }
                $Record['Offer1'] = new stdClass();
                $Record['Offer2'] = new stdClass();
                /**-- Get Latest Offer --**/
                // AND OfferDateTime > CURRENT_TIMESTAMP()
                $LatestOfferForAll = $this->db->query('SELECT * FROM tbl_offers WHERE JSON_SEARCH(`ContestIDs`,"one", "'.$Record['ContestID'].'") IS NOT NULL ORDER BY ID DESC LIMIT 1')->row();
                $OfferOneApplied == '';
                $OfferTwoApplied == '';
                if (!empty($LatestOfferForAll) && $LatestOfferForAll->UserSelectionType == 'AllUsers' && $LatestOfferForAll->OfferType == 'Offer-1') {
                    /**Work By kamlesh**/ 
                    /*-Apply Offer 1 for ALl Users-*/
                    $OfferOneApplied == 'Offer-1'; 
                    $Record['Offer1'] = $LatestOfferForAll;
                    $Record['Offer1']->OfferPrize = ($Record['EntryFee'] - (int) floor(($Record['EntryFee'] * $LatestOfferForAll->OfferPercent) / 100));
                    $Record['Offer1']->CurrentDateTime = $CurrentDateTime;
                }

                if (!empty($LatestOfferForAll) && $LatestOfferForAll->UserSelectionType == 'AllUsers' && $LatestOfferForAll->OfferType == 'Offer-2') {
                    /*-Apply Offer 2 for ALl Users-*/ 
                    $OfferTwoApplied == 'Offer-2';
                    $Record['Offer2'] = $LatestOfferForAll;
                    $Record['Offer2']->OfferPrize = ($Record['EntryFee'] - (int) floor(($Record['EntryFee'] * $LatestOfferForAll->OfferPercent) / 100));
                    $Record['Offer2']->CurrentDateTime = $CurrentDateTime;
                }

                $OfferOneQuery = $this->db->query('SELECT * FROM tbl_offers WHERE JSON_SEARCH(`ContestIDs`,"one", "'.$Record['ContestID'].'") IS NOT NULL AND JSON_SEARCH(`UserIDs`,"one", "'.$Where['SessionUserID'].'") IS NOT NULL AND OfferType = "Offer-1" ORDER BY ID DESC LIMIT 1')->row();
                if(!empty($OfferOneQuery) && $OfferOneApplied == '')
                {
                    $Record['Offer1'] = $OfferOneQuery;
                    $Record['Offer1']->OfferPrize = ($Record['EntryFee'] - (int) floor(($Record['EntryFee'] * $OfferOneQuery->OfferPercent) / 100));
                    $Record['Offer1']->CurrentDateTime = $CurrentDateTime;
                }
                $OfferTwoQuery = $this->db->query('SELECT * FROM tbl_offers WHERE JSON_SEARCH(`ContestIDs`,"one", "'.$Record['ContestID'].'") IS NOT NULL AND JSON_SEARCH(`UserIDs`,"one", "'.$Where['SessionUserID'].'") IS NOT NULL AND OfferType = "Offer-2" ORDER BY ID DESC LIMIT 1')->row();
                if(!empty($OfferTwoQuery) && $OfferTwoApplied == '')
                {
                    $Record['Offer2'] = $OfferTwoQuery;
                    $Record['Offer2']->OfferPrize = ($Record['EntryFee'] - (int) floor(($Record['EntryFee'] * $OfferTwoQuery->OfferPercent) / 100));
                    $Record['Offer2']->CurrentDateTime = $CurrentDateTime;
                }
                $Record['CustomizeWinning'] = (!empty($Record['CustomizeWinning'])) ? json_decode($Record['CustomizeWinning'], TRUE) : array();
                $Record['MatchScoreDetails'] = (!empty($Record['MatchScoreDetails'])) ? json_decode($Record['MatchScoreDetails'], TRUE) : new stdClass();
                $TotalAmountReceived = $this->getTotalContestCollections($Record['ContestGUID']);
                $Record['TotalAmountReceived'] = ($TotalAmountReceived) ? $TotalAmountReceived : 0;
                $TotalWinningAmount = $this->getTotalWinningAmount($Record['ContestGUID']);
                $Record['TotalWinningAmount'] = ($TotalWinningAmount) ? $TotalWinningAmount : 0;
                $Record['ContestSize'] = ($Record['ContestSize'] == 0 ) ? 'Unlimited' : $Record['ContestSize'];
                if (in_array('IsJoined', $Params)) {
                    if ($Record['IsJoined'] == 'Yes') {
                        $UserTeamDetails = $this->getUserTeams('TotalPoints', array('ContestID' => $Where['ContestID'], 'UserID' => $Where['SessionUserID']), true, 0);
                        $Record['UserTeamDetails'] = ($UserTeamDetails) ? $UserTeamDetails['Data']['Records'] : array();
                        // $Record['UserTeamDetails'] = $UserTeamDetails['Data']['Records'];
                    } else {
                        $Record['UserTeamDetails'] = array();
                    }
                    unset($Record['ContestID']);
                }

                if (!empty($Where['MatchID'])) {
                    $Record['Statics'] = $this->db->query('SELECT (SELECT COUNT(*) AS `NormalContest` FROM `sports_contest` C, `tbl_entity` E WHERE C.ContestID = E.EntityID AND E.StatusID IN (1,2,5) AND C.MatchID = "' . $Where['MatchID'] . '" AND C.ContestType="Normal" AND C.ContestFormat="League" AND C.ContestSize != (SELECT COUNT(*) from sports_contest_join where sports_contest_join.ContestID = C.ContestID)
                                            )as NormalContest,
                            ( SELECT COUNT(*) AS `ReverseContest` FROM `sports_contest` C, `tbl_entity` E WHERE C.ContestID = E.EntityID AND E.StatusID IN(1,2,5) AND C.MatchID = "' . $Where['MatchID'] . '" AND C.ContestType="Reverse" AND C.ContestFormat="League" AND C.ContestSize != (SELECT COUNT(*) from sports_contest_join where sports_contest_join.ContestID = C.ContestID)
                            )as ReverseContest,(
                            SELECT COUNT(*) AS `JoinedContest` FROM `sports_contest_join` J, `sports_contest` C WHERE C.ContestID = J.ContestID AND J.UserID = "' . @$Where['SessionUserID'] . '" AND C.MatchID = "' . $Where['MatchID'] . '" 
                            )as JoinedContest,( 
                            SELECT COUNT(*) AS `TotalTeams` FROM `sports_users_teams`WHERE UserID = "' . @$Where['SessionUserID'] . '" AND MatchID = "' . $Where['MatchID'] . '"
                        ) as TotalTeams,(SELECT COUNT(*) AS `H2HContest` FROM `sports_contest` C, `tbl_entity` E, `sports_contest_join` CJ WHERE C.ContestID = E.EntityID AND E.StatusID IN (1,2,5) AND C.MatchID = "' . $Where['MatchID'] . '" AND C.ContestFormat="Head to Head" AND E.StatusID = 1 AND C.ContestID = CJ.ContestID AND C.ContestSize != (SELECT COUNT(*) from sports_contest_join where sports_contest_join.ContestID = C.ContestID )) as H2HContests')->row();
                }

                return $Record;
            }
        } else {
            if (!$multiRecords) {
                return array();
            }
        }
        if (!empty($Where['MatchID'])) {
            $Return['Data']['Statics'] = $this->db->query('SELECT (SELECT COUNT(*) AS `NormalContest` FROM `sports_contest` C, `tbl_entity` E WHERE C.ContestID = E.EntityID AND E.StatusID IN (1,2,5) AND C.MatchID = "' . $Where['MatchID'] . '" AND C.ContestType="Normal" AND C.ContestFormat="League" AND C.ContestSize != (SELECT COUNT(*) from sports_contest_join where sports_contest_join.ContestID = C.ContestID)
                                    )as NormalContest,
                    ( SELECT COUNT(*) AS `ReverseContest` FROM `sports_contest` C, `tbl_entity` E WHERE C.ContestID = E.EntityID AND E.StatusID IN(1,2,5) AND C.MatchID = "' . $Where['MatchID'] . '" AND C.ContestType="Reverse" AND C.ContestFormat="League" AND C.ContestSize != (SELECT COUNT(*) from sports_contest_join where sports_contest_join.ContestID = C.ContestID)
                    )as ReverseContest,(
                    SELECT COUNT(*) AS `JoinedContest` FROM `sports_contest_join` J, `sports_contest` C WHERE C.ContestID = J.ContestID AND J.UserID = "' . @$Where['SessionUserID'] . '" AND C.MatchID = "' . $Where['MatchID'] . '" 
                    )as JoinedContest,( 
                    SELECT COUNT(*) AS `TotalTeams` FROM `sports_users_teams`WHERE UserID = "' . @$Where['SessionUserID'] . '" AND MatchID = "' . $Where['MatchID'] . '"
                ) as TotalTeams,(SELECT COUNT(*) AS `H2HContest` FROM `sports_contest` C, `tbl_entity` E, `sports_contest_join` CJ WHERE C.ContestID = E.EntityID AND E.StatusID IN (1,2,5) AND C.MatchID = "' . $Where['MatchID'] . '" AND C.ContestFormat="Head to Head" AND E.StatusID = 1 AND C.ContestID = CJ.ContestID AND C.ContestSize != (SELECT COUNT(*) from sports_contest_join where sports_contest_join.ContestID = C.ContestID )) as H2HContests')->row();
        }

        $Return['Data']['Records'] = empty($Records) ? array() : $Records;
        return $Return;
    }

    function getTotalContestCollections($ContestGUID) {
        return $this->db->query('SELECT SUM(C.EntryFee) as TotalAmountReceived FROM sports_contest C join sports_contest_join J on C.ContestID = J.ContestID WHERE C.ContestGUID = "' . $ContestGUID . '"')->row()->TotalAmountReceived;
    }

    function getTotalWinningAmount($ContestGUID) {
        return $this->db->query('SELECT SUM(J.UserWinningAmount) as TotalWinningAmount FROM sports_contest C join sports_contest_join J on C.ContestID = J.ContestID WHERE C.ContestGUID = "' . $ContestGUID . '"')->row()->TotalWinningAmount;
    }

    /*
      Description: Join contest
     */

    function joinContest($Input = array(), $SessionUserID, $ContestID, $MatchID, $UserTeamID) {
        $this->db->trans_start();
        /* Add entry to join contest table . */
        /**-- Get Latest Offer --**/
        $LatestOffer = $this->db->query('SELECT * FROM tbl_offers WHERE JSON_SEARCH(`ContestIDs`,"one", "'.$ContestID.'") IS NOT NULL ORDER BY ID DESC LIMIT 1')->row();
        $OfferApplied = '';
        if (!empty($LatestOffer) && $LatestOffer->UserSelectionType == 'AllUsers' && $LatestOffer->OfferType == 'Offer-1') {
            /**Work By kamlesh**/ 
            /*-Apply Offer 1 for ALl Users-*/ 
            $CurrentDateTime    = strtotime(date('Y-m-d H:i:s')); // UTC
            $OfferDateTime      = strtotime($LatestOffer->OfferDateTime); // UTC 
            if($CurrentDateTime < $OfferDateTime && ($this->db->query('SELECT COUNT(*) AS `TotalJoined` FROM `sports_contest_join` WHERE `ContestID` =' . $ContestID . ' AND UserID = ' . $SessionUserID)->row()->TotalJoined < $LatestOffer->NoOfTeams))
            {
                $OfferApplied = 'Offer-1';
                $EntryFeeDeduct = (int) floor(($Input['EntryFee']*$LatestOffer->OfferPercent)/100);
                $Input['EntryFee'] = $Input['EntryFee'] - $EntryFeeDeduct;
            }
        }

        if (!empty($LatestOffer) && $LatestOffer->UserSelectionType == 'AllUsers' && $LatestOffer->OfferType == 'Offer-2' && $OfferApplied == '') {
            /*-Apply Offer 2 for ALl Users-*/ 
            if(($this->db->query('SELECT COUNT(*) AS `TotalJoined` FROM `sports_contest_join` WHERE `ContestID` =' . $ContestID . ' AND UserID = ' . $SessionUserID)->row()->TotalJoined) + 1 >= $LatestOffer->NoOfTeams)
            {
                $OfferApplied = 'Offer-2';
                $EntryFeeDeduct = (int) floor(($Input['EntryFee'] * $LatestOffer->OfferPercent) / 100);
                $Input['EntryFee'] = $Input['EntryFee'] - $EntryFeeDeduct;
            }
        }
        if ($OfferApplied == '') {
            /**Work By Preeti**/ 
            $Query = $this->db->query('SELECT * FROM tbl_offers WHERE JSON_SEARCH(`ContestIDs`,"one", "'.$ContestID.'") IS NOT NULL AND JSON_SEARCH(`UserIDs`,"one", "'.$SessionUserID.'") IS NOT NULL AND OfferType = "Offer-1" ORDER BY ID DESC LIMIT 1')->row();
            if(!empty($Query))
            {
                $CurrentDateTime = strtotime(date('Y-m-d H:i:s')); // UTC
                $OfferDateTime = strtotime($Query->OfferDateTime); // UTC 
                if($CurrentDateTime < $OfferDateTime && ($this->db->query('SELECT COUNT(*) AS `TotalJoined` FROM `sports_contest_join` WHERE `ContestID` =' . $ContestID . ' AND UserID = ' . $SessionUserID)->row()->TotalJoined < $Query->NoOfTeams))
                {
                    $EntryFeeDeduct = (int) floor(($Input['EntryFee'] * $Query->OfferPercent) / 100);
                    $Input['EntryFee'] = $Input['EntryFee'] - $EntryFeeDeduct;
                }else{
                    $Query = $this->db->query('SELECT * FROM tbl_offers WHERE JSON_SEARCH(`ContestIDs`,"one", "'.$ContestID.'") IS NOT NULL AND JSON_SEARCH(`UserIDs`,"one", "'.$SessionUserID.'") IS NOT NULL AND OfferType = "Offer-2" ORDER BY ID DESC LIMIT 1')->row();
                    if(!empty($Query))
                    {
                        if(($this->db->query('SELECT COUNT(*) AS `TotalJoined` FROM `sports_contest_join` WHERE `ContestID` =' . $ContestID . ' AND UserID = ' . $SessionUserID)->row()->TotalJoined) + 1 >= $Query->NoOfTeams)
                        {
                            $EntryFeeDeduct = (int) floor(($Input['EntryFee'] * $Query->OfferPercent) / 100);
                            $Input['EntryFee'] = $Input['EntryFee'] - $EntryFeeDeduct;
                        }
                    }
                }    
            }else{
                $Query = $this->db->query('SELECT * FROM tbl_offers WHERE JSON_SEARCH(`ContestIDs`,"one", "'.$ContestID.'") IS NOT NULL AND JSON_SEARCH(`UserIDs`,"one", "'.$SessionUserID.'") IS NOT NULL AND OfferType = "Offer-2" ORDER BY ID DESC LIMIT 1')->row();
                if(!empty($Query))
                {
                    if(($this->db->query('SELECT COUNT(*) AS `TotalJoined` FROM `sports_contest_join` WHERE `ContestID` =' . $ContestID . ' AND UserID = ' . $SessionUserID)->row()->TotalJoined) + 1 >= $Query->NoOfTeams)
                    {
                        $EntryFeeDeduct = (int) floor(($Input['EntryFee'] * $Query->OfferPercent) / 100);
                        $Input['EntryFee'] = $Input['EntryFee'] - $EntryFeeDeduct;
                    }
                }
            }
        }

        $UserData = $this->Users_model->getUsers('TotalCash,WalletAmount,WinningAmount,CashBonus,FirstName,Email', array('UserID' => $SessionUserID));
        $InsertData = array(
            "UserID" => $SessionUserID,
            "ContestID" => $ContestID,
            "MatchID" => $MatchID,
            "UserTeamID" => $UserTeamID,
            "IsSubscribe" => $Input['IsSubscribe'],
            "EntryDate" => date('Y-m-d H:i:s')
        );
        if (isset($Input['SmartPool'])) {
            $InsertData['SmartPool'] = $Input['SmartPool'];
        }
        $this->db->insert('sports_contest_join', $InsertData);

        /* Manage User Wallet */
        if (@$Input['IsPaid'] == 'Yes') {
            $ContestEntryRemainingFees = @$Input['EntryFee'];
            $CashBonusContribution = @$Input['CashBonusContribution'];
            $WalletAmountDeduction = 0;
            $WinningAmountDeduction = 0;
            $CashBonusDeduction = 0;
            if (!empty($CashBonusContribution) && @$UserData['CashBonus'] > 0) {
                $CashBonusContributionAmount = $ContestEntryRemainingFees * ($CashBonusContribution / 100);
                if (@$UserData['CashBonus'] >= $CashBonusContributionAmount) {
                    $CashBonusDeduction = $CashBonusContributionAmount;
                } else {
                    $CashBonusDeduction = @$UserData['CashBonus'];
                }
                $ContestEntryRemainingFees = $ContestEntryRemainingFees - $CashBonusDeduction;
            }
            if ($ContestEntryRemainingFees > 0 && @$UserData['WalletAmount'] > 0) {
                if (@$UserData['WalletAmount'] >= $ContestEntryRemainingFees) {
                    $WalletAmountDeduction = $ContestEntryRemainingFees;
                } else {
                    $WalletAmountDeduction = @$UserData['WalletAmount'];
                }
                $ContestEntryRemainingFees = $ContestEntryRemainingFees - $WalletAmountDeduction;
            }
            if ($ContestEntryRemainingFees > 0 && @$UserData['WinningAmount'] > 0) {
                if (@$UserData['WinningAmount'] >= $ContestEntryRemainingFees) {
                    $WinningAmountDeduction = $ContestEntryRemainingFees;
                } else {
                    $WinningAmountDeduction = @$UserData['WinningAmount'];
                }
                $ContestEntryRemainingFees = $ContestEntryRemainingFees - $WinningAmountDeduction;
            }
            $TransactionID = substr(hash('sha256', mt_rand() . microtime()), 0, 20);
            $InsertData = array(
                "Amount" => @$Input['EntryFee'],
                "WalletAmount" => $WalletAmountDeduction,
                "WinningAmount" => $WinningAmountDeduction,
                "CashBonus" => $CashBonusDeduction,
                "TransactionType" => 'Dr',
                "TransactionID" => $TransactionID,
                "EntityID" => $ContestID,
                "UserTeamID" => $UserTeamID,
                "Narration" => 'Join Contest',
                "EntryDate" => date("Y-m-d H:i:s")
            );
            $WalletID = $this->Users_model->addToWallet($InsertData, $SessionUserID, 5);
            $UpdateJoinAmount = array(
                'JoinWalletAmount' => $WalletAmountDeduction,
                'JoinWinningAmount' => $WinningAmountDeduction,
                'JoinCashBonus' => $CashBonusDeduction
            );
            /* Update Contest amount */
            $this->db->where('UserID', $SessionUserID);
            $this->db->where('ContestID', $ContestID);
            $this->db->where('UserTeamID', $UserTeamID);
            $this->db->limit(1);
            $this->db->update('sports_contest_join', $UpdateJoinAmount);

            if (!$WalletID) return FALSE;
            $ContestsData = $this->getContests('ContestSize,TotalJoined,IsAutoCreate,ContestFormat,IsConfirm,NoOfWinners,CustomizeWinning', array('ContestID' => $ContestID));
            /* To Check If Contest Is Auto Create (Yes) */
            if ($ContestsData['IsAutoCreate'] == 'Yes' && ($ContestsData['ContestSize'] - $ContestsData['TotalJoined']) == 0) {
                /* Get Contests Details */
                $ContestData = $this->db->query('SELECT * FROM sports_contest WHERE ContestID = ' . $ContestID . ' LIMIT 1')->result_array()[0];

                /* Create Contest */
                $Contest = array();
                $Contest['ContestName'] = $ContestData['ContestName'];
                $Contest['ContestFormat'] = $ContestData['ContestFormat'];
                $Contest['ContestType'] = $ContestData['ContestType'];
                $Contest['GameType'] = $ContestData['GameType'];
                $Contest['GameTypeLive'] = $ContestData['GameTypeLive'];
                $Contest['Privacy'] = $ContestData['Privacy'];
                $Contest['IsPaid'] = $ContestData['IsPaid'];
                $Contest['IsConfirm'] = $ContestData['IsConfirm'];
                $Contest['IsAutoCreate'] = $ContestData['IsAutoCreate'];
                $Contest['AdminPercent'] = $ContestData['AdminPercent'];
                $Contest['ContestContribution'] = $ContestData['ContestContribution'];
                $Contest['UnfilledWinningPercent'] = $ContestData['UnfilledWinningPercent'];
                $Contest['WinningRatio'] = $ContestData['WinningRatio'];
                $Contest['WinUpTo'] = $ContestData['WinUpTo'];
                $Contest['SmartPool'] = $ContestData['SmartPool'];
                $Contest['ShowJoinedContest'] = $ContestData['ShowJoinedContest'];
                $Contest['WinningAmount'] = $ContestData['WinningAmount'];
                $Contest['ContestSize'] = $ContestData['ContestSize'];
                $Contest['EntryFee'] = $ContestData['EntryFee'];
                $Contest['NoOfWinners'] = $ContestData['NoOfWinners'];
                $Contest['EntryType'] = $ContestData['EntryType'];
                $Contest['IsVirtualUserJoined'] = $ContestData['IsVirtualUserJoined'];
                $Contest['VirtualUserJoinedPercentage'] = $ContestData['VirtualUserJoinedPercentage'];
                $Contest['UserJoinLimit'] = $ContestData['UserJoinLimit'];
                $Contest['CashBonusContribution'] = $ContestData['CashBonusContribution'];
                $Contest['CustomizeWinning'] = $ContestData['CustomizeWinning'];
                $Contest['IsWinnerSocialFeed'] = $ContestData['IsWinnerSocialFeed'];
                if($ContestData['WinningType'] == "Paid Join Contest"){
                  $this->addContest($Contest, $ContestData['UserID'], $ContestData['MatchID'], $ContestData['SeriesID']);
                }
            }
            /* Update winning breakup  */
            $Input['TotalJoined'] = $ContestsData['TotalJoined'];
            if ($Input['UnfilledWinningPercent'] == 'GuranteedPool' && $Input['EntryFee'] > 0 && $Input['TotalJoined'] > 2) { // Variable Contests
                $WinningRatio = (!empty($Input['WinningRatio']) ? $Input['WinningRatio'] : 40);
                $NoOfWinners = (int) floor((($Input['TotalJoined'] * $WinningRatio) / 100));
                $TotalCollection = $Input['EntryFee'] * $NoOfWinners;
                $WinningAmount = $Input['WinUpTo'] * $TotalCollection;

                $NewCustomizeWinning[] = array(
                    'From' => 1,
                    'To' => $NoOfWinners,
                    'Percent' => 100,
                    'WinningAmount' => $WinningAmount / $NoOfWinners
                );
                /* Update Contest New Winning */
                if (!empty($NewCustomizeWinning)) {
                    $this->db->where('ContestID', $ContestID);
                    $this->db->limit(1);
                    $this->db->update('sports_contest', array('CustomizeWinning' => json_encode($NewCustomizeWinning), 'NoOfWinners' => $NoOfWinners, 'WinningAmount' => $WinningAmount));
                }
            }
            /* Update winning breakup for Unfill Confirm Contest */
            if($ContestsData['ContestFormat'] == 'League' && $ContestsData['IsConfirm'] == 'Yes' && $Input['EntryFee'] > 0 && $Input['TotalJoined'] > 2)
            {
                $TotalCollection = $Input['EntryFee'] * $Input['TotalJoined'];
                $AdminFeePerUser = $Input['EntryFee'] - ($Input['ContestWinningAmount'] / $Input['ContestSize']);
                $TotalCollection = $TotalCollection - ($Input['TotalJoined'] * $AdminFeePerUser); 
                $WinningPercent = ($ContestsData['NoOfWinners']*100) / $Input['ContestSize'];
                $NoOfWinners = (int) floor((($Input['TotalJoined'] * $WinningPercent) / 100));      
                $CustomizeWinning = $ContestsData['CustomizeWinning'];     
                $PercentSum = 0;  
                foreach ($CustomizeWinning as $WinningValue)
                {
                    $WinnersDiff = ($WinningValue['To'] - $WinningValue['From']) +1;
                    if($WinnersDiff > 1)
                    {
                        $Count = 0;
                        for($j = 0; $j < $WinnersDiff; $j++)
                        {
                            $TempCusTom[] = array(
                                'From' => $WinningValue['From'] + $Count,
                                'To' => $WinningValue['From'] + $Count,
                                'Percent' => floor($WinningValue['Percent'] / $WinnersDiff),
                                'WinningAmount' => $WinningValue['WinningAmount']
                            );
                            $Count++;
                        }
                       
                    }else{
                            $TempCusTom[] = array(
                                'From' => $WinningValue['From'],
                                'To' => $WinningValue['To'],
                                'Percent' => $WinningValue['Percent'],
                                'WinningAmount' => $WinningValue['WinningAmount']
    
                            );   
                    }
                }
                for($i = 0; $i < $NoOfWinners; $i++)
                {
                    $PercentSum += $TempCusTom[$i]['Percent'];
                }
                
                for($i = 0; $i < $NoOfWinners; $i++)
                {
                    $NewWinningPercent = floor(($TempCusTom[$i]['Percent'] * 100) / $PercentSum);

                    $NewCustomizeWinning[] = array(
                        'From' => $TempCusTom[$i]['From'],
                        'To' => $TempCusTom[$i]['To'],
                        'Percent' => $NewWinningPercent,
                        'WinningAmount' => floor(($TotalCollection * $NewWinningPercent) / 100)
                    );
                }

                if (!empty($NewCustomizeWinning)) {
                    $this->db->where('ContestID', $ContestID);
                    $this->db->limit(1);
                    $this->db->update('sports_contest', array('DynamicCustomizeWinning' => json_encode($NewCustomizeWinning), 'NewNoOfWinners' => $NoOfWinners, 'WinningPercent' => $WinningPercent, 'NewWinningAmount' => $TotalCollection));
                }
                 
            }
        }else{
            $ContestsData = $this->getContests('ContestSize,TotalJoined,IsAutoCreate', array('ContestID' => $ContestID));
            /* To Check If Contest Is Auto Create (Yes) */
            if ($ContestsData['IsAutoCreate'] == 'Yes' && ($ContestsData['ContestSize'] - $ContestsData['TotalJoined']) == 0) {
                /* Get Contests Details */
                $ContestData = $this->db->query('SELECT * FROM sports_contest WHERE ContestID = ' . $ContestID . ' LIMIT 1')->result_array()[0];

                /* Create Contest */
                $Contest = array();
                $Contest['ContestName'] = $ContestData['ContestName'];
                $Contest['ContestFormat'] = $ContestData['ContestFormat'];
                $Contest['ContestType'] = $ContestData['ContestType'];
                $Contest['GameType'] = $ContestData['GameType'];
                $Contest['GameTypeLive'] = $ContestData['GameTypeLive'];
                $Contest['Privacy'] = $ContestData['Privacy'];
                $Contest['IsPaid'] = $ContestData['IsPaid'];
                $Contest['IsConfirm'] = $ContestData['IsConfirm'];
                $Contest['IsAutoCreate'] = $ContestData['IsAutoCreate'];
                $Contest['AdminPercent'] = $ContestData['AdminPercent'];
                $Contest['ContestContribution'] = $ContestData['ContestContribution'];
                $Contest['UnfilledWinningPercent'] = $ContestData['UnfilledWinningPercent'];
                $Contest['WinningRatio'] = $ContestData['WinningRatio'];
                $Contest['WinUpTo'] = $ContestData['WinUpTo'];
                $Contest['SmartPool'] = $ContestData['SmartPool'];
                $Contest['ShowJoinedContest'] = $ContestData['ShowJoinedContest'];
                $Contest['WinningAmount'] = $ContestData['WinningAmount'];
                $Contest['ContestSize'] = $ContestData['ContestSize'];
                $Contest['EntryFee'] = $ContestData['EntryFee'];
                $Contest['NoOfWinners'] = $ContestData['NoOfWinners'];
                $Contest['EntryType'] = $ContestData['EntryType'];
                $Contest['IsVirtualUserJoined'] = $ContestData['IsVirtualUserJoined'];
                $Contest['VirtualUserJoinedPercentage'] = $ContestData['VirtualUserJoinedPercentage'];
                $Contest['UserJoinLimit'] = $ContestData['UserJoinLimit'];
                $Contest['CashBonusContribution'] = $ContestData['CashBonusContribution'];
                $Contest['CustomizeWinning'] = $ContestData['CustomizeWinning'];
                $Contest['IsWinnerSocialFeed'] = $ContestData['IsWinnerSocialFeed'];
                if($ContestData['WinningType'] == "Paid Join Contest"){
                  $this->addContest($Contest, $ContestData['UserID'], $ContestData['MatchID'], $ContestData['SeriesID']);  
                }
            }
        }

        $this->db->trans_complete();
        if ($this->db->trans_status() === FALSE) {
            return FALSE;
        }
        /* Send Invoice */
        // if ($SessionUserID == 12368) {
            // if (!empty($Input['EntryFee']) && !empty($Input['AdminPercent'])) {
            //     require_once getcwd() . '/vendor/autoload.php';
            //     $FileName = 'contest-invoice-' . $Input['ContestGUID'] .'-'.$SessionUserID. '.pdf';
            //     $AdminCommission = ($Input['EntryFee'] * $Input['ContestSize']) - $Input['ContestWinningAmount'];
            //     $AdminPerUsrComm = round($AdminCommission / $Input['ContestSize'], 3);
            //     $PlatformFee     = round(($AdminPerUsrComm * 100) / 118, 3); //platformFee
            //     $TotalTax        = round(($AdminPerUsrComm - $PlatformFee), 3); // 18% 
            //     $GST             = round($TotalTax / 2, 3); // 9% SGST , 9% CGST
            //     // $InvoiceTime     = date('His', mktime(date('h')+5,date('i')+30,date('s')));
            //     date_default_timezone_set('Asia/Calcutta');
            //     $InvoiceTime     = date('His');
            //     /* Create PDF HTML */
            //     $PDFHtml = '<!DOCTYPE html><html><head><meta charset="utf-8"><meta http-equiv="X-UA-Compatible" content="IE=edge"><title>'.SITE_NAME.' - Contest Invoice</title><meta name="description" content=""><meta name="viewport" content="width=device-width, initial-scale=1"></head><body><div class="pdfTbale" style="table-layout: fixed; max-width: 1024px; margin:auto;"><table border="0" width="100%" cellspacing="0" cellpadding="0"><tbody><tr><td><table width="100%" cellspacing="0" cellpadding="0"><tbody><tr><td><table width="100%" cellspacing="0" cellpadding="0"><tbody><tr><td height="30"></td></tr><tr><td align="left" width="33.%"><table width="100%" cellspacing="0" cellpadding="0" style="font-size:12px; line-height: 20px;"><tbody><tr><td align="left" style="font-weight:900;color: #28125c;">FIKTIONWORKS PVT. LTD</td></tr><tr><td align="left" style="font-weight:900;">GSTIN - 02AAECF1475B1ZA</td></tr><tr><td align="left" style="font-weight:900;">CIN : U92410HP2020PTC007962</td></tr><tr><td align="left" >C/O RAJINDER SINGH VILL AJNOLI P.O. KOTLA KALAN, UNA, HIMACHAL PRADESH-174303</td></tr><tr><td align="left" style="font-weight:900;">STATE - HIMACHAL PRADESH</td></tr><tr><td align="left" style="font-weight:900;">HSN Code - 998439</td></tr><tr><td align="left" style="font-weight:900;">Description Of Service : ONLINE CONTENT</td></tr></tbody></table></td><td width="33.3%" style="text-align: center;vertical-align: top;"><img src="'. SITE_HOST . ROOT_FOLDER .'assets/img/logo.png" width="200px"></td><td width="33.3%" align="right" style="font-size:12px; vertical-align: top; line-height: 22px;"> <span style="color:#000000de"><b style="font-weight:900;">INVOICE DATE - '.date('d/m/Y').' </b> <br><b style="font-weight:700;">INVOICE NO - '.$Input['TeamNameShortLocal'].'V'.$Input['TeamNameShortVisitor'].'/'.$InvoiceTime.'/'.$SessionUserID.'</b> <br>BILLED TO / CUSTOMER DETAIL - <br>'.$Input['Email'].' </span></td></tr></tbody></table></td></tr><tr><td height="20"></td></tr><tr><td style="font-size: 14px;color: #fe0000;" align="center">'.$Input['TeamNameLocal'].' vs '.$Input['TeamNameVisitor'].'</td></tr><tr><td><table width="100%" cellspacing="0" cellpadding="0"><tbody><tr style="line-height:45px"><td align="center" style="color:#fe0000;font-size:16px; font-weight: 700;"> TAX INVOICE</td></tr></tbody></table></td></tr><tr><td><table width="100%" cellspacing="0" border="1px" style="text-align: center;border-color: rgb(0 0 0 / 15%);"><tbody><tr bgcolor="#fe0000"><th style="font-size:13px; color: #fff; padding: 10px;"> <b>Contest ID</b></th><th style="font-size:13px; color: #fff; padding: 10px;"> <b>Entry Amount (₹)</b></th><th style="font-size:13px; color: #fff; padding: 10px;"> <b>Taxable Value (Platform Fee) (₹) *</b></th><th style="font-size:13px; color: #fff; padding: 10px;"> <b>SGST @9% (₹)</b></th><th style="font-size:13px; color: #fff; padding: 10px;"> <b>CGST @9% (₹)</b></th><th style="font-size:13px; color: #fff; padding: 10px;"> <b>Total (₹)</b></th></tr><tr cellpadding="2" style="color:#333333;line-height:24px"><td>'.$Input['ContestGUID'].'</td><td style="font-size:14px;padding: 10px;">'.$Input['EntryFee'].'</td><td style="font-size:14px;padding: 10px;">'.$PlatformFee.'</td><td style="font-size:14px;padding: 10px;">'.$GST.'</td><td style="font-size:14px;padding: 10px;">'.$GST.'</td><td style="font-size:14px;padding: 10px;">'.$AdminPerUsrComm.'</td></tr><tr cellpadding="2" style="color:#333333;line-height:24px"><td>Total</td><td style="font-size:14px;padding: 10px;">'.$Input['EntryFee'].'</td><td style="font-size:14px;padding: 10px;">'.$PlatformFee.'</td><td style="font-size:14px;padding: 10px;">'.$GST.'</td><td style="font-size:14px;padding: 10px;">'.$GST.'</td><td style="font-size:14px;padding: 10px;">'.$AdminPerUsrComm.'</td></tr></tbody></table></td></tr><tr><td height="30"></td></tr><tr><td><table width="100%" cellspacing="0" cellpadding="0"><tbody><tr><td width="100%" align="right"><table width="100%" cellspacing="0" cellpadding="0"><tbody><tr style="line-height:35px;color:#000;font-size:14px;font-weight: 600;"><td align="right"> <span style="width:90%">Taxable Amount :</span><span style="display: inline-block;">₹'.$PlatformFee.'</span></td></tr><tr style="line-height:35px;color:#000;font-size:14px;font-weight: 600;"><td align="right"> <span style="width:90%">Total Tax :</span> <span style="display: inline-block;">₹'.$TotalTax.'</span></td></tr><tr bgcolor="#fbfcfc" style="line-height:35px;color:#000;font-size:14px;font-weight: 600;"><td align="right"> <span style="width:90%">Invoice Total :</span> <span style="display: inline-block;">₹'.$AdminPerUsrComm.'</span></td></tr><tr style="line-height:35px;color:#000;font-size:8px"><td align="right"> <span>(Amount in Words: ₹ '.convert_number_to_words($AdminPerUsrComm).' only)</span></td></tr><tr></tbody></table></td></tr></tbody></table><table width="100%" cellspacing="0" cellpadding="0"><tbody><tr><td width="100%" align="right"><table width="100%" cellspacing="0" cellpadding="0"><tbody><tr style="line-height:35px;color:#000000de;font-size:18px"><td>For FIKTIONWORKS PRIVATE LIMITED</td></tr><tr><td height="10"></td></tr><tr></tbody></table></td></tr><tr><td height="30"></td></tr><tr><td width="100%"><table width="100%" cellspacing="0" cellpadding="0" style="font-size:10px;text-align: center;"><tbody></tr><tr><td>THIS IS COMPUTER GENERATED INVOICE NO SIGNATURE REQUIRED</td></tr></tbody></table></td></tr><tr><td height="30"></td></tr></tbody></table></td></tr></tbody></table></td></tr></tbody></table></div></body></html>';

            //     $MPDF = new \Mpdf\Mpdf();
            //     ini_set("pcre.backtrack_limit", "500000000");
            //     $PDFFilePath = getcwd() . '/uploads/Contests/Invoices/' . $FileName;
            //     $MPDF->WriteHTML($PDFHtml);
            //     $output = $MPDF->output($PDFFilePath, \Mpdf\Output\Destination::FILE);
            //     send_mail(array(
            //         'emailTo'       => $Input['Email'],
            //         'template_id'   => JOIN_CONTEST_TEXT_INVOICE,
            //         'Subject'       => 'Tax Invoice for Joined Contest',
            //         "Name"          => $Input['FirstName'],
            //         'Message'       => "This tax invoice is for informational purpose only. Download to see your tax invoice.",
            //         'PDF_File'      => $PDFFilePath,
            //         'FileName'      => $FileName,
            //     ));
            //     /***-- Invoice PDF send to Admin--***/ 
            //     send_mail(array(
            //         'emailTo'       => 'invoicesfiktionworks@gmail.com',
            //         'template_id'   => JOIN_CONTEST_TEXT_INVOICE,
            //         'Subject'       => 'Tax Invoice for Joined Contest',
            //         "Name"          => $Input['FirstName'],
            //         'Message'       => "This tax invoice is for informational purpose only. Download to see your tax invoice.",
            //         'PDF_File'      => $PDFFilePath,
            //         'FileName'      => $FileName,
            //     ));
            //     /* Add for Daily Invoice Details*/ 
            //     $InvoiceData = array(
            //         "InvoiceNumber" => $Input['TeamNameShortLocal'].'V'.$Input['TeamNameShortVisitor'].'/'.$InvoiceTime.'/'.$SessionUserID,
            //         "BilledTo"      => $Input['Email'],
            //         "ContestGUID"   => $Input['ContestGUID'],
            //         "TaxableValue"  => $PlatformFee,
            //         "SGST"          => $GST,
            //         "CGST"          => $GST,
            //         "InvoiceTotal"  => $AdminPerUsrComm,
            //         "CreatedAt"     => date('Y-m-d H:i:s')
            //     );
            //     $this->db->insert('tbl_daily_invoice_report', $InvoiceData);
            //     // echo $this->db->last_query();die;
            // }
        // }
        return $this->Users_model->getWalletDetails($SessionUserID);
    }

    /*
      Description: To get joined contest
     */

    function getJoinedContests($Field = '', $Where = array(), $multiRecords = FALSE, $PageNo = 1, $PageSize = 15) {

        $Params = array();
        if (!empty($Field)) {
            $Params = array_map('trim', explode(',', $Field));
            $Field = '';
            $FieldArray = array(
                'MatchID' => 'M.MatchID',
                'MatchGUID' => 'M.MatchGUID',
                'StatusID' => 'E.StatusID',
                'MatchIDLive' => 'M.MatchIDLive',
                'MatchTypeID' => 'M.MatchTypeID',
                'MatchNo' => 'M.MatchNo',
                'MatchLocation' => 'M.MatchLocation',
                'MatchStartDateTime' => 'CONVERT_TZ(M.MatchStartDateTime,"+00:00","' . DEFAULT_TIMEZONE . '") AS MatchStartDateTime',
                'MatchStartDateTimeUTC' => 'M.MatchStartDateTime MatchStartDateTimeUTC',
                'ContestID' => 'C.ContestID',
                'Privacy' => 'C.Privacy',
                'IsPaid' => 'C.IsPaid',
                'IsConfirm' => 'C.IsConfirm',
                'ShowJoinedContest' => 'C.ShowJoinedContest',
                'CashBonusContribution' => 'C.CashBonusContribution',
                'UserInvitationCode' => 'C.UserInvitationCode',
                'WinningAmount' => 'C.WinningAmount',
                'GameType' => 'C.GameType',
                'ContestSize' => 'C.ContestSize',
                'ContestFormat' => 'C.ContestFormat',
                'ContestType' => 'C.ContestType',
                'GameTimeLive' => 'C.GameTimeLive',
                'EntryFee' => 'C.EntryFee',
                'NoOfWinners' => 'C.NoOfWinners',
                'EntryType' => 'C.EntryType',
                'CustomizeWinning' => 'C.CustomizeWinning',
                'UserID' => 'JC.UserID',
                'UserTeamID' => 'JC.UserTeamID',
                'JoinInning' => 'JC.JoinInning',
                'EntryDate' => 'JC.EntryDate',
                'TotalPoints' => 'JC.TotalPoints',
                'UserWinningAmount' => 'JC.UserWinningAmount',
                'SeriesID' => 'M.SeriesID',
                'TeamNameLocal' => 'TL.TeamName AS TeamNameLocal',
                'TeamNameVisitor' => 'TV.TeamName AS TeamNameVisitor',
                'TeamNameShortLocal' => 'TL.TeamNameShort AS TeamNameShortLocal',
                'TeamNameShortVisitor' => 'TV.TeamNameShort AS TeamNameShortVisitor',
                'TeamFlagLocal' => 'CONCAT("' . BASE_URL . '","uploads/TeamFlag/",TL.TeamFlag) as TeamFlagLocal',
                'TeamFlagVisitor' => 'CONCAT("' . BASE_URL . '","uploads/TeamFlag/",TV.TeamFlag) as TeamFlagVisitor',
                'SeriesName' => 'S.SeriesName AS SeriesName',
                'TotalJoined' => '(SELECT COUNT(*) AS TotalJoined
                                                FROM sports_contest_join
                                                WHERE sports_contest_join.ContestID =  C.ContestID ) AS TotalJoined',
                'UserTotalJoinedInMatch' => '(SELECT COUNT(*)
                                                FROM sports_contest_join
                                                WHERE sports_contest_join.MatchID =  M.MatchID AND UserID= ' . $Where['SessionUserID'] . ') AS UserTotalJoinedInMatch',
                'UserRank' => 'JC.UserRank',
                'StatusID' => 'E.StatusID',
                'Status' => 'CASE E.StatusID
                when "1" then "Pending"
                when "2" then "Running"
                when "3" then "Cancelled"
                when "5" then "Completed"
                END as Status',
                'MatchStartDateTime' => 'DATE_FORMAT(CONVERT_TZ(M.MatchStartDateTime,"+00:00","' . DEFAULT_TIMEZONE . '"), "' . DATE_FORMAT . ' %h:%i %p") as MatchStartDateTime',
                'CurrentDateTime' => 'DATE_FORMAT(CONVERT_TZ(Now(),"+00:00","' . DEFAULT_TIMEZONE . '"), "' . DATE_FORMAT . ' ") as CurrentDateTime',
                'MatchDate' => 'DATE_FORMAT(CONVERT_TZ(M.MatchStartDateTime,"+00:00","' . DEFAULT_TIMEZONE . '"), "%Y-%m-%d") MatchDate',
                'MatchTime' => 'DATE_FORMAT(CONVERT_TZ(M.MatchStartDateTime,"+00:00","' . DEFAULT_TIMEZONE . '"), "%H:%i:%s") MatchTime',
            );
            if ($Params) {
                foreach ($Params as $Param) {
                    $Field .= (!empty($FieldArray[$Param]) ? ',' . $FieldArray[$Param] : '');
                }
            }
        }

        $this->db->select('C.ContestGUID,C.ContestName');
        if (!empty($Field)) $this->db->select($Field, FALSE);
        $this->db->from('tbl_entity E, sports_contest C, sports_matches M, sports_teams TL, sports_teams TV,sports_series S,sports_contest_join JC');
        $this->db->where("C.ContestID", "JC.ContestID", FALSE);
        $this->db->where("C.ContestID", "E.EntityID", FALSE);
        $this->db->where("M.MatchID", "C.MatchID", FALSE);
        $this->db->where("S.SeriesID", "C.SeriesID", FALSE);
        $this->db->where("M.TeamIDLocal", "TL.TeamID", FALSE);
        $this->db->where("M.TeamIDVisitor", "TV.TeamID", FALSE);
        if (!empty($Where['Keyword'])) {
            $Where['Keyword'] = $Where['Keyword'];
            $this->db->group_start();
            $this->db->like("C.ContestName", $Where['Keyword']);
            $this->db->or_like("C.GameType", $Where['Keyword']);
            $this->db->or_like("C.ContestSize", $Where['Keyword']);
            $this->db->or_like("C.EntryFee", $Where['Keyword']);
            $this->db->or_like("C.WinningAmount", $Where['Keyword']);
            $this->db->or_like("M.MatchLocation", $Where['Keyword']);
            $this->db->or_like("M.MatchNo", $Where['Keyword']);
            $this->db->group_end();
        }
        if (!empty($Where['ContestID'])) {
            $this->db->where("C.ContestID", $Where['ContestID']);
        }
        if (!empty($Where['SessionUserID'])) {
            $this->db->where("JC.UserID", $Where['SessionUserID']);
        }
        if (!empty($Where['UserTeamID'])) {
            $this->db->where("JC.UserTeamID", $Where['UserTeamID']);
        }
        if (!empty($Where['ImplodeUserTeamIDs'])) {
            $this->db->where_in("JC.UserTeamID", $Where['ImplodeUserTeamIDs']);
        }
        if (!empty($Where['Privacy'])) {
            $this->db->where("C.Privacy", $Where['Privacy']);
        }
        if (!empty($Where['GameType'])) {
            $this->db->where("C.GameType", $Where['GameType']);
        }
        if (!empty($Where['IsPaid'])) {
            $this->db->where("C.IsPaid", $Where['IsPaid']);
        }
        if (!empty($Where['LeagueType'])) {
            $this->db->where("C.LeagueType", $Where['LeagueType']);
        }
        if (!empty($Where['WinningAmount'])) {
            $this->db->where("C.WinningAmount >=", $Where['WinningAmount']);
        }
        if (!empty($Where['ContestSize'])) {
            $this->db->where("C.ContestSize", $Where['ContestSize']);
        }
        if (!empty($Where['EntryFee'])) {
            $this->db->where("C.EntryFee", $Where['EntryFee']);
        }
        if (!empty($Where['NoOfWinners'])) {
            $this->db->where("C.NoOfWinners", $Where['NoOfWinners']);
        }
        if (!empty($Where['EntryType'])) {
            $this->db->where("C.EntryType", $Where['EntryType']);
        }
        // print_r($Where['MatchID']);die;
        if (!empty($Where['MatchID'])) {
            $this->db->where("C.MatchID", $Where['MatchID']);
        }
        if (!empty($Where['StatusID'])) {
            $this->db->where("E.StatusID", $Where['StatusID']);
        }
        if (!empty($Where['StatusIDIn'])) {
            $this->db->where_in("E.StatusID", $Where['StatusIDIn']);
        }

        if (!empty($Where['OrderBy']) && !empty($Where['Sequence'])) {
            $this->db->order_by($Where['OrderBy'], $Where['Sequence']);
        }
        $this->db->order_by('M.MatchStartDateTime', 'ASC');
        //$this->db->group_by('C.ContestGUID');

        if (!empty($Where['getJoinedMatches']) && $Where['getJoinedMatches'] == 'Yes') {
            // $this->db->group_by('C.MatchID');
        }
        //$this->db->group_by('C.ContestID');
        /* Total records count only if want to get multiple records */
        if ($multiRecords) {
            $TempOBJ = clone $this->db;
            $TempQ = $TempOBJ->get();
            $Return['Data']['TotalRecords'] = $TempQ->num_rows();
            if ($PageNo != 0) {
                $this->db->limit($PageSize, paginationOffset($PageNo, $PageSize)); /* for pagination */
            }
        } else {
            $this->db->limit(1);
        }
        $Query = $this->db->get();
        //echo $this->db->last_query();exit;
        if ($Query->num_rows() > 0) {
            if ($multiRecords) {
                $Records = array();
                foreach ($Query->result_array() as $key => $Record) {
                    $Records[] = $Record;
                    $Records[$key]['CustomizeWinning'] = (!empty($Record['CustomizeWinning'])) ? json_decode($Record['CustomizeWinning'], TRUE) : array();
                }
                $Return['Data']['Records'] = $Records;
            } else {
                $Record = $Query->row_array();
                $Record['CustomizeWinning'] = (!empty($Record['CustomizeWinning'])) ? json_decode($Record['CustomizeWinning'], TRUE) : array();
                return $Record;
            }
        } else {
            $Return['Data']['Records'] = array();
        }

        if (!empty($Where['MatchID'])) {
            $Return['Data']['Statics'] = $this->db->query('SELECT (SELECT COUNT(*) AS `NormalContest` FROM `sports_contest` C, `tbl_entity` E, `sports_contest_join` CJ WHERE C.ContestID = E.EntityID AND E.StatusID IN (1,2,5) AND C.MatchID = "' . $Where['MatchID'] . '" AND C.ContestType="Normal" AND C.ContestFormat="League" AND E.StatusID = 1 AND C.ContestID = CJ.ContestID AND C.ContestSize != (SELECT COUNT(*) from sports_contest_join where sports_contest_join.ContestID = C.ContestID)
                                    )as NormalContest,
                    ( SELECT COUNT(*) AS `ReverseContest` FROM `sports_contest` C, `tbl_entity` E, `sports_contest_join` CJ WHERE C.ContestID = E.EntityID AND E.StatusID IN(1,2,5) AND C.MatchID = "' . $Where['MatchID'] . '" AND C.ContestType="Reverse" AND C.ContestFormat="League" AND E.StatusID = 1 AND C.ContestID = CJ.ContestID AND C.ContestSize != (SELECT COUNT(*) from sports_contest_join where sports_contest_join.ContestID = C.ContestID)
                    )as ReverseContest,(
                    SELECT COUNT(*) AS `JoinedContest` FROM `sports_contest_join` J, `sports_contest` C WHERE C.ContestID = J.ContestID AND J.UserID = "' . @$Where['SessionUserID'] . '" AND C.MatchID = "' . $Where['MatchID'] . '" 
                    )as JoinedContest,( 
                    SELECT COUNT(*) AS `TotalTeams` FROM `sports_users_teams`WHERE UserID = "' . @$Where['SessionUserID'] . '" AND MatchID = "' . $Where['MatchID'] . '"
                ) as TotalTeams,(SELECT COUNT(*) AS `H2HContest` FROM `sports_contest` C, `tbl_entity` E, `sports_contest_join` CJ WHERE C.ContestID = E.EntityID AND E.StatusID IN (1,2,5) AND C.MatchID = "' . $Where['MatchID'] . '" AND C.ContestFormat="Head to Head" AND E.StatusID = 1 AND C.ContestID = CJ.ContestID AND C.ContestSize != (SELECT COUNT(*) from sports_contest_join where sports_contest_join.ContestID = C.ContestID )) as H2HContests')->row();
        } else {
            $Return['Data']['Statics'] = $this->db->query('SELECT (
                SELECT COUNT(DISTINCT J.MatchID) AS `UpcomingJoinedContest` FROM `sports_contest_join` J, `tbl_entity` E , `sports_matches` M WHERE E.EntityID = J.ContestID AND J.MatchID=M.MatchID AND E.StatusID = 1 AND J.UserID = "' . @$Where['SessionUserID'] . '" 
                )as UpcomingJoinedContest,
                (
                SELECT COUNT(DISTINCT J.MatchID) AS `LiveJoinedContest` FROM `sports_contest_join` J, `tbl_entity` E , `sports_matches` M WHERE E.EntityID = J.ContestID AND J.MatchID=M.MatchID AND E.StatusID = 2 AND J.UserID = "' . @$Where['SessionUserID'] . '" 
                )as LiveJoinedContest,
                (
                SELECT COUNT(DISTINCT J.MatchID) AS `CompletedJoinedContest` FROM `sports_contest_join` J, `tbl_entity` E, `sports_matches` M WHERE E.EntityID = J.ContestID AND J.MatchID=M.MatchID AND E.StatusID = 5 AND J.UserID = "' . @$Where['SessionUserID'] . '"
            )as CompletedJoinedContest'
                    )->row();
        }

        return $Return;
    }

    /*
      Description: ADD user team
     */

    function addUserTeam($Input = array(), $SessionUserID, $MatchID, $StatusID = 2) {

        $this->db->trans_start();

        $EntityGUID = get_guid();

        /* Add user team to entity table and get EntityID. */
        $EntityID = $this->Entity_model->addEntity($EntityGUID, array("EntityTypeID" => 12, "UserID" => $SessionUserID, "StatusID" => $StatusID));

        $UserTeamCount = $this->db->query('SELECT count(T.UserTeamID) as UserTeamsCount,U.Username from `sports_users_teams` T join tbl_users U on U.UserID = T.UserID WHERE T.MatchID = "' . $MatchID . '" AND T.UserID = "' . $SessionUserID . '" AND UserTeamType="Normal" ')->row();
        /* Add user team to user team table . */
        $teamName = " Team " . ($UserTeamCount->UserTeamsCount + 1);
        $InsertData = array(
            "UserTeamID" => $EntityID,
            "UserTeamGUID" => $EntityGUID,
            "UserID" => $SessionUserID,
            // "IsVirtual" => @$Input['IsVirtual'],
            "UserTeamName" => $teamName,
            "UserTeamType" => @$Input['UserTeamType'],
            "MatchID" => $MatchID
        );
        if (isset($Input['IsVirtual'])) {
            $InsertData['IsVirtual'] = @$Input['IsVirtual'];
        }
        $this->db->insert('sports_users_teams', $InsertData);

        /* Add User Team Players */
        if (!empty($Input['UserTeamPlayers'])) {

            /* Get Players */
            $PlayersIdsData = array();
            $PlayersData = $this->Sports_model->getPlayers('PlayerID,MatchID', array('MatchID' => $MatchID), TRUE, 0);
            if ($PlayersData) {
                foreach ($PlayersData['Data']['Records'] as $PlayerValue) {
                    $PlayersIdsData[$PlayerValue['PlayerGUID']] = $PlayerValue['PlayerID'];
                }
            }

            /* Manage User Team Players */
            $Input['UserTeamPlayers'] = (!is_array($Input['UserTeamPlayers'])) ? json_decode($Input['UserTeamPlayers'], TRUE) : $Input['UserTeamPlayers'];
            $UserTeamPlayers = array();
            foreach ($Input['UserTeamPlayers'] as $Value) {
                if (isset($PlayersIdsData[$Value['PlayerGUID']])) {
                    $UserTeamPlayers[] = array(
                        'UserTeamID' => $EntityID,
                        'MatchID' => $MatchID,
                        'PlayerID' => $PlayersIdsData[$Value['PlayerGUID']],
                        'PlayerPosition' => $Value['PlayerPosition']
                    );
                }
            }
            if ($UserTeamPlayers) $this->db->insert_batch('sports_users_team_players', $UserTeamPlayers);
        }

        $this->db->trans_complete();
        if ($this->db->trans_status() === FALSE) {
            return FALSE;
        }

        return $EntityGUID;
    }

    /*
      Description: EDIT user team
     */

    function editUserTeam($Input = array(), $UserTeamID) {

        $this->db->trans_start();

        /* Delete Team Players */
        $this->db->delete('sports_users_team_players', array('UserTeamID' => $UserTeamID));

        /* Edit user team to user team table . */
        $this->db->where('UserTeamID', $UserTeamID);
        $this->db->limit(1);
        $this->db->update('sports_users_teams', array('UserTeamName' => $Input['UserTeamName'], 'UserTeamType' => $Input['UserTeamType']));

        /* Add User Team Players */
        if (!empty($Input['UserTeamPlayers'])) {

            /* Get Match ID */
            $MatchID = $this->db->query('SELECT MatchID FROM sports_users_teams WHERE UserTeamID = ' . $UserTeamID . ' LIMIT 1')->row()->MatchID;
            /* Get Players */
            $PlayersIdsData = array();
            $PlayersData = $this->Sports_model->getPlayers('PlayerID,MatchID', array('MatchID' => $MatchID), TRUE, 0);
            if ($PlayersData) {
                foreach ($PlayersData['Data']['Records'] as $PlayerValue) {
                    $PlayersIdsData[$PlayerValue['PlayerGUID']] = $PlayerValue['PlayerID'];
                }
            }

            /* Manage User Team Players */
            $Input['UserTeamPlayers'] = (!is_array($Input['UserTeamPlayers'])) ? json_decode($Input['UserTeamPlayers'], TRUE) : $Input['UserTeamPlayers'];
            $UserTeamPlayers = array();
            foreach ($Input['UserTeamPlayers'] as $Value) {
                if (isset($PlayersIdsData[$Value['PlayerGUID']])) {
                    $UserTeamPlayers[] = array(
                        'UserTeamID' => $UserTeamID,
                        'MatchID' => $MatchID,
                        'PlayerID' => $PlayersIdsData[$Value['PlayerGUID']],
                        'PlayerPosition' => $Value['PlayerPosition']
                    );
                }
            }
            if ($UserTeamPlayers) $this->db->insert_batch('sports_users_team_players', $UserTeamPlayers);
        }

        $this->db->trans_complete();
        if ($this->db->trans_status() === FALSE) {
            return FALSE;
        }

        return TRUE;
    }

    /*
      Description: virtual team player update
     */

    function virtualTeamUpdate($Input = array(), $UserTeamID) {

        $this->db->select('C.ContestID,JC.UserTeamID,C.MatchID');
        $this->db->from('sports_contest C,sports_contest_join JC');
        $this->db->where("JC.ContestID", "C.ContestID", FALSE);
        $this->db->where("C.IsVirtualUserJoined !=", "No");
        $this->db->where("JC.CopiedTeam", $UserTeamID);
        $this->db->limit(1);
        $Query = $this->db->get();
        if ($Query->num_rows() > 0) {
            $UserTeams = $Query->row_array();
            $VirtualUserTeamID = $UserTeams['UserTeamID'];
            if (!empty($VirtualUserTeamID)) {
                $MyTeamPlayers = $this->db->query("SELECT CONCAT('[',GROUP_CONCAT(JSON_OBJECT('MatchID',MatchID, 'PlayerID', PlayerID, 'PlayerPosition' ,PlayerPosition )), ']') AS userTeamPlayers FROM  sports_users_team_players WHERE  UserTeamID = '" . $UserTeamID . "'")->row_array();
                $UserTeamPlayer = json_decode($MyTeamPlayers['userTeamPlayers'], true);
                if (!empty($UserTeamPlayer)) {
                    $this->db->trans_start();
                    $InsertTeamPlayers = array();
                    foreach ($UserTeamPlayer as $RealPlayer) {
                        $InsertTeamPlayers[] = array(
                            'UserTeamID' => $VirtualUserTeamID,
                            'MatchID' => $RealPlayer['MatchID'],
                            'PlayerID' => $RealPlayer['PlayerID'],
                            'PlayerPosition' => $RealPlayer['PlayerPosition']
                        );
                    }
                    if (!empty($InsertTeamPlayers)) {
                        /* Delete Team Players */
                        $this->db->delete('sports_users_team_players', array('UserTeamID' => $VirtualUserTeamID));
                        $this->db->insert_batch('sports_users_team_players', $InsertTeamPlayers);
                    }
                    $this->db->trans_complete();
                    if ($this->db->trans_status() === FALSE) {
                        return FALSE;
                    }
                }
            }
        }
        return TRUE;
    }

    /*
      Description: To get user teams
     */

    function getUserTeams($Field = '', $Where = array(), $multiRecords = FALSE, $PageNo = 1, $PageSize = 15) {
        $Params = array();
        if (!empty($Field)) {
            $Params = array_map('trim', explode(',', $Field));
            $Field = '';
            $FieldArray = array(
                'UserTeamID' => 'UT.UserTeamID',
                'MatchID' => 'UT.MatchID',
                'MatchInning' => 'UT.MatchInning',
                'TotalPoints' => 'JC.TotalPoints',
                'Username' => '(SELECT Username FROM tbl_users WHERE UserID = UT.UserID) AS Username',
                'TotalJoinedContests' => '(SELECT COUNT(ContestID) FROM sports_contest_join WHERE UserTeamID = UT.UserTeamID) TotalJoinedContests',
                'IsTeamJoined' => '(SELECT IF( EXISTS(
                                    SELECT sports_contest_join.ContestID FROM sports_contest_join
                                    WHERE sports_contest_join.UserTeamID =  UT.UserTeamID AND sports_contest_join.ContestID = ' . @$Where['TeamsContestID'] . ' LIMIT 1), "Yes", "No")) AS IsTeamJoined'
            );
            if ($Params) {
                foreach ($Params as $Param) {
                    $Field .= (!empty($FieldArray[$Param]) ? ',' . $FieldArray[$Param] : '');
                }
            }
        }
        $this->db->select('UT.UserTeamGUID,UT.UserTeamName,UT.UserTeamType,UT.UserTeamID,UT.MatchID,UT.UserID');
        if (!empty($Field)) $this->db->select($Field, FALSE);
        if (in_array('TotalPoints', $Params)) {
            $this->db->from('tbl_entity E, sports_users_teams UT,sports_contest_join JC');
            $this->db->where("UT.UserTeamID", "E.EntityID", false);
            $this->db->where("JC.UserTeamID", "UT.UserTeamID", false);
        } else {
            $this->db->from('tbl_entity E, sports_users_teams UT');
            $this->db->where("UT.UserTeamID", "E.EntityID", false);
        }

        if (!empty($Where['Keyword'])) {
            $this->db->like("UT.UserTeamName", $Where['Keyword']);
        }
        if (!empty($Where['MatchID'])) {
            $this->db->where("UT.MatchID", $Where['MatchID']);
        }
        if (!empty($Where['UserTeamType']) && $Where['UserTeamType'] != 'All') {
            $this->db->where("UT.UserTeamType", $Where['UserTeamType']);
        }
        if (!empty($Where['UserTeamID'])) {
            $this->db->where("UT.UserTeamID", $Where['UserTeamID']);
        }
        if (!empty($Where['ContestID'])) {
            $this->db->where("JC.ContestID", $Where['ContestID']);
        }
        if (!empty($Where['MatchInning'])) {
            $this->db->where("UT.MatchInning", $Where['MatchInning']);
        }
        if (!empty($Where['UserID']) && empty($Where['UserTeamID'])) { // UserTeamID used to manage other user team details (On live score page)
            $this->db->where("UT.UserID", $Where['UserID']);
        }
        if (!empty($Where['StatusID'])) {
            $this->db->where("E.StatusID", $Where['StatusID']);
        }

        if (!empty($Where['OrderBy']) && !empty($Where['Sequence'])) {
            if ($Where['OrderBy']=='UserTeamName') {
                $this->db->order_by('UT.UserTeamID', 'ASC');
            }else{
                $this->db->order_by($Where['OrderBy'], $Where['Sequence']);
            }
        }else{
            $this->db->order_by('UT.UserTeamID', 'DESC');
        }
        if (!empty($Where['MatchID'])) {
            $Return['Data']['Statics'] = $this->db->query('SELECT (
                SELECT COUNT(*) AS `NormalContest` FROM `sports_contest` C, `tbl_entity` E WHERE C.ContestID = E.EntityID AND E.StatusID IN (1,2,5) AND C.MatchID = "' . $Where['MatchID'] . '" AND C.ContestType="Normal"
                )as NormalContest,
                (
                SELECT COUNT(*) AS `JoinedContest` FROM `sports_contest_join` J, `sports_contest` C WHERE C.ContestID = J.ContestID AND J.UserID = "' . @$Where['SessionUserID'] . '" AND C.MatchID = "' . $Where['MatchID'] . '"
                )as JoinedContest,
                ( 
                SELECT COUNT(*) AS `TotalTeams` FROM `sports_users_teams`WHERE UserID = "' . @$Where['SessionUserID'] . '" AND MatchID = "' . $Where['MatchID'] . '" 
            ) as TotalTeams'
                    )->row();
        }
        /* Total records count only if want to get multiple records */
        if ($multiRecords) {
            $TempOBJ = clone $this->db;
            $TempQ = $TempOBJ->get();
            $Return['Data']['TotalRecords'] = $TempQ->num_rows();
            if ($PageNo != 0) {
                $this->db->limit($PageSize, paginationOffset($PageNo, $PageSize)); /* for pagination */
            }
        } else {
            $this->db->limit(1);
        }
        $Query = $this->db->get();
        // echo $this->db->last_query(); die();
        if ($Query->num_rows() > 0) {
            if ($multiRecords) {
                $Return['Data']['Records'] = $Query->result_array();
                if (in_array('UserTeamPlayers', $Params)) {
                    foreach ($Return['Data']['Records'] as $key => $value) {
                        $Return['Data']['Records'][$key]['Playing11Announce'] = "No";
                        $UserTeamPlayers = $this->getUserTeamPlayers('PlayerID,IsPlaying,PlayerSalary,PlayerSalaryCredit,PlayerPosition,PlayerBattingStyle,PlayerSelectedPercent,PlayerBowlingStyle,PlayerName,PlayerPic,PlayerCountry,PlayerRole,Points,TeamGUID,TotalPoints,TotalPointCredits,PlayerSelectedPercent,TeamName,TeamNameShort', array('UserTeamID' => $value['UserTeamID']));
                        $Return['Data']['Records'][$key]['UserTeamPlayers'] = ($UserTeamPlayers) ? $UserTeamPlayers : array();
                        $IsPlaying = in_array("Yes", array_column($UserTeamPlayers, 'IsPlaying'));
                        if ($IsPlaying) {
                            $Return['Data']['Records'][$key]['Playing11Announce'] = "Yes";
                        }
                    }
                }
                if (@$Where['ValidateAdvanceSafe'] == "Yes") {
                    foreach ($Return['Data']['Records'] as $key => $value) {
                        $Return['Data']['Records'][$key]['IsEditUserTeam'] = "Yes";
                        $isvalidate = $this->ValidateAdvanceSafePlay($value['MatchID'], $value['UserID'], $value['UserTeamID']);
                        if (!$isvalidate) {
                            $Return['Data']['Records'][$key]['IsEditUserTeam'] = "No";
                        }
                    }
                }
                return $Return;
            } else {
                $Record = $Query->row_array();
                if (in_array('UserTeamPlayers', $Params)) {
                    $Record['Playing11Announce'] = "No";
                    $UserTeamPlayers = $this->getUserTeamPlayers('PlayerID,IsPlaying,PlayerSelectedPercent,PlayerSalary,PlayerSalaryCredit,TeamGUID,PlayerPosition,PlayerBattingStyle,PlayerBowlingStyle,PlayerName,PlayerPic,PlayerCountry,PlayerRole,Points,TotalPoints,TotalPointCredits,PlayerSelectedPercent,TeamName,TeamNameShort', array('UserTeamID' => $Where['UserTeamID']));
                    $Record['UserTeamPlayers'] = ($UserTeamPlayers) ? $UserTeamPlayers : array();
                    $IsPlaying = in_array("Yes", array_column($UserTeamPlayers, 'IsPlaying'));
                    if ($IsPlaying) {
                        $Record['Playing11Announce'] = "Yes";
                    }
                }
                return $Record;
            }
        }

        return FALSE;
    }

    /*
      Description: To get user team players
     */

    function getUserTeamPlayers($Field = '', $Where = array()) {
        $Params = array();
        if (!empty($Field)) {
            $Params = array_map('trim', explode(',', $Field));
            $Field = '';
            $FieldArray = array(
                'SeriesGUID' => 'S.SeriesGUID',
                'PlayerPosition' => 'UTP.PlayerPosition',
                'Points' => 'UTP.Points',
                'PlayerID' => 'UTP.PlayerID',
                'PlayerName' => 'P.PlayerName',
                'PlayerPic' => 'IF(P.PlayerPic IS NULL,CONCAT("' . BASE_URL . '","uploads/PlayerPic/","player.png"),CONCAT("' . BASE_URL . '","uploads/PlayerPic/",P.PlayerPic)) AS PlayerPic',
                'PlayerCountry' => 'P.PlayerCountry',
                'PlayerSalary' => 'TP.PlayerSalary',
                'PlayerBattingStyle' => 'P.PlayerBattingStyle',
                'PlayerBowlingStyle' => 'P.PlayerBowlingStyle',
                'PlayerRole' => 'TP.PlayerRole',
                'PointsData' => 'TP.PointsData',
                'IsPlaying' => 'TP.IsPlaying',
                'TeamGUID' => 'T.TeamGUID',
                'TeamNameShort' => 'T.TeamNameShort',
                'TeamName' => 'T.TeamName',
                'MatchType' => 'SM.MatchTypeName as MatchType',
                'TotalPointCredits' => '(SELECT IFNULL(SUM(`TotalPoints`),0) FROM `sports_team_players` WHERE `PlayerID` = TP.PlayerID AND `SeriesID` = TP.SeriesID) TotalPointCredits'
            );
            if ($Params) {
                foreach ($Params as $Param) {
                    $Field .= (!empty($FieldArray[$Param]) ? ',' . $FieldArray[$Param] : '');
                }
            }
        }
        $this->db->select('P.PlayerGUID,P.PlayerID,M.MatchGUID,M.MatchID,UTP.Points');
        if (!empty($Field)) $this->db->select($Field, FALSE);
        $this->db->from('sports_users_team_players UTP, sports_players P, sports_team_players TP,sports_teams T,sports_matches M,sports_set_match_types SM');
        if (array_keys_exist($Params, array('SeriesGUID'))) {
            $this->db->from('sports_series S');
            $this->db->where("S.SeriesID", "TP.SeriesID", FALSE);
        }
        $this->db->where("UTP.PlayerID", "P.PlayerID", FALSE);
        $this->db->where("UTP.PlayerID", "TP.PlayerID", FALSE);
        $this->db->where("UTP.MatchID", "TP.MatchID", FALSE);
        $this->db->where("T.TeamID", "TP.TeamID", FALSE);
        $this->db->where("M.MatchID", "TP.MatchID", FALSE);
        $this->db->where("M.MatchTypeID", "SM.MatchTypeID", FALSE);
        if (!empty($Where['Keyword'])) {
            $Where['Keyword'] = $Where['Keyword'];
            $this->db->like("P.PlayerName", $Where['Keyword']);
        }
        //if (!empty($Where['UserTeamID'])) {
         $this->db->where("UTP.UserTeamID", $Where['UserTeamID']);
        //}
        if (!empty($Where['MatchID'])) {
            $this->db->where("UTP.MatchID", $Where['MatchID']);
        }
        if (!empty($Where['PlayerID'])) {
            $this->db->where("UTP.PlayerID", $Where['PlayerID']);
        }
        if (!empty($Where['PlayerRole'])) {
            $this->db->where("TP.PlayerRole", $Where['PlayerRole']);
        }
        if (!empty($Where['PlayerPosition'])) {
            $this->db->where("UTP.PlayerPosition", $Where['PlayerPosition']);
        }

        if (!empty($Where['OrderBy']) && !empty($Where['Sequence'])) {
            $this->db->order_by($Where['OrderBy'], $Where['Sequence']);
        }
        $this->db->order_by('P.PlayerName', 'ASC');
        $Query = $this->db->get();
        if ($Query->num_rows() > 0) {
            $BestXIPlayers = array();
            if (in_array('TopPlayer', $Params)) {
                $BestPlayers = $this->Sports_model->getMatchBestPlayers(array('MatchID' => $Where['MatchID']));
                if (!empty($BestPlayers)) {
                    $BestXIPlayers = array_column($BestPlayers['Data']['Records'], 'PlayerGUID');
                }
            }
            $Records = array();
            $MatchStatus = 0;
            foreach ($Query->result_array() as $key => $Record) {
                if ($key == 0) {
                    /* Get Match Status */
                    $Query = $this->db->query('SELECT E.StatusID FROM `sports_matches` `M`,`tbl_entity` `E` WHERE M.`MatchGUID` = "' . $Record['MatchGUID'] . '" AND M.MatchID = E.EntityID LIMIT 1');
                    $MatchStatus = ($Query->num_rows() > 0) ? $Query->row()->StatusID : 0;
                }
                $Records[] = $Record;
                $Records[$key]['TopPlayer'] = (in_array($Record['PlayerGUID'], $BestXIPlayers)) ? 'Yes' : 'No';
                $Records[$key]['PointCredits'] = ($MatchStatus == 2 || $MatchStatus == 5) ? $Record['Points'] : $Record['PlayerSalary'];
                if (in_array('MyTeamPlayer', $Params)) {
                    $this->db->select('DISTINCT(SUTP.PlayerID)');
                    $this->db->where("JC.UserTeamID", "SUTP.UserTeamID", FALSE);
                    $this->db->where("SUT.UserTeamID", "SUTP.UserTeamID", FALSE);
                    $this->db->where('SUT.MatchID', $Record['MatchID']);
                    $this->db->where('SUT.UserID', $Where['UserID']);
                    $this->db->from('sports_contest_join JC,sports_users_teams SUT,sports_users_team_players SUTP');
                    $MyPlayers = $this->db->get()->result_array();
                    $MyPlayersIds = (!empty($MyPlayers)) ? array_column($MyPlayers, 'PlayerID') : array();
                    $Records[$key]['MyPlayer'] = (in_array($Record['PlayerID'], $MyPlayersIds)) ? 'Yes' : 'No';
                }

                if (in_array('PlayerSelectedPercent', $Params)) {
                    //echo $Where['MatchID'];exit;
                    $TotalTeams = $this->db->query('Select count(*) as TotalTeams from sports_users_teams WHERE MatchID="' . $Record['MatchID'] . '"')->row()->TotalTeams;

                    $this->db->select('count(SUTP.PlayerID) as TotalPlayer');
                    $this->db->where("SUTP.UserTeamID", "SUT.UserTeamID", FALSE);
                    $this->db->where("SUTP.PlayerID", $Record['PlayerID']);
                    $this->db->where("SUTP.MatchID", $Record['MatchID']);
                    $this->db->from('sports_users_teams SUT,sports_users_team_players SUTP');
                    $Players = $this->db->get()->row();
                    $Records[$key]['PlayerSelectedPercent'] = ($TotalTeams > 0 ) ? round((($Players->TotalPlayer * 100 ) / $TotalTeams), 2) > 100 ? 100 : round((($Players->TotalPlayer * 100 ) / $TotalTeams), 2) : 0;
                }
                $Records[$key]['PointsData'] = (@$Record['PointsData'] != '' ? json_decode($Record['PointsData']) : array());
            }
            return $Records;
        }
        return FALSE;
    }

    /*
      Description: To get contest winning users
     */

    function getContestWinningUsers($Field = '', $Where = array(), $multiRecords = FALSE, $PageNo = 1, $PageSize = 15) {
        $Params = array();
        if (!empty($Field)) {
            $Params = array_map('trim', explode(',', $Field));
            $Field = '';
            $FieldArray = array(
                'UserWinningAmount' => 'JC.UserWinningAmount',
                'TotalPoints' => 'JC.TotalPoints',
                'EntryFee' => 'C.EntryFee',
                'ContestSize' => 'C.ContestSize',
                'NoOfWinners' => 'C.NoOfWinners',
                'UserTeamName' => 'UT.UserTeamName',
                'FullName' => 'CONCAT_WS(" ",U.FirstName,U.LastName) FullName',
                'UserRank' => 'JC.UserRank'
            );
            if ($Params) {
                foreach ($Params as $Param) {
                    $Field .= (!empty($FieldArray[$Param]) ? ',' . $FieldArray[$Param] : '');
                }
            }
        }
        $this->db->select('C.ContestName');
        if (!empty($Field)) $this->db->select($Field, FALSE);
        $this->db->from('sports_contest_join JC, sports_contest C, sports_users_teams UT, tbl_users U');
        $this->db->where("C.ContestID", "JC.ContestID", FALSE);
        $this->db->where("JC.UserTeamID", "UT.UserTeamID", FALSE);
        $this->db->where("JC.UserID", "U.UserID", FALSE);
        $this->db->where("JC.UserWinningAmount >", 0);
        if (!empty($Where['Keyword'])) {
            $this->db->like("C.ContestName", $Where['ContestName']);
        }
        if (!empty($Where['ContestID'])) {
            $this->db->where("JC.ContestID", $Where['ContestID']);
        }
        if (!empty($Where['OrderBy']) && !empty($Where['Sequence'])) {
            $this->db->order_by($Where['OrderBy'], $Where['Sequence']);
        }
        $this->db->order_by('UserRank', 'ASC');

        /* Total records count only if want to get multiple records */
        if ($multiRecords) {
            $TempOBJ = clone $this->db;
            $TempQ = $TempOBJ->get();
            $Return['Data']['TotalRecords'] = $TempQ->num_rows();
            if ($PageNo != 0) {
                $this->db->limit($PageSize, paginationOffset($PageNo, $PageSize)); /* for pagination */
            }
        } else {
            $this->db->limit(1);
        }
        // $this->db->cache_on(); //Turn caching on
        $Query = $this->db->get();
        if ($Query->num_rows() > 0) {
            if ($multiRecords) {
                $Return['Data']['Records'] = $Query->result_array();
                return $Return;
            } else {
                return $Query->row_array();
            }
        }
        return FALSE;
    }

    /*
      Description: To get joined contest users
     */

    function getJoinedContestsUsers($Field = '', $Where = array(), $multiRecords = FALSE, $PageNo = 1, $PageSize = 15) {
        $Params = array();
        if (!empty($Field)) {
            $Params = array_map('trim', explode(',', $Field));
            $Field = '';
            $FieldArray = array(
                'TotalPoints' => 'JC.TotalPoints',
                'UserWinningAmount' => 'JC.UserWinningAmount',
                'FirstName' => 'U.FirstName',
                'MiddleName' => 'U.MiddleName',
                'LastName' => 'U.LastName',
                'Username' => 'U.Username',
                'SmartPool' => 'JC.SmartPool',
                'SmartPoolWinning' => 'JC.SmartPoolWinning',
                'Email' => 'U.Email',
                'PhoneNumber' => 'U.PhoneNumber',
                'UserID' => 'U.UserID',
                'UserRank' => 'JC.UserRank',
                'UserTeamName' => 'UT.UserTeamName',
                'UserTeamID' => 'UT.UserTeamID',
                'ProfilePic' => 'IF(U.ProfilePic IS NULL,CONCAT("' . BASE_URL . '","uploads/profile/picture/","default.jpg"),CONCAT("' . BASE_URL . '","uploads/profile/picture/",U.ProfilePic)) AS ProfilePic',
                'UserRank' => 'JC.UserRank',
                'IsSubscribe' => 'JC.IsSubscribe'
            );
            if ($Params) {
                foreach ($Params as $Param) {
                    $Field .= (!empty($FieldArray[$Param]) ? ',' . $FieldArray[$Param] : '');
                }
            }
        }
        $this->db->select('U.UserGUID,UT.UserTeamGUID,JC.IsSubscribe');
        if (!empty($Field)) $this->db->select($Field, FALSE);
        $this->db->from('sports_contest_join JC, tbl_users U, sports_users_teams UT');
        $this->db->where("JC.UserTeamID", "UT.UserTeamID", FALSE);
        $this->db->where("JC.UserID", "U.UserID", FALSE);
        if (!empty($Where['UserID'])) {
            //$this->db->where("JC.UserID", $Where['UserID']);
        }
        if (!empty($Where['NotInUser'])) {
            // $this->db->where("JC.UserID !=", $Where['NotInUser']);
        }

        if (!empty($Where['PointFilter']) && $Where['PointFilter'] == 'TotalPoints') {
            $this->db->where("JC.TotalPoints >", 0);
        }

        if (!empty($Where['OnlyWinners']) && $Where['OnlyWinners'] == 'Yes') {
            $this->db->where("JC.UserWinningAmount >", 0);
        }

        if (!empty($Where['ContestID'])) {
            $this->db->where("JC.ContestID", $Where['ContestID']);
        }
        if (!empty($Where['isMailSent'])) {
            $this->db->where("JC.isMailSent", $Where['isMailSent']);
        }
        if (!empty($Where['OrderBy']) && !empty($Where['Sequence'])) {
            $this->db->order_by($Where['OrderBy'], $Where['Sequence']);
        } else {
            if (!empty($Where['UserID'])) {
                $this->db->order_by('JC.UserID=' . $Where['UserID'] . ' DESC', null, FALSE);
            }

            $this->db->order_by('JC.UserRank', 'ASC');
        }
        /* Total records count only if want to get multiple records */
        if ($multiRecords) {
            $TempOBJ = clone $this->db;
            $TempQ = $TempOBJ->get();
            $Return['Data']['TotalRecords'] = $TempQ->num_rows();
            if ($PageNo != 0) {
                $this->db->limit($PageSize, paginationOffset($PageNo, $PageSize)); /* for pagination */
            }
        } else {
            $this->db->limit(1);
        }
        $Query = $this->db->get();
        // echo $this->db->last_query();die;
        if ($Query->num_rows() > 0) {
            if ($multiRecords) {
                $Return['Data']['Records'] = $Query->result_array();
                foreach ($Return['Data']['Records'] as $key => $record) {
                    $UserTeamPlayers = $this->getUserTeamPlayers('PointsData,PlayerSelectedPercent,TopPlayer,MyTeamPlayer,PlayerPosition,SeriesGUID,PlayerName,PlayerRole,PlayerBattingStyle,PlayerBowlingStyle,PlayerPic,TeamGUID,PlayerSalary,MatchType,PointCredits', array('UserTeamID' => $record['UserTeamID'], 'UserID' => $Where['UserID'], 'MatchID' => $Where['MatchID']));
                    $Return['Data']['Records'][$key]['UserTeamPlayers'] = ($UserTeamPlayers) ? $UserTeamPlayers : array();
                }
                return $Return;
            } else {
                $result = $Query->row_array();

                foreach ($result as $key => $record) {
                    $UserTeamPlayers = $this->getUserTeamPlayers('PointsData,PlayerSelectedPercent,TopPlayer,MyTeamPlayer,PlayerPosition,SeriesGUID,PlayerName,PlayerRole,PlayerPic,PlayerBattingStyle,PlayerBowlingStyle,TeamGUID,PlayerSalary,MatchType,PointCredits', array('UserTeamID' => $record['UserTeamGUID'], 'UserID' => $Where['UserID'], 'MatchID' => $Where['MatchID']));
                    $Return['Data']['Records'][$key]['UserTeamPlayers'] = ($UserTeamPlayers) ? $UserTeamPlayers : array();
                }
                return $Return;
            }
        }
        return FALSE;
    }

    function getJoinedContestsUsersCache($Field = '', $Where = array(), $multiRecords = FALSE, $PageNo = 1, $PageSize = 15) {
        $Params = array();
        if (!empty($Field)) {
            $Params = array_map('trim', explode(',', $Field));
            $Field = '';
            $FieldArray = array(
                'TotalPoints' => 'JC.TotalPoints',
                'UserWinningAmount' => 'JC.UserWinningAmount',
                'FirstName' => 'U.FirstName',
                'MiddleName' => 'U.MiddleName',
                'LastName' => 'U.LastName',
                'Username' => 'U.Username',
                'SmartPool' => 'JC.SmartPool',
                'SmartPoolWinning' => 'JC.SmartPoolWinning',
                'Email' => 'U.Email',
                'PhoneNumber' => 'U.PhoneNumber',
                'UserID' => 'U.UserID',
                'UserRank' => 'JC.UserRank',
                'UserTeamName' => 'UT.UserTeamName',
                'UserTeamID' => 'UT.UserTeamID',
                'ProfilePic' => 'IF(U.ProfilePic IS NULL,CONCAT("' . BASE_URL . '","uploads/profile/picture/","default.jpg"),CONCAT("' . BASE_URL . '","uploads/profile/picture/",U.ProfilePic)) AS ProfilePic',
                'UserRank' => 'JC.UserRank',
                'IsSubscribe' => 'JC.IsSubscribe',
                "UserTypeID" => "UserTypeID"
            );
            if ($Params) {
                foreach ($Params as $Param) {
                    $Field .= (!empty($FieldArray[$Param]) ? ',' . $FieldArray[$Param] : '');
                }
            }
        }
        $this->db->select('U.UserGUID,UT.UserTeamGUID,JC.IsSubscribe');
        if (!empty($Field)) $this->db->select($Field, FALSE);
        $this->db->from('sports_contest_join JC, tbl_users U, sports_users_teams UT');
        $this->db->where("JC.UserTeamID", "UT.UserTeamID", FALSE);
        $this->db->where("JC.UserID", "U.UserID", FALSE);
        if (!empty($Where['UserID'])) {
            //$this->db->where("JC.UserID", $Where['UserID']);
        }
        if (!empty($Where['NotInUser'])) {
            // $this->db->where("JC.UserID !=", $Where['NotInUser']);
        }

        if (!empty($Where['PointFilter']) && $Where['PointFilter'] == 'TotalPoints') {
            $this->db->where("JC.TotalPoints >", 0);
        }

        if (!empty($Where['OnlyWinners']) && $Where['OnlyWinners'] == 'Yes') {
            $this->db->where("JC.UserWinningAmount >", 0);
        }

        if (!empty($Where['ContestID'])) {
            $this->db->where("JC.ContestID", $Where['ContestID']);
        }
        if (!empty($Where['isMailSent'])) {
            $this->db->where("JC.isMailSent", $Where['isMailSent']);
        }
        if (!empty($Where['OrderBy']) && !empty($Where['Sequence'])) {
            $this->db->order_by($Where['OrderBy'], $Where['Sequence']);
        } else {
            if (!empty($Where['UserID'])) {
                $this->db->order_by('JC.UserID=' . $Where['UserID'] . ' DESC', null, FALSE);
            }

            $this->db->order_by('JC.UserRank', 'ASC');
        }
        /* Total records count only if want to get multiple records */
        if ($multiRecords) {
            $TempOBJ = clone $this->db;
            $TempQ = $TempOBJ->get();
            $Return['Data']['TotalRecords'] = $TempQ->num_rows();
            if ($PageNo != 0) {
                $this->db->limit($PageSize, paginationOffset($PageNo, $PageSize)); /* for pagination */
            }
        } else {
            $this->db->limit(1);
        }
        $Query = $this->db->get();
        if ($Query->num_rows() > 0) {
            if ($multiRecords) {
                $Return['Data']['Records'] = $Query->result_array();
                return $Return;
            } else {
                $result = $Query->row_array();
                foreach ($result as $key => $record) {
                    $Return['Data']['Records'][$key]['UserTeamPlayers'] = array();
                }
                return $Return;
            }
        }
        return FALSE;
    }

    /*
      Description: To Cancel Contest
     */

    function cancelContest($Input = array(), $SessionUserID, $ContestID) {

        /* Update Contest Status */
        $this->db->where('EntityID', $ContestID);
        $this->db->limit(1);
        $this->db->update('tbl_entity', array('ModifiedDate' => date('Y-m-d H:i:s'), 'StatusID' => 3));

        $this->db->where('ContestID', $ContestID);
        $this->db->limit(1);
        $this->db->update('sports_contest', array('IsRefund' => "No", "CancelledBy" => "Admin"));
    }

    /*
      Description: To Download Contest Teams
     */

    function downloadTeams($Input = array()) {

        error_reporting(1);
        /* Teams File Name */
        $FileName = 'contest-teams-' . $Input['ContestGUID'] . '.pdf';
        if (file_exists(getcwd() . '/uploads/Contests/' . $FileName)) {
            return array('TeamsPdfFileURL' => BASE_URL . 'uploads/Contests/' . $FileName);
        } else {

            /* Create PDF file using MPDF Library */
            ob_start();
            ini_set('memory_limit', '-1');
            ini_set('max_execution_time', 300);
            require_once getcwd() . '/vendor/autoload.php';

            /* Get Matches Details */
            $ContestsData = $this->getContests('TeamNameLocal,TeamNameVisitor,EntryFee,ContestSize,UserInvitationCode', array('ContestID' => $Input['ContestID']));


            /* Get Contest User Teams */
            $UserTeams = $this->getUserTeams('Username,TotalPoints,UserTeamPlayers', array('ContestID' => $Input['ContestID']), TRUE, 0);


            /* Player Positions */
            $PlayerPositions = array('Captain' => '(C)', 'ViceCaptain' => '(VC)', 'Player' => '');

            /* Create PDF HTML */
            $PDFHtml = '<html lang="en" data-ng-app="fxi"><body style ="font-family: Montserrat, sans-serif;">';
            $PDFHtml .= '<div style="width:100%; max-width:1500px;">';
            $PDFHtml .= '<table style="background:#202020; width:100%;" width="100%" cellpadding="20"  cellspacing="0">';
            $PDFHtml .= '<tr>';
            $PDFHtml .= '<td style="padding:10px;">';
            $PDFHtml .= '<span><img src="' . SITE_HOST . ROOT_FOLDER . 'api/asset/img/emailer/logo.png"  width="110"></span>';
            $PDFHtml .= '</td>';
            $PDFHtml .= '<td style="padding:10px 0;font-size:15px; color:#fff;">';
            $PDFHtml .= $ContestsData['TeamNameLocal'] . ' V/S ' . $ContestsData['TeamNameVisitor'];
            $PDFHtml .= '</td>';
            $PDFHtml .= '<td style="padding:10px 0; font-size:15px; color:#fff;">';
            $PDFHtml .= 'Entry Fee: ' . DEFAULT_CURRENCY . $ContestsData['EntryFee'];
            $PDFHtml .= '</td>';
            $PDFHtml .= '<td style="padding:10px 0; font-size:15px; color:#fff;">';
            $PDFHtml .= 'Contest Size: ' . $ContestsData['ContestSize'];
            $PDFHtml .= '</td>';
            $PDFHtml .= '<td style="padding:10px 0; font-size:15px; color:#fff;">';
            $PDFHtml .= 'Invite Code: ' . $ContestsData['UserInvitationCode'];
            $PDFHtml .= '</td>';
            $PDFHtml .= '</tr>';
            $PDFHtml .= '</table>';
            $PDFHtml .= '<table style="width:100%; border:1px solid #ccc" cellpadding="5"  cellspacing="0">';
            $PDFHtml .= '<thead>';
            $PDFHtml .= '<tr>';
            $PDFHtml .= '<th style="font-size:13px; font-weight:600;border:1px solid #ccc; text-align:center;">User Team Name</th>';
            for ($I = 1; $I <= 11; $I++) {
                $PDFHtml .= '<th style="font-size:13px; font-weight:600;border:1px solid #ccc; text-align:center;">Player' . ' ' . $I . '</th>';
            }
            $PDFHtml .= '</tr>';
            $PDFHtml .= '</thead>';
            $PDFHtml .= '<tbody>';
            foreach ($UserTeams['Data']['Records'] as $TeamValue) {
                $PDFHtml .= '<tr>';
                $PDFHtml .= '<td style="font-size:13px; font-weight:600;border:1px solid #ccc; text-align:center;">' . $TeamValue['Username'] . '</td>';
                foreach ($TeamValue['UserTeamPlayers'] as $PlayerValue) {
                    $PDFHtml .= '<td style="font-size:13px; font-weight:600;border:1px solid #ccc; text-align:center;">' . $PlayerValue['PlayerName'] . ' ' . $PlayerPositions[$PlayerValue['PlayerPosition']] . '</td>';
                }
                $PDFHtml .= '</tr>';
            }
            $PDFHtml .= '</tbody>';
            $PDFHtml .= '</table>';
            $PDFHtml .= '</div></body></html>';

            /* MPDF Object */
            // $MPDF = new mPDF();
           
            $MPDF = new \Mpdf\Mpdf();

            ini_set("pcre.backtrack_limit", "500000000");
            $PDFFilePath = getcwd() . '/uploads/Contests/' . $FileName;
            $MPDF->WriteHTML($PDFHtml);
            $output = $MPDF->output($PDFFilePath, \Mpdf\Output\Destination::FILE);
            // $output = $MPDF->output($PDFFilePath, 'F');
            return array('TeamsPdfFileURL' => BASE_URL . 'uploads/Contests/' . $FileName);
        }
    }

    public function getWinningBreakup($Field = '', $Input = array(), $multiRecords = FALSE, $PageNo = 1, $PageSize = 15) {
        $dataArr = array();
        $EntryFee = $Input['EntryFee'];
        $WinningAmount = $Input['WinningAmount'];
        $MatchID = $Input['MatchID'];
        $UserID = $Input['UserID'];
        $ContestSize = $Input['ContestSize'];

        $IsMultiEntry = $Input['EntryType'];

        $TotalFee = (abs($WinningAmount) * 20) / 100;

        if ($Input['IsPaid'] == 'Yes') {
            $MatchID = $Input['MatchID'];
            $UserID = $Input['UserID'];
            $WinningAmount = $Input['WinningAmount'];

            if ($ContestSize > 0 && $ContestSize < 11) {
                $result = array();
                $data = [];
                if ($ContestSize > 5) {
                    $ContestSize = 5;
                }
                if ($ContestSize == 5) {

                    $result5[] = array(
                        'Rank' => "1",
                        'From' => "1",
                        'To' => "1",
                        'Percent' => "40",
                        'WinningAmount' => (string) (($WinningAmount * 40) / 100));

                    $result5[] = array(
                        'Rank' => "2",
                        'From' => "2",
                        'To' => "2",
                        'Percent' => "25",
                        'WinningAmount' => (string) (($WinningAmount * 25) / 100));

                    $result5[] = array(
                        'Rank' => "3",
                        'From' => '3',
                        'To' => '3',
                        'Percent' => '15',
                        'WinningAmount' => (string) (($WinningAmount * 15) / 100));
                    $result5[] = array(
                        'Rank' => "4",
                        'From' => '4',
                        'To' => '4',
                        'Percent' => '12.5',
                        'WinningAmount' => (string) (($WinningAmount * 12.5) / 100));
                    $result5[] = array(
                        'Rank' => "5",
                        'From' => '5',
                        'To' => '5',
                        'Percent' => '7.5',
                        'WinningAmount' => (string) (($WinningAmount * 7.5) / 100));


                    $data[] = array('NoOfWinners' => $ContestSize - 0, 'Winners' => $result5);
                    $ContestSize--;
                }

                if ($ContestSize == 4) {

                    $result4[] = array(
                        'Rank' => "1",
                        'From' => '1',
                        'To' => '1',
                        'Percent' => '40',
                        'WinningAmount' => (string) (($WinningAmount * 40) / 100));

                    $result4[] = array(
                        'Rank' => "2",
                        'From' => '2',
                        'To' => '2',
                        'Percent' => '30',
                        'WinningAmount' => (string) (($WinningAmount * 30) / 100));

                    $result4[] = array(
                        'Rank' => "3",
                        'From' => '3',
                        'To' => '3',
                        'Percent' => '20',
                        'WinningAmount' => (string) (($WinningAmount * 20) / 100));
                    $result4[] = array(
                        'Rank' => "4",
                        'From' => '4',
                        'To' => '4',
                        'Percent' => '10',
                        'WinningAmount' => (string) (($WinningAmount * 10) / 100));


                    $data[] = array('NoOfWinners' => $ContestSize - 0, 'Winners' => $result4);
                    $ContestSize--;
                }

                if ($ContestSize == 3) {

                    $result[] = array(
                        'Rank' => "1",
                        'From' => '1',
                        'To' => '1',
                        'Percent' => '50',
                        'WinningAmount' => (string) (($WinningAmount * 50) / 100));

                    $result[] = array(
                        'Rank' => "2",
                        'From' => '2',
                        'To' => '2',
                        'Percent' => '30',
                        'WinningAmount' => (string) (($WinningAmount * 30) / 100));

                    $result[] = array(
                        'Rank' => "3",
                        'From' => '3',
                        'To' => '3',
                        'Percent' => '20',
                        'WinningAmount' => (string) (($WinningAmount * 20) / 100));

                    $result1 = array();
                    $result1[] = array(
                        'Rank' => "1",
                        'From' => '1',
                        'To' => '1',
                        'Percent' => '70',
                        'WinningAmount' => (string) (($WinningAmount * 70) / 100));

                    $result1[] = array(
                        'Rank' => "2",
                        'From' => '2',
                        'To' => '2',
                        'Percent' => '30',
                        'WinningAmount' => (string) (($WinningAmount * 30) / 100));

                    $result2 = array();
                    $result2[] = array(
                        'Rank' => "1",
                        'From' => '1',
                        'To' => '1',
                        'Percent' => '100',
                        'WinningAmount' => (string) (($WinningAmount * 100) / 100));

                    $data[] = array('NoOfWinners' => $ContestSize - 0, 'Winners' => $result);
                    $data[] = array('NoOfWinners' => $ContestSize - 1, 'Winners' => $result1);
                    $data[] = array('NoOfWinners' => $ContestSize - 2, 'Winners' => $result2);
                }

                if ($ContestSize == 2) {

                    $result[] = array(
                        'Rank' => "1",
                        'From' => '1',
                        'To' => '1',
                        'Percent' => '100',
                        'WinningAmount' => (string) (($WinningAmount * 100) / 100));


                    $data[] = array('NoOfWinners' => $ContestSize - 1, 'Winners' => $result);
                }
                $Return['Data'] = $data;
            }


            if ($ContestSize > 10 && $ContestSize < 17) {

                $result = array();
                $data = [];
                if ($ContestSize > 10) {
                    $ContestSize = 7;
                }
                if ($ContestSize == 7) {

                    $result5[] = array(
                        'Rank' => "1",
                        'From' => '1',
                        'To' => '1',
                        'Percent' => '25',
                        'WinningAmount' => (string) (($WinningAmount * 25) / 100));

                    $result5[] = array(
                        'Rank' => '2',
                        'From' => '2',
                        'To' => '2',
                        'Percent' => '20',
                        'WinningAmount' => (string) (($WinningAmount * 20) / 100));

                    $result5[] = array(
                        'Rank' => "3-4",
                        'From' => '3',
                        'To' => '4',
                        'Percent' => '15',
                        'WinningAmount' => (string) (($WinningAmount * 15) / 100));
                    $result5[] = array(
                        'Rank' => "5",
                        'From' => '5',
                        'To' => '5',
                        'Percent' => '12.5',
                        'WinningAmount' => (string) (($WinningAmount * 12.5) / 100));
                    $result5[] = array(
                        'Rank' => '6',
                        'From' => '6',
                        'To' => '6',
                        'Percent' => '7.5',
                        'WinningAmount' => (string) (($WinningAmount * 7.5) / 100));
                    $result5[] = array(
                        'Rank' => "7",
                        'From' => '7',
                        'To' => '7',
                        'Percent' => '5',
                        'WinningAmount' => (string) (($WinningAmount * 5) / 100));


                    $data[] = array('NoOfWinners' => $ContestSize - 0, 'Winners' => $result5);
                    $ContestSize--;
                }

                if ($ContestSize == 6) {

                    $result4[] = array(
                        'Rank' => "1",
                        'From' => '1',
                        'To' => '1',
                        'Percent' => '30',
                        'WinningAmount' => (string) (($WinningAmount * 30) / 100));

                    $result4[] = array(
                        'Rank' => "2",
                        'From' => '2',
                        'To' => '2',
                        'Percent' => '25',
                        'WinningAmount' => (string) (($WinningAmount * 25) / 100));

                    $result4[] = array(
                        'Rank' => "3",
                        'From' => '3',
                        'To' => '3',
                        'Percent' => '20',
                        'WinningAmount' => (string) (($WinningAmount * 20) / 100));
                    $result4[] = array(
                        'Rank' => "4",
                        'From' => '4',
                        'To' => '4',
                        'Percent' => '12.5',
                        'WinningAmount' => (string) (($WinningAmount * 12.5) / 100));
                    $result4[] = array(
                        'Rank' => "5",
                        'From' => '5',
                        'To' => '5',
                        'Percent' => '7.5',
                        'WinningAmount' => (string) (($WinningAmount * 7.5) / 100));
                    $result4[] = array(
                        'Rank' => "6",
                        'From' => '6',
                        'To' => '6',
                        'Percent' => '5',
                        'WinningAmount' => (string) (($WinningAmount * 5) / 100));


                    $data[] = array('NoOfWinners' => $ContestSize - 0, 'Winners' => $result4);
                    $ContestSize--;
                }

                if ($ContestSize == 5) {

                    $result[] = array(
                        'Rank' => "1",
                        'From' => '1',
                        'To' => '1',
                        'Percent' => '40',
                        'WinningAmount' => (string) (($WinningAmount * 40) / 100));

                    $result[] = array(
                        'Rank' => "2",
                        'From' => '2',
                        'To' => '2',
                        'Percent' => '25',
                        'WinningAmount' => (string) (($WinningAmount * 25) / 100));

                    $result[] = array(
                        'Rank' => "3",
                        'From' => '3',
                        'To' => '3',
                        'Percent' => '15',
                        'WinningAmount' => (string) (($WinningAmount * 15) / 100));

                    $result[] = array(
                        'Rank' => "4",
                        'From' => 4,
                        'To' => 4,
                        'Percent' => 12.5,
                        'WinningAmount' => ($WinningAmount * 12.5) / 100);

                    $result[] = array(
                        'Rank' => "5",
                        'From' => '5',
                        'To' => '5',
                        'Percent' => '7.5',
                        'WinningAmount' => (string) (($WinningAmount * 7.5) / 100));



                    $data[] = array('NoOfWinners' => $ContestSize - 0, 'Winners' => $result);
                }

                $Return['Data'] = $data;
            }


            if ($ContestSize > 16 && $ContestSize < 21) {

                $result = array();
                $data = [];
                if ($ContestSize > 16) {
                    $ContestSize = 10;
                }
                if ($ContestSize == 10) {

                    $result5[] = array(
                        'Rank' => "1",
                        'From' => '1',
                        'To' => '1',
                        'Percent' => '25',
                        'WinningAmount' => (string) (($WinningAmount * 25) / 100));

                    $result5[] = array(
                        'Rank' => "2",
                        'From' => '2',
                        'To' => '2',
                        'Percent' => '20',
                        'WinningAmount' => (string) (($WinningAmount * 20) / 100));

                    $result5[] = array(
                        'Rank' => "3",
                        'From' => '3',
                        'To' => '3',
                        'Percent' => '15',
                        'WinningAmount' => (string) (($WinningAmount * 15) / 100));
                    $result5[] = array(
                        'Rank' => "4",
                        'From' => '4',
                        'To' => '4',
                        'Percent' => '10',
                        'WinningAmount' => (string) (($WinningAmount * 10) / 100));
                    $result5[] = array(
                        'Rank' => "5-10",
                        'From' => '5',
                        'To' => '10',
                        'Percent' => '5',
                        'WinningAmount' => (string) (($WinningAmount * 5) / 100));


                    $data[] = array('NoOfWinners' => $ContestSize - 0, 'Winners' => $result5);
                    $ContestSize = $ContestSize - 3;
                }

                if ($ContestSize == 7) {

                    $result4[] = array(
                        'Rank' => "1",
                        'From' => '1',
                        'To' => '1',
                        'Percent' => '25',
                        'WinningAmount' => (string) (($WinningAmount * 25) / 100));

                    $result4[] = array(
                        'Rank' => "2",
                        'From' => '2',
                        'To' => '2',
                        'Percent' => '20',
                        'WinningAmount' => (string) (($WinningAmount * 20) / 100));

                    $result4[] = array(
                        'Rank' => "3-4",
                        'From' => '3',
                        'To' => '4',
                        'Percent' => '15',
                        'WinningAmount' => (string) (($WinningAmount * 15) / 100));
                    $result4[] = array(
                        'Rank' => "5",
                        'From' => '5',
                        'To' => '5',
                        'Percent' => '12.5',
                        'WinningAmount' => (string) (($WinningAmount * 12.5) / 100));
                    $result4[] = array(
                        'Rank' => "6",
                        'From' => '6',
                        'To' => '6',
                        'Percent' => '7.5',
                        'WinningAmount' => (string) (($WinningAmount * 7.5) / 100));
                    $result4[] = array(
                        'Rank' => "7",
                        'From' => '7',
                        'To' => '7',
                        'Percent' => '5',
                        'WinningAmount' => (string) (($WinningAmount * 5) / 100));


                    $data[] = array('NoOfWinners' => $ContestSize - 0, 'Winners' => $result4);
                    $ContestSize--;
                }

                if ($ContestSize == 6) {

                    $result[] = array(
                        'Rank' => "1",
                        'From' => '1',
                        'To' => '1',
                        'Percent' => '30',
                        'WinningAmount' => (string) (($WinningAmount * 30) / 100));

                    $result[] = array(
                        'Rank' => "2",
                        'From' => '2',
                        'To' => '2',
                        'Percent' => '25',
                        'WinningAmount' => (string) (($WinningAmount * 25) / 100));

                    $result[] = array(
                        'Rank' => "3",
                        'From' => '3',
                        'To' => '3',
                        'Percent' => '20',
                        'WinningAmount' => (string) (($WinningAmount * 20) / 100));

                    $result[] = array(
                        'Rank' => "4",
                        'From' => '4',
                        'To' => '4',
                        'Percent' => '12.5',
                        'WinningAmount' => (string) (($WinningAmount * 12.5) / 100));

                    $result[] = array(
                        'Rank' => "5",
                        'From' => '5',
                        'To' => '5',
                        'Percent' => '7.5',
                        'WinningAmount' => (string) (($WinningAmount * 7.5) / 100));

                    $result[] = array(
                        'Rank' => "6",
                        'From' => '6',
                        'To' => '6',
                        'Percent' => '5',
                        'WinningAmount' => (string) (($WinningAmount * 5) / 100));

                    $data[] = array('NoOfWinners' => $ContestSize - 0, 'Winners' => $result);
                }

                $Return['Data'] = $data;
            }
            if ($ContestSize > 20 && $ContestSize < 25) {
                $result = array();
                $data = [];
                if ($ContestSize > 20) {
                    $ContestSize = 15;
                }
                if ($ContestSize == 15) {

                    $result5[] = array(
                        'Rank' => "1",
                        'From' => '1',
                        'To' => '1',
                        'Percent' => '20',
                        'WinningAmount' => (string) (($WinningAmount * 20) / 100));

                    $result5[] = array(
                        'Rank' => "2",
                        'From' => '2',
                        'To' => '2',
                        'Percent' => '15',
                        'WinningAmount' => (string) (($WinningAmount * 15) / 100));

                    $result5[] = array(
                        'Rank' => "3",
                        'From' => '3',
                        'To' => '3',
                        'Percent' => '10',
                        'WinningAmount' => (string) (($WinningAmount * 10) / 100));
                    $result5[] = array(
                        'Rank' => "4-6",
                        'From' => '4',
                        'To' => '6',
                        'Percent' => '7.5',
                        'WinningAmount' => (string) (($WinningAmount * 7.5) / 100));
                    $result5[] = array(
                        'Rank' => "7-10",
                        'From' => '7',
                        'To' => '10',
                        'Percent' => '5',
                        'WinningAmount' => (string) (($WinningAmount * 5) / 100));
                    $result5[] = array(
                        'Rank' => "11-15",
                        'From' => '11',
                        'To' => '15',
                        'Percent' => '2.5',
                        'WinningAmount' => (string) (($WinningAmount * 2.5) / 100));


                    $data[] = array('NoOfWinners' => $ContestSize - 0, 'Winners' => $result5);
                    $ContestSize = $ContestSize - 5;
                }

                if ($ContestSize == 10) {

                    $result4[] = array(
                        'Rank' => "1",
                        'From' => '1',
                        'To' => '1',
                        'Percent' => '25',
                        'WinningAmount' => (string) (($WinningAmount * 25) / 100));

                    $result4[] = array(
                        'Rank' => "2",
                        'From' => '2',
                        'To' => '2',
                        'Percent' => '20',
                        'WinningAmount' => (string) (($WinningAmount * 20) / 100));

                    $result4[] = array(
                        'Rank' => "3",
                        'From' => '3',
                        'To' => '3',
                        'Percent' => '15',
                        'WinningAmount' => (string) (($WinningAmount * 15) / 100));
                    $result4[] = array(
                        'Rank' => "4",
                        'From' => '4',
                        'To' => '4',
                        'Percent' => '10',
                        'WinningAmount' => (string) (($WinningAmount * 10) / 100));
                    $result4[] = array(
                        'Rank' => "5-10",
                        'From' => '5',
                        'To' => '10',
                        'Percent' => '5',
                        'WinningAmount' => (string) (($WinningAmount * 5) / 100));


                    $data[] = array('NoOfWinners' => $ContestSize - 0, 'Winners' => $result4);
                    $ContestSize = $ContestSize - 3;
                }

                if ($ContestSize == 7) {

                    $result[] = array(
                        'Rank' => "1",
                        'From' => '1',
                        'To' => '1',
                        'Percent' => '25',
                        'WinningAmount' => (string) (($WinningAmount * 25) / 100));

                    $result[] = array(
                        'Rank' => "2",
                        'From' => '2',
                        'To' => '2',
                        'Percent' => '20',
                        'WinningAmount' => (string) (($WinningAmount * 20) / 100));

                    $result[] = array(
                        'Rank' => "3-4",
                        'From' => '3',
                        'To' => '4',
                        'Percent' => '15',
                        'WinningAmount' => (string) (($WinningAmount * 15) / 100));

                    $result[] = array(
                        'Rank' => "5",
                        'From' => '5',
                        'To' => '5',
                        'Percent' => '12.5',
                        'WinningAmount' => (string) (($WinningAmount * 12.5) / 100));

                    $result[] = array(
                        'Rank' => "6",
                        'From' => '6',
                        'To' => '6',
                        'Percent' => '7.5',
                        'WinningAmount' => (string) (($WinningAmount * 7.5) / 100));

                    $result[] = array(
                        'Rank' => "7",
                        'From' => '7',
                        'To' => '7',
                        'Percent' => '5',
                        'WinningAmount' => (string) (($WinningAmount * 5) / 100));

                    $data[] = array('NoOfWinners' => $ContestSize - 0, 'Winners' => $result);
                }

                $Return['Data'] = $data;
            }

            if ($ContestSize > 24 && $ContestSize < 50) {
                $result = array();
                $data = [];
                $size = $ContestSize;
                if ($ContestSize > 24) {
                    $ContestSize = 25;
                }
                if ($ContestSize == 25) {

                    $result5[] = array(
                        'Rank' => "1",
                        'From' => '1',
                        'To' => '1',
                        'Percent' => '25',
                        'WinningAmount' => (string) (($WinningAmount * 25) / 100));

                    $result5[] = array(
                        'Rank' => "2",
                        'From' => '2',
                        'To' => '2',
                        'Percent' => '15',
                        'WinningAmount' => (string) (($WinningAmount * 15) / 100));

                    $result5[] = array(
                        'Rank' => "3",
                        'From' => '3',
                        'To' => '3',
                        'Percent' => '10',
                        'WinningAmount' => (string) (($WinningAmount * 10) / 100));
                    $result5[] = array(
                        'Rank' => "4",
                        'From' => '4',
                        'To' => '4',
                        'Percent' => '6',
                        'WinningAmount' => (string) (($WinningAmount * 6) / 100));
                    $result5[] = array(
                        'Rank' => "5",
                        'From' => '5',
                        'To' => '5',
                        'Percent' => '5',
                        'WinningAmount' => (string) (($WinningAmount * 5) / 100));
                    $result5[] = array(
                        'Rank' => "6-8",
                        'From' => '6',
                        'To' => '8',
                        'Percent' => '4',
                        'WinningAmount' => (string) (($WinningAmount * 4) / 100));
                    $result5[] = array(
                        'Rank' => "9-11",
                        'From' => '9',
                        'To' => '11',
                        'Percent' => '3',
                        'WinningAmount' => (string) (($WinningAmount * 3) / 100));
                    $result5[] = array(
                        'Rank' => "12-15",
                        'From' => '12',
                        'To' => '15',
                        'Percent' => '2',
                        'WinningAmount' => (string) (($WinningAmount * 2) / 100));
                    $result5[] = array(
                        'Rank' => "16-25",
                        'From' => '16',
                        'To' => '25',
                        'Percent' => '1',
                        'WinningAmount' => (string) (($WinningAmount * 1) / 100));


                    $data[] = array('NoOfWinners' => $ContestSize - 0, 'Winners' => $result5);
                    $ContestSize = $ContestSize - 10;
                }

                if ($ContestSize == 15) {

                    $result4[] = array(
                        'Rank' => "1",
                        'From' => '1',
                        'To' => '1',
                        'Percent' => '20',
                        'WinningAmount' => (string) (($WinningAmount * 20) / 100));

                    $result4[] = array(
                        'Rank' => "2",
                        'From' => '2',
                        'To' => '2',
                        'Percent' => '15',
                        'WinningAmount' => (string) (($WinningAmount * 15) / 100));

                    $result4[] = array(
                        'Rank' => "3",
                        'From' => '3',
                        'To' => '3',
                        'Percent' => '10',
                        'WinningAmount' => (string) (($WinningAmount * 10) / 100));
                    $result4[] = array(
                        'Rank' => "4-6",
                        'From' => '4',
                        'To' => '6',
                        'Percent' => '7.5',
                        'WinningAmount' => (string) (($WinningAmount * 7.5) / 100));
                    $result4[] = array(
                        'Rank' => "7-10",
                        'From' => '7',
                        'To' => '10',
                        'Percent' => '5',
                        'WinningAmount' => (string) (($WinningAmount * 5) / 100));
                    $result4[] = array(
                        'Rank' => "11-15",
                        'From' => '11',
                        'To' => '15',
                        'Percent' => '2.5',
                        'WinningAmount' => (string) (($WinningAmount * 2.5) / 100));


                    $data[] = array('NoOfWinners' => $ContestSize - 0, 'Winners' => $result4);
                    $ContestSize = $ContestSize - 5;
                }

                if ($ContestSize == 10 && $size < 31) {

                    $result[] = array(
                        'Rank' => "1",
                        'From' => '1',
                        'To' => '1',
                        'Percent' => '25',
                        'WinningAmount' => (string) (($WinningAmount * 25) / 100));

                    $result[] = array(
                        'Rank' => "2",
                        'From' => '2',
                        'To' => '2',
                        'Percent' => '20',
                        'WinningAmount' => (string) (($WinningAmount * 20) / 100));

                    $result[] = array(
                        'Rank' => "3",
                        'From' => '3',
                        'To' => '3',
                        'Percent' => '15',
                        'WinningAmount' => (string) (($WinningAmount * 15) / 100));
                    $result[] = array(
                        'Rank' => "4",
                        'From' => '4',
                        'To' => '4',
                        'Percent' => '10',
                        'WinningAmount' => (string) (($WinningAmount * 10) / 100));
                    $result[] = array(
                        'Rank' => "5-10",
                        'From' => '5',
                        'To' => '10',
                        'Percent' => '5',
                        'WinningAmount' => (string) (($WinningAmount * 5) / 100));

                    $data[] = array('NoOfWinners' => $ContestSize - 0, 'Winners' => $result);
                }

                $Return['Data'] = $data;
            }

            if ($ContestSize > 49 && $ContestSize < 1000000000) {
                $result = array();
                $data = [];
                if ($ContestSize > 50) {
                    $ContestSize = 50;
                }
                if ($ContestSize == 50) {

                    $result5[] = array(
                        'Rank' => "1",
                        'From' => '1',
                        'To' => '1',
                        'Percent' => '15',
                        'WinningAmount' => (string) (($WinningAmount * 15) / 100));

                    $result5[] = array(
                        'Rank' => "2",
                        'From' => '2',
                        'To' => '2',
                        'Percent' => '10',
                        'WinningAmount' => (string) (($WinningAmount * 10) / 100));

                    $result5[] = array(
                        'Rank' => "3",
                        'From' => '3',
                        'To' => '3',
                        'Percent' => '8',
                        'WinningAmount' => (string) (($WinningAmount * 8) / 100));
                    $result5[] = array(
                        'Rank' => "4",
                        'From' => '4',
                        'To' => '4',
                        'Percent' => '6',
                        'WinningAmount' => (string) (($WinningAmount * 6) / 100));
                    $result5[] = array(
                        'Rank' => "5",
                        'From' => '5',
                        'To' => '5',
                        'Percent' => '5',
                        'WinningAmount' => (string) (($WinningAmount * 5) / 100));
                    $result5[] = array(
                        'Rank' => "6",
                        'From' => '6',
                        'To' => '6',
                        'Percent' => '4',
                        'WinningAmount' => (string) (($WinningAmount * 4) / 100));
                    $result5[] = array(
                        'Rank' => "7",
                        'From' => '7',
                        'To' => '7',
                        'Percent' => '3.5',
                        'WinningAmount' => (string) (($WinningAmount * 3.5) / 100));
                    $result5[] = array(
                        'Rank' => "8",
                        'From' => '8',
                        'To' => '8',
                        'Percent' => '3',
                        'WinningAmount' => (string) (($WinningAmount * 3) / 100));
                    $result5[] = array(
                        'Rank' => "9",
                        'From' => '9',
                        'To' => '9',
                        'Percent' => '2.5',
                        'WinningAmount' => (string) (($WinningAmount * 2.5) / 100));

                    $result5[] = array(
                        'Rank' => "10",
                        'From' => '10',
                        'To' => '10',
                        'Percent' => '2',
                        'WinningAmount' => (string) (($WinningAmount * 2) / 100));
                    $result5[] = array(
                        'Rank' => "11-25",
                        'From' => '11',
                        'To' => '25',
                        'Percent' => '1.5',
                        'WinningAmount' => (string) (($WinningAmount * 1.5) / 100));
                    $result5[] = array(
                        'Rank' => "26-37",
                        'From' => '26',
                        'To' => '37',
                        'Percent' => '1',
                        'WinningAmount' => (string) (($WinningAmount * 1) / 100));
                    $result5[] = array(
                        'Rank' => "38-50",
                        'From' => '38',
                        'To' => '50',
                        'Percent' => '.5',
                        'WinningAmount' => (string) (($WinningAmount * .5) / 100));



                    $data[] = array('NoOfWinners' => $ContestSize - 0, 'Winners' => $result5);
                    $ContestSize = $ContestSize - 25;
                }

                if ($ContestSize == 25) {

                    $result4[] = array(
                        'Rank' => "1",
                        'From' => '1',
                        'To' => '1',
                        'Percent' => '25',
                        'WinningAmount' => (string) (($WinningAmount * 25) / 100));

                    $result4[] = array(
                        'Rank' => "2",
                        'From' => '2',
                        'To' => '2',
                        'Percent' => '15',
                        'WinningAmount' => (string) (($WinningAmount * 15) / 100));

                    $result4[] = array(
                        'Rank' => "3",
                        'From' => '3',
                        'To' => '3',
                        'Percent' => '10',
                        'WinningAmount' => (string) (($WinningAmount * 10) / 100));
                    $result4[] = array(
                        'Rank' => "4",
                        'From' => '4',
                        'To' => '4',
                        'Percent' => '6',
                        'WinningAmount' => (string) (($WinningAmount * 6) / 100));
                    $result4[] = array(
                        'Rank' => "5",
                        'From' => '5',
                        'To' => '5',
                        'Percent' => '5',
                        'WinningAmount' => (string) (($WinningAmount * 5) / 100));
                    $result4[] = array(
                        'Rank' => "6-8",
                        'From' => '6',
                        'To' => '8',
                        'Percent' => '4',
                        'WinningAmount' => (string) (($WinningAmount * 4) / 100));
                    $result4[] = array(
                        'Rank' => "9-11",
                        'From' => '9',
                        'To' => '11',
                        'Percent' => '3',
                        'WinningAmount' => (string) (($WinningAmount * 3) / 100));
                    $result4[] = array(
                        'Rank' => "12-15",
                        'From' => '12',
                        'To' => '15',
                        'Percent' => '2',
                        'WinningAmount' => (string) (($WinningAmount * 2) / 100));
                    $result4[] = array(
                        'Rank' => "16-25",
                        'From' => '16',
                        'To' => '25',
                        'Percent' => '1',
                        'WinningAmount' => (string) (($WinningAmount * 1) / 100));


                    $data[] = array('NoOfWinners' => $ContestSize - 0, 'Winners' => $result4);
                    $ContestSize = $ContestSize - 10;
                }



                $Return['Data'] = $data;
            }
        }
        return $Return;
    }

    /*
      Description: Switch user team
     */

    function switchUserTeam($UserID, $ContestID, $UserTeamID, $OldUserTeamGUID) {

        $UserTeamCount = $this->db->query('SELECT COUNT(ContestID) UserTeamsCount FROM `sports_contest_join` '
                        . 'WHERE UserID = ' . $UserID . ' AND ContestID = ' . $ContestID . ' AND UserTeamID=' . $UserTeamID . ' ')->row()->UserTeamsCount;
        if ($UserTeamCount <= 0) {
            /* Update Joined Contest Team Status */
            $this->db->where('UserID', $UserID);
            $this->db->where('ContestID', $ContestID);
            $this->db->where('UserTeamID', $OldUserTeamGUID);
            $this->db->limit(1);
            $this->db->update('sports_contest_join', array('UserTeamID' => $UserTeamID));

            /* Update New UserTeamID */
            $this->db->where(array('UserID' => $UserID, 'EntityID' => $ContestID, 'UserTeamID' => $OldUserTeamGUID));
            $this->db->limit(1);
            $this->db->update('tbl_users_wallet', array('UserTeamID' => $UserTeamID));
            return true;
        }
        return false;
    }

    function findKeyValuePlayers($array, $value) {
        if (is_array($array)) {
            $players = array();
            foreach ($array as $key => $rows) {
                if ($rows['PlayerRole'] == $value) {
                    $players[] = $array[$key];
                }
            }
            return $players;
        }
        return false;
    }

    /*
      Description: validate Advance or safe Play.
     */

    function ValidateAdvanceSafePlay($MatchID, $UserID, $UserTeamID) {
        $JoinedContest = $this->getJoinedContests("ContestID,GameType,GameTimeLive,MatchStartDateTimeUTC", array("UserTeamID" => $UserTeamID, "MatchID" => $MatchID, "SessionUserID" => $UserID, "GameType" => "Advance", "OrderBy" => "GameTimeLive", "Sequence" => "DESC"), TRUE);
        if ($JoinedContest['Data']['TotalRecords'] > 0) {
            $CurrentDateTime = strtotime(date('Y-m-d H:i:s')); // UTC 
            if ($JoinedContest['Data']['Records'][0]["GameTimeLive"] > 0) {
                $MatchStartDateTime = strtotime($JoinedContest['Data']['Records'][0]['MatchStartDateTimeUTC']) - $JoinedContest['Data']['Records'][0]["GameTimeLive"] * 60;
                if ($MatchStartDateTime > $CurrentDateTime) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    /*
      Description: To get joined contest users (MongoDB)
     */

    function getJoinedContestsUsersMongoDB($Where = array(), $PageNo = 1, $PageSize = 15) {
        ini_set('memory_limit', '256M');
        /* Get Joined Contest Users */
        $ContestCollection = $this->fantasydb->{'Contest_' . $Where['ContestID']};
        $JoinedContestsUsersMy = iterator_to_array($ContestCollection->find(["UserID" => $Where['UserID']], ['projection' => ['_id' => 0, 'UserGUID' => 1, 'UserTeamName' => 1, 'UserTeamGUID' => 1, 'Username' => 1,'IsSubscribe' => 1, 'FullName' => 1, 'ProfilePic' => 1, 'TotalPoints' => 1, 'UserTeamPlayers' => 1, 'UserRank' => 1, 'UserWinningAmount' => 1, 'TaxAmount' => 1, 'SmartPool' => 1, 'SmartPoolWinning' => 1], 'skip' => paginationOffset($PageNo, $PageSize), 'limit' => (int) $PageSize, 'sort' => ['UserRank' => 1]]));
        $JoinedContestsUsers = iterator_to_array($ContestCollection->find(["UserID" => ['$ne' => $Where['UserID']]], ['projection' => ['_id' => 0, 'UserGUID' => 1, 'UserTeamName' => 1, 'UserTeamGUID' => 1, 'Username' => 1,'IsSubscribe' => 1, 'FullName' => 1, 'ProfilePic' => 1, 'TotalPoints' => 1, 'UserTeamPlayers' => 1, 'UserRank' => 1, 'UserWinningAmount' => 1, 'TaxAmount' => 1, 'SmartPool' => 1, 'SmartPoolWinning' => 1], 'skip' => paginationOffset($PageNo, $PageSize), 'limit' => (int) $PageSize, 'sort' => ['TotalTeamPoints' => -1, 'UserRank' => 1]]));
        $JoinedContestsUsersAll = array_merge($JoinedContestsUsersMy, $JoinedContestsUsers);
        if (count($JoinedContestsUsers) > 0 || count($JoinedContestsUsersMy) > 0) {
            $Return['Data']['TotalRecords'] = $ContestCollection->count();
            $Return['Data']['Records'] = $JoinedContestsUsersAll;
            return $Return;
        }
        return FALSE;
    }

    /*
      Description: To get joined contest users team (MongoDB)
     */

    function getJoinedContestsUsersTeamMongoDB($Where = array(), $PageNo = 1, $PageSize = 15) {
        /* Get Joined Contest Users */
        $ContestCollection = $this->fantasydb->{'Contest_' . $Where['ContestID']};
        $MyTeam = $ContestCollection->find(["UserTeamID" => $Where['UserTeamID']], ['projection' => ['_id' => 0, 'UserGUID' => 1, 'UserTeamName' => 1, 'UserTeamGUID' => 1, 'Username' => 1, 'FullName' => 1, 'ProfilePic' => 1, 'TotalPoints' => 1, 'UserTeamPlayers' => 1, 'UserRank' => 1, 'UserWinningAmount' => 1, 'TaxAmount' => 1], 'skip' => paginationOffset($PageNo, $PageSize), 'limit' => (int) $PageSize, 'sort' => ['UserRank' => 1]]);
        $JoinedContestsUsersMy = (!empty($MyTeam)) ? iterator_to_array($MyTeam) : array();
        if (count($JoinedContestsUsersMy) > 0) {
            foreach ($JoinedContestsUsersMy as $ContestValue) {
                $Return['Data'] = $ContestValue;
                return $Return;
            }
        }
        return FALSE;
    }

    function getMatchWiseReports($SeriesID = '', $MatchID = '') {
        $Return = array();
        $Return['TotalJoinContestCollection'] = 0;
        $Return['TotalDepositCollection'] = 0;
        $Return['TotalCashBonusCollection'] = 0;
        $Return['TotalRealUserWinningCollection'] = 0;
        $Return['TotalVirtualUserWinningCollection'] = 0;
        $Return['Profit'] = 0;
        $Return['NetProfit'] = 0;
        $Return['loss'] = 0;
        $Return['MatchDetails'] = array();
        $Return['ContestDetails'] = array();
        $Return['TopWinners'] = array();
        $Return['TopLoosers'] = array();
        if (!empty($SeriesID)) {
            $this->db->select('M.MatchID,S.SeriesName,CONCAT(TL.TeamName," Vs ",TV.TeamName) as MatchName,DATE_FORMAT(CONVERT_TZ(M.MatchStartDateTime,"+00:00","' . DEFAULT_TIMEZONE . '"), "' . DATE_FORMAT . '") MatchStartDateTime');
            $this->db->from('sports_matches M,tbl_entity E,sports_series S,sports_teams TL, sports_teams TV');
            $this->db->where("E.EntityID", "M.MatchID", FALSE);
            $this->db->where("S.SeriesID", "M.SeriesID", FALSE);
            $this->db->where("M.TeamIDLocal", "TL.TeamID", FALSE);
            $this->db->where("M.TeamIDVisitor", "TV.TeamID", FALSE);
            $this->db->where("M.SeriesID", $SeriesID);
            if (!empty($MatchID)) {
                $this->db->where("M.MatchID", $MatchID);
            }
            $this->db->where("E.StatusID", 5);
            $Query = $this->db->get();
            if ($Query->num_rows() > 0) {
                $Matches = $Query->result_array();
                if (!empty($Matches)) {
                    foreach ($Matches as $Value) {
                        $TotalBonus = 0;
                        $Return['MatchDetails'] = $Value;

                        /** top 5 winners * */
                        $this->db->select("SUM(JC.UserWinningAmount) as TotalWinning,JC.UserID,U.Email,U.PhoneNumber,U.FirstName,U.Username");
                        $this->db->from('sports_contest_join JC,tbl_users U,sports_contest C');
                        $this->db->where("U.UserID", "JC.UserID", FALSE);
                        $this->db->where("JC.ContestID", "C.ContestID", FALSE);
                        $this->db->where("C.LeagueType", "Dfs");
                        $this->db->where("U.UserTypeID", 2);
                        $this->db->where("JC.MatchID", $Value['MatchID']);
                        $this->db->having("TotalWinning > 0");
                        $this->db->group_by("JC.UserID");
                        $this->db->order_by("TotalWinning", "DESC");
                        $this->db->limit(5);
                        $Query = $this->db->get();
                        if ($Query->num_rows() > 0) {
                            $Return['TopWinners'] = $Query->result_array();
                        }
                        /** top 5 loosers * */
                        $this->db->select("COUNT(WC.UserTeamID) as TotalJoined,SUM(WC.Amount) as TotalLosing,WC.UserID,U.Email,U.PhoneNumber,U.FirstName,U.Username");
                        $this->db->from('sports_contest C,tbl_users U,tbl_users_wallet WC');
                        $this->db->where("WC.EntityID", "C.ContestID", FALSE);
                        $this->db->where("U.UserID", "WC.UserID", FALSE);
                        $this->db->where("C.LeagueType", "Dfs");
                        $this->db->where("U.UserTypeID", 2);
                        $this->db->where("C.MatchID", $Value['MatchID']);
                        $this->db->where("WC.Narration", "Join Contest");
                        $this->db->where_not_in("WC.UserID",array_column($Return['TopWinners'],'UserID'));
                        $this->db->group_by("WC.UserID");
                        $this->db->order_by("TotalLosing", "DESC");
                        $this->db->order_by("TotalJoined", "DESC");
                        $this->db->limit(5);
                        $Query = $this->db->get();
                        if ($Query->num_rows() > 0) {
                            $Return['TopLoosers'] = $Query->result_array();
                        }
                        $this->db->select("CONCAT( '[',GROUP_CONCAT(ContestID), ']' ) as ContestID,"
                                . "(SELECT COUNT(ContestID) FROM `sports_contest` WHERE MatchID='" . $Value['MatchID'] . "' AND Privacy='No') as TotalPublicContest,"
                                . "(SELECT COUNT(ContestID) FROM `sports_contest` WHERE MatchID='" . $Value['MatchID'] . "' AND Privacy='Yes') as TotalPrivateContest,"
                                . "(SELECT COUNT(CT.ContestID) FROM `sports_contest` CT,tbl_entity E WHERE E.EntityID=CT.ContestID AND E.StatusID=5 AND CT.MatchID='" . $Value['MatchID'] . "' AND CT.Privacy='No') as TotalPublicCompleteContest,"
                                . "(SELECT COUNT(CT.ContestID) FROM `sports_contest` CT,tbl_entity E WHERE E.EntityID=CT.ContestID AND E.StatusID=3 AND CT.MatchID='" . $Value['MatchID'] . "' AND CT.Privacy='No') as TotalPublicCancelledContest,"
                                . "(SELECT COUNT(CT.ContestID) FROM `sports_contest` CT,tbl_entity E WHERE E.EntityID=CT.ContestID AND E.StatusID=5 AND CT.MatchID='" . $Value['MatchID'] . "' AND CT.Privacy='Yes') as TotalPrivateCompleteContest,"
                                . "(SELECT COUNT(CT.ContestID) FROM `sports_contest` CT,tbl_entity E WHERE E.EntityID=CT.ContestID AND E.StatusID=3 AND CT.MatchID='" . $Value['MatchID'] . "' AND CT.Privacy='Yes') as TotalPrivateCancelledContest,"
                                . "(SELECT COUNT(CT.UserID) FROM `sports_contest_join` CT,tbl_users U WHERE U.UserID=CT.UserID AND U.UserTypeID!=3 AND CT.MatchID='" . $Value['MatchID'] . "') as TotalJoinedUsersReal,"
                                . "(SELECT COUNT(CT.UserID) FROM `sports_contest_join` CT,tbl_users U WHERE U.UserID=CT.UserID AND U.UserTypeID=3 AND CT.MatchID='" . $Value['MatchID'] . "') as TotalJoinedUsersVirtual");
                        $this->db->from('sports_contest C,tbl_entity E');
                        $this->db->where("E.EntityID", "C.ContestID", FALSE);
                        $this->db->where("C.LeagueType", "Dfs");
                        $this->db->where("C.MatchID", $Value['MatchID']);
                        $this->db->where("E.StatusID", 5);
                        $this->db->limit(1);
                        $Query = $this->db->get();
                        if ($Query->num_rows() > 0) {
                            $Contests = $Query->row_array();
                            if (!empty($Contests)) {
                                $Return['ContestDetails'] = $Contests;
                                $AlContest = json_decode($Contests['ContestID']);
                                if (!empty($AlContest)) {
                                    /** join contest collection * */
                                    $this->db->select("SUM(W.Amount) TotalCollection,SUM(W.CashBonus) TotalBonusCollection");
                                    $this->db->from('tbl_users_wallet W,tbl_users U');
                                    $this->db->where("W.UserID", "U.UserID", FALSE);
                                    $this->db->where("W.Narration", "Join Contest");
                                    $this->db->where("U.UserTypeID", 2);
                                    $this->db->where("W.StatusID", 5);
                                    $this->db->where_in("W.EntityID", $AlContest);
                                    $this->db->limit(1);
                                    $Query = $this->db->get();
                                    $Collection = $Query->row_array();
                                    if (!empty($Collection)) {
                                        $Return['TotalJoinContestCollection'] += $Collection['TotalCollection'];
                                        $Return['TotalDepositCollection'] += $Collection['TotalCollection'] - $Collection['TotalBonusCollection'];
                                        $Return['TotalCashBonusCollection'] += $Collection['TotalBonusCollection'];
                                        $TotalBonus += $Collection['TotalBonusCollection'];
                                    }

                                    /** join winning collection real users * */
                                    $this->db->select("SUM(W.Amount) TotalWinningCollection");
                                    $this->db->from('tbl_users_wallet W,tbl_users U');
                                    $this->db->where("W.UserID", "U.UserID", FALSE);
                                    $this->db->where("W.Narration", "Join Contest Winning");
                                    $this->db->where("U.UserTypeID", 2);
                                    $this->db->where("W.StatusID", 5);
                                    $this->db->where_in("W.EntityID", $AlContest);
                                    $this->db->limit(1);
                                    $Query = $this->db->get();
                                    $CollectionWinning = $Query->row_array();
                                    if (!empty($CollectionWinning)) {
                                        $Return['TotalRealUserWinningCollection'] += $CollectionWinning['TotalWinningCollection'];
                                    }
                                    $ProfitLoss = $Return['TotalDepositCollection'] - $Return['TotalRealUserWinningCollection'];
                                    if ($ProfitLoss > 0) {
                                        $Return['NetProfit'] = $ProfitLoss;
                                        $Return['Profit'] = ($ProfitLoss) - round((($ProfitLoss) * 2.5 / 100), 2) - $TotalBonus;
                                    } else {

                                        $Return['loss'] = round(abs($ProfitLoss) + ((abs($ProfitLoss) * 2.5) / 100), 2) + $TotalBonus;
                                    }
                                    /** join winning collection virtual users * */
                                    $this->db->select("SUM(W.UserWinningAmount) TotalWinningCollection");
                                    $this->db->from('sports_contest_join W,tbl_users U');
                                    $this->db->where("W.UserID", "U.UserID", FALSE);
                                    $this->db->where("U.UserTypeID", 3);
                                    $this->db->where_in("W.ContestID", $AlContest);
                                    $this->db->limit(1);
                                    $Query = $this->db->get();
                                    $CollectionWinning = $Query->row_array();
                                    if (!empty($CollectionWinning)) {
                                        $Return['TotalVirtualUserWinningCollection'] += $CollectionWinning['TotalWinningCollection'];
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return $Return;
    }

    function getAccountReport($Where = array(), $SeriesID = '') {
        $SeriesIDTrue = $SeriesID;
        $this->db->select('S.SeriesGUID,M.MatchGUID,M.MatchID,M.MatchNo,S.SeriesID,S.SeriesStartDate,S.SeriesEndDate,S.SeriesName,CONCAT(TL.TeamName," Vs ",TV.TeamName) as MatchName,DATE_FORMAT(CONVERT_TZ(M.MatchStartDateTime,"+00:00","' . DEFAULT_TIMEZONE . '"), "' . DATE_FORMAT . '") MatchStartDateTime');
        $this->db->from('sports_matches M,tbl_entity E,sports_series S,sports_teams TL, sports_teams TV');
        $this->db->where("E.EntityID", "M.MatchID", FALSE);
        $this->db->where("S.SeriesID", "M.SeriesID", FALSE);
        $this->db->where("M.TeamIDLocal", "TL.TeamID", FALSE);
        $this->db->where("M.TeamIDVisitor", "TV.TeamID", FALSE);
        if (!empty($SeriesID)) {
            $this->db->where("M.SeriesID", $SeriesID);
        }
        if (!empty($Where['FromDate'])) {
            $this->db->where("DATE(M.MatchStartDateTime) >=", $Where['FromDate']);
        }
        if (!empty($Where['ToDate'])) {
            $this->db->where("DATE(M.MatchStartDateTime) <=", $Where['ToDate']);
        }
        $this->db->where("E.StatusID", 5);
        $this->db->order_by("M.MatchStartDateTime", "ASC");
        $Query = $this->db->get();
        $MatchReports = array();
        $MatchReports['TotalSeriesCollection'] = array();
        $TotalJoinContestCollection = 0;
        $TotalDepositCollection = 0;
        $TotalCashBonusCollection = 0;
        $TotalRealUserWinningCollection = 0;
        $TotalProfit = 0;
        $Totalloss = 0;

        $AllTotalNetProfit=0;
        $AllTotalProfit=0;
        $AllTotalLoss=0;


        if ($Query->num_rows() > 0) {
            $Matches = $Query->result_array();
            if (!empty($Matches)) {
                foreach ($Matches as $Value) {
                    $SeriesIDGet = $Matches[0]['SeriesID'];
                    $Return = array();
                    $Return['TotalJoinContestCollection'] = 0;
                    $Return['TotalDepositCollection'] = 0;
                    $Return['TotalCashBonusCollection'] = 0;
                    $Return['TotalRealUserWinningCollection'] = 0;
                    $Return['TotalVirtualUserWinningCollection'] = 0;
                    $Return['Profit'] = 0;
                    $Return['loss'] = 0;
                    $Return['NetProfit'] = 0;
                    $Return['MatchDetails'] = array();
                    $Return['ContestDetails'] = array();
                    $Return['MatchDetails'] = $Value;
                    $this->db->select("COUNT(C.ContestID) as TotalContest,CONCAT( '[',GROUP_CONCAT(ContestID), ']' ) as ContestID");
                    $this->db->from('sports_contest C,tbl_entity E');
                    $this->db->where("E.EntityID", "C.ContestID", FALSE);
                    $this->db->where("C.LeagueType", "Dfs");
                    $this->db->where("C.MatchID", $Value['MatchID']);
                    $this->db->where("E.StatusID", 5);
                    $this->db->limit(1);
                    $Query = $this->db->get();
                    if ($Query->num_rows() > 0) {
                        $Contests = $Query->row_array();
                        if (!empty($Contests)) {
                            $Return['ContestDetails'] = $Contests;
                            $AlContest = json_decode($Contests['ContestID']);
                            if (!empty($AlContest)) {
                                /** join contest collection * */
                                $this->db->select("SUM(W.Amount) TotalCollection,SUM(W.CashBonus) TotalBonusCollection");
                                $this->db->from('tbl_users_wallet W,tbl_users U');
                                $this->db->where("W.UserID", "U.UserID", FALSE);
                                $this->db->where("W.Narration", "Join Contest");
                                $this->db->where("U.UserTypeID !=", 3);
                                $this->db->where("W.StatusID", 5);
                                $this->db->where_in("W.EntityID", $AlContest);
                                $this->db->limit(1);
                                $Query = $this->db->get();
                                $Collection = $Query->row_array();
                                if (!empty($Collection)) {
                                    $Return['TotalJoinContestCollection'] += $Collection['TotalCollection'];
                                    $Return['TotalDepositCollection'] += $Collection['TotalCollection'] - $Collection['TotalBonusCollection'];
                                    $Return['TotalCashBonusCollection'] += $Collection['TotalBonusCollection'];

                                    $TotalJoinContestCollection += $Collection['TotalCollection'];
                                    $TotalDepositCollection += $Collection['TotalCollection'] - $Collection['TotalBonusCollection'];
                                    $TotalCashBonusCollection += $Collection['TotalBonusCollection'];
                                }

                                /** join winning collection real users * */
                                $this->db->select("SUM(W.Amount) TotalWinningCollection");
                                $this->db->from('tbl_users_wallet W,tbl_users U');
                                $this->db->where("W.UserID", "U.UserID", FALSE);
                                $this->db->where("W.Narration", "Join Contest Winning");
                                $this->db->where("U.UserTypeID !=", 3);
                                $this->db->where("W.StatusID", 5);
                                $this->db->where_in("W.EntityID", $AlContest);
                                $this->db->limit(1);
                                $Query = $this->db->get();
                                $CollectionWinning = $Query->row_array();
                                if (!empty($CollectionWinning)) {
                                    $Return['TotalRealUserWinningCollection'] += $CollectionWinning['TotalWinningCollection'];
                                    $TotalRealUserWinningCollection += $CollectionWinning['TotalWinningCollection'];
                                }
                                $ProfitLoss = $Return['TotalDepositCollection'] - $Return['TotalRealUserWinningCollection'];
                                if ($ProfitLoss > 0) {
                                    $Return['NetProfit'] = $ProfitLoss;
                                    $Pro = $Return['Profit'] = ($Return['TotalDepositCollection'] - $Return['TotalRealUserWinningCollection']) - round((($Return['TotalDepositCollection'] - $Return['TotalRealUserWinningCollection']) * 2.5 / 100), 2) - $Return['TotalCashBonusCollection'];
                                    $TotalProfit += ($Return['TotalDepositCollection'] - $Return['TotalRealUserWinningCollection']) - round((($Return['TotalDepositCollection'] - $Return['TotalRealUserWinningCollection']) * 2.5 / 100), 2) - $Return['TotalCashBonusCollection'];

                                    $AllTotalNetProfit +=$ProfitLoss;
                                    $AllTotalProfit += $Pro;


                                } else {
                                    $l = $Return['loss'] = abs($ProfitLoss) + round(((abs($ProfitLoss) * 2.5) / 100) + $Return['TotalCashBonusCollection'], 2);
                                    $Totalloss += abs(round(abs($ProfitLoss) + ((abs($ProfitLoss) * 2.5) / 100) + $Return['TotalCashBonusCollection'], 2));
                                    $AllTotalLoss +=$l;
                                }
                                /** join winning collection virtual users * */
                                $this->db->select("SUM(W.UserWinningAmount) TotalWinningCollection");
                                $this->db->from('sports_contest_join W,tbl_users U');
                                $this->db->where("W.UserID", "U.UserID", FALSE);
                                $this->db->where("U.UserTypeID", 3);
                                $this->db->where_in("W.ContestID", $AlContest);
                                $this->db->limit(1);
                                $Query = $this->db->get();
                                $CollectionWinning = $Query->row_array();
                                if (!empty($CollectionWinning)) {
                                    $Return['TotalVirtualUserWinningCollection'] += $CollectionWinning['TotalWinningCollection'];
                                }
                            }
                        }
                    }
                    $MatchReports['Matches'][] = $Return;
                }

                if (empty($SeriesID)) {
                    $SeriesID = $SeriesIDGet;
                }
                $TotalTeams = 0;
                /*$TotalTeams = $this->db->query("SELECT TeamIDLocal  FROM sports_matches WHERE `SeriesID` = " . $SeriesID . " UNION SELECT TeamIDVisitor FROM sports_matches WHERE `SeriesID` = " . $SeriesID)->result_array();*/

                $MatchReports['TotalSeriesCollection']['TotalProfit'] = 0;
                $MatchReports['TotalSeriesCollection']['Totalloss'] = 0;
                $TotalProfitLoss = $TotalDepositCollection - $TotalRealUserWinningCollection;
                if ($TotalProfitLoss > 0) {
                    $MatchReports['TotalSeriesCollection']['TotalProfit'] = ($TotalDepositCollection - $TotalRealUserWinningCollection) - round((($TotalDepositCollection - $TotalRealUserWinningCollection) * 2.5 / 100), 2) - $TotalCashBonusCollection;
                } else {
                    $MatchReports['TotalSeriesCollection']['Totalloss'] = '-' . round(abs($TotalProfitLoss) + ((abs($TotalProfitLoss) * 2.5) / 100) + $TotalCashBonusCollection, 2);
                }
                $MatchReports['TotalSeriesCollection']['TotalJoinContestCollection'] = $TotalJoinContestCollection;
                $MatchReports['TotalSeriesCollection']['TotalDepositCollection'] = $TotalDepositCollection;
                $MatchReports['TotalSeriesCollection']['TotalCashBonusCollection'] = $TotalCashBonusCollection;
                $MatchReports['TotalSeriesCollection']['TotalRealUserWinningCollection'] = $TotalRealUserWinningCollection;
                $MatchReports['TotalSeriesCollection']['TotalTeams'] = (count($TotalTeams));
                $MatchReports['TotalSeriesCollection']['SeriesID'] = $SeriesIDTrue;
                $SeriesAmount = $AllTotalNetProfit -  $AllTotalLoss;
                if($SeriesAmount > 0){
                     $MatchReports['TotalSeriesCollection']['TotalNetProfit'] = $AllTotalNetProfit -  $AllTotalLoss;
                     $MatchReports['TotalSeriesCollection']['TotalProfit'] = $AllTotalProfit -  $AllTotalLoss;
                     $MatchReports['TotalSeriesCollection']['Totalloss'] =  0;   
                }else{
                    $MatchReports['TotalSeriesCollection']['TotalNetProfit'] = 0;
                    $MatchReports['TotalSeriesCollection']['TotalProfit'] = 0;
                    $MatchReports['TotalSeriesCollection']['Totalloss'] =  $AllTotalLoss;
                }
                
            }
        }
        return $MatchReports;
    }

    function getUserAnalysisReport($Where = array()) {
        $Return = array();
        $Return['UsersList'] = array();
        $Return['FromTo'] = "";
        $Return['UserType'] = ($Where['UserType'] == "TopWinners") ? "Top 50 Winners" : "Top 50 Losers";

        if ($Where['UserType'] == "TopWinners") {
            $this->db->select("SUM(WC.Amount) as TotalWinning,WC.UserID,U.Email,U.PhoneNumber,U.FirstName,U.Username");
            $this->db->from('tbl_users_wallet WC,tbl_users U,sports_contest C');
            $this->db->where("U.UserID", "WC.UserID", FALSE);
            $this->db->where("WC.EntityID", "C.ContestID", FALSE);
            $this->db->where("C.LeagueType", "Dfs");
            $this->db->where("U.UserTypeID", 2);
            $this->db->where("WC.Narration", "Join Contest Winning");
            $this->db->where("WC.StatusID", 5);
            if (!empty($Where['DataFilter'])) {
                if ($Where['DataFilter'] == "Today") {
                    $this->db->where("DATE(WC.EntryDate)", date('Y-m-d'));
                    $Return['FromTo'] = date('d-m-Y') . " To " . date('d-m-Y');
                } else if ($Where['DataFilter'] == "Last7Days") {
                    $this->db->where("DATE(WC.EntryDate) <=", date('Y-m-d'));
                    $this->db->where("DATE(WC.EntryDate) >=", date('Y-m-d', strtotime('-7 days')));
                    $Return['FromTo'] = date('d-m-Y') . " To " . date('d-m-Y', strtotime('-7 days'));
                } else if ($Where['DataFilter'] == "Last15Days") {
                    $this->db->where("DATE(WC.EntryDate) <=", date('Y-m-d'));
                    $this->db->where("DATE(WC.EntryDate) >=", date('Y-m-d', strtotime('-15 days')));
                    $Return['FromTo'] = date('d-m-Y') . " To " . date('d-m-Y', strtotime('-15 days'));
                } else if ($Where['DataFilter'] == "Last30Days") {
                    $this->db->where("DATE(WC.EntryDate) <=", date('Y-m-d'));
                    $this->db->where("DATE(WC.EntryDate) >=", date('Y-m-d', strtotime('-1 month')));
                    $Return['FromTo'] = date('d-m-Y') . " To " . date('d-m-Y', strtotime('-1 month'));
                } else if ($Where['DataFilter'] == "Last3Months") {
                    $this->db->where("DATE(WC.EntryDate) <=", date('Y-m-d'));
                    $this->db->where("DATE(WC.EntryDate) >=", date('Y-m-d', strtotime('-3 month')));
                    $Return['FromTo'] = date('d-m-Y') . " To " . date('d-m-Y', strtotime('-3 month'));
                } else {
                    $this->db->where("DATE(WC.EntryDate) >=", $Where['FromDate']);
                    $this->db->where("DATE(WC.EntryDate) <=", $Where['ToDate']);
                    $Return['FromTo'] = date('d-m-Y', strtotime($Where['FromDate'])) . " To " . date('d-m-Y', strtotime($Where['ToDate']));
                }
            } else {
                if (!empty($Where['FromDate']) && !empty($Where['ToDate'])) {
                    $this->db->where("DATE(WC.EntryDate) >=", $Where['FromDate']);
                    $this->db->where("DATE(WC.EntryDate) <=", $Where['ToDate']);
                    $Return['FromTo'] = date('d-m-Y', strtotime($Where['FromDate'])) . " To " . date('d-m-Y', strtotime($Where['ToDate']));
                }
            }
            $this->db->having("TotalWinning > 0");
            $this->db->group_by("WC.UserID");
            $this->db->order_by("TotalWinning", "DESC");
            $this->db->limit(50);
            $Query = $this->db->get();
            if ($Query->num_rows() > 0) {
                $Return['UsersList'] = $Query->result_array();
            }
        } else if ($Where['UserType'] == "TopLosers") {

            $this->db->select("SUM(WC.Amount) as LosingAmount,COUNT(JC.UserTeamID) as TotalJoined,SUM(JC.UserWinningAmount) as TotalWinning,JC.UserID,U.Email,U.PhoneNumber,U.FirstName,WC.EntryDate,U.Username");
            $this->db->from('sports_contest_join JC,tbl_users U,sports_contest C,tbl_users_wallet WC');
            $this->db->where("U.UserID", "JC.UserID", FALSE);
            $this->db->where("JC.ContestID", "C.ContestID", FALSE);
            $this->db->where("WC.EntityID", "C.ContestID", FALSE);
            $this->db->where("C.LeagueType", "Dfs");
            $this->db->where("U.UserTypeID", 2);
            $this->db->where("WC.Narration", "Join Contest");
            $this->db->where("WC.StatusID", 5);
            if (!empty($Where['DataFilter'])) {
                if ($Where['DataFilter'] == "Today") {
                    $this->db->where("DATE(WC.EntryDate)", date('Y-m-d'));
                    $Return['FromTo'] = date('d-m-Y') . " To " . date('d-m-Y');
                } else if ($Where['DataFilter'] == "Last7Days") {
                    $this->db->where("DATE(WC.EntryDate) <=", date('Y-m-d'));
                    $this->db->where("DATE(WC.EntryDate) >=", date('Y-m-d', strtotime('-7 days')));
                    $Return['FromTo'] = date('d-m-Y') . " To " . date('d-m-Y', strtotime('-7 days'));
                } else if ($Where['DataFilter'] == "Last15Days") {
                    $this->db->where("DATE(WC.EntryDate) <=", date('Y-m-d'));
                    $this->db->where("DATE(WC.EntryDate) >=", date('Y-m-d', strtotime('-15 days')));
                    $Return['FromTo'] = date('d-m-Y') . " To " . date('d-m-Y', strtotime('-15 days'));
                } else if ($Where['DataFilter'] == "Last30Days") {
                    $this->db->where("DATE(WC.EntryDate) <=", date('Y-m-d'));
                    $this->db->where("DATE(WC.EntryDate) >=", date('Y-m-d', strtotime('-1 month')));
                    $Return['FromTo'] = date('d-m-Y') . " To " . date('d-m-Y', strtotime('-1 month'));
                } else if ($Where['DataFilter'] == "Last3Months") {
                    $this->db->where("DATE(WC.EntryDate) <=", date('Y-m-d'));
                    $this->db->where("DATE(WC.EntryDate) >=", date('Y-m-d', strtotime('-3 month')));
                    $Return['FromTo'] = date('d-m-Y') . " To " . date('d-m-Y', strtotime('-3 month'));
                } else {
                    $this->db->where("DATE(WC.EntryDate) >=", $Where['FromDate']);
                    $this->db->where("DATE(WC.EntryDate) <=", $Where['ToDate']);
                    $Return['FromTo'] = date('d-m-Y', strtotime($Where['FromDate'])) . " To " . date('d-m-Y', strtotime($Where['ToDate']));
                }
            } else {
                if (!empty($Where['FromDate']) && !empty($Where['ToDate'])) {
                    $this->db->where("DATE(WC.EntryDate) >=", $Where['FromDate']);
                    $this->db->where("DATE(WC.EntryDate) <=", $Where['ToDate']);
                    $Return['FromTo'] = date('d-m-Y', strtotime($Where['FromDate'])) . " To " . date('d-m-Y', strtotime($Where['ToDate']));
                }
            }
            $this->db->having("TotalWinning <= 0");
            $this->db->group_by("JC.UserID");
            $this->db->order_by("LosingAmount", "DESC");
            $this->db->order_by("TotalJoined", "DESC");
            $this->db->limit(50);
            $Query = $this->db->get();
            if ($Query->num_rows() > 0) {
                $Return['UsersList'] = $Query->result_array();
            }
        }
        return $Return;
    }

    function getContestName($contestType) {
        /** contest total deposit,bonus * */
        $this->db->select("DISTINCT(ContestName)");
        $this->db->from('sports_contest C,tbl_entity E');
        $this->db->where("E.EntityID", "C.ContestID", FALSE);
        $this->db->where("C.LeagueType", "Dfs");
        $this->db->where("C.Privacy", "No");
        $this->db->where("DATE(E.EntryDate) >=", date('Y-m-d', strtotime('-3 month')));
        if (!empty($contestType)) {
            $this->db->where_in("C.ContestType", $contestType);
        }
        $Query = $this->db->get();

        return $Query->result_array();
    }

    function getVirtualContestName($contestType) {
        /** contest total deposit,bonus * */
        $this->db->select("DISTINCT(ContestName)");
        $this->db->from('sports_contest C,tbl_entity E');
        $this->db->where("E.EntityID", "C.ContestID", FALSE);
        $this->db->where("C.LeagueType", "Dfs");
        $this->db->where("C.WinningType", "Free Join Contest");
        $this->db->where("DATE(E.EntryDate) >=", date('Y-m-d', strtotime('-3 month')));
        if (!empty($contestType)) {
            $this->db->where_in("C.ContestType", $contestType);
        }
        $Query = $this->db->get();

        return $Query->result_array();
    }


    function getContestPrivateName($contestType) {
        /** contest total deposit,bonus * */
        $this->db->select("DISTINCT(ContestName)");
        $this->db->from('sports_contest C,tbl_entity E');
        $this->db->where("E.EntityID", "C.ContestID", FALSE);
        $this->db->where("C.LeagueType", "Dfs");
        $this->db->where("C.Privacy", "Yes");
        $this->db->where("DATE(E.EntryDate) >=", date('Y-m-d', strtotime('-3 month')));
        if (!empty($contestType)) {
            $this->db->where_in("C.ContestType", $contestType);
        }
        $Query = $this->db->get();

        return $Query->result_array();
    }

    function getMatchContestAnalysis($MatchID) {
        $Return = array();
        $ReturnAll = array();

        $this->db->select("ContestID");
        $this->db->from('sports_contest C,tbl_entity E');
        $this->db->where("E.EntityID", "C.ContestID", FALSE);
        $this->db->where("C.LeagueType", "Dfs");
        $this->db->where("C.IsPaid", "Yes");
        $this->db->where("E.StatusID", 5);
        $this->db->where("C.MatchID", $MatchID);
        $Query = $this->db->get();
        $ContestList = $Query->result_array();
        if(!empty($ContestList)){
            foreach($ContestList as $ContestRow){

                        $Return['TotalDepositCollection'] = 0;
                        $Return['TotalCashBonusCollection'] = 0;
                        $Return['TotalRealUserWinningCollection'] = 0;
                        $Return['TotalRealUserJoined'] = 0;
                        $Return['TotalVirtualUserJoined'] = 0;
                        $Return['Profit'] = 0;
                        $Return['NetProfit'] = 0;
                        $Return['loss'] = 0;
                        $Return['visible'] = 0;
                        $Return['ContestDetails'] = array();


                        /** contest total deposit,bonus * */
                        $this->db->select("(SUM(WC.WalletAmount) + SUM(WC.WinningAmount)) as DepositAmount,SUM(WC.CashBonus) as CashBonus,"
                                . "COUNT(WC.WalletID) as TotalJoined");
                        $this->db->from('tbl_users_wallet WC,tbl_users U,sports_contest C,tbl_entity E');
                        $this->db->where("U.UserID", "WC.UserID", FALSE);
                        $this->db->where("WC.EntityID", "C.ContestID", FALSE);
                        $this->db->where("E.EntityID", "C.ContestID", FALSE);
                        $this->db->where("C.LeagueType", "Dfs");
                        $this->db->where("WC.Narration", "Join Contest");
                        $this->db->where("C.ContestID", $ContestRow['ContestID']);
                        if (!empty($Where['FromDate'])) {
                            $this->db->where("DATE(WC.EntryDate) >=", $Where['FromDate']);
                        }
                        if (!empty($Where['ToDate'])) {
                            $this->db->where("DATE(WC.EntryDate) <=", $Where['ToDate']);
                        }
                        $this->db->where("U.UserTypeID !=", 3);
                        $this->db->where("E.StatusID", 5);
                        $Query = $this->db->get();
                        if ($Query->num_rows() > 0) {
                            $Contests = $Query->row_array();
                            $Return['TotalDepositCollection'] = $Contests['DepositAmount'];
                            $Return['TotalCashBonusCollection'] = $Contests['CashBonus'];
                            $Return['TotalRealUserJoined'] = $Contests['TotalJoined'];
                        }

                        /** contest total virtual user joined * */
                        $this->db->select("COUNT(JC.UserID) as TotalVirtualUserJoined,C.ContestType,C.ContestName,C.WinningAmount,C.ContestSize,C.EntryFee");
                        $this->db->from('sports_contest_join JC,tbl_users U,sports_contest C,tbl_entity E');
                        $this->db->where("U.UserID", "JC.UserID", FALSE);
                        $this->db->where("JC.ContestID", "C.ContestID", FALSE);
                        $this->db->where("E.EntityID", "C.ContestID", FALSE);
                        $this->db->where("C.LeagueType", "Dfs");
                        $this->db->where("C.ContestID", $ContestRow['ContestID']);
                        if (!empty($Where['FromDate'])) {
                            $this->db->where("DATE(E.EntryDate) >=", $Where['FromDate']);
                        }
                        if (!empty($Where['ToDate'])) {
                            $this->db->where("DATE(E.EntryDate) <=", $Where['ToDate']);
                        }
                        $this->db->where("U.UserTypeID", 3);
                        $this->db->where("E.StatusID", 5);
                        $Query = $this->db->get();
                        if ($Query->num_rows() > 0) {
                            $Contests = $Query->row_array();
                            $Return['TotalVirtualUserJoined'] = $Contests['TotalVirtualUserJoined'];
                            $Return['ContestDetails']['ContestType'] = $Contests['ContestType'];
                            $Return['ContestDetails']['ContestName'] = $Contests['ContestName'];
                            $Return['ContestDetails']['WinningAmount'] = $Contests['WinningAmount'];
                            $Return['ContestDetails']['ContestSize'] = $Contests['ContestSize'];
                            $Return['ContestDetails']['EntryFee'] = $Contests['EntryFee'];
                        }

                        /** contest total winning * */
                        $this->db->select("SUM(UserWinningAmount) as UserWinningAmount");
                        $this->db->from('sports_contest_join JC,tbl_users U,sports_contest C,tbl_entity E');
                        $this->db->where("U.UserID", "JC.UserID", FALSE);
                        $this->db->where("JC.ContestID", "C.ContestID", FALSE);
                        $this->db->where("E.EntityID", "C.ContestID", FALSE);
                        $this->db->where("C.LeagueType", "Dfs");
                        $this->db->where("C.ContestID", $ContestRow['ContestID']);
                        if (!empty($Where['FromDate'])) {
                            $this->db->where("DATE(E.EntryDate) >=", $Where['FromDate']);
                        }
                        if (!empty($Where['ToDate'])) {
                            $this->db->where("DATE(E.EntryDate) <=", $Where['ToDate']);
                        }
                        $this->db->where("U.UserTypeID !=", 3);
                        $this->db->where("E.StatusID", 5);
                        $Query = $this->db->get();
                        if ($Query->num_rows() > 0) {
                            $Contests = $Query->row_array();
                            $Return['TotalRealUserWinningCollection'] = $Contests['UserWinningAmount'];
                        }
                        $ProfitLoss = $Return['TotalDepositCollection'] - $Return['TotalRealUserWinningCollection'];
                        if ($ProfitLoss > 0) {
                            $Return['visible'] = 'Profit';
                            $Return['NetProfit'] = $ProfitLoss;
                            $Return['Profit'] = ($Return['TotalDepositCollection'] - $Return['TotalRealUserWinningCollection']) - round((($Return['TotalDepositCollection'] - $Return['TotalRealUserWinningCollection']) * 2.5 / 100), 2) - $Return['TotalCashBonusCollection'];
                        } else {
                            $Return['visible'] = 'Loss';
                            $Return['loss'] = round(abs($ProfitLoss) + ((abs($ProfitLoss) * 2.5) / 100) + $Return['TotalCashBonusCollection'], 2);
                        }
                        $ReturnAll[] = $Return;
            }
        }
        return $ReturnAll;
    }

    function getContestAnalysisReport($Where = array(), $SeriesID = '', $MatchID = '') {

        $Return = array();
        $Return['TotalDepositCollection'] = 0;
        $Return['TotalCashBonusCollection'] = 0;
        $Return['TotalRealUserWinningCollection'] = 0;
        $Return['TotalRealUserJoined'] = 0;
        $Return['TotalVirtualUserJoined'] = 0;
        $Return['Profit'] = 0;
        $Return['NetProfit'] = 0;
        $Return['loss'] = 0;
        $Return['MatchDetails'] = array();
        $Return['ContestDetails'] = array();

        /** contest total deposit,bonus * */
        $this->db->select("(SUM(WC.WalletAmount) + SUM(WC.WinningAmount)) as DepositAmount,SUM(WC.CashBonus) as CashBonus,"
                . "COUNT(WC.WalletID) as TotalJoined");
        $this->db->from('tbl_users_wallet WC,tbl_users U,sports_contest C,tbl_entity E');
        $this->db->where("U.UserID", "WC.UserID", FALSE);
        $this->db->where("WC.EntityID", "C.ContestID", FALSE);
        $this->db->where("E.EntityID", "C.ContestID", FALSE);
        $this->db->where("C.LeagueType", "Dfs");
        $this->db->where("WC.Narration", "Join Contest");
        //$this->db->where("C.ContestID", 109726);
        if (!empty($Where['FromDate'])) {
            $this->db->where("DATE(WC.EntryDate) >=", $Where['FromDate']);
        }
        if (!empty($Where['ToDate'])) {
            $this->db->where("DATE(WC.EntryDate) <=", $Where['ToDate']);
        }
        $this->db->where("C.ContestType", $Where['ContestType']);
        $this->db->where("C.ContestName", trim($Where['ContestName']));
        if (!empty($SeriesID)) {
            $this->db->where("C.SeriesID", $SeriesID);
        }

        if (!empty($MatchID)) {
            $this->db->where("C.MatchID", $MatchID);
        }
        $this->db->where("U.UserTypeID !=", 3);
        $this->db->where("E.StatusID", 5);
        $Query = $this->db->get();
        if ($Query->num_rows() > 0) {
            $Contests = $Query->row_array();
            $Return['TotalDepositCollection'] = $Contests['DepositAmount'];
            $Return['TotalCashBonusCollection'] = $Contests['CashBonus'];
            $Return['TotalRealUserJoined'] = $Contests['TotalJoined'];
        }

        /** contest total virtual user joined * */
        $this->db->select("COUNT(JC.UserID) as TotalVirtualUserJoined,C.ContestType,C.ContestName");
        $this->db->from('sports_contest_join JC,tbl_users U,sports_contest C,tbl_entity E');
        $this->db->where("U.UserID", "JC.UserID", FALSE);
        $this->db->where("JC.ContestID", "C.ContestID", FALSE);
        $this->db->where("E.EntityID", "C.ContestID", FALSE);
        $this->db->where("C.LeagueType", "Dfs");
        //$this->db->where("C.ContestID", 109726);
        if (!empty($Where['FromDate'])) {
            $this->db->where("DATE(E.EntryDate) >=", $Where['FromDate']);
        }
        if (!empty($Where['ToDate'])) {
            $this->db->where("DATE(E.EntryDate) <=", $Where['ToDate']);
        }
        $this->db->where("C.ContestType", $Where['ContestType']);
        $this->db->where("C.ContestName", trim($Where['ContestName']));
        if (!empty($SeriesID)) {
            $this->db->where("C.SeriesID", $SeriesID);
        }

        if (!empty($MatchID)) {
            $this->db->where("C.MatchID", $MatchID);
        }
        $this->db->where("U.UserTypeID", 3);
        $this->db->where("E.StatusID", 5);
        $Query = $this->db->get();
        if ($Query->num_rows() > 0) {
            $Contests = $Query->row_array();
            $Return['TotalVirtualUserJoined'] = $Contests['TotalVirtualUserJoined'];
            $Return['ContestDetails']['ContestType'] = $Contests['ContestType'];
            $Return['ContestDetails']['ContestName'] = $Contests['ContestName'];
        }

        /** contest total winning * */
        $this->db->select("SUM(UserWinningAmount) as UserWinningAmount");
        $this->db->from('sports_contest_join JC,tbl_users U,sports_contest C,tbl_entity E');
        $this->db->where("U.UserID", "JC.UserID", FALSE);
        $this->db->where("JC.ContestID", "C.ContestID", FALSE);
        $this->db->where("E.EntityID", "C.ContestID", FALSE);
        $this->db->where("C.LeagueType", "Dfs");
        //$this->db->where("C.ContestID", 109726);
        if (!empty($Where['FromDate'])) {
            $this->db->where("DATE(E.EntryDate) >=", $Where['FromDate']);
        }
        if (!empty($Where['ToDate'])) {
            $this->db->where("DATE(E.EntryDate) <=", $Where['ToDate']);
        }
        $this->db->where("C.ContestType", $Where['ContestType']);
        $this->db->where("C.ContestName", trim($Where['ContestName']));
        if (!empty($SeriesID)) {
            $this->db->where("C.SeriesID", $SeriesID);
        }

        if (!empty($MatchID)) {
            $this->db->where("C.MatchID", $MatchID);
        }
        $this->db->where("U.UserTypeID !=", 3);
        $this->db->where("E.StatusID", 5);
        $Query = $this->db->get();
        if ($Query->num_rows() > 0) {
            $Contests = $Query->row_array();
            $Return['TotalRealUserWinningCollection'] = $Contests['UserWinningAmount'];
        }

        $ProfitLoss = $Return['TotalDepositCollection'] - $Return['TotalRealUserWinningCollection'];
        if ($ProfitLoss > 0) {
            $Return['NetProfit'] = $ProfitLoss;
            $Return['Profit'] = ($Return['TotalDepositCollection'] - $Return['TotalRealUserWinningCollection']) - round((($Return['TotalDepositCollection'] - $Return['TotalRealUserWinningCollection']) * 2.5 / 100), 2) - $Return['TotalCashBonusCollection'];
        } else {
            $Return['loss'] = round(abs($ProfitLoss) + ((abs($ProfitLoss) * 2.5) / 100) + $Return['TotalCashBonusCollection'], 2);
        }

        if (!empty($SeriesID)) {
            $this->db->select('M.MatchID,S.SeriesName,CONCAT(TL.TeamName," Vs ",TV.TeamName) as MatchName,DATE_FORMAT(CONVERT_TZ(M.MatchStartDateTime,"+00:00","' . DEFAULT_TIMEZONE . '"), "' . DATE_FORMAT . '") MatchStartDateTime');
            $this->db->from('sports_matches M,tbl_entity E,sports_series S,sports_teams TL, sports_teams TV');
            $this->db->where("E.EntityID", "M.MatchID", FALSE);
            $this->db->where("S.SeriesID", "M.SeriesID", FALSE);
            $this->db->where("M.TeamIDLocal", "TL.TeamID", FALSE);
            $this->db->where("M.TeamIDVisitor", "TV.TeamID", FALSE);
            $this->db->where("M.SeriesID", $SeriesID);
            if (!empty($MatchID)) {
                $this->db->where("M.MatchID", $MatchID);
            }
            $this->db->where("E.StatusID", 5);
            $this->db->limit(1);
            $Query = $this->db->get();
            if ($Query->num_rows() > 0) {
                $Return['MatchDetails'] = $Query->row_array();
            }
            $Return['MatchID'] = $MatchID;
        }
        return $Return;
    }

    function getVirtualContestAnalysisReport($Where = array(), $SeriesID = '', $MatchID = '') {

        $Return = array();
        $Return['TotalDepositCollection'] = 0;
        $Return['TotalCashBonusCollection'] = 0;
        $Return['TotalRealUserWinningCollection'] = 0;
        $Return['TotalRealUserJoined'] = 0;
        $Return['TotalVirtualUserJoined'] = 0;
        $Return['Profit'] = 0;
        $Return['NetProfit'] = 0;
        $Return['loss'] = 0;
        $Return['MatchDetails'] = array();
        $Return['ContestDetails'] = array();

        /** contest total deposit,bonus * */
        $this->db->select("(SUM(WC.WalletAmount) + SUM(WC.WinningAmount)) as DepositAmount,SUM(WC.CashBonus) as CashBonus,"
                . "COUNT(WC.WalletID) as TotalJoined");
        $this->db->from('tbl_users_wallet WC,tbl_users U,sports_contest C,tbl_entity E');
        $this->db->where("U.UserID", "WC.UserID", FALSE);
        $this->db->where("WC.EntityID", "C.ContestID", FALSE);
        $this->db->where("E.EntityID", "C.ContestID", FALSE);
        $this->db->where("C.LeagueType", "Dfs");
        $this->db->where("C.WinningType", "Free Join Contest");
        $this->db->where("WC.Narration", "Join Contest");
        //$this->db->where("C.ContestID", 109726);
        if (!empty($Where['FromDate'])) {
            $this->db->where("DATE(WC.EntryDate) >=", $Where['FromDate']);
        }
        if (!empty($Where['ToDate'])) {
            $this->db->where("DATE(WC.EntryDate) <=", $Where['ToDate']);
        }
        $this->db->where("C.ContestType", $Where['ContestType']);
        $this->db->where("C.ContestName", trim($Where['ContestName']));
        if (!empty($SeriesID)) {
            $this->db->where("C.SeriesID", $SeriesID);
        }

        if (!empty($MatchID)) {
            $this->db->where("C.MatchID", $MatchID);
        }
        $this->db->where("U.UserTypeID !=", 3);
        $this->db->where("E.StatusID", 5);
        $Query = $this->db->get();
        if ($Query->num_rows() > 0) {
            $Contests = $Query->row_array();
            $Return['TotalDepositCollection'] = $Contests['DepositAmount'];
            $Return['TotalCashBonusCollection'] = $Contests['CashBonus'];
            $Return['TotalRealUserJoined'] = $Contests['TotalJoined'];
        }

        /** contest total virtual user joined * */
        $this->db->select("COUNT(JC.UserID) as TotalVirtualUserJoined,C.ContestType,C.ContestName");
        $this->db->from('sports_contest_join JC,tbl_users U,sports_contest C,tbl_entity E');
        $this->db->where("U.UserID", "JC.UserID", FALSE);
        $this->db->where("JC.ContestID", "C.ContestID", FALSE);
        $this->db->where("E.EntityID", "C.ContestID", FALSE);
        $this->db->where("C.LeagueType", "Dfs");
        $this->db->where("C.WinningType", "Free Join Contest");

        //$this->db->where("C.ContestID", 109726);
        if (!empty($Where['FromDate'])) {
            $this->db->where("DATE(E.EntryDate) >=", $Where['FromDate']);
        }
        if (!empty($Where['ToDate'])) {
            $this->db->where("DATE(E.EntryDate) <=", $Where['ToDate']);
        }
        $this->db->where("C.ContestType", $Where['ContestType']);
        $this->db->where("C.ContestName", trim($Where['ContestName']));
        if (!empty($SeriesID)) {
            $this->db->where("C.SeriesID", $SeriesID);
        }

        if (!empty($MatchID)) {
            $this->db->where("C.MatchID", $MatchID);
        }
        $this->db->where("U.UserTypeID", 3);
        $this->db->where("E.StatusID", 5);
        $Query = $this->db->get();
        if ($Query->num_rows() > 0) {
            $Contests = $Query->row_array();
            $Return['TotalVirtualUserJoined'] = $Contests['TotalVirtualUserJoined'];
            $Return['ContestDetails']['ContestType'] = $Contests['ContestType'];
            $Return['ContestDetails']['ContestName'] = $Contests['ContestName'];
        }

        /** contest total winning * */
        $this->db->select("SUM(UserWinningAmount) as UserWinningAmount");
        $this->db->from('sports_contest_join JC,tbl_users U,sports_contest C,tbl_entity E');
        $this->db->where("U.UserID", "JC.UserID", FALSE);
        $this->db->where("JC.ContestID", "C.ContestID", FALSE);
        $this->db->where("E.EntityID", "C.ContestID", FALSE);
        $this->db->where("C.LeagueType", "Dfs");
        $this->db->where("C.WinningType", "Free Join Contest");
        
        //$this->db->where("C.ContestID", 109726);
        if (!empty($Where['FromDate'])) {
            $this->db->where("DATE(E.EntryDate) >=", $Where['FromDate']);
        }
        if (!empty($Where['ToDate'])) {
            $this->db->where("DATE(E.EntryDate) <=", $Where['ToDate']);
        }
        $this->db->where("C.ContestType", $Where['ContestType']);
        $this->db->where("C.ContestName", trim($Where['ContestName']));
        if (!empty($SeriesID)) {
            $this->db->where("C.SeriesID", $SeriesID);
        }

        if (!empty($MatchID)) {
            $this->db->where("C.MatchID", $MatchID);
        }
        $this->db->where("U.UserTypeID !=", 3);
        $this->db->where("E.StatusID", 5);
        $Query = $this->db->get();
        if ($Query->num_rows() > 0) {
            $Contests = $Query->row_array();
            $Return['TotalRealUserWinningCollection'] = $Contests['UserWinningAmount'];
        }

        $ProfitLoss = $Return['TotalDepositCollection'] - $Return['TotalRealUserWinningCollection'];
        if ($ProfitLoss > 0) {
            $Return['NetProfit'] = $ProfitLoss;
            $Return['Profit'] = ($Return['TotalDepositCollection'] - $Return['TotalRealUserWinningCollection']) - round((($Return['TotalDepositCollection'] - $Return['TotalRealUserWinningCollection']) * 2.5 / 100), 2) - $Return['TotalCashBonusCollection'];
        } else {
            $Return['loss'] = round(abs($ProfitLoss) + ((abs($ProfitLoss) * 2.5) / 100) + $Return['TotalCashBonusCollection'], 2);
        }

        if (!empty($SeriesID)) {
            $this->db->select('M.MatchID,S.SeriesName,CONCAT(TL.TeamName," Vs ",TV.TeamName) as MatchName,DATE_FORMAT(CONVERT_TZ(M.MatchStartDateTime,"+00:00","' . DEFAULT_TIMEZONE . '"), "' . DATE_FORMAT . '") MatchStartDateTime');
            $this->db->from('sports_matches M,tbl_entity E,sports_series S,sports_teams TL, sports_teams TV');
            $this->db->where("E.EntityID", "M.MatchID", FALSE);
            $this->db->where("S.SeriesID", "M.SeriesID", FALSE);
            $this->db->where("M.TeamIDLocal", "TL.TeamID", FALSE);
            $this->db->where("M.TeamIDVisitor", "TV.TeamID", FALSE);
            $this->db->where("M.SeriesID", $SeriesID);
            if (!empty($MatchID)) {
                $this->db->where("M.MatchID", $MatchID);
            }
            $this->db->where("E.StatusID", 5);
            $this->db->limit(1);
            $Query = $this->db->get();
            if ($Query->num_rows() > 0) {
                $Return['MatchDetails'] = $Query->row_array();
            }
            $Return['MatchID'] = $MatchID;
        }
        return $Return;
    }

    function getPrivateContestAnalysisReport($Where = array(), $SeriesID = '', $MatchID = '') {

        $Return = array();
        $Return['TotalDepositCollection'] = 0;
        $Return['TotalCashBonusCollection'] = 0;
        $Return['TotalRealUserWinningCollection'] = 0;
        $Return['TotalRealUserJoined'] = 0;
        $Return['TotalVirtualUserJoined'] = 0;
        $Return['Profit'] = 0;
        $Return['NetProfit'] = 0;
        $Return['loss'] = 0;
        $Return['MatchDetails'] = array();
        $Return['ContestDetails'] = array();

        /** contest total deposit,bonus * */
        $this->db->select("(SUM(WC.WalletAmount) + SUM(WC.WinningAmount)) as DepositAmount,SUM(WC.CashBonus) as CashBonus,"
                . "COUNT(WC.WalletID) as TotalJoined");
        $this->db->from('tbl_users_wallet WC,tbl_users U,sports_contest C,tbl_entity E');
        $this->db->where("U.UserID", "WC.UserID", FALSE);
        $this->db->where("WC.EntityID", "C.ContestID", FALSE);
        $this->db->where("E.EntityID", "C.ContestID", FALSE);
        $this->db->where("C.LeagueType", "Dfs");
        $this->db->where("WC.Narration", "Join Contest");
        $this->db->where("C.Privacy", "Yes");

        //$this->db->where("C.ContestID", 109726);
        if (!empty($Where['FromDate'])) {
            $this->db->where("DATE(WC.EntryDate) >=", $Where['FromDate']);
        }
        if (!empty($Where['ToDate'])) {
            $this->db->where("DATE(WC.EntryDate) <=", $Where['ToDate']);
        }
        $this->db->where("C.ContestType", $Where['ContestType']);
        $this->db->where("C.ContestName", trim($Where['ContestName']));
        if (!empty($SeriesID)) {
            $this->db->where("C.SeriesID", $SeriesID);
        }

        if (!empty($MatchID)) {
            $this->db->where("C.MatchID", $MatchID);
        }
        $this->db->where("U.UserTypeID !=", 3);
        $this->db->where("E.StatusID", 5);
        $Query = $this->db->get();
        if ($Query->num_rows() > 0) {
            $Contests = $Query->row_array();
            $Return['TotalDepositCollection'] = $Contests['DepositAmount'];
            $Return['TotalCashBonusCollection'] = $Contests['CashBonus'];
            $Return['TotalRealUserJoined'] = $Contests['TotalJoined'];
        }

        /** contest total virtual user joined * */
        $this->db->select("COUNT(JC.UserID) as TotalVirtualUserJoined,C.ContestType,C.ContestName");
        $this->db->from('sports_contest_join JC,tbl_users U,sports_contest C,tbl_entity E');
        $this->db->where("U.UserID", "JC.UserID", FALSE);
        $this->db->where("JC.ContestID", "C.ContestID", FALSE);
        $this->db->where("E.EntityID", "C.ContestID", FALSE);
        $this->db->where("C.LeagueType", "Dfs");
        $this->db->where("C.Privacy", "Yes");

        //$this->db->where("C.ContestID", 109726);
        if (!empty($Where['FromDate'])) {
            $this->db->where("DATE(E.EntryDate) >=", $Where['FromDate']);
        }
        if (!empty($Where['ToDate'])) {
            $this->db->where("DATE(E.EntryDate) <=", $Where['ToDate']);
        }
        $this->db->where("C.ContestType", $Where['ContestType']);
        $this->db->where("C.ContestName", trim($Where['ContestName']));
        if (!empty($SeriesID)) {
            $this->db->where("C.SeriesID", $SeriesID);
        }

        if (!empty($MatchID)) {
            $this->db->where("C.MatchID", $MatchID);
        }
        $this->db->where("U.UserTypeID", 3);
        $this->db->where("E.StatusID", 5);
        $Query = $this->db->get();
        if ($Query->num_rows() > 0) {
            $Contests = $Query->row_array();
            $Return['TotalVirtualUserJoined'] = $Contests['TotalVirtualUserJoined'];
            $Return['ContestDetails']['ContestType'] = $Contests['ContestType'];
            $Return['ContestDetails']['ContestName'] = $Contests['ContestName'];
        }

        /** contest total winning * */
        $this->db->select("SUM(UserWinningAmount) as UserWinningAmount");
        $this->db->from('sports_contest_join JC,tbl_users U,sports_contest C,tbl_entity E');
        $this->db->where("U.UserID", "JC.UserID", FALSE);
        $this->db->where("JC.ContestID", "C.ContestID", FALSE);
        $this->db->where("E.EntityID", "C.ContestID", FALSE);
        $this->db->where("C.LeagueType", "Dfs");
        $this->db->where("C.Privacy", "Yes");

        //$this->db->where("C.ContestID", 109726);
        if (!empty($Where['FromDate'])) {
            $this->db->where("DATE(E.EntryDate) >=", $Where['FromDate']);
        }
        if (!empty($Where['ToDate'])) {
            $this->db->where("DATE(E.EntryDate) <=", $Where['ToDate']);
        }
        $this->db->where("C.ContestType", $Where['ContestType']);
        $this->db->where("C.ContestName", trim($Where['ContestName']));
        if (!empty($SeriesID)) {
            $this->db->where("C.SeriesID", $SeriesID);
        }

        if (!empty($MatchID)) {
            $this->db->where("C.MatchID", $MatchID);
        }
        $this->db->where("U.UserTypeID !=", 3);
        $this->db->where("E.StatusID", 5);
        $Query = $this->db->get();
        if ($Query->num_rows() > 0) {
            $Contests = $Query->row_array();
            $Return['TotalRealUserWinningCollection'] = $Contests['UserWinningAmount'];
        }

        $ProfitLoss = $Return['TotalDepositCollection'] - $Return['TotalRealUserWinningCollection'];
        if ($ProfitLoss > 0) {
            $Return['NetProfit'] = $ProfitLoss;
            $Return['Profit'] = ($Return['TotalDepositCollection'] - $Return['TotalRealUserWinningCollection']) - round((($Return['TotalDepositCollection'] - $Return['TotalRealUserWinningCollection']) * 2.5 / 100), 2) - $Return['TotalCashBonusCollection'];
        } else {
            $Return['loss'] = round(abs($ProfitLoss) + ((abs($ProfitLoss) * 2.5) / 100) + $Return['TotalCashBonusCollection'], 2);
        }

        if (!empty($SeriesID)) {
            $this->db->select('M.MatchID,S.SeriesName,CONCAT(TL.TeamName," Vs ",TV.TeamName) as MatchName,DATE_FORMAT(CONVERT_TZ(M.MatchStartDateTime,"+00:00","' . DEFAULT_TIMEZONE . '"), "' . DATE_FORMAT . '") MatchStartDateTime');
            $this->db->from('sports_matches M,tbl_entity E,sports_series S,sports_teams TL, sports_teams TV');
            $this->db->where("E.EntityID", "M.MatchID", FALSE);
            $this->db->where("S.SeriesID", "M.SeriesID", FALSE);
            $this->db->where("M.TeamIDLocal", "TL.TeamID", FALSE);
            $this->db->where("M.TeamIDVisitor", "TV.TeamID", FALSE);
            $this->db->where("M.SeriesID", $SeriesID);
            if (!empty($MatchID)) {
                $this->db->where("M.MatchID", $MatchID);
            }
            $this->db->where("E.StatusID", 5);
            $this->db->limit(1);
            $Query = $this->db->get();
            if ($Query->num_rows() > 0) {
                $Return['MatchDetails'] = $Query->row_array();
            }
            $Return['MatchID'] = $MatchID;
        }
        return $Return;
    }

    function topwinnerloserMatchWise() {
        $this->db->select('M.MatchID,S.SeriesName,CONCAT(TL.TeamName," Vs ",TV.TeamName) as MatchName,DATE_FORMAT(CONVERT_TZ(M.MatchStartDateTime,"+00:00","' . DEFAULT_TIMEZONE . '"), "' . DATE_FORMAT . '") MatchStartDateTime');
        $this->db->from('sports_matches M,tbl_entity E,sports_series S,sports_teams TL, sports_teams TV');
        $this->db->where("E.EntityID", "M.MatchID", FALSE);
        $this->db->where("S.SeriesID", "M.SeriesID", FALSE);
        $this->db->where("M.TeamIDLocal", "TL.TeamID", FALSE);
        $this->db->where("M.TeamIDVisitor", "TV.TeamID", FALSE);
        if (!empty($Where['DataFilter'])) {
            if ($Where['DataFilter'] == "Today") {
                $this->db->where("DATE(M.MatchStartDateTime)", date('Y-m-d'));
            } else if ($Where['DataFilter'] == "Last7Days") {
                $this->db->where("DATE(M.MatchStartDateTime) <=", date('Y-m-d'));
                $this->db->where("DATE(M.MatchStartDateTime) >=", date('Y-m-d', strtotime('-7 days')));
            } else if ($Where['DataFilter'] == "Last15Days") {
                $this->db->where("DATE(M.MatchStartDateTime) <=", date('Y-m-d'));
                $this->db->where("DATE(M.MatchStartDateTime) >=", date('Y-m-d', strtotime('-15 days')));
            } else if ($Where['DataFilter'] == "Last30Days") {
                $this->db->where("DATE(M.MatchStartDateTime) <=", date('Y-m-d'));
                $this->db->where("DATE(M.MatchStartDateTime) >=", date('Y-m-d', strtotime('-1 month')));
            } else if ($Where['DataFilter'] == "Last3Months") {
                $this->db->where("DATE(M.MatchStartDateTime) <=", date('Y-m-d'));
                $this->db->where("DATE(M.MatchStartDateTime) >=", date('Y-m-d', strtotime('-3 month')));
            }
        } else {
            if (!empty($Where['FromDate'])) {
                $this->db->where("DATE(M.MatchStartDateTime) >=", $Where['FromDate']);
            }
            if (!empty($Where['ToDate'])) {
                $this->db->where("DATE(M.MatchStartDateTime) <=", $Where['ToDate']);
            }
        }
        $this->db->where("E.StatusID", 5);
        $Query = $this->db->get();
        $MatchReports = array();
        $Matches = $Query->result_array();
        if ($Query->num_rows() > 0) {
            $Matches = $Query->result_array();
            if (!empty($Matches)) {
                foreach ($Matches as $Value) {
                    if ($Where['UserType'] == "TopWinners") {
                        /** top 5 winners * */
                        $this->db->select("SUM(JC.UserWinningAmount) as TotalWinning,JC.UserID,U.Email,U.PhoneNumber,U.FirstName,U.Username");
                        $this->db->from('sports_contest_join JC,tbl_users U,sports_contest C');
                        $this->db->where("U.UserID", "JC.UserID", FALSE);
                        $this->db->where("JC.ContestID", "C.ContestID", FALSE);
                        $this->db->where("C.LeagueType", "Dfs");
                        $this->db->where("U.UserTypeID !=", 3);
                        $this->db->where("JC.MatchID", $Value['MatchID']);
                        $this->db->having("TotalWinning > 0");
                        $this->db->group_by("JC.UserID");
                        $this->db->order_by("TotalWinning", "DESC");
                        $this->db->limit(50);
                        $Query = $this->db->get();
                        if ($Query->num_rows() > 0) {
                            $Return['UsersList'] = $Query->result_array();
                        }
                    }
                    if ($Where['UserType'] == "TopLosers") {
                        /** top 5 loosers * */
                        $this->db->select("COUNT(UserTeamID) as TotalJoined,SUM(JC.UserWinningAmount) as TotalWinning,JC.UserID,U.Email,U.PhoneNumber,U.FirstName,U.Username");
                        $this->db->from('sports_contest_join JC,tbl_users U,sports_contest C');
                        $this->db->where("U.UserID", "JC.UserID", FALSE);
                        $this->db->where("JC.ContestID", "C.ContestID", FALSE);
                        $this->db->where("C.LeagueType", "Dfs");
                        $this->db->where("U.UserTypeID !=", 3);
                        $this->db->where("JC.MatchID", $Value['MatchID']);
                        $this->db->having("TotalWinning <= 0");
                        $this->db->group_by("JC.UserID");
                        $this->db->order_by("TotalWinning", "ASC");
                        $this->db->order_by("TotalJoined", "DESC");
                        $this->db->limit(50);
                        $Query = $this->db->get();
                        if ($Query->num_rows() > 0) {
                            $Return['UsersList'] = $Query->result_array();
                        }
                    }
                }
            }
        }
        return $Return;
    }

    function getUserRegisterReport($Where = array()) {
        $this->db->select('COUNT(U.UserID) AS TotalUsers,DATE_FORMAT(E.EntryDate,"%d-%b-%Y") as EntryDate ');
        $this->db->from('tbl_users U, tbl_entity E');
        $this->db->where("E.EntityID", "U.UserID", FALSE);
        if (!empty($Where['DataFilter'])) {
            if ($Where['DataFilter'] == "Today") {
                $this->db->where("DATE(E.EntryDate)", date('Y-m-d'));
            } elseif ($Where['DataFilter'] == "Yesterday") {
                $this->db->where("DATE(E.EntryDate)", date('Y-m-d', strtotime('-1 days')));
            } else if ($Where['DataFilter'] == "Last7Days") {
                $this->db->where("DATE(E.EntryDate) <=", date('Y-m-d'));
                $this->db->where("DATE(E.EntryDate) >=", date('Y-m-d', strtotime('-7 days')));
            } else if ($Where['DataFilter'] == "Last15Days") {
                $this->db->where("DATE(E.EntryDate) <=", date('Y-m-d'));
                $this->db->where("DATE(E.EntryDate) >=", date('Y-m-d', strtotime('-15 days')));
            } else if ($Where['DataFilter'] == "Last30Days") {
                $this->db->where("DATE(E.EntryDate) <=", date('Y-m-d'));
                $this->db->where("DATE(E.EntryDate) >=", date('Y-m-d', strtotime('-1 month')));
            } else if ($Where['DataFilter'] == "Last3Months") {
                $this->db->where("DATE(E.EntryDate) <=", date('Y-m-d'));
                $this->db->where("DATE(E.EntryDate) >=", date('Y-m-d', strtotime('-3 month')));
            } else {
                if (!empty($Where['FromDate'])) {
                    $this->db->where("DATE(E.EntryDate) >=", $Where['FromDate']);
                }
                if (!empty($Where['ToDate'])) {
                    $this->db->where("DATE(E.EntryDate) <=", $Where['ToDate']);
                }
            }
        } else {
            if (!empty($Where['FromDate'])) {
                $this->db->where("DATE(E.EntryDate) >=", $Where['FromDate']);
            }
            if (!empty($Where['ToDate'])) {
                $this->db->where("DATE(E.EntryDate) <=", $Where['ToDate']);
            }
        }
        $this->db->where("E.StatusID", 2);
        $this->db->where("U.UserTypeID", 2);
        $this->db->group_by('DATE( E.EntryDate )');
        $this->db->order_by('EntryDate', 'ASC');
        $Query = $this->db->get();
        $Users = $Query->result_array();
        return $Users;
    }

    function getUserJoinedFeeReport($Where = array()) {
        $Return = array();
        $DateRange = "";

        if ($Where['EntryFeeRange'] == "Practice" || $Where['EntryFeeRange'] == "Free") {
            $this->db->select('COUNT(DISTINCT(U.UserID)) AS TotalUsers,DATE_FORMAT(E.MatchStartDateTime,"%d-%b-%Y") as EntryDate ');
            $this->db->from('sports_contest C,tbl_users U,sports_matches E,sports_contest_join JC');
            $this->db->where("E.MatchID", "C.MatchID", FALSE);
            $this->db->where("JC.UserID", "U.UserID", FALSE);
            $this->db->where("JC.ContestID", "C.ContestID", FALSE);
            if (!empty($Where['DataFilter'])) {
                if ($Where['DataFilter'] == "Today") {
                    $this->db->where("DATE(E.MatchStartDateTime)", date('Y-m-d'));
                    $DateRange = date('Y-m-d');
                } elseif ($Where['DataFilter'] == "Yesterday") {
                    $this->db->where("DATE(E.MatchStartDateTime)", date('Y-m-d', strtotime('-1 days')));
                    $DateRange = date('Y-m-d', strtotime('-1 days'));
                } else if ($Where['DataFilter'] == "Last7Days") {
                    $this->db->where("DATE(E.MatchStartDateTime) <=", date('Y-m-d'));
                    $this->db->where("DATE(E.MatchStartDateTime) >=", date('Y-m-d', strtotime('-7 days')));
                    $DateRange = date('Y-m-d') . ' To ' . date('Y-m-d', strtotime('-7 days'));
                } else if ($Where['DataFilter'] == "Last15Days") {
                    $this->db->where("DATE(E.MatchStartDateTime) <=", date('Y-m-d'));
                    $this->db->where("DATE(E.MatchStartDateTime) >=", date('Y-m-d', strtotime('-15 days')));
                    $DateRange = date('Y-m-d', strtotime('-15 days')) . ' To ' . date('Y-m-d');
                } else if ($Where['DataFilter'] == "Last30Days") {
                    $this->db->where("DATE(E.MatchStartDateTime) <=", date('Y-m-d'));
                    $this->db->where("DATE(E.MatchStartDateTime) >=", date('Y-m-d', strtotime('-1 month')));
                    $DateRange = date('Y-m-d', strtotime('-1 month')) . ' To ' . date('Y-m-d');
                } else if ($Where['DataFilter'] == "Last3Months") {
                    $this->db->where("DATE(E.MatchStartDateTime) <=", date('Y-m-d'));
                    $this->db->where("DATE(E.MatchStartDateTime) >=", date('Y-m-d', strtotime('-3 month')));
                    $DateRange = date('Y-m-d', strtotime('-3 month')) . ' To ' . date('Y-m-d');
                } else {
                    if (!empty($Where['FromDate'])) {
                        $this->db->where("DATE(E.MatchStartDateTime) >=", $Where['FromDate']);
                    }
                    if (!empty($Where['ToDate'])) {
                        $this->db->where("DATE(E.MatchStartDateTime) <=", $Where['ToDate']);
                    }
                    $DateRange = $Where['FromDate'] . ' To ' . $Where['ToDate'];
                }
            } else {
                if (!empty($Where['FromDate'])) {
                    $this->db->where("DATE(E.MatchStartDateTime) >=", $Where['FromDate']);
                }
                if (!empty($Where['ToDate'])) {
                    $this->db->where("DATE(E.MatchStartDateTime) <=", $Where['ToDate']);
                }
            }
            if (!empty($Where['EntryFeeRange'])) {
                if ($Where['EntryFeeRange'] == "Practice") {
                    $this->db->where("C.ContestType", 'Practice');
                    $Return['FilterText'] = 'Entry Range - Practice Date -' . $DateRange;
                } else if ($Where['EntryFeeRange'] == "Free") {
                    $this->db->where("C.ContestType !=", 'Practice');
                    $this->db->where("C.EntryFee", 0);
                    $Return['FilterText'] = 'Entry Range - Free Date -' . $DateRange;
                }
            }
            $this->db->where("U.UserTypeID", 2);
            $this->db->group_by('DATE(E.MatchStartDateTime)');
            $this->db->order_by('E.MatchStartDateTime', 'ASC');
            $Query = $this->db->get();
        } else {
            $this->db->select('COUNT(DISTINCT(U.UserID)) AS TotalUsers,DATE_FORMAT(E.EntryDate,"%d-%b-%Y") as EntryDate ');
            $this->db->from('sports_contest C,tbl_users U,tbl_users_wallet E');
            $this->db->where("E.UserID", "U.UserID", FALSE);
            $this->db->where("E.EntityID", "C.ContestID", FALSE);
            if (!empty($Where['DataFilter'])) {
                if ($Where['DataFilter'] == "Today") {
                    $this->db->where("DATE(E.EntryDate)", date('Y-m-d'));
                    $DateRange = date('Y-m-d');
                } elseif ($Where['DataFilter'] == "Yesterday") {
                    $this->db->where("DATE(E.EntryDate)", date('Y-m-d', strtotime('-1 days')));
                    $DateRange = date('Y-m-d', strtotime('-1 days'));
                } else if ($Where['DataFilter'] == "Last7Days") {
                    $this->db->where("DATE(E.EntryDate) <=", date('Y-m-d'));
                    $this->db->where("DATE(E.EntryDate) >=", date('Y-m-d', strtotime('-7 days')));
                    $DateRange = date('Y-m-d') . ' To ' . date('Y-m-d', strtotime('-7 days'));
                } else if ($Where['DataFilter'] == "Last15Days") {
                    $this->db->where("DATE(E.EntryDate) <=", date('Y-m-d'));
                    $this->db->where("DATE(E.EntryDate) >=", date('Y-m-d', strtotime('-15 days')));
                    $DateRange = date('Y-m-d', strtotime('-15 days')) . ' To ' . date('Y-m-d');
                } else if ($Where['DataFilter'] == "Last30Days") {
                    $this->db->where("DATE(E.EntryDate) <=", date('Y-m-d'));
                    $this->db->where("DATE(E.EntryDate) >=", date('Y-m-d', strtotime('-1 month')));
                    $DateRange = date('Y-m-d', strtotime('-1 month')) . ' To ' . date('Y-m-d');
                } else if ($Where['DataFilter'] == "Last3Months") {
                    $this->db->where("DATE(E.EntryDate) <=", date('Y-m-d'));
                    $this->db->where("DATE(E.EntryDate) >=", date('Y-m-d', strtotime('-3 month')));
                    $DateRange = date('Y-m-d', strtotime('-3 month')) . ' To ' . date('Y-m-d');
                } else {
                    if (!empty($Where['FromDate'])) {
                        $this->db->where("DATE(E.EntryDate) >=", $Where['FromDate']);
                    }
                    if (!empty($Where['ToDate'])) {
                        $this->db->where("DATE(E.EntryDate) <=", $Where['ToDate']);
                    }
                    $DateRange = $Where['FromDate'] . ' To ' . $Where['ToDate'];
                }
            } else {
                if (!empty($Where['FromDate'])) {
                    $this->db->where("DATE(E.EntryDate) >=", $Where['FromDate']);
                }
                if (!empty($Where['ToDate'])) {
                    $this->db->where("DATE(E.EntryDate) <=", $Where['ToDate']);
                }
            }
            if (!empty($Where['EntryFeeRange'])) {
                if ($Where['EntryFeeRange'] == "1-50") {
                    $Range = explode("-", $Where['EntryFeeRange']);
                    $this->db->where("C.EntryFee >=", $Range[0]);
                    $this->db->where("C.EntryFee <=", $Range[1]);
                    $Return['FilterText'] = 'Entry Range - Rs.1 - 50 Date -' . $DateRange;
                } else if ($Where['EntryFeeRange'] == "50-100") {
                    $Range = explode("-", $Where['EntryFeeRange']);
                    $this->db->where("C.EntryFee >=", $Range[0]);
                    $this->db->where("C.EntryFee <=", $Range[1]);
                    $Return['FilterText'] = 'Entry Range - Rs.51 - 100 Date -' . $DateRange;
                } else if ($Where['EntryFeeRange'] == "100-500") {
                    $Range = explode("-", $Where['EntryFeeRange']);
                    $this->db->where("C.EntryFee >=", $Range[0]);
                    $this->db->where("C.EntryFee <=", $Range[1]);
                    $Return['FilterText'] = 'Entry Range - Rs.101 - 500 Date -' . $DateRange;
                } else if ($Where['EntryFeeRange'] == "500-1000") {
                    $Range = explode("-", $Where['EntryFeeRange']);
                    $this->db->where("C.EntryFee >=", $Range[0]);
                    $this->db->where("C.EntryFee <=", $Range[1]);
                    $Return['FilterText'] = 'Entry Range - Rs.501 - 1000 Date -' . $DateRange;
                } else if ($Where['EntryFeeRange'] == "1001") {
                    $this->db->where("C.EntryFee >=", $Where['EntryFeeRange']);
                    $Return['FilterText'] = 'Entry Range - 1001 and above Date -' . $DateRange;
                }
            }
            $this->db->where("U.UserTypeID", 2);
            $this->db->where("E.Narration", "Join Contest");
            $this->db->group_by('DATE(E.EntryDate)');
            $this->db->order_by('E.EntryDate', 'ASC');
            $Query = $this->db->get();
        }


        $Return['TotalList'] = $Query->result_array();
        return $Return;
    }

    function getUserPlanningLifetimeReport($Where = array()) {
        $Return = array();
        $DateRange = "";
        $this->db->select('COUNT(DISTINCT(U.UserID)) AS TotalUsers,DATE_FORMAT(WC.EntryDate,"%d-%b-%Y") as EntryDate ');
        $this->db->from('tbl_users U,tbl_entity E,tbl_users_wallet WC');
        $this->db->where("E.EntityID", "U.UserID", FALSE);
        $this->db->where("WC.UserID", "U.UserID", FALSE);
        if (!empty($Where['DataFilter'])) {
            if ($Where['DataFilter'] == "Today") {
                $this->db->where("DATE(E.EntryDate)", date('Y-m-d'));
                $DateRange = date('Y-m-d');
            } elseif ($Where['DataFilter'] == "Yesterday") {
                $this->db->where("DATE(WC.EntryDate)", date('Y-m-d', strtotime('-1 days')));
                $DateRange = date('Y-m-d', strtotime('-1 days'));
            } else if ($Where['DataFilter'] == "Last7Days") {
                $this->db->where("DATE(WC.EntryDate) <=", date('Y-m-d'));
                $this->db->where("DATE(WC.EntryDate) >=", date('Y-m-d', strtotime('-7 days')));
                $DateRange = date('Y-m-d') . ' To ' . date('Y-m-d', strtotime('-7 days'));
            } else if ($Where['DataFilter'] == "Last15Days") {
                $this->db->where("DATE(WC.EntryDate) <=", date('Y-m-d'));
                $this->db->where("DATE(WC.EntryDate) >=", date('Y-m-d', strtotime('-15 days')));
                $DateRange = date('Y-m-d', strtotime('-15 days')) . ' To ' . date('Y-m-d');
            } else if ($Where['DataFilter'] == "Last30Days") {
                $this->db->where("DATE(WC.EntryDate) <=", date('Y-m-d'));
                $this->db->where("DATE(WC.EntryDate) >=", date('Y-m-d', strtotime('-1 month')));
                $DateRange = date('Y-m-d', strtotime('-1 month')) . ' To ' . date('Y-m-d');
            } else if ($Where['DataFilter'] == "Last3Months") {
                $this->db->where("DATE(WC.EntryDate) <=", date('Y-m-d'));
                $this->db->where("DATE(WC.EntryDate) >=", date('Y-m-d', strtotime('-3 month')));
                $DateRange = date('Y-m-d', strtotime('-3 month')) . ' To ' . date('Y-m-d');
            } else {
                if (!empty($Where['FromDate'])) {
                    $this->db->where("DATE(WC.EntryDate) >=", $Where['FromDate']);
                }
                if (!empty($Where['ToDate'])) {
                    $this->db->where("DATE(WC.EntryDate) <=", $Where['ToDate']);
                }
                $DateRange = $Where['FromDate'] . ' To ' . $Where['ToDate'];
            }
        } else {
            if (!empty($Where['FromDate'])) {
                $this->db->where("DATE(WC.EntryDate) >=", $Where['FromDate']);
            }
            if (!empty($Where['ToDate'])) {
                $this->db->where("DATE(WC.EntryDate) <=", $Where['ToDate']);
            }
        }
        $this->db->where("U.UserTypeID", 2);
        $this->db->where("E.StatusID", 2);
        $this->db->where("WC.Narration", "Join Contest");
        $this->db->where("WC.SportsType !=", "Football");
        $this->db->group_by('DATE(WC.EntryDate)');
        $this->db->order_by('WC.EntryDate', 'ASC');
        $Query = $this->db->get();
        $Return['TotalList'] = $Query->result_array();
        $Return['FilterText'] = $DateRange;
        return $Return;
    }

    function getTotalContestCollectionsCompleted($ContestGUID) {
        return $this->db->query('SELECT SUM(C.EntryFee) as TotalAmountReceived FROM sports_contest_completed C join sports_contest_join_completed J on C.ContestID = J.ContestID WHERE C.ContestGUID = "' . $ContestGUID . '"')->row()->TotalAmountReceived;
    }

    function getTotalWinningAmountCompleted($ContestGUID) {
        return $this->db->query('SELECT SUM(J.UserWinningAmount) as TotalWinningAmount FROM sports_contest_completed C join sports_contest_join_completed J on C.ContestID = J.ContestID WHERE C.ContestGUID = "' . $ContestGUID . '"')->row()->TotalWinningAmount;
    }

    /*
      Description: To get contest completed
     */

    function getContestsCompleted($Field = '', $Where = array(), $multiRecords = FALSE, $PageNo = 1, $PageSize = 15) {
        $Params = array();
        if (!empty($Field)) {
            $Params = array_map('trim', explode(',', $Field));
            $Field = '';
            $FieldArray = array(
                'MatchID' => 'M.MatchID',
                'MatchGUID' => 'M.MatchGUID',
                'StatusID' => 'E.StatusID',
                'MatchIDLive' => 'M.MatchIDLive',
                'MatchTypeID' => 'M.MatchTypeID',
                'MatchNo' => 'M.MatchNo',
                'MatchLocation' => 'M.MatchLocation',
                'MatchStartDateTime' => 'CONVERT_TZ(M.MatchStartDateTime,"+00:00","' . DEFAULT_TIMEZONE . '") AS MatchStartDateTime',
                'MatchStartDateTimeUTC' => 'M.MatchStartDateTime as MatchStartDateTimeUTC',
                'MatchScoreDetails' => 'M.MatchScoreDetails',
                'AdminPercent' => 'C.AdminPercent',
                'ContestID' => 'C.ContestID',
                'GameTimeLive' => 'C.GameTimeLive',
                'LeagueType' => 'C.LeagueType',
                'GameType' => 'C.GameType',
                'Privacy' => 'C.Privacy',
                'UnfilledWinningPercent' => 'C.UnfilledWinningPercent',
                'WinUpTo' => 'C.WinUpTo',
                'WinningRatio' => 'C.WinningRatio',
                'SmartPool' => 'C.SmartPool',
                'IsPaid' => 'C.IsPaid',
                'IsConfirm' => 'C.IsConfirm',
                "IsAutoCreate" => 'C.IsAutoCreate',
                "IsVirtualUserJoined" => 'C.IsVirtualUserJoined',
                "VirtualUserJoinedPercentage" => 'C.VirtualUserJoinedPercentage',
                'ShowJoinedContest' => 'C.ShowJoinedContest',
                'WinningAmount' => 'C.WinningAmount',
                'ContestSize' => 'C.ContestSize',
                'ContestFormat' => 'C.ContestFormat',
                'ContestType' => 'C.ContestType',
                'CustomizeWinning' => 'C.CustomizeWinning',
                'EntryFee' => 'C.EntryFee',
                'IsDummyJoined' => 'C.IsDummyJoined',
                'NoOfWinners' => 'C.NoOfWinners',
                'EntryType' => 'C.EntryType',
                'UserJoinLimit' => 'C.UserJoinLimit',
                'CashBonusContribution' => 'C.CashBonusContribution',
                'EntryType' => 'C.EntryType',
                'IsWinningDistributed' => 'C.IsWinningDistributed',
                'IsWinningDistributeAmount' => 'C.IsWinningDistributeAmount',
                'UserInvitationCode' => 'C.UserInvitationCode',
                'IsPrivacyNameDisplay' => 'C.IsPrivacyNameDisplay',
                'SeriesID' => 'M.SeriesID',
                'TeamNameLocal' => 'TL.TeamName AS TeamNameLocal',
                'TeamGUIDLocal' => 'TL.TeamGUID AS TeamGUIDLocal',
                'TeamGUIDVisitor' => 'TV.TeamGUID AS TeamGUIDVisitor',
                'TeamNameVisitor' => 'TV.TeamName AS TeamNameVisitor',
                'TeamNameShortLocal' => 'TL.TeamNameShort AS TeamNameShortLocal',
                'TeamNameShortVisitor' => 'TV.TeamNameShort AS TeamNameShortVisitor',
                'TeamFlagLocal' => 'CONCAT("' . BASE_URL . '","uploads/TeamFlag/",TL.TeamFlag) as TeamFlagLocal',
                'TeamFlagVisitor' => 'CONCAT("' . BASE_URL . '","uploads/TeamFlag/",TV.TeamFlag) as TeamFlagVisitor',
                'StatusID' => 'E.StatusID',
                'SeriesName' => 'S.SeriesName',
                'IsJoined' => '(SELECT IF( EXISTS(SELECT EntryDate FROM sports_contest_join_completed
                                                        WHERE sports_contest_join_completed.ContestID =  C.ContestID AND UserID = ' . @$Where['SessionUserID'] . ' LIMIT 1), "Yes", "No")) AS IsJoined',
                'TotalJoined' => '(SELECT COUNT(*)
                                                        FROM sports_contest_join_completed
                                                        WHERE ContestID =  C.ContestID ) AS TotalJoined',
                'UserTotalJoinedInMatch' => '(SELECT COUNT(*)
                                                FROM sports_contest_join_completed,tbl_entity
                                                WHERE sports_contest_join_completed.MatchID =  M.MatchID AND sports_contest_join_completed.ContestID = tbl_entity.EntityID AND tbl_entity.StatusID != 3 AND sports_contest_join_completed.UserID= ' . @$Where['SessionUserID'] . ') AS UserTotalJoinedInMatch',
                'Status' => 'CASE E.StatusID
                                                    when "1" then "Pending"
                                                    when "2" then "Running"
                                                    when "3" then "Cancelled"
                                                    when "5" then "Completed"
                                                    END as Status',
                'MatchType' => 'MT.MatchTypeName AS MatchType',
                'CurrentDateTime' => 'DATE_FORMAT(CONVERT_TZ(Now(),"+00:00","' . DEFAULT_TIMEZONE . '"), "' . DATE_FORMAT . ' ") CurrentDateTime',
                'MatchDate' => 'DATE_FORMAT(CONVERT_TZ(M.MatchStartDateTime,"+00:00","' . DEFAULT_TIMEZONE . '"), "%Y-%m-%d") MatchDate',
                'MatchTime' => 'DATE_FORMAT(CONVERT_TZ(M.MatchStartDateTime,"+00:00","' . DEFAULT_TIMEZONE . '"), "%H:%i:%s") MatchTime',
            );
            if ($Params) {
                foreach ($Params as $Param) {
                    $Field .= (!empty($FieldArray[$Param]) ? ',' . $FieldArray[$Param] : '');
                }
            }
        }
        $this->db->select('C.ContestGUID,C.ContestName,C.ContestSize,C.ContestID');
        if (!empty($Field)) $this->db->select($Field, FALSE);
        $this->db->from('tbl_entity E, sports_contest_completed C, sports_matches M, sports_teams TL, sports_teams TV,sports_series S,sports_set_match_types MT');
        $this->db->where("C.ContestID", "E.EntityID", FALSE);
        $this->db->where("M.MatchID", "C.MatchID", FALSE);
        $this->db->where("S.SeriesID", "C.SeriesID", FALSE);
        $this->db->where("M.TeamIDLocal", "TL.TeamID", FALSE);
        $this->db->where("M.TeamIDVisitor", "TV.TeamID", FALSE);
        $this->db->where("M.MatchTypeID", "MT.MatchTypeID", FALSE);
        $this->db->where("C.LeagueType", "Dfs");
        if (!empty($Where['Keyword'])) {
            if (is_array(json_decode($Where['Keyword'], true))) {
                $Where['Keyword'] = json_decode($Where['Keyword'], true);

                if (isset($Where['Keyword']['ContestName'])) {
                    $this->db->like("C.ContestName", @$Where['Keyword']['ContestName']);
                }
                if (isset($Where['Keyword']['ContestType'])) {
                    $this->db->where("C.ContestType", @$Where['Keyword']['ContestType']);
                }
                if (isset($Where['Keyword']['GameType'])) {
                    $this->db->where("C.GameType", @$Where['Keyword']['GameType']);
                }
                if (isset($Where['Keyword']['ContestSize'])) {
                    $ContestSize = explode("-", $Where['Keyword']['ContestSize']);

                    if (count($ContestSize) > 1) {
                        $this->db->where("C.ContestSize >=", @$ContestSize[0]);
                        $this->db->where("C.ContestSize <=", @$ContestSize[1]);
                    } else {
                        $this->db->where("C.ContestSize >=", @$ContestSize[0]);
                    }
                }
                if (isset($Where['Keyword']['EntryFee'])) {

                    $EntryFee = explode("-", $Where['Keyword']['EntryFee']);
                    if (count($EntryFee) > 1) {
                        $this->db->where("C.EntryFee >=", $EntryFee[0]);
                        $this->db->where("C.EntryFee <=", $EntryFee[1]);
                    } else {
                        $this->db->where("C.EntryFee >=", $EntryFee[0]);
                    }
                }
            } else {
                $this->db->group_start();
                $this->db->like("C.ContestName", $Where['Keyword']);
                $this->db->or_like("C.GameType", $Where['Keyword']);
                $this->db->or_like("C.WinningAmount", $Where['Keyword']);
                $this->db->or_like("C.ContestSize", $Where['Keyword']);
                $this->db->or_like("C.EntryFee", $Where['Keyword']);
                $this->db->or_like("M.MatchLocation", $Where['Keyword']);
                $this->db->or_like("M.MatchNo", $Where['Keyword']);
                $this->db->group_end();
            }
        }

        if (!empty($Where['AdvanceSafeValidate'])) {
            $this->db->where("M.MatchStartDateTime >= (UTC_TIMESTAMP() + INTERVAL C.GameTimeLive MINUTE)");
        }

        if (!empty($Where['ContestID'])) {
            $this->db->where("C.ContestID", $Where['ContestID']);
        }
        if (!empty($Where['ContestGUID'])) {
            $this->db->where("C.ContestGUID", $Where['ContestGUID']);
        }

        if (!empty($Where['IsVirtualUserJoined']) && $Where['IsVirtualUserJoined'] == 'Yes') {
            $this->db->where("C.IsVirtualUserJoined", $Where['IsVirtualUserJoined']);
        }
        if (!empty($Where['SeriesID'])) {
            $this->db->where("C.SeriesID", $Where['SeriesID']);
        }
        if (!empty($Where['UserID'])) {
            $this->db->where("C.UserID", $Where['UserID']);
        }
        if (!empty($Where['Filter']) && $Where['Filter'] == 'Today') {
            $this->db->where("DATE(M.MatchStartDateTime)", date('Y-m-d'));
        }
        if (!empty($Where['Filter']) && $Where['Filter'] == 'MatchLive') {
            $this->db->where("M.MatchStartDateTime <=", date('Y-m-d H:i:s'));
        }
        if (!empty($Where['IsWinningAmount']) && $Where['IsWinningAmount'] == 'Yes') {
            $this->db->where("C.WinningAmount >", 0);
        }
        if (!empty($Where['Filter']) && $Where['Filter'] == 'Yesterday') {
            $this->db->where("DATE(M.MatchStartDateTime) <=", date('Y-m-d'));
        }
        if (!empty($Where['GameType'])) {
            $this->db->where("C.GameType", $Where['GameType']);
        }
        if (!empty($Where['LeagueType'])) {
            $this->db->where("C.LeagueType", $Where['LeagueType']);
        }
        if (!empty($Where['Privacy']) && $Where['Privacy'] != 'All') {
            $this->db->where("C.Privacy", $Where['Privacy']);
        }
        if (!empty($Where['ContestType'])) {
            $this->db->where("C.ContestType", $Where['ContestType']);
        }
        if (!empty($Where['EntryStartFrom'])) {
            $this->db->where("C.EntryFee >=", $Where['EntryStartFrom']);
        }
        if (!empty($Where['EntryEndTo'])) {
            $this->db->where("C.EntryFee <=", $Where['EntryEndTo']);
        }
        if (!empty($Where['WinningStartFrom'])) {
            $this->db->where("C.WinningAmount >=", $Where['WinningStartFrom']);
        }
        if (!empty($Where['WinningEndTo'])) {
            $this->db->where("C.WinningAmount <=", $Where['WinningEndTo']);
        }
        if (!empty($Where['ContestSizeStartFrom'])) {
            $this->db->where("C.ContestSize >=", $Where['ContestSizeStartFrom']);
        }
        if (!empty($Where['ContestSizeEndTo'])) {
            $this->db->where("C.ContestSize <=", $Where['ContestSizeEndTo']);
        }
        if (!empty($Where['IsRefund'])) {
            $this->db->where("C.IsRefund", $Where['IsRefund']);
        }
        if (!empty($Where['IsWinningDistributeAmount'])) {
            $this->db->where("C.IsWinningDistributeAmount", $Where['IsWinningDistributeAmount']);
        }
        if (!empty($Where['isMailSent'])) {
            $this->db->where("C.isMailSent", $Where['isMailSent']);
        }
        if (!empty($Where['ContestFormat'])) {
            $this->db->where("C.ContestFormat", $Where['ContestFormat']);
        }
        if (!empty($Where['IsPaid'])) {
            $this->db->where("C.IsPaid", $Where['IsPaid']);
        }
        if (!empty($Where['IsConfirm'])) {
            $this->db->where("C.IsConfirm", $Where['IsConfirm']);
        }
        if (!empty($Where['WinningAmount'])) {
            $this->db->where("C.WinningAmount >=", $Where['WinningAmount']);
        }
        if (!empty($Where['ContestSize'])) {
            $this->db->where("C.ContestSize", $Where['ContestSize']);
        }
        if (!empty($Where['FromDate'])) {
            $this->db->where("DATE(M.MatchStartDateTime) >=", $Where['FromDate']);
        }
        if (!empty($Where['ToDate'])) {
            $this->db->where("DATE(M.MatchStartDateTime) <=", $Where['ToDate']);
        }
        if (!empty($Where['EntryFee'])) {
            $this->db->where("C.EntryFee", $Where['EntryFee']);
        }
        if (!empty($Where['SmartPool'])) {
            $this->db->where("C.SmartPool", $Where['SmartPool']);
        }
        if (!empty($Where['NoOfWinners'])) {
            $this->db->where("C.NoOfWinners", $Where['NoOfWinners']);
        }
        if (!empty($Where['EntryType'])) {
            $this->db->where("C.EntryType", $Where['EntryType']);
        }
        if (!empty($Where['MatchID'])) {
            $this->db->where("C.MatchID", $Where['MatchID']);
        }
        if (!empty($Where['SeriesID'])) {
            $this->db->where("M.SeriesID", $Where['SeriesID']);
        }
        if (!empty($Where['StatusID'])) {
            $this->db->where_in("E.StatusID", $Where['StatusID']);
        }
        if (!empty($Where['MyJoinedContest']) && $Where['MyJoinedContest'] == "Yes") {
            $this->db->where('EXISTS (select ContestID from sports_contest_join_completed JE where JE.ContestID = C.ContestID AND JE.UserID=' . @$Where['SessionUserID'] . ')');
        }
        if (!empty($Where['UserInvitationCode'])) {
            $this->db->where("C.UserInvitationCode", $Where['UserInvitationCode']);
        }
        if (!empty($Where['IsWinningDistributed'])) {
            $this->db->where("C.IsWinningDistributed", $Where['IsWinningDistributed']);
        }
        if (!empty($Where['ContestSizeRange'])) {
            $Range = explode('-', $Where['ContestSizeRange']);
            if (!empty($Range) && count($Range) == 2) {
                $this->db->where("C.ContestSize >=", $Range[0]);
                $this->db->where("C.ContestSize <=", $Range[1]);
            } else if (!empty($Range) && count($Range) == 1) {
                $this->db->where("C.ContestSize >=", $Range[0]);
            }
        }
        if (!empty($Where['EntryFeeRange'])) {
            $Range = explode('-', $Where['EntryFeeRange']);
            if (!empty($Range) && count($Range) == 2) {
                $this->db->where("C.EntryFee >=", $Range[0]);
                $this->db->where("C.EntryFee <=", $Range[1]);
            } else if (!empty($Range) && count($Range) == 1) {
                $this->db->where("C.EntryFee >=", $Range[0]);
            }
        }
        if (!empty($Where['ContestFull']) && $Where['ContestFull'] == 'No') {
            $this->db->having("TotalJoined !=", 'C.ContestSize', FALSE);
        }
        if (!empty($Where['ContestFull']) && $Where['ContestFull'] == 'Yes') {
            $this->db->having("TotalJoined", 'C.ContestSize', FALSE);
        }
        if (!empty($Where['OrderBy']) && !empty($Where['Sequence'])) {
            if (!empty($Where['ContestList']) && $Where['ContestList'] == 'Yes') {
                $this->db->order_by('TotalJoined - C.ContestSize !=0', 'DESC', FALSE);
            }
            $this->db->order_by($Where['OrderBy'], $Where['Sequence']);
        } else {
            if (!empty($Where['OrderByToday']) && $Where['OrderByToday'] == 'Yes') {
                if ($Where['StatusID'] == 5 || $Where['StatusID'] == 3) {
                    $this->db->order_by('M.MatchStartDateTime', 'DESC');
                } else {
                    $this->db->order_by('DATE(M.MatchStartDateTime)="' . date('Y-m-d') . '" DESC', null, FALSE);
                    $this->db->order_by('E.StatusID=2 DESC', null, FALSE);
                    $this->db->order_by('E.StatusID=1 DESC', null, FALSE);
                }
            } else if (!empty($Where['OrderByList']) && $Where['OrderByList'] == 'Yes') {
                $this->db->order_by('M.MatchStartDateTime', 'DESC');
            } else {
                $this->db->order_by('M.MatchStartDateTime', 'ASC');
            }
        }

        /* Total records count only if want to get multiple records */
        if ($multiRecords) {
            $TempOBJ = clone $this->db;
            $TempQ = $TempOBJ->get();
            $Return['Data']['TotalRecords'] = $TempQ->num_rows();
            if ($PageNo != 0) {
                $this->db->limit($PageSize, paginationOffset($PageNo, $PageSize)); /* for pagination */
            }
        } else {
            $this->db->limit(1);
        }
        //$this->db->group_by('C.ContestID'); // Will manage later
        $Query = $this->db->get();
        // echo $this->db->last_query(); die();
        if ($Query->num_rows() > 0) {
            if ($multiRecords) {
                $Records = array();
                foreach ($Query->result_array() as $key => $Record) {
                    $Records[] = $Record;
                    $Records[$key]['CustomizeWinning'] = (!empty($Record['CustomizeWinning'])) ? json_decode($Record['CustomizeWinning'], true) : array();
                    $Records[$key]['MatchScoreDetails'] = (!empty($Record['MatchScoreDetails'])) ? json_decode($Record['MatchScoreDetails'], TRUE) : new stdClass();
                    $TotalAmountReceived = $this->getTotalContestCollectionsCompleted($Record['ContestGUID']);
                    $Records[$key]['TotalAmountReceived'] = ($TotalAmountReceived) ? (int) $TotalAmountReceived : 0;
                    $TotalWinningAmount = $this->getTotalWinningAmountCompleted($Record['ContestGUID']);
                    $Records[$key]['TotalWinningAmount'] = ($TotalWinningAmount) ? (int) $TotalWinningAmount : 0;
                    $Records[$key]['NoOfWinners'] = ($Record['NoOfWinners'] == 0 ) ? 1 : $Record['NoOfWinners'];
                    $Records[$key]['ContestSize'] = ($Record['ContestSize'] == 0 ) ? 'Unlimited' : $Record['ContestSize'];
                    if (in_array('IsJoined', $Params)) {
                        if ($Record['IsJoined'] == 'Yes') {
                            $UserTeamDetails = $this->getUserTeams('TotalPoints', array('ContestID' => @$Where['ContestID'], 'UserID' => $Where['SessionUserID'], 'ValidateAdvanceSafe' => "No"), true, 0);
                            $Records[$key]['UserTeamDetails'] = $UserTeamDetails['Data']['Records'];
                        } else {
                            $Records[$key]['UserTeamDetails'] = array();
                        }
                        unset($Records[$key]['ContestID']);
                    }
                    if (in_array('UserWinningAmount', $Params)) {
                        /* update user time break . */
                        if ($Record['Status'] == "Running" || $Record['Status'] == "Completed") {
                            $ContestCollection = $this->fantasydb->{'Contest_' . $Record['ContestID']};
                            $JoinedTeams = $ContestCollection->findOne(["UserID" => $Where['SessionUserID']], ['projection' => ['_id' => 0, 'UserGUID' => 1, 'UserTeamName' => 1, 'UserTeamGUID' => 1, 'Username' => 1, 'TotalPoints' => 1, 'UserRank' => 1, 'UserWinningAmount' => 1, "SmartPoolWinning" => 1, 'SmartPool' => 1], 'skip' => paginationOffset($PageNo, $PageSize), 'limit' => (int) $PageSize, 'sort' => ['UserRank' => 1]]);
                            $JoinedContestsUsers = (!empty($JoinedTeams)) ? iterator_to_array($JoinedTeams) : array();
                            if (count($JoinedContestsUsers) > 0) {
                                $Records[$key]['UserWinningAmount'] = (String) $JoinedContestsUsers['UserWinningAmount'];
                                $Records[$key]['SmartPoolWinning'] = (isset($JoinedContestsUsers['SmartPoolWinning'])) ? $JoinedContestsUsers['SmartPoolWinning'] : "";
                                $Records[$key]['TotalPoints'] = (String) $JoinedContestsUsers['TotalPoints'];
                                $Records[$key]['UserRank'] = (String) $JoinedContestsUsers['UserRank'];
                                $Records[$key]['UserTeamName'] = (String) $JoinedContestsUsers['UserTeamName'];
                            } else {
                                $Query = $this->db->query('SELECT JC.UserWinningAmount,JC.SmartPoolWinning,JC.TotalPoints,JC.UserRank,UT.UserTeamName FROM sports_contest_join JC,sports_users_teams UT WHERE UT.UserTeamID = JC.UserTeamID AND JC.ContestID = "' . $Record['ContestID'] . '" AND JC.UserID = "' . $Where['SessionUserID'] . '"');
                                $UserWinningAmount = $Query->row_array();
                                if (!empty($UserWinningAmount)) {
                                    $Records[$key]['UserWinningAmount'] = $UserWinningAmount['UserWinningAmount'];
                                    $Records[$key]['SmartPoolWinning'] = $UserWinningAmount['SmartPoolWinning'];
                                    $Records[$key]['TotalPoints'] = $UserWinningAmount['TotalPoints'];
                                    $Records[$key]['UserRank'] = $UserWinningAmount['UserRank'];
                                    $Records[$key]['UserTeamName'] = $UserWinningAmount['UserTeamName'];
                                }
                            }
                        } else {
                            $Query = $this->db->query('SELECT JC.UserWinningAmount,JC.SmartPoolWinning,JC.TotalPoints,JC.UserRank,UT.UserTeamName FROM sports_contest_join JC,sports_users_teams UT WHERE UT.UserTeamID = JC.UserTeamID AND JC.ContestID = "' . $Record['ContestID'] . '" AND JC.UserID = "' . $Where['SessionUserID'] . '"');
                            $UserWinningAmount = $Query->row_array();
                            if (!empty($UserWinningAmount)) {
                                $Records[$key]['UserWinningAmount'] = $UserWinningAmount['UserWinningAmount'];
                                $Records[$key]['SmartPoolWinning'] = $UserWinningAmount['SmartPoolWinning'];
                                $Records[$key]['TotalPoints'] = $UserWinningAmount['TotalPoints'];
                                $Records[$key]['UserRank'] = $UserWinningAmount['UserRank'];
                                $Records[$key]['UserTeamName'] = $UserWinningAmount['UserTeamName'];
                            }
                        }
                    }
                }
                $Return['Data']['Records'] = $Records;
            } else {
                $Record = $Query->row_array();
                $Record['CustomizeWinning'] = (!empty($Record['CustomizeWinning'])) ? json_decode($Record['CustomizeWinning'], TRUE) : array();
                $Record['MatchScoreDetails'] = (!empty($Record['MatchScoreDetails'])) ? json_decode($Record['MatchScoreDetails'], TRUE) : new stdClass();
                $TotalAmountReceived = $this->getTotalContestCollectionsCompleted($Record['ContestGUID']);
                $Record['TotalAmountReceived'] = ($TotalAmountReceived) ? $TotalAmountReceived : 0;
                $TotalWinningAmount = $this->getTotalWinningAmountCompleted($Record['ContestGUID']);
                $Record['TotalWinningAmount'] = ($TotalWinningAmount) ? $TotalWinningAmount : 0;
                $Record['ContestSize'] = ($Record['ContestSize'] == 0 ) ? 'Unlimited' : $Record['ContestSize'];
                if (in_array('IsJoined', $Params)) {
                    if ($Record['IsJoined'] == 'Yes') {
                        $UserTeamDetails = $this->getUserTeams('TotalPoints', array('ContestID' => $Where['ContestID'], 'UserID' => $Where['SessionUserID']), true, 0);
                        $Record['UserTeamDetails'] = $UserTeamDetails['Data']['Records'];
                    } else {
                        $Record['UserTeamDetails'] = array();
                    }
                    unset($Record['ContestID']);
                }

                if (!empty($Where['MatchID'])) {
                    $Record['Statics'] = $this->db->query('SELECT (SELECT COUNT(*) AS `NormalContest` FROM `sports_contest_completed` C, `tbl_entity` E WHERE C.ContestID = E.EntityID AND E.StatusID IN (1,2,5) AND C.MatchID = "' . $Where['MatchID'] . '" AND C.ContestType="Normal" AND C.ContestFormat="League" AND C.ContestSize != (SELECT COUNT(*) from sports_contest_join_completed where sports_contest_join_completed.ContestID = C.ContestID)
                                            )as NormalContest,
                            ( SELECT COUNT(*) AS `ReverseContest` FROM `sports_contest_completed` C, `tbl_entity` E WHERE C.ContestID = E.EntityID AND E.StatusID IN(1,2,5) AND C.MatchID = "' . $Where['MatchID'] . '" AND C.ContestType="Reverse" AND C.ContestFormat="League" AND C.ContestSize != (SELECT COUNT(*) from sports_contest_join_completed where sports_contest_join_completed.ContestID = C.ContestID)
                            )as ReverseContest,(
                            SELECT COUNT(*) AS `JoinedContest` FROM `sports_contest_join_completed` J, `sports_contest_completed` C WHERE C.ContestID = J.ContestID AND J.UserID = "' . @$Where['SessionUserID'] . '" AND C.MatchID = "' . $Where['MatchID'] . '" 
                            )as JoinedContest,( 
                            SELECT COUNT(*) AS `TotalTeams` FROM `sports_users_teams_completed`WHERE UserID = "' . @$Where['SessionUserID'] . '" AND MatchID = "' . $Where['MatchID'] . '"
                        ) as TotalTeams,(SELECT COUNT(*) AS `H2HContest` FROM `sports_contest_completed` C, `tbl_entity` E, `sports_contest_join_completed` CJ WHERE C.ContestID = E.EntityID AND E.StatusID IN (1,2,5) AND C.MatchID = "' . $Where['MatchID'] . '" AND C.ContestFormat="Head to Head" AND E.StatusID = 1 AND C.ContestID = CJ.ContestID AND C.ContestSize != (SELECT COUNT(*) from sports_contest_join_completed where sports_contest_join_completed.ContestID = C.ContestID )) as H2HContests')->row();
                }

                return $Record;
            }
        } else {
            if (!$multiRecords) {
                return array();
            }
        }
        if (!empty($Where['MatchID'])) {
            $Return['Data']['Statics'] = $this->db->query('SELECT (SELECT COUNT(*) AS `NormalContest` FROM `sports_contest_completed` C, `tbl_entity` E WHERE C.ContestID = E.EntityID AND E.StatusID IN (1,2,5) AND C.MatchID = "' . $Where['MatchID'] . '" AND C.ContestType="Normal" AND C.ContestFormat="League" AND C.ContestSize != (SELECT COUNT(*) from sports_contest_join_completed where sports_contest_join_completed.ContestID = C.ContestID)
                                    )as NormalContest,
                    ( SELECT COUNT(*) AS `ReverseContest` FROM `sports_contest_completed` C, `tbl_entity` E WHERE C.ContestID = E.EntityID AND E.StatusID IN(1,2,5) AND C.MatchID = "' . $Where['MatchID'] . '" AND C.ContestType="Reverse" AND C.ContestFormat="League" AND C.ContestSize != (SELECT COUNT(*) from sports_contest_join_completed where sports_contest_join_completed.ContestID = C.ContestID)
                    )as ReverseContest,(
                    SELECT COUNT(*) AS `JoinedContest` FROM `sports_contest_join_completed` J, `sports_contest_completed` C WHERE C.ContestID = J.ContestID AND J.UserID = "' . @$Where['SessionUserID'] . '" AND C.MatchID = "' . $Where['MatchID'] . '" 
                    )as JoinedContest,( 
                    SELECT COUNT(*) AS `TotalTeams` FROM `sports_users_teams_completed`WHERE UserID = "' . @$Where['SessionUserID'] . '" AND MatchID = "' . $Where['MatchID'] . '"
                ) as TotalTeams,(SELECT COUNT(*) AS `H2HContest` FROM `sports_contest_completed` C, `tbl_entity` E, `sports_contest_join_completed` CJ WHERE C.ContestID = E.EntityID AND E.StatusID IN (1,2,5) AND C.MatchID = "' . $Where['MatchID'] . '" AND C.ContestFormat="Head to Head" AND E.StatusID = 1 AND C.ContestID = CJ.ContestID AND C.ContestSize != (SELECT COUNT(*) from sports_contest_join_completed where sports_contest_join_completed.ContestID = C.ContestID )) as H2HContests')->row();
        }

        $Return['Data']['Records'] = empty($Records) ? array() : $Records;
        return $Return;
    }

    function getJoinedContestsUsersCacheCompleted($Field = '', $Where = array(), $multiRecords = FALSE, $PageNo = 1, $PageSize = 15) {
        $Params = array();
        if (!empty($Field)) {
            $Params = array_map('trim', explode(',', $Field));
            $Field = '';
            $FieldArray = array(
                'TotalPoints' => 'JC.TotalPoints',
                'UserWinningAmount' => 'JC.UserWinningAmount',
                'FirstName' => 'U.FirstName',
                'MiddleName' => 'U.MiddleName',
                'LastName' => 'U.LastName',
                'Username' => 'U.Username',
                'SmartPool' => 'JC.SmartPool',
                'SmartPoolWinning' => 'JC.SmartPoolWinning',
                'Email' => 'U.Email',
                'PhoneNumber' => 'U.PhoneNumber',
                'UserID' => 'U.UserID',
                'UserRank' => 'JC.UserRank',
                'UserTeamName' => 'UT.UserTeamName',
                'UserTeamID' => 'UT.UserTeamID',
                'ProfilePic' => 'IF(U.ProfilePic IS NULL,CONCAT("' . BASE_URL . '","uploads/profile/picture/","default.jpg"),CONCAT("' . BASE_URL . '","uploads/profile/picture/",U.ProfilePic)) AS ProfilePic',
                'UserRank' => 'JC.UserRank',
                "UserTypeID" => "UserTypeID"
            );
            if ($Params) {
                foreach ($Params as $Param) {
                    $Field .= (!empty($FieldArray[$Param]) ? ',' . $FieldArray[$Param] : '');
                }
            }
        }
        $this->db->select('U.UserGUID,UT.UserTeamGUID');
        if (!empty($Field)) $this->db->select($Field, FALSE);
        $this->db->from('sports_contest_join_completed JC, tbl_users U, sports_users_teams_completed UT');
        $this->db->where("JC.UserTeamID", "UT.UserTeamID", FALSE);
        $this->db->where("JC.UserID", "U.UserID", FALSE);
        if (!empty($Where['UserID'])) {
            //$this->db->where("JC.UserID", $Where['UserID']);
        }
        if (!empty($Where['NotInUser'])) {
            // $this->db->where("JC.UserID !=", $Where['NotInUser']);
        }

        if (!empty($Where['PointFilter']) && $Where['PointFilter'] == 'TotalPoints') {
            $this->db->where("JC.TotalPoints >", 0);
        }

        if (!empty($Where['OnlyWinners']) && $Where['OnlyWinners'] == 'Yes') {
            $this->db->where("JC.UserWinningAmount >", 0);
        }

        if (!empty($Where['ContestID'])) {
            $this->db->where("JC.ContestID", $Where['ContestID']);
        }
        if (!empty($Where['isMailSent'])) {
            $this->db->where("JC.isMailSent", $Where['isMailSent']);
        }
        if (!empty($Where['OrderBy']) && !empty($Where['Sequence'])) {
            $this->db->order_by($Where['OrderBy'], $Where['Sequence']);
        } else {
            if (!empty($Where['UserID'])) {
                $this->db->order_by('JC.UserID=' . $Where['UserID'] . ' DESC', null, FALSE);
            }

            $this->db->order_by('JC.UserRank', 'ASC');
        }
        /* Total records count only if want to get multiple records */
        if ($multiRecords) {
            $TempOBJ = clone $this->db;
            $TempQ = $TempOBJ->get();
            $Return['Data']['TotalRecords'] = $TempQ->num_rows();
            if ($PageNo != 0) {
                $this->db->limit($PageSize, paginationOffset($PageNo, $PageSize)); /* for pagination */
            }
        } else {
            $this->db->limit(1);
        }
        $Query = $this->db->get();
        if ($Query->num_rows() > 0) {
            if ($multiRecords) {
                $Return['Data']['Records'] = $Query->result_array();
                return $Return;
            } else {
                $result = $Query->row_array();
                foreach ($result as $key => $record) {
                    $Return['Data']['Records'][$key]['UserTeamPlayers'] = array();
                }
                return $Return;
            }
        }
        return FALSE;
    }

    /*
      Description: To get user teams
     */

    function getUserTeamsCompleted($Field = '', $Where = array(), $multiRecords = FALSE, $PageNo = 1, $PageSize = 15) {
        $Params = array();
        if (!empty($Field)) {
            $Params = array_map('trim', explode(',', $Field));
            $Field = '';
            $FieldArray = array(
                'UserTeamID' => 'UT.UserTeamID',
                'MatchID' => 'UT.MatchID',
                'MatchInning' => 'UT.MatchInning',
                'TotalPoints' => 'JC.TotalPoints',
                'Username' => '(SELECT Username FROM tbl_users WHERE UserID = UT.UserID) AS Username',
                'TotalJoinedContests' => '(SELECT COUNT(ContestID) FROM sports_contest_join_completed WHERE UserTeamID = UT.UserTeamID) TotalJoinedContests',
                'IsTeamJoined' => '(SELECT IF( EXISTS(
                                    SELECT sports_contest_join_completed.ContestID FROM sports_contest_join_completed
                                    WHERE sports_contest_join_completed.UserTeamID =  UT.UserTeamID AND sports_contest_join_completed.ContestID = ' . @$Where['TeamsContestID'] . ' LIMIT 1), "Yes", "No")) AS IsTeamJoined'
            );
            if ($Params) {
                foreach ($Params as $Param) {
                    $Field .= (!empty($FieldArray[$Param]) ? ',' . $FieldArray[$Param] : '');
                }
            }
        }
        $this->db->select('UT.UserTeamGUID,UT.UserTeamName,UT.UserTeamType,UT.UserTeamID,UT.MatchID,UT.UserID');
        if (!empty($Field)) $this->db->select($Field, FALSE);
        if (in_array('TotalPoints', $Params)) {
            $this->db->from('tbl_entity E, sports_users_teams_completed UT,sports_contest_join_completed JC');
            $this->db->where("UT.UserTeamID", "E.EntityID", false);
            $this->db->where("JC.UserTeamID", "UT.UserTeamID", false);
        } else {
            $this->db->from('tbl_entity E, sports_users_teams_completed UT');
            $this->db->where("UT.UserTeamID", "E.EntityID", false);
        }

        if (!empty($Where['Keyword'])) {
            $this->db->like("UT.UserTeamName", $Where['Keyword']);
        }
        if (!empty($Where['MatchID'])) {
            $this->db->where("UT.MatchID", $Where['MatchID']);
        }
        if (!empty($Where['UserTeamType']) && $Where['UserTeamType'] != 'All') {
            $this->db->where("UT.UserTeamType", $Where['UserTeamType']);
        }
        if (!empty($Where['UserTeamID'])) {
            $this->db->where("UT.UserTeamID", $Where['UserTeamID']);
        }
        if (!empty($Where['ContestID'])) {
            $this->db->where("JC.ContestID", $Where['ContestID']);
        }
        if (!empty($Where['MatchInning'])) {
            $this->db->where("UT.MatchInning", $Where['MatchInning']);
        }
        if (!empty($Where['UserID']) && empty($Where['UserTeamID'])) { // UserTeamID used to manage other user team details (On live score page)
            $this->db->where("UT.UserID", $Where['UserID']);
        }
        if (!empty($Where['StatusID'])) {
            $this->db->where("E.StatusID", $Where['StatusID']);
        }

        if (!empty($Where['OrderBy']) && !empty($Where['Sequence'])) {
            $this->db->order_by($Where['OrderBy'], $Where['Sequence']);
        }
        $this->db->order_by('UT.UserTeamID', 'DESC');
        if (!empty($Where['MatchID'])) {
            $Return['Data']['Statics'] = $this->db->query('SELECT (
                SELECT COUNT(*) AS `NormalContest` FROM `sports_contest_completed` C, `tbl_entity` E WHERE C.ContestID = E.EntityID AND E.StatusID IN (1,2,5) AND C.MatchID = "' . $Where['MatchID'] . '" AND C.ContestType="Normal"
                )as NormalContest,
                (
                SELECT COUNT(*) AS `JoinedContest` FROM `sports_contest_join_completed` J, `sports_contest_completed` C WHERE C.ContestID = J.ContestID AND J.UserID = "' . @$Where['SessionUserID'] . '" AND C.MatchID = "' . $Where['MatchID'] . '"
                )as JoinedContest,
                ( 
                SELECT COUNT(*) AS `TotalTeams` FROM `sports_users_teams_completed` WHERE UserID = "' . @$Where['SessionUserID'] . '" AND MatchID = "' . $Where['MatchID'] . '" 
            ) as TotalTeams'
                    )->row();
        }
        /* Total records count only if want to get multiple records */
        if ($multiRecords) {
            $TempOBJ = clone $this->db;
            $TempQ = $TempOBJ->get();
            $Return['Data']['TotalRecords'] = $TempQ->num_rows();
            if ($PageNo != 0) {
                $this->db->limit($PageSize, paginationOffset($PageNo, $PageSize)); /* for pagination */
            }
        } else {
            $this->db->limit(1);
        }
        $Query = $this->db->get();
        // echo $this->db->last_query(); die();
        if ($Query->num_rows() > 0) {
            if ($multiRecords) {
                $Return['Data']['Records'] = $Query->result_array();
                if (in_array('UserTeamPlayers', $Params)) {
                    foreach ($Return['Data']['Records'] as $key => $value) {
                        $Return['Data']['Records'][$key]['Playing11Announce'] = "No";
                        $UserTeamPlayers = $this->getUserTeamPlayersCompleted('PlayerID,IsPlaying,PlayerSalary,PlayerSalaryCredit,PlayerPosition,PlayerBattingStyle,PlayerSelectedPercent,PlayerBowlingStyle,PlayerName,PlayerPic,PlayerCountry,PlayerRole,Points,TeamGUID,TotalPoints,TotalPointCredits', array('UserTeamID' => $value['UserTeamID']));
                        $Return['Data']['Records'][$key]['UserTeamPlayers'] = ($UserTeamPlayers) ? $UserTeamPlayers : array();
                        $IsPlaying = in_array("Yes", array_column($UserTeamPlayers, 'IsPlaying'));
                        if ($IsPlaying) {
                            $Return['Data']['Records'][$key]['Playing11Announce'] = "Yes";
                        }
                    }
                }
                if ($Where['ValidateAdvanceSafe'] == "Yes") {
                    foreach ($Return['Data']['Records'] as $key => $value) {
                        $Return['Data']['Records'][$key]['IsEditUserTeam'] = "Yes";
                        $isvalidate = $this->ValidateAdvanceSafePlay($value['MatchID'], $value['UserID'], $value['UserTeamID']);
                        if (!$isvalidate) {
                            $Return['Data']['Records'][$key]['IsEditUserTeam'] = "No";
                        }
                    }
                }
                return $Return;
            } else {
                $Record = $Query->row_array();
                if (in_array('UserTeamPlayers', $Params)) {
                    $Record['Playing11Announce'] = "No";
                    $UserTeamPlayers = $this->getUserTeamPlayersCompleted('PlayerID,IsPlaying,PlayerSelectedPercent,PlayerSalary,PlayerSalaryCredit,TeamGUID,PlayerPosition,PlayerBattingStyle,PlayerBowlingStyle,PlayerName,PlayerPic,PlayerCountry,PlayerRole,Points,TotalPoints,TotalPointCredits', array('UserTeamID' => $Where['UserTeamID']));
                    $Record['UserTeamPlayers'] = ($UserTeamPlayers) ? $UserTeamPlayers : array();
                    $IsPlaying = in_array("Yes", array_column($UserTeamPlayers, 'IsPlaying'));
                    if ($IsPlaying) {
                        $Record['Playing11Announce'] = "Yes";
                    }
                }
                return $Record;
            }
        }

        return FALSE;
    }

    /*
      Description: To get user team players
     */

    function getUserTeamPlayersCompleted($Field = '', $Where = array()) {
        $Params = array();
        if (!empty($Field)) {
            $Params = array_map('trim', explode(',', $Field));
            $Field = '';
            $FieldArray = array(
                'SeriesGUID' => 'S.SeriesGUID',
                'PlayerPosition' => 'UTP.PlayerPosition',
                'Points' => 'UTP.Points',
                'PlayerID' => 'UTP.PlayerID',
                'PlayerName' => 'P.PlayerName',
                'PlayerPic' => 'IF(P.PlayerPic IS NULL,CONCAT("' . BASE_URL . '","uploads/PlayerPic/","player.png"),CONCAT("' . BASE_URL . '","uploads/PlayerPic/",P.PlayerPic)) AS PlayerPic',
                'PlayerCountry' => 'P.PlayerCountry',
                'PlayerSalary' => 'TP.PlayerSalary',
                'PlayerBattingStyle' => 'P.PlayerBattingStyle',
                'PlayerBowlingStyle' => 'P.PlayerBowlingStyle',
                'PlayerRole' => 'TP.PlayerRole',
                'PointsData' => 'TP.PointsData',
                'IsPlaying' => 'TP.IsPlaying',
                'TeamGUID' => 'T.TeamGUID',
                'MatchType' => 'SM.MatchTypeName as MatchType',
                'TotalPointCredits' => '(SELECT IFNULL(SUM(`TotalPoints`),0) FROM `sports_team_players_completed` WHERE `PlayerID` = TP.PlayerID AND `SeriesID` = TP.SeriesID) TotalPointCredits'
            );
            if ($Params) {
                foreach ($Params as $Param) {
                    $Field .= (!empty($FieldArray[$Param]) ? ',' . $FieldArray[$Param] : '');
                }
            }
        }
        $this->db->select('P.PlayerGUID,P.PlayerID,M.MatchGUID,UTP.Points');
        if (!empty($Field)) $this->db->select($Field, FALSE);
        $this->db->from('sports_users_team_players_completed UTP, sports_players P, sports_team_players_completed TP,sports_teams T,sports_matches M,sports_set_match_types SM');
        if (array_keys_exist($Params, array('SeriesGUID'))) {
            $this->db->from('sports_series S');
            $this->db->where("S.SeriesID", "TP.SeriesID", FALSE);
        }
        $this->db->where("UTP.PlayerID", "P.PlayerID", FALSE);
        $this->db->where("UTP.PlayerID", "TP.PlayerID", FALSE);
        $this->db->where("UTP.MatchID", "TP.MatchID", FALSE);
        $this->db->where("T.TeamID", "TP.TeamID", FALSE);
        $this->db->where("M.MatchID", "TP.MatchID", FALSE);
        $this->db->where("M.MatchTypeID", "SM.MatchTypeID", FALSE);
        if (!empty($Where['Keyword'])) {
            $Where['Keyword'] = $Where['Keyword'];
            $this->db->like("P.PlayerName", $Where['Keyword']);
        }
        if (!empty($Where['UserTeamID'])) {
            $this->db->where("UTP.UserTeamID", $Where['UserTeamID']);
        }
        if (!empty($Where['MatchID'])) {
            $this->db->where("UTP.MatchID", $Where['MatchID']);
        }
        if (!empty($Where['PlayerID'])) {
            $this->db->where("UTP.PlayerID", $Where['PlayerID']);
        }
        if (!empty($Where['PlayerRole'])) {
            $this->db->where("TP.PlayerRole", $Where['PlayerRole']);
        }
        if (!empty($Where['PlayerPosition'])) {
            $this->db->where("UTP.PlayerPosition", $Where['PlayerPosition']);
        }

        if (!empty($Where['OrderBy']) && !empty($Where['Sequence'])) {
            $this->db->order_by($Where['OrderBy'], $Where['Sequence']);
        }
        $this->db->order_by('P.PlayerName', 'ASC');
        $Query = $this->db->get();
        if ($Query->num_rows() > 0) {
            if (in_array('TopPlayer', $Params)) {
                $BestPlayers = $this->Sports_model->getMatchBestPlayersCompleted(array('MatchID' => $Where['MatchID']));
                if (!empty($BestPlayers)) {
                    $BestXIPlayers = array_column($BestPlayers['Data']['Records'], 'PlayerGUID');
                }
            }
            $Records = array();
            $MatchStatus = 0;
            foreach ($Query->result_array() as $key => $Record) {
                if ($key == 0) {
                    /* Get Match Status */
                    $Query = $this->db->query('SELECT E.StatusID FROM `sports_matches` `M`,`tbl_entity` `E` WHERE M.`MatchGUID` = "' . $Record['MatchGUID'] . '" AND M.MatchID = E.EntityID LIMIT 1');
                    $MatchStatus = ($Query->num_rows() > 0) ? $Query->row()->StatusID : 0;
                }
                $Records[] = $Record;
                $Records[$key]['TopPlayer'] = (in_array($Record['PlayerGUID'], $BestXIPlayers)) ? 'Yes' : 'No';
                $Records[$key]['PointCredits'] = ($MatchStatus == 2 || $MatchStatus == 5) ? $Record['Points'] : $Record['PlayerSalary'];
                if (in_array('MyTeamPlayer', $Params)) {
                    $this->db->select('DISTINCT(SUTP.PlayerID)');
                    $this->db->where("JC.UserTeamID", "SUTP.UserTeamID", FALSE);
                    $this->db->where("SUT.UserTeamID", "SUTP.UserTeamID", FALSE);
                    $this->db->where('SUT.MatchID', $Where['MatchID']);
                    $this->db->where('SUT.UserID', $Where['UserID']);
                    $this->db->from('sports_contest_join_completed JC,sports_users_teams_completed SUT,sports_users_team_players_completed SUTP');
                    $MyPlayers = $this->db->get()->result_array();
                    $MyPlayersIds = (!empty($MyPlayers)) ? array_column($MyPlayers, 'PlayerID') : array();
                    $Records[$key]['MyPlayer'] = (in_array($Record['PlayerID'], $MyPlayersIds)) ? 'Yes' : 'No';
                }

                if (in_array('PlayerSelectedPercent', $Params)) {
                    $TotalTeams = $this->db->query('Select count(*) as TotalTeams from sports_users_teams_completed WHERE MatchID="' . $Where['MatchID'] . '"')->row()->TotalTeams;

                    $this->db->select('count(SUTP.PlayerID) as TotalPlayer');
                    $this->db->where("SUTP.UserTeamID", "SUT.UserTeamID", FALSE);
                    $this->db->where("SUTP.PlayerID", $Record['PlayerID']);
                    $this->db->where("SUTP.MatchID", $Where['MatchID']);
                    $this->db->from('sports_users_teams_completed SUT,sports_users_team_players_completed SUTP');
                    $Players = $this->db->get()->row();
                    $Records[$key]['PlayerSelectedPercent'] = ($TotalTeams > 0 ) ? round((($Players->TotalPlayer * 100 ) / $TotalTeams), 2) > 100 ? 100 : round((($Players->TotalPlayer * 100 ) / $TotalTeams), 2) : 0;
                }
                $Records[$key]['PointsData'] = ($Record['PointsData'] != '' ? json_decode($Record['PointsData']) : array());
            }
            return $Records;
        }
        return FALSE;
    }

    /*
      Description: To get contest
     */

    function getContestsExports($Field = '', $Where = array(), $multiRecords = FALSE, $PageNo = 1, $PageSize = 15) {
        $Params = array();
        if (!empty($Field)) {
            $Params = array_map('trim', explode(',', $Field));
            $Field = '';
            $FieldArray = array(
                'MatchID' => 'M.MatchID',
                'MatchGUID' => 'M.MatchGUID',
                'StatusID' => 'E.StatusID',
                'MatchIDLive' => 'M.MatchIDLive',
                'MatchTypeID' => 'M.MatchTypeID',
                'MatchNo' => 'M.MatchNo',
                'MatchLocation' => 'M.MatchLocation',
                'MatchStartDateTime' => 'CONVERT_TZ(M.MatchStartDateTime,"+00:00","' . DEFAULT_TIMEZONE . '") AS MatchStartDateTime',
                'MatchStartDateTimeUTC' => 'M.MatchStartDateTime as MatchStartDateTimeUTC',
                'MatchScoreDetails' => 'M.MatchScoreDetails',
                'AdminPercent' => 'C.AdminPercent',
                'ContestID' => 'C.ContestID',
                'GameTimeLive' => 'C.GameTimeLive',
                'LeagueType' => 'C.LeagueType',
                'GameType' => 'C.GameType',
                'Privacy' => 'C.Privacy',
                'UnfilledWinningPercent' => 'C.UnfilledWinningPercent',
                'WinUpTo' => 'C.WinUpTo',
                'WinningRatio' => 'C.WinningRatio',
                'SmartPool' => 'C.SmartPool',
                'IsPaid' => 'C.IsPaid',
                'IsConfirm' => 'C.IsConfirm',
                "IsAutoCreate" => 'C.IsAutoCreate',
                "IsVirtualUserJoined" => 'C.IsVirtualUserJoined',
                "VirtualUserJoinedPercentage" => 'C.VirtualUserJoinedPercentage',
                'ShowJoinedContest' => 'C.ShowJoinedContest',
                'WinningAmount' => 'C.WinningAmount',
                'ContestSize' => 'C.ContestSize',
                'ContestFormat' => 'C.ContestFormat',
                'ContestType' => 'C.ContestType',
                'CustomizeWinning' => 'C.CustomizeWinning',
                'EntryFee' => 'C.EntryFee',
                'IsDummyJoined' => 'C.IsDummyJoined',
                'NoOfWinners' => 'C.NoOfWinners',
                'EntryType' => 'C.EntryType',
                'UserJoinLimit' => 'C.UserJoinLimit',
                'CashBonusContribution' => 'C.CashBonusContribution',
                'EntryType' => 'C.EntryType',
                'IsWinningDistributed' => 'C.IsWinningDistributed',
                'IsWinningDistributeAmount' => 'C.IsWinningDistributeAmount',
                'UserInvitationCode' => 'C.UserInvitationCode',
                'IsPrivacyNameDisplay' => 'C.IsPrivacyNameDisplay',
                'SeriesID' => 'M.SeriesID',
                'TeamNameLocal' => 'TL.TeamName AS TeamNameLocal',
                'TeamGUIDLocal' => 'TL.TeamGUID AS TeamGUIDLocal',
                'TeamGUIDVisitor' => 'TV.TeamGUID AS TeamGUIDVisitor',
                'TeamNameVisitor' => 'TV.TeamName AS TeamNameVisitor',
                'TeamNameShortLocal' => 'TL.TeamNameShort AS TeamNameShortLocal',
                'TeamNameShortVisitor' => 'TV.TeamNameShort AS TeamNameShortVisitor',
                'TeamFlagLocal' => 'CONCAT("' . BASE_URL . '","uploads/TeamFlag/",TL.TeamFlag) as TeamFlagLocal',
                'TeamFlagVisitor' => 'CONCAT("' . BASE_URL . '","uploads/TeamFlag/",TV.TeamFlag) as TeamFlagVisitor',
                'StatusID' => 'E.StatusID',
                'SeriesName' => 'S.SeriesName',
                'Status' => 'CASE E.StatusID
                                                    when "1" then "Pending"
                                                    when "2" then "Running"
                                                    when "3" then "Cancelled"
                                                    when "5" then "Completed"
                                                    END as Status',
                'MatchType' => 'MT.MatchTypeName AS MatchType',
                'CurrentDateTime' => 'DATE_FORMAT(CONVERT_TZ(Now(),"+00:00","' . DEFAULT_TIMEZONE . '"), "' . DATE_FORMAT . ' ") CurrentDateTime',
                'MatchDate' => 'DATE_FORMAT(CONVERT_TZ(M.MatchStartDateTime,"+00:00","' . DEFAULT_TIMEZONE . '"), "%Y-%m-%d") MatchDate',
                'MatchTime' => 'DATE_FORMAT(CONVERT_TZ(M.MatchStartDateTime,"+00:00","' . DEFAULT_TIMEZONE . '"), "%H:%i:%s") MatchTime',
            );
            if ($Params) {
                foreach ($Params as $Param) {
                    $Field .= (!empty($FieldArray[$Param]) ? ',' . $FieldArray[$Param] : '');
                }
            }
        }
        $this->db->select('C.ContestGUID,C.ContestName,C.ContestSize,C.ContestID');
        if (!empty($Field)) $this->db->select($Field, FALSE);
        $this->db->from('tbl_entity E, sports_contest C, sports_matches M, sports_teams TL, sports_teams TV,sports_series S,sports_set_match_types MT');
        $this->db->where("C.ContestID", "E.EntityID", FALSE);
        $this->db->where("M.MatchID", "C.MatchID", FALSE);
        $this->db->where("S.SeriesID", "C.SeriesID", FALSE);
        $this->db->where("M.TeamIDLocal", "TL.TeamID", FALSE);
        $this->db->where("M.TeamIDVisitor", "TV.TeamID", FALSE);
        $this->db->where("M.MatchTypeID", "MT.MatchTypeID", FALSE);
        $this->db->where("C.LeagueType", "Dfs");
        if (!empty($Where['Keyword'])) {
            if (is_array(json_decode($Where['Keyword'], true))) {
                $Where['Keyword'] = json_decode($Where['Keyword'], true);

                if (isset($Where['Keyword']['ContestName'])) {
                    $this->db->like("C.ContestName", @$Where['Keyword']['ContestName']);
                }
                if (isset($Where['Keyword']['ContestType'])) {
                    $this->db->where("C.ContestType", @$Where['Keyword']['ContestType']);
                }
                if (isset($Where['Keyword']['GameType'])) {
                    $this->db->where("C.GameType", @$Where['Keyword']['GameType']);
                }
                if (isset($Where['Keyword']['ContestSize'])) {
                    $ContestSize = explode("-", $Where['Keyword']['ContestSize']);

                    if (count($ContestSize) > 1) {
                        $this->db->where("C.ContestSize >=", @$ContestSize[0]);
                        $this->db->where("C.ContestSize <=", @$ContestSize[1]);
                    } else {
                        $this->db->where("C.ContestSize >=", @$ContestSize[0]);
                    }
                }
                if (isset($Where['Keyword']['EntryFee'])) {

                    $EntryFee = explode("-", $Where['Keyword']['EntryFee']);
                    if (count($EntryFee) > 1) {
                        $this->db->where("C.EntryFee >=", $EntryFee[0]);
                        $this->db->where("C.EntryFee <=", $EntryFee[1]);
                    } else {
                        $this->db->where("C.EntryFee >=", $EntryFee[0]);
                    }
                }
            } else {
                $this->db->group_start();
                $this->db->like("C.ContestName", $Where['Keyword']);
                $this->db->or_like("C.GameType", $Where['Keyword']);
                $this->db->or_like("C.WinningAmount", $Where['Keyword']);
                $this->db->or_like("C.ContestSize", $Where['Keyword']);
                $this->db->or_like("C.EntryFee", $Where['Keyword']);
                $this->db->or_like("M.MatchLocation", $Where['Keyword']);
                $this->db->or_like("M.MatchNo", $Where['Keyword']);
                $this->db->group_end();
            }
        }

        if (!empty($Where['AdvanceSafeValidate'])) {
            $this->db->where("M.MatchStartDateTime >= (UTC_TIMESTAMP() + INTERVAL C.GameTimeLive MINUTE)");
        }

        if (!empty($Where['ContestID'])) {
            $this->db->where("C.ContestID", $Where['ContestID']);
        }
        if (!empty($Where['ContestGUID'])) {
            $this->db->where("C.ContestGUID", $Where['ContestGUID']);
        }

        if (!empty($Where['IsVirtualUserJoined']) && $Where['IsVirtualUserJoined'] == 'Yes') {
            $this->db->where("C.IsVirtualUserJoined", $Where['IsVirtualUserJoined']);
        }
        if (!empty($Where['SeriesID'])) {
            $this->db->where("C.SeriesID", $Where['SeriesID']);
        }
        if (!empty($Where['UserID'])) {
            $this->db->where("C.UserID", $Where['UserID']);
        }
        if (!empty($Where['Filter']) && $Where['Filter'] == 'Today') {
            $this->db->where("DATE(M.MatchStartDateTime)", date('Y-m-d'));
        }
        if (!empty($Where['Filter']) && $Where['Filter'] == 'MatchLive') {
            $this->db->where("M.MatchStartDateTime <=", date('Y-m-d H:i:s'));
        }
        if (!empty($Where['IsWinningAmount']) && $Where['IsWinningAmount'] == 'Yes') {
            $this->db->where("C.WinningAmount >", 0);
        }
        if (!empty($Where['Filter']) && $Where['Filter'] == 'Yesterday') {
            $this->db->where("DATE(M.MatchStartDateTime) <=", date('Y-m-d'));
        }
        if (!empty($Where['GameType'])) {
            $this->db->where("C.GameType", $Where['GameType']);
        }
        if (!empty($Where['LeagueType'])) {
            $this->db->where("C.LeagueType", $Where['LeagueType']);
        }
        if (!empty($Where['Privacy']) && $Where['Privacy'] != 'All') {
            $this->db->where("C.Privacy", $Where['Privacy']);
        }
        if (!empty($Where['ContestType'])) {
            $this->db->where("C.ContestType", $Where['ContestType']);
        }
        if (!empty($Where['EntryStartFrom'])) {
            $this->db->where("C.EntryFee >=", $Where['EntryStartFrom']);
        }
        if (!empty($Where['EntryEndTo'])) {
            $this->db->where("C.EntryFee <=", $Where['EntryEndTo']);
        }
        if (!empty($Where['WinningStartFrom'])) {
            $this->db->where("C.WinningAmount >=", $Where['WinningStartFrom']);
        }
        if (!empty($Where['WinningEndTo'])) {
            $this->db->where("C.WinningAmount <=", $Where['WinningEndTo']);
        }
        if (!empty($Where['ContestSizeStartFrom'])) {
            $this->db->where("C.ContestSize >=", $Where['ContestSizeStartFrom']);
        }
        if (!empty($Where['ContestSizeEndTo'])) {
            $this->db->where("C.ContestSize <=", $Where['ContestSizeEndTo']);
        }
        if (!empty($Where['IsRefund'])) {
            $this->db->where("C.IsRefund", $Where['IsRefund']);
        }
        if (!empty($Where['IsWinningDistributeAmount'])) {
            $this->db->where("C.IsWinningDistributeAmount", $Where['IsWinningDistributeAmount']);
        }
        if (!empty($Where['isMailSent'])) {
            $this->db->where("C.isMailSent", $Where['isMailSent']);
        }
        if (!empty($Where['ContestFormat'])) {
            $this->db->where("C.ContestFormat", $Where['ContestFormat']);
        }
        if (!empty($Where['IsPaid'])) {
            $this->db->where("C.IsPaid", $Where['IsPaid']);
        }
        if (!empty($Where['IsConfirm'])) {
            $this->db->where("C.IsConfirm", $Where['IsConfirm']);
        }
        if (!empty($Where['WinningAmount'])) {
            $this->db->where("C.WinningAmount >=", $Where['WinningAmount']);
        }
        if (!empty($Where['ContestSize'])) {
            $this->db->where("C.ContestSize", $Where['ContestSize']);
        }
        if (!empty($Where['FromDate'])) {
            $this->db->where("DATE(M.MatchStartDateTime) >=", $Where['FromDate']);
        }
        if (!empty($Where['ToDate'])) {
            $this->db->where("DATE(M.MatchStartDateTime) <=", $Where['ToDate']);
        }
        if (!empty($Where['EntryFee'])) {
            $this->db->where("C.EntryFee", $Where['EntryFee']);
        }
        if (!empty($Where['SmartPool'])) {
            $this->db->where("C.SmartPool", $Where['SmartPool']);
        }
        if (!empty($Where['NoOfWinners'])) {
            $this->db->where("C.NoOfWinners", $Where['NoOfWinners']);
        }
        if (!empty($Where['EntryType'])) {
            $this->db->where("C.EntryType", $Where['EntryType']);
        }
        if (!empty($Where['MatchID'])) {
            $this->db->where("C.MatchID", $Where['MatchID']);
        }
        if (!empty($Where['SeriesID'])) {
            $this->db->where("M.SeriesID", $Where['SeriesID']);
        }
        if (!empty($Where['StatusID'])) {
            $this->db->where_in("E.StatusID", $Where['StatusID']);
        }
        if (!empty($Where['UserInvitationCode'])) {
            $this->db->where("C.UserInvitationCode", $Where['UserInvitationCode']);
        }
        if (!empty($Where['IsWinningDistributed'])) {
            $this->db->where("C.IsWinningDistributed", $Where['IsWinningDistributed']);
        }
        if (!empty($Where['ContestSizeRange'])) {
            $Range = explode('-', $Where['ContestSizeRange']);
            if (!empty($Range) && count($Range) == 2) {
                $this->db->where("C.ContestSize >=", $Range[0]);
                $this->db->where("C.ContestSize <=", $Range[1]);
            } else if (!empty($Range) && count($Range) == 1) {
                $this->db->where("C.ContestSize >=", $Range[0]);
            }
        }
        if (!empty($Where['EntryFeeRange'])) {
            $Range = explode('-', $Where['EntryFeeRange']);
            if (!empty($Range) && count($Range) == 2) {
                $this->db->where("C.EntryFee >=", $Range[0]);
                $this->db->where("C.EntryFee <=", $Range[1]);
            } else if (!empty($Range) && count($Range) == 1) {
                $this->db->where("C.EntryFee >=", $Range[0]);
            }
        }
        if (!empty($Where['ContestFull']) && $Where['ContestFull'] == 'No') {
            $this->db->having("TotalJoined !=", 'C.ContestSize', FALSE);
        }
        if (!empty($Where['OrderBy']) && !empty($Where['Sequence'])) {
            if (!empty($Where['ContestList']) && $Where['ContestList'] == 'Yes') {
                $this->db->order_by('TotalJoined - C.ContestSize !=0', 'DESC', FALSE);
            }
            $this->db->order_by($Where['OrderBy'], $Where['Sequence']);
            $this->db->order_by('EntryFee', 'ASC', FALSE);
        } else {
            if (!empty($Where['OrderByToday']) && $Where['OrderByToday'] == 'Yes') {
                $this->db->order_by('DATE(M.MatchStartDateTime)="' . date('Y-m-d') . '" DESC', null, FALSE);
                $this->db->order_by('E.StatusID=2 DESC', null, FALSE);
                $this->db->order_by('E.StatusID=1 DESC', null, FALSE);
            } else if (!empty($Where['OrderByList']) && $Where['OrderByList'] == 'Yes') {
                $this->db->order_by('M.MatchStartDateTime', 'DESC');
            } else {
                $this->db->order_by('M.MatchStartDateTime', 'ASC');
            }
        }

        /* Total records count only if want to get multiple records */
        if ($multiRecords) {
            $TempOBJ = clone $this->db;
            $TempQ = $TempOBJ->get();
            $Return['Data']['TotalRecords'] = $TempQ->num_rows();
        } else {
            $this->db->limit(1);
        }
        //$this->db->group_by('C.ContestID'); // Will manage later
        $Query = $this->db->get();
        if ($Query->num_rows() > 0) {
            if ($multiRecords) {
                $Records = array();
                foreach ($Query->result_array() as $key => $Record) {
                    $Records[] = $Record;
                    $Records[$key]['CustomizeWinning'] = (!empty($Record['CustomizeWinning'])) ? json_decode($Record['CustomizeWinning'], true) : array();
                    $Records[$key]['NoOfWinners'] = ($Record['NoOfWinners'] == 0 ) ? 1 : $Record['NoOfWinners'];
                    $Records[$key]['ContestSize'] = ($Record['ContestSize'] == 0 ) ? 'Unlimited' : $Record['ContestSize'];
                }
                $Return['Data']['Records'] = $Records;
            } else {
                $Record = $Query->row_array();
                $Record['CustomizeWinning'] = (!empty($Record['CustomizeWinning'])) ? json_decode($Record['CustomizeWinning'], TRUE) : array();
                return $Record;
            }
        } else {
            if (!$multiRecords) {
                return array();
            }
        }
        $Return['Data']['Records'] = empty($Records) ? array() : $Records;
        return $Return;
    }

    function getContestsExportsWithWinning($Field = '', $Where = array()) {
        $Params = array();
        if (!empty($Field)) {
            $Params = array_map('trim', explode(',', $Field));
            $Field = '';
            $FieldArray = array(
                'TotalPoints' => 'JC.TotalPoints',
                'UserWinningAmount' => 'JC.UserWinningAmount',
                'FirstName' => 'U.FirstName',
                'MiddleName' => 'U.MiddleName',
                'LastName' => 'U.LastName',
                'Username' => 'U.Username',
                'SmartPool' => 'JC.SmartPool',
                'SmartPoolWinning' => 'JC.SmartPoolWinning',
                'Email' => 'U.Email',
                'PhoneNumber' => 'U.PhoneNumber',
                'UserID' => 'U.UserID',
                'UserRank' => 'JC.UserRank',
                'UserTeamName' => 'UT.UserTeamName',
                'UserTeamID' => 'UT.UserTeamID',
                'ProfilePic' => 'IF(U.ProfilePic IS NULL,CONCAT("' . BASE_URL . '","uploads/profile/picture/","default.jpg"),CONCAT("' . BASE_URL . '","uploads/profile/picture/",U.ProfilePic)) AS ProfilePic',
                'UserRank' => 'JC.UserRank'
            );
            if ($Params) {
                foreach ($Params as $Param) {
                    $Field .= (!empty($FieldArray[$Param]) ? ',' . $FieldArray[$Param] : '');
                }
            }
        }
        $this->db->select('U.UserGUID,UT.UserTeamGUID');
        if (!empty($Field)) $this->db->select($Field, FALSE);
        $this->db->from('sports_contest_join JC, tbl_users U, sports_users_teams UT');
        $this->db->where("JC.UserTeamID", "UT.UserTeamID", FALSE);
        $this->db->where("JC.UserID", "U.UserID", FALSE);

        if (!empty($Where['PointFilter']) && $Where['PointFilter'] == 'TotalPoints') {
            $this->db->where("JC.TotalPoints >", 0);
        }
        if (!empty($Where['OnlyWinners']) && $Where['OnlyWinners'] == 'Yes') {
            $this->db->where("JC.UserWinningAmount >", 0);
        }
        if (!empty($Where['ContestID'])) {
            $this->db->where("JC.ContestID", $Where['ContestID']);
        }
        $this->db->order_by('JC.UserRank', 'ASC');
        $Query = $this->db->get();
        if ($Query->num_rows() > 0) {
            $Return['Records'] = $Query->result_array();
            return $Return;
        }
        return FALSE;
    }

    function getPrivateContestDataReport($Where = array(), $SeriesID = '', $MatchID = '') {

        $contestData = $this->db->query("SELECT C.ContestID,C.ContestName,C.EntryFee,U.FirstName,U.Email FROM sports_contest C join tbl_entity E on C.ContestID = E.EntityID Join tbl_users U on U.UserID = C.UserID Where C.SeriesID = $SeriesID AND C.MatchID = $MatchID AND E.StatusID = 5 AND Privacy = 'Yes' ")->result_array();

        if(!empty($contestData)){
            $MainData = [];
                foreach ($contestData as $key => $value) {
                    $UserData =  $this->db->query("SELECT J.UserID,count(J.UserID) as JoinCount, U.FirstName,U.Email  FROM sports_contest_join J Join tbl_users U on U.UserID = J.UserID WHERE J.ContestID = '".$value['ContestID']."' GROUP BY J.UserID  " )->result_array();
                    if($UserData){
                        $MainData[] = array(
                            
                            "ContestName" => $value['ContestName'],
                            "FirstName" => $value['FirstName'],
                            "Email" => $value['Email'],
                            "ContestID" => $value['ContestID'],
                            "EntryFee" => $value['EntryFee'],
                            "userJoinData" =>$UserData
                        );
                    }
                    
                }
        }
         if (!empty($MainData)) {
             $Return['Records'] = $MainData;
             
             return $Return;
         }else{
                return false;
         }
         
    }

    function freePrivateContestCheck($Input = array()) {
      $FreeContest = $this->db->query("SELECT ContestID FROM sports_contest WHERE MatchID = '".$Input['MatchID']."' AND IsPaid = '".$Input['IsPaid']."' AND ContestFormat = '".$Input['ContestFormat']."' AND Privacy = '".$Input['Privacy']."' AND UserID = '".$Input['UserID']."' ")->result();
      if(empty($FreeContest)){
        return true;
      }else{
        return false;
      }
    }

    /*
      Description: To get contest
     */

    function getDailyInvoiceReports($Where = array(), $multiRecords = FALSE, $PageNo = 1, $PageSize = 15) {
        $this->db->select('*');
        $this->db->from('tbl_daily_invoice_report');
        if (!empty($Where['CreatedAt']) && $Where['CreatedAt'] == 'Today') {
            $this->db->where("DATE(CreatedAt)", date('Y-m-d'));
        }else{
            $this->db->where("DATE(CreatedAt)", $Where['CreatedAt']);
        }
        if (!empty($Where['OrderBy']) && !empty($Where['Sequence'])) {
            $this->db->order_by($Where['OrderBy'], $Where['Sequence']);
        }
        /* Total records count only if want to get multiple records */
        if ($multiRecords) {
            $TempOBJ = clone $this->db;
            $TempQ = $TempOBJ->get();
            $Return['Data']['TotalRecords'] = $TempQ->num_rows();
        }
        $Query = $this->db->get();
        $Records = array();
        if ($Query->num_rows() > 0) {
            $Records = $Query->result_array();
        }

        $Return['Data']['Records'] = empty($Records) ? array() : $Records;
        return $Return;
    }


}

?>