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
using iTextSharp.text;
using iTextSharp.text.pdf;

/// <summary>
/// Summary description for MyCon
/// </summary>
public class MyCon
{

    public SqlConnection con = new SqlConnection(System.Configuration.ConfigurationManager.ConnectionStrings["MyConnection"].ToString());

    SqlCommand cmd = new SqlCommand();
    string Str;
    public string qurey;
    public string falge;
    public SqlCommand cmd1 = new SqlCommand();

    public MyCon()
    {

        //
        // TODO: Add constructor logic here
        //
    }
    public void ExecutQury(string strQury)
    {

        if (con.State == ConnectionState.Closed)
        {
            con.Open();
        }
        cmd = new SqlCommand(strQury, con);
        cmd.ExecuteNonQuery();
        con.Close();

    }
    public DataTable FillDataTable(string strQury)
    {
        DataTable dt = new DataTable();
        cmd = new SqlCommand(strQury, con);
        cmd.CommandText = strQury;
        cmd.CommandTimeout = 120;
        SqlDataAdapter da = new SqlDataAdapter(cmd);
        dt.Clear();
        da.Fill(dt);
        return dt;

    }
    public DataSet FillDataset(string strQury)
    {
        DataSet ds = new DataSet();
        cmd = new SqlCommand(strQury, con);
        cmd.CommandText = strQury;
        cmd.CommandTimeout = 120;
        SqlDataAdapter da = new SqlDataAdapter(cmd);
        ds.Clear();
        da.Fill(ds);
        return ds;
    }

    public void SaveData()
    {
        con.Open();
        cmd1.Connection = con;
        cmd1.CommandText = qurey;
        cmd1.CommandType = CommandType.StoredProcedure;
        cmd1.ExecuteNonQuery();
        cmd1.Parameters.Clear();
        con.Close();
    }
    public string AdExecScalar(string str)
    {
        try
        {
            if (con.State == ConnectionState.Closed)
            {
                con.Open();
            }
            cmd = new SqlCommand(str, con);
            str = Convert.ToString(cmd.ExecuteScalar());
            con.Close();
            if (str == "")
            {
                str = "0";
            }
            return str;
        }
        catch (Exception ex)
        {
            return "0";
        }
    }

    public void ExportDataSetToExcel(DataTable dt, string filename)
    {
        HttpResponse response = HttpContext.Current.Response;
        //response.Clear();
        //response.Charset = "";
        //response.ContentType = "application/vnd.ms-excel";
        //response.AddHeader("Content-Disposition", "attachment;filename=\"" + filename + "\"");
        //using (StringWriter writer = new StringWriter())
        //{
        //    using (HtmlTextWriter writer2 = new HtmlTextWriter(writer))
        //    {
        //        DataGrid grid = new DataGrid
        //        {
        //            DataSource = dt
        //        };
        //        grid.DataBind();
        //        grid.RenderControl(writer2);
        //        response.Write(writer.ToString());
        //        response.End();
        //    }
        //}
        /////////////////////////////////////////////////////
        //Clears all content output from the buffer stream.  
        response.ClearContent();
        //Adds HTTP header to the output stream  
        response.AddHeader("Content-Disposition", "attachment;filename=\"" + filename + "\"");

        // Gets or sets the HTTP MIME type of the output stream  
        response.ContentType = "application/vnd.ms-excel";
        string space = "";

        foreach (DataColumn dcolumn in dt.Columns)
        {

            response.Write(space + dcolumn.ColumnName);
            space = "\t";
        }
        response.Write("\n");
        int countcolumn;
        foreach (DataRow dr in dt.Rows)
        {
            space = "";
            for (countcolumn = 0; countcolumn < dt.Columns.Count; countcolumn++)
            {

                response.Write(space + dr[countcolumn].ToString());
                space = "\t";
            }
            response.Write("\n");
        }
        response.End();
    }
    public bool send(String emailId, String subject, String message)
    {
        string Email = ConfigurationManager.AppSettings["Email"].ToString();
        string Emailto = emailId;
        MailMessage mail = new MailMessage(Email, Emailto);
        mail.Subject = subject;
        mail.Body = message;
        mail.IsBodyHtml = true;
        SmtpClient smtp = new SmtpClient();
        smtp.Send(mail);
        return true;
    }

    public string randome(int no)
    {
        string strPwdchar = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        string strPwd = "";
        Random rnd = new Random();
        for (int i = 0; i < no; i++)
        {
            int iRandom = rnd.Next(0, strPwdchar.Length - 1);
            strPwd += strPwdchar.Substring(iRandom, 1);
        }
        return strPwd;
    }

    public DateTime indianTime()
    {
        TimeZoneInfo INDIAN_ZONE = TimeZoneInfo.FindSystemTimeZoneById("India Standard Time");
        DateTime indianTime = TimeZoneInfo.ConvertTimeFromUtc(DateTime.UtcNow, INDIAN_ZONE);
        return indianTime;
    }

    public void ExecutQury(string strQury, params string[] parameterValues)
    {

        if (con.State == ConnectionState.Closed)
        {
            con.Open();
        }
        cmd = new SqlCommand(strQury, con);
        for (int i = 0; i < parameterValues.Length; i++)
        {
            cmd.Parameters.Add("@" + i, parameterValues[i].ToString());
        }
        cmd.CommandTimeout = 120;
        cmd.ExecuteNonQuery();
        con.Close();

    }

    public DataTable FillDataTable(string strQury, params string[] parameterValues)
    {
        DataTable dt = new DataTable();
        cmd = new SqlCommand(strQury, con);
        for (int i = 0; i < parameterValues.Length; i++)
        {
            cmd.Parameters.Add("@" + i, parameterValues[i].ToString());
        }
        cmd.CommandText = strQury;
        SqlDataAdapter da = new SqlDataAdapter(cmd);
        dt.Clear();
        da.Fill(dt);
        return dt;
    }
    public DataSet FillDataset(string strQury, params string[] parameterValues)
    {
        DataSet ds = new DataSet();
        cmd = new SqlCommand(strQury, con);
        for (int i = 0; i < parameterValues.Length; i++)
        {
            cmd.Parameters.Add("@" + i, parameterValues[i].ToString());
        }
        cmd.CommandText = strQury;
        SqlDataAdapter da = new SqlDataAdapter(cmd);
        ds.Clear();
        da.Fill(ds);
        return ds;
    }
    public void generatepdf(string temppath, string emailid, string name, string number, string address,string page)
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

            string imageURL = HttpContext.Current.Server.MapPath("~\\stamp") + "\\"+ page +".jpeg";
            iTextSharp.text.Image jpg = iTextSharp.text.Image.GetInstance(imageURL);
            jpg.SetAbsolutePosition(0, 0);
            jpg.ScaleToFit(1080f, 820f);
            //jpg.SpacingBefore = 0;
            //jpg.SpacingAfter = 0;
            jpg.Alignment = Element.ALIGN_TOP;
            document.Add(jpg);

            phrase = new Phrase("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n", font3);
            document.Add(phrase);
			phrase = new Phrase("\n\n\n\n\n\n\n\n\n\n\n\n\n", font3);
            document.Add(phrase);
            phrase = new Phrase("\n\n\n   AGREEMENT FOR FREELANCE DATA TRANSCRIPTION\n\n", font3);
            document.Add(phrase);

            phrase = new Phrase(new Chunk("This Agreement executed ", font1));
            phrase.Add(new Chunk(  indianTime().ToString("dd-MM-yyyy"), font2));
			phrase.Add(new Chunk(" between.", font1));
			phrase.Add(new Chunk("INDIA TECHNOLOGY", font2));
            phrase.Add(new Chunk(" Having its Register Office at - ", font1));
            phrase.Add(new Chunk("Head Office: 711, SHEKHAR CENTRAL INDORE MANORAMA GANJ INDORE Madhya Pradesh India. PINCODE:-452001\n\n ", font2));
            //phrase.Add(new Chunk("F8, Yogeshwar complex, Adajan, Surat-395009 ", font2));
            //phrase.Add(new Chunk("Office no-11 dhruv complex,near maharani temple,boring road Patna,Bihar-500001 ", font2));
            //phrase.Add(new Chunk("210, 2ND FLOOR, JAINA TOWER 2, DISTRICT CENTRE, JANAKPURI WEST, DELHI -110058 ", font2));
            //phrase.Add(new Chunk("1024 Dda Market, Shastri Park, Delhi East, Delhi", font2));
            
