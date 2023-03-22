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
    MyCon mycon = new MyCon();
    clsAdmin AD = new clsAdmin();
    DataSet DS = new DataSet();
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            filldata();
        }
    }
    public void filldata()
    {
        //DS = AD.Select_Registration_Complete();
        DS = mycon.FillDataset("SELECT * from Tbl_registration with(nolock) where status='4' and fid='HO' order by end_date desc");
        grd_submission_complete.DataSource = DS.Tables[0];
        grd_submission_complete.DataBind();
    }
    protected void grd_submission_complete_RowCommand(object sender, GridViewCommandEventArgs e)
    {
        if (e.CommandName == "updateremark")
        {
            int index = Convert.ToInt32(e.CommandArgument);
            Label id = ((Label)grd_submission_complete.Rows[index].FindControl("Label3"));
            TextBox txt_remark = ((TextBox)grd_submission_complete.Rows[index].FindControl("TextBox12"));
            mycon.ExecutQury("update tbl_registration set subcomplremark=@0 where id=@1 ", txt_remark.Text, id.Text);
            filldata();
        }
    }
}
