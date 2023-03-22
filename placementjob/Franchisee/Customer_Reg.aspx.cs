using System;
using System.Data;
using System.Configuration;
using System.Linq;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Xml.Linq;
using System.Data.SqlClient;
using DLL;
using System.Net.Mail;


public partial class Franchisee_PersonalDetails : System.Web.UI.Page
{
    clsFranchisee CF = new clsFranchisee();
    DataSet ds = new DataSet();
    string CusID;
    clsAdmin AD = new clsAdmin();
    MyCon mycon = new MyCon();
    DataTable dt = new DataTable();

    protected void Page_Load(object sender, EventArgs e)
    {
        if (Session["Fran_Username"] == null)
        {
            Response.Redirect("Default.aspx");
        }
        GetCid();
        if (!IsPostBack)
        {
            FillPlan();
            fillcaller();
        }
    }
    public void fillcaller()
    {
        dt = mycon.FillDataTable("select callername from tbl_callermaster with(nolock) order by callername");
        ddl_callername.DataSource = dt;
        ddl_callername.DataTextField = "callername";
        ddl_callername.DataValueField = "callername";
        ddl_callername.DataBind();
    }
    public void FillPlan()
    {
        dt = mycon.FillDataTable("select pname,pid from tbl_plan with(nolock) order by id");
        drp_plan.DataSource = dt;
        drp_plan.DataTextField = "PName";
        drp_plan.DataValueField = "PId";
        drp_plan.DataBind();
        drp_plan.SelectedIndex = 2;
    }
    public void GetCid()
    {
        DataSet dsgetid = AD.Select_Customer_MaxId();
        DateTime dt = DateTime.Now;
        string date = dt.ToString("ddMMyyyy");

        if (dsgetid.Tables[0].Rows[0][0].ToString() == "")
        {
            CusID = "1" + "INJ" + date;
        }
        else
        {
            int no = Convert.ToInt32(dsgetid.Tables[0].Rows[0][0].ToString()) + 1;
            CusID = no + "INJ" + date;
        }
        lblid.Text = CusID.ToString();
    }
    public void cleartext()
    {
        txt_name.Text = "";
        txt_add.Text = "";
        //txt_compname.Text = "";
        //txt_city.Text = "";
        txt_mobno.Text = "";
        txt_email.Text = "";
        //drp_plan.SelectedIndex = 0;
    }
    protected void Button1_Click(object sender, EventArgs e)
    {
        try
        {
            string allowedChars = "";
            //allowedChars = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,";
            //allowedChars += "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,";
            allowedChars += "1,2,3,4,5,6,7,8,9,0";
            char[] sep = { ',' };
            string[] arr = allowedChars.Split(sep);
            string pass = "";
            string temp = "";
            Random rand = new Random();
            for (int i = 0; i < 5; i++)
            {
                temp = arr[rand.Next(0, arr.Length)];
                pass += temp;
            }
            CF.CId = CusID.ToString();
            CF.password = pass.ToString();
            CF.FId = ddl_franchisee.Text;
            CF.Name = txt_name.Text.Trim();
            CF.Address = txt_add.Text.Trim();
            CF.City = "";// txt_city.Text.Trim();
            CF.MobileNo = txt_mobno.Text.Trim();
            CF.EmailId = txt_email.Text.Trim();
            CF.PlanId = drp_plan.Text.Trim();
            CF.Reg_Date = mycon.indianTime().ToString("yyyy-MM-dd hh:mm:ss tt");
            CF.Status = "0";
            CF.Style = "0";
            CF.CompanyName = "";// txt_compname.Text;
			string pageno= ddl_pageno.Text;
           
            //CF.InsertCustomerReg();
            mycon.ExecutQury(@"insert into Tbl_Registration (CId,password,FId,Name,Address,City,MobileNo,EmailId,PlanId,Reg_Date,Status,Style,CompanyName,Change,callername) values 
                            (@0,@1,@2,@3,@4,@5,@6,@7,@8,@9,'0','0','anbprojection','0',@10)", CusID.ToString(), pass.ToString(), ddl_franchisee.Text,
                            txt_name.Text.Trim(), txt_add.Text.Trim(),ddl_pageno.Text, txt_mobno.Text.Trim(), txt_email.Text.Trim(), drp_plan.Text.Trim(),
                            mycon.indianTime().ToString("yyyy-MM-dd hh:mm:ss tt"), ddl_callername.Text);
            cleartext();
            GetCid();
            lblmsg.Visible = true;
            lblmsg.Text = "Registration Sucessfully Created , Reg. ID  :: " + CF.CId + ", Password: " + CF.password ;
            string temppath = Server.MapPath("~/SuperAdmin/Agreement/") + CF.CId + ".pdf";
            try
            {
                mycon.generatepdf(temppath, CF.EmailId, CF.Name, CF.MobileNo, CF.Address, pageno);

                string Email = ConfigurationManager.AppSettings["Email"].ToString();
                string Emailto = CF.EmailId;
                System.Net.Mail.MailMessage mail = new System.Net.Mail.MailMessage(Email, Emailto);
                mail.Subject = "Terms And Condition";
                mail.Body = @"Hello,<br/><br/>There is an attachment of the job contract (terms and conditions) about the on-line banking form filling project. If you are satisfied by the terms kindly take a print out of it and sign on all the pages or revert on the same email if you you accept all terms and condition.
After signing send us the scanned copy of it on this email id.
After receipt of your signed scanned copy only you will be given the log in details to start the project.<br/><br/>
                                <b>or <b/><br/><br/>
                                you can login and accept our terms and condition by digital signature.<br/><br/>
                                User id : " + CF.CId + @"   <br/>
                                Password :" + CF.password + @"<br/>
                              	https://ingbjob.com/
                                <br/><br/>
                                Thanks.";
                mail.IsBodyHtml = true;
                mail.Attachments.Add(new Attachment(temppath));
                SmtpClient smtp = new SmtpClient();
                smtp.Send(mail);
            }
            catch (Exception ex)
            {
                ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('Mail sending Fail Register complete.');", true);
            }


            //Response.Redirect("Customer_Reg.aspx?msg=succ");
        }
        catch (Exception ex)
        {
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + ex.Message.ToString() + "');", true);
        }
    }
}