            phrase.Add(new Chunk("\t\t (Herein after referred to as ‘the Party ", font1));
            phrase.Add(new Chunk( name, font2));
            phrase.Add(new Chunk("," +address + " herein after referred to as ‘the Party of the Second Part) .\n\n", font1));
            phrase.Add(new Chunk("WHEREAS", font2));
            phrase.Add(new Chunk("the Party of the First Part is engaged mainly in Outsourcing of IT enabled Services and To deliver Data Entry and Transcription and allied Activities and Other Ancillary Activities Associated there with an Organization engaged in providing data to you end clients and  data entry related line of work. And executing such work Outsourced, through Delivery Partners.\n\n", font1));
             phrase.Add(new Chunk("AND WHEREAS", font2));
            phrase.Add(new Chunk("the Party of the First Part is bound by time schedule set by the Delivery Partners and that its reputation is built upon speedy and accurate transcription and requires the said party to deliver accomplished work within shortest span and with desired accuracy the First Party has entered with a firm launching its new BANKING  PORTAL and has represented itself that it has an expertise in the area of providing MARKETING (Banking)/ Presently it is in a position to procure the business for form filling more meaningfully described in the column Scope of Work, through their principals.AND WHEREAS the Business Associate is engaged inter alias, in the business of providing a wide Spectrum of software solutions & services. The Business Associate has acquired the necessary expertise and developed the requisite skill base and Infrastructure for successful execution of Form Filling Projects.\n\n", font1));
             phrase.Add(new Chunk("Whereas, the Second Party is an individual and a Freelancer who is willing to provide its services to the Job Portal Company, via medium of First Party in relation with IT &all data related work which is to be provided by the First Party\n\n", font1));
            phrase.Add(new Chunk("This Agreement represents the business Agreement and operational understandings between the parties and shall remain in effect for a period of 30 DAYS from the date of execution hereof or from the date of providing the first data whichever is later & can be extended for the period as mutually agreed upon, for the purpose.\n", font1));
            document.Add(phrase);
			imageURL = HttpContext.Current.Server.MapPath("~\\images") + "\\company_stamp.jpg";
            jpg = iTextSharp.text.Image.GetInstance(imageURL);
            jpg.ScaleToFit(100f, 80f);
            jpg.SpacingBefore = 10f;
            jpg.SpacingAfter = 1f;
            jpg.Alignment = Element.ALIGN_LEFT;
            document.Add(jpg);

            phrase = new Phrase("\n \n\n \n                                NOW THIS AGREEMENT WITNESSETH AS FOLLOWS:\n\n", font2);
            phrase.Add(new Chunk("It is hereby agreed between the Parties as under:\n\n", font2));
            document.Add(phrase);

