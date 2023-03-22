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

/// <summary>
/// Summary description for MySession
/// </summary>
public class MySession
{
    private MySession()
    {
        CompName = string.Empty;
        UserName = string.Empty;
        AdminLog = string.Empty;
        Imei = string.Empty;
        RegId = string.Empty;
        StartTime = string.Empty;
        StopTime = string.Empty;
        
        MobileId = 0;
        
    }

    // Gets the current session.
    public static MySession Current
    {
        get
        {
            MySession session =
            (MySession)HttpContext.Current.Session["__MySession__"];
            if (session == null)
            {
                session = new MySession();
                HttpContext.Current.Session["__MySession__"] = session;
            }
            return session;
        }
    }

    // **** add your session properties here, e.g like this:
    public string CompName { get; set; }
    public string UserName { get; set; }
    public string AdminLog { get; set; }
    public string Imei { get; set; }
    public string RegId { get; set; }
    
    public int LoginId { get; set; }
    public int MobileId { get; set; }
    public int CompId { get; set; }
    public int UserType { get; set; }
    public string StartTime { get; set; }
    public string StopTime { get; set; }
}
