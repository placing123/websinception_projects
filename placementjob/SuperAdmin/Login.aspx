<%@ Page Title="" Language="C#" MasterPageFile="~/SuperAdmin/AdminMaster.master" AutoEventWireup="true"
    CodeFile="Login.aspx.cs" Inherits="Admin_Login" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td align="left" valign="top" class="pro_tit">
                   Cleare Login
                </td>
            </tr>
            <tr>
                <td>
                    <br />
                </td>
            </tr>
            <tr>
                <td>
                    <table>
                        <tr>
                            <td>
                                &nbsp;&nbsp; &nbsp;
                            </td>
                            <td>
                                Customer Id :
                            </td>
                            <td>
                                <asp:TextBox ID="txt_cid" runat="server"></asp:TextBox>
                            </td>
                            <td>
                                &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;
                            </td>
                            <td>
                                <asp:Button ID="btn_cler" runat="server" Text="Cleare" Width="84px" 
                                    onclick="btn_cler_Click" />
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </td>
</asp:Content>
