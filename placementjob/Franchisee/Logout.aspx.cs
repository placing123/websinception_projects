using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class Franchisee_Logout : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (Session["Fran_Username"] == null)
        {
            Response.Redirect("Default.aspx");
        }

        if (Session["Fran_Username"] != null)
        {
            Session.Abandon();
            Session.RemoveAll();

            Response.Redirect("Default.aspx");
        }
    }
}