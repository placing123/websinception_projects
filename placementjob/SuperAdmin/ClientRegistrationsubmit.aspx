<%@ Page Language="C#" MasterPageFile="~/SuperAdmin/AdminMaster.master" AutoEventWireup="true"
    CodeFile="ClientRegistrationsubmit.aspx.cs" Inherits="Admin_ClientRegistration"
    Title="Client Registration" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td align="left" valign="top" class="pro_tit">
                    Client Registration
                </td>
            </tr>
            <tr>
                <td>
                    <asp:Label ID="Label3" runat="server"></asp:Label>
                    <br />
                </td>
            </tr>
            <tr>
                <td>
                    <table border="1" frame="border" width="100%" style="color: #4b4b4b" cellspacing="8px"
                        width="100%">
                        <tr>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px" colspan="2">
                                <div class="mid_lef" style="width: 100%">
                                    <center>
                                        <asp:Label ID="lblmsg" runat="server" Visible="False" ForeColor="#009933"></asp:Label></center>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                Customer Id
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:Label ID="lblid" runat="server"></asp:Label>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Select Franchisee
                            </td>
                            <td>
                                <asp:DropDownList ID="drd_fran" runat="server" Height="22px" Width="150px">
                                </asp:DropDownList>
                            </td>
                        </tr>
                        <tr>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                Name
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:TextBox ID="txt_name" runat="server" Width="210px">
                                </asp:TextBox>
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator3" runat="server" ControlToValidate="txt_name"
                                    ErrorMessage="Enter Name" ValidationGroup="valgrp">
                                </asp:RequiredFieldValidator>
                            </td>
                        </tr>
                        <tr>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                Address
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:TextBox ID="txt_add" runat="server" Height="92px" TextMode="MultiLine" Width="211px">
                                </asp:TextBox>
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator4" runat="server" ControlToValidate="txt_add"
                                    ErrorMessage="Enter Address" ValidationGroup="valgrp">
                                </asp:RequiredFieldValidator>
                            </td>
                        </tr>
                        <tr>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                Company Name
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:TextBox ID="txt_compname" runat="server" Width="210px">
                                </asp:TextBox>
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator5" runat="server" ControlToValidate="txt_compname"
                                    ErrorMessage="Enter Company Name" ValidationGroup="valgrp">
                                </asp:RequiredFieldValidator>
                            </td>
                        </tr>
                        <tr>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                City
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:TextBox ID="txt_city" runat="server" Width="210px">
                                </asp:TextBox>
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator6" runat="server" ControlToValidate="txt_city"
                                    ErrorMessage="Enter City" ValidationGroup="valgrp">
                                </asp:RequiredFieldValidator>
                            </td>
                        </tr>
                        <tr>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                Mobile No.
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:TextBox ID="txt_mobno" runat="server" Width="210px">
                                </asp:TextBox>
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator7" runat="server" ControlToValidate="txt_mobno"
                                    ErrorMessage="Enter Mobile No" ValidationGroup="valgrp">
                                </asp:RequiredFieldValidator>
                                <asp:RegularExpressionValidator ID="RegularExpressionValidator1" runat="server" ControlToValidate="txt_mobno"
                                    ErrorMessage="Enter Valid Mobile No" ValidationGroup="valgrp" ValidationExpression="^[0-9]{10}">
                                </asp:RegularExpressionValidator>
                            </td>
                        </tr>
                        <tr>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                Email
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:TextBox ID="txt_email" runat="server" Width="210px" ValidationGroup="valgrp">
                                </asp:TextBox>
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator2" runat="server" ControlToValidate="txt_email"
                                    ErrorMessage="Enter Email" ValidationGroup="valgrp">
                                </asp:RequiredFieldValidator>
                                <asp:RegularExpressionValidator ID="RegularExpressionValidator2" runat="server" ControlToValidate="txt_email"
                                    ErrorMessage="Enter Valid Email Address" ValidationExpression="\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*"
                                    ValidationGroup="valgrp">
                                </asp:RegularExpressionValidator>
                            </td>
                        </tr>
                        <tr>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                Plan
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:DropDownList ID="drp_plan" runat="server" Width="150px">
                                </asp:DropDownList>
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator8" runat="server" ControlToValidate="drp_plan"
                                    InitialValue="- Select Plan -" ErrorMessage="Select Plan" ValidationGroup="valgrp">
                                </asp:RequiredFieldValidator>
                            </td>
                        </tr>
                        <tr>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                Style
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:RadioButton ID="rbtn_online" runat="server" Text="Online" GroupName="sty" />
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <asp:RadioButton ID="rbtn_offline" runat="server" Text="Offline" GroupName="sty" />
                            </td>
                        </tr>
                        <tr>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:ValidationSummary ID="ValidationSummary2" ShowMessageBox="true" ShowSummary="false"
                                    HeaderText="You must enter value in the following fields :" EnableClientScript="true"
                                    ValidationGroup="valgrp" runat="server" />
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:Button ID="Button1" runat="server" Text="Submit" ValidationGroup="valgrp" OnClick="Button1_Click" />
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </td>
</asp:Content>
