using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;
using System.Text.RegularExpressions;

public partial class Admin_AutoFill : System.Web.UI.Page
{
    MyCon mycon = new MyCon();
    DataTable dt = new DataTable();

    protected void Page_Load(object sender, EventArgs e)
    {

    }
    protected void btn_unlock_range_Click(object sender, EventArgs e)
    {
        if (txt_from.Text != "" && txt_to.Text != "" && txt_cid.Text != "")
        {
            mycon.ExecutQury("update tbl_client_bpo_data set date='" + mycon.indianTime().AddDays(1).ToString("yyyy-MM-dd") + "' where cid='" + txt_cid.Text + "' and sr_no>=" + txt_from.Text + "and sr_no<=" + txt_to.Text + "");
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Done." + "');", true);
        }
        else
        {
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Insert From and To value with Cusotmer ID." + "');", true);
        }
    }
    protected void btn_unlock_form_Click(object sender, EventArgs e)
    {
        if (txt_form.Text != "" && txt_cid.Text != "")
        {
            string srno = "";
            string[] temp = txt_form.Text.Split(new string[] { System.Environment.NewLine }, StringSplitOptions.None);
            for (int i = 0; i < temp.Length; i++)
            {
                if (i==temp.Length-1)
                {
                    srno += "'" + temp[i] + "'";
                }
                else
                {
                    srno += "'" + temp[i] + "',";
                }
            }

            mycon.ExecutQury("update tbl_client_bpo_data set date='" + mycon.indianTime().AddDays(1).ToString("yyyy-MM-dd") + "' where cid='" + txt_cid.Text + "' and sr_no in (" + srno + ")");
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Done." + "');", true);
        }
        else
        {
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Insert forms value with Cusotmer ID." + "');", true);
        }
    }
}