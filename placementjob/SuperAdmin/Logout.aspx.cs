using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class Admin_Logout : System.Web.UI.Page
{
    clsAdmin AD = new clsAdmin();
    protected void Page_Load(object sender, EventArgs e)
    {

    }
    protected void btn_show_Click(object sender, EventArgs e)
    {
        AD.cid = txt_cid.Text;
        AD.Work = "*****************************Logout--" + txt_cid.Text + "     " + DateTime.Now;
        AD.Insert_Log();     
    }
}