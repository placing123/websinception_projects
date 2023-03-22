<%@ Page Language="C#" MasterPageFile="~/Customer/Customer.master" AutoEventWireup="true"
    CodeFile="WorkSpace.aspx.cs" Inherits="Customer_Report" Title="Untitled Page" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <div align="center">
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
        </div>
        <div style="float: left;">
            <table style="width: 100%">
                           </tr>
                <tr>
                    <td style="width: 273px">
                        <br />
                    </td>
                </tr>
                <tr>
                    <td align="left" valign="top" style="width: 273px">
                        Page Completed :&nbsp;
                        <asp:Label ID="lblcompl" runat="server"></asp:Label>
                        &nbsp;/
                        <asp:Label ID="lbltotal" runat="server"></asp:Label>
                    </td>
                    <td align="center" style="width: 423px">
                        Page Jump to :&nbsp;
                        <asp:DropDownList ID="drp_pagejump" runat="server" AutoPostBack="true" OnSelectedIndexChanged="drp_pagejump_SelectedIndexChanged">
                        </asp:DropDownList>
                    </td>
                    <td align="right" style="padding-left:150px;">
                        
                    <a href="../images/Help.jpg" rel="clearbox[Gallery]" title="Surat">Image Help</a>
                    </td>
                    
                </tr>
                <tr>
                    <td colspan="3">
                        <asp:Label ID="lblid" runat="server" Visible="False"></asp:Label>Current Page:&nbsp;
                        <asp:Label ID="lblimgno" runat="server"></asp:Label>
                    </td>
                </tr>
            </table>
        </div>
        <div class="fra_mid">
            <div class="fra_mid_min">
                <div class="fra_mid_txt">
                    <img id="MainImg" runat="server" alt="" border="0" style="cursor: pointer; width: 839px;
                        height: 206px;" />
                </div>
                <div class="fra_mid_for">
                    <div class="fra_for_top">
                        <div class="lef">
                            <div class="lef_txt">
                                TBC Security No. :</div>
                            <div class="lef_form">
                                <asp:TextBox ID="txt_tbc" runat="server" class="forms3" AutoPostBack="True" OnTextChanged="txt_tbc_TextChanged"
                                    ReadOnly="True"></asp:TextBox>
                            </div>
                        </div>
                        <div class="log">
                            <a href="#" target="_blank">
                                <%--<img src="../images/tb_logo.png" width="61" height="61" border="0" />--%></a></div>
                    </div>
                    <div class="fra_for_mid">
                        <div class="min">
                            <div class="sub">
                                <div class="txt">
                                    Name :</div>
                                <div class="form">
                                    <asp:TextBox ID="txt_name" runat="server" class="forms" AutoPostBack="True" OnTextChanged="txt_name_TextChanged"
                                        ReadOnly="True"></asp:TextBox>
                                </div>
                            </div>
                            <div class="sub">
                                <div class="txt">
                                    Mobile No. :</div>
                                <div class="form">
                                    <asp:TextBox ID="txt_mobno" runat="server" class="forms" AutoPostBack="True" OnTextChanged="txt_mobno_TextChanged"
                                        ReadOnly="True"></asp:TextBox>
                                </div>
                            </div>
                            <div class="sub">
                                <div class="txt">
                                    Gender :</div>
                                <div class="form">
                                    <asp:TextBox ID="txt_gender" runat="server" class="forms" AutoPostBack="True" OnTextChanged="txt_gender_TextChanged"
                                        ReadOnly="True"></asp:TextBox>
                                </div>
                            </div>
                            <div class="sub">
                                <div class="txt">
                                    License No. :</div>
                                <div class="form">
                                    <asp:TextBox ID="txt_licenceno" runat="server" class="forms" AutoPostBack="True"
                                        OnTextChanged="txt_licenceno_TextChanged" ReadOnly="True"></asp:TextBox>
                                </div>
                            </div>
                            <br />
                            <div class="sub">
                                <div class="txt" style="font-size: x-large;">
                                    <br />
                                    Home&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                </div>
                            </div>
                            <div class="sub">
                                <div class="txt">
                                    Address :</div>
                                <div class="form">
                                    <asp:TextBox ID="txt_Hadd" runat="server" class="forms" TextMode="MultiLine" Height="109px"
                                        Width="267px" AutoPostBack="True" OnTextChanged="txt_Hadd_TextChanged" ReadOnly="True"></asp:TextBox>
                                </div>
                            </div>
                            <div class="sub">
                                <div class="txt">
                                    City:</div>
                                <div class="form">
                                    <asp:TextBox ID="txt_Hcity" runat="server" class="forms" AutoPostBack="True" OnTextChanged="txt_Hcity_TextChanged"
                                        ReadOnly="True"></asp:TextBox>
                                </div>
                            </div>
                            <div class="sub">
                                <div class="txt">
                                    State :</div>
                                <div class="form">
                                    <asp:TextBox ID="txt_HState" runat="server" class="forms" AutoPostBack="True" OnTextChanged="txt_HState_TextChanged"
                                        ReadOnly="True"></asp:TextBox>
                                </div>
                            </div>
                            <div class="sub">
                                <div class="txt">
                                    Pin Code No. :</div>
                                <div class="form">
                                    <asp:TextBox ID="txt_Hpin" runat="server" class="forms" AutoPostBack="True" OnTextChanged="txt_Hpin_TextChanged"
                                        ReadOnly="True"></asp:TextBox>
                                </div>
                            </div>
                        </div>
                        <div class="min" style="float: right;">
                            <div class="sub">
                                <div class="txt">
                                    Email ID :</div>
                                <div class="form">
                                    <asp:TextBox ID="txt_email" runat="server" class="forms" AutoPostBack="True" OnTextChanged="txt_email_TextChanged"
                                        ReadOnly="True"></asp:TextBox>
                                </div>
                            </div>
                            <div class="sub">
                                <div class="txt">
                                    GIR No. :</div>
                                <div class="form">
                                    <asp:TextBox ID="txt_girno" runat="server" class="forms" AutoPostBack="True" OnTextChanged="txt_girno_TextChanged"
                                        ReadOnly="True"></asp:TextBox>
                                </div>
                            </div>
                            <div class="sub">
                                <div class="txt">
                                    PAN No. :</div>
                                <div class="form">
                                    <asp:TextBox ID="txt_panno" runat="server" class="forms" AutoPostBack="True" OnTextChanged="txt_panno_TextChanged"
                                        ReadOnly="True"></asp:TextBox>
                                </div>
                            </div>
                            <div class="sub">
                                <div class="txt">
                                    Loan Approval Limit :</div>
                                <div class="form">
                                    <asp:TextBox ID="txt_loanapproval" runat="server" class="forms" AutoPostBack="True"
                                        OnTextChanged="txt_loanapproval_TextChanged" ReadOnly="True"></asp:TextBox>
                                </div>
                            </div>
                        </div>
                        <br />
                        <div class="sub">
                            <div class="txt" style="font-size: x-large;">
                                <br />
                                Office&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            </div>
                        </div>
                        <div class="sub">
                            <div class="txt">
                                Address :</div>
                            <div class="form">
                                <asp:TextBox ID="txt_Oadd" runat="server" class="forms" TextMode="MultiLine" Height="109px"
                                    Width="267px" AutoPostBack="True" OnTextChanged="txt_Oadd_TextChanged" ReadOnly="True"></asp:TextBox>
                            </div>
                        </div>
                        <div class="sub">
                            <div class="txt">
                                City :</div>
                            <div class="form">
                                <asp:TextBox ID="txt_Ocity" runat="server" class="forms" AutoPostBack="True" OnTextChanged="txt_Ocity_TextChanged"
                                    ReadOnly="True"></asp:TextBox>
                            </div>
                        </div>
                        <div class="sub">
                            <div class="txt">
                                Pin Code No. :</div>
                            <div class="form">
                                <asp:TextBox ID="txt_Opincode" runat="server" class="forms" AutoPostBack="True" OnTextChanged="txt_Opincode_TextChanged"
                                    ReadOnly="True"></asp:TextBox>
                            </div>
                        </div>
                    </div>
                    <div class="fra_for_bot">
                        <div class="min">
                            <div class="txt">
                                MRN No. :</div>
                            <div class="form">
                                <asp:TextBox ID="txt_menno" runat="server" class="forms4" AutoPostBack="True" OnTextChanged="txt_menno_TextChanged"
                                    ReadOnly="True"></asp:TextBox>
                            </div>
                        </div>
                        <div class="min">
                            <div class="txt">
                                AF :</div>
                            <div class="form">
                                <asp:TextBox ID="txt_af" runat="server" class="forms4" AutoPostBack="True" ReadOnly="True"
                                    OnTextChanged="txt_af_TextChanged"></asp:TextBox>
                            </div>
                        </div>
                        <div class="min">
                            <div class="txt">
                                NRI :</div>
                            <div class="form">
                                <asp:TextBox ID="txt_nri" runat="server" class="forms4" AutoPostBack="True" OnTextChanged="txt_nri_TextChanged"
                                    ReadOnly="True"></asp:TextBox>
                            </div>
                        </div>
                        <div class="min">
                            <div class="txt">
                                CP :</div>
                            <div class="form">
                                <asp:TextBox ID="txt_cp" runat="server" class="forms4" AutoPostBack="True" OnTextChanged="txt_cp_TextChanged"
                                    ReadOnly="True"></asp:TextBox>
                            </div>
                        </div>
                    </div>
                    <div class="fra_mid_but">
                    <table width="10%" border="0" align="center" cellpadding="0" cellspacing="0">
                        <tr>
                            <td align="left" valign="top">
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td align="left" valign="top" style="padding-right: 10px;">
                                            <asp:ImageButton ID="btnsubmit" runat="server" Width="113" Height="39" border="0"
                                                CssClass="fra_mid_but" ImageUrl="../images/submit.png" OnClick="btnsubmit_Click" />
                                        </td>
                                        <td align="left" valign="top">
                                            <asp:ImageButton ID="btnreset" runat="server" Width="113" Height="39" border="0"
                                                CssClass="fra_mid_but" ImageUrl="../images/reset.png" OnClick="btnreset_Click" />
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </div>
                </div>
            </div>
        </div>
       
    </div>
</asp:Content>
