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
using DLL;
using System.IO;
using System.Text.RegularExpressions;

public partial class Customer_WorkloadSubmit : System.Web.UI.Page
{
    clsAdmin AD = new clsAdmin();
    clsFranchisee CF = new clsFranchisee();
    DataSet ds = new DataSet();
    string id;
    string Fstring;
    string change_random = "";
    string[] change_random1;
    string[] random_txtbox = { "txt_tbc", "txt_girno", "txt_panno", "txt_licenceno" };
    string[] random_txtbox1 = { "Name", "txt_Hadd", "txt_Oadd", "txt_mobno" };
    //    string[] random_txtbox = { "txt_tbc", "txt_name", "txt_email", "txt_girno", "txt_licenceno", "txt_Hadd" };
    MyCon mycon = new MyCon();


    protected void Page_Load(object sender, EventArgs e)
    {
        try
        {

            if (!IsPostBack)
            {
                if (lblcid.Text == "" && Session["Cus_Username"] == null)
                {
                    Response.Redirect("../Default.aspx");
                }
                else
                {
                    if (lblcid.Text == "" && Session["Cus_Username"] != null)
                    {
                        lblcid.Text = Session["Cus_Username"].ToString();
                        lblrno.Text = Session["rndno"].ToString();
                        Session["Cus_Username"] = lblcid.Text;
                        Session["rndno"] = lblrno.Text;
                    }
                    else
                    {
                        Session.Abandon();
                        Session["Cus_Username"] = lblcid.Text;
                        Session["rndno"] = lblrno.Text;
                    }

                }
            }


            FillTotalComplete();
            if (!IsPostBack)
            {
                Session["time"] = DateTime.Now.ToString();
                FillDropDown();
                FillImage();
                Filltext();
                lblcurrentpge.Text = drp_pagejump.SelectedItem.Text;

            }
            formdate();
        }
        catch (Exception ex)
        {
        }
    }

    public void formdate()
    {
        string datedt = mycon.AdExecScalar("select date from Tbl_Client_Bpo_Data with(nolock) where cid='" + lblcid.Text + "' and sr_no='" + drp_pagejump.SelectedItem.ToString() + "'");
        if (datedt == "0")
        {
            txt_tbc.Enabled = true;
            Name.Enabled = true;
            txt_email.Enabled = true;
            txt_mobno.Enabled = true;
            txt_gender.Enabled = true;
            txt_licenceno.Enabled = true;
            txt_girno.Enabled = true;
            txt_panno.Enabled = true;
            txt_Hadd.Enabled = true;
            txt_Hcity.Enabled = true;
            txt_Hpin.Enabled = true;
            txt_HState.Enabled = true;
            txt_Oadd.Enabled = true;
            txt_Ocity.Enabled = true;
            txt_Opincode.Enabled = true;
            txt_loanapproval.Enabled = true;
            txt_menno.Enabled = true;
            txt_af.Enabled = true;
            txt_nri.Enabled = true;
            txt_cp.Enabled = true;
            btnsubmit.Enabled = true;
            btnreset.Enabled = true;
        }
        else if (datedt != "0")
        {
            DateTime date = Convert.ToDateTime(datedt);
            if (DateTime.Now.Date > date)
            {
                txt_tbc.Enabled = false;
                Name.Enabled = false;
                txt_email.Enabled = false;
                txt_mobno.Enabled = false;
                txt_gender.Enabled = false;
                txt_licenceno.Enabled = false;
                txt_girno.Enabled = false;
                txt_panno.Enabled = false;
                txt_Hadd.Enabled = false;
                txt_Hcity.Enabled = false;
                txt_Hpin.Enabled = false;
                txt_HState.Enabled = false;
                txt_Oadd.Enabled = false;
                txt_Ocity.Enabled = false;
                txt_Opincode.Enabled = false;
                txt_loanapproval.Enabled = false;
                txt_menno.Enabled = false;
                txt_af.Enabled = false;
                txt_nri.Enabled = false;
                txt_cp.Enabled = false;
                btnsubmit.Enabled = false;
                btnreset.Enabled = false;
            }
            else
            {
                txt_tbc.Enabled = true;
                Name.Enabled = true;
                txt_email.Enabled = true;
                txt_mobno.Enabled = true;
                txt_gender.Enabled = true;
                txt_licenceno.Enabled = true;
                txt_girno.Enabled = true;
                txt_panno.Enabled = true;
                txt_Hadd.Enabled = true;
                txt_Hcity.Enabled = true;
                txt_Hpin.Enabled = true;
                txt_HState.Enabled = true;
                txt_Oadd.Enabled = true;
                txt_Ocity.Enabled = true;
                txt_Opincode.Enabled = true;
                txt_loanapproval.Enabled = true;
                txt_menno.Enabled = true;
                txt_af.Enabled = true;
                txt_nri.Enabled = true;
                txt_cp.Enabled = true;
                btnsubmit.Enabled = true;
                btnreset.Enabled = true;
            }
        }
    }

    public void msg(string cid)
    {
        DataTable dt = new DataTable();
        dt = mycon.FillDataTable("SELECT * FROM [tbl_msg] Where [cid]='" + cid + "'");
        if (dt.Rows[0][0].ToString() == "1")
        {
            first.Visible = false;
            lblmsg.Text = dt.Rows[0]["msg"].ToString();
            lblmsg.Visible = true;

        }

    }
    private void FillImage()
    {
        MainImg.Src = "~/ClientImage/(" + drp_pagejump.Text + ").jpg";
    }
    private void CleareText()
    {
        txt_tbc.Text = "";
        Name.Text = "";
        txt_email.Text = "";
        txt_mobno.Text = "";
        txt_gender.Text = "";
        txt_licenceno.Text = "";
        txt_girno.Text = "";
        txt_panno.Text = "";
        txt_Hadd.Text = "";
        txt_Hcity.Text = "";
        txt_Hpin.Text = "";
        txt_HState.Text = "";
        txt_Oadd.Text = "";
        txt_Ocity.Text = "";
        txt_Opincode.Text = "";
        txt_loanapproval.Text = "";
        txt_menno.Text = "";
        txt_af.Text = "";
        txt_nri.Text = "";
        txt_cp.Text = "";
    }

