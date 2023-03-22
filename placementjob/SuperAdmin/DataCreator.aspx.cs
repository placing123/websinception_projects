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
using System.Text.RegularExpressions;
using System.Drawing;
using System.Drawing.Imaging;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.IO;

public partial class Admin_DataCreator : System.Web.UI.Page
{
    Random rm = new Random();
    clsAdmin AD = new clsAdmin();
    DataSet DS = new DataSet();
    DataSet DS1 = new DataSet();
    DataSet DS2 = new DataSet();
    protected void Page_Load(object sender, EventArgs e)
    {

        


        if (!IsPostBack)
        {
            try
            {
                string str;
                DS = AD.Select_Bpo_Name();
                for (int i = 0; i < DS.Tables[0].Rows.Count; i++)
                {
                    txt_name.Text = txt_name.Text + DS.Tables[0].Rows[i][0].ToString() + "\r\n";
                }
                txt_name.Text = txt_name.Text.Substring(0, txt_name.Text.Length - 2);

                DS = AD.Select_Bpo_EmailId();
                for (int i = 0; i < DS.Tables[0].Rows.Count; i++)
                {
                    txt_emailid.Text = txt_emailid.Text + DS.Tables[0].Rows[i][0].ToString() + "\r\n";
                }
                txt_emailid.Text = txt_emailid.Text.Substring(0, txt_emailid.Text.Length - 2);

                DS = AD.Select_Bpo_Mobile();
                for (int i = 0; i < DS.Tables[0].Rows.Count; i++)
                {
                    txt_mobileno.Text = txt_mobileno.Text + DS.Tables[0].Rows[i][0].ToString() + "\r\n";
                }
                txt_mobileno.Text = txt_mobileno.Text.Substring(0, txt_mobileno.Text.Length - 2);

                DS = AD.Select_Bpo_Address();
                for (int i = 0; i < DS.Tables[0].Rows.Count; i++)
                {
                    txt_adress.Text = txt_adress.Text + DS.Tables[0].Rows[i][0].ToString() + "\r\n";
                }
                txt_adress.Text = txt_adress.Text.Substring(0, txt_adress.Text.Length - 2);


                DS1 = AD.Select_Bpo_City();
                for (int i = 0; i < DS1.Tables[0].Rows.Count; i++)
                {
                    txt_SCP.Text = txt_SCP.Text + DS1.Tables[0].Rows[i][0].ToString() + "\r\n";
                }
                txt_SCP.Text = txt_SCP.Text.Substring(0, txt_SCP.Text.Length - 2);

                DS2 = AD.Select_Bpo_Pincode();
                for (int i = 0; i < DS2.Tables[0].Rows.Count; i++)
                {
                    txt_pin.Text = txt_pin.Text + DS2.Tables[0].Rows[i][0].ToString() + "\r\n";
                }
                txt_pin.Text = txt_pin.Text.Substring(0, txt_pin.Text.Length - 2);

                DS = AD.Select_Bpo_state();
                for (int i = 0; i < DS.Tables[0].Rows.Count; i++)
                {
                    txt_state.Text = txt_state.Text + DS.Tables[0].Rows[i][0].ToString() + "\r\n";
                }
                txt_state.Text = txt_state.Text.Substring(0, txt_state.Text.Length - 2);

            }
            catch
            {


            }
        }

    }

