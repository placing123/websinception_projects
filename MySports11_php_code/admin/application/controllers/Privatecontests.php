<?php
defined('BASEPATH') OR exit('No direct script access allowed');
class Privatecontests extends Admin_Controller_Secure {
	
	/*------------------------------*/
	/*------------------------------*/	
	public function index()
	{
		$load['css']=array(
			'asset/plugins/chosen/chosen.min.css'
		);
		$load['js']=array(
			'asset/js/'.$this->ModuleData['ModuleName'].'.js',
			'asset/plugins/chosen/chosen.jquery.min.js'
		);	

		$this->load->view('includes/header',$load);
		$this->load->view('includes/menu');
		$this->load->view('user/privatecontests_list');
		$this->load->view('includes/footer');
	}

	public function AuctionPrivateContests()
	{
		$load['css']=array(
			'asset/plugins/chosen/chosen.min.css'
		);
		$load['js']=array(
			'asset/js/AuctionAndDraftPrivatecontests.js',
			'asset/plugins/chosen/chosen.jquery.min.js'
		);	

		$this->load->view('includes/header',$load);
		$this->load->view('includes/menu');
		$this->load->view('privatecontest/auctionprivatecontests_list');
		$this->load->view('includes/footer');
	}

	public function SnakePrivateContests()
	{
		$load['css']=array(
			'asset/plugins/chosen/chosen.min.css'
		);
		$load['js']=array(
			'asset/js/AuctionAndDraftPrivatecontests.js',
			'asset/plugins/chosen/chosen.jquery.min.js'
		);	

		$this->load->view('includes/header',$load);
		$this->load->view('includes/menu');
		$this->load->view('privatecontest/snakeprivatecontests_list');
		$this->load->view('includes/footer');
	}



}
