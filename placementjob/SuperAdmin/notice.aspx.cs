using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;
using System.Text.RegularExpressions;
using System.IO;
using Novacode;
using iTextSharp.text.pdf;
using iTextSharp.text;
using System.Configuration;
using System.Net.Mail;

public partial class Admin_AutoFill : System.Web.UI.Page
{
    MyCon mycon = new MyCon();
    DataTable dt = new DataTable();

    protected void Page_Load(object sender, EventArgs e)
    {

    }

    protected void btn_generate_Click(object sender, EventArgs e)
    {
        dt = mycon.FillDataTable("select cid,name,address,reg_date,mobileno,(select days from tbl_plan with(nolock) where pid=tbl_registration.planid) as days  from tbl_registration with(nolock) where cid='" + txt_cid.Text + "'");

        if (dt.Rows.Count == 1)
        {
            string wordFileName = Server.MapPath("./notice/") + "notice.docx";
            string wordFileName1 = Server.MapPath("./notice/") + txt_cid.Text.Trim() + ".docx";

            if (File.Exists(wordFileName))
            {
                File.Copy(wordFileName, wordFileName1, true);
            }
            DocX document1 = DocX.Load(wordFileName1);
            document1.ReplaceText("X-date", mycon.indianTime().ToString("dd-MM-yyyy"), false, RegexOptions.IgnoreCase, null, null, MatchFormattingOptions.ExactMatch);
            document1.ReplaceText("X-name", dt.Rows[0]["name"].ToString(), false, RegexOptions.IgnoreCase, null, null, MatchFormattingOptions.ExactMatch);
            document1.ReplaceText("X-address", dt.Rows[0]["address"].ToString(), false, RegexOptions.IgnoreCase, null, null, MatchFormattingOptions.ExactMatch);
            document1.ReplaceText("X-regdate", Convert.ToDateTime(dt.Rows[0]["reg_date"].ToString()).ToString("dd-MM-yyyy"), false, RegexOptions.IgnoreCase, null, null, MatchFormattingOptions.ExactMatch);
            document1.ReplaceText("X-regnumber", dt.Rows[0]["cid"].ToString(), false, RegexOptions.IgnoreCase, null, null, MatchFormattingOptions.ExactMatch);
            document1.ReplaceText("X-number", dt.Rows[0]["mobileno"].ToString(), false, RegexOptions.IgnoreCase, null, null, MatchFormattingOptions.ExactMatch);
            document1.ReplaceText("X-days", dt.Rows[0]["days"].ToString(), false, RegexOptions.IgnoreCase, null, null, MatchFormattingOptions.ExactMatch);
            document1.ReplaceText("X-amount", DropDownList1.SelectedItem.Text, false, RegexOptions.IgnoreCase, null, null, MatchFormattingOptions.ExactMatch);
            document1.ReplaceText("X-wordamount", DropDownList1.SelectedValue, false, RegexOptions.IgnoreCase, null, null, MatchFormattingOptions.ExactMatch);
            document1.Save();

            Response.ContentType = "application/docx";
            Response.AppendHeader("Content-Disposition", "attachment; filename=" + txt_cid.Text.Trim() + ".docx");
            Response.TransmitFile(wordFileName1);
            Response.End();
        }

    }
    protected void btn_sendnoc_Click(object sender, EventArgs e)
    {
        dt = mycon.FillDataTable("select name,emailid from tbl_registration where cid='" + txt_cidnoc.Text + "'");
        string name = dt.Rows[0]["name"].ToString();
        string temppath = Server.MapPath("./noc/") + txt_cidnoc.Text + ".pdf";
        string emailid = dt.Rows[0]["emailid"].ToString();

        generatenoc(txt_cidnoc.Text, txt_amountnoc.Text, name, temppath);

        string Email = ConfigurationManager.AppSettings["Email"].ToString();
        string Emailto = emailid;
        System.Net.Mail.MailMessage mail = new System.Net.Mail.MailMessage(Email, Emailto);
        mail.Subject = "NOC";
        mail.Body = "Hi,<br/><br/>PFA No objection certificate.<br/><br/>Regards,";
        mail.IsBodyHtml = true;
        mail.Attachments.Add(new Attachment(temppath));
        SmtpClient smtp = new SmtpClient();
        smtp.Send(mail);
        ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Mail Send Succsessfully" + "');", true);
    }

