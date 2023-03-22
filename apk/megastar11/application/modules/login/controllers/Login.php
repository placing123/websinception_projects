<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Login extends MY_Controller {

    public function __construct() {
        parent::__construct();
        $this->load->model('login/login_model');
       date_default_timezone_set('Asia/Calcutta');  
    }

    public function check_internet_response(){
        echo json_encode(array('status'=>'1'));
    }
    
	public function index()
	{

        $data = array();
        if ($this->login_model->check_login() || isset($_SESSION['id'])) {
                $user_id = $this->session->userdata('id');           
                $user_name = $this->session->userdata('name');
                
                if($user_id ==1){
					
                    redirect(site_url('dashboard'));
                }else{
                    redirect(base_url());
                }
            }
            else{
            if ($this->input->post()) {
                $data = array('error_msg' => 'Invalid username or password.');
            } else {
                $data = array();
            }
			$data['js_script'] = 'login';
            $this->load->view('login', $data);

        }

	}

	
	public function logout()
	{
        if($this->session->userdata('id')){
            $this->session->sess_destroy();
        }
        redirect(base_url());
	}
}
