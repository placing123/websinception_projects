<?php  require APPPATH.'/libraries/REST_Controller.php';
use Restserver\Libraries\REST_Controller;
class Mainapi extends REST_Controller
{
    // public $apikey = 'RjujkBq7S8zdWISjvBDKqPX7Jw3xl5xV2GZderFVoGVFkqV5HO2rQxVCgiEL';
     public $apikey = 'XnHPnRIa3s0WQmKZEE4x9IS1Ty1mYuGMkIw5QngjoNOI4r0UxfUTtGQHsVj3';
    
    function __construct()
    {
        parent::__construct();
        $this->load->model('Webservice_model');     
        $this->load->library('email',array(
            'mailtype'  => 'html',
            'newline'   => '\r\n'
        ));

        header('Access-Control-Allow-Origin: *');
        header("Content-Type:application/json");
        header("Access-Control-Allow-Headers: X-API-KEY, Origin, X-Requested-With, Content-Type, Accept, Access-Control-Request-Method , Authentication");
        header("Access-Control-Allow-Methods: GET, POST, OPTIONS, PUT, DELETE");
        $method = $_SERVER['REQUEST_METHOD'];
        if($method == "OPTIONS") {
        die();
        if ($this->input->server('REQUEST_METHOD') == 'GET')  
            $postdata = json_encode($_GET);
        else if ($this->input->server('REQUEST_METHOD') == 'POST')
            $postdata = file_get_contents("php://input");
        
        $auth = '';

            if(isset(apache_request_headers()['Auth'])) {
                $auth = apache_request_headers()['Auth'];
            }
        
        }
    }
    
    //API for user registration 
    function user_registration_post()
    {   
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        $otp = rand(1111,9999); 
        $result_mob = $this->Webservice_model->user_reg_mobile($request);
        if($result_mob!="")
        {
            $result_email = $this->Webservice_model->user_reg_email($request);
            if($result_email !="")
            {
                $num = rand(1111,9999);
                $em = substr($request['email'],0, 4);
                $referral_code = strtoupper($em) . $num;
                
                if($request['code'] !="")
                {
                    $referral = $this->Webservice_model->referral_code($request);
                    if($referral =="")
                    {
                        $result = array('status'=>'error','message' =>"Referral code does not exist.",'responsecode'=>'500','data'=>"");
                    }
                    else
                    {
                        $result_both = $this->Webservice_model->referral_code_registration($request,$otp,$referral_code,$referral);
                        if($result_both!="")
                        {   
                            $moblie = $result_both->mobile;
                            $message = "Never share your otp to anyone. Your otp is" ." ".$otp;             
                            $resp = $this->send_sms($moblie,$message);
                            if($resp)
                            {
                                $result = array('status'=>'success','message' =>"Registration done",'responsecode'=>'200','data'=>$result_both);
                            }                   
                        }
                        else
                        {           
                            $result = array('status'=>'error','message' =>"Mobile number and email exist. ",'responsecode'=>'500','data'=>"");
                        }   
                    }   
                }   
                else
                {
                    $result_both = $this->Webservice_model->user_reg($request,$otp,$referral_code);
                
                    if($result_both!="")
                    {   
                        $moblie = $result_both->mobile;
                        $message = "Never share your otp to anyone. Your otp is" ." ".$otp;             
                        $resp = $this->send_sms($moblie,$message);
                        if($resp)
                        {
                            $result = array('status'=>'success','message' =>"Registration done",'responsecode'=>'200','data'=>$result_both);
                        }                   
                    }
                    else
                    {           
                        $result = array('status'=>'error','message' =>"Mobile number and email exist. ",'responsecode'=>'500','data'=>"");
                    }
                }           
            }   
            else
            {
                $result = array('status'=>'error','message' =>"Email id exist",'responsecode'=>'500','data'=>"");
            }   
        }   
        else
        {
            $result = array('status'=>'error','message' =>"Mobile Number exist",'responsecode'=>'500','data'=>"");
        }   
        
        $this->response($result);       
    }

    // API for user login
    function login_post()
    {   
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true);
        $randomnumber = rand(11111,99999);
        $result = $this->Webservice_model->login($request ,$randomnumber);
        if($request['type'] =="Normal")
        {
            if($result){
            $result = array('status' =>'success','data'=>$result,'message' =>"Data found",'responsecode'=>'200');
            }
            else
            {
                $result = array('status' =>'False','data'=>"",'message' =>"Your email or mobile and password is not correct",'responsecode'=>'500');
            }   
        }
        else if($request['type'] =="Email")
        {
            if($result){
            $result = array('status' =>'success','data'=>$result,'message' =>"Data found",'responsecode'=>'200');
            }
            else
            {
                $result = array('status' =>'False','data'=>"",'message' =>"Your email or mobile and password is not correct",'responsecode'=>'500');
            }   
        }   
        
        else{

        $res = $this->Webservice_model->check_registration($request,$randomnumber);

            $result = array('status' =>'False','message'=>'Your email or mobile is not registred','responsecode'=>'500','data'=>"");            
        }

