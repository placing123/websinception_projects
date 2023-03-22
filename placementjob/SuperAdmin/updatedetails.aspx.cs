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

public partial class Admin_Plan : System.Web.UI.Page
{
    DataTable dt = new DataTable();
    DataSet ds = new DataSet();
    MyCon mycon = new MyCon();
    protected void Page_Load(object sender, EventArgs e)
    {
    }
    protected void btn_search_Click(object sender, EventArgs e)
    {
        ds = mycon.FillDataset("select CId,Name,Address,MobileNo,EmailId,PlanId,callername,FId from Tbl_Registration with(nolock) where cid = '" + txt_cid.Text + "'");
        if (ds.Tables[0].Rows.Count == 1)
        {
            lbl_cid.Text = ds.Tables[0].Rows[0]["cid"].ToString();
            txt_name.Text = ds.Tables[0].Rows[0]["Name"].ToString();
            txt_address.Text = ds.Tables[0].Rows[0]["Address"].ToString();
            txt_mobileno.Text = ds.Tables[0].Rows[0]["MobileNo"].ToString();
            txt_emailid.Text = ds.Tables[0].Rows[0]["EmailId"].ToString();
            txt_planid.Text = ds.Tables[0].Rows[0]["PlanId"].ToString();
            txt_callername.Text = ds.Tables[0].Rows[0]["callername"].ToString();
            txt_franchiseename.Text = ds.Tables[0].Rows[0]["FId"].ToString();
        }
        else
        {
            cleartext();
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Detail not found!" + "');", true);
        }
    }
    private void cleartext()
    {
        lbl_cid.Text = "";
        txt_name.Text = "";
        txt_address.Text = "";
        txt_mobileno.Text = "";
        txt_emailid.Text = "";
        txt_planid.Text = "";
        txt_callername.Text = "";
        txt_franchiseename.Text = "";
    }
    protected void btn_update_Click(object sender, EventArgs e)
    {
        if (lbl_cid.Text != "")
        {
            mycon.ExecutQury("update Tbl_Registration set Name=@0,Address=@1,MobileNo=@2,EmailId=@3,PlanId=@4,callername=@5,FId=@6 where cid = @7",
                            txt_name.Text, txt_address.Text, txt_mobileno.Text, txt_emailid.Text, txt_planid.Text, txt_callername.Text,txt_franchiseename.Text, lbl_cid.Text);
            cleartext();
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Detail Updated." + "');", true);
        }
        else
        {
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "No search found." + "');", true);
        }
    }
}
