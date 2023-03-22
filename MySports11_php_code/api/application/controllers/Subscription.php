<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Subscription extends API_Controller_Secure
{
	function __construct()
	{	
		// echo "sdsdds";
		parent::__construct();
		$this->load->model('Subscription_model');
	}

	/*
	Name: 			posts
	Description: 	Use to add new post
	URL: 			/api/post	
	*/
	public function getSubscription_post()
	{
		// echo "sdsdsd";exit;
		$Data = $this->Subscription_model->getSubscriptions('',array_filter(array("ID"=>@$this->Post['ID'])),TRUE,@$this->Post['PageNo'], @$this->Post['PageSize']);
		if($Data){
			$UserData = $this->Users_model->getUsers('WalletAmount,SubscriptionEnd', array('UserID' => $this->SessionUserID));
			// print_r($UserData);exit;
			if($UserData['SubscriptionEnd'] >= date('Y-m-d H:i:s')){
				$Data['SubscriptionsStatus'] = true;
			}else{
				$Data['SubscriptionsStatus'] = false;
			}



			$subscription = $this->db->query("SELECT S.* FROM tbl_users_wallet W JOIN tbl_subscription S on S.ID = W.SubscriptionID WHERE W.UserID = $this->SessionUserID AND W.SubscriptionID != '' ORDER BY W.EntryDate DESC LIMIT 1")->row();
            if(!empty($subscription)){
                $Data['subscription'] = $subscription;
            }else{
                $Data['subscription'] = new stdClass();

            }
			$Data['SubscriptionEnd'] = $UserData['SubscriptionEnd'];
			$this->Return['ResponseCode'] 			=	200;
			$this->Return['Message']      			=	"Success";
			$this->Return['Data']      	=	$Data;

		}else{
			$this->Return['ResponseCode'] 			=	500;
			$this->Return['Message']      			=	"Someting went wrong try again later";
		}
	}

	public function buySubscription_post()
	{
		$this->form_validation->set_rules('ID', 'ID', 'trim|required');
		$this->form_validation->validation($this);  /* Run validation */

		$Data = $this->Subscription_model->getSubscriptions('',array_filter(array("ID"=>@$this->Post['ID'])),False,@$this->Post['PageNo'], @$this->Post['PageSize']);
		if(!empty($Data)){

			$UserData = $this->Users_model->getUsers('WalletAmount,SubscriptionEnd', array('UserID' => $this->SessionUserID));
			if($UserData['SubscriptionEnd'] >= date('Y-m-d H:i:s')){
				$this->Return['ResponseCode'] 			=	500;
				$this->Return['Message']      			=	"Already Subscribed";
				exit;
			}
			
			if($UserData['WalletAmount'] >= $Data['DiscountPrice']){
				$InsertData = array(
					"Amount" => $Data['DiscountPrice'],
					"WalletAmount" => $Data['DiscountPrice'],
					"SubscriptionID" => $this->Post['ID'],
					"TransactionType" => 'Dr',
					"EntityID" => null,
					"UserTeamID" => null,
					"Narration" => 'Subscription',
					"EntryDate" => date("Y-m-d H:i:s")
				);

				$this->Users_model->addToWallet($InsertData, $this->SessionUserID, 5);
				$timeInHours = $Data['Days']*24;
				$endDate = date("Y-m-d H:i:s", strtotime("+$timeInHours hours"));
				
				$this->Users_model->updateUserInfo($this->SessionUserID, array("SubscriptionStart" =>date('Y-m-d H:i:s'),"SubscriptionEnd" =>$endDate));

				$this->Return['ResponseCode'] 			=	200;
				$this->Return['Message']      			=	"Subscribe successfully";

			}else{
				$this->Return['ResponseCode'] 			=	500;
				$this->Return['Message']      			=	"Insufficient funds in your wallet";	
			}
		}else{
			$this->Return['ResponseCode'] 			=	500;
			$this->Return['Message']      			=	"Someting went wrong try again later";
		}
	}
}
