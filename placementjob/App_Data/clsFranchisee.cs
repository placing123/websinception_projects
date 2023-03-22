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

/// <summary>
/// Summary description for clsFranchisee
/// </summary>
public class clsFranchisee
{
    public string username { get; set; }
    public string password { get; set; }
    public string type { get; set; }
    public string CId { get; set; }
    public string FId { get; set; }
    public string Name { get; set; }
    public string Address { get; set; }
    public string City { get; set; }
    public string MobileNo { get; set; }
    public string EmailId { get; set; }
    public string PlanId { get; set; }
    public string Reg_Date { get; set; }
    public string Status { get; set; }
    public string Style { get; set; }
    public string FranchiseeName { get; set; }
    public string CompanyName { get; set; }
    public string Landline { get; set; }
    public string Email { get; set; }
    public string BankAC { get; set; }
    public string IFCCode { get; set; }
    public string RegisteredDate { get; set; }
    public string Temp { get; set; }
    public string QueryFor { get; set; }
    public string Gender { get; set; }
    public string LicenseNo { get; set; }
    public string GirNo { get; set; }
    public string PanNo { get; set; }
    public string H_Address { get; set; }
    public string H_City { get; set; }
    public string H_PinNo { get; set; }
    public string H_State { get; set; }
    public string H_Country { get; set; }
    public string O_Address { get; set; }
    public string O_City { get; set; }
    public string O_PinNo { get; set; }
    public string LAL { get; set; }
    public string MRNNo { get; set; }
    public string AF { get; set; }
    public string NRI { get; set; }
    public string CP { get; set; }
    public int Id { get; set; }
    public string Tbc_No { get; set; }
    public string Pagejumpto { get; set; }
    public string Fstring { get; set; }
    public string Condition { get; set; }
    public string dt { get; set; }
    public string  Announcement { get; set; }
    



    public static string GetConnection()
    {
        string sqlConnection = System.Configuration.ConfigurationManager.ConnectionStrings["MyConnection"].ToString();
        return sqlConnection;
    }

