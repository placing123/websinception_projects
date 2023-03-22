using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;

public partial class Franchisee_Franchisee : System.Web.UI.MasterPage
{
    clsAdmin AD = new clsAdmin();
    DataSet DS = new DataSet();
    clsFranchisee CF = new clsFranchisee();


    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            if (Session["Cus_Username"] == null)
            {
                Response.Redirect("../Default.aspx");
            }
            else
            {
                lblcid.Text = Session["Cus_Username"].ToString();
                AD.id = Session["Cus_Username"].ToString();
                DS = AD.Select_Registration_All();
                lbl_end_date.Text = DS.Tables[0].Rows[0]["End_Date"].ToString();

                //string rpt = mycon.AdExecScalar("select rpt from tbl_registration where cid='" + Session["Cus_Username"].ToString() + "'");
                //if (rpt == "1")
                //{
                //    ws.Visible = true;
                //}
                //else
                //{
                //    ws.Visible = false;
                //}
                //string logvisi = mycon.AdExecScalar("select logvisi from Tbl_Registration where CId='" + Session["Cus_Username"].ToString() + "'");
                //if (logvisi == "1")
                //{
                //    A1.Visible = true;
                //}
                //else
                //{
                //    A1.Visible = false;
                //}
                CF.Select_MissedDate_Status1();

            }
        }
    }
}
