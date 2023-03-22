using System;
using System.Collections;
using System.Configuration;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using System.Xml.Linq;

public partial class Admin_AdminMaster : System.Web.UI.MasterPage
{
    protected void Page_Load(object sender, EventArgs e)
    {
        try
        {
            lbl_username.Text = Session["username"].ToString();
            if (Session["username"].ToString() == "" || Session["type"].ToString() != "C")
            {
                Response.Redirect("default.aspx");

            }
        }

        catch
        {
            Response.Redirect("default.aspx");
        }
        // lbl_time.Text = DateTime.Now.ToShortDateString().ToString() +" <br> " + DateTime.Now.ToLongTimeString().ToString();

    }

    protected void b_logout_Click(object sender, EventArgs e)
    {
        try
        {

            Session.Clear();
            Session.RemoveAll();
            Response.Redirect("default.aspx");
        }
        catch
        {

        }

        //Session["username"] = "";
    }
}
