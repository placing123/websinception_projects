using System;
using System.Collections.Generic;
using System.Data;
using System.IO;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;

public partial class agreement : System.Web.UI.Page
{
    MyCon mycon = new MyCon();
    DataTable dt = new DataTable();
    protected void Page_Load(object sender, EventArgs e)
    {
        if (Session["Cus_Username"] == null)
        {
            Response.Redirect("login.aspx");
        }
        if (!IsPostBack)
        {    
			//Response.Write(Session["Cus_Username"].ToString());
            string agreement = mycon.AdExecScalar("select agreement from tbl_registration where cid='" + Session["Cus_Username"].ToString() + "'");
            if (agreement == "1")
            {
                Response.Redirect("login.aspx");
            }
            //http://docs.google.com/gview?url=http://typejobs.in/Control/Agreement/12154TPT18092018.pdf&embedded=true
            HtmlControl contentPanel1 = (HtmlControl)this.FindControl("pdfiframe");
            if (contentPanel1 != null)             
			 contentPanel1.Attributes["src"] = "https://docs.google.com/gview?url=https://ingbjob.com/SuperAdmin/Agreement/" + Session["Cus_Username"].ToString() + ".pdf&embedded=true";
			//name= Session["Cus_Username"];
		}
		
    }
    protected void btn_tnc_Click(object sender, EventArgs e)
    {
        //Read the Base64 string from Hidden Field.
        string base64 = Request.Form[hfImageData.UniqueID].Split(',')[1];

        //Convert Base64 string to Byte Array.
        byte[] bytes = Convert.FromBase64String(base64);

        //Save the Byte Array as Image File.
        string filePath = string.Format("~/sign/{0}.jpg", Session["Cus_Username"].ToString());
        File.WriteAllBytes(Server.MapPath(filePath), bytes);

        String ipAddress = System.Web.HttpContext.Current.Request.UserHostAddress;
        HttpRequest currentRequest = HttpContext.Current.Request;
        ipAddress = currentRequest.ServerVariables["HTTP_X_FORWARDED_FOR"];
        if (ipAddress == null || ipAddress.ToLower() == "unknown")
            ipAddress = currentRequest.ServerVariables["REMOTE_ADDR"];


        mycon.ExecutQury("update tbl_registration set agreement=1,ipaddress=@0 where cid=@1", ipAddress, Session["Cus_Username"].ToString());

        ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('We acknowledge your response you will get workload shortly.');", true);
        //mycon.send("", "", "");
        ClientScript.RegisterStartupScript(GetType(), "Javascript", "javascript:RedirectAfterDelayFn(); ", true);
    }
}