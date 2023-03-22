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

/// <summary>
/// Summary description for MyCon
/// </summary>
public class MyCon
{

    public SqlConnection con = new SqlConnection(System.Configuration.ConfigurationManager.ConnectionStrings["MyConnection"].ToString());
    SqlCommand cmd = new SqlCommand();
    string Str;
    public string qurey;
    public string falge;
    public SqlCommand cmd1 = new SqlCommand();

    public MyCon()
    {

        //
        // TODO: Add constructor logic here
        //
    }
    public void ExecutQury(string strQury)
    {
        try
        {
            con.Open();
            cmd = new SqlCommand(strQury, con);
            cmd.ExecuteNonQuery();
            con.Close();
        }
        catch (Exception ex)
        {
        }
    }
    public DataTable FillDataTable(string strQury)
    {        
            DataTable dt = new DataTable();
            cmd = new SqlCommand(strQury, con);
            cmd.CommandText = strQury;
            SqlDataAdapter da = new SqlDataAdapter(cmd);
            dt.Clear();
            da.Fill(dt);
            return dt;
      
    }

    public void SaveData()
    {
        con.Open();
        cmd1.Connection = con;
        cmd1.CommandText = qurey;
        cmd1.CommandType = CommandType.StoredProcedure;
        cmd1.ExecuteNonQuery();
        cmd1.Parameters.Clear();
        con.Close();
    }
    public string AdExecScalar(string str)
    {
        try
        {
            if (con.State == ConnectionState.Closed)
            {
                con.Open();
            }
            cmd = new SqlCommand(str, con);
            str = Convert.ToString(cmd.ExecuteScalar());
            con.Close();
            if (str == "")
            {
                str = "0";
            }
            return str;
        }
        catch (Exception ex)
        {
            return "0";
        }
    }
   
}
