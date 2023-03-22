<%@ Page Language="C#" AutoEventWireup="true" CodeFile="Entry.aspx.cs" Inherits="Admin_Entry" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>Entry</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
        .style1
        {
            width: 78px;
        }
        .style2
        {
            width: 44px;
        }
    </style>
</head>
<body>
    <form id="form1" runat="server">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td align="left" valign="top" class="hom_top">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td align="left" valign="top" style="padding-bottom: 20px;">
                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="50%" align="left" valign="top">
                                        <img src="images/hom_logo.png" width="432" height="119" />
                                    </td>
                                    <td width="50%" align="right" valign="top" class="hom_black_13_ari">
                                        Welcome<br />
                                        Good Morning!<br />
                                        <asp:Label ID="lbl_username" runat="server"></asp:Label>
                                        |
                                        <asp:LinkButton ID="b_logout" runat="server" OnClick="b_logout_Click">Log Out</asp:LinkButton>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td align="left" valign="top" class="das_min">
                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td align="left" valign="top" class="pro_tit">
                                        Entery
                                    </td>
                                </tr>
                                <tr>
                                    <td align="left" valign="top" class="das_box">
                                        <table>
                                            <tr>
                                                <td>
                                                    Name:
                                                </td>
                                                <td>
                                                    <asp:TextBox ID="txt_name" runat="server" Width="394px" Height="302px" TextMode="MultiLine"></asp:TextBox>
                                                </td>
                                                <td>
                                                </td>
                                                <td>
                                                    EmailId:
                                                </td>
                                                <td>
                                                    <asp:TextBox ID="txt_emailid" runat="server" Height="297px" TextMode="MultiLine"
                                                        Width="373px"></asp:TextBox>
                                                </td>
                                                <td>
                                                </td>
                                                <td class="style1">
                                                    Mobile No:
                                                </td>
                                                <td>
                                                    <asp:TextBox ID="txt_mobile" runat="server" Height="302px" TextMode="MultiLine" Width="234px"></asp:TextBox>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="style2">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="style2">
                                                </td>
                                                <td>
                                                    <asp:Button ID="btn_name" runat="server" Text="Add Name" OnClick="btn_name_Click" />
                                                </td>
                                                <td>
                                                </td>
                                                <td>
                                                </td>
                                                <td>
                                                    <asp:Button ID="btn_emailid" runat="server" Text="Add EmailId" 
                                                        onclick="btn_emailid_Click" />
                                                </td>
                                                <td>
                                                </td>
                                                <td class="style1">
                                                </td>
                                                <td>
                                                    <asp:Button ID="btn_mobile" runat="server" Text="Add Mobile" 
                                                        onclick="btn_mobile_Click" />
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <br />
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <br />
                                                </td>
                                                <td>
                                                </td>
                                                <td>
                                                </td>
                                                <td>
                                                </td>
                                                <td>
                                                </td>
                                                <td>
                                                </td>
                                                <td>
                                                    
                                                </td>
                                                <td>
                                                    
                                                </td>
                                            </tr>
                                                <table >
                                                    <tr>
                                                        <td>
                                                            Address:
                                                        </td>
                                                        <td>
                                                            <asp:TextBox ID="txt_address" runat="server" Height="387px" TextMode="MultiLine"
                                                                Width="742px"></asp:TextBox>
                                                        </td>
                                                        <td>
                                                            City:
                                                        </td>
                                                                                                                <td>
                                                            <asp:TextBox ID="txt_scp" runat="server" Height="386px" TextMode="MultiLine" 
                                                                Width="188px"></asp:TextBox>
                                                        </td>
                                                        <td>
                                                        Pincode:
                                                        </td>
                                                        <td>
                                                            <asp:TextBox ID="txt_pin" runat="server" Height="386px" TextMode="MultiLine" 
                                                                Width="188px"></asp:TextBox>
                                                        </td>
                                                        <td>
                                                        State
                                                        </td>
                                                        <td>
                                                            <asp:TextBox ID="txt_state" runat="server" Height="386px" TextMode="MultiLine" 
                                                                Width="188px"></asp:TextBox>
                                                        </td>
                                                        
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <br />
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                        </td>
                                                        <td>
                                                            <asp:Button ID="btn_add" runat="server" Text="Add Address" 
                                                                onclick="btn_add_Click" />
                                                        </td>
                                                         <td>
                                                        </td>                                                    
                                                        <td>
                                                         <asp:Button ID="btn_SCP" runat="server" Text="Add city" 
                                                                onclick="btn_SCP_Click" />
                                                        </td>
                                                        <td>
                                                        </td>
                                                         <td>
                                                        <asp:Button ID="btn_pin" runat="server" Text="Add Pincode" onclick="btn_pin_Click" />
                                                        </td>
                                                        <td>
                                                        </td>
                                                        <td>
                                                        <asp:Button ID="btn_state" runat="server" Text="Add State" 
                                                                onclick="btn_state_Click" />
                                                        </td>
                                                    </tr>
                                                </table>
                                            </tr>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td align="left" valign="top" class="pro_tit">
                            &nbsp;
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
    </form>
</body>
</html>
