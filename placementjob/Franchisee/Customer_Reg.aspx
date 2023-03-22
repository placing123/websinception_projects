<%@ Page Title="" Language="C#" MasterPageFile="~/Franchisee/Franchisee.master" AutoEventWireup="true"
    CodeFile="Customer_Reg.aspx.cs" Inherits="Franchisee_PersonalDetails" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <table border="1" frame="border" width="100%" style="color: #4b4b4b" cellspacing="8px" width="100%">
        <tr>
            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px" colspan="2">
                <div class="mid_lef" style="width: 100%">
                    <div class="mid_lef_tit">
                        <span style="color: #16C0A7;">Customer Registration</span>
                    </div>
                    <br />
                </div>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: center">
                <asp:Label ID="lblmsg" runat="server" Visible="False" ForeColor="#009933" Style="text-align: center"></asp:Label>
            </td>
        </tr>
        <tr>
            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">Customer Id
            </td>
            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                <asp:Label ID="lblid" runat="server"></asp:Label>
            </td>
        </tr>
        <tr>
            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">Name
            </td>
            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                <asp:TextBox ID="txt_name" runat="server" Width="210px"></asp:TextBox>
                <asp:RequiredFieldValidator ID="RequiredFieldValidator3" runat="server" ControlToValidate="txt_name"
                    ErrorMessage="Enter Name" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
            </td>
        </tr>
        <tr>
            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">Address
            </td>
            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                <asp:TextBox ID="txt_add" runat="server" Height="92px" TextMode="MultiLine" Width="211px"></asp:TextBox>
                <asp:RequiredFieldValidator ID="RequiredFieldValidator4" runat="server" ControlToValidate="txt_add"
                    ErrorMessage="Enter Address" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
            </td>
        </tr>
        <%--<tr>
            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">Company Name
            </td>
            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                <asp:TextBox ID="txt_compname" runat="server" Width="210px"></asp:TextBox>
                <asp:RequiredFieldValidator ID="RequiredFieldValidator5" runat="server" ControlToValidate="txt_compname"
                    ErrorMessage="Enter Company Name" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
            </td>
        </tr>--%>
        <%--<tr>
            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">City
            </td>
            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                <asp:TextBox ID="txt_city" runat="server" Width="210px"></asp:TextBox>
                <asp:RequiredFieldValidator ID="RequiredFieldValidator6" runat="server" ControlToValidate="txt_city"
                    ErrorMessage="Enter City" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
            </td>
        </tr>--%>
        <tr>
            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">Mobile No.
            </td>
            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                <asp:TextBox ID="txt_mobno" runat="server" Width="210px"></asp:TextBox>
                <asp:RequiredFieldValidator ID="RequiredFieldValidator7" runat="server" ControlToValidate="txt_mobno"
                    ErrorMessage="Enter Mobile No" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                <asp:RegularExpressionValidator ID="RegularExpressionValidator1" runat="server" ControlToValidate="txt_mobno"
                    ErrorMessage="Enter Valid Mobile No" ValidationGroup="valgrp" ValidationExpression="^[0-9]{10}"></asp:RegularExpressionValidator>
            </td>
        </tr>
        <tr>
            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">Email
            </td>
            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                <asp:TextBox ID="txt_email" runat="server" Width="210px" ValidationGroup="valgrp"></asp:TextBox>
                <asp:RequiredFieldValidator ID="RequiredFieldValidator2" runat="server" ControlToValidate="txt_email"
                    ErrorMessage="Enter Email" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                <asp:RegularExpressionValidator ID="RegularExpressionValidator2" runat="server" ControlToValidate="txt_email"
                    ErrorMessage="Enter Valid Email Address" ValidationExpression="\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*"
                    ValidationGroup="valgrp"></asp:RegularExpressionValidator>
            </td>
        </tr>
        <tr>
            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">Plan
            </td>
            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                <asp:DropDownList ID="drp_plan" runat="server" Width="150px">
                </asp:DropDownList>
            </td>
        </tr>
         <tr>
            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">Caller Name
            </td>
            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                <asp:DropDownList ID="ddl_callername" runat="server" Width="150px">
                </asp:DropDownList>
            </td>
        </tr>
          <tr>
            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">Franchisee
            </td>
            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                <asp:DropDownList ID="ddl_franchisee" runat="server" Width="150px">
                    <asp:ListItem Selected="True">HO</asp:ListItem>
                    
                    <asp:ListItem>IND1</asp:ListItem>
                     <asp:ListItem>IND2</asp:ListItem>
                    <asp:ListItem>surat</asp:ListItem>
                    <asp:ListItem>AJIT</asp:ListItem>
                     <asp:ListItem>SHOIL</asp:ListItem>
                    <asp:ListItem>ICHAPORE</asp:ListItem>
                   
                </asp:DropDownList>
            </td>
        </tr>
		<tr>
            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">Agreement Page No
            </td>
            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                <asp:DropDownList ID="ddl_pageno" runat="server" Width="150px">
                    <asp:ListItem Selected="True">1</asp:ListItem>
                    
                    <asp:ListItem>2</asp:ListItem>
                     <asp:ListItem>3</asp:ListItem>
                    <asp:ListItem>4</asp:ListItem>
					 <asp:ListItem>5</asp:ListItem>
					 <asp:ListItem>6</asp:ListItem>
                   
                </asp:DropDownList>
            </td>
        </tr>
        <tr style="visibility: hidden;">
            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">Style
            </td>
            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                <asp:RadioButton ID="rbtn_online" Checked="true" runat="server" Text="Online"
                    GroupName="sty" />
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <asp:RadioButton ID="rbtn_offline" runat="server" Text="Offline"
                    GroupName="sty" />
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
</asp:Content>
