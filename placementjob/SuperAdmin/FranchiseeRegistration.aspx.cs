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
using System.Net.Mail;
using System.Net;
using System.IO;

public partial class Admin_FranchiseeRegistration : System.Web.UI.Page
{
    clsAdmin AD = new clsAdmin();
    DataSet DS = new DataSet();
    MyCon mycon = new MyCon();
    protected void Page_Load(object sender, EventArgs e)
    {
        MyCon mycon = new MyCon();
        DataTable dt = new DataTable();

        ////string rpt;
        ////rpt = mycon.AdExecScalar("select p_s from Tbl_Franchisee where Fid='" + Session["Fran_Username"].ToString() + "'");
        ////if (rpt == "1")
        ////{
        ////    btn_active.Text = "Deactive";
        ////}
        ////else
        ////{
        ////    btn_active.Text = "Active";
        ////}
        Panel1.Visible = false;
        if (!IsPostBack)
        {
            fillactivegrid();
            filldeactivegrid();
            clearetext();
        }

    }
    protected void btn_active_Click(object sender, EventArgs e)
    {
        try
        {
            string str = Request.QueryString["fid"].ToString();
            AD.id = str;
            AD.Update_Franchisee_Active();
            fillactivegrid();
            filldeactivegrid();
        }
        catch
        {

        }


    }

    protected void btn_block_Click(object sender, EventArgs e)
    {
        try
        {
            string str = Request.QueryString["fid"].ToString();
            AD.id = str;
            AD.Update_Franchisee_Block_Unblock();
            fillactivegrid();
            filldeactivegrid();
        }
        catch
        {

        }


    }
    protected void btn_deactivate_Click(object sender, EventArgs e)
    {
        try
        {
            string str = Request.QueryString["fid"].ToString();
            AD.id = str;
            AD.Update_Franchisee_Deactive();
            fillactivegrid();
            filldeactivegrid();
        }
        catch
        {


        }


    }

    public void filldeactivegrid()
    {
        try
        {
            DS = AD.Select_Deactivate_Franc();
            grd_franch_deactive.DataSource = DS.Tables[0];
            grd_franch_deactive.DataBind();
        }
        catch
        {


        }

    }

    public void fillactivegrid()
    {
        try
        {

            DS = AD.Select_Active_Franc();
            grd_franch_active.DataSource = DS;
            grd_franch_active.DataBind();
        }
        catch
        {


        }
    }


    protected void btn_send_Click(object sender, EventArgs e)
    {
        try
        {
            StreamReader fp;// = new StreamReader();

            string fid = Request.QueryString["fid"].ToString();
            AD.id = fid;
            DS = AD.Select_Franchisee_All();
            string email = DS.Tables[0].Rows[0]["Email"].ToString();


            fp = File.OpenText(Server.MapPath("./Agreement/") + "F.txt");
            string htmlstring = fp.ReadToEnd();
            fp.Close();
            htmlstring = htmlstring.Replace("X-X-X-X", DS.Tables[0].Rows[0]["FranchiseeName"].ToString());
            htmlstring = htmlstring.Replace("XX.X.X.X", DS.Tables[0].Rows[0]["FId"].ToString());
            string str = DS.Tables[0].Rows[0]["Password"].ToString();
            str = clscommon.Decode(str);
            htmlstring = htmlstring.Replace("XXX/XX/X", str);
            if (mycon.send(email, "National-BPO Login Information", htmlstring))
            {
                ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Mail Send Succsessfully" + "');", true);
            }
            else
            {
                ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Mail Send Fail" + "');", true);
            }


        }
        catch
        {



        }

    }


