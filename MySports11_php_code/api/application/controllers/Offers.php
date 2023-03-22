<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Offers extends API_Controller_Secure {

    function __construct() {
        parent::__construct();
        $this->load->model('Offers_model');
    }
 

    /*
      Description: To get offers data
     */

    public function getOffers_post() {
       /* Validation section */
       $this->form_validation->set_rules('ContestGUID', 'ContestGUID', 'trim|callback_validateEntityGUID[Contest,ContestID]');
       $this->form_validation->set_rules('Keyword', 'Search Keyword', 'trim');
       $this->form_validation->set_rules('PageNo', 'PageNo', 'trim|integer');
       $this->form_validation->set_rules('PageSize', 'PageSize', 'trim|integer');
       $this->form_validation->set_rules('Status', 'Status', 'trim|callback_validateStatus');
       $this->form_validation->validation($this);  /* Run validation */
       /* Validation - ends */

       $OffersData = $this->Offers_model->getOffers((!empty($this->Post['Params']) ? $this->Post['Params'] : ''), array_merge($this->Post, array("StatusID" => @$this->StatusID, 'ContestID' => @$this->ContestID)), (!empty($this->ContestID)) ? FALSE : TRUE, @$this->Post['PageNo'], @$this->Post['PageSize']);
       if ($OffersData) {
           $this->Return['Data'] = (!empty($this->ContestID)) ? $OffersData :  $OffersData['Data'];
       }
    }


}

?>