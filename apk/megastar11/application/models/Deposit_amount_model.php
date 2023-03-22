<?php

if (!defined('BASEPATH'))
    exit('No direct script access allowed');

class Deposit_amount_model extends CI_Model
{

    public $table = 'deposite_bonus';
    public $id = 'id';
    public $order = 'DESC';

    function __construct()
    {
        parent::__construct();
    }

     function insert($data)
    {
        $this->db->insert($this->table, $data);
    }

    //get total rows
    function total_rows($q = NULL) {
        $this->db->like('id', $q);
	$this->db->from($this->table);
    $this->db->order_by('id','desc');
        return $this->db->count_all_results();
    }

     // get data with limit and search
    function get_limit_data($limit, $start = 0, $q = NULL) {      
        $this->db->order_by('id','desc');
           return $this->db->get($this->table)->result();
    }
    
     function get_by_id($id)
    {
        $this->db->where($this->id, $id);
        return $this->db->get($this->table)->row();
    }
    
     function update($id, $data)
    {
        $this->db->where($this->id, $id);
        $this->db->update($this->table, $data);
    }

    function delete($id)
    {
        $this->db->where($this->id, $id);
        $this->db->delete($this->table);
    }

    function get_all()
    {
        $this->db->order_by($this->id, $this->order);
        return $this->db->get($this->table)->result();
    }

   
    
}