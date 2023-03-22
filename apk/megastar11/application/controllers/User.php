<?php

if (!defined('BASEPATH'))
    exit('No direct script access allowed');

class User extends MY_Controller
{
    function __construct()
    {
        parent::__construct();
        $this->load->model('User_model');
    }

    function index()
    {
       $q = urldecode($this->input->get('q', TRUE));
        $start = intval($this->input->get('start'));
        
        if ($q <> '') {
            $config['base_url'] = base_url() . 'user/index.html?q=' . urlencode($q);
            $config['first_url'] = base_url() . 'user/index.html?q=' . urlencode($q);
        } else {
            $config['base_url'] = base_url() . 'user/index.html';
            $config['first_url'] = base_url() . 'user/index.html';
        }

        $config['per_page'] = 10;
        $config['page_query_string'] = TRUE;
        $config['total_rows'] = $this->User_model->total_rows($q);
        $user = $this->User_model->get_limit_data($config['per_page'], $start, $q);

        $this->load->library('pagination');
        $this->pagination->initialize($config);

        $data = array(
            'user_data' => $user,
            'q' => $q,
            'pagination' => $this->pagination->create_links(),
            'total_rows' => $config['total_rows'],
            'start' => $start,
        );
			$this->load->view('../modules/loggedin_template/header',$data);
            $this->load->view('user/user_list', $data);
			$this->load->view('../modules/loggedin_template/footer');
    }
    
    
    function wallet()
    {
        $id = $this->uri->segment(3);
        $data['wallets'] = $this->User_model->get_wallet($id);
        $data['wallets_amount'] = $this->User_model->get_wallet_amount($id);
        
        $data['wallets_credit'] = $this->User_model->get_wallet_amount_credit($id);
        $data['wallets_debit'] = $this->User_model->get_wallet_amount_debit($id);
        
        $data['wallets_winning'] = $this->User_model->get_wallet_amount_winning($id);
        $data['wallets_winning_debit'] = $this->User_model->get_wallet_amount_winning_debit($id);
        
       // echo $this->db->last_query();die;
    // echo "<pre>";  print_r($data);die;
        $this->load->view('../modules/loggedin_template/header',$data);
        $this->load->view('user/walletInfo', $data);
		$this->load->view('../modules/loggedin_template/footer');
    }
    
    function wallet_update()
    {
        $id = $this->uri->segment(3);
        
        $data['info'] = $this->User_model->get_txn_record($id);
         
        $this->load->view('../modules/loggedin_template/header',$data);
        $this->load->view('user/wallet_update', $data);
		$this->load->view('../modules/loggedin_template/footer');
    }   
    
    function user_wallet_update()
    {
       /// print_r($_POST);die;
       // $this->rule();
       $amount =  $this->input->post('value');
       $user_id =  $this->input->post('user_id');
       $id =  $this->input->post('id');
       
       $data = array('amount'=> $amount);
       
       $this->db->where('id',$id);
       $this->db->update('transection',$data);
       $this->session->set_flashdata('message','Amount update successfully');
       redirect('user/wallet/'.$user_id);
       
    }
    
    function UpdateReferral()
    {
        $RuserId =  $this->input->post('RuserId');
        $Rreferral =  $this->input->post('Rreferral');
        $data = array('referral_code'=> $Rreferral);

        $this->db->select('referral_code');
        $this->db->where('referral_code',$Rreferral);
        $check = $this->db->get('registration')->num_rows();
        if($check > 0)
        {
            echo "Exist";
        } 
        else
        {
            $this->db->where('user_id',$RuserId);
            $update = $this->db->update('registration',$data);

            if($update)
            {
                echo "Success"; 
            }    
            else
            {
                echo "Error";
            }
        }   
       
            
    }

    function ReferralExist()
    {
        $RuserId =  $this->input->post('RuserId');
        $Rreferral =  $this->input->post('Rreferral');
        $this->db->select('referral_code');
        $this->db->where('referral_code',$Rreferral);
        $check = $this->db->get('registration')->num_rows();
        if($check > 0)
        {
            echo "Success";
        } 
        else
        {
            echo "No";
        }
    }
    
    function referral_users()
    {
        $user_id = base64_decode($this->uri->segment(3));
        $ref_code = base64_decode($this->uri->segment(4));
        $data['user_data'] = $this->User_model->referral_users_list($ref_code);
        $data['users_info'] = $this->User_model->users_info($user_id);
        
        $this->load->view('../modules/loggedin_template/header',$data);
        $this->load->view('user/referral_users_list');
		$this->load->view('../modules/loggedin_template/footer');
    }
    function user_wallet_addcash()
    {
       /// print_r($_POST);die;
       // $this->rule();
       $amount =  $this->input->post('txt_cash');
       $user_id =  $this->input->post('user_id');
       
       $data = array('user_id'=> $user_id, 'amount'=> $amount, 'type'=> "winning", 'transaction_status'=> "SUCCESS", 'transection_mode'=> "for winning contest", 'contest_id'=> "1");
       
       $this->db->insert('transection',$data);
       $this->session->set_flashdata('message','Cash Amount add successfully');
       redirect('user/wallet/'.$user_id);
       
    }
    function user_wallet_addbonus()
    {
       /// print_r($_POST);die;
       // $this->rule();
       $amount =  $this->input->post('txt_bonus');
       $user_id =  $this->input->post('user_id');
       
       $data = array('user_id'=> $user_id, 'amount'=> $amount, 'type'=> "bonus", 'transaction_status'=> "SUCCESS", 'transection_mode'=> "referral bonus");
       
       $this->db->insert('transection',$data);
       $this->session->set_flashdata('message','Bonus Amount add successfully');
       redirect('user/wallet/'.$user_id);
       
    }
}