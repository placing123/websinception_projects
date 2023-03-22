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

public partial class Admin_adminpanel : System.Web.UI.Page
{
    MyCon mycon = new MyCon();
    DataTable dt = new DataTable();
    DataSet ds = new DataSet();
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            filldata();
        }
    }

    public void filldata()
    {
        ds = mycon.FillDataset(@"
select count(id) from tbl_registration with(nolock) where  cast(start_date as date)=cast(getdate() as date) and fid='ho';
select count(id) from tbl_registration with(nolock) where  cast(reg_date as date)=cast(getdate() as date) and fid='ho';
select count(id) from tbl_registration with(nolock) where cast(start_date as date)=cast(getdate() as date) and fid='fadmin';
select count(id) from tbl_registration with(nolock) where  cast(reg_date as date)=cast(getdate() as date) and fid='fadmin';
select count(id) from tbl_registration with(nolock) where cast(start_date as date)=cast(getdate() as date) and fid='new';
select count(id) from tbl_registration with(nolock) where  cast(reg_date as date)=cast(getdate() as date) and fid='new';
select count(id) from tbl_registration with(nolock) where cast(start_date as date)=cast(getdate() as date) and fid='raxit';
select count(id) from tbl_registration with(nolock) where  cast(reg_date as date)=cast(getdate() as date) and fid='raxit';
select count(id) from tbl_registration with(nolock) where cast(start_date as date)=cast(getdate() as date) and fid='kunal';
select count(id) from tbl_registration with(nolock) where  cast(reg_date as date)=cast(getdate() as date) and fid='kunal';
select count(id) from tbl_registration with(nolock) where cast(start_date as date)=cast(getdate() as date) and fid='amit';
select count(id) from tbl_registration with(nolock) where  cast(reg_date as date)=cast(getdate() as date) and fid='amit';
select count(id) from tbl_registration with(nolock) where cast(start_date as date)=cast(getdate() as date) and fid='AHM2';
select count(id) from tbl_registration with(nolock) where  cast(reg_date as date)=cast(getdate() as date) and fid='AHM2';
select count(id) from tbl_registration with(nolock) where  status='1';");

        lbl_howorkload.Text = ds.Tables[0].Rows[0][0].ToString() + "/" + ds.Tables[1].Rows[0][0].ToString();
        lbl_fadminworkload.Text = ds.Tables[2].Rows[0][0].ToString() + "/" + ds.Tables[3].Rows[0][0].ToString();
        lbl_patnaworkload.Text = ds.Tables[4].Rows[0][0].ToString() + "/" + ds.Tables[5].Rows[0][0].ToString();
        lbl_raxitworkload.Text = ds.Tables[6].Rows[0][0].ToString() + "/" + ds.Tables[7].Rows[0][0].ToString();
        lbl_kunalworkload.Text = ds.Tables[8].Rows[0][0].ToString() + "/" + ds.Tables[9].Rows[0][0].ToString();
        lbl_amitworkload.Text = ds.Tables[10].Rows[0][0].ToString() + "/" + ds.Tables[11].Rows[0][0].ToString();
        lbl_ahm2workload.Text = ds.Tables[12].Rows[0][0].ToString() + "/" + ds.Tables[13].Rows[0][0].ToString();
        lbl_activeclient.Text = ds.Tables[14].Rows[0][0].ToString();
    }
}
