<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Sports extends API_Controller {

    function __construct() {
        parent::__construct();
        $this->load->model('Sports_model');
    }

    /*
      Description: To get series data
     */

    public function getSeries_post() {
        $this->form_validation->set_rules('Status', 'Status', 'trim|callback_validateStatus');
        $this->form_validation->validation($this);  /* Run validation */
        /** sql injection keyword*/
        $FindWord=array("sleep","delete","select","SLEEP","DELETE","SELECT","Sleep","Delete","Select");
        $ReplaceWord=array("","","");
        $this->Post['Keyword'] = str_replace($FindWord,$ReplaceWord,@$this->Post['Keyword']);
        $this->Post['OrderBy'] = str_replace($FindWord,$ReplaceWord,@$this->Post['OrderBy']);
        $this->Post['Sequence'] = str_replace($FindWord,$ReplaceWord,@$this->Post['Sequence']);

        $SeriesData = $this->Sports_model->getSeries(@$this->Post['Params'], array_merge($this->Post, (!empty($this->Post['SeriesGUID'])) ? array('SeriesID' => $this->SeriesID) : array()), TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']);
        if (!empty($SeriesData)) {
            $this->Return['Data'] = $SeriesData['Data'];
        }
    }

    /*
      Description: To get rounds data
     */

    public function getRounds_post() {
        /** sql injection keyword*/
        $FindWord=array("sleep","delete","select","SLEEP","DELETE","SELECT","Sleep","Delete","Select");
        $ReplaceWord=array("","","");
        $this->Post['Keyword'] = str_replace($FindWord,$ReplaceWord,$this->Post['Keyword']);
        $this->Post['OrderBy'] = str_replace($FindWord,$ReplaceWord,$this->Post['OrderBy']);
        $this->Post['Sequence'] = str_replace($FindWord,$ReplaceWord,$this->Post['Sequence']);

        $SeriesData = $this->Sports_model->getRounds(@$this->Post['Params'], array_merge($this->Post, (!empty($this->Post['SeriesGUID'])) ? array('SeriesID' => $this->SeriesID) : array()), TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']);
        if (!empty($SeriesData)) {
            $this->Return['Data'] = $SeriesData['Data'];
        }
    }

    /*
      Description: To get matches data
     */

    public function getMatches_post() {
        $this->form_validation->set_rules('SeriesGUID', 'SeriesGUID', 'trim|callback_validateEntityGUID[Series,SeriesID]');
        $this->form_validation->set_rules('SessionKey', 'SessionKey', 'trim|callback_validateSession');
        $this->form_validation->set_rules('Keyword', 'Search Keyword', 'trim');
        $this->form_validation->set_rules('Filter', 'Filter', 'trim|in_list[Today,Series,MyJoinedMatch,TodayMatch,AddDays]');
        $this->form_validation->set_rules('OrderBy', 'OrderBy', 'trim');
        $this->form_validation->set_rules('Sequence', 'Sequence', 'trim|in_list[ASC,DESC]');
        $this->form_validation->set_rules('Status', 'Status', 'trim|callback_validateStatus');
        $this->form_validation->validation($this);  /* Run validation */


        /** sql injection keyword*/
        $FindWord=array("sleep","delete","select","update","SLEEP","insert","DELETE","SELECT","UPDATE","INSERT","Sleep","Delete","Select","Update","Insert");
        $ReplaceWord=array("","","");
        $this->Post['Keyword'] = str_replace($FindWord,$ReplaceWord,@$this->Post['Keyword']);
        $this->Post['OrderBy'] = str_replace($FindWord,$ReplaceWord,@$this->Post['OrderBy']);
        $this->Post['Sequence'] = str_replace($FindWord,$ReplaceWord,@$this->Post['Sequence']);
        
        /* Get Matches Data */
        if ($this->Post['Status'] == "Completed") {
            // if ($this->StatusID == 5) {
            //     $MatchesData = $this->Sports_model->getMatchesCompleted(@$this->Post['Params'], array_merge($this->Post, array('SeriesID' => @$this->SeriesID, 'StatusID' => $this->StatusID, 'UserID' => @$this->SessionUserID)), TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']);
            //     //$MatchesData = $this->Sports_model->getMatches(@$this->Post['Params'], array_merge($this->Post, array('SeriesID' => @$this->SeriesID, 'StatusID' => $this->StatusID, 'UserID' => @$this->SessionUserID)), TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']);
            // } else {
                $MatchesData = $this->Sports_model->getMatches(@$this->Post['Params'], array_merge($this->Post, array('SeriesID' => @$this->SeriesID, 'StatusID' => $this->StatusID, 'UserID' => @$this->SessionUserID)), TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']);
            // }
        } else {
            $MatchesData = $this->Sports_model->getMatches(@$this->Post['Params'], array_merge($this->Post, array('SeriesID' => @$this->SeriesID, 'StatusID' => $this->StatusID, 'UserID' => @$this->SessionUserID)), TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']);
        }
        if (!empty($MatchesData)) {
            $this->Return['Data'] = $MatchesData['Data'];
            $this->Return['Data']['UpcomingMatchesTime'] = MATCH_TIME_IN_HOUR;
        }
    }

    /*
      Description: To get match details
     */

    public function getMatch_post() {
        $this->form_validation->set_rules('SessionKey', 'SessionKey', 'trim|callback_validateSession');
        $this->form_validation->set_rules('MatchGUID', 'MatchGUID', 'trim|required|callback_validateEntityGUID[Matches,MatchID]');
        $this->form_validation->set_rules('Status', 'Status', 'trim|callback_validateStatus');
        $this->form_validation->validation($this);  /* Run validation */

        /* Get Match Data */

        $MatchDetails = $this->Sports_model->getMatches(@$this->Post['Params'], array_merge($this->Post, array('MatchID' => $this->MatchID, 'SessionUserID' => @$this->SessionUserID, 'StatusID' => @$this->StatusID)));
        if (!empty($MatchDetails)) {
            /*if($MatchDetails['ContestsAvailable'] = "Yes" 
                && $MatchDetails['TeamPlayersAvailable'] == "Yes"){
                $this->Return['Data'] = $MatchDetails;
            }else{
                $this->Return['ResponseCode'] = 500;
                $this->Return['Message'] = "This match will be open soon";
                exit;
            }*/
            $this->Return['Data'] = $MatchDetails;
        }
    }

    /*
      Description: To get match details
     */

    public function getMatchAuction_post() {
        $this->form_validation->set_rules('SessionKey', 'SessionKey', 'trim|callback_validateSession');
        $this->form_validation->set_rules('RoundID', 'RoundID', 'trim|required');
        $this->form_validation->validation($this);  /* Run validation */

        /* Get Match Data */
        $MatchDetails = $this->Sports_model->getMatchAuction(@$this->Post['Params'], array_merge($this->Post, array('StatusID' => 2,'OrderBy'=>"MatchStartDateTime","Sequence"=>"DESC")));
        if(empty($MatchDetails)){
          /* Get Match Data */
          $MatchDetails = $this->Sports_model->getMatchAuction(@$this->Post['Params'], array_merge($this->Post, array('StatusID' => 5,'OrderBy'=>"MatchStartDateTime","Sequence"=>"DESC")));
          if(empty($MatchDetails)){
            /* Get Match Data */
            $MatchDetails = $this->Sports_model->getMatchAuction(@$this->Post['Params'], array_merge($this->Post, array('StatusID' => 1,'OrderBy'=>"MatchStartDateTime","Sequence"=>"ASC")));
          }  
        }
        if (!empty($MatchDetails)) {
            $this->Return['Data'] = $MatchDetails;
        }
    }

    /*
      Description: To get players data
     */

    public function getPlayers_post() {
        $this->form_validation->set_rules('MatchGUID', 'MatchGUID', 'trim|callback_validateEntityGUID[Matches,MatchID]');
        $this->form_validation->set_rules('SeriesGUID', 'SeriesGUID', 'trim|callback_validateEntityGUID[Series,SeriesID]');
        $this->form_validation->set_rules('TeamGUID', 'TeamGUID', 'trim|callback_validateEntityGUID[Teams,TeamID]');
        $this->form_validation->set_rules('Keyword', 'Search Keyword', 'trim');
        $this->form_validation->set_rules('OrderBy', 'OrderBy', 'trim');
        $this->form_validation->set_rules('Sequence', 'Sequence', 'trim|in_list[ASC,DESC]');
        $this->form_validation->set_rules('SessionKey', 'SessionKey', 'trim|required|callback_validateSession');
        $this->form_validation->validation($this);  /* Run validation */
        /* Get Players Data */
        $playersData = $this->Sports_model->getPlayers(@$this->Post['Params'], array_merge($this->Post, array('TeamID' => @$this->TeamID, 'MatchID' => @$this->MatchID, 'SeriesID' => @$this->SeriesID, 'UserID' => @$this->SessionUserID)), TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']);
        if (!empty($playersData)) {
            $this->Return['Data'] = $playersData['Data'];
        }
    }

    /*
      Description: To get players data
     */

    public function managePlayers_post() {
        $this->form_validation->set_rules('MatchGUID', 'MatchGUID', 'trim|callback_validateEntityGUID[Matches,MatchID]');
        $this->form_validation->set_rules('SeriesGUID', 'SeriesGUID', 'trim|callback_validateEntityGUID[Series,SeriesID]');
        $this->form_validation->set_rules('TeamGUID', 'TeamGUID', 'trim|callback_validateEntityGUID[Teams,TeamID]');
        $this->form_validation->set_rules('Keyword', 'Search Keyword', 'trim');
        $this->form_validation->set_rules('OrderBy', 'OrderBy', 'trim');
        $this->form_validation->set_rules('Sequence', 'Sequence', 'trim|in_list[ASC,DESC]');
        $this->form_validation->set_rules('SessionKey', 'SessionKey', 'trim|required|callback_validateSession');
        $this->form_validation->validation($this);  /* Run validation */
        /* Get Players Data */
        $playersData = $this->Sports_model->getPlayers(@$this->Post['Params'], array_merge($this->Post, array('TeamID' => @$this->TeamID, 'MatchID' => @$this->MatchID, 'SeriesID' => @$this->SeriesID, 'UserID' => @$this->SessionUserID)), TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']);
        if (!empty($playersData)) {
            $this->Return['Data'] = $playersData['Data'];
        }
    }

    /*
      Description: chnage player playing status
     */

    public function changePlayingStatus_post() {
        $this->form_validation->set_rules('PlayerID', 'PlayerID', 'trim|required');
        $this->form_validation->set_rules('MatchGUID', 'MatchGUID', 'trim|callback_validateEntityGUID[Matches,MatchID]');
        $this->form_validation->set_rules('IsPlaying', 'IsPlaying', 'trim|required|in_list[Yes,No]');

        $this->form_validation->validation($this);  /* Run validation */
        /* Get Players Data */
        $this->db->where('PlayerID', $this->Post['PlayerID']);
        $this->db->where('MatchID', $this->MatchID);
        $this->db->limit(1);
        $this->db->update('sports_team_players', array('IsPlaying' => $this->Post['IsPlaying']));
    }

    /*
      Description: chnage Dublicate playing status
     */

    public function changeDublicatePlayingStatus_post() {
        $this->form_validation->set_rules('PlayerID', 'PlayerID', 'trim|required');
        $this->form_validation->set_rules('MatchGUID', 'MatchGUID', 'trim|callback_validateEntityGUID[Matches,MatchID]');
        $this->form_validation->set_rules('IsDublicate', 'IsDublicate', 'trim|required|in_list[Yes,No]');

        $this->form_validation->validation($this);  /* Run validation */
        /* Get Players Data */
        $this->db->where('PlayerID', $this->Post['PlayerID']);
        $this->db->where('MatchID', $this->MatchID);
        $this->db->limit(1);
        $this->db->update('sports_team_players', array('IsDublicate' => $this->Post['IsDublicate']));
    }

    /*
      Description: chnage player active status
     */

    public function changeActiveStatus_post() {
        $this->form_validation->set_rules('PlayerID', 'PlayerID', 'trim|required');
        $this->form_validation->set_rules('MatchGUID', 'MatchGUID', 'trim|callback_validateEntityGUID[Matches,MatchID]');
        $this->form_validation->set_rules('IsActive', 'IsActive', 'trim|required|in_list[Yes,No]');
        $this->form_validation->validation($this);  /* Run validation */

        /** sql injection keyword*/
        $FindWord=array("sleep","delete","select","update","SLEEP","insert","DELETE","SELECT","UPDATE","INSERT","Sleep","Delete","Select","Update","Insert");
        $ReplaceWord=array("","","");
        $this->Post['PlayerID'] = str_replace($FindWord,$ReplaceWord,$this->Post['PlayerID']);

        if ($this->Post['IsActive'] == 'No') {
            $check_player = $this->db->query("SELECT UserTeamID FROM sports_users_team_players WHERE MatchID = '" . $this->MatchID . "' AND PlayerID = " . $this->db->escape($this->Post['PlayerID']) . " limit 1")->result_array();
            if (!empty($check_player)) {
                $this->Return['ResponseCode'] = 500;
                $this->Return['Message'] = "You can't inactive this player because the player already in user team.";
                exit;
            }
        }
        /* Get Players Data */
        $this->db->where('PlayerID', $this->Post['PlayerID']);
        $this->db->where('MatchID', $this->MatchID);
        $this->db->limit(1);
        $this->db->update('sports_team_players', array('IsActive' => $this->Post['IsActive']));
    }

    /*
      Description: chnage player active status
     */

    public function changeRoleStatus_post() {
        $this->form_validation->set_rules('PlayerID', 'PlayerID', 'trim|required');
        $this->form_validation->set_rules('MatchGUID', 'MatchGUID', 'trim|callback_validateEntityGUID[Matches,MatchID]');
        $this->form_validation->set_rules('PlayerRole', 'PlayerRole', 'trim|required|in_list[Batsman,Bowler,AllRounder,WicketKeeper]');

        $this->form_validation->validation($this);  /* Run validation */
        /* Get Players Data */

                /** sql injection keyword*/
        $FindWord=array("sleep","delete","select","update","SLEEP","insert","DELETE","SELECT","UPDATE","INSERT","Sleep","Delete","Select","Update","Insert");
        $ReplaceWord=array("","","");
        $this->Post['PlayerID'] = str_replace($FindWord,$ReplaceWord,$this->Post['PlayerID']);
        $this->Post['PlayerRole'] = str_replace($FindWord,$ReplaceWord,$this->Post['PlayerRole']);

        $SeriesID = $this->db->query("SELECT SeriesID FROM sports_matches WHERE MatchID = '" . $this->MatchID . "'")->row()->SeriesID;
        $Matches = $this->db->query("SELECT
                                        M.MatchID,MatchStartDateTime
                                    FROM
                                        sports_matches M
                                    WHERE  M.SeriesID = '" . $SeriesID . "' AND DATE(M.MatchStartDateTime) >= '" . date('Y-m-d') . "'")->result_array();
        $Flag = TRUE;
        if (!empty($Matches)) {
            foreach ($Matches as $Rows) {
                $check_player = $this->db->query("SELECT UserTeamID FROM sports_users_team_players WHERE MatchID = '" . $Rows['MatchID'] . "' AND PlayerID = " . $this->db->escape($this->Post['PlayerID']) . " limit 1")->result_array();
                if (empty($check_player)) {
                    $this->Sports_model->updatePlayerRole($this->Post['PlayerID'], $Rows['MatchID'], array("PlayerRole" => $this->Post['PlayerRole'], 'IsAdminUpdate' => 'Yes'));
                } else {
                    if ($this->MatchID == $Rows['MatchID']) {
                        $Flag = FALSE;
                    }
                }
            }
        }
        if (!$Flag) {
            $this->Return['Message'] = "Player role has been changed only upcoming matches not current select match.";
        } else {
            $this->Return['Message'] = "Player role has been changed current select match and upcoming matches.";
        }
    }

    /*
      Description: To get player details
     */

    public function getPlayer_post() {
        $this->form_validation->set_rules('PlayerGUID', 'PlayerGUID', 'trim|required|callback_validateEntityGUID[Players,PlayerID]');
        $this->form_validation->set_rules('MatchGUID', 'MatchGUID', 'trim|required|callback_validateEntityGUID[Matches,MatchID]');
        $this->form_validation->validation($this);  /* Run validation */

        /* Get Player Data */
        $PlayerDetails = $this->Sports_model->getPlayers(@$this->Post['Params'], array_merge($this->Post, array('PlayerID' => $this->PlayerID, 'MatchID' => $this->MatchID)));
        if (!empty($PlayerDetails)) {
            $this->Return['Data'] = $PlayerDetails;
        }
    }

    /*
      Description: To get player details
     */

    public function getPlayerSingle_post() {
        $this->form_validation->set_rules('PlayerGUID', 'PlayerGUID', 'trim|required|callback_validateEntityGUID[Players,PlayerID]');
        // $this->form_validation->set_rules('MatchGUID', 'MatchGUID', 'trim|required|callback_validateEntityGUID[Matches,MatchID]');
        $this->form_validation->validation($this);  /* Run validation */

        /* Get Player Data */
        $PlayerDetails = $this->db->query("SELECT PlayerGUID,IF(PlayerPic IS NULL,CONCAT('".BASE_URL."','uploads/PlayerPic/','player.png'),CONCAT('".BASE_URL."','uploads/PlayerPic/',PlayerPic)) AS PlayerPic FROM sports_players WHERE PlayerID = ".$this->PlayerID)->row_array();
        // $PlayerDetails = $this->Sports_model->getPlayers(@$this->Post['Params'], array_merge($this->Post, array('PlayerID' => $this->PlayerID)));
        // echo $this->db->last_query();
        // print_r($PlayerDetails);exit;
        if (!empty($PlayerDetails)) {
            $this->Return['Data'] = $PlayerDetails;
        }
    }

    /*
      Description: To get teams
     */

    public function getTeams_post() {
        $this->form_validation->set_rules('SeriesGUID', 'SeriesGUID', 'trim|callback_validateEntityGUID[Series,SeriesID]');
        $this->form_validation->set_rules('TeamGUID', 'TeamGUID', 'trim|callback_validateEntityGUID[Teams,TeamID]');

        $this->form_validation->validation($this);  /* Run validation */

        $TeamsData = $this->Sports_model->getTeams(@$this->Post['Params'], array_merge($this->Post, array('TeamID' => @$this->TeamID, 'SeriesID' => @$this->SeriesID)), TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']);
        if (!empty($TeamsData)) {
            $this->Return['Data'] = $TeamsData['Data'];
        }
    }

    /*
      Description: To get team
     */

    public function getTeam_post() {
        $this->form_validation->set_rules('TeamGUID', 'TeamGUID', 'trim|required|callback_validateEntityGUID[Teams,TeamID]');
        $this->form_validation->validation($this);  /* Run validation */

        /* Get Match Data */
        $TeamDetails = $this->Sports_model->getTeams(@$this->Post['Params'], array_merge($this->Post, array('TeamID' => $this->TeamID)));
        if (!empty($TeamDetails)) {
            $this->Return['Data'] = $TeamDetails;
        }
    }

    /*
      Description: To get sports points for app
     */

    public function getPointsApp_post() {
        $this->form_validation->set_rules('PointsCategory', 'PointsCategory', 'trim|in_list[Normal,InPlay,Reverse]');
        $this->form_validation->validation($this);  /* Run validation */

        $PointsData = $this->Sports_model->getPointsApp($this->Post);
        if (!empty($PointsData)) {
            $this->Return['Data'] = $PointsData['Data'];
        }
    }

    /*
      Description: To get sports points
     */

    public function getPoints_post() {
        $this->form_validation->set_rules('PointsCategory', 'PointsCategory', 'trim|in_list[Normal,InPlay,Reverse]');
        $this->form_validation->validation($this);  /* Run validation */

        $PointsData = $this->Sports_model->getPoints($this->Post);
        if (!empty($PointsData)) {
            $this->Return['Data'] = $PointsData['Data'];
        }
    }

    /*
      Description: To get sports best played players of the match
     */

    public function match_players_best_played_post() {
        $this->form_validation->set_rules('MatchGUID', 'MatchGUID', 'trim|required|callback_validateEntityGUID[Matches,MatchID]');
        $this->form_validation->validation($this);  /* Run validation */

        $BestTeamData = $this->Sports_model->match_players_best_played(array('MatchID' => $this->MatchID), false);
        if (!empty($BestTeamData)) {
            $this->Return['Data'] = $BestTeamData['Data'];
        }
    }

    /*
      Description: To get sports best played players of the match
     */

    public function getMatchBestPlayers_post() {
        $this->form_validation->set_rules('MatchGUID', 'MatchGUID', 'trim|required|callback_validateEntityGUID[Matches,MatchID]');
        $this->form_validation->set_rules('SessionKey', 'SessionKey', 'trim|callback_validateSession');

        $this->form_validation->validation($this);  /* Run validation */

        $BestTeamData = $this->Sports_model->getMatchBestPlayers(array('MatchID' => $this->MatchID, 'UserID' => $this->SessionUserID), FALSE);
        if (!empty($BestTeamData)) {
            $this->Return['Data'] = $BestTeamData['Data'];
        }
    }

    /*
      Description: To get sports player fantasy stats series wise
     */

    public function getPlayerFantasyStats_post() {
        $this->form_validation->set_rules('SessionKey', 'SessionKey', 'trim|callback_validateSession');
        $this->form_validation->set_rules('SeriesGUID', 'SeriesGUID', 'trim|callback_validateEntityGUID[Series,SeriesID]');
        $this->form_validation->set_rules('MatchGUID', 'MatchGUID', 'trim|callback_validateEntityGUID[Matches,MatchID]');

        $this->form_validation->set_rules('PlayerGUID', 'PlayerGUID', 'trim|required|callback_validateEntityGUID[Players,PlayerID]');
        $this->form_validation->validation($this);  /* Run validation */

        $PendingMatchStatsArr = $CompletedMatchesStatsArr = array();
        $TotalRecords = 0;

        /* Get Pending Match Stats */
        $PendingMatchStats = $this->Sports_model->getPlayerFantasyStats(@$this->Post['Params'], array_merge($this->Post, array('SeriesID' => $this->SeriesID, 'MatchID' => $this->MatchID, 'PlayerID' => $this->PlayerID, 'StatusID' => 1, 'OrderBy' => 'MatchStartDateTime', 'Sequence' => 'ASC')), TRUE, 1, 1);
        if (!empty($PendingMatchStats)) {
            $TotalRecords = $PendingMatchStats['Data']['TotalRecords'];
            $PendingMatchStatsArr = $PendingMatchStats['Data']['Records'];
        }


        /* Get Completed Matches Stats */
        $CompletedMatchesStats = $this->Sports_model->getPlayerFantasyStats(@$this->Post['Params'], array_merge($this->Post, array('SeriesID' => $this->SeriesID, 'MatchID' => $this->MatchID, 'PlayerID' => $this->PlayerID, 'StatusID' => 5)), TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']);
        if (!empty($CompletedMatchesStats)) {
            $TotalRecords += $CompletedMatchesStats['Data']['TotalRecords'];
            $CompletedMatchesStatsArr = $CompletedMatchesStats['Data']['Records'];
        }

        $PlayerStats = $this->Sports_model->getPlayerFantasyStats(@$this->Post['Params'], array_merge($this->Post, array('SeriesID' => $this->SeriesID, 'MatchID' => $this->MatchID, 'PlayerID' => $this->PlayerID)), TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']);
        $this->Return['Data']['TotalRecords'] = $TotalRecords;
        $this->Return['Data']['Records'] = array_merge_recursive($PendingMatchStatsArr, $CompletedMatchesStatsArr);
        $this->Return['Data']['PlayerRole'] = $PendingMatchStatsArr[0]['PlayerRole'];
        $this->Return['Data']['PlayerRoleCompleted'] = $CompletedMatchesStats['Data']['Records'][0]['PlayerRole'];
        $this->Return['Data']['PlayerBattingStats'] = (!empty($PlayerStats['Data']['PlayerBattingStats']) ? $PlayerStats['Data']['PlayerBattingStats'] : new stdClass());
        $this->Return['Data']['PlayerBowlingStats'] = (!empty($PlayerStats['Data']['PlayerBowlingStats']) ? $PlayerStats['Data']['PlayerBowlingStats'] : new stdClass());
        $this->Return['Data']['PlayerFieldingStats'] = (!empty($PlayerStats['Data']['PlayerFieldingStats']) ? $PlayerStats['Data']['PlayerFieldingStats'] : new stdClass());
    }

        /*
      Description: To get sports player fantasy stats series wise
     */

    public function getPlayerFantasyStatsDraft_post() {
        $this->form_validation->set_rules('SessionKey', 'SessionKey', 'trim|callback_validateSession');
        $this->form_validation->set_rules('SeriesGUID', 'SeriesGUID', 'trim|callback_validateEntityGUID[Series,SeriesID]');
        $this->form_validation->set_rules('MatchGUID', 'MatchGUID', 'trim|callback_validateEntityGUID[Matches,MatchID]');

        $this->form_validation->set_rules('PlayerGUID', 'PlayerGUID', 'trim|required|callback_validateEntityGUID[Players,PlayerID]');
        $this->form_validation->validation($this);  /* Run validation */

        $PendingMatchStatsArr = $CompletedMatchesStatsArr = array();
        $TotalRecords = 0;

        /* Get Pending Match Stats */
        $PendingMatchStats = $this->Sports_model->getPlayerFantasyStatsDraft(@$this->Post['Params'], array_merge($this->Post, array('SeriesID' => $this->SeriesID, 'MatchID' => $this->MatchID, 'PlayerID' => $this->PlayerID, 'StatusID' => 1, 'OrderBy' => 'MatchStartDateTime', 'Sequence' => 'ASC')), TRUE, 1, 1);
        if (!empty($PendingMatchStats)) {
            $TotalRecords = $PendingMatchStats['Data']['TotalRecords'];
            $PendingMatchStatsArr = $PendingMatchStats['Data']['Records'];
        }


        /* Get Completed Matches Stats */
        $CompletedMatchesStats = $this->Sports_model->getPlayerFantasyStatsDraft(@$this->Post['Params'], array_merge($this->Post, array('SeriesID' => $this->SeriesID, 'MatchID' => $this->MatchID, 'PlayerID' => $this->PlayerID, 'StatusID' => 5)), TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']);
        if (!empty($CompletedMatchesStats)) {
            $TotalRecords += $CompletedMatchesStats['Data']['TotalRecords'];
            $CompletedMatchesStatsArr = $CompletedMatchesStats['Data']['Records'];
        }

        $PlayerStats = $this->Sports_model->getPlayerFantasyStatsDraft(@$this->Post['Params'], array_merge($this->Post, array('SeriesID' => $this->SeriesID, 'MatchID' => $this->MatchID, 'PlayerID' => $this->PlayerID)), TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']);
        $this->Return['Data']['TotalRecords'] = $TotalRecords;
        $this->Return['Data']['Records'] = array_merge_recursive($PendingMatchStatsArr, $CompletedMatchesStatsArr);
        $this->Return['Data']['PlayerRole'] = $PendingMatchStatsArr[0]['PlayerRole'];
        $this->Return['Data']['PlayerRoleCompleted'] = $CompletedMatchesStats['Data']['Records'][0]['PlayerRole'];
        $this->Return['Data']['PlayerBattingStats'] = (!empty($PlayerStats['Data']['PlayerBattingStats']) ? $PlayerStats['Data']['PlayerBattingStats'] : new stdClass());
        $this->Return['Data']['PlayerBowlingStats'] = (!empty($PlayerStats['Data']['PlayerBowlingStats']) ? $PlayerStats['Data']['PlayerBowlingStats'] : new stdClass());
        $this->Return['Data']['PlayerFieldingStats'] = (!empty($PlayerStats['Data']['PlayerFieldingStats']) ? $PlayerStats['Data']['PlayerFieldingStats'] : new stdClass());
    }

    public function draftPlayersPoint_post() {
        //$this->form_validation->set_rules('SeriesGUID', 'SeriesGUID', 'trim|required|callback_validateEntityGUID[Series,SeriesID]');
        $this->form_validation->set_rules('MatchGUID', 'MatchGUID', 'trim|callback_validateEntityGUID[Matches,MatchID]');
        $this->form_validation->set_rules('PlayerGUID', 'PlayerGUID', 'trim|required|callback_validateEntityGUID[Players,PlayerID]');
        $this->form_validation->set_rules('Keyword', 'Search Keyword', 'trim');
        $this->form_validation->set_rules('OrderBy', 'OrderBy', 'trim');
        $this->form_validation->set_rules('Sequence', 'Sequence', 'trim|in_list[ASC,DESC]');
        $this->form_validation->set_rules('SessionKey', 'SessionKey', 'trim|callback_validateSession');
        $this->form_validation->set_rules('RoundID', 'RoundID', 'trim');
        $this->form_validation->set_rules('SeriesID', 'SeriesID', 'trim');
        $this->form_validation->validation($this);  /* Run validation */
        /* Get Players Data */
        $playersData = $this->Sports_model->draftPlayersPoint(@$this->Post['Params'], array_merge($this->Post, array('PlayerID' => @$this->PlayerID, 'UserID' => @$this->SessionUserID, 'StatusID' => 5, 'MatchID' => @$this->MatchID)), TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']);

        if (!empty($playersData)) {
            $this->Return['Data'] = $playersData['Data'];
            $this->Return['status'] = 1;
        } else {
            $playersData = $this->Sports_model->draftPlayersPoint(@$this->Post['Params'], array_merge($this->Post, array('PlayerID' => @$this->PlayerID, 'UserID' => @$this->SessionUserID, 'StatusID' => 1, 'MatchID' => @$this->MatchID)), TRUE, 1, 1);
            if (!empty($playersData)) {
                $this->Return['Data'] = $playersData['Data'];
                $this->Return['status'] = 0;
            }
        }
    }


    public function dfsPlayersPoint_post() {
        //$this->form_validation->set_rules('SeriesGUID', 'SeriesGUID', 'trim|required|callback_validateEntityGUID[Series,SeriesID]');
        $this->form_validation->set_rules('MatchGUID', 'MatchGUID', 'trim|callback_validateEntityGUID[Matches,MatchID]');
        $this->form_validation->set_rules('PlayerGUID', 'PlayerGUID', 'trim|required|callback_validateEntityGUID[Players,PlayerID]');
        $this->form_validation->set_rules('Keyword', 'Search Keyword', 'trim');
        $this->form_validation->set_rules('OrderBy', 'OrderBy', 'trim');
        $this->form_validation->set_rules('Sequence', 'Sequence', 'trim|in_list[ASC,DESC]');
        $this->form_validation->set_rules('SessionKey', 'SessionKey', 'trim|callback_validateSession');
        $this->form_validation->set_rules('RoundID', 'RoundID', 'trim');
        $this->form_validation->set_rules('SeriesID', 'SeriesID', 'trim');
        $this->form_validation->validation($this);  /* Run validation */
        /* Get Players Data */
        $playersData = $this->Sports_model->dfsPlayersPoint(@$this->Post['Params'], array_merge($this->Post, array('PlayerID' => @$this->PlayerID, 'UserID' => @$this->SessionUserID, 'StatusID' => 5, 'MatchID' => @$this->MatchID)), TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']);

        if (!empty($playersData)) {
            $this->Return['Data'] = $playersData['Data'];
            $this->Return['status'] = 1;
        } else {
            $playersData = $this->Sports_model->dfsPlayersPoint(@$this->Post['Params'], array_merge($this->Post, array('PlayerID' => @$this->PlayerID, 'UserID' => @$this->SessionUserID, 'StatusID' => 1, 'MatchID' => @$this->MatchID)), TRUE, 1, 1);
            if (!empty($playersData)) {
                $this->Return['Data'] = $playersData['Data'];
                $this->Return['status'] = 0;
            }
        }
    }

    public function getMatchUser_post() {
        $this->form_validation->set_rules('SessionKey', 'SessionKey', 'trim|callback_validateSession');
        $this->form_validation->set_rules('MatchGUID', 'MatchGUID', 'trim|required|callback_validateEntityGUID[Matches,MatchID]');
        $this->form_validation->validation($this);  /* Run validation */

        /* Get Match Data */

        $MatchDetails = $this->Sports_model->getMatchUser(@$this->MatchID,@$this->Post['Type']);
        print_r($MatchDetails);exit;
    }

    public function matchBroadcast_post() {
        
        $this->form_validation->set_rules('SessionKey', 'SessionKey', 'trim|callback_validateSession');
        $this->form_validation->set_rules('MatchGUID', 'MatchGUID', 'trim|callback_validateEntityGUID[Matches,MatchID]');
        $this->form_validation->set_rules('SeriesGUID', 'SeriesGUID', 'trim|callback_validateEntityGUID[Series,SeriesID]');
        $this->form_validation->validation($this);  /* Run validation */

        // api accese for admin
        $UserTypeID = getUserTypeID($this->SessionUserID);
        $this->load->model('Admin_model');
        $PermittedModules = $this->Admin_model->getPermittedModules($UserTypeID);
        $ModuleData = ValidateUserAccess($PermittedModules,$_SERVER[REQUEST_URI]);
        if($ModuleData == "Failed") {
            $this->Return['ResponseCode']   =   500;
            $this->Return['Message']        =   "You do not have permission to access this api.";
            exit;
        }
        // end
        
        /* Validation - ends */
        /* check for media present - associate media with this Post - ends */
        if ($this->Post['UserType'] == 'Selected') {
            $UsersData['Data']['Records'] = $this->Post['selectedUser'];        
        } else {

            if($this->Post['Redirection'] == 'Dfs' || $this->Post['Redirection'] == 'Gully'){
                $UsersData['Data']['Records'] = $this->Sports_model->getMatchUser(@$this->MatchID,@$this->Post['Redirection']);
            }

            if($this->Post['Redirection'] == 'Auction'){
                $UsersData['Data']['Records'] = $this->Sports_model->getMatchUserAuction(@$this->SeriesID,@$this->Post['Redirection']);
            }

            if($this->Post['Redirection'] == 'FT-Dfs'){
                $UsersData['Data']['Records'] = $this->Sports_model->getMatchUserFootball(@$this->MatchID,@$this->Post['Redirection']);
            }
        }
        if ($UsersData) {
            if (!empty($this->Post['broadcast']) && $this->Post['broadcast'] == 3) {
                if ((!empty($this->Post['Normal']) && $this->Post['Normal'] == 1) || (!empty($this->Post['both']) && $this->Post['both'] == 1)) {
                    if ($this->Post['UserType'] == 'Selected') {
                       foreach ($UsersData['Data']['Records'] as $Value) {
                            $InsertData[] = array_filter(array(
                                "NotificationPatternID" => 2,
                                "UserID" => $this->SessionUserID,
                                "ToUserID" => $Value,
                                "RefrenceID" => "",
                                "NotificationText" => $this->Post['Title'],
                                "NotificationMessage" => $this->Post['Message'],
                                "MediaID" => "",
                                "EntryDate" => date("Y-m-d H:i:s")
                            ));
                        }
                    }else{
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
                    }
                    
                    if (!empty($InsertData)) {
                        $this->db->insert_batch('tbl_notifications', $InsertData);
                    }
                }
                if ((!empty($this->Post['Push']) && $this->Post['Push'] == 1) || (!empty($this->Post['both']) && $this->Post['both'] == 1)) {
                    if ($this->Post['UserType'] == 'Selected') {
                       foreach ($UsersData['Data']['Records'] as $ValueSelected) {
                            sendPushMessage($ValueSelected, $this->Post['Title'], $this->Post['Message'],'',@$this->Post['Redirection']);
                        }
                    } else {
                        foreach ($UsersData['Data']['Records'] as $Value) {
                            sendPushMessage($Value['UserID'], $this->Post['Title'], $this->Post['Message'],'',@$this->Post['Redirection']);
                        }
                    }
                }

                $this->Return['Message'] = 'Notification broadcasted.';
            } else {
                $this->Return['ResponseCode'] = 500;
                $this->Return['Message'] = 'Please Select broadcast Type.';
            }
        }
    }

}

?>
