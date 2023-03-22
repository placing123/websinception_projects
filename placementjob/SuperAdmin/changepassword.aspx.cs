using System;
using System.Collections;
using System.Configuration;
using System.Data;
using System.Linq;
using System.Net;
using System.Net.Mail;
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
    MyCon mycon = new MyCon();
    Random rand = new Random();


    protected void Page_Load(object sender, EventArgs e)
    {

    }
    protected void but_submit_Click(object sender, EventArgs e)
    {
        string[] arr = new string[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };
        string pass = "";
        string temp = "";
        for (int i = 0; i < 5; i++)
        {
            temp = arr[rand.Next(0, arr.Length)];
            pass += temp;
        }
        mycon.ExecutQury("update tbl_registration set Password='" + pass + "' where cid='" + txt_regno.Text + "'");
        ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Password Reset." + "');", true);
    }
    protected void Button1_Click(object sender, EventArgs e)
    {
        if (txt_regno.Text != "" && txt_newpassword.Text != "")
        {
            mycon.ExecutQury("update tbl_registration set Password='" + txt_newpassword.Text + "' where cid='" + txt_regno.Text + "'");
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Password Reset." + "');", true);
        }
        else
        {
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "all field are required." + "');", true);
        }

    }
}
