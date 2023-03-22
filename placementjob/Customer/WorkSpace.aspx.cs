using System;
using System.Collections;
using System.Configuration;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Xml.Linq;

public partial class Customer_Report : System.Web.UI.Page
{
    clsFranchisee CF = new clsFranchisee();
    string id;
    string Fstring;
    clsAdmin AD = new clsAdmin();
    DataSet DS = new DataSet();

    protected void Page_Load(object sender, EventArgs e)
    {
        if (Session["Cus_Username"] == null)
        {
            Response.Redirect("../Default.aspx");
        }
        MyCon mycon = new MyCon();
        string rpt;
        rpt = mycon.AdExecScalar("select rpt from tbl_registration where cid='" + Session["Cus_Username"].ToString() + "'");
        if (rpt == "0")
        {
            Response.Redirect("Report.aspx");
        }
        
        try
        {
            AD.id = Session["Cus_Username"].ToString();
            DS=AD.Select_Registration_All();

            if (DS.Tables[0].Rows[0]["Status"].ToString() == "4" && DS.Tables[0].Rows[0]["WorkloadSub_date"].ToString() == "")
            {
                form_main.Visible = false;
              
            }
            else if (DS.Tables[0].Rows[0]["Status"].ToString() == "1" || DS.Tables[0].Rows[0]["Status"].ToString() == "2" || DS.Tables[0].Rows[0]["Status"].ToString() == "3")
            {
                Response.Redirect("Default.aspx");
            }
            else
            {
                
            CF.CId = Session["Cus_Username"].ToString();

            DataSet ds = CF.Check_Cutoff_by_CId();

            DataSet ds2 = CF.Check_Cutoff2();
            DataSet ds3 = CF.Check_Cutoff3();

            if (Convert.ToInt32(ds.Tables[0].Rows[0]["QcCutOff"].ToString()) <= Convert.ToInt32(ds2.Tables[0].Rows[0][0].ToString()))
            {
                FillTotalComplete();

                checkstatus();
                if (!IsPostBack)
                {
                    GetImg();
                    FillDrp_Pageno();
                    filldata();
                    FinishTask();
                }
              

            }

            else
            {
                FillTotalComplete();
              
                if (!IsPostBack)
                {
                    GetImg();
                    FillDrp_Pageno();
                    filldata();
                    FinishTask();
                }
              
            }
            }

        }

        catch { }

       

    }
    public void checkstatus()
    {

        
            if (lblid.Text == "")
            {
                //CF.CId = id.ToString();
                //CF.Name = Session["Cus_Username"].ToString();

                AD.cid = CF.CId.ToString();//id.ToString();
                AD.name = Session["Cus_Username"].ToString();
                DataSet dsClient = AD.Get_Data_From_CID_2();

               // DataSet dsBpo = AD.Get_Data_From_CID_bpo_2();
             
            }

            if (lblid.Text != "")
            {
                CF.CId = lblid.Text;
                CF.Name = Session["Cus_Username"].ToString();
                AD.cid = CF.CId.ToString();//id.ToString();
                AD.name = Session["Cus_Username"].ToString();
                DataSet dsClient = AD.Get_Data_From_CID_2();




             //   DataSet dsBpo = AD.Get_Data_From_CID_bpo_2();
                
              

        }
    }

