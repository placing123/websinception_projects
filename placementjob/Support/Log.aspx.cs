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

public partial class Admin_Log : System.Web.UI.Page
{
    clsAdmin AD = new clsAdmin();
    DataSet ds = new DataSet();
    protected void Page_Load(object sender, EventArgs e)
    {

    }

    protected void btn_show_Click(object sender, EventArgs e)
    {
        try
        {
            AD.cid = txt_cid.Text.Trim();
            ds=AD.Select_Log();
            GridView1.DataSource = ds;
            GridView1.DataBind();
        }
        catch 
        {            
        }
    }
}
