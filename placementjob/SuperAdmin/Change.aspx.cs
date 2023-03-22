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
    MyCon mycon = new MyCon();
    protected void Page_Load(object sender, EventArgs e)
    {

    }

    protected void btn_show_Click(object sender, EventArgs e)
    {
        try
        {            
            GridView1.DataSource = mycon.FillDataTable("select * from tbl_chang where cid='" + txt_cid.Text.Trim() + "' order by id desc");
            GridView1.DataBind();
        }
        catch 
        {            
        }
    }
}