    private string TBCNo()
    {
        string str;
        string s1 = generatecapitalandsmal(3);
        string s2 = generatealphabate(1);
        string s3 = generatecapitalnumber(4);
        string s4 = generatealphabate(1);
        string s5 = generateno(3);
        string s6 = generatealphabate(1);
        string s7 = generatewithoutsymbol(11);
        str = s1 + s2 + s3 + s4 + s5 + s6 + s7;
        return str;
    }
    public string generatecapitalandsmal(int no)
    {
        string strPwdchar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        string strPwd = "";
        Random rnd = new Random();
        for (int i = 0; i < no; i++)
        {
            int iRandom = rnd.Next(0, strPwdchar.Length - 1);
            strPwd += strPwdchar.Substring(iRandom, 1);
        }
        return strPwd;
    }
    public string generatealphabate(int no)
    {

        string strPwdchar = "!@#$%&";
        string strPwd = "";
        Random rnd = new Random();
        for (int i = 0; i < no; i++)
        {
            int iRandom = rnd.Next(0, strPwdchar.Length - 1);
            strPwd += strPwdchar.Substring(iRandom, 1);
        }
        return strPwd;
    }
    public string generatecapitalnumber(int no)
    {
        string strPwdchar = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        string strPwd = "";
        Random rnd = new Random();
        for (int i = 0; i < no; i++)
        {
            int iRandom = rnd.Next(0, strPwdchar.Length - 1);
            strPwd += strPwdchar.Substring(iRandom, 1);
        }
        return strPwd;
    }
    public string generateno(int no)
    {
        string strPwdchar = "0123456789";
        string strPwd = "";
        Random rnd = new Random();
        for (int i = 0; i < no; i++)
        {
            int iRandom = rnd.Next(0, strPwdchar.Length - 1);
            strPwd += strPwdchar.Substring(iRandom, 1);
        }
        return strPwd;
    }
    public string generatewithoutsymbol(int no)
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
    private string Generaterandom(int no)
    {
        string strPwdchar = "abcdefghijklmnopqrstuvwxyz0123456789!@$%ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        string strPwd = "";
        Random rnd = new Random();
        for (int i = 0; i < no; i++)
        {
            int iRandom = rnd.Next(0, strPwdchar.Length - 1);
            strPwd += strPwdchar.Substring(iRandom, 1);
        }
        return strPwd;
    }

    private string generatecapital(int no)
    {
        string strPwdchar = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        string strPwd = "";
        Random rnd = new Random();
        for (int i = 0; i < no; i++)
        {
            int iRandom = rnd.Next(0, strPwdchar.Length - 1);
            strPwd += strPwdchar.Substring(iRandom, 1);
        }
        return strPwd;
    }

    public string GirNo()
    {
        string s;
        string s1 = generatecapital(3);
        string s2 = generatealphabate(1);
        string s3 = generatewithoutsymbol(10);
        s = s1 + s2 + s3;
        return s;
    }

    public string PanNo()
    {
        string s;
        string s1 = generatecapital(5);
        string s2 = generateno(2);
        string s3 = generatecapital(1);
        s = s1 + s2 + s3;
        return s;
    }
    public string MRNNO()
    {
        string s;
        string s1 = generateno(4);
        string s2 = generatecapital(2);
        string s3 = generateno(1);
        s = s1 + s2 + s3;
        return s;
    }

    public void ImageCreate(string text, string id)
    {

        int textLength = text.Length;
        int fontSize = 30;
        int orientation = 1;
        int antialias = 1;
        int width;
        int height;
        if (orientation == 1)
        {
            //width = 840;
            width = 1150;

            //height = fontSize + 190;
            height = 220;
        }
        else
        {
            width = fontSize + 20;
            height = (int)(fontSize * textLength * 1.9);
        }


        RectangleF rectF = new RectangleF(0, 0, width, height);
        Bitmap pic = new Bitmap(width, height, PixelFormat.Format24bppRgb);
        Graphics g = Graphics.FromImage(pic);
        g.SmoothingMode = SmoothingMode.Default;
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
        string fontName = "arial.ttf";
        PrivateFontCollection privateFontCollection = new PrivateFontCollection();
        privateFontCollection.AddFontFile(Server.MapPath("./") + fontName);

        FontFamily fontFamily = privateFontCollection.Families[0];

        // Set font style
        int fontStyle = Convert.ToInt32(Request.Form.Get("fontstyle"));
        fontStyle = 4;
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
            format.Alignment = StringAlignment.Far;
        }
        else if (alignment == 2)
        {
            format.Alignment = StringAlignment.Far;
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

        pic.Save(Server.MapPath("../ClientImage/" + id + ".jpg"), ImageFormat.Jpeg);
        // Dispose objects
        pic.Dispose();


    }
    
