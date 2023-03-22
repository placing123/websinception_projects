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





public partial class Admin_ClientRegistration : System.Web.UI.Page
{
    DataSet DS = new DataSet();
    clsAdmin AD = new clsAdmin();
    MyCon mycon = new MyCon();
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            filldropdown();
            fillavtivegrid();
            filldeactivegrid();
            cleartext();
        }

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

    public void filldeactivegrid()
    {
        try
        {
            AD.id = drd_fran.SelectedValue.ToString();
            DS = AD.Select_Deactive_Client();
            grd_client_deactive.DataSource = DS.Tables[0];
            grd_client_deactive.DataBind();

        }
        catch
        {

            //throw;
        }
    }
    public void fillavtivegrid()
    {
        try
        {
           // AD.id = drd_fran.SelectedValue.ToString();
           // DS = AD.Select_Active_Client();
            DS = AD.Select_ActiveALL_Client();
            grd_client_active.DataSource = DS.Tables[0];
            grd_client_active.DataBind();
        }
        catch
        {

            //throw;
        }
    }
    protected void btn_active_Click(object sender, EventArgs e)
    {
        try
        {
            string str = Request.QueryString["cid"].ToString();
            AD.id = str;
            AD.Update_Client_Active();
            fillavtivegrid();
            filldeactivegrid();
            AD.id = str;
            DS = AD.Select_Planid_From_Registration();
            string page = DS.Tables[0].Rows[0][0].ToString();
            int totalpage = Convert.ToInt32(page);
            AD.form = page;
            DS = AD.Select_No_Of_Bpo_Data();
            for (int i = 0; i < totalpage; i++)
            {
                AD.cid = str;
                AD.id = DS.Tables[0].Rows[i][0].ToString();
                AD.Srno = i + 1;
                AD.Insert_Client_Bpo_Activate();
            }
            AD.id = str;
            DS = AD.Select_Planid_From_Registration();
            AD.cid = str;
            AD.sdate = DateTime.Now.AddDays(1).ToString();
            AD.edate = DateTime.Now.AddDays(Convert.ToInt32(DS.Tables[0].Rows[0][1])).ToString("MM/dd/yyyy");

            AD.Update_Registration_Date();

        }
        catch
        {


        }
    }
    protected void btn_deactivate_Click(object sender, EventArgs e)
    {
        try
        {
            string str = Request.QueryString["cid"].ToString();
            AD.id = str;
            AD.Update_Client_Deactivate();
            fillavtivegrid();
            filldeactivegrid();
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

            string cid = Request.QueryString["cid"].ToString();
            AD.id = cid;
            DS = AD.Select_Registration_All();
            string email = DS.Tables[0].Rows[0]["EmailId"].ToString();


            fp = File.OpenText(Server.MapPath("./Agreement/") + "C.txt");
            string htmlstring = fp.ReadToEnd();
            fp.Close();
            htmlstring = htmlstring.Replace("X-X-X-X", DS.Tables[0].Rows[0]["Name"].ToString());
            htmlstring = htmlstring.Replace("XX.X.X.X", DS.Tables[0].Rows[0]["CId"].ToString());
            string str = DS.Tables[0].Rows[0]["Password"].ToString();
            str = clscommon.Decode(str);
            htmlstring = htmlstring.Replace("XXX/XX/X", str);
            if (mycon.send(email, "National-Bpo Login Information", htmlstring))
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


    protected void drd_fran_SelectedIndexChanged(object sender, EventArgs e)
    {
      //  fillavtivegrid();
        filldeactivegrid();
    }


   


    protected void btn_aggriment_Click(object sender, EventArgs e)
    {
        try
        {
            string cid = Request.QueryString["cid"].ToString();
            AD.id = cid;
            DS = AD.Select_Registration_All();
            string name = DS.Tables[0].Rows[0]["Name"].ToString();
            string address = DS.Tables[0].Rows[0]["Address"].ToString();
            string PlanId = DS.Tables[0].Rows[0]["PlanId"].ToString();


            string wordFileName = Server.MapPath("./Agreement/") + PlanId +".docx";
            string wordFileName1 = Server.MapPath("./Agreement/") + "Aggriment.docx";




            if (File.Exists(wordFileName))
            {
                File.Copy(wordFileName, wordFileName1, true);
            }




            Console.WriteLine("\tReplaceTextParallel()\n");
            DocX document1 = DocX.Load(wordFileName1);
            document1.ReplaceText("XXXXX-XXXXX-XXXXX", name, false, RegexOptions.IgnoreCase, null, null, MatchFormattingOptions.ExactMatch);
            document1.ReplaceText("XXXXXXXXXX-XXXXXXXXXX-XXXXXXXXXX", address, false, RegexOptions.IgnoreCase, null, null, MatchFormattingOptions.ExactMatch);
            document1.Save();
            Console.WriteLine("\tCreated: None\n");



            Response.ContentType = "application/docx";
            Response.AppendHeader("Content-Disposition", "attachment; filename=Aggriment.docx");
            Response.TransmitFile(Server.MapPath("Agreement/Aggriment.docx"));
            Response.End();
        }
        catch
        {
        }
    }

    //private void FileStream(string p, FileMode fileMode, FileAccess fileAccess)
    //{
    //    throw new NotImplementedException();
    //}


    protected void btn_update_Click(object sender, EventArgs e)
    {
        if (lblid.Text=="")
        {
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Please Select Client.." + "');", true);
            cleartext();
        }
        else
        {
            AD.cid = lblid.Text;
            AD.name = txt_name.Text;
            AD.Address = txt_add.Text;
            AD.CompanyName = txt_compname.Text;
            AD.City = txt_city.Text;
            AD.MobileNo = txt_mobno.Text;
            AD.Email = txt_email.Text;
            AD.Update_Client_Detail();
            cleartext();
            fillavtivegrid();
            filldeactivegrid();
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Client Data Updated Succsessfuly...." + "');", true);            

        }
    }
    public void cleartext()
    {
        lblid.Text = "";
        txt_name.Text = "";
        txt_add.Text = "";
        txt_compname.Text = "";
        txt_city.Text = "";
        txt_mobno.Text = "";
        txt_email.Text = "";
        lbl_plan.Text = "";
    }
    protected void btn_edit_Click(object sender, EventArgs e)
    {
        AD.id = Request.QueryString["cid"].ToString();
        DS = AD.Select_Registration_All();
        lblid.Text = DS.Tables[0].Rows[0]["CId"].ToString();
        txt_name.Text = DS.Tables[0].Rows[0]["Name"].ToString();
        txt_add.Text = DS.Tables[0].Rows[0]["Address"].ToString();
        txt_compname.Text = DS.Tables[0].Rows[0]["CompanyName"].ToString();
        txt_city.Text = DS.Tables[0].Rows[0]["City"].ToString();
        txt_mobno.Text = DS.Tables[0].Rows[0]["MobileNo"].ToString();
        txt_email.Text = DS.Tables[0].Rows[0]["EmailId"].ToString();
        lbl_plan.Text = DS.Tables[0].Rows[0]["PlanId"].ToString();
    }
    protected void btn_delete_Click(object sender, EventArgs e)
    {
        AD.cid = Request.QueryString["cid"].ToString();
        AD.Delete_Client_cid();
        fillavtivegrid();
        ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Client Deleted Succsessfuly...." + "');", true);            


    }
    protected void grd_client_active_RowDataBound(object sender, GridViewRowEventArgs e)
    {

       

        if (e.Row.RowType == DataControlRowType.DataRow)
        {
            Label lbldate = ((Label)e.Row.FindControl("lbl"));

            if (lbldate.Text == "0")
            {
                lbldate.Text = "Online";
                lbldate.ForeColor = System.Drawing.Color.Green;
            }


            if (lbldate.Text == "1")
            {
                lbldate.Text = "Offline";
                lbldate.ForeColor = System.Drawing.Color.Red;  
            }


            Label lblfranid = ((Label)e.Row.FindControl("lblfid"));
            AD.id = lblfranid.Text;
            DS = AD.Select_Franchisee_All();
            lblfranid.Text = DS.Tables[0].Rows[0]["FranchiseeName"].ToString();

        }

                
        //if (DataBinder.Eval(e.Row.DataItem, "Style") == "0")
        //{
        //    e.Row.Cells[9].Text = "Online";

        //}
        //else if (DataBinder.Eval(e.Row.DataItem, "Style") == "1")
        //{
        //    e.Row.Cells[9].Text = "Offline";

        //}
        //string str = DataBinder.Eval(e.Row.DataItem, "FId") ;
        //AD.id = str;
        //DS = AD.Select_Franchisee_All();

        //e.Row.Cells[3].Text = DS.Tables[0].Rows[0]["FranchiseeName"].ToString();

    }
    protected void grd_client_deactive_RowDataBound(object sender, GridViewRowEventArgs e)
    {
        if (e.Row.RowType == DataControlRowType.DataRow)
        {
            Label lbldate = ((Label)e.Row.FindControl("lbl1"));

            if (lbldate.Text == "0")
            {
                lbldate.Text = "Online";
                lbldate.ForeColor = System.Drawing.Color.Green;
            }
            if (lbldate.Text == "1")
            {
                lbldate.Text = "Offline";
                lbldate.ForeColor = System.Drawing.Color.Red;
            }
        }

    }
}
