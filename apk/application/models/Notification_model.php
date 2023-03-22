<?php

if (!defined('BASEPATH'))
    exit('No direct script access allowed');

class Notification_model extends CI_Model
{

    public $table = 'notification';
    public $id = 'id';
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
        $this->db->like('contest_id', $q);
	$this->db->or_like('id', $q);
	$this->db->or_like('match_id', $q);	
	$this->db->from($this->table);
        return $this->db->count_all_results();
    }

    // get data with limit and search
    function get_limit_data($limit, $start = 0, $q = NULL) {
        $this->db->order_by($this->id, $this->order);
        $this->db->like('id', $q);
	$this->db->or_like('match_id', $q);
	$this->db->or_like('contest_id', $q);	
	//$this->db->limit($limit, $start);
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

    function get_match_by_id($id)
    {
        $this->db->where('match_id',$id);
        return $this->db->get('match')->row();
    }


    function match_list()
    {
        $today = date("Y-m-d H:i:s");
        $this->db->select('match_id,title');
        $this->db->where('time >', $today);
        return  $this->db->get('match')->result();
    }

     // get data with limit and search
    function get_limit_data_contest($limit, $start = 0, $q = NULL,$id) {
    $this->db->order_by('id', $this->order);
    //$this->db->or_like('rank', $q);
   // $this->db->or_like('price', $q);
   // $this->db->limit($limit, $start);
    $this->db->where('id',$id);
    return $this->db->get('notification')->result();
    }

    

}

/* End of file Contest_model.php */
/* Location: ./application/models/Contest_model.php */
/* Please DO NOT modify this information : */
/* Generated by Harviacode Codeigniter CRUD Generator 2018-10-29 11:26:46 */
/* http://harviacode.com */