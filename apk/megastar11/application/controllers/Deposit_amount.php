<?php

if (!defined('BASEPATH'))
    exit('No direct script access allowed');

class Deposit_amount extends MY_Controller
{
    function __construct()
    {
        parent::__construct();
        $this->load->model('deposit_amount_model');
        $this->load->library('form_validation');
    }

    public function index()
    {
    	$q = urldecode($this->input->get('q', TRUE));
        $start = intval($this->input->get('start'));
        
        if ($q <> '') {
            $config['base_url'] = base_url() . 'deposit_amount/index.html?q=' . urlencode($q);
            $config['first_url'] = base_url() . 'deposit_amount/index.html?q=' . urlencode($q);
        } else {
            $config['base_url'] = base_url() . 'deposit_amount/index.html';
            $config['first_url'] = base_url() . 'deposit_amount/index.html';
        }

        $config['per_page'] = 10;
        $config['page_query_string'] = TRUE;
        $config['total_rows'] = $this->deposit_amount_model->total_rows($q);
        $bonuss = $this->deposit_amount_model->get_limit_data($config['per_page'], $start, $q);
        
        $this->load->library('pagination');
        $this->pagination->initialize($config);
        $data = array(
            'bonuss' => $bonuss,
            'q' => $q,
            'pagination' => $this->pagination->create_links(),
            'total_rows' => $config['total_rows'],
            'start' => $start,
        );
        $this->load->view('../modules/loggedin_template/header',$data);
        $this->load->view('deposit_amount/bonus_list', $data);
        $this->load->view('../modules/loggedin_template/footer',$data);
    }

    public function delete($id) 
    {
        $row = $this->deposit_amount_model->get_by_id($id);

        if ($row) {
            $this->deposit_amount_model->delete($id);
            $this->session->set_flashdata('message', 'Delete Record Success');
            redirect(site_url('deposit_amount'));
        } else {
            $this->session->set_flashdata('message', 'Record Not Found');
            redirect(site_url('deposit_amount'));
        }
    }
    
    public function update($id) 
    {
        $row = $this->deposit_amount_model->get_by_id($id);

        if ($row) {
            $data = array(
                'button' => 'Update',
                'action' => site_url('deposit_amount/update_action'),
                        'id' => set_value('id', $row->id),
                        'amount' => set_value('amount', $row->amount),
                        'percentage' => set_value('percentage', $row->percentage)
                    );
            $this->load->view('../modules/loggedin_template/header',$data);
            $this->load->view('deposit_amount/bonus_form', $data);
            $this->load->view('../modules/loggedin_template/footer',$data);
        } else {
            $this->session->set_flashdata('message', 'Record Not Found');
            redirect(site_url('deposit_amount'));
        }
    }

    public function create() 
    {       
        $data = array(
        'button' => 'Create',
        'action' => site_url('deposit_amount/create_action'),
        'id' => set_value('id'),
        'amount' => set_value('amount'),
        'percentage' => set_value('percentage'),
        
    );
        $this->load->view('../modules/loggedin_template/header',$data);
        $this->load->view('deposit_amount/bonus_form', $data);
            $this->load->view('../modules/loggedin_template/footer',$data);
    }

    public function create_action() 
    {
        $this->_rules();

        if ($this->form_validation->run() == FALSE) {
            $this->create();
        } else {
            $data = array(
        'amount' => $this->input->post('amount',TRUE),
        'percentage' => $this->input->post('percentage',TRUE),
        
        );

            $this->deposit_amount_model->insert($data);
            $this->session->set_flashdata('message', 'Create Record Success');
            redirect(site_url('deposit_amount'));
        }
    }
    
    public function update_action() 
    {
        $this->_rules();

        if ($this->form_validation->run() == FALSE) {
            $this->update($this->input->post('id', TRUE));
        } else {
            $data = array(
                'amount' => $this->input->post('amount',TRUE),
                'percentage' => $this->input->post('percentage',TRUE)
                  );

            $this->deposit_amount_model->update($this->input->post('id', TRUE), $data);
           // echo $this->db->last_query();die;
            $this->session->set_flashdata('message', 'Update Record Success');
            redirect(site_url('deposit_amount'));
        }
    }
    
     public function _rules() 
    {
	$this->form_validation->set_rules('percentage', 'percentage', 'trim|required');
    $this->form_validation->set_rules('amount', 'Fix Amount Value', 'trim|required');
	$this->form_validation->set_error_delimiters('<span class="text-danger">', '</span>');
    }
}
?>