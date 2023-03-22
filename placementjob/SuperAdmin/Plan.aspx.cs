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

public partial class Admin_Plan : System.Web.UI.Page
{
    DataSet ds = new DataSet();
    clsAdmin AD = new clsAdmin();
    protected void Page_Load(object sender, EventArgs e)
    {
        if(!IsPostBack)
        {
            Session["plantype"] = "";
            fillno();  
            fillplangrid();
        }
    }
    public void fillno()
    {
        ds = AD.Select_Plan_All();
        DateTime date = DateTime.Now;
        string d = date.Day.ToString();
        string m = date.Month.ToString();
        string y = date.Year.ToString();
        string id = (ds.Tables[0].Rows.Count + 1).ToString();
        txt_plan_no.Text = "P" + d + m + y + id;
        }
    public void fillplangrid()
    {
        grd_plan.DataSource=AD.Select_Plan_All();
        grd_plan.DataBind();
    }

    protected void btn_submit_Click(object sender, EventArgs e)
    {
       
        try
        {
            if (fu_agreement.HasFile)
            {
                string type = Session["plantype"].ToString();
                if (type == "E")
                {
                    string str = txt_plan_no.Text + ".docx";
                    fu_agreement.PostedFile.SaveAs(Server.MapPath(".\\Agreement\\" + str));
                    AD.pid = txt_plan_no.Text;
                    AD.pname = txt_plan_name.Text;
                    AD.days = txt_days.Text;
                    AD.fees = txt_fees.Text;
                    AD.form = txt_form.Text;                    
                    AD.qccutoff = txt_qccutoff.Text;
                    AD.Update_Plan();
                    fillplangrid();
                    fillno();
                    textclear();
                    ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Plan Updated Successfully...." + "');", true);
                }
                else
                {
                    string str = txt_plan_no.Text + ".docx";
                    fu_agreement.PostedFile.SaveAs(Server.MapPath(".\\Agreement\\" + str));
                    AD.pid = txt_plan_no.Text;
                    AD.pname = txt_plan_name.Text;
                    AD.days = txt_days.Text;
                    AD.fees = txt_fees.Text;
                    AD.form = txt_form.Text;
                    AD.qccutoff = txt_qccutoff.Text;
                    AD.Insert_Plan();
                    fillplangrid();
                    fillno();
                    textclear();
                    ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Plan Inserted Successfully...." + "');", true);
                }
            }
            else
            {
                ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Plese Select Agriment...." + "');", true);

            }
            
        }
        catch 
        {
            
            
        }
       
    }
    public void textclear()
    {
        txt_plan_name.Text = "";
        txt_days.Text = "";
        txt_fees.Text = "";
        txt_form.Text = "";
        txt_qccutoff.Text = "";
    }

    protected void btn_edit_Click(object sender, EventArgs e)
    {
        string str = Request.QueryString["pid"];
        AD.pid = str;
        ds=AD.Select_Plan();
        txt_plan_no.Text = ds.Tables[0].Rows[0]["PId"].ToString();
        txt_plan_name.Text = ds.Tables[0].Rows[0]["PName"].ToString();
        txt_form.Text = ds.Tables[0].Rows[0]["Form"].ToString();
        txt_qccutoff.Text = ds.Tables[0].Rows[0]["QcCutOff"].ToString();
        txt_days.Text = ds.Tables[0].Rows[0]["Days"].ToString();
        txt_fees.Text = ds.Tables[0].Rows[0]["Fees"].ToString();
        Session["plantype"] = "E";               
    }
    protected void btn_download_Click(object sender, EventArgs e)
    {
        string str = Request.QueryString["pid"].ToString();// + ".docx";
        Response.ContentType = "application/docx";
        Response.AppendHeader("Content-Disposition", "attachment; filename=" + str + ".docx");
        Response.TransmitFile(Server.MapPath("Agreement/" + str + ".docx"));
        Response.End();
    }

    protected void btn_delete_Click(object sender, EventArgs e)
    {
        string str = Request.QueryString["pid"];
        AD.pid = str;
        AD.Delete_Plan();
        ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Plan Delete Successfully...." + "');", true);
        fillplangrid();
    }
}
