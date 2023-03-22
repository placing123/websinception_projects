<%@ Page Title="" Language="C#" MasterPageFile="~/SuperAdmin/AdminMaster.master" AutoEventWireup="true" CodeFile="Logout.aspx.cs" Inherits="Admin_Logout" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" Runat="Server">

   <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td align="left" valign="top" class="pro_tit">
                    Log
                    Out</td>
            </tr>
        </table>
        <table style="width: 100%">
            <tr>
                <td style="width: 85px">
                    &nbsp;
                </td>
                <td>
                    &nbsp;
                </td>
            </tr>
            <tr>
                <td style="width: 85px">
                    &nbsp;
                </td>
                <td>
                    &nbsp;
                </td>
            </tr>
            <tr>
                <td style="width: 85px">
                    Client ID
                </td>
                <td>
                    <asp:TextBox ID="txt_cid" runat="server"></asp:TextBox>
                    <asp:RequiredFieldValidator ID="RequiredFieldValidator1" runat="server" 
                        ControlToValidate="txt_cid" ErrorMessage="Enter Customer Id" 
                        ValidationGroup="val"></asp:RequiredFieldValidator>
                </td>
            </tr>
            <tr>
                <td style="width: 85px">
                    &nbsp;
                </td>
                <td>
                    &nbsp;
                </td>
            </tr>
            <tr>
                <td style="width: 85px">
                    &nbsp;
                </td>
                <td>
                    <asp:Button ID="btn_show" runat="server" Text="Shubmit" 
                        OnClick="btn_show_Click" ValidationGroup="val" />
                </td>
            </tr>
            <tr>
                <td style="width: 85px">
                    &nbsp;
                </td>
                <td>
                    &nbsp;
                </td>
            </tr>
            <tr>
                <td style="width: 85px">
                    &nbsp;
                </td>
                <td>
                    &nbsp;</td>
            </tr>
        </table>
    </td>
</asp:Content>

