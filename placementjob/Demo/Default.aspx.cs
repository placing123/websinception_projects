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
using System.Data.SqlClient;
using System.Collections.Generic;
using System.IO;


public partial class Admin_Default : System.Web.UI.Page
{
    clsAdmin AD = new clsAdmin();
    DataSet DS = new DataSet();
    protected void Page_Load(object sender, EventArgs e)
    {
        
    }
    private string GenerateRandomCode()
    {
        Random r = new Random();
        string s = "";
        for (int j = 0; j < 5; j++)
        {
            int i = r.Next(3);
            int ch;
            switch (i)
            {
                case 1:
                    ch = r.Next(0, 9);
                    s = s + ch.ToString();
                    break;
                case 2:
                    ch = r.Next(65, 90);
                    s = s + Convert.ToChar(ch).ToString();
                    break;
                case 3:
                    ch = r.Next(97, 122);
                    s = s + Convert.ToChar(ch).ToString();
                    break;
                default:
                    ch = r.Next(97, 122);
                    s = s + Convert.ToChar(ch).ToString();
                    break;
            }
            r.NextDouble();
            r.Next(100, 1999);
        }
        return s;
    }
    protected void b_login_Click(object sender, EventArgs e)
    {
        if (txt_userid.Text=="demo" && txt_password.Text=="demo")
        {
            Session["demo"] = GenerateRandomCode();
            Session["Cus_Username"] = txt_userid.Text.Trim();
            AD.id = txt_userid.Text.Trim();
            DS = AD.Select_Registration_All();
            String ipAddress = System.Web.HttpContext.Current.Request.UserHostAddress;

            HttpRequest currentRequest = HttpContext.Current.Request;
            ipAddress = currentRequest.ServerVariables["HTTP_X_FORWARDED_FOR"];
            if (ipAddress == null || ipAddress.ToLower() == "unknown")
                ipAddress = currentRequest.ServerVariables["REMOTE_ADDR"];

            AD.cid = txt_userid.Text.Trim();
            AD.Work = "*****************************LogIn--USER=" + Session["demo"].ToString() + ipAddress + Session["Cus_Username"].ToString() + "     " + DateTime.Now;
            AD.Insert_Log();
            Response.Redirect("default1.aspx");
        }
        else
        {
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + " Invalid userid and password" + "')", true);
        }
    }
}
