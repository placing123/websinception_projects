using System;
using System.Data;
using System.Configuration;
using System.Linq;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Xml.Linq;
using System.Data.SqlClient;
using DLL;
using System.IO;
using System.Collections.Generic;
using System.Text;

public partial class Customer_WorkloadSubmit : System.Web.UI.Page
{
    clsAdmin AD = new clsAdmin();
    DataSet DS = new DataSet();

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            MainImg.Src = "img/1.jpg";
        }
    }
   
   
   
    protected void Page_PreRender(Object sender, EventArgs e)
    {
        txt_tbc.Attributes.Add("autocomplete", "off");
        Name.Attributes.Add("autocomplete", "off");
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
    
    protected void cleare()
    {
        txt_tbc.Text = "";
        Name.Text = "";
        txt_email.Text = "";
        txt_mobno.Text = "";
        txt_gender.Text = "";
        txt_licenceno.Text = "";
        txt_girno.Text = "";
        txt_panno.Text = "";
        txt_Hadd.Text = "";
        txt_Hcity.Text = "";
        txt_Hpin.Text = "";
        txt_HState.Text = "";
        txt_Oadd.Text = "";
        txt_Ocity.Text = "";
        txt_Opincode.Text = "";
        txt_loanapproval.Text = "";
        txt_menno.Text = "";
        txt_af.Text = "";
        txt_nri.Text = "";
        txt_cp.Text = "";
    }
    protected void drp_pagejump_SelectedIndexChanged(object sender, EventArgs e)
    {
        MainImg.Src = "img/" + drp_pagejump.Text + ".jpg";
        cleare();
    }
   
    protected void btnsubmit_Click(object sender, EventArgs e)
    {
        

    }
    protected void btnreset_Click1(object sender, EventArgs e)
    {
       
    }
    protected void txt_areac2_TextChanged(object sender, EventArgs e)
    {        

    }
   
    protected void txt_medi_TextChanged(object sender, EventArgs e)
    {
      
    }
    protected void txt_srno_TextChanged(object sender, EventArgs e)
    {

    }
    protected void btnreset_Click(object sender, ImageClickEventArgs e)
    {

    }
    protected void btnsubmit_Click(object sender, ImageClickEventArgs e)
    {
        try
        {
            
            Session["Cus_Username"] = "DEMO";
            AD.id = "DEMO";
            DS = AD.Select_Registration_All();
            String ipAddress = System.Web.HttpContext.Current.Request.UserHostAddress;

            HttpRequest currentRequest = HttpContext.Current.Request;
            ipAddress = currentRequest.ServerVariables["HTTP_X_FORWARDED_FOR"];
            if (ipAddress == null || ipAddress.ToLower() == "unknown")
                ipAddress = currentRequest.ServerVariables["REMOTE_ADDR"];
            string str = Session["demo"].ToString();
            AD.cid = "DEMO";
            AD.Work = "User=" + str + "       Page=" + drp_pagejump .Text+"    "+ ipAddress + Session["Cus_Username"].ToString() + "     " + DateTime.Now;
            AD.Insert_Log();
            drp_pagejump.SelectedIndex = drp_pagejump.SelectedIndex + 1;
            MainImg.Src = "img/" + drp_pagejump.Text + ".jpg";
        }
        catch
        {

            drp_pagejump.SelectedIndex = 0;
            MainImg.Src = "img/" + drp_pagejump.Text + ".jpg";
        }
        cleare();
    }
}