    public DataSet Master_Client_BPO_Data()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            DataSet ds = new DataSet();
            objconn.Open();
            return ds = SqlHelper.ExecuteDataset(objconn, "Master_Client_BPO_Data", CId, QueryFor);
        }
    }

    public int SelectAllAnnocemnt()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {           
            objconn.Open();
            return SqlHelper.ExecuteNonQuery(objconn, "SelectAllAnnocemnt", Announcement);
        }
    }

    public DataSet SelectAllAnnocemnt11()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            DataSet ds = new DataSet();
            objconn.Open();
            return ds = SqlHelper.ExecuteDataset(objconn, "SelectAllAnnocemnt11");
        }
    }
    public string CompanyNamr { get; set; }
    public int InsertCustomerReg()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            objconn.Open();
            return SqlHelper.ExecuteNonQuery(objconn, "InsertCustomerReg", CId, password, FId, Name, Address, City, MobileNo, EmailId, PlanId, Reg_Date, Status, Style,CompanyName);
        }
    }



    public int Select_MissedDate_Status1()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            objconn.Open();
            return SqlHelper.ExecuteNonQuery(objconn, "Select_MissedDate_Status1");
        }
    }


    public int Update_Tbl_Client_Bpo_Data()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            objconn.Open();
            return SqlHelper.ExecuteNonQuery(objconn, "Update_Tbl_Client_Bpo_Data", Id, CId, Tbc_No, Name, EmailId, MobileNo, Gender, LicenseNo, GirNo, PanNo, H_Address, H_City, H_PinNo, H_State, O_Address, O_City, O_PinNo, LAL, MRNNo, AF, NRI, CP, Status);
        }
    }

    public int InsertFranchiseeReg()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            objconn.Open();
            return SqlHelper.ExecuteNonQuery(objconn, "InsertFranchiseeReg", FId, password, FranchiseeName, CompanyName, Address, Landline, MobileNo, Email, BankAC, IFCCode, Status, RegisteredDate);
        }
    }

    public int Update_Status_2_3()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            objconn.Open();
            return SqlHelper.ExecuteNonQuery(objconn, "Update_Status_2_3", Id, Status, Name);
        }
    }

    public DataSet Update_status_Reg_Cid()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            DataSet ds = new DataSet();
            objconn.Open();
            return ds = SqlHelper.ExecuteDataset(objconn, "Update_status_Reg_Cid", CId);
        }
    }


    public DataSet Select_Status_Tbl_Registration_by_CID()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            DataSet ds = new DataSet();
            objconn.Open();
            return ds = SqlHelper.ExecuteDataset(objconn, "Select_Status_Tbl_Registration_by_CID", CId);
        }
    }


    public DataSet Getdate_from_Tbl_Registration_by_CID()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            DataSet ds = new DataSet();
            objconn.Open();
            return ds = SqlHelper.ExecuteDataset(objconn, "Getdate_from_Tbl_Registration_by_CID", CId);
        }
    }

    

    public DataSet Get_Customer_ID()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            DataSet ds = new DataSet();
            objconn.Open();
            return ds = SqlHelper.ExecuteDataset(objconn, "Get_Customer_ID");
        }
    }

    public DataSet Check_Cutoff_by_CId()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            DataSet ds = new DataSet();
            objconn.Open();
            return ds = SqlHelper.ExecuteDataset(objconn, "Check_Cutoff_by_CId", CId);
        }
    }

    public DataSet Check_Cutoff2()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            DataSet ds = new DataSet();
            objconn.Open();
            return ds = SqlHelper.ExecuteDataset(objconn, "Check_Cutoff2", CId);
        }
    }
    public DataSet Check_Cutoff3()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            DataSet ds = new DataSet();
            objconn.Open();
            return ds = SqlHelper.ExecuteDataset(objconn, "Check_Cutoff3", CId);
        }
    }

    public DataSet Chnage_Status_Userlogin()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            DataSet ds = new DataSet();
            objconn.Open();
            return ds = SqlHelper.ExecuteDataset(objconn, "Chnage_Status_Userlogin", CId, Status);
        }
    }


    public int insert_workloadregdate_regtbl()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            objconn.Open();
            return SqlHelper.ExecuteNonQuery(objconn, "insert_workloadregdate_regtbl", CId, dt);
        }
    }    

    public DataSet Getdate_from_Tbl_Registration()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            DataSet ds = new DataSet();
            objconn.Open();
            return ds = SqlHelper.ExecuteDataset(objconn, "Getdate_from_Tbl_Registration", CId);
        }
    }

    public DataSet Get_Data_From_CID()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            DataSet ds = new DataSet();
            objconn.Open();
            return ds = SqlHelper.ExecuteDataset(objconn, "Get_Data_From_CID", CId,Name);
        }
    }
    

    public DataSet Get_Data_From_CID_bpo()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            DataSet ds = new DataSet();
            objconn.Open();
            return ds = SqlHelper.ExecuteDataset(objconn, "Get_Data_From_CID_bpo", CId);
        }
    }

    public int Update_RW_ClientData()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            objconn.Open();
            return SqlHelper.ExecuteNonQuery(objconn, "Update_RW_ClientData", Id, Fstring,Name);
        }
    }

    public DataSet Get_Id_From_Pagejump()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            DataSet ds = new DataSet();
            objconn.Open();
            return ds = SqlHelper.ExecuteDataset(objconn, "Get_Id_From_Pagejump", CId);
        }
    }
    public DataSet Get_Id_From_Pagejump_3()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            DataSet ds = new DataSet();
            objconn.Open();
            return ds = SqlHelper.ExecuteDataset(objconn, "Get_Id_From_Pagejump_3", CId);
        }
    }




    public DataSet Get_Franchisee_ID()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            DataSet ds = new DataSet();
            objconn.Open();
            return ds = SqlHelper.ExecuteDataset(objconn, "Get_Franchisee_ID");
        }
    }

    public DataSet franchisee_Customer_login()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "franchisee_Customer_login", username, password, type);
            return ds;
        }
    }


     public DataSet franchisee_Customer_login_BUB()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "franchisee_Customer_login_BUB", username, password, type);
            return ds;
        }
    }





    public DataSet ViewPersonaldetails()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "ViewPersonaldetails", username);
            return ds;
        }
    }

    public DataSet ViewPersonaldetails_Customer()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "ViewPersonaldetails_Customer", username);
            return ds;
        }
    }


    public DataSet ViewAllcustomer_by_fid()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "ViewAllcustomer_by_fid", username);
            return ds;
        }
    }

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


    public DataSet Select_Plan_All_BY_ID()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Select_Plan_All_BY_ID", PlanId);
            return ds;
        }
    }




}



