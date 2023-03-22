using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;

public partial class Admin_Login : System.Web.UI.Page
{
    clsAdmin AD = new clsAdmin();
    clsFranchisee CF = new clsFranchisee();
    DataSet ds = new DataSet();
    DataTable dt = new DataTable();
    DataTable dt1 = new DataTable();
    DataTable dt2 = new DataTable();
    MyCon mycon = new MyCon();
    string Fstring;
    string two;
    string three;

    protected void Page_Load(object sender, EventArgs e)
    {

    }
    protected void btn_cler_Click(object sender, EventArgs e)
    {
        try
        {
            EndTask();
            Label1.Text = "done";
        }
        catch
        {
        }

    }
    private void EndTask()
    {
        dt = mycon.FillDataTable("select id from tbl_client_bpo_data where cid='" + txt_cid.Text.Trim() + "'  order by id");

        dt1 = mycon.FillDataTable("select * from Tbl_Client_Bpo_Data where CId='" + txt_cid.Text.Trim() + "'  order by id");

        dt2 = mycon.FillDataTable("select * from Tbl_Bpo_Data where Id in (select id from tbl_client_bpo_data where cid='" + txt_cid.Text.Trim() + "' ) order by id");

        DataSet dsClient = new DataSet();
        dsClient.Tables.Add(dt1);


        DataSet dsBpo = new DataSet();
        dsBpo.Tables.Add(dt2);

        for (int i = 0; i < dt.Rows.Count; i++)
        {

            CF.CId = dt.Rows[i]["id"].ToString();
            CF.Name = txt_cid.Text.Trim();


            string CTbc_No = dsClient.Tables[0].Rows[i]["Tbc_No"].ToString();
            string CName = dsClient.Tables[0].Rows[i]["Name"].ToString();
            string CEmailId = dsClient.Tables[0].Rows[i]["EmailId"].ToString();
            string CMobileNo = dsClient.Tables[0].Rows[i]["MobileNo"].ToString();
            string CGender = dsClient.Tables[0].Rows[i]["Gender"].ToString();
            string CLicenseNo = dsClient.Tables[0].Rows[i]["LicenseNo"].ToString();
            string CGirNo = dsClient.Tables[0].Rows[i]["GirNo"].ToString();
            string CPanNo = dsClient.Tables[0].Rows[i]["PanNo"].ToString();
            string CH_Address = dsClient.Tables[0].Rows[i]["H_Address"].ToString();
            string CH_City = dsClient.Tables[0].Rows[i]["H_City"].ToString();
            string CH_PinNo = dsClient.Tables[0].Rows[i]["H_PinNo"].ToString();
            string CH_State = dsClient.Tables[0].Rows[i]["H_State"].ToString();
            string CO_Address = dsClient.Tables[0].Rows[i]["O_Address"].ToString();
            string CO_City = dsClient.Tables[0].Rows[i]["O_City"].ToString();
            string CO_PinNo = dsClient.Tables[0].Rows[i]["O_PinNo"].ToString();
            string CLAL = dsClient.Tables[0].Rows[i]["LAL"].ToString();
            string CMRNNo = dsClient.Tables[0].Rows[i]["MRNNo"].ToString();
            string CAF = dsClient.Tables[0].Rows[i]["AF"].ToString();
            string CNRI = dsClient.Tables[0].Rows[i]["NRI"].ToString();
            string CCP = dsClient.Tables[0].Rows[i]["CP"].ToString();


            string BTbc_No = dsBpo.Tables[0].Rows[i]["Tbc_No"].ToString();
            string BName = dsBpo.Tables[0].Rows[i]["Name"].ToString();
            string BEmailId = dsBpo.Tables[0].Rows[i]["EmailId"].ToString();
            string BMobileNo = dsBpo.Tables[0].Rows[i]["MobileNo"].ToString();
            string BGender = dsBpo.Tables[0].Rows[i]["Gender"].ToString();
            string BLicenseNo = dsBpo.Tables[0].Rows[i]["LicenseNo"].ToString();
            string BGirNo = dsBpo.Tables[0].Rows[i]["GirNo"].ToString();
            string BPanNo = dsBpo.Tables[0].Rows[i]["PanNo"].ToString();
            string BH_Address = dsBpo.Tables[0].Rows[i]["H_Address"].ToString();
            string BH_City = dsBpo.Tables[0].Rows[i]["H_City"].ToString();
            string BH_PinNo = dsBpo.Tables[0].Rows[i]["H_PinNo"].ToString();
            string BH_State = dsBpo.Tables[0].Rows[i]["H_State"].ToString();
            string BO_Address = dsBpo.Tables[0].Rows[i]["O_Address"].ToString();
            string BO_City = dsBpo.Tables[0].Rows[i]["O_City"].ToString();
            string BO_PinNo = dsBpo.Tables[0].Rows[i]["O_PinNo"].ToString();
            string BLAL = dsBpo.Tables[0].Rows[i]["LAL"].ToString();
            string BMRNNo = dsBpo.Tables[0].Rows[i]["MRNNo"].ToString();
            string BAF = dsBpo.Tables[0].Rows[i]["AF"].ToString();
            string BNRI = dsBpo.Tables[0].Rows[i]["NRI"].ToString();
            string BCP = dsBpo.Tables[0].Rows[i]["CP"].ToString();






            try
            {
                if (CTbc_No.ToString() == BTbc_No.ToString())
                {
                    Fstring = "1" + ",";
                }
                else
                {
                    Fstring = "0" + ",";
                }

                if (CName.ToString() == BName.ToString())
                {
                    Fstring += "1" + ",";
                }
                else
                {
                    Fstring += "0" + ",";
                }

                if (CEmailId.ToString() == BEmailId.ToString())
                {
                    Fstring += "1" + ",";
                }
                else
                {
                    Fstring += "0" + ",";
                }

                if (CMobileNo.ToString() == BMobileNo.ToString())
                {
                    Fstring += "1" + ",";
                }
                else
                {
                    Fstring += "0" + ",";
                }

                if (CGender.ToString() == BGender.ToString())
                {
                    Fstring += "1" + ",";
                }
                else
                {
                    Fstring += "0" + ",";
                }

                if (CLicenseNo.ToString() == BLicenseNo.ToString())
                {
                    Fstring += "1" + ",";
                }
                else
                {
                    Fstring += "0" + ",";
                }
                if (CGirNo.ToString() == BGirNo.ToString())
                {
                    Fstring += "1" + ",";
                }
                else
                {
                    Fstring += "0" + ",";
                }

                if (CPanNo.ToString() == BPanNo.ToString())
                {
                    Fstring += "1" + ",";
                }
                else
                {
                    Fstring += "0" + ",";
                }

                if (CH_Address.ToString() == BH_Address.ToString())
                {
                    Fstring += "1" + ",";
                }
                else
                {
                    Fstring += "0" + ",";
                }
                BH_City = BH_City.Replace('–', '-');
                if (CH_City.ToString() == BH_City.ToString())
                {
                    Fstring += "1" + ",";
                }
                else
                {
                    Fstring += "0" + ",";
                }

                if (CH_PinNo.ToString() == BH_PinNo.ToString())
                {
                    Fstring += "1" + ",";
                }
                else
                {
                    Fstring += "0" + ",";
                }

                if (CH_State.ToString() == BH_State.ToString())
                {
                    Fstring += "1" + ",";
                }
                else
                {
                    Fstring += "0" + ",";
                }

                if (CO_Address.ToString() == BO_Address.ToString())
                {
                    Fstring += "1" + ",";
                }
                else
                {
                    Fstring += "0" + ",";
                }
                BO_City = BO_City.Replace('–', '-');
                if (CO_City.ToString() == BO_City.ToString())
                {
                    Fstring += "1" + ",";
                }
                else
                {
                    Fstring += "0" + ",";
                }

                if (CO_PinNo.ToString() == BO_PinNo.ToString())
                {
                    Fstring += "1" + ",";
                }
                else
                {
                    Fstring += "0" + ",";
                }

                if (CLAL.ToString() == BLAL.ToString())
                {
                    Fstring += "1" + ",";
                }
                else
                {
                    Fstring += "0" + ",";
                }

                if (CMRNNo.ToString() == BMRNNo.ToString())
                {
                    Fstring += "1" + ",";
                }
                else
                {
                    Fstring += "0" + ",";
                }

                if (CAF.ToString() == BAF.ToString())
                {
                    Fstring += "1" + ",";
                }
                else
                {
                    Fstring += "0" + ",";
                }

                if (CNRI.ToString() == BNRI.ToString())
                {
                    Fstring += "1" + ",";
                }
                else
                {
                    Fstring += "0" + ",";
                }

                if (CCP.ToString() == BCP.ToString())
                {
                    Fstring += "1";
                }
                else
                {
                    Fstring += "0";
                }


                CF.Id = Convert.ToInt32(dt.Rows[i]["id"].ToString());
                CF.Fstring = Fstring.ToString();
                //  CF.Update_RW_ClientData();


                if (Fstring.Contains("0"))
                {
                    CF.Id = Convert.ToInt32(dt.Rows[i]["id"].ToString());
                    CF.Name = txt_cid.Text.Trim();
                    CF.Status = "3";
                    //CF.Update_Status_2_3();
                    three = three + "'" + CF.Id + "'" + ",";
                }
                else
                {
                    CF.Id = Convert.ToInt32(dt.Rows[i]["id"].ToString());
                    CF.Name = txt_cid.Text.Trim();
                    CF.Status = "2";
                    //CF.Update_Status_2_3();
                    two = two + "'" + CF.Id + "'" + ",";
                }
            }
            catch
            { }
        }

        if (two != null)
        {
            two = two.Substring(0, two.Length - 1);
            mycon.ExecutQury("update Tbl_Client_Bpo_Data set Status='2' where Id in (" + two + ") and CId='" + txt_cid.Text.Trim() + "'");
        }
        if (three != null)
        {
            three = three.Substring(0, three.Length - 1);
            mycon.ExecutQury("update Tbl_Client_Bpo_Data set Status='3' where Id in (" + three + ") and CId='" + txt_cid.Text.Trim() + "'");
        }
        //////////////////////////////////////////////

        CF.CId = txt_cid.Text.Trim();

        ds = CF.Check_Cutoff_by_CId();

        DataSet ds2 = CF.Check_Cutoff2();
        DataSet ds3 = CF.Check_Cutoff3();

        if (Convert.ToInt32(ds.Tables[0].Rows[0]["QcCutOff"].ToString()) <= Convert.ToInt32(ds2.Tables[0].Rows[0][0].ToString()))
        {
            CF.CId = txt_cid.Text.Trim();
            CF.Status = "2";
            CF.Chnage_Status_Userlogin();

            DataSet dss = CF.Getdate_from_Tbl_Registration();

            if (dss.Tables[0].Rows[0][0].ToString() == "")
            {
                CF.CId = txt_cid.Text.Trim();
                CF.dt = System.DateTime.Now.ToString();
                CF.insert_workloadregdate_regtbl();
            }
        }

        else
        {
            CF.CId = txt_cid.Text.Trim();
            CF.Status = "3";
            CF.Chnage_Status_Userlogin();

            CF.CId = txt_cid.Text.Trim();
            CF.dt = System.DateTime.Now.ToString();
            CF.insert_workloadregdate_regtbl();
        }

    }
}