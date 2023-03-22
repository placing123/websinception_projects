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


public partial class Franchisee_Plan : System.Web.UI.Page
{
    clsAdmin CA = new clsAdmin();

    protected void Page_Load(object sender, EventArgs e)
    {

        DataSet ds = CA.Select_Plan_All();
        viewplan.DataSource = ds;
        viewplan.DataBind();
    }
}