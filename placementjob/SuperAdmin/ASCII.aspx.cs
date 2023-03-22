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
using System.Text;

public partial class Customer_ASCII : System.Web.UI.Page
{
    clsFranchisee CF = new clsFranchisee();
    string id;
    string Fstring;
    clsAdmin AD = new clsAdmin();
    DataSet DS = new DataSet();
    ASCIIEncoding ascii = new ASCIIEncoding();


    protected void Page_Load(object sender, EventArgs e)
    {

        if (Session["Ccustomer_Cid"] == null)
        {
            Response.Redirect("../Default.aspx");
        }
        try
        {

            AD.id = Session["id"].ToString();
            AD.cid = Session["Ccustomer_Cid"].ToString();

            DataSet dsClient = AD.Get_Data_From_CID_SRNO();

            Tbc_No.Text = dsClient.Tables[0].Rows[0]["Tbc_No"].ToString();
            Tbc_No_Ascii.Text = "";

            byte[] a = ascii.GetBytes(Tbc_No.Text);
            for (int i = 0; i < a.Length; i++)
            {
                Tbc_No_Ascii.Text += a[i].ToString()+ " ";
            }

            Name.Text = dsClient.Tables[0].Rows[0]["Name"].ToString();
            Name_ASCII.Text = "";

            a = ascii.GetBytes(Name.Text);
            for (int i = 0; i < a.Length; i++)
            {
                Name_ASCII.Text += a[i].ToString() + " ";
            }


            EmailID.Text = dsClient.Tables[0].Rows[0]["EmailId"].ToString();

            a = ascii.GetBytes(EmailID.Text);
            for (int i = 0; i < a.Length; i++)
            {
                EmailID_ASCII.Text += a[i].ToString() + " ";
            }

            Mobile_No.Text = dsClient.Tables[0].Rows[0]["MobileNo"].ToString();
            a = ascii.GetBytes(Mobile_No.Text);
            for (int i = 0; i < a.Length; i++)
            {
                Mobile_No_ASCII.Text += a[i].ToString() + " ";
            }


            Gender.Text = dsClient.Tables[0].Rows[0]["Gender"].ToString();

            a = ascii.GetBytes(Gender.Text);
            for (int i = 0; i < a.Length; i++)
            {
                Gender_ASCII.Text += a[i].ToString() + " ";
            }

            License_No.Text = dsClient.Tables[0].Rows[0]["LicenseNo"].ToString();
            a = ascii.GetBytes(License_No.Text);
            for (int i = 0; i < a.Length; i++)
            {
                License_No_ASCII.Text += a[i].ToString() + " ";
            }

            Gir_No.Text = dsClient.Tables[0].Rows[0]["GirNo"].ToString();
            a = ascii.GetBytes(Gir_No.Text);
            for (int i = 0; i < a.Length; i++)
            {
                Gir_No_ASCII.Text += a[i].ToString() + " ";
            }

            Pan_No.Text = dsClient.Tables[0].Rows[0]["PanNo"].ToString();
            a = ascii.GetBytes(Pan_No.Text);
            for (int i = 0; i < a.Length; i++)
            {
                Pan_No_ASCII.Text += a[i].ToString() + " ";
            }

            H_Address.Text = dsClient.Tables[0].Rows[0]["H_Address"].ToString();
            a = ascii.GetBytes(H_Address.Text);
            for (int i = 0; i < a.Length; i++)
            {
                H_Address_ASCII.Text += a[i].ToString() + " ";
            }
            H_City.Text = dsClient.Tables[0].Rows[0]["H_City"].ToString();
            a = ascii.GetBytes(H_City.Text);
            for (int i = 0; i < a.Length; i++)
            {
                H_City_ASCII.Text += a[i].ToString() + " ";
            }
            H_Pincode.Text = dsClient.Tables[0].Rows[0]["H_PinNo"].ToString();
            a = ascii.GetBytes(H_Pincode.Text);
            for (int i = 0; i < a.Length; i++)
            {
                H_Pincode_ASCII.Text += a[i].ToString() + " ";
            }
            H_State.Text = dsClient.Tables[0].Rows[0]["H_State"].ToString();
            a = ascii.GetBytes(H_State.Text);
            for (int i = 0; i < a.Length; i++)
            {
                H_State_ASCII.Text += a[i].ToString() + " ";
            }
            O_Address.Text = dsClient.Tables[0].Rows[0]["O_Address"].ToString();
            a = ascii.GetBytes(O_Address.Text);
            for (int i = 0; i < a.Length; i++)
            {
                O_Address_ASCII.Text += a[i].ToString() + " ";
            }
            O_City.Text = dsClient.Tables[0].Rows[0]["O_City"].ToString();
            a = ascii.GetBytes(O_City.Text);
            for (int i = 0; i < a.Length; i++)
            {
                O_City_ASCII.Text += a[i].ToString() + " ";
            }
            O_Pinno.Text = dsClient.Tables[0].Rows[0]["O_PinNo"].ToString();
            a = ascii.GetBytes(O_Pinno.Text);
            for (int i = 0; i < a.Length; i++)
            {
                O_Pinno_ASCII.Text += a[i].ToString() + " ";
            }
            LAL.Text = dsClient.Tables[0].Rows[0]["LAL"].ToString();
            a = ascii.GetBytes(LAL.Text);
            for (int i = 0; i < a.Length; i++)
            {
                LAL_ASCII.Text += a[i].ToString() + " ";
            }
            Mrn_No.Text = dsClient.Tables[0].Rows[0]["MRNNo"].ToString();
            a = ascii.GetBytes(Mrn_No.Text);
            for (int i = 0; i < a.Length; i++)
            {
                Mrn_No_ASCII.Text += a[i].ToString() + " ";
            }
            AF.Text = dsClient.Tables[0].Rows[0]["AF"].ToString();
            a = ascii.GetBytes(AF.Text);
            for (int i = 0; i < a.Length; i++)
            {
                AF_ASCII.Text += a[i].ToString() + " ";
            }
            Nri.Text = dsClient.Tables[0].Rows[0]["NRI"].ToString();
            a = ascii.GetBytes(Nri.Text);
            for (int i = 0; i < a.Length; i++)
            {
                Nri_ASCII.Text += a[i].ToString() + " ";
            }
            CP.Text = dsClient.Tables[0].Rows[0]["CP"].ToString();
            a = ascii.GetBytes(CP.Text);
            for (int i = 0; i < a.Length; i++)
            {
                CP_ASCII.Text += a[i].ToString() + " ";
            }

///////////////////////////////////////////////////////////////////////////

            CF.CId = dsClient.Tables[0].Rows[0]["Id"].ToString();
            CF.Name = Session["Ccustomer_Cid"].ToString();
            dsClient = CF.Get_Data_From_CID_bpo();


            Tbc_No_.Text = dsClient.Tables[0].Rows[0]["Tbc_No"].ToString();
            Tbc_No_Ascii_.Text = "";
            a = ascii.GetBytes(Tbc_No_.Text);
            for (int i = 0; i < a.Length; i++)
            {
                Tbc_No_Ascii_.Text += a[i].ToString() + " ";
            }

            Name_.Text = dsClient.Tables[0].Rows[0]["Name"].ToString();
            Name_ASCII_.Text = "";

            a = ascii.GetBytes(Name_.Text);
            for (int i = 0; i < a.Length; i++)
            {
                Name_ASCII_.Text += a[i].ToString() + " ";
            }


            EmailID_.Text = dsClient.Tables[0].Rows[0]["EmailId"].ToString();

            a = ascii.GetBytes(EmailID_.Text);
            for (int i = 0; i < a.Length; i++)
            {
                EmailID_ASCII_.Text += a[i].ToString() + " ";
            }

            Mobile_No_.Text = dsClient.Tables[0].Rows[0]["MobileNo"].ToString();
            a = ascii.GetBytes(Mobile_No_.Text);
            for (int i = 0; i < a.Length; i++)
            {
                Mobile_No_ASCII_.Text += a[i].ToString() + " ";
            }


            Gender_.Text = dsClient.Tables[0].Rows[0]["Gender"].ToString();

            a = ascii.GetBytes(Gender_.Text);
            for (int i = 0; i < a.Length; i++)
            {
                Gender_ASCII_.Text += a[i].ToString() + " ";
            }

            License_No_.Text = dsClient.Tables[0].Rows[0]["LicenseNo"].ToString();
            a = ascii.GetBytes(License_No_.Text);
            for (int i = 0; i < a.Length; i++)
            {
                License_No_ASCII_.Text += a[i].ToString() + " ";
            }

            Gir_No_.Text = dsClient.Tables[0].Rows[0]["GirNo"].ToString();
            a = ascii.GetBytes(Gir_No_.Text);
            for (int i = 0; i < a.Length; i++)
            {
                Gir_No_ASCII_.Text += a[i].ToString() + " ";
            }

            Pan_No_.Text = dsClient.Tables[0].Rows[0]["PanNo"].ToString();
            a = ascii.GetBytes(Pan_No_.Text);
            for (int i = 0; i < a.Length; i++)
            {
                Pan_No_ASCII_.Text += a[i].ToString() + " ";
            }

            H_Address_.Text = dsClient.Tables[0].Rows[0]["H_Address"].ToString();
            a = ascii.GetBytes(H_Address_.Text);
            for (int i = 0; i < a.Length; i++)
            {
                H_Address_ASCII_.Text += a[i].ToString() + " ";
            }
            H_City_.Text = dsClient.Tables[0].Rows[0]["H_City"].ToString();
            a = ascii.GetBytes(H_City_.Text);
            for (int i = 0; i < a.Length; i++)
            {
                H_City_ASCII_.Text += a[i].ToString() + " ";
            }
            H_Pincode_.Text = dsClient.Tables[0].Rows[0]["H_PinNo"].ToString();
            a = ascii.GetBytes(H_Pincode_.Text);
            for (int i = 0; i < a.Length; i++)
            {
                H_Pincode_ASCII_.Text += a[i].ToString() + " ";
            }
            H_State_.Text = dsClient.Tables[0].Rows[0]["H_State"].ToString();
            a = ascii.GetBytes(H_State_.Text);
            for (int i = 0; i < a.Length; i++)
            {
                H_State_ASCII_.Text += a[i].ToString() + " ";
            }
            O_Address_.Text = dsClient.Tables[0].Rows[0]["O_Address"].ToString();
            a = ascii.GetBytes(O_Address_.Text);
            for (int i = 0; i < a.Length; i++)
            {
                O_Address_ASCII_.Text += a[i].ToString() + " ";
            }
            O_City_.Text = dsClient.Tables[0].Rows[0]["O_City"].ToString();
            a = ascii.GetBytes(O_City_.Text);
            for (int i = 0; i < a.Length; i++)
            {
                O_City_ASCII_.Text += a[i].ToString() + " ";
            }
            O_Pinno_.Text = dsClient.Tables[0].Rows[0]["O_PinNo"].ToString();
            a = ascii.GetBytes(O_Pinno_.Text);
            for (int i = 0; i < a.Length; i++)
            {
                O_Pinno_ASCII_.Text += a[i].ToString() + " ";
            }
            LAL_.Text = dsClient.Tables[0].Rows[0]["LAL"].ToString();
            a = ascii.GetBytes(LAL_.Text);
            for (int i = 0; i < a.Length; i++)
            {
                LAL_ASCII_.Text += a[i].ToString() + " ";
            }
            Mrn_No_.Text = dsClient.Tables[0].Rows[0]["MRNNo"].ToString();
            a = ascii.GetBytes(Mrn_No_.Text);
            for (int i = 0; i < a.Length; i++)
            {
                Mrn_No_ASCII_.Text += a[i].ToString() + " ";
            }
            AF_.Text = dsClient.Tables[0].Rows[0]["AF"].ToString();
            a = ascii.GetBytes(AF_.Text);
            for (int i = 0; i < a.Length; i++)
            {
                AF_ASCII_.Text += a[i].ToString() + " ";
            }
            Nri_.Text = dsClient.Tables[0].Rows[0]["NRI"].ToString();
            a = ascii.GetBytes(Nri_.Text);
            for (int i = 0; i < a.Length; i++)
            {
                Nri_ASCII_.Text += a[i].ToString() + " ";
            }
            CP_.Text = dsClient.Tables[0].Rows[0]["CP"].ToString();
            a = ascii.GetBytes(CP_.Text);
            for (int i = 0; i < a.Length; i++)
            {
                CP_ASCII_.Text += a[i].ToString() + " ";
            }

            try
            {
                if (Tbc_No.Text == Tbc_No_.Text)
                {
                    Tbc_No.ForeColor = System.Drawing.Color.Green;
                    
                }
                else
                {
                    Tbc_No.ForeColor = System.Drawing.Color.Red;
                    

                }

                if (Name.Text == Name_.Text)
                {
                    Name.ForeColor = System.Drawing.Color.Green;
                }
                else
                {
                    Name.ForeColor = System.Drawing.Color.Red;

                }

                if (EmailID.Text == EmailID_.Text)
                {
                    EmailID.ForeColor = System.Drawing.Color.Green;
                }
                else
                {
                    EmailID.ForeColor = System.Drawing.Color.Red;

                }

                if (Mobile_No.Text == Mobile_No_.Text)
                {
                    Mobile_No.ForeColor = System.Drawing.Color.Green;
                }
                else
                {
                    Mobile_No.ForeColor = System.Drawing.Color.Red;

                }

                if (Gender.Text == Gender_.Text)
                {
                    Gender.ForeColor = System.Drawing.Color.Green;
                }
                else
                {
                    Gender.ForeColor = System.Drawing.Color.Red;

                }

                if (License_No.Text == License_No_.Text)
                {
                    License_No.ForeColor = System.Drawing.Color.Green;
                }
                else
                {
                    License_No.ForeColor = System.Drawing.Color.Red;

                }

                if (Gir_No.Text == Gir_No_.Text)
                {
                    Gir_No.ForeColor = System.Drawing.Color.Green;
                }
                else
                {
                    Gir_No.ForeColor = System.Drawing.Color.Red;

                }

                if (Pan_No.Text == Pan_No_.Text)
                {
                    Pan_No.ForeColor = System.Drawing.Color.Green;
                }
                else
                {
                    Pan_No.ForeColor = System.Drawing.Color.Red;

                }
                if (H_Address.Text == H_Address_.Text)
                {
                    H_Address.ForeColor = System.Drawing.Color.Green;
                }
                else
                {
                    H_Address.ForeColor = System.Drawing.Color.Red;

                }
                if (H_City.Text == H_City_.Text)
                {
                    H_City.ForeColor = System.Drawing.Color.Green;
                }
                else
                {
                    H_City.ForeColor = System.Drawing.Color.Red;

                }

                if (H_Pincode.Text == H_Pincode_.Text)
                {
                    H_Pincode.ForeColor = System.Drawing.Color.Green;
                }
                else
                {
                    H_Pincode.ForeColor = System.Drawing.Color.Red;

                }

                if (H_State.Text == H_State_.Text)
                {
                    H_State.ForeColor = System.Drawing.Color.Green;
                }
                else
                {
                    H_State.ForeColor = System.Drawing.Color.Red;
                }

                if (O_Address.Text == O_Address_.Text)
                {
                    O_Address.ForeColor = System.Drawing.Color.Green;
                }
                else
                {
                    O_Address.ForeColor = System.Drawing.Color.Red;
                }

                if (O_City.Text == O_City_.Text)
                {
                    O_City.ForeColor = System.Drawing.Color.Green;
                }
                else
                {
                    O_City.ForeColor = System.Drawing.Color.Red;
                }


                if (O_Pinno.Text == O_Pinno_.Text)
                {
                    O_Pinno.ForeColor = System.Drawing.Color.Green;
                }
                else
                {
                    O_Pinno.ForeColor = System.Drawing.Color.Red;
                }
                if (LAL.Text == LAL_.Text)
                {
                    LAL.ForeColor = System.Drawing.Color.Green;
                }
                else
                {
                    LAL.ForeColor = System.Drawing.Color.Red;
                }


                if (Mrn_No.Text == Mrn_No_.Text)
                {
                    Mrn_No.ForeColor = System.Drawing.Color.Green;
                }
                else
                {
                    Mrn_No.ForeColor = System.Drawing.Color.Red;
                }

                if (AF.Text == AF_.Text)
                {
                    AF.ForeColor = System.Drawing.Color.Green;
                }
                else
                {
                    AF.ForeColor = System.Drawing.Color.Red;
                }

                if (Nri.Text == Nri_.Text)
                {
                    Nri.ForeColor = System.Drawing.Color.Green;
                }
                else
                {
                    Nri.ForeColor = System.Drawing.Color.Red;
                }

                if (CP.Text == CP_.Text)
                {
                    CP.ForeColor = System.Drawing.Color.Green;
                }
                else
                {
                    CP.ForeColor = System.Drawing.Color.Red;
                }




            }
            catch (Exception ee)
            { }
        }
        catch 
        {
            
           
        }
    }
}
