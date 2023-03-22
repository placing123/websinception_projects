using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;
using System.Data.SqlClient;

public partial class Customer_AutoHelpLine : System.Web.UI.Page
{
    clsAdmin AD = new clsAdmin();
    DataSet ds = new DataSet();
    MyCon mycon = new MyCon();
    DataTable dt = new DataTable();
    DataTable dt1 = new DataTable();
    SqlConnection con = new SqlConnection(System.Configuration.ConfigurationManager.ConnectionStrings["MyConnection"].ToString());

    SqlCommand cmd = new SqlCommand();
    protected void Page_Load(object sender, EventArgs e)
    {
        try
        {
            if (!IsPostBack)
            {
                FillDropDown();
                DataUpdate();
                FillPending();
                FillData();
            }
        }
        catch (Exception ex)
        {
        }
    }

    private void DataUpdate()
    {
        dt = mycon.FillDataTable("select * from tbl_help where cid='" + Session["Cus_Username"].ToString() + "' and status='0' order by id");
        DateTime datetime = new DateTime();
        for (int i = 0; i < dt.Rows.Count; i++)
        {
            datetime = Convert.ToDateTime(dt.Rows[i]["datetime"].ToString());
            var one = datetime;
            var two = mycon.indianTime();

            TimeSpan ts = new TimeSpan();
            string data;
            ts = two - one;
            if (ts.Days > 0)
            {
                data = mycon.AdExecScalar("select " + dt.Rows[i]["field"].ToString() + " from tbl_bpo_data where id=(select id from tbl_client_bpo_data where sr_no='" + dt.Rows[i]["form"].ToString() + "' and cid='" + dt.Rows[i]["cid"].ToString() + "')");
                try
                {
                    if (con.State == ConnectionState.Closed)
                    {
                        con.Open();
                    }
                    cmd = new SqlCommand("update tbl_help set data=@data,status='1' where id='" + dt.Rows[i]["id"].ToString() + "'", con);
                    cmd.Parameters.Add(new SqlParameter("@data", data));
                    cmd.ExecuteNonQuery();
                    con.Close();
                }
                catch (Exception ex)
                {
                }
            }
            else if (ts.Hours >= 2)
            {
                data = mycon.AdExecScalar("select " + dt.Rows[i]["field"].ToString() + " from tbl_bpo_data where id=(select id from tbl_client_bpo_data where sr_no='" + dt.Rows[i]["form"].ToString() + "' and cid='" + dt.Rows[i]["cid"].ToString() + "')");
                try
                {
                    if (con.State == ConnectionState.Closed)
                    {
                        con.Open();
                    }
                    cmd = new SqlCommand("update tbl_help set data=@data,status='1' where id='" + dt.Rows[i]["id"].ToString() + "'", con);
                    cmd.Parameters.Add(new SqlParameter("@data", data));
                    cmd.ExecuteNonQuery();
                    con.Close();
                }
                catch (Exception ex)
                {
                }
            }
        }
    }
    private void FillData()
    {
        grd_form_detail.DataSource = mycon.FillDataTable("select * from tbl_help where cid='" + Session["Cus_Username"].ToString() + "' and status='1' order by id");
        grd_form_detail.DataBind();
    }
    private void FillPending()
    {
        grd_pending.DataSource = mycon.FillDataTable("select * from tbl_help where cid='" + Session["Cus_Username"].ToString() + "' and status='0' order by id");
        grd_pending.DataBind();
    }
    private void FillDropDown()
    {
        AD.cid = Session["Cus_Username"].ToString();
        ds = AD.Select_Client_Bpo_Data_Cid_SrNo();
        ddl_form.DataTextField = ds.Tables[0].Columns["sr_no"].ToString();
        ddl_form.DataValueField = ds.Tables[0].Columns["id"].ToString();
        ddl_form.DataSource = ds;
        ddl_form.DataBind();
    }
    protected void btn_submit_Click(object sender, EventArgs e)
    {
        try
        {
            string helpform1, totalform1;

            helpform1 = mycon.AdExecScalar("select count(distinct form) from tbl_help where cid='" + Session["Cus_Username"].ToString() + "'");
            totalform1 = mycon.AdExecScalar("SELECT Tbl_Plan.Form FROM Tbl_Plan INNER JOIN Tbl_Registration ON Tbl_Plan.PId = Tbl_Registration.PlanId where Tbl_Registration.cid='" + Session["Cus_Username"].ToString() + "'");
            int totalform = Convert.ToInt32(totalform1) / 10;
            int helpform = Convert.ToInt32(helpform1);
            if (helpform < totalform)
            {
                mycon.ExecutQury("insert into tbl_help (cid,form,field,Field_name,datetime,status) values('" + Session["Cus_Username"].ToString() + "','" + ddl_form.SelectedItem + "','" + ddl_field.SelectedValue + "','" + ddl_field.SelectedItem + "','" + mycon.indianTime().ToString("yyyy-MM-dd hh:mm:ss tt") + "','0')");
                ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Help Line Request Accepted...." + "');", true);
            }
            else
            {
                dt = mycon.FillDataTable("select id from tbl_help where cid='" + Session["Cus_Username"].ToString() + "' and form='" + ddl_form.SelectedItem + "' ");
                if (dt.Rows.Count > 0)
                {
                    mycon.ExecutQury("insert into tbl_help (cid,form,field,Field_name,datetime,status) values('" + Session["Cus_Username"].ToString() + "','" + ddl_form.SelectedItem + "','" + ddl_field.SelectedValue + "','" + ddl_field.SelectedItem + "','" + mycon.indianTime().ToString("yyyy-MM-dd hh:mm:ss tt") + "','0')");
                    ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Help Line Request Accepted...." + "');", true);
                }
                else
                {
                    ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Sorry Help Line Not avilable any more...." + "');", true);
                }
            }
            FillPending();
            FillData();

        }
        catch (Exception ex)
        {
        }
    }
}