<?php

if (!defined('BASEPATH'))
    exit('No direct script access allowed');

class Match_model extends CI_Model
{

    public $table = 'match';
    public $id = 'match_id';
    public $order = 'DESC';

    function __construct()
    {
        parent::__construct();
    }

     // get all
    function get_all()
    {
        $this->db->order_by($this->id, $this->order);
        return $this->db->get($this->table)->result();
    }

    // get data by id
    function get_by_id($id)
    {
        $this->db->where($this->id, $id);
        return $this->db->get($this->table)->row();
    }
    
    // get total rows
    function total_rows($q = NULL) {
        $this->db->like('match_id', $q);
	//$this->db->or_like('name', $q);
	//$this->db->or_like('created_date', $q);
	//$this->db->or_like('modified_date', $q);
	$this->db->from($this->table);
        return $this->db->count_all_results();
    }

    // get data with limit and search
    function get_limit_data($limit, $start = 0, $q = NULL) {
        $this->db->order_by($this->id, $this->order);
        $this->db->like('match_id', $q);
	//$this->db->or_like('name', $q);
	//$this->db->or_like('created_date', $q);
	//$this->db->or_like('modified_date', $q);
	//$this->db->limit($limit, $start);
        return $this->db->get($this->table)->result();
    }

    function get_fixture_match_data()
    {
        $this->db->order_by($this->id, $this->order);
        $this->db->where('match_status','Fixture');

        return $this->db->get($this->table)->result();
    }
    
    function get_live_match_data()
    {
        $this->db->order_by($this->id, $this->order);
        $this->db->where('match_status','Live');

        return $this->db->get($this->table)->result();
    }

    // insert data
    function insert($data)
    {
        $this->db->insert($this->table, $data);
    }

    // update data
    function update($id, $data)
    {
        $this->db->where($this->id, $id);
        $this->db->update($this->table, $data);
    }

    // delete data
    function delete($id)
    {
        $this->db->where($this->id, $id);
        $this->db->delete($this->table);
    }

    // team list
    function get_team_list()
    {
   		return $this->db->get('team')->result();
    }

    function match_type()
    {
    	return $this->db->get('match_type')->result();
    }

    function match_status()
    {
    	return $this->db->get('match_status')->result();
    }

    function team_name($id)
    {
    	$this->db->select('team_name');
    	$this->db->where('team_id',$id);
    	return $this->db->get('team')->row();
    }

    function get_match_type($id)
    {
    	$this->db->select('name');
    	$this->db->where('id',$id);
    	return $this->db->get('match_type')->row();
    }

    function get_match_status($id)
    {
    	$this->db->select('name');
    	$this->db->where('id',$id);
    	return $this->db->get('match_status')->row();
    }
    
    function get_match_by_match_status($status,$desc='asc',$limit=10)
    {
        $this->db->select('match.*,t1.team_name as t1_name, t2.team_name as t2_name');
    	$this->db->where('match_status',$status);
    	$this->db->join('team as t1','t1.team_id=match.teamid1');
    	$this->db->join('team as t2','t2.team_id=match.teamid2');
    	$this->db->order_by('match_id',$desc);
    	//$this->db->limit($limit);
    	return $this->db->get('match')->result();
    }
    
    function get_match_by_match_status_result($status,$desc='desc')
    {
        $this->db->select('match.*,t1.team_name as t1_name, t2.team_name as t2_name');
    	$this->db->where('match_status',$status);
    	$this->db->join('team as t1','t1.team_id=match.teamid1');
    	$this->db->join('team as t2','t2.team_id=match.teamid2');
    	$this->db->order_by('match_id',$desc);
    	return $this->db->get('match')->result();
    }
    
    function get_today_match()
    {
        $today = date('Y-m-d');
       $this->db->select('match.*,t1.team_name as t1_name, t2.team_name as t2_name');
       //array()
    	$this->db->where("DATE(match_date_time)" , $today);
    	$this->db->or_where('match_status','Live');
    	$this->db->join('team as t1','t1.team_id=match.teamid1');
    	$this->db->join('team as t2','t2.team_id=match.teamid2');
    	$this->db->order_by('match_id',$desc);
    	$this->db->limit($limit);
    	return $this->db->get('match')->result();
        
    }
    
    function upcomming_live_matches(){
        $today = date('Y-m-d');
        $this->db->select('match.*');
    	$this->db->where("DATE(time) >= '".$today."'");
    	$this->db->or_where('match_status','Live');
    
    	$this->db->order_by('match_id',$desc);
    	return $this->db->get('match')->result();   
    }
    
    function get_topranks($id,$contest_id)
    {
      $sql =  "SELECT * FROM `leaderboard` WHERE `matchid` =  $id  AND `contestid` =  $contest_id   ORDER BY `leaderboard`.`rank` + 0 ASC LIMIT 3";
      $res = $this->db->query($sql);
      return $res->result();
    }

    function total_team_join($id)
    {
       return $this->db->get_where('my_team',array('match_id'=>$id))->result();
    }
    
    
    function total_team_joins($id,$c_id)
    {
        $this->db->select('teamid,user_id');
       return $this->db->get_where('leaderboard',array('matchid'=>$id,'contestid'=>$c_id))->result();
    }

