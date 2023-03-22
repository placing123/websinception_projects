using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;

public partial class Customer_logs : System.Web.UI.Page
{
    clsAdmin AD = new clsAdmin();
    DataSet ds = new DataSet();
    protected void Page_Load(object sender, EventArgs e)
    {
        logs();
    }
    public void logs()
    {
        AD.cid = Session["Cus_Username"].ToString();
        ds = AD.Select_Log();
        GridView1.DataSource = ds;
        GridView1.DataBind();
    }
}