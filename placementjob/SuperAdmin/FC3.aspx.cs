using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;

public partial class Admin_FC3 : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        try
        {
            GridView1.DataSource = (DataTable)Session["fc3"];
            GridView1.DataBind();
        }
        catch (Exception ex)
        {
        }
    }
    protected void Button1_Click(object sender, EventArgs e)
    {
        try
        {
            MyCon mycon = new MyCon();
            DataTable dt= new DataTable();
            dt = (DataTable)Session["fc3"];
            mycon.ExportDataSetToExcel(dt, "fc3.xls");
        }
        catch (Exception ex)
        {
        }
    }
}