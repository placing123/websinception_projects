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

public partial class Admin_Plan : System.Web.UI.Page
{
    DataSet ds = new DataSet();
    clsAdmin AD = new clsAdmin();
    MyCon mycon = new MyCon();
    DataTable dt = new DataTable();
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            filldata();
        }
    }
    public void filldata()
    {
        dt = mycon.FillDataTable("select ROW_NUMBER() over (order by id) as srno,id,callername from tbl_callermaster with(nolock) order by id");
        grd_plan.DataSource = dt;
        grd_plan.DataBind();
    }
    protected void btn_add_Click(object sender, EventArgs e)
    {
        mycon.ExecutQury("insert into tbl_callermaster(callername) values(@0)", txt_callername.Text);
        filldata();
        txt_callername.Text = "";
    }
    protected void grd_plan_RowCommand(object sender, GridViewCommandEventArgs e)
    {
        if (e.CommandName == "deletecaller")
        {
            int index = Convert.ToInt32(e.CommandArgument);
            Label id = ((Label)grd_plan.Rows[index].FindControl("Label1"));
            mycon.ExecutQury("delete from tbl_callermaster where id=" + id.Text);
            filldata();
        }
    }
}