        $this->response($result);

    }   

    //API for forgot password
    function forget_password_post()
    {
       header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        if($request['type'] == "Number")
        {
            $number = $request['EmailorNumber'];
            $this->db->from('registration');
            $this->db->where('mobile',$number);
            $query=$this->db->get();
            if($query->num_rows()>0)
            {
                $result=$query->row();
                $type = $result->type;
                if($type =="Normal")
                {
                    $moblie = $result->mobile;

                    $otp = rand(1111,9999); 
                    $respe = $this->Webservice_model->forget_password($otp,$moblie);
                    $message = "Never share your otp to anyone. Your otp is" ." ".$otp;             
                    $resp = $this->send_sms($moblie,$message);
                    if($resp !="")
                    {
                        $result = array('status'=>'success','message' =>"Otp send successfully",'responsecode'=>'200','data'=>$respe);
                    }
                }
                else
                {
                    $result =  Array("status" => "error", "message" => "Your account type is ".$type." so login with ".$type."" ,"responsecode"=>500, "data"=>"");
                }   

            }
            else
            {
                $result = array('status'=>'error','message' =>"Mobile Number not exist",'responsecode'=>'500','data'=>"");
            }   
        }
        else
        {

            $email = $request['EmailorNumber'];
            $this->db->from('registration');
            $this->db->where('email',$email);
            $query=$this->db->get();
            if($query->num_rows()>0)
            {
                $result=$query->row();

                $type = $result->type;        
               
                if($type =="Facebook")
                {   
                  $result =  Array("status" => "error", "message" => "Your registration type is Facebook so login with Facebook" ,"responsecode"=>500, "data"=>"");
                }
                else if($type =="Google"){
                    $result = Array( 'status' => 'error',"message" => "Your registration type is  Google so login with Google","responsecode"=>500, "data"=>"");
                }
                else if($type =="Normal"){
                    $moblie = $result->mobile;

                    $otp = rand(1111,9999); 
                    $respe = $this->Webservice_model->forget_password($otp,$moblie);
                    $message = "Never share your otp to anyone. Your otp is" ." ".$otp;             
                    $resp = $this->send_sms($moblie,$message);
                    if($resp !="")
                    {
                        $result = array('status'=>'success','message' =>"Otp send successfully",'responsecode'=>'200','data'=>$respe);
                    }
                }
            }
            else{
                $result = Array('status' => 'error', "responsecode"=>500, "data"=>"" ,"message" => "Email Id Not Exist");
            }
        }   

        $this->response($result);
    }
    
    
     //API for send SMS
    function send_sms($contact_no,$message){    
        
        $username = "Rummyfire321";
        $smstype = "TRANS";
        //Your authentication key
        $apikey = "5b73d901-2f6b-4eb6-83d9-9bed795bbff9";
        //Multiple mobiles numbers separated by comma
        $mobileNumber = $contact_no;
        //Sender ID,While using route4 sender id should be 6 characters long.
        $senderId = "RUMYFR";
        //Your message to send, Add URL encoding here.
        $message = $message;
        //Define route 
       
        
        //Prepare you post parameters
        $postData = array(
            'apikey' => $apikey,
            'numbers' => $mobileNumber,
            'message' => $message,
            'sendername' => $senderId,
            'smstype' => $smstype,
            'username' => $username
        );
        //API URL
        $url = "http://sms.cubetechsolutions.in/sendSMS";
        // init the resource
        $ch = curl_init();
        curl_setopt_array($ch, array(
            CURLOPT_URL => $url,
            CURLOPT_RETURNTRANSFER => true,
            CURLOPT_POST => true,
            CURLOPT_POSTFIELDS => $postData
            //,CURLOPT_FOLLOWLOCATION => true
        ));
        //Ignore SSL certificate verification
        curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, 0);
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, 0);
        //get response
        $output = curl_exec($ch);
        //Print error if any
        if (curl_errno($ch)) {
            echo 'error:' . curl_error($ch);
        }
        curl_close($ch);
        
        return true;
        
        /*$username = "pappu321";
        $smstype = "TRANS";
        //Your authentication key
        $apikey = "7bc9df1f-6c18-4f86-8a46-63de0be7cfe6";
        //Multiple mobiles numbers separated by comma
        $mobileNumber = $contact_no;
        //Sender ID,While using route4 sender id should be 6 characters long.
        $senderId = "MYFIVE";
        //Your message to send, Add URL encoding here.
        $message = $message;
        //Define route 
       
        
        //Prepare you post parameters
        $postData = array(
            'apikey' => $apikey,
            'numbers' => $mobileNumber,
            'message' => $message,
            'sendername' => $senderId,
            'smstype' => $smstype,
            'username' => $username
        );
        //API URL
        $url = "http://sms.cubetechsolutions.in/sendSMS/";
        // init the resource
        $ch = curl_init();
        curl_setopt_array($ch, array(
            CURLOPT_URL => $url,
            CURLOPT_RETURNTRANSFER => true,
            CURLOPT_POST => true,
            CURLOPT_POSTFIELDS => $postData
            //,CURLOPT_FOLLOWLOCATION => true
        ));
        //Ignore SSL certificate verification
        curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, 0);
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, 0);
        //get response
        $output = curl_exec($ch);
        //Print error if any
        if (curl_errno($ch)) {
            echo 'error:' . curl_error($ch);
        }
        curl_close($ch);
        return true;*/

    }


    // API for user number verify
    function user_number_verify_post()
    {   
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true);                     
        $resp = $this->Webservice_model->number_verify($request);
        header('Content-type: application/json');
        
        if($resp!="")
        {
            $result = array('status'=>'success','message' =>"Number verification done",'responsecode'=>'200','data'=>"");
        }
        else
        {           
            $result = array('status'=>'error','message' =>"Number verification not done",'responsecode'=>'500','data'=>"");
        }

        $this->response($result);       
    }

    // API for verify forgot password by OTP
    function varify_forgot_password_post()
    {
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        $resp = $this->Webservice_model->varify_forgot_password($request);
        if($resp!="")
        {
            $result = array('status'=>'success','message' =>"Otp verification done",'responsecode'=>'200','data'=>$resp);
        }
        else
        {           
            $result = array('status'=>'error','message' =>"Otp verification not done",'responsecode'=>'500','data'=>"");
        }

        $this->response($result);  
    }

    // API for change password
    function update_password_post()
    {
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        $resp = $this->Webservice_model->update_password($request);
        if($resp!="")
        {
            $result = array('status'=>'success','message' =>"Password update successfully",'responsecode'=>'200','data'=>"");
        }
        else
        {           
            $result = array('status'=>'error','message' =>"Password not update ",'responsecode'=>'500','data'=>"");
        }
        $this->response($result);  
    }

    // API for edit user profile by user id
    function edit_profile_post()
    {
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        $resp = $this->Webservice_model->user_profile($request);
        if($resp!="")
        {
            $result = array('status'=>'success','message' =>"User profile update successfully",'responsecode'=>'200','data'=>"");
        }
        else
        {           
            $result = array('status'=>'error','message' =>"User profile not update ",'responsecode'=>'500','data'=>"");
        }

        $this->response($result);  
    }

    // API to get all state 
    function get_state_get()
    {
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        $resp = $this->Webservice_model->get_state();
        
        if($resp!="")
        {
            $result = array('status'=>'success','message' =>"State found successfully",'responsecode'=>'200','data'=>$resp);
        }
        else
        {           
            $result = array('status'=>'error','message' =>"State not found",'responsecode'=>'500','data'=>"");
        }

        $this->response($result);  
    }

    // API to get all city by state id
    function get_city_post()
    {
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        $resp = $this->Webservice_model->get_city($request);
        
        if($resp!="")
        {
            $result = array('status'=>'success','message' =>"Citys found successfully",'responsecode'=>'200','data'=>$resp);
        }
        else
        {           
            $result = array('status'=>'error','message' =>"Citys not found",'responsecode'=>'500','data'=>"");
        }

        $this->response($result);  
    }

    // API for view profile by user id
    function view_profile_post()
    {
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        $resp = $this->Webservice_model->view_user_profile($request);
        
        if($resp!="")
        {
            $fav_team = explode(',',$resp->favriteTeam);
            
            $fav = $this->Webservice_model->fave_team_name($fav_team);

            foreach ($fav as $value) {
            
                $team[] = $value->team_name;
                
            }
            $resp->favriteTeam =implode(",",$team);
        
            $resp->country = "India";
            $result = array('status'=>'success','message' =>"Profile data found successfully",'responsecode'=>'200','data'=>$resp);
        }
        else
        {           
            $result = array('status'=>'error','message' =>"Profile data  not found",'responsecode'=>'500','data'=>"");
        }
        $this->response($result);  
    }

    // API to get match record according to type// Fixture,Live,Result
    function match_record_post()
    {
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        $resp = $this->Webservice_model->match_record($request);
        //echo $this->db->last_query();die;
        if(count($resp) >0){
        foreach ($resp as $key) { 
           
            $team1 = $this->Webservice_model->team_name($key->teamid1);
            $key->team_name1 = $team1->team_name;
            $key->team_image1 = $team1->team_image;
            $key->team_short_name1 = $team1->team_short_name;
            $team2 = $this->Webservice_model->team_name($key->teamid2);
            $key->team_name2 = $team2->team_name;
            $key->team_image2 = $team2->team_image;
            $key->team_short_name2 = $team2->team_short_name;
        
            if($key->match_status == "Fixture")
            {
                $key->team1Score = "";
                $key->team1Over = "";
                $key->team2Score = "";
                $key->team2Over = "";
                $key->team1Score_secondInning = "";
                $key->team1Over_secondInning = "";
                $key->team2Score_secondInning = "";
                $key->team2Over_secondInning = "";
                $key->match_status_note = "";
                $key->toss_winner_team = "";
                $key->winner_team = "";
                $t2 = $key->match_date_time;

                $res = "";
                $seconds = strtotime($t2) - strtotime(date('Y-m-d H:i:s'));
                $res = $seconds = floor(($seconds + ($days * 86400) + ($hours * 3600) + ($minutes*60)));
                $key->time = $res;

                if($res <= 0)
                {
                    $mat_id = $key->match_id;
                    
                    $data = array('time' =>"0000-00-00 00:00:00",
                                'match_status' =>'Live',
                                'matchStarted'=>'1'
                     );
                    $this->Webservice_model->update_match_status_by_unique_id($mat_id,$data);
                    $this->Webservice_model->update_match_status_by_unique_id_user($mat_id,$data);
                }                 
            }

            else
            {
               $key->time = 00;
            } 

            if($key->match_status == "Live")
            { 
                if($key->team1Score =="")
                {
                    $key->team1Score = "";
                }
                if($key->team1Over =="")
                {
                    $key->team1Over = "";
                }
                if($key->team2Score =="")
                {
                    $key->team2Score = "";
                }
                if($key->team2Over =="")
                {
                    $key->team2Over = "";
                }
                if($key->team1Score_secondInning =="")
                {
                    $key->team1Score_secondInning = "";
                }
                if($key->team1Over_secondInning =="")
                {
                    $key->team1Over_secondInning = "";
                }
                if($key->team2Score_secondInning =="")
                {
                    $key->team2Score_secondInning = "";
                }
                if($key->team2Over_secondInning =="")
                {
                    $key->team2Over_secondInning = "";
                }
            }
          
        } }
        
        if($resp!="")
        {
          $result = array('status'=>'success','message' =>" data found successfully",'responsecode'=>'200','data'=>$resp);
        }
        else
        {       
          $result = array('status'=>'error','message' =>" data  not found",'responsecode'=>'500','data'=>"");
        }
        $this->response($result);  
    }

    // API for resend otp
    function resend_otp_post()
    {   
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true);
        $result = $this->Webservice_model->resend_otp($request);
        header('Content-type: application/json');
        if($result){    
            $moblie = $result->mobile;
            $message = "Never share your otp to anyone. Your otp is" ." ".$result->otp;
            $resp = $this->send_sms($moblie,$message);

            $result = array('status' =>'success','data'=>$result,'message' =>"Otp send successfully",'responsecode'=>'200');
        }else{          
            $result = array('status' => 'error','message'=>'user id invalid','responsecode'=>'500','data'=>"");
        }
        $this->response($result);

    }

    // API for change password
    function change_password_post()
    {   
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true);
        $result = $this->Webservice_model->change_password($request);
        header('Content-type: application/json');
        if($result){
            $record = $this->Webservice_model->update_password($request);
            if($record)
            {
                $response = array('status' =>'success','data'=>"",'message' =>"Password update successfully",'responsecode'=>'200');
            }   
            else
            {
                $response= array('status' =>'error','data'=>"" ,'message' =>"Password not update",'responsecode'=>'500');
            }   
            
        }else{
            
            $response = array('status' => 'error','message'=>'Old password not match ','responsecode'=>'500','data'=>"");
        }
        $this->response($response);

    }

    // API to get list of match record by user id
    function mymatch_record_post()
    {
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        $resp = $this->Webservice_model->mymatch_record($request);
        foreach ($resp as $key) { 
        $contest_count = $this->Webservice_model->contest_count($key->match_id,$key->user_id);
        
        $team_count = $this->Webservice_model->team_count($key->match_id,$key->user_id);
        $key->contest_count = $contest_count;
        $key->team_count = $team_count;
        $team1 = $this->Webservice_model->team_name($key->teamid1);
        $key->team_name1 = $team1->team_name;
        $key->team_image1 = $team1->team_image;
        $key->team_short_name1 =$team1->team_short_name;

        $team2 = $this->Webservice_model->team_name($key->teamid2);
        $key->team_name2 = $team2->team_name;
        $key->team_image2 = $team2->team_image;
        $key->team_short_name2 =$team2->team_short_name;

        
        $match_result = $this->Webservice_model->match_score($key->match_id);
        $key->league_name  = $match_result->league_name;

        if($key->match_status == "Fixture")
        {
            $t2 = $key->match_date_time;

            $res = "";
            $seconds = strtotime($t2) - strtotime(date('Y-m-d H:i:s'));
            $res = $seconds = floor(($seconds + ($days * 86400) + ($hours * 3600) + ($minutes*60)));
            $key->time = $res;

            if($res <= 0)
                {
                    $mat_id = $key->match_id;               
                    $data = array('time' =>"0000-00-00 00:00:00",
                                'match_status' =>'Live',
                                'matchStarted'=>'1'
                     );
                $this->Webservice_model->update_match_status_by_unique_id($mat_id,$data);
                $this->Webservice_model->update_match_status_by_unique_id_user($mat_id,$data);
                }
            
        }   
        else
        {
            $key->time = 00;
        }

        $this->db->select('unique_id');
        $this->db->where('match_id',$key->match_id);
        $U_id = $this->db->get('match')->row()->unique_id;
        if($key->match_status == "Live")
        {
            if($match_result->team1Score =="")
            {
                $key->team1Score = "";    
            }
            else
            {
                $key->team1Score = $match_result->team1Score;
            }
            if($match_result->team1Over =="")
            {
                $key->team1Over = "";    
            }
            else
            {
                $key->team1Over = $match_result->team1Over;
            }
            if($match_result->team2Score =="")
            {
                $key->team2Score = "";    
            }
            else
            {
                $key->team2Score = $match_result->team2Score;
            }
            if($match_result->team2Over =="")
            {
                $key->team2Over = "";    
            }
            else
            {
                $key->team2Over = $match_result->team2Over;
            }
            if($match_result->team1Score_secondInning =="")
            {
                $key->team1Score_secondInning = "";    
            }
            else
            {
                $key->team1Score_secondInning = $match_result->team1Score_secondInning;
            }
            if($match_result->team1Over_secondInning =="")
            {
                $key->team1Over_secondInning = "";    
            }
            else
            {
                $key->team1Over_secondInning = $match_result->team1Over_secondInning;
            }
            if($match_result->team2Score_secondInning =="")
            {
                $key->team2Score_secondInning = "";    
            }
            else
            {
                $key->team2Score_secondInning = $match_result->team2Score_secondInning;
            }
            if($match_result->team2Over_secondInning =="")
            {
                $key->team2Over_secondInning = "";    
            }
            else
            {
                $key->team2Over_secondInning = $match_result->team2Over_secondInning;
            }
            
            $key->match_status_note = "";
        }   

        if($key->match_status == "Result")
        {
            $key->team1Score = $match_result->team1Score;
            $key->team1Over = $match_result->team1Over;
            $key->team2Score = $match_result->team2Score;
            $key->team2Over = $match_result->team2Over;
            $key->team1Score_secondInning = $match_result->team1Score_secondInning;
            $key->team1Over_secondInning = $match_result->team1Over_secondInning;
            $key->team2Score_secondInning = $match_result->team2Score_secondInning;
            $key->team2Over_secondInning = $match_result->team2Over_secondInning;
            $key->match_status_note = $match_result->match_status_note;
        }   
        
        }
        
        if(count($resp) >"0")
        {
            $result = array('status'=>'success','message' =>" data found successfully",'responsecode'=>'200','data'=>$resp);
        }
        else
        {           
            $result = array('status'=>'error','message' =>" data  not found",'responsecode'=>'500','data'=>"");
        }
        $this->response($result);  
    }

    //Api for contest list by type // All/ Hot/ Mega 
    function contest_list_post()
    {
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        $resp = $this->Webservice_model->contest_list($request);
        foreach ($resp as $key) {
            $key->remaining_team = $key->total_team - $key->join_team;
        }
        if(count($resp) >"0")
        {
            $result = array('status'=>'success','message' =>" Contest list found successfully",'responsecode'=>'200','data'=>$resp);
        }
        else
        {           
            $result = array('status'=>'error','message' =>" Contest list not found",'responsecode'=>'500','data'=>"");
        }
        $this->response($result);  
    }

    //Api for get winning information by contest id
    function winning_info_post()
    {
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        $resp = $this->Webservice_model->winning_info($request);        
        if($resp !="")
        {
            $result = array('status'=>'success','message' =>"Winning information found",'responsecode'=>'200','data'=>$resp);
        }
        else
        {           
            $result = array('status'=>'error','message' =>"No data found",'responsecode'=>'500','data'=>"");
        }
        $this->response($result);  
    }  

    //Api for team list by match id not in use
    function team_list_old_post()
    {
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        $resp = $this->Webservice_model->team_list($request);
        foreach ($resp as  $value) {
            $number = $this->Webservice_model->team_number($value->matchid,$value->teamid);
            if($number !="")
            {
                $value->team_number = "1";
            }   
            else
            {
                $value->team_number = "2";
            }       
            $team = $this->Webservice_model->team_name($value->teamid);
            $player =$this->Webservice_model->player_name($value->playerid);
            $name = explode(" ", $player->name);
            $fname = str_split($name[0]);
            $desigination =$this->Webservice_model->player_desigination($player->designationid);
            $value->team_name = $team->team_name;
            $value->team_image = $team->team_image;
            $value->short_name = $team->team_short_name;
            $value->player_name =$player->name;
            $value->player_shortname = $fname[0].". ".$name[1];
            $value->player_points ="00";
            $value->credit_points =$player->credit_points;
            $value->player_image =$player->image;
            $value->player_desigination =$desigination->title;
            $value->player_desigination_short_name =$desigination->short_term;
         }      
        if(count($resp) >"0")
        {
            $result = array('status'=>'success','message' =>"Team information found",'responsecode'=>'200','data'=>$resp);
        }
        else
        {           
            $result = array('status'=>'error','message' =>"No data found",'responsecode'=>'500','data'=>"");
        }
        $this->response($result);  
    }

    //Api for team list by match id join contest by user
    function leaderboard_post()
    {
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        $resp = $this->Webservice_model->leaderboard($request);
        if($resp !="")
        {
            $this->update_leaderboard($request);

            $result = array('status'=>'success','message' =>"Leader board data",'responsecode'=>'200','data'=>$resp);
        }
        else
        {           
            $result = array('status'=>'error','message' =>"No data found",'responsecode'=>'500','data'=>"");
        }
        $this->response($result);  
    }

    //Api for get player information by player id
    function player_information_post()
    {
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        $resp = $this->Webservice_model->player_information($request);
        //echo $this->db->last_query();die;
        $day = new DateTime($resp->dob); 
        $resp->dob = $day->format('F jS, Y');
        if($resp !="")
        {
            $result = array('status'=>'success','message' =>"Player information",'responsecode'=>'200','data'=>$resp);
        }
        else
        {           
            $result = array('status'=>'error','message' =>"No data found",'responsecode'=>'500','data'=>"");
        }
        $this->response($result);  
    }

    // API for team list by user id and team id
    function team_list_post()
    {
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true);        

            $players = $this->Webservice_model->get_myteam_player_id($request['user_id'], $request['my_team_id']);
            foreach ($players as $player) {
                $pla[] = $player->player_id;
            }
            $resp = $this->Webservice_model->team_list1($request);
            
            foreach ($resp as  $value) {

                if(in_array($value->playerid,$pla))
                {
                    $value->is_select = "1";
                }   
                $number = $this->Webservice_model->team_number($request['matchid'],$value->teamid);
                if($number !="")
                {
                    $value->team_number = "1";
                }   
                else
                {
                    $value->team_number = "2";
                }   
                $name = explode(" ", $value->name);
                $fname = str_split($name[0]);
                if($name[2] !="")
                {
                    $fname = str_split($name[0]);
                    $value->player_shortname = $fname[0].". ".$name[2];
                }   
                else
                {
                    $fname = str_split($name[0]);
                    $value->player_shortname = $fname[0].". ".$name[1];
                }

                $value->player_desigination =$value->title;         
            }       
            if(count($resp) >"0")
            {
                $result = array('status'=>'success','message' =>"Team information found",'responsecode'=>'200','data'=>$resp);
            }
            else
            {           
                $result = array('status'=>'error','message' =>"No data found",'responsecode'=>'500','data'=>"");
            }      
        
        $this->response($result);  
    }

    //Api for save team list   
    function save_team_post()
    {
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true);
        $checkMatch = $this->Webservice_model->check_match_status($request['matchid']);
        if($checkMatch['match_status'] =="Fixture")
        {
            $resp = $this->Webservice_model->selected_player_byuser($request);  
            if($request['edit'] =="1")
            {
                $resp = $this->Webservice_model->selected_player_byuser($request);
                if($resp !="")
                {
                    $result = array('status'=>'success','message' =>"User team update successfully",'responsecode'=>'200','data'=>$resp);
                }
                else
                {          
                    $result = array('status'=>'error','message' =>"Try again",'responsecode'=>'500','data'=>"");
                }
            }
            else
            {
                if($resp !="")
                {
                    $result = array('status'=>'success','message' =>"User team create successfully",'responsecode'=>'200','data'=>$resp);
                }
                else
                {          
                    $result = array('status'=>'error','message' =>"Try again",'responsecode'=>'500','data'=>"");
                }
            }
        }
        else
        {
             $result = array('status'=>'error','message' =>"Time over for create or edit team",'responsecode'=>'500','data'=>"");
        }
       
        $this->response($result);  
    }
    //Api for get team list  by user id
    function user_team_list_post()
    {
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        $resp = $this->Webservice_model->selected_player_list_by_userid($request);  
        foreach ($resp as $value) {

        $player = $this->Webservice_model->player_name($value->player_id);
        $designation = $this->Webservice_model->player_desigination($value->designationid);
        $value->player_name = $player->name;
        $value->player_credit_points = $player->credit_points;
        $value->player_image = $player->image;
        $value->player_title = $designation->title;
        $value->player_short_term = $designation->short_term;

        if($value->is_captain =="1")
        {
            $value->is_captain = "captain";
        }
        else
        {
            $value->is_captain = "no";
        }   
        if($value->is_vicecaptain =="1")
        {
            $value->is_vicecaptain = "vicecaptain";
        }
        else
        {
            $value->is_vicecaptain = "no";
        }   
                
            }   
        if($resp !="")
        {
            $result = array('status'=>'success','message' =>"Team list",'responsecode'=>'200','data'=>$resp);
        }
        else
        {           
            $result = array('status'=>'error','message' =>"No record found",'responsecode'=>'500','data'=>"");
        }
       
        
        $this->response($result);  
    }

    //Api for get user team list  by user id and match id
    function my_team_list_post()
    {
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        $resp = $this->Webservice_model->user_team_list($request);  
        $i = 1;
        foreach ($resp as $value){
            $bat =  $this->Webservice_model->count_batsman($value->id);
            $ball = $this->Webservice_model->count_boller($value->id);
            $all =  $this->Webservice_model->count_allrounder($value->id);
            $wkeeper =$this->Webservice_model->count_wkeeper($value->id);
            $captain =$this->Webservice_model->captain($value->id);
            $vicecaptain =$this->Webservice_model->vicecaptain($value->id);
            $value->team_number = "Team " .$i;
            $value->captain =   $captain->name;
            $value->vicecaptain =   $vicecaptain->name;
            $value->batsman = $bat;
            $value->boller = $ball;
            $value->allrounder = $all;
            $value->wkeeper = $wkeeper;

            $i++;
        }

        if(count($resp) > 0)
        {
            $result = array('status'=>'success','message' =>"Team list",'responsecode'=>'200','data'=>$resp);
        }
        else
        {           
            $result = array('status'=>'error','message' =>"No record found",'responsecode'=>'500','data'=>"");
        }
        
        $this->response($result);  
    }


    function join_contest_post()
    {
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        
        $c_id = $request['contest_id'];
        $credit_amount = $this->Webservice_model->credit_amount($request);

        $debit_amount = $this->Webservice_model->debit_amount($request);    
        $bonus_credit = $this->Webservice_model->bonus_amount($request);
        $bonus_debit = $this->Webservice_model->bonus_amount_debit($request);
        $bonus = $bonus_credit-$bonus_debit;
        
        $winning_credit = $this->Webservice_model->winning_amount($request);
        $winning_debit = $this->Webservice_model->winning_amount_debit($request);

        $credit = $credit_amount - $debit_amount;
        $winning = $winning_credit - $winning_debit;

        $contest_fees = $this->Webservice_model->get_contest($request['contest_id']);

        $profile = $this->Webservice_model->view_user_profile($request);

        $re['code'] = $profile->code;

        if($request['private'] == "1")
        {
            $this->Webservice_model->update_private_contest($request);
        }

        $referral_user = $this->Webservice_model->referral_code($re);
        if($referral_user !="")
        {
            $reffer = $referral_user;
        }   
        else
        {
            $reffer ="";
        }   

        $contest_fee = $contest_fees->entry;
        $referral_bonus = round($contest_fee/100*33.33,2);
        $contest_amount = $request['contest_amount'];
        
        $bonus_amt = $contest_fee - $contest_amount;
        $multiple_teams = $contest_fees->multiple_teams;
        
        $RTeam = $contest_fees->total_team - $contest_fees->join_team;
        
        if($RTeam > 0)
        {
            if($multiple_teams =="1")
            {
                $team_count = $this->Webservice_model->get_team_count_for_match($request);
                if($team_count > 0 )
                {
                    $result = array('status'=>'error','message' =>"You have joined with this contest please select other contest",'responsecode'=>'500','data'=>"");
                }
                else
                {
                    if($contest_fee > $contest_amount)
                    {
                        $bonus_10_percent = $contest_fee/100*5;
            
                        if("$bonus_10_percent" == "$bonus_amt")
                        {
                            if($credit > 0)
                            { 
                                $team_count = $this->Webservice_model->get_team_count_for_match($request);
                                $new_team_count = $team_count+1;
                                $resp = $this->Webservice_model->join_contest($request,$new_team_count);    
                    
                                if($resp !="")
                                {
                                    if($credit < $request['contest_amount'])
                                    {
                                        $remaining_amount = $request['contest_amount'] - $credit;
                                        $ckeck_bonus_fee = $this->Webservice_model->join_contest_with_credit_and_winning_fees($request,$bonus_amt,$reffer,$credit,$remaining_amount,$resp); 
                                    }
                                    else 
                                    {
                                        $ckeck_bonus_fee = $this->Webservice_model->join_contest_with_bonus_fees($request,$bonus_amt,$reffer,$resp);
                                    }   
                                    if($referral_user !="")
                                    {
                                        $this->Webservice_model->user_referral_bonus($request,$referral_bonus,$reffer);
                                    }   
            
                                    $result = array('status'=>'success','message' =>"Contest joined successfully",'responsecode'=>'200','data'=>"");
                                }
                                else
                                {
                                    $result = array('status'=>'error','message' =>"You have joined with this team please select other team",'responsecode'=>'500','data'=>"");
                                }
                            }   
                            else if($winning >= $contest_amount)
                            {
                                $team_count = $this->Webservice_model->get_team_count_for_match($request);
                                $new_team_count = $team_count+1;
                                $resp = $this->Webservice_model->join_contest($request,$new_team_count);    
                    
                                if($resp !="")
                                {
                                    $ckeck_bonus_fee = $this->Webservice_model->join_contest_with_bonus_fees_winning($request,$bonus_amt,$reffer,$resp); 
                                    if($referral_user !="")
                                    {
                                        $this->Webservice_model->user_referral_bonus($request,$referral_bonus,$reffer);
                                    }   
            
                                    $result = array('status'=>'success','message' =>"Contest joined successfully",'responsecode'=>'200','data'=>"");
                                }
                                else
                                {
                                    $result = array('status'=>'error','message' =>"You have joined with this team please select other team",'responsecode'=>'500','data'=>"");
                                }
                            }   
                            else
                            {
                                $result = array('status'=>'error','message' =>"You dont have sufficient amount",'responsecode'=>'500','data'=>"");
                            }                   
                        }
                        else 
                        {
                            $result = array('status'=>'error','message' =>"You dont have sufficient bonus amount",'responsecode'=>'500','data'=>"");
                        }
            
                    }
                    else
                    {
                        if($credit > 0 )
                        {
                            $team_count = $this->Webservice_model->get_team_count_for_match($request);
                            $new_team_count = $team_count+1;
                            $resp = $this->Webservice_model->join_contest($request,$new_team_count);    
            
                            if($resp !="")
                            {                   
                                if($credit < $request['contest_amount'])
                                {
                                    $remaining_amount = $request['contest_amount'] - $credit;
                                    $ckeck_bonus_fee = $this->Webservice_model->join_contest_with_credit_and_winning_fees_amount($request,$reffer,$remaining_amount,$credit,$resp); 
                                }
                                else
                                {
                                    $ckeck_bonus_fee = $this->Webservice_model->join_contest_without_bonus_fees($request,$reffer,$resp);
                                }   
                                
                                if($referral_user !="")
                                {
                                    $this->Webservice_model->user_referral_bonus($request,$referral_bonus,$reffer);
                                }   
            
                                $result = array('status'=>'success','message' =>"Contest joined successfully",'responsecode'=>'200','data'=>"");
                            }
                            else
                            {
                                $result = array('status'=>'error','message' =>"You have joined with this team please select other team",'responsecode'=>'500','data'=>"");
                            }
                        }   
                        else
                        {
                            $team_count = $this->Webservice_model->get_team_count_for_match($request);
                            $new_team_count = $team_count+1;
                            $resp = $this->Webservice_model->join_contest($request,$new_team_count);    
                    
                            if($resp !="")
                            {
                                $ckeck_bonus_fee = $this->Webservice_model->join_contest_with_bonus_fees_winning($request,$bonus_amt,$reffer,$resp); 
                                if($referral_user !="")
                                {
                                    $this->Webservice_model->user_referral_bonus($request,$referral_bonus,$reffer);
                                }   
            
                                $result = array('status'=>'success','message' =>"Contest joined successfully",'responsecode'=>'200','data'=>"");
                            }
                            else
                            {
                                $result = array('status'=>'error','message' =>"You have joined with this team please select other team",'responsecode'=>'500','data'=>"");
                            }
                        }   
                        
                    }
                }
            }
            else
            {
                if($contest_fee > $contest_amount)
                {
                    $bonus_10_percent = $contest_fee/100*5;
        
                    if("$bonus_10_percent" == "$bonus_amt")
                    {
                        if($credit > 0)
                        { 
                            $team_count = $this->Webservice_model->get_team_count_for_match($request);
                            $new_team_count = $team_count+1;
                            $resp = $this->Webservice_model->join_contest($request,$new_team_count);    
                
                            if($resp !="")
                            {
                                if($credit < $request['contest_amount'])
                                {
                                    $remaining_amount = $request['contest_amount'] - $credit;
                                    $ckeck_bonus_fee = $this->Webservice_model->join_contest_with_credit_and_winning_fees($request,$bonus_amt,$reffer,$credit,$remaining_amount,$resp); 
                                }
                                else 
                                {
                                    $ckeck_bonus_fee = $this->Webservice_model->join_contest_with_bonus_fees($request,$bonus_amt,$reffer,$resp);
                                }   
                                if($referral_user !="")
                                {
                                    $this->Webservice_model->user_referral_bonus($request,$referral_bonus,$reffer);
                                }   
        
                                $result = array('status'=>'success','message' =>"Contest joined successfully",'responsecode'=>'200','data'=>"");
                            }
                            else
                            {
                                $result = array('status'=>'error','message' =>"You have joined with this team please select other team",'responsecode'=>'500','data'=>"");
                            }
                        }   
                        else if($winning >= $contest_amount)
                        {
                            $team_count = $this->Webservice_model->get_team_count_for_match($request);
                            $new_team_count = $team_count+1;
                            $resp = $this->Webservice_model->join_contest($request,$new_team_count);    
                
                            if($resp !="")
                            {
                                $ckeck_bonus_fee = $this->Webservice_model->join_contest_with_bonus_fees_winning($request,$bonus_amt,$reffer,$resp); 
                                if($referral_user !="")
                                {
                                    $this->Webservice_model->user_referral_bonus($request,$referral_bonus,$reffer);
                                }   
        
                                $result = array('status'=>'success','message' =>"Contest joined successfully",'responsecode'=>'200','data'=>"");
                            }
                            else
                            {
                                $result = array('status'=>'error','message' =>"You have joined with this team please select other team",'responsecode'=>'500','data'=>"");
                            }
                        }   
                        else
                        {
                            $result = array('status'=>'error','message' =>"You dont have sufficient amount",'responsecode'=>'500','data'=>"");
                        }                   
                    }
                    else 
                    {
                        $result = array('status'=>'error','message' =>"You dont have sufficient bonus amount",'responsecode'=>'500','data'=>"");
                    }
        
                }
                else
                {
                    if($credit > 0 )
                    {
                        $team_count = $this->Webservice_model->get_team_count_for_match($request);
                        $new_team_count = $team_count+1;
                        $resp = $this->Webservice_model->join_contest($request,$new_team_count);    
        
                        if($resp !="")
                        {                   
                            if($credit < $request['contest_amount'])
                            {
                                $remaining_amount = $request['contest_amount'] - $credit;
                                $ckeck_bonus_fee = $this->Webservice_model->join_contest_with_credit_and_winning_fees_amount($request,$reffer,$remaining_amount,$credit,$resp); 
                            }
                            else
                            {
                                $ckeck_bonus_fee = $this->Webservice_model->join_contest_without_bonus_fees($request,$reffer,$resp);
                            }   
                            
                            if($referral_user !="")
                            {
                                $this->Webservice_model->user_referral_bonus($request,$referral_bonus,$reffer);
                            }   
        
                            $result = array('status'=>'success','message' =>"Contest joined successfully",'responsecode'=>'200','data'=>"");
                        }
                        else
                        {
                            $result = array('status'=>'error','message' =>"You have joined with this team please select other team",'responsecode'=>'500','data'=>"");
                        }
                    }   
                    else
                    {
                        $team_count = $this->Webservice_model->get_team_count_for_match($request);
                        $new_team_count = $team_count+1;
                        $resp = $this->Webservice_model->join_contest($request,$new_team_count);    
                
                        if($resp !="")
                        {
                            $ckeck_bonus_fee = $this->Webservice_model->join_contest_with_bonus_fees_winning($request,$bonus_amt,$reffer,$resp); 
                            if($referral_user !="")
                            {
                                $this->Webservice_model->user_referral_bonus($request,$referral_bonus,$reffer);
                            }   
        
                            $result = array('status'=>'success','message' =>"Contest joined successfully",'responsecode'=>'200','data'=>"");
                        }
                        else
                        {
                            $result = array('status'=>'error','message' =>"You have joined with this team please select other team",'responsecode'=>'500','data'=>"");
                        }
                    }   
                    
                }
            }
        }
        else
        {
            $result = array('status'=>'error','message' =>"Contest full please join other contest",'responsecode'=>'500','data'=>"");
        }
        if($resp !="")
        {
            $contest_full_check = $this->Webservice_model->check_countest_full($c_id);
            if($contest_full_check['total_team'] == $contest_full_check['join_team'])
            {
                $get_contest = $this->Webservice_model->get_contest($c_id);
                $this->Webservice_model->create_new_contest($get_contest);
            }  
        }    
        
        $this->response($result);  
    }


    //Api for user join match contest list by user id and match id
    function my_join_contest_list_post()
    {
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        
        $resp = $this->Webservice_model->my_join_contest($request); 
        $user_id = $request['user_id'];
        foreach ($resp as $contest) {
            $data[] =   $this->Webservice_model->get_contest($contest->contest_id);
            
        }
        foreach ($data as $key ) {

        $key->remaining_team = $key->total_team - $key->join_team;
        $count = $this->Webservice_model->team_count_for_contest($key->contest_id,$user_id);
        $key->team_count = $count;
            
        }
        if($data !="")
        {
            $result = array('status'=>'success','message' =>"Contest List ",'responsecode'=>'200','data'=>$data);
        }
        else
        {
            $result = array('status'=>'error','message' =>"No contest found",'responsecode'=>'500','data'=>"");
        }

        $this->response($result);  
    }

    // API for joined contest list 
    function joined_contest_post()  
    {

        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        $resp = $this->Webservice_model->contest_list_by_id($request);
        $TotalWinners =  $this->Webservice_model->get_Contestwinner($request['contest_id']);
       // $this->update_score_points_get();
        $re['matchid'] = $resp->match_id;
        //$this->update_leaderboard_user($re);
    
        $leaderboards_session_user = $this->Webservice_model->leaderboard_contest_id($request);

        $leaderboards_non_session_user = $this->Webservice_model->leaderboard_contest_other_user_id($request);
        
        $leaderboards = array_merge($leaderboards_session_user,$leaderboards_non_session_user);
   
        $match_status = $this->Webservice_model->match_status($request);

        $score_match = $this->Webservice_model->match_score($resp->match_id);
        
        if($score_match->match_status == "Live")
        {
            if($score_match->team1Score !="")
            {
                $resp->team1Score = $score_match->team1Score;
            }
            else
            {
                $resp->team1Score = "";
            }
            if($score_match->team1Over !="")
            {
                $resp->team1Over = $score_match->team1Over;
            } 
            else
            {
                $resp->team1Over = "";
            }   
            if($score_match->team2Score !="")
            {
                $resp->team2Score = $score_match->team2Score;
            }
            else
            {
                $resp->team2Score = "";
            }
            if($score_match->team2Over !="")
            {
                $resp->team2Over = $score_match->team2Over;
            }
            else
            {
                $resp->team2Over = "";
            }
            if($score_match->team1Score_secondInning !="")
            {
                $resp->team1Score_secondInning = $score_match->team1Score_secondInning;
            }
            else
            {
                $resp->team1Score_secondInning = "";
            }
            if($score_match->team1Over_secondInning !="")
            {
                $resp->team1Over_secondInning = $score_match->team1Over_secondInning;
            }
            else
            {
                $resp->team1Over_secondInning = "";
            }
            if($score_match->team2Score_secondInning !="")
            {
                $resp->team2Score_secondInning = $score_match->team2Score_secondInning;
            }
            else
            {
                $resp->team2Score_secondInning = "";
            }
            if($score_match->team2Over_secondInning !="")
            {
                $resp->team2Over_secondInning = $score_match->team2Over_secondInning;
            }
            else
            {
                $resp->team2Over_secondInning = "";
            }
            $resp->match_status_note = "";
        }

        if($score_match->match_status == "Result")
        {
            if($score_match->team1Score !="")
            {
                $resp->team1Score = $score_match->team1Score;
            }
            else
            {
                $resp->team1Score = "";
            }
            if($score_match->team1Over !="")
            {
                $resp->team1Over = $score_match->team1Over;
            } 
            else
            {
                $resp->team1Over = "";
            }   
            if($score_match->team2Score !="")
            {
                $resp->team2Score = $score_match->team2Score;
            }
            else
            {
                $resp->team2Score = "";
            }
            if($score_match->team2Over !="")
            {
                $resp->team2Over = $score_match->team2Over;
            }
            else
            {
                $resp->team2Over = "";
            }
            if($score_match->team1Score_secondInning !="")
            {
                $resp->team1Score_secondInning = $score_match->team1Score_secondInning;
            }
            else
            {
                $resp->team1Score_secondInning = "";
            }
            if($score_match->team1Over_secondInning !="")
            {
                $resp->team1Over_secondInning = $score_match->team1Over_secondInning;
            }
            else
            {
                $resp->team1Over_secondInning = "";
            }
            if($score_match->team2Score_secondInning !="")
            {
                $resp->team2Score_secondInning = $score_match->team2Score_secondInning;
            }
            else
            {
                $resp->team2Score_secondInning = "";
            }
            if($score_match->team2Over_secondInning !="")
            {
                $resp->team2Over_secondInning = $score_match->team2Over_secondInning;
            }
            else
            {
                $resp->team2Over_secondInning = "";
            }
                $resp->match_status_note = $score_match->match_status_note ;
           
        }  

        if($match_status->match_status =="Fixture")
        {
            $resp->match_status ="0";
        }   
        else
        {
            $resp->match_status ="1";
        }   

        $resp->user_team_count = $this->Webservice_model->team_count_contest($request);
        $resp->all_team_count = $this->Webservice_model->all_team_count_contest($request);
        $resp->remaining_team =$resp->total_team - $resp->join_team;
            $i =1;

        $prices = 0;
        $price = 0;
        $CountArray = 0;
        $FinalPrice = 0;
        $winning_amount = 0;    
            
        foreach ($leaderboards as $leaderboardq) {

            $Lead =  array();
            $Mainarray = array();

            if($match_status->match_status =="Live")
            {
                if($match_status->time =="0000-00-00 00:00:00")
                {
                    if($leaderboardq->rank !="-")
                    {
                        $leaderboardq->winning_amount = "00";
                    }   
                    else
                    {
                        $leaderboardq->rank ="1";
                    }   
                    
                }
                else
                {
                    $leaderboardq->rank ="1";
                }   
                
            }
            else if($match_status->match_status =="Result")
            {
                $Mainarray = array();
                $Ucount = array();
                $Lcount = array();
                $count = $this->Webservice_model->checkCount($leaderboardq->rank,$resp->contest_id);

                if($count == 1)
                {
                    $price = $this->Webservice_model->get_price($leaderboardq->rank,$resp->contest_id); 
                    if($resp->payment_status =="0")
                    {
                        $leaderboardq->winning_amount = "";
                    }
                    else if($price != "")
                    {
                        if($TotalWinners >= $leaderboardq->rank)
                        {
                            $leaderboardq->winning_amount = $price;
                        }    
                        else
                        {
                            $leaderboardq->winning_amount = "00";
                        }
                    }
                    else
                    {
                        $leaderboardq->winning_amount = "00";

                    }  
                } 
                else if($count > 1)
                {
                    $Ucount = $this->Webservice_model->checkUpperCount($leaderboardq->rank,$resp->contest_id,$leaderboardq->temprank);
                    $Lcount = $this->Webservice_model->checkLowerrCount($leaderboardq->rank,$resp->contest_id,$leaderboardq->temprank);

                    if (isset($Ucount) && !empty($Ucount)) 
                    {
                       $Ucount = $Ucount;
                    }
                    else
                    {
                        $Ucount = array();
                    }    
                    if(isset($Lcount) && !empty($Lcount))
                    {
                        $Lcount = $Lcount;
                    } 
                    else
                    {
                        $Lcount = array();
                    }    

                    $Lead['user_id'] =  $leaderboardq->user_id;
                    $Lead['rank'] =  $leaderboardq->rank;
                    $Lead['points'] =  $leaderboardq->points;
                    $Lead['id'] =  $leaderboardq->id;
                    $Lead['temprank'] =  $leaderboardq->temprank;

                    $Mainarray = array();
                    $Mainarray= array_merge(array($Lead) ,$Ucount,$Lcount); 
                    $CountArray = 0;
                    $CountArray =  count($Mainarray);

                    $winningP = 0;
                    $prices = 0;
                    foreach ($Mainarray as $key ) {
                        $winningP += $this->Webservice_model->get_price($key['temprank'],$resp->contest_id);
                        
                    }
                    $FinalPrice = $winningP / $CountArray ;
                          
                    $prices = $FinalPrice;
                    if($resp->payment_status =="0")
                    {
                        $leaderboardq->winning_amount = "";
                    }
                    else if($prices != "")
                    {
                        if($TotalWinners >= $leaderboardq->rank )
                        {
                            $leaderboardq->winning_amount = round($prices, 2);                            
                        }    
                        else
                        {
                            $leaderboardq->winning_amount = "00";
                        }
                    }
                    else
                    {
                        $leaderboardq->winning_amount = "00";
                    }  
                }     
               
            }
            $leaderboardq->Team = $leaderboardq->TeamName;
            $resp->leaderboard[] = $leaderboardq;
            $i++;
        }
        

        if($resp !="")
        {
            $result = array('status'=>'success','message' =>" Contest list found successfully",'responsecode'=>'200','data'=>$resp);
        }
        else
        {           
            $result = array('status'=>'error','message' =>" Contest list not found",'responsecode'=>'500','data'=>"");
        }
        $this->response($result);  
    }

    //Api for get user team list  by user id and team id
    function my_joined_team_list_post()
    {
       header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        
        $resp = $this->Webservice_model->my_joined_team_list($request); 
        
            $Points =0 ;
            
            foreach ($resp as  $value) {

                $number = $this->Webservice_model->team_number($request['match_id'],$value->teamid);
                if($number !="")
                {
                    $value->team_number = "1";
                }   
                else
                {
                    $value->team_number = "2";
                }

            $player =   $this->Webservice_model->player_points_for_user($value->player_id,$value->my_team_id,$request['match_id']);
            $value->is_captain= $player->is_captain;
            $value->is_vicecaptain= $player->is_vicecaptain;
            
            $Points  = $player->total_points + $player->batting_points + $player->bolling_points + $player->fielding_points + $player->second_innings_batting + $player->second_innings_bolling + $player->second_innings_fielding;

            if($player->is_captain =="1")
            {
                $value->points  = $Points * 2;
            }

            else if($player->is_vicecaptain =="1")
            {
                $value->points =  $Points * 1.5;
            }

            else
            {
                $value->points =  $Points ;
            }  
            
            $name = explode(" ", $value->name);
                
                if($name[2] !="")
                {
                    $fname = str_split($name[0]);
                    $value->player_shortname = $fname[0].". ".$name[2];
                }   
                else
                {
                    $fname = str_split($name[0]);
                    $value->player_shortname = $fname[0].". ".$name[1];
                }   
            }

        if($resp !="")
        {
            $result = array('status'=>'success','message' =>"Team list",'responsecode'=>'200','data'=>$resp);
        }
        else
        {           
            $result = array('status'=>'error','message' =>"No record found",'responsecode'=>'500','data'=>"");
        }
       
        
        $this->response($result);  
    }

    //Api for user join match contest list live by user id and match id
    function my_join_contest_list_live_post()
    {
       header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
       // $this->update_score_points_get();
        $resp = $this->Webservice_model->my_join_contest_live($request);
        
        $match_info = $this->Webservice_model->match_score($request['match_id']);
        
        $user_id = $request['user_id'];
        foreach ($resp as $contest) {
            $data[] =   $this->Webservice_model->get_contest_live($contest->my_match_id);   
        }
        $teams = $this->Webservice_model->match_team($request);
        
            $i=1;
            foreach ($teams as $team) {
                $team->name ="Team ". $i;
                $i++;
             }  
            
        foreach ($data as $key ) {  
             $TotalWinners = $this->Webservice_model->get_Contestwinner($key->contest_id) ;
            if($key->match_status == "Live")
            {                 
                $key->winning_amount ="00";
                if($key->rank =="-")
                {
                    $key->rank ="1";
                }
                else
                {
                    $rank = $this->Webservice_model->get_rank($key->contest_id,$key->match_id,$key->my_team_id);
                    $key->rank = $rank->rank;
                }   
            }
            else if($key->match_status =="Result")
            {           
               // $this->update_rank_user($key->match_id);    
                $leaderboardq = $this->Webservice_model->get_rank($key->contest_id,$key->match_id,$key->my_team_id);
                $Mainarray = array();
                $Ucount = array();
                $Lcount = array();
                $count = $this->Webservice_model->checkCount($leaderboardq->rank,$key->contest_id);

                $key->rank =$leaderboardq->rank;
                 if($count == 1)
                {
                    $price = $this->Webservice_model->get_price($leaderboardq->rank,$key->contest_id);
                    if($key->payment_status =="0")
                    {
                        $key->winning_amount = ""; 
                    }
                    else if($price != "")
                    {
                        if($TotalWinners >= $leaderboardq->rank)
                        {
                            $key->winning_amount = $price;
                        }    
                        else
                        {
                            $key->winning_amount = "00";
                        }
                    }
                    else
                    {
                        $key->winning_amount = "00"; 
                    } 
                }
                else if($count > 1)
                {
                    $Ucount = $this->Webservice_model->checkUpperCount($leaderboardq->rank,$key->contest_id,$leaderboardq->temprank);
                    $Lcount = $this->Webservice_model->checkLowerrCount($leaderboardq->rank,$key->contest_id,$leaderboardq->temprank);

                    if (isset($Ucount) && !empty($Ucount)) 
                    {
                       $Ucount = $Ucount;
                    }
                    else
                    {
                        $Ucount = array();
                    }    
                    if(isset($Lcount) && !empty($Lcount))
                    {
                        $Lcount = $Lcount;
                    } 
                    else
                    {
                        $Lcount = array();
                    }    

                    $Lead['user_id'] =  $leaderboardq->user_id;
                    $Lead['rank'] =  $leaderboardq->rank;
                    $Lead['points'] =  $leaderboardq->points;
                    $Lead['id'] =  $leaderboardq->id;
                    $Lead['temprank'] =  $leaderboardq->temprank;

                    $Mainarray = array();
                    $Mainarray= array_merge(array($Lead) ,$Ucount,$Lcount); 
                    $CountArray = 0;
                    $CountArray =  count($Mainarray);

                    $winningP = 0;
                    $prices = 0;
                    foreach ($Mainarray as $keys ) {
                        $winningP += $this->Webservice_model->get_price($keys['temprank'],$key->contest_id);
                    }

                    $FinalPrice = $winningP / $CountArray ;
                          
                    $prices = $FinalPrice; 
                 
                    if($key->payment_status =="0")
                    {
                        $key->winning_amount = "";
                    }
                    else if($prices != "")
                    {
                        if($TotalWinners >= $leaderboardq->rank )
                        {
                            $key->winning_amount = round($prices, 2);                            
                        }    
                        else
                        {
                            $key->winning_amount = "00";
                        }
                    }
                    else
                    {
                        $key->winning_amount = "00";
                    }  
                }          
            }   
            foreach ($teams as $team) {
                if($team->id == $key->my_team_id)
                {
                    $key->team_name = $team->name;
                }       
            }
            $key->remaining_team = $key->total_team - $key->join_team;
            
            $total_points = $this->Webservice_model->get_leaderboard($key->contest_id,$key->my_team_id,$request['match_id'],$request['user_id']);

            $key->points = $total_points;
        }
        
        if($data !="")
        {
            $result = array('status'=>'success','message' =>"Contest List ",'responsecode'=>'200','data'=>$data);
        }
        else
        {
            $result = array('status'=>'error','message' =>"No contest found",'responsecode'=>'500','data'=>"");
        }

        $this->response($result);  
    }

    function update_match_status_get()
    {
        $time = date('Y-m-d H:i:00');
        $response = $this->Webservice_model->get_match_status_by_type($time,'Fixture');
        if(count($response) >0)
        {
            foreach ($response as $resp) {
                $id = $resp->match_id;

                $api_url  = "https://cricket.sportmonks.com/api/v2.0/fixtures/".$resp->unique_id."?api_token=".$this->apikey."";

                $ch = curl_init();
                curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
                curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
                curl_setopt($ch, CURLOPT_URL,$api_url);
                $result=curl_exec($ch);
                curl_close($ch);
                $resp = json_decode(json_encode(json_decode($result)), True);

                $match_status =  $resp['data']['status'];
                
                if($match_status =="Finished")
                {
                    $data = array('time'=>"0000-00-00 00:00:00",
                        "match_status" =>"Result",
                        'matchStarted'=>'1'
                    );
                }
                elseif($match_status =="Cancl")
                {
                    $data = array('time'=>"0000-00-00 00:00:00",
                        "match_status" =>"Result",
                        'matchStarted'=>'0',
                        'cancelled'=>'1'
                    );                    
                }
                elseif($match_status =="Aban.")
                {
                    $data = array('time'=>"0000-00-00 00:00:00",
                        "match_status" =>"Result",
                        'matchStarted'=>'0',
                        'cancelled'=>'1'
                    );                    
                }
                elseif($match_status =="Postp")
                {
                    $data = array('time'=>"0000-00-00 00:00:00",
                        "match_status" =>"Result",
                        'matchStarted'=>'0',
                        'cancelled'=>'1'
                    );
                }
                elseif($match_status =="live")
                {
                    $data = array('time'=>"0000-00-00 00:00:00",
                        "match_status" =>"Live",
                        'matchStarted'=>'1'
                    );                    
                }

                $this->db->where('match_id',$id);
                $this->db->update('match',$data);

                $this->db->where('match_id',$id);
                $this->db->update('my_match',$data);
            }
        }       
    }

    // API for get notification list for user
    function notification_post()
    {
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        $resp = $this->Webservice_model->notification($request);    
        if(count($resp))
        {
            foreach ($resp as $value) {
                $match  = $this->Webservice_model->get_match($value->match_id);
                $contest    = $this->Webservice_model->get_contest($value->contest_id);

                $value->match_id =$match->match_id;
                $value->title =$value->title;
                $value->contest_id =$contest->contest_id;
                $value->contest_name =$contest->contest_name;
                $value->contest_description =$value->message;
                $value->notification_type =$value->notification_type;
                $value->notification_image =$value->notification_image;
            }
        }   
        
        if(count($resp) >0)
        {
            $result = array('status'=>'success','message' =>"notification List ",'responsecode'=>'200','data'=>$resp);
        }
        else
        {
            $result = array('status'=>'error','message' =>"No notification found",'responsecode'=>'500','data'=>"");
        }

        $this->response($result);  
    }

    //API for get team list
    function get_team_list_get()
    {
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        $resp = $this->Webservice_model->get_all_team();
        if($resp !="")
        {
            $result = array('status'=>'success','message' =>"Team List ",'responsecode'=>'200','data'=>$resp);
        }
        else
        {
            $result = array('status'=>'error','message' =>"No Team found",'responsecode'=>'500','data'=>"");
        }

        $this->response($result);  
    }

    //API for update favrite team name
    function favrite_team_post()
    {
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        
        $resp = $this->Webservice_model->favrite_team($request);
        if($resp !="")
        {
            $result = array('status'=>'success','message' =>"Favrite Team update ",'responsecode'=>'200');
        }
        else
        {
            $result = array('status'=>'error','message' =>"Try again",'responsecode'=>'500','data'=>"");
        }

        $this->response($result);  
    }

    //API for add amount in user account by user id
    function add_amount_post()
    {
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        if($request['mode'] == "Paytm")
        {
            $txn_status = strtoupper($request['transection_detail']['STATUS']);
            if($txn_status ==="TXN_SUCCESS")
            {
                $tx_status = "TXN_SUCCESS";
            }   
            else
            {
                $tx_status = $txn_status;
            }   
        }
        else if($request['mode'] == "Cashfree")
        {
            $txn_status = $request['transection_detail']['txStatus'];
            $txn_status_fail = $request['transection_detail']['txMsg'];
            
            if($txn_status ==="SUCCESS")
            {
                $tx_status = "TXN_SUCCESS";
            }   
            else if($txn_status ==="FAILED")
            {
                $tx_status = "TXN_FAILURE";
            }
            else
            {
                $tx_status = $txn_status_fail;
            }   
        }
        else if($request['mode'] == "TrakNPay")
        {
            $txn_status = $request['transection_detail']['response_code'];
            $txn_status_fail = $request['transection_detail']['response_message'];
            
            if($txn_status =="0")
            {
                $tx_status = "TXN_SUCCESS";
            }   
            else if($txn_status =="1000")
            {
                $tx_status = "TXN_FAILURE";
            }
            else
            {
                $tx_status = $txn_status_fail;
            }   
        }
        else if($request['mode'] == "Paykun")
        {
            //$txn_status = strtoupper($request['transection_detail']['status']);
            
            if($request['transection_detail']['status'] == "Success")
            {
                $txn_status = "TXN_SUCCESS";
            }
            
            if($txn_status ==="TXN_SUCCESS")
            {
                $tx_status = "TXN_SUCCESS";
            }   
            else
            {
                $tx_status = $txn_status;
            }      
        }
        else
        {
            $txn_status = strtoupper($request['transection_detail']['result']['status']);
            if($txn_status ==="SUCCESS")
            {
                $tx_status = "TXN_SUCCESS";
            }   
            else
            {
                $tx_status = $txn_status;
            }   
        }   

        $resp = $this->Webservice_model->add_amount($request,$tx_status);
        
        if($resp !="")
        {
            if($resp->transaction_status =="PENDING")
            {
                $result = array('status'=>'error','message' =>"TRANSACTION PENDING",'responsecode'=>'500','data'=>$resp);
            }
            else if($resp->transaction_status =="TXN_SUCCESS")
            {
                $result = array('status'=>'success','message' =>"TRANSACTION SUCCESSFULL",'responsecode'=>'200','data'=>$resp);
            }
            else if($resp->transaction_status =="SUCCESS")
            {
                $result = array('status'=>'success','message' =>"TRANSACTION SUCCESSFULL",'responsecode'=>'200','data'=>$resp);
            }
            else if($resp->transaction_status =="FAILURE") 
            {
                $result = array('status'=>'error','message' =>"TRANSACTION FAILURE",'responsecode'=>'500','data'=>$resp);
            }
            else 
            {
                $result = array('status'=>'error','message' =>"TRANSACTION FAILURE",'responsecode'=>'500','data'=>$resp);
            }
        }
        else
        {
            $result = array('status'=>'error','message' =>"Try again",'responsecode'=>'500','data'=>"");
        }

        $this->response($result);  
    }

    // API for account details by user id
    function my_account_transaction_post()
    {
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        $resp = $this->Webservice_model->account_details($request);
       
        if($resp !="")
        {           

            $result = array('status'=>'success','message' =>"Transaction details",'responsecode'=>'200','data'=>$resp);
        }
        else
        {
            $result = array('status'=>'error','message' =>"No details found",'responsecode'=>'500','amount'=>"0",'data'=>"");
        }

        $this->response($result);  
    }

    // API for my account details
    function my_account_post()
    {
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        $credit = $this->Webservice_model->credit_amount($request);     
        $debit = $this->Webservice_model->debit_amount($request);

        $bonus_credit = $this->Webservice_model->bonus_amount($request);
        $bonus_debit = $this->Webservice_model->bonus_amount_debit($request);

        $bonus = $bonus_credit-$bonus_debit;
                
        $winning_credit = $this->Webservice_model->winning_amount($request);
        $winning_debit = $this->Webservice_model->winning_amount_debit($request);
        $document_information = $this->Webservice_model->document_information($request);
        
        $resp['aadhar_status'] = $document_information->aadharcard_status;
        $resp['pan_status'] = $document_information->pancard_status;
        $winning = $winning_credit - $winning_debit;

        $resp['total_amount'] = round($credit + $winning - $debit , 2);
        $resp['credit_amount'] = round($credit - $debit,2);
   
        if($bonus > 0)
        {
            $resp['bonous_amount'] = round($bonus,2); 
        }
        else 
        {   
            $resp['bonous_amount'] = "0"; 
        }
        if($winning >0)
        {
            $resp['winning_amount'] = round($winning,2) ; 
        }
        else 
        {   
            $resp['winning_amount'] = "0"; 
        }   
        
        $resp['credit_note'] = "Amount credit";
        $resp['bonous_note'] = "Bonus credit";
        $resp['winning_note'] = "Winning credit";
        if($resp !="")
        {
            $result = array('status'=>'success','message' =>"account details",'responsecode'=>'200','data'=>$resp);
        }
        else
        {
            $result = array('status'=>'error','message' =>"No details found",'responsecode'=>'500','amount'=>"0",'data'=>"");
        }

        $this->response($result);  
    }

    //API for update points for playing eleven
    function update_points_get()
    {
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        $matchs = $this->Webservice_model->get_all_live_match();
        if(count($matchs) > 0)
        {   
            foreach ($matchs as $match) 
            {
                $id = $this->Webservice_model->get_match($match->match_id);                
                $api_url = "https://cricket.sportmonks.com/api/v2.0/fixtures/".$id->unique_id."?api_token=".$this->apikey."&include=lineup";
                $ch = curl_init();
                curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
                curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
                curl_setopt($ch, CURLOPT_URL,$api_url);
                $result=curl_exec($ch);
                curl_close($ch);
                $resp = json_decode(json_encode(json_decode($result)), True);     
                $score = $this->Webservice_model->playing_eleven('1'); 
                
                if($match->match_status =="Live")
                {
                    if($id->type =="ODI" or $id->type =="Woman ODI")
                    {   
                        $teamA =  $resp['data']['lineup'];
                        foreach ($teamA as $player) 
                        {    
                            if($player['id'] != "")
                            {
                                $playerinfo = $this->Webservice_model->player_info($player['id']);
                                $player_id = $playerinfo->id;  
                                $this->Webservice_model->playing_eleven_score($score->odiscore,$player_id,$id->match_id);
                                $this->user_playing_eleven($score->odiscore,$player_id,$id->match_id);
                            }
                        }                     
                    }
                    else if($id->type =="T10")
                    {   
                        $teamA =  $resp['data']['lineup'];
                        foreach ($teamA as $player) 
                        {    
                            if($player['id'] != "" )
                            {
                                $playerinfo = $this->Webservice_model->player_info($player['id']);
                                $player_id = $playerinfo->id;  
                                $this->Webservice_model->playing_eleven_score($score->t10score,$player_id,$id->match_id);
                                $this->user_playing_eleven($score->t10score,$player_id,$id->match_id);
                            }
                        }                     
                    }

                    else if($id->type =="Twenty20" or $id->type =="T20" or $id->type =="Woman T20" or $id->type =="T20I")
                    {                       
                        $teamA =  $resp['data']['lineup'];
                        foreach ($teamA as $player) 
                        {    
                            if($player['id'] != "")
                            {
                                $playerinfo = $this->Webservice_model->player_info($player['id']);
                                $player_id = $playerinfo->id;  
                                $this->Webservice_model->playing_eleven_score($score->t20score,$player_id,$id->match_id);
                                $this->user_playing_eleven($score->t20score,$player_id,$id->match_id);
                            }   
                        }
                    }

                    else if($id->type =="Test")
                    {   
                        $teamA =  $resp['data']['lineup'];
                        foreach ($teamA as $player) 
                        { 
                            if($player['id'] != "")
                            {
                                $playerinfo = $this->Webservice_model->player_info($player['id']);
                                $player_id = $playerinfo->id;  
                                $this->Webservice_model->playing_eleven_score($score->testscore,$player_id,$id->match_id);
                                $this->user_playing_eleven($score->testscore,$player_id,$id->match_id);
                            }
                        }
                    }
                }                
            }
        }           
    }


    // API for update points for playing eleven
    function update_score_points_get()
    {
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        
        $matchs = $this->Webservice_model->get_all_live_match();
        
        if(count($matchs) > 0)
        {   
            foreach ($matchs as $match) 
            {
                $this->update_rank_user($match->match_id);
                $id = $this->Webservice_model->get_match($match->match_id);

                $api_url = "https://cricket.sportmonks.com/api/v2.0/fixtures/".$id->unique_id."?api_token=".$this->apikey."&include=batting,bowling";

                $ch = curl_init();
                curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
                curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
                curl_setopt($ch, CURLOPT_URL,$api_url);
                $result=curl_exec($ch);
                curl_close($ch);
                $resp = json_decode(json_encode(json_decode($result)), True);
              
                if($id->type =="ODI")
                {   
                    $playing_eleven = $this->Webservice_model->playing_eleven("1");
                    $single = $this->Webservice_model->playing_eleven("2");
                    $duck = $this->Webservice_model->playing_eleven("8");
                    $six = $this->Webservice_model->playing_eleven("10");
                    $four = $this->Webservice_model->playing_eleven("9");   
                    $fifty = $this->Webservice_model->playing_eleven("11"); 
                    $hundred = $this->Webservice_model->playing_eleven("12");
                    $between50_60 = $this->Webservice_model->playing_eleven("33");
                    $between40_50 = $this->Webservice_model->playing_eleven("34");
                    $below40 = $this->Webservice_model->playing_eleven("35");
                    
                    foreach ($resp['data']['batting'] as $player) 
                    {
                        $single_runs = $single->odiscore;
                        $four_runs = $four->odiscore;
                        $six_runs = $six->odiscore;

                        $six_hits = $player['six_x'];
                        $four_hits = $player['four_x'];
                        $run_hits = $player['score'];
                        $strike_rate = $player['rate'];

                        $single_hits = $run_hits ;//- $four_hits*4 - $six_hits*6;   

                        if($single_hits =="0")
                        {
                            $playerinfo = $this->Webservice_model->player_info($player['player_id']);
                            if($playerinfo->designationid !="2")
                            {
                                $total_score = $playing_eleven->odiscore + $duck->odiscore;
                                    $player_id = $playerinfo->id; 

                                $this->Webservice_model->update_playing_eleven_score($total_score,$player_id,$id->match_id);

                            } 
                        }   
                        else
                        {
                            if($strike_rate <60 && $strike_rate >50)
                            {
                                $s_rate = $between50_60->odiscore;
                            }
                            else if($strike_rate <40.99 && $strike_rate >40)
                            {
                                $s_rate = $between40_50->odiscore;
                            }
                            else if($strike_rate <40)
                            {
                                $s_rate = $below40->odiscore;
                            }

                            if($single_hits >=50 && $single_hits <100)
                            {
                                $total_score = $single_runs*$single_hits + $four_runs*$four_hits + $six_runs*$six_hits + $playing_eleven->odiscore + $fifty->odiscore +$s_rate;
                            }
                            else if($single_hits >=100)
                            {
                                $total_score = $single_runs*$single_hits + $four_runs*$four_hits + $six_runs*$six_hits + $playing_eleven->odiscore + $hundred->odiscore +$s_rate;
                            }
                            else
                            {
                                $total_score = $single_runs*$single_hits + $four_runs*$four_hits + $six_runs*$six_hits + $playing_eleven->odiscore +$s_rate;
                            }

                            $playerinfo = $this->Webservice_model->player_info($player['player_id']);
                            $player_id = $playerinfo->id;  

                            $this->Webservice_model->update_playing_eleven_score($total_score,$player_id,$id->match_id); 

                            }                        
                    }
                    

                    $wicket = $this->Webservice_model->playing_eleven("3");

                    $maiden_over = $this->Webservice_model->playing_eleven("13");

                    $four_wicket = $this->Webservice_model->playing_eleven("14");
                    $five_wicket =$this->Webservice_model->playing_eleven("15");

                    $between45_35 =$this->Webservice_model->playing_eleven("23");
                    $between349_25 =$this->Webservice_model->playing_eleven("24");
                    $below25 =$this->Webservice_model->playing_eleven("25");
                    $between7_8 =$this->Webservice_model->playing_eleven("26");
                    $between801_9 =$this->Webservice_model->playing_eleven("27");
                    $above9 =$this->Webservice_model->playing_eleven("28");

                    foreach ($resp['data']['bowling'] as $bowling) 
                    {
                        $Maiden  = $bowling['medians'];
                        $wickets = $bowling['wickets'];
                        $Economic = $bowling['rate'];
                        
                        $maiden_over_bolled = $Maiden*$maiden_over->odiscore;

                        $wickets_taken = $wickets*$wicket->odiscore;

                        if($wickets =="4")
                        {
                            $wickets_taken_four = $four_wicket->odiscore;
                        }
                        else
                        {
                            $wickets_taken_four = 0;
                        }
                        if($wickets >="5")
                        {
                            $wickets_taken_five = $five_wicket->odiscore;
                        }
                        else
                        {
                            $wickets_taken_five = 0;
                        }

                        if($Economic <4.5 && $Economic >3.5)
                        {
                            $economic_rate = $between45_35->odiscore;
                        }

                        else if($Economic <3.49 && $Economic >2.5)
                        {
                            $economic_rate = $between349_25->odiscore;
                        }

                        else if($Economic <2.5)
                        {
                            $economic_rate = $below25->odiscore;
                        }   
                        else if($Economic <8 && $Economic >7)
                        {
                            $economic_rate = $between7_8->odiscore;
                        }

                        else if($Economic <= 9 && $Economic >8.01)
                        {
                            $economic_rate = $between801_9->odiscore;
                        }
                        else if($Economic >9)
                        {
                            $economic_rate = $above9->odiscore;
                        }
                        else if($Economic >4.5 && $Economic < 7)
                        {
                            $economic_rate = "0";
                        }   

                        $bolling_score = $maiden_over_bolled+$wickets_taken+$wickets_taken_four+$wickets_taken_five+$economic_rate;
                        $playerinfo = $this->Webservice_model->player_info($bowling['player_id']);
                        $player_id = $playerinfo->id;  

                        $this->Webservice_model->update_playing_eleven_bolling_score($bolling_score,$player_id,$id->match_id);  
                        
                    }   

                    $catch_points = $this->Webservice_model->playing_eleven("4");

                    foreach ($resp['data']['batting'] as $key ) {
                        if($key['catch_stump_player_id'] !="")
                        {
                           $fielding[] = $key['catch_stump_player_id'];
                        }    
                    }
                    $fieldings = array_count_values($fielding);
                    

                    foreach ($fieldings as $key => $value) {

                        $fielding_score = $value*$catch_points->odiscore;

                        $playerinfo = $this->Webservice_model->player_info($key);
                        $player_id = $playerinfo->id;  

                        $this->Webservice_model->update_playing_eleven_fielding_score($fielding_score,$player_id,$id->match_id); 
                        
                    }
                }

                else if($id->type =="T10")
                {   
                    $playing_eleven = $this->Webservice_model->playing_eleven("1");

                    $single = $this->Webservice_model->playing_eleven("2");
                    $duck = $this->Webservice_model->playing_eleven("8");   
                    $four = $this->Webservice_model->playing_eleven("9");   
                    $six = $this->Webservice_model->playing_eleven("10");
                    $fifty = $this->Webservice_model->playing_eleven("11"); 
                    
                    $morethan30 = $this->Webservice_model->playing_eleven("38");
                    $between90_100 = $this->Webservice_model->playing_eleven("45");
                    $between80_90 = $this->Webservice_model->playing_eleven("46");
                    $below80 = $this->Webservice_model->playing_eleven("47");

                    foreach ($resp['data']['batting'] as $player) 
                    {
                        $single_runs = $single->t10score;
                        $four_runs = $four->t10score;
                        $six_runs = $six->t10score;

                        $six_hits = $player['six_x'];
                        $four_hits = $player['four_x'];
                        $run_hits = $player['score'];
                        $strike_rate = $player['rate'];
                        $single_hits = $run_hits;

                        $playerinfo = $this->Webservice_model->player_info($player['player_id']);
                        $player_id = $playerinfo->id;

                        if($single_hits =="0")
                        {
                            if($playerinfo->designationid !="2")
                            {
                                $total_score = $playing_eleven->t10score + $duck->t10score;
                                $this->Webservice_model->update_playing_eleven_score($total_score,$player_id,$id->match_id);

                            }                           
                        }
                        else
                        {
                            if($strike_rate <90 && $strike_rate >100)
                            {
                                $s_rate = $between90_100->t10score;
                            }
                            else if($strike_rate <89.99 && $strike_rate >80)
                            {
                                $s_rate = $between80_90->t10score;
                            }
                            else if($strike_rate <80)
                            {
                                $s_rate = $below80->t10score;
                            }
                            else
                            {
                                $s_rate = 0;
                            }   

                            if($single_hits >=50 && $single_hits <100)
                            {
                                $total_score = $single_runs*$single_hits + $four_runs*$four_hits + $six_runs*$six_hits + $playing_eleven->t10score + $morethan30 ->t10score + $fifty->t10score +$s_rate;
                            }
                            else if($single_hits >=30 && $single_hits <50)
                            {
                                $total_score = $single_runs*$single_hits + $four_runs*$four_hits + $six_runs*$six_hits + $playing_eleven->t10score + $morethan30 ->t10score +$s_rate;
                            }
                            else
                            {
                                $total_score = $single_runs*$single_hits + $four_runs*$four_hits + $six_runs*$six_hits + $playing_eleven->t10score +$s_rate;
                            }   

                            $this->Webservice_model->update_playing_eleven_score($total_score,$player_id,$id->match_id);
                            
                        }                         
                                               
                    }

                    $wicket = $this->Webservice_model->playing_eleven("3");

                    $maiden_over = $this->Webservice_model->playing_eleven("13");
                    $two_wicket = $this->Webservice_model->playing_eleven("36");
                    $three_wicket =$this->Webservice_model->playing_eleven("37");
                    $below6 =$this->Webservice_model->playing_eleven("39");
                    $between6_7 =$this->Webservice_model->playing_eleven("40");
                    $between7_8 =$this->Webservice_model->playing_eleven("41");
                    $between11_12 =$this->Webservice_model->playing_eleven("42");
                    $between12_13 =$this->Webservice_model->playing_eleven("43");
                    $above13 =$this->Webservice_model->playing_eleven("44");
                    
                    foreach ($resp['data']['bowling'] as $bowling) 
                    {
                        $Maiden  = $bowling['medians'];
                        $wickets = $bowling['wickets'];
                        $Economic = $bowling['rate'];

                        $maiden_over_bolled = $Maiden*$maiden_over->t10score;

                        $wickets_taken = $wickets*$wicket->t10score;

                        if($wickets =="2")
                        {
                            $wickets_taken_two = $two_wicket->t10score;
                        }
                        else
                        {
                            $wickets_taken_two = 0;
                        }
                        if($wickets >="3")
                        {
                            $wickets_taken_three = $three_wicket->t10score;
                        }
                        else
                        {
                            $wickets_taken_three = 0;
                        }

                        if($Economic <6 )
                        {
                            $economic_rate = $below6->t10score;
                        }

                        else if($Economic <7 && $Economic >6)
                        {
                            $economic_rate = $between6_7->t10score;
                        }
                        else if($Economic <8 && $Economic >7)
                        {
                            $economic_rate = $between7_8->t10score;
                        }
                        else if($Economic <12 && $Economic >11)
                        {
                            $economic_rate = $between11_12->t10score;
                        }
                        else if($Economic <13 && $Economic >12)
                        {
                            $economic_rate = $between12_13->t10score;
                        }
                        else if($Economic >13)
                        {
                            $economic_rate = $above13->t10score;
                        }

                        $bolling_score = $maiden_over_bolled+$wickets_taken+$wickets_taken_two+$wickets_taken_three+$economic_rate;

                        $playerinfo = $this->Webservice_model->player_info($bowling['player_id']);
                        $player_id = $playerinfo->id;  

                        $this->Webservice_model->update_playing_eleven_bolling_score($bolling_score,$player_id,$id->match_id);  
                    
                    }

                    $catch_points = $this->Webservice_model->playing_eleven("4");

                    foreach ($resp['data']['batting'] as $key ) {
                        if($key['catch_stump_player_id'] !="")
                        {
                           $fielding[] = $key['catch_stump_player_id'];
                        }    
                    }
                    $fieldings = array_count_values($fielding);
                    

                    foreach ($fieldings as $key => $value) {

                        $fielding_score = $value*$catch_points->t10score;

                        $playerinfo = $this->Webservice_model->player_info($key);
                        $player_id = $playerinfo->id;  

                        $this->Webservice_model->update_playing_eleven_fielding_score($fielding_score,$player_id,$id->match_id); 
                    }
                }

                else if($id->type =="Twenty20" or $id->type =="T20" or $id->type =="Woman T20" or $id->type =="T20I")
                {  
                    $playing_eleven = $this->Webservice_model->playing_eleven("1");

                    $single = $this->Webservice_model->playing_eleven("2");
                    $six = $this->Webservice_model->playing_eleven("10");
                    $duck = $this->Webservice_model->playing_eleven("8");   
                    $four = $this->Webservice_model->playing_eleven("9");   
                    $fifty = $this->Webservice_model->playing_eleven("11"); 
                    $hundred = $this->Webservice_model->playing_eleven("12");   

                    $between60_70 = $this->Webservice_model->playing_eleven("30");
                    $between59_60 = $this->Webservice_model->playing_eleven("31");
                    $below50 = $this->Webservice_model->playing_eleven("32");

                    foreach ($resp['data']['batting'] as $player)
                    {
                        $single_runs = $single->t20score;
                        $four_runs = $four->t20score;
                        $six_runs = $six->t20score;

                        $six_hits = $player['six_x'];
                        $four_hits = $player['four_x'];
                        $run_hits = $player['score'];
                        $strike_rate = $player['rate'];
                        $single_hits = $run_hits;// - $four_hits*4 - $six_hits*6;

                        $playerinfo = $this->Webservice_model->player_info($player['player_id']);
                        $player_id = $playerinfo->id;

                            if($single_hits =="0")
                            {
                                if($playerinfo->designationid !="2")
                                {
                                    $total_score = $playing_eleven->t20score + $duck->t20score;
                                    $this->Webservice_model->update_playing_eleven_score($total_score,$player_id,$id->match_id);
                                }                           
                            }
                            else
                            {
                                if($strike_rate <70 && $strike_rate >60)
                                {
                                    $s_rate = $between60_70->t20score;
                                }
                                else if($strike_rate <59.99 && $strike_rate >50)
                                {
                                    $s_rate = $between59_60->t20score;
                                }
                                else if($strike_rate <50)
                                {
                                    $s_rate = $below50->t20score;
                                }
                                else
                                {
                                    $s_rate = 0;
                                }   

                                if($single_hits >=50 && $single_hits <100)
                                {
                                    $total_score = $single_runs*$single_hits + $four_runs*$four_hits + $six_runs*$six_hits + $playing_eleven->t20score + $fifty->t20score +$s_rate;
                                }
                                else if($single_hits >=100)
                                {
                                    $total_score = $single_runs*$single_hits + $four_runs*$four_hits + $six_runs*$six_hits + $playing_eleven->t20score + $hundred->t20score +$s_rate;
                                }   
                                else
                                {
                                    $total_score = $single_runs*$single_hits + $four_runs*$four_hits + $six_runs*$six_hits + $playing_eleven->t20score +$s_rate;
                                }   
                                
                                
                                $this->Webservice_model->update_playing_eleven_score($total_score,$player_id,$id->match_id);

                            }
                         
                        }                       
                    
                    $wicket = $this->Webservice_model->playing_eleven("3");

                    $maiden_over = $this->Webservice_model->playing_eleven("13");
                    $four_wicket = $this->Webservice_model->playing_eleven("14");
                    $five_wicket =$this->Webservice_model->playing_eleven("15");
                    $between6_5 =$this->Webservice_model->playing_eleven("17");
                    $between5_4 =$this->Webservice_model->playing_eleven("18");
                    $below4 =$this->Webservice_model->playing_eleven("19");
                    $between9_10 =$this->Webservice_model->playing_eleven("20");
                    $between10_11 =$this->Webservice_model->playing_eleven("21");
                    $above11 =$this->Webservice_model->playing_eleven("22");
                    
                    foreach ($resp['data']['bowling'] as $bowling) {

                        $Maiden  = $bowling['medians'];
                        $wickets = $bowling['wickets'];
                        $Economic = $bowling['rate'];

                        $maiden_over_bolled = $Maiden*$maiden_over->t20score;

                        $wickets_taken = $wickets*$wicket->t20score;

                        if($wickets =="4")
                        {
                            $wickets_taken_four = $four_wicket->t20score;
                        }
                        else
                        {
                            $wickets_taken_four = 0;
                        }
                        if($wickets >="5")
                        {
                            $wickets_taken_five = $five_wicket->t20score;
                        }
                        else
                        {
                            $wickets_taken_five = 0;
                        }

                        if($Economic <6 && $Economic >5)
                        {
                            $economic_rate = $between6_5->t20score;
                        }

                        else if($Economic <4.99 && $Economic >4)
                        {
                            $economic_rate = $between5_4->t20score;
                        }

                        else if($Economic <4)
                        {
                            $economic_rate = $below4->t20score;
                        }   
                        else if($Economic <10 && $Economic >9)
                        {
                            $economic_rate = $between9_10->t20score;
                        }

                        else if($Economic <11 && $Economic >10.01)
                        {
                            $economic_rate = $between10_11->t20score;
                        }
                        else if($Economic >11)
                        {
                            $economic_rate = $above11->t20score;
                        }
                        else if($Economic > 6 && $Economic <9)
                        {
                            $economic_rate = "0";
                        }   

                        $bolling_score = $maiden_over_bolled+$wickets_taken+$wickets_taken_four+$wickets_taken_five+$economic_rate;

                        $playerinfo = $this->Webservice_model->player_info($bowling['player_id']);
                        $player_id = $playerinfo->id;  

                        $this->Webservice_model->update_playing_eleven_bolling_score($bolling_score,$player_id,$id->match_id);  
                    }
                    

                    $catch_points = $this->Webservice_model->playing_eleven("4");

                    foreach ($resp['data']['batting'] as $key ) {
                        if($key['catch_stump_player_id'] !="")
                        {
                           $fielding[] = $key['catch_stump_player_id'];
                        }    
                    }
                    $fieldings = array_count_values($fielding);
                    

                    foreach ($fieldings as $key => $value) {

                        $fielding_score = $value*$catch_points->t20score;

                        $playerinfo = $this->Webservice_model->player_info($key);
                        $player_id = $playerinfo->id;  

                        $this->Webservice_model->update_playing_eleven_fielding_score($fielding_score,$player_id,$id->match_id); 
                    }
                }

                else if($id->type =="Test")
                {
                    $playing_eleven = $this->Webservice_model->playing_eleven("1");
                    $single = $this->Webservice_model->playing_eleven("2");
                    $wicket = $this->Webservice_model->playing_eleven("3");
                    $catch_points = $this->Webservice_model->playing_eleven("4");
                    $six = $this->Webservice_model->playing_eleven("10");
                    $duck = $this->Webservice_model->playing_eleven("8");   
                    $four = $this->Webservice_model->playing_eleven("9");   
                    $fifty = $this->Webservice_model->playing_eleven("11"); 
                    $hundred = $this->Webservice_model->playing_eleven("12");   
                    $four_wicket = $this->Webservice_model->playing_eleven("14");
                    $five_wicket =$this->Webservice_model->playing_eleven("15");

                    $single_runs = $single->testscore;
                    $four_runs = $four->testscore;
                    $six_runs = $six->testscore;
                        foreach ($resp['data']['batting'] as $player)
                        {
                            if($player['scoreboard'] =="S1" or $player['scoreboard'] =="S2" )
                            {
                                $six_hits = $player['six_x'];
                                $four_hits = $player['four_x'];
                                $run_hits = $player['score'];
                                $single_hits = $run_hits;
                                $playerinfo = $this->Webservice_model->player_info($player['player_id']);
                                $player_id = $playerinfo->id;

                                if($single_hits =="0")
                                {
                                    if($playerinfo->designationid !="2")
                                    {
                                        $total_score = $playing_eleven->testscore + $duck->testscore;
                                        
                                        $this->Webservice_model->update_playing_eleven_score($total_score,$player_id,$id->match_id);
                                    }                           
                                }
                                else
                                {
                                    if($single_hits >=50 && $single_hits <100)
                                {
                                    $total_score = $single_runs*$single_hits + $four_runs*$four_hits + $six_runs*$six_hits + $playing_eleven->testscore + $fifty->testscore;
                                }
                                else if($single_hits >=100)
                                {
                                    $total_score = $single_runs*$single_hits + $four_runs*$four_hits + $six_runs*$six_hits + $playing_eleven->testscore + $hundred->testscore;
                                }   
                                else
                                {
                                    $total_score = $single_runs*$single_hits + $four_runs*$four_hits + $six_runs*$six_hits + $playing_eleven->testscore;
                                }       
                                $this->Webservice_model->update_playing_eleven_score($total_score,$player_id,$id->match_id);
                             
                                }   
                            }  
                            
                            else if($player['scoreboard'] =="S3" or $player['scoreboard'] =="S4" )
                            { 
                                $six_hits = $player['six_x'];
                                $four_hits = $player['four_x'];
                                $run_hits = $player['score'];
                                $single_hits = $run_hits;
                                
                                $playerinfo = $this->Webservice_model->player_info($player['player_id']);
                                $player_id = $playerinfo->id;

                                if($single_hits =="0")
                                {
                                    if($playerinfo->designationid !="2")
                                    {
                                        $total_score =$duck->testscore;
                                    
                                        $this->Webservice_model->update_playing_eleven_score_second_innings($total_score,$player_id,$id->match_id);
                                    }                           
                                }
                                else
                                {
                                    if($single_hits >=50 && $single_hits <100)
                                {
                                    $total_score = $single_runs*$single_hits + $four_runs*$four_hits + $six_runs*$six_hits + $fifty->testscore;
                                }
                                else if($single_hits >=100)
                                {
                                    $total_score = $single_runs*$single_hits + $four_runs*$four_hits + $six_runs*$six_hits + $hundred->testscore;
                                }   
                                else
                                {
                                    $total_score = $single_runs*$single_hits + $four_runs*$four_hits + $six_runs*$six_hits;
                                }       
                                $this->Webservice_model->update_playing_eleven_score_second_innings($total_score,$player_id,$id->match_id);
                                }   
                                
                            }
                        }   
                        
                        foreach ($resp['data']['bowling'] as $bowling) {
                            if($bowling['scoreboard'] =="S1" or $bowling['scoreboard'] =="S2")
                            {
                            $wickets = $bowling['wickets'];

                            $wickets_taken = $wickets*$wicket->testscore;

                            if($wickets =="4")
                            {
                                $wickets_taken_four = $four_wicket->testscore;
                            }
                            else
                            {
                                $wickets_taken_four = 0;
                            }
                            if($wickets >="5")
                            {
                                $wickets_taken_five = $five_wicket->testscore;
                            }
                            else
                            {
                                $wickets_taken_five = 0;
                            }

                            $bolling_score =$wickets_taken+$wickets_taken_four+$wickets_taken_five;

                            $playerinfo = $this->Webservice_model->player_info($bowling['player_id']);
                            $player_id = $playerinfo->id;  

                            $this->Webservice_model->update_playing_eleven_bolling_score($bolling_score,$player_id,$id->match_id);  
                            }

                            else if($bowling['scoreboard'] =="S3" or $bowling['scoreboard'] =="S4")
                            {
                                $wickets = $bowling['wickets'];

                                $wickets_taken = $wickets*$wicket->testscore;

                                if($wickets =="4")
                                {
                                    $wickets_taken_four = $four_wicket->testscore;
                                }
                                else
                                {
                                    $wickets_taken_four = 0;
                                }
                                if($wickets >="5")
                                {
                                    $wickets_taken_five = $five_wicket->testscore;
                                }
                                else
                                {
                                    $wickets_taken_five = 0;
                                }

                                $bolling_score =$wickets_taken+$wickets_taken_four+$wickets_taken_five;

                                $playerinfo = $this->Webservice_model->player_info($bowling['player_id']);
                                $player_id = $playerinfo->id;  

                                $this->Webservice_model->update_playing_eleven_bolling_score_second_innings($bolling_score,$player_id,$id->match_id); 
                            }
                        }   
                        

                        $catch_points = $this->Webservice_model->playing_eleven("4");

                        foreach ($resp['data']['batting'] as $key ) {
                            if($key['catch_stump_player_id'] !="")
                            {
                               $fielding[] = $key['catch_stump_player_id'];
                            }    
                        }
                        $fieldings = array_count_values($fielding);
                        

                        foreach ($fieldings as $key => $value) {

                            $fielding_score = $value*$catch_points->testscore;

                            $playerinfo = $this->Webservice_model->player_info($key);
                            $player_id = $playerinfo->id;  

                            $this->Webservice_model->update_playing_eleven_fielding_score($fielding_score,$player_id,$id->match_id); 
                        }
                                      
                }   
            }
        }           
    }

     // API for update points for playing eleven
     function playing_history_post()
     { 
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        
        $contest = $this->Webservice_model->playing_history_contest($request);
        $match = $this->Webservice_model->playing_history_match($request);

        $resp['wins'] = "0"; 
        $resp['series'] = "0"; 
        if($contest !="")
        {
            $resp['contest'] = $contest;
        }
        else
        {
            $resp['contest'] = 0;
        }   
        if($match !="")
        {
            $resp['matchs'] = $match;
        }
        else
        {
            $resp['matchs'] = 0;
        }
        if($resp !="")
        {
            $result = array('status'=>'success','message' =>"Playing history",'responsecode'=>'200','data'=>$resp);
        }
        else
        {
            $result = array('status'=>'error','message' =>"No details found",'responsecode'=>'500','amount'=>"0",'data'=>"");
        }

        $this->response($result); 

                    
    }

    // API for update leaderboard points according to user points not in use 
    function update_leaderboard($request)
    {   
            
        $id['match_id'] = $request['matchid'];      
        $resp = $this->Webservice_model->get_leaderboard_record($request);  

        foreach ($resp as $key) 
        {
            $players = $this->Webservice_model->get_myteam_player_id($key->user_id, $key->teamid);
            $pla = array();
            foreach ($players as $player) {
                $pla[] = $player->player_id;
            }       
                
            $playing = $this->Webservice_model->playing_team_list($id);
            $points =0;
            $bolling_points= 0;
            $fielding_points= 0;
            foreach ($playing as  $play){               
                if(in_array($play['playerid'],$pla))
                {
                    $points += $this->Webservice_model->total_points($play['playerid'],$key->matchid);  
                    $bolling_points += $this->Webservice_model->total_points_bolling($play['playerid'],$key->matchid);
                    $fielding_points += $this->Webservice_model->total_points_fielding($play['playerid'],$key->matchid);    
                }   
            } 
            $bating = $points;
            $ball = $bolling_points;
            $fielding = $fielding_points;

            $total_points = $bating+$ball+$fielding;
            $this->Webservice_model->update_leaderboard($key->teamid,$id['match_id'],$key->user_id,$total_points);
            
        }   
    }

    // APi for user plauying eleven score update 
    function user_playing_eleven($score,$player_id,$match_id)
    { 
        $allteams = $this->Webservice_model->all_team_for_this_match($match_id);
        
        foreach ($allteams as $allteam) {
            $pla = $this->checkplayer($player_id,$allteam);

            if($pla !="")
            {                           
                $captain = $this->check_captain($pla['id']);
                $vicecaptain = $this->check_vicecaptain($pla['id']);
                if($captain['is_captain'] =="1")
                {   
                    $data = array('total_points' =>2*$score,'playing_score'=>'1');
                    $this->Webservice_model->update_user_points_for_match($data,$pla);
                    
                }
                elseif ($vicecaptain['is_vicecaptain'] =="1") {
                    $data = array('total_points' =>1.5*$score,'playing_score'=>'1');
                    $this->Webservice_model->update_user_points_for_match($data,$pla);
                }
                else
                {
                    $data = array('total_points' =>$score,'playing_score'=>'1');
                    $this->Webservice_model->update_user_points_for_match($data,$pla);
                }   
            }               
        }
        
    }

    // API for check user player is playing or not not
    function checkplayer($player_id,$allteam)
    {
        return $this->Webservice_model->check_playerfor_user_bymatch($player_id,$allteam);
    }

    // API for check user player is captain or not not
    function check_captain($id)
    {
        return $this->Webservice_model->check_playerfor_user_captain($id);
    }

    // API for check user player is vice captain or not not
    function check_vicecaptain($id)
    {
        return $this->Webservice_model->check_playerfor_user_vicecaptain($id);
    }

    // Api for update playing eleven score for batting
    function update_playing_eleven_score_for_user_battng($score,$player_id,$match_id)
    {       
        $allteams = $this->Webservice_model->all_team_for_this_match($match_id);

        foreach ($allteams as $allteam) {
            $pla = $this->checkplayer($player_id,$allteam);
            
            if($pla !="")
            {                           
                $captain = $this->check_captain($pla['id']);
                $vicecaptain = $this->check_vicecaptain($pla['id']);
                if($captain['is_captain'] =="1")
                {   
                    $data = array('batting_points' =>2*$score);
                    $this->Webservice_model->update_user_points_for_match($data,$pla);
                    
                }
                elseif ($vicecaptain['is_vicecaptain'] =="1") {
                    $data = array('batting_points' =>1.5*$score);
                    $this->Webservice_model->update_user_points_for_match($data,$pla);
                }
                else
                {
                    $data = array('batting_points' =>$score);
                    
                    $this->Webservice_model->update_user_points_for_match($data,$pla);
                }
                
            }   
            
        }

    }

    // Api for update playing eleven score for second batting for test match
    function update_playing_eleven_score_for_user_battng_second($score,$player_id,$match_id)
    {       
        $allteams = $this->Webservice_model->all_team_for_this_match($match_id);

        foreach ($allteams as $allteam) {
            $pla = $this->checkplayer($player_id,$allteam);
            
            if($pla !="")
            {                           
                $captain = $this->check_captain($pla['id']);
                $vicecaptain = $this->check_vicecaptain($pla['id']);
                if($captain['is_captain'] =="1")
                {   
                    $data = array('second_innings_batting' =>2*$score);
                    $this->Webservice_model->update_user_points_for_match($data,$pla);
                    
                }
                elseif ($vicecaptain['is_vicecaptain'] =="1") {
                    $data = array('second_innings_batting' =>1.5*$score);
                    $this->Webservice_model->update_user_points_for_match($data,$pla);
                    
                }
                else
                {
                    $data = array('second_innings_batting' =>$score);
                    $this->Webservice_model->update_user_points_for_match($data,$pla);
                }
                
            }   
            
        }

    }

    // Api for update playing eleven score for bolling 
    function update_playing_eleven_score_for_user_bolling($score,$player_id,$match_id)
    {
        $allteams = $this->Webservice_model->all_team_for_this_match($match_id);

        foreach ($allteams as $allteam) {
            $pla = $this->checkplayer($player_id,$allteam);
            
            if($pla !="")
            {                           
                $captain = $this->check_captain($pla['id']);
                $vicecaptain = $this->check_vicecaptain($pla['id']);
                if($captain['is_captain'] =="1")
                {   
                    $data = array('bolling_points' =>2*$score);
                    $this->Webservice_model->update_user_points_for_match($data,$pla);
                    
                }
                elseif ($vicecaptain['is_vicecaptain'] =="1") {
                    $data = array('bolling_points' =>1.5*$score);
                    $this->Webservice_model->update_user_points_for_match($data,$pla);
                }
                else
                {
                    $data = array('bolling_points' =>$score);
                    $this->Webservice_model->update_user_points_for_match($data,$pla);
                }
            }   
        }
    }

    // Api for update playing eleven score for second bolling for test match
    function update_playing_eleven_score_for_user_bolling_second($score,$player_id,$match_id)
    {
        $allteams = $this->Webservice_model->all_team_for_this_match($match_id);

        foreach ($allteams as $allteam) {
            $pla = $this->checkplayer($player_id,$allteam);
            
            if($pla !="")
            {                           
                $captain = $this->check_captain($pla['id']);
                $vicecaptain = $this->check_vicecaptain($pla['id']);
                if($captain['is_captain'] =="1")
                {   
                    $data = array('second_innings_bolling' =>2*$score);
                    $this->Webservice_model->update_user_points_for_match($data,$pla);
                    
                }
                elseif ($vicecaptain['is_vicecaptain'] =="1") {
                    $data = array('second_innings_bolling' =>1.5*$score);
                    $this->Webservice_model->update_user_points_for_match($data,$pla);
                }
                else
                {
                    $data = array('second_innings_bolling' =>$score);
                    $this->Webservice_model->update_user_points_for_match($data,$pla);
                }
            }   
        }
    }

    // Api for update playing eleven score for fielding 
    function update_playing_eleven_score_for_user_fielding($score,$player_id,$match_id)
    {
        $allteams = $this->Webservice_model->all_team_for_this_match($match_id);

        foreach ($allteams as $allteam) {
            $pla = $this->checkplayer($player_id,$allteam);
            
            if($pla !="")
            {                           
                $captain = $this->check_captain($pla['id']);
                $vicecaptain = $this->check_vicecaptain($pla['id']);
                if($captain['is_captain'] =="1")
                {   
                    $data = array('fielding_points' =>2*$score);
                    $this->Webservice_model->update_user_points_for_match($data,$pla);
                    
                }
                elseif ($vicecaptain['is_vicecaptain'] =="1") {
                    $data = array('fielding_points' =>1.5*$score);
                    $this->Webservice_model->update_user_points_for_match($data,$pla);
                }
                else
                {
                    $data = array('fielding_points' =>$score);
                    $this->Webservice_model->update_user_points_for_match($data,$pla);
                }
                
            }   
            
        }
    }

    // Api for update playing eleven score for second fielding for test match
    function update_playing_eleven_score_for_user_fielding_second($score,$player_id,$match_id)
    {
        $allteams = $this->Webservice_model->all_team_for_this_match($match_id);

        foreach ($allteams as $allteam) {
            $pla = $this->checkplayer($player_id,$allteam);
            
            if($pla !="")
            {                           
                $captain = $this->check_captain($pla['id']);
                $vicecaptain = $this->check_vicecaptain($pla['id']);
                if($captain['is_captain'] =="1")
                {   
                    $data = array('second_innings_fielding' =>2*$score);
                    $this->Webservice_model->update_user_points_for_match($data,$pla);
                    
                }
                elseif ($vicecaptain['is_vicecaptain'] =="1") {
                    $data = array('second_innings_fielding' =>1.5*$score);
                    $this->Webservice_model->update_user_points_for_match($data,$pla);
                }
                else
                {
                    $data = array('second_innings_fielding' =>$score);
                    $this->Webservice_model->update_user_points_for_match($data,$pla);
                }
            }   
        }
    }

    // API for update leaderbord points
    function update_leaderboard_user($request)
    {   
        $id['match_id'] = $request['matchid'];  
        
        $resp = $this->Webservice_model->get_team_record($id);
        foreach ($resp as $key) 
        {   

            $allPlayers =  $this->Webservice_model->my_team_all_players($key->user_id, $key->id);


            $Apoints = 0 ;
            $Bpoints = 0 ;
            $Cpoints = 0 ;
            $total_points = 0 ;
            foreach ($allPlayers as $Player) {

                $player = $this->Webservice_model->player_points_for_user($Player->player_id,$key->id,$id['match_id']);
               // echo $this->db->last_query();die;

                $Points  = $player->total_points + $player->batting_points + $player->bolling_points + $player->fielding_points + $player->second_innings_batting + $player->second_innings_bolling + $player->second_innings_fielding;

                if($player->is_captain =="1")
                {
                    $Apoints  = $Points * 2;
                }

                else if($player->is_vicecaptain =="1")
                {
                    $Bpoints =  $Points * 1.5;
                }

                else if($player->is_captain =="0" && $player->is_vicecaptain =="0")
                {
                    $Cpoints +=  $Points ;
                }  

            }

            $total_points = $Apoints + $Bpoints + $Cpoints;

            $this->Webservice_model->update_leaderboard($key->id,$key->match_id,$key->user_id,$total_points);
            
        }   
    }
    
    function update_rank_user($request)
    {
        $id = $request; 

        $contests = $this->Webservice_model->get_total_contest_for_match($id);

        foreach ($contests as $contest) 
        {               
            $respp = $this->Webservice_model->get_team_record_by_rank_data($id, $contest['contest_id']);

            $a = "1";
            foreach ($respp as $keys) 
            {   
                $this->Webservice_model->update_rankby_pointsNow($keys->id, $a);
                $a++;
            }  
            $resp = $this->Webservice_model->get_team_record_by_rank_data($id, $contest['contest_id']);
            
            $i = 1;             
            foreach ($resp as $key) 
            {   
                if($resp[0]->id == $key->id)
                {
                    $this->Webservice_model->update_rankby_points($key->id, $i);
                    $i++;
                }    
                else if($resp[0]->id != $key->id)
                {
                    $checkRank =  $this->Webservice_model->getRankAndPoint($key->temprank,$id, $contest['contest_id'],$key->points);
                 
                    if(isset($checkRank) && !empty($checkRank))
                    {   
                        if($checkRank->points === $key->points)
                        {

                            $this->Webservice_model->update_rankby_points($key->id, $checkRank->rank);
                            $i++;
                        }    
                        else
                        {
                            $this->Webservice_model->update_rankby_points($key->id, $i);
                            $i++;
                        }    
                    }
                    else
                    {
                         $this->Webservice_model->update_rankby_points($key->id, $i);
                         $i++;
                    }   
                } 
            }   
        }
    }
    
    function update_rank_userss($request)
    {
       $id = $request['matchid']; 
        $contests = $this->Webservice_model->get_total_contest_for_match($id);

        foreach ($contests as $contest) 
        {               
            $resp = $this->Webservice_model->get_team_record_by_rank_data($id, $contest['contest_id']);
            $i = 1;             
            foreach ($resp as $key) 
            {   
                $this->Webservice_model->update_rankby_points($key->id, $i);

                $i++;
            }   
        }
    }
    
    

    // API for update winning amount in user account
    function update_winning_amount_user($request)
    {
       
    }

    //// API for update match status from live to result
    function update_match_status_from_live_to_result_get()
    {
        $live_matches = $this->Webservice_model->get_all_live_match();

        foreach ($live_matches as $live_match) 
        {
            $api_url = "https://cricket.sportmonks.com/api/v2.0/fixtures/".$live_match->unique_id."?api_token=".$this->apikey."";

            $ch = curl_init();
            curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
            curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
            curl_setopt($ch, CURLOPT_URL,$api_url);
            $result=curl_exec($ch);
            curl_close($ch);
            $cricketMatches= json_decode(json_encode(json_decode($result)), True); //json_decode($result);
            
            if($cricketMatches['data']['status'] =="Finished")
            {  
                if($live_match->complete_status == "0")
                {
                    $this->update_rank_user($live_match->match_id);
                    $this->update_score_points_final_after_match_complete($live_match->match_id);
                }    
                $match_result =  $this->Webservice_model->get_match_by_unique_id($live_match->unique_id);
            
                if($match_result !="")
                {        
                    $winner =   $this->db->get_where('team',array('unique_id'=>$cricketMatches['data']['winner_team_id']))->row();
                    $tosswinner =   $this->db->get_where('team',array('unique_id'=>$cricketMatches['data']['toss_won_team_id']))->row();
                    
                    if($live_match->complete_status == "0")
                    {        
                        $matchdata = array('match_status'=>"Live",
                                'winner_team'=>$winner->team_id,
                                'complete_status'=>'1',
                                'toss_winner_team'=>$tosswinner->team_id,
                                'match_status_note'=>$cricketMatches['data']['note']
                            );
    
                        $data = array('match_status'=>"Live",
                                'winner_team'=>$winner->team_id,
                                'toss_winner_team'=>$tosswinner->team_id
                            );    
                            
                        $this->Webservice_model->update_match_status_by_unique_id($match_result->match_id,$matchdata);
                        $this->Webservice_model->update_match_status_by_match_id($match_result->match_id);
                    }    
                    $this->update_rank_user($match_result->match_id);
                                         
                }
                
                $api_url  = "https://cricket.sportmonks.com/api/v2.0/fixtures/".$live_match->unique_id."?api_token=".$this->apikey."&include=runs";

                $ch = curl_init();
                curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
                curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
                curl_setopt($ch, CURLOPT_URL,$api_url);
                $result=curl_exec($ch);
                curl_close($ch);
                $result_responce = json_decode(json_encode(json_decode($result)), True);

                $match_id = $live_match->match_id;
                
                $innings_count = count($result_responce['data']['runs']);

                if($innings_count > 0)
                {
                    foreach ($result_responce['data']['runs'] as $innings) 
                    {
                        if($innings_count == "4")
                        {
                            $team_id_main = $this->Webservice_model->get_team_unique_id($innings['team_id']);
                            $get_id_by_match = $this->Webservice_model->get_team_id_in_match_table($team_id_main,$match_id);

                            if($get_id_by_match !="")
                            {
                                $T1 = $innings['score'].'/'.$innings['wickets'];
                                $O1 = $innings['overs'];

                                $save_score = array('team1Score_secondInning' =>$T1,'team1Over_secondInning'=>$O1);
                            }   
                            else
                            {
                                $T1 = $innings['score'].'/'.$innings['wickets'];
                                $O1 = $innings['overs'];
                                $save_score = array('team2Score_secondInning' =>$T1,'team2Over_secondInning'=>$O1);
                            }
                            $this->db->where('unique_id',$key->unique_id);
                            $this->db->update('match',$save_score);                           
                        }
                        else if($innings_count == "3")
                        {
                            $team_id_main = $this->Webservice_model->get_team_unique_id($innings['team_id']);
                            $get_id_by_match = $this->Webservice_model->get_team_id_in_match_table($team_id_main,$match_id);

                            if($get_id_by_match !="")
                            {
                                $T1 = $innings['score'].'/'.$innings['wickets'];
                                $O1 = $innings['overs'];

                                $save_score = array('team1Score_secondInning' =>$T1,'team1Over_secondInning'=>$O1);
                            }   
                            else
                            {
                                $T1 = $innings['score'].'/'.$innings['wickets'];
                                $O1 = $innings['overs'];
                                $save_score = array('team2Score_secondInning' =>$T1,'team2Over_secondInning'=>$O1);
                            }
                            $this->db->where('unique_id',$key->unique_id);
                            $this->db->update('match',$save_score);                           
                        }
                        else if($innings_count == "2")
                        {
                            $team_id_main = $this->Webservice_model->get_team_unique_id($innings['team_id']);
                            $get_id_by_match = $this->Webservice_model->get_team_id_in_match_table($team_id_main,$match_id);

                            if($get_id_by_match !="")
                            {
                                $T1 = $innings['score'].'/'.$innings['wickets'];
                                $O1 = $innings['overs'];

                                $save_score = array('team1Score' =>$T1,'team1Over'=>$O1);
                            }   
                            else
                            {
                                $T1 = $innings['score'].'/'.$innings['wickets'];
                                $O1 = $innings['overs'];
                                $save_score = array('team2Score' =>$T1,'team2Over'=>$O1);
                            }
                            $this->db->where('unique_id',$key->unique_id);
                            $this->db->update('match',$save_score); 
                            
                        }
                        else if($innings_count == "1")
                        {
                            $team_id_main = $this->Webservice_model->get_team_unique_id($innings['team_id']);
                            $get_id_by_match = $this->Webservice_model->get_team_id_in_match_table($team_id_main,$match_id);

                            if($get_id_by_match !="")
                            {
                                $T1 = $innings['score'].'/'.$innings['wickets'];
                                $O1 = $innings['overs'];

                                $save_score = array('team1Score' =>$T1,'team1Over'=>$O1);
                            }   
                            else
                            {
                                $T1 = $innings['score'].'/'.$innings['wickets'];
                                $O1 = $innings['overs'];
                                $save_score = array('team2Score' =>$T1,'team2Over'=>$O1);
                            }
                            $this->db->where('unique_id',$key->unique_id);
                            $this->db->update('match',$save_score);                         
                        }    
                    }
                }
            }  
        }
    }

    //API for get refer friend list 
    function refer_friend_list_post()
    {
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        
        $resp = $this->Webservice_model->refer_friend_list($request);
        foreach ($resp as $key){
            $bonus = $this->Webservice_model->get_reffer_bonus($key->user_id);
            if($bonus !="")
            {
                $key->bonus = $bonus;
            }
            else
            {
                $key->bonus = 0;
            }   

        }

        if(count($resp) >0)
        {
            $result = array('status'=>'success','message' =>"Friend list",'responsecode'=>'200','data'=>$resp);
        }
        else
        {
            $result = array('status'=>'error','message' =>"No Friend found",'responsecode'=>'500','data'=>"");
        }

        $this->response($result); 
        
    }

    //API for withdrow amount
    function withdrow_amount_post()
    {
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        
        $winning_credit = $this->Webservice_model->winning_amount($request);
        $winning_debit = $this->Webservice_model->winning_amount_debit($request);

        $winning = $winning_credit - $winning_debit;
        
        $this->Webservice_model->user_account_info($request);
        if($winning > $request['amount'])
        {   
            $resp = $this->Webservice_model->winning_amount_debit_request($request);
        

            if($resp !="")
            {
                $result = array('status'=>'success','message' =>"Withdrow request send to admin we will contact you soon",'responsecode'=>'200','data'=>"");
            }
            else
            {
                $result = array('status'=>'error','message' =>"Something went wrong please try again later",'responsecode'=>'500','data'=>"");
            }
        }       
        else
        {
            $result = array('status'=>'error','message' =>"No sufficient amount",'responsecode'=>'500');
        }

        $this->response($result); 
        
    }


    //API for user account information withdrow amount
    function user_withdrow_information_post()
    {
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        
        $resp = $this->Webservice_model->user_withdrow_information($request);
        
        if($resp !="")
        {
            $result = array('status'=>'success','message' =>"User withdrow information ",'responsecode'=>'200','data'=>$resp);
        }
        else
        {
            $result = array('status'=>'error','message' =>"No information found",'responsecode'=>'500','data'=>"");
        }

        $this->response($result); 
        
    }

    //API for user global rank
    function global_rank_post()
    {
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        
        $resp1 = $this->Webservice_model->global_rank_user($request);
        
        $i= 1;
        foreach ($resp1 as $key){
            $key->rank = $i++;
            $key->points = round($key->points , 2);
        }

        foreach ($resp1 as $session) {
            if($session->user_id == $request['user_id'])
            {
                $data1[] = $session; 
            }   
        }
        foreach ($resp1 as $non_session) {
            if($non_session->user_id != $request['user_id'])
            {
                $data2[] = $non_session;
            }   
        }

        $resp = array_merge($data1,$data2);
        
        if($resp !="")
        {
            $result = array('status'=>'success','message' =>"Global rank record ",'responsecode'=>'200','data'=>$resp);
        }
        else
        {
            $result = array('status'=>'error','message' =>"No information found",'responsecode'=>'500','data'=>"");
        }

        $this->response($result); 
        
    }

    //API for user hash key
    function hashkey_post()
    {       
        $request = $this->input->post('hashkey');
        
        $string =  explode("&",$request);       
        
        $a = explode("=", $string[0]);
        $b = explode("=", $string[1]);
        $c = explode("=", $string[2]);
        $d = explode("=", $string[3]);
        $e = explode("=", $string[4]);
        $f = explode("=", $string[5]);
        $g = explode("=", $string[6]);  
        $h = explode("=", $string[7]);
        $i = explode("=", $string[8]);
        $j = explode("=", $string[9]);
        $k = explode("=", $string[10]);     

        $key            = $a[1];
        $amount         = $b[1];
        $txnid          = $c[1];
        $email          = $d[1];
        $productinfo    = $e[1];
        $firstname      = $f[1];
        $udf1           = $g[1];
        $udf2           = $h[1];
        $udf3           = $i[1];
        $udf4           = $j[1];
        $udf5           = $k[1];

        

        $salt="eBp3QATTma"; // PLACE YOUR SALT KEY HERE

        // Salt should be same Post Request
        
        $retHashSeq = $key . '|' . $this->checkNull($txnid) . '|' .$this->checkNull($amount) . '|' .$this->checkNull($productinfo) . '|' . $this->checkNull($firstname) . '|' . $this->checkNull($email) . '|' . $this->checkNull($udf1) . '|' . $this->checkNull($udf2) . '|' . $this->checkNull($udf3) . '|' . $this->checkNull($udf4) . '|' . $this->checkNull($udf5) . '||||||'. $salt;
        

        $hash = strtolower(hash('sha512', $retHashSeq)); 
        $arr['payment_hash'] = $hash;
        $arr['status']=0;
        $arr['errorCode']=null;
        $arr['responseCode']=null;
        $arr['hashtest']=$retHashSeq;
        $output=$arr;
        
        if($output !="")
        {
            $result = $output;
        }
        else
        {
            $result = array('status'=>'error','message' =>"Some error occured please try agiain",'responsecode'=>'500','data'=>"");
        }

        $this->response($result); 
        
    }

    // API for check null value
    function checkNull($value)
    {
        if ($value == null){
            return '';
        } else {
            return $value;
        }
    }


     function get_offers_post(){
        $result = array();
        $show_offers = $this->Webservice_model->get_offers();
        if(!empty($show_offers))
        {
            $result = array('status'=>'success','message' =>"Data Found",'responsecode'=>'200','data'=>$show_offers);
        }
        else
        {
            $result = array('status'=>'error','message' =>"No information found",'responsecode'=>'500','data'=>"");
        }

        $this->response($result); 
    }

     function update_app_post(){
        $result = array();
        $version = $this->Webservice_model->update_app();
        if(!empty($version))
        {
            $result = array('status'=>'success','message' =>"New version",'responsecode'=>'200','data'=>$version);
        }
        else
        {
            $result = array('status'=>'error','message' =>"No information found",'responsecode'=>'500','data'=>"");
        }

        $this->response($result); 
    }

     function update_documents_post(){
      
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        if($request['type'] == 'Aadhaar')
        { 
            $dd = date('YmdHis');
            $base=$request['document'];
            $value=time();
            $UserImage = 'UserImage'.$dd.'.jpg';
            $filename = 'uploads/documents/'.$UserImage;
            $binary=base64_decode($base);
            $status = file_put_contents($filename,$binary);
            $user_id = $request['user_id'];
            $data = array(
                'aadhar_card_name'=>$request['name'],
                'aadhar'=>$request['document_number'],
                'aadharcard_status'=>"1",               
                'state'=>$request['state'],
                'aadharcard_dob'=>$request['dob'],
                'aadharcard_image'=>$UserImage
            );

            $resp = $this->Webservice_model->update_documents_details($data,$user_id);

            if(!empty($resp))
            {
                $result = array('status'=>'success','message' =>"Documents Uploads",'responsecode'=>'200','data'=>"");
            }
            else
            {
                $result = array('status'=>'error','message' =>"Please try again",'responsecode'=>'500','data'=>"");
            }

            $this->response($result); 
        }
        else
        { 
            $dd = date('YmdHis');
            $base=$request['document'];
            $value=time();
            $UserImage = 'UserImage'.$dd.'.jpg';
            $filename = 'uploads/documents/'.$UserImage;
            $binary=base64_decode($base);
            $status = file_put_contents($filename,$binary);
            $user_id = $request['user_id'];
            $data = array(
                'pan_card_name'=>$request['name'],
                'pan_number'=>$request['document_number'],
                'state'=>$request['state'],
                'pancard_status'=>"1",
                'pancard_dob'=>$request['dob'],
                'pancard_image'=>$UserImage
            );

            $resp = $this->Webservice_model->update_documents_details($data,$user_id);

            if(!empty($resp))
            {
                $result = array('status'=>'success','message' =>"Documents Uploads",'responsecode'=>'200','data'=>"");
            }
            else
            {
                $result = array('status'=>'error','message' =>"Please try again",'responsecode'=>'500','data'=>"");
            }

            $this->response($result); 
        }  
    }
    
     function user_contest_post(){
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        
        if($request['team_size'] >= "2" && $request['team_size'] <= "4")
        {
            $data = array('rank'=>1,'from_rank' =>0,'percent_destribution'=>100 ,'to_rank'=>1,'poolprice' =>$request['price'] ,'price' =>$request['price']);
            $last_id  = $this->Webservice_model->common_insert($data,'user_winning_info');
            $resp = $this->Webservice_model->get_leaderboard_user($request,$last_id);
        }

        else if($request['team_size'] >= "5" && $request['team_size'] <='19')
        {
            $data = array('rank'=>1,'from_rank' =>0,'percent_destribution'=>40 ,'to_rank'=>1,'poolprice' =>$request['price']*40/100 ,'price' =>$request['price']);
            $this->Webservice_model->common_insert($data,'user_winning_info');
            $data = array('rank'=>2,'from_rank' =>0,'percent_destribution'=>25 ,'to_rank'=>2,'poolprice' =>$request['price']*25/100 ,'price' =>$request['price']);
            $this->Webservice_model->common_insert($data,'user_winning_info');
            $data = array('rank'=>3,'from_rank' =>0,'to_rank'=>3,'percent_destribution'=>15 ,'poolprice' =>$request['price']*15/100 ,'price' =>$request['price']);
            $this->Webservice_model->common_insert($data,'user_winning_info');
            $data = array('rank'=>4,'from_rank' =>0,'to_rank'=>4,'percent_destribution'=>12.5 , 'poolprice' =>$request['price']*12.5/100 ,'price' =>$request['price']);
            $this->Webservice_model->common_insert($data,'user_winning_info');
            $data = array('rank'=>5,'from_rank' =>0,'to_rank'=>5,'percent_destribution'=>7.5 , 'poolprice' =>$request['price']*7.5/100 ,'price' =>$request['price']);
            $last_id  = $this->Webservice_model->common_insert($data,'user_winning_info');

            $resp = $this->Webservice_model->get_leaderboard_user($request,$last_id);
        }

        else if($request['team_size'] >= "20" && $request['team_size'] <='24')
        {
            $data = array('rank'=>1,'from_rank' =>0,'to_rank'=>1,'percent_destribution'=>25 ,'poolprice' =>$request['price']*25/100 ,'price' =>$request['price']);
            $this->Webservice_model->common_insert($data,'user_winning_info');
            $data = array('rank'=>2,'from_rank' =>0,'to_rank'=>2,'percent_destribution'=>20 , 'poolprice' =>$request['price']*20/100 ,'price' =>$request['price']);
            $this->Webservice_model->common_insert($data,'user_winning_info');
            $data = array('rank'=>3,'from_rank' =>0,'to_rank'=>3,'percent_destribution'=>15 , 'poolprice' =>$request['price']*15/100 ,'price' =>$request['price']);
            $this->Webservice_model->common_insert($data,'user_winning_info');
            $data = array('rank'=>4,'from_rank' =>0,'to_rank'=>4,'percent_destribution'=>10 , 'poolprice' =>$request['price']*10/100 ,'price' =>$request['price']);
             $this->Webservice_model->common_insert($data,'user_winning_info');
            $data = array('rank'=>'5-10','from_rank' =>5,'to_rank'=>10,'percent_destribution'=>5 , 'poolprice' =>$request['price']*5/100 ,'price' =>$request['price']);
            $last_id  = $this->Webservice_model->common_insert($data,'user_winning_info');

            $resp = $this->Webservice_model->get_leaderboard_user($request,$last_id);
        }

        else if($request['team_size'] >= "25" && $request['team_size'] <='49')
        {
            $data = array('rank'=>1,'from_rank' =>0,'to_rank'=>1,'percent_destribution'=>25 , 'poolprice' =>$request['price']*25/100 ,'price' =>$request['price']);
            $this->Webservice_model->common_insert($data,'user_winning_info');
            $data = array('rank'=>2,'from_rank' =>0,'to_rank'=>2,'percent_destribution'=>15 , 'poolprice' =>$request['price']*15/100 ,'price' =>$request['price']);
            $this->Webservice_model->common_insert($data,'user_winning_info');
            $data = array('rank'=>3,'from_rank' =>0,'to_rank'=>3,'percent_destribution'=>10 , 'poolprice' =>$request['price']*10/100 ,'price' =>$request['price']);
            $this->Webservice_model->common_insert($data,'user_winning_info');
            $data = array('rank'=>4,'from_rank' =>0,'to_rank'=>4,'percent_destribution'=>6 , 'poolprice' =>$request['price']*6/100 ,'price' =>$request['price']);
             $this->Webservice_model->common_insert($data,'user_winning_info');
            $data = array('rank'=>5,'from_rank' =>0,'to_rank'=>5,'percent_destribution'=>5 ,'poolprice' =>$request['price']*5/100 ,'price' =>$request['price']);
            $this->Webservice_model->common_insert($data,'user_winning_info');
            $data = array('rank'=>'6-8','from_rank' =>6,'percent_destribution'=>4 , 'to_rank'=>8,'poolprice' =>$request['price']*4/100 ,'price' =>$request['price']);
            $this->Webservice_model->common_insert($data,'user_winning_info');
            $data = array('rank'=>'9-11','from_rank' =>9,'percent_destribution'=>3 , 'to_rank'=>11,'poolprice' =>$request['price']*3/100 ,'price' =>$request['price']);
            $this->Webservice_model->common_insert($data,'user_winning_info');
            $data = array('rank'=>'12-15','from_rank' =>12,'percent_destribution'=>2 , 'to_rank'=>15,'poolprice' =>$request['price']*2/100 ,'price' =>$request['price']);
            $this->Webservice_model->common_insert($data,'user_winning_info');
            $data = array('rank'=>'16-25','from_rank' =>16,'to_rank'=>25,'percent_destribution'=>1 , 'poolprice' =>$request['price']*1/100 ,'price' =>$request['price']);
            $this->Webservice_model->common_insert($data,'user_winning_info');

            $resp = $this->Webservice_model->get_leaderboard_user($request,$last_id);
        }

        else if($request['team_size'] >= "50" && $request['team_size'] <='100')
        {
            $data = array('rank'=>1,'from_rank' =>0,'percent_destribution'=>15 , 'to_rank'=>1,'poolprice' =>$request['price']*15/100 ,'price' =>$request['price']);
            $this->Webservice_model->common_insert($data,'user_winning_info');
            $data = array('rank'=>2,'from_rank' =>0,'percent_destribution'=>10 , 'to_rank'=>2,'poolprice' =>$request['price']*10/100 ,'price' =>$request['price']);
            $this->Webservice_model->common_insert($data,'user_winning_info');
            $data = array('rank'=>3,'from_rank' =>0,'to_rank'=>3,'percent_destribution'=>8 , 'poolprice' =>$request['price']*8/100 ,'price' =>$request['price']);
            $this->Webservice_model->common_insert($data,'user_winning_info');
            $data = array('rank'=>4,'from_rank' =>0,'to_rank'=>4,'percent_destribution'=>6 , 'poolprice' =>$request['price']*6/100 ,'price' =>$request['price']);
             $this->Webservice_model->common_insert($data,'user_winning_info');
            $data = array('rank'=>5,'from_rank' =>0,'to_rank'=>5,'percent_destribution'=>5 , 'poolprice' =>$request['price']*5/100 ,'price' =>$request['price']);
            $this->Webservice_model->common_insert($data,'user_winning_info');
            $data = array('rank'=>6,'from_rank' =>0,'to_rank'=>6,'percent_destribution'=>4 , 'poolprice' =>$request['price']*4/100 ,'price' =>$request['price']);
            $this->Webservice_model->common_insert($data,'user_winning_info');
            $data = array('rank'=>7,'from_rank' =>0,'to_rank'=>7,'percent_destribution'=>3.5 , 'poolprice' =>$request['price']*3.5/100 ,'price' =>$request['price']);
            $this->Webservice_model->common_insert($data,'user_winning_info');
            $data = array('rank'=>8,'from_rank' =>0,'to_rank'=>8,'percent_destribution'=>3 , 'poolprice' =>$request['price']*3/100 ,'price' =>$request['price']);
            $this->Webservice_model->common_insert($data,'user_winning_info');
            $data = array('rank'=>9,'from_rank' =>0,'to_rank'=>9,'percent_destribution'=>2.5 , 'poolprice' =>$request['price']*2.5/100 ,'price' =>$request['price']);
            $this->Webservice_model->common_insert($data,'user_winning_info');

            $data = array('rank'=>10,'from_rank' =>0,'to_rank'=>10,'percent_destribution'=>2 , 'poolprice' =>$request['price']*2/100 ,'price' =>$request['price']);
            $this->Webservice_model->common_insert($data,'user_winning_info');

            $data = array('rank'=>'11-25','from_rank' =>11,'to_rank'=>25,'percent_destribution'=>1.5 , 'poolprice' =>$request['price']*1.5/100 ,'price' =>$request['price']);
            $this->Webservice_model->common_insert($data,'user_winning_info');

            $data = array('rank'=>'26-27','from_rank' =>26,'to_rank'=>37,'percent_destribution'=>1 , 'poolprice' =>$request['price']*1/100 ,'price' =>$request['price']);
            $this->Webservice_model->common_insert($data,'user_winning_info');

            $data = array('rank'=>'38-50','from_rank' =>38,'to_rank'=>50,'percent_destribution'=>0.5 , 'poolprice' =>$request['price']*0.5/100 ,'price' =>$request['price']);
            $this->Webservice_model->common_insert($data,'user_winning_info');

            $resp = $this->Webservice_model->get_leaderboard_user($request,$last_id);
        }   
        

        if(!empty($resp))
        {
            $result = array('status'=>'success','message' =>"User leaderboard",'responsecode'=>'200','data'=>$resp);
        }
        else
        {
            $result = array('status'=>'error','message' =>"No information found",'responsecode'=>'500','data'=>"");
        }

        $this->response($result); 
    }
    
    
    function user_contestCreate_post(){
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        
        $alpha = random_str(4, 'abcdefghijklmnopqrstuvwxyz');
        $val = rand(11,99);
        $code = $val. $alpha. $request['user_id'];
        
        if($request['userTotalteam'] >= "2" && $request['userTotalteam'] <= "4")    
        {
            $userTotalWinners = "1";
        }

        else if($request['userTotalteam'] >= "5" && $request['userTotalteam'] <= "19")  
        {
            $userTotalWinners = "5";
        }
        else if($request['userTotalteam'] >= "20" && $request['userTotalteam'] <= "24") 
        {
            $userTotalWinners = "10";
        }
        else if($request['userTotalteam'] >= "25" && $request['userTotalteam'] <= "49") 
        {
            $userTotalWinners = "25";
        }
        else if($request['userTotalteam'] >= "50" && $request['userTotalteam'] <= "100")    
        {
            $userTotalWinners = "50";
        }
        
            $data = array('userContestName' =>$request['userContestName'],
                            'userWinners' =>$request['userWinners'],
                            'userTotalteam' =>$request['userTotalteam'],
                            'userJoinTeam' =>$request['userTotalteam'] -1 ,
                            'userEntry' =>$request['userEntry'],
                            'userMatchid' =>$request['userMatchid'],
                            'unique_code'=>$code,
                            'userId' =>$request['user_id'],
                            'userTotalWinners'=>$userTotalWinners,
                            'createdDate'=>date('Y-m-d H:i:s'),
                         );

            $resp  = $this->Webservice_model->user_contestCreate($data,'user_contest');
            
        if(!empty($resp))
        {
            $result = array('status'=>'success','message' =>"User Contest create",'responsecode'=>'200','data'=>$resp);
        }
        else
        {
            $result = array('status'=>'error','message' =>"No information found",'responsecode'=>'500','data'=>"");
        }

        $this->response($result); 

    }
    
    function user_contestList_post(){
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        
        $resp  = $this->Webservice_model->user_contestList($request);
        if(!empty($resp))
        {
            $result = array('status'=>'success','message' =>"User Contest create",'responsecode'=>'200','data'=>$resp);
        }
        else
        {
            $result = array('status'=>'error','message' =>"No information found",'responsecode'=>'500','data'=>"");
        }

        $this->response($result); 

    }
    
    
    function update_user_profile_image_post(){
        
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        $dd = date('YmdHis');
            $base=$request['profile_image'];
            $value=time();
            $UserImage = 'UserImage'.$dd.'.jpg';
            $filename = 'uploads/user/'.$UserImage;
            $binary=base64_decode($base);
            //$file = fopen('uploads/user/'.$filename, 'w');
            $status = file_put_contents($filename,$binary);
            // Create File
            //fwrite($file, $binary);
            //fclose($file);
            if($status){
            $user_id = $request['user_id'];
                $data = array(
                    'image'=>$UserImage
            );
            
            $resp = $this->Webservice_model->update_profile_image($data,$user_id);
             if(!empty($resp))
            {
                $result = array('status'=>'success','message' =>"Profile Updated Successfully",'responsecode'=>'200','data'=>"$UserImage");
            }
            else
            {
                $result = array('status'=>'error','message' =>"Please try again",'responsecode'=>'500','data'=>"");
            }
            }else{
                $result = array('status'=>'error','message' =>"Image Not Saved",'responsecode'=>'500','data'=>"");
            }

            $this->response($result); 
    }
    
    
    public function notify_get()
    {
        $time = date('Y-m-d H:i:s');
        $this->db->select('time');
        $this->db->where('time >=', $time);
        $this->db->where('match_status','Fixture');
        $this->db->where('time !=','0000-00-00 00:00:00');
        $matches = $this->db->get('match')->result_array();
        foreach($matches as $matche)
        {
            $t2 = $matche['time'];
            $res = "";
            $seconds = strtotime($t2) - strtotime(date('Y-m-d H:i:s'));
            $res = $seconds = floor(($seconds + ($days * 86400) + ($hours * 3600) + ($minutes*60)));
            
            if($res == '1800' )
            {
                $devices = $this->db->get_where('registration')->result();             
                foreach($devices as $device)
                {
                    $token = $device->mobiletoken; 
                    if($token !="")
                    {
                        $suc = $this->send_notification("Match will be start in 30 Minute","Join contest before match start",$token); 
                    } 
                }
            }
        }
                    
    }
    
    public function send_notification($message,$title,$token)
   {
       // API access key from Google API's Console
            define( 'API_ACCESS_KEY', 'AAAA1Yx65HE:APA91bFCWHH4IPw_HKwvREwK5OEyTwsxAqOxqK8cIhVxV2yKwHqEKWvyhbfEzJcysAluckI61FFtnV0Wm9eNeVp_-RMQ9VOexaXe5-qvxFw_p3_LQWlIEcIWIZqbwcWh7k_FKZPPBeEY' );
            $registrationIds = array( $token );
            // prep the bundle
            $msg = array
            (
                'message'   => $message,
                'title'     => $title,
                'subtitle'  => $title,
                'tickerText'    => 'Ticker text here.',
                'vibrate'   => 1,
                'sound'     => 1,
                'largeIcon' => 'large_icon',
                'smallIcon' => 'small_icon'
            );
            $fields = array
            (
                'registration_ids'  => $registrationIds,
                'data'          => $msg
            );
             
            $headers = array
            (
                'Authorization: key=' . API_ACCESS_KEY,
                'Content-Type: application/json'
            );
             
            $ch = curl_init();
            curl_setopt( $ch,CURLOPT_URL, 'https://fcm.googleapis.com/fcm/send' );
            curl_setopt( $ch,CURLOPT_POST, true );
            curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
            curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
            curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
            curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );
            $result = curl_exec($ch );
            curl_close( $ch );
            return true;
   }



   
   
    public function CashFree_token_post()
   {
       $amount =  $this->input->post('amount');
       $orderId = $this->input->post('orderId');
 
         $mode = "PROD";
        if($mode == 'PROD'){
            $host = "https://api.cashfree.com";
            $appId = "59676a5fa054f24d46ce7dd2167695";
            $secret = "675358633cc69e26cf96a83da470a73733ece256";
        } else {
            $host = "https://test.cashfree.com";
            $appId = "59676a5fa054f24d46ce7dd2167695";
            $secret = "675358633cc69e26cf96a83da470a73733ece256";
        }
        $url = $host . "/api/v2/cftoken/order";
        
        // Get cURL resource
        $curl = curl_init();
        // Set some options - we are passing in a useragent too here
        curl_setopt($curl, CURLOPT_HTTPHEADER, array(
            'x-client-id: ' . $appId,
            'x-client-secret: ' . $secret,
            'Content-Type:application/json'
        ));
        curl_setopt_array($curl, array(
            CURLOPT_RETURNTRANSFER => 1,
            CURLOPT_URL => $url,
            CURLOPT_POST => 1,
            CURLOPT_POSTFIELDS => json_encode (array(
                'orderId' =>  $orderId,
                'orderAmount' => $amount,
                'orderCurrency' => 'INR',
            ))
        ));
        // Send the request & save response to $resp
        $resp = curl_exec($curl);
        // Close request to clear up some resources
        curl_close($curl);
        $cf_response = json_decode($resp, true);
        $token = $cf_response["cftoken"];
        $response = array("token" => $token, "orderId" => $orderId);
        $result = array('status'=>'success','message' =>"cashfree token",'responsecode'=>'200','data'=>$response);
        
        $this->response($result); 
    
    }
    
    function match_playing_eleven_get()
    {        
        $matchs = $this->Webservice_model->get_match_less_than_one_hour();       
        foreach($matchs as $match)
        {
            $m_id = $match['unique_id'];
            $e_out = $match['eleven_out'];
            
            $api_url = "https://cricket.sportmonks.com/api/v2.0/fixtures/".$m_id."?api_token=".$this->apikey."&include=lineup";
            
            $ch = curl_init();
            curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
            curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
            curl_setopt($ch, CURLOPT_URL,$api_url);
            $result=curl_exec($ch);
            curl_close($ch);
            $result_responce = json_decode(json_encode(json_decode($result)), True);
             
            $playing11 = $result_responce['data']['lineup'];
            
            if( isset($playing11) && count($playing11) > 0 ) { 
                $this->Webservice_model->update_match_play_eleven($m_id);
                foreach($playing11 as $team)
                { 
                    $p_id =  $this->Webservice_model->get_match_player_id($m_id,$team['id']);
                    $p_elleven = array('playing_status'=>'1');
                    $this->db->where('id',$p_id['m_p_id']);
                    $this->db->update('match_players',$p_elleven);
                }
                
                if($e_out == "0")
                {
                    $title = $match['title'];
                    $this->sendLineupPushNotification($title);
                }
            }
       }
    }
   
    function match_cancelled_get()
    {
        $today_matches = $this->Webservice_model->get_today_match();

        foreach ($today_matches as $today_matche) 
        {
            $api_url = "https://cricket.sportmonks.com/api/v2.0/fixtures/".$today_matche['unique_id']."?api_token=".$this->apikey."";

            $ch = curl_init();
            curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
            curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
            curl_setopt($ch, CURLOPT_URL,$api_url);
            $result=curl_exec($ch);
            curl_close($ch);
            $result_responce = json_decode(json_encode(json_decode($result)), True);
            
            $MStatus = $result_responce['data']['status'];
            
            $FinalStatus = array("Postp", "Cancl", "Aban.");
                
            if(in_array($MStatus, $FinalStatus))
            {    
               $response = $this->Webservice_model->get_match_id_by_unique_id($today_matche['unique_id']);
               
                if($response !="")
                {
                    $status = $result_responce['data']['note'];
                    $data = array('match_status'=>'Result','match_status_note'=>$status,'cancelled'=>'1');
                   
                    $this->Webservice_model->update_match_status_by_unique_id($response->match_id, $data);
                  
                    $dataone = array('match_status'=>'Result','cancelled'=>'1');
                  
                    $this->Webservice_model->update_match_status_by_unique_id_user($response->match_id, $dataone);
                } 
            }
        }         
    }

    
    function my_match_record_post()
    {
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        $liveCount = $this->Webservice_model->my_match_record($request,"Live",'5');
        $lCount = count($liveCount);
        if($lCount < 5)
        {
            $final_count = 5 ;
            $finCount = $final_count - $lCount;
            $fixtureCount = $this->Webservice_model->my_match_record($request,"Fixture",$finCount);
            $fCount = count($fixtureCount);
            $mainCount = $lCount + $fCount;
            if($mainCount < 5)
            {
                $resCount = $final_count -$mainCount;
                $resultCount = $this->Webservice_model->my_match_record($request,"Result",$resCount);
                $rCount = count($resultCount);
                if($rCount > 0)
                {
                    $array = array_merge($liveCount,$fixtureCount,$resultCount);
                    $resp = $array;                   
                } 
                else
                {
                    $array = array_merge($liveCount,$fixtureCount);
                    $resp =  $array;   
                }
                
            }   
            else
            {
                $array = array_merge($liveCount,$fixtureCount);
                $resp =  $array;               
            } 
        }   
        else
        {             
            $resp =   $liveCount;
        } 

        $resp = json_decode(json_encode($resp), FALSE);
       
        foreach ($resp as $key) 
        { 

        $key->credit_type ="";
        $key->bonus_type ="";
        $key->winning_type ="";    
        $contest_count = $this->Webservice_model->contest_count($key->match_id,$key->user_id);
        
        $team_count = $this->Webservice_model->team_count($key->match_id,$key->user_id);
        $key->contest_count = $contest_count;
        $key->team_count = $team_count;
        $team1 = $this->Webservice_model->team_name($key->teamid1);
        $key->team_name1 = $team1->team_name;
        $key->team_image1 = $team1->team_image;
        $key->team_short_name1 =$team1->team_short_name;

        $team2 = $this->Webservice_model->team_name($key->teamid2);
        $key->team_name2 = $team2->team_name;
        $key->team_image2 = $team2->team_image;
        $key->team_short_name2 =$team2->team_short_name;

        
        $match_result = $this->Webservice_model->match_score($key->match_id);
        $key->league_name  = $match_result->league_name;

        if($key->match_status == "Fixture")
        {
            $t2 = $key->match_date_time;

            $res = "";
            $seconds = strtotime($t2) - strtotime(date('Y-m-d H:i:s'));
            $res = $seconds = floor(($seconds + ($days * 86400) + ($hours * 3600) + ($minutes*60)));
            $key->time = $res;

            if($res <= 0)
                {
                    $mat_id = $key->match_id;               
                    $data = array('time' =>"0000-00-00 00:00:00",
                                'match_status' =>'Live',
                                'matchStarted'=>'1'
                     );
                $this->Webservice_model->update_match_status_by_unique_id($mat_id,$data);
                $this->Webservice_model->update_match_status_by_unique_id_user($mat_id,$data);
                }
            
        }   
        else
        {
            $key->time = 00;
        }

        $U_id = $key->unique_id;
        
        if($key->match_status == "Live")    
        {
           
            if($match_result->team1Score =="")
            {
                $key->team1Score = "";    
            }
            else
            {
                $key->team1Score = $match_result->team1Score;
            }
            if($match_result->team1Over =="")
            {
                $key->team1Over = "";    
            }
            else
            {
                $key->team1Over = $match_result->team1Over;
            }
            if($match_result->team2Score =="")
            {
                $key->team2Score = "";    
            }
            else
            {
                $key->team2Score = $match_result->team2Score;
            }
            if($match_result->team2Over =="")
            {
                $key->team2Over = "";    
            }
            else
            {
                $key->team2Over = $match_result->team2Over;
            }
            if($match_result->team1Score_secondInning =="")
            {
                $key->team1Score_secondInning = "";    
            }
            else
            {
                $key->team1Score_secondInning = $match_result->team1Score_secondInning;
            }
            if($match_result->team1Over_secondInning =="")
            {
                $key->team1Over_secondInning = "";    
            }
            else
            {
                $key->team1Over_secondInning = $match_result->team1Over_secondInning;
            }
            if($match_result->team2Score_secondInning =="")
            {
                $key->team2Score_secondInning = "";    
            }
            else
            {
                $key->team2Score_secondInning = $match_result->team2Score_secondInning;
            }
            if($match_result->team2Over_secondInning =="")
            {
                $key->team2Over_secondInning = "";    
            }
            else
            {
                $key->team2Over_secondInning = $match_result->team2Over_secondInning;
            }
            
            $key->match_status_note = "";
        }   

        if($key->match_status == "Result")
        {
            if($match_result->team1Score =="")
            {
                $key->team1Score = "";    
            }
            else
            {
                $key->team1Score = $match_result->team1Score;
            }
            if($match_result->team1Over =="")
            {
                $key->team1Over = "";    
            }
            else
            {
                $key->team1Over = $match_result->team1Over;
            }
            if($match_result->team2Score =="")
            {
                $key->team2Score = "";    
            }
            else
            {
                $key->team2Score = $match_result->team2Score;
            }
            if($match_result->team2Over =="")
            {
                $key->team2Over = "";    
            }
            else
            {
                $key->team2Over = $match_result->team2Over;
            }
            if($match_result->team1Score_secondInning =="")
            {
                $key->team1Score_secondInning = "";    
            }
            else
            {
                $key->team1Score_secondInning = $match_result->team1Score_secondInning;
            }
            if($match_result->team1Over_secondInning =="")
            {
                $key->team1Over_secondInning = "";    
            }
            else
            {
                $key->team1Over_secondInning = $match_result->team1Over_secondInning;
            }
            if($match_result->team2Score_secondInning =="")
            {
                $key->team2Score_secondInning = "";    
            }
            else
            {
                $key->team2Score_secondInning = $match_result->team2Score_secondInning;
            }
            if($match_result->team2Over_secondInning =="")
            {
                $key->team2Over_secondInning = "";    
            }
            else
            {
                $key->team2Over_secondInning = $match_result->team2Over_secondInning;
            }
            $key->match_status_note = $match_result->match_status_note;
        }    
        
        }

        $response = $this->Webservice_model->get_fixture_matchs();

               
                if(count($response) >0){
                foreach ($response as $keydata) { 
                   
                    $team1 = $this->Webservice_model->team_name($keydata->teamid1);
                    $keydata->team_name1 = $team1->team_name;
                    $keydata->team_image1 = $team1->team_image;
                    $keydata->team_short_name1 = $team1->team_short_name;
                    $team2 = $this->Webservice_model->team_name($keydata->teamid2);
                    $keydata->team_name2 = $team2->team_name;
                    $keydata->team_image2 = $team2->team_image;
                    $keydata->team_short_name2 = $team2->team_short_name;
                
                    if($keydata->match_status == "Fixture")
                    {
                        $keydata->team1Score = "";
                        $keydata->team1Over = "";
                        $keydata->team2Score = "";
                        $keydata->team2Over = "";
                        $keydata->team1Score_secondInning = "";
                        $keydata->team1Over_secondInning = "";
                        $keydata->team2Score_secondInning = "";
                        $keydata->team2Over_secondInning = "";
                        $keydata->match_status_note = "";
                        $keydata->toss_winner_team = "";
                        $keydata->winner_team = "";
                        $t2 = $keydata->match_date_time;

                        $res = "";
                        $seconds = strtotime($t2) - strtotime(date('Y-m-d H:i:s'));
                        $res = $seconds = floor(($seconds + ($days * 86400) + ($hours * 3600) + ($minutes*60)));
                        $keydata->time = $res;

                        if($res <= 0)
                        {
                            $mat_id = $keydata->match_id;
                            
                            $data = array('time' =>"0000-00-00 00:00:00",
                                        'match_status' =>'Live',
                                        'matchStarted'=>'1'
                             );
                            $this->Webservice_model->update_match_status_by_unique_id($mat_id,$data);
                            $this->Webservice_model->update_match_status_by_unique_id_user($mat_id,$data);
                        }                 
                    }
                }
            }
        if(count($response) > 0)
        {
            $response = $response;
        }    
        else
        {
            $fixtureMatch = "";
        }  
        if(count($resp) > 0)
        {
            $resp = $resp;
        }    
        else
        {
            $resp = array();
        }    
        
        $result = array('status'=>'success','message' =>" data found successfully",'responsecode'=>'200', 'data'=>array('fixtureMatch'=>$response,"myMatch"=>$resp));
        
        
        $this->response($result);  
    }
    
    function sendLineupPushNotification($title) 
    {   
        $apiKey = "AAAAVwc3hT8:APA91bFyfkp72M68LJogNNxy_gA5xIU6e9LliEDsG9gmcXI8lbFPqVR9bFbw-xO62ZFir8XrKMGYoT1KCVB9FIrjVeq1LEcpsPq_88mDjImptAz2p0NN6KFukKXnESDgoKTpmV7ME_Jx";
        $message =  "Hurry up! , Line up out for the match join soon.";
        
        $post = array(
                        'to' => '/topics/all',
                        'data' => array (
                                'title' => $title,
                                'message' => $message
                        )
                     );
                     
       
    
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
    }
    
    function update_match_score_get()
    {
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        $p['status'] = "Live";
        $resp = $this->Webservice_model->match_record($p);
        if(count($resp) >0)
        {
            foreach ($resp as $key) 
            {   

                if($key->match_status == "Live")
                {
                    $re['matchid'] = $match_id = $key->match_id;
                    $key->match_status_note ="";
                    $api_url  = "https://cricket.sportmonks.com/api/v2.0/fixtures/".$key->unique_id."?api_token=".$this->apikey."&include=runs";
    
                    $ch = curl_init();
                    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
                    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
                    curl_setopt($ch, CURLOPT_URL,$api_url);
                    $result=curl_exec($ch);
                    curl_close($ch);
                    $result_responce = json_decode(json_encode(json_decode($result)), True);
                    
                    $innings_count = count($result_responce['data']['runs']);
                   
                    $R1 = $result_responce['data']['runs'][0];
                    $R2 = $result_responce['data']['runs'][1];
                    $R3 = $result_responce['data']['runs'][2];
                    $R4 = $result_responce['data']['runs'][3];
                    
                    if($innings_count > 0)
                {
                    $this->update_leaderboard_user($re);
                    foreach ($result_responce['data']['runs'] as $innings) 
                    {
                        if($innings_count == "4")
                        {
                            $team_id_main = $this->Webservice_model->get_team_unique_id($innings['team_id']);
                            $get_id_by_match = $this->Webservice_model->get_team_id_in_match_table($team_id_main,$match_id);

                            if($get_id_by_match !="")
                            {
                                $T1 = $innings['score'].'/'.$innings['wickets'];
                                $O1 = $innings['overs'];

                                $save_score = array('team1Score_secondInning' =>$T1,'team1Over_secondInning'=>$O1);
                            }   
                            else
                            {
                                $T1 = $innings['score'].'/'.$innings['wickets'];
                                $O1 = $innings['overs'];
                                $save_score = array('team2Score_secondInning' =>$T1,'team2Over_secondInning'=>$O1);
                            }
                            $this->db->where('unique_id',$key->unique_id);
                            $this->db->update('match',$save_score);                           
                        }
                        else if($innings_count == "3")
                        {
                            $team_id_main = $this->Webservice_model->get_team_unique_id($innings['team_id']);
                            $get_id_by_match = $this->Webservice_model->get_team_id_in_match_table($team_id_main,$match_id);

                            if($get_id_by_match !="")
                            {
                                $T1 = $innings['score'].'/'.$innings['wickets'];
                                $O1 = $innings['overs'];

                                $save_score = array('team1Score_secondInning' =>$T1,'team1Over_secondInning'=>$O1);
                            }   
                            else
                            {
                                $T1 = $innings['score'].'/'.$innings['wickets'];
                                $O1 = $innings['overs'];
                                $save_score = array('team2Score_secondInning' =>$T1,'team2Over_secondInning'=>$O1);
                            }
                            $this->db->where('unique_id',$key->unique_id);
                            $this->db->update('match',$save_score);                           
                        }
                        else if($innings_count == "2")
                        {
                            $team_id_main = $this->Webservice_model->get_team_unique_id($innings['team_id']);
                            $get_id_by_match = $this->Webservice_model->get_team_id_in_match_table($team_id_main,$match_id);

                            if($get_id_by_match !="")
                            {
                                $T1 = $innings['score'].'/'.$innings['wickets'];
                                $O1 = $innings['overs'];

                                $save_score = array('team1Score' =>$T1,'team1Over'=>$O1);
                            }   
                            else
                            {
                                $T1 = $innings['score'].'/'.$innings['wickets'];
                                $O1 = $innings['overs'];
                                $save_score = array('team2Score' =>$T1,'team2Over'=>$O1);
                            }
                            $this->db->where('unique_id',$key->unique_id);
                            $this->db->update('match',$save_score);                           
                        }
                        else if($innings_count == "1")
                        {
                            $team_id_main = $this->Webservice_model->get_team_unique_id($innings['team_id']);
                            $get_id_by_match = $this->Webservice_model->get_team_id_in_match_table($team_id_main,$match_id);

                            if($get_id_by_match !="")
                            {
                                $T1 = $innings['score'].'/'.$innings['wickets'];
                                $O1 = $innings['overs'];

                                $save_score = array('team1Score' =>$T1,'team1Over'=>$O1);
                            }   
                            else
                            {
                                $T1 = $innings['score'].'/'.$innings['wickets'];
                                $O1 = $innings['overs'];
                                $save_score = array('team2Score' =>$T1,'team2Over'=>$O1);
                            }
                            $this->db->where('unique_id',$key->unique_id);
                            $this->db->update('match',$save_score);                           
                        }    
                    }
                }
                }
            }
        }
    }
    
    function update_score_points_final_after_match_complete($m_id)
    {
        $id = $this->Webservice_model->get_match($m_id);

        $api_url = "https://cricket.sportmonks.com/api/v2.0/fixtures/".$id->unique_id."?api_token=".$this->apikey."&include=batting,bowling";

        $ch = curl_init();
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_URL,$api_url);
        $result=curl_exec($ch);
        curl_close($ch);
        $resp = json_decode(json_encode(json_decode($result)), True);
      
        if($id->type =="ODI")
        {   
            $playing_eleven = $this->Webservice_model->playing_eleven("1");
            $single = $this->Webservice_model->playing_eleven("2");
            $duck = $this->Webservice_model->playing_eleven("8");
            $six = $this->Webservice_model->playing_eleven("10");
            $four = $this->Webservice_model->playing_eleven("9");   
            $fifty = $this->Webservice_model->playing_eleven("11"); 
            $hundred = $this->Webservice_model->playing_eleven("12");
            $between50_60 = $this->Webservice_model->playing_eleven("33");
            $between40_50 = $this->Webservice_model->playing_eleven("34");
            $below40 = $this->Webservice_model->playing_eleven("35");
            
            foreach ($resp['data']['batting'] as $player) 
            {
                $single_runs = $single->odiscore;
                $four_runs = $four->odiscore;
                $six_runs = $six->odiscore;

                $six_hits = $player['six_x'];
                $four_hits = $player['four_x'];
                $run_hits = $player['score'];
                $strike_rate = $player['rate'];

                $single_hits = $run_hits ;//- $four_hits*4 - $six_hits*6;   

                if($single_hits =="0")
                {
                    $playerinfo = $this->Webservice_model->player_info($player['player_id']);
                    if($playerinfo->designationid !="2")
                    {
                        $total_score = $playing_eleven->odiscore + $duck->odiscore;
                            $player_id = $playerinfo->id; 

                        $this->Webservice_model->update_playing_eleven_score($total_score,$player_id,$id->match_id);

                    } 
                }   
                else
                {
                    if($strike_rate <60 && $strike_rate >50)
                    {
                        $s_rate = $between50_60->odiscore;
                    }
                    else if($strike_rate <40.99 && $strike_rate >40)
                    {
                        $s_rate = $between40_50->odiscore;
                    }
                    else if($strike_rate <40)
                    {
                        $s_rate = $below40->odiscore;
                    }

                    if($single_hits >=50 && $single_hits <100)
                    {
                        $total_score = $single_runs*$single_hits + $four_runs*$four_hits + $six_runs*$six_hits + $playing_eleven->odiscore + $fifty->odiscore +$s_rate;
                    }
                    else if($single_hits >=100)
                    {
                        $total_score = $single_runs*$single_hits + $four_runs*$four_hits + $six_runs*$six_hits + $playing_eleven->odiscore + $hundred->odiscore +$s_rate;
                    }
                    else
                    {
                        $total_score = $single_runs*$single_hits + $four_runs*$four_hits + $six_runs*$six_hits + $playing_eleven->odiscore +$s_rate;
                    }

                    $playerinfo = $this->Webservice_model->player_info($player['player_id']);
                    $player_id = $playerinfo->id;  

                    $this->Webservice_model->update_playing_eleven_score($total_score,$player_id,$id->match_id); 
                    }                        
            }

            $wicket = $this->Webservice_model->playing_eleven("3");

            $maiden_over = $this->Webservice_model->playing_eleven("13");

            $four_wicket = $this->Webservice_model->playing_eleven("14");
            $five_wicket =$this->Webservice_model->playing_eleven("15");

            $between45_35 =$this->Webservice_model->playing_eleven("23");
            $between349_25 =$this->Webservice_model->playing_eleven("24");
            $below25 =$this->Webservice_model->playing_eleven("25");
            $between7_8 =$this->Webservice_model->playing_eleven("26");
            $between801_9 =$this->Webservice_model->playing_eleven("27");
            $above9 =$this->Webservice_model->playing_eleven("28");

            foreach ($resp['data']['bowling'] as $bowling) 
            {
                $Maiden  = $bowling['medians'];
                $wickets = $bowling['wickets'];
                $Economic = $bowling['rate'];
                
                $maiden_over_bolled = $Maiden*$maiden_over->odiscore;

                $wickets_taken = $wickets*$wicket->odiscore;

                if($wickets =="4")
                {
                    $wickets_taken_four = $four_wicket->odiscore;
                }
                else
                {
                    $wickets_taken_four = 0;
                }
                if($wickets >="5")
                {
                    $wickets_taken_five = $five_wicket->odiscore;
                }
                else
                {
                    $wickets_taken_five = 0;
                }

                if($Economic <4.5 && $Economic >3.5)
                {
                    $economic_rate = $between45_35->odiscore;
                }

                else if($Economic <3.49 && $Economic >2.5)
                {
                    $economic_rate = $between349_25->odiscore;
                }

                else if($Economic <2.5)
                {
                    $economic_rate = $below25->odiscore;
                }   
                else if($Economic <8 && $Economic >7)
                {
                    $economic_rate = $between7_8->odiscore;
                }

                else if($Economic <= 9 && $Economic >8.01)
                {
                    $economic_rate = $between801_9->odiscore;
                }
                else if($Economic >9)
                {
                    $economic_rate = $above9->odiscore;
                }
                else if($Economic >4.5 && $Economic < 7)
                {
                    $economic_rate = "0";
                }   

                $bolling_score = $maiden_over_bolled+$wickets_taken+$wickets_taken_four+$wickets_taken_five+$economic_rate;
                $playerinfo = $this->Webservice_model->player_info($bowling['player_id']);
                $player_id = $playerinfo->id;  

                $this->Webservice_model->update_playing_eleven_bolling_score($bolling_score,$player_id,$id->match_id);  
            }   

            $catch_points = $this->Webservice_model->playing_eleven("4");

            foreach ($resp['data']['batting'] as $key ) {
                if($key['catch_stump_player_id'] !="")
                {
                   $fielding[] = $key['catch_stump_player_id'];
                }    
            }
            
            $fieldings = array_count_values($fielding);
            
            foreach ($fieldings as $key => $value) {

                $fielding_score = $value*$catch_points->odiscore;

                $playerinfo = $this->Webservice_model->player_info($key);
                $player_id = $playerinfo->id;  

                $this->Webservice_model->update_playing_eleven_fielding_score($fielding_score,$player_id,$id->match_id); 
            }
        }

        else if($id->type =="T10")
        {   
            $playing_eleven = $this->Webservice_model->playing_eleven("1");

            $single = $this->Webservice_model->playing_eleven("2");
            $duck = $this->Webservice_model->playing_eleven("8");   
            $four = $this->Webservice_model->playing_eleven("9");   
            $six = $this->Webservice_model->playing_eleven("10");
            $fifty = $this->Webservice_model->playing_eleven("11"); 
            
            $morethan30 = $this->Webservice_model->playing_eleven("38");
            $between90_100 = $this->Webservice_model->playing_eleven("45");
            $between80_90 = $this->Webservice_model->playing_eleven("46");
            $below80 = $this->Webservice_model->playing_eleven("47");

            foreach ($resp['data']['batting'] as $player) 
            {
                $single_runs = $single->t10score;
                $four_runs = $four->t10score;
                $six_runs = $six->t10score;

                $six_hits = $player['six_x'];
                $four_hits = $player['four_x'];
                $run_hits = $player['score'];
                $strike_rate = $player['rate'];
                $single_hits = $run_hits;

                $playerinfo = $this->Webservice_model->player_info($player['player_id']);
                $player_id = $playerinfo->id;

                if($single_hits =="0")
                {
                    if($playerinfo->designationid !="2")
                    {
                        $total_score = $playing_eleven->t10score + $duck->t10score;
                        $this->Webservice_model->update_playing_eleven_score($total_score,$player_id,$id->match_id);

                    }                           
                }
                else
                {
                    if($strike_rate <90 && $strike_rate >100)
                    {
                        $s_rate = $between90_100->t10score;
                    }
                    else if($strike_rate <89.99 && $strike_rate >80)
                    {
                        $s_rate = $between80_90->t10score;
                    }
                    else if($strike_rate <80)
                    {
                        $s_rate = $below80->t10score;
                    }
                    else
                    {
                        $s_rate = 0;
                    }   

                    if($single_hits >=50 && $single_hits <100)
                    {
                        $total_score = $single_runs*$single_hits + $four_runs*$four_hits + $six_runs*$six_hits + $playing_eleven->t10score + $morethan30 ->t10score + $fifty->t10score +$s_rate;
                    }
                    else if($single_hits >=30 && $single_hits <50)
                    {
                        $total_score = $single_runs*$single_hits + $four_runs*$four_hits + $six_runs*$six_hits + $playing_eleven->t10score + $morethan30 ->t10score +$s_rate;
                    }
                    else
                    {
                        $total_score = $single_runs*$single_hits + $four_runs*$four_hits + $six_runs*$six_hits + $playing_eleven->t10score +$s_rate;
                    }   

                    $this->Webservice_model->update_playing_eleven_score($total_score,$player_id,$id->match_id);
                    
                }                         
            }

            $wicket = $this->Webservice_model->playing_eleven("3");
            $maiden_over = $this->Webservice_model->playing_eleven("13");
            $two_wicket = $this->Webservice_model->playing_eleven("36");
            $three_wicket =$this->Webservice_model->playing_eleven("37");
            $below6 =$this->Webservice_model->playing_eleven("39");
            $between6_7 =$this->Webservice_model->playing_eleven("40");
            $between7_8 =$this->Webservice_model->playing_eleven("41");
            $between11_12 =$this->Webservice_model->playing_eleven("42");
            $between12_13 =$this->Webservice_model->playing_eleven("43");
            $above13 =$this->Webservice_model->playing_eleven("44");
            
            foreach ($resp['data']['bowling'] as $bowling) 
            {
                $Maiden  = $bowling['medians'];
                $wickets = $bowling['wickets'];
                $Economic = $bowling['rate'];

                $maiden_over_bolled = $Maiden*$maiden_over->t10score;

                $wickets_taken = $wickets*$wicket->t10score;

                if($wickets =="2")
                {
                    $wickets_taken_two = $two_wicket->t10score;
                }
                else
                {
                    $wickets_taken_two = 0;
                }
                if($wickets >="3")
                {
                    $wickets_taken_three = $three_wicket->t10score;
                }
                else
                {
                    $wickets_taken_three = 0;
                }

                if($Economic <6 )
                {
                    $economic_rate = $below6->t10score;
                }

                else if($Economic <7 && $Economic >6)
                {
                    $economic_rate = $between6_7->t10score;
                }
                else if($Economic <8 && $Economic >7)
                {
                    $economic_rate = $between7_8->t10score;
                }
                else if($Economic <12 && $Economic >11)
                {
                    $economic_rate = $between11_12->t10score;
                }
                else if($Economic <13 && $Economic >12)
                {
                    $economic_rate = $between12_13->t10score;
                }
                else if($Economic >13)
                {
                    $economic_rate = $above13->t10score;
                }

                $bolling_score = $maiden_over_bolled+$wickets_taken+$wickets_taken_two+$wickets_taken_three+$economic_rate;

                $playerinfo = $this->Webservice_model->player_info($bowling['player_id']);
                $player_id = $playerinfo->id;  

                $this->Webservice_model->update_playing_eleven_bolling_score($bolling_score,$player_id,$id->match_id);  
            }

            $catch_points = $this->Webservice_model->playing_eleven("4");

            foreach ($resp['data']['batting'] as $key ) {
                if($key['catch_stump_player_id'] !="")
                {
                   $fielding[] = $key['catch_stump_player_id'];
                }    
            }
            
            $fieldings = array_count_values($fielding);
            foreach ($fieldings as $key => $value) {

                $fielding_score = $value*$catch_points->t10score;

                $playerinfo = $this->Webservice_model->player_info($key);
                $player_id = $playerinfo->id;  

                $this->Webservice_model->update_playing_eleven_fielding_score($fielding_score,$player_id,$id->match_id); 
            }
        }

        else if($id->type =="Twenty20" or $id->type =="T20" or $id->type =="Woman T20" or $id->type =="T20I")
        {  
            $playing_eleven = $this->Webservice_model->playing_eleven("1");

            $single = $this->Webservice_model->playing_eleven("2");
            $six = $this->Webservice_model->playing_eleven("10");
            $duck = $this->Webservice_model->playing_eleven("8");   
            $four = $this->Webservice_model->playing_eleven("9");   
            $fifty = $this->Webservice_model->playing_eleven("11"); 
            $hundred = $this->Webservice_model->playing_eleven("12");   

            $between60_70 = $this->Webservice_model->playing_eleven("30");
            $between59_60 = $this->Webservice_model->playing_eleven("31");
            $below50 = $this->Webservice_model->playing_eleven("32");

            foreach ($resp['data']['batting'] as $player)
            {
                $single_runs = $single->t20score;
                $four_runs = $four->t20score;
                $six_runs = $six->t20score;

                $six_hits = $player['six_x'];
                $four_hits = $player['four_x'];
                $run_hits = $player['score'];
                $strike_rate = $player['rate'];
                $single_hits = $run_hits;// - $four_hits*4 - $six_hits*6;

                $playerinfo = $this->Webservice_model->player_info($player['player_id']);
                $player_id = $playerinfo->id;

                    if($single_hits =="0")
                    {
                        if($playerinfo->designationid !="2")
                        {
                            $total_score = $playing_eleven->t20score + $duck->t20score;
                            $this->Webservice_model->update_playing_eleven_score($total_score,$player_id,$id->match_id);

                        }                           
                    }
                    else
                    {
                        if($strike_rate <70 && $strike_rate >60)
                        {
                            $s_rate = $between60_70->t20score;
                        }
                        else if($strike_rate <59.99 && $strike_rate >50)
                        {
                            $s_rate = $between59_60->t20score;
                        }
                        else if($strike_rate <50)
                        {
                            $s_rate = $below50->t20score;
                        }
                        else
                        {
                            $s_rate = 0;
                        }   

                        if($single_hits >=50 && $single_hits <100)
                        {
                            $total_score = $single_runs*$single_hits + $four_runs*$four_hits + $six_runs*$six_hits + $playing_eleven->t20score + $fifty->t20score +$s_rate;
                        }
                        else if($single_hits >=100)
                        {
                            $total_score = $single_runs*$single_hits + $four_runs*$four_hits + $six_runs*$six_hits + $playing_eleven->t20score + $hundred->t20score +$s_rate;
                        }   
                        else
                        {
                            $total_score = $single_runs*$single_hits + $four_runs*$four_hits + $six_runs*$six_hits + $playing_eleven->t20score +$s_rate;
                        }   
                        
                        
                        $this->Webservice_model->update_playing_eleven_score($total_score,$player_id,$id->match_id);
                    }
                }                       
            
            $wicket = $this->Webservice_model->playing_eleven("3");
            $maiden_over = $this->Webservice_model->playing_eleven("13");
            $four_wicket = $this->Webservice_model->playing_eleven("14");
            $five_wicket =$this->Webservice_model->playing_eleven("15");
            $between6_5 =$this->Webservice_model->playing_eleven("17");
            $between5_4 =$this->Webservice_model->playing_eleven("18");
            $below4 =$this->Webservice_model->playing_eleven("19");
            $between9_10 =$this->Webservice_model->playing_eleven("20");
            $between10_11 =$this->Webservice_model->playing_eleven("21");
            $above11 =$this->Webservice_model->playing_eleven("22");
            
            foreach ($resp['data']['bowling'] as $bowling) {

                $Maiden  = $bowling['medians'];
                $wickets = $bowling['wickets'];
                $Economic = $bowling['rate'];

                $maiden_over_bolled = $Maiden*$maiden_over->t20score;

                $wickets_taken = $wickets*$wicket->t20score;

                if($wickets =="4")
                {
                    $wickets_taken_four = $four_wicket->t20score;
                }
                else
                {
                    $wickets_taken_four = 0;
                }
                if($wickets >="5")
                {
                    $wickets_taken_five = $five_wicket->t20score;
                }
                else
                {
                    $wickets_taken_five = 0;
                }

                if($Economic <6 && $Economic >5)
                {
                    $economic_rate = $between6_5->t20score;
                }

                else if($Economic <4.99 && $Economic >4)
                {
                    $economic_rate = $between5_4->t20score;
                }

                else if($Economic <4)
                {
                    $economic_rate = $below4->t20score;
                }   
                else if($Economic <10 && $Economic >9)
                {
                    $economic_rate = $between9_10->t20score;
                }

                else if($Economic <11 && $Economic >10.01)
                {
                    $economic_rate = $between10_11->t20score;
                }
                else if($Economic >11)
                {
                    $economic_rate = $above11->t20score;
                }
                else if($Economic > 6 && $Economic <9)
                {
                    $economic_rate = "0";
                }   

                $bolling_score = $maiden_over_bolled+$wickets_taken+$wickets_taken_four+$wickets_taken_five+$economic_rate;

                $playerinfo = $this->Webservice_model->player_info($bowling['player_id']);
                $player_id = $playerinfo->id;  

                $this->Webservice_model->update_playing_eleven_bolling_score($bolling_score,$player_id,$id->match_id);  
            }
            
            $catch_points = $this->Webservice_model->playing_eleven("4");

            foreach ($resp['data']['batting'] as $key ) {
                if($key['catch_stump_player_id'] !="")
                {
                   $fielding[] = $key['catch_stump_player_id'];
                }    
            }
            
            $fieldings = array_count_values($fielding);
            
            foreach ($fieldings as $key => $value) {

                $fielding_score = $value*$catch_points->t20score;

                $playerinfo = $this->Webservice_model->player_info($key);
                $player_id = $playerinfo->id;  

                $this->Webservice_model->update_playing_eleven_fielding_score($fielding_score,$player_id,$id->match_id); 
            }
        }

        else if($id->type =="Test")
        {
            $playing_eleven = $this->Webservice_model->playing_eleven("1");
            $single = $this->Webservice_model->playing_eleven("2");
            $wicket = $this->Webservice_model->playing_eleven("3");
            $catch_points = $this->Webservice_model->playing_eleven("4");
            $six = $this->Webservice_model->playing_eleven("10");
            $duck = $this->Webservice_model->playing_eleven("8");   
            $four = $this->Webservice_model->playing_eleven("9");   
            $fifty = $this->Webservice_model->playing_eleven("11"); 
            $hundred = $this->Webservice_model->playing_eleven("12");   
            $four_wicket = $this->Webservice_model->playing_eleven("14");
            $five_wicket =$this->Webservice_model->playing_eleven("15");

            $single_runs = $single->testscore;
            $four_runs = $four->testscore;
            $six_runs = $six->testscore;
                foreach ($resp['data']['batting'] as $player)
                {
                    if($player['scoreboard'] =="S1" or $player['scoreboard'] =="S2" )
                    {
                        $six_hits = $player['six_x'];
                        $four_hits = $player['four_x'];
                        $run_hits = $player['score'];
                        $single_hits = $run_hits;
                        $playerinfo = $this->Webservice_model->player_info($player['player_id']);
                        $player_id = $playerinfo->id;

                        if($single_hits =="0")
                        {
                            if($playerinfo->designationid !="2")
                            {
                                $total_score = $playing_eleven->testscore + $duck->testscore;
                                
                              $this->Webservice_model->update_playing_eleven_score($total_score,$player_id,$id->match_id);
                            }                           
                        }
                        else
                        {
                            if($single_hits >=50 && $single_hits <100)
                        {
                            $total_score = $single_runs*$single_hits + $four_runs*$four_hits + $six_runs*$six_hits + $playing_eleven->testscore + $fifty->testscore;
                        }
                        else if($single_hits >=100)
                        {
                            $total_score = $single_runs*$single_hits + $four_runs*$four_hits + $six_runs*$six_hits + $playing_eleven->testscore + $hundred->testscore;
                        }   
                        else
                        {
                            $total_score = $single_runs*$single_hits + $four_runs*$four_hits + $six_runs*$six_hits + $playing_eleven->testscore;
                        }       
                        $this->Webservice_model->update_playing_eleven_score($total_score,$player_id,$id->match_id);
                     
                        }   
                    }  
                    
                    else if($player['scoreboard'] =="S3" or $player['scoreboard'] =="S4" )
                    { 
                        $six_hits = $player['six_x'];
                        $four_hits = $player['four_x'];
                        $run_hits = $player['score'];
                        $single_hits = $run_hits;
                        
                        $playerinfo = $this->Webservice_model->player_info($player['player_id']);
                        $player_id = $playerinfo->id;

                        if($single_hits =="0")
                        {
                            if($playerinfo->designationid !="2")
                            {
                                $total_score =$duck->testscore;
                            
                                $this->Webservice_model->update_playing_eleven_score_second_innings($total_score,$player_id,$id->match_id);
                            }                           
                        }
                        else
                        {
                            if($single_hits >=50 && $single_hits <100)
                        {
                            $total_score = $single_runs*$single_hits + $four_runs*$four_hits + $six_runs*$six_hits + $fifty->testscore;
                        }
                        else if($single_hits >=100)
                        {
                            $total_score = $single_runs*$single_hits + $four_runs*$four_hits + $six_runs*$six_hits + $hundred->testscore;
                        }   
                        else
                        {
                            $total_score = $single_runs*$single_hits + $four_runs*$four_hits + $six_runs*$six_hits;
                        }       
                        $this->Webservice_model->update_playing_eleven_score_second_innings($total_score,$player_id,$id->match_id);
                        
                        }   
                        
                    }
                }   
                
                foreach ($resp['data']['bowling'] as $bowling) {
                    if($bowling['scoreboard'] =="S1" or $bowling['scoreboard'] =="S2")
                    {
                    $wickets = $bowling['wickets'];

                    $wickets_taken = $wickets*$wicket->testscore;

                    if($wickets =="4")
                    {
                        $wickets_taken_four = $four_wicket->testscore;
                    }
                    else
                    {
                        $wickets_taken_four = 0;
                    }
                    if($wickets >="5")
                    {
                        $wickets_taken_five = $five_wicket->testscore;
                    }
                    else
                    {
                        $wickets_taken_five = 0;
                    }

                    $bolling_score =$wickets_taken+$wickets_taken_four+$wickets_taken_five;

                    $playerinfo = $this->Webservice_model->player_info($bowling['player_id']);
                    $player_id = $playerinfo->id;  

                    $this->Webservice_model->update_playing_eleven_bolling_score($bolling_score,$player_id,$id->match_id);  
                    }

                    else if($bowling['scoreboard'] =="S3" or $bowling['scoreboard'] =="S4")
                    {
                        $wickets = $bowling['wickets'];

                        $wickets_taken = $wickets*$wicket->testscore;

                        if($wickets =="4")
                        {
                            $wickets_taken_four = $four_wicket->testscore;
                        }
                        else
                        {
                            $wickets_taken_four = 0;
                        }
                        if($wickets >="5")
                        {
                            $wickets_taken_five = $five_wicket->testscore;
                        }
                        else
                        {
                            $wickets_taken_five = 0;
                        }

                        $bolling_score =$wickets_taken+$wickets_taken_four+$wickets_taken_five;

                        $playerinfo = $this->Webservice_model->player_info($bowling['player_id']);
                        $player_id = $playerinfo->id;  

                        $this->Webservice_model->update_playing_eleven_bolling_score_second_innings($bolling_score,$player_id,$id->match_id); 
                    }
                }  
                
                $catch_points = $this->Webservice_model->playing_eleven("4");

                foreach ($resp['data']['batting'] as $key ) {
                    if($key['catch_stump_player_id'] !="")
                    {
                       $fielding[] = $key['catch_stump_player_id'];
                    }    
                }
                
                $fieldings = array_count_values($fielding);
                
                foreach ($fieldings as $key => $value) {

                    $fielding_score = $value*$catch_points->testscore;

                    $playerinfo = $this->Webservice_model->player_info($key);
                    $player_id = $playerinfo->id;  

                    $this->Webservice_model->update_playing_eleven_fielding_score($fielding_score,$player_id,$id->match_id); 
                }             
        }   
                    
    }
    
    //API for popup offer
    function popup_offer_post()
    {
        header('Content-type: application/json');
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata,true); 
        
        $resp = $this->Webservice_model->popup_offer();
        if($resp !="")
        {
            $result = array('status'=>'success','message' =>"Popup Offer found",'responsecode'=>'200' ,'data'=>$resp);
        }
        else
        {
            $result = array('status'=>'error','message' =>"No offer found",'responsecode'=>'500','data'=>"");
        }

        $this->response($result);  
    }
    
}