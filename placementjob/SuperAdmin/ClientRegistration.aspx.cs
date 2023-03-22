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
using System.IO;
using System.Net.Mail;
using System.Net;
using Microsoft.Office.Interop.Word;
using Novacode;
using System.Text.RegularExpressions;
using System.Security.Cryptography.X509Certificates;
using System.Net.Security;
using System.Diagnostics;
using iTextSharp.text.pdf;
using iTextSharp.text;
using System.Data.SqlClient;



public partial class Admin_ClientRegistration : System.Web.UI.Page
{
    DataSet ds = new DataSet();
    clsAdmin AD = new clsAdmin();
    MyCon mycon = new MyCon();
    System.Data.DataTable dt = new System.Data.DataTable();
    protected void Page_Load(object sender, EventArgs e)
    {

    }

    protected void btn_active_Click(object sender, EventArgs e)
    {

        string str = Request.QueryString["cid"].ToString();
        string count = mycon.AdExecScalar("select count(id) from tbl_client_bpo_data with(nolock) where cid='" + str + "'");
        if (count == "0")
        {
            AD.id = str;
            AD.Update_Client_Active();
            fillsearch();
            AD.id = str;
            ds = AD.Select_Planid_From_Registration();
            string page = ds.Tables[0].Rows[0][0].ToString();
            int totalpage = Convert.ToInt32(page);
            AD.form = page;
            ds = AD.Select_No_Of_Bpo_Data();
            //for (int i = 0; i < totalpage; i++)
            //{
            //    AD.cid = str;
            //    AD.id = ds.Tables[0].Rows[i][0].ToString();
            //    AD.Srno = i + 1;
            //    AD.Insert_Client_Bpo_Activate();
            //}
            System.Data.DataTable tbltypedata = new System.Data.DataTable();
            tbltypedata.Columns.Add("Id");
            tbltypedata.Columns.Add("CId");
            tbltypedata.Columns.Add("Status");
            tbltypedata.Columns.Add("Sr_No");
            for (int i = 1; i <= ds.Tables[0].Rows.Count; i++)
            {
                tbltypedata.Rows.Add(ds.Tables[0].Rows[i - 1][0].ToString(), str, "0", i);
            }
            string consString = ConfigurationManager.ConnectionStrings["MyConnection"].ConnectionString;
            using (SqlConnection con = new SqlConnection(consString))
            {
                using (SqlBulkCopy sqlBulkCopy = new SqlBulkCopy(con))
                {
                    sqlBulkCopy.BulkCopyTimeout = 1200;
                    sqlBulkCopy.DestinationTableName = "Tbl_Client_Bpo_Data";
                    sqlBulkCopy.ColumnMappings.Add("Id", "Id");
                    sqlBulkCopy.ColumnMappings.Add("CId", "CId");
                    sqlBulkCopy.ColumnMappings.Add("Status", "Status");
                    sqlBulkCopy.ColumnMappings.Add("Sr_No", "Sr_No");
                    con.Open();
                    sqlBulkCopy.WriteToServer(tbltypedata);
                    con.Close();
                }
            }
            AD.id = str;
            ds = AD.Select_Planid_From_Registration();
            AD.cid = str;
            AD.sdate = mycon.indianTime().ToString("yyyy-MM-dd hh:mm:ss tt");
            AD.edate = mycon.indianTime().AddDays(Convert.ToInt32(ds.Tables[0].Rows[0][1])).ToString("yyyy-MM-dd hh:mm:ss tt");
            AD.Update_Registration_Date();
        }
    }
    public override void VerifyRenderingInServerForm(Control control)
    {
        /* Confirms that an HtmlForm control is rendered for the specified ASP.NET
           server control at run time. */
    }
    protected void btn_send_Click(object sender, EventArgs e)
    {
        try
        {
            StreamReader fp;// = new StreamReader();

            string cid = Request.QueryString["cid"].ToString();
            AD.id = cid;
            ds = AD.Select_Registration_All();
            string email = ds.Tables[0].Rows[0]["EmailId"].ToString();


            fp = File.OpenText(Server.MapPath("./Agreement/") + "C.txt");
            string htmlstring = fp.ReadToEnd();
            fp.Close();
            htmlstring = htmlstring.Replace("X-X-X-X", ds.Tables[0].Rows[0]["Name"].ToString());
            htmlstring = htmlstring.Replace("XX.X.X.X", ds.Tables[0].Rows[0]["CId"].ToString());
            string str = ds.Tables[0].Rows[0]["Password"].ToString();
            //str = clscommon.Decode(str);
            htmlstring = htmlstring.Replace("XXX/XX/X", str);
            if (mycon.send(email, "Login Information", htmlstring))
            {
                ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Mail Send Succsessfully" + "');", true);
            }
            else
            {
                ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Mail Send Fail" + "');", true);
            }


        }
        catch (Exception ex)
        {
            Label3.Text = ex.ToString();
        }
    }

