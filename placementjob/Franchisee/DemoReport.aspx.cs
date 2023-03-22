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
    MyCon mycon = new MyCon();
    DataTable dt = new DataTable();
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            fillgrid();
        }
    }
    public void fillgrid()
    {
        grd_submission_fail.DataSource = mycon.FillDataTable("SELECT cid,name,status,end_date from Tbl_registration with(nolock) where status IN('3' , '3_1') and fid='F0411201423' "+
                                                             "and planid in ('P101220146','P193201510','P26220145','P29820142') order by end_date");
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
    protected void grd_submission_fail_RowCommand(object sender, GridViewCommandEventArgs e)
    {
        if (e.CommandName.ToString() == "check")
        {
            int index = Convert.ToInt32(e.CommandArgument);
            Label cid = ((Label)grd_submission_fail.Rows[index].FindControl("Label1"));
            Label status = ((Label)grd_submission_fail.Rows[index].FindControl("lblstatus"));
            if (status.Text == "3")
            {
                mycon.ExecutQury("update Tbl_registration set status='4',Qc='Fail',form='0' where cid='" + cid.Text + "'");
            }
            else
            {
                mycon.ExecutQury("update Tbl_registration set status='4',Qc='Not Submitted',form='0' where cid='" + cid.Text + "'");
            }
            fillgrid();
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Qc Sended." + "');", true);
        }
    }
}
