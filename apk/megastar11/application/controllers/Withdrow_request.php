<?php

if (!defined('BASEPATH'))
    exit('No direct script access allowed');

class Withdrow_request extends MY_Controller
{
    function __construct()
    {
        parent::__construct();
        $this->load->model('User_model');
        $this->load->model('Transaction_model');
        $this->load->library('form_validation');
    }

    public function index()
    {
        $q = urldecode($this->input->get('q', TRUE));
        $start = intval($this->input->get('start'));
        
        if ($q <> '') {
            $config['base_url'] = base_url() . 'players/index.html?q=' . urlencode($q);
            $config['first_url'] = base_url() . 'players/index.html?q=' . urlencode($q);
        } else {
            $config['base_url'] = base_url() . 'players/index.html';
            $config['first_url'] = base_url() . 'players/index.html';
        }

        $config['per_page'] = 10;
        $config['page_query_string'] = TRUE;
        // $config['total_rows'] = $this->Players_model->total_rows($q);
        $request = $this->Transaction_model->get_limit_data($config['per_page'], $start, $q);
        //print_r($request);die();
        $this->load->library('pagination');
        $this->pagination->initialize($config);

        $data = array(
            'request_data' => $request,
            'q' => $q,
            'pagination' => $this->pagination->create_links(),
            'total_rows' => $config['total_rows'],
            'start' => $start,
        );
        $this->load->view('../modules/loggedin_template/header',$data);
        $this->load->view('withdrow_request/request_list', $data);
        $this->load->view('../modules/loggedin_template/footer',$data);
    }

    

    public function _rules() 
    {
	$this->form_validation->set_rules('name', 'name', 'trim|required');
	$this->form_validation->set_rules('designationid', 'designationid', 'trim|required');
	$this->form_validation->set_rules('teamid', 'teamid', 'trim|required');
	$this->form_validation->set_rules('credit_points', 'credit points', 'trim|required');
	// $this->form_validation->set_rules('image', 'image', 'trim|required');
	// $this->form_validation->set_rules('created_date', 'created date', 'trim|required');
	// $this->form_validation->set_rules('modified_date', 'modified date', 'trim|required');

	$this->form_validation->set_rules('id', 'id', 'trim');
	$this->form_validation->set_error_delimiters('<span class="text-danger">', '</span>');
    }
    
    public function change_status()
    {
        $id = $this->input->post('id');
        $val = $this->input->post('val');
        $this->db->select('withdrow_request');
        $this->db->where('id',$id);
        $record = $this->db->get('transection')->row_array();
        if($record["withdrow_request"] == "1")
        {
            if($val == "2")
            {
                $status = "2";
                $message = "Payment done by Admin.";
            }
            else if($val == "3")
            {
                $status = "3";
                $message = "Withdraw request rejected by admin.";
            }
            
            
            $data = array('withdrow_request' =>$status, "transection_mode"=>$message);
            
            $this->db->where('id',$id);
            $update = $this->db->update('transection',$data);
            
            if($update == "1" && $status =="3")
            {
                $this->db->select('user_id,amount');
                $this->db->where('id',$id);
                $resp = $this->db->get('transection')->row_array();
                
                $newData = array('user_id'=> $resp['user_id'],
                                "amount"=>$resp['amount'],
                                'type'=>'winning',
                                "transaction_status"=>"SUCCESS",
                                "transection_mode"=>"Self Withdraw amount revert",
                                "created_date"=>date("Y-m-d h:i:s") );
                $this->db->insert('transection',$newData);
            }
            
        }
        else
        {
            $status ==0;
        }
        
        if($status ==2)
        {
            echo json_encode('<button style="border-radius: 6px;" type="button" class="btn btn-success">Payment Accept</button>');
        }    
        else if($status ==3){
            echo json_encode('<button style="border-radius: 6px;" type="button" class="btn btn-danger">Payment Reject</button>');
        } 
            
        
    }

}