    protected void btn_update_Click(object sender, EventArgs e)
    {
        if (lblid.Text == "")
        {
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Please Select Client.." + "');", true);
            cleartext();
        }
        else
        {
            AD.cid = lblid.Text;
            AD.name = txt_name.Text;
            AD.Address = txt_add.Text;
            AD.CompanyName = txt_compname.Text;
            AD.City = txt_city.Text;
            AD.MobileNo = txt_mobno.Text;
            AD.Email = txt_email.Text;
            AD.Update_Client_Detail();
            cleartext();
            fillsearch();
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Client Data Updated Succsessfuly...." + "');", true);
        }
    }
    public void cleartext()
    {
        lblid.Text = "";
        txt_name.Text = "";
        txt_add.Text = "";
        txt_compname.Text = "";
        txt_city.Text = "";
        txt_mobno.Text = "";
        txt_email.Text = "";
        lbl_plan.Text = "";
    }
    protected void btn_edit_Click(object sender, EventArgs e)
    {
        AD.id = Request.QueryString["cid"].ToString();
        ds = AD.Select_Registration_All();
        lblid.Text = ds.Tables[0].Rows[0]["CId"].ToString();
        txt_name.Text = ds.Tables[0].Rows[0]["Name"].ToString();
        txt_add.Text = ds.Tables[0].Rows[0]["Address"].ToString();
        txt_compname.Text = ds.Tables[0].Rows[0]["CompanyName"].ToString();
        txt_city.Text = ds.Tables[0].Rows[0]["City"].ToString();
        txt_mobno.Text = ds.Tables[0].Rows[0]["MobileNo"].ToString();
        txt_email.Text = ds.Tables[0].Rows[0]["EmailId"].ToString();
        lbl_plan.Text = ds.Tables[0].Rows[0]["PlanId"].ToString();
    }
    protected void btn_delete_Click(object sender, EventArgs e)
    {
        AD.cid = Request.QueryString["cid"].ToString();
        AD.Delete_Client_cid();
        fillsearch();
        ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Client Deleted Succsessfuly...." + "');", true);
    }
    protected void btn_search_Click(object sender, EventArgs e)
    {
        fillsearch();
    }
    public void fillsearch()
    {
        dt = mycon.FillDataTable("select * from tbl_registration where cid='" + txt_cid_search.Text + "'");
        if (dt.Rows.Count == 1)
        {
            if (dt.Rows[0]["status"].ToString() == "0")
            {
                grd_client_active.DataSource = dt;
                grd_client_active.DataBind();
                grd_client_deactive.DataSource = null;
                grd_client_deactive.DataBind();
            }
            else if (dt.Rows[0]["status"].ToString() == "1")
            {
                grd_client_active.DataSource = null;
                grd_client_active.DataBind();
                grd_client_deactive.DataSource = dt;
                grd_client_deactive.DataBind();
            }
            else
            {
                grd_client_active.DataSource = null;
                grd_client_active.DataBind();
                grd_client_deactive.DataSource = null;
                grd_client_deactive.DataBind();
                ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "please search this id in workspace" + "');", true);
            }
        }
        else
        {
            grd_client_active.DataSource = null;
            grd_client_active.DataBind();
            grd_client_deactive.DataSource = null;
            grd_client_deactive.DataBind();
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "No Data Found." + "');", true);
        }
    }
    protected void grd_client_active_RowCommand(object sender, GridViewCommandEventArgs e)
    {
        if (e.CommandName == "downloadagreement")
        {
            int index = Convert.ToInt32(e.CommandArgument);
            Label cid = ((Label)grd_client_active.Rows[index].FindControl("Label6"));
            Label name = ((Label)grd_client_active.Rows[index].FindControl("Label1"));
            Label address = ((Label)grd_client_active.Rows[index].FindControl("Label3"));
            Label number = ((Label)grd_client_active.Rows[index].FindControl("Label4"));
            Label emailid = ((Label)grd_client_active.Rows[index].FindControl("Label5"));

            string wordFileName = Server.MapPath("./Agreement/") + "agreement.docx";
            string wordFileName1 = Server.MapPath("./Agreement/") + cid.Text + ".docx";

            if (File.Exists(wordFileName))
            {
                File.Copy(wordFileName, wordFileName1, true);
            }
            DocX document1 = DocX.Load(wordFileName1);
            document1.ReplaceText("X-name", name.Text, false, RegexOptions.IgnoreCase, null, null, MatchFormattingOptions.ExactMatch);
            document1.ReplaceText("X-date", mycon.indianTime().ToString("dd-MM-yyyy"), false, RegexOptions.IgnoreCase, null, null, MatchFormattingOptions.ExactMatch);
            document1.ReplaceText("X-emailid", emailid.Text, false, RegexOptions.IgnoreCase, null, null, MatchFormattingOptions.ExactMatch);
            document1.ReplaceText("X-number", number.Text, false, RegexOptions.IgnoreCase, null, null, MatchFormattingOptions.ExactMatch);
            document1.ReplaceText("X-address", address.Text, false, RegexOptions.IgnoreCase, null, null, MatchFormattingOptions.ExactMatch);
            document1.Save();

            Response.ContentType = "application/docx";
            Response.AppendHeader("Content-Disposition", "attachment; filename=" + cid.Text + ".docx");
            Response.TransmitFile(wordFileName1);
            Response.End();
        }
        if (e.CommandName == "editdetails")
        {
            int index = Convert.ToInt32(e.CommandArgument);
            Label cid = ((Label)grd_client_active.Rows[index].FindControl("Label6"));
            Label name = ((Label)grd_client_active.Rows[index].FindControl("Label1"));
            Label address = ((Label)grd_client_active.Rows[index].FindControl("Label3"));
            Label number = ((Label)grd_client_active.Rows[index].FindControl("Label4"));
            Label emailid = ((Label)grd_client_active.Rows[index].FindControl("Label5"));
            Label companyname = ((Label)grd_client_active.Rows[index].FindControl("Label7"));
            Label city = ((Label)grd_client_active.Rows[index].FindControl("Label8"));
            Label planid = ((Label)grd_client_active.Rows[index].FindControl("Label9"));

            lblid.Text = cid.Text;
            txt_name.Text = name.Text;
            txt_add.Text = address.Text;
            txt_compname.Text = companyname.Text;
            txt_city.Text = city.Text;
            txt_mobno.Text = number.Text;
            txt_email.Text = emailid.Text;
            lbl_plan.Text = planid.Text;
        }
    }

    private static void DrawLine(PdfWriter writer, float x1, float y1, float x2, float y2, Color color)
    {
        PdfContentByte contentByte = writer.DirectContent;
        contentByte.SetColorStroke(color);
        contentByte.MoveTo(x1, y1);
        contentByte.LineTo(x2, y2);
        contentByte.Stroke();
    }
    private static PdfPCell PhraseCell(Phrase phrase, int align)
    {
        PdfPCell cell = new PdfPCell(phrase);
        cell.BorderColor = Color.WHITE;
        cell.VerticalAlignment = PdfCell.ALIGN_TOP;
        cell.HorizontalAlignment = align;
        cell.PaddingBottom = 2f;
        cell.PaddingTop = 0f;
        return cell;
    }
    private static PdfPCell ImageCell(string path, float scale, int align)
    {
        iTextSharp.text.Image image = iTextSharp.text.Image.GetInstance(HttpContext.Current.Server.MapPath(path));
        image.ScalePercent(scale);
        PdfPCell cell = new PdfPCell(image);
        cell.BorderColor = Color.WHITE;
        cell.VerticalAlignment = PdfCell.ALIGN_TOP;
        cell.HorizontalAlignment = align;
        cell.PaddingBottom = 0f;
        cell.PaddingTop = 0f;
        return cell;
    }
    protected void grd_client_deactive_RowCommand(object sender, GridViewCommandEventArgs e)
    {
        if (e.CommandName == "sendacknowledge")
        {
            int index = Convert.ToInt32(e.CommandArgument);
            Label emailid = ((Label)grd_client_deactive.Rows[index].FindControl("Label1"));
            string msg = "";
            msg = msg + "<b>Dear Freelancer,</b><br/><br/>";
            msg = msg + "HI<br/>";
            msg = msg + "We have already provided you login id and password in the previous mail along with the link of the website which is    https://ingbjob.com  from where you can login and start your work.<br/><br/>";
            msg = msg + "<b>  Customer Support(10:30 AM to 6:30 PM):+91 7581960101 , +91 7580981101</b><br/><br/>";
            msg = msg + "Thanking you for signing the digital contract with us.<br/>";
            msg = msg + "According to the Information technology Act 2000 (The Act) which came into force on 17-10- 2000. The Act applies to the whole of India and even to persons who commit offence outside India. The Act validates “DIGITAL SIGNATURE” and provides for enabling a person to use it just like the traditional signature. The basic purpose of digital signature is not different from our conventional signature. The purpose therefore is to authenticate the document, to identify the person and to make the contents of the document binding on person putting digital signature. Let us see what digital signature is in technical terms. Digital signature raise the same liability as the regular one for the causes of criminal and civil matters.<br/><br/>";
            msg = msg + "So The Hon Supreme saying digital signature or digital signature scheme is a mathematical scheme for demonstrating the authenticity of a digital message or document. A valid digital signature gives a recipient reason to believe that the message was created by a known sender, and that it was not altered in transit. So this digital transition has them authentication as the regular one in the eye of law and you are abiding with the same.<br/><br/>";
            msg = msg + "Best Regards,<br/>";
            msg = msg + "Legal Team<br/><br/>";
            msg = msg + "Disclaimer: This email and any files transmitted with it are confidential and intended solely for the use of the individual or entity to which they are addressed. This message contains confidential information and is intended only for the individual named. If you are not the named addressee you should not disseminate, distribute or copy this e-mail. Please notify the sender immediately by e-mail if you have received this e-mail by mistake and delete this e-mail from your system. If you are not the intended recipient you are notified that disclosing, copying, distributing or taking any action in reliance on the contents of this information is strictly prohibited. ";
            mycon.send(emailid.Text, "Agreement Acceptance Acknowledgement", msg);
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Mail Send Succsessfully" + "');", true);
        }

    }
}