            phrase = new Phrase();
            phrase.Add(new Chunk("1.1 That both the Parties has decided with sweet will and free consent to work together for gains.\n\n", font1));
            phrase.Add(new Chunk("1.2 The purpose of Parties behind this Agreement is to work for gain in relation to the Freelance services. \n\n", font1));
            phrase.Add(new Chunk("1.3. That the First Party is Ruling out on survey for banking purpose digitally/non digitally /Free Lancers and is focused to provide its services to the Partners/Associates (M/s INGBJOB.):\n\n", font1));
            phrase.Add(new Chunk("2. Scope of Work:", font2));
            phrase.Add(new Chunk(" The original data will be available on the work environment software provided by INGB JOBS at the time of signup. Business associate are required to feed the provided data in the provided software as per the guidelines. Data supply and preservation of the output file is done online on real time basis. the party of the first part is collecting is collecting data for banking formalities of our end clients for that they are ruling out on survey for the data by typing in company’s portal the employee of the YCLSL SOLUTION AND SERVICES are also working on this project those person are qualified in their work and fulfill the company requirement the company will pay for them according to terms & condition.\n\n", font1));
           phrase.Add(new Chunk(" \t \t The data surveying duration will be for one year from 2021 to 2022.2.1 \n\n", font1));
            phrase.Add(new Chunk(" 2.1) The First Party shall provide details of the FORMS through the login credentials shared through SMS Or Email.\n\n", font1));
            phrase.Add(new Chunk("2.2 The Second Party further Represents to the First Party, the time for the Completion of the said data entry related services as mentioned in this Agreement, shall Commence Immediately upon logging on the portal OR if the Commencement Date is mentioned in the said Communication, from such date, and it shall Continue to Access its said Portal/E-Mail as provided in the Records of the First Party, as frequently as necessary for the said Purpose..\n\n", font1));
            phrase.Add(new Chunk(" That the Second Party agrees to pay Rs. 6000 as charges for membership, Portal charges, GST and other applicable charges in case of failure to submit complete workload or to provide workload on time with desired accuracy. This membership will include Jobs Vacancy information in Pan India through our Social Media platform.\n\n", font1));
            phrase.Add(new Chunk("2.3 That in lieu/consideration of the above Fees/charges, the first party will provide agreement which will be valid for 1 month but project duration will be 5 days as mentioned. It also pertinent to mention here that one project will contain 500 FORMS in one project .\n\n", font1));
             phrase.Add(new Chunk("2.4 That the First Party will give 500 FORMS in PDF or any other image format on the Company’s Portal. On the Portal itself, the details of the work of data processing are provided, which will clearly mention the details, as in what & how is to be processed.\n\n", font1));
			 phrase.Add(new Chunk("\t \t  a) Payment to be made maximum within 5 days of each calendar month, from the QC report, which will be given usually within 5 days of submission of the work. \n", font1));
			 phrase.Add(new Chunk(" \t \t b) That the First party gets all these FORMS from INGBJOB (END CLIENTS).\n\n", font1));
			 phrase.Add(new Chunk(" \t \t c)  In case of any dispute second party must contact to the first party and if they are unable to resolve their problem, they can proceed legally. Second party can communicate through SUPPORT@INGBPROJECTION.COM  or on customer care numbers provided.\n\n", font1));
			phrase.Add(new Chunk("3. Plan Details:", font2));
			phrase.Add(new Chunk(" Second party will get 500 forms for 5 days. Per form rate will be Rs. 35/-.", font1));
			phrase.Add(new Chunk("(a). No initial payment is required to be given by second party.", font1));
			phrase.Add(new Chunk("(b). After getting the accuracy report of having 90% above accuracy, your payment will be processed within 7 international working days in to your respective bank account. An accurate form is that which doesn't have any error such as spelling/punctuation/extra space/extra text/missing text.", font1));
			phrase.Add(new Chunk("(c). In the matter of failure, non-submission, accuracy below 90% then company is entitled to receive amount of Rs. 6000* by any cost from the second party. If in case second party uses multiple login then penalty will be Rs. 999/-. If second party passes and achieves the accuracy of 90% or above, then amount will be deducted from his work payment and remaining shall be paid.", font1));
			phrase.Add(new Chunk("(d). The charge of Rs. 6000* is related to service, development and maintenance cost of the platform where he is working online.", font1));
			phrase.Add(new Chunk("      \n \n Technical clause:\n", font2));
            phrase.Add(new Chunk("• Helpline department will support you in only 10% queries from the whole project.\n", font1));
            phrase.Add(new Chunk("• For example: if you have taken the 500 pages/forms plan, then helpline dept. is liable to give reply only 55 pages/forms queries of 10% of whole project.\n", font1));
            phrase.Add(new Chunk("• Work will automatically get Submit in 48 hours.\n", font1));
            phrase.Add(new Chunk("• No use of any shortcut keys while typing in terminal else you will be responsible for the same.\n\n", font1));
            phrase.Add(new Chunk("4. TIMEFRAME FOR COMPLETION OF TRANSCRIPTION:", font2));
            phrase.Add(new Chunk(" The Second Party shall complete the services of the said Data entry work in Seven (5) days TAT period, i.e., maximum 500 FORMS can be completed within a period of 5 days. The Second Party alone shall be responsible for the maintenance of Hardware and Personnel for such timely services and no excuse of whatsoever Nature shall be entertained for delay in Supply of services, since Time is the Essence of this Contract.\n\n", font1));
            phrase.Add(new Chunk("5. DURATION OF THE CONTRACT:", font2));
            phrase.Add(new Chunk(" The Present Contract shall be in force for 1 month membership. The said Contract shall come to an End at the Expiry of the said Period and may be renewed by Mutual Consent and on such Revised Terms agreed between the Parties and on Payment of Processing Charges for another Project by the Second Party.\n\n", font1));
            phrase.Add(new Chunk(" ID Allocation:", font2));
			 phrase.Add(new Chunk("Business Associate will get single id to work on and Business Associate can work 24X7 on this id.\n", font1));
			phrase.Add(new Chunk(" TAT (Turn Around Time):", font2));
            phrase.Add(new Chunk(" Turn around time for completing the project is mentioned in the schedule. The Business Associate through this agreement guarantees the delivery of work within stipulated timeframe with desired accuracy.\n\n", font1));
            phrase.Add(new Chunk("6. SERVICE CHARGE:", font2));
            phrase.Add(new Chunk(" If Business Associate fails to fulfill terms and conditions mentioned by Client, then Business Associate have to compulsory pay penalty amount of 3999* to stop legal proceedings within 12 hours. In the matter of fact failure, not submitted the Client is entitled to receive penalty amount by any cost. If Business Associate achieves accuracy then Business Associate will not be liable to pay the penalty amount. If Business Associate fails in achieving accuracy, then Business Associate has to pay the penalty according to the selected plan as a liability.\n\n", font1));
            phrase.Add(new Chunk(" WHY SERVICE CHARGES?\n", font2));
			phrase.Add(new Chunk(" - We offer 24*7 helpline options on website.\n", font1));
			phrase.Add(new Chunk(" - We offer day time customer care call support.\n", font1));
			phrase.Add(new Chunk(" - Email support\n", font1));
			phrase.Add(new Chunk(" - Job consultation charges.\n", font1));
			phrase.Add(new Chunk(" - Stamp paper and agreement preparation charges.\n", font1));
			phrase.Add(new Chunk(" - Charges will be deducted from the payment once accuracy is achieved.\n\n", font2));
			phrase.Add(new Chunk("7. Confidentiality :\n", font2));
			phrase.Add(new Chunk(" a) 'Confidential Information' refers in this Agreement to any information - technical, commercial or of any other nature (including any information regarding the identity of a customer of First party and all otherinformation attributable to the customer’s business or systems) - regardless of whether or not such information has been documented, with the exception of information that is or becomes publicly known other than by the Second Party’s breach of the provisions of This Agreement.\n\n", font1));
			phrase.Add(new Chunk(" b) The Second party undertakes not to use Confidential Information or other information, such as software, etc., obtained within the scope of this Agreement for any other purpose or in any other context than to carry out its specific assignments under this Agreement. Furthermore, the Second Party is prohibited from using Confidential Information obtained within one specific assignment under this Agreement in order to carry out another specific assignment under this Agreement, unless otherwise expressly agreed within the scope of the latter assignment.\n\n", font1));
			phrase.Add(new Chunk(" c) The Second Party undertakes under this Section that this shall also apply to the Second Party’s employees, Associates and consultants. The Second party shall ensure that such employees or consultants that are likely to come in contact with Confidential Information sign separate /confidentiality undertakings on the same terms and condition.\n\n", font1));
		   phrase.Add(new Chunk("8. TERMS OF PAYMENT AND COMPENSATION: \n\n", font2));
            phrase.Add(new Chunk(" The Payment Terms for each of the Plans shall be as Under:\n\n", font1));
            phrase.Add(new Chunk(" The payment for every FORM will be INR 35 but achieving 90% accuracy is compulsory for this payment and compensation. If you will achieve accuracy below 90% then INR 5 will be given per accurate FORMS but completion of project is mandatory.\n\n", font1));
			 phrase.Add(new Chunk(" The Entire such Payment Payable by the First Party to the Second Party, shall be made within maximum 5 days of the Receipt of the Accuracy Report.\n\n ", font1));
			 phrase.Add(new Chunk(" In each plan the payment shall be made only for the accurate data processing of FORMS. Any Inaccurate data processing will not qualify for the payment regardless of number of errors found in that page, more or less \n\n", font1));
			  phrase.Add(new Chunk("9.DETERMINATION OF ACCURACY:\n ", font2));
            phrase.Add(new Chunk(" The accuracy will be determined per data processing of FORMS. If any Mistake is found such as spelling error, Punctuation error, Extra Word, Missing word, Extra Space, Space Missing, Extra Enter or Enter Missing, then that form will be considered as Inaccurate FORMS and hence if accuracy falls below 90% then no payment shall be processed. The Test of the Accuracy shall be made by a determining Centre appointed by the Party of the First Party’s Associate/Partner (FORMS filling) and the Report of such Accuracy subject to procedure outlined below, would be Final and Conclusive, with no room for Disputing the Veracity of the same by the Party of the Second Part.\n\n", font1));
            phrase.Add(new Chunk("\n 10. PROCEDURE FOR GENERATION OF ACCURACY REPORT :\n\n", font2));
             phrase.Add(new Chunk("The Determining Centre personnel shall check all the data processing of FORMS. After an error is found in a particular FORM the Centre personnel shall list that as inaccurate and start checking the next FORM. All the errors in the whole FORM will not be shown in the Accuracy Report.Once all data processing of FORM are checked, the final Accuracy Report shall be generated.\n\n ", font1));
			phrase.Add(new Chunk("11. TECHNICAL SPECIFICATIONS FOR DATA RELATED WORK:\n\n", font2));
			phrase.Add(new Chunk(" The Font used in Data shall be ‘default font of portal’ Size default, irrespective of the Font used in the IMAGE.\n", font1));
			phrase.Add(new Chunk(" No ‘Justification’ of Transcribed Text shall be made.\n", font1));
			phrase.Add(new Chunk("The Data shall be an Exact Replica of the FORMS in terms of a Split in a Word or the End of a line.\n", font1));
			phrase.Add(new Chunk("Accent Characters shall be typed as Normal Characters.\n", font1));
			phrase.Add(new Chunk(" Transcribed text shall be in its normal Style – ‘Bold’ or ‘Italics’ shall not be used.\n", font1));
            phrase.Add(new Chunk(" Shortcut keys and the character mapping should not be used.+\n", font1));
            phrase.Add(new Chunk("All the fields in the forms should be typed, any field should not be left blank.\n", font1));
            phrase.Add(new Chunk(" NO grammatical rules should be applied. \n", font1));
            phrase.Add(new Chunk(" \t Give one space between two words if applicable in the data.\n\n", font1));
            phrase.Add(new Chunk("12. Severability:\n", font2));
            phrase.Add(new Chunk(" The various provisions of this “Freelance services Contract” are severable and if any provision is held to be invalid or unenforceable by any court of competent jurisdiction then such invalidity or unenforceability shall not affect the remaining provisions of this Services Agreement. A FORM will not be edited after 48 hours of submission on the portal. It will be locked till the submission date.\n\n", font1));
            phrase.Add(new Chunk("13. Dispute Resolution, Governing Law and Arbitration:", font2));
            phrase.Add(new Chunk(" a) This Agreement shall be governed by laws of India. Any dispute arising in relation to this Agreement shall first be resolved through amicable way i.e. amicable talks and then arbitration under the Arbitration & Conciliation Act, 1996.\n\n", font1));
            phrase.Add(new Chunk(" b) The First Party shall notify an Arbitrator to the Second Party. Provided that none of such arbitrators shall have represented or had a business connection with the First Party previously.\n", font1));
			phrase.Add(new Chunk(" c) The arbitration shall be held in Indore Madhya Pradesh and conducted in English language. Every order of the arbitrator shall be justified by reasons in writing.\n", font1));
			phrase.Add(new Chunk(" d) Notwithstanding the foregoing, the First Party shall be entitled to obtain such injunctive or equitable relief as may be necessary by any court of competent jurisdiction including any court having jurisdiction over a place where the Second Party is having presence.\n", font1));
		   phrase.Add(new Chunk("\n\n 14. WAIVER:\n\n", font2));
            phrase.Add(new Chunk(" The Second Party expressly undertakes to submit as per the timeframe and accuracy clauses and should the Second Party fail in either of the said parameters, in terms of not achieving Minimum Accuracy for each stage of work as set out in the Schedule herein, or delivering the same beyond the timeframe set out elsewhere herein, it expressly undertakes that the said submission shall have to be reworked entirely and that the Second Party does not have any rights whatsoever on the said submission and expressly waives any rights thereupon.\n\n", font1));
            phrase.Add(new Chunk("\n \n  That this Agreement has been drafted upon the facts and instructions furnished and with free consent & will of both the Parties. Both the parties have read and understood the contents of this Agreement prior to the execution of the same.\n\n", font2));
             phrase.Add(new Chunk(" \n IN WITNESS, WHEREOF ", font2));
            phrase.Add(new Chunk(" the Parties have set their hand here under,\n\n", font1));
            phrase.Add(new Chunk(  indianTime().ToString("dd-MM-yyyy"), font2));
		   
