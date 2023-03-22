<%@ Page Title="" Language="C#" MasterPageFile="~/SuperAdmin/AdminMaster.master" AutoEventWireup="true"
    CodeFile="formlockunlock.aspx.cs" Inherits="Admin_AutoFill" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td align="left" valign="top" class="pro_tit">AutoFill
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
                                <asp:Button ID="btn_unlock_range" runat="server" Text="Unlock Form Range" OnClick="btn_unlock_range_Click" />
                            </td>
                        </tr>
                        <tr>
                            <td>Form :-
                            </td>
                            <td>
                                <asp:TextBox runat="server" ID="txt_form" Height="196px" TextMode="MultiLine" Width="210px"></asp:TextBox>
                            </td>
                            <td>
                                <asp:Button ID="btn_unlock_form" runat="server" Text="Unlock Forms" OnClick="btn_unlock_form_Click"  />
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </td>
</asp:Content>
