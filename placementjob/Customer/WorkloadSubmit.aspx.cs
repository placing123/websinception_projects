using System;
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
using DLL;
using System.IO;
using iTextSharp.text;
using iTextSharp.text.pdf;
using iTextSharp.text.html;
using iTextSharp.text.html.simpleparser;


public partial class Customer_WorkloadSubmit : System.Web.UI.Page
{
    clsFranchisee CF = new clsFranchisee();
    DataSet ds = new DataSet();
    DataSet ds1 = new DataSet();
    clsAdmin AD = new clsAdmin();
    string Fstring, filter;
    DataTable pdf = new DataTable();
    DataTable dt = new DataTable();
    DataTable dt1 = new DataTable();
    MyCon mycon = new MyCon();
    DataTable exel = new DataTable();

    protected void Page_Load(object sender, EventArgs e)
    {

        if (Session["Cus_Username"] == null)
        {
            Response.Redirect("../Default.aspx");
        }
        if (!IsPostBack)
        {
            string status = mycon.AdExecScalar("select status from tbl_registration where CId='" + Session["Cus_Username"].ToString() + "'");
            if (status == "3")
            {
                div_download.Style["visibility"] = "true";
            }
        }
    }
    protected void Button1_Click(object sender, EventArgs e)
    {
        CF.CId = Session["Cus_Username"].ToString();

        CF.QueryFor = "Complete";
        DataSet ds = CF.Master_Client_BPO_Data();
        lblcompl.Text = ds.Tables[0].Rows[0][0].ToString();

        CF.QueryFor = "Total";
        DataSet ds1 = CF.Master_Client_BPO_Data();
        lbltotal.Text = ds1.Tables[0].Rows[0][0].ToString();

        if (lblcompl.Text == lbltotal.Text)
        {
            Cutoff();
         

        }
        else
        {
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('Please complete your work before submitting forms.');", true);
        }
    }

