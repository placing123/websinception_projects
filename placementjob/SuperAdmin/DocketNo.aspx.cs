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



public partial class Admin_DocketNo : System.Web.UI.Page
{
    clsAdmin AD = new clsAdmin();
    DataSet DS = new DataSet();

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            fillgrid();
        }

    }
    public void fillgrid()
    {
        try
        {
            DS = AD.Select_Docket_List();
            grd_Docate.DataSource = DS;
            grd_Docate.DataBind();
        }
        catch
        { }
    }

    //protected void btn_showclient_Click(object sender, EventArgs e)
    //{
    //    AD.enddate = txt_date.Text.ToString();
    //    AD.enddate0 = txt_date0.Text.ToString();
    //    DS = AD.Select_Registration_Docate1();
    //    grd_Docate.DataSource = DS;
    //    grd_Docate.DataBind();
    //}
    protected void grd_Docate_RowCommand(object sender, GridViewCommandEventArgs e)
    {
        if (e.CommandName == "check")
        {
            GridViewRow row = (GridViewRow)(((ImageButton)e.CommandSource).NamingContainer);

            TextBox textBox = (TextBox)row.FindControl("txtdoc");
            Label labelcid = (Label)row.FindControl("Label1");

            string docketno = textBox.Text;
            string cid = labelcid.Text;
            AD.cid = cid;
            AD.DocketNo = docketno;
            AD.Update_Registration_Docket_Recevied();
            fillgrid();
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Docket No. Inserted Sucsessfully .." + "');", true);
        }
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


            string wordFileName = Server.MapPath("./Agreement/") + PlanId + ".docx";
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
    protected void btnsubarr_Click(object sender, EventArgs e)
    {
        foreach (GridViewRow row in grd_Docate.Rows)
        {
            CheckBox chk = (CheckBox)row.FindControl("chk");
            if (chk.Checked)
            {
                //TextBox textBox = (TextBox)row.FindControl("txtdoc");
                Label labelcid = (Label)row.FindControl("Label1");

                string docketno = Txt_doc.Text;
                string cid = labelcid.Text;
                AD.cid = cid;
                AD.DocketNo = docketno;
                AD.Update_Registration_Docket_Recevied();
            }
        }
        fillgrid();
        ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Docket No. Inserted Sucsessfully .." + "');", true);
    }
    private void selectarr(bool status)
    {
        Object obj;
        CheckBox chk;
        try
        {

            foreach (GridViewRow row in grd_Docate.Rows)
            {
                obj = row.FindControl("chk");
                if (obj != null)
                {
                    chk = (CheckBox)obj;
                    chk.Checked = status;
                }
            }
        }
        catch (Exception ex)
        {

        }
    }
    protected void btnselarr_Click(object sender, EventArgs e)
    {
        if (btnselarr.Text == "Select All")
        {
            selectarr(true);
            btnselarr.Text = "DeSelect All";
        }
        else
        {
            selectarr(false);
            btnselarr.Text = "Select All";
        }
    }
}
