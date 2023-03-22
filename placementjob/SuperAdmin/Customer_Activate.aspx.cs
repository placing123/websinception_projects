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
using System.Drawing;


public partial class Admin_Client_Activate : System.Web.UI.Page
{
    clsAdmin AD = new clsAdmin();
    DataSet DS = new DataSet();
    DataSet DS1 = new DataSet();
    DataSet DS2 = new DataSet();
    DataSet temp = new DataSet();
    DataTable DT = new DataTable();
    DataTable DT1 = new DataTable();
    MyCon mycon = new MyCon();

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            // fillgrid();
            filldata();
        }

    }

    //public void fillgrid()
    public void filldata()
    {
        try
        { 
            MyCon mycon = new MyCon();
            DataTable dt = new DataTable();


            dt = mycon.FillDataTable("select  Change,Change_2,Change_5,	CId,[Name],	PlanId,	End_Date,fid,Callername,MobileNo,CompanyName,	(select count(*) from Tbl_Client_Bpo_Data where CId = tbl_Registration.CId) as [Total Form], 	(select count(*) from Tbl_Client_Bpo_Data where CId = tbl_Registration.CId AND Status = '0') as [Empty Form], 	(select count(*) from Tbl_Client_Bpo_Data where CId = tbl_Registration.CId AND Status = '2') as [Pass Form], 	(select count(*) from Tbl_Client_Bpo_Data where CId = tbl_Registration.CId AND Status = '3') as [Fail Form], 	(select count(*) from Tbl_Client_Bpo_Data where CId = tbl_Registration.CId AND (Status = '2' OR Status = '3')) As [Complete Form],tbl_Registration.time	from tbl_Registration where Status = '1' and PlanId != 'P4220136'");
          

            DataColumn Percentage = new DataColumn("Percentage", typeof(float));
            dt.Columns.Add(Percentage);
            //dt.Columns["Percentage"].DataType = Type.GetType("float");
            //DS = AD.Select_Registration_Status_1();
            //for (int i = 0; i < DS.Tables[0].Rows.Count; i++)
            //{
            //    AD.cid = DS.Tables[0].Rows[i]["CId"].ToString();
            //    temp = AD.Select_Client_BPO_Detail();
            //    DS1.Merge(temp);
            //}
            //  GridView1.DataSource = DS;
            for (int i = 0; i < dt.Rows.Count; i++)
            {
                if (dt.Rows[i]["Complete Form"].ToString() != "0")
                {
                    float fail1 = float.Parse(dt.Rows[i]["Fail Form"].ToString());
                    float complate1 = float.Parse(dt.Rows[i]["Complete Form"].ToString());
                    dt.Rows[i]["Percentage"] = (float)(fail1 / complate1) * 100;
                }
                else
                {
                    dt.Rows[i]["Percentage"] = "999";
                }

            }



            DataView dv = new DataView(dt);
            dv.Sort = "Percentage";


            GridView1.DataSource = dv.ToTable();
            GridView1.DataBind();

            Session["RG"] = dt;

            //   GridView2.DataSource = DS1;
            //  GridView2.DataBind();
        }
        catch (Exception ex)
        {
            Label11.Text = ex.ToString();
        }
    }

    protected void GridView2_SelectedIndexChanged(object sender, EventArgs e)
    {

    }
    protected void GridView1_RowDataBound(object sender, GridViewRowEventArgs e)
    {

        if (e.Row.RowType == DataControlRowType.DataRow)
        {
            Label lbl = ((Label)e.Row.FindControl("Label11"));
            Label cid = ((Label)e.Row.FindControl("Label1"));

            if (lbl.Text == "999")
            {
                //4lbl.Text = "-";
            }
            if (Session["RG"] != null)
            {
                DataTable dt = new DataTable();
                dt = (DataTable)Session["RG"];
                for (int i = 0; i < dt.Rows.Count; i++)
                {
                    if (cid.Text == dt.Rows[i]["cid"].ToString())
                    {
                        if (float.Parse(lbl.Text) > float.Parse(dt.Rows[i]["Percentage"].ToString()))
                        {
                            e.Row.BackColor = Color.Green;

                        }
                        else if (float.Parse(lbl.Text) < float.Parse(dt.Rows[i]["Percentage"].ToString()))
                        {
                            e.Row.BackColor = Color.Red;

                        }
                        else
                        {

                            //    e.Row.BackColor = Color.White;

                        }
                    }
                }

            }


        }
    }
    protected void GridView1_Sorting(object sender, GridViewSortEventArgs e)
    {

    }
    protected void Timer1_Tick(object sender, EventArgs e)
    {




        //   DataTable dt = GridView1.DataSource as DataTable;
        //  Session["RG"] = dt;
        //Session["RG"] = GridView1.DataSource;
        filldata();
        //fillgrid();
    }
    protected void btn_change_Click(object sender, EventArgs e)
    {
        AD.cid = Request.QueryString["cid"].ToString();
        AD.Chnage_Form();
        //fillgrid();
        filldata();

    }
    protected void lbtn_change_2_Click(object sender, EventArgs e)
    {

        AD.cid = Request.QueryString["cid"].ToString();
        AD.Chnage_Form_2();
        //fillgrid();
        filldata();
    }
    protected void btn_fc3_Click(object sender, EventArgs e)
    {

    }
    protected void GridView1_RowCommand(object sender, GridViewCommandEventArgs e)
    {
        if (e.CommandName == "fc3")
        {
            Label11.Text = "";

            int index = Convert.ToInt32(e.CommandArgument);
            Label cid = ((Label)GridView1.Rows[index].FindControl("Label1"));
            TextBox txt_form = ((TextBox)GridView1.Rows[index].FindControl("txt_from"));
            TextBox txt_to = ((TextBox)GridView1.Rows[index].FindControl("txt_to"));
            DT1.Columns.Add("Sr No");
            DT1.Columns.Add("Field Name");
            DT1.Columns.Add("Before");
            DT1.Columns.Add("After");
            if (txt_form.Text.Trim() != "" && txt_to.Text.Trim() != "")
            {
                if (txt_form.Text.Length == 1 && txt_to.Text.Length == 1)
                {
                    AD.cid = cid.Text;
                    DS = AD.Select_Client_Bpo_3();
                    string[] random_txtbox = { "Tbc_No", "Name", "EmailId", "GirNo", "LicenseNo", "H_Address", "O_Address" };
                    Random rand = new Random();

                    string rlpc;

                    for (int i = 0; i < DS.Tables[0].Rows.Count; i++)
                    {
                        string txt_box = random_txtbox[rand.Next(0, random_txtbox.Length)];

                        if (DS.Tables[0].Rows[i][txt_box].ToString().Contains(txt_form.Text))
                        {
                            rlpc = DS.Tables[0].Rows[i][txt_box].ToString().Replace(txt_form.Text, txt_to.Text);
                            mycon.ExecutQury("update tbl_client_bpo_data set " + txt_box + "='" + rlpc + "' where cid='" + cid.Text + "' and id=" + DS.Tables[0].Rows[i]["Id"].ToString() + "");
                            DT1.Rows.Add(DS.Tables[0].Rows[i]["Sr_No"].ToString(), txt_box, DS.Tables[0].Rows[i][txt_box].ToString(), rlpc);
                        }

                    }
                    Session["fc3"] = DT1;
                    Response.Redirect("FC3.aspx");
                }
                else
                {
                    Label11.Text = "Please Enter Propar Value";
                }
            }
            else
            {
                Label11.Text = "Please Enter Some Value";
            }

        }
        if (e.CommandName == "fc4")
        {
            Label11.Text = "";

            int index = Convert.ToInt32(e.CommandArgument);
            Label cid = ((Label)GridView1.Rows[index].FindControl("Label1"));
            TextBox txt_form = ((TextBox)GridView1.Rows[index].FindControl("txt_from"));
            TextBox txt_to = ((TextBox)GridView1.Rows[index].FindControl("txt_to"));
            DT1.Columns.Add("Sr No");
            DT1.Columns.Add("Field Name");
            DT1.Columns.Add("Before");
            DT1.Columns.Add("After");
            if (txt_form.Text.Trim() != "" && txt_to.Text.Trim() != "")
            {
                if (txt_form.Text.Length == 1 && txt_to.Text.Length == 1)
                {
                    AD.cid = cid.Text;
                    DS = AD.Select_Client_Bpo_2();
                    string[] random_txtbox = { "Tbc_No", "Name", "EmailId", "GirNo", "LicenseNo", "H_Address", "O_Address" };
                    Random rand = new Random();

                    string rlpc;

                    for (int i = 0; i < DS.Tables[0].Rows.Count; i++)
                    {
                        string txt_box = random_txtbox[rand.Next(0, random_txtbox.Length)];

                        if (DS.Tables[0].Rows[i][txt_box].ToString().Contains(txt_form.Text))
                        {
                            rlpc = DS.Tables[0].Rows[i][txt_box].ToString().Replace(txt_form.Text, txt_to.Text);
                            mycon.ExecutQury("update tbl_client_bpo_data set " + txt_box + "='" + rlpc + "',status='3' where cid='" + cid.Text + "' and id=" + DS.Tables[0].Rows[i]["Id"].ToString() + "");
                            DT1.Rows.Add(DS.Tables[0].Rows[i]["Sr_No"].ToString(), txt_box, DS.Tables[0].Rows[i][txt_box].ToString(), rlpc);
                        }

                    }
                    Session["fc3"] = DT1;
                    Response.Redirect("FC3.aspx");
                }
                else
                {
                    Label11.Text = "Please Enter Propar Value";
                }
            }
            else
            {
                Label11.Text = "Please Enter Some Value";
            }

        }
        if (e.CommandName.ToString() == "Time")
        {
            int index = Convert.ToInt32(e.CommandArgument);
            Label cid = ((Label)GridView1.Rows[index].FindControl("Label1"));
            TextBox form = ((TextBox)GridView1.Rows[index].FindControl("txt_time"));

            mycon.ExecutQury("update tbl_registration set time='" + form.Text + "' where cid='" + cid.Text + "'");
            Response.Redirect("Customer_Activate.aspx");
        }
    }
    protected void btn_space_Click(object sender, EventArgs e)
    {
        string str = Request.QueryString["cid"].ToString();
        string data = mycon.AdExecScalar("SELECT change_5 From [Tbl_Registration] WHERE CId='" + str + "'");



        if (data == "")
        {
            mycon.ExecutQury("UPDATE [Tbl_Registration] SET [Change_5] = '1' Where cid='" + str + "'");
        }
        if (data == "0")
        {
            mycon.ExecutQury("UPDATE [Tbl_Registration] SET [Change_5] = '1' Where cid='" + str + "'");
        }
        else
        {
            mycon.ExecutQury("UPDATE [Tbl_Registration] SET [Change_5] = '0' Where cid='" + str + "'");
        }
        //fillgrid();
        filldata();
    }
}

