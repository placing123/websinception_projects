<%@ Page Title="" Language="C#" MasterPageFile="~/Support/AdminMaster.master" AutoEventWireup="true"
    CodeFile="AutoFill.aspx.cs" Inherits="Admin_AutoFill" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td align="left" valign="top" class="pro_tit">AutoFill
                </td>
            </tr>
            <tr>
                <td>
                    <asp:Label ID="lbl_error" runat="server" ForeColor="Red"></asp:Label></td>
            </tr>
            <tr>
                <td>
                    <table>
                        <tr>
                            <td>Customer Id :-
                            </td>
                            <td>
                                <asp:TextBox ID="txt_cid" runat="server"></asp:TextBox>
                            </td>

                        </tr>
                        <tr>
                            <td>From :-</td>
                            <td>
                                <asp:TextBox ID="txt_from" runat="server"></asp:TextBox>
                            </td>
                            <td>To :-</td>
                            <td>
                                <asp:TextBox ID="txt_to" runat="server"></asp:TextBox>
                            </td>
                            <td>
                                <asp:Button ID="btn_blanck" runat="server" OnClick="btn_blanck_Click"
                                    Text="Blanck" />
                            </td>
                        </tr>
                        <tr>
                            <td>Percentage :-
                            </td>
                            <td>
                                <asp:TextBox ID="txt_per" runat="server"></asp:TextBox>
                            </td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <asp:Button ID="btnsubmit" runat="server" Text="Fill" OnClick="btnsubmit_Click" Width="50px" />
                            </td>
                        </tr>
                        <tr>
                            <td>Form :-
                            </td>
                            <td>
                                <asp:TextBox runat="server" ID="txt_form" Height="196px" TextMode="MultiLine" Width="210px"></asp:TextBox>
                            </td>
                            <td>
                                <asp:Button runat="server" ID="txt_form_fill" Text="Fill Form" OnClick="txt_form_fill_Click" />
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </td>
</asp:Content>
