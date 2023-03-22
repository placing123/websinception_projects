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

public partial class Admin_SubmitedClient : System.Web.UI.Page
{
    clsAdmin AD = new clsAdmin();
    DataSet DS = new DataSet();
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            fillgrid();
        }
    }
    public void fillgrid()
    {
        DS=AD.Select_Registration_Submitted();
        grd_submitted.DataSource = DS;
        grd_submitted.DataBind();
    }
    protected void Check_Click(object sender, EventArgs e)
    {
        string str = Request.QueryString["cid"].ToString();
        AD.id = str;
        DS = AD.Select_Registration_All();
        if (DS.Tables[0].Rows[0]["Recevied"].ToString() == "2")
        {
            AD.id = str;
            AD.Update_Submited_Client();

            AD.cid = str;
            AD.Qc = "Pass";
            AD.Update_Qc();


            fillgrid();
        }
        else
        {
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Aggriment Not Received..." + "');", true);
        }
    }
    protected void btn_change_Click(object sender, EventArgs e)
    {
        string cid=Request.QueryString["cid"].ToString();
        Session["Cus_Username"] = cid;
        Session["QC"] = "Pass";
        //Session["Fail"] = "0";
        Response.Redirect("../Customer/Admin_Default.aspx");
   
    }

    protected void btn_change_Fail_Click(object sender, EventArgs e)
    {
        string cid = Request.QueryString["cid"].ToString();
        Session["Cus_Username"] = cid;
        Session["QC"] = "Fail";
        //Session["Fail11"] = "0";
        Response.Redirect("../Customer/Admin_Default.aspx");

    }
    protected void LinkButton1_Click(object sender, EventArgs e)
    {
        string cid = Request.QueryString["cid"].ToString();
        AD.cid = cid.ToString();
        AD.status = "5";
        AD.Update_Registration_Status();
        fillgrid();
    }
}
