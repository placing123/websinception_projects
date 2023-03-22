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
using System.IO;
using System.Net.Mail;

public partial class Control_Email : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {

    }
    protected void btnSubmit_Click(object sender, EventArgs e)
    {
        send(txtTo.Text, txtSubject.Text, txtBody.Text);
        Page.RegisterStartupScript("UserMsg", "<script>alert('Mail sent thank you...');if(alert){ window.location='email.aspx';}</script>");
    }
    public bool send(String emailId, String subject, String message)
    {
        string Email = ConfigurationManager.AppSettings["Email"].ToString();
        string Emailto = emailId;
        MailMessage mail = new MailMessage(Email, Emailto);
        mail.Subject = subject;
        mail.Body = message;
        mail.IsBodyHtml = true;
        if (fileUpload1.HasFile)
        {
            // File Upload path
            String FileName = fileUpload1.PostedFile.FileName;
            if (File.Exists(Server.MapPath("~/Download/" + FileName)))
            {
                File.Delete(Server.MapPath("~/Download/" + FileName));
            }
            fileUpload1.PostedFile.SaveAs(Server.MapPath("~/Download/" + FileName));
            Attachment at = new Attachment(Server.MapPath("~/Download/" + FileName));
            mail.Attachments.Add(at);
        }
        if (FileUpload2.HasFile)
        {           
            String FileName = FileUpload2.PostedFile.FileName;
            if (File.Exists(Server.MapPath("~/Download/" + FileName)))
            {
                File.Delete(Server.MapPath("~/Download/" + FileName));
            }
            FileUpload2.PostedFile.SaveAs(Server.MapPath("~/Download/" + FileName));             
            Attachment at = new Attachment(Server.MapPath("~/Download/" + FileName));
            mail.Attachments.Add(at);
        }
        if (FileUpload3.HasFile)
        {
            String FileName = FileUpload3.PostedFile.FileName;
            if (File.Exists(Server.MapPath("~/Download/" + FileName)))
            {
                File.Delete(Server.MapPath("~/Download/" + FileName));
            }
            FileUpload3.PostedFile.SaveAs(Server.MapPath("~/Download/" + FileName));
            Attachment at = new Attachment(Server.MapPath("~/Download/" + FileName));
            mail.Attachments.Add(at);
        }
        if (FileUpload4.HasFile)
        {
            String FileName = FileUpload4.PostedFile.FileName;
            if (File.Exists(Server.MapPath("~/Download/" + FileName)))
            {
                File.Delete(Server.MapPath("~/Download/" + FileName));
            }
            FileUpload4.PostedFile.SaveAs(Server.MapPath("~/Download/" + FileName));
            Attachment at = new Attachment(Server.MapPath("~/Download/" + FileName));
            mail.Attachments.Add(at);
        }
        if (FileUpload5.HasFile)
        {
            String FileName = FileUpload5.PostedFile.FileName;
            if (File.Exists(Server.MapPath("~/Download/" + FileName)))
            {
                File.Delete(Server.MapPath("~/Download/" + FileName));
            }
            FileUpload5.PostedFile.SaveAs(Server.MapPath("~/Download/" + FileName));
            Attachment at = new Attachment(Server.MapPath("~/Download/" + FileName));
            mail.Attachments.Add(at);
        }
        if (txt_bcc.Text.Trim().ToString()!="")
        {
            MailAddress bcc = new MailAddress(txt_bcc.Text);
            mail.Bcc.Add(bcc);    
        }

        if (txt_cc.Text.Trim().ToString() != "")
        {
            MailAddress cc = new MailAddress(txt_cc.Text);
            mail.CC.Add(cc);
        }
        SmtpClient smtp = new SmtpClient();
        smtp.Send(mail);
        return true;
    }
}