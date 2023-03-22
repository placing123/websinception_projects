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

using System.IO.Compression;
using System.IO;
using System.Text;
using Ionic.Zip;
using System.Data.SqlClient;
using System.Data.OleDb;
using System.Drawing;
using System.Drawing.Imaging;
using System.Drawing.Drawing2D;
using System.Drawing.Text;

public partial class Customer_Offline_Client : System.Web.UI.Page
{
    clsAdmin AD = new clsAdmin();
    DataSet DS = new DataSet();
    clsFranchisee CF = new clsFranchisee();

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
            lbl_end_date.Text = DS.Tables[0].Rows[0]["End_Date"].ToString();
        }
        FillTotalComplete();
    }  
    protected void btn_download_Click(object sender, EventArgs e)
    {
        try
        {

            string str = Session["Cus_Username"].ToString();
            AD.id = str;
            DS = AD.Select_Planid_From_Registration();
            string page = DS.Tables[0].Rows[0][0].ToString();
            int totalpage = Convert.ToInt32(page);
            AD.form = page;
            AD.cid = str;
            DS = AD.Select_Client_Bpo_Data_Cid();

            DataTable dt = new DataTable();
            MyCon mycon = new MyCon();

            dt = mycon.FillDataTable("select * from tbl_bpo_data where id in (select id from tbl_client_bpo_data where cid='" + str + "')");
            Directory.CreateDirectory(Server.MapPath("~/ClientImage/") + str);
            for (int i = 0; i < dt.Rows.Count; i++)
            {                
                string image = dt.Rows[i]["Tbc_No"].ToString() + " " + dt.Rows[i]["Name"].ToString() + " " + dt.Rows[i]["EmailId"].ToString() + " " + dt.Rows[i]["MobileNo"].ToString() + " " + dt.Rows[i]["Gender"].ToString() + " " + dt.Rows[i]["LicenseNo"].ToString() + " " + dt.Rows[i]["GirNo"].ToString() + " " + dt.Rows[i]["PanNo"].ToString() + " \"" + dt.Rows[i]["H_Address"].ToString() + " " + dt.Rows[i]["H_City"].ToString() + " " + dt.Rows[i]["H_PinNo"].ToString() + " " + dt.Rows[i]["H_State"].ToString() + "\"  \"" + dt.Rows[i]["O_Address"].ToString() + " " + dt.Rows[i]["O_City"].ToString() + " " + dt.Rows[i]["O_PinNo"].ToString() + "\" " + dt.Rows[i]["LAL"].ToString() + " " + dt.Rows[i]["MRNNo"].ToString() + " " + dt.Rows[i]["AF"].ToString() + " " + dt.Rows[i]["NRI"].ToString() + " " + dt.Rows[i]["CP"].ToString();               
                ImageCreate(image, dt.Rows[i]["Id"].ToString(), str);
            }
            using (ZipFile zip = new ZipFile())
            {

                for (int i = 0; i < totalpage; i++)
                {
                   zip.AddFile(Server.MapPath("~/ClientImage/") + str + "/" + DS.Tables[0].Rows[i][0].ToString() + ".jpg", "Images");
                }
                zip.Save(Server.MapPath("~/Download/") + str + ".zip");
                try
                {
                    Directory.Delete(Server.MapPath("~/ClientImage/") + str, true);
                }
                catch (Exception ex)
                {
                }
            }
            Response.Clear();
            Response.ContentType = "application/zip";
            Response.AppendHeader("Content-Disposition", "attachment; filename=" + str + ".zip");
            Response.TransmitFile(Server.MapPath("~/Download/") + str + ".zip");
            Response.End();
        }
        catch (Exception ex)
        {

        }
    }
    public void ImageCreate(string text, string id, string cid)
    {

        int textLength = text.Length;
        int fontSize = 20;
        int orientation = 1;
        int antialias = 1;
        int width;
        int height;
        if (orientation == 1)
        {
            width = 840;
            height = fontSize + 100;
        }
        else
        {
            width = fontSize + 20;
            height = (int)(fontSize * textLength * 1.5);
        }


        RectangleF rectF = new RectangleF(0, 0, width, height);
        Bitmap pic = new Bitmap(width, height, PixelFormat.Format24bppRgb);
        Graphics g = Graphics.FromImage(pic);
        g.SmoothingMode = SmoothingMode.AntiAlias;
        if (antialias == 1) g.TextRenderingHint = TextRenderingHint.AntiAlias;

        // Set colors
        string fgColor = "Black";
        string bgColor = "White";
        Color fontColor = Color.FromName(fgColor);
        Color rectColor = Color.FromName(bgColor);
        SolidBrush fgBrush = new SolidBrush(fontColor);
        SolidBrush bgBrush = new SolidBrush(rectColor);

        // Rectangle or ellipse?
        int bound = Convert.ToInt32(Request.Form.Get("bound"));
        bound = 1;
        if (bound == 1)
        {
            g.FillRectangle(bgBrush, rectF);
        }
        else
        {
            g.FillRectangle(new SolidBrush(Color.White), rectF);
            g.FillEllipse(bgBrush, rectF);
        }

        // Load font   
        string fontName = "TIMES.TTF";
        PrivateFontCollection privateFontCollection = new PrivateFontCollection();
        privateFontCollection.AddFontFile(Server.MapPath("~/") + fontName);

        FontFamily fontFamily = privateFontCollection.Families[0];

        // Set font style
        int fontStyle = Convert.ToInt32(Request.Form.Get("fontstyle"));
        fontStyle = 3;
        FontStyle style = FontStyle.Regular;
        switch (fontStyle)
        {
            case 2:
                style = FontStyle.Bold;
                break;

            case 3:
                style = FontStyle.Italic;
                break;

            case 4:
                style = (FontStyle.Bold) | (FontStyle.Italic);
                break;

            case 5:
                style = FontStyle.Strikeout;
                break;

            case 6:
                style = FontStyle.Underline;
                break;
        }
        Font font = new Font(fontFamily, fontSize, style, GraphicsUnit.Pixel);

        // Set font direction & alignment
        StringFormat format = new StringFormat();



        int reverse = 1;
        if (reverse == 1 && orientation == 1)
        {
            format.FormatFlags = StringFormatFlags.DirectionRightToLeft;
        }
        else if (reverse == 1 && orientation > 1)
        {
            StringBuilder temp = new StringBuilder();
            for (int i = textLength - 1; i >= 0; i--)
            {
                temp.Insert((textLength - 1) - i, text[i]);
            }
            text = temp.ToString();
        }
        if (orientation > 1)
        {
            rectF.X = width / 4;
            rectF.Width = fontSize - (fontSize / 4);
        }
        int alignment = 2;        /////Convert.ToInt32(Request.Form.Get("alignment"));            
        if (alignment == 1)
        {
            format.Alignment = StringAlignment.Near;
        }
        else if (alignment == 2)
        {
            format.Alignment = StringAlignment.Center;
        }
        else
        {
            format.Alignment = StringAlignment.Far;
        }
        format.LineAlignment = StringAlignment.Center;

        // Draw any drop-shadow
        //int dropShadow = 0;       ////Convert.ToInt32(Request.Form.Get("dropshadow"));

        //if (dropShadow > 0)
        //{
        //    Color shadowColor = Color.FromName("Pink");   ///Request.Form.Get("shadowcolor")
        //    switch (dropShadow)
        //    {
        //        case 1:
        //            rectF.Offset(-3, -3);
        //            g.DrawString(text, font, new SolidBrush(shadowColor), rectF, format);
        //            rectF.Offset(+3, +3);
        //            break;

        //        case 2:
        //            rectF.Offset(+3, -3);
        //            g.DrawString(text, font, new SolidBrush(shadowColor), rectF, format);
        //            rectF.Offset(-3, +3);
        //            break;

        //        case 3:
        //            rectF.Offset(-3, +3);
        //            g.DrawString(text, font, new SolidBrush(shadowColor), rectF, format);
        //            rectF.Offset(+3, -3);
        //            break;

        //        case 4:
        //            rectF.Offset(+3, +3);
        //            g.DrawString(text, font, new SolidBrush(shadowColor), rectF, format);
        //            rectF.Offset(-3, -3);
        //            break;
        //    }
        //}

        // Finally, draw the font
        g.DrawString(text, font, fgBrush, rectF, format);
        //  Response.ContentType = "image/png";
        // pic.Save(Response.OutputStream, ImageFormat.Png);

        pic.Save(Server.MapPath("~/ClientImage/" + cid + "/" + id + ".jpg"), ImageFormat.Jpeg);
        // Dispose objects
        pic.Dispose();
    }
    public void FillTotalComplete()
    {
        CF.CId = Session["Cus_Username"].ToString();

        CF.QueryFor = "Complete";
        DataSet ds = CF.Master_Client_BPO_Data();
      //  lblcompl.Text = ds.Tables[0].Rows[0][0].ToString();

        //if (lblcompl.Text == "0")
        //{
        //    lblcurrentpge.Text = "1";
        //}

        //else
        //{
        //    int a = Convert.ToInt32(lblcompl.Text) + 1;
        //    lblcurrentpge.Text = Convert.ToString(a);
        //}
     //   lblcurrentpge.Text = Convert.ToString(drp_pagejump.SelectedIndex + 1);
        //CF.QueryFor = "Total";
        //DataSet ds1 = CF.Master_Client_BPO_Data();
        //lbltotal.Text = ds1.Tables[0].Rows[0][0].ToString();

        //if (lblcompl.Text == lbltotal.Text)
        //{
        //    // Page.ClientScript.RegisterStartupScript(this.GetType(), "alert", "alert('Your Workload is Completed Successfully');", true);
        //    lblmsg.Text = "Your Workload is Completed Successfully ";
        //    lblmsg.Visible = true;


            CF.CId = Session["Cus_Username"].ToString();
            DataSet dss = CF.Select_Status_Tbl_Registration_by_CID();

            if (dss.Tables[0].Rows[0][0].ToString() == "1")
            {
               first.Visible = true;
            }



            else if (dss.Tables[0].Rows[0][0].ToString() == "2")
            {
                lblmsg.Text = "Your Qc Is In Progress";
                lblmsg.Visible = true;
                first.Visible = false;


            }
            else if (dss.Tables[0].Rows[0][0].ToString() == "3")
            {
                lblmsg.Text = "Your Qc Is In Progress";
              lblmsg.Visible = true;
              first.Visible = false;


            }
            else if (dss.Tables[0].Rows[0][0].ToString() == "3_1")
            {
                lblmsg.Text = "Your Qc Is In Progress";
                lblmsg.Visible = true;
                first.Visible = false;
            }


            else if (dss.Tables[0].Rows[0][0].ToString() == "4")
            {
                first.Visible = false;
                Response.Redirect("Report.aspx");
            }
            else
            {
                first.Visible = false;
            }
      //  }
    }
}