    private void FillDropDown()
    {
        try
        {
            AD.cid = lblcid.Text;
            ds = AD.Select_Client_Bpo_Data_Cid_SrNo();
            drp_pagejump.DataTextField = ds.Tables[0].Columns["sr_no"].ToString();
            drp_pagejump.DataValueField = ds.Tables[0].Columns["id"].ToString();
            drp_pagejump.DataSource = ds;
            drp_pagejump.DataBind();

            Random rand = new Random();
            //change_random1 = new string[2];
            AD.id = lblcid.Text;
            ds = AD.Select_Registration_All();
            if (ds.Tables[0].Rows[0]["Change_2"].ToString().Trim() == "1")
            {
                ds = AD.Select_Client_Bpo_Data_Cid_SrNo();
                for (int i = 0; i < 2000; i++)
                {
                    change_random += rand.Next(1, ds.Tables[0].Rows.Count).ToString() + ",";
                }
            }
            else if (ds.Tables[0].Rows[0]["Change_5"].ToString().Trim() == "1")
            {
                ds = AD.Select_Client_Bpo_Data_Cid_SrNo();
                for (int i = 0; i < 2000; i++)
                {
                    change_random += rand.Next(1, ds.Tables[0].Rows.Count).ToString() + ",";
                }
            }
            else
            {
                ds = AD.Select_Client_Bpo_Data_Cid_SrNo();
                for (int i = 0; i < 1000; i++)
                {
                    change_random += rand.Next(1, ds.Tables[0].Rows.Count).ToString() + ",";
                }
            }
            Session["random"] = change_random;
        }
        catch
        {

        }

    }
    private void Filltext()
    {
        CF.CId = drp_pagejump.SelectedValue;
        CF.Name = lblcid.Text;
        DataSet ds = CF.Get_Data_From_CID();

        txt_tbc.Text = ds.Tables[0].Rows[0]["Tbc_No"].ToString();
        Name.Text = ds.Tables[0].Rows[0]["Name"].ToString();
        txt_email.Text = ds.Tables[0].Rows[0]["EmailId"].ToString();
        txt_mobno.Text = ds.Tables[0].Rows[0]["MobileNo"].ToString();
        txt_gender.Text = ds.Tables[0].Rows[0]["Gender"].ToString();
        txt_licenceno.Text = ds.Tables[0].Rows[0]["LicenseNo"].ToString();
        txt_girno.Text = ds.Tables[0].Rows[0]["GirNo"].ToString();
        txt_panno.Text = ds.Tables[0].Rows[0]["PanNo"].ToString();
        txt_Hadd.Text = ds.Tables[0].Rows[0]["H_Address"].ToString();
        txt_Hcity.Text = ds.Tables[0].Rows[0]["H_City"].ToString();
        txt_Hpin.Text = ds.Tables[0].Rows[0]["H_PinNo"].ToString();
        txt_HState.Text = ds.Tables[0].Rows[0]["H_State"].ToString();
        txt_Oadd.Text = ds.Tables[0].Rows[0]["O_Address"].ToString();
        txt_Ocity.Text = ds.Tables[0].Rows[0]["O_City"].ToString();
        txt_Opincode.Text = ds.Tables[0].Rows[0]["O_PinNo"].ToString();
        txt_loanapproval.Text = ds.Tables[0].Rows[0]["LAL"].ToString();
        txt_menno.Text = ds.Tables[0].Rows[0]["MRNNo"].ToString();
        txt_af.Text = ds.Tables[0].Rows[0]["AF"].ToString();
        txt_nri.Text = ds.Tables[0].Rows[0]["NRI"].ToString();
        txt_cp.Text = ds.Tables[0].Rows[0]["CP"].ToString();
    }
    public void stylecheck()
    {
        AD.cid = lblcid.Text;
        ds = AD.Select_Registration_Style();
        if (ds.Tables[0].Rows[0][0].ToString() == "0")
        {
        }
        else
        {
            Response.Redirect("~/Customer/Offline_Client.aspx");
        }
    }
    protected void Page_PreRender(Object sender, EventArgs e)
    {
        txt_tbc.Attributes.Add("autocomplete", "off");
        Name.Attributes.Add("autocomplete", "off");
        txt_email.Attributes.Add("autocomplete", "off");
        txt_mobno.Attributes.Add("autocomplete", "off");
        txt_gender.Attributes.Add("autocomplete", "off");
        txt_licenceno.Attributes.Add("autocomplete", "off");
        txt_girno.Attributes.Add("autocomplete", "off");
        txt_panno.Attributes.Add("autocomplete", "off");
        txt_Hadd.Attributes.Add("autocomplete", "off");
        txt_Hcity.Attributes.Add("autocomplete", "off");
        txt_Hpin.Attributes.Add("autocomplete", "off");
        txt_HState.Attributes.Add("autocomplete", "off");
        txt_Oadd.Attributes.Add("autocomplete", "off");
        txt_Ocity.Attributes.Add("autocomplete", "off");
        txt_Opincode.Attributes.Add("autocomplete", "off");
        txt_loanapproval.Attributes.Add("autocomplete", "off");
        txt_menno.Attributes.Add("autocomplete", "off");
        txt_af.Attributes.Add("autocomplete", "off");
        txt_nri.Attributes.Add("autocomplete", "off");
        txt_cp.Attributes.Add("autocomplete", "off");
        // txtimgcode.Attributes.Add("autocomplete", "off");
    }
    public void FillTotalComplete()
    {
        CF.CId = lblcid.Text;

        CF.QueryFor = "Complete";
        DataSet ds = CF.Master_Client_BPO_Data();
        lblcompl.Text = ds.Tables[0].Rows[0][0].ToString();

        CF.QueryFor = "Total";
        DataSet ds1 = CF.Master_Client_BPO_Data();
        lbltotal.Text = ds1.Tables[0].Rows[0][0].ToString();
        CF.CId = lblcid.Text;
        DataSet dss = CF.Select_Status_Tbl_Registration_by_CID();
        if (dss.Tables[0].Rows[0][0].ToString() == "-1")
        {
            Response.Redirect("../default.aspx");
        }
        if (lblcompl.Text == lbltotal.Text)
        {
            // Page.ClientScript.RegisterStartupScript(this.GetType(), "alert", "alert('Your Workload is Completed Successfully');", true);
            lblmsg.Text = "Your Workload is Completed Successfully ";
            lblmsg.Visible = true;


            CF.CId = lblcid.Text;
            dss = CF.Select_Status_Tbl_Registration_by_CID();

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
            else if (dss.Tables[0].Rows[0][0].ToString() == "5")
            {
                lblmsg.Text = "Your Qc Is In Progress";
                lblmsg.Visible = true;
                first.Visible = false;
            }
            else if (dss.Tables[0].Rows[0][0].ToString() == "6")
            {
                lblmsg.Text = "Your ID IS Terminated Contact Your Franchisee about more detail.";
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
        }
        else
        {
            CF.CId = lblcid.Text;
            dss = CF.Select_Status_Tbl_Registration_by_CID();

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
            else if (dss.Tables[0].Rows[0][0].ToString() == "5")
            {
                lblmsg.Text = "Your Qc Is In Progress";
                lblmsg.Visible = true;
                first.Visible = false;
            }
            else if (dss.Tables[0].Rows[0][0].ToString() == "6")
            {
                lblmsg.Text = "Your ID IS Terminated Contact Your Franchisee about more detail.";
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
        }
    }
    public void FillDrp_Pageno()
    {
        int no = Convert.ToInt32(lbltotal.Text.Trim());

        CF.CId = lblcid.Text;
        DataSet ds = CF.Get_Id_From_Pagejump();

        drp_pagejump.DataTextField = ds.Tables[0].Columns[0].ToString();
        drp_pagejump.DataValueField = ds.Tables[0].Columns[1].ToString();
        drp_pagejump.DataSource = ds;
        drp_pagejump.DataBind();
    }
    public void GetImg()
    {
        try
        {
            CF.CId = lblcid.Text;
            CF.QueryFor = "GetID";

            DataSet ds = CF.Master_Client_BPO_Data();

            string photo = ds.Tables[0].Rows[0][0].ToString();
            MainImg.Src = "~/ClientImage/(" + photo + ").jpg";
        }
        catch
        { }
    }

    protected void txt_tbc_TextChanged(object sender, EventArgs e)
    {
        SetFocus(txt_gender);
    }
    protected void txt_gender_TextChanged(object sender, EventArgs e)
    {
        SetFocus(txt_email);
    }
    protected void txt_email_TextChanged(object sender, EventArgs e)
    {
        SetFocus(txt_loanapproval);
    }
    protected void txt_loanapproval_TextChanged(object sender, EventArgs e)
    {
        SetFocus(txt_mobno);
    }
    protected void txt_mobno_TextChanged(object sender, EventArgs e)
    {
        SetFocus(txt_panno);
    }
    protected void txt_panno_TextChanged(object sender, EventArgs e)
    {
        SetFocus(txt_girno);
    }
    protected void txt_girno_TextChanged(object sender, EventArgs e)
    {
        SetFocus(txt_licenceno);

    }
    protected void txt_licenceno_TextChanged(object sender, EventArgs e)
    {
        SetFocus(Name);

    }
    protected void txt_name_TextChanged(object sender, EventArgs e)
    {
        SetFocus(txt_Opincode);

    }

    protected void txt_Hadd_TextChanged(object sender, EventArgs e)
    {
        SetFocus(txt_Hpin);
    }
    protected void txt_Hpin_TextChanged(object sender, EventArgs e)
    {
        SetFocus(txt_Oadd);

    }
    protected void txt_Oadd_TextChanged(object sender, EventArgs e)
    {
        SetFocus(txt_Ocity);


    }
    protected void txt_HState_TextChanged(object sender, EventArgs e)
    {
        SetFocus(txt_Ocity);

    }
    public void change()
    {
        try
        {
            string[] name;
            AD.id = lblcid.Text;
            ds = AD.Select_Registration_All();
            Random rand = new Random();
            string random = Session["random"].ToString();

            change_random1 = random.Split(',');

            if (ds.Tables[0].Rows[0]["Change"].ToString().Trim() == "1" || ds.Tables[0].Rows[0]["Change_2"].ToString().Trim() == "1")
            {
                int no = drp_pagejump.SelectedIndex + 1;
                if (change_random1.Contains(no.ToString()))
                {
                    string txt_box = random_txtbox[rand.Next(0, random_txtbox.Length)];

                    TextBox txt = (TextBox)this.UpdatePanel1.FindControl(txt_box);
                    DataTable dt = new DataTable();
                    string change = "0";
                    dt = mycon.FillDataTable("select * from tbl_error");
                    bool keepGoing = true;
                    for (int i = 0; i < dt.Rows.Count && keepGoing; i++)
                    {
                        char correct = Convert.ToChar(dt.Rows[i]["correct"].ToString());
                        char error = Convert.ToChar(dt.Rows[i]["error"].ToString());

                        char[] text = txt.Text.ToCharArray();
                        for (int j = 0; j < text.Length && keepGoing; j++)
                        {
                            if (text[j].ToString() == correct.ToString())
                            {
                                text[j] = error;
                                txt.Text = new string(text);
                                change = "1";
                                AD.cid = lblcid.Text;
                                AD.srno = drp_pagejump.SelectedItem.Text;
                                AD.field = txt_box;
                                AD.error = error.ToString();
                                AD.correct = correct.ToString();
                                AD.Insert_Change();
                                keepGoing = false;
                                break;

                            }
                        }
                    }
                }
            }
        }
        catch (Exception)
        {

            throw;
        }

    }
    protected void txt_Ocity_TextChanged(object sender, EventArgs e)
    {
        try
        {
            string[] name;
            AD.id = lblcid.Text;
            ds = AD.Select_Registration_All();
            Random rand = new Random();
            string random = Session["random"].ToString();

            change_random1 = random.Split(',');

            if (ds.Tables[0].Rows[0]["Change"].ToString().Trim() == "1" || ds.Tables[0].Rows[0]["Change_2"].ToString().Trim() == "1")
            {
                int no = drp_pagejump.SelectedIndex + 1;
                if (change_random1.Contains(no.ToString()))
                {
                    string txt_box = random_txtbox[rand.Next(0, random_txtbox.Length)];

                    TextBox txt = (TextBox)this.UpdatePanel1.FindControl(txt_box);
                    DataTable dt = new DataTable();
                    string change = "0";
                    dt = mycon.FillDataTable("select * from tbl_error");
                    for (int i = 0; i < dt.Rows.Count; i++)
                    {
                        char correct = Convert.ToChar(dt.Rows[i]["correct"].ToString());
                        char error = Convert.ToChar(dt.Rows[i]["error"].ToString());

                        if (txt.Text.Contains(correct))
                        {
                            txt.Text = txt.Text.Replace(correct, error);
                            change = "1";
                            AD.cid = lblcid.Text;
                            AD.srno = drp_pagejump.SelectedItem.Text;
                            AD.field = txt_box;
                            AD.error = error.ToString();
                            AD.correct = correct.ToString();
                            AD.Insert_Change();
                            break;
                        }
                    }
                    //if (change == "0")
                    //{
                    //    name = Name.Text.Split(' ');
                    //    Name.Text = "";

                    //    Random rnd = new Random();

                    //    int iRandom = rnd.Next(1, name.Length - 1);
                    //    for (int i = 0; i < name.Length; i++)
                    //    {
                    //        if (i == iRandom)
                    //        {
                    //            Name.Text = Name.Text + "  " + name[i];
                    //        }
                    //        else if (i == 0)
                    //        {
                    //            Name.Text = Name.Text + name[i];
                    //        }
                    //        else
                    //        {
                    //            Name.Text = Name.Text + " " + name[i];
                    //        }

                    //    }
                    //    AD.cid = Session["Cus_Username"].ToString();
                    //    AD.srno = drp_pagejump.SelectedItem.Text;
                    //    AD.field = "Name";
                    //    AD.error = "Space";
                    //    AD.correct = "Space";
                    //    AD.Insert_Change();
                    //}
                    //if (txt.Text.Contains('O'))
                    //{
                    //    txt.Text = txt.Text.Replace('O', 'Q');
                    //}
                    //else if (txt.Text.Contains('Q'))
                    //{
                    //    txt.Text = txt.Text.Replace('Q', 'O');
                    //}
                    //else if (txt.Text.Contains('0'))
                    //{
                    //    txt.Text = txt.Text.Replace('0', 'o');
                    //}
                    //else if (txt.Text.Contains('o'))
                    //{
                    //    txt.Text = txt.Text.Replace('o', '0');
                    //}
                    //else if (txt.Text.Contains('9'))
                    //{
                    //    txt.Text = txt.Text.Replace('9', 'g');
                    //}
                    //else if (txt.Text.Contains('g'))
                    //{
                    //    txt.Text = txt.Text.Replace('g', '9');
                    //}
                    //else
                    //{
                    //    name = txt_name.Text.Split(' ');
                    //    txt_name.Text = "";

                    //    Random rnd = new Random();

                    //    int iRandom = rnd.Next(1, name.Length - 1);
                    //    for (int i = 0; i < name.Length; i++)
                    //    {
                    //        if (i == iRandom)
                    //        {
                    //            txt_name.Text = txt_name.Text + "  " + name[i];
                    //        }
                    //        else if (i == 0)
                    //        {
                    //            txt_name.Text = txt_name.Text + name[i];
                    //        }
                    //        else
                    //        {
                    //            txt_name.Text = txt_name.Text + " " + name[i];
                    //        }

                    //    }
                    //}
                }

            }
            else if (ds.Tables[0].Rows[0]["Change_5"].ToString().Trim() == "1")
            {
                string txt_box = random_txtbox1[rand.Next(0, random_txtbox1.Length)];

                TextBox txt = (TextBox)this.UpdatePanel1.FindControl(txt_box);
                name = txt.Text.Split(' ');
                txt.Text = "";

                Random rnd = new Random();

                int iRandom = rnd.Next(1, name.Length - 1);
                for (int i = 0; i < name.Length; i++)
                {
                    if (i == iRandom)
                    {
                        txt.Text = txt.Text + "  " + name[i];
                    }
                    else if (i == 0)
                    {
                        txt.Text = txt.Text + name[i];
                    }
                    else
                    {
                        txt.Text = txt.Text + " " + name[i];
                    }

                }
                AD.cid = lblcid.Text;
                AD.srno = drp_pagejump.SelectedItem.Text;
                AD.field = txt_box;
                AD.error = "Space";
                AD.correct = "Space";
                AD.Insert_Change();
            }





            //  Page.ClientScript.RegisterStartupScript(this.GetType(), "onload", someScript);

            ScriptManager.RegisterStartupScript(UpdatePanel1, UpdatePanel1.GetType(), "Success", "jqueryminjs();", true);

            //  ClientScript.RegisterStartupScript(GetType(), ClientID, "test();", true);
            //   SetFocus(txt_menno);
            //   this.MaintainScrollPositionOnPostBack = true;
            // ClientScript.RegisterStartupScript(GetType(), "MyKey", "clickOadd();", true);


        }
        catch
        {
        }

    }
    protected void txt_Hcity_TextChanged(object sender, EventArgs e)
    {
        SetFocus(txt_nri);

    }
    protected void txt_nri_TextChanged(object sender, EventArgs e)
    {

    }
    protected void txt_menno_TextChanged(object sender, EventArgs e)
    {
        SetFocus(txt_cp);

    }
    protected void txt_cp_TextChanged(object sender, EventArgs e)
    {
        //SetFocus(txt_af);

    }
    protected void drp_pagejump_SelectedIndexChanged(object sender, EventArgs e)
    {
        try
        {
            MainImg.Src = "~/ClientImage/(" + drp_pagejump.Text + ").jpg";
            Filltext();
            lblcurrentpge.Text = drp_pagejump.SelectedItem.Text;
            //populatecolors();

        }
        catch (Exception ex)
        {
        }


        //lblid.Text = drp_pagejump.SelectedValue;

        //int a = drp_pagejump.SelectedIndex + 1;
        //lblcurrentpge.Text = Convert.ToString(a);

        //CF.CId = drp_pagejump.SelectedValue;
        //CF.Name = Session["Cus_Username"].ToString();
        //DataSet ds = CF.Get_Data_From_CID();

        //string photo = ds.Tables[0].Rows[0][0].ToString();
        //MainImg.Src = "../Admin/ClientImage/" + photo + ".jpg";


        //txt_tbc.Text = ds.Tables[0].Rows[0]["Tbc_No"].ToString();
        //txt_name.Text = ds.Tables[0].Rows[0]["Name"].ToString();
        //txt_email.Text = ds.Tables[0].Rows[0]["EmailId"].ToString();
        //txt_mobno.Text = ds.Tables[0].Rows[0]["MobileNo"].ToString();
        //txt_gender.Text = ds.Tables[0].Rows[0]["Gender"].ToString();
        //txt_licenceno.Text = ds.Tables[0].Rows[0]["LicenseNo"].ToString();
        //txt_girno.Text = ds.Tables[0].Rows[0]["GirNo"].ToString();
        //txt_panno.Text = ds.Tables[0].Rows[0]["PanNo"].ToString();
        //txt_Hadd.Text = ds.Tables[0].Rows[0]["H_Address"].ToString();
        //txt_Hcity.Text = ds.Tables[0].Rows[0]["H_City"].ToString();
        //txt_Hpin.Text = ds.Tables[0].Rows[0]["H_PinNo"].ToString();
        //txt_HState.Text = ds.Tables[0].Rows[0]["H_State"].ToString();
        //txt_Oadd.Text = ds.Tables[0].Rows[0]["O_Address"].ToString();
        //txt_Ocity.Text = ds.Tables[0].Rows[0]["O_City"].ToString();
        //txt_Opincode.Text = ds.Tables[0].Rows[0]["O_PinNo"].ToString();
        //txt_loanapproval.Text = ds.Tables[0].Rows[0]["LAL"].ToString();
        //txt_menno.Text = ds.Tables[0].Rows[0]["MRNNo"].ToString();
        //txt_af.Text = ds.Tables[0].Rows[0]["AF"].ToString();
        //txt_nri.Text = ds.Tables[0].Rows[0]["NRI"].ToString();
        //txt_cp.Text = ds.Tables[0].Rows[0]["CP"].ToString();


    }
    protected void btn_imgformate_Click(object sender, EventArgs e)
    {
        ScriptManager.RegisterStartupScript(this, GetType(), "Success", "alert('New Issue is Created <br/> Email Notification is sent to Admin');", true);
    }
    protected void btnsubmit_Click(object sender, EventArgs e)
    {
        try
        {
            string time = mycon.AdExecScalar("select time from Tbl_Registration where cid='" + lblcid.Text + "'");
            System.Threading.Thread.Sleep(Convert.ToInt32(time));


            DataTable dt = new DataTable();
            dt = mycon.FillDataTable("Select login from Tbl_Registration where cid='" + lblcid.Text + "'");
            string rndno = dt.Rows[0][0].ToString();
            if (rndno.Trim() != lblrno.Text)
            {
                Session["login"] = "Multiple Login are not allow.";
                Session["rndno"] = "";
                Response.Redirect("../session.aspx");
            }

            CF.Id = Convert.ToInt32(drp_pagejump.SelectedValue);
            CF.CId = lblcid.Text;

            CF.Tbc_No = txt_tbc.Text;
            CF.Name = Name.Text;
            CF.EmailId = txt_email.Text;
            CF.MobileNo = txt_mobno.Text;
            CF.Gender = txt_gender.Text;
            CF.LicenseNo = txt_licenceno.Text;
            CF.GirNo = txt_girno.Text;
            CF.PanNo = txt_panno.Text;
            CF.H_Address = txt_Hadd.Text;
            CF.H_City = txt_Hcity.Text;
            CF.H_PinNo = txt_Hpin.Text;
            CF.H_State = txt_HState.Text;
            CF.O_Address = txt_Oadd.Text;
            CF.O_City = txt_Ocity.Text;
            CF.O_PinNo = txt_Opincode.Text;
            CF.LAL = txt_loanapproval.Text;
            CF.MRNNo = txt_menno.Text;
            CF.AF = txt_af.Text;
            CF.NRI = txt_nri.Text;
            CF.CP = txt_cp.Text;
            CF.Status = qc();
            CF.spaceerror = spaceerror();

            CF.Id = Convert.ToInt32(drp_pagejump.SelectedValue);
            CF.CId = lblcid.Text;
            CF.Name = Name.Text;

            CF.Reg_Date = Convert.ToString(DateTime.Now.Date.AddDays(2));
            CF.Update_Tbl_Client_Bpo_Data();


            CleareText();
            try
            {
                drp_pagejump.SelectedIndex = drp_pagejump.SelectedIndex + 1;
            }
            catch
            {
                drp_pagejump.SelectedIndex = 0;
            }
            FillImage();
            Filltext();
            lblcurrentpge.Text = Convert.ToString(drp_pagejump.SelectedIndex + 1);



            //    Log Code..................



            AD.cid = lblcid.Text;
            System.Web.HttpBrowserCapabilities browser = Request.Browser;
            string s = "       Browser >" + "Type = " + browser.Type + "Platform = " + browser.Platform + "";
            string detail;
            string strHostName = System.Net.Dns.GetHostName();
            String ipAddress = System.Web.HttpContext.Current.Request.UserHostAddress;

            HttpRequest currentRequest = HttpContext.Current.Request;
            ipAddress = currentRequest.ServerVariables["HTTP_X_FORWARDED_FOR"];
            if (ipAddress == null || ipAddress.ToLower() == "unknown")
                ipAddress = currentRequest.ServerVariables["REMOTE_ADDR"];

            AD.Work = strHostName + "  " + ipAddress + "  " + DateTime.Now + "  " + drp_pagejump.SelectedValue + "  " + Convert.ToString(drp_pagejump.SelectedIndex) + s;
            AD.Insert_Log();
            // populatecolors();
        }
        catch (Exception ex)
        {
            // populatecolors();
            string error = ex.ToString();
            mycon.ExecutQury("INSERT INTO [tbl_errorlog]([cid],[error],[date]) VALUES ('" + lblcid.Text + "','" + error + "','" + DateTime.Now + "')");
            //   lblerror.Text = ex.Message.ToString();
        }
    }
    private string qc()
    {
        string CTbc_No = txt_tbc.Text;
        string CName = Name.Text;
        string CEmailId = txt_email.Text;
        string CMobileNo = txt_mobno.Text;
        string CGender = txt_gender.Text;
        string CLicenseNo = txt_licenceno.Text;
        string CGirNo = txt_girno.Text;
        string CPanNo = txt_panno.Text;
        string CH_Address = txt_Hadd.Text;
        string CH_City = txt_Hcity.Text;
        string CH_PinNo = txt_Hpin.Text;
        string CH_State = txt_HState.Text;
        string CO_Address = txt_Oadd.Text;
        string CO_City = txt_Ocity.Text;
        string CO_PinNo = txt_Opincode.Text;
        string CLAL = txt_loanapproval.Text;
        string CMRNNo = txt_menno.Text;
        string CAF = txt_af.Text;
        string CNRI = txt_nri.Text;
        string CCP = txt_cp.Text;

        CF.CId = drp_pagejump.Text;
        CF.Name = lblcid.Text;

        DataSet dsBpo = CF.Get_Data_From_CID_bpo();



        string BTbc_No = dsBpo.Tables[0].Rows[0]["Tbc_No"].ToString();
        string BName = dsBpo.Tables[0].Rows[0]["Name"].ToString();
        string BEmailId = dsBpo.Tables[0].Rows[0]["EmailId"].ToString();
        string BMobileNo = dsBpo.Tables[0].Rows[0]["MobileNo"].ToString();
        string BGender = dsBpo.Tables[0].Rows[0]["Gender"].ToString();
        string BLicenseNo = dsBpo.Tables[0].Rows[0]["LicenseNo"].ToString();
        string BGirNo = dsBpo.Tables[0].Rows[0]["GirNo"].ToString();
        string BPanNo = dsBpo.Tables[0].Rows[0]["PanNo"].ToString();
        string BH_Address = dsBpo.Tables[0].Rows[0]["H_Address"].ToString();
        string BH_City = dsBpo.Tables[0].Rows[0]["H_City"].ToString();
        string BH_PinNo = dsBpo.Tables[0].Rows[0]["H_PinNo"].ToString();
        string BH_State = dsBpo.Tables[0].Rows[0]["H_State"].ToString();
        string BO_Address = dsBpo.Tables[0].Rows[0]["O_Address"].ToString();
        string BO_City = dsBpo.Tables[0].Rows[0]["O_City"].ToString();
        string BO_PinNo = dsBpo.Tables[0].Rows[0]["O_PinNo"].ToString();
        string BLAL = dsBpo.Tables[0].Rows[0]["LAL"].ToString();
        string BMRNNo = dsBpo.Tables[0].Rows[0]["MRNNo"].ToString();
        string BAF = dsBpo.Tables[0].Rows[0]["AF"].ToString();
        string BNRI = dsBpo.Tables[0].Rows[0]["NRI"].ToString();
        string BCP = dsBpo.Tables[0].Rows[0]["CP"].ToString();


        if (CTbc_No.ToString() == BTbc_No.ToString())
        {
            Fstring = "1" + ",";
        }
        else
        {
            Fstring = "0" + ",";
        }

        if (CName.ToString() == BName.ToString())
        {
            Fstring += "1" + ",";
        }
        else
        {
            Fstring += "0" + ",";
        }

        if (CEmailId.ToString() == BEmailId.ToString())
        {
            Fstring += "1" + ",";
        }
        else
        {
            Fstring += "0" + ",";
        }

        if (CMobileNo.ToString() == BMobileNo.ToString())
        {
            Fstring += "1" + ",";
        }
        else
        {
            Fstring += "0" + ",";
        }

        if (CGender.ToString() == BGender.ToString())
        {
            Fstring += "1" + ",";
        }
        else
        {
            Fstring += "0" + ",";
        }

        if (CLicenseNo.ToString() == BLicenseNo.ToString())
        {
            Fstring += "1" + ",";
        }
        else
        {
            Fstring += "0" + ",";
        }
        if (CGirNo.ToString() == BGirNo.ToString())
        {
            Fstring += "1" + ",";
        }
        else
        {
            Fstring += "0" + ",";
        }

        if (CPanNo.ToString() == BPanNo.ToString())
        {
            Fstring += "1" + ",";
        }
        else
        {
            Fstring += "0" + ",";
        }

        if (CH_Address.ToString() == BH_Address.ToString())
        {
            Fstring += "1" + ",";
        }
        else
        {
            Fstring += "0" + ",";
        }
        BH_City = BH_City.Replace('–', '-');
        if (CH_City.ToString() == BH_City.ToString())
        {
            Fstring += "1" + ",";
        }
        else
        {
            Fstring += "0" + ",";
        }

        if (CH_PinNo.ToString() == BH_PinNo.ToString())
        {
            Fstring += "1" + ",";
        }
        else
        {
            Fstring += "0" + ",";
        }

        if (CH_State.ToString() == BH_State.ToString())
        {
            Fstring += "1" + ",";
        }
        else
        {
            Fstring += "0" + ",";
        }

        if (CO_Address.ToString() == BO_Address.ToString())
        {
            Fstring += "1" + ",";
        }
        else
        {
            Fstring += "0" + ",";
        }
        BO_City = BO_City.Replace('–', '-');
        if (CO_City.ToString() == BO_City.ToString())
        {
            Fstring += "1" + ",";
        }
        else
        {
            Fstring += "0" + ",";
        }

        if (CO_PinNo.ToString() == BO_PinNo.ToString())
        {
            Fstring += "1" + ",";
        }
        else
        {
            Fstring += "0" + ",";
        }

        if (CLAL.ToString() == BLAL.ToString())
        {
            Fstring += "1" + ",";
        }
        else
        {
            Fstring += "0" + ",";
        }

        if (CMRNNo.ToString() == BMRNNo.ToString())
        {
            Fstring += "1" + ",";
        }
        else
        {
            Fstring += "0" + ",";
        }

        if (CAF.ToString() == BAF.ToString())
        {
            Fstring += "1" + ",";
        }
        else
        {
            Fstring += "0" + ",";
        }

        if (CNRI.ToString() == BNRI.ToString())
        {
            Fstring += "1" + ",";
        }
        else
        {
            Fstring += "0" + ",";
        }

        if (CCP.ToString() == BCP.ToString())
        {
            Fstring += "1";
        }
        else
        {
            Fstring += "0";
        }

        if (Fstring.Contains("0"))
        {
            return "3";
        }
        else
        {
            return "2";
        }
    }
    private string spaceerror()
    {
        int spacecount = 0;
        int errorcount = 0;

        string CTbc_No = txt_tbc.Text;
        string CName = Name.Text;
        string CEmailId = txt_email.Text;
        string CMobileNo = txt_mobno.Text;
        string CGender = txt_gender.Text;
        string CLicenseNo = txt_licenceno.Text;
        string CGirNo = txt_girno.Text;
        string CPanNo = txt_panno.Text;
        string CH_Address = txt_Hadd.Text;
        string CH_City = txt_Hcity.Text;
        string CH_PinNo = txt_Hpin.Text;
        string CH_State = txt_HState.Text;
        string CO_Address = txt_Oadd.Text;
        string CO_City = txt_Ocity.Text;
        string CO_PinNo = txt_Opincode.Text;
        string CLAL = txt_loanapproval.Text;
        string CMRNNo = txt_menno.Text;
        string CAF = txt_af.Text;
        string CNRI = txt_nri.Text;
        string CCP = txt_cp.Text;

        CF.CId = drp_pagejump.Text;
        CF.Name = lblcid.Text;

        DataSet dsBpo = CF.Get_Data_From_CID_bpo();



        string BTbc_No = dsBpo.Tables[0].Rows[0]["Tbc_No"].ToString();
        string BName = dsBpo.Tables[0].Rows[0]["Name"].ToString();
        string BEmailId = dsBpo.Tables[0].Rows[0]["EmailId"].ToString();
        string BMobileNo = dsBpo.Tables[0].Rows[0]["MobileNo"].ToString();
        string BGender = dsBpo.Tables[0].Rows[0]["Gender"].ToString();
        string BLicenseNo = dsBpo.Tables[0].Rows[0]["LicenseNo"].ToString();
        string BGirNo = dsBpo.Tables[0].Rows[0]["GirNo"].ToString();
        string BPanNo = dsBpo.Tables[0].Rows[0]["PanNo"].ToString();
        string BH_Address = dsBpo.Tables[0].Rows[0]["H_Address"].ToString();
        string BH_City = dsBpo.Tables[0].Rows[0]["H_City"].ToString();
        string BH_PinNo = dsBpo.Tables[0].Rows[0]["H_PinNo"].ToString();
        string BH_State = dsBpo.Tables[0].Rows[0]["H_State"].ToString();
        string BO_Address = dsBpo.Tables[0].Rows[0]["O_Address"].ToString();
        string BO_City = dsBpo.Tables[0].Rows[0]["O_City"].ToString();
        string BO_PinNo = dsBpo.Tables[0].Rows[0]["O_PinNo"].ToString();
        string BLAL = dsBpo.Tables[0].Rows[0]["LAL"].ToString();
        string BMRNNo = dsBpo.Tables[0].Rows[0]["MRNNo"].ToString();
        string BAF = dsBpo.Tables[0].Rows[0]["AF"].ToString();
        string BNRI = dsBpo.Tables[0].Rows[0]["NRI"].ToString();
        string BCP = dsBpo.Tables[0].Rows[0]["CP"].ToString();


        if (CTbc_No == BTbc_No)
        {
            Fstring = "1" + ",";
        }
        else
        {
            if (Regex.Replace(CTbc_No.Trim(), " {2,}", " ") == Regex.Replace(BTbc_No.Trim(), " {2,}", " "))
            {
                spacecount++;
            }
            errorcount++;
            Fstring = "0" + ",";
        }

        if (CName == BName)
        {
            Fstring += "1" + ",";
        }
        else
        {
            if (Regex.Replace(CName.Trim(), " {2,}", " ") == Regex.Replace(BName.Trim(), " {2,}", " "))
            {
                spacecount++;
            }
            errorcount++;
            Fstring += "0" + ",";
        }

        if (CEmailId == BEmailId)
        {
            Fstring += "1" + ",";
        }
        else
        {
            if (Regex.Replace(CEmailId.Trim(), " {2,}", " ") == Regex.Replace(BEmailId.Trim(), " {2,}", " "))
            {
                spacecount++;
            }
            errorcount++;
            Fstring += "0" + ",";
        }

        if (CMobileNo == BMobileNo)
        {
            Fstring += "1" + ",";
        }
        else
        {
            if (Regex.Replace(CMobileNo.Trim(), " {2,}", " ") == Regex.Replace(BMobileNo.Trim(), " {2,}", " "))
            {
                spacecount++;
            }
            errorcount++;
            Fstring += "0" + ",";
        }

        if (CGender == BGender)
        {
            Fstring += "1" + ",";
        }
        else
        {
            if (Regex.Replace(CGender.Trim(), " {2,}", " ") == Regex.Replace(BGender.Trim(), " {2,}", " "))
            {
                spacecount++;
            }
            errorcount++;
            Fstring += "0" + ",";
        }

        if (CLicenseNo == BLicenseNo)
        {
            Fstring += "1" + ",";
        }
        else
        {
            if (Regex.Replace(CLicenseNo.Trim(), " {2,}", " ") == Regex.Replace(BLicenseNo.Trim(), " {2,}", " "))
            {
                spacecount++;
            }
            errorcount++;
            Fstring += "0" + ",";
        }

        if (CGirNo == BGirNo)
        {
            Fstring += "1" + ",";
        }
        else
        {
            if (Regex.Replace(CGirNo.Trim(), " {2,}", " ") == Regex.Replace(BGirNo.Trim(), " {2,}", " "))
            {
                spacecount++;
            }
            errorcount++;
            Fstring += "0" + ",";
        }

        if (CPanNo == BPanNo)
        {
            Fstring += "1" + ",";
        }
        else
        {
            if (Regex.Replace(CPanNo.Trim(), " {2,}", " ") == Regex.Replace(BPanNo.Trim(), " {2,}", " "))
            {
                spacecount++;
            }
            errorcount++;
            Fstring += "0" + ",";
        }

        if (CH_Address == BH_Address)
        {
            Fstring += "1" + ",";
        }
        else
        {
            if (Regex.Replace(CH_Address.Trim(), " {2,}", " ") == Regex.Replace(BH_Address.Trim(), " {2,}", " "))
            {
                spacecount++;
            }
            errorcount++;
            Fstring += "0" + ",";
        }
        BH_City = BH_City.Replace('–', '-');
        if (CH_City == BH_City)
        {
            Fstring += "1" + ",";
        }
        else
        {
            if (Regex.Replace(CH_City.Trim(), " {2,}", " ") == Regex.Replace(BH_City.Trim(), " {2,}", " "))
            {
                spacecount++;
            }
            errorcount++;
            Fstring += "0" + ",";
        }

        if (CH_PinNo == BH_PinNo)
        {
            Fstring += "1" + ",";
        }
        else
        {
            if (Regex.Replace(CH_PinNo.Trim(), " {2,}", " ") == Regex.Replace(BH_PinNo.Trim(), " {2,}", " "))
            {
                spacecount++;
            }
            errorcount++;
            Fstring += "0" + ",";
        }

        if (CH_State == BH_State)
        {
            Fstring += "1" + ",";
        }
        else
        {
            if (Regex.Replace(CH_State.Trim(), " {2,}", " ") == Regex.Replace(BH_State.Trim(), " {2,}", " "))
            {
                spacecount++;
            }
            errorcount++;
            Fstring += "0" + ",";
        }

        if (CO_Address == BO_Address)
        {
            Fstring += "1" + ",";
        }
        else
        {
            if (Regex.Replace(CO_Address.Trim(), " {2,}", " ") == Regex.Replace(BO_Address.Trim(), " {2,}", " "))
            {
                spacecount++;
            }
            errorcount++;
            Fstring += "0" + ",";
        }
        BO_City = BO_City.Replace('–', '-');
        if (CO_City == BO_City)
        {
            Fstring += "1" + ",";
        }
        else
        {
            if (Regex.Replace(CO_City.Trim(), " {2,}", " ") == Regex.Replace(BO_City.Trim(), " {2,}", " "))
            {
                spacecount++;
            }
            errorcount++;
            Fstring += "0" + ",";
        }

        if (CO_PinNo == BO_PinNo)
        {
            Fstring += "1" + ",";
        }
        else
        {
            if (Regex.Replace(CO_PinNo.Trim(), " {2,}", " ") == Regex.Replace(BO_PinNo.Trim(), " {2,}", " "))
            {
                spacecount++;
            }
            errorcount++;
            Fstring += "0" + ",";
        }

        if (CLAL == BLAL)
        {
            Fstring += "1" + ",";
        }
        else
        {
            if (Regex.Replace(CLAL.Trim(), " {2,}", " ") == Regex.Replace(BLAL.Trim(), " {2,}", " "))
            {
                spacecount++;
            }
            errorcount++;
            Fstring += "0" + ",";
        }

        if (CMRNNo == BMRNNo)
        {
            Fstring += "1" + ",";
        }
        else
        {
            if (Regex.Replace(CMRNNo.Trim(), " {2,}", " ") == Regex.Replace(BMRNNo.Trim(), " {2,}", " "))
            {
                spacecount++;
            }
            errorcount++;
            Fstring += "0" + ",";
        }

        if (CAF == BAF)
        {
            Fstring += "1" + ",";
        }
        else
        {
            if (Regex.Replace(CAF.Trim(), " {2,}", " ") == Regex.Replace(BAF.Trim(), " {2,}", " "))
            {
                spacecount++;
            }
            errorcount++;
            Fstring += "0" + ",";
        }

        if (CNRI == BNRI)
        {
            Fstring += "1" + ",";
        }
        else
        {
            if (Regex.Replace(CNRI.Trim(), " {2,}", " ") == Regex.Replace(BNRI.Trim(), " {2,}", " "))
            {
                spacecount++;
            }
            errorcount++;
            Fstring += "0" + ",";
        }

        if (CCP == BCP)
        {
            Fstring += "1";
        }
        else
        {
            if (Regex.Replace(CCP.Trim(), " {2,}", " ") == Regex.Replace(BCP.Trim(), " {2,}", " "))
            {
                spacecount++;
            }
            errorcount++;
            Fstring += "0";
        }

        if (Fstring.Contains("0"))
        {
            if (spacecount == errorcount)
            {
                return "1";
            }
            return "0";
        }
        else
        {
            return "0";
        }
    }
    protected void btnreset_Click(object sender, EventArgs e)
    {
        Response.Redirect("Default.aspx");
    }
}
