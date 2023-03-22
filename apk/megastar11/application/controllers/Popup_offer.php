<?php

if (!defined('BASEPATH'))
    exit('No direct script access allowed');

class Popup_offer extends MY_Controller
{
    function __construct()
    {
        parent::__construct();
        $this->load->model('Popup_model');
    }

    public function index()
    {
        $popup_offer = $this->Popup_model->get_all();
        
        $data = array('offers' => $popup_offer);
		$this->load->view('../modules/loggedin_template/header',$data);
        $this->load->view('popup_offer/list');
		$this->load->view('../modules/loggedin_template/footer');
    }

    public function create() 
    {
        $data = array(
            'button' => 'Create',
            'action' => site_url('popup_offer/create_action'),
			    'id' => set_value('id'),
			    'title' => set_value('title'),
			    'image' => set_value('image'),
			    'created_date' => set_value('created_date'),
			    'modified_date' => set_value('modified_date'),
			);
	    $this->load->view('../modules/loggedin_template/header',$data);
        $this->load->view('popup_offer/offer_form', $data);
		$this->load->view('../modules/loggedin_template/footer',$data);
    }

    public function create_action() 
    {
           if (isset($_FILES['image']['name']) && !empty($_FILES['image']['name'])) {
            $folder = "uploads/popup_offer/";
            
            if (move_uploaded_file($_FILES['image']['tmp_name'], $folder . preg_replace('/\s+/', '_', $_FILES['image']['name']))) {
                $image = $_FILES['image']['name'];
                $image = preg_replace('/\s+/', '_', $image);
            }
            $data = array('image'=>$image,
            	'title'=>$this->input->post('title'));

            $this->Popup_model->insert($data);
           // echo $this->db->last_query();die();
            $this->session->set_flashdata('message', 'Create Record Success');
            redirect(site_url('popup_offer'));   
        }
    }

    public function update($id) 
    {
        $row = $this->Popup_model->get_by_id($id);

        if ($row) {
            $data = array(
                'button' => 'Update',
                'action' => site_url('popup_offer/update_action'),
        		'id' => set_value('id', $row->id),
        		'title' => set_value('id', $row->title),
                'old_image' => set_value('id', $row->image),
		
	    );
           
			$this->load->view('../modules/loggedin_template/header',$data);
            $this->load->view('popup_offer/offer_form', $data);
				$this->load->view('../modules/loggedin_template/footer',$data);
        } else {
            $this->session->set_flashdata('message', 'Record Not Found');
            redirect(site_url('popup_offer'));
        }
    }
    
    public function update_action() 
    {
        if (isset($_FILES['image']['name']) && !empty($_FILES['image']['name'])) {
            $folder = "uploads/popup_offer/";
            
            if (move_uploaded_file($_FILES['image']['tmp_name'], $folder . preg_replace('/\s+/', '_', $_FILES['image']['name']))) {
                $image = $_FILES['image']['name'];
                $image = preg_replace('/\s+/', '_', $image);
            }
        }
        else
       {
            $image = $this->input->post('old_image',TRUE);
       }     
           $data =  array('image'=>$image,'title'=>$this->input->post('title'));

            $this->Popup_model->update($this->input->post('id', TRUE), $data);
            $this->session->set_flashdata('message', 'Update Record Success');
            redirect(site_url('popup_offer'));
        
    }

     public function delete($id) 
    {
        $row = $this->Popup_model->get_by_id($id);

        if ($row) {
            $this->Popup_model->delete($id);
            $this->session->set_flashdata('message', 'Delete Record Success');
            redirect(site_url('popup_offer'));
        } else {
            $this->session->set_flashdata('message', 'Record Not Found');
            redirect(site_url('popup_offer'));
        }
    }

    public function on_off()
    {
        $id = $this->input->post('id');
        $this->db->select('status');
        $this->db->where('id',$id);
        $record = $this->db->get('popup_offer')->row_array();

        if($record['status'] == "1")
        {
            $status = "0";
        }   
        if($record['status'] == "0")
        {
            $status = "1";
        }   
        $data = array('status' =>$status);
        
        $this->db->where('id',$id);
        $this->db->update('popup_offer',$data);
        
        if($status ==1)
        {
            echo json_encode('<button style="border-radius: 6px;" type="button" class="btn btn-success">Active</button>');
        }    
        else if($status ==0){
            echo json_encode('<button style="border-radius: 6px;" type="button" class="btn btn-danger">Not Active</button>');
        } 
            
        
    }
}
