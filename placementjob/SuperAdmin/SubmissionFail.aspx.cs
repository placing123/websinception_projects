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

public partial class Admin_SubmissionFail : System.Web.UI.Page
{
    clsAdmin AD = new clsAdmin();
    DataSet DS = new DataSet();
    DataTable dt = new DataTable();
    MyCon mycon = new MyCon();
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            fillgrid();
        }
    }
    public void fillgrid()
    {
        dt = mycon.FillDataTable("SELECT *,ROW_NUMBER() over (order by end_date) as srno from Tbl_registration with(nolock) where status IN('3' , '3_1') order by end_date");
        //DS = AD.Select_Registration_Fail();
        grd_submission_fail.DataSource = dt;
        grd_submission_fail.DataBind();
    }

    protected void grd_submission_fail_SelectedIndexChanged(object sender, EventArgs e)
    {

    }
    protected void grd_submission_fail_RowDataBound(object sender, GridViewRowEventArgs e)
    {
        if (e.Row.RowType == DataControlRowType.DataRow)
        {
            Label lbldate = ((Label)e.Row.FindControl("lblstatus"));
            if (lbldate.Text == "3")
            {
                lbldate.Text = "Submission Fail";
                lbldate.ForeColor = System.Drawing.Color.Red;
            }
            if (lbldate.Text == "3_1")
            {
                lbldate.Text = "Not Submited";
                lbldate.ForeColor = System.Drawing.Color.Red;
            }
        }
    }
    protected void btn_change_Click(object sender, EventArgs e)
    {
        string cid = Request.QueryString["cid"].ToString();
        Session["Cus_Username"] = cid;
        Session["QC"] = "Fail";
        //Session["Fail"] = "1";
        Response.Redirect("../Customer/Admin_Default.aspx");
    }
    protected void btn_delete_Click(object sender, EventArgs e)
    {
        AD.cid = Request.QueryString["cid"].ToString();
        AD.Delete_Client_cid();
        fillgrid();
        ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Client Deleted Succsessfuly...." + "');", true);
    }
    protected void LinkButton1_Click(object sender, EventArgs e)
    {
        string cid = Request.QueryString["cid"].ToString();
        AD.cid = cid.ToString();
        AD.status = "5";
        AD.Update_Registration_Status();
        fillgrid();
    }
    protected void grd_submission_fail_RowCommand(object sender, GridViewCommandEventArgs e)
    {
        if (e.CommandName.ToString() == "check")
        {
            int index = Convert.ToInt32(e.CommandArgument);
            Label cid = ((Label)grd_submission_fail.Rows[index].FindControl("Label1"));
            TextBox form = ((TextBox)grd_submission_fail.Rows[index].FindControl("txt_form"));
            string str = cid.Text;
            AD.id = str;
            DS = AD.Select_Registration_All();
            AD.id = str;
            AD.Update_Submited_Client();

            AD.cid = str;
            if (DS.Tables[0].Rows[0]["status"].ToString() == "3")
            {
                AD.Qc = "Faill";
            }
            else
            {
                AD.Qc = "Not Submitted";
            }
            AD.Update_Qc();
            if (form.Text.Trim() == "")
            {
                AD.cid = str;
                AD.form = "0";
                AD.Update_Registration_Form();
            }
            else
            {
                AD.cid = str;
                AD.form = form.Text;
                AD.Update_Registration_Form();
            }
            fillgrid();
        }
    }
    protected void btnsubarr_Click(object sender, EventArgs e)
    {
        foreach (GridViewRow row in grd_submission_fail.Rows)
        {
            CheckBox chk = (CheckBox)row.FindControl("chk");
            if (chk.Checked)
            {
                Label cid = (Label)row.FindControl("Label1");
                TextBox form = ((TextBox)row.FindControl("txtform"));
                string str = cid.Text;
                //string str = Request.QueryString["cid"].ToString();
                AD.id = str;
                DS = AD.Select_Registration_All();
                //if (DS.Tables[0].Rows[0]["Recevied"].ToString() == "2")
                //{
                AD.id = str;
                AD.Update_Submited_Client();// this will make status 4
                AD.cid = str;
                if (DS.Tables[0].Rows[0]["status"].ToString() == "3")
                {
                    AD.Qc = "Faill";
                }
                else
                {
                    AD.Qc = "Not Submitted";
                }
                AD.Update_Qc(); //this will update qc field
                if (txtform.Text.Trim() == "")
                {
                    AD.cid = str;
                    AD.form = "0";
                    AD.Update_Registration_Form();//this will update form field
                }
                else
                {
                    AD.cid = str;
                    AD.form = txtform.Text;
                    AD.Update_Registration_Form();//this will update form field
                }
                fillgrid();
                ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "QC Checked Sucsessfully .." + "');", true);
                //}
                //else
                //{
                //    ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Aggriment Not Received..." + "');", true);
                //}
            }

        }


    }
    private void selectarr(bool status)
    {
        Object obj;
        CheckBox chk;
        try
        {

            foreach (GridViewRow row in grd_submission_fail.Rows)
            {
                obj = row.FindControl("chk");
                if (obj != null)
                {
                    chk = (CheckBox)obj;
                    chk.Checked = status;
                }
            }
        }
        catch (Exception ex)
        {

        }
    }
    protected void btnselarr_Click(object sender, EventArgs e)
    {
        if (btnselarr.Text == "Select All")
        {
            selectarr(true);
            btnselarr.Text = "DeSelect All";
        }
        else
        {
            selectarr(false);
            btnselarr.Text = "Select All";
        }
    }
    protected void btnsubagg_Click(object sender, EventArgs e)
    {
        foreach (GridViewRow row in grd_submission_fail.Rows)
        {
            CheckBox chk = (CheckBox)row.FindControl("chk");
            if (chk.Checked)
            {
                Label cid = (Label)row.FindControl("Label1");
                AD.cid = cid.Text;
                AD.status = "5";
                AD.Update_Registration_Status();

            }
        }
        fillgrid();
        ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Checked Sucsessfully .." + "');", true);
    }
    protected void btn_select_Click(object sender, EventArgs e)
    {
        int from = Convert.ToInt32(txt_from.Text);
        int to = Convert.ToInt32(txt_to.Text);
        int total = to - from + 1;
        for (int i = 0; i < total; i++)
        {
            CheckBox chkb = ((CheckBox)grd_submission_fail.Rows[from++ - 1].FindControl("chk"));
            chkb.Checked = true;
        }
    }
    protected void btn_deselect_Click(object sender, EventArgs e)
    {
        int from = Convert.ToInt32(txt_from.Text);
        int to = Convert.ToInt32(txt_to.Text);
        int total = to - from + 1;
        for (int i = 0; i < total; i++)
        {
            CheckBox chkb = ((CheckBox)grd_submission_fail.Rows[from++ - 1].FindControl("chk"));
            chkb.Checked = false;
        }
    }
    protected void btn_Delete_Click(object sender, EventArgs e)
    {
        int from = Convert.ToInt32(txt_from.Text);
        int to = Convert.ToInt32(txt_to.Text);
        int total = to - from + 1;
        for (int i = 0; i < total; i++)
        {
            CheckBox chkb = ((CheckBox)grd_submission_fail.Rows[from++ - 1].FindControl("chk"));
            chkb.Checked = true;
            AD.Delete_Client_cid();
            fillgrid();
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Client Deleted Succsessfuly...." + "');", true);
        
        }
    }
}
