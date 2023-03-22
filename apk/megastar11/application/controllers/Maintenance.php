<?php

if (!defined('BASEPATH'))
    exit('No direct script access allowed');

class Maintenance extends MY_Controller
{
    function __construct()
    {
        parent::__construct();
        $this->load->model('Website_model');
    }

    public function index()
    {
        $maintenance = $this->Website_model->maintenance_data();
        
        $data = array('maintenance' => $maintenance);
		$this->load->view('../modules/loggedin_template/header',$data);
        $this->load->view('maintenance/list');
		$this->load->view('../modules/loggedin_template/footer');
    }

    public function on_off()
    {
        $id = $this->input->post('id');
        $this->db->select('maintenance_status');
        $this->db->where('id','1');
        $record = $this->db->get('version')->row_array();

        if($record['maintenance_status'] == "1")
        {
            $status = "0";
        }   
        if($record['maintenance_status'] == "0")
        {
            $status = "1";
        }   
        $data = array('maintenance_status' =>$status);
        
        $this->db->where('id','1');
        $this->db->update('version',$data);
        
        if($status ==1)
        {
            echo json_encode('<button style="border-radius: 6px;" type="button" class="btn btn-danger">Under Maintenance</button>');
        }    
        else if($status ==0){
            echo json_encode('<button style="border-radius: 6px;" type="button" class="btn btn-success">Active</button>');
        } 
            
        
    }
}
