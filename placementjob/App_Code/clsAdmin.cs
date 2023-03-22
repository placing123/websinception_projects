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
    public string status { get; set; }
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

    public string FId { get; set; }
    public string FranchiseeName { get; set; }
    public string CompanyName { get; set; }
    public string Address { get; set; }
    public string Landline { get; set; }
    //public string MobileNo { get; set; }
    public string Email { get; set; }
    public string BankAC { get; set; }
    public string IFCCode { get; set; }


   

    public static string GetConnection()
    {
        string sqlConnection = System.Configuration.ConfigurationManager.ConnectionStrings["MyConnection"].ToString();
        return sqlConnection;
    }



    public DataSet Select_Registration_Status_1()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Select_Registration_Status_1");
            return ds;
        }
    }







    public void Update_Registration_Block_Date()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            SqlHelper.ExecuteDataset(objconn, "Update_Registration_Block_Date",cid);

        }
    }


    public DateTime date { get; set; }
    public string type{ get; set; }
    public string Plan{ get; set; }
    public string Page{ get; set; }
    public string personaldetail{ get; set; }
    public string FullName{ get; set; }
    public string State{ get; set; }
    public string Contact_No{ get; set; }
    public string Ref{ get; set; }
    public string Site { get; set; }




    public void Insert_Inquiry()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            SqlHelper.ExecuteDataset(objconn, "Insert_Inquiry", date, Site, type, Plan, Page, personaldetail, FullName, EmailId, Address, City, State, Contact_No, Ref);

        }
    }



    public void Update_Inquiry_Status()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            SqlHelper.ExecuteDataset(objconn, "Update_Inquiry_Status", id);

        }
    }

    public DataSet Select_Inquiry()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Select_Inquiry");
            return ds;
        }
    }
    public DataSet Get_Data_From_CID_SRNO()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Get_Data_From_CID_SRNO", id, cid);
            return ds;
        }
    } 



    public DataSet Select_Client_CNF()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Select_Client_CNF", cid, Name, FId);
            return ds;
        }
    }


   
    public string DocketNo { get; set; }
    public string Qc { get; set; }
   
    
    
    
    public string Work { get; set; }
    public void Insert_Log()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            SqlHelper.ExecuteDataset(objconn, "Insert_Log", cid, Work);

        }
    }
    public DataSet Select_Client_BPO_Detail()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Select_Client_BPO_Detail", cid);
            return ds;
        }
    }


    public DataSet Select_Log()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Select_Log",cid);
            return ds;
        }
    }




    public void Update_Qc()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            objconn.Open();
            SqlHelper.ExecuteDataset(objconn, "Update_Qc", cid, Qc);
        }
    }
    public void Update_Registration_Docket_Recevied()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            SqlHelper.ExecuteDataset(objconn, "Update_Registration_Docket_Recevied", cid, DocketNo);

        }
    }
    public DataSet Select_Docket_List()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Select_Docket_List");
            return ds;
        }
    }

    public void Update_Registration_Aggriment_1_to_2()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            SqlHelper.ExecuteDataset(objconn, "Update_Registration_Aggriment_1_to_2", cid);

        }
    }

    public void Update_Registration_Aggriment_0_to_1()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            SqlHelper.ExecuteDataset(objconn, "Update_Registration_Aggriment_0_to_1", cid);

        }
    }
    public DataSet Select_Registration_Aggriment_0()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Select_Registration_Aggriment_0");
            return ds;
        }
    }
    public DataSet Select_Registration_Aggriment_1()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Select_Registration_Aggriment_1");
            return ds;
        }
    }

  public DataSet Select_Client_Bpo_Data_3_1()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Select_Client_Bpo_Data_3_1");
            return ds;
        }
    }


  public void Update_Franchisee_Block_Unblock()
  {
      using (SqlConnection objconn = new SqlConnection(GetConnection()))
      {

          objconn.Open();
          SqlHelper.ExecuteDataset(objconn, "Update_Franchisee_Block_Unblock", id);

      }
  }

  public DataSet Get_Data_From_CID_1()
  {
      using (SqlConnection objconn = new SqlConnection(GetConnection()))
      {

          objconn.Open();
          DataSet ds = new DataSet();
          ds = SqlHelper.ExecuteDataset(objconn, "Get_Data_From_CID_1", cid);
          return ds;
      }
  }

  public DataSet Select_Client_Bpo_3()
  {

      using (SqlConnection objconn = new SqlConnection(GetConnection()))
      {

          objconn.Open();
          DataSet ds = new DataSet();
          ds = SqlHelper.ExecuteDataset(objconn, "Select_Client_Bpo_3", cid);
          return ds;
      }
  }


  public DataSet Get_Data_From_CID_3()
  {
      using (SqlConnection objconn = new SqlConnection(GetConnection()))
      {
          DataSet ds = new DataSet();
          objconn.Open();
          return ds = SqlHelper.ExecuteDataset(objconn, "Get_Data_From_CID_3", cid, name);
      }
  }


 public DataSet Update_Registration_Date_Status()
    {

        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Update_Registration_Date_Status", cid, enddate);
            return ds;
        }
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
    public DataSet Select_Client_Bpo_Data_Cid()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Select_Client_Bpo_Data_Cid", cid);
            return ds;
        }
    }
    public DataSet Select_Franc_MaxId()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            DataSet ds = new DataSet();
            objconn.Open();
            return ds = SqlHelper.ExecuteDataset(objconn, "Select_Franc_MaxId");
        }
    }
    public DataSet Select_Customer_MaxId()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            DataSet ds = new DataSet();
            objconn.Open();
            return ds = SqlHelper.ExecuteDataset(objconn, "Select_Customer_MaxId");
        }
    }

    public DataSet Select_Client_Bpo_Data_By_Cid()
    {

        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Select_Client_Bpo_Data_By_Cid", cid);
            return ds;
        }
    }

    public void Change_Psw()
    {

        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Change_Psw", username, password);            
        }
    }

    public DataSet Get_Data_From_CID_2()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            DataSet ds = new DataSet();
            objconn.Open();
            return ds = SqlHelper.ExecuteDataset(objconn, "Get_Data_From_CID_2", cid, name);
        }
    }
    public DataSet Select_Registration_Style()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            DataSet ds = new DataSet();
            objconn.Open();
            return ds = SqlHelper.ExecuteDataset(objconn, "Select_Registration_Style", cid);
        }
    }

    public void Update_Franchisee_Detail()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            
            objconn.Open();
            SqlHelper.ExecuteDataset(objconn, "Update_Franchisee_Detail", FId,FranchiseeName,CompanyName,Address,Landline,MobileNo,Email,BankAC,IFCCode);
        }
    }
    public string City { get; set; }
    public void Update_Client_Detail()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            SqlHelper.ExecuteDataset(objconn, "Update_Client_Detail", cid, name, Address, CompanyName,City, MobileNo, Email);
        }
    }
    public void Delete_Client_cid()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            SqlHelper.ExecuteDataset(objconn, "Delete_Client_cid", cid);
        }
    }


    public void Delete_Plan()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            objconn.Open();
            SqlHelper.ExecuteDataset(objconn, "Delete_Plan", pid);
        }
    }


    public DataSet Get_Id_From_Pagejump()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            DataSet ds = new DataSet();
            objconn.Open();
            return ds = SqlHelper.ExecuteDataset(objconn, "Get_Id_From_Pagejump", cid);
        }
    }

    public void Delete_Client_BPOData_cid()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            SqlHelper.ExecuteDataset(objconn, "Delete_Client_BPOData_cid", cid);
        }
    }

    //public DataSet Select_Registration_Rdate()
    //{
    //    using (SqlConnection objconn = new SqlConnection(GetConnection()))
    //    {

    //        objconn.Open();
    //        DataSet ds = new DataSet();
    //        ds = SqlHelper.ExecuteDataset(objconn, "Select_Registration_Rdate", enddate, enddate0);
    //        return ds;
    //    }
    //}


    public DataSet Select_Registration_Rdate1()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Select_Registration_Rdate1", enddate,enddate0);
            return ds;
        }
    }


    public void Delete_Franchisee_fid()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            SqlHelper.ExecuteDataset(objconn, "Delete_Franchisee_fid", cid);
        }
    }
    //public DataSet Get_Data_From_CID_bpo_2()
    //{
    //    using (SqlConnection objconn = new SqlConnection(GetConnection()))
    //    {
    //        DataSet ds = new DataSet();
    //        objconn.Open();
    //        return ds = SqlHelper.ExecuteDataset(objconn, "Get_Data_From_CID_bpo_2", name);
    //    }
    //}
    public DataSet Sub_Admin_Login()
    {

        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Sub_Admin_Login", username, password);
            return ds;
        }
    }
    public DataSet Select_Client_Bpo_2()
    {

        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Select_Client_Bpo_2", cid);
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

    public int Srno { get; set; }

    public void Insert_Client_Bpo_Activate()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            SqlHelper.ExecuteDataset(objconn, "Insert_Client_Bpo_Activate", cid, id, Srno);
        }
    }
    public string name { get; set; }
    public void Insert_Bpo_Name()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            SqlHelper.ExecuteDataset(objconn, "Insert_Bpo_Name",name );
        }
    }

    public void Insert_Bpo_Emailid()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            SqlHelper.ExecuteDataset(objconn, "Insert_Bpo_Emailid", name);
        }
    }

    public void Insert_Bpo_mobileno()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            SqlHelper.ExecuteDataset(objconn, "Insert_Bpo_mobileno", name);
        }
    }

    public void Insert_Bpo_address()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            SqlHelper.ExecuteDataset(objconn, "Insert_Bpo_address", name);
        }
    }

    public void Insert_Bpo_State()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            SqlHelper.ExecuteDataset(objconn, "Insert_Bpo_State", name);
        }
    }
    public void Insert_Bpo_City()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            SqlHelper.ExecuteDataset(objconn, "Insert_Bpo_City", name);
        }
    }
    public void Insert_Bpo_Pincode()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            SqlHelper.ExecuteDataset(objconn, "Insert_Bpo_Pincode", name);
        }
    }
    public DataSet Select_Bpo_Name()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Select_Bpo_Name");
            return ds;
        }
    }
    public DataSet Select_Bpo_EmailId()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Select_Bpo_EmailId");
            return ds;
        }
    }
    public DataSet Select_Bpo_Mobile()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Select_Bpo_Mobile");
            return ds;
        }
    }
    public DataSet Select_Bpo_Address()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Select_Bpo_Address");
            return ds;
        }
    }
    public DataSet Select_Bpo_state()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Select_Bpo_state");
            return ds;
        }
    }
    public DataSet Select_Bpo_City()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Select_Bpo_City");
            return ds;
        }
    }
    public DataSet Select_Bpo_Pincode()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Select_Bpo_Pincode");
            return ds;
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

    public DataSet Select_ActiveALL_Client()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(objconn, "Select_ActiveALL_Client");
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
    public string enddate0 { get; set; }


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
    public DataSet SelectALL_Bpo_Data()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet DS = new DataSet();
            DS = SqlHelper.ExecuteDataset(objconn, "SelectALL_Bpo_Data");
            return DS;
        }
    }
    public DataSet Select_Client_Bpo_Data_Cid_SrNo()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet DS = new DataSet();
            DS = SqlHelper.ExecuteDataset(objconn, "Select_Client_Bpo_Data_Cid_SrNo", cid);
            return DS;
        }
    }

    public void Chnage_Form()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            SqlHelper.ExecuteDataset(objconn, "Chnage_Form", cid);            
        }
    }
    public void Chnage_Form_2()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            SqlHelper.ExecuteDataset(objconn, "Chnage_Form_2", cid);
        }
    }
    public void Update_Registration_Status()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            SqlHelper.ExecuteDataset(objconn, "Update_Registration_Status", status, cid);
        }
    }

    public DataSet Select_Registration_Status()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {
            objconn.Open();
            DataSet DS = new DataSet();
            DS = SqlHelper.ExecuteDataset(objconn, "Select_Registration_Status", status);
            return DS;
        }
    }
    public void Update_Registration_Form()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            SqlHelper.ExecuteDataset(objconn, "Update_Registration_Form", cid, form);
        }
    }
    public DataSet Get_Id_From_Pagejump_3_form()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            DataSet DS = new DataSet();
            DS = SqlHelper.ExecuteDataset(objconn, "Get_Id_From_Pagejump_3_form", cid, form);
            return DS;
        }
    }

    public string field { get; set; }
    public string srno { get; set; }
    public string error { get; set; }
    public string correct { get; set; }

    public void Insert_Change()
    {
        using (SqlConnection objconn = new SqlConnection(GetConnection()))
        {

            objconn.Open();
            SqlHelper.ExecuteDataset(objconn, "Insert_Change", cid, srno, field, error, correct);

        }
    }
}
