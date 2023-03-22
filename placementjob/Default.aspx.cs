using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;

public partial class _Default : System.Web.UI.Page
{
    clsFranchisee CF = new clsFranchisee();
    DataSet ds = new DataSet();
    clsAdmin AD = new clsAdmin();
    MyCon my = new MyCon();
    DataTable dt = new DataTable();
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            CF.Select_MissedDate_Status1();
        }

    }
    protected void btn_submit_Click(object sender, EventArgs e)
    {
        dt = my.FillDataTable("select status,agreement from tbl_registration where cid='" + txt_Uname.Text + "' and password='" + txt_pass.Text + "'");
        if (dt.Rows.Count == 1)
        {
            if (dt.Rows[0]["status"].ToString() == "0")
            {
                if (dt.Rows[0]["agreement"].ToString() == "0")
                {
                    Session["Cus_Username"] = txt_Uname.Text.Trim();
                    Response.Redirect("agreement.aspx");
                }
                else if (dt.Rows[0]["agreement"].ToString() == "1")
                {
                    //ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('Digital signature already sign, wait for the workload.');", true);
                    ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('We have received your signature successfully. We will verify the signature and once it will be verified you will get the workload.');", true);

                    return;
                }
            }
        }
        else
        {
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('ID and Password do not match.');", true);
        }


        CF.username = txt_Uname.Text.Trim();
        CF.password = txt_pass.Text.Trim();
        CF.type = "Customer";
        ds = CF.franchisee_Customer_login();
        if (ds.Tables[0].Rows.Count == 1)
        {
            string stop = my.AdExecScalar("SELECT [stop] FROM [Tbl_Registration] Where [CId]='" + CF.username + "'");
            if (stop == "1")
            {

            }
            else
            {
                try
                {
                    string rndno;
                    rndno = generateno(6);
                    dt = my.FillDataTable("select login from tbl_registration where cid='" + txt_Uname.Text + "'");
                    if (dt.Rows[0][0].ToString() == "0")
                    {
                        my.ExecutQury("update tbl_registration set login='" + rndno + "' where cid='" + txt_Uname.Text + "'");
                        Session["rndno"] = rndno;
                    }
                    else
                    {
                        Session["login"] = "Multiple Login are not allow.";
                        Session["Cus_Username"] = txt_Uname.Text.Trim();
                        Response.Redirect("session.aspx");
                    }
                }
                catch
                { }
                Session["Cus_Username"] = txt_Uname.Text.Trim();
                AD.id = txt_Uname.Text.Trim();
                ds = AD.Select_Registration_All();
                String ipAddress = System.Web.HttpContext.Current.Request.UserHostAddress;
                HttpRequest currentRequest = HttpContext.Current.Request;
                ipAddress = currentRequest.ServerVariables["HTTP_X_FORWARDED_FOR"];
                if (ipAddress == null || ipAddress.ToLower() == "unknown")
                    ipAddress = currentRequest.ServerVariables["REMOTE_ADDR"];
                AD.cid = txt_Uname.Text.Trim();
                AD.Work = "*****************************LogIn--" + ipAddress + Session["Cus_Username"].ToString() + "     " + DateTime.Now;
                AD.Insert_Log();
                Response.Redirect("Customer/Default.aspx");
            }
        }
    }
    public string generateno(int no)
    {
        string strPwdchar = "0123456789";
        string strPwd = "";
        Random rnd = new Random();
        for (int i = 0; i < no; i++)
        {
            int iRandom = rnd.Next(0, strPwdchar.Length - 1);
            strPwd += strPwdchar.Substring(iRandom, 1);
        }
        return strPwd;
    }
}