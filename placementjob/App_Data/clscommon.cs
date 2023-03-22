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

/// <summary>
/// Summary description for clscommon
/// </summary>
public class clscommon
{
	public clscommon()
	{
		//
		// TODO: Add constructor logic here
		//
	}

    public static void CheckConnection()
    {
        try
        {
            using (SqlConnection cn = new SqlConnection(clscommon.GetConnectionString))
            {
                cn.Open();
            }
        }
        catch
        {
            System.Web.HttpContext.Current.Session["alternate"] = "MyConnection";

        }
    }

    public static string GetConnectionString
    {
        get
        {
            if (System.Web.HttpContext.Current.Session["alternate"] == null)
                return System.Configuration.ConfigurationManager.ConnectionStrings["MyConnection"].ToString();
            else
                return System.Configuration.ConfigurationManager.ConnectionStrings["MyConnection"].ToString();
        }
    }

    public static string Encode(string sData)
    {
        try
        {
            byte[] encData_byte = new byte[sData.Length];
            encData_byte = System.Text.Encoding.UTF8.GetBytes(sData);
            string encodedData = Convert.ToBase64String(encData_byte);
            return encodedData;
        }
        catch (Exception ex)
        {
            throw new Exception("Error in Encode" + ex.Message);
        }
    }

    public static string Decode(string sData)
    {
        try
        {
            System.Text.UTF8Encoding encoder = new System.Text.UTF8Encoding();
            System.Text.Decoder utf8Decode = encoder.GetDecoder();
            byte[] todecode_byte = Convert.FromBase64String(sData);
            int charCount = utf8Decode.GetCharCount(todecode_byte, 0, todecode_byte.Length);
            char[] decoded_char = new char[charCount];
            utf8Decode.GetChars(todecode_byte, 0, todecode_byte.Length, decoded_char, 0);
            string result = new String(decoded_char);
            return result;
        }
        catch (Exception ex)
        {
            throw new Exception("Error in Decode" + ex.Message);
        }
    }

    public static string PADL(string sString, int iArea, string sFillChar)
    {
        int i;
        string Result = "";

        Result = sString.Trim();
        if (Result.Length < iArea)
        {
            for (i = Result.Length; i < iArea; i++)
            {
                Result = sFillChar + Result;
            }
        }
        return Result;
    }

    public static string PADL(int iString, int iArea, string sFillChar)
    {
        return PADL(iString.ToString(), iArea, sFillChar);
    }

    public static string PADR(string sString, int iArea, string sFillChar)
    {
        int i;
        string Result = "";

        Result = sString.Trim();
        if (Result.Length < iArea)
        {
            for (i = Result.Length; i < iArea; i++)
            {
                Result += sFillChar;
            }
        }
        Result = Result.Substring(0, iArea);
        return Result;
    }

    public static string PADR(int iString, int iArea, string sFillChar)
    {
        return PADR(iString.ToString(), iArea, sFillChar);
    }
}
