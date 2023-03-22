<?php

if (!defined('BASEPATH')) exit('No direct script access allowed');

class SnakeDrafts_model extends CI_Model {

    public function __construct() {
        parent::__construct();
        $this->load->model('Sports_model');
        $this->load->model('Settings_model');
    }

    /*
      Description: To get all series
     */

    function getSeries($Field = '', $Where = array(), $multiRecords = FALSE, $PageNo = 1, $PageSize = 15) {
        $Params = array();
        if (!empty($Field)) {
            $Params = array_map('trim', explode(',', $Field));
            $Field = '';
            $FieldArray = array(
                'SeriesID' => 'S.SeriesID',
                'DraftUserLimit' => 'S.DraftUserLimit',
                'DraftTeamPlayerLimit' => 'S.DraftTeamPlayerLimit',
                'DraftPlayerSelectionCriteria' => 'S.DraftPlayerSelectionCriteria',
                'StatusID' => 'E.StatusID',
                'SeriesIDLive' => 'S.SeriesIDLive',
                'AuctionDraftIsPlayed' => 'S.AuctionDraftIsPlayed',
                'DraftUserLimit' => 'S.DraftUserLimit',
                'DraftTeamPlayerLimit' => 'S.DraftTeamPlayerLimit',
                'DraftPlayerSelectionCriteria' => 'S.DraftPlayerSelectionCriteria',
                'SeriesStartDate' => 'DATE_FORMAT(CONVERT_TZ(S.SeriesStartDate,"+00:00","' . DEFAULT_TIMEZONE . '"), "' . DATE_FORMAT . '") SeriesStartDate',
                'SeriesEndDate' => 'DATE_FORMAT(CONVERT_TZ(S.SeriesEndDate,"+00:00","' . DEFAULT_TIMEZONE . '"), "' . DATE_FORMAT . '") SeriesEndDate',
                'SeriesStartDateUTC' => 'S.SeriesStartDate as SeriesStartDateUTC',
                'SeriesEndDateUTC' => 'S.SeriesEndDate as SeriesEndDateUTC',
                'TotalMatches' => '(SELECT COUNT(*) AS TotalMatches
                FROM sports_matches
                WHERE sports_matches.SeriesID =  S.SeriesID ) AS TotalMatches',
                'SeriesMatchStartDate' => '(SELECT MatchStartDateTime AS SeriesMatchStartDate
                FROM sports_matches
                WHERE sports_matches.SeriesID =  S.SeriesID order by MatchStartDateTime asc limit 1) AS SeriesMatchStartDate',
                'Status' => 'CASE E.StatusID
                when "2" then "Active"
                when "6" then "Inactive"
                END as Status',
                'AuctionDraftStatus' => 'CASE S.AuctionDraftStatusID
                when "1" then "Pending"
                when "2" then "Running"
                when "5" then "Completed"
                END as AuctionDraftStatus',
                //'IsJoinedContest' => '(select count(SeriesID) from sports_contest_join where SeriesID = S.SeriesID AND E.StatusID=' . (!is_array(@$Where['StatusID'])) ? @$Where['StatusID'] : 2 . ') AND UserID=' . @$Where['SessionUserID'] . ') as IsJoinedContest',
                'TotalUserWinning' => '(select SUM(JC.UserWinningAmount) from sports_contest_join JC where JC.SeriesID = S.SeriesID AND E.StatusID=6 AND JC.UserID=' . @$Where['SessionUserID'] . ') as TotalUserWinning'
            );

            if ($Params) {
                foreach ($Params as $Param) {
                    $Field .= (!empty($FieldArray[$Param]) ? ',' . $FieldArray[$Param] : '');
                }
            }
        }
        $this->db->select('S.SeriesGUID,S.SeriesName');
        if (!empty($Field)) $this->db->select($Field, FALSE);
        $this->db->from('tbl_entity E, sports_series S');
        $this->db->where("S.SeriesID", "E.EntityID", FALSE);
        if (!empty($Where['Keyword'])) {
            $this->db->like("S.SeriesName", $Where['Keyword']);
        }
        if (!empty($Where['DraftAuctionPlay']) && $Where['DraftAuctionPlay'] == "Yes") {
            $this->db->where("S.AuctionDraftIsPlayed", "Yes");
        }
        if (!empty($Where['SeriesID'])) {
            $this->db->where("S.SeriesID", $Where['SeriesID']);
        }
        if (!empty($Where['AuctionDraftIsPlayed'])) {
            $this->db->where("S.AuctionDraftIsPlayed", $Where['AuctionDraftIsPlayed']);
        }
        if (!empty($Where['SeriesStartDate'])) {
            $this->db->where("S.SeriesStartDate >=", $Where['SeriesStartDate']);
        }
        if (!empty($Where['SeriesEndDate'])) {
            $this->db->where("S.SeriesEndDate >=", $Where['SeriesEndDate']);
        }
        if (!empty($Where['StatusID'])) {
            $this->db->where("E.StatusID", $Where['StatusID']);
        }
        if (!empty($Where['AuctionDraftIsPlayed'])) {
            $this->db->where("S.AuctionDraftIsPlayed", $Where['AuctionDraftIsPlayed']);
        }
        if (!empty($Where['AuctionDraftStatusID'])) {
            $this->db->where("S.AuctionDraftStatusID", $Where['AuctionDraftStatusID']);
        }

        if (isset($Where['MyJoinedSeries']) && $Where['MyJoinedSeries'] = "Yes") {
            $this->db->where('EXISTS (select ContestID from sports_contest_join JE where JE.SeriesID = S.SeriesID AND JE.UserID=' . @$Where['SessionUserID'] . ')');
        }

        if (!empty($Where['OrderBy']) && !empty($Where['Sequence'])) {
            $this->db->order_by($Where['OrderBy'], $Where['Sequence']);
        }
        $this->db->order_by('E.StatusID', 'ASC');
        $this->db->order_by('S.SeriesStartDate', 'DESC');
        $this->db->order_by('S.SeriesName', 'ASC');

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
                if (!empty($Where['MyJoinedSeriesCount']) && $Where['MyJoinedSeriesCount'] == "Yes") {
                    $Return['Data']['Statics'] = $this->db->query('SELECT (
                            SELECT COUNT(DISTINCT S.SeriesID) AS `UpcomingJoinedContest` FROM `sports_series` S
                            JOIN `sports_contest_join` J ON S.SeriesID = J.SeriesID JOIN `tbl_entity` E ON E.EntityID = J.SeriesID WHERE E.StatusID = 1 AND J.UserID ="' . @$Where['SessionUserID'] . '" 
                        )as UpcomingJoinedContest,
                        ( SELECT COUNT(DISTINCT S.SeriesID) AS `LiveJoinedContest` FROM `sports_series` S JOIN `sports_contest_join` J ON S.SeriesID = J.SeriesID JOIN `tbl_entity` E ON E.EntityID = J.SeriesID WHERE  E.StatusID = 2 AND J.UserID = "' . @$Where['SessionUserID'] . '" 
                        )as LiveJoinedContest,
                        ( SELECT COUNT(DISTINCT S.SeriesID) AS `CompletedJoinedContest` FROM `sports_series` S JOIN `sports_contest_join` J ON S.SeriesID = J.SeriesID JOIN `tbl_entity` E ON E.EntityID = J.SeriesID WHERE  E.StatusID = 5 AND J.UserID = "' . @$Where['SessionUserID'] . '" 
                    )as CompletedJoinedContest'
                            )->row();
                }
                return $Return;
            } else {
                return $Query->row_array();
            }
        }
        return FALSE;
    }

    /*
      Description: To get all series rounds
     */

    function getSeriesRounds($Field = '', $Where = array(), $multiRecords = FALSE, $PageNo = 1, $PageSize = 15) {
        $Params = array();
        if (!empty($Field)) {
            $Params = array_map('trim', explode(',', $Field));
            $Field = '';
            $FieldArray = array(
                'SeriesID' => 'S.SeriesID',
                'RoundID' => 'S.RoundID',
                'DraftUserLimit' => 'S.DraftUserLimit',
                'DraftTeamPlayerLimit' => 'S.DraftTeamPlayerLimit',
                'DraftPlayerSelectionCriteria' => 'S.DraftPlayerSelectionCriteria',
                'StatusID' => 'S.StatusID',
                'SeriesIDLive' => 'S.RoundIDLive SeriesIDLive',
                'AuctionDraftIsPlayed' => 'S.AuctionDraftIsPlayed',
                'DraftUserLimit' => 'S.DraftUserLimit',
                'DraftTeamPlayerLimit' => 'S.DraftTeamPlayerLimit',
                'DraftPlayerSelectionCriteria' => 'S.DraftPlayerSelectionCriteria',
                'SeriesStartDate' => 'DATE_FORMAT(CONVERT_TZ(S.RoundStartDate,"+00:00","' . DEFAULT_TIMEZONE . '"), "' . DATE_FORMAT . '") SeriesStartDate',
                'SeriesEndDate' => 'DATE_FORMAT(CONVERT_TZ(S.RoundEndDate,"+00:00","' . DEFAULT_TIMEZONE . '"), "' . DATE_FORMAT . '") SeriesEndDate',
                'SeriesStartDateUTC' => 'S.RoundStartDate as SeriesStartDateUTC',
                'SeriesEndDateUTC' => 'S.RoundEndDate as SeriesEndDateUTC',
                'TotalMatches' => '(SELECT COUNT(*) AS TotalMatches
                FROM sports_matches
                WHERE sports_matches.RoundID =  S.RoundID ) AS TotalMatches',
                'SeriesMatchStartDate' => '(SELECT MatchStartDateTime AS SeriesMatchStartDate
                FROM sports_matches
                WHERE sports_matches.RoundID =  S.RoundID order by MatchStartDateTime asc limit 1) AS SeriesMatchStartDate',
                'Status' => 'CASE S.StatusID
                when "2" then "Active"
                when "6" then "Inactive"
                END as Status',
                'AuctionDraftStatus' => 'CASE S.AuctionDraftStatusID
                when "1" then "Pending"
                when "2" then "Running"
                when "5" then "Completed"
                END as AuctionDraftStatus',
                //'IsJoinedContest' => '(select count(SeriesID) from sports_contest_join where SeriesID = S.SeriesID AND E.StatusID=' . (!is_array(@$Where['StatusID'])) ? @$Where['StatusID'] : 2 . ') AND UserID=' . @$Where['SessionUserID'] . ') as IsJoinedContest',
                'TotalUserWinning' => '(select SUM(JC.UserWinningAmount) from sports_contest_join JC where JC.RoundID = S.RoundID AND S.StatusID=6 AND JC.UserID=' . @$Where['SessionUserID'] . ') as TotalUserWinning'
            );

            if ($Params) {
                foreach ($Params as $Param) {
                    $Field .= (!empty($FieldArray[$Param]) ? ',' . $FieldArray[$Param] : '');
                }
            }
        }
        $this->db->select('S.RoundID,S.RoundName SeriesName');
        if (!empty($Field)) $this->db->select($Field, FALSE);
        $this->db->from('sports_series_rounds S');
        if (!empty($Where['Keyword'])) {
            $this->db->like("S.RoundName", $Where['Keyword']);
        }
        if (!empty($Where['DraftAuctionPlay']) && $Where['DraftAuctionPlay'] == "Yes") {
            $this->db->where("S.AuctionDraftIsPlayed", "Yes");
        }
        if (!empty($Where['SeriesID'])) {
            $this->db->where("S.SeriesID", $Where['SeriesID']);
        }
        if (!empty($Where['RoundID'])) {
            $this->db->where("S.RoundID", $Where['RoundID']);
        }
        if (!empty($Where['AuctionDraftIsPlayed'])) {
            $this->db->where("S.AuctionDraftIsPlayed", $Where['AuctionDraftIsPlayed']);
        }
        if (!empty($Where['SeriesStartDate'])) {
            $this->db->where("S.SeriesStartDate >=", $Where['SeriesStartDate']);
        }
        if (!empty($Where['SeriesEndDate'])) {
            $this->db->where("S.SeriesEndDate >=", $Where['SeriesEndDate']);
        }
        if (!empty($Where['StatusID'])) {
            $this->db->where("S.StatusID", $Where['StatusID']);
        }
        if (!empty($Where['AuctionDraftIsPlayed'])) {
            $this->db->where("S.AuctionDraftIsPlayed", $Where['AuctionDraftIsPlayed']);
        }
        if (!empty($Where['AuctionDraftStatusID'])) {
            $this->db->where("S.AuctionDraftStatusID", $Where['AuctionDraftStatusID']);
        }

        if (isset($Where['MyJoinedSeries']) && $Where['MyJoinedSeries'] = "Yes") {
            $this->db->where('EXISTS (select JE.ContestID from sports_contest_join JE,sports_contest C where C.ContestID=JE.ContestID AND C.LeagueType="Draft" AND JE.RoundID = S.RoundID AND JE.UserID=' . @$Where['SessionUserID'] . ')');
        }

        if (!empty($Where['OrderBy']) && !empty($Where['Sequence'])) {
            $this->db->order_by($Where['OrderBy'], $Where['Sequence']);
        }
        $this->db->order_by('S.StatusID', 'ASC');
        $this->db->order_by('S.RoundStartDate', 'DESC');
        $this->db->order_by('S.RoundName', 'ASC');

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
                if (!empty($Where['MyJoinedSeriesCount']) && $Where['MyJoinedSeriesCount'] == "Yes") {
                    $Return['Data']['Statics'] = $this->db->query('SELECT (
                            SELECT COUNT(DISTINCT S.RoundID) AS `UpcomingJoinedContest` FROM `sports_series_rounds` S
                            JOIN `sports_contest_join` J ON S.RoundID = J.RoundID WHERE S.AuctionDraftStatusID = 1 AND J.UserID ="' . @$Where['SessionUserID'] . '" 
                        )as UpcomingJoinedContest,
                        ( SELECT COUNT(DISTINCT S.RoundID) AS `LiveJoinedContest` FROM `sports_series_rounds` S JOIN `sports_contest_join` J ON S.RoundID = J.RoundID WHERE  S.AuctionDraftStatusID = 2 AND J.UserID = "' . @$Where['SessionUserID'] . '" 
                        )as LiveJoinedContest,
                        ( SELECT COUNT(DISTINCT S.RoundID) AS `CompletedJoinedContest` FROM `sports_series_rounds` S JOIN `sports_contest_join` J ON S.RoundID = J.RoundID WHERE  S.AuctionDraftStatusID = 5 AND J.UserID = "' . @$Where['SessionUserID'] . '" 
                    )as CompletedJoinedContest'
                            )->row();
                }
                return $Return;
            } else {
                return $Query->row_array();
            }
        }
        return FALSE;
    }

    /*
      Description:    ADD contest to system.
     */

    function addContest($Input = array(), $SessionUserID, $MatchID, $SeriesID, $StatusID = 1) {
        $defaultCustomizeWinningObj = new stdClass();
        $defaultCustomizeWinningObj->From = 1;
        $defaultCustomizeWinningObj->To = 1;
        $defaultCustomizeWinningObj->Percent = 100;
        $defaultCustomizeWinningObj->WinningAmount = @$Input['WinningAmount'];

        $this->db->trans_start();
        $EntityGUID = get_guid();

        // $Series = $this->Sports_model->getSeries("DraftTeamPlayerLimit,DraftPlayerSelectionCriteria", array("SeriesID" => $SeriesID));
        $DraftTeamPlayerLimit = @$Input['DraftTeamPlayerLimit'];

        /* Add contest to entity table and get EntityID. */
        $EntityID = $this->Entity_model->addEntity($EntityGUID, array("EntityTypeID" => 11, "UserID" => $SessionUserID, "StatusID" => $StatusID));
        $LeagueJoinDateTime = strtotime(@$Input['LeagueJoinDateTime']) + strtotime('-330 minutes', 0);
        /* Add contest to contest table . */
        $InsertData = array_filter(array(
            "ContestID" => $EntityID,
            "ContestGUID" => $EntityGUID,
            "UserID" => ($SessionUserID == 125) ? null : $SessionUserID,
            "MatchID" => $MatchID,
            //"ContestName" => (!empty(@$Input['ContestName'])) ? @$Input['ContestName'] : (@$Input['IsPaid'] == "Yes") ? "Win ".@$Input['WinningAmount'] : "Win Skill",
            "ContestName" => @$Input['ContestName'],
            "LeagueType" => @$Input['LeagueType'],
            "LeagueJoinDateTime" => (@$Input['LeagueJoinDateTime']) ? date('Y-m-d H:i', $LeagueJoinDateTime) : null,
            "AuctionUpdateTime" => (@$Input['LeagueJoinDateTime']) ? date('Y-m-d H:i', $LeagueJoinDateTime + 3600) : null,
            "ContestFormat" => @$Input['ContestFormat'],
            "ContestType" => @$Input['ContestType'],
            "Privacy" => @$Input['Privacy'],
            "IsPaid" => @$Input['IsPaid'],
            "IsConfirm" => @$Input['IsConfirm'],
            "LeagueType" => "Draft",
            "ShowJoinedContest" => @$Input['ShowJoinedContest'],
            "WinningAmount" => @$Input['WinningAmount'],
            "GameType" => @$Input['GameType'],
            "GameTimeLive" => @$Input['GameTimeLive'],
            "AdminPercent" => @$Input['AdminPercent'],
            "ContestSize" => (@$Input['ContestFormat'] == 'Head to Head') ? 2 : @$Input['ContestSize'],
            "EntryFee" => (@$Input['IsPaid'] == 'Yes') ? @$Input['EntryFee'] : 0,
            "NoOfWinners" => (@$Input['IsPaid'] == 'Yes') ? @$Input['NoOfWinners'] : 1,
            "EntryType" => @$Input['EntryType'],
            "UserJoinLimit" => (@$Input['EntryType'] == 'Multiple') ? @$Input['UserJoinLimit'] : 1,
            "CashBonusContribution" => @$Input['CashBonusContribution'],
            "CustomizeWinning" => (@$Input['IsPaid'] == 'Yes') ? @$Input['CustomizeWinning'] : json_encode(array($defaultCustomizeWinningObj)),
            "SeriesID" => @$SeriesID,
            "MatchID" => @$MatchID,
            "UserInvitationCode" => random_string('alnum', 6),
            "MinimumUserJoined" => @$Input['MinimumUserJoined'],
            "DraftTotalRounds" => $DraftTeamPlayerLimit,
            "DraftTeamPlayerLimit" => $DraftTeamPlayerLimit,
            "DraftLiveRound" => 1,
            //"IsCalculateCaptainVC" => @$Input['IsCalculateCaptainVC'],
            //"PointSystem" => @$Input['PointSystem'],
            "DraftPlayerSelectionCriteria" => @$Input['DraftPlayerSelectionCriteria'],
            "RoundID" => @$Input['RoundID']
        ));
        $this->db->insert('sports_contest', $InsertData);
        $this->addAuctionPlayerDraft($SeriesID, $EntityID, $Input['RoundID'], $MatchID);
        $this->db->trans_complete();
        if ($this->db->trans_status() === FALSE) {
            return FALSE;
        }
        return $EntityID;
    }

    /*
      Description:    ADD auction players
     */

    function addAuctionPlayerDraft($SeriesID, $ContestID, $RoundID, $MatchID) {


        $playersData = $this->Sports_model->getPlayers("PlayerID,PlayerName,PlayerRole", array('MatchID' => $MatchID, 'OrderBy' => "PlayerID", "Sequence" => "ASC"), TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']);

        if ($playersData['Data']['TotalRecords'] > 0) {
            $Players = $playersData['Data']['Records'];
            shuffle($Players);
            if (!empty($Players)) {
                $InsertBatch = array();
                $InsertBatchPlayer = array();
                foreach ($Players as $Player) {
                    $Temp['SeriesID'] = $SeriesID;
                    $Temp['RoundID'] = $RoundID;
                    $Temp['MatchID'] = $MatchID;
                    $Temp['ContestID'] = $ContestID;
                    $Temp['PlayerID'] = $Player['PlayerID'];
                    $Temp['PlayerRole'] = $Player['PlayerRole'];
                    $Temp['BidCredit'] = 0;
                    $Temp['PlayerStatus'] = "Upcoming";
                    $InsertBatch[] = $Temp;

                    $Temp1['SeriesID'] = $SeriesID;
                    $Temp1['RoundID'] = $RoundID;
                    $Temp1['ContestID'] = $ContestID;
                    $Temp1['PlayerID'] = $Player['PlayerID'];
                    $Temp1['PlayerRole'] = $Player['PlayerRole'];
                    $InsertBatchPlayer[] = $Temp1;
                }
                if (!empty($InsertBatch)) {
                    $this->db->insert_batch('tbl_auction_player_bid_status', $InsertBatch);
                }
            }
        }

        /* $playersData = $this->getPlayers("PlayerID,PlayerName,PlayerRole", array('RoundID' => $RoundID, 'OrderBy' => "PlayerID", "Sequence" => "ASC"), TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']);
          if ($playersData['Data']['TotalRecords'] > 0) {
          $Players = $playersData['Data']['Records'];
          shuffle($Players);
          if (!empty($Players)) {
          $InsertBatch = array();
          $InsertBatchPlayer = array();
          foreach ($Players as $Player) {
          $Temp['SeriesID'] = $SeriesID;
          $Temp['RoundID'] = $RoundID;
          $Temp['ContestID'] = $ContestID;
          $Temp['PlayerID'] = $Player['PlayerID'];
          $Temp['PlayerRole'] = $Player['PlayerRole'];
          $Temp['BidCredit'] = 0;
          $Temp['PlayerStatus'] = "Upcoming";
          $InsertBatch[] = $Temp;

          $Temp1['SeriesID'] = $SeriesID;
          $Temp1['RoundID'] = $RoundID;
          $Temp1['ContestID'] = $ContestID;
          $Temp1['PlayerID'] = $Player['PlayerID'];
          $Temp1['PlayerRole'] = $Player['PlayerRole'];
          $InsertBatchPlayer[] = $Temp1;
          }
          if (!empty($InsertBatch)) {
          $this->db->insert_batch('tbl_auction_player_bid_status', $InsertBatch);

          $Query = $this->db->query('SELECT SeriesID FROM sports_auction_draft_player_point WHERE RoundID = "' . $RoundID . '" LIMIT 1');
          $SeriesID = ($Query->num_rows() > 0) ? $Query->row()->SeriesID : false;
          if (!$SeriesID) {
          $this->db->insert_batch('sports_auction_draft_player_point', $InsertBatchPlayer);
          }
          }
          }
          } */
    }

    function addAuctionPlayerDraftOLDPlayers($SeriesID, $ContestID, $RoundID) {
        $this->db->select('PlayerID,PlayerRole,TeamID');
        $this->db->from('sports_team_players');
        $this->db->where("RoundID", $RoundID);
        $this->db->where("SeriesID", $SeriesID);
        $this->db->group_by("PlayerID");
        $Query = $this->db->get();
        if ($Query->num_rows() > 0) {
            $RoundPlayers = $Query->result_array();
            $Players = $RoundPlayers;
            shuffle($Players);
            if (!empty($Players)) {
                $InsertBatch = array();
                $InsertBatchPlayer = array();
                $InsertBatchPlayerPrivateContest = array();
                foreach ($Players as $Player) {
                    $Temp['SeriesID'] = $SeriesID;
                    $Temp['RoundID'] = $RoundID;
                    $Temp['ContestID'] = $ContestID;
                    $Temp['PlayerID'] = $Player['PlayerID'];
                    $Temp['PlayerRole'] = $Player['PlayerRole'];
                    $Temp['BidCredit'] = 0;
                    $Temp['PlayerStatus'] = "Upcoming";
                    $InsertBatch[] = $Temp;

                    $Temp1['SeriesID'] = $SeriesID;
                    $Temp1['RoundID'] = $RoundID;
                    $Temp1['ContestID'] = $ContestID;
                    $Temp1['PlayerID'] = $Player['PlayerID'];
                    $Temp1['PlayerRole'] = $Player['PlayerRole'];
                    $InsertBatchPlayer[] = $Temp1;

                    $Temp2['SeriesID'] = $SeriesID;
                    $Temp2['TeamID'] = $Player['TeamID'];
                    $Temp2['RoundID'] = $RoundID;
                    $Temp2['ContestID'] = $ContestID;
                    $Temp2['PlayerID'] = $Player['PlayerID'];
                    $Temp2['PlayerRole'] = $Player['PlayerRole'];
                    $Temp2['IsPlaying'] = "Yes";
                    $InsertBatchPlayerPrivateContest[] = $Temp2;
                }
                if (!empty($InsertBatch)) {
                    $this->db->insert_batch('tbl_auction_player_bid_status', $InsertBatch);

                    $Query = $this->db->query('SELECT SeriesID FROM sports_auction_draft_player_point WHERE RoundID = "' . $RoundID . '" LIMIT 1');
                    $SeriesID = ($Query->num_rows() > 0) ? $Query->row()->SeriesID : false;
                    if (!$SeriesID) {
                        $this->db->insert_batch('sports_auction_draft_player_point', $InsertBatchPlayer);
                    }
                    if ($InsertBatchPlayerPrivateContest) {
                        //$this->db->insert_batch('sports_private_contest_team_players', $InsertBatchPlayerPrivateContest);
                        //$this->db->insert_batch('sports_auction_draft_player_point_private', $InsertBatchPlayer);
                    }
                }
            }
        }
    }

    /*
      Description:    ADD auction players
     */

    function addAuctionPlayer($SeriesID, $ContestID) {
        $playersData = $this->getPlayersDraft("PlayerID,PlayerName,PlayerRole", array('SeriesID' => $SeriesID, 'OrderBy' => "PlayerID", "Sequence" => "ASC"), TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']);
        if ($playersData['Data']['TotalRecords'] > 0) {
            $Players = $playersData['Data']['Records'];
            shuffle($Players);
            if (!empty($Players)) {
                $InsertBatch = array();
                $InsertBatchPlayer = array();
                foreach ($Players as $Player) {
                    $Temp['SeriesID'] = $SeriesID;
                    $Temp['ContestID'] = $ContestID;
                    $Temp['PlayerID'] = $Player['PlayerID'];
                    $Temp['PlayerRole'] = $Player['PlayerRole'];
                    $Temp['BidCredit'] = 0;
                    $Temp['PlayerStatus'] = "Upcoming";
                    $InsertBatch[] = $Temp;

                    $Temp1['SeriesID'] = $SeriesID;
                    $Temp1['ContestID'] = $ContestID;
                    $Temp1['PlayerID'] = $Player['PlayerID'];
                    $Temp1['PlayerRole'] = $Player['PlayerRole'];
                    $InsertBatchPlayer[] = $Temp1;
                }
                if (!empty($InsertBatch)) {
                    $this->db->insert_batch('tbl_auction_player_bid_status', $InsertBatch);

                    $Query = $this->db->query('SELECT SeriesID FROM sports_auction_draft_player_point WHERE SeriesID = "' . $SeriesID . '" LIMIT 1');
                    $SeriesID = ($Query->num_rows() > 0) ? $Query->row()->SeriesID : false;
                    if (!$SeriesID) {
                        $this->db->insert_batch('sports_auction_draft_player_point', $InsertBatchPlayer);
                    }
                }
            }
        }
    }

    function getPlayersDraft($Field = '', $Where = array(), $multiRecords = FALSE, $PageNo = 1, $PageSize = 15) {
        $Params = array();
        if (!empty($Field)) {
            $Params = array_map('trim', explode(',', $Field));
            $Field = '';
            $FieldArray = array(
                'PlayerID' => 'P.PlayerID',
                'PlayerSalary' => 'P.PlayerSalary',
                'BidCredit' => 'UTP.BidCredit',
                'ContestID' => 'APBS.ContestID as ContestID',
                'SeriesID' => 'APBS.SeriesID as SeriesID',
                'BidSoldCredit' => '(SELECT BidCredit FROM tbl_auction_player_bid_status WHERE SeriesID=' . $Where['SeriesID'] . ' AND ContestID=' . $Where['ContestID'] . ' AND PlayerID=P.PlayerID) BidSoldCredit',
                'SeriesGUID' => 'S.SeriesGUID as SeriesGUID',
                'ContestGUID' => 'C.ContestGUID as ContestGUID',
                'BidDateTime' => 'APBS.DateTime as BidDateTime',
                'TimeDifference' => " IF(APBS.DateTime IS NULL,20,TIMEDIFF(UTC_TIMESTAMP,APBS.DateTime)) as TimeDifference",
                'PlayerStatus' => '(SELECT PlayerStatus FROM tbl_auction_player_bid_status WHERE PlayerID=P.PlayerID AND SeriesID=' . @$Where['SeriesID'] . ' AND ContestID=' . @$Where['ContestID'] . ') as PlayerStatus',
                'UserTeamGUID' => 'UT.UserTeamGUID',
                'UserID' => 'UT.UserID',
                'PlayerPosition' => 'UTP.PlayerPosition',
                'AuctionTopPlayerSubmitted' => 'UT.AuctionTopPlayerSubmitted',
                'IsAssistant' => 'UT.IsAssistant',
                'UserTeamName' => 'UT.UserTeamName',
                'PlayerIDLive' => 'P.PlayerIDLive',
                'PlayerPic' => 'IF(P.PlayerPic IS NULL,CONCAT("' . BASE_URL . '","uploads/PlayerPic/","player.png"),CONCAT("' . BASE_URL . '","uploads/PlayerPic/",P.PlayerPic)) AS PlayerPic',
                'PlayerCountry' => 'P.PlayerCountry',
                'PlayerBattingStyle' => 'P.PlayerBattingStyle',
                'PlayerBowlingStyle' => 'P.PlayerBowlingStyle',
                'PlayerBattingStats' => 'P.PlayerBattingStats',
                'PlayerBowlingStats' => 'P.PlayerBowlingStats',
                'LastUpdateDiff' => 'IF(P.LastUpdatedOn IS NULL, 0, TIME_TO_SEC(TIMEDIFF("' . date('Y-m-d H:i:s') . '", P.LastUpdatedOn))) LastUpdateDiff',
            );
            if ($Params) {
                foreach ($Params as $Param) {
                    $Field .= (!empty($FieldArray[$Param]) ? ',' . $FieldArray[$Param] : '');
                }
            }
        }
        $this->db->select('P.PlayerGUID,P.PlayerName');
        if (!empty($Field)) $this->db->select($Field, FALSE);
        $this->db->from('tbl_entity E, sports_players P');

        if (!empty($Where['PlayerBidStatus']) && $Where['PlayerBidStatus'] == "Yes") {
            $this->db->from('tbl_auction_player_bid_status APBS,sports_series S,sports_contest C');
            $this->db->where("APBS.PlayerID", "P.PlayerID", FALSE);
            $this->db->where("S.SeriesID", "APBS.SeriesID", FALSE);
            $this->db->where("C.ContestID", "APBS.ContestID", FALSE);
            if (!empty($Where['PlayerStatus'])) {
                $this->db->where("APBS.PlayerStatus", $Where['PlayerStatus']);
            }
            if (!empty($Where['ContestID'])) {
                $this->db->where("APBS.ContestID", $Where['ContestID']);
            }
        }

        if (!empty($Where['MySquadPlayer']) && $Where['MySquadPlayer'] == "Yes") {
            $this->db->from('sports_users_teams UT, sports_users_team_players UTP');
            $this->db->where("UTP.PlayerID", "P.PlayerID", FALSE);
            $this->db->where("UT.UserTeamID", "UTP.UserTeamID", FALSE);
            if (!empty($Where['SessionUserID'])) {
                $this->db->where("UT.UserID", @$Where['SessionUserID']);
            }
            if (!empty($Where['IsAssistant'])) {
                $this->db->where("UT.IsAssistant", @$Where['IsAssistant']);
            }
            if (!empty($Where['IsPreTeam'])) {
                $this->db->where("UT.IsPreTeam", @$Where['IsPreTeam']);
            }
            if (!empty($Where['BidCredit'])) {
                $this->db->where("UTP.BidCredit >", @$Where['BidCredit']);
            }
            $this->db->where("UT.ContestID", @$Where['ContestID']);
        }
        $this->db->where("P.PlayerID", "E.EntityID", FALSE);
        if (!empty($Where['Keyword'])) {
            $Where['Keyword'] = trim($Where['Keyword']);
            $this->db->group_start();
            $this->db->like("P.PlayerName", $Where['Keyword']);
            $this->db->or_like("P.PlayerRole", $Where['Keyword']);
            $this->db->or_like("P.PlayerCountry", $Where['Keyword']);
            $this->db->or_like("P.PlayerBattingStyle", $Where['Keyword']);
            $this->db->or_like("P.PlayerBowlingStyle", $Where['Keyword']);
            $this->db->group_end();
        }
        $this->db->where('EXISTS (select PlayerID FROM sports_team_players WHERE PlayerID=P.PlayerID AND SeriesID=' . @$Where['SeriesID'] . ')');
        if (!empty($Where['TeamID'])) {
            $this->db->where("TP.TeamID", $Where['TeamID']);
        }
        if (!empty($Where['IsPlaying'])) {
            $this->db->where("TP.IsPlaying", $Where['IsPlaying']);
        }
        if (!empty($Where['PlayerID'])) {
            $this->db->where("P.PlayerID", $Where['PlayerID']);
        }
        if (!empty($Where['IsAdminSalaryUpdated'])) {
            $this->db->where("P.IsAdminSalaryUpdated", $Where['IsAdminSalaryUpdated']);
        }
        if (!empty($Where['StatusID'])) {
            $this->db->where("E.StatusID", $Where['StatusID']);
        }
        if (!empty($Where['CronFilter']) && $Where['CronFilter'] == 'OneDayDiff') {
            $this->db->having("LastUpdateDiff", 0);
            $this->db->or_having("LastUpdateDiff >=", 86400); // 1 Day
        }

        if (!empty($Where['OrderBy']) && !empty($Where['Sequence'])) {
            $this->db->order_by($Where['OrderBy'], $Where['Sequence']);
        }

        if (!empty($Where['RandData'])) {
            $this->db->order_by($Where['RandData']);
        } else {
            //$this->db->order_by('P.PlayerSalary', 'DESC');
            //$this->db->order_by('P.PlayerID', 'DESC');
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

        // $this->db->cache_on(); //Turn caching on
        $Query = $this->db->get();
        //echo $this->db->last_query();exit;
        if ($Query->num_rows() > 0) {
            if ($multiRecords) {
                $Records = array();
                foreach ($Query->result_array() as $key => $Record) {
                    $Records[] = $Record;
                    $IsAssistant = "";
                    $AuctionTopPlayerSubmitted = "No";
                    $UserTeamGUID = "";
                    $UserTeamName = "";
                    // $Records[$key]['PlayerSalary'] = $Record['PlayerSalary']*10000000;
                    $Records[$key]['PlayerBattingStats'] = (!empty($Record['PlayerBattingStats'])) ? json_decode($Record['PlayerBattingStats']) : new stdClass();
                    $Records[$key]['PlayerBowlingStats'] = (!empty($Record['PlayerBowlingStats'])) ? json_decode($Record['PlayerBowlingStats']) : new stdClass();
                    $Records[$key]['PointsData'] = (!empty($Record['PointsData'])) ? json_decode($Record['PointsData'], TRUE) : array();
                    $Records[$key]['PlayerRole'] = "";
                    $IsAssistant = $Record['IsAssistant'];
                    $UserTeamGUID = $Record['UserTeamGUID'];
                    $UserTeamName = $Record['UserTeamName'];
                    $AuctionTopPlayerSubmitted = $Record['AuctionTopPlayerSubmitted'];
                    $this->db->select('PlayerID,PlayerRole,PlayerSalary');
                    $this->db->where('PlayerID', $Record['PlayerID']);
                    $this->db->from('sports_team_players');
                    $this->db->order_by("PlayerSalary", 'DESC');
                    $this->db->limit(1);
                    $PlayerDetails = $this->db->get()->result_array();
                    if (!empty($PlayerDetails)) {
                        $Records[$key]['PlayerRole'] = $PlayerDetails['0']['PlayerRole'];
                    }
                }
                if (!empty($Where['MySquadPlayer']) && $Where['MySquadPlayer'] == "Yes") {
                    $Return['Data']['IsAssistant'] = $IsAssistant;
                    $Return['Data']['UserTeamGUID'] = $UserTeamGUID;
                    $Return['Data']['UserTeamName'] = $UserTeamName;
                    $Return['Data']['AuctionTopPlayerSubmitted'] = $AuctionTopPlayerSubmitted;
                }
                $Return['Data']['Records'] = $Records;
                return $Return;
            } else {
                $Record = $Query->row_array();
                $Record['PlayerBattingStats'] = (!empty($Record['PlayerBattingStats'])) ? json_decode($Record['PlayerBattingStats']) : new stdClass();
                $Record['PlayerBowlingStats'] = (!empty($Record['PlayerBowlingStats'])) ? json_decode($Record['PlayerBowlingStats']) : new stdClass();
                $Record['PointsData'] = (!empty($Record['PointsData'])) ? json_decode($Record['PointsData'], TRUE) : array();
                $Record['PlayerRole'] = "";
                $this->db->select('PlayerID,PlayerRole,PlayerSalary');
                $this->db->where('PlayerID', $Record['PlayerID']);
                $this->db->from('sports_team_players');
                $this->db->order_by("PlayerSalary", 'DESC');
                $this->db->limit(1);
                $PlayerDetails = $this->db->get()->result_array();
                if (!empty($PlayerDetails)) {
                    $Record['PlayerRole'] = $PlayerDetails['0']['PlayerRole'];
                }
                return $Record;
            }
        }
        return FALSE;
    }

    function addAuctionPlayerRandom($SeriesID, $ContestID) {
        $playersData = $this->getPlayers("PlayerID,PlayerSalary", array('SeriesID' => $SeriesID), TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']);
        if ($playersData['Data']['TotalRecords'] > 0) {
            $Players = $playersData['Data']['Records'];
            if (!empty($Players)) {
                $PlayerCatOne = array();
                $PlayerCatTwo = array();
                $Temp = array();
                foreach ($Players as $Rows) {
                    $Temp["PlayerID"] = $Rows["PlayerID"];
                    $Temp["PlayerSalary"] = $Rows["PlayerSalary"];
                    if ($Rows["PlayerSalary"] >= 9) {
                        $PlayerCatOne[] = $Temp;
                    } else {
                        $PlayerCatTwo[] = $Temp;
                    }
                }
                shuffle($PlayerCatOne);
                shuffle($PlayerCatTwo);
                $Players = array_merge($PlayerCatOne, $PlayerCatTwo);
                shuffle($Players);
                $InsertBatch = array();
                $TempPlayer = array();
                foreach ($Players as $Player) {
                    $TempPlayer['SeriesID'] = $SeriesID;
                    $TempPlayer['ContestID'] = $ContestID;
                    $TempPlayer['PlayerID'] = $Player['PlayerID'];
                    $TempPlayer['BidCredit'] = 0;
                    $TempPlayer['PlayerStatus'] = "Upcoming";
                    $TempPlayer['CreateDateTime'] = date("Y-m-d H:i:s");
                    $InsertBatch[] = $TempPlayer;
                }
                if (!empty($InsertBatch)) {
                    $this->db->insert_batch('tbl_auction_player_bid_status', $InsertBatch);
                }
            }
        }
    }

    /*
      Description: Update contest to system.
     */

    function updateContest($Input = array(), $SessionUserID, $ContestID, $StatusID = 1) {
        $defaultCustomizeWinningObj = new stdClass();
        $defaultCustomizeWinningObj->From = 1;
        $defaultCustomizeWinningObj->To = 1;
        $defaultCustomizeWinningObj->Percent = 100;
        $defaultCustomizeWinningObj->WinningAmount = @$Input['WinningAmount'];
        $LeagueJoinDateTime = strtotime(@$Input['LeagueJoinDateTime']) + strtotime('-330 minutes', 0);
        /* Add contest to contest table . */
        $UpdateData = array_filter(array(
            "ContestName" => @$Input['ContestName'],
            "ContestFormat" => @$Input['ContestFormat'],
            "ContestType" => @$Input['ContestType'],
            "LeagueJoinDateTime" => (@$Input['LeagueJoinDateTime']) ? date('Y-m-d H:i', $LeagueJoinDateTime) : null,
            "AuctionUpdateTime" => (@$Input['LeagueJoinDateTime']) ? date('Y-m-d H:i', $LeagueJoinDateTime + 3600) : null,
            "Privacy" => @$Input['Privacy'],
            "IsPaid" => @$Input['IsPaid'],
            "IsConfirm" => @$Input['IsConfirm'],
            "GameType" => @$Input['GameType'],
            "GameTimeLive" => @$Input['GameTimeLive'],
            "AdminPercent" => @$Input['AdminPercent'],
            "MinimumUserJoined" => @$Input['MinimumUserJoined'],
            "ShowJoinedContest" => @$Input['ShowJoinedContest'],
            "WinningAmount" => @$Input['WinningAmount'],
            "ContestSize" => (@$Input['ContestFormat'] == 'Head to Head') ? 2 : @$Input['ContestSize'],
            "EntryFee" => (@$Input['IsPaid'] == 'Yes') ? @$Input['EntryFee'] : 0,
            "NoOfWinners" => (@$Input['IsPaid'] == 'Yes') ? @$Input['NoOfWinners'] : 1,
            "EntryType" => @$Input['EntryType'],
            "UserJoinLimit" => (@$Input['EntryType'] == 'Multiple') ? @$Input['UserJoinLimit'] : 1,
            "CashBonusContribution" => @$Input['CashBonusContribution'],
            // "CustomizeWinning" => (@$Input['IsPaid'] == 'Yes') ? @$Input['CustomizeWinning'] : NULL,
            "CustomizeWinning" => (@$Input['IsPaid'] == 'Yes') ? @$Input['CustomizeWinning'] : array($defaultCustomizeWinningObj),
        ));
        $this->db->where('ContestID', $ContestID);
        $this->db->limit(1);
        $this->db->update('sports_contest', $UpdateData);
    }

    function getTeams($MatchID) {
        $Return['Records'] = array();
        $Return['TotalRecords'] = 0;
        $this->db->select(" DISTINCT T.TeamID", FALSE);
        $this->db->select("T.TeamName,T.TeamNameShort");
        $this->db->from('sports_teams T');
        $this->db->where('EXISTS (select MatchID from sports_matches M where (M.TeamIDLocal = T.TeamID OR M.TeamIDVisitor = T.TeamID) AND M.MatchID=' . $MatchID . ')');
        $this->db->order_by('T.TeamID', "DESC");
        $TempOBJ = clone $this->db;
        $TempQ = $TempOBJ->get();
        $Return['TotalRecords'] = $TempQ->num_rows();
        $Query = $this->db->get();
        if ($Query->num_rows() > 0) {
            $Return['Records'] = $Query->result_array();
        }
        return $Return;
    }

    /*
      Description: Update auction game.
     */

    function getDraftGameStatusUpdate($Input = array(), $ContestID, $AuctionStatusID) {


        /* check contest cancel or not * */
        $Contest = $this->autoCancelAuction($ContestID);
        if ($Contest) {
            return false;
        }
        /* Add contest to contest table . */
        $UpdateData = array(
            "AuctionStatusID" => $AuctionStatusID,
        );
        $this->db->where('ContestID', $ContestID);
        $this->db->limit(1);
        $this->db->update('sports_contest', $UpdateData);
        return $Rows = $this->db->affected_rows();
    }

    /*
      Description: To Auto suffle round update
     */

    function autoShuffleRoundUpdate($ContestID) {
        $this->db->select("J.ContestID,J.UserID,J.DraftUserPosition");
        $this->db->from('sports_contest_join J');
        $this->db->where("J.ContestID", $ContestID);
        $this->db->order_by("J.DraftUserPosition", "ASC");
        $Query = $this->db->get();
        $Rows = $Query->num_rows();
        if ($Rows > 0) {
            $Users = $Query->result_array();
            shuffle($Users);
            $i = 1;
            foreach ($Users as $User) {
                /* Update auction Status */
                $this->db->where('ContestID', $User['ContestID']);
                $this->db->where('UserID', $User['UserID']);
                $this->db->limit(1);
                $this->db->update('sports_contest_join', array('DraftUserPosition' => $i));
                $i++;
            }
        }
        return true;
    }

    /*
      Description: To Auto Cancel Auction
     */

    function autoCancelAuction($ContestID) {

        ini_set('max_execution_time', 300);

        /* Get Contest Data */
        $ContestsUsers = $this->getContests('ContestID,EntryFee,TotalJoined,ContestSize,IsConfirm,Privacy', array('AuctionStatusID' => 1, 'ContestID' => $ContestID, 'IsConfirm' => "No", "IsPaid" => "Yes"), true, 0);

        if ($ContestsUsers['Data']['TotalRecords'] == 0) {
            return false;
        }
        foreach ($ContestsUsers['Data']['Records'] as $Value) {

            $IsCancelled = (($Value['IsConfirm'] == 'No' && $Value['TotalJoined'] != $Value['ContestSize']) ? 1 : 0);
            if ($IsCancelled == 0) return false;

            if($Value['Privacy'] == "No"){
                if($Value['TotalJoined'] > 0){
                    /* Update Contest Status */
                    $this->db->where('EntityID', $Value['ContestID']);
                    $this->db->limit(1);
                    $this->db->update('tbl_entity', array('ModifiedDate' => date('Y-m-d H:i:s'), 'StatusID' => 3));

                    /* Update auction Status */
                    $this->db->where('ContestID', $Value['ContestID']);
                    $this->db->limit(1);
                    $this->db->update('sports_contest', array('AuctionStatusID' => 3, 'IsRefund' => "Yes","CancelledBy"=>"Draft"));
                }else{
                    /* Update auction Status */
                    $LeagueJoinDateTime = date("Y-m-d H:i", strtotime("+45 minutes"));
                    $this->db->where('ContestID', $Value['ContestID']);
                    $this->db->limit(1);
                    $this->db->update('sports_contest', array('LeagueJoinDateTime' => $LeagueJoinDateTime));
                    return true;
                }
            }else{

                /* Update Contest Status */
                $this->db->where('EntityID', $Value['ContestID']);
                $this->db->limit(1);
                $this->db->update('tbl_entity', array('ModifiedDate' => date('Y-m-d H:i:s'), 'StatusID' => 3));

                /* Update auction Status */
                $this->db->where('ContestID', $Value['ContestID']);
                $this->db->limit(1);
                $this->db->update('sports_contest', array('AuctionStatusID' => 3, 'IsRefund' => "Yes","CancelledBy"=>"Draft")); 
            }


            /* Get Joined Contest */
            $JoinedContestsUsers = $this->getJoinedContestsUsers('UserID,FirstName,Email,UserTeamID', array('ContestID' => $Value['ContestID']), true, 0);
            if (!$JoinedContestsUsers) return false;

            foreach ($JoinedContestsUsers['Data']['Records'] as $JoinValue) {

                /* Refund Wallet Money */
                if (!empty($Value['EntryFee'])) {

                    /* Get Wallet Details */
                    $WalletDetails = $this->Users_model->getWallet('WalletAmount,WinningAmount,CashBonus', array(
                        'UserID' => $JoinValue['UserID'],
                        'EntityID' => $Value['ContestID'],
                        'Narration' => 'Join Contest'
                    ));
                    $InsertData = array(
                        "Amount" => $WalletDetails['WalletAmount'] + $WalletDetails['WinningAmount'] + $WalletDetails['CashBonus'],
                        "WalletAmount" => $WalletDetails['WalletAmount'],
                        "WinningAmount" => $WalletDetails['WinningAmount'],
                        "CashBonus" => $WalletDetails['CashBonus'],
                        "TransactionType" => 'Cr',
                        "EntityID" => $Value['ContestID'],
                        "UserTeamID" => $JoinValue['UserTeamID'],
                        "Narration" => 'Cancel Contest',
                        "EntryDate" => date("Y-m-d H:i:s")
                    );
                    $this->Users_model->addToWallet($InsertData, $JoinValue['UserID'], 5);

                    $TotalRefundAmount = $WalletDetails['WalletAmount'] + $WalletDetails['WinningAmount'] + $WalletDetails['CashBonus'];
                    /** add notification cancel contest */
                    $this->Notification_model->addNotification('refund', 'Contest Cancelled Refund', $JoinValue['UserID'], $JoinValue['UserID'], $Value['ContestID'], 'Your Gully Rs.' . $TotalRefundAmount . ' has been refunded.');

                    //$NotificationMessage='Your Gully Rs.' . $TotalRefundAmount . ' has been refunded.';
                    //sendPushMessage($JoinValue['UserID'], 'Contest Cancelled', $NotificationMessage, $Data=array("RefrenceID"=>'', "NotificationPatternGUID"=>'winnings'));
                }

                /** update user refund status Yes */
                $this->db->where('ContestID', $Value['ContestID']);
                $this->db->where('UserTeamID', $JoinValue['UserTeamID']);
                $this->db->where('UserID', $JoinValue['UserID']);
                $this->db->limit(1);
                $this->db->update('sports_contest_join', array('IsRefund' => "Yes"));

                /* Send Mail To Users */
                /* $EmailArr = array(
                  "Name" => $JoinValue['FirstName'],
                  "SeriesName" => $Value['SeriesName'],
                  "ContestName" => $Value['ContestName'],
                  "MatchNo" => $Value['MatchNo'],
                  "TeamNameLocal" => $Value['TeamNameLocal'],
                  "TeamNameVisitor" => $Value['TeamNameVisitor']
                  );
                  sendMail(array(
                  'emailTo' => $JoinValue['Email'],
                  'emailSubject' => "Cancel Contest- " . SITE_NAME,
                  'emailMessage' => emailTemplate($this->load->view('emailer/cancel_contest', $EmailArr, true))
                  )); */
            }

            return true;
        }
    }

    /*
      Description: Update user live status.
     */

    function userLiveStatusUpdate($Input = array(), $ContestID, $UserID, $SeriesID, $RoundID) {

        /** to update other user offline * */
        $UpdateDatas = array(
            "DraftUserLive" => "No"
        );
        $this->db->where('ContestID', $ContestID);
        $this->db->update('sports_contest_join', $UpdateDatas);

        /* user status update . */
        $UpdateData = array(
            "DraftUserLive" => $Input['UserStatus'],
            "DraftUserLiveTime" => date('Y-m-d H:i:s'),
        );
        $this->db->where('ContestID', $ContestID);
        $this->db->where('UserID', $UserID);
        $this->db->limit(1);
        $this->db->update('sports_contest_join', $UpdateData);
        return $Rows = $this->db->affected_rows();
    }

    /*
      Description: Update draft round.
     */

    function roundUpdate($Input = array(), $ContestID, $SeriesID) {

        /** to update other user offline * */
        $UpdateDatas = array(
            "DraftLiveRound" => $Input['DraftLiveRound']
        );
        $this->db->where('ContestID', $ContestID);
        //$this->db->where('SeriesID', $SeriesID);
        $this->db->update('sports_contest', $UpdateDatas);
        return $Rows = $this->db->affected_rows();
    }

    /*
      Description: get user in live
     */

    function checkUserDraftInlive($Input, $RoundID, $ContestID) {
        $Return = array();
        $Return["Status"] = 0;
        /** check draft in live * */
        $DraftGames = $this->getContests('ContestID,AuctionStatus,SeriesID,SeriesGUID,DraftLiveRound,RoundID,MatchID,MatchGUID', array('AuctionStatusID' => 2, 'LeagueType' => "Draft", "ContestID" => $ContestID, "RoundID" => $RoundID), TRUE, 1);
        if ($DraftGames['Data']['TotalRecords'] > 0) {
            $Users = array();
            foreach ($DraftGames['Data']['Records'] as $Key => $Draft) {

                /** to get user live and time difference * */
                $this->db->select("J.ContestID,J.UserID,J.DraftUserLiveTime,J.DraftUserLive,U.UserGUID");
                $this->db->from('sports_contest_join J, tbl_users U');
                $this->db->where("J.DraftUserLive", "Yes");
                $this->db->where("U.UserID", "J.UserID", FALSE);
                $this->db->where("J.ContestID", $Draft['ContestID']);
                //$this->db->where("J.RoundID", $RoundID);
                $Query = $this->db->get();
                if ($Query->num_rows() > 0) {
                    $LiveUser = $Query->row_array();
                    $CurrentDateTime = date('Y-m-d H:i:s');
                    $DraftUserLiveTime = $LiveUser['DraftUserLiveTime'];
                    $CurrentDateTime = new DateTime($CurrentDateTime);
                    $AuctionBreakDateTime = new DateTime($DraftUserLiveTime);
                    $diffSeconds = $CurrentDateTime->getTimestamp() - $AuctionBreakDateTime->getTimestamp();
                    $Users[$Key]["UserLiveInTimeSeconds"] = $diffSeconds;
                    $Users[$Key]["ContestID"] = $Draft['ContestID'];
                    $Users[$Key]["SeriesID"] = $Draft['SeriesID'];
                    $Users[$Key]["RoundID"] = $Draft['RoundID'];
                    $Users[$Key]["ContestGUID"] = $Draft['ContestGUID'];
                    $Users[$Key]["SeriesGUID"] = $Draft['SeriesGUID'];
                    $Users[$Key]["MatchID"] = $Draft['MatchID'];
                    $Users[$Key]["MatchGUID"] = $Draft['MatchGUID'];
                    $Users[$Key]["DraftLiveRound"] = $Draft['DraftLiveRound'];
                    $Users[$Key]["UserID"] = $LiveUser['UserID'];
                    $Users[$Key]["UserGUID"] = $LiveUser['UserGUID'];
                    $Users[$Key]["DraftUserLiveTime"] = $LiveUser['DraftUserLiveTime'];
                    $Users[$Key]["UserStatus"] = "Live";
                } else {
                    /** to get user live and time difference * */
                    $this->db->select("J.ContestID,J.UserID,J.DraftUserLiveTime,J.DraftUserLive,U.UserGUID");
                    $this->db->from('sports_contest_join J, tbl_users U');
                    $this->db->where("U.UserID", "J.UserID", FALSE);
                    $this->db->where("J.ContestID", $Draft['ContestID']);
                    //$this->db->where("J.RoundID", $Draft['RoundID']);
                    $this->db->where("J.DraftUserPosition", 1);
                    $Query = $this->db->get();
                    if ($Query->num_rows() > 0) {
                        $LiveUser = $Query->row_array();
                        $CurrentDateTime = date('Y-m-d H:i:s');
                        $DraftUserLiveTime = $LiveUser['DraftUserLiveTime'];
                        $Users[$Key]["UserLiveInTimeSeconds"] = 0;
                        $Users[$Key]["ContestID"] = $Draft['ContestID'];
                        $Users[$Key]["SeriesID"] = $Draft['SeriesID'];
                        $Users[$Key]["RoundID"] = $Draft['RoundID'];
                        $Users[$Key]["ContestGUID"] = $Draft['ContestGUID'];
                        $Users[$Key]["SeriesGUID"] = $Draft['SeriesGUID'];
                        $Users[$Key]["MatchID"] = $Draft['MatchID'];
                        $Users[$Key]["MatchGUID"] = $Draft['MatchGUID'];
                        $Users[$Key]["DraftLiveRound"] = $Draft['DraftLiveRound'];
                        $Users[$Key]["UserID"] = $LiveUser['UserID'];
                        $Users[$Key]["UserGUID"] = $LiveUser['UserGUID'];
                        $Users[$Key]["DraftUserLiveTime"] = $LiveUser['DraftUserLiveTime'];
                        $Users[$Key]["UserStatus"] = "Upcoming";
                    }
                }
            }
            $U = array();
            foreach ($Users as $Rows) {
                $U[] = $Rows;
            }
            $Return["Data"] = $U;
            $Return["Message"] = "Users in live";
            $Return["Status"] = 1;
        } else {
            $Return["Message"] = "Draft not live";
        }
        return $Return;
    }

    /*
      Description: get user in live
     */

    function getUserInLive() {
        $Return = array();
        $Return["Status"] = 0;
        /** check draft in live * */
        $DraftGames = $this->getContests('ContestID,AuctionStatus,SeriesID,SeriesGUID,RoundID,MatchID,MatchGUID', array('AuctionStatusID' => 2, 'LeagueType' => "Draft"), TRUE,0);
        if ($DraftGames['Data']['TotalRecords'] > 0) {
            $Users = array();
            foreach ($DraftGames['Data']['Records'] as $Key => $Draft) {

                /** to get user live and time difference * */
                $this->db->select("J.ContestID,J.UserID,J.DraftUserLiveTime,J.DraftUserLive,U.UserGUID");
                $this->db->from('sports_contest_join J, tbl_users U');
                $this->db->where("J.DraftUserLive", "Yes");
                $this->db->where("U.UserID", "J.UserID", FALSE);
                $this->db->where("J.ContestID", $Draft['ContestID']);
                $Query = $this->db->get();
                if ($Query->num_rows() > 0) {
                    $LiveUser = $Query->row_array();
                    $CurrentDateTime = date('Y-m-d H:i:s');
                    $DraftUserLiveTime = $LiveUser['DraftUserLiveTime'];
                    $CurrentDateTime = new DateTime($CurrentDateTime);
                    $AuctionBreakDateTime = new DateTime($DraftUserLiveTime);
                    $diffSeconds = $CurrentDateTime->getTimestamp() - $AuctionBreakDateTime->getTimestamp();
                    $Users[$Key]["UserLiveInTimeSeconds"] = $diffSeconds;
                    $Users[$Key]["ContestID"] = $Draft['ContestID'];
                    $Users[$Key]["SeriesID"] = $Draft['SeriesID'];
                    $Users[$Key]["RoundID"] = $Draft['RoundID'];
                    $Users[$Key]["ContestGUID"] = $Draft['ContestGUID'];
                    $Users[$Key]["SeriesGUID"] = $Draft['SeriesGUID'];
                    $Users[$Key]["MatchID"] = $Draft['MatchID'];
                    $Users[$Key]["MatchGUID"] = $Draft['MatchGUID'];
                    $Users[$Key]["UserID"] = $LiveUser['UserID'];
                    $Users[$Key]["UserGUID"] = $LiveUser['UserGUID'];
                    $Users[$Key]["UserStatus"] = "Live";
                } else {
                    /** to get user live and time difference * */
                    $this->db->select("J.ContestID,J.UserID,J.DraftUserLiveTime,J.DraftUserLive,U.UserGUID");
                    $this->db->from('sports_contest_join J, tbl_users U');
                    $this->db->where("U.UserID", "J.UserID", FALSE);
                    $this->db->where("J.ContestID", $Draft['ContestID']);
                    $this->db->where("J.DraftUserPosition", 1);
                    $Query = $this->db->get();
                    if ($Query->num_rows() > 0) {
                        $LiveUser = $Query->row_array();
                        $CurrentDateTime = date('Y-m-d H:i:s');
                        $DraftUserLiveTime = $LiveUser['DraftUserLiveTime'];
                        $Users[$Key]["UserLiveInTimeSeconds"] = 0;
                        $Users[$Key]["ContestID"] = $Draft['ContestID'];
                        $Users[$Key]["SeriesID"] = $Draft['SeriesID'];
                        $Users[$Key]["RoundID"] = $Draft['RoundID'];
                        $Users[$Key]["ContestGUID"] = $Draft['ContestGUID'];
                        $Users[$Key]["SeriesGUID"] = $Draft['SeriesGUID'];
                        $Users[$Key]["MatchID"] = $Draft['MatchID'];
                        $Users[$Key]["MatchGUID"] = $Draft['MatchGUID'];
                        $Users[$Key]["UserID"] = $LiveUser['UserID'];
                        $Users[$Key]["UserGUID"] = $LiveUser['UserGUID'];
                        $Users[$Key]["UserStatus"] = "Upcoming";
                    }else{
                        /** to get user live and time difference * */
                        $this->db->select("J.ContestID,J.UserID,J.DraftUserLiveTime,J.DraftUserLive,U.UserGUID");
                        $this->db->from('sports_contest_join J, tbl_users U');
                        $this->db->where("U.UserID", "J.UserID", FALSE);
                        $this->db->where("J.ContestID", $Draft['ContestID']);
                        $Query = $this->db->get();
                        if ($Query->num_rows() == 0) {
                                /* Update Contest Status */
                                $this->db->where('EntityID', $Draft['ContestID']);
                                $this->db->limit(1);
                                $this->db->update('tbl_entity', array('ModifiedDate' => date('Y-m-d H:i:s'), 'StatusID' => 3));

                                /* Update auction Status */
                                $this->db->where('ContestID', $Draft['ContestID']);
                                $this->db->limit(1);
                                $this->db->update('sports_contest', array('AuctionStatusID' => 3, 'IsRefund' => "Yes"));
                        }
                    }
                }
            }
            $U = array();
            foreach ($Users as $Rows) {
                $U[] = $Rows;
            }
            $Return["Data"] = $U;
            $Return["Message"] = "Users in live";
            $Return["Status"] = 1;
        } else {
            $Return["Message"] = "Draft not live";
        }
        return $Return;
    }

    /*
      Description: get round next user in live
     */

    function draftRoundUpdate($ContestID, $SeriesID, $Round) {
        /* Add contest to contest table . */
        $UpdateData = array_filter(array(
            "DraftLiveRound" => $Round
        ));
        //$this->db->where('SeriesID', $SeriesID);
        $this->db->where('ContestID', $ContestID);
        $this->db->limit(1);
        $this->db->update('sports_contest', $UpdateData);
        return true;
    }

    function getRoundNextUserInLive($Input, $SeriesID, $RoundID, $ContestID) {
        $Return = array();
        $Return["Status"] = 0;
        $Return['Message'] = "Record Not found";
        /** check draft in live * */
        $DraftGames = $this->getContests('ContestID,SeriesID,SeriesGUID,DraftTotalRounds,TotalJoined,DraftLiveRound,RoundID,MatchID,MatchGUID', array('AuctionStatusID' => 2, 'LeagueType' => "Draft", "ContestID" => $ContestID, "RoundID" => $RoundID), TRUE, 1);

        if ($DraftGames['Data']['TotalRecords'] > 0) {
            $Users = array();
            foreach ($DraftGames['Data']['Records'] as $Key => $Draft) {

                if ($Draft['DraftLiveRound'] <= $Draft['DraftTotalRounds']) {
                    $Flag = FALSE;
                    /** skipped first user not getting player * */
                    if ($Draft['DraftLiveRound'] == 1) {

                        $this->db->select("J.ContestID,J.UserID,J.DraftUserLiveTime,J.DraftUserLive,U.UserGUID,U.FirstName");
                        $this->db->from('sports_contest_join J, tbl_users U');
                        $this->db->where("J.DraftUserLive", "Yes");
                        $this->db->where("U.UserID", "J.UserID", FALSE);
                        $this->db->where("J.ContestID", $ContestID);
                        $this->db->where("J.DraftUserPosition", 1);
                        $Query = $this->db->get();
                        if ($Query->num_rows() > 0) {
                            $LiveUser = $Query->row_array();
                            $UserTeamID = $this->db->query('SELECT T.UserTeamID from `sports_users_teams` T join tbl_users U on U.UserID = T.UserID WHERE T.MatchID = "' . $Draft['MatchID'] . '" AND T.UserID = "' . $LiveUser['UserID'] . '" AND T.ContestID = "' . $ContestID . '" AND IsPreTeam = "No" AND IsAssistant="No" ')->row()->UserTeamID;
                            if (!empty($UserTeamID)) {
                                $UserTeamID = $this->db->query('SELECT PlayerID from `sports_users_team_players` WHERE UserTeamID = "' . $UserTeamID . '" ')->row()->PlayerID;
                                if (empty($UserTeamID)) {
                                    $Flag = TRUE;
                                }
                            } else {
                                $Flag = TRUE;
                            }
                            if ($Flag) {
                                $CurrentDateTime = date('Y-m-d H:i:s');
                                $DraftUserLiveTime = $LiveUser['DraftUserLiveTime'];
                                $CurrentDateTime = new DateTime($CurrentDateTime);
                                $AuctionBreakDateTime = new DateTime($DraftUserLiveTime);
                                $diffSeconds = $CurrentDateTime->getTimestamp() - $AuctionBreakDateTime->getTimestamp();
                                $Users["UserLiveInTimeSeconds"] = $diffSeconds;
                                $Users["ContestID"] = $Draft['ContestID'];
                                $Users["SeriesID"] = $Draft['SeriesID'];
                                $Users["RoundID"] = $Draft['RoundID'];
                                $Users["ContestGUID"] = $Draft['ContestGUID'];
                                $Users["SeriesGUID"] = $Draft['SeriesGUID'];
                                $Users["MatchID"] = $Draft['MatchID'];
                                $Users["MatchGUID"] = $Draft['MatchGUID'];
                                $Users["DraftLiveRound"] = $Draft['DraftLiveRound'];
                                $Users["DraftNextRound"] = $Draft['DraftLiveRound'] + 1;
                                $Users["UserID"] = $LiveUser['UserID'];
                                $Users["UserGUID"] = $LiveUser['UserGUID'];
                                $Users["FirstName"] = $LiveUser['FirstName'];
                                $Return["Status"] = 1;
                                $Return["Data"] = $Users;
                                $Return['Message'] = "User in live";
                            }
                        }
                    }
                    if (!$Flag) {
                       /** check last player in live * */
                        $this->db->select("J.ContestID,J.UserID,J.DraftUserLiveTime,J.DraftUserLive,U.UserGUID");
                        $this->db->from('sports_contest_join J, tbl_users U');
                        $this->db->where("J.DraftUserLive", "Yes");
                        $this->db->where("U.UserID", "J.UserID", FALSE);
                        $this->db->where("J.ContestID", $ContestID);
                        $this->db->where("J.DraftUserPosition", 1);
                        $Query = $this->db->get();
                        if ($Query->num_rows() > 0) {
                            $this->db->select("J.ContestID,J.UserID,J.DraftUserLiveTime,J.DraftUserLive,U.UserGUID");
                            $this->db->from('sports_contest_join J, tbl_users U');
                            $this->db->where("J.DraftUserLive", "No");
                            $this->db->where("U.UserID", "J.UserID", FALSE);
                            $this->db->where("J.ContestID", $ContestID);
                            $this->db->where("J.DraftUserPosition", 2);
                            $Query = $this->db->get();
                            if ($Query->num_rows() > 0) {
                                    $LiveUser = $Query->row_array();
                                    $CurrentDateTime = date('Y-m-d H:i:s');
                                    $DraftUserLiveTime = $LiveUser['DraftUserLiveTime'];
                                    $CurrentDateTime = new DateTime($CurrentDateTime);
                                    $AuctionBreakDateTime = new DateTime($DraftUserLiveTime);
                                    $diffSeconds = $CurrentDateTime->getTimestamp() - $AuctionBreakDateTime->getTimestamp();
                                    $Users["UserLiveInTimeSeconds"] = $diffSeconds;
                                    $Users["ContestID"] = $Draft['ContestID'];
                                    $Users["SeriesID"] = $Draft['SeriesID'];
                                    $Users["RoundID"] = $Draft['RoundID'];
                                    $Users["ContestGUID"] = $Draft['ContestGUID'];
                                    $Users["SeriesGUID"] = $Draft['SeriesGUID'];
                                    $Users["MatchID"] = $Draft['MatchID'];
                                    $Users["MatchGUID"] = $Draft['MatchGUID'];
                                    $Users["DraftLiveRound"] = $Draft['DraftLiveRound'];
                                    $Users["DraftNextRound"] = $Draft['DraftLiveRound'];
                                    $Users["UserID"] = $LiveUser['UserID'];
                                    $Users["UserGUID"] = $LiveUser['UserGUID'];
                                    $Users["FirstName"] = $LiveUser['FirstName'];
                                    $Return["Status"] = 1;
                                    $Return["Data"] = $Users;
                                    $Return['Message'] = "User in live";
                            }
                        }else{
                            /** check last player in live * */
                            $this->db->select("J.ContestID,J.UserID,J.DraftUserLiveTime,J.DraftUserLive,U.UserGUID");
                            $this->db->from('sports_contest_join J, tbl_users U');
                            $this->db->where("J.DraftUserLive", "Yes");
                            $this->db->where("U.UserID", "J.UserID", FALSE);
                            $this->db->where("J.ContestID", $ContestID);
                            $this->db->where("J.DraftUserPosition", 2);
                            $Query = $this->db->get();
                            if ($Query->num_rows() > 0) {
                                $this->db->select("J.ContestID,J.UserID,J.DraftUserLiveTime,J.DraftUserLive,U.UserGUID");
                                $this->db->from('sports_contest_join J, tbl_users U');
                                $this->db->where("J.DraftUserLive", "No");
                                $this->db->where("U.UserID", "J.UserID", FALSE);
                                $this->db->where("J.ContestID", $ContestID);
                                $this->db->where("J.DraftUserPosition", 1);
                                $Query = $this->db->get();
                                if ($Query->num_rows() > 0) {
                                    $LiveUser = $Query->row_array();
                                    $CurrentDateTime = date('Y-m-d H:i:s');
                                    $DraftUserLiveTime = $LiveUser['DraftUserLiveTime'];
                                    $CurrentDateTime = new DateTime($CurrentDateTime);
                                    $AuctionBreakDateTime = new DateTime($DraftUserLiveTime);
                                    $diffSeconds = $CurrentDateTime->getTimestamp() - $AuctionBreakDateTime->getTimestamp();
                                    $Users["UserLiveInTimeSeconds"] = $diffSeconds;
                                    $Users["ContestID"] = $Draft['ContestID'];
                                    $Users["SeriesID"] = $Draft['SeriesID'];
                                    $Users["RoundID"] = $Draft['RoundID'];
                                    $Users["ContestGUID"] = $Draft['ContestGUID'];
                                    $Users["SeriesGUID"] = $Draft['SeriesGUID'];
                                    $Users["MatchID"] = $Draft['MatchID'];
                                    $Users["MatchGUID"] = $Draft['MatchGUID'];
                                    $Users["DraftLiveRound"] = $Draft['DraftLiveRound'];
                                    $Users["DraftNextRound"] = $Draft['DraftLiveRound'] + 1;
                                    $Users["UserID"] = $LiveUser['UserID'];
                                    $Users["UserGUID"] = $LiveUser['UserGUID'];
                                    $Users["FirstName"] = $LiveUser['FirstName'];
                                    $Return["Status"] = 1;
                                    $Return["Data"] = $Users;
                                    $Return['Message'] = "User in live";
                                    $this->draftRoundUpdate($Draft['ContestID'], $Draft['SeriesID'], $Draft['DraftLiveRound'] + 1);
                                }
                            }else{
                                /** check last player in live * */
                                $this->db->select("J.ContestID,J.UserID,J.DraftUserLiveTime,J.DraftUserLive,U.UserGUID,U.FirstName");
                                $this->db->from('sports_contest_join J, tbl_users U');
                                $this->db->where("U.UserID", "J.UserID", FALSE);
                                $this->db->where("J.ContestID", $ContestID);
                                $this->db->where("J.DraftUserPosition", 1);
                                $Query = $this->db->get();
                                if ($Query->num_rows() > 0) {
                                    $LiveUser = $Query->row_array();
                                    $CurrentDateTime = date('Y-m-d H:i:s');
                                    $DraftUserLiveTime = $LiveUser['DraftUserLiveTime'];
                                    $Users["UserLiveInTimeSeconds"] = 0;
                                    $Users["ContestID"] = $Draft['ContestID'];
                                    $Users["SeriesID"] = $Draft['SeriesID'];
                                    $Users["RoundID"] = $Draft['RoundID'];
                                    $Users["ContestGUID"] = $Draft['ContestGUID'];
                                    $Users["SeriesGUID"] = $Draft['SeriesGUID'];
                                    $Users["MatchID"] = $Draft['MatchID'];
                                    $Users["MatchGUID"] = $Draft['MatchGUID'];
                                    $Users["DraftLiveRound"] = $Draft['DraftLiveRound'];
                                    $Users["DraftNextRound"] = $Draft['DraftLiveRound'];
                                    $Users["UserID"] = $LiveUser['UserID'];
                                    $Users["UserGUID"] = $LiveUser['UserGUID'];
                                    $Users["FirstName"] = $LiveUser['FirstName'];
                                    $Return["Status"] = 1;
                                    $Return["Data"] = $Users;
                                    $Return['Message'] = "User in live";
                                }
                            }
                        } 
                    }
                }
            }
        } else {
            $Return["Message"] = "Draft not live";
        }
        //dump($Return);
        return $Return;
    }

    function getRoundNextUserInLiveOLD($Input, $SeriesID, $RoundID, $ContestID) {
        $Return = array();
        $Return["Status"] = 0;
        $Return['Message'] = "Record Not found";
        /** check draft in live * */
        $DraftGames = $this->getContests('ContestID,SeriesID,SeriesGUID,DraftTotalRounds,TotalJoined,DraftLiveRound,RoundID,MatchID,MatchGUID', array('AuctionStatusID' => 2, 'LeagueType' => "Draft", "ContestID" => $ContestID, "RoundID" => $RoundID), TRUE, 1);

        if ($DraftGames['Data']['TotalRecords'] > 0) {
            $Users = array();
            foreach ($DraftGames['Data']['Records'] as $Key => $Draft) {

                if ($Draft['DraftLiveRound'] <= $Draft['DraftTotalRounds']) {
                    $Flag = FALSE;
                    /** skipped first user not getting player * */
                    if ($Draft['DraftLiveRound'] == 1) {

                        $this->db->select("J.ContestID,J.UserID,J.DraftUserLiveTime,J.DraftUserLive,U.UserGUID,U.FirstName");
                        $this->db->from('sports_contest_join J, tbl_users U');
                        $this->db->where("J.DraftUserLive", "Yes");
                        $this->db->where("U.UserID", "J.UserID", FALSE);
                        $this->db->where("J.ContestID", $ContestID);
                        $this->db->where("J.DraftUserPosition", 1);
                        $Query = $this->db->get();
                        if ($Query->num_rows() > 0) {
                            $LiveUser = $Query->row_array();
                            $UserTeamID = $this->db->query('SELECT T.UserTeamID from `sports_users_teams` T join tbl_users U on U.UserID = T.UserID WHERE T.MatchID = "' . $Draft['MatchID'] . '" AND T.UserID = "' . $LiveUser['UserID'] . '" AND T.ContestID = "' . $ContestID . '" AND IsPreTeam = "No" AND IsAssistant="No" ')->row()->UserTeamID;
                            if (!empty($UserTeamID)) {
                                $UserTeamID = $this->db->query('SELECT PlayerID from `sports_users_team_players` WHERE UserTeamID = "' . $UserTeamID . '" ')->row()->PlayerID;
                                if (empty($UserTeamID)) {
                                    $Flag = TRUE;
                                }
                            } else {
                                $Flag = TRUE;
                            }
                            if ($Flag) {
                                $CurrentDateTime = date('Y-m-d H:i:s');
                                $DraftUserLiveTime = $LiveUser['DraftUserLiveTime'];
                                $CurrentDateTime = new DateTime($CurrentDateTime);
                                $AuctionBreakDateTime = new DateTime($DraftUserLiveTime);
                                $diffSeconds = $CurrentDateTime->getTimestamp() - $AuctionBreakDateTime->getTimestamp();
                                $Users["UserLiveInTimeSeconds"] = $diffSeconds;
                                $Users["ContestID"] = $Draft['ContestID'];
                                $Users["SeriesID"] = $Draft['SeriesID'];
                                $Users["RoundID"] = $Draft['RoundID'];
                                $Users["ContestGUID"] = $Draft['ContestGUID'];
                                $Users["SeriesGUID"] = $Draft['SeriesGUID'];
                                $Users["MatchID"] = $Draft['MatchID'];
                                $Users["MatchGUID"] = $Draft['MatchGUID'];
                                $Users["DraftLiveRound"] = $Draft['DraftLiveRound'];
                                $Users["DraftNextRound"] = $Draft['DraftLiveRound'] + 1;
                                $Users["UserID"] = $LiveUser['UserID'];
                                $Users["UserGUID"] = $LiveUser['UserGUID'];
                                $Users["FirstName"] = $LiveUser['FirstName'];
                                $Return["Status"] = 1;
                                $Return["Data"] = $Users;
                                $Return['Message'] = "User in live";
                            }
                        }
                    }

                    if (!$Flag) {
                        /** check last player in live * */
                        $this->db->select("J.ContestID,J.UserID,J.DraftUserLiveTime,J.DraftUserLive,U.UserGUID");
                        $this->db->from('sports_contest_join J, tbl_users U');
                        $this->db->where("J.DraftUserLive", "Yes");
                        $this->db->where("U.UserID", "J.UserID", FALSE);
                        $this->db->where("J.ContestID", $ContestID);
                        $Query = $this->db->get();
                        if ($Query->num_rows() == 0) {
                            /** check last player in live * */
                            $this->db->select("J.ContestID,J.UserID,J.DraftUserLiveTime,J.DraftUserLive,U.UserGUID,U.FirstName");
                            $this->db->from('sports_contest_join J, tbl_users U');
                            $this->db->where("U.UserID", "J.UserID", FALSE);
                            $this->db->where("J.ContestID", $ContestID);
                            $this->db->where("J.DraftUserPosition", 1);
                            $Query = $this->db->get();
                            if ($Query->num_rows() > 0) {
                                $LiveUser = $Query->row_array();
                                $CurrentDateTime = date('Y-m-d H:i:s');
                                $DraftUserLiveTime = $LiveUser['DraftUserLiveTime'];
                                $Users["UserLiveInTimeSeconds"] = 0;
                                $Users["ContestID"] = $Draft['ContestID'];
                                $Users["SeriesID"] = $Draft['SeriesID'];
                                $Users["RoundID"] = $Draft['RoundID'];
                                $Users["ContestGUID"] = $Draft['ContestGUID'];
                                $Users["SeriesGUID"] = $Draft['SeriesGUID'];
                                $Users["MatchID"] = $Draft['MatchID'];
                                $Users["MatchGUID"] = $Draft['MatchGUID'];
                                $Users["DraftLiveRound"] = $Draft['DraftLiveRound'];
                                $Users["DraftNextRound"] = $Draft['DraftLiveRound'];
                                $Users["UserID"] = $LiveUser['UserID'];
                                $Users["UserGUID"] = $LiveUser['UserGUID'];
                                $Users["FirstName"] = $LiveUser['FirstName'];
                                $Return["Status"] = 1;
                                $Return["Data"] = $Users;
                                $Return['Message'] = "User in live";
                            }
                        } else {
                            /** check round even or odd * */
                            if (($Draft['DraftLiveRound'] % 2) != 0) {
                                /** value odd number * */
                                /** check last player in live * */
                                $this->db->select("J.ContestID,J.UserID,J.DraftUserLiveTime,J.DraftUserLive,U.UserGUID,U.FirstName");
                                $this->db->from('sports_contest_join J, tbl_users U');
                                $this->db->where("J.DraftUserLive", "Yes");
                                $this->db->where("U.UserID", "J.UserID", FALSE);
                                $this->db->where("J.ContestID", $ContestID);
                                $this->db->where("J.DraftUserPosition", $Draft['TotalJoined']);
                                $Query = $this->db->get();

                                if ($Query->num_rows() > 0) {
                                    $LiveUser = $Query->row_array();
                                    $CurrentDateTime = date('Y-m-d H:i:s');
                                    $DraftUserLiveTime = $LiveUser['DraftUserLiveTime'];
                                    $CurrentDateTime = new DateTime($CurrentDateTime);
                                    $AuctionBreakDateTime = new DateTime($DraftUserLiveTime);
                                    $diffSeconds = $CurrentDateTime->getTimestamp() - $AuctionBreakDateTime->getTimestamp();
                                    $Users["UserLiveInTimeSeconds"] = $diffSeconds;
                                    $Users["ContestID"] = $Draft['ContestID'];
                                    $Users["SeriesID"] = $Draft['SeriesID'];
                                    $Users["RoundID"] = $Draft['RoundID'];
                                    $Users["ContestGUID"] = $Draft['ContestGUID'];
                                    $Users["SeriesGUID"] = $Draft['SeriesGUID'];
                                    $Users["MatchID"] = $Draft['MatchID'];
                                    $Users["MatchGUID"] = $Draft['MatchGUID'];
                                    $Users["DraftLiveRound"] = $Draft['DraftLiveRound'];
                                    $Users["DraftNextRound"] = $Draft['DraftLiveRound'] + 1;
                                    $Users["UserID"] = $LiveUser['UserID'];
                                    $Users["UserGUID"] = $LiveUser['UserGUID'];
                                    $Users["FirstName"] = $LiveUser['FirstName'];
                                    $Return["Status"] = 1;
                                    $Return["Data"] = $Users;
                                    $Return['Message'] = "User in live";
                                    $this->draftRoundUpdate($Draft['ContestID'], $Draft['SeriesID'], $Draft['DraftLiveRound'] + 1);
                                } else {
                                    $this->db->select("J.ContestID,J.UserID,J.DraftUserLiveTime,J.DraftUserLive,U.UserGUID,J.DraftUserPosition");
                                    $this->db->from('sports_contest_join J, tbl_users U');
                                    $this->db->where("J.DraftUserLive", "Yes");
                                    $this->db->where("U.UserID", "J.UserID", FALSE);
                                    $this->db->where("J.ContestID", $ContestID);
                                    $Query = $this->db->get();
                                    if ($Query->num_rows() > 0) {
                                        $CurrentUser = $Query->row_array();
                                        $this->db->select("J.ContestID,J.UserID,J.DraftUserLiveTime,J.DraftUserLive,U.UserGUID,J.DraftUserPosition,U.FirstName");
                                        $this->db->from('sports_contest_join J, tbl_users U');
                                        $this->db->where("J.DraftUserLive", "No");
                                        $this->db->where("J.DraftUserPosition", $CurrentUser['DraftUserPosition'] + 1);
                                        $this->db->where("U.UserID", "J.UserID", FALSE);
                                        $this->db->where("J.ContestID", $ContestID);
                                        $Query = $this->db->get();

                                        if ($Query->num_rows() > 0) {
                                            $NextUser = $Query->row_array();
                                            $CurrentDateTime = date('Y-m-d H:i:s');
                                            $DraftUserLiveTime = $NextUser['DraftUserLiveTime'];
                                            $CurrentDateTime = new DateTime($CurrentDateTime);
                                            $AuctionBreakDateTime = new DateTime($DraftUserLiveTime);
                                            $diffSeconds = $CurrentDateTime->getTimestamp() - $AuctionBreakDateTime->getTimestamp();
                                            $Users["UserLiveInTimeSeconds"] = $diffSeconds;
                                            $Users["ContestID"] = $Draft['ContestID'];
                                            $Users["SeriesID"] = $Draft['SeriesID'];
                                            $Users["RoundID"] = $Draft['RoundID'];
                                            $Users["ContestGUID"] = $Draft['ContestGUID'];
                                            $Users["SeriesGUID"] = $Draft['SeriesGUID'];
                                            $Users["MatchID"] = $Draft['MatchID'];
                                            $Users["MatchGUID"] = $Draft['MatchGUID'];
                                            $Users["DraftLiveRound"] = $Draft['DraftLiveRound'];
                                            $Users["DraftNextRound"] = $Draft['DraftLiveRound'];
                                            $Users["UserID"] = $NextUser['UserID'];
                                            $Users["UserGUID"] = $NextUser['UserGUID'];
                                            $Users["FirstName"] = $NextUser['FirstName'];
                                            $Return["Status"] = 1;
                                            $Return["Data"] = $Users;
                                            $Return['Message'] = "User in live";
                                        }
                                    }
                                }
                            } else {
                                /* value odd number * */
                                /** check last player in live * */
                                $this->db->select("J.ContestID,J.UserID,J.DraftUserLiveTime,J.DraftUserLive,U.UserGUID,U.FirstName");
                                $this->db->from('sports_contest_join J, tbl_users U');
                                $this->db->where("J.DraftUserLive", "Yes");
                                $this->db->where("U.UserID", "J.UserID", FALSE);
                                $this->db->where("J.ContestID", $ContestID);
                                $this->db->where("J.DraftUserPosition", 1);
                                $Query = $this->db->get();
                                if ($Query->num_rows() > 0) {
                                    $LiveUser = $Query->row_array();
                                    $CurrentDateTime = date('Y-m-d H:i:s');
                                    $DraftUserLiveTime = $LiveUser['DraftUserLiveTime'];
                                    $CurrentDateTime = new DateTime($CurrentDateTime);
                                    $AuctionBreakDateTime = new DateTime($DraftUserLiveTime);
                                    $diffSeconds = $CurrentDateTime->getTimestamp() - $AuctionBreakDateTime->getTimestamp();
                                    $Users["UserLiveInTimeSeconds"] = $diffSeconds;
                                    $Users["ContestID"] = $Draft['ContestID'];
                                    $Users["SeriesID"] = $Draft['SeriesID'];
                                    $Users["RoundID"] = $Draft['RoundID'];
                                    $Users["ContestGUID"] = $Draft['ContestGUID'];
                                    $Users["SeriesGUID"] = $Draft['SeriesGUID'];
                                    $Users["MatchID"] = $Draft['MatchID'];
                                    $Users["MatchGUID"] = $Draft['MatchGUID'];
                                    $Users["DraftLiveRound"] = $Draft['DraftLiveRound'];
                                    $Users["DraftNextRound"] = $Draft['DraftLiveRound'] + 1;
                                    $Users["UserID"] = $LiveUser['UserID'];
                                    $Users["UserGUID"] = $LiveUser['UserGUID'];
                                    $Users["FirstName"] = $LiveUser['FirstName'];
                                    $Return["Status"] = 1;
                                    $Return["Data"] = $Users;
                                    $Return['Message'] = "User in live";
                                    $this->draftRoundUpdate($Draft['ContestID'], $Draft['SeriesID'], $Draft['DraftLiveRound'] + 1);
                                } else {
                                    $this->db->select("J.ContestID,J.UserID,J.DraftUserLiveTime,J.DraftUserLive,U.UserGUID,J.DraftUserPosition");
                                    $this->db->from('sports_contest_join J, tbl_users U');
                                    $this->db->where("J.DraftUserLive", "Yes");
                                    $this->db->where("U.UserID", "J.UserID", FALSE);
                                    $this->db->where("J.ContestID", $ContestID);
                                    $Query = $this->db->get();
                                    if ($Query->num_rows() > 0) {
                                        $CurrentUser = $Query->row_array();
                                        $this->db->select("J.ContestID,J.UserID,J.DraftUserLiveTime,J.DraftUserLive,U.UserGUID,J.DraftUserPosition,U.FirstName");
                                        $this->db->from('sports_contest_join J, tbl_users U');
                                        $this->db->where("J.DraftUserLive", "No");
                                        $this->db->where("J.DraftUserPosition", $CurrentUser['DraftUserPosition'] - 1);
                                        $this->db->where("U.UserID", "J.UserID", FALSE);
                                        $this->db->where("J.ContestID", $ContestID);
                                        $Query = $this->db->get();
                                        if ($Query->num_rows() > 0) {
                                            $NextUser = $Query->row_array();
                                            $CurrentDateTime = date('Y-m-d H:i:s');
                                            $DraftUserLiveTime = $NextUser['DraftUserLiveTime'];
                                            $CurrentDateTime = new DateTime($CurrentDateTime);
                                            $AuctionBreakDateTime = new DateTime($DraftUserLiveTime);
                                            $diffSeconds = $CurrentDateTime->getTimestamp() - $AuctionBreakDateTime->getTimestamp();
                                            $Users["UserLiveInTimeSeconds"] = $diffSeconds;
                                            $Users["ContestID"] = $Draft['ContestID'];
                                            $Users["SeriesID"] = $Draft['SeriesID'];
                                            $Users["RoundID"] = $Draft['RoundID'];
                                            $Users["ContestGUID"] = $Draft['ContestGUID'];
                                            $Users["SeriesGUID"] = $Draft['SeriesGUID'];
                                            $Users["MatchID"] = $Draft['MatchID'];
                                            $Users["MatchGUID"] = $Draft['MatchGUID'];
                                            $Users["DraftLiveRound"] = $Draft['DraftLiveRound'];
                                            $Users["DraftNextRound"] = $Draft['DraftLiveRound'];
                                            $Users["UserID"] = $NextUser['UserID'];
                                            $Users["UserGUID"] = $NextUser['UserGUID'];
                                            $Users["FirstName"] = $NextUser['FirstName'];
                                            $Return["Status"] = 1;
                                            $Return["Data"] = $Users;
                                            $Return['Message'] = "User in live";
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            $Return["Message"] = "Draft not live";
        }
        dump($Return);
        return $Return;
    }

    function addDraftUserTeam($UserID, $ContestID, $SeriesID, $RoundID, $MatchID) {
        /** check is assistant and unsold player * */
        $UserTeamID = $this->db->query('SELECT T.UserTeamID from `sports_users_teams` T join tbl_users U on U.UserID = T.UserID WHERE   T.MatchID = "' . $MatchID . '" AND T.UserID = "' . $UserID . '" AND T.ContestID = "' . $ContestID . '" AND IsPreTeam = "No" AND IsAssistant="No" ')->row()->UserTeamID;
        if (empty($UserTeamID)) {
            $EntityGUID = get_guid();
            $EntityID = $this->Entity_model->addEntity($EntityGUID, array("EntityTypeID" => 12, "UserID" => $UserID, "StatusID" => 2));
            /* Add user team to user team table . */
            $teamName = "PostSnakeTeam 1";
            $UserTeamID = $EntityID;
            $InsertData = array(
                "UserTeamID" => $EntityID,
                "UserTeamGUID" => $EntityGUID,
                "UserID" => $UserID,
                "UserTeamName" => $teamName,
                "UserTeamType" => "Draft",
                "IsPreTeam" => "No",
                "SeriesID" => @$SeriesID,
                "RoundID" => @$RoundID,
                "MatchID" => @$MatchID,
                "ContestID" => $ContestID,
                "IsAssistant" => "No",
            );
            $this->db->insert('sports_users_teams', $InsertData);
        }
        return $UserTeamID;
    }

    function addDraftUserTeamSquad($UserTeamID, $UserID, $ContestID, $SeriesID, $Player, $RoundID = 0) {
        /** dynamic player role * */
        $ContestCriteria = $this->SnakeDrafts_model->getContests('ContestID,DraftTeamPlayerLimit,DraftPlayerSelectionCriteria', array("RoundID" => $RoundID, "ContestID" => $ContestID), FALSE, 1);
        //$Series = $this->Sports_model->getSeries("DraftTeamPlayerLimit,DraftPlayerSelectionCriteria", array("SeriesID" => $SeriesID));
        $DraftPlayerSelectionCriteria = (!empty($ContestCriteria['DraftPlayerSelectionCriteria'])) ? $ContestCriteria['DraftPlayerSelectionCriteria'] : array("Wk" => 0, "Bat" => 0, "Ar" => 0, "Bowl" => 0);
        $DraftTeamPlayerLimit = (!empty($ContestCriteria['DraftTeamPlayerLimit'])) ? $ContestCriteria['DraftTeamPlayerLimit'] : 0;
        /** check is assistant and unsold player * */
        $this->db->select("UTP.PlayerID,UTP.PlayerPosition");
        $this->db->from('sports_users_team_players UTP');
        $this->db->where("UTP.UserTeamID", $UserTeamID);
        $Query = $this->db->get();
        $Rows = $Query->num_rows();
        if ($Rows > 0) {
            $DraftSquad = $Query->result_array();
            foreach ($DraftSquad as $Key => $PlayerSquad) {
                $DraftSquad[$Key]['PlayerRole'] = $this->db->query('SELECT S.PlayerRole from `tbl_auction_player_bid_status` S  WHERE S.SeriesID = "' . $SeriesID . '" AND S.ContestID = "' . $ContestID . '" AND S.PlayerID = "' . $PlayerSquad['PlayerID'] . '"')->row()->PlayerRole;
            }
            /** check player role condition * */
            $PlayerRoles = array_count_values(array_column($DraftSquad, 'PlayerRole'));
            /** check bowler role * */
            if (@$PlayerRoles['Bowler'] < $DraftPlayerSelectionCriteria['Bowl'] && $Player['PlayerRole'] == "Bowler") {
                /** insert user team player squad * */
                $InsertData = array(
                    "UserTeamID" => $UserTeamID,
                    "PlayerPosition" => "Player",
                    "PlayerID" => $Player['PlayerID'],
                    "SeriesID" => $SeriesID,
                    "DateTime" => date('Y-m-d H:i:s'),
                );
                $this->db->insert('sports_users_team_players', $InsertData);
                /* Add contest to contest table . */
                $UpdateData = array_filter(array(
                    "PlayerStatus" => "Sold",
                    "DateTime" => date('Y-m-d H:i:s')
                ));
                $this->db->where('SeriesID', $SeriesID);
                $this->db->where('ContestID', $ContestID);
                $this->db->where('PlayerID', $Player['PlayerID']);
                $this->db->limit(1);
                $this->db->update('tbl_auction_player_bid_status', $UpdateData);
                return true;
            } else if (@$PlayerRoles['Batsman'] < $DraftPlayerSelectionCriteria['Bat'] && $Player['PlayerRole'] == "Batsman") {
                /** insert user team player squad * */
                $InsertData = array(
                    "UserTeamID" => $UserTeamID,
                    "PlayerPosition" => "Player",
                    "PlayerID" => $Player['PlayerID'],
                    "SeriesID" => $SeriesID,
                    "DateTime" => date('Y-m-d H:i:s'),
                );
                $this->db->insert('sports_users_team_players', $InsertData);
                /* Add contest to contest table . */
                $UpdateData = array_filter(array(
                    "PlayerStatus" => "Sold",
                    "DateTime" => date('Y-m-d H:i:s')
                ));
                $this->db->where('SeriesID', $SeriesID);
                $this->db->where('ContestID', $ContestID);
                $this->db->where('PlayerID', $Player['PlayerID']);
                $this->db->limit(1);
                $this->db->update('tbl_auction_player_bid_status', $UpdateData);
                return true;
            } else if (@$PlayerRoles['AllRounder'] < $DraftPlayerSelectionCriteria['Ar'] && $Player['PlayerRole'] == "AllRounder") {
                /** insert user team player squad * */
                $InsertData = array(
                    "UserTeamID" => $UserTeamID,
                    "PlayerPosition" => "Player",
                    "PlayerID" => $Player['PlayerID'],
                    "SeriesID" => $SeriesID,
                    "DateTime" => date('Y-m-d H:i:s'),
                );
                $this->db->insert('sports_users_team_players', $InsertData);
                /* Add contest to contest table . */
                $UpdateData = array_filter(array(
                    "PlayerStatus" => "Sold",
                    "DateTime" => date('Y-m-d H:i:s')
                ));
                $this->db->where('SeriesID', $SeriesID);
                $this->db->where('ContestID', $ContestID);
                $this->db->where('PlayerID', $Player['PlayerID']);
                $this->db->limit(1);
                $this->db->update('tbl_auction_player_bid_status', $UpdateData);
                return true;
            } else if (@$PlayerRoles['WicketKeeper'] < $DraftPlayerSelectionCriteria['Wk'] && $Player['PlayerRole'] == "WicketKeeper") {
                /** insert user team player squad * */
                $InsertData = array(
                    "UserTeamID" => $UserTeamID,
                    "PlayerPosition" => "Player",
                    "PlayerID" => $Player['PlayerID'],
                    "SeriesID" => $SeriesID,
                    "DateTime" => date('Y-m-d H:i:s'),
                );
                $this->db->insert('sports_users_team_players', $InsertData);
                /* Add contest to contest table . */
                $UpdateData = array_filter(array(
                    "PlayerStatus" => "Sold",
                    "DateTime" => date('Y-m-d H:i:s')
                ));
                $this->db->where('SeriesID', $SeriesID);
                $this->db->where('ContestID', $ContestID);
                $this->db->where('PlayerID', $Player['PlayerID']);
                $this->db->limit(1);
                $this->db->update('tbl_auction_player_bid_status', $UpdateData);
                return true;
            } else {
                //echo 1;exit;
                /** check total player in squad * */
                if ($Rows < $DraftTeamPlayerLimit) {
                    /** insert user team player squad * */
                    $InsertData = array(
                        "UserTeamID" => $UserTeamID,
                        "PlayerPosition" => "Player",
                        "PlayerID" => $Player['PlayerID'],
                        "SeriesID" => $SeriesID,
                        "DateTime" => date('Y-m-d H:i:s'),
                    );
                    $this->db->insert('sports_users_team_players', $InsertData);
                    /* Add contest to contest table . */
                    $UpdateData = array_filter(array(
                        "PlayerStatus" => "Sold",
                        "DateTime" => date('Y-m-d H:i:s')
                    ));
                    $this->db->where('SeriesID', $SeriesID);
                    $this->db->where('ContestID', $ContestID);
                    $this->db->where('PlayerID', $Player['PlayerID']);
                    $this->db->limit(1);
                    $this->db->update('tbl_auction_player_bid_status', $UpdateData);
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            /** insert user team player squad * */
            $InsertData = array(
                "UserTeamID" => $UserTeamID,
                "PlayerPosition" => "Player",
                "PlayerID" => $Player['PlayerID'],
                "SeriesID" => $SeriesID,
                "DateTime" => date('Y-m-d H:i:s'),
            );
            $this->db->insert('sports_users_team_players', $InsertData);

            /** update player status * */
            /* Add contest to contest table . */
            $UpdateData = array_filter(array(
                "PlayerStatus" => "Sold",
                "DateTime" => date('Y-m-d H:i:s')
            ));
            $this->db->where('SeriesID', $SeriesID);
            $this->db->where('ContestID', $ContestID);
            $this->db->where('PlayerID', $Player['PlayerID']);
            $this->db->limit(1);
            $this->db->update('tbl_auction_player_bid_status', $UpdateData);
            return true;
        }
    }

    function addDraftUserTeamSquadAssistant($UserTeamID, $UserID, $ContestID, $SeriesID, $Player, $RoundID, $MatchID) {
        $Return = array();
        $Return["Status"] = 0;
        $Return["Player"] = array();
        /** dynamic player role * */
        $ContestCriteria = $this->SnakeDrafts_model->getContests('ContestID,DraftTeamPlayerLimit,DraftPlayerSelectionCriteria', array("MatchID" => $MatchID, "ContestID" => $ContestID), FALSE, 1);
        //$Series = $this->Sports_model->getSeries("DraftTeamPlayerLimit,DraftPlayerSelectionCriteria", array("SeriesID" => $SeriesID));
        $DraftPlayerSelectionCriteria = (!empty($ContestCriteria['DraftPlayerSelectionCriteria'])) ? $ContestCriteria['DraftPlayerSelectionCriteria'] : array("Wk" => 0, "Bat" => 0, "Ar" => 0, "Bowl" => 0);
        //$DraftPlayerSelectionCriteria = (!empty($DraftPlayerSelectionCriteria)) ? $DraftPlayerSelectionCriteria : array("Wk" => 0, "Bat" => 0, "Ar" => 0, "Bowl" => 0);
        $DraftTeamPlayerLimit = (!empty($ContestCriteria['DraftTeamPlayerLimit'])) ? $ContestCriteria['DraftTeamPlayerLimit'] : 0;

        /** check is assistant and unsold player * */
        $this->db->select("UTP.PlayerID,UTP.PlayerPosition");
        $this->db->from('sports_users_team_players UTP');
        $this->db->where("UTP.UserTeamID", $UserTeamID);
        $Query = $this->db->get();
        $Rows = $Query->num_rows();

        if ($Rows > 0) {
            $DraftSquad = $Query->result_array();
            foreach ($DraftSquad as $Key => $PlayerSquad) {
                $DraftSquad[$Key]['PlayerRole'] = $this->db->query('SELECT S.PlayerRole from `tbl_auction_player_bid_status` S  WHERE S.MatchID = "' . $MatchID . '" AND S.ContestID = "' . $ContestID . '" AND S.PlayerID = "' . $PlayerSquad['PlayerID'] . '"')->row()->PlayerRole;
            }
            /** check player role condition * */
            $PlayerRoles = array_count_values(array_column($DraftSquad, 'PlayerRole'));

            /** check is assistant and unsold player * */
            $this->db->select("BS.PlayerRole,UTP.PlayerID,UTP.BidCredit,UT.UserTeamID,UT.UserID,UTP.AuctionDraftAssistantPriority,BS.PlayerStatus,SP.PlayerName");
            $this->db->from('sports_users_teams UT, sports_users_team_players UTP,tbl_auction_player_bid_status BS,sports_players SP');
            $this->db->where("UT.UserTeamID", "UTP.UserTeamID", FALSE);
            $this->db->where("UT.ContestID", "BS.ContestID", FALSE);
            $this->db->where("UT.MatchID", "BS.MatchID", FALSE);
            $this->db->where("UTP.PlayerID", "BS.PlayerID", FALSE);
            $this->db->where("BS.PlayerID", "SP.PlayerID", FALSE);
            $this->db->where("UT.IsAssistant", "Yes");
            $this->db->where("UT.IsPreTeam", "Yes");
            $this->db->where("UT.UserTeamType", "Draft");
            //$this->db->where("BS.PlayerStatus", "Upcoming");
            $this->db->where_in("BS.PlayerStatus", array("Live", "Upcoming"));
            $this->db->where("UT.ContestID", $ContestID);
//            $this->db->where("UT.SeriesID", $SeriesID);
//            $this->db->where("UT.RoundID", $RoundID);
            $this->db->where("UT.UserID", $UserID);
            $this->db->order_by("UTP.AuctionDraftAssistantPriority", "ASC");
            $Query = $this->db->get();
            if ($Query->num_rows() > 0) {
                $AssistantPlayers = $Query->result_array();
                foreach ($AssistantPlayers as $Assistant) {

                    /** check wicketKeeper minium criteria* */
                    if (@$PlayerRoles['WicketKeeper'] < $DraftPlayerSelectionCriteria['Wk'] && $Assistant['PlayerRole'] == "WicketKeeper") {
                        /** insert user team player squad * */
                        $InsertData = array(
                            "UserTeamID" => $UserTeamID,
                            "PlayerPosition" => "Player",
                            "PlayerID" => $Assistant['PlayerID'],
                            "SeriesID" => @$SeriesID,
                            "RoundID" => @$RoundID,
                            "MatchID" => @$MatchID,
                            "DateTime" => date('Y-m-d H:i:s'),
                        );
                        $this->db->insert('sports_users_team_players', $InsertData);
                        /* Add contest to contest table . */
                        $UpdateData = array_filter(array(
                            "PlayerStatus" => "Sold",
                            "DateTime" => date('Y-m-d H:i:s')
                        ));
//                        $this->db->where('SeriesID', $SeriesID);
//                        $this->db->where('RoundID', $RoundID);
                        $this->db->where('ContestID', $ContestID);
                        $this->db->where('PlayerID', $Assistant['PlayerID']);
                        $this->db->limit(1);
                        $this->db->update('tbl_auction_player_bid_status', $UpdateData);
                        $Return["Status"] = 1;
                        $Return["Player"] = $Assistant;
                        return $Return;
                    }

                    /** check bowler role * */
                    if (@$PlayerRoles['Bowler'] < $DraftPlayerSelectionCriteria['Bowl'] && $Assistant['PlayerRole'] == "Bowler") {
                        /** insert user team player squad * */
                        $InsertData = array(
                            "UserTeamID" => $UserTeamID,
                            "PlayerPosition" => "Player",
                            "PlayerID" => $Assistant['PlayerID'],
                            "SeriesID" => $SeriesID,
                            "RoundID" => $RoundID,
                            "MatchID" => $MatchID,
                            "DateTime" => date('Y-m-d H:i:s'),
                        );
                        $this->db->insert('sports_users_team_players', $InsertData);
                        /* Add contest to contest table . */
                        $UpdateData = array_filter(array(
                            "PlayerStatus" => "Sold",
                            "DateTime" => date('Y-m-d H:i:s')
                        ));
//                        $this->db->where('SeriesID', $SeriesID);
//                        $this->db->where('RoundID', $RoundID);
                        $this->db->where('ContestID', $ContestID);
                        $this->db->where('PlayerID', $Assistant['PlayerID']);
                        $this->db->limit(1);
                        $this->db->update('tbl_auction_player_bid_status', $UpdateData);
                        $Return["Status"] = 1;
                        $Return["Player"] = $Assistant;
                        return $Return;
                    }

                    if (@$PlayerRoles['Batsman'] < $DraftPlayerSelectionCriteria['Bat'] && $Assistant['PlayerRole'] == "Batsman") {
                        /** insert user team player squad * */
                        $InsertData = array(
                            "UserTeamID" => $UserTeamID,
                            "PlayerPosition" => "Player",
                            "PlayerID" => $Assistant['PlayerID'],
                            "SeriesID" => $SeriesID,
                            "RoundID" => $RoundID,
                            "MatchID" => $MatchID,
                            "DateTime" => date('Y-m-d H:i:s'),
                        );
                        $this->db->insert('sports_users_team_players', $InsertData);
                        /* Add contest to contest table . */
                        $UpdateData = array_filter(array(
                            "PlayerStatus" => "Sold",
                            "DateTime" => date('Y-m-d H:i:s')
                        ));
//                        $this->db->where('SeriesID', $SeriesID);
//                        $this->db->where('RoundID', $RoundID);
                        $this->db->where('ContestID', $ContestID);
                        $this->db->where('PlayerID', $Assistant['PlayerID']);
                        $this->db->limit(1);
                        $this->db->update('tbl_auction_player_bid_status', $UpdateData);
                        $Return["Status"] = 1;
                        $Return["Player"] = $Assistant;
                        return $Return;
                    }

                    if (@$PlayerRoles['AllRounder'] < $DraftPlayerSelectionCriteria['Ar'] && $Assistant['PlayerRole'] == "AllRounder") {
                        /** insert user team player squad * */
                        $InsertData = array(
                            "UserTeamID" => $UserTeamID,
                            "PlayerPosition" => "Player",
                            "PlayerID" => $Assistant['PlayerID'],
                            "SeriesID" => $SeriesID,
                            "RoundID" => $RoundID,
                            "MatchID" => $MatchID,
                            "DateTime" => date('Y-m-d H:i:s'),
                        );
                        $this->db->insert('sports_users_team_players', $InsertData);
                        /* Add contest to contest table . */
                        $UpdateData = array_filter(array(
                            "PlayerStatus" => "Sold",
                            "DateTime" => date('Y-m-d H:i:s')
                        ));
//                        $this->db->where('SeriesID', $SeriesID);
//                        $this->db->where('RoundID', $RoundID);
                        $this->db->where('ContestID', $ContestID);
                        $this->db->where('PlayerID', $Assistant['PlayerID']);
                        $this->db->limit(1);
                        $this->db->update('tbl_auction_player_bid_status', $UpdateData);
                        $Return["Status"] = 1;
                        $Return["Player"] = $Assistant;

                        return $Return;
                    }

                    if (@$PlayerRoles['WicketKeeper'] >= $DraftPlayerSelectionCriteria['Wk'] && @$PlayerRoles['Bowler'] >= $DraftPlayerSelectionCriteria['Bowl'] && @$PlayerRoles['Batsman'] >= $DraftPlayerSelectionCriteria['Bat'] && @$PlayerRoles['AllRounder'] >= $DraftPlayerSelectionCriteria['Ar']) {
                        /** check total player in squad * */
                        if ($Rows < $DraftTeamPlayerLimit) {
                            /** insert user team player squad * */
                            $InsertData = array(
                                "UserTeamID" => $UserTeamID,
                                "PlayerPosition" => "Player",
                                "PlayerID" => $Assistant['PlayerID'],
                                "SeriesID" => $SeriesID,
                                "RoundID" => $RoundID,
                                "MatchID" => $MatchID,
                                "DateTime" => date('Y-m-d H:i:s'),
                            );
                            $this->db->insert('sports_users_team_players', $InsertData);
                            /* Add contest to contest table . */
                            $UpdateData = array_filter(array(
                                "PlayerStatus" => "Sold",
                                "DateTime" => date('Y-m-d H:i:s')
                            ));
//                            $this->db->where('SeriesID', $SeriesID);
//                            $this->db->where('RoundID', $RoundID);
                            $this->db->where('ContestID', $ContestID);
                            $this->db->where('PlayerID', $Assistant['PlayerID']);
                            $this->db->limit(1);
                            $this->db->update('tbl_auction_player_bid_status', $UpdateData);
                            $Return["Status"] = 1;
                            $Return["Player"] = $Assistant;

                            return $Return;
                        } else {

                            $Return["Status"] = 0;
                            $Return["Player"] = $Assistant;
                            return $Return;
                        }
                    }
                }
            }
        } else {
            /** insert user team player squad * */
            $InsertData = array(
                "UserTeamID" => $UserTeamID,
                "PlayerPosition" => "Player",
                "PlayerID" => $Player['PlayerID'],
                "SeriesID" => $SeriesID,
                "RoundID" => $RoundID,
                "MatchID" => $MatchID,
                "DateTime" => date('Y-m-d H:i:s'),
            );
            $this->db->insert('sports_users_team_players', $InsertData);

            /** update player status * */
            /* Add contest to contest table . */
            $UpdateData = array_filter(array(
                "PlayerStatus" => "Sold",
                "DateTime" => date('Y-m-d H:i:s')
            ));
//            $this->db->where('SeriesID', $SeriesID);
//            $this->db->where('RoundID', $RoundID);
            $this->db->where('ContestID', $ContestID);
            $this->db->where('PlayerID', $Player['PlayerID']);
            $this->db->limit(1);
            $this->db->update('tbl_auction_player_bid_status', $UpdateData);
            $Return["Status"] = 1;
            $Return["Player"] = $Player;
            return $Return;
        }
    }

    function addDraftUserTeamSquadNotAssistant($UserTeamID, $UserID, $ContestID, $SeriesID, $Player, $DraftContestDetails, $RoundID, $MatchID) {
        $Return = array();
        $Return["Status"] = 0;
        $Return["Player"] = array();
        /** dynamic player role * */
        $ContestCriteria = $this->SnakeDrafts_model->getContests('ContestID,DraftTeamPlayerLimit,DraftPlayerSelectionCriteria', array("MatchID" => $MatchID, "ContestID" => $ContestID), FALSE, 1);
        //$Series = $this->Sports_model->getSeries("DraftTeamPlayerLimit,DraftPlayerSelectionCriteria", array("SeriesID" => $SeriesID));
        $DraftPlayerSelectionCriteria = (!empty($ContestCriteria['DraftPlayerSelectionCriteria'])) ? $ContestCriteria['DraftPlayerSelectionCriteria'] : array("Wk" => 0, "Bat" => 0, "Ar" => 0, "Bowl" => 0);
        $DraftTeamPlayerLimit = (!empty($ContestCriteria['DraftTeamPlayerLimit'])) ? $ContestCriteria['DraftTeamPlayerLimit'] : 0;
        /** check is assistant and unsold player * */
        $this->db->select("UTP.PlayerID,UTP.PlayerPosition");
        $this->db->from('sports_users_team_players UTP');
        $this->db->where("UTP.UserTeamID", $UserTeamID);
        $Query = $this->db->get();
        $Rows = $Query->num_rows();
        if ($Rows > 0) {
            $DraftSquad = $Query->result_array();
            foreach ($DraftSquad as $Key => $PlayerSquad) {
                $DraftSquad[$Key]['PlayerRole'] = $this->db->query('SELECT S.PlayerRole from `tbl_auction_player_bid_status` S  WHERE  S.MatchID = "' . $MatchID . '" AND S.ContestID = "' . $ContestID . '" AND S.PlayerID = "' . $PlayerSquad['PlayerID'] . '"')->row()->PlayerRole;
            }

            $DraftLiveRound = $DraftContestDetails['Data']['Records'][0]['DraftLiveRound'];
            $CriteriaRounds = $DraftPlayerSelectionCriteria['Wk'] + $DraftPlayerSelectionCriteria['Bat'] + $DraftPlayerSelectionCriteria['Bowl'] + $DraftPlayerSelectionCriteria['Ar'];
            /** check player role condition * */
            $PlayerRoles = array_count_values(array_column($DraftSquad, 'PlayerRole'));

            if ($DraftLiveRound <= $CriteriaRounds) {

                if (@$PlayerRoles['WicketKeeper'] < $DraftPlayerSelectionCriteria['Wk']) {
                    /** check total player in squad * */
                    if ($Rows < $DraftTeamPlayerLimit) {

                        /** check is assistant and unsold player * */
                        $this->db->select("BS.PlayerRole,BS.PlayerStatus,BS.PlayerID,SP.PlayerName");
                        $this->db->from('tbl_auction_player_bid_status BS,sports_players SP');
                        $this->db->where("BS.PlayerID", "SP.PlayerID", FALSE);
                        //$this->db->where("BS.PlayerStatus", "Upcoming");
                        $this->db->where_in("BS.PlayerStatus", array("Live", "Upcoming"));
                        $this->db->where("BS.ContestID", $ContestID);
//                        $this->db->where("BS.SeriesID", $SeriesID);
//                        $this->db->where("BS.RoundID", $RoundID);
                        $this->db->where("BS.PlayerRole", "WicketKeeper");
                        $this->db->limit(1);
                        $Query = $this->db->get();
                        if ($Query->num_rows() > 0) {
                            $AllPlayer = $Query->row_array();
                            /** insert user team player squad * */
                            $InsertData = array(
                                "UserTeamID" => $UserTeamID,
                                "PlayerPosition" => "Player",
                                "PlayerID" => $AllPlayer['PlayerID'],
                                "SeriesID" => $SeriesID,
                                "RoundID" => $RoundID,
                                "MatchID" => $MatchID,
                                "DateTime" => date('Y-m-d H:i:s'),
                            );
                            $this->db->insert('sports_users_team_players', $InsertData);
                            /* Add contest to contest table . */
                            $UpdateData = array_filter(array(
                                "PlayerStatus" => "Sold",
                                "DateTime" => date('Y-m-d H:i:s')
                            ));
//                            $this->db->where('SeriesID', $SeriesID);
//                            $this->db->where('RoundID', $RoundID);
                            $this->db->where('ContestID', $ContestID);
                            $this->db->where('PlayerID', $AllPlayer['PlayerID']);
                            $this->db->limit(1);
                            $this->db->update('tbl_auction_player_bid_status', $UpdateData);
                            $Return["Status"] = 1;
                            $Return["Player"] = $AllPlayer;
                            return $Return;
                        } else {
                            $Return["Status"] = 0;
                            return $Return;
                        }
                    } else {
                        $Return["Status"] = 0;
                        return $Return;
                    }
                }

                if (@$PlayerRoles['Batsman'] < $DraftPlayerSelectionCriteria['Bat']) {
                    /** check total player in squad * */
                    if ($Rows < $DraftTeamPlayerLimit) {

                        /** check is assistant and unsold player * */
                        $this->db->select("BS.PlayerRole,BS.PlayerStatus,BS.PlayerID,SP.PlayerName");
                        $this->db->from('tbl_auction_player_bid_status BS,sports_players SP');
                        $this->db->where("BS.PlayerID", "SP.PlayerID", FALSE);
                        //$this->db->where("BS.PlayerStatus", "Upcoming");
                        $this->db->where_in("BS.PlayerStatus", array("Live", "Upcoming"));
                        $this->db->where("BS.ContestID", $ContestID);
//                        $this->db->where("BS.SeriesID", $SeriesID);
//                        $this->db->where("BS.RoundID", $RoundID);
                        $this->db->where("BS.PlayerRole", "Batsman");
                        $this->db->limit(1);
                        $Query = $this->db->get();
                        if ($Query->num_rows() > 0) {
                            $AllPlayer = $Query->row_array();
                            /** insert user team player squad * */
                            $InsertData = array(
                                "UserTeamID" => $UserTeamID,
                                "PlayerPosition" => "Player",
                                "PlayerID" => $AllPlayer['PlayerID'],
                                "SeriesID" => $SeriesID,
                                "RoundID" => $RoundID,
                                "MatchID" => $MatchID,
                                "DateTime" => date('Y-m-d H:i:s'),
                            );
                            $this->db->insert('sports_users_team_players', $InsertData);
                            /* Add contest to contest table . */
                            $UpdateData = array_filter(array(
                                "PlayerStatus" => "Sold",
                                "DateTime" => date('Y-m-d H:i:s')
                            ));
//                            $this->db->where('SeriesID', $SeriesID);
//                            $this->db->where('RoundID', $RoundID);
                            $this->db->where('ContestID', $ContestID);
                            $this->db->where('PlayerID', $AllPlayer['PlayerID']);
                            $this->db->limit(1);
                            $this->db->update('tbl_auction_player_bid_status', $UpdateData);
                            $Return["Status"] = 1;
                            $Return["Player"] = $AllPlayer;
                            return $Return;
                        } else {
                            $Return["Status"] = 0;
                            return $Return;
                        }
                    } else {
                        $Return["Status"] = 0;
                        return $Return;
                    }
                }

                if (@$PlayerRoles['Bowler'] < $DraftPlayerSelectionCriteria['Bowl']) {
                    /** check total player in squad * */
                    if ($Rows < $DraftTeamPlayerLimit) {

                        /** check is assistant and unsold player * */
                        $this->db->select("BS.PlayerRole,BS.PlayerStatus,BS.PlayerID,SP.PlayerName");
                        $this->db->from('tbl_auction_player_bid_status BS,sports_players SP');
                        $this->db->where("BS.PlayerID", "SP.PlayerID", FALSE);
                        // $this->db->where("BS.PlayerStatus", "Upcoming");
                        $this->db->where_in("BS.PlayerStatus", array("Live", "Upcoming"));
                        $this->db->where("BS.ContestID", $ContestID);
//                        $this->db->where("BS.SeriesID", $SeriesID);
//                        $this->db->where("BS.RoundID", $RoundID);
                        $this->db->where("BS.PlayerRole", "Bowler");
                        $this->db->limit(1);
                        $Query = $this->db->get();
                        if ($Query->num_rows() > 0) {
                            $AllPlayer = $Query->row_array();
                            /** insert user team player squad * */
                            $InsertData = array(
                                "UserTeamID" => $UserTeamID,
                                "PlayerPosition" => "Player",
                                "PlayerID" => $AllPlayer['PlayerID'],
                                "SeriesID" => $SeriesID,
                                "RoundID" => $RoundID,
                                "MatchID" => $MatchID,
                                "DateTime" => date('Y-m-d H:i:s'),
                            );
                            $this->db->insert('sports_users_team_players', $InsertData);
                            /* Add contest to contest table . */
                            $UpdateData = array_filter(array(
                                "PlayerStatus" => "Sold",
                                "DateTime" => date('Y-m-d H:i:s')
                            ));
//                            $this->db->where('SeriesID', $SeriesID);
//                            $this->db->where('RoundID', $RoundID);
                            $this->db->where('ContestID', $ContestID);
                            $this->db->where('PlayerID', $AllPlayer['PlayerID']);
                            $this->db->limit(1);
                            $this->db->update('tbl_auction_player_bid_status', $UpdateData);
                            $Return["Status"] = 1;
                            $Return["Player"] = $AllPlayer;
                            return $Return;
                        } else {
                            $Return["Status"] = 0;
                            return $Return;
                        }
                    } else {
                        $Return["Status"] = 0;
                        return $Return;
                    }
                }

                if (@$PlayerRoles['AllRounder'] < $DraftPlayerSelectionCriteria['Ar']) {
                    /** check total player in squad * */
                    if ($Rows < $DraftTeamPlayerLimit) {

                        /** check is assistant and unsold player * */
                        $this->db->select("BS.PlayerRole,BS.PlayerStatus,BS.PlayerID,SP.PlayerName");
                        $this->db->from('tbl_auction_player_bid_status BS,sports_players SP');
                        $this->db->where("BS.PlayerID", "SP.PlayerID", FALSE);
                        //$this->db->where("BS.PlayerStatus", "Upcoming");
                        $this->db->where_in("BS.PlayerStatus", array("Live", "Upcoming"));
                        $this->db->where("BS.ContestID", $ContestID);
//                        $this->db->where("BS.SeriesID", $SeriesID);
//                        $this->db->where("BS.RoundID", $RoundID);
                        $this->db->where("BS.PlayerRole", "AllRounder");
                        $this->db->limit(1);
                        $Query = $this->db->get();
                        if ($Query->num_rows() > 0) {
                            $AllPlayer = $Query->row_array();
                            /** insert user team player squad * */
                            $InsertData = array(
                                "UserTeamID" => $UserTeamID,
                                "PlayerPosition" => "Player",
                                "PlayerID" => $AllPlayer['PlayerID'],
                                "SeriesID" => $SeriesID,
                                "RoundID" => $RoundID,
                                "MatchID" => $MatchID,
                                "DateTime" => date('Y-m-d H:i:s'),
                            );
                            $this->db->insert('sports_users_team_players', $InsertData);
                            /* Add contest to contest table . */
                            $UpdateData = array_filter(array(
                                "PlayerStatus" => "Sold",
                                "DateTime" => date('Y-m-d H:i:s')
                            ));
//                            $this->db->where('SeriesID', $SeriesID);
//                            $this->db->where('RoundID', $RoundID);
                            $this->db->where('ContestID', $ContestID);
                            $this->db->where('PlayerID', $AllPlayer['PlayerID']);
                            $this->db->limit(1);
                            $this->db->update('tbl_auction_player_bid_status', $UpdateData);
                            $Return["Status"] = 1;
                            $Return["Player"] = $AllPlayer;
                            return $Return;
                        } else {
                            $Return["Status"] = 0;
                            return $Return;
                        }
                    } else {
                        $Return["Status"] = 0;
                        return $Return;
                    }
                }
            } else {
                /** check total player in squad * */
                if ($Rows < $DraftTeamPlayerLimit) {

                    /** check is assistant and unsold player * */
                    $this->db->select("BS.PlayerRole,BS.PlayerStatus,BS.PlayerID,SP.PlayerName");
                    $this->db->from('tbl_auction_player_bid_status BS,sports_players SP');
                    $this->db->where("BS.PlayerID", "SP.PlayerID", FALSE);
                    //$this->db->where("BS.PlayerStatus", "Upcoming");
                    $this->db->where_in("BS.PlayerStatus", array("Live", "Upcoming"));
                    $this->db->where("BS.ContestID", $ContestID);
//                    $this->db->where("BS.SeriesID", $SeriesID);
//                    $this->db->where("BS.RoundID", $RoundID);
                    $this->db->limit(1);
                    $Query = $this->db->get();
                    if ($Query->num_rows() > 0) {
                        $AllPlayer = $Query->row_array();
                        /** insert user team player squad * */
                        $InsertData = array(
                            "UserTeamID" => $UserTeamID,
                            "PlayerPosition" => "Player",
                            "PlayerID" => $AllPlayer['PlayerID'],
                            "SeriesID" => $SeriesID,
                            "RoundID" => $RoundID,
                            "MatchID" => $MatchID,
                            "DateTime" => date('Y-m-d H:i:s'),
                        );
                        $this->db->insert('sports_users_team_players', $InsertData);
                        /* Add contest to contest table . */
                        $UpdateData = array_filter(array(
                            "PlayerStatus" => "Sold",
                            "DateTime" => date('Y-m-d H:i:s')
                        ));
//                        $this->db->where('SeriesID', $SeriesID);
//                        $this->db->where('RoundID', $RoundID);
                        $this->db->where('ContestID', $ContestID);
                        $this->db->where('PlayerID', $AllPlayer['PlayerID']);
                        $this->db->limit(1);
                        $this->db->update('tbl_auction_player_bid_status', $UpdateData);
                        $Return["Status"] = 1;
                        $Return["Player"] = $AllPlayer;
                        return $Return;
                    } else {
                        $Return["Status"] = 0;
                        return $Return;
                    }
                } else {
                    $Return["Status"] = 0;
                    return $Return;
                }
            }
        } else {
            /** insert user team player squad * */
            $InsertData = array(
                "UserTeamID" => $UserTeamID,
                "PlayerPosition" => "Player",
                "PlayerID" => $Player['PlayerID'],
                "SeriesID" => $SeriesID,
                "RoundID" => $RoundID,
                "MatchID" => $MatchID,
                "DateTime" => date('Y-m-d H:i:s'),
            );
            $this->db->insert('sports_users_team_players', $InsertData);

            /** update player status * */
            /* Add contest to contest table . */
            $UpdateData = array_filter(array(
                "PlayerStatus" => "Sold",
                "DateTime" => date('Y-m-d H:i:s')
            ));
//            $this->db->where('SeriesID', $SeriesID);
//            $this->db->where('RoundID', $RoundID);
            $this->db->where('ContestID', $ContestID);
            $this->db->where('PlayerID', $Player['PlayerID']);
            $this->db->limit(1);
            $this->db->update('tbl_auction_player_bid_status', $UpdateData);
            $Return["Status"] = 1;
            $Return["Player"] = $Player;
            return $Return;
        }
    }

    function addDraftUserTeamSquadIsPlayer($UserTeamID, $UserID, $ContestID, $SeriesID, $Player, $DraftContestDetails, $RoundID, $MatchID) {
        $Return = array();
        $Return["Status"] = 0;
        $Return["Player"] = array();
        /** dynamic player role * */
        $ContestCriteria = $this->SnakeDrafts_model->getContests('ContestID,DraftTeamPlayerLimit,DraftPlayerSelectionCriteria', array("MatchID" => $MatchID, "ContestID" => $ContestID), FALSE, 1);
        //$Series = $this->Sports_model->getSeries("DraftTeamPlayerLimit,DraftPlayerSelectionCriteria", array("SeriesID" => $SeriesID));
        $DraftPlayerSelectionCriteria = (!empty($ContestCriteria['DraftPlayerSelectionCriteria'])) ? $ContestCriteria['DraftPlayerSelectionCriteria'] : array("Wk" => 0, "Bat" => 0, "Ar" => 0, "Bowl" => 0);
        //$DraftPlayerSelectionCriteria = (!empty($DraftPlayerSelectionCriteria)) ? $DraftPlayerSelectionCriteria : array("Wk" => 0, "Bat" => 0, "Ar" => 0, "Bowl" => 0);
        $DraftTeamPlayerLimit = (!empty($ContestCriteria['DraftTeamPlayerLimit'])) ? $ContestCriteria['DraftTeamPlayerLimit'] : 0;
        /** check is assistant and unsold player * */
        $this->db->select("UTP.PlayerID,UTP.PlayerPosition");
        $this->db->from('sports_users_team_players UTP');
        $this->db->where("UTP.UserTeamID", $UserTeamID);
        $Query = $this->db->get();
        $Rows = $Query->num_rows();
        if ($Rows > 0) {
            $DraftSquad = $Query->result_array();
            foreach ($DraftSquad as $Key => $PlayerSquad) {
                $DraftSquad[$Key]['PlayerRole'] = $this->db->query('SELECT S.PlayerRole from `tbl_auction_player_bid_status` S  WHERE S.MatchID = "' . $MatchID . '" AND S.ContestID = "' . $ContestID . '" AND S.PlayerID = "' . $PlayerSquad['PlayerID'] . '"')->row()->PlayerRole;
            }

            $DraftLiveRound = $DraftContestDetails['Data']['Records'][0]['DraftLiveRound'];
            $CriteriaRounds = $DraftPlayerSelectionCriteria['Wk'] + $DraftPlayerSelectionCriteria['Bat'] + $DraftPlayerSelectionCriteria['Bowl'] + $DraftPlayerSelectionCriteria['Ar'];
            /** check player role condition * */
            $PlayerRoles = array_count_values(array_column($DraftSquad, 'PlayerRole'));

            if ($DraftLiveRound <= $CriteriaRounds) {

                if ($Player['PlayerRole'] == "WicketKeeper") {

                    if (@$PlayerRoles['WicketKeeper'] < $DraftPlayerSelectionCriteria['Wk']) {
                        /** check total player in squad * */
                        if ($Rows < $DraftTeamPlayerLimit) {
                            /** insert user team player squad * */
                            $InsertData = array(
                                "UserTeamID" => $UserTeamID,
                                "PlayerPosition" => "Player",
                                "PlayerID" => $Player['PlayerID'],
                                "SeriesID" => $SeriesID,
                                "RoundID" => $RoundID,
                                "MatchID" => $MatchID,
                                "DateTime" => date('Y-m-d H:i:s'),
                            );
                            $this->db->insert('sports_users_team_players', $InsertData);
                            /* Add contest to contest table . */
                            $UpdateData = array_filter(array(
                                "PlayerStatus" => "Sold",
                                "DateTime" => date('Y-m-d H:i:s')
                            ));
//                            $this->db->where('SeriesID', $SeriesID);
//                            $this->db->where('RoundID', $RoundID);
                            $this->db->where('ContestID', $ContestID);
                            $this->db->where('PlayerID', $Player['PlayerID']);
                            $this->db->limit(1);
                            $this->db->update('tbl_auction_player_bid_status', $UpdateData);
                            $Return["Status"] = 1;
                            $Return["Player"] = $Player;
                            return $Return;
                        } else {
                            $Return["Status"] = 0;
                            $Return['Message'] = "Team Players length can't exceed $DraftTeamPlayerLimit";
                            return $Return;
                        }
                    } else {
                        $Return["Status"] = 0;
                        $Return['Message'] = "Minimum Criteria for WicketKeeper is fulfilled. Please select player for another position will you complete the minimum criteria of 11 Players";
                        return $Return;
                    }
                }

                if ($Player['PlayerRole'] == "Batsman") {
                    if (@$PlayerRoles['Batsman'] < $DraftPlayerSelectionCriteria['Bat']) {
                        /** check total player in squad * */
                        if ($Rows < $DraftTeamPlayerLimit) {
                            /** insert user team player squad * */
                            $InsertData = array(
                                "UserTeamID" => $UserTeamID,
                                "PlayerPosition" => "Player",
                                "PlayerID" => $Player['PlayerID'],
                                "SeriesID" => $SeriesID,
                                "RoundID" => $RoundID,
                                "MatchID" => $MatchID,
                                "DateTime" => date('Y-m-d H:i:s'),
                            );
                            $this->db->insert('sports_users_team_players', $InsertData);
                            /* Add contest to contest table . */
                            $UpdateData = array_filter(array(
                                "PlayerStatus" => "Sold",
                                "DateTime" => date('Y-m-d H:i:s')
                            ));
//                            $this->db->where('SeriesID', $SeriesID);
//                            $this->db->where('RoundID', $RoundID);
                            $this->db->where('ContestID', $ContestID);
                            $this->db->where('PlayerID', $Player['PlayerID']);
                            $this->db->limit(1);
                            $this->db->update('tbl_auction_player_bid_status', $UpdateData);
                            $Return["Status"] = 1;
                            $Return["Player"] = $Player;
                            return $Return;
                        } else {
                            $Return["Status"] = 0;
                            $Return['Message'] = "Team Players length can't exceed $DraftTeamPlayerLimit";
                            return $Return;
                        }
                    } else {
                        $Return["Status"] = 0;
                        $Return['Message'] = "Minimum Criteria for Batsman is fulfilled. Please select player for another position will you complete the minimum criteria of 11 Players";
                        return $Return;
                    }
                }

                if ($Player['PlayerRole'] == "Bowler") {
                    if (@$PlayerRoles['Bowler'] < $DraftPlayerSelectionCriteria['Bowl']) {
                        /** check total player in squad * */
                        if ($Rows < $DraftTeamPlayerLimit) {
                            /** insert user team player squad * */
                            $InsertData = array(
                                "UserTeamID" => $UserTeamID,
                                "PlayerPosition" => "Player",
                                "PlayerID" => $Player['PlayerID'],
                                "SeriesID" => $SeriesID,
                                "RoundID" => $RoundID,
                                "MatchID" => $MatchID,
                                "DateTime" => date('Y-m-d H:i:s'),
                            );
                            $this->db->insert('sports_users_team_players', $InsertData);
                            /* Add contest to contest table . */
                            $UpdateData = array_filter(array(
                                "PlayerStatus" => "Sold",
                                "DateTime" => date('Y-m-d H:i:s')
                            ));
//                            $this->db->where('SeriesID', $SeriesID);
//                            $this->db->where('RoundID', $RoundID);
                            $this->db->where('ContestID', $ContestID);
                            $this->db->where('PlayerID', $Player['PlayerID']);
                            $this->db->limit(1);
                            $this->db->update('tbl_auction_player_bid_status', $UpdateData);
                            $Return["Status"] = 1;
                            $Return["Player"] = $Player;
                            return $Return;
                        } else {
                            $Return["Status"] = 0;
                            $Return['Message'] = "Team Players length can't exceed $DraftTeamPlayerLimit";
                            return $Return;
                        }
                    } else {
                        $Return["Status"] = 0;
                        $Return['Message'] = "Minimum Criteria for Bowler is fulfilled. Please select player for another position will you complete the minimum criteria of 11 Players";
                        return $Return;
                    }
                }

                if ($Player['PlayerRole'] == "AllRounder") {
                    if (@$PlayerRoles['AllRounder'] < $DraftPlayerSelectionCriteria['Ar']) {
                        /** check total player in squad * */
                        if ($Rows < $DraftTeamPlayerLimit) {
                            /** insert user team player squad * */
                            $InsertData = array(
                                "UserTeamID" => $UserTeamID,
                                "PlayerPosition" => "Player",
                                "PlayerID" => $Player['PlayerID'],
                                "SeriesID" => $SeriesID,
                                "RoundID" => $RoundID,
                                "MatchID" => $MatchID,
                                "DateTime" => date('Y-m-d H:i:s'),
                            );
                            $this->db->insert('sports_users_team_players', $InsertData);
                            /* Add contest to contest table . */
                            $UpdateData = array_filter(array(
                                "PlayerStatus" => "Sold",
                                "DateTime" => date('Y-m-d H:i:s')
                            ));
//                            $this->db->where('SeriesID', $SeriesID);
//                            $this->db->where('RoundID', $RoundID);
                            $this->db->where('ContestID', $ContestID);
                            $this->db->where('PlayerID', $Player['PlayerID']);
                            $this->db->limit(1);
                            $this->db->update('tbl_auction_player_bid_status', $UpdateData);
                            $Return["Status"] = 1;
                            $Return["Player"] = $Player;
                            return $Return;
                        } else {
                            $Return["Status"] = 0;
                            $Return['Message'] = "Team Players length can't exceed $DraftTeamPlayerLimit";
                            return $Return;
                        }
                    } else {
                        $Return["Status"] = 0;
                        $Return['Message'] = "Minimum Criteria for AllRounder is fulfilled. Please select player for another position will you complete the minimum criteria of 11 Players";
                        return $Return;
                    }
                }
            } else {
                if ($Rows < $DraftTeamPlayerLimit) {
                    /** insert user team player squad * */
                    $InsertData = array(
                        "UserTeamID" => $UserTeamID,
                        "PlayerPosition" => "Player",
                        "PlayerID" => $Player['PlayerID'],
                        "SeriesID" => $SeriesID,
                        "RoundID" => $RoundID,
                        "MatchID" => $MatchID,
                        "DateTime" => date('Y-m-d H:i:s'),
                    );
                    $this->db->insert('sports_users_team_players', $InsertData);
                    /* Add contest to contest table . */
                    $UpdateData = array_filter(array(
                        "PlayerStatus" => "Sold",
                        "DateTime" => date('Y-m-d H:i:s')
                    ));
//                    $this->db->where('SeriesID', $SeriesID);
//                    $this->db->where('RoundID', $RoundID);
                    $this->db->where('ContestID', $ContestID);
                    $this->db->where('PlayerID', $Player['PlayerID']);
                    $this->db->limit(1);
                    $this->db->update('tbl_auction_player_bid_status', $UpdateData);
                    $Return["Status"] = 1;
                    $Return["Player"] = $Player;
                    return $Return;
                } else {
                    $Return["Status"] = 0;
                    $Return['Message'] = "Team Players length can't exceed $DraftTeamPlayerLimit";
                    return $Return;
                }
            }
        } else {
            /** insert user team player squad * */
            $InsertData = array(
                "UserTeamID" => $UserTeamID,
                "PlayerPosition" => "Player",
                "PlayerID" => $Player['PlayerID'],
                "SeriesID" => $SeriesID,
                "RoundID" => $RoundID,
                "MatchID" => $MatchID,
                "DateTime" => date('Y-m-d H:i:s'),
            );
            $this->db->insert('sports_users_team_players', $InsertData);

            /** update player status * */
            /* Add contest to contest table . */
            $UpdateData = array_filter(array(
                "PlayerStatus" => "Sold",
                "DateTime" => date('Y-m-d H:i:s')
            ));
//            $this->db->where('SeriesID', $SeriesID);
//            $this->db->where('RoundID', $RoundID);
            $this->db->where('ContestID', $ContestID);
            $this->db->where('PlayerID', $Player['PlayerID']);
            $this->db->limit(1);
            $this->db->update('tbl_auction_player_bid_status', $UpdateData);
            $Return["Status"] = 1;
            $Return["Player"] = $Player;
            return $Return;
        }
    }

    /*
      Description: draft player sold.
     */

    function draftPlayerSold($Input = array(), $SeriesID, $ContestID, $UserID, $PlayerID = "", $RoundID, $MatchID) {
        $Return = array();
        $Return["Data"]["Status"] = 0;
        $Return['Message'] = "Draft player error";
        $Return["Data"]['Player'] = array();
        $Return["Data"]['User'] = array();
        $Return["Data"]['DraftStatus'] = "Running";

        /** check auction completed * */
        $DraftGames = $this->getContests('ContestID,SeriesID,SeriesGUID,DraftTotalRounds,TotalJoined,DraftLiveRound,RoundID,MatchID,MatchGUID', array('AuctionStatusID' => 2, 'LeagueType' => "Draft", "ContestID" => $ContestID, "MatchID" => $MatchID), TRUE, 1);

        /** check player in live * */
        $this->db->select("ContestID,DraftUserLiveTime");
        $this->db->from('sports_contest_join');
        $this->db->where("DraftUserLive", "Yes");
        $this->db->where("UserID", $UserID);
        $this->db->where("ContestID", $ContestID);
//        $this->db->where("SeriesID", $SeriesID);
//        $this->db->where("RoundID", $RoundID);
        $this->db->limit(1);
        $Query = $this->db->get();
        //print_r($Query);die;
        if ($Query->num_rows() > 0) {
            $DraftUserDetails = $Query->row_array();
            /** check is assistant and unsold player * */
            $this->db->select("Username as FirstName,UserGUID,UserID");
            $this->db->from('tbl_users');
            $this->db->where("UserID", $UserID);
            $this->db->limit(1);
            $Query = $this->db->get();
            $UserDetails = $Query->row_array();
            /** check player id empty * */
            if (empty($PlayerID)) {

                $DraftUserLiveTime = $DraftUserDetails['DraftUserLiveTime'];
                $CurrentDateTime = new DateTime($CurrentDateTime);
                $AuctionBreakDateTime = new DateTime($DraftUserLiveTime);
                $diffSeconds = $CurrentDateTime->getTimestamp() - $AuctionBreakDateTime->getTimestamp();
                if ($diffSeconds >= 119) {
                    /** check is assistant and unsold player * */
                    $this->db->select("BS.PlayerRole,UTP.PlayerID,UTP.BidCredit,UT.UserTeamID,UT.UserID,UTP.AuctionDraftAssistantPriority,BS.PlayerStatus,SP.PlayerName");
                    $this->db->from('sports_users_teams UT, sports_users_team_players UTP,tbl_auction_player_bid_status BS,sports_players SP');
                    $this->db->where("UT.UserTeamID", "UTP.UserTeamID", FALSE);
                    $this->db->where("UT.ContestID", "BS.ContestID", FALSE);
                    $this->db->where("UT.MatchID", "BS.MatchID", FALSE);
                    $this->db->where("UTP.PlayerID", "BS.PlayerID", FALSE);
                    $this->db->where("BS.PlayerID", "SP.PlayerID", FALSE);
                    $this->db->where("UT.IsAssistant", "Yes");
                    $this->db->where("UT.IsPreTeam", "Yes");
                    $this->db->where("UT.UserTeamType", "Draft");
                    //$this->db->where("BS.PlayerStatus", "Upcoming");
                    $this->db->where_in("BS.PlayerStatus", array("Live", "Upcoming"));
                    $this->db->where("UT.ContestID", $ContestID);
//                    $this->db->where("UT.SeriesID", $SeriesID);
//                    $this->db->where("UT.RoundID", $RoundID);
                    $this->db->where("UT.UserID", $UserID);
                    $this->db->order_by("UTP.AuctionDraftAssistantPriority", "ASC");
                    $this->db->limit(1);
                    $Query = $this->db->get();
                    if ($Query->num_rows() > 0) {
                        $AssistantPlayers = $Query->result_array();
                        foreach ($AssistantPlayers as $Player) {
                            /** user team and squad create * */
                            $UserTeamID = $this->addDraftUserTeam($UserID, $ContestID, $SeriesID, $RoundID, $MatchID);
                            if ($UserTeamID) {
                                $Status = $this->addDraftUserTeamSquadAssistant($UserTeamID, $UserID, $ContestID, $SeriesID, $Player, $RoundID, $MatchID);
                                if (empty($Status)) {
                                    /** check is assistant and unsold player * */
                                    $this->db->select("BS.PlayerRole,BS.PlayerStatus,BS.PlayerID,SP.PlayerName");
                                    $this->db->from('tbl_auction_player_bid_status BS,sports_players SP');
                                    $this->db->where("BS.PlayerID", "SP.PlayerID", FALSE);
                                    // $this->db->where("BS.PlayerStatus", "Upcoming");
                                    $this->db->where_in("BS.PlayerStatus", array("Live", "Upcoming"));
                                    $this->db->where("BS.ContestID", $ContestID);
//                                    $this->db->where("BS.SeriesID", $SeriesID);
//                                    $this->db->where("BS.RoundID", $RoundID);
                                    $this->db->limit(1);
                                    $Query = $this->db->get();
                                    if ($Query->num_rows() > 0) {
                                        $AllPlayer = $Query->result_array();
                                        foreach ($AllPlayer as $Player) {
                                            /** user team and squad create * */
                                            $UserTeamID = $this->addDraftUserTeam($UserID, $ContestID, $SeriesID, $RoundID, $MatchID);
                                            if ($UserTeamID) {
                                                $Status = $this->addDraftUserTeamSquadNotAssistant($UserTeamID, $UserID, $ContestID, $SeriesID, $Player, $DraftGames, $RoundID, $MatchID);
                                                if ($Status['Status'] == 1) {
                                                    $Return["Data"]["Status"] = 1;
                                                    $Return['Message'] = "Successfully player added";
                                                } else {
                                                    $Return['Message'] = "Team Players length can't greater than 11";
                                                }
                                                $Return["Data"]['Player'] = $Status['Player'];
                                                $Return["Data"]['User'] = $UserDetails;
                                            }
                                        }
                                    }
                                } else {
                                    if ($Status['Status'] == 1) {
                                        $Return["Data"]["Status"] = 1;
                                        $Return['Message'] = "Successfully player added";
                                    } else {
                                        $Return['Message'] = "Team Players length can't greater than 11";
                                    }
                                    $Return["Data"]['Player'] = $Status['Player'];
                                    $Return["Data"]['User'] = $UserDetails;
                                }
                            }
                        }
                    } else {
                        /** check is assistant and unsold player * */
                        $this->db->select("BS.PlayerRole,BS.PlayerStatus,BS.PlayerID,SP.PlayerName");
                        $this->db->from('tbl_auction_player_bid_status BS,sports_players SP');
                        $this->db->where("BS.PlayerID", "SP.PlayerID", FALSE);
                        // $this->db->where("BS.PlayerStatus", "Upcoming");
                        $this->db->where_in("BS.PlayerStatus", array("Live", "Upcoming"));
                        $this->db->where("BS.ContestID", $ContestID);
//                        $this->db->where("BS.SeriesID", $SeriesID);
//                        $this->db->where("BS.RoundID", $RoundID);
                        $this->db->limit(1);
                        $Query = $this->db->get();
                        if ($Query->num_rows() > 0) {
                            $AllPlayer = $Query->result_array();
                            //print_r($AllPlayer);exit;
                            foreach ($AllPlayer as $Player) {
                                /** user team and squad create * */
                                $UserTeamID = $this->addDraftUserTeam($UserID, $ContestID, $SeriesID, $RoundID, $MatchID);
                                if ($UserTeamID) {
                                    $Status = $this->addDraftUserTeamSquadNotAssistant($UserTeamID, $UserID, $ContestID, $SeriesID, $Player, $DraftGames, $RoundID, $MatchID);

                                    if ($Status['Status'] == 1) {
                                        $Return["Data"]["Status"] = 1;
                                        $Return['Message'] = "Successfully player added";
                                    } else {
                                        $Return['Message'] = "Team Players length can't greater than 11";
                                    }
                                    $Return["Data"]['Player'] = $Status['Player'];
                                    $Return["Data"]['User'] = $UserDetails;
                                }
                            }
                        }
                    }
                }
            } else {
                /** check is assistant and unsold player * */
                $this->db->select("BS.PlayerRole,BS.PlayerStatus,BS.PlayerID,SP.PlayerName");
                $this->db->from('tbl_auction_player_bid_status BS,sports_players SP');
                $this->db->where("BS.PlayerID", "SP.PlayerID", FALSE);
                $this->db->where_in("BS.PlayerStatus", array("Live", "Upcoming"));
                $this->db->where("BS.ContestID", $ContestID);
//                $this->db->where("BS.SeriesID", $SeriesID);
//                $this->db->where("BS.RoundID", $RoundID);
                $this->db->where("BS.PlayerID", $PlayerID);
                $this->db->limit(1);
                $Query = $this->db->get();
                if ($Query->num_rows() > 0) {
                    $AllPlayer = $Query->result_array();
                    foreach ($AllPlayer as $Player) {
                        /** user team and squad create * */
                        $UserTeamID = $this->addDraftUserTeam($UserID, $ContestID, $SeriesID, $RoundID, $MatchID);
                        if ($UserTeamID) {
                            $IsPlayer = $this->db->query('SELECT PlayerID from `sports_users_team_players` WHERE UserTeamID = "' . $UserTeamID . '" AND PlayerID="' . $PlayerID . '" ')->row()->PlayerID;
                            if (empty($IsPlayer)) {
                                $Status = $this->addDraftUserTeamSquadIsPlayer($UserTeamID, $UserID, $ContestID, $SeriesID, $Player, $DraftGames, $RoundID, $MatchID);
                                if ($Status['Status'] == 1) {
                                    $Return["Data"]["Status"] = 1;
                                    $Return['Message'] = "Successfully player added";
                                } else {
                                    $Return['Message'] = "Team Players length can't greater than 11";
                                    $Return['Message'] = $Status['Message'];
                                }
                            } else {
                                $Return['Message'] = "Player already sold in same user team";
                            }
                            $Return["Data"]['Player'] = $Status['Player'];
                            $Return["Data"]['User'] = $UserDetails;
                        }
                    }
                } else {
                    $Return['Message'] = "Draft player already sold";
                }
            }

            if ($DraftGames['Data']['TotalRecords'] > 0) {
                $Users = array();
                foreach ($DraftGames['Data']['Records'] as $Key => $Draft) {
                    if ($Draft['DraftLiveRound'] >= $Draft['DraftTotalRounds']) {
                        if (($Draft['DraftLiveRound'] % 2) == 0) {
                            /** check last player in live * */
                            $this->db->select("J.ContestID,J.UserID");
                            $this->db->from('sports_contest_join J, tbl_users U');
                            $this->db->where("J.DraftUserLive", "Yes");
                            $this->db->where("U.UserID", "J.UserID", FALSE);
                            $this->db->where("J.ContestID", $ContestID);
//                            $this->db->where("J.SeriesID", $SeriesID);
//                            $this->db->where("J.RoundID", $RoundID);
                            $this->db->where("J.UserID", $UserID);
                            $this->db->where("J.DraftUserPosition", 1);
                            $Query = $this->db->get();
                            if ($Query->num_rows() > 0) {
                                $Return["Data"]['DraftStatus'] = "Completed";
                                /* draft complete . */
                                $UpdateData = array_filter(array(
                                    "AuctionStatusID" => 5,
                                    "AuctionUpdateTime" => date('Y-m-d H:i:s')
                                ));
//                                $this->db->where('SeriesID', $SeriesID);
//                                $this->db->where('RoundID', $RoundID);
                                $this->db->where('ContestID', $ContestID);
                                $this->db->limit(1);
                                $this->db->update('sports_contest', $UpdateData);
                            }
                        } else {
                            /** check last player in live * */
                            $this->db->select("J.ContestID,J.UserID");
                            $this->db->from('sports_contest_join J, tbl_users U');
                            $this->db->where("J.DraftUserLive", "Yes");
                            $this->db->where("U.UserID", "J.UserID", FALSE);
                            $this->db->where("J.ContestID", $ContestID);
//                            $this->db->where("J.SeriesID", $SeriesID);
//                            $this->db->where("J.RoundID", $RoundID);
                            $this->db->where("J.UserID", $UserID);
                            $this->db->where("J.DraftUserPosition", $Draft['TotalJoined']);
                            $Query = $this->db->get();
                            if ($Query->num_rows() > 0) {
                                $Return["Data"]['DraftStatus'] = "Completed";
                                /* draft complete . */
                                $UpdateData = array_filter(array(
                                    "AuctionStatusID" => 5,
                                    "AuctionUpdateTime" => date('Y-m-d H:i:s')
                                ));
//                                $this->db->where('SeriesID', $SeriesID);
//                                $this->db->where('RoundID', $RoundID);
                                $this->db->where('ContestID', $ContestID);
                                $this->db->limit(1);
                                $this->db->update('sports_contest', $UpdateData);
                            }
                        }
                    }
                }
            }
        } else {
            $Return['Message'] = "User not in live";
        }

        return $Return;
    }

    /*
      Description: get draft rounds.
     */

    function getRounds($RoundID, $ContestID, $ContestDetails) {

        $Return = array();
        $Rounds = array();
        /** to check total player * */
        $DraftTeamPlayerLimit = (!empty($ContestDetails['DraftTeamPlayerLimit'])) ? $ContestDetails['DraftTeamPlayerLimit'] : 0;

        /** get total joined draft users * */
        $JoinedUsers = $this->getJoinedContestsUsers("Username,UserID,DraftUserPosition,ProfilePic,AuctionUserStatus,DraftUserLive", array('ContestID' => $ContestID, 'RoundID' => $RoundID, "OrderBy" => "DraftUserPosition", "Sequence" => "ASC"), TRUE);
        if (!empty($JoinedUsers)) {
            $TotalRecords = $JoinedUsers['Data']['TotalRecords'];
            if ($JoinedUsers['Data']['TotalRecords'] > 0) {
                for ($i = 1; $i <= $DraftTeamPlayerLimit; $i++) {
                    $Users = array();
                    foreach ($JoinedUsers['Data']['Records'] as $Rows) {
                        $Temp['DraftUserPosition'] = $Rows["DraftUserPosition"];
                        $Temp['UserGUID'] = $Rows["UserGUID"];
                        $Temp['FirstName'] = ucwords($Rows["Username"]);
                        $Temp['UserID'] = $Rows["UserID"];
                        $Temp['UserGUID'] = $Rows["UserGUID"];
                        $Temp['ProfilePic'] = $Rows["ProfilePic"];
                        $Temp['AuctionUserStatus'] = $Rows["AuctionUserStatus"];
                        $Temp['DraftUserLive'] = $Rows["DraftUserLive"];
                        $Users[] = $Temp;
                    }
                    if ($i % 2 == 0) {
                       // $Users = array_reverse($Users);
                    }
                    $Rounds[$i - 1]['Users'] = $Users;
                    $Rounds[$i - 1]['Round'] = $i;
                }
            }
        }
        return $Rounds;
    }

    function checkAuctionPlayerOnBidAndAuctionCompleted($SeriesID, $ContestID) {
        /** check upcoming player * */
        $this->db->select("PlayerID,BidCredit,PlayerStatus");
        $this->db->from("tbl_auction_player_bid_status");
        $this->db->where('SeriesID', $SeriesID);
        $this->db->where('ContestID', $ContestID);
        $this->db->where_in('PlayerStatus', array("Upcoming", "Live"));
        $this->db->limit(1);
        $Query = $this->db->get();
        if ($Query->num_rows() <= 0) {
            /** auction completed * */
            $UpdateData = array(
                "AuctionStatusID" => 5
            );
            $this->db->where('ContestID', $ContestID);
            $this->db->limit(1);
            $this->db->update('sports_contest', $UpdateData);
        }

        return;
    }

    function addUserTeamPlayerAfterSold($UserID, $SeriesID, $ContestID, $PlayerID, $BidCredit) {

        /** update player bid credit * */
        $UpdateData = array(
            "BidCredit" => $BidCredit,
        );
        $this->db->where('SeriesID', $SeriesID);
        $this->db->where('ContestID', $ContestID);
        $this->db->where('PlayerID', $PlayerID);
        $this->db->limit(1);
        $this->db->update('tbl_auction_player_bid_status', $UpdateData);


        $EntityGUID = get_guid();
        /* Add user team to entity table and get EntityID. */

        $UserBudget = $this->getJoinedContestsUsers("ContestID,UserID,AuctionBudget", array('ContestID' => $ContestID, 'SeriesID' => $SeriesID, 'UserID' => $UserID), FALSE);
        if (!empty($UserBudget)) {
            $this->db->trans_start();

            $UserContestBudget = $UserBudget['AuctionBudget'];
            $UserContestBudget = $UserContestBudget - $BidCredit;
            /* update contest user budget. */
            $UpdateData = array(
                "AuctionBudget" => $UserContestBudget,
            );
            $this->db->where('SeriesID', $SeriesID);
            $this->db->where('ContestID', $ContestID);
            $this->db->where('UserID', $UserID);
            $this->db->limit(1);
            $this->db->update('sports_contest_join', $UpdateData);

            $UserTeamID = $this->db->query('SELECT T.UserTeamID from `sports_users_teams` T join tbl_users U on U.UserID = T.UserID WHERE T.SeriesID = "' . $SeriesID . '" AND T.UserID = "' . $UserID . '" AND T.ContestID = "' . $ContestID . '" AND IsPreTeam = "No" AND IsAssistant="No" ')->row()->UserTeamID;
            if (empty($UserTeamID)) {
                $EntityID = $this->Entity_model->addEntity($EntityGUID, array("EntityTypeID" => 12, "UserID" => $UserID, "StatusID" => 2));
                /* Add user team to user team table . */
                $teamName = "PostAuctionTeam 1";
                $InsertData = array(
                    "UserTeamID" => $EntityID,
                    "UserTeamGUID" => $EntityGUID,
                    "UserID" => $UserID,
                    "UserTeamName" => $teamName,
                    "UserTeamType" => "Auction",
                    "IsPreTeam" => "No",
                    "SeriesID" => $SeriesID,
                    "ContestID" => $ContestID,
                    "IsAssistant" => "No",
                );
                $this->db->insert('sports_users_teams', $InsertData);
                /* Add User Team Players */
                if (!empty($PlayerID)) {

                    /* Manage User Team Players */
                    $UserTeamPlayers = array(
                        'UserTeamID' => $EntityID,
                        'SeriesID' => $SeriesID,
                        'PlayerID' => $PlayerID,
                        'PlayerPosition' => "Player",
                        'BidCredit' => $BidCredit
                    );
                    $this->db->insert('sports_users_team_players', $UserTeamPlayers);
                }
            } else {
                /* Add User Team Players */
                if (!empty($PlayerID)) {
                    /* Manage User Team Players */
                    $UserTeamPlayers = array(
                        'UserTeamID' => $UserTeamID,
                        'SeriesID' => $SeriesID,
                        'PlayerID' => $PlayerID,
                        'PlayerPosition' => "Player",
                        'BidCredit' => $BidCredit
                    );
                    $this->db->insert('sports_users_team_players', $UserTeamPlayers);
                }
            }
            $this->db->trans_complete();
            if ($this->db->trans_status() === FALSE) {
                return FALSE;
            }
        } else {
            return false;
        }
        return $EntityGUID;
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
                'MatchIDLive' => 'M.MatchIDLive',
                'MatchTypeID' => 'M.MatchTypeID',
                'MatchNo' => 'M.MatchNo',
                'MatchTypeByApi' => 'M.MatchTypeByApi',
                'MatchStartDateTime' => 'CONVERT_TZ(M.MatchStartDateTime,"+00:00","' . DEFAULT_TIMEZONE . '") AS MatchStartDateTime',
                'MatchStartDateTimeUTC' => 'M.MatchStartDateTime as MatchStartDateTimeUTC',
                'MatchScoreDetails' => 'M.MatchScoreDetails',
                'StatusID' => 'E.StatusID',
                'ContestID' => 'C.ContestID',
                'ContestGUID' => 'C.ContestGUID',
                'Privacy' => 'C.Privacy',
                'IsPaid' => 'C.IsPaid',
                'GameType' => 'C.GameType',
                'RoundID' => 'C.RoundID',
                'AuctionUpdateTime' => 'C.AuctionUpdateTime',
                'AuctionBreakDateTime' => 'C.AuctionBreakDateTime',
                'AuctionTimeBreakAvailable' => 'C.AuctionTimeBreakAvailable',
                'AuctionIsBreakTimeStatus' => 'C.AuctionIsBreakTimeStatus',
                'LeagueType' => 'IF(C.LeagueType = "Draft", "Snake Draft", "Auction Draft") as LeagueType',
                'LeagueJoinDateTime' => 'CONVERT_TZ(C.LeagueJoinDateTime,"+00:00","' . DEFAULT_TIMEZONE . '") AS LeagueJoinDateTime',
                'LeagueJoinDateTimeUTC' => 'C.LeagueJoinDateTime as LeagueJoinDateTimeUTC',
                'GameTimeLive' => 'C.GameTimeLive',
                'AdminPercent' => 'C.AdminPercent',
                'IsConfirm' => 'C.IsConfirm',
                'ShowJoinedContest' => 'C.ShowJoinedContest',
                'WinningAmount' => 'C.WinningAmount',
                'ContestSize' => 'C.ContestSize',
                'ContestFormat' => 'C.ContestFormat',
                'ContestType' => 'C.ContestType',
                'MatchID' => 'C.MatchID',
                'MatchGUID' => 'M.MatchGUID',
                'CustomizeWinning' => 'C.CustomizeWinning',
                'EntryFee' => 'C.EntryFee',
                'NoOfWinners' => 'C.NoOfWinners',
                'EntryType' => 'C.EntryType',
                'UserJoinLimit' => 'C.UserJoinLimit',
                'DraftTotalRounds' => 'C.DraftTotalRounds',
                'MinimumUserJoined' => 'C.MinimumUserJoined',
                'CashBonusContribution' => 'C.CashBonusContribution',
                'EntryType' => 'C.EntryType',
                'IsWinningDistributed' => 'C.IsWinningDistributed',
                'UserInvitationCode' => 'C.UserInvitationCode',
                'DraftLiveRound' => 'C.DraftLiveRound',
                'SeriesID' => 'C.SeriesID',
                'DraftUserLimit' => 'S.DraftUserLimit',
                'DraftTeamPlayerLimit' => 'C.DraftTeamPlayerLimit',
                'DraftPlayerSelectionCriteria' => 'C.DraftPlayerSelectionCriteria',
                'SeriesGUID' => 'S.SeriesGUID',
                'SeriesName' => 'S.SeriesName',
                'MatchType' => 'MT.MatchTypeName AS MatchType',
                'IsJoined' => '(SELECT IF( EXISTS(
                                SELECT EntryDate FROM sports_contest_join
                                WHERE sports_contest_join.ContestID =  C.ContestID AND UserID = ' . @$Where['SessionUserID'] . ' LIMIT 1), "Yes", "No")) AS IsJoined',
                'TotalJoined' => '(SELECT COUNT(0) FROM sports_contest_join
                                WHERE sports_contest_join.ContestID =  C.ContestID) AS TotalJoined',
                'StatusID' => 'E.StatusID',
                'AuctionStatusID' => 'C.AuctionStatusID',
                'AuctionStatus' => 'CASE C.AuctionStatusID
                             when "1" then "Pending"
                             when "2" then "Running"
                             when "5" then "Completed"
                             when "3" then "Cancelled"
                             END as AuctionStatus',
                'Status' => 'CASE E.StatusID
                             when "1" then "Pending"
                             when "2" then "Running"
                             when "3" then "Cancelled"
                             when "5" then "Completed"
                             END as Status'
            );
            if ($Params) {
                foreach ($Params as $Param) {
                    $Field .= (!empty($FieldArray[$Param]) ? ',' . $FieldArray[$Param] : '');
                }
            }
        }
        if (!empty($Where['TotalJoinedByRound']) && $Where['TotalJoinedByRound'] == 'Yes' && $Where['RoundID']) {
            $TotalJoinedByRound = $this->db->query("SELECT COUNT(0) as TotalJoinedByRound FROM sports_contest_join, sports_contest C WHERE sports_contest_join.ContestID =  C.ContestID  AND LeagueType = 'Draft' AND sports_contest_join.UserID = '" . @$Where['SessionUserID'] . "' AND sports_contest_join.MatchID = '" . @$Where['MatchID'] . "'");
            $TotalJoinedByRound = $TotalJoinedByRound->row()->TotalJoinedByRound;
            $Return['Data']['TotalJoinedByRound'] = $TotalJoinedByRound;
        }
        $this->db->select('C.ContestGUID,C.ContestName,S.SeriesID,C.RoundID');
        if (!empty($Field)) $this->db->select($Field, FALSE);
        $this->db->from('tbl_entity E, sports_contest C,sports_series S,sports_matches M,sports_set_match_types MT');
        $this->db->where("C.ContestID", "E.EntityID", FALSE);
        $this->db->where("S.SeriesID", "C.SeriesID", FALSE);
        $this->db->where("M.MatchID", "C.MatchID", FALSE);
        $this->db->where("MT.MatchTypeID", "M.MatchTypeID", FALSE);
        $this->db->where("C.LeagueType !=", 'Dfs');
        $this->db->where("C.LeagueType", 'Draft');
        if (!empty($Where['Keyword'])) {
            $Where['Keyword'] = $Where['Keyword'];
            $this->db->group_start();
            $this->db->like("C.ContestName", $Where['Keyword']);
            $this->db->or_like("S.SeriesName", $Where['Keyword']);
            $this->db->group_end();
        }
        if (!empty($Where['ContestID'])) {
            $this->db->where("C.ContestID", $Where['ContestID']);
        }
        if (!empty($Where['RoundID'])) {
            $this->db->where("C.RoundID", $Where['RoundID']);
        }
        if (!empty($Where['MatchID'])) {
            $this->db->where("C.MatchID", $Where['MatchID']);
        }
        if (!empty($Where['ContestType'])) {
            $this->db->where("C.ContestType", $Where['ContestType']);
        }
        if (!empty($Where['ContestGUID'])) {
            $this->db->where("C.ContestGUID", $Where['ContestGUID']);
        }
        if (!empty($Where['LeagueType'])) {
            $this->db->where("C.LeagueType", $Where['LeagueType']);
        }
        // if (!empty($Where['AuctionStatusID'])) {
        //     $this->db->where("C.AuctionStatusID", $Where['AuctionStatusID']);
        // }
        if (!empty($Where['UserID'])) {
            $this->db->where("C.UserID", $Where['UserID']);
        }
        if (!empty($Where['Filter']) && $Where['Filter'] == 'Today') {
            $this->db->where("DATE(M.MatchStartDateTime)", date('Y-m-d'));
        }

        if (!empty($Where['Filter']) && $Where['Filter'] == 'LiveAuction') {
            $CurrentDatetime = strtotime(date('Y-m-d H:i:s')) + 3600;
            $NextTime = date("Y-m-d H:i:s");
            $CurrentDatetime = strtotime(date('Y-m-d H:i:s')) - 3600;
            $PreTime = date("Y-m-d H:i:s", $CurrentDatetime);
            $this->db->where("C.LeagueJoinDateTime <=", $NextTime);
            //$this->db->where("C.LeagueJoinDateTime >=", $PreTime);
        }
        if (!empty($Where['Privacy']) && $Where['Privacy'] != 'All') {
            $this->db->where("C.Privacy", $Where['Privacy']);
        }
        if (!empty($Where['ContestType'])) {
            $this->db->where("C.ContestType", $Where['ContestType']);
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
        if (!empty($Where['AutionInLive']) && $Where['AutionInLive'] == "Yes") {
            $this->db->where("C.LeagueJoinDateTime <=", date('Y-m-d H:i:s'));
            $this->db->where("C.AuctionUpdateTime <=", date('Y-m-d H:i:s'));
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
        if (!empty($Where['SeriesID'])) {
            $this->db->where("C.SeriesID", $Where['SeriesID']);
        }
        if (!empty($Where['StatusID'])) {
            $this->db->where_in("E.StatusID", $Where['StatusID']);
        }

        if (!empty($Where['AuctionStatusID'])) {
            if(is_array($Where['AuctionStatusID'])){
                $this->db->where_in("C.AuctionStatusID", $Where['AuctionStatusID']); 
           }else{
                if (isset($Where['MyJoinedContest']) && $Where['MyJoinedContest'] = "Yes") {
                    if ($Where['AuctionStatusID'] == 1) {
                        $Where['AuctionStatusID'] = array(1,5,2);
                        $this->db->where_in("C.AuctionStatusID", $Where['AuctionStatusID']); 
                    }else if($Where['AuctionStatusID'] == 5){
                        $Where['AuctionStatusID'] = array(3,5);
                        $this->db->where_in("C.AuctionStatusID", $Where['AuctionStatusID']); 
                    }else if($Where['AuctionStatusID'] == 2){
                        $Where['AuctionStatusID'] = array(5);
                        $this->db->where_in("C.AuctionStatusID", $Where['AuctionStatusID']); 
                    }else{
                      $this->db->where("C.AuctionStatusID", $Where['AuctionStatusID']);   
                    }
                }else{
                  $this->db->where("C.AuctionStatusID", $Where['AuctionStatusID']);  
                }
           } 
        }



        // if (!empty($Where['AuctionStatusID'])) {
        //     $this->db->where_in("C.AuctionStatusID", $Where['AuctionStatusID']);
        // }

        if (isset($Where['MyJoinedContest']) && $Where['MyJoinedContest'] = "Yes") {
            $this->db->where('EXISTS (select ContestID from sports_contest_join JE where JE.ContestID = C.ContestID AND JE.UserID=' . @$Where['SessionUserID'] . ')');
             // if ($Where['StatusID'] == 1) {
             //    $Where['AuctionStatusID'] = array(1,2,5);
             //    $this->db->where_in("C.AuctionStatusID", $Where['AuctionStatusID']);
             // }else if($Where['StatusID'] == 2){
             //    $Where['AuctionStatusID'] = array(1,2,5);
             //    $this->db->where_in("C.AuctionStatusID", $Where['AuctionStatusID']); 
             // }  
        } else {
            if ($Where['StatusID'] != 3) {
                $this->db->where("E.StatusID !=", 3);
            }
        }
        if (!empty($Where['UserInvitationCode'])) {
            $this->db->where("C.UserInvitationCode", $Where['UserInvitationCode']);
        }
        if (!empty($Where['IsWinningDistributed'])) {
            $this->db->where("C.IsWinningDistributed", $Where['IsWinningDistributed']);
        }
        if (!empty($Where['ContestFull']) && $Where['ContestFull'] == 'No') {
            $this->db->having("TotalJoined !=", 'C.ContestSize', FALSE);
        }
        if (!empty($Where['OrderBy']) && !empty($Where['Sequence'])) {
            $this->db->order_by($Where['OrderBy'], $Where['Sequence']);
        }
        $this->db->order_by('EntryFee', 'ASC', FALSE);
        $this->db->order_by('C.LeagueJoinDateTime', 'DESC');

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
//        echo $this->db->last_query();
//        exit;
        if ($Query->num_rows() > 0) {
            if ($multiRecords) {
                $Records = array();
                $defaultCustomizeWinningObj = new stdClass();
                $defaultCustomizeWinningObj->From = 1;
                $defaultCustomizeWinningObj->To = 1;
                $defaultCustomizeWinningObj->Percent = 100;
                $defaultCustomizeWinningObj->WinningAmount = $Record['WinningAmount'];
                foreach ($Query->result_array() as $key => $Record) {

                    $Records[] = $Record;
                    $Records[$key]['CustomizeWinning'] = (!empty($Record['CustomizeWinning'])) ? json_decode($Record['CustomizeWinning'], TRUE) : array($defaultCustomizeWinningObj);
                    $Records[$key]['MatchScoreDetails'] = (!empty($Record['MatchScoreDetails'])) ? json_decode($Record['MatchScoreDetails'], TRUE) : new stdClass();
                    $TotalAmountReceived = $this->getTotalContestCollections($Record['ContestGUID']);
                    $Records[$key]['TotalAmountReceived'] = ($TotalAmountReceived) ? $TotalAmountReceived : 0;
                    $TotalWinningAmount = $this->getTotalWinningAmount($Record['ContestGUID']);
                    $Records[$key]['TotalWinningAmount'] = ($TotalWinningAmount) ? $TotalWinningAmount : 0;
                    $Records[$key]['NoOfWinners'] = ($Record['NoOfWinners'] == 0 ) ? 1 : $Record['NoOfWinners'];
                    $Records[$key]['IsSeriesMatchStarted'] = "No";
                    if (in_array('DraftPlayerSelectionCriteria', $Params)) {
                        $Records[$key]['DraftPlayerSelectionCriteria'] = (!empty($Record['DraftPlayerSelectionCriteria'])) ? json_decode($Record['DraftPlayerSelectionCriteria'], TRUE) : array();
                    }
                    if (isset($Where['MyJoinedContest']) && $Where['MyJoinedContest'] == "Yes") {
                        $Records[$key]['IsAuctionFinalTeamSubmitted'] = "No";
                        /** to check auction user final team submitted * */
                        $this->db->select("UserTeamID");
                        $this->db->from('sports_users_teams');
                        $this->db->where("ContestID", $Record['ContestID']);
                        $this->db->where("UserID", @$Where['SessionUserID']);
                        $this->db->where("IsPreTeam", "No");
                        $this->db->where("IsAssistant", "No");
                        $this->db->where("AuctionTopPlayerSubmitted", "Yes");
                        $this->db->where("UserTeamType", "Draft");
                        $this->db->limit(1);
                        $Query = $this->db->get();
                        if ($Query->num_rows() > 0) {
                            $Records[$key]['IsAuctionFinalTeamSubmitted'] = "Yes";
                        }

                        if (isset($Where['MyStats']) && $Where['MyStats'] = "Yes") {
                            $this->db->select("TotalPoints,UserRank,UserWinningAmount,AuctionBudget");
                            $this->db->from('sports_contest_join');
                            $this->db->where("ContestID", $Record['ContestID']);
                            $this->db->where("UserID", @$Where['SessionUserID']);
                            $this->db->limit(1);
                            $Query = $this->db->get();
                            if ($Query->num_rows() > 0) {
                                $JoinContest = $Query->row_array();
                                $Records[$key]['TotalPoints'] = $JoinContest['TotalPoints'];
                                $Records[$key]['UserRank'] = $JoinContest['UserRank'];
                                $Records[$key]['UserWinningAmount'] = $JoinContest['UserWinningAmount'];
                                $Records[$key]['AuctionBudget'] = 1000000000 - $JoinContest['AuctionBudget'];
                            }
                        }
                    }

                    /** to check series stared or not * */
                    if (isset($Where['IsSeriesStarted']) && $Where['IsSeriesStarted'] == "Yes") {
                        $this->db->select("MatchID,MatchStartDateTime");
                        $this->db->from('sports_matches');
                        $this->db->where("MatchID", $Record['MatchID']);
//                        $this->db->where("RoundID", $Where['RoundID']);
                        $this->db->order_by("MatchStartDateTime", "ASC");
                        $this->db->limit(1);
                        $Query = $this->db->get();
                        if ($Query->num_rows() > 0) {
                            $MatchDetails = $Query->row_array();
                            $CurrentDateTime = strtotime(date('Y-m-d H:i:s'));
                            $MatchDateTime = strtotime($MatchDetails["MatchStartDateTime"]);
                            if ($CurrentDateTime >= $MatchDateTime) {
                                $Records[$key]['IsSeriesMatchStarted'] = "Yes";
                            }
                        }
                    }
                    $Records[$key]['IsAssistanceCreated'] = "No";
                    if (isset($Where['IsAssistanceCreated']) && $Where['IsAssistanceCreated'] == "Yes") {
                        $this->db->select("UserTeamID");
                        $this->db->from('sports_users_teams');
                        $this->db->where("ContestID", $Record['ContestID']);
                        $this->db->where("UserTeamType", "Draft");
                        $this->db->where("IsPreTeam", "Yes");
                        $this->db->limit(1);
                        $Query = $this->db->get();
                        if ($Query->num_rows() > 0) {
                            $Records[$key]['IsAssistanceCreated'] = "Yes";
                        }
                    }
                }

                $Return['Data']['Records'] = $Records;
            } else {
                $Record = $Query->row_array();
                $Record['CustomizeWinning'] = (!empty($Record['CustomizeWinning'])) ? json_decode($Record['CustomizeWinning'], TRUE) : new stdClass();
                $Record['MatchScoreDetails'] = (!empty($Record['MatchScoreDetails'])) ? json_decode($Record['MatchScoreDetails'], TRUE) : new stdClass();
                $TotalAmountReceived = $this->getTotalContestCollections($Record['ContestGUID']);
                $Record['TotalAmountReceived'] = ($TotalAmountReceived) ? $TotalAmountReceived : 0;
                $TotalWinningAmount = $this->getTotalWinningAmount($Record['ContestGUID']);
                $Record['TotalWinningAmount'] = ($TotalWinningAmount) ? $TotalWinningAmount : 0;
                if (in_array('DraftPlayerSelectionCriteria', $Params)) {
                    $Record['DraftPlayerSelectionCriteria'] = (!empty($Record['DraftPlayerSelectionCriteria'])) ? json_decode($Record['DraftPlayerSelectionCriteria'], TRUE) : array();
                }
                /** to check series type * */
                if (isset($Where['DraftSeriesType']) && $Where['DraftSeriesType'] == "Yes") {
                    $this->db->select("RoundFormat");
                    $this->db->from('sports_series_rounds');
                    $this->db->where("RoundID", $Where['RoundID']);
                    $this->db->limit(1);
                    $Query = $this->db->get();
                    if ($Query->num_rows() > 0) {
                        $MatchDetails = $Query->row_array();
                        $Record['DraftSeriesType'] = strtolower($MatchDetails['RoundFormat']);
                    }
                }
                $Record['IsSeriesMatchStarted'] = "No";
                /** to check series stared or not * */
                if (isset($Where['IsSeriesStarted']) && $Where['IsSeriesStarted'] == "Yes") {
                    $this->db->select("MatchID,MatchStartDateTime");
                    $this->db->from('sports_matches');
                    $this->db->where("MatchID", $Record['MatchID']);
                    $this->db->order_by("MatchStartDateTime", "ASC");
                    $this->db->limit(1);
                    $Query = $this->db->get();
                    if ($Query->num_rows() > 0) {
                        $MatchDetails = $Query->row_array();
                        $CurrentDateTime = strtotime(date('Y-m-d H:i:s'));
                        $MatchDateTime = strtotime($MatchDetails["MatchStartDateTime"]);
                        if ($CurrentDateTime >= $MatchDateTime) {
                            $Record['IsSeriesMatchStarted'] = "Yes";
                        }
                    }
                }

                $Record['IsAssistanceCreated'] = "No";
                if (isset($Where['IsAssistanceCreated']) && $Where['IsAssistanceCreated'] == "Yes") {
                    $this->db->select("UserTeamID");
                    $this->db->from('sports_users_teams');
                    $this->db->where("ContestID", $Record['ContestID']);
                    $this->db->where("UserTeamType", "Draft");
                    $this->db->where("IsPreTeam", "Yes");
                    $this->db->limit(1);
                    $Query = $this->db->get();
                    if ($Query->num_rows() > 0) {
                        $Record['IsAssistanceCreated'] = "Yes";
                    }
                }


                return $Record;
            }
        }
        if (!empty($Where['MatchID'])) {
            $Return['Data']['Statics'] = $this->db->query('SELECT (SELECT COUNT(*) AS `NormalContest` FROM `sports_contest` C, `tbl_entity` E WHERE C.ContestID = E.EntityID AND E.StatusID IN (1,2,5) AND C.MatchID = "' . $Where['MatchID'] . '" AND C.ContestType="Normal" AND C.ContestFormat="League" AND C.ContestSize != (SELECT COUNT(*) from sports_contest_join where sports_contest_join.ContestID = C.ContestID)
                                    )as NormalContest,
                    ( SELECT COUNT(*) AS `ReverseContest` FROM `sports_contest` C, `tbl_entity` E WHERE C.ContestID = E.EntityID AND E.StatusID IN(1,2,5) AND C.MatchID = "' . $Where['MatchID'] . '" AND C.ContestType="Reverse" AND C.ContestFormat="League" AND C.ContestSize != (SELECT COUNT(*) from sports_contest_join where sports_contest_join.ContestID = C.ContestID)
                    )as ReverseContest,(
                    SELECT COUNT(*) AS `JoinedContest` FROM `sports_contest_join` J, `sports_contest` C,tbl_entity E WHERE C.ContestID = J.ContestID AND J.UserID = "' . @$Where['SessionUserID'] . '" AND C.MatchID = "' . $Where['MatchID'] . '" AND C.ContestID = E.EntityID AND E.StatusID != 3 
                    )as JoinedContest,( 
                    SELECT COUNT(*) AS `TotalTeams` FROM `sports_users_teams`WHERE UserID = "' . @$Where['SessionUserID'] . '" AND MatchID = "' . $Where['MatchID'] . '"
                ) as TotalTeams,(SELECT COUNT(*) AS `H2HContest` FROM `sports_contest` C, `tbl_entity` E WHERE C.ContestID = E.EntityID AND E.StatusID IN (1,2,5) AND C.MatchID = "' . $Where['MatchID'] . '" AND C.ContestFormat="Head to Head" AND E.StatusID = 1 AND C.ContestSize != (SELECT COUNT(*) from sports_contest_join where sports_contest_join.ContestID = C.ContestID )) as H2HContests')->row();
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

    function joinContest($Input = array(), $SessionUserID, $ContestID, $SeriesID, $RoundID, $UserTeamID, $MatchID) {

        $this->db->trans_start();
        /* Add entry to join contest table . */
        $DraftUserPosition = 0;
        $this->db->select("COUNT(UserID) as Joined");
        $this->db->from("sports_contest_join");
        $this->db->where("ContestID", $ContestID);
        $Query = $this->db->get();
        $Result = $Query->row_array();
        if (isset($Result['Joined'])) {
            $DraftUserPosition = $Result['Joined'] + 1;
        }
        $InsertData = array(
            "UserID" => $SessionUserID,
            "ContestID" => $ContestID,
            "SeriesID" => $SeriesID,
            "RoundID" => $RoundID,
            "MatchID" => $MatchID,
            "UserTeamID" => $UserTeamID,
            "DraftUserPosition" => $DraftUserPosition,
            "EntryDate" => date('Y-m-d H:i:s')
        );
        $this->db->insert('sports_contest_join', $InsertData);
        /* Manage User Wallet */
        if (@$Input['IsPaid'] == 'Yes') {
            $ContestEntryRemainingFees = @$Input['EntryFee'];
            $CashBonusContribution = @$Input['CashBonusContribution'];
            $WalletAmountDeduction = 0;
            $WinningAmountDeduction = 0;
            $CashBonusDeduction = 0;
            if (!empty($CashBonusContribution) && @$Input['CashBonus'] > 0) {
                $CashBonusContributionAmount = $ContestEntryRemainingFees * ($CashBonusContribution / 100);
                if (@$Input['CashBonus'] >= $CashBonusContributionAmount) {
                    $CashBonusDeduction = $CashBonusContributionAmount;
                } else {
                    $CashBonusDeduction = @$Input['CashBonus'];
                }
                $ContestEntryRemainingFees = $ContestEntryRemainingFees - $CashBonusDeduction;
            }
            if ($ContestEntryRemainingFees > 0 && @$Input['WalletAmount'] > 0) {
                if (@$Input['WalletAmount'] >= $ContestEntryRemainingFees) {
                    $WalletAmountDeduction = $ContestEntryRemainingFees;
                } else {
                    $WalletAmountDeduction = @$Input['WalletAmount'];
                }
                $ContestEntryRemainingFees = $ContestEntryRemainingFees - $WalletAmountDeduction;
            }
            if ($ContestEntryRemainingFees > 0 && @$Input['WinningAmount'] > 0) {
                if (@$Input['WinningAmount'] >= $ContestEntryRemainingFees) {
                    $WinningAmountDeduction = $ContestEntryRemainingFees;
                } else {
                    $WinningAmountDeduction = @$Input['WinningAmount'];
                }
                $ContestEntryRemainingFees = $ContestEntryRemainingFees - $WinningAmountDeduction;
            }
            $InsertData = array(
                "Amount" => @$Input['EntryFee'],
                "WalletAmount" => $WalletAmountDeduction,
                "WinningAmount" => $WinningAmountDeduction,
                "CashBonus" => $CashBonusDeduction,
                "TransactionType" => 'Dr',
                "EntityID" => $ContestID,
                "UserTeamID" => $UserTeamID,
                "Narration" => 'Join Contest',
                "EntryDate" => date("Y-m-d H:i:s")
            );
            $WalletID = $this->Users_model->addToWallet($InsertData, $SessionUserID, 5);
            if (!$WalletID) return FALSE;
        }

        $this->db->trans_complete();
        if ($this->db->trans_status() === FALSE) {
            return FALSE;
        }

        $ContestsData = $this->getContests('ContestSize,ContestJoinedType,TotalJoined,IsAutoCreate,AdminPercent,GameTimeLive,', array('ContestID' => $ContestID));

        if (($ContestsData['ContestSize'] - $ContestsData['TotalJoined']) == 0) {
            $this->autoCreateAuctionDraft($ContestID);
        }


        /* update contest round * */
        $this->autoShuffleRoundUpdate($ContestID);

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
                'ContestID' => 'C.ContestID',
                'Privacy' => 'C.Privacy',
                'IsPaid' => 'C.IsPaid',
                'IsConfirm' => 'C.IsConfirm',
                'ShowJoinedContest' => 'C.ShowJoinedContest',
                'CashBonusContribution' => 'C.CashBonusContribution',
                'UserInvitationCode' => 'C.UserInvitationCode',
                'WinningAmount' => 'C.WinningAmount',
                'ContestSize' => 'C.ContestSize',
                'UserTeamID' => 'JC.UserTeamID',
                'ContestFormat' => 'C.ContestFormat',
                'ContestType' => 'C.ContestType',
                'EntryFee' => 'C.EntryFee',
                'NoOfWinners' => 'C.NoOfWinners',
                'EntryType' => 'C.EntryType',
                'CustomizeWinning' => 'C.CustomizeWinning',
                'UserID' => 'JC.UserID',
                'JoinInning' => 'JC.JoinInning',
                'EntryDate' => 'JC.EntryDate',
                'TotalPoints' => 'JC.TotalPoints',
                'UserWinningAmount' => 'JC.UserWinningAmount',
                'SeriesID' => 'S.SeriesID',
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
                'CurrentDateTime' => 'DATE_FORMAT(CONVERT_TZ(Now(),"+00:00","' . DEFAULT_TIMEZONE . '"), "' . DATE_FORMAT . ' ") CurrentDateTime',
            );
            if ($Params) {
                foreach ($Params as $Param) {
                    $Field .= (!empty($FieldArray[$Param]) ? ',' . $FieldArray[$Param] : '');
                }
            }
        }

        $this->db->select('C.ContestGUID,C.ContestName');
        if (!empty($Field)) $this->db->select($Field, FALSE);
        $this->db->from('tbl_entity E, sports_contest C,sports_contest_join JC');
        $this->db->where("C.ContestID", "JC.ContestID", FALSE);
        $this->db->where("C.ContestID", "E.EntityID", FALSE);

        if (!empty($Where['Keyword'])) {
            $Where['Keyword'] = $Where['Keyword'];
            $this->db->group_start();
            $this->db->like("C.ContestName", $Where['Keyword']);
            $this->db->group_end();
        }
        if (!empty($Where['ContestID'])) {
            $this->db->where("C.ContestID", $Where['ContestID']);
        }
        if (!empty($Where['SeriesID'])) {
            $this->db->where("JC.SeriesID", $Where['SeriesID']);
        }
        if (!empty($Where['SessionUserID'])) {
            $this->db->where("JC.UserID", $Where['SessionUserID']);
        }
        if (!empty($Where['UserTeamID'])) {
            $this->db->where("JC.UserTeamID", $Where['UserTeamID']);
        }
        if (!empty($Where['Privacy'])) {
            $this->db->where("C.Privacy", $Where['Privacy']);
        }
        if (!empty($Where['IsPaid'])) {
            $this->db->where("C.IsPaid", $Where['IsPaid']);
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
        if (!empty($Where['StatusID'])) {
            $this->db->where("E.StatusID", $Where['StatusID']);
        }
        if (!empty($Where['OrderBy']) && !empty($Where['Sequence'])) {
            $this->db->order_by($Where['OrderBy'], $Where['Sequence']);
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
        // $this->db->group_by("UT.UserTeamID");
        $Query = $this->db->get();
        //echo $this->db->last_query();
        //exit;
        if ($Query->num_rows() > 0) {
            if ($multiRecords) {
                $Records = array();
                $Return['Data']['Records'] = $Query->result_array();
            } else {
                $Record = $Query->row_array();
                return $Record;
            }
        } else {
            $Return['Data']['Records'] = array();
        }

        return $Return;
    }

    /*
      Description: To get all players
     */

    function getPlayers($Field = '', $Where = array(), $multiRecords = FALSE, $PageNo = 1, $PageSize = 15) {
        $Params = array();
        if (!empty($Field)) {
            $Params = array_map('trim', explode(',', $Field));
            $Field = '';
            $FieldArray = array(
                'PlayerID' => 'P.PlayerID',
                'PlayerSalary' => 'P.PlayerSalary',
                'BidCredit' => 'UTP.BidCredit',
                'ContestID' => 'APBS.ContestID as ContestID',
                'SeriesID' => 'APBS.SeriesID as SeriesID',
                'BidSoldCredit' => '(SELECT BidCredit FROM tbl_auction_player_bid_status WHERE MatchID=' . $Where['MatchID'] . ' AND ContestID=' . $Where['ContestID'] . ' AND PlayerID=P.PlayerID) BidSoldCredit',
                'SeriesGUID' => 'S.SeriesGUID as SeriesGUID',
                'ContestGUID' => 'C.ContestGUID as ContestGUID',
                'BidDateTime' => 'APBS.DateTime as BidDateTime',
                'TimeDifference' => " IF(APBS.DateTime IS NULL,20,TIMEDIFF(UTC_TIMESTAMP,APBS.DateTime)) as TimeDifference",
                //'PlayerStatus' => '(SELECT PlayerStatus FROM tbl_auction_player_bid_status WHERE PlayerID=P.PlayerID AND SeriesID=' . @$Where['SeriesID'] . ' AND ContestID=' . @$Where['ContestID'] . ') as PlayerStatus',
                'PlayerStatus' => 'APBS.PlayerStatus as PlayerStatus',
                'PlayerRole' => 'APBS.PlayerRole as PlayerRole',
                'UserTeamGUID' => 'UT.UserTeamGUID',
                'UserID' => 'UT.UserID',
                'PlayerPosition' => 'UTP.PlayerPosition',
                'AuctionDraftAssistantPriority' => 'UTP.AuctionDraftAssistantPriority',
                'AuctionTopPlayerSubmitted' => 'UT.AuctionTopPlayerSubmitted',
                'IsAssistant' => 'UT.IsAssistant',
                'UserTeamName' => 'UT.UserTeamName',
                'PlayerIDLive' => 'P.PlayerIDLive',
                'PlayerPic' => 'IF(P.PlayerPic IS NULL,CONCAT("' . BASE_URL . '","uploads/PlayerPic/","player.png"),CONCAT("' . BASE_URL . '","uploads/PlayerPic/",P.PlayerPic)) AS PlayerPic',
                'PlayerCountry' => 'P.PlayerCountry',
                'PlayerBattingStyle' => 'P.PlayerBattingStyle',
                'PlayerBowlingStyle' => 'P.PlayerBowlingStyle',
                'PlayerBattingStats' => 'P.PlayerBattingStats',
                'PlayerBowlingStats' => 'P.PlayerBowlingStats',
                'LastUpdateDiff' => 'IF(P.LastUpdatedOn IS NULL, 0, TIME_TO_SEC(TIMEDIFF("' . date('Y-m-d H:i:s') . '", P.LastUpdatedOn))) LastUpdateDiff',
            );
            if ($Params) {
                foreach ($Params as $Param) {
                    $Field .= (!empty($FieldArray[$Param]) ? ',' . $FieldArray[$Param] : '');
                }
            }
        }
        $this->db->select('P.PlayerGUID,P.PlayerName');
        if (!empty($Field)) $this->db->select($Field, FALSE);
        $this->db->from('tbl_entity E, sports_players P');

        if (!empty($Where['PlayerBidStatus']) && $Where['PlayerBidStatus'] == "Yes") {
            $this->db->from('tbl_auction_player_bid_status APBS,sports_series S,sports_contest C');
            $this->db->where("APBS.PlayerID", "P.PlayerID", FALSE);
            $this->db->where("S.SeriesID", "APBS.SeriesID", FALSE);
            $this->db->where("C.ContestID", "APBS.ContestID", FALSE);
            if (!empty($Where['PlayerStatus'])) {
                $this->db->where("APBS.PlayerStatus", $Where['PlayerStatus']);
            }
            if (!empty($Where['ContestID'])) {
                $this->db->where("APBS.ContestID", $Where['ContestID']);
            }
            if (!empty($Where['RoundID'])) {
                $this->db->where("APBS.RoundID", $Where['RoundID']);
            }
        }

        if (!empty($Where['MySquadPlayer']) && $Where['MySquadPlayer'] == "Yes") {
            $this->db->select('UTP.Points TotalPoints');
            $this->db->from('sports_users_teams UT, sports_users_team_players UTP , sports_team_players SADP');
            $this->db->where("UTP.PlayerID", "P.PlayerID", FALSE);
            $this->db->where("UT.UserTeamID", "UTP.UserTeamID", FALSE);
            $this->db->where("SADP.MatchID", "UTP.MatchID", FALSE);
            $this->db->where("SADP.PlayerID", "UTP.PlayerID", FALSE);
            if (!empty($Where['SessionUserID'])) {
                $this->db->where("UT.UserID", @$Where['SessionUserID']);
            }
            if (!empty($Where['IsAssistant'])) {
                $this->db->where("UT.IsAssistant", @$Where['IsAssistant']);
            }
            if (!empty($Where['IsPreTeam'])) {
                $this->db->where("UT.IsPreTeam", @$Where['IsPreTeam']);
            }
            if (!empty($Where['UserID'])) {
                $this->db->where("UT.UserID", @$Where['UserID']);
            }
            if (!empty($Where['BidCredit'])) {
                $this->db->where("UTP.BidCredit >", @$Where['BidCredit']);
            }
            $this->db->where("UT.ContestID", @$Where['ContestID']);
        }

        $this->db->where("P.PlayerID", "E.EntityID", FALSE);
        if (!empty($Where['Keyword'])) {
            $Where['Keyword'] = trim($Where['Keyword']);
            $this->db->group_start();
            $this->db->like("P.PlayerName", $Where['Keyword']);
            $this->db->or_like("P.PlayerRole", $Where['Keyword']);
            $this->db->or_like("P.PlayerCountry", $Where['Keyword']);
            $this->db->or_like("P.PlayerBattingStyle", $Where['Keyword']);
            $this->db->or_like("P.PlayerBowlingStyle", $Where['Keyword']);
            $this->db->group_end();
        }
        $this->db->where('EXISTS (select PlayerID FROM sports_team_players WHERE PlayerID=P.PlayerID AND MatchID=' . @$Where['MatchID'] . ')');
        if (!empty($Where['TeamID'])) {
            $this->db->where("TP.TeamID", $Where['TeamID']);
        }
        if (!empty($Where['IsPlaying'])) {
            $this->db->where("TP.IsPlaying", $Where['IsPlaying']);
        }
        if (!empty($Where['PlayerID'])) {
            $this->db->where("P.PlayerID", $Where['PlayerID']);
        }
        if (!empty($Where['IsAdminSalaryUpdated'])) {
            $this->db->where("P.IsAdminSalaryUpdated", $Where['IsAdminSalaryUpdated']);
        }
        if (!empty($Where['StatusID'])) {
            $this->db->where("E.StatusID", $Where['StatusID']);
        }
        if (!empty($Where['CronFilter']) && $Where['CronFilter'] == 'OneDayDiff') {
            $this->db->having("LastUpdateDiff", 0);
            $this->db->or_having("LastUpdateDiff >=", 86400); // 1 Day
        }

        if (!empty($Where['OrderBy']) && !empty($Where['Sequence'])) {
            $this->db->order_by($Where['OrderBy'], $Where['Sequence']);
        }

        if (!empty($Where['RandData'])) {
            $this->db->order_by($Where['RandData']);
        } else {
            //$this->db->order_by('P.PlayerSalary', 'DESC');
            //$this->db->order_by('P.PlayerID', 'DESC');
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

        // $this->db->cache_on(); //Turn caching on
        $Query = $this->db->get();
        //echo $this->db->last_query();exit;
        if ($Query->num_rows() > 0) {
            if ($multiRecords) {
                $Records = array();
                foreach ($Query->result_array() as $key => $Record) {
                    $Records[] = $Record;
                    $IsAssistant = "";
                    $AuctionTopPlayerSubmitted = "No";
                    $UserTeamGUID = "";
                    $UserTeamName = "";
                    // $Records[$key]['PlayerSalary'] = $Record['PlayerSalary']*10000000;
                    $Records[$key]['PlayerBattingStats'] = (!empty($Record['PlayerBattingStats'])) ? json_decode($Record['PlayerBattingStats']) : new stdClass();
                    $Records[$key]['PlayerBowlingStats'] = (!empty($Record['PlayerBowlingStats'])) ? json_decode($Record['PlayerBowlingStats']) : new stdClass();
                    $Records[$key]['PointsData'] = (!empty($Record['PointsData'])) ? json_decode($Record['PointsData'], TRUE) : array();
                    //$Records[$key]['PlayerRole'] = "";
                    $IsAssistant = $Record['IsAssistant'];
                    $UserTeamGUID = $Record['UserTeamGUID'];
                    $UserTeamName = $Record['UserTeamName'];
                    $AuctionTopPlayerSubmitted = $Record['AuctionTopPlayerSubmitted'];
                    $this->db->select('TP.PlayerID,TP.PlayerRole,TP.PlayerSalary,T.TeamNameShort,T.TeamName');
                    $this->db->from('sports_team_players TP,sports_teams T');
                    $this->db->where('TP.TeamID', "T.TeamID", FALSE);
                    $this->db->where('TP.PlayerID', $Record['PlayerID']);
                    $this->db->where('TP.MatchID', @$Where['MatchID']);
                    $this->db->order_by("TP.PlayerSalary", 'DESC');
                    $this->db->limit(1);
                    $PlayerDetails = $this->db->get()->result_array();
                    if (!empty($PlayerDetails)) {
                        //$Records[$key]['PlayerRole'] = $PlayerDetails['0']['PlayerRole'];
                        $Records[$key]['TeamNameShort'] = $PlayerDetails['0']['TeamNameShort'];
                        $Records[$key]['TeamName'] = $PlayerDetails['0']['TeamName'];
                    }
                }
                if (!empty($Where['MySquadPlayer']) && $Where['MySquadPlayer'] == "Yes") {
                    $Return['Data']['IsAssistant'] = $IsAssistant;
                    $Return['Data']['UserTeamGUID'] = $UserTeamGUID;
                    $Return['Data']['UserTeamName'] = $UserTeamName;
                    $Return['Data']['AuctionTopPlayerSubmitted'] = $AuctionTopPlayerSubmitted;
                }
                $Return['Data']['Records'] = $Records;
                return $Return;
            } else {
                $Record = $Query->row_array();
                $Record['PlayerBattingStats'] = (!empty($Record['PlayerBattingStats'])) ? json_decode($Record['PlayerBattingStats']) : new stdClass();
                $Record['PlayerBowlingStats'] = (!empty($Record['PlayerBowlingStats'])) ? json_decode($Record['PlayerBowlingStats']) : new stdClass();
                $Record['PointsData'] = (!empty($Record['PointsData'])) ? json_decode($Record['PointsData'], TRUE) : array();
                //$Record['PlayerRole'] = "";
                $this->db->select('PlayerID,PlayerRole,PlayerSalary');
                $this->db->where('PlayerID', $Record['PlayerID']);
                $this->db->from('sports_team_players');
                $this->db->order_by("PlayerSalary", 'DESC');
                $this->db->limit(1);
                $PlayerDetails = $this->db->get()->result_array();
                if (!empty($PlayerDetails)) {
                    $Record['PlayerRole'] = $PlayerDetails['0']['PlayerRole'];
                }
                return $Record;
            }
        }
        return FALSE;
    }

    /*
      Description: To get all players auction
     */

    function getPlayersAuction($Field = '', $Where = array(), $multiRecords = FALSE, $PageNo = 1, $PageSize = 15) {
        $Params = array();
        if (!empty($Field)) {
            $Params = array_map('trim', explode(',', $Field));
            $Field = '';
            $FieldArray = array(
                'PlayerID' => 'P.PlayerID',
                'PlayerSalary' => 'P.PlayerSalary',
                'BidCredit' => 'UTP.BidCredit',
                'ContestID' => 'APBS.ContestID as ContestID',
                'SeriesID' => 'APBS.SeriesID as SeriesID',
                'BidSoldCredit' => '(SELECT BidCredit FROM tbl_auction_player_bid_status WHERE SeriesID=' . $Where['SeriesID'] . ' AND ContestID=' . $Where['ContestID'] . ' AND PlayerID=P.PlayerID) BidSoldCredit',
                'SeriesGUID' => 'S.SeriesGUID as SeriesGUID',
                'ContestGUID' => 'C.ContestGUID as ContestGUID',
                'BidDateTime' => 'APBS.DateTime as BidDateTime',
                'TimeDifference' => " IF(APBS.DateTime IS NULL,20,TIMEDIFF(UTC_TIMESTAMP,APBS.DateTime)) as TimeDifference",
                'PlayerStatus' => '(SELECT PlayerStatus FROM tbl_auction_player_bid_status WHERE PlayerID=P.PlayerID AND SeriesID=' . @$Where['SeriesID'] . ' AND ContestID=' . @$Where['ContestID'] . ') as PlayerStatus',
                'UserTeamGUID' => 'UT.UserTeamGUID',
                'UserID' => 'UT.UserID',
                'PlayerPosition' => 'UTP.PlayerPosition',
                'AuctionTopPlayerSubmitted' => 'UT.AuctionTopPlayerSubmitted',
                'IsAssistant' => 'UT.IsAssistant',
                'UserTeamName' => 'UT.UserTeamName',
                'PlayerIDLive' => 'P.PlayerIDLive',
                'PlayerPic' => 'IF(P.PlayerPic IS NULL,CONCAT("' . BASE_URL . '","uploads/PlayerPic/","player.png"),CONCAT("' . BASE_URL . '","uploads/PlayerPic/",P.PlayerPic)) AS PlayerPic',
                'PlayerCountry' => 'P.PlayerCountry',
                'PlayerBattingStyle' => 'P.PlayerBattingStyle',
                'PlayerBowlingStyle' => 'P.PlayerBowlingStyle',
                'PlayerBattingStats' => 'P.PlayerBattingStats',
                'PlayerBowlingStats' => 'P.PlayerBowlingStats',
                'LastUpdateDiff' => 'IF(P.LastUpdatedOn IS NULL, 0, TIME_TO_SEC(TIMEDIFF("' . date('Y-m-d H:i:s') . '", P.LastUpdatedOn))) LastUpdateDiff',
            );
            if ($Params) {
                foreach ($Params as $Param) {
                    $Field .= (!empty($FieldArray[$Param]) ? ',' . $FieldArray[$Param] : '');
                }
            }
        }
        $this->db->select('P.PlayerGUID,P.PlayerName');
        if (!empty($Field)) $this->db->select($Field, FALSE);
        $this->db->from('tbl_entity E, sports_players P,tbl_auction_player_bid_status ABS');

        $this->db->where("ABS.PlayerID", "P.PlayerID", FALSE);

        if (!empty($Where['PlayerBidStatus']) && $Where['PlayerBidStatus'] == "Yes") {
            $this->db->from('tbl_auction_player_bid_status APBS,sports_series S,sports_contest C');
            $this->db->where("APBS.PlayerID", "P.PlayerID", FALSE);
            $this->db->where("S.SeriesID", "APBS.SeriesID", FALSE);
            $this->db->where("C.ContestID", "APBS.ContestID", FALSE);
            if (!empty($Where['PlayerStatus'])) {
                $this->db->where("APBS.PlayerStatus", $Where['PlayerStatus']);
            }
            if (!empty($Where['ContestID'])) {
                $this->db->where("APBS.ContestID", $Where['ContestID']);
            }
        }

        if (!empty($Where['MySquadPlayer']) && $Where['MySquadPlayer'] == "Yes") {
            $this->db->from('sports_users_teams UT, sports_users_team_players UTP');
            $this->db->where("UTP.PlayerID", "P.PlayerID", FALSE);
            $this->db->where("UT.UserTeamID", "UTP.UserTeamID", FALSE);
            if (!empty($Where['SessionUserID'])) {
                $this->db->where("UT.UserID", @$Where['SessionUserID']);
            }
            if (!empty($Where['IsAssistant'])) {
                $this->db->where("UT.IsAssistant", @$Where['IsAssistant']);
            }
            if (!empty($Where['IsPreTeam'])) {
                $this->db->where("UT.IsPreTeam", @$Where['IsPreTeam']);
            }
            if (!empty($Where['BidCredit'])) {
                $this->db->where("UTP.BidCredit >", @$Where['BidCredit']);
            }
            $this->db->where("UT.ContestID", @$Where['ContestID']);
        }

        $this->db->where("P.PlayerID", "E.EntityID", FALSE);
        if (!empty($Where['Keyword'])) {
            $Where['Keyword'] = trim($Where['Keyword']);
            $this->db->group_start();
            $this->db->like("P.PlayerName", $Where['Keyword']);
            $this->db->or_like("P.PlayerRole", $Where['Keyword']);
            $this->db->or_like("P.PlayerCountry", $Where['Keyword']);
            $this->db->or_like("P.PlayerBattingStyle", $Where['Keyword']);
            $this->db->or_like("P.PlayerBowlingStyle", $Where['Keyword']);
            $this->db->group_end();
        }
        if (!empty($Where['TeamID'])) {
            $this->db->where("TP.TeamID", $Where['TeamID']);
        }
        if (!empty($Where['SeriesID'])) {
            $this->db->where("ABS.SeriesID", $Where['SeriesID']);
        }
        if (!empty($Where['ContestID'])) {
            $this->db->where("ABS.ContestID", $Where['ContestID']);
        }
        if (!empty($Where['IsPlaying'])) {
            $this->db->where("TP.IsPlaying", $Where['IsPlaying']);
        }
        if (!empty($Where['PlayerID'])) {
            $this->db->where("P.PlayerID", $Where['PlayerID']);
        }
        if (!empty($Where['IsAdminSalaryUpdated'])) {
            $this->db->where("P.IsAdminSalaryUpdated", $Where['IsAdminSalaryUpdated']);
        }
        if (!empty($Where['StatusID'])) {
            $this->db->where("E.StatusID", $Where['StatusID']);
        }
        if (!empty($Where['CronFilter']) && $Where['CronFilter'] == 'OneDayDiff') {
            $this->db->having("LastUpdateDiff", 0);
            $this->db->or_having("LastUpdateDiff >=", 86400); // 1 Day
        }

        if (!empty($Where['OrderBy']) && !empty($Where['Sequence'])) {
            $this->db->order_by($Where['OrderBy'], $Where['Sequence']);
        }

        if (!empty($Where['RandData'])) {
            $this->db->order_by($Where['RandData']);
        } else {
            //$this->db->order_by('P.PlayerSalary', 'DESC');
            $this->db->order_by('CreateDateTime', 'ASC');
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

        // $this->db->cache_on(); //Turn caching on
        $Query = $this->db->get();
        //echo $this->db->last_query();
        //exit;
        if ($Query->num_rows() > 0) {
            if ($multiRecords) {
                $Records = array();
                foreach ($Query->result_array() as $key => $Record) {
                    $Records[] = $Record;
                    $IsAssistant = "";
                    $AuctionTopPlayerSubmitted = "No";
                    $UserTeamGUID = "";
                    $UserTeamName = "";
                    // $Records[$key]['PlayerSalary'] = $Record['PlayerSalary']*10000000;
                    $Records[$key]['PlayerBattingStats'] = (!empty($Record['PlayerBattingStats'])) ? json_decode($Record['PlayerBattingStats']) : new stdClass();
                    $Records[$key]['PlayerBowlingStats'] = (!empty($Record['PlayerBowlingStats'])) ? json_decode($Record['PlayerBowlingStats']) : new stdClass();
                    $Records[$key]['PointsData'] = (!empty($Record['PointsData'])) ? json_decode($Record['PointsData'], TRUE) : array();
                    $Records[$key]['PlayerRole'] = "";
                    $IsAssistant = $Record['IsAssistant'];
                    $UserTeamGUID = $Record['UserTeamGUID'];
                    $UserTeamName = $Record['UserTeamName'];
                    $AuctionTopPlayerSubmitted = $Record['AuctionTopPlayerSubmitted'];
                    $this->db->select('PlayerID,PlayerRole,PlayerSalary');
                    $this->db->where('PlayerID', $Record['PlayerID']);
                    $this->db->from('sports_team_players');
                    $this->db->order_by("PlayerSalary", 'DESC');
                    $this->db->limit(1);
                    $PlayerDetails = $this->db->get()->result_array();
                    if (!empty($PlayerDetails)) {
                        $Records[$key]['PlayerRole'] = $PlayerDetails['0']['PlayerRole'];
                    }
                }
                if (!empty($Where['MySquadPlayer']) && $Where['MySquadPlayer'] == "Yes") {
                    $Return['Data']['IsAssistant'] = $IsAssistant;
                    $Return['Data']['UserTeamGUID'] = $UserTeamGUID;
                    $Return['Data']['UserTeamName'] = $UserTeamName;
                    $Return['Data']['AuctionTopPlayerSubmitted'] = $AuctionTopPlayerSubmitted;
                }
                $Return['Data']['Records'] = $Records;
                return $Return;
            } else {
                $Record = $Query->row_array();
                $Record['PlayerBattingStats'] = (!empty($Record['PlayerBattingStats'])) ? json_decode($Record['PlayerBattingStats']) : new stdClass();
                $Record['PlayerBowlingStats'] = (!empty($Record['PlayerBowlingStats'])) ? json_decode($Record['PlayerBowlingStats']) : new stdClass();
                $Record['PointsData'] = (!empty($Record['PointsData'])) ? json_decode($Record['PointsData'], TRUE) : array();
                $Record['PlayerRole'] = "";
                $this->db->select('PlayerID,PlayerRole,PlayerSalary');
                $this->db->where('PlayerID', $Record['PlayerID']);
                $this->db->from('sports_team_players');
                $this->db->order_by("PlayerSalary", 'DESC');
                $this->db->limit(1);
                $PlayerDetails = $this->db->get()->result_array();
                if (!empty($PlayerDetails)) {
                    $Record['PlayerRole'] = $PlayerDetails['0']['PlayerRole'];
                }
                return $Record;
            }
        }
        return FALSE;
    }

    /*
      Description: ADD user team
     */

    function addUserTeam($Input = array(), $SessionUserID, $SeriesID, $RoundID, $ContestID, $StatusID = 2, $MatchID = "") {

        $this->db->trans_start();
        $EntityGUID = get_guid();
        /* Add user team to entity table and get EntityID. */
        $EntityID = $this->Entity_model->addEntity($EntityGUID, array("EntityTypeID" => 12, "UserID" => $SessionUserID, "StatusID" => $StatusID));
        $UserTeamCount = $this->db->query('SELECT count(T.UserTeamID) as UserTeamsCount,U.Username from `sports_users_teams` T join tbl_users U on U.UserID = T.UserID WHERE T.SeriesID = "' . $SeriesID . '" AND T.UserID = "' . $SessionUserID . '" ')->row();
        /* Add user team to user team table . */
        $teamName = "PreSnakeTeam 1";
        $InsertData = array(
            "UserTeamID" => $EntityID,
            "UserTeamGUID" => $EntityGUID,
            "UserID" => $SessionUserID,
            "UserTeamName" => $teamName,
            "UserTeamType" => @$Input['UserTeamType'],
            "IsPreTeam" => @$Input['IsPreTeam'],
            "SeriesID" => @$SeriesID,
            "RoundID" => @$RoundID,
            "ContestID" => @$ContestID,
            "MatchID" => @$MatchID,
            "IsAssistant" => "No",
        );
        $this->db->insert('sports_users_teams', $InsertData);

        /* Add User Team Players */
        if (!empty($Input['UserTeamPlayers'])) {

            /* Get Players */
            $PlayersIdsData = array();
            $PlayersData = $this->getSeriesPlayers('PlayerID,MatchID,SeriesID', array('SeriesID' => $SeriesID, 'RoundID' => $RoundID), TRUE, 0);
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
                        'SeriesID' => @$SeriesID,
                        'RoundID' => @$RoundID,
                        'MatchID' => @$MatchID,
                        'PlayerID' => $PlayersIdsData[$Value['PlayerGUID']],
                        'PlayerPosition' => $Value['PlayerPosition'],
                        'AuctionDraftAssistantPriority' => $Value['AuctionDraftAssistantPriority'],
                        'DateTime' => date('Y-m-d H:i:s')
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
      Description: Assistant on off
     */

    function assistantTeamOnOff($Input = array(), $SessionUserID, $RoundID, $ContestID, $UserTeamID) {

        $this->db->trans_start();

        /* Update Contest Status */
        $this->db->where('UserTeamID', $UserTeamID);
        $this->db->where('UserID', $SessionUserID);
        //$this->db->where('RoundID', $RoundID);
        $this->db->where('ContestID', $ContestID);
        $this->db->where('IsPreTeam', "Yes");
        $this->db->limit(1);
        $this->db->update('sports_users_teams', array('IsAssistant' => @$Input['IsAssistant']));

        $this->db->trans_complete();
        if ($this->db->trans_status() === FALSE) {
            return FALSE;
        }

        return TRUE;
    }

    /*
      Description: add auction player bid
     */

    function get_max($Array, $Index) {
        $All = array();
        foreach ($Array as $key => $value) {
            /* creating array where the key is transaction_no and
              the value is the array containing this transaction_no */
            $All[$value['BidCredit']] = $value;
        }
        /* now sort the array by the key (transaction_no) */
        krsort($All);
        /* get the second array and return it (see the link below) */
        return array_slice($All, $Index, 1)[0];
    }

    function addAuctionPlayerBid($Input = array(), $SessionUserID, $SeriesID, $ContestID, $PlayerID) {
        $Return = array();
        /** to check user already in bid * */
        $this->db->select("PlayerID,UserID,DateTime");
        $this->db->from('tbl_auction_player_bid');
        $this->db->where("PlayerID", $PlayerID);
        $this->db->where("ContestID", $ContestID);
        $this->db->where("SeriesID", $SeriesID);
        $this->db->limit(1);
        $this->db->order_by("DateTime", "DESC");
        $this->db->order_by("BidCredit", "DESC");
        $Query = $this->db->get();
        if ($Query->num_rows() > 0) {
            $PlayerBid = $Query->result_array();
            if (!empty($PlayerBid)) {
                if ($SessionUserID == $PlayerBid[0]['UserID']) {
                    $Return["Message"] = "You are currently in bid please wait next bid";
                    $Return["Status"] = 0;
                    return $Return;
                }
            }
        }

        /** to check auction in live * */
        /* $AuctionGames = $this->getContests('ContestID,AuctionBreakDateTime,AuctionStatus,SeriesID,AuctionTimeBreakAvailable,AuctionIsBreakTimeStatus', array('AuctionStatusID' => 2, 'ContestID' => $ContestID), FALSE);
          if (empty($AuctionGames)) {
          $Return["Message"] = "Auction not stared.";
          $Return["Status"] = 0;
          return $Return;
          } */

        /** to check user available budget * */
        $this->db->select("AuctionBudget");
        $this->db->from('sports_contest_join');
        $this->db->where("AuctionBudget >=", $Input['BidCredit']);
        $this->db->where("ContestID", $ContestID);
        $this->db->where("SeriesID", $SeriesID);
        $this->db->where("UserID", $SessionUserID);
        $Query = $this->db->get();
        if ($Query->num_rows() > 0) {
            /** To check player in assistant * */
//            $BidUserID = "";
//            $BidUserCredit = "";
//            $this->db->select("UTP.PlayerID,UTP.BidCredit,UT.UserTeamID,UT.UserID");
//            $this->db->from('sports_users_teams UT, sports_users_team_players UTP');
//            $this->db->where("UT.UserTeamID", "UTP.UserTeamID", FALSE);
//            $this->db->where("UT.IsAssistant", "Yes");
//            $this->db->where("UT.IsPreTeam", "Yes");
//            $this->db->where("UTP.BidCredit >", $Input['BidCredit']);
//            $this->db->where("UT.ContestID", $ContestID);
//            $this->db->where("UT.SeriesID", $SeriesID);
//            $this->db->where("UTP.PlayerID", $PlayerID);
//            $Query = $this->db->get();
//            $PlayersAssistant = $Query->result_array();
//            $Rows = $Query->num_rows();
//            if ($Rows > 0) {
//                /** To check assistant player single * */
//                if ($Rows == 1) {
//
//                    $CurrentBidCredit = $Input['BidCredit'];
//                    $AssistantBidCredit = $PlayersAssistant[0]['BidCredit'];
//                    if ($AssistantBidCredit > $CurrentBidCredit) {
//                        if (100000 >= $CurrentBidCredit || $CurrentBidCredit < 1000000) {
//                            $CurrentBidCredit = $CurrentBidCredit + 100000;
//                        } else if (1000000 >= $CurrentBidCredit || $CurrentBidCredit < 10000000) {
//                            $CurrentBidCredit = $CurrentBidCredit + 1000000;
//                        } else if (10000000 >= $CurrentBidCredit || $CurrentBidCredit < 100000000) {
//                            $CurrentBidCredit = $CurrentBidCredit + 10000000;
//                        } else if (10000000 >= $CurrentBidCredit || $CurrentBidCredit < 1000000000) {
//                            $CurrentBidCredit = $CurrentBidCredit + 100000000;
//                        }
//                    }
//                    $BidUserID = $PlayersAssistant[0]['UserID'];
//                    $BidUserCredit = $CurrentBidCredit;
//
//                    /** to check user available budget * */
//                    $this->db->select("AuctionBudget");
//                    $this->db->from('sports_contest_join');
//                    $this->db->where("AuctionBudget >=", $CurrentBidCredit);
//                    $this->db->where("ContestID", $ContestID);
//                    $this->db->where("SeriesID", $SeriesID);
//                    $this->db->where("UserID", $PlayersAssistant[0]['UserID']);
//                    $Query = $this->db->get();
//                    if ($Query->num_rows() > 0) {
//                        /* add player bid */
//                        $InsertData = array(
//                            "SeriesID" => $SeriesID,
//                            "ContestID" => $ContestID,
//                            "UserID" => $PlayersAssistant[0]['UserID'],
//                            "PlayerID" => $PlayerID,
//                            "BidCredit" => $CurrentBidCredit,
//                            "DateTime" => date('Y-m-d H:i:s')
//                        );
//                        $this->db->insert('tbl_auction_player_bid', $InsertData);
//                    } else {
//                        $Return["Message"] = "You have not insufficient budget";
//                        $Return["Status"] = 0;
//                        return $Return;
//                    }
//                } else if ($Rows > 1) {
//                    /** get second highest user* */
//                    $SecondUser = $this->get_max($PlayersAssistant, 1);
//                    if (empty($SecondUser)) {
//                        $SecondUser = $PlayersAssistant[0];
//                    }
//                    $CurrentBidCredit = $AssistantBidCredit = $SecondUser['BidCredit'];
//                    if (100000 >= $AssistantBidCredit || $AssistantBidCredit < 1000000) {
//                        $CurrentBidCredit = $AssistantBidCredit + 100000;
//                    } else if (1000000 >= $AssistantBidCredit || $AssistantBidCredit < 10000000) {
//                        $CurrentBidCredit = $AssistantBidCredit + 1000000;
//                    } else if (10000000 >= $AssistantBidCredit || $AssistantBidCredit < 100000000) {
//                        $CurrentBidCredit = $AssistantBidCredit + 10000000;
//                    } else if (10000000 >= $AssistantBidCredit || $AssistantBidCredit < 1000000000) {
//                        $CurrentBidCredit = $AssistantBidCredit + 100000000;
//                    }
//                    /** get top user* */
//                    $TopUser = $this->get_max($PlayersAssistant, 0);
//                    $TopUserBidCredit = $TopUser['BidCredit'];
//                    if ($CurrentBidCredit > $TopUserBidCredit) {
//                        $CurrentBidCredit = $TopUserBidCredit;
//                    }
//                    $BidUserID = $TopUser['UserID'];
//                    $BidUserCredit = $CurrentBidCredit;
//
//                    /** to check user available budget * */
//                    $this->db->select("AuctionBudget");
//                    $this->db->from('sports_contest_join');
//                    $this->db->where("AuctionBudget >=", $CurrentBidCredit);
//                    $this->db->where("ContestID", $ContestID);
//                    $this->db->where("SeriesID", $SeriesID);
//                    $this->db->where("UserID", $TopUser['UserID']);
//                    $Query = $this->db->get();
//                    if ($Query->num_rows() > 0) {
//                        /* add player bid */
//                        $InsertData = array(
//                            "SeriesID" => $SeriesID,
//                            "ContestID" => $ContestID,
//                            "UserID" => $TopUser['UserID'],
//                            "PlayerID" => $PlayerID,
//                            "BidCredit" => $CurrentBidCredit,
//                            "DateTime" => date('Y-m-d H:i:s')
//                        );
//                        $this->db->insert('tbl_auction_player_bid', $InsertData);
//                    } else {
//                        $Return["Message"] = "You have not insufficient budget";
//                        $Return["Status"] = 0;
//                        return $Return;
//                    }
//                }
//            } else {
//                $BidUserID = $SessionUserID;
//                $BidUserCredit = $Input['BidCredit'];
//                /* add player bid */
//                $InsertData = array(
//                    "SeriesID" => $SeriesID,
//                    "ContestID" => $ContestID,
//                    "UserID" => $SessionUserID,
//                    "PlayerID" => $PlayerID,
//                    "BidCredit" => @$Input['BidCredit'],
//                    "DateTime" => date('Y-m-d H:i:s')
//                );
//                $this->db->insert('tbl_auction_player_bid', $InsertData);
//            }

            $BidUserID = $SessionUserID;
            $BidUserCredit = $Input['BidCredit'];
            /* add player bid */
            $InsertData = array(
                "SeriesID" => $SeriesID,
                "ContestID" => $ContestID,
                "UserID" => $SessionUserID,
                "PlayerID" => $PlayerID,
                "BidCredit" => @$Input['BidCredit'],
                "DateTime" => date('Y-m-d H:i:s')
            );
            $this->db->insert('tbl_auction_player_bid', $InsertData);

            if (!empty($BidUserID) && !empty($BidUserCredit)) {
                $UserData = $this->Users_model->getUsers("Email", array('UserID' => $BidUserID));
                $UserData['BidCredit'] = $BidUserCredit;
                $Return["Message"] = "You have not insufficient budget";
                $Return["Status"] = 1;
                $Return["Data"] = $UserData;
            }
        } else {
            $Return["Message"] = "You have not insufficient budget";
            $Return["Status"] = 0;
        }

        return $Return;
    }

    /*
      Description: get auction bid player time
     */

    function auctionBidTimeManagement($Input, $ContestID = "", $SeriesID = "") {
        $Players = array();
        $TempPlayer = array();
        /** get live auction * */
        $AuctionGames = $this->getContests('ContestID,AuctionBreakDateTime,AuctionStatus,SeriesID,AuctionTimeBreakAvailable,AuctionIsBreakTimeStatus', array('AuctionStatusID' => 2, 'ContestID' => $ContestID, 'SeriesID' => $SeriesID), TRUE, 1);
        if ($AuctionGames['Data']['TotalRecords'] > 0) {
            foreach ($AuctionGames['Data']['Records'] as $Auction) {
                $Players = array();
                /** get contest hold user time management * */
                $AuctionHoldDateTime = "";
                $this->db->select("ContestID,UserID,AuctionTimeBank,AuctionHoldDateTime");
                $this->db->from('sports_contest_join');
                $this->db->where("ContestID", $Auction['ContestID']);
                $this->db->where("SeriesID", $Auction['SeriesID']);
                $this->db->where("IsHold", "Yes");
                $Query = $this->db->get();
                $Rows = $Query->num_rows();
                $HoldUser = $Query->row_array();
                if (!empty($HoldUser)) {
                    $AuctionHoldDateTime = $HoldUser['AuctionHoldDateTime'];
                }
                /** get live player * */
                $PlayerInLive = $playersData = $this->getPlayers($Input['Params'], array_merge($Input, array('SeriesID' => $Auction['SeriesID'], 'ContestID' => $Auction['ContestID'], 'PlayerBidStatus' => 'Yes', 'PlayerStatus' => 'Live', 'OrderBy' => "PlayerID", "Sequence" => "ASC")));
                if (!empty($playersData)) {
                    $Players[] = $playersData;
                } else {
                    /** get upcoming player * */
                    $playersData = $this->getPlayers($Input['Params'], array_merge($Input, array('SeriesID' => $Auction['SeriesID'], 'ContestID' => $Auction['ContestID'], 'PlayerBidStatus' => 'Yes', 'PlayerStatus' => 'Upcoming', 'OrderBy' => "PlayerID", "Sequence" => "ASC")));
                    if (!empty($playersData)) {
                        $Players[] = $playersData;
                    }
                }
                if (!empty($Players)) {
                    foreach ($Players as $key => $Player) {
                        $Players[$key]['PreAssistant'] = "No";
                        if (empty($PlayerInLive)) {
                            $Players[$key]['AuctionTimeBreakAvailable'] = $Auction['AuctionTimeBreakAvailable'];
                        } else {
                            $Players[$key]['AuctionTimeBreakAvailable'] = "No";
                        }

                        $Players[$key]['AuctionIsBreakTimeStatus'] = $Auction['AuctionIsBreakTimeStatus'];
                        /** auction break date time to current date time difference * */
                        $Players[$key]['BreakTimeInSec'] = 0;
                        if ($Auction['AuctionIsBreakTimeStatus'] == "Yes" && $Auction['AuctionTimeBreakAvailable'] == "No") {
                            $AuctionBreakDateTime = $Auction['AuctionBreakDateTime'];
                            $CurrentDateTime = date('Y-m-d H:i:s');
                            $CurrentDateTime = new DateTime($CurrentDateTime);
                            $AuctionBreakDateTime = new DateTime($AuctionBreakDateTime);
                            $diffSeconds = $CurrentDateTime->getTimestamp() - $AuctionBreakDateTime->getTimestamp();
                            $Players[$key]['BreakTimeInSec'] = $diffSeconds;
                        }

                        /** to check player in already bid * */
                        $this->db->select("PlayerID,SeriesID,ContestID,BidCredit,DateTime,UserID");
                        $this->db->from('tbl_auction_player_bid');
                        $this->db->where("ContestID", $Player['ContestID']);
                        $this->db->where("SeriesID", $Player['SeriesID']);
                        $this->db->where("PlayerID", $Player['PlayerID']);
                        $this->db->order_by("DateTime", "DESC");
                        $this->db->limit(1);
                        $PlayerDetails = $this->db->get()->result_array();
                        $CurrentDateTime = date('Y-m-d H:i:s');
                        if (!empty($PlayerDetails)) {
                            $Players[$key]['IsSold'] = "UpcomingSold";
                            $DateTime = $PlayerDetails[0]['DateTime'];
                            /** get bid time difference in seconds * */
                            $Players[$key]['TimeDifference'] = strtotime($CurrentDateTime) - strtotime($DateTime);
                            if (!empty($AuctionHoldDateTime)) {
                                $Players[$key]['TimeDifference'] = strtotime($AuctionHoldDateTime) - strtotime($DateTime);
                            }

                            /** check current player in assistant * */
                            $this->db->select("UTP.PlayerID,UTP.BidCredit,UT.UserTeamID,UT.UserID,U.UserGUID,UTP.DateTime");
                            $this->db->from('sports_users_teams UT, sports_users_team_players UTP,tbl_users U');
                            $this->db->where("UT.UserTeamID", "UTP.UserTeamID", FALSE);
                            $this->db->where("U.UserID", "UT.UserID", FALSE);
                            $this->db->where("UT.IsAssistant", "Yes");
                            $this->db->where("UT.IsPreTeam", "Yes");
                            $this->db->where("UT.ContestID", $Player['ContestID']);
                            $this->db->where("UT.SeriesID", $Player['SeriesID']);
                            $this->db->where("UTP.PlayerID", $Player['PlayerID']);
                            $this->db->where("UTP.BidCredit >", $PlayerDetails[0]['BidCredit']);
                            $this->db->order_by("UTP.BidCredit", "DESC");
                            $this->db->limit(2);
                            $Query = $this->db->get();
                            $Rows = $Query->num_rows();
                            if ($Rows > 0) {
                                if ($Rows > 1) {
                                    /** get second highest user* */
                                    $PlayersAssistant = $Query->result_array();
                                    //print_r($PlayersAssistant);exit;
                                    $UserID = 0;
                                    $UserGUID = 0;
                                    $BidCredit = array_column($PlayersAssistant, 'BidCredit', "UserGUID");
                                    $AssistantDateTime = array_column($PlayersAssistant, 'DateTime', "UserGUID");
                                    $UserIDGUID = array_column($PlayersAssistant, 'UserID', "UserGUID");
                                    $MoreThenSamePlayer = array_count_values($BidCredit);
                                    array_filter($MoreThenSamePlayer, function($n) {
                                        return $n > 1;
                                    });
                                    if (!empty($MoreThenSamePlayer)) {
                                        $UserGUID = array_search(min($AssistantDateTime), $AssistantDateTime);
                                        $UserID = $UserIDGUID[array_search(min($AssistantDateTime), $AssistantDateTime)];

                                        $CurrentBidCreditNew = $AssistantBidCredit = $PlayersAssistant[0]['BidCredit'];
                                        if (100000 >= $AssistantBidCredit || $AssistantBidCredit < 1000000) {
                                            $CurrentBidCreditNew = $AssistantBidCredit + 100000;
                                        } else if (1000000 >= $AssistantBidCredit || $AssistantBidCredit < 10000000) {
                                            $CurrentBidCreditNew = $AssistantBidCredit + 1000000;
                                        } else if (10000000 >= $AssistantBidCredit || $AssistantBidCredit < 100000000) {
                                            $CurrentBidCreditNew = $AssistantBidCredit + 10000000;
                                        } else if (10000000 >= $AssistantBidCredit || $AssistantBidCredit < 1000000000) {
                                            $CurrentBidCreditNew = $AssistantBidCredit + 100000000;
                                        }
                                        if ($CurrentBidCreditNew > $PlayersAssistant[0]['BidCredit']) {
                                            $CurrentBidCreditNew = $PlayersAssistant[0]['BidCredit'];
                                        }
                                    } else {
                                        $SecondUser = $this->get_max($PlayersAssistant, 1);
                                        if (empty($SecondUser)) {
                                            $SecondUser = $PlayersAssistant[0];
                                        }
                                        $CurrentBidCreditNew = $AssistantBidCredit = $SecondUser['BidCredit'];
                                        if (100000 >= $AssistantBidCredit || $AssistantBidCredit < 1000000) {
                                            $CurrentBidCreditNew = $AssistantBidCredit + 100000;
                                        } else if (1000000 >= $AssistantBidCredit || $AssistantBidCredit < 10000000) {
                                            $CurrentBidCreditNew = $AssistantBidCredit + 1000000;
                                        } else if (10000000 >= $AssistantBidCredit || $AssistantBidCredit < 100000000) {
                                            $CurrentBidCreditNew = $AssistantBidCredit + 10000000;
                                        } else if (10000000 >= $AssistantBidCredit || $AssistantBidCredit < 1000000000) {
                                            $CurrentBidCreditNew = $AssistantBidCredit + 100000000;
                                        }
                                        /** get top user* */
                                        $TopUser = $this->get_max($PlayersAssistant, 0);
                                        $TopUserBidCredit = $TopUser['BidCredit'];
                                        if ($CurrentBidCreditNew > $TopUserBidCredit) {
                                            $CurrentBidCreditNew = $TopUserBidCredit;
                                        }
                                        $UserID = $TopUser['UserID'];
                                        $UserGUID = $TopUser['UserGUID'];
                                    }
                                    /** to check user available budget * */
                                    $this->db->select("AuctionBudget");
                                    $this->db->from('sports_contest_join');
                                    $this->db->where("AuctionBudget >=", $CurrentBidCreditNew);
                                    $this->db->where("ContestID", $Player['ContestID']);
                                    $this->db->where("SeriesID", $Player['SeriesID']);
                                    $this->db->where("UserID", $UserID);
                                    $Query = $this->db->get();
                                    if ($Query->num_rows() > 0) {
                                        /* add player bid */
                                        $Players[$key]['UserGUID'] = $UserGUID;
                                        $Players[$key]['BidCredit'] = $CurrentBidCreditNew;
                                        $Players[$key]['PreAssistant'] = "Yes";
                                    } else {
                                        $Players[$key]['PreAssistant'] = "No";
                                    }
                                } else {
                                    $PlayersAssistantOnBId = $Query->row_array();
                                    $Players[$key]['UserGUID'] = $PlayersAssistantOnBId["UserGUID"];
                                    if ($PlayersAssistantOnBId["UserID"] != $PlayerDetails[0]['UserID']) {
                                        $CurrentBidCredit = $PlayerDetails[0]['BidCredit'];
                                        $AssistantBidCredit = $PlayersAssistantOnBId['BidCredit'];
                                        if ($AssistantBidCredit > $CurrentBidCredit) {
                                            if (100000 >= $CurrentBidCredit || $CurrentBidCredit < 1000000) {
                                                $CurrentBidCredit = $CurrentBidCredit + 100000;
                                            } else if (1000000 >= $CurrentBidCredit || $CurrentBidCredit < 10000000) {
                                                $CurrentBidCredit = $CurrentBidCredit + 1000000;
                                            } else if (10000000 >= $CurrentBidCredit || $CurrentBidCredit < 100000000) {
                                                $CurrentBidCredit = $CurrentBidCredit + 10000000;
                                            } else if (10000000 >= $CurrentBidCredit || $CurrentBidCredit < 1000000000) {
                                                $CurrentBidCredit = $CurrentBidCredit + 100000000;
                                            }
                                        }
                                        if ($AssistantBidCredit >= $CurrentBidCredit) {
                                            $Players[$key]['BidCredit'] = $CurrentBidCredit;

                                            /** to check user available budget * */
                                            $this->db->select("AuctionBudget");
                                            $this->db->from('sports_contest_join');
                                            $this->db->where("AuctionBudget >=", $CurrentBidCredit);
                                            $this->db->where("ContestID", $Player['ContestID']);
                                            $this->db->where("SeriesID", $Player['SeriesID']);
                                            $this->db->where("UserID", $PlayersAssistantOnBId['UserID']);
                                            $Query = $this->db->get();
                                            if ($Query->num_rows() > 0) {
                                                $Players[$key]['PreAssistant'] = "Yes";
                                            } else {
                                                $Players[$key]['PreAssistant'] = "No";
                                            }
                                        } else {
                                            $Players[$key]['PreAssistant'] = "No";
                                        }
                                    } else {
                                        $Players[$key]['PreAssistant'] = "No";
                                    }
                                }
                            }
                        } else {

                            /** check current player in assistant * */
                            $this->db->select("UTP.PlayerID,UTP.BidCredit,UT.UserTeamID,UT.UserID,U.UserGUID");
                            $this->db->from('sports_users_teams UT, sports_users_team_players UTP,tbl_users U');
                            $this->db->where("UT.UserTeamID", "UTP.UserTeamID", FALSE);
                            $this->db->where("U.UserID", "UT.UserID", FALSE);
                            $this->db->where("UT.IsAssistant", "Yes");
                            $this->db->where("UT.IsPreTeam", "Yes");
                            $this->db->where("UT.ContestID", $Player['ContestID']);
                            $this->db->where("UT.SeriesID", $Player['SeriesID']);
                            $this->db->where("UTP.PlayerID", $Player['PlayerID']);
                            $this->db->order_by("UTP.DateTime", "DESC");
                            $Query = $this->db->get();
                            if ($Query->num_rows() > 0) {
                                $PlayersAssistantOnBId = $Query->row_array();
                                $Players[$key]['UserGUID'] = $PlayersAssistantOnBId["UserGUID"];
                                $Players[$key]['BidCredit'] = 100000;
                                /** to check user available budget * */
                                $this->db->select("AuctionBudget");
                                $this->db->from('sports_contest_join');
                                $this->db->where("AuctionBudget >=", 100000);
                                $this->db->where("ContestID", $Player['ContestID']);
                                $this->db->where("SeriesID", $Player['SeriesID']);
                                $this->db->where("UserID", $PlayersAssistantOnBId['UserID']);
                                $Query = $this->db->get();
                                if ($Query->num_rows() > 0) {
                                    $Players[$key]['PreAssistant'] = "Yes";
                                } else {
                                    $Players[$key]['PreAssistant'] = "No";
                                }
                            } else {
                                $Players[$key]['PreAssistant'] = "No";
                            }

                            /** get bid time difference in seconds * */
                            if (!empty($Player['BidDateTime'])) {
                                $Players[$key]['TimeDifference'] = strtotime($CurrentDateTime) - strtotime($Player['BidDateTime']);

                                if (!empty($AuctionHoldDateTime)) {
                                    $Players[$key]['TimeDifference'] = strtotime($AuctionHoldDateTime) - strtotime($Player['BidDateTime']);
                                }
                            } else {
                                /** check first player and second player * */
                                $this->db->select("ContestID");
                                $this->db->from('tbl_auction_player_bid_status');
                                $this->db->where("ContestID", $Auction['ContestID']);
                                $this->db->where("SeriesID", $Auction['SeriesID']);
                                $this->db->where("DateTime is NOT NULL", NULL, FALSE);
                                $Query = $this->db->get();
                                if ($Query->num_rows() > 0) {
                                    $Players[$key]['TimeDifference'] = 15;
                                } else {
                                    $Players[$key]['TimeDifference'] = 20;
                                }
                            }

                            $Players[$key]['IsSold'] = "UpcomingUnSold";
                        }
                    }
                    $TempPlayer[] = $Players[0];
                }
            }
        }

        return $TempPlayer;
    }

    /*
      Description: EDIT user team
     */

    function editUserTeam($Input = array(), $UserTeamID, $MatchID = "") {



        $this->db->trans_start();

        /* Delete Team Players */
        $this->db->delete('sports_users_team_players', array('UserTeamID' => $UserTeamID));

        /* Edit user team to user team table . */
        $this->db->where('UserTeamID', $UserTeamID);
        $this->db->limit(1);
        $this->db->update('sports_users_teams', array('UserTeamName' => $Input['UserTeamName'], 'UserTeamType' => $Input['UserTeamType']));

        /* Add User Team Players */
        if (!empty($Input['UserTeamPlayers'])) {

            /* Get Players */
            $PlayersIdsData = array();
            $PlayersData = $this->getSeriesPlayers('PlayerID,MatchID,SeriesID', array('SeriesID' => $Input['SeriesID'], 'RoundID' => $Input['RoundID']), TRUE, 0);
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
                        'SeriesID' => @$Input['SeriesID'],
                        'RoundID' => @$Input['RoundID'],
                        'MatchID' => @$MatchID,
                        'PlayerID' => $PlayersIdsData[$Value['PlayerGUID']],
                        'PlayerPosition' => $Value['PlayerPosition'],
                        'DateTime' => date('Y-m-d H:i:s'),
                        'AuctionDraftAssistantPriority' => $Value['AuctionDraftAssistantPriority'],
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
      Description: get player series wise
     */

    function getSeriesPlayers($Field = '', $Where = array(), $multiRecords = FALSE, $PageNo = 1, $PageSize = 15) {
        $Params = array();
        if (!empty($Field)) {
            $Params = array_map('trim', explode(',', $Field));
            $Field = '';
            $FieldArray = array(
                'SeriesGUID' => 'S.SeriesGUID',
                'TeamGUID' => 'T.TeamGUID',
                'TeamName' => 'T.TeamName',
                'TeamNameShort' => 'T.TeamNameShort',
                'TeamFlag' => 'T.TeamFlag',
                'PlayerID' => 'P.PlayerID',
                'PlayerIDLive' => 'P.PlayerIDLive',
                'PlayerRole' => 'TP.PlayerRole',
                'IsPlaying' => 'TP.IsPlaying',
                'TotalPoints' => 'TP.TotalPoints',
                'PointsData' => 'TP.PointsData',
                'SeriesID' => 'TP.SeriesID',
                'TeamID' => 'TP.TeamID',
                'PlayerPic' => 'IF(P.PlayerPic IS NULL,CONCAT("' . BASE_URL . '","uploads/PlayerPic/","player.png"),CONCAT("' . BASE_URL . '","uploads/PlayerPic/",P.PlayerPic)) AS PlayerPic',
                'PlayerCountry' => 'P.PlayerCountry',
                'PlayerBattingStyle' => 'P.PlayerBattingStyle',
                'PlayerBowlingStyle' => 'P.PlayerBowlingStyle',
                'PlayerBattingStats' => 'P.PlayerBattingStats',
                'PlayerBowlingStats' => 'P.PlayerBowlingStats',
                'PlayerSalary' => 'FORMAT(TP.PlayerSalary,1) as PlayerSalary',
                'PlayerSalaryCredit' => 'FORMAT(TP.PlayerSalary,1) PlayerSalaryCredit',
                'LastUpdateDiff' => 'IF(P.LastUpdatedOn IS NULL, 0, TIME_TO_SEC(TIMEDIFF("' . date('Y-m-d H:i:s') . '", P.LastUpdatedOn))) LastUpdateDiff',
                'MatchTypeID' => 'SSM.MatchTypeID',
                'MatchType' => 'SSM.MatchTypeName as MatchType',
                'TotalPointCredits' => '(SELECT SUM(`TotalPoints`) FROM `sports_team_players` WHERE `PlayerID` = TP.PlayerID AND `SeriesID` = TP.SeriesID) TotalPointCredits'
            );
            if ($Params) {
                foreach ($Params as $Param) {
                    $Field .= (!empty($FieldArray[$Param]) ? ',' . $FieldArray[$Param] : '');
                }
            }
        }
        $this->db->select('DISTINCT(P.PlayerGUID),P.PlayerName');
        if (!empty($Field)) $this->db->select($Field, FALSE);
        $this->db->from('tbl_entity E, sports_players P');
        if (array_keys_exist($Params, array('TeamGUID', 'TeamName', 'TeamNameShort', 'TeamFlag', 'PlayerRole', 'IsPlaying', 'TotalPoints', 'PointsData', 'SeriesID', 'MatchID'))) {
            $this->db->from('sports_teams T, sports_team_players TP');
            $this->db->where("P.PlayerID", "TP.PlayerID", FALSE);
            $this->db->where("TP.TeamID", "T.TeamID", FALSE);
        }
        $this->db->where("P.PlayerID", "E.EntityID", FALSE);
        if (!empty($Where['Keyword'])) {
            $Where['Keyword'] = trim($Where['Keyword']);
            $this->db->group_start();
            $this->db->like("P.PlayerName", $Where['Keyword']);
            $this->db->or_like("TP.PlayerRole", $Where['Keyword']);
            $this->db->or_like("P.PlayerCountry", $Where['Keyword']);
            $this->db->or_like("P.PlayerBattingStyle", $Where['Keyword']);
            $this->db->or_like("P.PlayerBowlingStyle", $Where['Keyword']);
            $this->db->group_end();
        }
        if (array_keys_exist($Params, array('SeriesGUID'))) {
            $this->db->from('sports_series S');
            $this->db->where("S.SeriesID", "TP.SeriesID", FALSE);
        }
        if (!empty($Where['MatchID'])) {
            $this->db->where("TP.MatchID", $Where['MatchID']);
        }
        if (!empty($Where['SeriesID'])) {
            $this->db->where("TP.SeriesID", $Where['SeriesID']);
        }
        if (!empty($Where['RoundID'])) {
            $this->db->where("TP.RoundID", $Where['RoundID']);
        }
        if (!empty($Where['PlayerGUID'])) {
            $this->db->where("P.PlayerGUID", $Where['PlayerGUID']);
        }
        if (!empty($Where['TeamID'])) {
            $this->db->where("TP.TeamID", $Where['TeamID']);
        }
        if (!empty($Where['IsPlaying'])) {
            $this->db->where("TP.IsPlaying", $Where['IsPlaying']);
        }
        if (!empty($Where['PlayerID'])) {
            $this->db->where("P.PlayerID", $Where['PlayerID']);
        }
        if (!empty($Where['PlayerRole'])) {
            $this->db->where("TP.PlayerRole", $Where['PlayerRole']);
        }
        if (!empty($Where['IsAdminSalaryUpdated'])) {
            $this->db->where("P.IsAdminSalaryUpdated", $Where['IsAdminSalaryUpdated']);
        }
        if (!empty($Where['StatusID'])) {
            $this->db->where("E.StatusID", $Where['StatusID']);
        }
        if (!empty($Where['CronFilter']) && $Where['CronFilter'] == 'OneDayDiff') {
            $this->db->having("LastUpdateDiff", 0);
            $this->db->or_having("LastUpdateDiff >=", 86400); // 1 Day
        }
        if (!empty($Where['PlayerSalary']) && $Where['PlayerSalary'] == 'Yes') {
            $this->db->where("TP.PlayerSalary >", 0);
        }
        if (!empty($Where['OrderBy']) && !empty($Where['Sequence'])) {
            $this->db->order_by($Where['OrderBy'], $Where['Sequence']);
        }

        if (!empty($Where['RandData'])) {
            $this->db->order_by($Where['RandData']);
        } else {
            $this->db->order_by('P.PlayerName', 'ASC');
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
        // $this->db->cache_on(); //Turn caching on
        $Query = $this->db->get();
        //echo $this->db->last_query();exit;
        $MatchStatus = 0;
        if ($Query->num_rows() > 0) {
            if ($multiRecords) {
                $Records = array();
                foreach ($Query->result_array() as $key => $Record) {
                    $Records[] = $Record;
                    $Records[$key]['PlayerBattingStats'] = (!empty($Record['PlayerBattingStats'])) ? json_decode($Record['PlayerBattingStats']) : new stdClass();
                    $Records[$key]['PlayerBowlingStats'] = (!empty($Record['PlayerBowlingStats'])) ? json_decode($Record['PlayerBowlingStats']) : new stdClass();
                    $Records[$key]['PointsData'] = (!empty($Record['PointsData'])) ? json_decode($Record['PointsData'], TRUE) : array();
                    $Records[$key]['PlayerSalary'] = $Record['PlayerSalary'];
                    $TotalPointsRound = ($MatchStatus == 2 || $MatchStatus == 5) ? @$Record['TotalPoints'] : @$Record['TotalPointCredits'];
                    $Records[$key]['PointCredits'] = $TotalPointsRound;
                }
                $Return['Data']['Records'] = $Records;
                return $Return;
            } else {
                $Record = $Query->row_array();
                $Record['PlayerBattingStats'] = (!empty($Record['PlayerBattingStats'])) ? json_decode($Record['PlayerBattingStats']) : new stdClass();
                $Record['PlayerBowlingStats'] = (!empty($Record['PlayerBowlingStats'])) ? json_decode($Record['PlayerBowlingStats']) : new stdClass();
                $Record['PointsData'] = (!empty($Record['PointsData'])) ? json_decode($Record['PointsData'], TRUE) : array();
                $Record['PlayerSalary'] = $Record['PlayerSalary'];
                $TotalPointsRound = ($MatchStatus == 2 || $MatchStatus == 5) ? @$Record['TotalPoints'] : @$Record['TotalPointCredits'];
                $Record['PointCredits'] = $TotalPointsRound;
                return $Record;
            }
        }
        return FALSE;
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
                'MatchInning' => 'UT.MatchInning'
            );
            if ($Params) {
                foreach ($Params as $Param) {
                    $Field .= (!empty($FieldArray[$Param]) ? ',' . $FieldArray[$Param] : '');
                }
            }
        }
        $this->db->select('UT.UserTeamGUID,UT.UserTeamName,UT.UserTeamType');
        if (!empty($Field)) $this->db->select($Field, FALSE);
        $this->db->from('tbl_entity E, sports_users_teams UT');
        $this->db->where("UT.UserTeamID", "E.EntityID", FALSE);
        if (!empty($Where['Keyword'])) {
            $this->db->like("UT.UserTeamName", $Where['Keyword']);
        }
        if (!empty($Where['SeriesID'])) {
            $this->db->where("UT.SeriesID", $Where['SeriesID']);
        }
        if (!empty($Where['MatchID'])) {
            $this->db->where("UT.MatchID", $Where['MatchID']);
        }
        if (!empty($Where['ContestID'])) {
            $this->db->where("UT.ContestID", $Where['ContestID']);
        }
        if (!empty($Where['UserTeamType']) && $Where['UserTeamType'] != 'All') {
            $this->db->where("UT.UserTeamType", $Where['UserTeamType']);
        }
        if (!empty($Where['UserTeamID'])) {
            $this->db->where("UT.UserTeamID", $Where['UserTeamID']);
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
                SELECT COUNT(*) AS `NormalContest` FROM `sports_contest` C, `tbl_entity` E WHERE C.ContestID = E.EntityID AND E.StatusID IN (1,2,5) AND C.MatchID = "' . $Where['MatchID'] . '" AND C.ContestType="Normal"
                )as NormalContest,
                (
                SELECT COUNT(*) AS `ReverseContest` FROM `sports_contest` C, `tbl_entity` E WHERE C.ContestID = E.EntityID AND E.StatusID IN(1,2,5) AND C.MatchID = "' . $Where['MatchID'] . '" AND C.ContestType="Reverse"
                )as ReverseContest,
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


        if ($Query->num_rows() > 0) {
            if ($multiRecords) {
                $Return['Data']['Records'] = $Query->result_array();
                if (in_array('UserTeamPlayers', $Params)) {
                    foreach ($Return['Data']['Records'] as $key => $value) {
                        $Return['Data']['Records'][$key]['UserTeamPlayers'] = $this->getUserTeamPlayers('PlayerPosition,PlayerName,PlayerPic,PlayerCountry,PlayerRole,Points', array('UserTeamID' => $value['UserTeamID']));
                    }
                }
                return $Return;
            } else {
                $Record = $Query->row_array();
                if (in_array('UserTeamPlayers', $Params)) {
                    $UserTeamPlayers = $this->getUserTeamPlayers('PlayerPosition,PlayerName,PlayerPic,PlayerCountry,PlayerRole,Points,BidCredit,ContestGUID', array('UserTeamID' => $Where['UserTeamID']));
                    $Record['UserTeamPlayers'] = ($UserTeamPlayers) ? $UserTeamPlayers : array();
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
                'PlayerPosition' => 'UTP.PlayerPosition',
                'Points' => 'UTP.Points',
                'PlayerName' => 'P.PlayerName',
                'PlayerID' => 'P.PlayerID',
                'PlayerPic' => 'IF(P.PlayerPic IS NULL,CONCAT("' . BASE_URL . '","uploads/PlayerPic/","player.png"),CONCAT("' . BASE_URL . '","uploads/PlayerPic/",P.PlayerPic)) AS PlayerPic',
                'PlayerCountry' => 'P.PlayerCountry',
                'PlayerSalary' => 'P.PlayerSalary',
                'PlayerRole' => 'TP.PlayerRole',
                'TeamGUID' => 'T.TeamGUID',
                'MatchType' => 'SM.MatchTypeName as MatchType',
                'BidCredit' => 'UTP.BidCredit'
            );
            if ($Params) {
                foreach ($Params as $Param) {
                    $Field .= (!empty($FieldArray[$Param]) ? ',' . $FieldArray[$Param] : '');
                }
            }
        }
        $this->db->select('DISTINCT P.PlayerGUID');
        if (!empty($Field)) $this->db->select($Field, FALSE);
        $this->db->from('sports_users_team_players UTP, sports_players P, sports_team_players TP,sports_teams T');
        $this->db->where("UTP.PlayerID", "P.PlayerID", FALSE);
        $this->db->where("UTP.PlayerID", "TP.PlayerID", FALSE);
        $this->db->where("T.TeamID", "TP.TeamID", FALSE);
        if (!empty($Where['Keyword'])) {
            $Where['Keyword'] = $Where['Keyword'];
            $this->db->like("P.PlayerName", $Where['Keyword']);
        }
        if (!empty($Where['UserTeamID'])) {
            $this->db->where("UTP.UserTeamID", $Where['UserTeamID']);
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
        //$this->db->group_by('P.PlayerID');
        $this->db->order_by('P.PlayerName', 'ASC');
        $Query = $this->db->get();
        if ($Query->num_rows() > 0) {
            $Records = array();
            foreach ($Query->result_array() as $key => $Record) {
                $Records[] = $Record;
                if (array_keys_exist($Params, array('PlayerSalary'))) {
                    $Records[$key]['PlayerSalary'] = (!empty($Record['PlayerSalary'])) ? json_decode($Record['PlayerSalary']) : new stdClass();
                }

                if (array_keys_exist($Params, array('PointCredits'))) {
                    if ($Record['MatchType'] == 'T20') {
                        $Records[$key]['PointCredits'] = (json_decode($Record['PlayerSalary'], TRUE)['T20Credits']) ? json_decode($Record['PlayerSalary'], TRUE)['T20Credits'] : 0;
                    } else if ($Record['MatchType'] == 'Test') {
                        $Records[$key]['PointCredits'] = (json_decode($Record['PlayerSalary'], TRUE)['T20iCredits']) ? json_decode($Record['PlayerSalary'], TRUE)['T20iCredits'] : 0;
                    } else if ($Record['MatchType'] == 'T20I') {
                        $Records[$key]['PointCredits'] = (json_decode($Record['PlayerSalary'], TRUE)['ODICredits']) ? json_decode($Record['PlayerSalary'], TRUE)['ODICredits'] : 0;
                    } else if ($Record['MatchType'] == 'ODI') {
                        $Records[$key]['PointCredits'] = (json_decode($Record['PlayerSalary'], TRUE)['TestCredits']) ? json_decode($Record['PlayerSalary'], TRUE)['TestCredits'] : 0;
                    } else {
                        $Records[$key]['PointCredits'] = (json_decode($Record['PlayerSalary'], TRUE)['T20Credits']) ? json_decode($Record['PlayerSalary'], TRUE)['T20Credits'] : 0;
                    }
                }
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

    function getUserTeamPlayersAuction($Field = '', $Where = array()) {
        $Params = array();
        if (!empty($Field)) {
            $Params = array_map('trim', explode(',', $Field));
            $Field = '';
            $FieldArray = array(
                'PlayerPosition' => 'UTP.PlayerPosition',
                'Points' => 'UTP.Points',
                'PlayerName' => 'P.PlayerName',
                'PlayerID' => 'P.PlayerID',
                'PlayerPic' => 'IF(P.PlayerPic IS NULL,CONCAT("' . BASE_URL . '","uploads/PlayerPic/","player.png"),CONCAT("' . BASE_URL . '","uploads/PlayerPic/",P.PlayerPic)) AS PlayerPic',
                'PlayerCountry' => 'P.PlayerCountry',
                'PlayerSalary' => 'P.PlayerSalary',
                'BidCredit' => 'UTP.BidCredit',
                'TotalPoints' => 'SUM(UTP.Points) TotalPoints'
            );
            if ($Params) {
                foreach ($Params as $Param) {
                    $Field .= (!empty($FieldArray[$Param]) ? ',' . $FieldArray[$Param] : '');
                }
            }
        }
        $this->db->select('P.PlayerGUID,P.PlayerID');
        if (!empty($Field)) $this->db->select($Field, FALSE);
        $this->db->from('sports_users_team_players UTP, sports_players P');
        $this->db->where("UTP.PlayerID", "P.PlayerID", FALSE);
        if (!empty($Where['Keyword'])) {
            $Where['Keyword'] = $Where['Keyword'];
            $this->db->like("P.PlayerName", $Where['Keyword']);
        }
        if (!empty($Where['UserTeamID'])) {
            $this->db->where("UTP.UserTeamID", $Where['UserTeamID']);
        }
        if (!empty($Where['PlayerPosition'])) {
            $this->db->where("UTP.PlayerPosition", $Where['PlayerPosition']);
        }

        if (!empty($Where['OrderBy']) && !empty($Where['Sequence'])) {
            $this->db->order_by($Where['OrderBy'], $Where['Sequence']);
        }
        $this->db->order_by('UTP.Points', 'DESC');
        $Query = $this->db->get();
        if ($Query->num_rows() > 0) {
            $Players = $Query->result_array();
            foreach ($Players as $Key => $Player) {
                $Players[$Key]['PlayerRole'] = "";
                $this->db->select("BS.PlayerRole,BS.PlayerID");
                $this->db->from('tbl_auction_player_bid_status BS');
                $this->db->where("BS.ContestID", $Where["ContestID"]);
                $this->db->where("BS.PlayerID", $Player["PlayerID"]);
                $this->db->limit(1);
                $Query = $this->db->get();
                if ($Query->num_rows() > 0) {
                    $Role = $Query->row_array();
                    $Players[$Key]['PlayerRole'] = $Role['PlayerRole'];
                }
            }
            return $Players;
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
                'Email' => 'U.Email',
                'UserID' => 'U.UserID',
                'UserRank' => 'JC.UserRank',
                'AuctionTimeBank' => 'JC.AuctionTimeBank',
                'AuctionBudget' => 'JC.AuctionBudget',
                'AuctionUserStatus' => 'JC.AuctionUserStatus',
                'ProfilePic' => 'IF(U.ProfilePic IS NULL,CONCAT("' . BASE_URL . '","uploads/profile/picture/","default.jpg"),CONCAT("' . BASE_URL . '","uploads/profile/picture/",U.ProfilePic)) AS ProfilePic',
                'UserRank' => 'JC.UserRank',
                'DraftUserPosition' => 'JC.DraftUserPosition',
                'DraftUserLive' => 'JC.DraftUserLive'
            );
            if ($Params) {
                foreach ($Params as $Param) {
                    $Field .= (!empty($FieldArray[$Param]) ? ',' . $FieldArray[$Param] : '');
                }
            }
        }
        $this->db->select('U.UserGUID,JC.UserTeamID');
        if (!empty($Field)) $this->db->select($Field, FALSE);
        $this->db->from('sports_contest_join JC, tbl_users U');
        $this->db->where("JC.UserID", "U.UserID", FALSE);
        if (!empty($Where['ContestID'])) {
            $this->db->where("JC.ContestID", $Where['ContestID']);
        }
        if (!empty($Where['UserID'])) {
            $this->db->where("JC.UserID", $Where['UserID']);
        }
        if (!empty($Where['RoundID'])) {
            $this->db->where("JC.RoundID", $Where['RoundID']);
        }
        if (!empty($Where['SeriesID'])) {
            $this->db->where("JC.SeriesID", $Where['SeriesID']);
        }
        if (!empty($Where['OrderBy']) && !empty($Where['Sequence'])) {
            $this->db->order_by($Where['OrderBy'], $Where['Sequence']);
        } else {

            if (!empty($Where['SessionUserID'])) {
                $this->db->order_by('JC.UserID=' . $Where['SessionUserID'] . ' DESC', null, FALSE);
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
        //echo $this->db->last_query();exit;

        if ($Query->num_rows() > 0) {
            if ($multiRecords) {
                $Return['Data']['Records'] = $Query->result_array();
                foreach ($Return['Data']['Records'] as $key => $record) {
                    if (!empty($record['UserTeamID'])) {
                        $UserTeamPlayers = $this->getUserTeamPlayersAuction('PLayerID,BidCredit,Points,PlayerPosition,PlayerName,PlayerRole,PlayerPic,TeamGUID,PlayerSalary,MatchType,PointCredits', array('UserTeamID' => $record['UserTeamID'], "ContestID" => $Where['ContestID']));
                        $Return['Data']['Records'][$key]['UserTeamPlayers'] = ($UserTeamPlayers) ? $UserTeamPlayers : array();
                    } else {
                        $Return['Data']['Records'][$key]['UserTeamPlayers'] = array();
                    }
                }
                return $Return;
            } else {
                $result = $Query->row_array();
                return $result;
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

        /* Get Joined Contest */
        $JoinedContestsUsers = $this->getJoinedContestsUsers('UserID,FirstName,Email,UserTeamID', array('ContestID' => $ContestID), TRUE, 0);
        if (!$JoinedContestsUsers) exit;

        foreach ($JoinedContestsUsers['Data']['Records'] as $Value) {

            /* Refund Wallet Money */
            if (!empty($Input['EntryFee'])) {

                /* Get Wallet Details */
                $WalletDetails = $this->Users_model->getWallet('WalletAmount,WinningAmount,CashBonus', array(
                    'UserID' => $Value['UserID'],
                    'EntityID' => $ContestID,
                    'UserTeamID' => $Value['UserTeamID'],
                    'Narration' => 'Join Contest'
                ));

                $InsertData = array(
                    "Amount" => $WalletDetails['WalletAmount'] + $WalletDetails['WinningAmount'] + $WalletDetails['WinningAmount'],
                    "WalletAmount" => $WalletDetails['WalletAmount'],
                    "WinningAmount" => $WalletDetails['WinningAmount'],
                    "CashBonus" => $WalletDetails['CashBonus'],
                    "TransactionType" => 'Cr',
                    "EntityID" => $ContestID,
                    "UserTeamID" => $Value['UserTeamID'],
                    "Narration" => 'Cancel Contest',
                    "EntryDate" => date("Y-m-d H:i:s")
                );
                $this->Users_model->addToWallet($InsertData, $Value['UserID'], 5);
            }

            /* Send Mail To Users */
            $EmailArr = array(
                "Name" => $Value['FirstName'],
                "SeriesName" => @$Input['SeriesName'],
                "ContestName" => @$Input['ContestName'],
                "MatchNo" => @$Input['MatchNo'],
                "TeamNameLocal" => @$Input['TeamNameLocal'],
                "TeamNameVisitor" => @$Input['TeamNameVisitor']
            );
            sendMail(array(
                'emailTo' => $Value['Email'],
                'emailSubject' => "Cancel Contest- " . SITE_NAME,
                'emailMessage' => emailTemplate($this->load->view('emailer/cancel_contest', $EmailArr, TRUE))
            ));
        }
    }

    /*
      Description: To get joined contest users
     */

    function getContestBidHistory($Field = '', $Where = array(), $multiRecords = FALSE, $PageNo = 1, $PageSize = 15) {
        $Params = array();
        if (!empty($Field)) {
            $Params = array_map('trim', explode(',', $Field));
            $Field = '';
            $FieldArray = array(
                'FirstName' => 'U.FirstName',
                'MiddleName' => 'U.MiddleName',
                'LastName' => 'U.LastName',
                'Username' => 'U.Username',
                'Email' => 'U.Email',
                'UserID' => 'U.UserID',
                'BidCredit' => 'JC.BidCredit',
                //'DateTime' => 'JC.DateTime',
                'DateTime' => 'DATE_FORMAT(CONVERT_TZ(DateTime,"+00:00","' . DEFAULT_TIMEZONE . '"), "' . DATE_FORMAT . '") DateTime',
                'ProfilePic' => 'IF(U.ProfilePic IS NULL,CONCAT("' . BASE_URL . '","uploads/profile/picture/","default.jpg"),CONCAT("' . BASE_URL . '","uploads/profile/picture/",U.ProfilePic)) AS ProfilePic'
            );
            if ($Params) {
                foreach ($Params as $Param) {
                    $Field .= (!empty($FieldArray[$Param]) ? ',' . $FieldArray[$Param] : '');
                }
            }
        }
        $this->db->select('U.UserGUID');
        if (!empty($Field)) $this->db->select($Field, FALSE);
        $this->db->from('tbl_auction_player_bid JC, tbl_users U');

        $this->db->where("JC.UserID", "U.UserID", FALSE);
        if (!empty($Where['ContestID'])) {
            $this->db->where("JC.ContestID", $Where['ContestID']);
        }
        if (!empty($Where['UserID'])) {
            $this->db->where("JC.UserID", $Where['UserID']);
        }
        if (!empty($Where['PlayerID'])) {
            $this->db->where("JC.PlayerID", $Where['PlayerID']);
        }
        if (!empty($Where['SeriesID'])) {
            $this->db->where("JC.SeriesID", $Where['SeriesID']);
        }
        if (!empty($Where['OrderBy']) && !empty($Where['Sequence'])) {
            $this->db->order_by($Where['OrderBy'], $Where['Sequence']);
        } else {
            $this->db->order_by('JC.DateTime', 'DESC');
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
        //echo $this->db->last_query();exit;

        if ($Query->num_rows() > 0) {
            if ($multiRecords) {
                $Return['Data']['Records'] = $Query->result_array();
                foreach ($Return['Data']['Records'] as $key => $record) {
                    //$UserTeamPlayers = $this->getUserTeamPlayers('PlayerPosition,PlayerName,PlayerRole,PlayerPic,TeamGUID,PlayerSalary,MatchType,PointCredits', array('UserTeamID' => $record['UserTeamID']));
                    // $Return['Data']['Records'][$key]['UserTeamPlayers'] = ($UserTeamPlayers) ? $UserTeamPlayers : array();
                }
                return $Return;
            } else {
                $result = $Query->row_array();
                return $result;
            }
        }
        return FALSE;
    }

    /*
      Description: To auto add minute in every hours
     */

    function auctionLiveAddMinuteInEveryHours($CronID) {

        /* Get Contests Data */
        $Contests = $this->getContests("ContestID,SeriesID,AuctionUpdateTime,LeagueJoinDateTimeUTC,AuctionTimeBreakAvailable", array('LeagueType' => 'Auction', "AuctionStatusID" => 2), TRUE, 1, 50);
        if (isset($Contests['Data']['Records']) && !empty($Contests['Data']['Records'])) {
            foreach ($Contests['Data']['Records'] as $Value) {
                $CurrentDatetime = strtotime(date('Y-m-d H:i:s'));
                $AuctionUpdateTime = strtotime($Value['AuctionUpdateTime']);
                if ($CurrentDatetime >= $AuctionUpdateTime) {
                    /** contest auction joined user get * */
                    $this->db->select("ContestID,UserID,AuctionTimeBank");
                    $this->db->from('sports_contest_join');
                    $this->db->where("ContestID", $Value['ContestID']);
                    $this->db->where("SeriesID", $Value['SeriesID']);
                    $Query = $this->db->get();
                    $Rows = $Query->num_rows();
                    if ($Rows > 0) {
                        $JoinedUsers = $Query->result_array();
                        foreach ($JoinedUsers as $User) {
                            /** contest auction user time bank update every hours * */
                            $UpdateData = array(
                                "AuctionTimeBank" => $User['AuctionTimeBank'] + 60
                            );
                            $this->db->where('ContestID', $Value['ContestID']);
                            $this->db->where('UserID', $User['UserID']);
                            $this->db->limit(1);
                            $this->db->update('sports_contest_join', $UpdateData);
                        }
                    }

                    /** contest auction break time update * */
                    $UpdateData = array(
                        "AuctionTimeBreakAvailable" => "Yes",
                        "AuctionUpdateTime" => date('Y-m-d H:i:s', strtotime(date('Y-m-d H:i:s')) + 3600)
                    );
                    $this->db->where('ContestID', $Value['ContestID']);
                    $this->db->limit(1);
                    $this->db->update('sports_contest', $UpdateData);
                }
            }
        } else {
            $this->db->where('CronID', $CronID);
            $this->db->limit(1);
            $this->db->update('log_cron', array('CronResponse' => @json_encode(array('Query' => $this->db->last_query()), JSON_UNESCAPED_UNICODE)));
        }
        return true;
    }

    /*
      Description: Update user status.
     */

    function changeUserStatus($Input = array(), $UserID, $ContestID) {

        /* Add contest to contest table . */
        $UpdateData = array(
            "AuctionUserStatus" => $Input['DraftUserStatus']
        );
        $this->db->where('ContestID', $ContestID);
        $this->db->where('UserID', $UserID);
        $this->db->limit(1);
        $this->db->update('sports_contest_join', $UpdateData);
        return true;
    }

    /*
      Description: Update contest status.
     */

    function changeContestStatus($ContestID) {

        /* Add contest to contest table . */
        /* Update Match Status */
        $this->db->where('EntityID', $ContestID);
        $this->db->limit(1);
        $this->db->update('tbl_entity', array('ModifiedDate' => date('Y-m-d H:i:s'), 'StatusID' => 2));
        return true;
    }

    /*
      Description: Update user hold time.
     */

    function auctionHoldTimeUpdate($Input = array(), $UserID, $ContestID) {

        $AuctionTimeBank = $this->db->query('SELECT AuctionTimeBank FROM sports_contest_join WHERE ContestID = ' . $ContestID . ' AND UserID= ' . $UserID . ' LIMIT 1')->row()->AuctionTimeBank;
        $RemainingTime = $AuctionTimeBank - $Input['HoldTime'];
        if ($RemainingTime < 0) {
            $RemainingTime = 0;
        }
        /* Add contest to contest table . */
        $UpdateData = array(
            "AuctionTimeBank" => $RemainingTime
        );
        $this->db->where('ContestID', $ContestID);
        $this->db->where('UserID', $UserID);
        $this->db->limit(1);
        $this->db->update('sports_contest_join', $UpdateData);
        return true;
    }

    /*
      Description: Update user status.
     */

    function changeUserContestStatusHoldOnOff($Input = array(), $UserID, $ContestID) {
        $Return = array();
        /* Add contest to contest table . */
        $UpdateData = array();
        $UpdateData['IsHold'] = $Input['IsHold'];
        if ($Input['IsHold'] == "Yes") {
            /** to check already user in hold * */
            $this->db->select("UserID");
            $this->db->from('sports_contest_join');
            $this->db->where("ContestID", $ContestID);
            $this->db->where("UserID", $UserID);
            $this->db->where("IsHold", "Yes");
            $this->db->limit(1);
            $Query = $this->db->get();
            if ($Query->num_rows() > 0) {
                $Return["Message"] = "Auction already hold";
                $Return["Status"] = 0;
                return $Return;
            }

            $UpdateData['AuctionHoldDateTime'] = date("Y-m-d H:i:s");

            /** check user time left * */
            $AuctionTimeBank = $this->db->query('SELECT AuctionTimeBank FROM sports_contest_join WHERE ContestID = ' . $ContestID . ' AND UserID= ' . $UserID . ' AND AuctionTimeBank <= 0 LIMIT 1')->row()->AuctionTimeBank;
            if (!empty($AuctionTimeBank)) {
                $Return["Message"] = "User hold time exceeded";
                $Return["Status"] = 0;
                return $Return;
            }
        }
        if ($Input['IsHold'] == "No") {

            /** to check already user in unhold * */
            $this->db->select("UserID");
            $this->db->from('sports_contest_join');
            $this->db->where("ContestID", $ContestID);
            $this->db->where("UserID", $UserID);
            $this->db->where("IsHold", "No");
            $this->db->limit(1);
            $Query = $this->db->get();
            if ($Query->num_rows() > 0) {
                $Return["Message"] = "User alrady unhold";
                $Return["Status"] = 1;
                return $Return;
            }

            /** check user on hold * */
            $IsHold = $this->db->query('SELECT IsHold FROM sports_contest_join WHERE ContestID = ' . $ContestID . ' AND UserID= ' . $UserID . ' AND IsHold= "Yes" LIMIT 1')->row()->IsHold;
            if (!empty($IsHold)) {
                /* update user time break . */
                $Query = $this->db->query('SELECT AuctionHoldDateTime,AuctionTimeBank FROM sports_contest_join WHERE ContestID = "' . $ContestID . '" AND UserID = "' . $UserID . '" LIMIT 1');
                $Contest = $Query->row_array();
                if (!empty($Contest)) {
                    $CurrentDateTime = date('Y-m-d H:i:s');
                    $CurrentDateTime = new DateTime($CurrentDateTime);
                    $AuctionHoldDateTime = new DateTime($Contest['AuctionHoldDateTime']);
                    $diffSeconds = $CurrentDateTime->getTimestamp() - $AuctionHoldDateTime->getTimestamp();
                    $AuctionTimeBank = $Contest['AuctionTimeBank'] - $diffSeconds;
                    if ($AuctionTimeBank < 0) {
                        $AuctionTimeBank = 0;
                    }
                    $UpdateData['AuctionTimeBank'] = $AuctionTimeBank;
                }

                /* get last player last bid . */
                $Input['Params'] = "ContestGUID,SeriesGUID,SeriesID,ContestID,TimeDifference,BidDateTime,PlayerStatus,PlayerGUID,PlayerID,PlayerRole,PlayerPic,PlayerCountry,PlayerBornPlace,PlayerSalary,PlayerSalaryCredit";
                $AuctionList = $this->auctionBidTimeManagement($Input, $ContestID);
                if (!empty($AuctionList)) {
                    $TimeDifference = abs($AuctionList[0]['TimeDifference']);
                    $PlayerStatus = abs($AuctionList[0]['PlayerStatus']);
                    /** update player table date time upcoming * */
                    if ($PlayerStatus == "Upcoming") {
                        $CurrentDate = strtotime(date("Y-m-d H:i:s")) - $TimeDifference;
                        $CurrentDate = date("Y-m-d H:i:s", $CurrentDate);
                        /** update player table date time * */
                        $this->db->where('ContestID', $ContestID);
                        $this->db->where('SeriesID', $AuctionList[0]['SeriesID']);
                        $this->db->where('PlayerID', $AuctionList[0]['PlayerID']);
                        $this->db->limit(1);
                        $this->db->update('tbl_auction_player_bid_status', array("DateTime" => $CurrentDate));
                    }
                    /** update player table date time live * */
                    if ($PlayerStatus == "Live") {
                        /* get last player bid auction contest . */
                        $this->db->select("PlayerID,SeriesID,ContestID,UserID,BidCredit,DateTime");
                        $this->db->from('tbl_auction_player_bid');
                        $this->db->where("ContestID", $ContestID);
                        $this->db->where("PlayerID", $AuctionList[0]['PlayerID']);
                        $this->db->order_by("DateTime", "DESC");
                        $this->db->limit(1);
                        $LastBid = $this->db->get()->row_array();
                        if (!empty($LastBid)) {
                            $CurrentDate = strtotime(date("Y-m-d H:i:s")) - $TimeDifference;
                            $CurrentDate = date("Y-m-d H:i:s", $CurrentDate);
                            /** update player table date time * */
                            $this->db->where('ContestID', $ContestID);
                            $this->db->where('SeriesID', $LastBid['SeriesID']);
                            $this->db->where('PlayerID', $LastBid['PlayerID']);
                            $this->db->where('UserID', $LastBid['UserID']);
                            $this->db->where('BidCredit', $LastBid['BidCredit']);
                            $this->db->where('DateTime', $LastBid['DateTime']);
                            $this->db->limit(1);
                            $this->db->update('tbl_auction_player_bid', array("DateTime" => $CurrentDate));
                        } else {
                            /** update player table date time * */
                            $CurrentDate = strtotime(date("Y-m-d H:i:s")) - $TimeDifference;
                            $CurrentDate = date("Y-m-d H:i:s", $CurrentDate);
                            /** update player table date time * */
                            $this->db->where('ContestID', $ContestID);
                            $this->db->where('SeriesID', $AuctionList[0]['SeriesID']);
                            $this->db->where('PlayerID', $AuctionList[0]['PlayerID']);
                            $this->db->limit(1);
                            $this->db->update('tbl_auction_player_bid_status', array("DateTime" => $CurrentDate));
                        }
                    }
                }
            } else {
                $Return["Message"] = "Auction already unhold";
                $Return["Status"] = 0;
                return $Return;
            }
        }
        $this->db->where('ContestID', $ContestID);
        $this->db->where('UserID', $UserID);
        $this->db->limit(1);
        $this->db->update('sports_contest_join', $UpdateData);
        $Return["Message"] = "User hold status successfully updated";
        $Return["Status"] = 1;
        return $Return;
    }

    /*
      Description: aution on break
     */

    function auctionOnBreak($Input = array(), $ContestID) {
        $UpdateData = array();

        /* Add contest to contest table . */
        $UpdateData = array(
            "AuctionIsBreakTimeStatus" => $Input['AuctionIsBreakTimeStatus'],
            "AuctionTimeBreakAvailable" => $Input['AuctionTimeBreakAvailable']
        );
        if ($Input['AuctionIsBreakTimeStatus'] == "Yes") {
            $UpdateData['AuctionBreakDateTime'] = date('Y-m-d H:i:s');
        }
        $this->db->where('ContestID', $ContestID);
        $this->db->limit(1);
        $this->db->update('sports_contest', $UpdateData);
        return true;
    }

    /*
      Description: EDIT auction user team players
     */

    function auctionTeamPlayersSubmit($Input = array(), $UserTeamID, $SeriesID) {


        $this->db->trans_start();

        /* Delete Team Players */
        $this->db->delete('sports_users_team_players', array('UserTeamID' => $UserTeamID));

        /* Edit user team to user team table . */
        $this->db->where('UserTeamID', $UserTeamID);
        $this->db->limit(1);
        $this->db->update('sports_users_teams', array('AuctionTopPlayerSubmitted' => "Yes"));


        /* Add User Team Players */
        if (!empty($Input['UserTeamPlayers'])) {

            /* Get Players */
            $PlayersIdsData = array();
            $PlayersData = $this->getSeriesPlayers('PlayerID,MatchID,SeriesID', array('SeriesID' => $SeriesID), TRUE, 0);
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
                        'SeriesID' => $SeriesID,
                        'PlayerID' => $PlayersIdsData[$Value['PlayerGUID']],
                        'PlayerPosition' => $Value['PlayerPosition'],
                        'BidCredit' => $Value['BidCredit']
                    );
                }
            }
            if ($UserTeamPlayers) $this->db->insert_batch('sports_users_team_players', $UserTeamPlayers);
        }

        $this->db->select("UserID,ContestID");
        $this->db->from('sports_users_teams');
        $this->db->where("UserTeamID", $UserTeamID);
        $this->db->limit(1);
        $Query = $this->db->get();
        if ($Query->num_rows() > 0) {
            $Records = $Query->row_array();
            /* update join contest team . */
            $this->db->where('ContestID', $Records['ContestID']);
            $this->db->where('UserID', $Records['UserID']);
            $this->db->limit(1);
            $this->db->update('sports_contest_join', array('UserTeamID' => $UserTeamID));
        }


        $this->db->trans_complete();
        if ($this->db->trans_status() === FALSE) {
            return FALSE;
        }

        return TRUE;
    }

    function getAuctionPlayersPoints($Field = '', $Where = array(), $multiRecords = FALSE, $PageNo = 1, $PageSize = 15) {
        $Params = array();
        if (!empty($Field)) {
            $Params = array_map('trim', explode(',', $Field));
            $Field = '';
            $FieldArray = array(
                'PlayerID' => 'P.PlayerID',
                'PlayerSalary' => 'P.PlayerSalary',
                'SeriesGUID' => 'S.SeriesGUID as SeriesGUID',
                'ContestGUID' => 'C.ContestGUID as ContestGUID',
                'TotalPoints' => 'SUM(TotalPoints) TotalPoints',
                'SeriesID' => 'TP.SeriesID',
                'PlayerIDLive' => 'P.PlayerIDLive',
                'PlayerPic' => 'IF(P.PlayerPic IS NULL,CONCAT("' . BASE_URL . '","uploads/PlayerPic/","player.png"),CONCAT("' . BASE_URL . '","uploads/PlayerPic/",P.PlayerPic)) AS PlayerPic',
                'PlayerCountry' => 'P.PlayerCountry',
                'PlayerBattingStyle' => 'P.PlayerBattingStyle',
                'PlayerBowlingStyle' => 'P.PlayerBowlingStyle',
                'PlayerBattingStats' => 'P.PlayerBattingStats',
                'PlayerBowlingStats' => 'P.PlayerBowlingStats',
                'LastUpdateDiff' => 'IF(P.LastUpdatedOn IS NULL, 0, TIME_TO_SEC(TIMEDIFF("' . date('Y-m-d H:i:s') . '", P.LastUpdatedOn))) LastUpdateDiff',
            );
            if ($Params) {
                foreach ($Params as $Param) {
                    $Field .= (!empty($FieldArray[$Param]) ? ',' . $FieldArray[$Param] : '');
                }
            }
        }
        $this->db->select('DISTINCT P.PlayerGUID,P.PlayerName');
        if (!empty($Field)) $this->db->select($Field, FALSE);
        $this->db->from('tbl_entity E, sports_players P,sports_team_players TP');
        $this->db->where("P.PlayerID", "E.EntityID", FALSE);
        $this->db->where("TP.PlayerID", "P.PlayerID", FALSE);

        if (!empty($Where['SeriesID'])) {
            $this->db->where("TP.SeriesID", $Where['SeriesID']);
        }
        if (!empty($Where['PlayerID'])) {
            $this->db->where("P.PlayerID", $Where['PlayerID']);
        }
        if (!empty($Where['StatusID'])) {
            $this->db->where("E.StatusID", $Where['StatusID']);
        }
        if (!empty($Where['OrderBy']) && !empty($Where['Sequence'])) {
            $this->db->order_by($Where['OrderBy'], $Where['Sequence']);
        }
        if (!empty($Where['RandData'])) {
            $this->db->order_by($Where['RandData']);
        }
        //$this->db->group_by("TP.PlayerID");
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
        // echo $this->db->last_query();
        // exit;
        if ($Query->num_rows() > 0) {
            if ($multiRecords) {
                $Records = array();
                foreach ($Query->result_array() as $key => $Record) {
                    $Records[] = $Record;
                    $IsAssistant = "";
                    $AuctionTopPlayerSubmitted = "No";
                    $UserTeamGUID = "";
                    $UserTeamName = "";
                    // $Records[$key]['PlayerSalary'] = $Record['PlayerSalary']*10000000;
                    $Records[$key]['PlayerBattingStats'] = (!empty($Record['PlayerBattingStats'])) ? json_decode($Record['PlayerBattingStats']) : new stdClass();
                    $Records[$key]['PlayerBowlingStats'] = (!empty($Record['PlayerBowlingStats'])) ? json_decode($Record['PlayerBowlingStats']) : new stdClass();
                    $Records[$key]['PointsData'] = (!empty($Record['PointsData'])) ? json_decode($Record['PointsData'], TRUE) : array();
                    $Records[$key]['PlayerRole'] = "";
                    $IsAssistant = $Record['IsAssistant'];
                    $UserTeamGUID = $Record['UserTeamGUID'];
                    $UserTeamName = $Record['UserTeamName'];
                    $AuctionTopPlayerSubmitted = $Record['AuctionTopPlayerSubmitted'];
                    $this->db->select('PlayerID,PlayerRole,PlayerSalary');
                    $this->db->where('PlayerID', $Record['PlayerID']);
                    $this->db->from('sports_team_players');
                    $this->db->order_by("PlayerSalary", 'DESC');
                    $this->db->limit(1);
                    $PlayerDetails = $this->db->get()->result_array();
                    if (!empty($PlayerDetails)) {
                        $Records[$key]['PlayerRole'] = $PlayerDetails['0']['PlayerRole'];
                    }
                }
                if (!empty($Where['MySquadPlayer']) && $Where['MySquadPlayer'] == "Yes") {
                    $Return['Data']['IsAssistant'] = $IsAssistant;
                    $Return['Data']['UserTeamGUID'] = $UserTeamGUID;
                    $Return['Data']['UserTeamName'] = $UserTeamName;
                    $Return['Data']['AuctionTopPlayerSubmitted'] = $AuctionTopPlayerSubmitted;
                }
                $Return['Data']['Records'] = $Records;
                return $Return;
            } else {
                $Record = $Query->row_array();
                $Record['PlayerBattingStats'] = (!empty($Record['PlayerBattingStats'])) ? json_decode($Record['PlayerBattingStats']) : new stdClass();
                $Record['PlayerBowlingStats'] = (!empty($Record['PlayerBowlingStats'])) ? json_decode($Record['PlayerBowlingStats']) : new stdClass();
                $Record['PointsData'] = (!empty($Record['PointsData'])) ? json_decode($Record['PointsData'], TRUE) : array();
                $Record['PlayerRole'] = "";
                $this->db->select('PlayerID,PlayerRole,PlayerSalary');
                $this->db->where('PlayerID', $Record['PlayerID']);
                $this->db->from('sports_team_players');
                $this->db->order_by("PlayerSalary", 'DESC');
                $this->db->limit(1);
                $PlayerDetails = $this->db->get()->result_array();
                if (!empty($PlayerDetails)) {
                    $Record['PlayerRole'] = $PlayerDetails['0']['PlayerRole'];
                }
                return $Record;
            }
        }
        return FALSE;
    }

    /*
      Description: EDIT auction user team players
     */

    function draftTeamPlayersSubmit($Input = array(), $UserTeamID, $MatchID) {


        $this->db->trans_start();

        /* Delete Team Players */
        $this->db->delete('sports_users_team_players', array('UserTeamID' => $UserTeamID));

        /* Edit user team to user team table . */
        $this->db->where('UserTeamID', $UserTeamID);
        $this->db->limit(1);
        $this->db->update('sports_users_teams', array('AuctionTopPlayerSubmitted' => "Yes"));


        /* Add User Team Players */
        if (!empty($Input['UserTeamPlayers'])) {

            /* Get Players */
            $PlayersIdsData = array();
            $PlayersData = $this->getPlayers('PlayerID,MatchID', array('MatchID' => $MatchID), TRUE, 0);
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
                        'SeriesID' => $SeriesID,
                        'RoundID' => $RoundID,
                        'MatchID' => $MatchID,
                        'PlayerID' => $PlayersIdsData[$Value['PlayerGUID']],
                        'PlayerPosition' => $Value['PlayerPosition'],
                    );
                }
            }
            if ($UserTeamPlayers) $this->db->insert_batch('sports_users_team_players', $UserTeamPlayers);
        }

        $this->db->select("UserID,ContestID");
        $this->db->from('sports_users_teams');
        $this->db->where("UserTeamID", $UserTeamID);
        $this->db->limit(1);
        $Query = $this->db->get();
        if ($Query->num_rows() > 0) {
            $Records = $Query->row_array();
            /* update join contest team . */
            $this->db->where('ContestID', $Records['ContestID']);
            $this->db->where('UserID', $Records['UserID']);
            $this->db->limit(1);
            $this->db->update('sports_contest_join', array('UserTeamID' => $UserTeamID));
        }

        $this->db->trans_complete();
        if ($this->db->trans_status() === FALSE) {
            return FALSE;
        }

        return TRUE;
    }

    function draftTeamAutoSubmit($CronID) {

        /** get draft contest all joined user team not submitted after 15 min * */
        $this->db->select("C.ContestID,C.AuctionUpdateTime,TIMESTAMPDIFF(MINUTE,C.AuctionUpdateTime,UTC_TIMESTAMP()) as M");
        $this->db->from('sports_contest C');
        $this->db->where("C.AuctionStatusID", 5);
        $this->db->where("C.LeagueType", "Draft");
        $this->db->where("C.DraftUserTeamSubmitted", "No");
        $this->db->where("TIMESTAMPDIFF(MINUTE,C.AuctionUpdateTime,UTC_TIMESTAMP()) >", 3);
        $Query = $this->db->get();
        if ($Query->num_rows() > 0) {
            $Contests = $Query->result_array();
            foreach ($Contests as $Contest) {
                $this->db->select("C.ContestID,C.UserID,T.UserTeamID,T.UserID");
                $this->db->from('sports_contest_join C,sports_users_teams T');
                $this->db->where("T.ContestID", "C.ContestID", FALSE);
                $this->db->where("T.UserID", "C.UserID", FALSE);
                $this->db->where("C.ContestID", $Contest['ContestID']);
                $this->db->where("T.UserTeamType", "Draft");
                $this->db->where("T.IsPreTeam", "No");
                $this->db->where("T.AuctionTopPlayerSubmitted", "No");
                $Query = $this->db->get();
                if ($Query->num_rows() > 0) {
                    $JoinedUser = $Query->result_array();
                    foreach ($JoinedUser as $Join) {
                        /** get first and second player* */
                        $Sql = "SELECT UserTeamID,PlayerID FROM sports_users_team_players WHERE UserTeamID = '" . $Join['UserTeamID'] . "'  ORDER BY DateTime ASC LIMIT 2";
                        $Players = $this->Sports_model->customQuery($Sql);
                        if (!empty($Players)) {
                            $PlayerPosition = array("Captain", "ViceCaptain");
                            foreach ($Players as $Key => $Player) {
                                /** first and second player position update* */
                                $Sql = "UPDATE sports_users_team_players SET PlayerPosition='" . $PlayerPosition[$Key] . "' WHERE UserTeamID = '" . $Join['UserTeamID'] . "' AND PlayerID='" . $Player['PlayerID'] . "'  LIMIT 1";
                                $Return = $this->Sports_model->customQuery($Sql, FALSE, TRUE);
                            }
                            /* Edit user team to user team table . */
                            $this->db->where('UserTeamID', $Join['UserTeamID']);
                            $this->db->limit(1);
                            $this->db->update('sports_users_teams', array('AuctionTopPlayerSubmitted' => "Yes"));

                            /* update join contest team . */
                            $this->db->where('ContestID', $Join['ContestID']);
                            $this->db->where('UserID', $Join['UserID']);
                            $this->db->limit(1);
                            $this->db->update('sports_contest_join', array('UserTeamID' => $Join['UserTeamID']));
                        }
                    }
                }
                /* Edit user team to user team table . */
                $this->db->where('ContestID', $Contest['ContestID']);
                $this->db->limit(1);
                $this->db->update('sports_contest', array('DraftUserTeamSubmitted' => "Yes"));
            }
        }
    }

    /**
     * Get snake draft player history
     */
    function getContestDraftPlayerHistory($Field = '', $Where = array(), $multiRecords = FALSE, $PageNo = 1, $PageSize = 15) {
        $Params = array();
        if (!empty($Field)) {
            $Params = array_map('trim', explode(',', $Field));
            $Field = '';
            $FieldArray = array(
                'PlayerName' => 'SP.PlayerName',
                'PlayerRole' => 'JC.PlayerRole',
                'DateTime' => 'DATE_FORMAT(CONVERT_TZ(DateTime,"+00:00","' . DEFAULT_TIMEZONE . '"), "' . DATE_FORMAT . '") DateTime',
                'PlayerPic' => 'IF(SP.PlayerPic IS NULL,CONCAT("' . BASE_URL . '","uploads/PlayerPic/","player.png"),CONCAT("' . BASE_URL . '","uploads/PlayerPic/",SP.PlayerPic)) AS PlayerPic',
            );
            if ($Params) {
                foreach ($Params as $Param) {
                    $Field .= (!empty($FieldArray[$Param]) ? ',' . $FieldArray[$Param] : '');
                }
            }
        }
        $this->db->select('SP.PlayerID');
        if (!empty($Field)) $this->db->select($Field, FALSE);
        $this->db->from('tbl_auction_player_bid_status JC');
        $this->db->join('sports_players SP', 'JC.PlayerID = SP.PlayerID', 'inner');
        $this->db->where('JC.PlayerStatus', 'Sold');
        if (!empty($Where['ContestID'])) {
            $this->db->where("JC.ContestID", $Where['ContestID']);
        }
        if (!empty($Where['SeriesID'])) {
            $this->db->where("JC.SeriesID", $Where['SeriesID']);
        }
        if (!empty($Where['RoundID'])) {
            $this->db->where("JC.RoundID", $Where['RoundID']);
        }
        if (!empty($Where['OrderBy']) && !empty($Where['Sequence'])) {
            $this->db->order_by($Where['OrderBy'], $Where['Sequence']);
        } else {
            $this->db->order_by('JC.DateTime', 'DESC');
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
                foreach ($Return['Data']['Records'] as $key => $record) {
                    $this->db->select('U.FirstName');
                    $this->db->from('sports_users_teams UT');
                    $this->db->join('tbl_users U', 'U.UserID = UT.UserID', 'inner');
                    $this->db->join('sports_users_team_players UTP', 'UTP.UserTeamID = UT.UserTeamID', 'inner');
                    $this->db->where('UTP.PlayerID', $record['PlayerID']);
                    $this->db->where('UT.ContestID', $Where['ContestID']);
                    $this->db->where('UT.IsPreTeam', "No");
                    $this->db->where('UT.IsAssistant', "No");
                    $this->db->limit(1);
                    $data = $this->db->get();
                    $result = $data->row_array();
                    $Return['Data']['Records'][$key]['FirstName'] = $result['FirstName'];
                }
                return $Return;
            } else {
                $result = $Query->row_array();
                return $result;
            }
        }
        return FALSE;
    }

    function draftPlayersPoint($Field = '', $Where = array(), $multiRecords = FALSE, $PageNo = 1, $PageSize = 15) {
        $Params = array();
        if (!empty($Field)) {
            $Params = array_map('trim', explode(',', $Field));
            $Field = '';
            $FieldArray = array(
                'TeamGUID' => 'T.TeamGUID',
                'TeamName' => 'T.TeamName',
                'TeamNameShort' => 'T.TeamNameShort',
                'TeamFlag' => 'T.TeamFlag',
                'PlayerID' => 'P.PlayerID',
                'PlayerIDLive' => 'P.PlayerIDLive',
                'PlayerRole' => 'TP.PlayerRole',
                'IsPlaying' => 'TP.IsPlaying',
                'TotalPoints' => 'TP.TotalPoints',
                'PointsData' => 'TP.PointsData',
                'SeriesID' => 'TP.SeriesID',
                'MatchID' => 'TP.MatchID',
                'TeamID' => 'TP.TeamID',
                'PlayerPic' => 'IF(P.PlayerPic IS NULL,CONCAT("' . BASE_URL . '","uploads/PlayerPic/","player.png"),CONCAT("' . BASE_URL . '","uploads/PlayerPic/",P.PlayerPic)) AS PlayerPic',
                'PlayerCountry' => 'P.PlayerCountry',
                'PlayerBattingStyle' => 'P.PlayerBattingStyle',
                'PlayerBowlingStyle' => 'P.PlayerBowlingStyle',
                'PlayerBattingStats' => 'P.PlayerBattingStats',
                'PlayerBowlingStats' => 'P.PlayerBowlingStats',
                'PlayerSalary' => 'TP.PlayerSalary',
                'PlayerSalaryCredit' => 'TP.PlayerSalary PlayerSalaryCredit',
                'LastUpdateDiff' => 'IF(P.LastUpdatedOn IS NULL, 0, TIME_TO_SEC(TIMEDIFF("' . date('Y-m-d H:i:s') . '", P.LastUpdatedOn))) LastUpdateDiff',
                'MatchTypeID' => 'SSM.MatchTypeID',
                'MatchType' => 'SSM.MatchTypeName as MatchType',
                'TotalPointCredits' => '(SELECT SUM(`TotalPoints`) FROM `sports_team_players` WHERE `PlayerID` = TP.PlayerID AND `SeriesID` = TP.SeriesID) TotalPointCredits'
            );
            if ($Params) {
                foreach ($Params as $Param) {
                    $Field .= (!empty($FieldArray[$Param]) ? ',' . $FieldArray[$Param] : '');
                }
            }
        }
        $this->db->select('P.PlayerGUID,P.PlayerName,TL.TeamName AS TeamNameLocal,TV.TeamName AS TeamNameVisitor,TL.TeamNameShort AS TeamNameShortLocal,TV.TeamNameShort AS TeamNameShortVisitor, CONCAT("' . BASE_URL . '","uploads/TeamFlag/",TL.TeamFlag) AS TeamFlagLocal, CONCAT("' . BASE_URL . '","uploads/TeamFlag/",TV.TeamFlag) AS TeamFlagVisitor');

        if (!empty($Field)) $this->db->select($Field, FALSE);
        $this->db->from('tbl_entity E, sports_players P, sports_teams TL, sports_teams TV');
        if (array_keys_exist($Params, array('TeamGUID', 'TeamName', 'TeamNameShort', 'TeamFlag', 'PlayerRole', 'IsPlaying', 'TotalPoints', 'PointsData', 'SeriesID', 'MatchID'))) {
            $this->db->from('sports_teams T,sports_matches M, sports_private_contest_team_players TP,sports_set_match_types SSM');
            $this->db->where("P.PlayerID", "TP.PlayerID", FALSE);
            $this->db->where("TP.TeamID", "T.TeamID", FALSE);
            $this->db->where("TP.MatchID", "M.MatchID", FALSE);
            $this->db->where("M.MatchTypeID", "SSM.MatchTypeID", FALSE);
        }
        $this->db->where("M.TeamIDLocal", "TL.TeamID", FALSE);
        $this->db->where("M.TeamIDVisitor", "TV.TeamID", FALSE);
        $this->db->where("TP.MatchID", "E.EntityID", FALSE);
        if (!empty($Where['Keyword'])) {
            $Where['Keyword'] = trim($Where['Keyword']);
            $this->db->group_start();
            $this->db->like("P.PlayerName", $Where['Keyword']);
            $this->db->or_like("TP.PlayerRole", $Where['Keyword']);
            $this->db->or_like("P.PlayerCountry", $Where['Keyword']);
            $this->db->or_like("P.PlayerBattingStyle", $Where['Keyword']);
            $this->db->or_like("P.PlayerBowlingStyle", $Where['Keyword']);
            $this->db->group_end();
        }
        if (!empty($Where['SeriesID'])) {
            $this->db->where("TP.SeriesID", $Where['SeriesID']);
        }
        if (!empty($Where['ContestID'])) {
            $this->db->where("TP.ContestID", $Where['ContestID']);
        }
        if (!empty($Where['RoundID'])) {
            $this->db->where("TP.RoundID", $Where['RoundID']);
        }
        if (!empty($Where['PlayerGUID'])) {
            $this->db->where("P.PlayerGUID", $Where['PlayerGUID']);
        }
        if (!empty($Where['TeamID'])) {
            $this->db->where("TP.TeamID", $Where['TeamID']);
        }
        if (!empty($Where['IsPlaying'])) {
            $this->db->where("TP.IsPlaying", $Where['IsPlaying']);
        }
        if (!empty($Where['PlayerID'])) {
            $this->db->where("P.PlayerID", $Where['PlayerID']);
        }
        if (!empty($Where['IsAdminSalaryUpdated'])) {
            $this->db->where("P.IsAdminSalaryUpdated", $Where['IsAdminSalaryUpdated']);
        }
        if (!empty($Where['StatusID'])) {
            if ($Where['StatusID'] == 5) {
                $Where['StatusID'] = array(2, 5, 3, 8);
                $this->db->where_in("E.StatusID", $Where['StatusID']);
            } else {
                $Where['StatusID'] = array(1, 2, 3, 4, 5, 8, 10);
                $this->db->where_in("E.StatusID", $Where['StatusID']);
            }
        }
        if (!empty($Where['CronFilter']) && $Where['CronFilter'] == 'OneDayDiff') {
            $this->db->having("LastUpdateDiff", 0);
            $this->db->or_having("LastUpdateDiff >=", 86400); // 1 Day
        }

        if (!empty($Where['OrderBy']) && !empty($Where['Sequence'])) {
            $this->db->order_by($Where['OrderBy'], $Where['Sequence']);
        }

        if (!empty($Where['RandData'])) {
            $this->db->order_by($Where['RandData']);
        } else {
            $this->db->order_by('P.PlayerName', 'ASC');
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
        //$this->db->where("E.StatusID", $StatusID);
        // $this->db->cache_on(); //Turn caching on
        $Query = $this->db->get();
        //echo $this->db->last_query();exit;
        $MatchStatus = 0;
        if (!empty($Where['SeriesID'])) {
            /* Get Match Status */
            $MatchQuery = $this->db->query('SELECT E.StatusID FROM `sports_matches` `M`,`tbl_entity` `E` WHERE M.`SeriesID` = "' . $Where['SeriesID'] . '" AND M.SeriesID = E.EntityID LIMIT 1');
            $MatchStatus = ($MatchQuery->num_rows() > 0) ? $MatchQuery->row()->StatusID : 0;
        }
        if ($Query->num_rows() > 0) {
            if ($multiRecords) {
                $Records = array();
                foreach ($Query->result_array() as $key => $Record) {

                    $Records[] = $Record;
                    $Records[$key]['PlayerBattingStats'] = (!empty($Record['PlayerBattingStats'])) ? json_decode($Record['PlayerBattingStats']) : new stdClass();
                    $Records[$key]['PlayerBowlingStats'] = (!empty($Record['PlayerBowlingStats'])) ? json_decode($Record['PlayerBowlingStats']) : new stdClass();
                    $Records[$key]['PointsData'] = (!empty($Record['PointsData'])) ? json_decode($Record['PointsData'], TRUE) : array();
                    $Records[$key]['PlayerSalary'] = $Record['PlayerSalary'];
                    $Records[$key]['PointCredits'] = ($MatchStatus == 2 || $MatchStatus == 5) ? @$Record['TotalPoints'] : @$Record['TotalPointCredits'];

                    if (in_array('MyTeamPlayer', $Params)) {
                        $this->db->select('SUTP.PlayerID,SUTP.SeriesID');
                        $this->db->where('SUTP.SeriesID', $Where['SeriesID']);
                        $this->db->where('SUT.UserID', $Where['UserID']);
                        $this->db->from('sports_users_teams SUT,sports_users_team_players SUTP');
                        $MyPlayers = $this->db->get()->result_array();
                        if (!empty($MyPlayers)) {
                            foreach ($MyPlayers as $k => $value) {
                                if ($value['PlayerID'] == $Record['PlayerID']) {
                                    $Records[$key]['MyPlayer'] = 'Yes';
                                } else {
                                    $Records[$key]['MyPlayer'] = 'No';
                                }
                            }
                        } else {
                            $Records[$key]['MyPlayer'] = 'No';
                        }
                    }

                    if (in_array('PlayerSelectedPercent', $Params)) {
                        $TotalTeams = $this->db->query('Select count(*) as TotalTeams from sports_users_teams WHERE SeriesID="' . $Where['SeriesID'] . '"')->row()->TotalTeams;

                        $this->db->select('count(SUTP.PlayerID) as TotalPlayer');
                        $this->db->where("SUTP.UserTeamID", "SUT.UserTeamID", FALSE);
                        $this->db->where("SUTP.PlayerID", $Record['PlayerID']);
                        $this->db->where("SUTP.SeriesID", $Where['SeriesID']);
                        $this->db->from('sports_users_teams SUT,sports_users_team_players SUTP');
                        $Players = $this->db->get()->row();
                        $Records[$key]['PlayerSelectedPercent'] = ($TotalTeams > 0 ) ? round((($Players->TotalPlayer * 100 ) / $TotalTeams), 2) > 100 ? 100 : round((($Players->TotalPlayer * 100 ) / $TotalTeams), 2) : 0;
                    }

                    if (in_array('TopPlayer', $Params)) {
                        $Wicketkipper = $this->findKeyValuePlayers($Records, "WicketKeeper");
                        $Batsman = $this->findKeyValuePlayers($Records, "Batsman");
                        $Bowler = $this->findKeyValuePlayers($Records, "Bowler");
                        $Allrounder = $this->findKeyValuePlayers($Records, "AllRounder");
                        usort($Batsman, function ($a, $b) {
                            return $b['TotalPoints'] - $a['TotalPoints'];
                        });
                        usort($Bowler, function ($a, $b) {
                            return $b['TotalPoints'] - $a['TotalPoints'];
                        });
                        usort($Wicketkipper, function ($a, $b) {
                            return $b['TotalPoints'] - $a['TotalPoints'];
                        });
                        usort($Allrounder, function ($a, $b) {
                            return $b['TotalPoints'] - $a['TotalPoints'];
                        });

                        $TopBatsman = array_slice($Batsman, 0, 4);
                        $TopBowler = array_slice($Bowler, 0, 3);
                        $TopWicketkipper = array_slice($Wicketkipper, 0, 1);
                        $TopAllrounder = array_slice($Allrounder, 0, 3);

                        $AllPlayers = array();
                        $AllPlayers = array_merge($TopBatsman, $TopBowler);
                        $AllPlayers = array_merge($AllPlayers, $TopAllrounder);
                        $AllPlayers = array_merge($AllPlayers, $TopWicketkipper);

                        rsort($AllPlayers, function($a, $b) {
                            return $b['TotalPoints'] - $a['TotalPoints'];
                        });
                    }

                    if (in_array($Record['PlayerID'], array_column($AllPlayers, 'PlayerID'))) {
                        $Records[$key]['TopPlayer'] = 'Yes';
                    } else {
                        $Records[$key]['TopPlayer'] = 'No';
                    }
                }
                $Return['Data']['Records'] = $Records;
                return $Return;
            } else {
                $Record = $Query->row_array();
                $Record['PlayerBattingStats'] = (!empty($Record['PlayerBattingStats'])) ? json_decode($Record['PlayerBattingStats']) : new stdClass();
                $Record['PlayerBowlingStats'] = (!empty($Record['PlayerBowlingStats'])) ? json_decode($Record['PlayerBowlingStats']) : new stdClass();
                $Record['PointsData'] = (!empty($Record['PointsData'])) ? json_decode($Record['PointsData'], TRUE) : array();
                $Record['PlayerSalary'] = $Record['PlayerSalary'];
                $Record['PointCredits'] = ($MatchStatus == 2 || $MatchStatus == 5) ? @$Record['TotalPoints'] : @$Record['TotalPointCredits'];
                if (in_array('MyTeamPlayer', $Params)) {
                    $this->db->select('SUTP.PlayerID,SUTP.SeriesID');
                    $this->db->where('SUTP.SeriesID', $Where['SeriesID']);
                    $this->db->where('SUT.UserID', $Where['UserID']);
                    $this->db->from('sports_users_teams SUT,sports_users_team_players SUTP');
                    $MyPlayers = $this->db->get()->result_array();
                    foreach ($MyPlayers as $key => $value) {
                        if ($value['PlayerID'] == $Record['PlayerID']) {
                            $Records['MyPlayer'] = 'Yes';
                        } else {
                            $Records['MyPlayer'] = 'No';
                        }
                    }
                }

                if (in_array('TopPlayer', $Params)) {
                    $Wicketkipper = $this->findKeyValuePlayers($Records, "WicketKeeper");
                    $Batsman = $this->findKeyValuePlayers($Records, "Batsman");
                    $Bowler = $this->findKeyValuePlayers($Records, "Bowler");
                    $Allrounder = $this->findKeyValuePlayers($Records, "AllRounder");
                    usort($Batsman, function ($a, $b) {
                        return $b['TotalPoints'] - $a['TotalPoints'];
                    });
                    usort($Bowler, function ($a, $b) {
                        return $b['TotalPoints'] - $a['TotalPoints'];
                    });
                    usort($Wicketkipper, function ($a, $b) {
                        return $b['TotalPoints'] - $a['TotalPoints'];
                    });
                    usort($Allrounder, function ($a, $b) {
                        return $b['TotalPoints'] - $a['TotalPoints'];
                    });
                    $TopBatsman = array_slice($Batsman, 0, 4);
                    $TopBowler = array_slice($Bowler, 0, 3);
                    $TopWicketkipper = array_slice($Wicketkipper, 0, 1);
                    $TopAllrounder = array_slice($Allrounder, 0, 3);
                    $AllPlayers = array();
                    $AllPlayers = array_merge($TopBatsman, $TopBowler);
                    $AllPlayers = array_merge($AllPlayers, $TopAllrounder);
                    $AllPlayers = array_merge($AllPlayers, $TopWicketkipper);

                    rsort($AllPlayers, function($a, $b) {
                        return $b['TotalPoints'] - $a['TotalPoints'];
                    });
                }
                if (in_array($Record['PlayerID'], array_column($AllPlayers, 'PlayerID'))) {
                    $Records['TopPlayer'] = 'Yes';
                } else {
                    $Records['TopPlayer'] = 'No';
                }

                return $Record;
            }
        }
        return FALSE;
    }

    /*
      Description: To get all matches
     */

    function getMatches($Field = '', $Where = array(), $multiRecords = FALSE, $PageNo = 1, $PageSize = 15) {
        $Params = array();
        if (!empty($Field)) {
            $Params = array_map('trim', explode(',', $Field));
            $Field = '';
            $FieldArray = array(
                'SeriesID' => 'S.SeriesID',
                'SeriesGUID' => 'S.SeriesGUID',
                'StatusID' => 'E.StatusID',
                'SeriesIDLive' => 'S.SeriesIDLive',
                'CompetitionStateKey' => 'S.CompetitionStateKey',
                'SeriesName' => 'S.SeriesName',
                'SeriesStartDate' => 'DATE_FORMAT(CONVERT_TZ(S.SeriesStartDate,"+00:00","' . DEFAULT_TIMEZONE . '"), "' . DATE_FORMAT . '") SeriesStartDate',
                'SeriesEndDate' => 'DATE_FORMAT(CONVERT_TZ(S.SeriesEndDate,"+00:00","' . DEFAULT_TIMEZONE . '"), "' . DATE_FORMAT . '") SeriesEndDate',
                'MatchID' => 'M.MatchID',
                'IsPlayingXINotificationSent' => 'M.IsPlayingXINotificationSent',
                'APIAutoTimeUpdate' => 'M.APIAutoTimeUpdate',
                'MatchIDLive' => 'M.MatchIDLive',
                'MatchTypeID' => 'M.MatchTypeID',
                'MatchNo' => 'M.MatchNo',
                'RoundID' => 'M.RoundID',
                'MatchLocation' => 'M.MatchLocation',
                'IsPreSquad' => 'M.IsPreSquad',
                'IsPlayerPointsUpdated' => 'M.IsPlayerPointsUpdated',
                'MatchScoreDetails' => 'M.MatchScoreDetails',
                'MatchStartDateTime' => 'DATE_FORMAT(CONVERT_TZ(M.MatchStartDateTime,"+00:00","' . DEFAULT_TIMEZONE . '"), "' . DATE_FORMAT . '") MatchStartDateTime',
                'CurrentDateTime' => 'DATE_FORMAT(CONVERT_TZ(Now(),"+00:00","' . DEFAULT_TIMEZONE . '"), "' . DATE_FORMAT . ' ") CurrentDateTime',
                'MatchDate' => 'DATE_FORMAT(CONVERT_TZ(M.MatchStartDateTime,"+00:00","' . DEFAULT_TIMEZONE . '"), "%Y-%m-%d") MatchDate',
                'MatchTime' => 'DATE_FORMAT(CONVERT_TZ(M.MatchStartDateTime,"+00:00","' . DEFAULT_TIMEZONE . '"), "%H:%i:%s") MatchTime',
                'MatchStartDateTimeUTC' => 'M.MatchStartDateTime as MatchStartDateTimeUTC',
                'ServerDateTimeUTC' => 'UTC_TIMESTAMP() as ServerDateTimeUTC',
                'TeamIDLocal' => 'TL.TeamID AS TeamIDLocal',
                'TeamIDVisitor' => 'TV.TeamID AS TeamIDVisitor',
                'TeamGUIDLocal' => 'TL.TeamGUID AS TeamGUIDLocal',
                'TeamGUIDVisitor' => 'TV.TeamGUID AS TeamGUIDVisitor',
                'TeamIDLiveLocal' => 'TL.TeamIDLive AS TeamIDLiveLocal',
                'TeamIDLiveVisitor' => 'TV.TeamIDLive AS TeamIDLiveVisitor',
                'TeamNameLocal' => 'TL.TeamName AS TeamNameLocal',
                'TeamNameVisitor' => 'TV.TeamName AS TeamNameVisitor',
                'TeamNameShortLocal' => 'TL.TeamNameShort AS TeamNameShortLocal',
                'TeamNameShortVisitor' => 'TV.TeamNameShort AS TeamNameShortVisitor',
                'TeamFlagLocal' => 'CONCAT("' . BASE_URL . '","uploads/TeamFlag/",TL.TeamFlag) as TeamFlagLocal',
                'TeamFlagVisitor' => 'CONCAT("' . BASE_URL . '","uploads/TeamFlag/",TV.TeamFlag) as TeamFlagVisitor',
                'TeamPlayersAvailable' => 'IF(
                                        (
                                        SELECT
                                            count(sports_team_players.PlayerID) as TPlayer
                                        FROM
                                            sports_team_players
                                        WHERE
                                            sports_team_players.MatchID = M.MatchID AND sports_team_players.PlayerRole="Batsman" AND PlayerSalary > 0 HAVING TPlayer > 3
                                        LIMIT 1
                                    ) IS NOT NULL,
                                    "Yes",
                                    "No"
                                    ) AS TeamPlayersAvailable',
                'ContestAvailable' => 'IF(
                                        (
                                        SELECT
                                            count(sports_contest.ContestID) as ContestID
                                        FROM
                                            sports_contest
                                        WHERE
                                            sports_contest.MatchID = M.MatchID HAVING ContestID > 0
                                        LIMIT 1
                                    ) IS NOT NULL,
                                    "Yes",
                                    "No"
                                    ) AS ContestAvailable',
                'MyTotalJoinedContest' => '(SELECT COUNT(DISTINCT sports_contest_join.ContestID)
                                                FROM sports_contest_join
                                                WHERE sports_contest_join.MatchID =  M.MatchID AND UserID= ' . @$Where['UserID'] . ') AS MyTotalJoinedContest',
                'ContestsAvailable' => '(select count(C.ContestGUID) from sports_contest C where C.MatchID = M.MatchID AND E.StatusID=1) as ContestsAvailable',
                'Status' => 'CASE E.StatusID
                when "1" then "Pending"
                when "2" then "Running"
                when "3" then "Cancelled"
                when "5" then "Completed"  
                when "8" then "Abandoned"  
                when "9" then "No Result" 
                when "10" then "Reviewing" 
                END as Status',
                'MatchType' => 'MT.MatchTypeName AS MatchType',
                'isReminderNotificationSent' => 'M.isReminderNotificationSent',
                'LastUpdateDiff' => 'IF(M.LastUpdatedOn IS NULL, 0, TIME_TO_SEC(TIMEDIFF("' . date('Y-m-d H:i:s') . '", M.LastUpdatedOn))) LastUpdateDiff',
                'TotalUserWinning' => '(select SUM(UserWinningAmount) from sports_contest_join where MatchID = M.MatchID AND UserID=' . @$Where['UserID'] . ') as TotalUserWinning',
                'isJoinedContest' => '(select count(*) from sports_contest_join where MatchID = M.MatchID AND UserID = "' . @$Where['SessionUserID'] . '" AND E.StatusID=' . @$Where['StatusID'] . ') as JoinedContests'
            );
            if ($Params) {
                foreach ($Params as $Param) {
                    $Field .= (!empty($FieldArray[$Param]) ? ',' . $FieldArray[$Param] : '');
                }
            }
        }
        $this->db->select('M.MatchGUID,TL.TeamName AS TeamNameLocal,TV.TeamName AS TeamNameVisitor,TL.TeamNameShort AS TeamNameShortLocal,TV.TeamNameShort AS TeamNameShortVisitor,M.MatchDisplay');
        if (!empty($Field)) $this->db->select($Field, FALSE);
        $this->db->from('tbl_entity E, sports_series S, sports_matches M, sports_teams TL, sports_teams TV, sports_set_match_types MT');
        $this->db->where("M.SeriesID", "S.SeriesID", FALSE);
        $this->db->where("M.MatchID", "E.EntityID", FALSE);
        $this->db->where("M.MatchTypeID", "MT.MatchTypeID", FALSE);
        $this->db->where("M.TeamIDLocal", "TL.TeamID", FALSE);
        $this->db->where("M.TeamIDVisitor", "TV.TeamID", FALSE);
        if (!empty($Where['Keyword'])) {
            $this->db->group_start();
            $this->db->like("S.SeriesName", $Where['Keyword']);
            $this->db->or_like("M.MatchNo", $Where['Keyword']);
            $this->db->or_like("M.MatchLocation", $Where['Keyword']);
            $this->db->or_like("TL.TeamName", $Where['Keyword']);
            $this->db->or_like("TV.TeamName", $Where['Keyword']);
            $this->db->or_like("TL.TeamNameShort", $Where['Keyword']);
            $this->db->or_like("TV.TeamNameShort", $Where['Keyword']);
            $this->db->group_end();
        }
        $this->db->where("M.MatchTypeByApi", "Real");
        if (!empty($Where['SeriesID'])) {
            $this->db->where("S.SeriesID", $Where['SeriesID']);
        }
        if (!empty($Where['SeriesEndDate'])) {
            $this->db->where("S.SeriesEndDate", $Where['SeriesEndDate']);
        }
        if (!empty($Where['MatchID'])) {
            $this->db->where("M.MatchID", $Where['MatchID']);
        }
        if (!empty($Where['LivePlayerStatusUpdate'])) {
            $this->db->where("M.LivePlayerStatusUpdate", $Where['LivePlayerStatusUpdate']);
        }
        if (!empty($Where['RoundID'])) {
            $this->db->where("M.RoundID", $Where['RoundID']);
        }
        if (!empty($Where['MatchIDLive'])) {
            $this->db->where("M.MatchIDLive", $Where['MatchIDLive']);
        }
        if (!empty($Where['PlayerStatsUpdate'])) {
            $this->db->where("M.PlayerStatsUpdate", $Where['PlayerStatsUpdate']);
        }
        if (!empty($Where['MatchCompleteDateTime'])) {
            $this->db->where("M.MatchCompleteDateTime <", $Where['MatchCompleteDateTime']);
        }
        if (!empty($Where['MatchTypeID'])) {
            $this->db->where("M.MatchTypeID", $Where['MatchTypeID']);
        }
        if (!empty($Where['IsPlayingXINotificationSent'])) {
            $this->db->where("M.IsPlayingXINotificationSent", $Where['IsPlayingXINotificationSent']);
        }
        if (!empty($Where['TeamIDLocal'])) {
            $this->db->where("M.TeamIDLocal", $Where['TeamIDLocal']);
        }
        if (!empty($Where['IsPreSquad'])) {
            $this->db->where("M.IsPreSquad", $Where['IsPreSquad']);
        }
        if (!empty($Where['TeamIDVisitor'])) {
            $this->db->where("M.TeamIDVisitor", $Where['TeamIDVisitor']);
        }
        if (!empty($Where['IsPlayerPointsUpdated'])) {
            $this->db->where("M.IsPlayerPointsUpdated", $Where['IsPlayerPointsUpdated']);
        }
        if (!empty($Where['MatchStartDateTime'])) {
            $this->db->where("M.MatchStartDateTime <=", $Where['MatchStartDateTime']);
        }
        if (!empty($Where['MatchStartDateTimeComplete'])) {
            $this->db->where("M.MatchStartDateTime >=", $Where['MatchStartDateTimeComplete']);
        }
        if (!empty($Where['Filter']) && $Where['Filter'] == 'AddDays') {
            // $this->db->where_in("DATE(M.MatchStartDateTime)", array(date('Y-m-d', strtotime('+10 day', strtotime(date('Y-m-d')))), date('Y-m-d', strtotime('+9 day', strtotime(date('Y-m-d')))), date('Y-m-d', strtotime('+8 day', strtotime(date('Y-m-d')))), date('Y-m-d', strtotime('+7 day', strtotime(date('Y-m-d')))), date('Y-m-d', strtotime('+6 day', strtotime(date('Y-m-d')))), date('Y-m-d', strtotime('+5 day', strtotime(date('Y-m-d')))), date('Y-m-d', strtotime('+4 day', strtotime(date('Y-m-d')))), date('Y-m-d', strtotime('+3 day', strtotime(date('Y-m-d')))), date('Y-m-d', strtotime('+2 day', strtotime(date('Y-m-d')))), date('Y-m-d', strtotime('+1 day', strtotime(date('Y-m-d')))), date('Y-m-d')));
            $this->db->where('DATE(M.MatchStartDateTime) >=', date('Y-m-d'));
            $this->db->where('DATE(M.MatchStartDateTime) <=', date('Y-m-d', strtotime('+7 day', strtotime(date('Y-m-d')))));
        }
        if (!empty($Where['MatchStartDateTimeBetweenLive'])) {
            $MatchStartDateTimeBetween = $Where['MatchStartDateTimeBetweenLive'];
            $MatchStartDateTimeCurrent = date('Y-m-d H:i', strtotime(date('Y-m-d H:i')));
            $this->db->where("M.MatchStartDateTime >=", $MatchStartDateTimeBetween);
            $this->db->where("M.MatchStartDateTime <=", $MatchStartDateTimeCurrent);
        }
        if (!empty($Where['isReminderNotificationSent'])) {
            $this->db->where("isReminderNotificationSent", $Where['isReminderNotificationSent']);
        }
        if (!empty($Where['Filter']) && $Where['Filter'] == 'Today') {
            // $this->db->where("DATE(M.MatchStartDateTime)", date('Y-m-d'));
        }
        if (!empty($Where['IsPlayerOrTimeAvl']) && $Where['IsPlayerOrTimeAvl'] == 'Yes') {
            $CurrentPreviewTime = date('Y-m-d H:i', strtotime('+1 hours', strtotime(date('Y-m-d H:i'))));
            $this->db->where("M.MatchStartDateTime >", $CurrentPreviewTime);
        }
        if (!empty($Where['Filter']) && $Where['Filter'] == 'TodayMatch') {
            $this->db->where("DATE(M.MatchStartDateTime)", date('Y-m-d'));
        }
        if (!empty($Where['Filter']) && $Where['Filter'] == 'Yesterday') {
            $this->db->where("M.MatchStartDateTime <=", date('Y-m-d H:i:s'));
        }
        if (!empty($Where['Filter']) && $Where['Filter'] == 'AddDays') {
            $this->db->where_in("DATE(M.MatchStartDateTime)", array(date('Y-m-d', strtotime('+7 day', strtotime(date('Y-m-d')))), date('Y-m-d', strtotime('+6 day', strtotime(date('Y-m-d')))), date('Y-m-d', strtotime('+5 day', strtotime(date('Y-m-d')))), date('Y-m-d', strtotime('+4 day', strtotime(date('Y-m-d')))), date('Y-m-d', strtotime('+3 day', strtotime(date('Y-m-d')))), date('Y-m-d', strtotime('+2 day', strtotime(date('Y-m-d')))), date('Y-m-d', strtotime('+1 day', strtotime(date('Y-m-d')))), date('Y-m-d')));
        }
        if (!empty($Where['Filter']) && $Where['Filter'] == 'YesterdayToday') {
            $this->db->where_in("DATE(M.MatchStartDateTime)", array(date('Y-m-d', strtotime('-1 day', strtotime(date('Y-m-d')))), date('Y-m-d')));
        }
        if (!empty($Where['Filter']) && $Where['Filter'] == 'MyJoinedMatch') {
            $this->db->where('EXISTS (select 1 from sports_contest_join J,sports_contest C where C.ContestID=J.ContestID AND J.MatchID = M.MatchID AND C.LeagueType="Draft" AND J.UserID=' . $Where['UserID'] . ')');
        }
        if (!empty($Where['StatusID'])) {
            if ($Where['StatusID'] == 2) {
                $Where['StatusID'] = array(2, 10);
                $this->db->where_in("E.StatusID", $Where['StatusID']);
            } else if ($Where['StatusID'] == 5) {
                $Where['StatusID'] = array(5, 8, 3);
                $this->db->where_in("E.StatusID", $Where['StatusID']);
            } else {
                $this->db->where_in("E.StatusID", $Where['StatusID']);
            }
        }
        if (!empty($Where['CronFilter']) && $Where['CronFilter'] == 'OneDayDiff') {
            $this->db->having("LastUpdateDiff", 0);
            $this->db->or_having("LastUpdateDiff >=", 86400); // 1 Day
        }
        /* if (!empty($Where['existingContests'])) {
          $StatusID = $Where['StatusID'];
          print_r($StatusID);
          $this->db->where('EXISTS (select MatchID from sports_contest where MatchID = M.MatchID AND E.StatusID IN(2,10)');
          } */

        if (!empty($Where['OrderBy']) && !empty($Where['Sequence'])) {
            $this->db->order_by($Where['OrderBy'], $Where['Sequence']);
        } else {
            if (!empty($Where['OrderByToday']) && $Where['OrderByToday'] == 'Yes') {
                $this->db->order_by('E.StatusID=1 DESC', null, FALSE);
                $this->db->order_by('M.MatchStartDateTime', "ASC");
                /* $this->db->order_by('DATE(M.MatchStartDateTime)="' . date('Y-m-d') . '" ASC', null, FALSE);
                  $this->db->order_by('E.StatusID=1 DESC', null, FALSE); */
            } else {
                $this->db->order_by('E.StatusID', 'ASC');
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
        // $this->db->cache_on(); //Turn caching on
        $Query = $this->db->get();
        //echo $this->db->last_query();exit;
        if ($multiRecords) {
            if ($Query->num_rows() > 0) {
                $Records = array();
                foreach ($Query->result_array() as $key => $Record) {
                    $Records[] = $Record;
                    $Records[$key]['MatchScoreDetails'] = (!empty($Record['MatchScoreDetails'])) ? json_decode($Record['MatchScoreDetails'], TRUE) : new stdClass();

                    if ($Record['MatchDisplay'] == "Enable") {
                        $Records[$key]['ContestsAvailable'] = "Yes";
                        $Records[$key]['ContestAvailable'] = "Yes";
                    } else {
                        $Records[$key]['ContestsAvailable'] = "No";
                        $Records[$key]['ContestAvailable'] = "No";
                    }
                }
                $Return['Data']['Records'] = $Records;
            }
            if (!empty($Where['MyJoinedMatchesCount']) && $Where['MyJoinedMatchesCount'] == 1) {
                $Return['Data']['Statics'] = $this->db->query('SELECT (
                        SELECT COUNT(DISTINCT M.MatchID) AS `UpcomingJoinedContest` FROM `sports_matches` M
                            JOIN `sports_contest_join` J ON M.MatchID = J.MatchID JOIN `tbl_entity` E ON E.EntityID = M.MatchID WHERE E.StatusID = 1 AND J.UserID ="' . @$Where['UserID'] . '" 
                        )as UpcomingJoinedContest,
                        ( SELECT COUNT(DISTINCT M.MatchID) AS `LiveJoinedContest` FROM `sports_matches` M JOIN `sports_contest_join` J ON M.MatchID = J.MatchID JOIN `tbl_entity` E ON E.EntityID = M.MatchID WHERE  E.StatusID IN (2,10) AND J.UserID = "' . @$Where['UserID'] . '" 
                        )as LiveJoinedContest,
                        ( SELECT COUNT(DISTINCT J.MatchID) AS `CompletedJoinedContest` FROM `sports_contest_join` J, `tbl_entity` E, `sports_matches` M WHERE E.EntityID = M.MatchID AND J.MatchID=M.MatchID AND E.StatusID IN (5,10) AND J.UserID = "' . @$Where['UserID'] . '" 
                    )as CompletedJoinedContest'
                        )->row();
            }
            return $Return;
        } else {
            if ($Query->num_rows() > 0) {
                $Record = $Query->row_array();
                $Record['MatchScoreDetails'] = (!empty($Record['MatchScoreDetails'])) ? json_decode($Record['MatchScoreDetails'], TRUE) : new stdClass();
                if ($Record['MatchDisplay'] == "Enable") {
                    $Record['ContestsAvailable'] = "Yes";
                    $Record['ContestAvailable'] = "Yes";
                    $Record['TeamPlayersAvailable'] = "Yes";
                } else {
                    $Record['ContestsAvailable'] = "No";
                    $Record['ContestAvailable'] = "No";
                    $Record['TeamPlayersAvailable'] = "No";
                }
                return $Record;
            }
        }
        return FALSE;
    }

    function autoCreateAuctionDraftOld($ContestID = '') {


        $Input = $this->db->query('SELECT * FROM sports_contest WHERE ContestID = ' . $ContestID . ' LIMIT 1')->result_array()[0];
//        if ($Input['IsAutoCreate'] == "No") {
//            return true;
//        }
        $defaultCustomizeWinningObj = new stdClass();
        $defaultCustomizeWinningObj->From = 1;
        $defaultCustomizeWinningObj->To = 1;
        $defaultCustomizeWinningObj->Percent = 100;
        $defaultCustomizeWinningObj->WinningAmount = @$Input['WinningAmount'];


        $MatchDetails = $this->db->query("SELECT MatchStartDateTime
                FROM sports_matches
                WHERE sports_matches.MatchID =  '" . $Input['MatchID'] . "' order by MatchStartDateTime asc limit 1")->result_array()[0];
        $ExtendDate = date('Y-m-d H:i:s', strtotime('+2 hours', strtotime($Input['LeagueJoinDateTime'])));
        $MatchStartDate = date('Y-m-d H:i:s', strtotime($MatchDetails['MatchStartDateTime']));
        if ($ExtendDate > $MatchStartDate) {
            return true;
        }

        $LeagueJoinDateTime = strtotime($Input['LeagueJoinDateTime']) + strtotime('+360 minutes', 0);
        $InsertData = array_filter(array(
            "ContestName" => @$Input['ContestName'],
            "LeagueType" => @$Input['LeagueType'],
            "LeagueJoinDateTime" => date('Y-m-d H:i', $LeagueJoinDateTime),
            "AuctionUpdateTime" => date('Y-m-d H:i', $LeagueJoinDateTime),
            "ContestFormat" => @$Input['ContestFormat'],
            "ContestType" => @$Input['ContestType'],
            "PlayerSubmitCriteria" => @$Input['PlayerSubmitCriteria'],
            "Privacy" => @$Input['Privacy'],
            "PreContestID" => $Input['PreContestID'],
            "IsPaid" => @$Input['IsPaid'],
            "RoundID" => @$Input['RoundID'],
            "IsConfirm" => @$Input['IsConfirm'],
            "IsAutoCreate" => @$Input['IsAutoCreate'],
            "ShowJoinedContest" => @$Input['ShowJoinedContest'],
            "WinningAmount" => @$Input['WinningAmount'],
            "GameType" => @$Input['GameType'],
            "GameTimeLive" => @$Input['GameTimeLive'],
            "AdminPercent" => @$Input['AdminPercent'],
            "ContestSize" => (@$Input['ContestFormat'] == 'Head to Head') ? 2 : @$Input['ContestSize'],
            "EntryFee" => (@$Input['IsPaid'] == 'Yes') ? @$Input['EntryFee'] : 0,
            "NoOfWinners" => (@$Input['IsPaid'] == 'Yes') ? @$Input['NoOfWinners'] : 1,
            "EntryType" => @$Input['EntryType'],
            "UserJoinLimit" => (@$Input['EntryType'] == 'Multiple') ? @$Input['UserJoinLimit'] : 1,
            "CashBonusContribution" => @$Input['CashBonusContribution'],
            "CustomizeWinning" => (@$Input['IsPaid'] == 'Yes') ? @$Input['CustomizeWinning'] : json_encode(array($defaultCustomizeWinningObj)),
            "UserInvitationCode" => random_string('alnum', 6),
            "MinimumUserJoined" => @$Input['MinimumUserJoined'],
            "DraftTotalRounds" => @$Input['DraftTeamPlayerLimit'],
            "DraftTeamPlayerLimit" => @$Input['DraftTeamPlayerLimit'],
            "DraftPlayerSelectionCriteria" => @$Input['DraftPlayerSelectionCriteria'],
            "IsCVC" => @$Input['IsCVC'],
            "TeamPlayerPointCalcriteria" => @$Input['TeamPlayerPointCalcriteria']
        ));
        //$ContestID = $this->addContestDraft($InsertData, '125', Null, $Input['RoundID']);
        $ContestID = $this->addContest($InsertData, "125", $Input['MatchID'], $Input['SeriesID']);
        return $ContestID;
    }

    function autoCreateAuctionDraft($ContestID = '') {
        $Input = $this->db->query('SELECT * FROM sports_contest WHERE ContestID = ' . $ContestID . ' LIMIT 1')->result_array()[0];
        $defaultCustomizeWinningObj = new stdClass();
        $defaultCustomizeWinningObj->From = 1;
        $defaultCustomizeWinningObj->To = 1;
        $defaultCustomizeWinningObj->Percent = 100;
        $defaultCustomizeWinningObj->WinningAmount = @$Input['WinningAmount'];
        $MatchDetails = $this->db->query("SELECT MatchStartDateTime
                FROM sports_matches
                WHERE sports_matches.MatchID =  '" . $Input['MatchID'] . "' order by MatchStartDateTime asc limit 1")->result_array()[0];

        $ExtendDate = date('Y-m-d H:i:s', strtotime('+3 hours', strtotime($Input['LeagueJoinDateTime'])));
        $MatchStartDate = date('Y-m-d H:i:s', strtotime($MatchDetails['MatchStartDateTime']));
        $LeagueJoinDateTime = date('Y-m-d H:i:s', strtotime($Input['LeagueJoinDateTime']) + strtotime('+30 minutes', 0));

        $start_date = new DateTime($MatchStartDate);
        $since_start = $start_date->diff(new DateTime($LeagueJoinDateTime));

        $minutes = $since_start->days * 24 * 60;
        $minutes += $since_start->h * 60;
        $minutes += $since_start->i;
        if ($minutes < 70) {
            return true;
        }
        $LeagueJoinDateTime = strtotime($Input['LeagueJoinDateTime']) + strtotime('+375 minutes', 0);
        $InsertData = array_filter(array(
            "ContestName" => @$Input['ContestName'],
            "LeagueType" => @$Input['LeagueType'],
            "LeagueJoinDateTime" => date('Y-m-d H:i', $LeagueJoinDateTime),
            "AuctionUpdateTime" => date('Y-m-d H:i', $LeagueJoinDateTime),
            "ContestFormat" => @$Input['ContestFormat'],
            "ContestType" => @$Input['ContestType'],
            "PlayerSubmitCriteria" => @$Input['PlayerSubmitCriteria'],
            "Privacy" => @$Input['Privacy'],
            "PreContestID" => $Input['PreContestID'],
            "IsPaid" => @$Input['IsPaid'],
            "RoundID" => @$Input['RoundID'],
            "IsConfirm" => @$Input['IsConfirm'],
            "IsAutoCreate" => @$Input['IsAutoCreate'],
            "ShowJoinedContest" => @$Input['ShowJoinedContest'],
            "WinningAmount" => @$Input['WinningAmount'],
            "GameType" => @$Input['GameType'],
            "GameTimeLive" => @$Input['GameTimeLive'],
            "AdminPercent" => @$Input['AdminPercent'],
            "ContestSize" => (@$Input['ContestFormat'] == 'Head to Head') ? 2 : @$Input['ContestSize'],
            "EntryFee" => (@$Input['IsPaid'] == 'Yes') ? @$Input['EntryFee'] : 0,
            "NoOfWinners" => (@$Input['IsPaid'] == 'Yes') ? @$Input['NoOfWinners'] : 1,
            "EntryType" => @$Input['EntryType'],
            "UserJoinLimit" => (@$Input['EntryType'] == 'Multiple') ? @$Input['UserJoinLimit'] : 1,
            "CashBonusContribution" => @$Input['CashBonusContribution'],
            "CustomizeWinning" => (@$Input['IsPaid'] == 'Yes') ? @$Input['CustomizeWinning'] : json_encode(array($defaultCustomizeWinningObj)),
            "UserInvitationCode" => random_string('alnum', 6),
            "MinimumUserJoined" => @$Input['MinimumUserJoined'],
            "DraftTotalRounds" => @$Input['DraftTeamPlayerLimit'],
            "DraftTeamPlayerLimit" => @$Input['DraftTeamPlayerLimit'],
            "DraftPlayerSelectionCriteria" => @$Input['DraftPlayerSelectionCriteria'],
            "IsCVC" => @$Input['IsCVC'],
            "TeamPlayerPointCalcriteria" => @$Input['TeamPlayerPointCalcriteria']
        ));
        $ContestID = $this->addContest($InsertData, "125", $Input['MatchID'], $Input['SeriesID']);
        return $ContestID;
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
                'StatusID' => 'E.StatusID',
                'ContestID' => 'C.ContestID',
                'ContestGUID' => 'C.ContestGUID',
                'Privacy' => 'C.Privacy',
                'IsPaid' => 'C.IsPaid',
                'GameType' => 'C.GameType',
                'RoundID' => 'C.RoundID',
                'AuctionUpdateTime' => 'C.AuctionUpdateTime',
                'AuctionBreakDateTime' => 'C.AuctionBreakDateTime',
                'AuctionTimeBreakAvailable' => 'C.AuctionTimeBreakAvailable',
                'AuctionIsBreakTimeStatus' => 'C.AuctionIsBreakTimeStatus',
                'LeagueType' => 'IF(C.LeagueType = "Draft", "Snake Draft", "Auction Draft") as LeagueType',
                'LeagueJoinDateTime' => 'CONVERT_TZ(C.LeagueJoinDateTime,"+00:00","' . DEFAULT_TIMEZONE . '") AS LeagueJoinDateTime',
                'LeagueJoinDateTimeUTC' => 'C.LeagueJoinDateTime as LeagueJoinDateTimeUTC',
                'GameTimeLive' => 'C.GameTimeLive',
                'AdminPercent' => 'C.AdminPercent',
                'IsConfirm' => 'C.IsConfirm',
                'ShowJoinedContest' => 'C.ShowJoinedContest',
                'WinningAmount' => 'C.WinningAmount',
                'ContestSize' => 'C.ContestSize',
                'ContestFormat' => 'C.ContestFormat',
                'ContestType' => 'C.ContestType',
                'MatchID' => 'C.MatchID',
                'MatchGUID' => 'M.MatchGUID',
                'CustomizeWinning' => 'C.CustomizeWinning',
                'EntryFee' => 'C.EntryFee',
                'NoOfWinners' => 'C.NoOfWinners',
                'EntryType' => 'C.EntryType',
                'UserJoinLimit' => 'C.UserJoinLimit',
                'DraftTotalRounds' => 'C.DraftTotalRounds',
                'MinimumUserJoined' => 'C.MinimumUserJoined',
                'CashBonusContribution' => 'C.CashBonusContribution',
                'EntryType' => 'C.EntryType',
                'IsWinningDistributed' => 'C.IsWinningDistributed',
                'UserInvitationCode' => 'C.UserInvitationCode',
                'DraftLiveRound' => 'C.DraftLiveRound',
                'SeriesID' => 'C.SeriesID',
                'DraftUserLimit' => 'S.DraftUserLimit',
                'DraftTeamPlayerLimit' => 'C.DraftTeamPlayerLimit',
                'DraftPlayerSelectionCriteria' => 'C.DraftPlayerSelectionCriteria',
                'SeriesGUID' => 'S.SeriesGUID',
                'SeriesName' => 'S.SeriesName',
                'MatchType' => 'MT.MatchTypeName AS MatchType',
                'IsJoined' => '(SELECT IF( EXISTS(
                                SELECT EntryDate FROM sports_contest_join_completed
                                WHERE sports_contest_join_completed.ContestID =  C.ContestID AND UserID = ' . @$Where['SessionUserID'] . ' LIMIT 1), "Yes", "No")) AS IsJoined',
                'TotalJoined' => '(SELECT COUNT(0) FROM sports_contest_join_completed
                                WHERE sports_contest_join_completed.ContestID =  C.ContestID) AS TotalJoined',
                'StatusID' => 'E.StatusID',
                'AuctionStatusID' => 'C.AuctionStatusID',
                'AuctionStatus' => 'CASE C.AuctionStatusID
                             when "1" then "Pending"
                             when "2" then "Running"
                             when "5" then "Completed"
                             when "3" then "Cancelled"
                             END as AuctionStatus',
                'Status' => 'CASE E.StatusID
                             when "1" then "Pending"
                             when "2" then "Running"
                             when "3" then "Cancelled"
                             when "5" then "Completed"
                             END as Status'
            );
            if ($Params) {
                foreach ($Params as $Param) {
                    $Field .= (!empty($FieldArray[$Param]) ? ',' . $FieldArray[$Param] : '');
                }
            }
        }
        if (!empty($Where['TotalJoinedByRound']) && $Where['TotalJoinedByRound'] == 'Yes' && $Where['RoundID']) {
            $TotalJoinedByRound = $this->db->query("SELECT COUNT(0) as TotalJoinedByRound FROM sports_contest_join_completed, sports_contest_completed C WHERE sports_contest_join_completed.ContestID =  C.ContestID  AND LeagueType = 'Draft' AND sports_contest_join_completed.UserID = '" . @$Where['SessionUserID'] . "' AND sports_contest_join_completed.MatchID = '" . @$Where['MatchID'] . "'");
            $TotalJoinedByRound = $TotalJoinedByRound->row()->TotalJoinedByRound;
            $Return['Data']['TotalJoinedByRound'] = $TotalJoinedByRound;
        }
        $this->db->select('C.ContestGUID,C.ContestName,S.SeriesID,C.RoundID');
        if (!empty($Field)) $this->db->select($Field, FALSE);
        $this->db->from('tbl_entity E, sports_contest_completed C,sports_series S,sports_matches M,sports_set_match_types MT');
        $this->db->where("C.ContestID", "E.EntityID", FALSE);
        $this->db->where("S.SeriesID", "C.SeriesID", FALSE);
        $this->db->where("M.MatchID", "C.MatchID", FALSE);
        $this->db->where("MT.MatchTypeID", "M.MatchTypeID", FALSE);
        $this->db->where("C.LeagueType !=", 'Dfs');
        $this->db->where("C.LeagueType", 'Draft');
        if (!empty($Where['Keyword'])) {
            $Where['Keyword'] = $Where['Keyword'];
            $this->db->group_start();
            $this->db->like("C.ContestName", $Where['Keyword']);
            $this->db->or_like("S.SeriesName", $Where['Keyword']);
            $this->db->group_end();
        }
        if (!empty($Where['ContestID'])) {
            $this->db->where("C.ContestID", $Where['ContestID']);
        }
        if (!empty($Where['RoundID'])) {
            $this->db->where("C.RoundID", $Where['RoundID']);
        }
        if (!empty($Where['MatchID'])) {
            $this->db->where("C.MatchID", $Where['MatchID']);
        }
        if (!empty($Where['ContestType'])) {
            $this->db->where("C.ContestType", $Where['ContestType']);
        }
        if (!empty($Where['ContestGUID'])) {
            $this->db->where("C.ContestGUID", $Where['ContestGUID']);
        }
        if (!empty($Where['LeagueType'])) {
            $this->db->where("C.LeagueType", $Where['LeagueType']);
        }
        if (!empty($Where['AuctionStatusID'])) {
            $this->db->where("C.AuctionStatusID", $Where['AuctionStatusID']);
        }
        if (!empty($Where['UserID'])) {
            $this->db->where("C.UserID", $Where['UserID']);
        }
        if (!empty($Where['Filter']) && $Where['Filter'] == 'Today') {
            $this->db->where("DATE(M.MatchStartDateTime)", date('Y-m-d'));
        }

        if (!empty($Where['Filter']) && $Where['Filter'] == 'LiveAuction') {
            $CurrentDatetime = strtotime(date('Y-m-d H:i:s')) + 3600;
            $NextTime = date("Y-m-d H:i:s");
            $CurrentDatetime = strtotime(date('Y-m-d H:i:s')) - 3600;
            $PreTime = date("Y-m-d H:i:s", $CurrentDatetime);
            $this->db->where("C.LeagueJoinDateTime <=", $NextTime);
            //$this->db->where("C.LeagueJoinDateTime >=", $PreTime);
        }
        if (!empty($Where['Privacy']) && $Where['Privacy'] != 'All') {
            $this->db->where("C.Privacy", $Where['Privacy']);
        }
        if (!empty($Where['ContestType'])) {
            $this->db->where("C.ContestType", $Where['ContestType']);
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
        if (!empty($Where['AutionInLive']) && $Where['AutionInLive'] == "Yes") {
            $this->db->where("C.LeagueJoinDateTime <=", date('Y-m-d H:i:s'));
            $this->db->where("C.AuctionUpdateTime <=", date('Y-m-d H:i:s'));
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
        if (!empty($Where['SeriesID'])) {
            $this->db->where("C.SeriesID", $Where['SeriesID']);
        }
        if (!empty($Where['StatusID'])) {
            $this->db->where_in("E.StatusID", $Where['StatusID']);
        }
        if (!empty($Where['AuctionStatusID'])) {
            $this->db->where_in("C.AuctionStatusID", $Where['AuctionStatusID']);
        }

        if (isset($Where['MyJoinedContest']) && $Where['MyJoinedContest'] = "Yes") {
            $this->db->where('EXISTS (select ContestID from sports_contest_join_completed JE where JE.ContestID = C.ContestID AND JE.UserID=' . @$Where['SessionUserID'] . ')');
        } else {
            if ($Where['StatusID'] != 3) {
                $this->db->where("E.StatusID !=", 3);
            }
        }
        if (!empty($Where['UserInvitationCode'])) {
            $this->db->where("C.UserInvitationCode", $Where['UserInvitationCode']);
        }
        if (!empty($Where['IsWinningDistributed'])) {
            $this->db->where("C.IsWinningDistributed", $Where['IsWinningDistributed']);
        }
        if (!empty($Where['ContestFull']) && $Where['ContestFull'] == 'No') {
            $this->db->having("TotalJoined !=", 'C.ContestSize', FALSE);
        }
        if (!empty($Where['OrderBy']) && !empty($Where['Sequence'])) {
            $this->db->order_by($Where['OrderBy'], $Where['Sequence']);
        }
        $this->db->order_by('C.LeagueJoinDateTime', 'DESC');

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
//        echo $this->db->last_query();
//        exit;
        if ($Query->num_rows() > 0) {
            if ($multiRecords) {
                $Records = array();
                $defaultCustomizeWinningObj = new stdClass();
                $defaultCustomizeWinningObj->From = 1;
                $defaultCustomizeWinningObj->To = 1;
                $defaultCustomizeWinningObj->Percent = 100;
                $defaultCustomizeWinningObj->WinningAmount = $Record['WinningAmount'];
                foreach ($Query->result_array() as $key => $Record) {

                    $Records[] = $Record;
                    $Records[$key]['CustomizeWinning'] = (!empty($Record['CustomizeWinning'])) ? json_decode($Record['CustomizeWinning'], TRUE) : array($defaultCustomizeWinningObj);
                    //$Records[$key]['MatchScoreDetails'] = (!empty($Record['MatchScoreDetails'])) ? json_decode($Record['MatchScoreDetails'], TRUE) : new stdClass();
                    $TotalAmountReceived = $this->getTotalContestCollectionsCompleted($Record['ContestGUID']);
                    $Records[$key]['TotalAmountReceived'] = ($TotalAmountReceived) ? $TotalAmountReceived : 0;
                    $TotalWinningAmount = $this->getTotalWinningAmountCompleted($Record['ContestGUID']);
                    $Records[$key]['TotalWinningAmount'] = ($TotalWinningAmount) ? $TotalWinningAmount : 0;
                    $Records[$key]['NoOfWinners'] = ($Record['NoOfWinners'] == 0 ) ? 1 : $Record['NoOfWinners'];
                    $Records[$key]['IsSeriesMatchStarted'] = "No";
                    if (in_array('DraftPlayerSelectionCriteria', $Params)) {
                        $Records[$key]['DraftPlayerSelectionCriteria'] = (!empty($Record['DraftPlayerSelectionCriteria'])) ? json_decode($Record['DraftPlayerSelectionCriteria'], TRUE) : array();
                    }
                    if (isset($Where['MyJoinedContest']) && $Where['MyJoinedContest'] == "Yes") {
                        $Records[$key]['IsAuctionFinalTeamSubmitted'] = "No";
                        /** to check auction user final team submitted * */
                        $this->db->select("UserTeamID");
                        $this->db->from('sports_users_teams_completed');
                        $this->db->where("ContestID", $Record['ContestID']);
                        $this->db->where("UserID", @$Where['SessionUserID']);
                        $this->db->where("IsPreTeam", "No");
                        $this->db->where("IsAssistant", "No");
                        $this->db->where("AuctionTopPlayerSubmitted", "Yes");
                        $this->db->where("UserTeamType", "Draft");
                        $this->db->limit(1);
                        $Query = $this->db->get();
                        if ($Query->num_rows() > 0) {
                            $Records[$key]['IsAuctionFinalTeamSubmitted'] = "Yes";
                        }

                        if (isset($Where['MyStats']) && $Where['MyStats'] = "Yes") {
                            $this->db->select("TotalPoints,UserRank,UserWinningAmount,AuctionBudget");
                            $this->db->from('sports_contest_join_completed');
                            $this->db->where("ContestID", $Record['ContestID']);
                            $this->db->where("UserID", @$Where['SessionUserID']);
                            $this->db->limit(1);
                            $Query = $this->db->get();
                            if ($Query->num_rows() > 0) {
                                $JoinContest = $Query->row_array();
                                $Records[$key]['TotalPoints'] = $JoinContest['TotalPoints'];
                                $Records[$key]['UserRank'] = $JoinContest['UserRank'];
                                $Records[$key]['UserWinningAmount'] = $JoinContest['UserWinningAmount'];
                                $Records[$key]['AuctionBudget'] = 1000000000 - $JoinContest['AuctionBudget'];
                            }
                        }
                    }

                    /** to check series stared or not * */
                    if (isset($Where['IsSeriesStarted']) && $Where['IsSeriesStarted'] == "Yes") {
                        $this->db->select("MatchID,MatchStartDateTime");
                        $this->db->from('sports_matches');
                        $this->db->where("MatchID", $Record['MatchID']);
//                        $this->db->where("RoundID", $Where['RoundID']);
                        $this->db->order_by("MatchStartDateTime", "ASC");
                        $this->db->limit(1);
                        $Query = $this->db->get();
                        if ($Query->num_rows() > 0) {
                            $MatchDetails = $Query->row_array();
                            $CurrentDateTime = strtotime(date('Y-m-d H:i:s'));
                            $MatchDateTime = strtotime($MatchDetails["MatchStartDateTime"]);
                            if ($CurrentDateTime >= $MatchDateTime) {
                                $Records[$key]['IsSeriesMatchStarted'] = "Yes";
                            }
                        }
                    }
                }

                $Return['Data']['Records'] = $Records;
            } else {
                $Record = $Query->row_array();
                $Record['CustomizeWinning'] = (!empty($Record['CustomizeWinning'])) ? json_decode($Record['CustomizeWinning'], TRUE) : new stdClass();
                $TotalAmountReceived = $this->getTotalContestCollectionsCompleted($Record['ContestGUID']);
                $Record['TotalAmountReceived'] = ($TotalAmountReceived) ? $TotalAmountReceived : 0;
                $TotalWinningAmount = $this->getTotalWinningAmountCompleted($Record['ContestGUID']);
                $Record['TotalWinningAmount'] = ($TotalWinningAmount) ? $TotalWinningAmount : 0;
                if (in_array('DraftPlayerSelectionCriteria', $Params)) {
                    $Record['DraftPlayerSelectionCriteria'] = (!empty($Record['DraftPlayerSelectionCriteria'])) ? json_decode($Record['DraftPlayerSelectionCriteria'], TRUE) : array();
                }
                /** to check series type * */
                if (isset($Where['DraftSeriesType']) && $Where['DraftSeriesType'] == "Yes") {
                    $this->db->select("RoundFormat");
                    $this->db->from('sports_series_rounds');
                    $this->db->where("RoundID", $Where['RoundID']);
                    $this->db->limit(1);
                    $Query = $this->db->get();
                    if ($Query->num_rows() > 0) {
                        $MatchDetails = $Query->row_array();
                        $Record['DraftSeriesType'] = strtolower($MatchDetails['RoundFormat']);
                    }
                }
                $Record['IsSeriesMatchStarted'] = "No";
                /** to check series stared or not * */
                if (isset($Where['IsSeriesStarted']) && $Where['IsSeriesStarted'] == "Yes") {
                    $this->db->select("MatchID,MatchStartDateTime");
                    $this->db->from('sports_matches');
                    $this->db->where("MatchID", $Record['MatchID']);
//                    $this->db->where("SeriesID", $Record['SeriesID']);
//                    $this->db->where("RoundID", $Where['RoundID']);
                    $this->db->order_by("MatchStartDateTime", "ASC");
                    $this->db->limit(1);
                    $Query = $this->db->get();
                    if ($Query->num_rows() > 0) {
                        $MatchDetails = $Query->row_array();
                        $CurrentDateTime = strtotime(date('Y-m-d H:i:s'));
                        $MatchDateTime = strtotime($MatchDetails["MatchStartDateTime"]);
                        if ($CurrentDateTime >= $MatchDateTime) {
                            $Record['IsSeriesMatchStarted'] = "Yes";
                        }
                    }
                }
                return $Record;
            }
        }
        if (!empty($Where['MatchID'])) {
            $Return['Data']['Statics'] = $this->db->query('SELECT (SELECT COUNT(*) AS `NormalContest` FROM `sports_contest_completed` C, `tbl_entity` E WHERE C.ContestID = E.EntityID AND E.StatusID IN (1,2,5) AND C.MatchID = "' . $Where['MatchID'] . '" AND C.ContestType="Normal" AND C.ContestFormat="League" AND C.ContestSize != (SELECT COUNT(*) from sports_contest_join_completed where sports_contest_join_completed.ContestID = C.ContestID)
                                    )as NormalContest,
                    ( SELECT COUNT(*) AS `ReverseContest` FROM `sports_contest_completed` C, `tbl_entity` E WHERE C.ContestID = E.EntityID AND E.StatusID IN(1,2,5) AND C.MatchID = "' . $Where['MatchID'] . '" AND C.ContestType="Reverse" AND C.ContestFormat="League" AND C.ContestSize != (SELECT COUNT(*) from sports_contest_join_completed where sports_contest_join_completed.ContestID = C.ContestID)
                    )as ReverseContest,(
                    SELECT COUNT(*) AS `JoinedContest` FROM `sports_contest_join_completed` J, `sports_contest_completed` C,tbl_entity E WHERE C.ContestID = J.ContestID AND J.UserID = "' . @$Where['SessionUserID'] . '" AND C.MatchID = "' . $Where['MatchID'] . '" AND C.ContestID = E.EntityID AND E.StatusID != 3 
                    )as JoinedContest,( 
                    SELECT COUNT(*) AS `TotalTeams` FROM `sports_users_teams_completed`WHERE UserID = "' . @$Where['SessionUserID'] . '" AND MatchID = "' . $Where['MatchID'] . '"
                ) as TotalTeams,(SELECT COUNT(*) AS `H2HContest` FROM `sports_contest_completed` C, `tbl_entity` E WHERE C.ContestID = E.EntityID AND E.StatusID IN (1,2,5) AND C.MatchID = "' . $Where['MatchID'] . '" AND C.ContestFormat="Head to Head" AND E.StatusID = 1 AND C.ContestSize != (SELECT COUNT(*) from sports_contest_join_completed where sports_contest_join_completed.ContestID = C.ContestID )) as H2HContests')->row();
        }
        $Return['Data']['Records'] = empty($Records) ? array() : $Records;
        return $Return;
    }

    function getTotalContestCollectionsCompleted($ContestGUID) {
        return $this->db->query('SELECT SUM(C.EntryFee) as TotalAmountReceived FROM sports_contest_completed C join sports_contest_join_completed J on C.ContestID = J.ContestID WHERE C.ContestGUID = "' . $ContestGUID . '"')->row()->TotalAmountReceived;
    }

    function getTotalWinningAmountCompleted($ContestGUID) {
        return $this->db->query('SELECT SUM(J.UserWinningAmount) as TotalWinningAmount FROM sports_contest_completed C join sports_contest_join_completed J on C.ContestID = J.ContestID WHERE C.ContestGUID = "' . $ContestGUID . '"')->row()->TotalWinningAmount;
    }

    /*
      Description: To get all matches Completed
     */

    function getMatchesCompleted($Field = '', $Where = array(), $multiRecords = FALSE, $PageNo = 1, $PageSize = 15) {
        $Params = array();
        if (!empty($Field)) {
            $Params = array_map('trim', explode(',', $Field));
            $Field = '';
            $FieldArray = array(
                'SeriesID' => 'S.SeriesID',
                'SeriesGUID' => 'S.SeriesGUID',
                'StatusID' => 'E.StatusID',
                'SeriesIDLive' => 'S.SeriesIDLive',
                'CompetitionStateKey' => 'S.CompetitionStateKey',
                'SeriesName' => 'S.SeriesName',
                'SeriesStartDate' => 'DATE_FORMAT(CONVERT_TZ(S.SeriesStartDate,"+00:00","' . DEFAULT_TIMEZONE . '"), "' . DATE_FORMAT . '") SeriesStartDate',
                'SeriesEndDate' => 'DATE_FORMAT(CONVERT_TZ(S.SeriesEndDate,"+00:00","' . DEFAULT_TIMEZONE . '"), "' . DATE_FORMAT . '") SeriesEndDate',
                'MatchID' => 'M.MatchID',
                'IsPlayingXINotificationSent' => 'M.IsPlayingXINotificationSent',
                'APIAutoTimeUpdate' => 'M.APIAutoTimeUpdate',
                'MatchIDLive' => 'M.MatchIDLive',
                'MatchTypeID' => 'M.MatchTypeID',
                'MatchNo' => 'M.MatchNo',
                'RoundID' => 'M.RoundID',
                'MatchLocation' => 'M.MatchLocation',
                'IsPreSquad' => 'M.IsPreSquad',
                'IsPlayerPointsUpdated' => 'M.IsPlayerPointsUpdated',
                'MatchScoreDetails' => 'M.MatchScoreDetails',
                'MatchStartDateTime' => 'DATE_FORMAT(CONVERT_TZ(M.MatchStartDateTime,"+00:00","' . DEFAULT_TIMEZONE . '"), "' . DATE_FORMAT . '") MatchStartDateTime',
                'CurrentDateTime' => 'DATE_FORMAT(CONVERT_TZ(Now(),"+00:00","' . DEFAULT_TIMEZONE . '"), "' . DATE_FORMAT . ' ") CurrentDateTime',
                'MatchDate' => 'DATE_FORMAT(CONVERT_TZ(M.MatchStartDateTime,"+00:00","' . DEFAULT_TIMEZONE . '"), "%Y-%m-%d") MatchDate',
                'MatchTime' => 'DATE_FORMAT(CONVERT_TZ(M.MatchStartDateTime,"+00:00","' . DEFAULT_TIMEZONE . '"), "%H:%i:%s") MatchTime',
                'MatchStartDateTimeUTC' => 'M.MatchStartDateTime as MatchStartDateTimeUTC',
                'ServerDateTimeUTC' => 'UTC_TIMESTAMP() as ServerDateTimeUTC',
                'TeamIDLocal' => 'TL.TeamID AS TeamIDLocal',
                'TeamIDVisitor' => 'TV.TeamID AS TeamIDVisitor',
                'TeamGUIDLocal' => 'TL.TeamGUID AS TeamGUIDLocal',
                'TeamGUIDVisitor' => 'TV.TeamGUID AS TeamGUIDVisitor',
                'TeamIDLiveLocal' => 'TL.TeamIDLive AS TeamIDLiveLocal',
                'TeamIDLiveVisitor' => 'TV.TeamIDLive AS TeamIDLiveVisitor',
                'TeamNameLocal' => 'TL.TeamName AS TeamNameLocal',
                'TeamNameVisitor' => 'TV.TeamName AS TeamNameVisitor',
                'TeamNameShortLocal' => 'TL.TeamNameShort AS TeamNameShortLocal',
                'TeamNameShortVisitor' => 'TV.TeamNameShort AS TeamNameShortVisitor',
                'TeamFlagLocal' => 'CONCAT("' . BASE_URL . '","uploads/TeamFlag/",TL.TeamFlag) as TeamFlagLocal',
                'TeamFlagVisitor' => 'CONCAT("' . BASE_URL . '","uploads/TeamFlag/",TV.TeamFlag) as TeamFlagVisitor',
                'TeamPlayersAvailable' => 'IF(
                                        (
                                        SELECT
                                            count(sports_team_players.PlayerID) as TPlayer
                                        FROM
                                            sports_team_players
                                        WHERE
                                            sports_team_players.MatchID = M.MatchID AND PlayerSalary > 0 HAVING TPlayer > 21
                                        LIMIT 1
                                    ) IS NOT NULL,
                                    "Yes",
                                    "No"
                                    ) AS TeamPlayersAvailable',
                'ContestAvailable' => 'IF(
                                        (
                                        SELECT
                                            count(sports_contest_completed.ContestID) as ContestID
                                        FROM
                                            sports_contest_completed
                                        WHERE
                                            sports_contest_completed.MatchID = M.MatchID HAVING ContestID > 0
                                        LIMIT 1
                                    ) IS NOT NULL,
                                    "Yes",
                                    "No"
                                    ) AS ContestAvailable',
                'MyTotalJoinedContest' => '(SELECT COUNT(DISTINCT sports_contest_join_completed.ContestID)
                                                FROM sports_contest_join_completed
                                                WHERE sports_contest_join_completed.MatchID =  M.MatchID AND UserID= ' . @$Where['UserID'] . ') AS MyTotalJoinedContest',
                'ContestsAvailable' => '(select count(C.ContestGUID) from sports_contest_completed C where C.MatchID = M.MatchID AND E.StatusID=1) as ContestsAvailable',
                'Status' => 'CASE E.StatusID
                when "1" then "Pending"
                when "2" then "Running"
                when "3" then "Cancelled"
                when "5" then "Completed"  
                when "8" then "Abandoned"  
                when "9" then "No Result" 
                when "10" then "Reviewing" 
                END as Status',
                'MatchType' => 'MT.MatchTypeName AS MatchType',
                'isReminderNotificationSent' => 'M.isReminderNotificationSent',
                'LastUpdateDiff' => 'IF(M.LastUpdatedOn IS NULL, 0, TIME_TO_SEC(TIMEDIFF("' . date('Y-m-d H:i:s') . '", M.LastUpdatedOn))) LastUpdateDiff',
                'TotalUserWinning' => '(select SUM(UserWinningAmount) from sports_contest_join_completed where MatchID = M.MatchID AND UserID=' . @$Where['UserID'] . ') as TotalUserWinning',
                'isJoinedContest' => '(select count(*) from sports_contest_join_completed where MatchID = M.MatchID AND UserID = "' . @$Where['SessionUserID'] . '" AND E.StatusID=' . @$Where['StatusID'] . ') as JoinedContests'
            );
            if ($Params) {
                foreach ($Params as $Param) {
                    $Field .= (!empty($FieldArray[$Param]) ? ',' . $FieldArray[$Param] : '');
                }
            }
        }
        $this->db->select('M.MatchGUID,TL.TeamName AS TeamNameLocal,TV.TeamName AS TeamNameVisitor,TL.TeamNameShort AS TeamNameShortLocal,TV.TeamNameShort AS TeamNameShortVisitor');
        if (!empty($Field)) $this->db->select($Field, FALSE);
        $this->db->from('tbl_entity E, sports_series S, sports_matches M, sports_teams TL, sports_teams TV, sports_set_match_types MT');
        $this->db->where("M.SeriesID", "S.SeriesID", FALSE);
        $this->db->where("M.MatchID", "E.EntityID", FALSE);
        $this->db->where("M.MatchTypeID", "MT.MatchTypeID", FALSE);
        $this->db->where("M.TeamIDLocal", "TL.TeamID", FALSE);
        $this->db->where("M.TeamIDVisitor", "TV.TeamID", FALSE);
        if (!empty($Where['Keyword'])) {
            $this->db->group_start();
            $this->db->like("S.SeriesName", $Where['Keyword']);
            $this->db->or_like("M.MatchNo", $Where['Keyword']);
            $this->db->or_like("M.MatchLocation", $Where['Keyword']);
            $this->db->or_like("TL.TeamName", $Where['Keyword']);
            $this->db->or_like("TV.TeamName", $Where['Keyword']);
            $this->db->or_like("TL.TeamNameShort", $Where['Keyword']);
            $this->db->or_like("TV.TeamNameShort", $Where['Keyword']);
            $this->db->group_end();
        }
        $this->db->where("M.MatchTypeByApi", "Real");
        if (!empty($Where['SeriesID'])) {
            $this->db->where("S.SeriesID", $Where['SeriesID']);
        }
        if (!empty($Where['SeriesEndDate'])) {
            $this->db->where("S.SeriesEndDate", $Where['SeriesEndDate']);
        }
        if (!empty($Where['MatchID'])) {
            $this->db->where("M.MatchID", $Where['MatchID']);
        }
        if (!empty($Where['LivePlayerStatusUpdate'])) {
            $this->db->where("M.LivePlayerStatusUpdate", $Where['LivePlayerStatusUpdate']);
        }
        if (!empty($Where['RoundID'])) {
            $this->db->where("M.RoundID", $Where['RoundID']);
        }
        if (!empty($Where['MatchIDLive'])) {
            $this->db->where("M.MatchIDLive", $Where['MatchIDLive']);
        }
        if (!empty($Where['PlayerStatsUpdate'])) {
            $this->db->where("M.PlayerStatsUpdate", $Where['PlayerStatsUpdate']);
        }
        if (!empty($Where['MatchCompleteDateTime'])) {
            $this->db->where("M.MatchCompleteDateTime <", $Where['MatchCompleteDateTime']);
        }
        if (!empty($Where['MatchTypeID'])) {
            $this->db->where("M.MatchTypeID", $Where['MatchTypeID']);
        }
        if (!empty($Where['IsPlayingXINotificationSent'])) {
            $this->db->where("M.IsPlayingXINotificationSent", $Where['IsPlayingXINotificationSent']);
        }
        if (!empty($Where['TeamIDLocal'])) {
            $this->db->where("M.TeamIDLocal", $Where['TeamIDLocal']);
        }
        if (!empty($Where['IsPreSquad'])) {
            $this->db->where("M.IsPreSquad", $Where['IsPreSquad']);
        }
        if (!empty($Where['TeamIDVisitor'])) {
            $this->db->where("M.TeamIDVisitor", $Where['TeamIDVisitor']);
        }
        if (!empty($Where['IsPlayerPointsUpdated'])) {
            $this->db->where("M.IsPlayerPointsUpdated", $Where['IsPlayerPointsUpdated']);
        }
        if (!empty($Where['MatchStartDateTime'])) {
            $this->db->where("M.MatchStartDateTime <=", $Where['MatchStartDateTime']);
        }
        if (!empty($Where['MatchStartDateTimeComplete'])) {
            $this->db->where("M.MatchStartDateTime >=", $Where['MatchStartDateTimeComplete']);
        }
        if (!empty($Where['MatchStartDateTimeBetweenLive'])) {
            $MatchStartDateTimeBetween = $Where['MatchStartDateTimeBetweenLive'];
            $MatchStartDateTimeCurrent = date('Y-m-d H:i', strtotime(date('Y-m-d H:i')));
            $this->db->where("M.MatchStartDateTime >=", $MatchStartDateTimeBetween);
            $this->db->where("M.MatchStartDateTime <=", $MatchStartDateTimeCurrent);
        }
        if (!empty($Where['isReminderNotificationSent'])) {
            $this->db->where("isReminderNotificationSent", $Where['isReminderNotificationSent']);
        }
        if (!empty($Where['Filter']) && $Where['Filter'] == 'Today') {
            // $this->db->where("DATE(M.MatchStartDateTime)", date('Y-m-d'));
        }
        if (!empty($Where['Filter']) && $Where['Filter'] == 'TodayMatch') {
            $this->db->where("DATE(M.MatchStartDateTime)", date('Y-m-d'));
        }
        if (!empty($Where['Filter']) && $Where['Filter'] == 'Yesterday') {
            $this->db->where("M.MatchStartDateTime <=", date('Y-m-d H:i:s'));
        }
        if (!empty($Where['Filter']) && $Where['Filter'] == 'AddDays') {
            $this->db->where_in("DATE(M.MatchStartDateTime)", array(date('Y-m-d', strtotime('+7 day', strtotime(date('Y-m-d')))), date('Y-m-d', strtotime('+6 day', strtotime(date('Y-m-d')))), date('Y-m-d', strtotime('+5 day', strtotime(date('Y-m-d')))), date('Y-m-d', strtotime('+4 day', strtotime(date('Y-m-d')))), date('Y-m-d', strtotime('+3 day', strtotime(date('Y-m-d')))), date('Y-m-d', strtotime('+2 day', strtotime(date('Y-m-d')))), date('Y-m-d', strtotime('+1 day', strtotime(date('Y-m-d')))), date('Y-m-d')));
        }
        if (!empty($Where['Filter']) && $Where['Filter'] == 'YesterdayToday') {
            $this->db->where_in("DATE(M.MatchStartDateTime)", array(date('Y-m-d', strtotime('-1 day', strtotime(date('Y-m-d')))), date('Y-m-d')));
        }
        if (!empty($Where['Filter']) && $Where['Filter'] == 'MyJoinedMatch') {
            $this->db->where('EXISTS (select 1 from sports_contest_join_completed J,sports_contest_completed C where C.ContestID=J.ContestID AND J.MatchID = M.MatchID AND C.LeagueType="Draft" AND J.UserID=' . $Where['UserID'] . ')');
        }
        if (!empty($Where['StatusID'])) {
            if ($Where['StatusID'] == 2) {
                $Where['StatusID'] = array(2, 10);
                $this->db->where_in("E.StatusID", $Where['StatusID']);
            } else if ($Where['StatusID'] == 5) {
                $Where['StatusID'] = array(5, 8, 3);
                $this->db->where_in("E.StatusID", $Where['StatusID']);
            } else {
                $this->db->where_in("E.StatusID", $Where['StatusID']);
            }
        }
        if (!empty($Where['CronFilter']) && $Where['CronFilter'] == 'OneDayDiff') {
            $this->db->having("LastUpdateDiff", 0);
            $this->db->or_having("LastUpdateDiff >=", 86400); // 1 Day
        }
        /* if (!empty($Where['existingContests'])) {
          $StatusID = $Where['StatusID'];
          print_r($StatusID);
          $this->db->where('EXISTS (select MatchID from sports_contest where MatchID = M.MatchID AND E.StatusID IN(2,10)');
          } */

        if (!empty($Where['OrderBy']) && !empty($Where['Sequence'])) {
            $this->db->order_by($Where['OrderBy'], $Where['Sequence']);
        } else {
            if (!empty($Where['OrderByToday']) && $Where['OrderByToday'] == 'Yes') {
                $this->db->order_by('E.StatusID=1 DESC', null, FALSE);
                $this->db->order_by('M.MatchStartDateTime', "ASC");
                /* $this->db->order_by('DATE(M.MatchStartDateTime)="' . date('Y-m-d') . '" ASC', null, FALSE);
                  $this->db->order_by('E.StatusID=1 DESC', null, FALSE); */
            } else {
                $this->db->order_by('E.StatusID', 'ASC');
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
        // $this->db->cache_on(); //Turn caching on
        $Query = $this->db->get();
        if ($multiRecords) {
            if ($Query->num_rows() > 0) {
                $Records = array();
                foreach ($Query->result_array() as $key => $Record) {
                    $Records[] = $Record;
                    $Records[$key]['MatchScoreDetails'] = (!empty($Record['MatchScoreDetails'])) ? json_decode($Record['MatchScoreDetails'], TRUE) : new stdClass();
                }
                $Return['Data']['Records'] = $Records;
            }
            if (!empty($Where['MyJoinedMatchesCount']) && $Where['MyJoinedMatchesCount'] == 1) {
                $Return['Data']['Statics'] = $this->db->query('SELECT (
                        SELECT COUNT(DISTINCT M.MatchID) AS `UpcomingJoinedContest` FROM `sports_matches` M
                            JOIN `sports_contest_join_completed` J ON M.MatchID = J.MatchID JOIN `tbl_entity` E ON E.EntityID = M.MatchID WHERE E.StatusID = 1 AND J.UserID ="' . @$Where['UserID'] . '" 
                        )as UpcomingJoinedContest,
                        ( SELECT COUNT(DISTINCT M.MatchID) AS `LiveJoinedContest` FROM `sports_matches` M JOIN `sports_contest_join_completed` J ON M.MatchID = J.MatchID JOIN `tbl_entity` E ON E.EntityID = M.MatchID WHERE  E.StatusID IN (2,10) AND J.UserID = "' . @$Where['UserID'] . '" 
                        )as LiveJoinedContest,
                        ( SELECT COUNT(DISTINCT J.MatchID) AS `CompletedJoinedContest` FROM `sports_contest_join_completed` J, `tbl_entity` E, `sports_matches` M WHERE E.EntityID = M.MatchID AND J.MatchID=M.MatchID AND E.StatusID IN (5,10) AND J.UserID = "' . @$Where['UserID'] . '" 
                    )as CompletedJoinedContest'
                        )->row();
            }
            return $Return;
        } else {
            if ($Query->num_rows() > 0) {
                $Record = $Query->row_array();
                $Record['MatchScoreDetails'] = (!empty($Record['MatchScoreDetails'])) ? json_decode($Record['MatchScoreDetails'], TRUE) : new stdClass();
                return $Record;
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
                        $this->db->where("C.LeagueType", "Draft");
                        $this->db->where("U.UserTypeID", 2);
                        $this->db->where("C.AuctionStatusID", 5);
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
                        $this->db->where("C.LeagueType", "Draft");
                        $this->db->where("U.UserTypeID", 2);
                        $this->db->where("C.AuctionStatusID", 5);
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
                        $this->db->where("C.LeagueType", "Draft");
                        $this->db->where("C.MatchID", $Value['MatchID']);
                        $this->db->where("E.StatusID", 5);
                        $this->db->where("C.AuctionStatusID", 5);
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
                    $this->db->where("C.LeagueType", "Draft");
                    $this->db->where("C.MatchID", $Value['MatchID']);
                    $this->db->where("E.StatusID", 5);
                    $this->db->where("C.AuctionStatusID", 5);
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
                $TotalTeams = array();
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
                $MatchReports['TotalSeriesCollection']['TotalNetProfit'] = $AllTotalNetProfit;
                $MatchReports['TotalSeriesCollection']['TotalProfit'] = $AllTotalProfit;
                $MatchReports['TotalSeriesCollection']['Totalloss'] =  $AllTotalLoss;
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
            $this->db->where("C.LeagueType", "Draft");
            $this->db->where("U.UserTypeID", 2);
            $this->db->where("WC.Narration", "Join Contest Winning");
            $this->db->where("WC.StatusID", 5);
            $this->db->where("C.AuctionStatusID", 5);
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
            $this->db->where("C.LeagueType", "Draft");
            $this->db->where("U.UserTypeID", 2);
            $this->db->where("WC.Narration", "Join Contest");
            $this->db->where("WC.StatusID", 5);
            $this->db->where("C.AuctionStatusID", 5);
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
        $this->db->where("C.LeagueType", "Draft");
        $this->db->where("C.Privacy", "No");
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
        $this->db->where("C.LeagueType", "Draft");
        $this->db->where("C.IsPaid", "Yes");
        $this->db->where("E.StatusID", 5);
        $this->db->where("C.AuctionStatusID", 5);
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
                        $this->db->where("C.LeagueType", "Draft");
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
                        $this->db->where("C.AuctionStatusID", 5);
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
                        $this->db->where("C.LeagueType", "Draft");
                        $this->db->where("C.ContestID", $ContestRow['ContestID']);
                        if (!empty($Where['FromDate'])) {
                            $this->db->where("DATE(E.EntryDate) >=", $Where['FromDate']);
                        }
                        if (!empty($Where['ToDate'])) {
                            $this->db->where("DATE(E.EntryDate) <=", $Where['ToDate']);
                        }
                        $this->db->where("U.UserTypeID", 3);
                        $this->db->where("E.StatusID", 5);
                        $this->db->where("C.AuctionStatusID", 5);
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
                        $this->db->where("C.LeagueType", "Draft");
                        $this->db->where("C.ContestID", $ContestRow['ContestID']);
                        if (!empty($Where['FromDate'])) {
                            $this->db->where("DATE(E.EntryDate) >=", $Where['FromDate']);
                        }
                        if (!empty($Where['ToDate'])) {
                            $this->db->where("DATE(E.EntryDate) <=", $Where['ToDate']);
                        }
                        $this->db->where("U.UserTypeID !=", 3);
                        $this->db->where("E.StatusID", 5);
                        $this->db->where("C.AuctionStatusID", 5);
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
        $this->db->where("C.LeagueType", "Draft");
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
        $this->db->where("C.AuctionStatusID", 5);
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
        $this->db->where("C.LeagueType", "Draft");
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
        $this->db->where("C.AuctionStatusID", 5);
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
        $this->db->where("C.LeagueType", "Draft");
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
        $this->db->where("C.AuctionStatusID", 5);
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

        /*
      Description:  Use to update user profile info.
     */

    function ChangeStatus($EntityID, $Input = array()) {
        $UpdateArray = array_filter(array(
            "StatusID" => $Input['StatusID'],
            "ModifiedDate" => date("Y-m-d H:i:s")
        ));
        if (!empty($UpdateArray)) {
            if ($Input['StatusID'] == 3) {
                $this->autoCancelAuction($EntityID);

                /* Update entity Data. */
                $this->db->where('EntityID', $EntityID);
                $this->db->limit(1);
                $this->db->update('tbl_entity', $UpdateArray);
                
                /** contest status **/
                /* Update entity Data. */
                $this->db->where('ContestID', $EntityID);
                $this->db->limit(1);
                $this->db->update('sports_contest', array('AuctionStatusID'=>3));
            } else {
                /* Update entity Data. */
                $this->db->where('EntityID', $EntityID);
                $this->db->limit(1);
                $this->db->update('tbl_entity', $UpdateArray);

                /** contest status **/
                /* Update entity Data. */
                $this->db->where('ContestID', $EntityID);
                $this->db->limit(1);
                $this->db->update('sports_contest', array('AuctionStatusID'=>3));
            }
            return TRUE;
        }
    }


}

?>