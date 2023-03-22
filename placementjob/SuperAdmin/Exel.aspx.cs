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

public partial class Admin_Log : System.Web.UI.Page
{
    clsAdmin AD = new clsAdmin();
    DataSet ds = new DataSet();
    MyCon mycon = new MyCon();
    DataTable dt = new DataTable();
    DataTable dt1 = new DataTable();
    DataTable exel = new DataTable();
    protected void Page_Load(object sender, EventArgs e)
    {
    }

    protected void btn_show_Click(object sender, EventArgs e)
    {
        try
        {
            dt = mycon.FillDataTable("SELECT status FROM Tbl_Registration where cid='" + txt_cid.Text.Trim() + "'");
            if (dt.Rows[0][0].ToString()=="4")
            {
               
                exel.Columns.Add("Form No");
                exel.Columns.Add("Field Name");
                exel.Columns.Add("Error Word");
                exel.Columns.Add("Correct word");

                dt = mycon.FillDataTable("SELECT *  FROM Tbl_Client_Bpo_Data where CId='" + txt_cid.Text.Trim() + "' and status='3' order by id");
                dt1 = mycon.FillDataTable("select * from Tbl_Bpo_Data where Id in (SELECT Id FROM Tbl_Client_Bpo_Data where CId='" + txt_cid.Text.Trim() + "' and status='3') order by id");
                 

                for (int i = 0; i < dt.Rows.Count; i++)
                {
                    if (dt.Rows[i]["Tbc_No"].ToString() != dt1.Rows[i]["Tbc_No"].ToString())
                    {
                        exel.Rows.Add(dt.Rows[i]["Sr_No"].ToString(), "L.A.NO", dt.Rows[i]["Tbc_No"].ToString(), dt1.Rows[i]["Tbc_No"].ToString());
                    }
                    if (dt.Rows[i]["Name"].ToString() != dt1.Rows[i]["Name"].ToString())
                    {
                        exel.Rows.Add(dt.Rows[i]["Sr_No"].ToString(), "Name", dt.Rows[i]["Name"].ToString(), dt1.Rows[i]["Name"].ToString());
                    }
                    if (dt.Rows[i]["EmailId"].ToString() != dt1.Rows[i]["EmailId"].ToString())
                    {
                        exel.Rows.Add(dt.Rows[i]["Sr_No"].ToString(), "Email Id", dt.Rows[i]["EmailId"].ToString(), dt1.Rows[i]["EmailId"].ToString());
                    }
                    if (dt.Rows[i]["Gender"].ToString() != dt1.Rows[i]["Gender"].ToString())
                    {
                        exel.Rows.Add(dt.Rows[i]["Sr_No"].ToString(), "Gender", dt.Rows[i]["Gender"].ToString(), dt1.Rows[i]["Gender"].ToString());
                    }
                    if (dt.Rows[i]["LicenseNo"].ToString() != dt1.Rows[i]["LicenseNo"].ToString())
                    {
                        exel.Rows.Add(dt.Rows[i]["Sr_No"].ToString(), "Center Code", dt.Rows[i]["LicenseNo"].ToString(), dt1.Rows[i]["LicenseNo"].ToString());
                    }
                    if (dt.Rows[i]["GirNo"].ToString() != dt1.Rows[i]["GirNo"].ToString())
                    {
                        exel.Rows.Add(dt.Rows[i]["Sr_No"].ToString(), "Registration No", dt.Rows[i]["GirNo"].ToString(), dt1.Rows[i]["GirNo"].ToString());
                    }
                    if (dt.Rows[i]["PanNo"].ToString() != dt1.Rows[i]["PanNo"].ToString())
                    {
                        exel.Rows.Add(dt.Rows[i]["Sr_No"].ToString(), "Secure No", dt.Rows[i]["PanNo"].ToString(), dt1.Rows[i]["PanNo"].ToString());
                    }
                    if (dt.Rows[i]["H_Address"].ToString() != dt1.Rows[i]["H_Address"].ToString())
                    {
                        exel.Rows.Add(dt.Rows[i]["Sr_No"].ToString(), "Home Address", dt.Rows[i]["H_Address"].ToString(), dt1.Rows[i]["H_Address"].ToString());
                    }
                    if (dt.Rows[i]["H_City"].ToString() != dt1.Rows[i]["H_City"].ToString())
                    {
                        exel.Rows.Add(dt.Rows[i]["Sr_No"].ToString(), "Home City", dt.Rows[i]["H_City"].ToString(), dt1.Rows[i]["H_City"].ToString());
                    }
                    if (dt.Rows[i]["H_PinNo"].ToString() != dt1.Rows[i]["H_PinNo"].ToString())
                    {
                        exel.Rows.Add(dt.Rows[i]["Sr_No"].ToString(), "Home PinNo", dt.Rows[i]["H_PinNo"].ToString(), dt1.Rows[i]["H_PinNo"].ToString());
                    }
                    if (dt.Rows[i]["H_State"].ToString() != dt1.Rows[i]["H_State"].ToString())
                    {
                        exel.Rows.Add(dt.Rows[i]["Sr_No"].ToString(), "Home State", dt.Rows[i]["H_State"].ToString(), dt1.Rows[i]["H_State"].ToString());
                    }
                    if (dt.Rows[i]["O_Address"].ToString() != dt1.Rows[i]["O_Address"].ToString())
                    {
                        exel.Rows.Add(dt.Rows[i]["Sr_No"].ToString(), "Office Address", dt.Rows[i]["O_Address"].ToString(), dt1.Rows[i]["O_Address"].ToString());
                    }
                    if (dt.Rows[i]["O_City"].ToString() != dt1.Rows[i]["O_City"].ToString())
                    {
                        exel.Rows.Add(dt.Rows[i]["Sr_No"].ToString(), "Office City", dt.Rows[i]["O_City"].ToString(), dt1.Rows[i]["O_City"].ToString());
                    }
                    if (dt.Rows[i]["O_PinNo"].ToString() != dt1.Rows[i]["O_PinNo"].ToString())
                    {
                        exel.Rows.Add(dt.Rows[i]["Sr_No"].ToString(), "Office PinNO", dt.Rows[i]["O_PinNo"].ToString(), dt1.Rows[i]["O_PinNo"].ToString());
                    }
                    if (dt.Rows[i]["LAL"].ToString() != dt1.Rows[i]["LAL"].ToString())
                    {
                        exel.Rows.Add(dt.Rows[i]["Sr_No"].ToString(), "Loan Amount", dt.Rows[i]["LAL"].ToString(), dt1.Rows[i]["LAL"].ToString());
                    }
                    if (dt.Rows[i]["MRNNo"].ToString() != dt1.Rows[i]["MRNNo"].ToString())
                    {
                        exel.Rows.Add(dt.Rows[i]["Sr_No"].ToString(), "CCN No", dt.Rows[i]["MRNNo"].ToString(), dt1.Rows[i]["MRNNo"].ToString());
                    }
                    if (dt.Rows[i]["AF"].ToString() != dt1.Rows[i]["AF"].ToString())
                    {
                        exel.Rows.Add(dt.Rows[i]["Sr_No"].ToString(), "SRN", dt.Rows[i]["AF"].ToString(), dt1.Rows[i]["AF"].ToString());
                    }
                    if (dt.Rows[i]["NRI"].ToString() != dt1.Rows[i]["NRI"].ToString())
                    {
                        exel.Rows.Add(dt.Rows[i]["Sr_No"].ToString(), "NCV", dt.Rows[i]["NRI"].ToString(), dt1.Rows[i]["NRI"].ToString());
                    }
                    if (dt.Rows[i]["CP"].ToString() != dt1.Rows[i]["CP"].ToString())
                    {
                        exel.Rows.Add(dt.Rows[i]["Sr_No"].ToString(), "CN", dt.Rows[i]["CP"].ToString(), dt1.Rows[i]["CP"].ToString());
                    }

                }
                mycon.ExportDataSetToExcel(exel, txt_cid.Text.Trim()+".xls");
            }
            else
            {
                ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Customer Work is not complete." + "');", true);
            }
        }
        catch 
        {            
        }
    }
}
