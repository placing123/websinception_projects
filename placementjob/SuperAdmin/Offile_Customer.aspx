<%@ Page Language="C#" MasterPageFile="~/SuperAdmin/AdminMaster.master" AutoEventWireup="true"
    CodeFile="Offile_Customer.aspx.cs" Inherits="Admin_Offile_Customer" Title="Untitled Page" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td align="left" valign="top" class="pro_tit">
                    Offline Customer
                </td>
            </tr>
        </table>
        <br />
        <table style="width: 100%">
            <tr>
                <td style="width: 154px">
                    Customer ID:
                </td>
                <td>
                    <asp:TextBox ID="txt_cid" runat="server" Style="margin-left: 0px"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td>
                    Upload File:
                </td>
                <td>
                    <asp:FileUpload ID="FileUpload1" runat="server" />
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <asp:Button ID="Button1" runat="server" Text="First" OnClick="Button1_Click" />
                    &nbsp;&nbsp;&nbsp;
                    <asp:Button ID="Button2" runat="server" Text="second" OnClick="Button2_Click" />
                </td>
            </tr>
        </table>
    </td>
</asp:Content>
