<?php

if (!defined('BASEPATH'))
    exit('No direct script access allowed');

class Payment extends MY_Controller
{
    function __construct()
    {
        parent::__construct();
        $this->load->model('Match_model');
        $this->load->model('Contest_model');   
        $this->load->model('User_model');    
    }

    
    public function send()
    {
        $c_id = $this->uri->segment('3');
        $id = $this->uri->segment('4');
        $match_id = base64_decode($id);
        $contest_id = base64_decode($c_id);
        $team_count = $this->uri->segment('5');
        $team_Count = base64_decode($team_count);

        $match_status =  $this->Match_model->match_status_check($match_id);
        if($match_status =="Result")
        {
            if(isset($team_Count)  && $team_Count > 0)
            {   
                $leaderboards = $this->Contest_model->get_lBoard($contest_id,$match_id);

                $Contestwinner = $this->Contest_model->get_Contestwinner($contest_id);
                   
                $winning_price = 0;
                $CountArray = 0;
                $ii = 1;
                    
                foreach ($leaderboards as $leaderboard) 
                {
                    $Mainarray = array();
                    if($leaderboard['rank'] <= $Contestwinner)
                    {
                        $count = $this->Contest_model->checkCount($leaderboard['rank'],$contest_id);
                    
                        if($count == 1)
                        {
                            $winning_price = $this->Contest_model->get_winning_price($leaderboard['rank'],$contest_id);
                            if($winning_price !="")
                            {
                                $this->Contest_model->contest_winning_amount_credit($leaderboard['user_id'],$winning_price,$contest_id,$leaderboard['temprank']);
                            }     
                        } 
                        else if($count > 1 )
                        {
                            $Ucount = $this->Contest_model->checkUpperCount($leaderboard['rank'],$contest_id,$leaderboard['temprank']);
                            $Lcount = $this->Contest_model->checkLowerrCount($leaderboard['rank'],$contest_id,$leaderboard['temprank']);

                            if (isset($Ucount) && !empty($Ucount)) {
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

                            $Mainarray= array_merge(array($leaderboard) ,$Ucount,$Lcount); 

                            $CountArray =  count($Mainarray);

                            $winningP = 0;
                            $FPrice = 0;
                            $FinalPrice = 0;
                            foreach ($Mainarray as $key ) {
                                $winningP += $this->Contest_model->get_winning_price($key['temprank'],$contest_id);
                            }
        
                            $FinalPrice = $winningP / $CountArray ;
                          
                            $FPrice = round($FinalPrice,2);

                            if($FPrice !="")
                            {
                                $this->Contest_model->contest_winning_amount_credit($leaderboard['user_id'],$FPrice,$contest_id,$leaderboard['temprank']);
                            }
                        }  
                    }
                    $ii++; 
                }
        
               $data = array('payment_status' =>'1');
               $this->Contest_model->update($contest_id , $data);

                $this->session->set_flashdata('message','Payment transfer complete');
                redirect('old_match/contest_list/'.$match_id);
                   
            }
            else
            {
               $this->session->set_flashdata('message','No record found...');
                redirect('old_match/contest_list/'.$match_id);
            }
        }   
        else
        {
            $this->session->set_flashdata('message','Match is not complete');
            redirect('old_match/contest_list/'.$match_id);
        }   
    }
    

    public function matchPaymentsend()
    {
        $id = $this->uri->segment('3');
        $match_id = base64_decode($id);

        $match_status =  $this->Match_model->match_status_check($match_id);
        if($match_status =="Result")
        {
            $contest_list = $this->Contest_model->get_contest_id_by_match_for_payment($match_id);

            if(isset($contest_list) && !empty($contest_list))
            {
                foreach ($contest_list as $contest) {

                    $contest_id = $contest['contest_id'];
                    $leaderboards = $this->Contest_model->get_lBoard($contest_id,$match_id);

                $Contestwinner = $this->Contest_model->get_Contestwinner($contest_id);
                
                $winning_price = 0;
                $CountArray = 0;
                $ii = 1;
                foreach ($leaderboards as $leaderboard) 
                {
                    $Mainarray = array();
                    if($leaderboard['rank'] <= $Contestwinner)
                    {
                        $count = $this->Contest_model->checkCount($leaderboard['rank'],$contest_id);
                    
                        if($count == 1)
                        {
                            $winning_price = $this->Contest_model->get_winning_price($leaderboard['rank'],$contest_id);
                            if($winning_price !="")
                            {
                                $this->Contest_model->contest_winning_amount_credit($leaderboard['user_id'],$winning_price,$contest_id);
                            }     
                        } 
                        else if($count > 1 )
                        {
                            $Ucount = $this->Contest_model->checkUpperCount($leaderboard['rank'],$contest_id,$leaderboard['temprank']);
                            $Lcount = $this->Contest_model->checkLowerrCount($leaderboard['rank'],$contest_id,$leaderboard['temprank']);

                            if (isset($Ucount) && !empty($Ucount)) {
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

                            $Mainarray= array_merge(array($leaderboard) ,$Ucount,$Lcount); 

                            $CountArray =  count($Mainarray);

                            $winningP = 0;
                            $FPrice = 0;
                            $FinalPrice = 0;
                            foreach ($Mainarray as $key ) {
                                $winningP += $this->Contest_model->get_winning_price($key['temprank'],$contest_id);
                            }
        
                            $FinalPrice = $winningP / $CountArray ;
                          
                            $FPrice = round($FinalPrice,2);

                            if($FPrice !="")
                            {
                                $this->Contest_model->contest_winning_amount_credit($leaderboard['user_id'],$FPrice,$contest_id);
                            }
                        }  
                    }
                    $ii++; 
                }

                $cdata = array('payment_status' =>'1');
                $this->Contest_model->update($contest['contest_id'] , $cdata);
            }


            $data = array('payment_status' =>'1');
            $this->Match_model->update($match_id , $data);

            $this->session->set_flashdata('message','Payment transfer complete');
            redirect('old_match/contest_list/'.$match_id);
                
            }
            else
            {
                $this->session->set_flashdata('message','No record found...');
                redirect('old_match/contest_list/'.$match_id);
            }
        }   
        else
        {
            $this->session->set_flashdata('message','Match is not complete');
            redirect('old_match/contest_list/'.$match_id);
        }
    }
    
    public function refund()
    {
        $id = $this->uri->segment('3');
        $match_id = base64_decode($id);

        $match_status =  $this->Match_model->match_status_cancel_check($match_id);
        if($match_status)
        {
            $users_list = $this->Match_model->get_my_match_which_cancelled($match_id);
            
            if(isset($users_list) && !empty($users_list))
            {
                foreach ($users_list as $user) 
            {
                if(isset($user['credit_type']) && !empty($user['credit_type']))
                {
                    $data = array('user_id'=>$user['user_id'],
                                'amount'=>$user['credit_type'],
                                'type'=>'credit',
                                'transaction_status'=>'TXN_SUCCESS',
                                'created_date'=>date('Y-m-d h:i:s'),
                                'transection_mode'=>'Refund for match cancel'
                    );
                    amount_refund($data);
                }
                if(isset($user['bonus_type']) && !empty($user['bonus_type']))
                {
                    $data = array('user_id'=>$user['user_id'],
                                'amount'=>$user['bonus_type'],
                                'type'=>'bonus',
                                'transaction_status'=>'SUCCESS',
                                'created_date'=>date('Y-m-d h:i:s'),
                                'transection_mode'=>'Refund for match cancel'
                    );
                    amount_refund($data);
                }
                if(isset($user['winning_type']) && !empty($user['winning_type']))
                {
                    $data = array('user_id'=>$user['user_id'],
                                'amount'=>$user['winning_type'],
                                'type'=>'winning',
                                'transaction_status'=>'SUCCESS',
                                'created_date'=>date('Y-m-d h:i:s'),
                                'transection_mode'=>'Refund for match cancel'
                    );
                    amount_refund($data);
                }
            }
            
            $dataupd = array('refund' =>'1');
            $this->Match_model->update($match_id , $dataupd);

            $this->session->set_flashdata('message','Payment transfer complete');
            redirect('cancel');
            }
            else
            {
                $this->session->set_flashdata('message','No user join this match...');
                redirect('cancel');
            }
            
        }   
        else
        {
            $this->session->set_flashdata('message','Match is not cancel');
            redirect('cancel');
        }   
    }
}