<%@ Page Title="" Language="C#" MasterPageFile="~/SuperAdmin/AdminMaster.master" AutoEventWireup="true"
    CodeFile="notice.aspx.cs" Inherits="Admin_AutoFill" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td align="left" valign="top" class="pro_tit">Generate Notice
                </td>
            </tr>
            <tr>
                <td></td>
            </tr>
            <tr>
                <td>
                    <table>
                        <tr>
                            <td>Customer Id :
                            </td>
                            <td>
                                <asp:TextBox ID="txt_cid" runat="server"></asp:TextBox>
                            </td>
                            <td>
                                <asp:FileUpload ID="FileUpload1" runat="server" />
                            </td>
                        </tr>
                        <tr>
                            <td>Amount :
                            </td>
                            <td>
                                <asp:DropDownList ID="DropDownList1" runat="server">
                                    <asp:ListItem Selected="True" Value="Four thousand only">4000</asp:ListItem>
                                    <asp:ListItem Value="Six thousand only">6000</asp:ListItem>
                                </asp:DropDownList>
                            </td>
                            <td>
                                <asp:FileUpload ID="FileUpload2" runat="server" />
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <asp:Button ID="btn_generate" runat="server" Text="Generate Notice" OnClick="btn_generate_Click" />
                            </td>
                            <td>
                                <asp:FileUpload ID="FileUpload3" runat="server" />
                            </td>
                            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <asp:Button ID="btn_sendnotice" runat="server" Text="Send Notice" OnClick="btn_sendnotice_Click" />
                            </td>
                            
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td>
                                <asp:FileUpload ID="FileUpload4" runat="server" />
                            </td>
                        </tr>
                         <tr>
                            <td></td>
                            <td></td>
                            <td>
                                <asp:FileUpload ID="FileUpload5" runat="server" />
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td>
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                </td>
            </tr>
            <tr>
                <td>
                    <table>
                        <tr>
                            <td>Customer Id :
                            </td>
                            <td>
                                <asp:TextBox ID="txt_cidnoc" runat="server"></asp:TextBox>
                            </td>
                        </tr>
                        <tr>
                            <td>Amount :
                            </td>
                            <td>
                                <asp:TextBox ID="txt_amountnoc" runat="server"></asp:TextBox>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <asp:Button ID="btn_sendnoc" runat="server" Text="Send NOC" OnClick="btn_sendnoc_Click" />
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </td>
</asp:Content>
