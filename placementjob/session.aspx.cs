using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class session : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        try
        {
            Label1.Text = Session["login"].ToString();
            Session["login"] = "";
        }
        catch
        {
        }
    }
    protected void Button1_Click(object sender, EventArgs e)
    {
        try
        {
            MyCon mycon = new MyCon();
            mycon.ExecutQury("update Tbl_Registration set login='0' where cid='" + Session["Cus_Username"].ToString() + " '");
            Response.Redirect("default.aspx");
        }
        catch
        {
        }
    }
}