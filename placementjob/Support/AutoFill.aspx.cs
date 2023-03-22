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
    clsFranchisee CF = new clsFranchisee();
    clsAdmin AD = new clsAdmin();
    DataTable dt = new DataTable();
    DataTable dt1 = new DataTable();
    DataTable dt2 = new DataTable();
    Random rand = new Random();
    string random;

    string[] random_txtbox = { "Tbc_No", "Name", "EmailId", "GirNo", "LicenseNo", "H_Address", "PanNo", "O_Address", "O_City", "H_City" };
    int total, perform, remain;

    protected void Page_Load(object sender, EventArgs e)
    {

    }
    protected void btnsubmit_Click(object sender, EventArgs e)
    {
        try
        {
            if (txt_cid.Text != "" && txt_from.Text != "" && txt_to.Text != "" && txt_per.Text != "")
            {
                mycon.autofill(txt_cid.Text, Convert.ToInt32(txt_from.Text), Convert.ToInt32(txt_to.Text), Convert.ToInt32(txt_per.Text));
                ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Done." + "');", true);
            }
            else
            {
                ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Enter all values." + "');", true);
            }
        }
        catch (Exception ex)
        {
            lbl_error.Text = ex.Message.ToString();
        }
    }
    protected void btn_blanck_Click(object sender, EventArgs e)
    {
        if (txt_cid.Text != "" && txt_from.Text != "" && txt_to.Text != "")
        {
            dt = mycon.FillDataTable("select [index] from tbl_client_bpo_data with(nolock) where cid='" + txt_cid.Text + "' and sr_no>=" + txt_from.Text + "and sr_no<=" + txt_to.Text + "");
            for (int i = 0; i < dt.Rows.Count; i++)
            {
                mycon.ExecutQury(@"update Tbl_Client_Bpo_Data set Tbc_No=NULL,Name=NULL,EmailId=NULL,MobileNo=NULL,Gender=NULL,LicenseNo=NULL,GirNo=NULL,PanNo=NULL,H_Address=NULL
                                ,H_City=NULL,H_PinNo=NULL,H_State=NULL,O_Address=NULL,O_City=NULL,O_PinNo=NULL,LAL=NULL,MRNNo=NULL,AF=NULL,NRI=NULL,CP=NULL,
                                Status='0',spaceerror='0',date=NULL  where [index]=@0", dt.Rows[i]["index"].ToString());
            }
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Customer Data is Blanck." + "');", true);
            cleartext();
        }
        else
        {
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Enter all values." + "');", true);
        }

    }
    protected void txt_form_fill_Click(object sender, EventArgs e)
    {
        string mail1 = txt_form.Text;
        mail1 = mail1.Replace('\r', ' ');
        mail1 = mail1.Replace('\n', '~');
        mail1 = Regex.Replace(mail1, @"\s+", " ");
        string[] mail = mail1.Split('~');
        for (int i = 0; i < mail.Length; i++)
        {
            mail[i] = mail[i].Trim();
            dt = mycon.FillDataTable("select id,cid,[index] from tbl_client_bpo_data with(nolock) where cid='" + txt_cid.Text + "' and sr_no=" + mail[i].ToString() + "");

            dt1 = mycon.FillDataTable("select * from tbl_bpo_data with(nolock) where id=" + dt.Rows[0]["Id"].ToString() + "");

//            mycon.ExecutQury(@"update Tbl_Client_Bpo_Data set ipaddress=@0,firstname=@1,lastname=@2,address=@3,city=@4,state=@5,zip=@6,homephone=@7,workphone=@8,email=@9,dob=@10,
//                                ssn=@11,dlnumber=@12,dlstate=@13,bankaba=@14,bankacnumber=@15,bankname=@16,employer=@17,hireddate=@18,supername=@19,superphone=@20,
//                                Status=@21,spaceerror=@22  where Id=@23 and CId=@24", dt1.Rows[0]["ipaddress"].ToString(), dt1.Rows[0]["firstname"].ToString(), dt1.Rows[0]["lastname"].ToString(),
//                               dt1.Rows[0]["address"].ToString(), dt1.Rows[0]["city"].ToString(), dt1.Rows[0]["state"].ToString(), dt1.Rows[0]["zip"].ToString(),
//                               dt1.Rows[0]["homephone"].ToString(), dt1.Rows[0]["workphone"].ToString(), dt1.Rows[0]["email"].ToString(), dt1.Rows[0]["dob"].ToString(),
//                               dt1.Rows[0]["ssn"].ToString(), dt1.Rows[0]["dlnumber"].ToString(), dt1.Rows[0]["dlstate"].ToString(), dt1.Rows[0]["bankaba"].ToString(),
//                               dt1.Rows[0]["bankacnumber"].ToString(), dt1.Rows[0]["bankname"].ToString(), dt1.Rows[0]["employer"].ToString(), dt1.Rows[0]["hireddate"].ToString(),
//                               dt1.Rows[0]["supername"].ToString(), dt1.Rows[0]["superphone"].ToString(), "2", "0", dt.Rows[0]["Id"].ToString(), dt.Rows[0]["CId"].ToString());
            mycon.ExecutQury(@"update Tbl_Client_Bpo_Data set Tbc_No=@0,Name=@1,EmailId=@2,MobileNo=@3,Gender=@4,LicenseNo=@5,GirNo=@6,PanNo=@7,H_Address=@8,H_City=@9,H_PinNo=@10,
                                        H_State=@11,O_Address=@12,O_City=@13,O_PinNo=@14,LAL=@15,MRNNo=@16,AF=@17,NRI=@18,CP=@19,
                                        Status=@20,spaceerror=@21,date=@22  where [index]=@23", dt1.Rows[0]["Tbc_No"].ToString(), dt1.Rows[0]["Name"].ToString(), dt1.Rows[0]["EmailId"].ToString(),
                               dt1.Rows[0]["MobileNo"].ToString(), dt1.Rows[0]["Gender"].ToString(), dt1.Rows[0]["LicenseNo"].ToString(), dt1.Rows[0]["GirNo"].ToString(),
                               dt1.Rows[0]["PanNo"].ToString(), dt1.Rows[0]["H_Address"].ToString(), dt1.Rows[0]["H_City"].ToString(), dt1.Rows[0]["H_PinNo"].ToString(),
                               dt1.Rows[0]["H_State"].ToString(), dt1.Rows[0]["O_Address"].ToString(), dt1.Rows[0]["O_City"].ToString(), dt1.Rows[0]["O_PinNo"].ToString(),
                               dt1.Rows[0]["LAL"].ToString(), dt1.Rows[0]["MRNNo"].ToString(), dt1.Rows[0]["AF"].ToString(), dt1.Rows[0]["NRI"].ToString(),
                               dt1.Rows[0]["CP"].ToString(), "2", "0", mycon.indianTime().AddDays(2).ToString("yyyy-MM-dd hh:mm:ss tt"), dt.Rows[0]["index"].ToString());
        }
        //////////////////////
        cleartext();
        ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Filled." + "');", true);


    }

    public void cleartext()
    {
        txt_cid.Text = "";
        txt_from.Text = "";
        txt_to.Text = "";
        txt_per.Text = "";
        txt_form.Text = "";
    }
    
}