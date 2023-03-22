<?php
defined('BASEPATH') OR exit('No direct script access allowed');
class Offers extends Admin_Controller_Secure {
	
	/*------------------------------*/
	/*------------------------------*/	
	
	public function index()
	{
		$load['css']=array(
			'asset/plugins/chosen/chosen.min.css',
			'asset/plugins/select2/css/select2.css',
			'asset/plugins/jquery-datetimepicker/jquery.datetimepicker.min.css'
		);
		$load['js']=array(
			'asset/js/'.$this->ModuleData['ModuleName'].'.js',
			'asset/plugins/chosen/chosen.jquery.min.js',
			'asset/plugins/jquery.form.js',
			'asset/js/'.$this->ModuleData['ModuleName'].'.js',
			'asset/plugins/select2/js/select2.js',
			'asset/plugins/jquery-datetimepicker/jquery.datetimepicker.full.min.js'
		);	

		$this->load->view('includes/header',$load);
		$this->load->view('includes/menu');
		$this->load->view('offers/offers_list');
		$this->load->view('includes/footer');
	}
}
