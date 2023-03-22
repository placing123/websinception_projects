using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.IO;
using System.Data.OleDb;
using System.Data;

public partial class Control_pdfread : System.Web.UI.Page
{
    OleDbConnection oledbConn;
    clsFranchisee CF = new clsFranchisee();
    string Fstring;
    MyCon mycon = new MyCon();

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            Session["pass"] = "0";
            Session["fail"] = "0";
        }
    }

    protected void btn_read_Click(object sender, EventArgs e)
    {
        if (File.Exists(Server.MapPath("~/Download/" + FileUpload1.FileName.ToString())))
            File.Delete(Server.MapPath("~/Download/" + FileUpload1.FileName.ToString()));

        FileUpload1.PostedFile.SaveAs(Server.MapPath("~/Download/" + FileUpload1.FileName.ToString()));

        string path = System.IO.Path.GetFullPath(Server.MapPath("~/Download/" + FileUpload1.FileName.ToString()));

        if (Path.GetExtension(path) == ".xls")
        {
            oledbConn = new OleDbConnection("Provider=Microsoft.Jet.OLEDB.4.0;Data Source=" + path + ";Extended Properties=\"Excel 8.0;HDR=Yes;IMEX=2\"");
        }
        else if (Path.GetExtension(path) == ".xlsx")
        {
            oledbConn = new OleDbConnection(@"Provider=Microsoft.ACE.OLEDB.12.0;Data Source=" + path + ";Extended Properties='Excel 12.0;HDR=YES;IMEX=1;';");
        }

        oledbConn.Open();
        OleDbCommand cmd = new OleDbCommand();
        OleDbDataAdapter oleda = new OleDbDataAdapter();
        DataSet ds = new DataSet();
        cmd.Connection = oledbConn;
        cmd.CommandType = CommandType.Text;
        cmd.CommandText = "SELECT * FROM [Sheet1$]";
        oleda = new OleDbDataAdapter(cmd);
        oleda.Fill(ds);

        for (int i = 0; i < ds.Tables[0].Rows.Count; i++)
        {
            cid.Text = cid.Text + ds.Tables[0].Rows[i]["cid"].ToString() + "<br/>";
            form.Text = form.Text + ds.Tables[0].Rows[i]["form"].ToString() + "<br/>";
            report.Text = report.Text + EndTask(ds.Tables[0].Rows[i]["cid"].ToString(), ds.Tables[0].Rows[i]["form"].ToString(), ds.Tables[0].Rows[i]["field"].ToString(), ds.Tables[0].Rows[i]["value"].ToString()) + "<br/>";
        }
        no_pass.Text = Session["pass"].ToString();
        no_fail.Text = Session["fail"].ToString();
    }
    private string EndTask(string cid, string form, string field, string value)
    {
        CF.CId = mycon.AdExecScalar("select id from Tbl_Client_Bpo_Data where cid='" + cid + "' and sr_no='" + form + "'");
        CF.Name = cid;

        DataSet dsClient = CF.Get_Data_From_CID();

        string CTbc_No = dsClient.Tables[0].Rows[0]["Tbc_No"].ToString();
        string CName = dsClient.Tables[0].Rows[0]["Name"].ToString();
        string CEmailId = dsClient.Tables[0].Rows[0]["EmailId"].ToString();
        string CMobileNo = dsClient.Tables[0].Rows[0]["MobileNo"].ToString();
        string CGender = dsClient.Tables[0].Rows[0]["Gender"].ToString();
        string CLicenseNo = dsClient.Tables[0].Rows[0]["LicenseNo"].ToString();
        string CGirNo = dsClient.Tables[0].Rows[0]["GirNo"].ToString();
        string CPanNo = dsClient.Tables[0].Rows[0]["PanNo"].ToString();
        string CH_Address = dsClient.Tables[0].Rows[0]["H_Address"].ToString();
        string CH_City = dsClient.Tables[0].Rows[0]["H_City"].ToString();
        string CH_PinNo = dsClient.Tables[0].Rows[0]["H_PinNo"].ToString();
        string CH_State = dsClient.Tables[0].Rows[0]["H_State"].ToString();
        string CO_Address = dsClient.Tables[0].Rows[0]["O_Address"].ToString();
        string CO_City = dsClient.Tables[0].Rows[0]["O_City"].ToString();
        string CO_PinNo = dsClient.Tables[0].Rows[0]["O_PinNo"].ToString();
        string CLAL = dsClient.Tables[0].Rows[0]["LAL"].ToString();
        string CMRNNo = dsClient.Tables[0].Rows[0]["MRNNo"].ToString();
        string CAF = dsClient.Tables[0].Rows[0]["AF"].ToString();
        string CNRI = dsClient.Tables[0].Rows[0]["NRI"].ToString();
        string CCP = dsClient.Tables[0].Rows[0]["CP"].ToString();

        if (field == "Tbc_No")
        {
            CTbc_No = value;
        }
        else if (field == "Name")
        {
            CName = value;
        }
        else if (field == "EmailId")
        {
            CEmailId = value;
        }
        else if (field == "MobileNo")
        {
            CMobileNo = value;
        }
        else if (field == "Gender")
        {
            CGender = value;
        }
        else if (field == "LicenseNo")
        {
            CLicenseNo = value;
        }
        else if (field == "GirNo")
        {
            CGirNo = value;
        }
        else if (field == "PanNo")
        {
            CPanNo = value;
        }
        else if (field == "H_Address")
        {
            CH_Address = value;
        }
        else if (field == "H_City")
        {
            CH_City = value;
        }
        else if (field == "H_PinNo")
        {
            CH_PinNo = value;
        }
        else if (field == "H_State")
        {
            CH_Address = value;
        }
        else if (field == "O_Address")
        {
            CO_Address = value;
        }
        else if (field == "O_City")
        {
            CO_City = value;
        }
        else if (field == "O_PinNo")
        {
            CO_PinNo = value;
        }
        else if (field == "LAL")
        {
            CLAL = value;
        }
        else if (field == "MRNNo")
        {
            CMRNNo = value;
        }
        else if (field == "AF")
        {
            CAF = value;
        }
        else if (field == "NRI")
        {
            CNRI = value;
        }
        else if (field == "CP")
        {
            CCP = value;
        }


        DataSet dsBpo = CF.Get_Data_From_CID_bpo();
        string BTbc_No = dsBpo.Tables[0].Rows[0]["Tbc_No"].ToString();
        string BName = dsBpo.Tables[0].Rows[0]["Name"].ToString();
        string BEmailId = dsBpo.Tables[0].Rows[0]["EmailId"].ToString();
        string BMobileNo = dsBpo.Tables[0].Rows[0]["MobileNo"].ToString();
        string BGender = dsBpo.Tables[0].Rows[0]["Gender"].ToString();
        string BLicenseNo = dsBpo.Tables[0].Rows[0]["LicenseNo"].ToString();
        string BGirNo = dsBpo.Tables[0].Rows[0]["GirNo"].ToString();
        string BPanNo = dsBpo.Tables[0].Rows[0]["PanNo"].ToString();
        string BH_Address = dsBpo.Tables[0].Rows[0]["H_Address"].ToString();
        string BH_City = dsBpo.Tables[0].Rows[0]["H_City"].ToString();
        string BH_PinNo = dsBpo.Tables[0].Rows[0]["H_PinNo"].ToString();
        string BH_State = dsBpo.Tables[0].Rows[0]["H_State"].ToString();
        string BO_Address = dsBpo.Tables[0].Rows[0]["O_Address"].ToString();
        string BO_City = dsBpo.Tables[0].Rows[0]["O_City"].ToString();
        string BO_PinNo = dsBpo.Tables[0].Rows[0]["O_PinNo"].ToString();
        string BLAL = dsBpo.Tables[0].Rows[0]["LAL"].ToString();
        string BMRNNo = dsBpo.Tables[0].Rows[0]["MRNNo"].ToString();
        string BAF = dsBpo.Tables[0].Rows[0]["AF"].ToString();
        string BNRI = dsBpo.Tables[0].Rows[0]["NRI"].ToString();
        string BCP = dsBpo.Tables[0].Rows[0]["CP"].ToString();


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

        //CF.Id = Convert.ToInt32(drp_pagejump.SelectedValue);
        //CF.Fstring = Fstring.ToString();
        //CF.Update_RW_ClientData();


        if (Fstring.Contains("0"))
        {
            //CF.Id = Convert.ToInt32(drp_pagejump.SelectedValue);
            //CF.Name = Session["Cus_Username"].ToString();
            //CF.Status = "3";
            //CF.Update_Status_2_3();
            int fail = Convert.ToInt32(Session["fail"].ToString());
            fail = fail + 1;
            Session["fail"] = fail.ToString();
            return "Fail";
            
        }
        else
        {
            //CF.Id = Convert.ToInt32(drp_pagejump.SelectedValue);
            //CF.Name = Session["Cus_Username"].ToString();
            //CF.Status = "2";
            //CF.Update_Status_2_3();
            int fail = Convert.ToInt32(Session["pass"].ToString());
            fail = fail + 1;
            Session["pass"] = fail.ToString();
            return "Pass";
           

        }


    }
    protected void Unnamed1_Click(object sender, EventArgs e)
    {
        if (File.Exists(Server.MapPath("~/Download/" + FileUpload1.FileName.ToString())))
            File.Delete(Server.MapPath("~/Download/" + FileUpload1.FileName.ToString()));

        FileUpload1.PostedFile.SaveAs(Server.MapPath("~/Download/" + FileUpload1.FileName.ToString()));

        string path = System.IO.Path.GetFullPath(Server.MapPath("~/Download/" + FileUpload1.FileName.ToString()));

        if (Path.GetExtension(path) == ".xls")
        {
            oledbConn = new OleDbConnection("Provider=Microsoft.Jet.OLEDB.4.0;Data Source=" + path + ";Extended Properties=\"Excel 8.0;HDR=Yes;IMEX=2\"");
        }
        else if (Path.GetExtension(path) == ".xlsx")
        {
            oledbConn = new OleDbConnection(@"Provider=Microsoft.ACE.OLEDB.12.0;Data Source=" + path + ";Extended Properties='Excel 12.0;HDR=YES;IMEX=1;';");
        }

        oledbConn.Open();
        OleDbCommand cmd = new OleDbCommand();
        OleDbDataAdapter oleda = new OleDbDataAdapter();
        DataSet ds = new DataSet();
        DataSet ds1 = new DataSet();

        cmd.Connection = oledbConn;
        cmd.CommandType = CommandType.Text;
        cmd.CommandText = "SELECT * FROM [Sheet1$]";
        oleda = new OleDbDataAdapter(cmd);
        oleda.Fill(ds);

        for (int i = 0; i < ds.Tables[0].Rows.Count; i++)
        {
            cid.Text = cid.Text + ds.Tables[0].Rows[i]["cid"].ToString() + "<br/>";
            form.Text = form.Text + ds.Tables[0].Rows[i]["form"].ToString() + "<br/>";
            CompleteTask(ds.Tables[0].Rows[i]["cid"].ToString(), ds.Tables[0].Rows[i]["form"].ToString(), ds.Tables[0].Rows[i]["field"].ToString(), ds.Tables[0].Rows[i]["value"].ToString());
        }

        CF.CId = ds.Tables[0].Rows[0][0].ToString();

        ds1 = CF.Check_Cutoff_by_CId();

        DataSet ds2 = CF.Check_Cutoff2();
        DataSet ds3 = CF.Check_Cutoff3();

        if (Convert.ToInt32(ds1.Tables[0].Rows[0]["QcCutOff"].ToString()) <= Convert.ToInt32(ds2.Tables[0].Rows[0][0].ToString()))
        {
            CF.CId = ds.Tables[0].Rows[0][0].ToString();
            CF.Status = "2";
            CF.Chnage_Status_Userlogin();

            DataSet dss = CF.Getdate_from_Tbl_Registration();

            if (dss.Tables[0].Rows[0][0].ToString() == "")
            {
                CF.CId = ds.Tables[0].Rows[0][0].ToString();
                CF.dt = System.DateTime.Now.ToString();
                CF.insert_workloadregdate_regtbl();
            }
        }

        else
        {
            CF.CId = ds.Tables[0].Rows[0][0].ToString();
            CF.Status = "3";
            CF.Chnage_Status_Userlogin();

            CF.CId = ds.Tables[0].Rows[0][0].ToString();
            CF.dt = System.DateTime.Now.ToString();
            CF.insert_workloadregdate_regtbl();
        }

    }
    private void CompleteTask(string cid, string form, string field, string value)
    {
        CF.CId = mycon.AdExecScalar("select id from Tbl_Client_Bpo_Data where cid='" + cid + "' and sr_no='" + form + "'");
        CF.Name = cid;
        
        mycon.ExecutQury("update Tbl_Client_Bpo_Data set " + field + "='" + value + "' where cid='" + cid + "' and sr_no='" + form + "'");
        DataSet dsClient = CF.Get_Data_From_CID();

        string CTbc_No = dsClient.Tables[0].Rows[0]["Tbc_No"].ToString();
        string CName = dsClient.Tables[0].Rows[0]["Name"].ToString();
        string CEmailId = dsClient.Tables[0].Rows[0]["EmailId"].ToString();
        string CMobileNo = dsClient.Tables[0].Rows[0]["MobileNo"].ToString();
        string CGender = dsClient.Tables[0].Rows[0]["Gender"].ToString();
        string CLicenseNo = dsClient.Tables[0].Rows[0]["LicenseNo"].ToString();
        string CGirNo = dsClient.Tables[0].Rows[0]["GirNo"].ToString();
        string CPanNo = dsClient.Tables[0].Rows[0]["PanNo"].ToString();
        string CH_Address = dsClient.Tables[0].Rows[0]["H_Address"].ToString();
        string CH_City = dsClient.Tables[0].Rows[0]["H_City"].ToString();
        string CH_PinNo = dsClient.Tables[0].Rows[0]["H_PinNo"].ToString();
        string CH_State = dsClient.Tables[0].Rows[0]["H_State"].ToString();
        string CO_Address = dsClient.Tables[0].Rows[0]["O_Address"].ToString();
        string CO_City = dsClient.Tables[0].Rows[0]["O_City"].ToString();
        string CO_PinNo = dsClient.Tables[0].Rows[0]["O_PinNo"].ToString();
        string CLAL = dsClient.Tables[0].Rows[0]["LAL"].ToString();
        string CMRNNo = dsClient.Tables[0].Rows[0]["MRNNo"].ToString();
        string CAF = dsClient.Tables[0].Rows[0]["AF"].ToString();
        string CNRI = dsClient.Tables[0].Rows[0]["NRI"].ToString();
        string CCP = dsClient.Tables[0].Rows[0]["CP"].ToString();
        
        DataSet dsBpo = CF.Get_Data_From_CID_bpo();
        string BTbc_No = dsBpo.Tables[0].Rows[0]["Tbc_No"].ToString();
        string BName = dsBpo.Tables[0].Rows[0]["Name"].ToString();
        string BEmailId = dsBpo.Tables[0].Rows[0]["EmailId"].ToString();
        string BMobileNo = dsBpo.Tables[0].Rows[0]["MobileNo"].ToString();
        string BGender = dsBpo.Tables[0].Rows[0]["Gender"].ToString();
        string BLicenseNo = dsBpo.Tables[0].Rows[0]["LicenseNo"].ToString();
        string BGirNo = dsBpo.Tables[0].Rows[0]["GirNo"].ToString();
        string BPanNo = dsBpo.Tables[0].Rows[0]["PanNo"].ToString();
        string BH_Address = dsBpo.Tables[0].Rows[0]["H_Address"].ToString();
        string BH_City = dsBpo.Tables[0].Rows[0]["H_City"].ToString();
        string BH_PinNo = dsBpo.Tables[0].Rows[0]["H_PinNo"].ToString();
        string BH_State = dsBpo.Tables[0].Rows[0]["H_State"].ToString();
        string BO_Address = dsBpo.Tables[0].Rows[0]["O_Address"].ToString();
        string BO_City = dsBpo.Tables[0].Rows[0]["O_City"].ToString();
        string BO_PinNo = dsBpo.Tables[0].Rows[0]["O_PinNo"].ToString();
        string BLAL = dsBpo.Tables[0].Rows[0]["LAL"].ToString();
        string BMRNNo = dsBpo.Tables[0].Rows[0]["MRNNo"].ToString();
        string BAF = dsBpo.Tables[0].Rows[0]["AF"].ToString();
        string BNRI = dsBpo.Tables[0].Rows[0]["NRI"].ToString();
        string BCP = dsBpo.Tables[0].Rows[0]["CP"].ToString();


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

        CF.Id =Convert.ToInt32(CF.CId);
        CF.Fstring = Fstring.ToString();
        CF.Update_RW_ClientData();

        if (Fstring.Contains("0"))
        {
            CF.Id = Convert.ToInt32(CF.CId);
            CF.Name = Session["Cus_Username"].ToString();
            CF.Status = "3";
            CF.Update_Status_2_3();
        }
        else
        {
            CF.Id = Convert.ToInt32(CF.CId);
            CF.Name = Session["Cus_Username"].ToString();
            CF.Status = "2";
            CF.Update_Status_2_3();
        }
    }
}