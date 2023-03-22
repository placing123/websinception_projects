using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;

public partial class Control_payment : System.Web.UI.Page
{
    MyCon my = new MyCon();
    DataTable dt = new DataTable();

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
                      

        }
        
    }
    public void fillgrid(string str)
    {
        dt = new DataTable();
        dt = my.FillDataTable(str);
        grd_client_active.DataSource = dt;
        grd_client_active.DataBind();

    }
    protected void grd_client_active_RowDataBound(object sender, GridViewRowEventArgs e)
    {
        if (e.Row.RowType == DataControlRowType.DataRow)
        {
            Label lbldate = ((Label)e.Row.FindControl("Label1"));
            if (lbldate.Text == "0")
            {
                lbldate.Text = "Registered";
                lbldate.ForeColor = System.Drawing.Color.Red;
                
            }
            if (lbldate.Text == "1")
            {
                lbldate.Text = "Paid";
                lbldate.ForeColor = System.Drawing.Color.Green;
            }
        }
    }
    protected void btn_sdate_Click(object sender, EventArgs e)
    {
        if (txt_tdate.Text == "")
        {
            fillgrid("SELECT * FROM [Tbl_Payment] where [date] ='" + txt_fdate.Text + "' order by id desc");
        }
        
        else
        {
            if (txt_fdate.Text != "" && txt_tdate.Text != "")
            {
                fillgrid("SELECT * FROM [Tbl_Payment] where [date] BETWEEN '" + txt_fdate.Text + "' and '" + txt_tdate.Text + "' order by id desc");
            }
            else
            {
                ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Invalid dates" + "');", true);
            }
            
        }
        grd_client_active.Visible = true;
        grdclient.Visible = false;
    }
    protected void btn_semail_Click(object sender, EventArgs e)
    {
        fillgrid("SELECT * FROM [Tbl_Payment] where [email] ='" + txt_esarch.Text.Trim() + "' order by id desc");
        grd_client_active.Visible = true;
        grdclient.Visible = false;
    }
    protected void LinkButton1_Click(object sender, EventArgs e)
    {
        if (Request.QueryString["Email"] != null)
        {
            string str = Request.QueryString["Email"].ToString();
            grd_client_active.Visible = true;
            grdclient.Visible = true;
            dt = new DataTable();
            dt = my.FillDataTable("Select * from tbl_product where [email]='" + str + "'");
            grdclient.DataSource = dt;
            grdclient.DataBind();
        }
        else
        {
            grd_client_active.Visible = true;
            grdclient.Visible = false;
        }
       
    }
    protected void LinkButton2_Click(object sender, EventArgs e)
    {
        if (Request.QueryString["Email"] != null)
        {
            string str = Request.QueryString["Email"].ToString();
            my.ExecutQury("delete FROM [Tbl_Payment] where [email]='" + str + "'");
            my.ExecutQury("delete from tbl_product where [email]='" + str + "'");
            if (txt_tdate.Text == "")
            {
                fillgrid("SELECT * FROM [Tbl_Payment] where [date] ='" + txt_fdate.Text + "' order by id desc");
            }

            else
            {
                if (txt_fdate.Text != "" && txt_tdate.Text != "")
                {
                    fillgrid("SELECT * FROM [Tbl_Payment] where [date] BETWEEN '" + txt_fdate.Text + "' and '" + txt_tdate.Text + "' order by id desc");
                }
                else
                {
                    ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Invalid dates" + "');", true);
                }

            }
            grd_client_active.Visible = true;
            grdclient.Visible = false;
        }
    }
    protected void LinkButton3_Click(object sender, EventArgs e)
    {
        if (Request.QueryString["Email"] != null)
        {
            string str = Request.QueryString["Email"].ToString();
            my.ExecutQury("update tbl_payment set status='1' where [email]='" + str + "'");
            if (txt_tdate.Text == "")
            {
                fillgrid("SELECT * FROM [Tbl_Payment] where [date] ='" + txt_fdate.Text + "' order by id desc");
            }

            else
            {
                if (txt_fdate.Text != "" && txt_tdate.Text != "")
                {
                    fillgrid("SELECT * FROM [Tbl_Payment] where [date] BETWEEN '" + txt_fdate.Text + "' and '" + txt_tdate.Text + "' order by id desc");
                }
                else
                {
                    ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Invalid dates" + "');", true);
                }

            }
            grd_client_active.Visible = true;
            grdclient.Visible = false;
        }
    }
    protected void grd_client_active_RowCommand(object sender, GridViewCommandEventArgs e)
    {
        MyCon mycon = new MyCon();
        if (e.CommandName.ToString() == "Time")
        {
            int index = Convert.ToInt32(e.CommandArgument);
            Label cid = ((Label)grd_client_active.Rows[index].FindControl("CId"));
            TextBox form = ((TextBox)grd_client_active.Rows[index].FindControl("txt_time"));

            mycon.ExecutQury("UPDATE [Tbl_Payment]  SET [followup] ='" + form.Text + "' WHERE id='" + cid.Text + "' ");
            if (txt_tdate.Text == "")
            {
                fillgrid("SELECT * FROM [Tbl_Payment] where [date] ='" + txt_fdate.Text + "' order by id desc");
            }

            else
            {
                if (txt_fdate.Text != "" && txt_tdate.Text != "")
                {
                    fillgrid("SELECT * FROM [Tbl_Payment] where [date] BETWEEN '" + txt_fdate.Text + "' and '" + txt_tdate.Text + "' order by id desc");
                }
                else
                {
                    ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Invalid dates" + "');", true);
                }

            }
            grd_client_active.Visible = true;
            grdclient.Visible = false;
            //Response.Redirect("payment.aspx");
        }
    }
}