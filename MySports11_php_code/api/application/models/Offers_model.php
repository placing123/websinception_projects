<?php

if (!defined('BASEPATH')) exit('No direct script access allowed');

class Offers_model extends CI_Model {

    public function __construct() {
        parent::__construct();
    }

    
    /*
      Description:    ADD offers to system.
     */

    function addOffers($Input = array(), $SessionUserID, $MatchID, $ContestIDs, $UserIDs) {
        /* Add offer to offers table . */
        $InsertData = array_filter(array(
            "OfferType" => @$Input['OfferType'],
            "OfferName" => @$Input['OfferName'],
            "OfferPercent" => @$Input['OfferPercent'],
            "MatchID" => $MatchID,
            "ContestIDs" => json_encode($ContestIDs),
            "OfferDateTime" => @$Input['OfferDateTime'],
            "NoOfTeams" => @$Input['NoOfTeams'],
            "UserSelectionType" => @$Input['UserSelectionType'],
            "NoOfRandomUsers" => @$Input['NoOfRandomUsers'],
            "UserIDs" => json_encode($UserIDs)
        ));
        $this->db->insert('tbl_offers', $InsertData);
        
        $this->db->trans_complete();
        if ($this->db->trans_status() === FALSE) {
            return FALSE;
        }
        return TRUE;
    }

     /*
      Description: To get all offers
     */
    function getOffers($Field = '', $Where = array(), $multiRecords = FALSE, $PageNo = 1, $PageSize = 15){
        $Params = array();
        if (!empty($Field)) {
            $Params = array_map('trim', explode(',', $Field));
            $Field = '';
            $FieldArray = array(
                'OfferID'         => 'O.ID',
                'OfferType'       => 'O.OfferType',
                'OfferPercent'    => 'O.OfferPercent',
                'OfferName'        => 'O.OfferName',
                'OfferDateTime'    => 'O.OfferDateTime',
                'NoOfTeams '       => 'O.NoOfTeams',
                'UserSelectionType' => 'O.UserSelectionType',
                'NoOfRandomUsers'    => 'O.NoOfRandomUsers'
            );
            if ($Params) {
                foreach ($Params as $Param) {
                    $Field .= (!empty($FieldArray[$Param]) ? ',' . $FieldArray[$Param] : '');
                }
            }
        }
        $this->db->select('O.ID AS OfferID,O.OfferName');
        if (!empty($Field))
            $this->db->select($Field, FALSE);
        $this->db->from('tbl_offers O');
      
        if (!empty($Where['Keyword'])) {
            $this->db->like("O.OfferName", $Where['Keyword']);
        }
        if (!empty($Where['MatchID'])) {
            $this->db->where("O.MatchID", $Where['MatchID']);
        }
        if (!empty($Where['OfferType'])) {
            $this->db->where("O.OfferType", $Where['OfferType']);
        }
        if (!empty($Where['UserSelectionType'])) {
            $this->db->where("O.UserSelectionType", $Where['UserSelectionType']);
        }
        
        if (!empty($Where['OrderBy']) && !empty($Where['Sequence'])) {
            $this->db->order_by($Where['OrderBy'], $Where['Sequence']);
        } else {
            $this->db->order_by('O.OfferName', 'ASC');
        }

        /* Total records count only if want to get multiple records */
        if ($multiRecords) {
            $TempOBJ = clone $this->db;
            $TempQ = $TempOBJ->get();
            $Return['Data']['TotalRecords'] = $TempQ->num_rows();
            if ($PageNo != 0) {
                $this->db->limit($PageSize, paginationOffset($PageNo, $PageSize)); /* for pagination */
            }
        } else {
            $this->db->limit(1);
        }
        $Query = $this->db->get();
        if ($Query->num_rows() > 0) {
            if ($multiRecords) {
                $Return['Data']['Records'] = $Query->result_array();
                return $Return;
            } else {
                $Record = $Query->row_array();
                return $Record;
            }
        }
        return FALSE;
    }

}

?>