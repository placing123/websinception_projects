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

public class clsAdmin
{
    public string username { get; set; }
    public string password { get; set; }
    public string id { get; set; }
    public string cid { get; set; }
    public string pid { get; set; }
    public string pname { get; set; }
    public string days { get; set; }
    public string fees { get; set; }
    public string form { get; set; }
    public string qccutoff { get; set; }
    public string sdate { get; set; }
    public string edate { get; set; }

    public static string GetConnection()
    {
        string sqlConnection = System.Configuration.ConfigurationManager.ConnectionStrings["MyConnection"].ToString();
        return sqlConnection;
    }

    public DataSet admin_login()
    {

        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Admin_Login", username, password);
            return ds;
        }
    }




    public DataSet Select_Active_Franc()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Select_Active_Franc");
            return ds;
        }
    }

    public DataSet Select_Deactivate_Franc()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Select_Deactivate_Franc");
            return ds;
        }
    }

    public DataSet Select_Planid_From_Registration()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Select_Planid_From_Registration", id);
            return ds;
        }
    }

    public DataSet Select_No_Of_Bpo_Data()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Select_No_Of_Bpo_Data", form);
            return ds;
        }
    }
    
    public void Insert_Client_Bpo_Activate()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            SqlHelper.ExecuteDataset(objconn, "Insert_Client_Bpo_Activate",cid,id);
        }
    }

    public DataSet Select_Active_Client()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Select_Active_Client",id);
            return ds;
        }
    }

    public DataSet Select_Deactive_Client()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Select_Deactive_Client", id);
            return ds;
        }
    }
    public DataSet Select_Franc_List()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Select_Franc_List");
            return ds;
        }
    }

    public void Update_Franchisee_Active()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            SqlHelper.ExecuteDataset(objconn, "Update_Franchisee_Active",id);

        }
    }


    public void Update_Registration_Date()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            SqlHelper.ExecuteDataset(objconn, "Update_Registration_Date", cid,sdate,edate);

        }
    }
    public void Update_Franchisee_Deactive()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            SqlHelper.ExecuteDataset(objconn, "Update_Franchisee_Deactive",id);

        }
    }

    public DataSet Select_Registration_All()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Select_Registration_All", id);
            return ds;
        }
    }
    public DataSet Select_Franchisee_All()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Select_Franchisee_All", id);
            return ds;
        }
    }

    public void Update_Client_Active()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            objconn.Open();
            SqlHelper.ExecuteDataset(objconn, "Update_Client_Active", id);
        }

    }
    public void Update_Client_Deactivate()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            objconn.Open();
            SqlHelper.ExecuteDataset(objconn, "Update_Client_Deactivate", id);
        }

    }
    //-----------------------------Plan---------------------------------------------
    
    public DataSet Select_Plan_All()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Select_Plan_All");
            return ds;
        }
    }
    public void Insert_Plan()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            objconn.Open();
            SqlHelper.ExecuteDataset(objconn, "Insert_Plan", pid,pname,days,fees,form,qccutoff);
        }

    }
    public DataSet Select_Plan()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Select_Plan",pid);
            return ds;
        }
    }
    public void Update_Plan()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            objconn.Open();
            SqlHelper.ExecuteDataset(objconn, "Update_Plan", pid, pname, days, fees,form,qccutoff);
        }

    }
    //-----------------------------------------Submision End date----------------------------
    public string enddate { get; set; }

    public DataSet Select_Registration_EndDate()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Select_Registration_EndDate", enddate);
            return ds;
        }
    }
    //---------------------------------------Submited------------------------------------------

    public DataSet Select_Registration_Submitted()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Select_Registration_Submitted");
            return ds;
        }
    }
    //---------------------------------------Submission fail------------------------------------------

    public DataSet Select_Registration_Fail()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Select_Registration_Fail");
            return ds;
        }
    }
    //---------------------------------------Submission Complete------------------------------------------

    public DataSet Select_Registration_Complete()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Select_Registration_Complete");
            return ds;
        }
    }
    //---------------------------------------Submission Update------------------------------------------

    public void Update_Submited_Client()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
           
             SqlHelper.ExecuteDataset(objconn, "Update_Submited_Client",id);
            
        }
    }
    //--------------------------------------Data Creator----------------------------------------------
    public string Tbc_No { get; set; }
    public string Name { get; set; }
    public string EmailId { get; set; }
    public string MobileNo { get; set; }
    public string Gender { get; set; }
    public string LicenseNo { get; set; }
    public string GirNo { get; set; }
    public string PanNo { get; set; }
    public string H_Address { get; set; }
    public string H_City { get; set; }
    public string H_PinNo { get; set; }
    public string H_State { get; set; }
    public string O_Address { get; set; }
    public string O_City { get; set; }
    public string O_PinNo { get; set; }
    public string LAL { get; set; }
    public string MRNNo { get; set; }
    public string AF { get; set; }
    public string NRI { get; set; }
    public string CP { get; set; }

    public DataSet Insert_Bpo_Data()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet DS = new DataSet();
            DS=SqlHelper.ExecuteDataset(objconn, "Insert_Bpo_Data", Tbc_No, Name, EmailId, MobileNo, Gender, LicenseNo, GirNo, PanNo, H_Address, H_City, H_PinNo, H_State, O_Address, O_City, O_PinNo, LAL, MRNNo, AF, NRI, CP);
            return DS;
        }
    }

}
