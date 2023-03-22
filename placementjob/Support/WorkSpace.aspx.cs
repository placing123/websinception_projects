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

public partial class Admin_WorkSpace : System.Web.UI.Page
{

    DataSet DS = new DataSet();
    clsAdmin AD = new clsAdmin();
    MyCon mycon = new MyCon();
    DataTable dt = new DataTable();
    protected void Page_Load(object sender, EventArgs e)
    {

    }
    protected void drd_fran_SelectedIndexChanged(object sender, EventArgs e)
    {
    }

    protected void butSUBMIT_Click(object sender, EventArgs e)
    {
        fillgrid();
    }

    private void fillgrid()
    {
        dt = mycon.FillDataTable("select *,(select pname from tbl_plan with(nolock) where pid=Tbl_Registration.PlanId) as planname,(select count(id) from tbl_client_bpo_data with(nolock) where cid=tbl_Registration.cid and status in ('2','3')) as completeform,(select count(id) from tbl_client_bpo_data with(nolock) where cid=tbl_Registration.cid and status in ('3')) as failform,(select count(id) from tbl_client_bpo_data with(nolock) where cid=tbl_Registration.cid and status in ('2')) as passform,(select count(id) from tbl_client_bpo_data with(nolock) where cid=tbl_Registration.cid and spaceerror='1') as spaceform from Tbl_Registration with(nolock) where CId Like('%" + txtCID.Text + "%') And  [Name] Like('%" + txtNAME.Text + "%') And  [EmailId] Like('%" + txt_emailid.Text + "%')");
        grd_client_active.DataSource = dt;
        grd_client_active.DataBind();
        if (dt.Rows.Count==1)
        {
            filldata(dt.Rows[0]["cid"].ToString());
        }
        else
        {
            tblwork.Visible = false;
        }
    }

