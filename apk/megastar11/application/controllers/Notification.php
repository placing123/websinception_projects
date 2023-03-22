<?php

if (!defined('BASEPATH'))
    exit('No direct script access allowed');

class Notification extends MY_Controller
{
    function __construct()
    {
        parent::__construct();
        $this->load->model('Notification_model');
        $this->load->model('Contest_model');
        $this->load->model('Match_model');  
        $this->load->model('User_model');  
        $this->load->library('form_validation');
    }

    public function index()
    {
        $q = urldecode($this->input->get('q', TRUE));
        $start = intval($this->input->get('start'));
        
        if ($q <> '') {
            $config['base_url'] = base_url() . 'contest/index.html?q=' . urlencode($q);
            $config['first_url'] = base_url() . 'contest/index.html?q=' . urlencode($q);
        } else {
            $config['base_url'] = base_url() . 'contest/index.html';
            $config['first_url'] = base_url() . 'contest/index.html';
        }

        $config['per_page'] = 10;
        $config['page_query_string'] = TRUE;
        $config['total_rows'] = $this->Notification_model->total_rows($q);
        $notification = $this->Notification_model->get_limit_data($config['per_page'], $start, $q);
        $this->load->library('pagination');
        $this->pagination->initialize($config);       
        $data = array(
            'notification_data' => $notification,
            'q' => $q,
            'pagination' => $this->pagination->create_links(),
            'total_rows' => $config['total_rows'],
            'start' => $start,
        );
		$this->load->view('../modules/loggedin_template/header',$data);
        $this->load->view('notification/notification_list');
		$this->load->view('../modules/loggedin_template/footer');
    }

    public function read($id) 
    {
        $row = $this->Notification_model->get_by_id($id);
        if ($row) {
            $data['id'] = $row->id;

           $data['contest'] = $this->Contest_model->get_by_id($row->contest_id);
           $data['match'] = $this->Match_model->get_by_id($row->match_id);
		
	    
			$this->load->view('../modules/loggedin_template/header',$data);
            $this->load->view('notification/notification_read');
				$this->load->view('../modules/loggedin_template/footer');
        } else {
            $this->session->set_flashdata('message', 'Record Not Found');
            redirect(site_url('contest'));
        }
    }

    public function create() 
    {
        $data = array(
            'button' => 'Create',
            'action' => site_url('notification/create_action'),
            'contest_id' => set_value('contest_id'),	   
            'match_id' => set_value('match_id'),
            'title' => set_value('title'),
            'msg' => set_value('msg')//,
            /*'notification_type' => set_value('notification_type'),
            'notification_image' => set_value('image'),*/
	    );

        $data['matchs'] = $this->Notification_model->match_list();
		$this->load->view('../modules/loggedin_template/header',$data);
        $this->load->view('notification/notification_form', $data);
		$this->load->view('../modules/loggedin_template/footer',$data);
    }
    