    public void FinishTask()
    {
        if (lblid.Text == "")
        {
            CF.CId = id.ToString();
            CF.Name = Session["Cus_Username"].ToString();

            DataSet dsClient = CF.Get_Data_From_CID();

            lblimgno.Text = dsClient.Tables[0].Rows[0]["Sr_No"].ToString();


            txt_tbc.Text= dsClient.Tables[0].Rows[0]["Tbc_No"].ToString();
            txt_name.Text = dsClient.Tables[0].Rows[0]["Name"].ToString();
            txt_email.Text = dsClient.Tables[0].Rows[0]["EmailId"].ToString();
            txt_mobno.Text = dsClient.Tables[0].Rows[0]["MobileNo"].ToString();
            txt_gender.Text = dsClient.Tables[0].Rows[0]["Gender"].ToString();
            txt_licenceno.Text = dsClient.Tables[0].Rows[0]["LicenseNo"].ToString();
            txt_girno.Text = dsClient.Tables[0].Rows[0]["GirNo"].ToString();
            txt_panno.Text = dsClient.Tables[0].Rows[0]["PanNo"].ToString();
            txt_Hadd.Text = dsClient.Tables[0].Rows[0]["H_Address"].ToString();
            txt_Hcity.Text = dsClient.Tables[0].Rows[0]["H_City"].ToString();
            txt_Hpin.Text = dsClient.Tables[0].Rows[0]["H_PinNo"].ToString();
            txt_HState.Text = dsClient.Tables[0].Rows[0]["H_State"].ToString();
            txt_Oadd.Text = dsClient.Tables[0].Rows[0]["O_Address"].ToString();
            txt_Ocity.Text = dsClient.Tables[0].Rows[0]["O_City"].ToString();
            txt_Opincode.Text = dsClient.Tables[0].Rows[0]["O_PinNo"].ToString();
            txt_loanapproval.Text = dsClient.Tables[0].Rows[0]["LAL"].ToString();
            txt_menno.Text = dsClient.Tables[0].Rows[0]["MRNNo"].ToString();
            txt_af.Text = dsClient.Tables[0].Rows[0]["AF"].ToString();
            txt_nri.Text = dsClient.Tables[0].Rows[0]["NRI"].ToString();
            txt_cp.Text = dsClient.Tables[0].Rows[0]["CP"].ToString();


           
        }

        if (lblid.Text != "")
        {
            CF.CId = lblid.Text;
            CF.Name = Session["Cus_Username"].ToString();

            DataSet dsClient = CF.Get_Data_From_CID();

            lblimgno.Text = dsClient.Tables[0].Rows[0]["Sr_No"].ToString();


            txt_tbc.Text = dsClient.Tables[0].Rows[0]["Tbc_No"].ToString();
            txt_name.Text = dsClient.Tables[0].Rows[0]["Name"].ToString();
            txt_email.Text = dsClient.Tables[0].Rows[0]["EmailId"].ToString();
            txt_mobno.Text = dsClient.Tables[0].Rows[0]["MobileNo"].ToString();
            txt_gender.Text = dsClient.Tables[0].Rows[0]["Gender"].ToString();
            txt_licenceno.Text = dsClient.Tables[0].Rows[0]["LicenseNo"].ToString();
            txt_girno.Text = dsClient.Tables[0].Rows[0]["GirNo"].ToString();
            txt_panno.Text = dsClient.Tables[0].Rows[0]["PanNo"].ToString();
            txt_Hadd.Text = dsClient.Tables[0].Rows[0]["H_Address"].ToString();
            txt_Hcity.Text = dsClient.Tables[0].Rows[0]["H_City"].ToString();
            txt_Hpin.Text = dsClient.Tables[0].Rows[0]["H_PinNo"].ToString();
            txt_HState.Text = dsClient.Tables[0].Rows[0]["H_State"].ToString();
            txt_Oadd.Text = dsClient.Tables[0].Rows[0]["O_Address"].ToString();
            txt_Ocity.Text = dsClient.Tables[0].Rows[0]["O_City"].ToString();
            txt_Opincode.Text = dsClient.Tables[0].Rows[0]["O_PinNo"].ToString();
            txt_loanapproval.Text = dsClient.Tables[0].Rows[0]["LAL"].ToString();
            txt_menno.Text = dsClient.Tables[0].Rows[0]["MRNNo"].ToString();
            txt_af.Text = dsClient.Tables[0].Rows[0]["AF"].ToString();
            txt_nri.Text = dsClient.Tables[0].Rows[0]["NRI"].ToString();
            txt_cp.Text = dsClient.Tables[0].Rows[0]["CP"].ToString();



            DataSet dsBpo = CF.Get_Data_From_CID_bpo();
         
        }

    }

    protected void Page_PreRender(Object sender, EventArgs e)
    {
        txt_tbc.Attributes.Add("autocomplete", "off");
        txt_name.Attributes.Add("autocomplete", "off");
        txt_email.Attributes.Add("autocomplete", "off");
        txt_mobno.Attributes.Add("autocomplete", "off");
        txt_gender.Attributes.Add("autocomplete", "off");
        txt_licenceno.Attributes.Add("autocomplete", "off");
        txt_girno.Attributes.Add("autocomplete", "off");
        txt_panno.Attributes.Add("autocomplete", "off");
        txt_Hadd.Attributes.Add("autocomplete", "off");
        txt_Hcity.Attributes.Add("autocomplete", "off");
        txt_Hpin.Attributes.Add("autocomplete", "off");
        txt_HState.Attributes.Add("autocomplete", "off");
        txt_Oadd.Attributes.Add("autocomplete", "off");
        txt_Ocity.Attributes.Add("autocomplete", "off");
        txt_Opincode.Attributes.Add("autocomplete", "off");
        txt_loanapproval.Attributes.Add("autocomplete", "off");
        txt_menno.Attributes.Add("autocomplete", "off");
        txt_af.Attributes.Add("autocomplete", "off");
        txt_nri.Attributes.Add("autocomplete", "off");
        txt_cp.Attributes.Add("autocomplete", "off");
    }