    function get_toprank($id,$contest_id)
    {
        $this->db->select('*');
        $this->db->where('matchid',$id);
        $this->db->where('contestid',$contest_id);
        $this->db->order_by('rank','asc');
        $this->db->limit('3');
        return $this->db->get('leaderboard')->result();
    }

    function get_leaderboard($id,$contest_id)
    {
        $this->db->select('id,teamid,TeamName,rank,points,user_id,created_date');
        $this->db->where('matchid',$id);
        $this->db->where('contestid',$contest_id);
        $this->db->order_by('rank','asc');
        return $this->db->get('leaderboard')->result();
    }


   function get_userjoin_count($id){
        $this->db->select('*');
        $this->db->where('matchid',$id);
        $this->db->group_by('user_id');
        return $this->db->get('leaderboard')->num_rows();
   }

   function get_rank_byid($id)
   {
        $this->db->select('*');
        $this->db->where('teamid',$id);
        return $this->db->get('leaderboard')->row();
   }

   function get_usersjoin_count(){
        $this->db->select('*');
        $this->db->group_by('user_id');
        return $this->db->get('leaderboard')->num_rows();
   }

    
    
    function match_status_check($id)
    {
        $this->db->select('match_status');
        $this->db->where('match_id',$id);
        $resp = $this->db->get('match')->row_array();
        if($resp !="")
        {
            return  $resp['match_status'];
        }    
        else
        {
            return false;
        }     
    }
    
    function get_all_cancell_match()
    {
        $this->db->select('match_id,title,type,match_date_time,refund,payment_status');
        $this->db->where('cancelled','1');
        $this->db->order_by($this->id, $this->order);
        return $this->db->get($this->table)->result();
    }
    
    function match_status_cancel_check($id)
    {
        $this->db->select('match_id');
        $this->db->where('match_id',$id);
        $this->db->where('cancelled','1');
        $this->db->where('refund','0');
        $que = $this->db->get('match');
        if($que->num_rows()>0)
        {
            return $que->row_array();
        }
        else
        {
            return false;
        }
    }
    
    function get_my_match_which_cancelled($id)
    {
        $this->db->select('my_match_id,credit_type,bonus_type,winning_type,user_id');
        $this->db->where('match_id',$id);
        $this->db->where('contest_cancell','0');
        $this->db->order_by('my_match_id','desc');
        $que = $this->db->get('my_match');
        if($que->num_rows()>0)
        {
            return $que->result_array();
        }
        else
        {
            return false;
        }
    }
    
    function get_today_match_data()
    {
        $date = date('Y-m-d');
        $this->db->order_by($this->id, $this->order);
        $this->db->where('match_status !=','Result');
        $this->db->where('DATE(match_date_time)',$date);
        $this->db->order_by('match_date_time','desc');
        return $this->db->get($this->table)->result();
    }
    
    function contest_match_cancelled($contestid,$matchid)
    {
        $this->db->select('credit_type,bonus_type,winning_type,user_id');
        $this->db->where('match_id',$matchid);
        $this->db->where('contest_id',$contestid);
        $this->db->order_by('my_match_id','desc');
        $que = $this->db->get('my_match');
        if($que->num_rows()>0)
        {
            return $que->result_array();
        }
        else
        {
            return false;
        }
    }
    
    function get_team_record($id)
    {   
        $this->db->select('*');  
        $this->db->where('match_id',$id['match_id']);
        $resp =  $this->db->get('my_team')->result();
        if($resp !="")
        {
            return $resp;
        }    
        else
        {
            return false;
        } 
    }
    
    function my_team_all_players($userid,$team_id)
    {
        $this->db->select('player_id,is_captain,is_vicecaptain');
        $this->db->where('my_team_id',$team_id);
        $this->db->where('user_id',$userid);
        return $this->db->get('my_team_player')->result();
    }
    
    function player_points_for_user($player_id,$my_team_id,$match_id)
    {
        $this->db->select('my_team_player.is_captain,my_team_player.is_vicecaptain,match_players.total_points,match_players.batting_points,match_players.bolling_points,match_players.fielding_points,match_players.second_innings_batting,match_players.second_innings_bolling,match_players.second_innings_fielding');
        $this->db->from('my_team_player');
        $this->db->where('my_team_player.player_id',$player_id);
        $this->db->where('my_team_player.my_team_id',$my_team_id);
        $this->db->where('match_players.matchid',$match_id);
        $this->db->join('match_players','match_players.playerid= my_team_player.player_id');
        $resp =  $this->db->get()->row();
        if($resp !="")
        {
            return $resp;
        }    
        else
        {
            return false;
        }  
    }
    
    function update_leaderboard($team_id,$match_id,$user_id,$points)
    {
        $data = array('points' =>$points);
        $this->db->where('teamid',$team_id);
        $this->db->where('matchid',$match_id);
        $this->db->where('user_id',$user_id);
        $this->db->update('leaderboard',$data);
    }

}