    public void firstline(string text, string id)
    {

        string fgColor = "Black";

        Color fontColor = Color.FromName(fgColor);

        SolidBrush fgBrush = new SolidBrush(fontColor);

        ///////////////////////////////////////////////////////
        string fontName = "MTCORSVA.ttf";
        PrivateFontCollection privateFontCollection = new PrivateFontCollection();
        privateFontCollection.AddFontFile(Server.MapPath("./") + fontName);

        FontFamily fontFamily = privateFontCollection.Families[0];

        /////////////////
        int fontSize = 30;
        /////////////////

        int fontStyle = 3;
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
        ///////////////////////////////////////////////////////
        //Load the Image to be written on.
        Bitmap bitMapImage = new System.Drawing.Bitmap(Server.MapPath("../ClientImage/" + id + ".jpg"));
        Graphics graphicImage = Graphics.FromImage(bitMapImage);

        //Smooth graphics is nice.
        graphicImage.SmoothingMode = SmoothingMode.AntiAlias;

        //I am drawing a oval around my text.
        graphicImage.DrawArc(new Pen(Color.Red, 3), 90, 235, 150, 50, 0, 360);

        //Write your text.
        graphicImage.DrawString(text, font, fgBrush, new Point(10, 10));

        //Set the content type
        Response.ContentType = "image/jpeg";

        //Save the new image to the response output stream.
        bitMapImage.Save(Server.MapPath("../ClientImage/" + id + "-.jpg"), ImageFormat.Jpeg);

        //Clean house.
        graphicImage.Dispose();
        bitMapImage.Dispose();
        File.Delete(Server.MapPath("../ClientImage/" + id + ".jpg"));
        File.Copy(Server.MapPath("../ClientImage/" + id + "-.jpg"), Server.MapPath("../ClientImage/" + id + ".jpg"));
        File.Delete(Server.MapPath("../ClientImage/" + id + "-.jpg"));

    }
    public void secondline(string text, string id)
    {
        ///////////////////////////////////////////////////////
        string fontName = "MTCORSVA.ttf";
        PrivateFontCollection privateFontCollection = new PrivateFontCollection();
        privateFontCollection.AddFontFile(Server.MapPath("./") + fontName);

        FontFamily fontFamily = privateFontCollection.Families[0];

        /////////////////
        int fontSize = 30;
        /////////////////

        int fontStyle = 3;
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
        ///////////////////////////////////////////////////////
        //Load the Image to be written on.
        Bitmap bitMapImage = new System.Drawing.Bitmap(Server.MapPath("../ClientImage/" + id + ".jpg"));
        Graphics graphicImage = Graphics.FromImage(bitMapImage);

        //Smooth graphics is nice.
        graphicImage.SmoothingMode = SmoothingMode.AntiAlias;

        //I am drawing a oval around my text.
        graphicImage.DrawArc(new Pen(Color.Red, 3), 90, 235, 150, 50, 0, 360);

        //Write your text.
        graphicImage.DrawString(text, font, SystemBrushes.WindowText, new Point(10, 30));

        //Set the content type
        Response.ContentType = "image/jpeg";

        //Save the new image to the response output stream.
        bitMapImage.Save(Server.MapPath("../ClientImage/" + id + "-.jpg"), ImageFormat.Jpeg);

        //Clean house.
        graphicImage.Dispose();
        bitMapImage.Dispose();
        File.Delete(Server.MapPath("../ClientImage/" + id + ".jpg"));
        File.Copy(Server.MapPath("../ClientImage/" + id + "-.jpg"), Server.MapPath("../ClientImage/" + id + ".jpg"));
        File.Delete(Server.MapPath("../ClientImage/" + id + "-.jpg"));

    }
    public void thirdline(string text, string id)
    {
        ///////////////////////////////////////////////////////
        string fontName = "MTCORSVA.ttf";
        PrivateFontCollection privateFontCollection = new PrivateFontCollection();
        privateFontCollection.AddFontFile(Server.MapPath("./") + fontName);

        FontFamily fontFamily = privateFontCollection.Families[0];

        /////////////////
        int fontSize = 30;
        /////////////////

        int fontStyle = 3;
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
        ///////////////////////////////////////////////////////
        //Load the Image to be written on.
        Bitmap bitMapImage = new System.Drawing.Bitmap(Server.MapPath("../ClientImage/" + id + ".jpg"));
        Graphics graphicImage = Graphics.FromImage(bitMapImage);

        //Smooth graphics is nice.
        graphicImage.SmoothingMode = SmoothingMode.AntiAlias;

        //I am drawing a oval around my text.
        graphicImage.DrawArc(new Pen(Color.Red, 3), 90, 235, 150, 50, 0, 360);

        //Write your text.
        graphicImage.DrawString(text, font, SystemBrushes.WindowText, new Point(10, 50));

        //Set the content type
        Response.ContentType = "image/jpeg";

        //Save the new image to the response output stream.
        bitMapImage.Save(Server.MapPath("../ClientImage/" + id + "-.jpg"), ImageFormat.Jpeg);

        //Clean house.
        graphicImage.Dispose();
        bitMapImage.Dispose();
        File.Delete(Server.MapPath("../ClientImage/" + id + ".jpg"));
        File.Copy(Server.MapPath("../ClientImage/" + id + "-.jpg"), Server.MapPath("../ClientImage/" + id + ".jpg"));
        File.Delete(Server.MapPath("../ClientImage/" + id + "-.jpg"));

    }
    public void fourthline(string text, string id)
    {
        ///////////////////////////////////////////////////////
        string fontName = "MTCORSVA.ttf";
        PrivateFontCollection privateFontCollection = new PrivateFontCollection();
        privateFontCollection.AddFontFile(Server.MapPath("./") + fontName);

        FontFamily fontFamily = privateFontCollection.Families[0];

        /////////////////
        int fontSize = 30;
        /////////////////

        int fontStyle = 3;
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
        ///////////////////////////////////////////////////////
        //Load the Image to be written on.
        Bitmap bitMapImage = new System.Drawing.Bitmap(Server.MapPath("../ClientImage/" + id + ".jpg"));
        Graphics graphicImage = Graphics.FromImage(bitMapImage);

        //Smooth graphics is nice.
        graphicImage.SmoothingMode = SmoothingMode.AntiAlias;

        //I am drawing a oval around my text.
        graphicImage.DrawArc(new Pen(Color.Red, 3), 90, 235, 150, 50, 0, 360);

        //Write your text.
        graphicImage.DrawString(text, font, SystemBrushes.WindowText, new Point(10, 70));

        //Set the content type
        Response.ContentType = "image/jpeg";

        //Save the new image to the response output stream.
        bitMapImage.Save(Server.MapPath("../ClientImage/" + id + "-.jpg"), ImageFormat.Jpeg);

        //Clean house.
        graphicImage.Dispose();
        bitMapImage.Dispose();
        File.Delete(Server.MapPath("../ClientImage/" + id + ".jpg"));
        File.Copy(Server.MapPath("../ClientImage/" + id + "-.jpg"), Server.MapPath("../ClientImage/" + id + ".jpg"));
        File.Delete(Server.MapPath("../ClientImage/" + id + "-.jpg"));

    }
    public void fifthline(string text, string id)
    {
        ///////////////////////////////////////////////////////
        string fontName = "MTCORSVA.ttf";
        PrivateFontCollection privateFontCollection = new PrivateFontCollection();
        privateFontCollection.AddFontFile(Server.MapPath("./") + fontName);

        FontFamily fontFamily = privateFontCollection.Families[0];

        /////////////////
        int fontSize = 30;
        /////////////////

        int fontStyle = 3;
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
        ///////////////////////////////////////////////////////
        //Load the Image to be written on.
        Bitmap bitMapImage = new System.Drawing.Bitmap(Server.MapPath("../ClientImage/" + id + ".jpg"));
        Graphics graphicImage = Graphics.FromImage(bitMapImage);

        //Smooth graphics is nice.
        graphicImage.SmoothingMode = SmoothingMode.AntiAlias;

        //I am drawing a oval around my text.
        graphicImage.DrawArc(new Pen(Color.Red, 3), 90, 235, 150, 50, 0, 360);

        //Write your text.
        graphicImage.DrawString(text, font, SystemBrushes.WindowText, new Point(10, 90));

        //Set the content type
        Response.ContentType = "image/jpeg";

        //Save the new image to the response output stream.
        bitMapImage.Save(Server.MapPath("../ClientImage/" + id + "-.jpg"), ImageFormat.Jpeg);

        //Clean house.
        graphicImage.Dispose();
        bitMapImage.Dispose();
        File.Delete(Server.MapPath("../ClientImage/" + id + ".jpg"));
        File.Copy(Server.MapPath("../ClientImage/" + id + "-.jpg"), Server.MapPath("../ClientImage/" + id + ".jpg"));
        File.Delete(Server.MapPath("../ClientImage/" + id + "-.jpg"));

    }
    protected void btn_generate_Click(object sender, EventArgs e)
    {

        try
        {

            //     string tbcno = TBCNo();

            ////////////////////

            string name1 = txt_name.Text;
            name1 = name1.Replace('\r', ' ');
            name1 = name1.Replace('\n', '~');
            name1 = Regex.Replace(name1, @"\s+", " ");
            string[] name = name1.Split('~');
            for (int i = 0; i < name.Length; i++)
            {
                name[i] = name[i].Trim();
            }
            int namelength = name.Length;

            /////////////////////////

            string emailid1 = txt_emailid.Text;
            emailid1 = emailid1.Replace('\r', ' ');
            emailid1 = emailid1.Replace('\n', '~');
            emailid1 = Regex.Replace(emailid1, @"\s+", " ");
            string[] emailid = emailid1.Split('~');
            for (int i = 0; i < emailid.Length; i++)
            {
                emailid[i] = emailid[i].Trim();
            }
            int emaillength = emailid.Length;


            //////////////////////////////

            string mobile1 = txt_mobileno.Text;
            mobile1 = mobile1.Replace('\r', ' ');
            mobile1 = mobile1.Replace('\n', '~');
            mobile1 = Regex.Replace(mobile1, @"\s+", " ");
            string[] mobile = mobile1.Split('~');
            for (int i = 0; i < mobile.Length; i++)
            {
                mobile[i] = mobile[i].Trim();
            }
            int mobilelength = mobile.Length;

            /////////////////////////////


            string[] gender = { "M", "F" };

            int genderlength = gender.Length;


            ///////////////////////////

            //    string licence = Generaterandom(10);

            //////////////////////////

            //    string girno = GirNo();

            ///////////////////////////////

            //     string panno = PanNo();

            ////////////////////////////////

            string address1 = txt_adress.Text;
            address1 = address1.Replace('\r', ' ');
            address1 = address1.Replace('\n', '~');
            address1 = Regex.Replace(address1, @"\s+", " ");
            string[] adress = address1.Split('~');
            for (int i = 0; i < adress.Length; i++)
            {
                adress[i] = adress[i].Trim();
            }

            int addresslength = adress.Length;


            /////////////////////////////////

            //string scp1 = txt_SCP.Text;
            //scp1 = scp1.Replace('\r', ' ');
            //scp1 = scp1.Replace('\n', '~');
            //scp1 = Regex.Replace(scp1, @"\s+", " ");
            //string[] scp = scp1.Split('~');
            //string[] scp2;
            //string[] state = new string[scp.Length];
            //string[] city = new string[scp.Length];
            //string[] pincode = new string[scp.Length];
            //for (int i = 0; i < scp.Length; i++)
            //{

            //    scp[i] = scp[i].Trim();
            //    if (scp[i] != "")
            //    {
            //        scp2 = scp[i].Split(' ');
            //        state[i] = scp2[0];
            //        city[i] = scp2[1];
            //        pincode[i] = scp2[2];
            //    }
            //}


            string city1 = txt_SCP.Text;
            city1 = city1.Replace('\r', ' ');
            city1 = city1.Replace('\n', '~');
            city1 = Regex.Replace(city1, @"\s+", " ");
            string[] city = city1.Split('~');
            for (int i = 0; i < city.Length; i++)
            {
                city[i] = city[i].Trim();
            }
            int citylength = city.Length;




            string pin1 = txt_pin.Text;
            pin1 = pin1.Replace('\r', ' ');
            pin1 = pin1.Replace('\n', '~');
            pin1 = Regex.Replace(pin1, @"\s+", " ");
            string[] pincode = pin1.Split('~');
            for (int i = 0; i < pincode.Length; i++)
            {
                pincode[i] = pincode[i].Trim();
            }
            int pincodelength = pincode.Length;


            string state1 = txt_state.Text;
            state1 = state1.Replace('\r', ' ');
            state1 = state1.Replace('\n', '~');
            state1 = Regex.Replace(state1, @"\s+", " ");
            string[] state = state1.Split('~');
            for (int i = 0; i < state.Length; i++)
            {
                state[i] = state[i].Trim();
            }
            int statelength = state.Length;

            //////////////////////////////////

            //      Int32 lal = 100000 * rm.Next(1, 999);



            //////////////////////////////////////////

            //     string mrnno = MRNNO();


            /////////////////////////////////////////

            string[] AF = { "0", "1" };

            int aflength = AF.Length;

            /////////////////////////////////////////

            string[] NRI = { "Y", "N" };
            int nrilength = NRI.Length;

            ////////////////////////////////////////

            string[] CP = { "0", "1", "2" };
            int cplength = CP.Length;


            for (int i = 0; i < Convert.ToInt32(txt_entrys.Text); i++)
            {
                AD.Tbc_No = TBCNo().ToString();
                AD.Name = name[rm.Next(0, namelength)].ToString();
                AD.EmailId = emailid[rm.Next(0, emaillength)].ToString();
                AD.MobileNo = mobile[rm.Next(0, mobilelength)].ToString();
                AD.Gender = gender[rm.Next(0, genderlength)].ToString();
                AD.LicenseNo = Generaterandom(10).ToString();
                AD.GirNo = GirNo().ToString();
                AD.PanNo = PanNo().ToString();
                AD.H_Address = adress[rm.Next(0, addresslength)].ToString();
                AD.H_City = city[rm.Next(0, citylength)].ToString();
                AD.H_PinNo = pincode[rm.Next(0, pincodelength)].ToString();
                AD.H_State = state[rm.Next(0, statelength)].ToString();
                AD.O_Address = adress[rm.Next(0, addresslength)].ToString();
                AD.O_City = city[rm.Next(0, citylength)].ToString();
                AD.O_PinNo = pincode[rm.Next(0, pincodelength)].ToString();
                AD.LAL = (100000 * rm.Next(1, 999)).ToString();
                AD.MRNNo = MRNNO();
                AD.AF = AF[rm.Next(0, aflength)].ToString();
                AD.NRI = NRI[rm.Next(0, nrilength)].ToString();
                AD.CP = CP[rm.Next(0, cplength)].ToString();
                DS = AD.Insert_Bpo_Data();
                string insertid = DS.Tables[0].Rows[0][0].ToString();
                string image = AD.Tbc_No + " " + AD.Name + " " + AD.EmailId + " " + AD.MobileNo + " " + AD.Gender + " " + AD.LicenseNo + " " + AD.GirNo + " " + AD.PanNo + " \"" + AD.H_Address + " " + AD.H_City + " " + AD.H_PinNo + " " + AD.H_State + "\"  \"" + AD.O_Address + " " + AD.O_City + " " + AD.O_PinNo + "\" " + AD.LAL + " " + AD.MRNNo + " " + AD.AF + " " + AD.NRI + " " + AD.CP;
                ImageCreate(image, insertid);

            }
            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Data Created Succsessfully..." + "');", true);


        }

        catch
        {

            //    ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + ex.ToString() + "');", true);

        }



    }