    public void FillTotalComplete()
    {
        CF.CId = Session["Cus_Username"].ToString();

        CF.QueryFor = "Complete";
        DataSet ds = CF.Master_Client_BPO_Data();
        lblcompl.Text = ds.Tables[0].Rows[0][0].ToString();

        CF.QueryFor = "Total";
        DataSet ds1 = CF.Master_Client_BPO_Data();
        lbltotal.Text = ds1.Tables[0].Rows[0][0].ToString();

       
    }

    public void FillDrp_Pageno()
    {
        int no = Convert.ToInt32(lbltotal.Text.Trim());

        CF.CId = Session["Cus_Username"].ToString();
        DataSet ds = CF.Get_Id_From_Pagejump();
        drp_pagejump.DataTextField = ds.Tables[0].Columns[0].ToString();
        drp_pagejump.DataValueField = ds.Tables[0].Columns[1].ToString();
        drp_pagejump.DataSource = ds;
        drp_pagejump.DataBind();

    }
    public void FillDrp_Pageno1()
    {
        int no = Convert.ToInt32(lbltotal.Text.Trim());

        CF.CId = Session["Cus_Username"].ToString();
        DataSet ds = CF.Get_Id_From_Pagejump_3();

        drp_pagejump.DataTextField = ds.Tables[0].Columns[0].ToString();
        drp_pagejump.DataValueField = ds.Tables[0].Columns[1].ToString();
        drp_pagejump.DataSource = ds;
        drp_pagejump.DataBind();
    }

