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

public partial class Admin_Submission_Complete : System.Web.UI.Page
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
        //DS = AD.Select_Registration_Complete();
        dt = mycon.FillDataTable("SELECT *,ROW_NUMBER() over (order by end_date) as srno from Tbl_registration with(nolock) where status='4' order by End_Date");
        grd_submission_complete.DataSource = dt;
        grd_submission_complete.DataBind();
    }

    protected void LinkButton1_Click(object sender, EventArgs e)
    {

    }
    protected void grd_submission_complete_RowCommand(object sender, GridViewCommandEventArgs e)
    {
        if (e.CommandName == "deletecid")
        {
            int index = Convert.ToInt32(e.CommandArgument);
            Label cid = ((Label)grd_submission_complete.Rows[index].FindControl("Label1"));
            deletecid(cid.Text);
            fillgrid();
        }
        //MyCon mycon = new MyCon();
        //int index = Convert.ToInt32(e.CommandArgument);
        //Label cid = ((Label)grd_submission_complete.Rows[index].FindControl("Label1"));
        //Label rpt = ((Label)grd_submission_complete.Rows[index].FindControl("Label12"));

        //if (rpt.Text == "Disable")
        //{
        //    mycon.ExecutQury("update tbl_registration set rpt='1' where cid='" + cid.Text + "'");
        //}
        //else
        //{
        //    mycon.ExecutQury("update tbl_registration set rpt='0' where cid='" + cid.Text + "'");
        //}
        //fillgrid();                
    }
    public void deletecid(string cid)
    {
        mycon.ExecutQury(@" delete from Tbl_Client_Bpo_Data where CId=@0;
                            delete from Tbl_Help where CId=@0;
                            delete from Tbl_Log where CId=@0;    
                            delete from Tbl_Registration where CId=@0;", cid);
    }
    protected void grd_submission_complete_RowDataBound(object sender, GridViewRowEventArgs e)
    {
        if (e.Row.RowType == DataControlRowType.DataRow)
        {
            Label lbldate = ((Label)e.Row.FindControl("Label12"));
            if (lbldate.Text=="0")
            {
               lbldate.Text = "Disable";
            }
            else
            {
               lbldate.Text = "Enable";

            }
        }
    }
    protected void btn_delete_Click(object sender, EventArgs e)
    {
        foreach (GridViewRow row in grd_submission_complete.Rows)
        {
            CheckBox chk = (CheckBox)row.FindControl("chk");
            if (chk.Checked)
            {
                Label cid = (Label)row.FindControl("Label1");
                deletecid(cid.Text);
            }
        }
        ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Deleted.." + "');", true);
        //mycon.ExecutQury("dbcc shrinkfile('admin_newff',1)");
        //mycon.ExecutQury("dbcc shrinkfile('admin_newff_log',1)");
        fillgrid();
    }
    protected void btn_select_Click(object sender, EventArgs e)
    {
        int from = Convert.ToInt32(txt_from.Text);
        int to = Convert.ToInt32(txt_to.Text);
        int total = to - from + 1;
        for (int i = 0; i < total; i++)
        {
            CheckBox chkb = ((CheckBox)grd_submission_complete.Rows[from++ - 1].FindControl("chk"));
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
            CheckBox chkb = ((CheckBox)grd_submission_complete.Rows[from++ - 1].FindControl("chk"));
            chkb.Checked = false;
        }
    }
}