    public function create_action()  
    {  
        $this->set_rules();

        if ($this->form_validation->run() == FALSE) {
            $this->create();
        } else {
            
            $type =  $this->input->post('user_type',TRUE);

           if($type == "1")
           {
                $resp =   $this->User_model->get_all();
                // echo $this->db->last_query();die();
                foreach ($resp as $key) 
                {
                    $data[] = $key->user_id;
                }

                $diff = $data;
                // echo "<pre>";print_r($diff);die();
                $result = implode(",",$data);
           } 
           elseif($type == "2")
           {
                $resp =   $this->User_model->get_all();
                foreach ($resp as $key) 
                {
                    $data[] = $key->user_id;
                }
                $not_participate =  $this->User_model->not_participate_15_days();
                foreach ($not_participate as  $value) {
                    $not_15_days[] = $value->user_id;
                }

                $diff = array_diff($data,$not_15_days);
                $result = implode(",",$diff);

           } 
           elseif($type == "3")
           {
                $resp =   $this->User_model->get_all();
                foreach ($resp as $key) 
                {
                    $data[] = $key->user_id;
                }
                $not_participate =  $this->User_model->not_participate_30_days();
                foreach ($not_participate as  $value) {
                    $not_30_days[] = $value->user_id;
                }
                $diff = array_diff($data,$not_30_days);
                $result = implode(",",$diff);

           }
           
           if (isset($_FILES['image']['name']) && !empty($_FILES['image']['name'])) {
            
                $folder = "uploads/notification_image/";
                
                if (move_uploaded_file($_FILES['image']['tmp_name'], $folder . preg_replace('/\s+/', '_', $_FILES['image']['name']))) {
                    $image = $_FILES['image']['name'];
                    $image = preg_replace('/\s+/', '_', $image);
                }
            }
            else
            {
                $image = NULL;
            }
            
            foreach ($diff as $user) 
            {     

                $device = $this->db->get_where('registration',array('user_id'=>$user))->row();    
                
                $title = $this->input->post('title',TRUE);
                $token = $device->mobiletoken;
                
                $message = $this->input->post('msg',TRUE);
                
                $notification_image = $_FILES['image']['name']; 
                $notification_type = $this->input->post('notification_type',TRUE);
                
                if($token !="")
                { 
                    $suc = $this->send_data($message,$title,$token,$notification_image,$notification_type); 
                }  
                
            }
            
            $data = array(
    		'contest_id' => $this->input->post('contest_id',TRUE),
    		'match_id' => $this->input->post('match_id',TRUE),
            'type' => $this->input->post('user_type',TRUE),
            'title' => $this->input->post('title',TRUE),
            'message' => $this->input->post('msg',TRUE),
            'notification_type' => $this->input->post('notification_type',TRUE),
            'notification_image' => $image,
            'user_ids' => $result,	
            'created_date'=> date("Y-m-d H:i:s"),	
    	    ); 
                      
            $this->Notification_model->insert($data);
            $this->session->set_flashdata('message', 'Notification send Successfully');
            $this->index();
            // redirect(site_url('notification'));
            
        }
    }
    
    
    /*$config['upload_path']          = './uploads/notification_image/';
    		$config['allowed_types']        = 'gif|jpg|png';
     		//$config['max_size']             = 307200; //3MB
    		$config['encrypt_name']			= TRUE;
    
                    $this->load->library('upload', $config);
    
                    if ( ! $this->upload->do_upload($img))
                    {
                            $error = $this->upload->display_errors();
    						print_r($error); die;
                    }
                    else
                    {
                            $data = $this->upload->data();
    						//return "uploads/slider/".$data['file_name'];
                    }*/
    
    
    public function update($id) 
    {
        $row = $this->Notification_model->get_by_id($id);

        if ($row) {
            $data = array(
                'button' => 'Update',
                'action' => site_url('notification/update_action'),
		'contest_id' => set_value('contest_id', $row->contest_id),
		'match_id' => set_value('match_id', $row->match_id),
		'id' => set_value('contest_tag', $row->id),
        'title' => set_value('title', $row->title),
        'msg' => set_value('msg', $row->message),
        'type' => set_value('type', $row->type),
	    );

            $data['contests'] = $this->Contest_model->get_contest_by_match($row->match_id);
            $data['matchs'] = $this->Notification_model->match_list();
			$this->load->view('../modules/loggedin_template/header',$data);
            $this->load->view('notification/notification_form', $data);
				$this->load->view('../modules/loggedin_template/footer',$data);
        } else {
            $this->session->set_flashdata('message', 'Record Not Found');
            redirect(site_url('notification'));
        }
    }
    
    public function update_action() 
    {
        $this->set_rules();

        if ($this->form_validation->run() == FALSE) {
            $this->update($this->input->post('id', TRUE));
        } else {
        //   $type =  $this->input->post('user_type',TRUE);

        //   if($type == "1")
        //   {
        //         $resp =   $this->User_model->get_all();
        //         foreach ($resp as $key) 
        //         {
        //             $data[] = $key->user_id;
        //         }
        //         $result = implode(",",$data);
        //   } 
        //   elseif($type == "2")
        //   {
        //         $resp =   $this->User_model->get_all();
        //         foreach ($resp as $key) 
        //         {
        //             $data[] = $key->user_id;
        //         }
        //         $not_participate =  $this->User_model->not_participate_15_days();
        //         foreach ($not_participate as  $value) {
        //             $not_15_days[] = $value->user_id;
        //         }

        //         $diff = array_diff($data,$not_15_days);
        //         $result = implode(",",$diff);

        //   } 
        //   elseif($type == "3")
        //   {
        //         $resp =   $this->User_model->get_all();
        //         foreach ($resp as $key) 
        //         {
        //             $data[] = $key->user_id;
        //         }
        //         $not_participate =  $this->User_model->not_participate_30_days();
        //         foreach ($not_participate as  $value) {
        //             $not_30_days[] = $value->user_id;
        //         }
        //         $diff = array_diff($data,$not_30_days);
        //         $result = implode(",",$diff);

        //   } 

            $data = array(
            'contest_id' => $this->input->post('contest_id',TRUE),
            'match_id' => $this->input->post('match_id',TRUE),
            'type' => $this->input->post('user_type',TRUE),
            'title' => $this->input->post('title',TRUE),
            'message' => $this->input->post('msg',TRUE),
            'user_ids' => $result,   
            );    

            $this->Notification_model->update($this->input->post('id', TRUE), $data);
            $this->session->set_flashdata('message', 'Update Record Success');
            redirect(site_url('notification'));
        }
    }
    
