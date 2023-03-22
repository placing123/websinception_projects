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

public partial class Admin_inquiry : System.Web.UI.Page
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
        DS = AD.Select_Inquiry();
        grd_Inquiry.DataSource = DS;
        grd_Inquiry.DataBind();
    }
    protected void btn_check_Click(object sender, EventArgs e)
    {

    }
    protected void grd_Inquiry_SelectedIndexChanged(object sender, EventArgs e)
    {

    }
    protected void grd_Inquiry_RowCommand(object sender, GridViewCommandEventArgs e)
    {
         int index = Convert.ToInt32(e.CommandArgument);
         Label id = ((Label)grd_Inquiry.Rows[index].FindControl("Label1"));
         AD.id = id.Text;
         AD.Update_Inquiry_Status();
         fillgrid();

    }
}
