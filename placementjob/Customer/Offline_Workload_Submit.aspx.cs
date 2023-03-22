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
using Ionic.Zip;
using System.IO;
using System.Data.OleDb;
using System.Xml;
using System.Data.SqlClient;




public partial class Customer_Offline_Workload_Submit : System.Web.UI.Page
{
    clsAdmin AD = new clsAdmin();
    DataSet DS = new DataSet();
    DataSet DS1 = new DataSet();
    OleDbDataAdapter DA = new OleDbDataAdapter();
    MyCon mycon = new MyCon();
    clsFranchisee CF = new clsFranchisee();
    string Fstring;

    string filter;

    protected void Page_Load(object sender, EventArgs e)
    {
        
        if (Session["Cus_Username"] == null)
        {
            Response.Redirect("~/default.aspx");
        }
        if (!IsPostBack)
        {
            AD.id = Session["Cus_Username"].ToString();
            DS = AD.Select_Registration_All();
            //lbl_end_date.Text = DS.Tables[0].Rows[0]["End_Date"].ToString();
        }

    }
    protected void btn_upload_Click(object sender, EventArgs e)
    {

        string str = Session["Cus_Username"].ToString() + ".zip";
        if (File.Exists(Server.MapPath("~/Download/" + str)))
            File.Delete(Server.MapPath("~/Download/" + str));

        FileUpload1.PostedFile.SaveAs(Server.MapPath("~/Download/" + str));

        string sourceLocation = Server.MapPath("~/Download/" + str);
        string destLocation = Server.MapPath("~/Download/");
        string CondFiles = Server.MapPath("~/Download/");
        try
        {
            using (ZipFile zip1 = ZipFile.Read(sourceLocation))
            {
                zip1.Password = "iamhackernoonecatchme##123987surat@gujarat@india123";
                foreach (ZipEntry e1 in zip1)
                {
                    e1.Extract(destLocation, ExtractExistingFileAction.OverwriteSilently);
                }
                foreach (ZipEntry e2 in zip1)
                {
                    if (e2.FileName.ToString().Substring((e2.FileName.LastIndexOf('.') + 1), (e2.FileName.ToString().Length - e2.FileName.LastIndexOf('.') - 1)) == "jpg")
                    {
                        e2.Extract(CondFiles, ExtractExistingFileAction.OverwriteSilently);
                    }
                }
            }
        }
        catch
        {


        }

        str = Session["Cus_Username"].ToString();

        XmlTextReader xmlreader = new XmlTextReader(Server.MapPath("~/Download/DLL/") + str + ".dll");
        DS.ReadXml(xmlreader);
        DataTable dt = new DataTable();





        DataColumn dc = new DataColumn();
        dc.ColumnName = "Tbc_no";
        dc.DataType = typeof(string);
        dt.Columns.Add(dc);

        dc = new DataColumn();
        dc.ColumnName = "Name";
        dc.DataType = typeof(string);
        dt.Columns.Add(dc);

        dc = new DataColumn();
        dc.ColumnName = "EmailId";
        dc.DataType = typeof(string);
        dt.Columns.Add(dc);


        dc = new DataColumn();
        dc.ColumnName = "MobileNo";
        dc.DataType = typeof(string);
        dt.Columns.Add(dc);

        dc = new DataColumn();
        dc.ColumnName = "Gender";
        dc.DataType = typeof(string);
        dt.Columns.Add(dc);

        dc = new DataColumn();
        dc.ColumnName = "LicenseNo";
        dc.DataType = typeof(string);
        dt.Columns.Add(dc);

        dc = new DataColumn();
        dc.ColumnName = "GirNo";
        dc.DataType = typeof(string);
        dt.Columns.Add(dc);

        dc = new DataColumn();
        dc.ColumnName = "PanNo";
        dc.DataType = typeof(string);
        dt.Columns.Add(dc);

        dc = new DataColumn();
        dc.ColumnName = "H_Address";
        dc.DataType = typeof(string);
        dt.Columns.Add(dc);

        dc = new DataColumn();
        dc.ColumnName = "H_City";
        dc.DataType = typeof(string);
        dt.Columns.Add(dc);

        dc = new DataColumn();
        dc.ColumnName = "H_PinNo";
        dc.DataType = typeof(string);
        dt.Columns.Add(dc);

        dc = new DataColumn();
        dc.ColumnName = "H_State";
        dc.DataType = typeof(string);
        dt.Columns.Add(dc);

        dc = new DataColumn();
        dc.ColumnName = "O_Address";
        dc.DataType = typeof(string);
        dt.Columns.Add(dc);

        dc = new DataColumn();
        dc.ColumnName = "O_City";
        dc.DataType = typeof(string);
        dt.Columns.Add(dc);

        dc = new DataColumn();
        dc.ColumnName = "O_PinNo";
        dc.DataType = typeof(string);
        dt.Columns.Add(dc);

        dc = new DataColumn();
        dc.ColumnName = "LAL";
        dc.DataType = typeof(string);
        dt.Columns.Add(dc);


        dc = new DataColumn();
        dc.ColumnName = "MRNNo";
        dc.DataType = typeof(string);
        dt.Columns.Add(dc);

        dc = new DataColumn();
        dc.ColumnName = "AF";
        dc.DataType = typeof(string);
        dt.Columns.Add(dc);

        dc = new DataColumn();
        dc.ColumnName = "NRI";
        dc.DataType = typeof(string);
        dt.Columns.Add(dc);

        dc = new DataColumn();
        dc.ColumnName = "CP";
        dc.DataType = typeof(string);
        dt.Columns.Add(dc);

        dc = new DataColumn();
        dc.ColumnName = "Status";
        dc.DataType = typeof(string);
        dt.Columns.Add(dc);

        dc = new DataColumn();
        dc.ColumnName = "Report";
        dc.DataType = typeof(string);
        dt.Columns.Add(dc);


        dc = new DataColumn();
        dc.ColumnName = "Id";
        dc.DataType = typeof(int);
        dt.Columns.Add(dc);
        dt.Columns["Id"].SetOrdinal(0);
        
        DataRow dr = dt.NewRow();
        for (int i = 0; i < DS.Tables[0].Rows.Count; i++)
        {
            dr = dt.NewRow();
            dt.Rows.Add(dr);
            for (int j = 0; j < DS.Tables[0].Columns.Count; j++)
            {
                dt.Rows[i][j] = DS.Tables[0].Rows[i][j];

            }
        }

        // dt=DS.Tables[0];


        SqlBulkCopy bulkcopy = new SqlBulkCopy(System.Configuration.ConfigurationManager.ConnectionStrings["MyConnection"].ToString());
        dt.TableName = "tbl_client_bpo_data";
        //for (int i = 0; i < dt.Rows.Count; i++)
        //{
        //    dt.Rows[i]["id"] = clscommon.Encode(Convert.ToInt32(dt.Rows[i]["id"].ToString()).ToString());
        //}
        for (int i = 0; i < dt.Rows.Count; i++)
        {
            for (int j = 0; j < dt.Columns.Count; j++)
            {
                if (dt.Columns[j].ColumnName == "Id")
                {
                }
                else
                {
                    dt.Rows[i][j] = clscommon.Decode(dt.Rows[i][j].ToString());
                }
            }
        }

        //        for (int i = 0; i < dt.Columns.Count; i++)
        //        {
        //            if (dt.Columns[i].ColumnName.ToString()=="Id")
        //            {
        ////                dt.Columns[i].DataType = typeof(Int32);
        //                //dt.Columns[i].ColumnName = "Id1";


        ////                dt.Columns.Add("sr_no", typeof(int), "Convert(Id, 'System.Int32')");
        //             //   dt.Columns.Remove("Id1");


        //                //dt.Columns[i].DataType = typeof(Int32);
        //              //  Convert.ToInt32(dt.Columns[i]);


        //            }
        //            else
        //            {
        //                dt.Columns[i].DataType = typeof(string);

        //            }

        //        }

        dc = new DataColumn();
        dc.ColumnName = "cid";
        dc.DefaultValue = Session["Cus_Username"].ToString();
        dc.DataType = typeof(string);
        dt.Columns.Add(dc);

        dc = new DataColumn();
        dc.ColumnName = "sr_no";
        dc.DefaultValue = 0;
        dc.DataType = typeof(int);
        dt.Columns.Add(dc);
        for (int i = 0; i < dt.Rows.Count; i++)
        {
            dt.Rows[i]["sr_no"] = i + 1;
        }

        dt.Columns["Id"].SetOrdinal(0);
        dt.Columns["CId"].SetOrdinal(1);

        for (int i = 0; i < dt.Rows.Count; i++)
        {
            CF.CId = dt.Rows[i]["Id"].ToString();
            DS1 = CF.Get_Data_From_CID_bpo();
            Fstring = "";

            string CTbc_No = dt.Rows[i]["Tbc_No"].ToString();
            string CName = dt.Rows[i]["Name"].ToString();
            string CEmailId = dt.Rows[i]["EmailId"].ToString();
            string CMobileNo = dt.Rows[i]["MobileNo"].ToString();
            string CGender = dt.Rows[i]["Gender"].ToString();
            string CLicenseNo = dt.Rows[i]["LicenseNo"].ToString();
            string CGirNo = dt.Rows[i]["GirNo"].ToString();
            string CPanNo = dt.Rows[i]["PanNo"].ToString();


            filter = dt.Rows[i]["H_Address"].ToString();
            if (filter.Contains('\"'))
            {
                filter = filter.Substring(1, filter.Length - 1);
            }
            string CH_Address = filter;

            //string CH_Address = dt.Rows[i]["H_Address"].ToString();




            string CH_City = dt.Rows[i]["H_City"].ToString();
            string CH_PinNo = dt.Rows[i]["H_PinNo"].ToString();


            filter = dt.Rows[i]["H_State"].ToString();
            if (filter.Contains('\"'))
            {
                filter = filter.Substring(0, filter.Length - 1);
            }
            string CH_State = filter;

            //string CH_State =  dt.Rows[i]["H_State"].ToString();

            filter = dt.Rows[i]["O_Address"].ToString();
            if (filter.Contains('\"'))
            {
                filter = filter.Substring(1, filter.Length - 1);
            }
            string CO_Address = filter;



            //string CO_Address = dt.Rows[i]["O_Address"].ToString();
            string CO_City = dt.Rows[i]["O_City"].ToString();

            filter = dt.Rows[i]["O_PinNo"].ToString();
            if (filter.Contains('\"'))
            {
                filter = filter.Substring(0, filter.Length - 1);
            }
            string CO_PinNo = filter;







            // string CO_PinNo = dt.Rows[i]["O_PinNo"].ToString();
            string CLAL = dt.Rows[i]["LAL"].ToString();
            string CMRNNo = dt.Rows[i]["MRNNo"].ToString();
            string CAF = dt.Rows[i]["AF"].ToString();
            string CNRI = dt.Rows[i]["NRI"].ToString();
            string CCP = dt.Rows[i]["CP"].ToString();





            string BTbc_No = DS1.Tables[0].Rows[0]["Tbc_No"].ToString();
            string BName = DS1.Tables[0].Rows[0]["Name"].ToString();
            string BEmailId = DS1.Tables[0].Rows[0]["EmailId"].ToString();
            string BMobileNo = DS1.Tables[0].Rows[0]["MobileNo"].ToString();
            string BGender = DS1.Tables[0].Rows[0]["Gender"].ToString();
            string BLicenseNo = DS1.Tables[0].Rows[0]["LicenseNo"].ToString();
            string BGirNo = DS1.Tables[0].Rows[0]["GirNo"].ToString();
            string BPanNo = DS1.Tables[0].Rows[0]["PanNo"].ToString();
            string BH_Address = DS1.Tables[0].Rows[0]["H_Address"].ToString();
            string BH_City = DS1.Tables[0].Rows[0]["H_City"].ToString();
            string BH_PinNo = DS1.Tables[0].Rows[0]["H_PinNo"].ToString();
            string BH_State = DS1.Tables[0].Rows[0]["H_State"].ToString();
            string BO_Address = DS1.Tables[0].Rows[0]["O_Address"].ToString();
            string BO_City = DS1.Tables[0].Rows[0]["O_City"].ToString();
            string BO_PinNo = DS1.Tables[0].Rows[0]["O_PinNo"].ToString();
            string BLAL = DS1.Tables[0].Rows[0]["LAL"].ToString();
            string BMRNNo = DS1.Tables[0].Rows[0]["MRNNo"].ToString();
            string BAF = DS1.Tables[0].Rows[0]["AF"].ToString();
            string BNRI = DS1.Tables[0].Rows[0]["NRI"].ToString();
            string BCP = DS1.Tables[0].Rows[0]["CP"].ToString();



            if (CTbc_No.ToString() == BTbc_No.ToString())
            {
                Fstring = "1" + ",";
            }
            else
            {
                Fstring = "0" + ",";
            }

            if (CName.ToString() == BName.ToString())
            {
                Fstring += "1" + ",";
            }
            else
            {
                Fstring += "0" + ",";
            }

            if (CEmailId.ToString() == BEmailId.ToString())
            {
                Fstring += "1" + ",";
            }
            else
            {
                Fstring += "0" + ",";
            }

            if (CMobileNo.ToString() == BMobileNo.ToString())
            {
                Fstring += "1" + ",";
            }
            else
            {
                Fstring += "0" + ",";
            }

            if (CGender.ToString() == BGender.ToString())
            {
                Fstring += "1" + ",";
            }
            else
            {
                Fstring += "0" + ",";
            }

            if (CLicenseNo.ToString() == BLicenseNo.ToString())
            {
                Fstring += "1" + ",";
            }
            else
            {
                Fstring += "0" + ",";
            }
            if (CGirNo.ToString() == BGirNo.ToString())
            {
                Fstring += "1" + ",";
            }
            else
            {
                Fstring += "0" + ",";
            }

            if (CPanNo.ToString() == BPanNo.ToString())
            {
                Fstring += "1" + ",";
            }
            else
            {
                Fstring += "0" + ",";
            }

            if (CH_Address.ToString() == BH_Address.ToString())
            {
                Fstring += "1" + ",";
            }
            else
            {
                Fstring += "0" + ",";
            }

            if (CH_City.ToString() == BH_City.ToString())
            {
                Fstring += "1" + ",";
            }
            else
            {
                Fstring += "0" + ",";
            }

            if (CH_PinNo.ToString() == BH_PinNo.ToString())
            {
                Fstring += "1" + ",";
            }
            else
            {
                Fstring += "0" + ",";
            }

            if (CH_State.ToString() == BH_State.ToString())
            {
                Fstring += "1" + ",";
            }
            else
            {
                Fstring += "0" + ",";
            }

            if (CO_Address.ToString() == BO_Address.ToString())
            {
                Fstring += "1" + ",";
            }
            else
            {
                Fstring += "0" + ",";
            }

            if (CO_City.ToString() == BO_City.ToString())
            {
                Fstring += "1" + ",";
            }
            else
            {
                Fstring += "0" + ",";
            }

            if (CO_PinNo.ToString() == BO_PinNo.ToString())
            {
                Fstring += "1" + ",";
            }
            else
            {
                Fstring += "0" + ",";
            }

            if (CLAL.ToString() == BLAL.ToString())
            {
                Fstring += "1" + ",";
            }
            else
            {
                Fstring += "0" + ",";
            }

            if (CMRNNo.ToString() == BMRNNo.ToString())
            {
                Fstring += "1" + ",";
            }
            else
            {
                Fstring += "0" + ",";
            }

            if (CAF.ToString() == BAF.ToString())
            {
                Fstring += "1" + ",";
            }
            else
            {
                Fstring += "0" + ",";
            }

            if (CNRI.ToString() == BNRI.ToString())
            {
                Fstring += "1" + ",";
            }
            else
            {
                Fstring += "0" + ",";
            }

            if (CCP.ToString() == BCP.ToString())
            {
                Fstring += "1";
            }
            else
            {
                Fstring += "0";
            }


            CF.Id = Convert.ToInt32(dt.Rows[i]["Id"]);
            CF.Name = Session["Cus_Username"].ToString();
            CF.Fstring = Fstring.ToString();
            //CF.Update_RW_ClientData();

            if (Fstring.Contains("0"))
            {
                //CF.Id = Convert.ToInt32(dt.Rows[i]["Id"]);
                //CF.Name = Session["Cus_Username"].ToString();
                //CF.Status = "3";
                //CF.Update_Status_2_3();
                dt.Rows[i]["Status"] = "3";


            }

            else
            {
                //CF.Id = Convert.ToInt32(dt.Rows[i]["Id"]);
                //CF.Name = Session["Cus_Username"].ToString();
                //CF.Status = "2";
                //CF.Update_Status_2_3();
                dt.Rows[i]["Status"] = "2";

            }


        }
        


        mycon.ExecutQury("delete tbl_client_bpo_data where cid='" + dt.Rows[0]["Cid"] + "'");
        bulkcopy.DestinationTableName = dt.TableName;
        bulkcopy.WriteToServer(dt);
        
        for (int i = 0; i < DS.Tables[1].Rows.Count; i++)
        {          
            AD.cid = Session["Cus_Username"].ToString();
            AD.Work = DS.Tables[1].Rows[i][1].ToString();
            AD.Insert_Log();
        }
              
        Cutoff();
        ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Upload Complete.." + "');", true);
    }