    protected void btn_update_Click(object sender, EventArgs e)
    {
        if (lblid.Text == "")
        {
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Please Select Franchisee..." + "');", true);
        }
        else
        {

            AD.FId = lblid.Text;
            AD.FranchiseeName = txt_frname.Text.Trim();
            AD.CompanyName = txt_compname.Text.Trim();
            AD.Address = txt_add.Text.Trim();
            AD.Landline = txt_landno.Text.Trim();
            AD.MobileNo = txt_mob.Text.Trim();
            AD.Email = txt_email.Text.Trim();
            AD.BankAC = txt_bankac.Text.Trim();
            AD.IFCCode = txt_ifc.Text.Trim();
            AD.Update_Franchisee_Detail();
            fillactivegrid();
            filldeactivegrid();
            clearetext();
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Franchisee Detail Updated Succsessfully..." + "');", true);

        }
    }
    public void clearetext()
    {

        lblid.Text = "";
        txt_frname.Text = "";
        txt_compname.Text = "";
        txt_add.Text = "";
        txt_landno.Text = "";
        txt_mob.Text = "";
        txt_email.Text = "";
        txt_bankac.Text = "";
        txt_ifc.Text = "";
    }
    protected void btn_edit_Click(object sender, EventArgs e)
    {
        string str = Request.QueryString["fid"].ToString();
        AD.id = str;
        DS = AD.Select_Franchisee_All();
        lblid.Text = str;
        txt_frname.Text = DS.Tables[0].Rows[0]["FranchiseeName"].ToString();
        txt_compname.Text = DS.Tables[0].Rows[0]["CompanyName"].ToString();
        txt_add.Text = DS.Tables[0].Rows[0]["Address"].ToString();
        txt_landno.Text = DS.Tables[0].Rows[0]["Landline"].ToString();
        txt_mob.Text = DS.Tables[0].Rows[0]["MobileNo"].ToString();
        txt_email.Text = DS.Tables[0].Rows[0]["Email"].ToString();
        txt_bankac.Text = DS.Tables[0].Rows[0]["BankAC"].ToString();
        txt_ifc.Text = DS.Tables[0].Rows[0]["IFCCode"].ToString();
    }
    protected void btn_delete_Click(object sender, EventArgs e)
    {
        AD.cid = Request.QueryString["fid"].ToString();
        AD.Delete_Franchisee_fid();
        fillactivegrid();
        ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Franchise Deleted Succsessfuly...." + "');", true);
    }
    protected void grd_franch_deactive_RowDataBound(object sender, GridViewRowEventArgs e)
    {
        if (e.Row.RowType == DataControlRowType.DataRow)
        {
            Label lblblockstatus = ((Label)e.Row.FindControl("lblblockstatus"));

            if (lblblockstatus.Text == "1")
            {
                lblblockstatus.Text = "UnBlock";
                e.Row.Cells[2].BackColor = System.Drawing.Color.LightGreen;
            }

            if (lblblockstatus.Text == "2")
            {
                lblblockstatus.Text = "Blocked";
                e.Row.Cells[2].BackColor = System.Drawing.Color.LightPink;
            }


            Label lblvisible = ((Label)e.Row.FindControl("Label12"));

            if (lblvisible.Text == "1")
            {
                lblvisible.Text = "visible On";
                lblvisible.BackColor = System.Drawing.Color.LightGreen;
            }

            if (lblvisible.Text == "0")
            {
                lblvisible.Text = "visible Off";
                lblvisible.BackColor = System.Drawing.Color.LightPink;
            }


            Label password = ((Label)e.Row.FindControl("Label2"));
            password.Text = clscommon.Decode(password.Text);
        }
    }



    protected void btn_ap_Click(object sender, EventArgs e)
    {
        Panel1.Visible = true;
        //btn_ap.Text=
    }
    protected void btn_active_Click1(object sender, EventArgs e)
    {
        if (btn_active.Text == "Active")
        {
            string id = Request.QueryString["fid"].ToString();
            string str = "UPDATE [Tbl_Franchisee] SET [p_url]='" + txt_url.Text + "',[p_s]='1' WHERE fid='" + id.ToString() + "'";
            mycon.ExecutQury(str);
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Portal Activeted..." + "');", true);
            btn_active.Text = "Deactive";
        }
        if (btn_active.Text == "Deactive")
        {
            string id = Request.QueryString["fid"].ToString();
            string str = "UPDATE [Tbl_Franchisee] SET [p_url]='',[p_s]='1' WHERE fid='" + id.ToString() + "'";
            mycon.ExecutQury(str);
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Portal Deactiveted..." + "');", true);
            btn_active.Text = "Active";
        }

    }

    protected void btn_vis_Click(object sender, EventArgs e)
    {
        string id = Request.QueryString["fid"].ToString();
        string str ;//= Request.QueryString["fid"].ToString();
        str = mycon.AdExecScalar("select visible from Tbl_Franchisee where Fid='" + id .ToString () + "'");
        if (str == "1")
        {
            string str1 = "UPDATE  Tbl_Franchisee SET visible='0' WHERE Fid='" + id .ToString () +"' ";
            mycon.ExecutQury(str1);
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Visiblity Chenged" + "');", true);
        }
        if (str == "0")
        {
            string str1 = "UPDATE  Tbl_Franchisee SET visible='1' WHERE Fid='" + id.ToString() + "' ";
            mycon.ExecutQury(str1);
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Visiblity Chenged" + "');", true);
        }
        
    }
}