		  /* phrase.Add(new Chunk("\n Contact E-Mail I.D:- " + emailid + "\n", font1));
            phrase.Add(new Chunk(" Business Associate Name:- " + name + "\n", font1));
            phrase.Add(new Chunk(" Business Associate Contact Number:- " + number + "\n", font1));
            phrase.Add(new Chunk(" Business Associate Permanent Address:- " + address + "\n\n", font1));
            phrase.Add(new Chunk("Hereby, I(Business Associate) Agree That The Above Terms And Conditions Are Totally Correct/True And I(Business Associate) Accept All Of The Above Terms And Conditions And Willing To Work With ONLINEWORKJOBS According To The Above Terms And Conditions. I(Business Associate) Am Also Providing Typing Jobs My(Business Associate) I.D Proof For The Acceptance Of The Above Points From My(Business Associate) Side. Below Is My (Business Associate) Authorized Signatory As A Confirmation For The Above Points.\n\n", font1));
            phrase.Add(new Chunk("IN WITNESS WHEREOF the parties hereto have executed these presents on the date herein before " + indianTime().ToString("dd-MM-yyyy") + " written:\n", font1));
            phrase.Add(new Chunk("\nClient: \nFor ONLINEWORKJOBS \n", font2));**/
            document.Add(phrase);
           imageURL = HttpContext.Current.Server.MapPath("~\\images") + "\\company_sign.jpg";
            jpg = iTextSharp.text.Image.GetInstance(imageURL);
            jpg.ScaleToFit(100f, 80f);
            jpg.SpacingBefore = 10f;
            jpg.SpacingAfter = 1f;
            jpg.Alignment = Element.ALIGN_LEFT;
            document.Add(jpg);
            phrase = new Phrase();
            phrase.Add(new Chunk(" PROPRIETOR \n", font2));
			phrase.Add(new Chunk(" (ON BEHALF OF YCLSL SOLUTION & SERVICES.)\n\n", font2));
            phrase.Add(new Chunk(" \n \n CUSTOMER SIGN \n", font2));
			phrase.Add(new Chunk("\n ___________________ \n", font2));
			phrase.Add(new Chunk("(Ms.)	" + name + "\n\n\n\n\n", font2));
			
            phrase.Add(new Chunk("DECLARATION / UNDERTAKING BY THE FREELANCER\n\n", font2));
			phrase.Add(new Chunk(" 1. I hereby declare that I have read the firms Policy for Use of Computer Facilities which explains the behavior expected from the freelancer and also their obligations relating to the agreement:-\n", font1));
			phrase.Add(new Chunk(" \n * Not to disclose the system password to anyone.\n", font2));
			phrase.Add(new Chunk(" * Not to leave my PC unattended. I would be personally responsible for its misuse of any nature when I am away. \n", font2));
			phrase.Add(new Chunk(" * Not to share Company's confidential information with anyone.Nor proprietary/confidential information.\n", font2));
			phrase.Add(new Chunk(" * I assure that the sign which is done digitally is done by me with full responsibility. I am liable to pay the portal charge, if I don’t complete the task within stipulated time or with desired accuracy. \n", font2));
			phrase.Add(new Chunk(" * In case of any dispute I will contact on the official email ID provided by the organization i.e.  supportjobs@ingbjob.com \n", font2));
			phrase.Add(new Chunk(" * To have my System scanned for virus once a week. \n", font2));
			phrase.Add(new Chunk(" * To take print out of mails only when absolutely necessary. \n", font2));
			phrase.Add(new Chunk(" * To always try and ensure that the attachment when required to be sent with mail share below 10MB size.\n", font2));
			phrase.Add(new Chunk(" * To always send documents in pdf format. \n", font2));
			phrase.Add(new Chunk(" * To send images, whenever required, only in JPEG/PNG format.\n", font2));
			phrase.Add(new Chunk(" * Not to use any type of software from any source at any time whatsoever.  If required for official purpose at any time, approval from IT department will be taken in writing to make sure such software are scanned properly before use, and such software will be downloaded legally and with IT department’s consensus. \n", font2));
			phrase.Add(new Chunk(" * Password given should be confidential. \n", font2));

			phrase.Add(new Chunk("\n \n 2. I fully agree and accept that it is my personal responsibility to adhere to the Company's I.T. Policy and any amendment / modification thereof and to comply with all of the provisions stated therein in true letter and spirit. I understand and accountable for any consequence or any misuse of system. I further undertake to abide by the I.T. Policy guidelines as a condition of my employment and my continuing employment in the Company. \n", font1));
			phrase.Add(new Chunk(" \n I ACCEPT ALL TERMS AND CONDITION ", font2)); 
			phrase.Add(new Chunk(" \n \n CUSTOMER SIGN \n", font2));
			phrase.Add(new Chunk("\n ___________________ \n", font2));
			phrase.Add(new Chunk("(Ms.)	" + name + "\n\n\n\n\n", font2));
			document.Add(phrase);
			imageURL = HttpContext.Current.Server.MapPath("~\\images") + "\\company_stamp.jpg";
            jpg = iTextSharp.text.Image.GetInstance(imageURL);
            jpg.ScaleToFit(100f, 80f);
            jpg.SpacingBefore = 10f;
            jpg.SpacingAfter = 1f;
            jpg.Alignment = Element.ALIGN_LEFT;
            document.Add(jpg);
			//phrase.Add(new Chunk("(Ms.)	" + name + "\n", font2));
            //document.Add(phrase);
            //=========================== Document is close and addition end =======================

            document.Close();
            byte[] bytes = memoryStream.ToArray();
            memoryStream.Close();

            System.Threading.Thread.Sleep(5000);
        }

    }
