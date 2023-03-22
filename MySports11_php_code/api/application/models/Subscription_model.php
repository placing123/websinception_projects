<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Subscription_model extends CI_Model
{
	public function __construct()
	{
		parent::__construct();
	}	

	/*
	Description: 	Use to get list of post.
	Note:			$Field should be comma seprated and as per selected tables alias. 
	*/
	function getSubscriptions($Field='', $Where=array(), $multiRecords=FALSE, $PageNo=1, $PageSize=10){
		/* Define section  */
		// $Return = array('Data' => array('Records' => array()));
		/* Define variables - ends */
		$this->db->select('*');
		$this->db->from('tbl_subscription');

		if(!empty($Where['ID'])){
			$this->db->where("ID",$Where['ID']);
		}

		if(!empty($Where['Days'])){
			$this->db->where("Days",$Where['Days']);
		}
		$this->db->order_by('Days', 'ASC');

		if($multiRecords){ 
			$TempOBJ = clone $this->db;
			$TempQ = $TempOBJ->get();
			$Return['TotalRecords'] = $TempQ->num_rows();
			$this->db->limit($PageSize, paginationOffset($PageNo, $PageSize)); /*for pagination*/
		}else{
			$this->db->limit(1);
		}

		// $this->db->order_by('language','ASC');
		$Query = $this->db->get();	
		// echo $this->db->last_query();
		if($Query->num_rows()>0){
			foreach($Query->result_array() as $Record){
				if(!$multiRecords){
					return $Record;
				}
				$Records[] = $Record;
			}
			$Return['Records'] = $Records;
			return $Return;
		}
		return FALSE;		
	}

			/*
	Description: 	Use to add new category
	*/
	function addSubscription($input = array()){
		$this->db->trans_start();
	//	$EntityGUID = get_guid();
		/* Add post to entity table and get EntityID. */
	//	$CategoryID = $this->Entity_model->addEntity($EntityGUID, array("EntityTypeID"=>3, "UserID"=>$UserID, "StatusID"=>$StatusID));

		/* Add category */
		$InsertData = array_filter(array(
			"Title" 		=>	$input['Title'],
			"Days" 		    =>	$input['Days'],
			"Price" 		=>	$input['Price'],
			"DiscountPrice" 		=>	$input['DiscountPrice'],
			"Text" 		=>	$input['Text']

		));
		
		$this->db->insert('tbl_subscription', $InsertData);

		$this->db->trans_complete();
		if ($this->db->trans_status() === FALSE)
		{
			return FALSE;
		}
		return true;
	}



	/*
	Description: 	Use to update category.
	*/
	function editSubscription($Input=array()){
		$UpdateArray = array_filter(array(
			"Title" 		=>	@$Input['Title'],
			"Days" 		    =>	@$Input['Days'],
			"Price" 		=>	@$Input['Price'],
			"DiscountPrice" 		=>	@$Input['DiscountPrice'],
			"Text" 		=>	@$Input['Text']

		));
		// print_r($Input);exit;
		if(!empty($UpdateArray)){
			/* Update User details to users table. */
			$this->db->where('ID', $Input['ID']);
			$this->db->limit(1);
			$this->db->update('tbl_subscription', $UpdateArray);
		}
		// $this->Entity_model->updateEntityInfo($CategoryID, array('StatusID'=>@$Input['StatusID']));
		return TRUE;
	}

	function deleteSubscription($SubID){
		if (empty($SubID)) {
            return true;
        }
        $this->db->where(array("ID" => $SubID));
        $this->db->delete('tbl_subscription');
        $this->db->limit(1);
        return TRUE;
	}
}