    public void GetImg()
    {
        try
        {
            CF.CId = Session["Cus_Username"].ToString();
            CF.QueryFor = "GetID";

            DataSet ds = CF.Master_Client_BPO_Data();

            string photo = ds.Tables[0].Rows[0][0].ToString();
            MainImg.Src = "../ClientImage/" + photo + ".jpg";
        }
        catch
        { }
    }
    protected void btnsubmit_Click(object sender, ImageClickEventArgs e)
    {
        
    }
    protected void btnreset_Click(object sender, ImageClickEventArgs e)
    {
        Response.Redirect("Default.aspx");

    }
    protected void txt_tbc_TextChanged(object sender, EventArgs e)
    {
        SetFocus(txt_gender);
    }
    protected void txt_gender_TextChanged(object sender, EventArgs e)
    {
        SetFocus(txt_email);
    }
    protected void txt_email_TextChanged(object sender, EventArgs e)
    {
        SetFocus(txt_loanapproval);
    }
    protected void txt_loanapproval_TextChanged(object sender, EventArgs e)
    {
        SetFocus(txt_mobno);
    }
    protected void txt_mobno_TextChanged(object sender, EventArgs e)
    {
        SetFocus(txt_panno);
    }
    protected void txt_panno_TextChanged(object sender, EventArgs e)
    {
        SetFocus(txt_girno);
    }
    protected void txt_girno_TextChanged(object sender, EventArgs e)
    {
        SetFocus(txt_licenceno);

    }
    protected void txt_licenceno_TextChanged(object sender, EventArgs e)
    {
        SetFocus(txt_name);

    }
    protected void txt_name_TextChanged(object sender, EventArgs e)
    {
        SetFocus(txt_Opincode);

    }
    protected void txt_Opincode_TextChanged(object sender, EventArgs e)
    {
        SetFocus(txt_Hadd);
    }
    protected void txt_Hadd_TextChanged(object sender, EventArgs e)
    {
        SetFocus(txt_Hpin);
    }
    protected void txt_Hpin_TextChanged(object sender, EventArgs e)
    {
        SetFocus(txt_Oadd);

    }
    protected void txt_Oadd_TextChanged(object sender, EventArgs e)
    {
        SetFocus(txt_HState);

    }
    protected void txt_HState_TextChanged(object sender, EventArgs e)
    {
        SetFocus(txt_Ocity);

    }
    protected void txt_Ocity_TextChanged(object sender, EventArgs e)
    {
        SetFocus(txt_Hcity);

    }
    protected void txt_Hcity_TextChanged(object sender, EventArgs e)
    {
        SetFocus(txt_nri);

    }
    protected void txt_nri_TextChanged(object sender, EventArgs e)
    {
        SetFocus(txt_menno);

    }
    protected void txt_menno_TextChanged(object sender, EventArgs e)
    {
        SetFocus(txt_cp);

    }
    protected void txt_cp_TextChanged(object sender, EventArgs e)
    {
        SetFocus(txt_af);

    }
    protected void drp_pagejump_SelectedIndexChanged(object sender, EventArgs e)
    {


        lblid.Text = drp_pagejump.SelectedValue;

        CF.CId = drp_pagejump.SelectedValue;
        CF.Name = Session["Cus_Username"].ToString();
        DataSet ds = CF.Get_Data_From_CID();

        lblimgno.Text = ds.Tables[0].Rows[0]["Sr_No"].ToString();

        string photo = ds.Tables[0].Rows[0][0].ToString();
        MainImg.Src = "../ClientImage/" + photo + ".jpg";


        txt_tbc.Text = ds.Tables[0].Rows[0]["Tbc_No"].ToString();
        txt_name.Text = ds.Tables[0].Rows[0]["Name"].ToString();
        txt_email.Text = ds.Tables[0].Rows[0]["EmailId"].ToString();
        txt_mobno.Text = ds.Tables[0].Rows[0]["MobileNo"].ToString();
        txt_gender.Text = ds.Tables[0].Rows[0]["Gender"].ToString();
        txt_licenceno.Text = ds.Tables[0].Rows[0]["LicenseNo"].ToString();
        txt_girno.Text = ds.Tables[0].Rows[0]["GirNo"].ToString();
        txt_panno.Text = ds.Tables[0].Rows[0]["PanNo"].ToString();
        txt_Hadd.Text = ds.Tables[0].Rows[0]["H_Address"].ToString();
        txt_Hcity.Text = ds.Tables[0].Rows[0]["H_City"].ToString();
        txt_Hpin.Text = ds.Tables[0].Rows[0]["H_PinNo"].ToString();
        txt_HState.Text = ds.Tables[0].Rows[0]["H_State"].ToString();
        txt_Oadd.Text = ds.Tables[0].Rows[0]["O_Address"].ToString();
        txt_Ocity.Text = ds.Tables[0].Rows[0]["O_City"].ToString();
        txt_Opincode.Text = ds.Tables[0].Rows[0]["O_PinNo"].ToString();
        txt_loanapproval.Text = ds.Tables[0].Rows[0]["LAL"].ToString();
        txt_menno.Text = ds.Tables[0].Rows[0]["MRNNo"].ToString();
        txt_af.Text = ds.Tables[0].Rows[0]["AF"].ToString();
        txt_nri.Text = ds.Tables[0].Rows[0]["NRI"].ToString();
        txt_cp.Text = ds.Tables[0].Rows[0]["CP"].ToString();

        FinishTask();
    }
    public void filldata()
    {

        lblid.Text = drp_pagejump.SelectedValue;

        CF.CId = drp_pagejump.SelectedValue;
        CF.Name = Session["Cus_Username"].ToString();
        DataSet ds = CF.Get_Data_From_CID();

        string photo = ds.Tables[0].Rows[0][0].ToString();
        MainImg.Src = "../ClientImage/" + photo + ".jpg";


        txt_tbc.Text = ds.Tables[0].Rows[0]["Tbc_No"].ToString();
        txt_name.Text = ds.Tables[0].Rows[0]["Name"].ToString();
        txt_email.Text = ds.Tables[0].Rows[0]["EmailId"].ToString();
        txt_mobno.Text = ds.Tables[0].Rows[0]["MobileNo"].ToString();
        txt_gender.Text = ds.Tables[0].Rows[0]["Gender"].ToString();
        txt_licenceno.Text = ds.Tables[0].Rows[0]["LicenseNo"].ToString();
        txt_girno.Text = ds.Tables[0].Rows[0]["GirNo"].ToString();
        txt_panno.Text = ds.Tables[0].Rows[0]["PanNo"].ToString();
        txt_Hadd.Text = ds.Tables[0].Rows[0]["H_Address"].ToString();
        txt_Hcity.Text = ds.Tables[0].Rows[0]["H_City"].ToString();
        txt_Hpin.Text = ds.Tables[0].Rows[0]["H_PinNo"].ToString();
        txt_HState.Text = ds.Tables[0].Rows[0]["H_State"].ToString();
        txt_Oadd.Text = ds.Tables[0].Rows[0]["O_Address"].ToString();
        txt_Ocity.Text = ds.Tables[0].Rows[0]["O_City"].ToString();
        txt_Opincode.Text = ds.Tables[0].Rows[0]["O_PinNo"].ToString();
        txt_loanapproval.Text = ds.Tables[0].Rows[0]["LAL"].ToString();
        txt_menno.Text = ds.Tables[0].Rows[0]["MRNNo"].ToString();
        txt_af.Text = ds.Tables[0].Rows[0]["AF"].ToString();
        txt_nri.Text = ds.Tables[0].Rows[0]["NRI"].ToString();
        txt_cp.Text = ds.Tables[0].Rows[0]["CP"].ToString();
    }

    protected void txt_af_TextChanged(object sender, EventArgs e)
    {

    }
    protected void btn_ascii_Click(object sender, EventArgs e)
    {
        Session["id"] = drp_pagejump.SelectedValue;
        Response.Redirect("ASCII.aspx");
    }
}