public void signgeneratepdf(string temppath, string emailid, string name, string number, string address, string page, string cid)
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

            string imageURL = HttpContext.Current.Server.MapPath("~\\stamp") + "\\" + page + ".jpeg";
            iTextSharp.text.Image jpg = iTextSharp.text.Image.GetInstance(imageURL);
            jpg.SetAbsolutePosition(0, 0);
            jpg.ScaleToFit(1080f, 820f);
            //jpg.SpacingBefore = 0;
            //jpg.SpacingAfter = 0;
            jpg.Alignment = Element.ALIGN_TOP;
            document.Add(jpg);

            phrase = new Phrase("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n", font3);
            document.Add(phrase);
            phrase = new Phrase("\n\n\n\n\n\n\n\n\n\n\n\n\n", font3);
            document.Add(phrase);
            phrase = new Phrase("\n\n\n   AGREEMENT FOR FREELANCE DATA TRANSCRIPTION\n\n", font3);
            document.Add(phrase);

            phrase = new Phrase(new Chunk("This Agreement executed ", font1));
            phrase.Add(new Chunk(indianTime().ToString("dd-MM-yyyy"), font2));
            phrase.Add(new Chunk(" between.", font1));
            phrase.Add(new Chunk("INDIA TECHNOLOGY.", font2));
            phrase.Add(new Chunk(" Having its Register Office at - ", font1));
            phrase.Add(new Chunk("Head Office: 711, SHEKHAR CENTRAL INDORE MANORAMA GANJ INDORE Madhya Pradesh India. PINCODE:-452001\n\n ", font2));
            //phrase.Add(new Chunk("F8, Yogeshwar complex, Adajan, Surat-395009 ", font2));
            //phrase.Add(new Chunk("Office no-11 dhruv complex,near maharani temple,boring road Patna,Bihar-500001 ", font2));
            //phrase.Add(new Chunk("210, 2ND FLOOR, JAINA TOWER 2, DISTRICT CENTRE, JANAKPURI WEST, DELHI -110058 ", font2));
            //phrase.Add(new Chunk("1024 Dda Market, Shastri Park, Delhi East, Delhi", font2));

            phrase.Add(new Chunk("\t\t (Herein after referred to as ‘the Party ", font1));
            phrase.Add(new Chunk(name, font2));
            phrase.Add(new Chunk("," + address + " herein after referred to as ‘the Party of the Second Part) .\n\n", font1));
            phrase.Add(new Chunk("WHEREAS", font2));
            phrase.Add(new Chunk("the Party of the First Part is engaged mainly in Outsourcing of IT enabled Services and To deliver Data Entry and Transcription and allied Activities and Other Ancillary Activities Associated there with an Organization engaged in providing data to you end clients and  data entry related line of work. And executing such work Outsourced, through Delivery Partners.\n\n", font1));
            phrase.Add(new Chunk("AND WHEREAS", font2));
            phrase.Add(new Chunk("the Party of the First Part is bound by time schedule set by the Delivery Partners and that its reputation is built upon speedy and accurate transcription and requires the said party to deliver accomplished work within shortest span and with desired accuracy the First Party has entered with a firm launching its new BANKING  PORTAL and has represented itself that it has an expertise in the area of providing MARKETING (Banking)/ Presently it is in a position to procure the business for form filling more meaningfully described in the column Scope of Work, through their principals.AND WHEREAS the Business Associate is engaged inter alias, in the business of providing a wide Spectrum of software solutions & services. The Business Associate has acquired the necessary expertise and developed the requisite skill base and Infrastructure for successful execution of Form Filling Projects.\n\n", font1));
            phrase.Add(new Chunk("Whereas, the Second Party is an individual and a Freelancer who is willing to provide its services to the Job Portal Company, via medium of First Party in relation with IT &all data related work which is to be provided by the First Party\n\n", font1));
            phrase.Add(new Chunk("This Agreement represents the business Agreement and operational understandings between the parties and shall remain in effect for a period of 30 DAYS from the date of execution hereof or from the date of providing the first data whichever is later & can be extended for the period as mutually agreed upon, for the purpose.\n", font1));
            document.Add(phrase);
            imageURL = HttpContext.Current.Server.MapPath("~\\images") + "\\company_stamp.jpg";
            jpg = iTextSharp.text.Image.GetInstance(imageURL);
            jpg.ScaleToFit(100f, 80f);
            jpg.SpacingBefore = 10f;
            jpg.SpacingAfter = 1f;
            jpg.Alignment = Element.ALIGN_LEFT;
            document.Add(jpg);

            phrase = new Phrase("\n \n\n \n                                NOW THIS AGREEMENT WITNESSETH AS FOLLOWS:\n\n", font2);
            phrase.Add(new Chunk("It is hereby agreed between the Parties as under:\n\n", font2));
            document.Add(phrase);

            phrase = new Phrase();
            phrase.Add(new Chunk("1.1 That both the Parties has decided with sweet will and free consent to work together for gains.\n\n", font1));
            phrase.Add(new Chunk("1.2 The purpose of Parties behind this Agreement is to work for gain in relation to the Freelance services. \n\n", font1));
            phrase.Add(new Chunk("1.3. That the First Party is Ruling out on survey for banking purpose digitally/non digitally /Free Lancers and is focused to provide its services to the Partners/Associates (M/s INGBJOB.):\n\n", font1));
            phrase.Add(new Chunk("2. Scope of Work:", font2));
            phrase.Add(new Chunk(" The original data will be available on the work environment software provided by INGB JOBS at the time of signup. Business associate are required to feed the provided data in the provided software as per the guidelines. Data supply and preservation of the output file is done online on real time basis. the party of the first part is collecting is collecting data for banking formalities of our end clients for that they are ruling out on survey for the data by typing in company’s portal the employee of the YCLSL SOLUTION AND SERVICES are also working on this project those person are qualified in their work and fulfill the company requirement the company will pay for them according to terms & condition.\n\n", font1));
            phrase.Add(new Chunk(" \t \t The data surveying duration will be for one year from 2021 to 2022.2.1 \n\n", font1));
            phrase.Add(new Chunk(" 2.1) The First Party shall provide details of the FORMS through the login credentials shared through SMS Or Email.\n\n", font1));
            phrase.Add(new Chunk("2.2 The Second Party further Represents to the First Party, the time for the Completion of the said data entry related services as mentioned in this Agreement, shall Commence Immediately upon logging on the portal OR if the Commencement Date is mentioned in the said Communication, from such date, and it shall Continue to Access its said Portal/E-Mail as provided in the Records of the First Party, as frequently as necessary for the said Purpose..\n\n", font1));
            phrase.Add(new Chunk(" That the Second Party agrees to pay Rs. 6000 as charges for membership, Portal charges, GST and other applicable charges in case of failure to submit complete workload or to provide workload on time with desired accuracy. This membership will include Jobs Vacancy information in Pan India through our Social Media platform.\n\n", font1));
            phrase.Add(new Chunk("2.3 That in lieu/consideration of the above Fees/charges, the first party will provide agreement which will be valid for 1 month but project duration will be 5 days as mentioned. It also pertinent to mention here that one project will contain 500 FORMS in one project .\n\n", font1));
            phrase.Add(new Chunk("2.4 That the First Party will give 500 FORMS in PDF or any other image format on the Company’s Portal. On the Portal itself, the details of the work of data processing are provided, which will clearly mention the details, as in what & how is to be processed.\n\n", font1));
            phrase.Add(new Chunk("\t \t  a) Payment to be made maximum within 5 days of each calendar month, from the QC report, which will be given usually within 5 days of submission of the work. \n", font1));
            phrase.Add(new Chunk(" \t \t b) That the First party gets all these FORMS from INGBJOB (END CLIENTS).\n\n", font1));
            phrase.Add(new Chunk(" \t \t c)  In case of any dispute second party must contact to the first party and if they are unable to resolve their problem, they can proceed legally. Second party can communicate through SUPPORT@INGBPROJECTION.COM  or on customer care numbers provided.\n\n", font1));
            phrase.Add(new Chunk("3. Plan Details:", font2));
            phrase.Add(new Chunk(" Second party will get 500 forms for 5 days. Per form rate will be Rs. 35/-.", font1));
            phrase.Add(new Chunk("(a). No initial payment is required to be given by second party.", font1));
            phrase.Add(new Chunk("(b). After getting the accuracy report of having 90% above accuracy, your payment will be processed within 7 international working days in to your respective bank account. An accurate form is that which doesn't have any error such as spelling/punctuation/extra space/extra text/missing text.", font1));
            phrase.Add(new Chunk("(c). In the matter of failure, non-submission, accuracy below 90% then company is entitled to receive amount of Rs. 6000* by any cost from the second party. If in case second party uses multiple login then penalty will be Rs. 999/-. If second party passes and achieves the accuracy of 90% or above, then amount will be deducted from his work payment and remaining shall be paid.", font1));
            phrase.Add(new Chunk("(d). The charge of Rs. 6000* is related to service, development and maintenance cost of the platform where he is working online.", font1));
            phrase.Add(new Chunk("      \n \n Technical clause:\n", font2));
            phrase.Add(new Chunk("• Helpline department will support you in only 10% queries from the whole project.\n", font1));
            phrase.Add(new Chunk("• For example: if you have taken the 500 pages/forms plan, then helpline dept. is liable to give reply only 55 pages/forms queries of 10% of whole project.\n", font1));
            phrase.Add(new Chunk("• Work will automatically get Submit in 48 hours.\n", font1));
            phrase.Add(new Chunk("• No use of any shortcut keys while typing in terminal else you will be responsible for the same.\n\n", font1));
            phrase.Add(new Chunk("4. TIMEFRAME FOR COMPLETION OF TRANSCRIPTION:", font2));
            phrase.Add(new Chunk(" The Second Party shall complete the services of the said Data entry work in Seven (5) days TAT period, i.e., maximum 500 FORMS can be completed within a period of 5 days. The Second Party alone shall be responsible for the maintenance of Hardware and Personnel for such timely services and no excuse of whatsoever Nature shall be entertained for delay in Supply of services, since Time is the Essence of this Contract.\n\n", font1));
            phrase.Add(new Chunk("5. DURATION OF THE CONTRACT:", font2));
            phrase.Add(new Chunk(" The Present Contract shall be in force for 1 month membership. The said Contract shall come to an End at the Expiry of the said Period and may be renewed by Mutual Consent and on such Revised Terms agreed between the Parties and on Payment of Processing Charges for another Project by the Second Party.\n\n", font1));
            phrase.Add(new Chunk(" ID Allocation:", font2));
            phrase.Add(new Chunk("Business Associate will get single id to work on and Business Associate can work 24X7 on this id.\n", font1));
            phrase.Add(new Chunk(" TAT (Turn Around Time):", font2));
            phrase.Add(new Chunk(" Turn around time for completing the project is mentioned in the schedule. The Business Associate through this agreement guarantees the delivery of work within stipulated timeframe with desired accuracy.\n\n", font1));
            phrase.Add(new Chunk("6. SERVICE CHARGE:", font2));
            phrase.Add(new Chunk(" If Business Associate fails to fulfill terms and conditions mentioned by Client, then Business Associate have to compulsory pay penalty amount of 3999* to stop legal proceedings within 12 hours. In the matter of fact failure, not submitted the Client is entitled to receive penalty amount by any cost. If Business Associate achieves accuracy then Business Associate will not be liable to pay the penalty amount. If Business Associate fails in achieving accuracy, then Business Associate has to pay the penalty according to the selected plan as a liability.\n\n", font1));
            phrase.Add(new Chunk(" WHY SERVICE CHARGES?\n", font2));
            phrase.Add(new Chunk(" - We offer 24*7 helpline options on website.\n", font1));
            phrase.Add(new Chunk(" - We offer day time customer care call support.\n", font1));
            phrase.Add(new Chunk(" - Email support\n", font1));
            phrase.Add(new Chunk(" - Job consultation charges.\n", font1));
            phrase.Add(new Chunk(" - Stamp paper and agreement preparation charges.\n", font1));
            phrase.Add(new Chunk(" - Charges will be deducted from the payment once accuracy is achieved.\n\n", font2));
            phrase.Add(new Chunk("7. Confidentiality :\n", font2));
            phrase.Add(new Chunk(" a) 'Confidential Information' refers in this Agreement to any information - technical, commercial or of any other nature (including any information regarding the identity of a customer of First party and all otherinformation attributable to the customer’s business or systems) - regardless of whether or not such information has been documented, with the exception of information that is or becomes publicly known other than by the Second Party’s breach of the provisions of This Agreement.\n\n", font1));
            phrase.Add(new Chunk(" b) The Second party undertakes not to use Confidential Information or other information, such as software, etc., obtained within the scope of this Agreement for any other purpose or in any other context than to carry out its specific assignments under this Agreement. Furthermore, the Second Party is prohibited from using Confidential Information obtained within one specific assignment under this Agreement in order to carry out another specific assignment under this Agreement, unless otherwise expressly agreed within the scope of the latter assignment.\n\n", font1));
            phrase.Add(new Chunk(" c) The Second Party undertakes under this Section that this shall also apply to the Second Party’s employees, Associates and consultants. The Second party shall ensure that such employees or consultants that are likely to come in contact with Confidential Information sign separate /confidentiality undertakings on the same terms and condition.\n\n", font1));
            phrase.Add(new Chunk("8. TERMS OF PAYMENT AND COMPENSATION: \n\n", font2));
            phrase.Add(new Chunk(" The Payment Terms for each of the Plans shall be as Under:\n\n", font1));
            phrase.Add(new Chunk(" The payment for every FORM will be INR 35 but achieving 90% accuracy is compulsory for this payment and compensation. If you will achieve accuracy below 90% then INR 5 will be given per accurate FORMS but completion of project is mandatory.\n\n", font1));
            phrase.Add(new Chunk(" The Entire such Payment Payable by the First Party to the Second Party, shall be made within maximum 5 days of the Receipt of the Accuracy Report.\n\n ", font1));
            phrase.Add(new Chunk(" In each plan the payment shall be made only for the accurate data processing of FORMS. Any Inaccurate data processing will not qualify for the payment regardless of number of errors found in that page, more or less \n\n", font1));
            phrase.Add(new Chunk("9.DETERMINATION OF ACCURACY:\n ", font2));
            phrase.Add(new Chunk(" The accuracy will be determined per data processing of FORMS. If any Mistake is found such as spelling error, Punctuation error, Extra Word, Missing word, Extra Space, Space Missing, Extra Enter or Enter Missing, then that form will be considered as Inaccurate FORMS and hence if accuracy falls below 90% then no payment shall be processed. The Test of the Accuracy shall be made by a determining Centre appointed by the Party of the First Party’s Associate/Partner (FORMS filling) and the Report of such Accuracy subject to procedure outlined below, would be Final and Conclusive, with no room for Disputing the Veracity of the same by the Party of the Second Part.\n\n", font1));
            phrase.Add(new Chunk("\n 10. PROCEDURE FOR GENERATION OF ACCURACY REPORT :\n\n", font2));
            phrase.Add(new Chunk("The Determining Centre personnel shall check all the data processing of FORMS. After an error is found in a particular FORM the Centre personnel shall list that as inaccurate and start checking the next FORM. All the errors in the whole FORM will not be shown in the Accuracy Report.Once all data processing of FORM are checked, the final Accuracy Report shall be generated.\n\n ", font1));
            phrase.Add(new Chunk("11. TECHNICAL SPECIFICATIONS FOR DATA RELATED WORK:\n\n", font2));
            phrase.Add(new Chunk(" The Font used in Data shall be ‘default font of portal’ Size default, irrespective of the Font used in the IMAGE.\n", font1));
            phrase.Add(new Chunk(" No ‘Justification’ of Transcribed Text shall be made.\n", font1));
            phrase.Add(new Chunk("The Data shall be an Exact Replica of the FORMS in terms of a Split in a Word or the End of a line.\n", font1));
            phrase.Add(new Chunk("Accent Characters shall be typed as Normal Characters.\n", font1));
            phrase.Add(new Chunk(" Transcribed text shall be in its normal Style – ‘Bold’ or ‘Italics’ shall not be used.\n", font1));
            phrase.Add(new Chunk(" Shortcut keys and the character mapping should not be used.+\n", font1));
            phrase.Add(new Chunk("All the fields in the forms should be typed, any field should not be left blank.\n", font1));
            phrase.Add(new Chunk(" NO grammatical rules should be applied. \n", font1));
            phrase.Add(new Chunk(" \t Give one space between two words if applicable in the data.\n\n", font1));
            phrase.Add(new Chunk("12. Severability:\n", font2));
            phrase.Add(new Chunk(" The various provisions of this “Freelance services Contract” are severable and if any provision is held to be invalid or unenforceable by any court of competent jurisdiction then such invalidity or unenforceability shall not affect the remaining provisions of this Services Agreement. A FORM will not be edited after 48 hours of submission on the portal. It will be locked till the submission date.\n\n", font1));
            phrase.Add(new Chunk("13. Dispute Resolution, Governing Law and Arbitration:", font2));
            phrase.Add(new Chunk(" a) This Agreement shall be governed by laws of India. Any dispute arising in relation to this Agreement shall first be resolved through amicable way i.e. amicable talks and then arbitration under the Arbitration & Conciliation Act, 1996.\n\n", font1));
            phrase.Add(new Chunk(" b) The First Party shall notify an Arbitrator to the Second Party. Provided that none of such arbitrators shall have represented or had a business connection with the First Party previously.\n", font1));
            phrase.Add(new Chunk(" c) The arbitration shall be held in Indore Madhya Pradesh and conducted in English language. Every order of the arbitrator shall be justified by reasons in writing.\n", font1));
            phrase.Add(new Chunk(" d) Notwithstanding the foregoing, the First Party shall be entitled to obtain such injunctive or equitable relief as may be necessary by any court of competent jurisdiction including any court having jurisdiction over a place where the Second Party is having presence.\n", font1));
            phrase.Add(new Chunk("\n\n 14. WAIVER:\n\n", font2));
            phrase.Add(new Chunk(" The Second Party expressly undertakes to submit as per the timeframe and accuracy clauses and should the Second Party fail in either of the said parameters, in terms of not achieving Minimum Accuracy for each stage of work as set out in the Schedule herein, or delivering the same beyond the timeframe set out elsewhere herein, it expressly undertakes that the said submission shall have to be reworked entirely and that the Second Party does not have any rights whatsoever on the said submission and expressly waives any rights thereupon.\n\n", font1));
            phrase.Add(new Chunk("\n \n  That this Agreement has been drafted upon the facts and instructions furnished and with free consent & will of both the Parties. Both the parties have read and understood the contents of this Agreement prior to the execution of the same.\n\n", font2));
            phrase.Add(new Chunk(" \n IN WITNESS, WHEREOF ", font2));
            phrase.Add(new Chunk(" the Parties have set their hand here under,\n\n", font1));
            phrase.Add(new Chunk(indianTime().ToString("dd-MM-yyyy"), font2));

            /* phrase.Add(new Chunk("\n Contact E-Mail I.D:- " + emailid + "\n", font1));
              phrase.Add(new Chunk(" Business Associate Name:- " + name + "\n", font1));
              phrase.Add(new Chunk(" Business Associate Contact Number:- " + number + "\n", font1));
              phrase.Add(new Chunk(" Business Associate Permanent Address:- " + address + "\n\n", font1));
              phrase.Add(new Chunk("Hereby, I(Business Associate) Agree That The Above Terms And Conditions Are Totally Correct/True And I(Business Associate) Accept All Of The Above Terms And Conditions And Willing To Work With ONLINEWORKJOBS According To The Above Terms And Conditions. I(Business Associate) Am Also Providing Typing Jobs My(Business Associate) I.D Proof For The Acceptance Of The Above Points From My(Business Associate) Side. Below Is My (Business Associate) Authorized Signatory As A Confirmation For The Above Points.\n\n", font1));
              phrase.Add(new Chunk("IN WITNESS WHEREOF the parties hereto have executed these presents on the date herein before " + indianTime().ToString("dd-MM-yyyy") + " written:\n", font1));
              phrase.Add(new Chunk("\nClient: \nFor ONLINEWORKJOBS \n", font2));**/
            document.Add(phrase);
            imageURL = HttpContext.Current.Server.MapPath("~\\images") + "\\company_sign.jpg";
            jpg = iTextSharp.text.Image.GetInstance(imageURL);
            jpg.ScaleToFit(100f, 80f);
            jpg.SpacingBefore = 10f;
            jpg.SpacingAfter = 1f;
            jpg.Alignment = Element.ALIGN_LEFT;
            document.Add(jpg);
            phrase = new Phrase();
            phrase.Add(new Chunk(" PROPRIETOR \n", font2));
            phrase.Add(new Chunk(" (ON BEHALF OF YCLSL SOLUTION & SERVICES.)\n\n", font2));
            phrase.Add(new Chunk(" \n \n CUSTOMER SIGN \n\n", font2));

            document.Add(phrase);
            imageURL = HttpContext.Current.Server.MapPath("~\\sign") + "\\" + cid + ".jpg";
            jpg = iTextSharp.text.Image.GetInstance(imageURL);
            jpg.ScaleToFit(100f, 80f);
            jpg.SpacingBefore = 10f;
            jpg.SpacingAfter = 1f;
            jpg.Alignment = Element.ALIGN_LEFT;
            document.Add(jpg);
            phrase = new Phrase();

            phrase.Add(new Chunk(" ___________________ \n", font2));
            phrase.Add(new Chunk("(Ms.)	" + name + "\n\n\n", font2));

            phrase.Add(new Chunk("DECLARATION / UNDERTAKING BY THE FREELANCER\n\n", font2));
            phrase.Add(new Chunk(" 1. I hereby declare that I have read the firms Policy for Use of Computer Facilities which explains the behavior expected from the freelancer and also their obligations relating to the agreement:-\n", font1));
            phrase.Add(new Chunk(" \n * Not to disclose the system password to anyone.\n", font2));
            phrase.Add(new Chunk(" * Not to leave my PC unattended. I would be personally responsible for its misuse of any nature when I am away. \n", font2));
            phrase.Add(new Chunk(" * Not to share Company's confidential information with anyone.Nor proprietary/confidential information.\n", font2));
            phrase.Add(new Chunk(" * I assure that the sign which is done digitally is done by me with full responsibility. I am liable to pay the portal charge, if I don’t complete the task within stipulated time or with desired accuracy. \n", font2));
            phrase.Add(new Chunk(" * In case of any dispute I will contact on the official email ID provided by the organization i.e.  supportjobs@ingbjob.com \n", font2));
            phrase.Add(new Chunk(" * To have my System scanned for virus once a week. \n", font2));
            phrase.Add(new Chunk(" * To take print out of mails only when absolutely necessary. \n", font2));
            phrase.Add(new Chunk(" * To always try and ensure that the attachment when required to be sent with mail share below 10MB size.\n", font2));
            phrase.Add(new Chunk(" * To always send documents in pdf format. \n", font2));
            phrase.Add(new Chunk(" * To send images, whenever required, only in JPEG/PNG format.\n", font2));
            phrase.Add(new Chunk(" * Not to use any type of software from any source at any time whatsoever.  If required for official purpose at any time, approval from IT department will be taken in writing to make sure such software are scanned properly before use, and such software will be downloaded legally and with IT department’s consensus. \n", font2));
            phrase.Add(new Chunk(" * Password given should be confidential. \n", font2));

            phrase.Add(new Chunk("\n \n 2. I fully agree and accept that it is my personal responsibility to adhere to the Company's I.T. Policy and any amendment / modification thereof and to comply with all of the provisions stated therein in true letter and spirit. I understand and accountable for any consequence or any misuse of system. I further undertake to abide by the I.T. Policy guidelines as a condition of my employment and my continuing employment in the Company. \n", font1));
            phrase.Add(new Chunk(" \n I ACCEPT ALL TERMS AND CONDITION ", font2));
            phrase.Add(new Chunk(" \n \n CUSTOMER SIGN \n\n", font2));

            document.Add(phrase);
            imageURL = HttpContext.Current.Server.MapPath("~\\sign") + "\\"+ cid + ".jpg";
            jpg = iTextSharp.text.Image.GetInstance(imageURL);
            jpg.ScaleToFit(100f, 80f);
            jpg.SpacingBefore = 10f;
            jpg.SpacingAfter = 1f;
            jpg.Alignment = Element.ALIGN_LEFT;
            document.Add(jpg);

            phrase = new Phrase();
            phrase.Add(new Chunk(" ___________________ \n", font2));
            phrase.Add(new Chunk("(Ms.)	" + name + "\n\n", font2));
            document.Add(phrase);
            imageURL = HttpContext.Current.Server.MapPath("~\\images") + "\\company_stamp.jpg";
            jpg = iTextSharp.text.Image.GetInstance(imageURL);
            jpg.ScaleToFit(100f, 80f);
            jpg.SpacingBefore = 10f;
            jpg.SpacingAfter = 1f;
            jpg.Alignment = Element.ALIGN_LEFT;
            document.Add(jpg);
            //phrase.Add(new Chunk("(Ms.)	" + name + "\n", font2));
            //document.Add(phrase);
            //=========================== Document is close and addition end =======================

            document.Close();
            byte[] bytes = memoryStream.ToArray();
            memoryStream.Close();

            System.Threading.Thread.Sleep(5000);
        }

    }
    public void autofill(string cid, int from, int to, int per)
    {
        DataTable dt = new DataTable();
        DataTable dt1 = new DataTable();
        DataTable dt2 = new DataTable();
        Random rand = new Random();
        clsAdmin AD = new clsAdmin();
        int total, perform, remain;
        string[] random_txtbox = { "Tbc_No", "Name", "EmailId", "GirNo", "LicenseNo", "H_Address", "PanNo", "O_Address", "O_City", "H_City" };

        string planid = AdExecScalar("select planid from tbl_registration with(nolock) where cid='" + cid + "'");

        dt = FillDataTable("select [index],id,date from tbl_client_bpo_data with(nolock) where cid='" + cid + "' and sr_no>=" + from + " and sr_no<=" + to + " and (date>='" + indianTime().ToString("yyyy-MM-dd hh:mm:ss tt") + "' or date is null ) order by id");
        dt1 = FillDataTable("select * from tbl_bpo_data with(nolock) where id in (select id from tbl_client_bpo_data with(nolock) where cid='" + cid + "' and sr_no>=" + from + " and sr_no<=" + to + " and (date>='" + indianTime().ToString("yyyy-MM-dd hh:mm:ss tt") + "' or date is null )) order by id");
        for (int i = 0; i < dt.Rows.Count; i++)
        {

            ExecutQury(@"update Tbl_Client_Bpo_Data set Tbc_No=@0,Name=@1,EmailId=@2,MobileNo=@3,Gender=@4,LicenseNo=@5,GirNo=@6,PanNo=@7,H_Address=@8,H_City=@9,H_PinNo=@10,
                                        H_State=@11,O_Address=@12,O_City=@13,O_PinNo=@14,LAL=@15,MRNNo=@16,AF=@17,NRI=@18,CP=@19,
                                        Status=@20,spaceerror=@21,date=@22  where [index]=@23", dt1.Rows[i]["Tbc_No"].ToString(), dt1.Rows[i]["Name"].ToString(), dt1.Rows[i]["EmailId"].ToString(),
                                dt1.Rows[i]["MobileNo"].ToString(), dt1.Rows[i]["Gender"].ToString(), dt1.Rows[i]["LicenseNo"].ToString(), dt1.Rows[i]["GirNo"].ToString(),
                                dt1.Rows[i]["PanNo"].ToString(), dt1.Rows[i]["H_Address"].ToString(), dt1.Rows[i]["H_City"].ToString(), dt1.Rows[i]["H_PinNo"].ToString(),
                                dt1.Rows[i]["H_State"].ToString(), dt1.Rows[i]["O_Address"].ToString(), dt1.Rows[i]["O_City"].ToString(), dt1.Rows[i]["O_PinNo"].ToString(),
                                dt1.Rows[i]["LAL"].ToString(), dt1.Rows[i]["MRNNo"].ToString(), dt1.Rows[i]["AF"].ToString(), dt1.Rows[i]["NRI"].ToString(),
                                dt1.Rows[i]["CP"].ToString(), "2", "0", indianTime().AddDays(2).ToString("yyyy-MM-dd hh:mm:ss tt"), dt.Rows[i]["index"].ToString());
        }

        total = dt.Rows.Count;
        perform = (total * per) / 100;
        remain = total - perform;
        dt2 = FillDataTable("SELECT TOP (" + remain + ")* FROM Tbl_client_Bpo_Data with(nolock) where cid='" + cid + "' and sr_no>=" + from + " and sr_no<=" + to + " and (date>='" + indianTime().ToString("yyyy-MM-dd hh:mm:ss tt") + "' or date is null ) ORDER BY newid() ");
        for (int i = 0; i < remain; i++)
        {
            string txt_box = random_txtbox[rand.Next(0, random_txtbox.Length)];
            string value = dt2.Rows[i][txt_box].ToString();
            int change = 0;
            if (value.Contains('W'))
            {
                value = value.Replace('W', 'w');
            }
            else if (value.Contains('V'))
            {
                value = value.Replace('V', 'v');
            }
            else if (value.Contains('m'))
            {
                value = value.Replace('m', 'n');
            }
            else if (value.Contains('s'))
            {
                value = value.Replace("s", "");
            }
            else if (value.Contains('n'))
            {
                value = value.Replace("n", "");
            }
            else if (value.Contains('d'))
            {
                value = value.Replace("d", "s");
            }
            else if (value.Contains('.'))
            {
                value = value.Replace(".", ",");
            }
            else if (value.Contains('y'))
            {
                value = value.Replace("y", "u");
            }
            else if (value.Contains('u'))
            {
                value = value.Replace("u", "y");
            }
            else if (value.Contains('z'))
            {
                value = value.Replace("z", "x");
            }
            else if (value.Contains('x'))
            {
                value = value.Replace("x", "z");
            }
            else if (value.Contains('k'))
            {
                value = value.Replace("k", "l");
            }
            else if (value.Contains('t'))
            {
                value = value.Replace("t", "tt");
            }
            else if (value.Contains('2'))
            {
                value = value.Replace("2", "7");
            }
            else if (value.Contains('7'))
            {
                value = value.Replace("7", "2");
            }
            else if (value.Contains('?'))
            {
                value = value.Replace("?", "2");
            }
            else if (value.Contains('g'))
            {
                value = value.Replace("g", "h");
            }
            else if (value.Contains('h'))
            {
                value = value.Replace("h", "g");
            }
            else if (value.Contains("om"))
            {
                value = value.Replace("om", "mo");
            }
            else if (value.Contains("of"))
            {
                value = value.Replace("of", "fo");
            }
            else if (value.Contains(".1"))
            {
                value = value.Replace(".1", "1.");
            }
            else if (value.Contains("12"))
            {
                value = value.Replace("12", "21");
            }
            else if (value.Contains("01"))
            {
                value = value.Replace("01", "10");
            }
            else if (value.Contains("0"))
            {
                value = value.Replace("0", "00");
            }
            else if (value.Contains("-1"))
            {
                value = value.Replace("-1", "1-");
            }
            else
            {
                change = 1;
            }
            if (change == 0)
            {
                ExecutQury("update tbl_client_bpo_data set " + txt_box + "=@0,status='3' where [index]=" + dt2.Rows[i]["index"].ToString() + "", value);
            }
        }
        AD.cid = cid;
        AD.Work = "##############################################";
        AD.Insert_Log();
        //insertlog(AD.cid, AD.Work);
    }

}
