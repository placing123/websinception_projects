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

public partial class Admin_changepassword : System.Web.UI.Page
{
    clsAdmin AD = new clsAdmin();
    DataSet DS = new DataSet();
    DataTable Dt = new DataTable();
    MyCon Mycon = new MyCon();
    protected void Page_Load(object sender, EventArgs e)
    {

    }
    protected void but_submit_Click(object sender, EventArgs e)
    {
        string oldpsw = txt_Old.Text;
        string newpsw = txt_New.Text;
        string confirmpsw = txt_Confirm.Text;
        string user = Session["Cus_Username"].ToString();

        if (newpsw == confirmpsw)
        {
            AD.username = user;
            AD.password = oldpsw;

            Dt = Mycon.FillDataTable("select * from tbl_registration where cid='" + user + "' and Password='" + oldpsw + "' ");
            //DS = AD.admin_login();
            if (Dt.Rows.Count == 1)
            {               
                Mycon.FillDataTable("update tbl_registration set Password='" + newpsw + "' where cid='" + user + "'");
                ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Password Successfully Change..." + "');", true);
                txt_Old.Text = "";
                txt_New.Text = "";
                txt_Confirm.Text = "";
            }
            else
                ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Old Password is wrong..." + "');", true);
        }
        else
        {
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "New And Confirm Password not Match..." + "');", true);

        }
    }
}