    protected void but_AllImage_Click(object sender, EventArgs e)
    {
        DataSet DsBpo = AD.SelectALL_Bpo_Data();

        for (int i = 0; i < DsBpo.Tables[0].Rows.Count; i++)
        {
            string insertid = DsBpo.Tables[0].Rows[i][0].ToString();

            if (File.Exists(Server.MapPath("~/ClientImage/" + insertid + ".jpg")))
            {
            }
            else
            {
                //string image = DsBpo.Tables[0].Rows[i][1].ToString() + " " + DsBpo.Tables[0].Rows[i][2].ToString() + " " + DsBpo.Tables[0].Rows[i][3].ToString() + " " + DsBpo.Tables[0].Rows[i][4].ToString() + " " + DsBpo.Tables[0].Rows[i][5].ToString() + " " + DsBpo.Tables[0].Rows[i][6].ToString() + " " + DsBpo.Tables[0].Rows[i][7].ToString() + " " + DsBpo.Tables[0].Rows[i][8].ToString() + " \"" + DsBpo.Tables[0].Rows[i][9].ToString() + " " + DsBpo.Tables[0].Rows[i][10].ToString() + " " + DsBpo.Tables[0].Rows[i][11].ToString() + " " + DsBpo.Tables[0].Rows[i][12].ToString() + "\"  \"" + DsBpo.Tables[0].Rows[i][13].ToString() + " " + DsBpo.Tables[0].Rows[i][14].ToString() + " " + DsBpo.Tables[0].Rows[i][15].ToString() + "\" " + DsBpo.Tables[0].Rows[i][16].ToString() + " " + DsBpo.Tables[0].Rows[i][17].ToString() + " " + DsBpo.Tables[0].Rows[i][18].ToString() + " " + DsBpo.Tables[0].Rows[i][19].ToString() + " " + DsBpo.Tables[0].Rows[i][20].ToString();
                string first = DsBpo.Tables[0].Rows[i]["Tbc_No"].ToString() + "   " + DsBpo.Tables[0].Rows[i]["Name"].ToString() + "   " + DsBpo.Tables[0].Rows[i]["EmailId"].ToString() + "   " + DsBpo.Tables[0].Rows[i]["MobileNo"].ToString() + System.Environment.NewLine;
                string second = DsBpo.Tables[0].Rows[i]["Gender"].ToString() + "   " + DsBpo.Tables[0].Rows[i]["LicenseNo"].ToString() + "   " + DsBpo.Tables[0].Rows[i]["GirNo"].ToString() + "   " + DsBpo.Tables[0].Rows[i]["PanNo"].ToString() + System.Environment.NewLine;
                string third = DsBpo.Tables[0].Rows[i]["MRNNo"].ToString() + "   " + DsBpo.Tables[0].Rows[i]["AF"].ToString() + "   " + DsBpo.Tables[0].Rows[i]["NRI"].ToString() + "   " + DsBpo.Tables[0].Rows[i]["CP"].ToString() + "   " + DsBpo.Tables[0].Rows[i]["LAL"].ToString() + System.Environment.NewLine;
                string four = DsBpo.Tables[0].Rows[i]["H_City"].ToString() + "   " + DsBpo.Tables[0].Rows[i]["H_Address"].ToString() + "   " + DsBpo.Tables[0].Rows[i]["H_PinNo"].ToString() + "   " + DsBpo.Tables[0].Rows[i]["H_State"].ToString() + System.Environment.NewLine;
                string five = DsBpo.Tables[0].Rows[i]["O_City"].ToString() + "   " + DsBpo.Tables[0].Rows[i]["O_Address"].ToString() + "   " + DsBpo.Tables[0].Rows[i]["O_PinNo"].ToString();
                string image1 = first + second + third + four + five;

                create(image1, insertid);
            }

            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Image Created..." + "');", true);

        }
    }
    public void create(string text1, string id)
    {
        //====================== hard 18 normal 30 ================also change font folder
        string text = text1;
        //string[] fonts1 = new string[] { "myfont03-regular.ttf",  "myfont07.ttf","myfont08.ttf","myfonthard09.ttf",
        //    "myfont001-regular.ttf","myfont002-regular.ttf","myfont003-regular.ttf","myfont004-regular.ttf","myfont005-regular.ttf","myfont006-regular.ttf",
        //    "myfont007-regular.ttf","myfont008-regular.ttf","myfont009-regular.ttf","myfont010-regular.ttf"};
        string[] fonts1 = new string[] { "arial.ttf" };
        string fontname = fonts1[rm.Next(0, fonts1.Length)];
        Bitmap bitmap = new Bitmap(10, 10);

        PrivateFontCollection privateFontCollection = new PrivateFontCollection();
        privateFontCollection.AddFontFile(Server.MapPath("~/SuperAdmin/") + fontname);
        FontFamily fontFamily = privateFontCollection.Families[0];

        Font font = new Font(fontFamily, 18, FontStyle.Regular, GraphicsUnit.Pixel);
        Graphics graphics = Graphics.FromImage(bitmap);
        int width = (int)graphics.MeasureString(text, font).Width;
        int height = (int)graphics.MeasureString(text, font).Height;
        bitmap = new Bitmap(bitmap, new Size(width, height));
        graphics = Graphics.FromImage(bitmap);
        graphics.Clear(Color.White);
        graphics.SmoothingMode = SmoothingMode.AntiAlias;
        graphics.TextRenderingHint = TextRenderingHint.AntiAlias;
        graphics.DrawString(text, font, new SolidBrush(System.Drawing.Color.Black), 0, 5);
        graphics.Flush();
        graphics.Dispose();
        bitmap.Save(Server.MapPath("~/ClientImage/") + id + ".jpg", ImageFormat.Jpeg);
    }
    protected void Button1_Click(object sender, EventArgs e)
    {
        DataSet DsBpo = AD.SelectALL_Bpo_Data();

        for (int i = 0; i < DsBpo.Tables[0].Rows.Count; i++)
        {
            string insertid = DsBpo.Tables[0].Rows[i][0].ToString();

            if (File.Exists(Server.MapPath("~/ClientImage/" + insertid + ".jpg")))
            {
                File.Delete(Server.MapPath("~/ClientImage/" + insertid + ".jpg"));
            }
            else
            {
              
            }

            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + "Image Created..." + "');", true);

        }
    }
}
