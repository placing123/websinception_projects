<%@ Page Language="C#" MasterPageFile="~/Customer/Customer.master" AutoEventWireup="true"
    CodeFile="Report.aspx.cs" Inherits="Customer_Report" Title="Untitled Page" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <h3><i class="fa fa-angle-right"></i>&nbsp Work Report</h3>
    <div class="row mt">
        <div class="col-lg-12">
            <div align="center">
                <blink> <asp:Label ID="lbl_status_3_1" runat="server" Visible="False"></asp:Label></blink>
            </div>
        </div>
    </div>

    <div id="form_main" runat="server">
        <script type="text/javascript">
            document.onkeydown = ShowKeyCode;
            function ShowKeyCode(evt) {
                if (evt.keyCode == '123')
                    return false;
            }
        </script>
        <script type="text/javascript">
            var message = "";

            function clickIE() {
                if (document.all)
                { (message); return false; }
            }

            function clickNS(e) {
                if 
(document.layers || (document.getElementById && !document.all)) {
                    if (e.which == 2 || e.which == 3) { (message); return false; }
                }
            }
            if (document.layers)
            { document.captureEvents(Event.MOUSEDOWN); document.onmousedown = clickNS; }
            else
            { document.onmouseup = clickNS; document.oncontextmenu = clickIE; }

            document.oncontextmenu = new Function("return false")
        </script>
        <script language="JavaScript">

            var message = "Sorry, that function is disabled.\n\n";
            message += "All content is protected.";

            function click(e) {
                if (document.all) {
                    if (event.button == 2) {
                        alert(message);
                        return false;
                    }
                }
                if (document.layers) {
                    if (e.which == 3) {
                        alert(message);
                        return false;
                    }
                }
            }

            if (document.layers) {
                document.captureEvents(Event.MOUSEDOWN);
            }
            document.onmousedown = click;
        </script>
        <div class="fra_top" align="right">
            <table width="10%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td align="right" valign="top">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <%--  <td align="left" valign="top">
                                <a href="#">
                                    <img src="../images/previous.png" width="63" height="22" border="0" /></a>
                            </td>
                            <td align="left" valign="top" style="padding: 0px 5px;">
                                <a href="#" class="blu_14">1</a><a href="#" class="blu_14">2</a><a href="#" class="blu_14">3</a><a
                                    href="#" class="blu_14">4</a><a href="#" class="blu_14">5</a><a href="#" class="blu_14">6</a><a
                                        href="#" class="blu_14">7</a><a href="#" class="blu_14">8</a><a href="#" class="blu_14">9</a>.&nbsp;.&nbsp;.
                            </td>
                            <td align="right" valign="top">
                                <a href="#">
                                    <img src="../images/next.png" width="41" height="22" border="0" /></a>
                            </td>--%>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            <asp:LinkButton ID="btn_ascii" runat="server" OnClick="btn_ascii_Click">Form With ASCII</asp:LinkButton>
        </div>
        <div class="col-lg-12 form-panel">
            <div class="">
                <div style="float: left;">
                    <table style="width: 100%">
                        <tr>
                            <td align="center" colspan="3">
                                <blink>  <asp:Label ID="lblmsg" runat="server" Font-Size="Larger"></asp:Label></blink>
                            </td>
                        </tr>
                        <tr>
                            <td align="center" colspan="3">
                                <asp:Label ID="lbl_status" runat="server" Text=""></asp:Label>
                            </td>
                        </tr>
                        <tr>
                            <td style="width: 273px">
                                <br />
                            </td>
                        </tr>
                        <tr>
                            <td align="left" valign="top" style="width: 273px">Page Completed :&nbsp;
                        <asp:Label ID="lblcompl" runat="server"></asp:Label>
                                &nbsp;/
                        <asp:Label ID="lbltotal" runat="server"></asp:Label>
                            </td>
                            <td align="center" style="width: 423px">Page Jump to :&nbsp;
                        <asp:DropDownList ID="drp_pagejump" runat="server" AutoPostBack="true" OnSelectedIndexChanged="drp_pagejump_SelectedIndexChanged">
                        </asp:DropDownList>
                            </td>
                            <td align="right">Image NO:&nbsp;
                        <asp:Label ID="lblimgno" runat="server"></asp:Label>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3">
                                <asp:Label ID="lblid" runat="server" Visible="False"></asp:Label>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <div class="row mt">
            <div class="col-lg-12">
                <div class="col-lg-12 fra_mid_txt">
                    <div>
                        
						<center><img id="MainImg"  runat="server" alt="" border="0" style="cursor: pointer;height:370px;width:1000px" /></center>
                    </div>
                </div>
            </div>
        </div>
        <div class="row mt">
            <div class="col-lg-12 form-panel">
                <div class="col-lg-12">
                     <div class="col-lg-3">
                       Name :<br />
                        <asp:TextBox ID="txt_name" runat="server" class="form-control" AutoPostBack="True" OnTextChanged="txt_name_TextChanged"
                            ReadOnly="True">
                        </asp:TextBox>
                        <br />
                        <br />
                        <asp:Label ID="lbl_name" runat="server"></asp:Label>
                    </div>
                    <div class="col-lg-3">
                        Email ID :<br />
                        <asp:TextBox ID="txt_email" runat="server" class="form-control" AutoPostBack="True" OnTextChanged="txt_email_TextChanged"
                            ReadOnly="True">
                        </asp:TextBox>
                        <br />
                        <br />
                        <asp:Label ID="lbl_emailid" runat="server"></asp:Label>
                    </div>
                    <div class="col-lg-3">
                        Mobile No. :<br />
                        <asp:TextBox ID="txt_mobno" runat="server" class="form-control" AutoPostBack="True" OnTextChanged="txt_mobno_TextChanged"
                            ReadOnly="True">
                        </asp:TextBox>
                        <br />
                        <br />
                        <asp:Label ID="lbl_mobileno" runat="server"></asp:Label>
                    </div>
                
                    <div class="col-lg-3">
                        Gender :<br />
                        <asp:TextBox ID="txt_gender" runat="server" class="form-control" AutoPostBack="True" OnTextChanged="txt_gender_TextChanged"
                            ReadOnly="True">
                        </asp:TextBox>
                        <br />
                        <br />
                        <asp:Label ID="lbl_gender" runat="server"></asp:Label>
                    </div>
                    </div>
                <br />
                <br />
                <br />
                <div class="col-lg-12">
                    <div class="col-lg-3">
                        L. A. No. :<br />
                        <asp:TextBox ID="txt_tbc" runat="server" class="form-control" AutoPostBack="True" OnTextChanged="txt_tbc_TextChanged"
                            ReadOnly="True">
                        </asp:TextBox>
                        <br />
                        <br />
                        <asp:Label ID="lbl_tbcno" runat="server" Text=""></asp:Label>
                    </div>
                    
                    
                    <div class="col-lg-3">
					SRN :<br />
                        <asp:TextBox ID="txt_af" runat="server" class="form-control" AutoPostBack="True" ReadOnly="True"
                            OnTextChanged="txt_af_TextChanged" Width="150px">
                        </asp:TextBox>
                        <br />
                        <br />
                        <asp:Label ID="lbl_af" runat="server"></asp:Label>

                        
                    </div>
					<div class="col-lg-3">
                        Secure No. :<br />
                        <asp:TextBox ID="txt_panno" runat="server" class="form-control" AutoPostBack="True" OnTextChanged="txt_panno_TextChanged"
                            ReadOnly="True">
                        </asp:TextBox>
                        <br />
                        <br />
                        <asp:Label ID="lbl_panno" runat="server"></asp:Label>
                    </div>
					<div class="col-lg-3">
                           Center Code :<br />
                        <asp:TextBox ID="txt_licenceno" runat="server" class="form-control" AutoPostBack="True"
                            OnTextChanged="txt_licenceno_TextChanged" ReadOnly="True">
                        </asp:TextBox><br />
						<asp:Label ID="lbl_licenseno" runat="server"></asp:Label>
                    </div>
					 </div>
                <br />
                <br />
                <br />
                <div class="col-lg-12">
                    <div class="col-lg-2">
                        Registration No. :<br />
                        <asp:TextBox ID="txt_girno" runat="server" class="form-control" AutoPostBack="True" OnTextChanged="txt_girno_TextChanged"
                            ReadOnly="True">
                        </asp:TextBox>
                        <br />
                        <br />
                        <asp:Label ID="lbl_girno" runat="server"></asp:Label>
                    </div>
                   
                
                    <div class="col-lg-2">
                        CCN No. :<br />
                        <asp:TextBox ID="txt_menno" runat="server" class="form-control" AutoPostBack="True" OnTextChanged="txt_menno_TextChanged"
                            ReadOnly="True" Width="150px">
                        </asp:TextBox>
                        <br />
                        <br />
                        <asp:Label ID="lbl_mrnno" runat="server"></asp:Label>
                    </div>
                    
                    <div class="col-lg-2">
                        NCV :<br />
                        <asp:TextBox ID="txt_nri" runat="server" class="form-control" AutoPostBack="True" OnTextChanged="txt_nri_TextChanged"
                            ReadOnly="True" Width="150px">
                        </asp:TextBox>
                        <br />
                        <br />
                        <asp:Label ID="lbl_NRI" runat="server"></asp:Label>
                    </div>
                    <div class="col-lg-2">
                        CN :<br />
                        <asp:TextBox ID="txt_cp" runat="server" class="form-control" AutoPostBack="True" OnTextChanged="txt_cp_TextChanged"
                            ReadOnly="True" Width="150px">
                        </asp:TextBox>
                        <br />
                        <br />
                        <asp:Label ID="lbl_cp" runat="server"></asp:Label>
                    </div>
                    <div class="col-lg-4">
                        Loan Amount:<br />
                        <asp:TextBox ID="txt_loanapproval" runat="server" class="form-control" AutoPostBack="True"
                            OnTextChanged="txt_loanapproval_TextChanged" ReadOnly="True">
                        </asp:TextBox>
                        <br />
                        <br />
                        <asp:Label ID="lbl_LAL" runat="server"></asp:Label>
                    </div>
                </div>
                <br />
                <br />
                <br />
                <br />
                <div class="col-lg-12">
                    <div class="col-lg-3">
                        <b>Home </b>City:<br />
                        <asp:TextBox ID="txt_Hcity" runat="server" class="form-control" AutoPostBack="True" OnTextChanged="txt_Hcity_TextChanged"
                            ReadOnly="True">
                        </asp:TextBox>
                        <br />
                        <br />
                        <asp:Label ID="lbl_home_city" runat="server"></asp:Label>
                    </div>
					<div class="col-lg-3">
                        <b>Home</b> PinCode :<br />
                        <asp:TextBox ID="txt_Hpin" runat="server" class="form-control" AutoPostBack="True" OnTextChanged="txt_Hpin_TextChanged"
                            ReadOnly="True">
                        </asp:TextBox>
                        <br />
                        <br />
                        <asp:Label ID="lbl_home_pincode" runat="server"></asp:Label>
                    </div>
                    <div class="col-lg-3">
                        State :<br />
                        <asp:TextBox ID="txt_HState" runat="server" class="form-control" AutoPostBack="True" OnTextChanged="txt_HState_TextChanged"
                            ReadOnly="True">
                        </asp:TextBox>
                        <br />
                        <br />
                        <asp:Label ID="lbl_home_state" runat="server"></asp:Label>
                    </div>
                    <div class="col-lg-3">
                        Address :<br />
                        <asp:TextBox ID="txt_Hadd" runat="server" class="form-control" TextMode="MultiLine" Height="60px"
                            AutoPostBack="True" OnTextChanged="txt_Hadd_TextChanged" ReadOnly="True">
                        </asp:TextBox>
                        <br />
                        <br />

                        <br />
                        <asp:Label ID="lbl_home_address" runat="server"></asp:Label>
                    </div>
                    
                </div>
                <br />
                <br />
                <br />
                <div class="col-lg-12">
                    <div class="col-lg-4">
                        <b>Office </b>City :<br />
                        <asp:TextBox ID="txt_Ocity" runat="server" class="form-control" AutoPostBack="True" OnTextChanged="txt_Ocity_TextChanged"
                            ReadOnly="True">
                        </asp:TextBox>
                        <br />
                        <br />
                        <asp:Label ID="lbl_office_city" runat="server"></asp:Label>
                    </div>
					 <div class="col-lg-4">
                       <b>Office</b> PinCode :<br />
                        <asp:TextBox ID="txt_Opincode" runat="server" class="form-control" AutoPostBack="True" OnTextChanged="txt_Opincode_TextChanged"
                            ReadOnly="True">
                        </asp:TextBox>
                        <br />
                        <br />
                        <asp:Label ID="lbl_office_pincode" runat="server"></asp:Label>
                    </div>
                    <div class="col-lg-4">
                        Address :<br />
                        <asp:TextBox ID="txt_Oadd" runat="server" class="form-control" TextMode="MultiLine" Height="60px"
                            AutoPostBack="True" OnTextChanged="txt_Oadd_TextChanged" ReadOnly="True">
                        </asp:TextBox>
                        <br />
                        <br />
                        <br />
                        <asp:Label ID="lbl_office_address" runat="server"></asp:Label>
                    </div>
                   


                </div>

                <div class="fra_for_bot">
                    <div class="min">
                        <div class="txt">
                        </div>
                        <div class="form">
                        </div>
                    </div>
                    <div class="min">
                        <div class="txt">
                        </div>
                        <div class="form">
                        </div>
                    </div>
                    <div class="min">
                        <div class="txt">
                        </div>
                        <div class="form">
                        </div>
                    </div>
                    <div class="min">
                        <div class="txt">
                        </div>
                        <div class="form">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="fra_top">
        <table width="10%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
                <td align="center" valign="top">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <%--  <td align="left" valign="top">
                                <a href="#">
                                    <img src="../images/previous.png" width="63" height="22" border="0" /></a>
                            </td>
                            <td align="left" valign="top" style="padding: 0px 5px;">
                                <a href="#" class="blu_14">1</a><a href="#" class="blu_14">2</a><a href="#" class="blu_14">3</a><a
                                    href="#" class="blu_14">4</a><a href="#" class="blu_14">5</a><a href="#" class="blu_14">6</a><a
                                        href="#" class="blu_14">7</a><a href="#" class="blu_14">8</a><a href="#" class="blu_14">9</a>.&nbsp;.&nbsp;.
                            </td>
                            <td align="right" valign="top">
                                <a href="#">
                                    <img src="../images/next.png" width="41" height="22" border="0" /></a>
                            </td>--%>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </div>

</asp:Content>
