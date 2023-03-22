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

public partial class Admin_SubmissionEndDate : System.Web.UI.Page
{
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
    {  //{
    //    DateTime dt = System.DateTime.Now;

    //    string a = dt.ToString("MM/dd/yyyy");






        AD.enddate = DateTime.Now.ToString("MM/dd/yyyy");
        DS = AD.Select_Registration_EndDate();
        grd_submissionend.DataSource = DS;
        grd_submissionend.DataBind();
        
    }
    protected void txt_date_TextChanged(object sender, EventArgs e)
    {
        AD.enddate = txt_date.Text.ToString();
        DS = AD.Select_Registration_EndDate();
        grd_submissionend.DataSource = DS;
        grd_submissionend.DataBind();
    }
}
