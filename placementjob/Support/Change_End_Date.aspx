<%@ Page Language="C#" MasterPageFile="~/Support/AdminMaster.master" AutoEventWireup="true"
    CodeFile="Change_End_Date.aspx.cs" Inherits="Admin_Date_Change" Title="Change Date" %>

<%@ Register Assembly="AjaxControlToolkit" Namespace="AjaxControlToolkit" TagPrefix="asp" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <td>
        <asp:ToolkitScriptManager ID="ToolkitScriptManager1" runat="server">
        </asp:ToolkitScriptManager>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td align="left" valign="top" class="pro_tit">
                    Date End Change
                </td>
            </tr>
            <tr>
                <td>
                    <br />
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;</td>
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
                            <td style="height: 34px">
                                &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;
                            </td>
                            <td style="border-style: dashed; border-width: thin; height: 34px; width: 164px; padding-left:20px;">
                                Customer Id:
                            </td>
                            <td style="border-style: dashed; border-width: thin; height: 34px;">
                                <asp:TextBox ID="txt_cid" runat="server" ontextchanged="txt_cid_TextChanged" 
                                    AutoPostBack="True"></asp:TextBox>
                            </td>
                        </tr>
                        <tr>
                            <td style="height: 36px">
                                <br />
                            </td>
                            <td style="border-style: dashed; border-width: thin; width: 164px; height: 36px ;padding-left:20px;">
                                Current End Date:</td>
                            <td style="border-style: dashed; border-width: thin; height: 36px;">
                                <asp:Label ID="lbl_current_date" runat="server"></asp:Label>
                            </td>
                        </tr>
                        <tr>
                            <td style="height: 36px">
                            </td>
                            <td style="border-style: dashed; border-width: thin; height: 36px; width: 164px; padding-left:20px;">
                                Select Date:
                            </td>
                            <td style="border-style: dashed; border-width: thin; height: 36px;">
                                <asp:TextBox ID="txt_date" runat="server" Height="24px" Width="179px"></asp:TextBox>
                                <asp:CalendarExtender ID="CalendarExtender1" runat="server" TargetControlID="txt_date">
                                </asp:CalendarExtender>
                            </td>
                        </tr>
                        <tr>
                            <td style="height: 18px">
                                <br />
                            </td>
                            <td style="border-style: dashed; border-width: thin; height: 18px; width: 164px;">
                            </td>
                            <td style="border-style: dashed; border-width: thin; height: 18px;">
                            </td>
                        </tr>
                        <tr>
                            <td>
                            </td>
                            <td style="border-style: dashed; border-width: thin; width: 164px;">
                            </td>
                            <td style="border-style: dashed; border-width: thin">
                                <asp:Button ID="btn_date_change" runat="server" Text="Update" 
                                    OnClick="btn_date_change_Click" style="height: 26px" />
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <br />
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </td>
</asp:Content>
