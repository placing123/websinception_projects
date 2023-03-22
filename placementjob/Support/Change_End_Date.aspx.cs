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

public partial class Admin_Date_Change : System.Web.UI.Page
{
    clsAdmin AD = new clsAdmin();
    DataSet DS = new DataSet();
    MyCon mycon = new MyCon();

    protected void Page_Load(object sender, EventArgs e)
    {
    }
    protected void txt_cid_TextChanged(object sender, EventArgs e)
    {
        lbl_current_date.Text = mycon.AdExecScalar("select end_date from tbl_registration where cid='" + txt_cid.Text + "'");
    }
    protected void btn_date_change_Click(object sender, EventArgs e)
    {
        mycon.ExecutQury("update tbl_registration set end_date='" + txt_date.Text + "',status='1' where cid='" + txt_cid.Text + "'");
        ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Date is updated" + "');", true);
    }
}

