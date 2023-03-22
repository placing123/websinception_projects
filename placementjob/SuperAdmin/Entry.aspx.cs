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
using System.Text.RegularExpressions;

public partial class Admin_Entry : System.Web.UI.Page
{
    clsAdmin AD = new clsAdmin();

    protected void Page_Load(object sender, EventArgs e)
    {
        try
        {
            lbl_username.Text = Session["username"].ToString();
            if (Session["username"].ToString() == "" || Session["type"].ToString() != "E")
            {
                Response.Redirect("default.aspx");

            }
        }

        catch
        {
            Response.Redirect("default.aspx");
        }
        // lbl_time.Text = DateTime.Now.ToShortDateString().ToString() +" <br> " + DateTime.Now.ToLongTimeString().ToString();

    }

    protected void b_logout_Click(object sender, EventArgs e)
    {
        try
        {

            Session.Clear();
            Session.RemoveAll();
            Response.Redirect("default.aspx");
        }
        catch
        {

        }

        //Session["username"] = "";
    }
    protected void btn_name_Click(object sender, EventArgs e)
    {
        string name1 = txt_name.Text;
        name1 = name1.Replace('\r', ' ');
        name1 = name1.Replace('\n', '~');
        name1 = Regex.Replace(name1, @"\s+", " ");
        string[] name = name1.Split('~');
        for (int i = 0; i < name.Length; i++)
        {
            
            name[i] = name[i].Trim();
            if (name[i]!="")
            {
                AD.name = name[i];
                AD.Insert_Bpo_Name();    
            }
            
        }
        ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Name Added Succsesssfully...." + "');", true);
    }
    protected void btn_emailid_Click(object sender, EventArgs e)
    {
        string name1 = txt_emailid.Text;
        name1 = name1.Replace('\r', ' ');
        name1 = name1.Replace('\n', '~');
        name1 = Regex.Replace(name1, @"\s+", " ");
        string[] name = name1.Split('~');
        for (int i = 0; i < name.Length; i++)
        {

            name[i] = name[i].Trim();
            if (name[i] != "")
            {
                AD.name = name[i];
                AD.Insert_Bpo_Emailid();
            }

        }
        ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Emailid Added Succsesssfully...." + "');", true);
    }
    protected void btn_mobile_Click(object sender, EventArgs e)
    {
        string name1 = txt_mobile.Text;
        name1 = name1.Replace('\r', ' ');
        name1 = name1.Replace('\n', '~');
        name1 = Regex.Replace(name1, @"\s+", " ");
        string[] name = name1.Split('~');
        for (int i = 0; i < name.Length; i++)
        {

            name[i] = name[i].Trim();
            if (name[i] != "")
            {
                AD.name = name[i];
                AD.Insert_Bpo_mobileno();
            }

        }
        ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Mobile Number Added Succsesssfully...." + "');", true);
    }
    protected void btn_add_Click(object sender, EventArgs e)
    {
        string name1 = txt_address.Text;
        name1 = name1.Replace('\r', ' ');
        name1 = name1.Replace('\n', '~');
        name1 = Regex.Replace(name1, @"\s+", " ");
        string[] name = name1.Split('~');
        for (int i = 0; i < name.Length; i++)
        {

            name[i] = name[i].Trim();
            if (name[i] != "")
            {
                AD.name = name[i];
                AD.Insert_Bpo_address();
            }

        }
        ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Address Added Succsesssfully...." + "');", true);
    }
    protected void btn_SCP_Click(object sender, EventArgs e)
    {
        //string scp1 = txt_scp.Text;
        //scp1 = scp1.Replace('\r', ' ');
        //scp1 = scp1.Replace('\n', '~');
        //scp1 = Regex.Replace(scp1, @"\s+", " ");
        //string[] scp = scp1.Split('~');
        //string[] scp2;
        //string[] state = new string[scp.Length];
        //string[] city = new string[scp.Length];
        //string[] pincode = new string[scp.Length];
        //for (int i = 0; i < scp.Length; i++)
        //{
        //    scp[i] = scp[i].Trim();
         

        //    if (scp[i] != "")
        //    {
        //        scp2 = scp[i].Split(' ');
        //        state[i] = scp2[0];
        //        city[i] = scp2[1];
        //        pincode[i] = scp2[2];

        //        AD.name = state[i];
        //        AD.Insert_Bpo_State();
        //        AD.name = city[i];
        //        AD.Insert_Bpo_City();
        //        AD.name = pincode[i];
        //        AD.Insert_Bpo_Pincode();
        //    }
        //}
        //ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "State,City,Pincode Added Succsesssfully...." + "');", true);

        string name1 = txt_scp.Text;
        name1 = name1.Replace('\r', ' ');
        name1 = name1.Replace('\n', '~');
        name1 = Regex.Replace(name1, @"\s+", " ");
        string[] name = name1.Split('~');
        for (int i = 0; i < name.Length; i++)
        {

            name[i] = name[i].Trim();
            if (name[i] != "")
            {
                AD.name = name[i];
                AD.Insert_Bpo_City();
            }

        }
        ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "City Added Succsesssfully...." + "');", true);



    }

    protected void btn_pin_Click(object sender, EventArgs e)
    {
        string pin = txt_pin.Text;
        pin = pin.Replace('\r', ' ');
        pin = pin.Replace('\n', '~');
        pin = Regex.Replace(pin, @"\s+", " ");
        string[] pincode = pin.Split('~');


        for (int i = 0; i < pincode.Length; i++)
        {

            pincode[i] = pincode[i].Trim();
            if (pincode[i] != "")
            {
                AD.name = pincode[i];
                AD.Insert_Bpo_Pincode();
            }

        }
        ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Pincode Added Succsesssfully...." + "');", true);


    }
    protected void btn_state_Click(object sender, EventArgs e)
    {
        string name1 = txt_state.Text;
        name1 = name1.Replace('\r', ' ');
        name1 = name1.Replace('\n', '~');
        name1 = Regex.Replace(name1, @"\s+", " ");
        string[] name = name1.Split('~');
        for (int i = 0; i < name.Length; i++)
        {

            name[i] = name[i].Trim();
            if (name[i] != "")
            {
                AD.name = name[i];
                AD.Insert_Bpo_State();
            }

        }
        ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "State Added Succsesssfully...." + "');", true);
    }
}
