using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.IO;
using System.Data;

public partial class Franchisee_Logout : System.Web.UI.Page
{
    clsAdmin AD = new clsAdmin();
    protected void Page_Load(object sender, EventArgs e)
    {
       
        if (Session["Cus_Username"] == null)
        {
            Response.Redirect("../Default.aspx");
        }
        try
        {
             MyCon mycon = new MyCon();
            DataTable dt   = new DataTable();
            dt=mycon.FillDataTable("Select login from Tbl_Registration where cid='" + Session["Cus_Username"].ToString()  + "'");
            string rndno = dt.Rows[0][0].ToString();
            if (rndno.Trim() == Session["rndno"].ToString())
            {
                
                mycon.ExecutQury("update Tbl_Registration set login='0' where cid='" + Session["Cus_Username"].ToString() + " '");

            }

         

        }
        catch
        {
        }
        AD.cid = Session["Cus_Username"].ToString();
        AD.Work = "*****************************Logout--" + Session["Cus_Username"].ToString() + "     " + DateTime.Now;
        AD.Insert_Log();      

        if (Session["Cus_Username"] != null)
        {
            Session.Abandon();
            Session.RemoveAll();

            Response.Redirect("../Default.aspx");
        }
    }
}