    public void generatenoc(string cid, string amount, string name, string temppath)
    {
        iTextSharp.text.Document document = new iTextSharp.text.Document(PageSize.A4, 30f, 30f, 20f, 20f);

        PdfWriter.GetInstance(document, new FileStream(temppath, FileMode.Create));

        using (System.IO.MemoryStream memoryStream = new System.IO.MemoryStream())
        {
            Phrase phrase;
            PdfPCell cell;
            PdfTable table;
            iTextSharp.text.Font font1 = FontFactory.GetFont("Arial", 12, iTextSharp.text.Font.NORMAL, Color.BLACK);
            iTextSharp.text.Font font2 = FontFactory.GetFont("Arial", 12, iTextSharp.text.Font.BOLD, Color.BLACK);
            iTextSharp.text.Font font3 = FontFactory.GetFont("Arial", 20, iTextSharp.text.Font.BOLD, Color.BLACK);

            document.Open();

            //============================= Document is open and addition start =====================

            //string imageURL = HttpContext.Current.Server.MapPath("~\\images") + "\\header.jpg";
            //iTextSharp.text.Image jpg = iTextSharp.text.Image.GetInstance(imageURL);
            //jpg = iTextSharp.text.Image.GetInstance(imageURL);
            //jpg.ScaleToFit(700f, 180f);
            //jpg.SpacingBefore = 10f;
            //jpg.SpacingAfter = 1f;
            //jpg.Alignment = Element.ALIGN_CENTER;
            //document.Add(jpg);
            phrase = new Phrase("\n\n                                   ONLINEWORKSJOB\n", font3);
            document.Add(phrase);
            phrase = new Phrase("                912/A, EMPORIO COMPLEX, DINDOLI,SURAT,GUJARAT 394210\n\n                                                       NO OBJECTION CERTIFICATE\n\n\n", font2);
            document.Add(phrase);

            phrase = new Phrase(new Chunk("Dear ", font1));
            phrase.Add(new Chunk(name + ",\n\n\n", font1));
            phrase.Add(new Chunk("This is no objection from ONLINEWORKSJOB regarding the project provided by company, as per the agreement clauses you were liable to pay the penalty amount.\n\n\n", font1));
            phrase.Add(new Chunk("The company has received penalty amount of Rs. ", font1));
            phrase.Add(new Chunk(amount, font1));
            phrase.Add(new Chunk("/- and hence your case has been closed and no further action from ONLINEWORKSJOB would be taken against you.\n\n\n", font1));
            phrase.Add(new Chunk("Thanks and Regards,\n", font1));
            document.Add(phrase);

            string imageURL = HttpContext.Current.Server.MapPath("~\\images") + "\\sign.png";
            iTextSharp.text.Image jpg = iTextSharp.text.Image.GetInstance(imageURL);
            jpg = iTextSharp.text.Image.GetInstance(imageURL);
            jpg = iTextSharp.text.Image.GetInstance(imageURL);
            jpg.ScaleToFit(100f, 80f);
            jpg.SpacingBefore = 10f;
            jpg.SpacingAfter = 1f;
            jpg.Alignment = Element.ALIGN_LEFT;
            document.Add(jpg);

            phrase = new Phrase();
            phrase.Add(new Chunk("ONLINEWORKSJOB", font1));
            document.Add(phrase);

            //=========================== Document is close and addition end =======================

            document.Close();
            byte[] bytes = memoryStream.ToArray();
            memoryStream.Close();

            //System.Threading.Thread.Sleep(5000);
        }
    }
    protected void btn_sendnotice_Click(object sender, EventArgs e)
    {
        dt = mycon.FillDataTable("select cid,emailid,name,address,reg_date,mobileno,(select days from tbl_plan with(nolock) where pid=tbl_registration.planid) as days  from tbl_registration with(nolock) where cid='" + txt_cid.Text + "'");
        string temppath = Server.MapPath("./notice/") + txt_cid.Text + ".pdf";
        generatenotice(temppath, dt.Rows[0]["name"].ToString(), dt.Rows[0]["address"].ToString(), dt.Rows[0]["mobileno"].ToString(), Convert.ToDateTime(dt.Rows[0]["reg_date"].ToString()).ToString("dd-MM-yyyy"), dt.Rows[0]["cid"].ToString(), dt.Rows[0]["days"].ToString(), DropDownList1.SelectedItem.ToString(), DropDownList1.SelectedValue);

        StreamReader fp = File.OpenText(Server.MapPath("./notice/") + "noticemail.html");
        string htmlstring = fp.ReadToEnd();
        fp.Close();

        htmlstring = htmlstring.Replace("X-date", mycon.indianTime().ToString("dd-MM-yyyy"));
        htmlstring = htmlstring.Replace("X-name", dt.Rows[0]["name"].ToString());
        if (DropDownList1.SelectedItem.Text == "4000")
        {
            htmlstring = htmlstring.Replace("X-amount", "7000");
            htmlstring = htmlstring.Replace("X-wordamount", "Seven Thousand Only");
        }
        else if (DropDownList1.SelectedItem.Text == "6000")
        {
            htmlstring = htmlstring.Replace("X-amount", "9000");
            htmlstring = htmlstring.Replace("X-wordamount", "Nine Thousand Only");
        }

        MailMessage mail = new MailMessage();
        mail.To.Add(dt.Rows[0]["emailid"].ToString());
        mail.From = new MailAddress("law.rajchambers@gmail.com");
        mail.Subject = "LEGAL NOTICE (ATTACHMENT)";
        mail.Body = htmlstring;
        mail.IsBodyHtml = true;
        mail.Attachments.Add(new Attachment(temppath));
        if (FileUpload1.HasFile)
        {
            string strFileName = System.IO.Path.GetFileName(FileUpload1.PostedFile.FileName);
            Attachment attachFile = new Attachment(FileUpload1.PostedFile.InputStream, strFileName);
            mail.Attachments.Add(attachFile);
        }
        if (FileUpload2.HasFile)
        {
            string strFileName = System.IO.Path.GetFileName(FileUpload2.PostedFile.FileName);
            Attachment attachFile = new Attachment(FileUpload2.PostedFile.InputStream, strFileName);
            mail.Attachments.Add(attachFile);
        }
        if (FileUpload3.HasFile)
        {
            string strFileName = System.IO.Path.GetFileName(FileUpload3.PostedFile.FileName);
            Attachment attachFile = new Attachment(FileUpload3.PostedFile.InputStream, strFileName);
            mail.Attachments.Add(attachFile);
        }
        if (FileUpload4.HasFile)
        {
            string strFileName = System.IO.Path.GetFileName(FileUpload4.PostedFile.FileName);
            Attachment attachFile = new Attachment(FileUpload4.PostedFile.InputStream, strFileName);
            mail.Attachments.Add(attachFile);
        }
        if (FileUpload5.HasFile)
        {
            string strFileName = System.IO.Path.GetFileName(FileUpload5.PostedFile.FileName);
            Attachment attachFile = new Attachment(FileUpload5.PostedFile.InputStream, strFileName);
            mail.Attachments.Add(attachFile);
        }
        SmtpClient smtp = new SmtpClient();
        smtp.Host = "smtp.gmail.com"; //Or Your SMTP Server Address
        smtp.Credentials = new System.Net.NetworkCredential
             ("law.rajchambers@gmail.com", "abhaythesarda"); // ***use valid credentials***
        smtp.Port = 587;

        //Or your Smtp Email ID and Password
        smtp.EnableSsl = true;
        smtp.Send(mail);
        ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Mail Send Succsessfully" + "');", true);

    }
    public void generatenotice(string temppath, string name, string address, string number, string regdate, string regnumber, string days, string amount, string word)
    {
        iTextSharp.text.Document document = new iTextSharp.text.Document(PageSize.A4, 30f, 30f, 20f, 20f);

        PdfWriter.GetInstance(document, new FileStream(temppath, FileMode.Create));

        using (System.IO.MemoryStream memoryStream = new System.IO.MemoryStream())
        {
            Phrase phrase;
            PdfPCell cell;
            PdfTable table;
            iTextSharp.text.Font font1 = FontFactory.GetFont("Arial", 12, iTextSharp.text.Font.NORMAL, Color.BLACK);
            iTextSharp.text.Font font2 = FontFactory.GetFont("Arial", 12, iTextSharp.text.Font.BOLD, Color.BLACK);
            iTextSharp.text.Font font3 = FontFactory.GetFont("Arial", 15, iTextSharp.text.Font.BOLD, Color.BLACK);

            document.Open();
            //============================= Document is open and addition start =====================


            string imageURL = HttpContext.Current.Server.MapPath("~\\images") + "\\img_lawyer.jpg";
            iTextSharp.text.Image jpg = iTextSharp.text.Image.GetInstance(imageURL);
            //Resize image depend upon your need
            jpg.ScaleToFit(120, 100);
            //Give space before image
            jpg.SpacingBefore = 10f;
            //Give some space after the image
            jpg.SpacingAfter = 1f;
            //set Alignment 
            jpg.Alignment = Element.ALIGN_RIGHT | iTextSharp.text.Image.TEXTWRAP;
            document.Add(jpg);

            phrase = new Phrase();
            phrase.Add(new Chunk("\n\nM. Ikram Raj\n", font3));
            phrase.Add(new Chunk("M.A.(ENG.) LL.B.\n", font2));
            phrase.Add(new Chunk("ADVOCATE\n", font2));
            phrase.Add(new Chunk("Ref.No. L.N./05/2019\n\n\n", font2));
            phrase.Add(new Chunk("                                          LEGAL DEMAND NOTICE THROUGH REGD.AD.\n\n", font2));
            phrase.Add(new Chunk("Dated: " + mycon.indianTime().ToString("dd-MM-yyyy") + ".\nReg. A.D. / O.C.\nTo,\n\n", font1));
            phrase.Add(new Chunk("M/S - " + name + "\nADDRESS - " + address + "\nCONTACT NUMBER - " + number + "\n\n", font2));
            phrase.Add(new Chunk("Dear Sir / Madam\n\n", font1));
            phrase.Add(new Chunk("I am concerned for ", font1));
            phrase.Add(new Chunk("ONLINEWORKSJOB", font2));
            phrase.Add(new Chunk(", 210, 2nd Floor, Jaina Tower 2, District Center Janakpuri West, Delhi-110058 (herein referred to as my client) and under the instructions and authority received from and on behalf of my client, I under signed, do hereby serve upon you instant legal demand notice, contents of which are as under :-\n\n", font1));
            phrase.Add(new Chunk("That my above named client is doing business of Business Outsourcing Service (BPO) in the name and style of ", font1));
            phrase.Add(new Chunk("ONLINEWORKSJOB", font2));
            phrase.Add(new Chunk(", at his above mentioned address. You are residing at your above mentioned address and are known to my client since you availed online BPO assignment of ", font1));
            phrase.Add(new Chunk("(FORM FILLING) ", font2));
            phrase.Add(new Chunk("from ", font1));
            phrase.Add(new Chunk("ONLINEWORKSJOB. ", font2));
            phrase.Add(new Chunk("Our client reserves all the right to file a complaint under Indian contract act 1872 section 73,74 and 75 for the breach of the contract. Further, our client reserves all the rights to file appropriate complaint before Bar Council of India in respect of the same.\n\n", font1));
            phrase.Add(new Chunk("The foregoing is not intended nor shall it be constructed as a complete recitation of the facts and events concerning the above- referenced matter, nor shall it be constructed as a waiver of any rights, remedies or claims, legal or equitable, which our client may have. This is without any prejudice to any of legal rights of our client.\n\n", font1));
            phrase.Add(new Chunk("1. Breach of contract is a legal cause of action and a type of civil wrong, in which a binding agreement or bargained-for exchange is not honoured by one or more of the parties to the contract by non-performance or interference with the other party's performance.\n\n", font1));
            phrase.Add(new Chunk("2. That, you as on Dated: ", font1));
            phrase.Add(new Chunk(regdate, font2));
            phrase.Add(new Chunk(" completed registration with my client agreed upon the Terms & Conditions/Rules & Regulations by entering into Agreement on Non Judicial Stamp Paper of Rs. 50/- as on dt. ", font1));
            phrase.Add(new Chunk(regdate, font2));
            phrase.Add(new Chunk(" with my client with respect ", font1));
            phrase.Add(new Chunk("Data Outsourcing Assignments (FORM FILLING) vide Project ID No " + regnumber + ". ", font2));
            phrase.Add(new Chunk("Now according to clause no. 2 (2(b)) of terms and conditions of said agreement entered into by you with my client i.e. ", font1));
            phrase.Add(new Chunk("TAT (Turn Around Time) ", font2));
            phrase.Add(new Chunk("you have not submitted work with desired accuracy within stipulated period of  " + days + " days mentioned in the agreement.\n\n", font1));
            phrase.Add(new Chunk("3. That, my client informed you for the same by sending you an email with respect to the non-submission or inaccuracy of work assigned to you and for the very reason you have made liable yourself for the payment of utility and maintenance charges to my client because of non-submission or inaccuracy of the work as per the terms.\n\n", font1));
            phrase.Add(new Chunk("4. That, in spite of consistent communications by my client through telecommunication and email you have not paid Rs. " + amount + "/- of utility and maintenance charges for ", font1));
            phrase.Add(new Chunk("ID. No. " + regnumber, font2));
            phrase.Add(new Chunk(" deliberately ignoring my client, knowing full well the grim consequences thereof. You are therefore liable ", font1));
            phrase.Add(new Chunk("for civil as well as criminal legal action.\n\n", font2));
            phrase.Add(new Chunk("5. That, this legal demand notice is issued to you to pay an amount of Rs. " + amount + " (" + word + ") and Rs 3000 (Three thousand only) as legal fee for this instant legal demand notice within 2 days through Cash Deposit/ NEFT/IMPS in favour of my client ‘", font1));
            phrase.Add(new Chunk("ONLINEWORKSJOB", font2));
            phrase.Add(new Chunk("’ , fling which my client will be constrained to legally proceedings against you in the competent courts of law.\n\n", font1));
            phrase.Add(new Chunk("6. That , this notice is clear, no word has a cutting, misprinting and neither is missing, if any , it bars my signature , this notice comprises of 2 pages which are serially pasted on the left side margin , you are advised to keep this notice in your safe possession , so that it may be produced before the competent courts of law whenever required. The copy of the same has been retained by me in my office.\n\n", font1));
            phrase.Add(new Chunk("7. That, by this notice, you are hereby instructed to pay the utility and maintenance charges of ", font1));
            phrase.Add(new Chunk("Rs. " + amount + "/- within 2 days", font2));
            phrase.Add(new Chunk(" of the receipt of this notice, failing which, my client will straightway initiate legal remedy (civil as well as criminal) at Tis Hazari Court (New Delhi) and all the cost and consequences for the said proceedings/prosecution will be on your head of which take a serious note.\n\n", font1));
            phrase.Add(new Chunk("8. This notice is given to you because of your default and you are therefore liable to pay Rs. 3000/- (Three thousand only) as legal charges to my client.\n\n", font1));
            phrase.Add(new Chunk("9. Kindly Deposit " + amount + "/- + 3000/-.\n\n", font1));
            phrase.Add(new Chunk("This notice is sent to you by Regd. A.D\n\n", font1));
            phrase.Add(new Chunk("New Delhi\n", font1));
            phrase.Add(new Chunk("Date- : " + mycon.indianTime().ToString("dd-MM-yyyy") + ".\n", font1));
            phrase.Add(new Chunk("M. Ikram Raj\n", font2));
            phrase.Add(new Chunk("Advocate\n\n", font2));
            document.Add(phrase);

            imageURL = HttpContext.Current.Server.MapPath("~\\images") + "\\img-lawyerstamp.png";
            jpg = iTextSharp.text.Image.GetInstance(imageURL);
            jpg = iTextSharp.text.Image.GetInstance(imageURL);
            jpg.ScaleToFit(200f, 160f);
            jpg.SpacingBefore = 10f;
            jpg.SpacingAfter = 1f;
            jpg.Alignment = Element.ALIGN_LEFT;
            document.Add(jpg);

            //=========================== Document is close and addition end =======================

            document.Close();
            byte[] bytes = memoryStream.ToArray();
            memoryStream.Close();

            //System.Threading.Thread.Sleep(5000);
        }
    }
}