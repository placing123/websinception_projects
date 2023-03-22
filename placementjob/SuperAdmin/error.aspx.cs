using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class Control_error : System.Web.UI.Page
{
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
        grd_plan.DataSource = mycon.FillDataTable("select * from tbl_error");
        grd_plan.DataBind();
    }
    public void Clere()
    {
        txt_error.Text="";
        txt_correct.Text="";
    }
    protected void btn_submit_Click(object sender, EventArgs e)
    {
        mycon.ExecutQury("insert into tbl_error (Error,Correct) values('" + txt_error.Text + "','" + txt_correct.Text + "')");
        filldata();
        Clere();
    }
    protected void btn_delete_Click(object sender, EventArgs e)
    {
        string id = Request.QueryString["Id"].ToString();
        mycon.ExecutQury("delete from tbl_error where id =" + id + "");
        filldata();
        Clere();

    }
}