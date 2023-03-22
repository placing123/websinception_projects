using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;

public partial class Admin_Login : System.Web.UI.Page
{
    MyCon mycon = new MyCon();
    DataTable dt = new DataTable();
    Random random = new Random();

    protected void Page_Load(object sender, EventArgs e)
    {

    }
    protected void btn_cler_Click(object sender, EventArgs e)
    {
        string[] textbox = { "Tbc_No", "Name", "EmailId", "MobileNo", "Gender", "LicenseNo", "GirNo", "PanNo", "H_Address", "H_City", "H_PinNo", "H_State", 
                               "O_Address", "O_City", "O_PinNo", "LAL", "MRNNo", "AF", "NRI", "CP" };


       
        dt = mycon.FillDataTable("SELECT TOP (" + txt_form.Text + ") [index] from tbl_client_bpo_data with(nolock) where cid='" + txt_cid.Text + "' and status='2' ORDER BY newid()");
        for (int i = 0; i < dt.Rows.Count; i++)
        {
            string field = textbox[random.Next(textbox.Length)];
            System.Threading.Thread.Sleep(100);
            mycon.ExecutQury("update tbl_client_bpo_data set " + field + "=" + field + "+' ',status='3',spaceerror='1' where [index]='" + dt.Rows[i]["index"].ToString() + "'");

        }
        ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Done." + "');", true);



    }
    private void EndTask(string cid)
    {

    }

}