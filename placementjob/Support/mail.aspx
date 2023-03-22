<%@ Page Title="" Language="C#" MasterPageFile="~/Support/AdminMaster.master" AutoEventWireup="true" ValidateRequest="false"
    CodeFile="mail.aspx.cs" Inherits="Admin_AutoFill" %>

<%@ Register Assembly="FreeTextBox" Namespace="FreeTextBoxControls" TagPrefix="FTB" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td align="left" valign="top" class="pro_tit">Reminder Mail
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
                                <asp:TextBox ID="txt_cid" runat="server" Height="196px" TextMode="MultiLine" Width="210px"></asp:TextBox>
                            </td>
                            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <asp:TextBox ID="txt_number" runat="server"></asp:TextBox>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <asp:Button ID="btn_send" runat="server" Text="Send Reminder Mail" OnClick="btn_send_Click" />
                            </td>
                            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <asp:Button ID="btn_warningmail" runat="server" Text="Send Warning Mail" OnClick="btn_warningmail_Click" />
                            </td>
                            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <asp:Button ID="btn_warningmailfail" runat="server" Text="Send Warning Mail" OnClick="btn_warningmailfail_Click" />
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            Not Submitted  (6000)</td>

                            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            Fail (4000)</td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td>
                    <table>
                        <tr>
                            <td>Bank Details :-
                                
                            </td>
                            <td>
                                <FTB:FreeTextBox ID="FreeTextBox1" runat="server" Width="400px" Height="200px"></FTB:FreeTextBox>
                            </td>
                            <td>
                                <asp:Button ID="btn_bankdetails" runat="server" Text="Update Bank Details" OnClick="btn_bankdetails_Click" />
                            </td>
                        </tr>
                    </table>
                </td>

            </tr>
        </table>
    </td>
</asp:Content>
