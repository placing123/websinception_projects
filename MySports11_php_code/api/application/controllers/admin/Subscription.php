<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Subscription extends API_Controller {

    function __construct() {
        parent::__construct();
        $this->load->model('Subscription_model');
    }


    public function index_post() {
      $LanguageData = $this->Subscription_model->getSubscriptions('',array_filter(array("ID"=>@$this->Post['ID'])),TRUE,@$this->Post['PageNo'], @$this->Post['PageSize']);
        if (!empty($LanguageData)) {
            $this->Return['Data'] = $LanguageData;
        }
    }

    public function add_post() {
        /* Validation section */
        $this->form_validation->set_rules('Title', 'Title', 'trim|required');
        $this->form_validation->set_rules('Days', 'Days', 'trim|required|integer');
        $this->form_validation->set_rules('Price', 'Price', 'trim|required|numeric');
        $this->form_validation->set_rules('DiscountPrice', 'DiscountPrice', 'trim|required|numeric');
        $this->form_validation->set_rules('Text', 'Text', 'trim|required');
        $this->form_validation->validation($this);  /* Run validation */

        if($this->Post['Days'] < 1 || $this->Post['Days'] > 100 ){
          $this->Return['ResponseCode'] = 500;
          $this->Return['Message'] = "Days should be greater then 1 or less then 100";
          exit;
        }
        
        if($this->Post['Price'] < 1 || $this->Post['Price'] > 10000){
          $this->Return['ResponseCode'] = 500;
          $this->Return['Message'] = "Price should be greater then 1 or less then 10000";
          exit;
        }
        
        if($this->Post['DiscountPrice'] < 1 || $this->Post['DiscountPrice'] > 10000){
          $this->Return['ResponseCode'] = 500;
          $this->Return['Message'] = "DiscountPrice should be greater then 1 or less then 10000";
          exit;
        } 

        if($this->Post['DiscountPrice'] > $this->Post['Price']){
          $this->Return['ResponseCode'] = 500;
          $this->Return['Message'] = "DiscountPrice cannot be greater then orignal price";
          exit;
        } 

      $SubscriptionData = $this->Subscription_model->getSubscriptions('',array_filter(array("Days"=>@$this->Post['Days'])),FALSE);
      if(!empty($SubscriptionData)){
        $this->Return['ResponseCode'] = 500;
        $this->Return['Message'] = "Subscription already added for ".$this->Post['Days']." day";
        exit;
      }

      $Data = $this->Subscription_model->addSubscription($this->Post);
        if (!empty($Data)){
            $this->Return['Data'];
        }
      }

    public function edit_post() {
        /* Validation section */  
        $this->form_validation->set_rules('ID', 'ID', 'trim|required');
        $this->form_validation->set_rules('Title', 'Title', 'trim|required');
        $this->form_validation->set_rules('Days', 'Days', 'trim|required|integer');
        $this->form_validation->set_rules('Price', 'Price', 'trim|required|numeric');
        $this->form_validation->set_rules('DiscountPrice', 'DiscountPrice', 'trim|required|numeric');
        $this->form_validation->set_rules('Text', 'Text', 'trim|required');
        $this->form_validation->validation($this);  /* Run validation */

        if($this->Post['Days'] < 1 || $this->Post['Days'] > 100 ){
          $this->Return['ResponseCode'] = 500;
          $this->Return['Message'] = "Days should be greater then 1 or less then 100";
          exit;
        }
        
        if($this->Post['Price'] < 1 || $this->Post['Price'] > 10000){
          $this->Return['ResponseCode'] = 500;
          $this->Return['Message'] = "Price should be greater then 1 or less then 10000";
          exit;
        }
        
        if($this->Post['DiscountPrice'] < 1 || $this->Post['DiscountPrice'] > 10000){
          $this->Return['ResponseCode'] = 500;
          $this->Return['Message'] = "DiscountPrice should be greater then 1 or less then 10000";
          exit;
        } 

        if($this->Post['DiscountPrice'] > $this->Post['Price']){
          $this->Return['ResponseCode'] = 500;
          $this->Return['Message'] = "DiscountPrice cannot be greater then orignal price";
          exit;
        } 

      $SubscriptionCheck = $this->db->query("SELECT * FROM tbl_subscription WHERE Days = ".$this->Post['Days']." AND ID != ".$this->Post['ID']." ")->num_rows();  
      
      if(!empty($SubscriptionCheck)){
        $this->Return['ResponseCode'] = 500;
        $this->Return['Message'] = "Subscription already added for ".$this->Post['Days']." day";
        exit;
      }

      $Data = $this->Subscription_model->editSubscription($this->Post);
        if (!empty($Data)){
            $this->Return['Data'];
        }
      }

    public function delete_post() {
        /* Validation section */  
        $this->form_validation->set_rules('ID', 'ID', 'trim|required');
        $this->form_validation->validation($this);  /* Run validation */

        $LanguageData = $this->Subscription_model->deleteSubscription($this->Post['ID']);
        if (!empty($LanguageData)){
          $this->Return['Data'];
        }
    }


    public function getLanguages_post() {
      $LanguageData = $this->Subscription_model->getLanguage('',array_filter(array("LanguageID"=>@$this->Post['LanguageID'])),TRUE,@$this->Post['PageNo'], @$this->Post['PageSize']);
        if (!empty($LanguageData)) {
            $this->Return['Data'] = $LanguageData;
        }
    }

}
