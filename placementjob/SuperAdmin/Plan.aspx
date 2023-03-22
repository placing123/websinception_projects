<%@ Page Language="C#" MasterPageFile="~/SuperAdmin/AdminMaster.master" AutoEventWireup="true"
    CodeFile="Plan.aspx.cs" Inherits="Admin_Plan" Title="Plan" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td align="left" valign="top" class="pro_tit">
                    Plan
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
                            <td style="width: 29px">
                                &nbsp;
                            </td>
                            <td style="width: 102px">
                                Plan No:
                            </td>
                            <td style="width: 165px">
                                <asp:TextBox ID="txt_plan_no" runat="server" ReadOnly="True"></asp:TextBox>
                            </td>
                            <td>
                                &nbsp;</td>
                        </tr>
                        <tr>
                            <td style="width: 29px">
                                &nbsp;
                            </td>
                            <td style="width: 102px">
                                Plan Name:
                            </td>
                            <td style="width: 165px">
                                <asp:TextBox ID="txt_plan_name" runat="server"></asp:TextBox>
                            </td>
                            <td>
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator1" runat="server" 
                                    ControlToValidate="txt_plan_name" ErrorMessage="Enter Plan Name" 
                                    ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                            </td>
                        </tr>
                        <tr>
                            <td style="width: 29px">
                                &nbsp;
                            </td>
                            <td style="width: 102px">
                                Days:
                            </td>
                            <td style="width: 165px">
                                <asp:TextBox ID="txt_days" runat="server"></asp:TextBox>
                            </td>
                            <td>
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator2" runat="server" 
                                    ControlToValidate="txt_days" ErrorMessage="Enter Plan Days" 
                                    ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                            </td>
                        </tr>
                        <tr>
                            <td style="width: 29px">
                                &nbsp;
                            </td>
                            <td style="width: 102px">
                                Forms:
                            </td>
                            <td style="width: 165px">
                                <asp:TextBox ID="txt_form" runat="server"></asp:TextBox>
                            </td>
                            <td>
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator3" runat="server" 
                                    ControlToValidate="txt_form" ErrorMessage="Enter No Of Forms" 
                                    ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                            </td>
                        </tr>
                        <tr>
                            <td style="width: 29px">
                                &nbsp;
                            </td>
                            <td style="width: 102px">
                                Qc-CutOff:
                            </td>
                            <td style="width: 165px">
                                <asp:TextBox ID="txt_qccutoff" runat="server"></asp:TextBox>
                            </td>
                            <td>
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator4" runat="server" 
                                    ControlToValidate="txt_qccutoff" ErrorMessage="Enter CutOff" 
                                    ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                            </td>
                        </tr>
                        <tr>
                            <td style="width: 29px">
                                &nbsp;
                            </td>
                            <td style="width: 102px">
                                Fees:
                            </td>
                            <td style="width: 165px">
                                <asp:TextBox ID="txt_fees" runat="server"></asp:TextBox>
                            </td>
                            <td>
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator5" runat="server" 
                                    ControlToValidate="txt_fees" ErrorMessage="Enter Fees" 
                                    ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                            </td>
                        </tr>
                        <tr>
                            <td style="width: 29px">
                                &nbsp;
                            </td>
                            <td style="width: 102px">
                                Agreement:                           </td>
                            <td style="width: 165px">
                                <asp:FileUpload ID="fu_agreement" runat="server" />
                            </td>
                            <td>
                                <asp:RegularExpressionValidator ID="RegularExpressionValidator2" ControlToValidate="fu_agreement"
                                    ValidationExpression="^.*\.((d|D)(o|O)(c|C)(x|X))$" runat="server" ErrorMessage="Invalid File Type....(Use only .docx file)"
                                    ValidationGroup="valgrp" />
                            </td>
                        </tr>
                        <tr>
                            <td style="width: 29px">
                                &nbsp;
                            </td>
                            <td style="width: 102px">
                                &nbsp;
                            </td>
                            <td style="width: 165px">
                                &nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td style="width: 29px">
                                &nbsp;
                            </td>
                            <td style="width: 102px">
                                &nbsp;
                            </td>
                            <td style="width: 165px">
                                <asp:Button ID="btn_submit" runat="server" OnClick="btn_submit_Click" Text="Submit"
                                    ValidationGroup="valgrp" />
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td>
                </td>
            </tr>
            <tr>
                <td>
                    <asp:GridView ID="grd_plan" runat="server" AutoGenerateColumns="False" CellPadding="4"
                        ForeColor="#333333" GridLines="None" Width="100%">
                        <RowStyle BackColor="#F7F6F3" ForeColor="#333333" HorizontalAlign="Center" />
                        <Columns>
                            <asp:TemplateField HeaderText="Id" SortExpression="Id" Visible="False">
                                <ItemTemplate>
                                    <asp:Label ID="Label1" runat="server" Text='<%# Bind("Id") %>'></asp:Label>
                                </ItemTemplate>
                                <EditItemTemplate>
                                    <asp:TextBox ID="TextBox1" runat="server" Text='<%# Bind("Id") %>'></asp:TextBox>
                                </EditItemTemplate>
                            </asp:TemplateField>
                            <asp:TemplateField HeaderText="PId" SortExpression="PId">
                                <ItemTemplate>
                                    <asp:Label ID="Label2" runat="server" Text='<%# Bind("PId") %>'></asp:Label>
                                </ItemTemplate>
                                <EditItemTemplate>
                                    <asp:TextBox ID="TextBox2" runat="server" Text='<%# Bind("PId") %>'></asp:TextBox>
                                </EditItemTemplate>
                            </asp:TemplateField>
                            <asp:TemplateField HeaderText="PName" SortExpression="PName">
                                <ItemTemplate>
                                    <asp:Label ID="Label3" runat="server" Text='<%# Bind("PName") %>'></asp:Label>
                                </ItemTemplate>
                                <EditItemTemplate>
                                    <asp:TextBox ID="TextBox3" runat="server" Text='<%# Bind("PName") %>'></asp:TextBox>
                                </EditItemTemplate>
                            </asp:TemplateField>
                            <asp:TemplateField HeaderText="Form" SortExpression="Page">
                                <EditItemTemplate>
                                    <asp:TextBox ID="TextBox6" runat="server" Text='<%# Bind("Form") %>'></asp:TextBox>
                                </EditItemTemplate>
                                <ItemTemplate>
                                    <asp:Label ID="Label6" runat="server" Text='<%# Bind("Form") %>'></asp:Label>
                                </ItemTemplate>
                            </asp:TemplateField>
                            <asp:TemplateField HeaderText="Qc-CutOff" SortExpression="QcCutOff">
                                <ItemTemplate>
                                    <asp:Label ID="Label7" runat="server" Text='<%# Bind("QcCutOff") %>'></asp:Label>
                                </ItemTemplate>
                                <EditItemTemplate>
                                    <asp:TextBox ID="TextBox7" runat="server" Text='<%# Bind("QcCutOff") %>'></asp:TextBox>
                                </EditItemTemplate>
                            </asp:TemplateField>
                            <asp:TemplateField HeaderText="Days" SortExpression="Days">
                                <ItemTemplate>
                                    <asp:Label ID="Label4" runat="server" Text='<%# Bind("Days") %>'></asp:Label>
                                </ItemTemplate>
                                <EditItemTemplate>
                                    <asp:TextBox ID="TextBox4" runat="server" Text='<%# Bind("Days") %>'></asp:TextBox>
                                </EditItemTemplate>
                            </asp:TemplateField>
                            <asp:TemplateField HeaderText="Fees" SortExpression="Fees">
                                <ItemTemplate>
                                    <asp:Label ID="Label5" runat="server" Text='<%# Bind("Fees") %>'></asp:Label>
                                </ItemTemplate>
                                <EditItemTemplate>
                                    <asp:TextBox ID="TextBox5" runat="server" Text='<%# Bind("Fees") %>'></asp:TextBox>
                                </EditItemTemplate>
                            </asp:TemplateField>
                        </Columns>
                        <FooterStyle BackColor="#5D7B9D" Font-Bold="True" ForeColor="White" />
                        <PagerStyle BackColor="#284775" ForeColor="White" HorizontalAlign="Center" />
                        <SelectedRowStyle BackColor="#E2DED6" Font-Bold="True" ForeColor="#333333" />
                        <HeaderStyle BackColor="#DE7037" Font-Bold="True" ForeColor="White" />
                        <EditRowStyle BackColor="#999999" />
                        <AlternatingRowStyle BackColor="White" ForeColor="#284775" />
                        
                    </asp:GridView>
                </td>
            </tr>
            <tr>
                <td>
                </td>
            </tr>
        </table>
    </td>
</asp:Content>
