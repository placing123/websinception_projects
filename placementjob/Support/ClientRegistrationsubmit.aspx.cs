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
using System.IO;
using System.Net.Mail;
using System.Net;

using Novacode;
using System.Text.RegularExpressions;
using System.Security.Cryptography.X509Certificates;
using System.Net.Security;





public partial class Admin_ClientRegistration : System.Web.UI.Page
{
    clsFranchisee CF = new clsFranchisee();
    DataSet DS = new DataSet();
    clsAdmin AD = new clsAdmin();
    MyCon mycon = new MyCon();
    string CusID;

    protected void Page_Load(object sender, EventArgs e)
    {        
        if (Request.QueryString["msg"] != null)
        {
            lblmsg.Visible = true;
            lblmsg.Text = "Registration Sucessfully Created";
        }

        if (!IsPostBack)
        {
            FillPlan();
            GetCid();
            filldropdown();
        }

    }

    public void FillPlan()
    {
        if (!IsPostBack)
        {

            DataSet dsplan = new DataSet();
            dsplan = CF.Select_Plan_All();
            drp_plan.DataSource = dsplan;
            drp_plan.DataTextField = "PName";
            drp_plan.DataValueField = "PId";
            drp_plan.DataBind();
            drp_plan.Items.Insert(0, "- Select Plan -");
        }
    }

    public void GetCid()
    {
        DataSet dsgetid = AD.Select_Customer_MaxId();
        DateTime dt = DateTime.Now;
        string date = dt.ToString("ddMMyyyy");

        if (dsgetid.Tables[0].Rows[0][0].ToString() == "")
        {
            CusID = "C" + date + "1";
        }
        else
        {
            int no = Convert.ToInt32(dsgetid.Tables[0].Rows[0][0].ToString()) + 1;
            CusID = "C" + date + no;
        }

        lblid.Text = CusID.ToString();

    }

    public void filldropdown()
    {
        drd_fran.Items.Clear();
        DS = AD.Select_Franc_List();
        drd_fran.DataTextField = DS.Tables[0].Columns["FranchiseeName"].ToString().Trim();
        drd_fran.DataValueField = DS.Tables[0].Columns["fid"].ToString().Trim();
        drd_fran.DataSource = DS.Tables[0];
        drd_fran.DataBind();
        drd_fran.SelectedIndex = 0;
    }
    
    protected void Button1_Click(object sender, EventArgs e)
    {
        try
        {

            string allowedChars = "";
            allowedChars = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,";
            allowedChars += "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,";
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

            CF.CId = lblid.Text.ToString();

            string Enpass = clscommon.Encode(pass.ToString());
            CF.password = Enpass;

            CF.FId = drd_fran.SelectedValue.ToString();
            CF.Name = txt_name.Text.Trim();
            CF.Address = txt_add.Text.Trim();
            CF.City = txt_city.Text.Trim();
            CF.MobileNo = txt_mobno.Text.Trim();
            CF.EmailId = txt_email.Text.Trim();
            CF.PlanId = drp_plan.Text.Trim();
            CF.Reg_Date = System.DateTime.Now.AddHours(12.5).ToString("MM/dd/yyyy");
            CF.Status = "0";
            //CF.Style = "0";
            if (rbtn_online.Checked == true)
            {
                CF.Style = "0";
            }
            else
            {
                CF.Style = "1";
            }
            CF.CompanyName = txt_compname.Text;
            CF.InsertCustomerReg();
            Response.Redirect("ClientRegistrationsubmit.aspx?msg=succ");
        }
        catch { }
    }
}
