using System;
using System.Data;
using System.Configuration;
using System.Linq;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Xml.Linq;
using System.Data.SqlClient;
using DLL;
using System.Drawing;


public partial class Franchisee_Plan : System.Web.UI.Page
{
    DataTable dt = new DataTable();
    MyCon mycon = new MyCon();
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            filldata();
        }
    }
    public void filldata()
    {
        dt = mycon.FillDataTable("select  id,companyname,cxactiveremark,Change,Change_2,Change_5,	CId,[Name],mobileno,emailid,	PlanId, 	End_Date,fid, 	(select count(*) from Tbl_Client_Bpo_Data where CId = tbl_Registration.CId) as [Total Form], 	(select count(*) from Tbl_Client_Bpo_Data where CId = tbl_Registration.CId AND Status = '0') as [Empty Form], 	(select count(*) from Tbl_Client_Bpo_Data where CId = tbl_Registration.CId AND Status = '2') as [Pass Form], 	(select count(*) from Tbl_Client_Bpo_Data where CId = tbl_Registration.CId AND Status = '3') as [Fail Form], 	(select count(*) from Tbl_Client_Bpo_Data where CId = tbl_Registration.CId AND (Status = '2' OR Status = '3')) As [Complete Form],tbl_Registration.time	from tbl_Registration where Status = '1' and PlanId != 'P4920181' order by end_date");

        DataColumn Percentage = new DataColumn("Percentage", typeof(float));
        dt.Columns.Add(Percentage);

        for (int i = 0; i < dt.Rows.Count; i++)
        {
            if (dt.Rows[i]["Complete Form"].ToString() != "0")
            {
                float fail1 = float.Parse(dt.Rows[i]["Pass Form"].ToString());
                float complate1 = float.Parse(dt.Rows[i]["Complete Form"].ToString());
                dt.Rows[i]["Percentage"] = (float)(fail1 / complate1) * 100;
            }
            else
            {
                dt.Rows[i]["Percentage"] = "0";
            }

        }

        DataView dv = new DataView(dt);
        dv.Sort = "Percentage DESC";

        GridView1.DataSource = dt;// dv.ToTable();
        GridView1.DataBind();

        Session["RG"] = dt;
    }

    protected void GridView1_RowCommand(object sender, GridViewCommandEventArgs e)
    {

        if (e.CommandName == "updateremark")
        {
            int index = Convert.ToInt32(e.CommandArgument);
            Label id = ((Label)GridView1.Rows[index].FindControl("Label2"));
            TextBox txt_remark = ((TextBox)GridView1.Rows[index].FindControl("TextBox12"));
            mycon.ExecutQury("update tbl_registration set cxactiveremark=@0 where id=@1 ", txt_remark.Text,id.Text);
            filldata();
        }

    }
}