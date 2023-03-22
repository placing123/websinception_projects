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

public partial class Admin_Date_Change : System.Web.UI.Page
{
    clsAdmin AD = new clsAdmin();
    DataSet DS = new DataSet();
    MyCon mycon = new MyCon();

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            Fill_Gride();
            cleareDate();
        }

    }
    public void cleareDate()
    {
        lbl_cid.Text = "";
        lbl_current_date.Text = "";
        txt_date.Text = "";
    }
    public void Fill_Gride()
    {
        try
        {
            DataTable dt = mycon.FillDataTable("select * from tbl_registration where status in ('3','3_1')");
            DS = AD.Select_Client_Bpo_Data_3_1();
            grd_client_active.DataSource = dt;
            grd_client_active.DataBind();
        }
        catch (Exception ex)
        {


        }



    }

    protected void grd_client_active_RowDataBound(object sender, GridViewRowEventArgs e)
    {
        try
        {

            if (e.Row.RowType == DataControlRowType.DataRow)
            {
                Label lbldate = ((Label)e.Row.FindControl("lblstyle"));

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

        }
        catch (Exception ex)
        {


        }


    }
    protected void btn_change_Click(object sender, EventArgs e)
    {
        try
        {
            string cid = Request.QueryString[0].ToString();
            lbl_cid.Text = cid;
            AD.id = cid;
            DS = AD.Select_Registration_All();
            lbl_current_date.Text = DS.Tables[0].Rows[0]["End_Date"].ToString();
        }
        catch (Exception ex)
        {


        }


    }
    protected void btn_date_change_Click(object sender, EventArgs e)
    {
        try
        {
            if (lbl_cid.Text == "")
            {
                ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Plase Select Customer For Change Ending Date..." + "');", true);
            }
            else if(txt_date.Text=="")      
            {
                ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Please Select Date..." + "');", true);
            }
            else
            {
                AD.cid = lbl_cid.Text;
                AD.enddate = txt_date.Text;
                AD.Update_Registration_Date_Status();
                Fill_Gride();
                cleareDate();
                ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Date Updated Sucsessfully..." + "');", true);
            }

        }
        catch (Exception ex)
        {


        }


    }
}

