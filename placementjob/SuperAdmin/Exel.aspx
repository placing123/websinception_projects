<%@ Page Language="C#" MasterPageFile="~/SuperAdmin/AdminMaster.master" AutoEventWireup="true"
    CodeFile="Exel.aspx.cs" Inherits="Admin_Log" Title="Untitled Page" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td align="left" valign="top" class="pro_tit">
                    Exel
                </td>
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
                    <asp:Button ID="btn_show" runat="server" Text="Show" OnClick="btn_show_Click" />
                </td>
            </tr>           
        </table>
    </td>
</asp:Content>
