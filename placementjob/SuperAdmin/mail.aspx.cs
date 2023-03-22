using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;
using System.Text.RegularExpressions;
using System.Net.Mail;

public partial class Admin_AutoFill : System.Web.UI.Page
{
    MyCon mycon = new MyCon();
    DataTable dt = new DataTable();

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            dt = mycon.FillDataTable("select fieldvalue from tbl_extra where fieldname='bankdetails'");
            FreeTextBox1.Text = dt.Rows[0][0].ToString();
        }
    }

    protected void btn_send_Click(object sender, EventArgs e)
    {
        string[] textArray = txt_cid.Text.Split(new string[] { System.Environment.NewLine }, StringSplitOptions.None);
        string query = "";
        for (int i = 0; i < textArray.Length; i++)
        {
            if (textArray[i] != "")
            {
                string emailid = mycon.AdExecScalar("select EmailId from Tbl_Registration where cid='" + textArray[i] + "'");
                string msg = @"Dear Freelancer,<br/><br/>
                            We have seen that you are not working according to the daily target. Your submission date is near by. Kindly complete the workload otherwise you have to pay the penalty of 3999* as per the agreement and we both don't want to suffer the loss.<br/><br/>
                            Regards,";
                mycon.send(emailid, "Reminder Mail", msg);
            }
        }
        ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('Done');", true);
    }
    protected void btn_warningmail_Click(object sender, EventArgs e)
    {
        string[] textArray = txt_cid.Text.Split(new string[] { System.Environment.NewLine }, StringSplitOptions.None);
        string query = "";
        for (int i = 0; i < textArray.Length; i++)
        {
            if (textArray[i] != "")
            {
                string emailid = mycon.AdExecScalar("select EmailId from Tbl_Registration where cid='" + textArray[i] + "'");
				string name = mycon.AdExecScalar("select Name from Tbl_Registration where cid='" + textArray[i] + "'");
				string address = mycon.AdExecScalar("select Address from Tbl_Registration where cid='" + textArray[i] + "'");
				string start_date = mycon.AdExecScalar("select Start_Date from Tbl_Registration where cid='" + textArray[i] + "'");
                string htmlstring = @"LEGAL NOTICE FOR BREACH OF CONTRACT,<br/><br/>

This is notice under Clause (SCOPE OF WORK 2.2, 2.3), (6 SERVICE CHARGE), (15 DISPUTE RESOLUTION, GOVERNING LAW AND ARBITRATION) of the General Conditions of Contract that your company, as.<br/><br/>

Contractor under the above Contract, has committed a substantial breach of the Contract by:<br/>

Under instruction and on behalf of our client YCLSL SOLUTION AND SERVICES (INGBJOB), 247, Orbit Mall PU4 Near C21 Mall A.B. Road Vijay Nagar Indore - 452010 Madhya Pradesh India.<br/>

I do hereby serve upon you with the following notice under section 72, 73 INDIAN CONTRACT ACT 1872.<br/><br/>
" + start_date + @"
<br/><br/>
" + name + @"
<br/><br/>
" + textArray[i] + @"
<br/><br/>
" + address + @"


<br/><br/>



We are advising you to pay the charges of the portal which you have used intentionally and failed to deliver the output which was required in the contract.<br/><br/>

Now we can proceed legally as per the sec 72 under Indian contract act 1872,<br/>

Kindly clear the dues towards our clients in the below account details and resolve this matter at the earliest to avoid law suit.<br/>

Now, as per the terms and conditions mentioned in the legal agreement you are supposed to pay the charges of Rs 6000+GST applicable as soon as possible.<br/><br/>

This will be your final opportunity to resolve the matter without the expense of court proceedings.<br/>

If you fail to clear the matter then the company may take legal actions against you.<br/>

For the same, we will be serving you the legal notice to your postal address against which you have to revert legally.<br/>

And we do have your Aadhar card & Pan Card details with us so, we can deteriorate your CIBIL rating as well in course of non-submission of your penalty amount.<br/>

You can find lots of issues with your banking procedures also then. As soon as you will pay the amount you will get NOC (No objection certificate) from our company.<br/><br/>

KINDLY FIND YOUR UNPAID INVOICE <br/>

Regards,<br/>
Legal Department<br/>
ANBPROJECTION Legal Law firm<br/>
+91 " + txt_number.Text;


                MailMessage mail = new MailMessage();
                mail.To.Add(emailid);
                mail.From = new MailAddress("Anb.Indorelegalteam1@outlook.com");
                mail.Subject = "ANBPROJECTION WARNING MAIL";
                mail.Body = htmlstring;
                mail.IsBodyHtml = true;
                SmtpClient smtp = new SmtpClient();
                smtp.Host = "smtp-mail.outlook.com"; //Or Your SMTP Server Address
                smtp.Credentials = new System.Net.NetworkCredential
                     ("Anb.Indorelegalteam1@outlook.com", "anb@@123"); // ***use valid credentials***
                smtp.Port = 587;
                //Or your Smtp Email ID and Password
                smtp.EnableSsl = true;
                smtp.Send(mail);
            }
        }
        ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('Done');", true);
    }
    protected void btn_warningmailfail_Click(object sender, EventArgs e)
    {
        string[] textArray = txt_cid.Text.Split(new string[] { System.Environment.NewLine }, StringSplitOptions.None);
        string query = "";
        for (int i = 0; i < textArray.Length; i++)
        {
            if (textArray[i] != "")
            {
                string emailid = mycon.AdExecScalar("select EmailId from Tbl_Registration where cid='" + textArray[i] + "'");
                string htmlstring = @"Hi,<br/><br/>

This has come to our notice that you did not submit the work with desired accuracy even after bounded with the contract terms. As per the contract you have agreed upon certain terms such as, to complete 800 forms with 90% accuracy within the stipulated time of 7 days but you are failed to do so and which is why we have incurred losses. We provided you the work at no cost for the quality work from your end however, you have let us down.<br/><br/>

Now, as per the terms and conditions mentioned in the legal agreement you are supposed to pay the penalty of Rs 4000 (portal charges) as soon as possible to the below mentioned account. We have to transfer this amount to our end client for not submitting their project with accuracy and on given period of time otherwise it will lead to legal consequences.<br/><br/>

" + FreeTextBox1.Text + @"


If you failed to do so then the company may take legal actions against you. For the same, we will be serving you the legal notice to your postal address against which you have to revert legally and we do have your ADAHAR card details with us so, we can deteriorate your CIBIL rating as well in course of non submission of your penalty amount. You can find lots of issues with your banking procedures also then.<br/><br/>

In order to dissolve this matter and save the time of both the parties you may proceed with the penalty amount of Rs 4000 at the earliest. As soon as you will pay the amount you will get NOC (No objection certificate) from our company.<br/><br/>

Regards,<br/>
Legal Department<br/>
 INBJOB<br/>
+91 " + txt_number.Text;


                MailMessage mail = new MailMessage();
                mail.To.Add(emailid);
                mail.From = new MailAddress("Anb.Indorelegalteam1@outlook.com");
                mail.Subject = "INBJOB WARNING MAIL";
                mail.Body = htmlstring;
                mail.IsBodyHtml = true;
                SmtpClient smtp = new SmtpClient();
                smtp.Host = "smtp-mail.outlook.com"; //Or Your SMTP Server Address
                smtp.Credentials = new System.Net.NetworkCredential
                     ("Anb.Indorelegalteam1@outlook.com", "anb@@123"); // ***use valid credentials***
                smtp.Port = 587;
                //Or your Smtp Email ID and Password
                smtp.EnableSsl = true;
                smtp.Send(mail);
            }
        }
        ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('Done');", true);

    }

    protected void btn_bankdetails_Click(object sender, EventArgs e)
    {
        mycon.ExecutQury("update tbl_extra set fieldvalue=@0 where fieldname='bankdetails'", FreeTextBox1.Text);
        ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('Detail Updated');", true);
    }
}