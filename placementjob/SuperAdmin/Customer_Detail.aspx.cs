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
using System.Xml;
using System.Xml.Xsl;
using Novacode;
using System.Text.RegularExpressions;
using System.Reflection;
using System.Globalization;

public partial class Admin_Customer_Detail : System.Web.UI.Page
{
    DataSet DS = new DataSet();
    clsAdmin AD = new clsAdmin();
    DataTable dt = new DataTable();
    MyCon mycon = new MyCon();
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
            dt = mycon.FillDataTable("select * from tbl_registration with(nolock) where cast(start_date as date) = cast(@0 as date) order by start_date", DateTime.Now.ToString("yyyy-MM-dd"));
            grd_client_active.DataSource = dt;
            grd_client_active.DataBind();
        }
        catch
        { }
    }

    protected void grd_client_active_RowDataBound(object sender, GridViewRowEventArgs e)
    {
        try
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

                Label lblS1 = ((Label)e.Row.FindControl("lblStatus"));

                if (lblS1.Text == "0")
                {
                    lblS1.Text = "Not Active";
                    lblS1.ForeColor = System.Drawing.Color.Blue;
                }
                else if (lblS1.Text == "1")
                {
                    lblS1.Text = "Active";
                    lblS1.ForeColor = System.Drawing.Color.Green;
                }
                else if (lblS1.Text == "2")
                {
                    lblS1.Text = "Qc Pass";
                    lblS1.ForeColor = System.Drawing.Color.Green;
                }
                else if (lblS1.Text == "3")
                {
                    lblS1.Text = "Qc Fail";
                    lblS1.ForeColor = System.Drawing.Color.Red;
                }
                else if (lblS1.Text == "3_1")
                {
                    lblS1.Text = "Work Load Not Submited";
                    lblS1.ForeColor = System.Drawing.Color.Red;
                }
                else if (lblS1.Text == "4")
                {
                    lblS1.Text = "Completed";
                    lblS1.ForeColor = System.Drawing.Color.Green;
                }
            }
        }
        catch 
        {
            
        }

    }
    protected void grd_client_active_SelectedIndexChanged(object sender, EventArgs e)
    {

    }
    protected void Button1_Click(object sender, EventArgs e)
    {
        if (txt_datefrom.Text == "" && txt_dateto.Text == "")
        {
            grd_client_active.DataSource = null;
            grd_client_active.DataBind();
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Select from date." + "');", true);
        }
        else if (txt_datefrom.Text == "" && txt_dateto.Text != "")
        {
            grd_client_active.DataSource = null;
            grd_client_active.DataBind();
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Select form date also" + "');", true);
        }
        else if (txt_datefrom.Text != "" && txt_dateto.Text == "")
        {
            string datetime = DateTime.ParseExact(txt_datefrom.Text, "dd-MM-yyyy", CultureInfo.InvariantCulture).ToString("yyyy-MM-dd");
            dt = mycon.FillDataTable("select * from tbl_registration with(nolock) where cast(start_date as date) = cast(@0 as date) order by start_date", datetime);
            grd_client_active.DataSource = dt;
            grd_client_active.DataBind();
        }
        else if (txt_datefrom.Text != "" && txt_dateto.Text != "")
        {
            string fromdate = DateTime.ParseExact(txt_datefrom.Text, "dd-MM-yyyy", CultureInfo.InvariantCulture).ToString("yyyy-MM-dd");
            string todate = DateTime.ParseExact(txt_dateto.Text, "dd-MM-yyyy", CultureInfo.InvariantCulture).ToString("yyyy-MM-dd");
            dt = mycon.FillDataTable("select * from tbl_registration with(nolock) where cast(start_date as date) >= cast(@0 as date) and cast(start_date as date) <= cast(@1 as date) order by start_date", fromdate, todate);
            grd_client_active.DataSource = dt;
            grd_client_active.DataBind();
        }
    }
}