    public void filldata(string CId)
    {
        tblwork.Visible = true;
        try
        {
            AD.cid = CId;
            AD.Name = "";
            AD.FId = "";
            DS = AD.Select_Client_CNF();
            dt = mycon.FillDataTable("select *,(select pname from tbl_plan with(nolock) where pid=Tbl_Registration.PlanId) as planname,(select count(id) from tbl_client_bpo_data with(nolock) where cid=tbl_Registration.cid and status in ('2','3')) as completeform,(select count(id) from tbl_client_bpo_data with(nolock) where cid=tbl_Registration.cid and status in ('3')) as failform,(select count(id) from tbl_client_bpo_data with(nolock) where cid=tbl_Registration.cid and status in ('2')) as passform,(select count(id) from tbl_client_bpo_data with(nolock) where cid=tbl_Registration.cid and spaceerror='1') as spaceform from Tbl_Registration with(nolock) where CId Like('%" + CId + "%')");
            string cName = DS.Tables[0].Rows[0]["Name"].ToString();
            grd_client_active.DataSource = dt;
            grd_client_active.DataBind();

            lblCID.Text = CId;
            lblNAME.Text = cName;
            AD.cid = CId;
            DS = AD.Select_Client_BPO_Detail();

            lblTFORM.Text = DS.Tables[0].Rows[0]["Totle Form"].ToString();
            lblEFORM.Text = DS.Tables[0].Rows[0]["Empty Form"].ToString();
            lblPFORM.Text = DS.Tables[0].Rows[0]["Pass Form"].ToString();
            lblFForm.Text = DS.Tables[0].Rows[0]["Fail From"].ToString();

            lblPFORMDetail.Text = "";
            lblFFORMDetail.Text = "";
            lbl_emt_img_no.Text = "";
            lblSFORMDetail.Text = "";
            for (int i = 0; i < DS.Tables[2].Rows.Count; i++)
            {
                lblPFORMDetail.Text += DS.Tables[2].Rows[i]["Sr_No"].ToString() + ", ";
            }

            for (int i = 0; i < DS.Tables[3].Rows.Count; i++)
            {
                //LinkButton1.Text += DS.Tables[3].Rows[i]["Sr_No"].ToString() + ", ";

                lblFFORMDetail.Text += DS.Tables[3].Rows[i]["Sr_No"].ToString() + ", ";
            }

            DataTable DT = new DataTable();
            DT = mycon.FillDataTable("select Sr_No from tbl_client_bpo_data with(nolock) where status='0' and CId='" + CId + "' order by Sr_No");

            for (int i = 0; i < DT.Rows.Count; i++)
            {
                //LinkButton1.Text += DS.Tables[3].Rows[i]["Sr_No"].ToString() + ", ";

                lbl_emt_img_no.Text += DT.Rows[i]["Sr_No"].ToString() + ", ";
            }
            DT = new DataTable();
            DT = mycon.FillDataTable("select Sr_No from tbl_client_bpo_data with(nolock) where status='1'and CId='" + CId + "'  order by Sr_No");

            for (int i = 0; i < DT.Rows.Count; i++)
            {
                //LinkButton1.Text += DS.Tables[3].Rows[i]["Sr_No"].ToString() + ", ";

                lbl_no_sub_form.Text += DT.Rows[i]["Sr_No"].ToString() + ", ";
            }
            string str = mycon.AdExecScalar("select COUNT(Sr_No) from tbl_client_bpo_data with(nolock) where status='1' and CId='" + CId + "'  order by Sr_No");
            LBLNOFORM.Text = str;

            DT = mycon.FillDataTable("select Sr_No from tbl_client_bpo_data with(nolock) where spaceerror='1' and CId='" + CId + "' order by sr_no");
            lblSForm.Text = DT.Rows.Count.ToString();

            for (int i = 0; i < DT.Rows.Count; i++)
            {
                //LinkButton1.Text += DS.Tables[3].Rows[i]["Sr_No"].ToString() + ", ";

                lblSFORMDetail.Text += DT.Rows[i]["Sr_No"].ToString() + ", ";
            }
        }
        catch
        {
        }
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
    protected void btn_OPEN_Click(object sender, EventArgs e)
    {
        tblwork.Visible = true;
        string CId = Request.QueryString["Cid"].ToString();
        filldata(CId);
    }
    protected void butFORM_Click(object sender, EventArgs e)
    {
        Session["id"] = txtFORMNO.Text;
        Session["Ccustomer_Cid"] = lblCID.Text;
        Response.Redirect("ASCII.aspx");
    }
    protected void btn_clr_Click(object sender, EventArgs e)
    {
        string CId = Request.QueryString["Cid"].ToString();
        MyCon mycon = new MyCon();
        mycon.ExecutQury("update Tbl_Registration set login='0' where cid='" + CId + " '");
        ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Cleare.." + "');", true);
    }
    protected void btn_chg_Click(object sender, EventArgs e)
    {
        string cid = Request.QueryString["cid"].ToString();
        Session["Cus_Username"] = cid;
        Session["QC"] = "Pass";
        Response.Redirect("../Customer/Admin_Default.aspx");
    }
    protected void btn_chg_fl_Click(object sender, EventArgs e)
    {
        string cid = Request.QueryString["cid"].ToString();
        Session["Cus_Username"] = cid;
        Session["QC"] = "Fail";
        //Session["Fail11"] = "0";
        Response.Redirect("../Customer/Admin_Default.aspx");
    }
    protected void Button1_Click(object sender, EventArgs e)
    {
        DataTable dt = new DataTable();
        MyCon mycon = new MyCon();
        //s dt = mycon.FillDataTable("select sr_no from Tbl_Client_Bpo_Data where cid='" + lblCID.Text + "' and Tbc_No like '%" + txt_tbc.Text + "%'");
        dt = mycon.FillDataTable("select sr_no from tbl_client_bpo_data where cid='" + lblCID.Text + "' and id in(select id from tbl_bpo_data where tbc_no like '%" + txt_tbc.Text + "%')");
        Label1.Text = "";
        for (int i = 0; i < dt.Rows.Count; i++)
        {
            Label1.Text = Label1.Text + dt.Rows[i][0].ToString() + ",";
        }
    }
    protected void btn_log_Click(object sender, EventArgs e)
    {
        tbllog.Visible = true;
        string CId = Request.QueryString["Cid"].ToString();
        try
        {
            DataSet ds = new DataSet();
            AD.cid = CId;
            ds = AD.Select_Log();
            GridView1.DataSource = ds;
            GridView1.DataBind();
        }
        catch (Exception)
        {

            throw;
        }
    }
}
