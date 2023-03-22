<%@ Page Title="" Language="C#" MasterPageFile="~/SuperAdmin/AdminMaster.master" AutoEventWireup="true"
    CodeFile="spaceerror.aspx.cs" Inherits="Admin_Login" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td align="left" valign="top" class="pro_tit">Space Error</td>
            </tr>
            <tr>
                <td>
                    <asp:Label ID="Label1" runat="server"></asp:Label>
                    <br />
                </td>
            </tr>
            <tr>
                <td>
                    <table>
                        <tr>
                            <td>&nbsp;&nbsp; &nbsp;
                            </td>
                            <td>Customer Id :
                            </td>
                            <td>
                                <asp:TextBox ID="txt_cid" runat="server"></asp:TextBox>
                            </td>
                            <td>Form :
                            </td>
                            <td>
                                <asp:TextBox ID="txt_form" runat="server">
                                </asp:TextBox>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td>
                                <asp:Button ID="btn_cler" runat="server" Text="Go" Width="84px"
                                    OnClick="btn_cler_Click" OnClientClick="this.disabled=true;" UseSubmitBehavior="false" />
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </td>
</asp:Content>
