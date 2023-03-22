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


public partial class BPO_Admin_Announcement : System.Web.UI.Page
{
    clsFranchisee CF = new clsFranchisee();

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            try
            {
                DataSet ds = CF.SelectAllAnnocemnt11();
                FreeTextBox1.Content = ds.Tables[0].Rows[0][0].ToString();

            }
            catch
            { }
        }
    }
    protected void Button1_Click(object sender, EventArgs e)
    {
        try
        {
            CF.Announcement = FreeTextBox1.Content;
            CF.SelectAllAnnocemnt();
        }

        catch { }
    }
}
