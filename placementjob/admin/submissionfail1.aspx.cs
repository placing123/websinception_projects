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
using System.Drawing;


public partial class Franchisee_Plan : System.Web.UI.Page
{
    DataSet DS = new DataSet();
    clsAdmin AD = new clsAdmin();
    DataTable dt = new DataTable();
    MyCon mycon = new MyCon();
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            filldata();
        }
    }
    public void filldata()
    {
        dt = mycon.FillDataTable(@"SELECT *,
                                (select count(*) from Tbl_Client_Bpo_Data where CId = tbl_Registration.CId AND (Status = '2' OR Status = '3')) as completeforms 
                                from Tbl_registration with(nolock) where status IN('3' , '3_1')  and fid='HO' order by end_date desc");
        //DS = AD.Select_Registration_Fail();
        grd_submission_fail.DataSource = dt;
        grd_submission_fail.DataBind();
    }
    protected void grd_submission_fail_RowDataBound(object sender, GridViewRowEventArgs e)
    {
        if (e.Row.RowType == DataControlRowType.DataRow)
        {
            Label lbldate = ((Label)e.Row.FindControl("lblstatus"));
            if (lbldate.Text == "3")
            {
                lbldate.Text = "Submission Fail";
                //lbldate.ForeColor = System.Drawing.Color.Red;
                e.Row.BackColor = Color.Red;

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
        if (e.CommandName == "updateremark")
        {
            int index = Convert.ToInt32(e.CommandArgument);
            Label id = ((Label)grd_submission_fail.Rows[index].FindControl("Label3"));
            TextBox txt_remark = ((TextBox)grd_submission_fail.Rows[index].FindControl("TextBox12"));
            mycon.ExecutQury("update tbl_registration set subfailremark=@0 where id=@1 ", txt_remark.Text, id.Text);
            filldata();
        }
    }
}