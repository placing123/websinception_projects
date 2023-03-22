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

public partial class Agreement : System.Web.UI.Page
{
    clsAdmin AD = new clsAdmin();
    clsFranchisee CF = new clsFranchisee();
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
        AD.status = "5";
       DS= AD.Select_Registration_Status();
       grd_submission_fail.DataSource = DS;
       grd_submission_fail.DataBind();
       AD.status = "6";
       DS = AD.Select_Registration_Status();
       GridView1.DataSource = DS;
       GridView1.DataBind();
    }
    protected void Check_Click(object sender, EventArgs e)
    {

        CF.CId = Request.QueryString["cid"].ToString().Trim(); 

        DS = CF.Check_Cutoff_by_CId();

        DataSet ds2 = CF.Check_Cutoff2();
        DataSet ds3 = CF.Check_Cutoff3();

        if (Convert.ToInt32(DS.Tables[0].Rows[0]["QcCutOff"].ToString()) <= Convert.ToInt32(ds2.Tables[0].Rows[0][0].ToString()))
        {
           
            CF.Status = "2";
            CF.Chnage_Status_Userlogin();

            
        }

        else
        {
           
            CF.Status = "3";
            CF.Chnage_Status_Userlogin();

            
        }
        fillgrid();
    }
    protected void btn_change_Click(object sender, EventArgs e)
    {
        string cid = Request.QueryString["cid"].ToString();
        AD.cid = cid.ToString();
        AD.status = "6";
        AD.Update_Registration_Status();
        fillgrid();

    }
    protected void grd_submission_fail_RowDataBound(object sender, GridViewRowEventArgs e)
    {
        if (e.Row.RowType == DataControlRowType.DataRow)
        {
            Label lbldate = ((Label)e.Row.FindControl("lbl"));

            if (lbldate.Text == "0")
            {
                lbldate.Text = "Online";
                lbldate.ForeColor = System.Drawing.Color.Green;
            }


            if (lbldate.Text == "1")
            {
                lbldate.Text = "Offline";
                lbldate.ForeColor = System.Drawing.Color.Red;
            }
        }
    }
    protected void grd_submission_fail_SelectedIndexChanged(object sender, EventArgs e)
    {

    }
    protected void LinkButton1_Click(object sender, EventArgs e)
    {
        string cid = Request.QueryString["cid"].ToString();
        AD.cid = cid.ToString();
        AD.status = "5";
        AD.Update_Registration_Status();
        fillgrid();
    }
    protected void btn_final_Click(object sender, EventArgs e)
    {
        string cid = Request.QueryString["cid"].ToString();
        AD.cid = cid.ToString();
        AD.status = "4";
        AD.Update_Registration_Status();
        fillgrid();

    }
    protected void GridView1_SelectedIndexChanged(object sender, EventArgs e)
    {

    }
    protected void GridView1_RowDataBound(object sender, GridViewRowEventArgs e)
    {
        if (e.Row.RowType == DataControlRowType.DataRow)
        {
            Label lbldate = ((Label)e.Row.FindControl("lbl"));

            if (lbldate.Text == "0")
            {
                lbldate.Text = "Online";
                lbldate.ForeColor = System.Drawing.Color.Green;
            }


            if (lbldate.Text == "1")
            {
                lbldate.Text = "Offline";
                lbldate.ForeColor = System.Drawing.Color.Red;
            }
        }
    }
}