    public function delete($id) 
    {
        $row = $this->Notification_model->get_by_id($id);

        if ($row) {
            $this->Notification_model->delete($id);
            $this->session->set_flashdata('message', 'Delete Record Success');
            redirect(site_url('notification'));
        } else {
            $this->session->set_flashdata('message', 'Record Not Found');
            redirect(site_url('notification'));
        }
    }


    public function  set_rules()
    {
        $this->form_validation->set_rules('user_type', 'User Type', 'trim|required');
        $this->form_validation->set_rules('title', 'Title', 'trim|required');
        $this->form_validation->set_rules('msg', 'Message', 'trim|required');
       // $this->form_validation->set_rules('contest_id', 'Contest', 'trim|required');
        //$this->form_validation->set_rules('match_id', 'Match', 'trim|required');
        $this->form_validation->set_error_delimiters('<span class="text-danger">', '</span>');
    }

    public function get_contest_by_matchid()
    {
        $id = $this->input->post('id');
        $data = $this->Contest_model->get_contest_by_matchid($id);
        echo $data;
        
    }


   

    public function send_data ($message,$title,$token,$notification_image,$notification_type)
    { 
       //$apiKey = "AAAA1Yx65HE:APA91bFCWHH4IPw_HKwvREwK5OEyTwsxAqOxqK8cIhVxV2yKwHqEKWvyhbfEzJcysAluckI61FFtnV0Wm9eNeVp_-RMQ9VOexaXe5-qvxFw_p3_LQWlIEcIWIZqbwcWh7k_FKZPPBeEY";
       
       $apiKey = "AAAAVwc3hT8:APA91bFyfkp72M68LJogNNxy_gA5xIU6e9LliEDsG9gmcXI8lbFPqVR9bFbw-xO62ZFir8XrKMGYoT1KCVB9FIrjVeq1LEcpsPq_88mDjImptAz2p0NN6KFukKXnESDgoKTpmV7ME_Jx";
        
        $post = array(
                        'to' => '/topics/all',
                        'data' => array (
                                'title' => $title,
                                'message' => $message,
                                'notification_image' => $notification_image,
                                'notification_type' => $notification_type
                        )
                     );
                     //'notification_type' => "1",
       
    
        $headers = array( 
                            'Authorization: key=' . $apiKey,
                            'Content-Type: application/json'
                        );
    
        $ch = curl_init();  
        curl_setopt($ch, CURLOPT_URL, 'https://fcm.googleapis.com/fcm/send');   
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);    
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($post));
        curl_setopt($ch, CURLOPT_FOLLOWLOCATION, TRUE);
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);  
        $result = curl_exec($ch);
        
        if (curl_errno($ch)) {
            echo 'FCM error: ' . curl_error($ch);
        } else {
            return true;
        }
        curl_close($ch);
       
       
       // print_r($token);die();
    
            // define('server_key', 'AAAA-785AVo:APA91bHBFDtc5QrbZLd7Agx2la5Fr2WNnp6q3Ow9XtKpZkKwz_bxSDUXIZLtIw3jgobFe2JMc8H75EYdHAkUEMH6wP21w4qrFYLp-0YWql8tGl3kHEpnGZ96ptTadK8OWMIlSiDVYDVs');

            // $token = [$token];
            
            // $header = [ 'Authorization: key='.server_key,
            //             'Content-Type: application/json'
            //           ];


            //      $msg = [
            //           'title' =>$title,
            //           'message' => $message
            //      ];    

            //      $payload = [
            //               'registration_ids' => $token,
            //               'data' => $msg
            //      ]; 

            // $curl = curl_init();

            // curl_setopt_array($curl, array(
            //   CURLOPT_URL => "https://fcm.googleapis.com/fcm/send",
            //   CURLOPT_RETURNTRANSFER => true,
              
            //   CURLOPT_CUSTOMREQUEST => "POST",
            //   CURLOPT_POSTFIELDS => json_encode($payload),
            //   CURLOPT_HTTPHEADER => $header
            // ));

            // $response = curl_exec($curl);
            // $err = curl_error($curl);

            // curl_close($curl);

            // if ($err) {
            //   echo "cURL Error #:" . $err; //die();
            // } else {
            //  // echo $response; die('success');

            //   $res =  json_decode($response);// die('success');
                
            //   $result = $res->success;
            //   if($result == 1)
            //   {
            //     return true;
            //   }
            //   else
            // {
            //         return false;

            //     }
            // }
    }
  

}


