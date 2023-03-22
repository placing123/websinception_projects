using System;
using System.Collections.Generic;
using System.Linq;
using System.Collections;
using System.Configuration;
using System.Data;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Xml.Linq;

using System.IO.Compression;
using System.IO;
using System.Text;
using Ionic.Zip;
using System.Data.SqlClient;
using System.Data.OleDb;
using System.Drawing;
using System.Drawing.Imaging;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
public partial class Customer_walet : System.Web.UI.Page
{
    clsAdmin AD = new clsAdmin();
    DataTable dt = new DataTable();
    MyCon mycon = new MyCon();
    DataSet DS = new DataSet();
    clsFranchisee CF = new clsFranchisee();
    protected void Page_Load(object sender, EventArgs e)
    {

        if (Session["Cus_Username"] == null)
        {
            Response.Redirect("~/default.aspx");
        }
        if (!IsPostBack)
        {
            AD.id = Session["Cus_Username"].ToString();
            DS = AD.Select_Registration_All();
            lbl_end_date.Text = DS.Tables[0].Rows[0]["End_Date"].ToString();
        }
        FillTotalComplete();
    }
    public void FillTotalComplete()
    {
        CF.CId = Session["Cus_Username"].ToString();

        CF.QueryFor = "Complete";
        DataSet ds = CF.Master_Client_BPO_Data();
        lblcompl.Text = ds.Tables[0].Rows[0][0].ToString();

        CF.QueryFor = "Total";
        DataSet ds1 = CF.Master_Client_BPO_Data();
        lbltotal.Text = ds1.Tables[0].Rows[0][0].ToString();
        CF.CId = Session["Cus_Username"].ToString();
        DataSet dss = CF.Select_Status_Tbl_Registration_by_CID();
        if (lblcompl.Text == lbltotal.Text)
        {
            // Page.ClientScript.RegisterStartupScript(this.GetType(), "alert", "alert('Your Workload is Completed Successfully');", true);
            //lblmsg.Text = "Your Workload is Completed Successfully ";
            //lblmsg.Visible = true;


            CF.CId = Session["Cus_Username"].ToString();
            dss = CF.Select_Status_Tbl_Registration_by_CID();


            if (Convert.ToDecimal(lblcompl.Text) == 0)
            {
                int balance = (Convert.ToInt32(lblcompl.Text) * 0);
                lblbalance.Text = Convert.ToString(balance);
                lblbalance.Visible = true;
            }
            else
            {
                int balance1 = (Convert.ToInt32(lblcompl.Text) * 40);
                lblbalance.Text = Convert.ToString(balance1);
                lblbalance.Visible = true;
            }
        }
        else
        {
            CF.CId = Session["Cus_Username"].ToString();
            dss = CF.Select_Status_Tbl_Registration_by_CID();
            if (Convert.ToDecimal(lblcompl.Text) == 0)
            {
                int balance = (Convert.ToInt32(lblcompl.Text) * 0);
                lblbalance.Text = Convert.ToString(balance);
                lblbalance.Visible = true;
            }
            else
            {
                int balance1 = (Convert.ToInt32(lblcompl.Text) * 35);
                lblbalance.Text = Convert.ToString(balance1);
                lblbalance.Visible = true;
            }
        }
    }
}