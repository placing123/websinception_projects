using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;

public partial class Admin_Login : System.Web.UI.Page
{
    clsAdmin AD = new clsAdmin();  
    protected void Page_Load(object sender, EventArgs e)
    {

    }
    protected void btn_cler_Click(object sender, EventArgs e)
    {
        try
        {
            AD.id = txt_cid.Text;
            AD.Update_Client_Deactivate();            
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Done." + "');", true);
        }
        catch
        {
        }
    }
}