    public void Cutoff()
    {
        CF.CId = Session["Cus_Username"].ToString();
        DataSet dsGetdate = CF.Getdate_from_Tbl_Registration_by_CID();
        try
        {
            CF.CId = Session["Cus_Username"].ToString();

            DataSet ds = CF.Check_Cutoff_by_CId();
            DataSet ds2 = CF.Check_Cutoff2();
            DataSet ds3 = CF.Check_Cutoff3();

            if (Convert.ToInt32(ds.Tables[0].Rows[0]["QcCutOff"].ToString()) <= Convert.ToInt32(ds2.Tables[0].Rows[0][0].ToString()))
            {
                CF.CId = Session["Cus_Username"].ToString();
                CF.Status = "2";
                CF.Chnage_Status_Userlogin();

                DataSet dss = CF.Getdate_from_Tbl_Registration();

                if (dss.Tables[0].Rows[0][0].ToString() == "")
                {
                    CF.CId = Session["Cus_Username"].ToString();
                    CF.dt = System.DateTime.Now.ToString();
                    CF.insert_workloadregdate_regtbl();
                }
            }

            else
            {
                CF.CId = Session["Cus_Username"].ToString();
                CF.Status = "3";
                CF.Chnage_Status_Userlogin();

                CF.CId = Session["Cus_Username"].ToString();
                CF.dt = System.DateTime.Now.ToString();
                CF.insert_workloadregdate_regtbl();
            }
        }
        catch { }
    }
}