    public void Cutoff()
    {
        CF.CId = Session["Cus_Username"].ToString();
        DataSet dsGetdate = CF.Getdate_from_Tbl_Registration_by_CID();

        if (dsGetdate.Tables[0].Rows[0]["WorkloadSub_date"].ToString() != "")
        {
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('Workload Already Submitted.');", true);

        }

        else
        {
            try
            {
                AD.cid = Session["Cus_Username"].ToString();
                ds = AD.Get_Data_From_CID_1();
                for (int i = 0; i < ds.Tables[0].Rows.Count; i++)
                {
                    CF.CId = ds.Tables[0].Rows[i]["Id"].ToString();
                    ds1 = CF.Get_Data_From_CID_bpo();

                    Fstring = "";
                    string CTbc_No = ds.Tables[0].Rows[i]["Tbc_No"].ToString();
                    string CName = ds.Tables[0].Rows[i]["Name"].ToString();
                    string CEmailId = ds.Tables[0].Rows[i]["EmailId"].ToString();
                    string CMobileNo = ds.Tables[0].Rows[i]["MobileNo"].ToString();
                    string CGender = ds.Tables[0].Rows[i]["Gender"].ToString();
                    string CLicenseNo = ds.Tables[0].Rows[i]["LicenseNo"].ToString();
                    string CGirNo = ds.Tables[0].Rows[i]["GirNo"].ToString();
                    string CPanNo = ds.Tables[0].Rows[i]["PanNo"].ToString();


                    filter = ds.Tables[0].Rows[i]["H_Address"].ToString();
                    if (filter.Contains('\"'))
                    {
                        filter = filter.Substring(1, filter.Length - 1);
                    }
                    string CH_Address = filter;

                    //string CH_Address = ds.Tables[0].Rows[i]["H_Address"].ToString();




                    string CH_City = ds.Tables[0].Rows[i]["H_City"].ToString();
                    string CH_PinNo = ds.Tables[0].Rows[i]["H_PinNo"].ToString();


                    filter = ds.Tables[0].Rows[i]["H_State"].ToString();
                    if (filter.Contains('\"'))
                    {
                        filter = filter.Substring(0, filter.Length - 1);
                    }
                    string CH_State = filter;

                    //string CH_State =  ds.Tables[0].Rows[i]["H_State"].ToString();

                    filter = ds.Tables[0].Rows[i]["O_Address"].ToString();
                    if (filter.Contains('\"'))
                    {
                        filter = filter.Substring(1, filter.Length - 1);
                    }
                    string CO_Address = filter;



                    //string CO_Address = ds.Tables[0].Rows[i]["O_Address"].ToString();
                    string CO_City = ds.Tables[0].Rows[i]["O_City"].ToString();

                    filter = ds.Tables[0].Rows[i]["O_PinNo"].ToString();
                    if (filter.Contains('\"'))
                    {
                        filter = filter.Substring(0, filter.Length - 1);
                    }
                    string CO_PinNo = filter;

                    // string CO_PinNo = ds.Tables[0].Rows[i]["O_PinNo"].ToString();
                    string CLAL = ds.Tables[0].Rows[i]["LAL"].ToString();
                    string CMRNNo = ds.Tables[0].Rows[i]["MRNNo"].ToString();
                    string CAF = ds.Tables[0].Rows[i]["AF"].ToString();
                    string CNRI = ds.Tables[0].Rows[i]["NRI"].ToString();
                    string CCP = ds.Tables[0].Rows[i]["CP"].ToString();

                    string BTbc_No = ds1.Tables[0].Rows[0]["Tbc_No"].ToString();
                    string BName = ds1.Tables[0].Rows[0]["Name"].ToString();
                    string BEmailId = ds1.Tables[0].Rows[0]["EmailId"].ToString();
                    string BMobileNo = ds1.Tables[0].Rows[0]["MobileNo"].ToString();
                    string BGender = ds1.Tables[0].Rows[0]["Gender"].ToString();
                    string BLicenseNo = ds1.Tables[0].Rows[0]["LicenseNo"].ToString();
                    string BGirNo = ds1.Tables[0].Rows[0]["GirNo"].ToString();
                    string BPanNo = ds1.Tables[0].Rows[0]["PanNo"].ToString();
                    string BH_Address = ds1.Tables[0].Rows[0]["H_Address"].ToString();
                    string BH_City = ds1.Tables[0].Rows[0]["H_City"].ToString();
                    string BH_PinNo = ds1.Tables[0].Rows[0]["H_PinNo"].ToString();
                    string BH_State = ds1.Tables[0].Rows[0]["H_State"].ToString();
                    string BO_Address = ds1.Tables[0].Rows[0]["O_Address"].ToString();
                    string BO_City = ds1.Tables[0].Rows[0]["O_City"].ToString();
                    string BO_PinNo = ds1.Tables[0].Rows[0]["O_PinNo"].ToString();
                    string BLAL = ds1.Tables[0].Rows[0]["LAL"].ToString();
                    string BMRNNo = ds1.Tables[0].Rows[0]["MRNNo"].ToString();
                    string BAF = ds1.Tables[0].Rows[0]["AF"].ToString();
                    string BNRI = ds1.Tables[0].Rows[0]["NRI"].ToString();
                    string BCP = ds1.Tables[0].Rows[0]["CP"].ToString();



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


                    //CF.Id = Convert.ToInt32(DT.Rows[i]["Id"]);
                    //CF.Name = TextBox2.Text;
                    //CF.Fstring = Fstring.ToString();
                    //CF.Update_RW_ClientData();

                    if (Fstring.Contains("0"))
                    {
                        CF.Id = Convert.ToInt32(ds.Tables[0].Rows[i]["Id"]);
                        CF.Name = Session["Cus_Username"].ToString();
                        CF.Status = "3";
                        CF.Update_Status_2_3();
                    }

                    else
                    {
                        CF.Id = Convert.ToInt32(ds.Tables[0].Rows[i]["Id"]);
                        CF.Name = Session["Cus_Username"].ToString();
                        CF.Status = "2";
                        CF.Update_Status_2_3();
                    }
                }
                CF.CId = Session["Cus_Username"].ToString();

                ds = CF.Check_Cutoff_by_CId();

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
                    div_download.Style["visibility"] = "true";

                }
                ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('Work Submitted Sucessfully.');", true);
            }

            catch { }
        }
    }
    protected void btn_download_Click(object sender, EventArgs e)
    {
        try
        {
            exel.Columns.Add("Form Number", typeof(int));
            exel.Columns.Add("Field Name");
            exel.Columns.Add("Type Words");

            dt = mycon.FillDataTable("SELECT *  FROM Tbl_Client_Bpo_Data with(nolock) where CId='" + Session["Cus_Username"].ToString() + "' order by sr_no");

            for (int i = 0; i < dt.Rows.Count; i++)
            {

                exel.Rows.Add(Convert.ToInt32(dt.Rows[i]["Sr_No"].ToString()), "L.A.NO", dt.Rows[i]["Tbc_No"].ToString());

                exel.Rows.Add(Convert.ToInt32(dt.Rows[i]["Sr_No"].ToString()), "Name", dt.Rows[i]["Name"].ToString());

                exel.Rows.Add(Convert.ToInt32(dt.Rows[i]["Sr_No"].ToString()), "Email Id", dt.Rows[i]["EmailId"].ToString());

                exel.Rows.Add(Convert.ToInt32(dt.Rows[i]["Sr_No"].ToString()), "Mobile No", dt.Rows[i]["MobileNo"].ToString());

                exel.Rows.Add(Convert.ToInt32(dt.Rows[i]["Sr_No"].ToString()), "Gender", dt.Rows[i]["Gender"].ToString());

                exel.Rows.Add(Convert.ToInt32(dt.Rows[i]["Sr_No"].ToString()), "Center Code", dt.Rows[i]["LicenseNo"].ToString());

                exel.Rows.Add(Convert.ToInt32(dt.Rows[i]["Sr_No"].ToString()), "Registration No", dt.Rows[i]["GirNo"].ToString());

                exel.Rows.Add(Convert.ToInt32(dt.Rows[i]["Sr_No"].ToString()), "Secure No", dt.Rows[i]["PanNo"].ToString());

                exel.Rows.Add(Convert.ToInt32(dt.Rows[i]["Sr_No"].ToString()), "CCN No", dt.Rows[i]["MRNNo"].ToString());

                exel.Rows.Add(Convert.ToInt32(dt.Rows[i]["Sr_No"].ToString()), "SRN", dt.Rows[i]["AF"].ToString());

                exel.Rows.Add(Convert.ToInt32(dt.Rows[i]["Sr_No"].ToString()), "NCV", dt.Rows[i]["NRI"].ToString());

                exel.Rows.Add(Convert.ToInt32(dt.Rows[i]["Sr_No"].ToString()), "CN", dt.Rows[i]["CP"].ToString());

                exel.Rows.Add(Convert.ToInt32(dt.Rows[i]["Sr_No"].ToString()), "Loan Amount", dt.Rows[i]["LAL"].ToString());

                exel.Rows.Add(Convert.ToInt32(dt.Rows[i]["Sr_No"].ToString()), "Home City", dt.Rows[i]["H_City"].ToString());

                exel.Rows.Add(Convert.ToInt32(dt.Rows[i]["Sr_No"].ToString()), "Home Address", dt.Rows[i]["H_Address"].ToString());

                exel.Rows.Add(Convert.ToInt32(dt.Rows[i]["Sr_No"].ToString()), "Home Zip Code", dt.Rows[i]["H_PinNo"].ToString());

                exel.Rows.Add(Convert.ToInt32(dt.Rows[i]["Sr_No"].ToString()), "Home State", dt.Rows[i]["H_State"].ToString());

                exel.Rows.Add(Convert.ToInt32(dt.Rows[i]["Sr_No"].ToString()), "Office City", dt.Rows[i]["O_City"].ToString());

                exel.Rows.Add(Convert.ToInt32(dt.Rows[i]["Sr_No"].ToString()), "Office Address", dt.Rows[i]["O_Address"].ToString());

                exel.Rows.Add(Convert.ToInt32(dt.Rows[i]["Sr_No"].ToString()), "Office Zip Code", dt.Rows[i]["O_PinNo"].ToString());
            }


            exel.DefaultView.Sort = "Form Number" + " " + "ASC";
            exel = exel.DefaultView.ToTable();

            ExporttoPDF(exel, Session["Cus_Username"].ToString());
            //ExporttoExcel(exel, Session["Cus_Username"].ToString());
        }
        catch
        {
        }
    }
    private void ExporttoExcel(DataTable table, string filename)
    {
        Response.ClearContent();
        Response.Buffer = true;
        Response.AddHeader("content-disposition", string.Format("attachment; filename={0}", filename + ".xls"));
        Response.ContentType = "application/ms-excel";
        DataTable dt = table;
        string str = string.Empty;
        foreach (DataColumn dtcol in dt.Columns)
        {
            Response.Write(str + dtcol.ColumnName);
            str = "\t";
        }
        Response.Write("\n");
        foreach (DataRow dr in dt.Rows)
        {
            str = "";
            for (int j = 0; j < dt.Columns.Count; j++)
            {
                Response.Write(str + Convert.ToString(dr[j]));
                str = "\t";
            }
            Response.Write("\n");
        }
        Response.End();
    }

    private void ExporttoPDF(DataTable pdf, string cid)
    {
        GridView GridView1 = new GridView();
        GridView1.AllowPaging = false;
        GridView1.DataSource = pdf;
        GridView1.DataBind();

        Response.ContentType = "application/pdf";
        Response.AddHeader("content-disposition", "attachment;filename=" + cid + ".pdf");
        Response.Cache.SetCacheability(HttpCacheability.NoCache);
        StringWriter sw = new StringWriter();
        HtmlTextWriter hw = new HtmlTextWriter(sw);
        GridView1.RenderControl(hw);
        StringReader sr = new StringReader(sw.ToString());
        Document pdfDoc = new Document(PageSize.A4, 10f, 10f, 10f, 0f);
        HTMLWorker htmlparser = new HTMLWorker(pdfDoc);
        PdfWriter.GetInstance(pdfDoc, Response.OutputStream);
        pdfDoc.Open();
        htmlparser.Parse(sr);
        pdfDoc.Close();
        Response.Write(pdfDoc);
        Response.End();
    }
}