using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class Admin_Login : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {

    }
    protected void btn_cler_Click(object sender, EventArgs e)
    {
        try
        {
            if (txt_cid.Text.Trim()!="")
            {
                MyCon mycon = new MyCon();
                mycon.ExecutQury("update Tbl_Registration set login='0' where cid='" + txt_cid.Text + " '");
                ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Cleare.." + "');", true);
            }
            
        }
        catch 
        {
        }
        
    }
}