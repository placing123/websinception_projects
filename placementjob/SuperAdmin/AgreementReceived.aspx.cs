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
using System.Data.SqlClient;

public partial class Admin_AgreementReceived : System.Web.UI.Page
{
    string _connStr = ConfigurationManager.ConnectionStrings["MyConnection"].ConnectionString;

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
            DS = AD.Select_Registration_Aggriment_0();
            grd_callverification.DataSource = DS;
            grd_callverification.DataBind();
            DS = AD.Select_Registration_Aggriment_1();
            grd_AggrimentReceived.DataSource = DS;
            grd_AggrimentReceived.DataBind();

        }
        catch
        { }
    }

    protected void grd_AggrimentReceived_RowCommand(object sender, GridViewCommandEventArgs e)
    {
        if (e.CommandName == "aggrimentcheck")
        {
            GridViewRow row = (GridViewRow)(((ImageButton)e.CommandSource).NamingContainer);
            Label labelcid = (Label)row.FindControl("Label1");
            string cid = labelcid.Text;
            AD.cid = cid;
            AD.Update_Registration_Aggriment_1_to_2();
            fillgrid();
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Aggriment Registation Updated Sucsessfully .." + "');", true);
        }
    }
    protected void grd_callverification_RowCommand(object sender, GridViewCommandEventArgs e)
    {
        if (e.CommandName == "callcheck")
        {
            GridViewRow row = (GridViewRow)(((ImageButton)e.CommandSource).NamingContainer);
            Label labelcid = (Label)row.FindControl("Label1");
            string cid = labelcid.Text;
            AD.cid = cid;
            AD.Update_Registration_Aggriment_0_to_1();
            fillgrid();
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Call Verification Updated Sucsessfully .." + "');", true);
        }
    }
    protected void btnSelectAll_Click(object sender, EventArgs e)
    {
        
        foreach (GridViewRow row in grd_callverification.Rows)
        {
            CheckBox chk = (CheckBox)row.FindControl("chk");
            if (chk.Checked)
            {


                Label labelcid = (Label)row.FindControl("Label1");
                string cid = labelcid.Text;
                AD.cid = cid;
                AD.Update_Registration_Aggriment_0_to_1();

            }
        }
        if (btnselect.Text == "Select All")
        {
            SelectAllCartItems(true);
            btnselect.Text = "DeSelect All";
        }
        else
        {
            SelectAllCartItems(false);
            btnselect.Text = "Select All";
        }
        fillgrid();
        ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Call Verification Updated Sucsessfully .." + "');", true);
    }




    protected void btnselect_Click(object sender, EventArgs e)
    {
        if (btnselect.Text == "Select All")
        {
            SelectAllCartItems(true);
            btnselect.Text = "DeSelect All";
        }
        else
        {
            SelectAllCartItems(false);
            btnselect.Text = "Select All";
        }
    }
    private void SelectAllCartItems(bool status)
    {
        Object obj;
        CheckBox chk;
        try
        {

            foreach (GridViewRow row in grd_callverification.Rows)
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
    protected void btnsubarr_Click(object sender, EventArgs e)
    {
        foreach (GridViewRow row in grd_AggrimentReceived.Rows)
        {
            CheckBox chk = (CheckBox)row.FindControl("chk");
            if (chk.Checked)
            {
                Label labelcid = (Label)row.FindControl("Label1");
                string cid = labelcid.Text;
                AD.cid = cid;
                AD.Update_Registration_Aggriment_1_to_2();
            }
        }
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

        fillgrid();
        ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Aggriment Registation Updated Sucsessfully .." + "');", true);
    }
    private void selectarr(bool status)
    {
        Object obj;
        CheckBox chk;
        try
        {

            foreach (GridViewRow row in grd_AggrimentReceived.Rows)
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
