<%@ Page Language="C#" AutoEventWireup="true" CodeFile="changepassword.aspx.cs" Inherits="Admin_changepassword" MasterPageFile="~/SuperAdmin/AdminMaster.master" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <td>
    <td>
    <tr>
        <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="left" valign="top" class="pro_tit">Password Reset
                    </td>
                </tr>
                <tr>
                    <td>
                        <table style="margin: 50px; border-style: dashed; border-width: thin;" cellpadding="5px" cellspacing="10px">
                            <tr>
                                <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">Reg. Number:: 
                                </td>
                                <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                    <asp:TextBox ID="txt_regno" runat="server" Width="200" Height="20"></asp:TextBox>
                                </td>
                                 <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                    <asp:TextBox ID="txt_newpassword" runat="server" Width="200" Height="20"></asp:TextBox>
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td>
                                    <asp:RequiredFieldValidator ID="RequiredFieldValidator1" runat="server" ErrorMessage="Enter Reg. Number" ControlToValidate="txt_regno" ForeColor="Red" ValidationGroup="v1"></asp:RequiredFieldValidator>
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                    <asp:Button ID="but_submit" runat="server" Text="Reset Password" OnClick="but_submit_Click" ValidationGroup="v1" />
                                </td>
                                <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                    <asp:Button ID="Button1" runat="server" Text="Set New Password" OnClick="Button1_Click"  />
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    </td>
    </td>
</asp:Content>
