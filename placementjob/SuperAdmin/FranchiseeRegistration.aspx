<%@ Page Language="C#" MasterPageFile="~/SuperAdmin/AdminMaster.master" AutoEventWireup="true"
    CodeFile="FranchiseeRegistration.aspx.cs" Inherits="Admin_FranchiseeRegistration"
    Title="Franchisee Registration" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td align="left" valign="top" class="pro_tit">
                    Franchisee Registration
                </td>
            </tr>
            <tr>
                <td>
                    <br />
                </td>
            </tr>
            <tr>
                <td>
                    <asp:GridView ID="grd_franch_active" runat="server" AutoGenerateColumns="False" Width="100%"
                        CellPadding="4" ForeColor="#333333" GridLines="None">
                        <RowStyle BackColor="#F7F6F3" ForeColor="#333333" />
                        <Columns>
                            <asp:TemplateField>
                                <ItemTemplate>
                                    <asp:LinkButton ID="btn_active" runat="server" OnClick="btn_active_Click" PostBackUrl='<%# Eval("fid" ,"FranchiseeRegistration.aspx?fid={0}") %>'>Active</asp:LinkButton>
                                </ItemTemplate>
                            </asp:TemplateField>
                            <asp:BoundField DataField="FId" HeaderText="FId" SortExpression="FId" Visible="true" />
                            <asp:BoundField DataField="FranchiseeName" HeaderText="FranchiseeName" SortExpression="FranchiseeName" />
                            <asp:BoundField DataField="CompanyName" HeaderText="CompanyName" SortExpression="CompanyName" />
                            <asp:BoundField DataField="Address" HeaderText="Address" SortExpression="Address" />
                            <asp:BoundField DataField="MobileNo" HeaderText="MobileNo" SortExpression="MobileNo" />
                            <asp:BoundField DataField="RegisteredDate" HeaderText="RegisteredDate" SortExpression="RegisteredDate" />
                            <asp:BoundField DataField="Email" HeaderText="Email" SortExpression="Email" />
                            <asp:BoundField DataField="BankAC" HeaderText="BankAC" SortExpression="BankAC" />
                            <asp:TemplateField>
                                <ItemTemplate>
                                    <asp:LinkButton ID="btn_delete" runat="server" OnClick="btn_delete_Click" PostBackUrl='<%# Eval("fid" ,"FranchiseeRegistration.aspx?fid={0}") %>'>Delete</asp:LinkButton>
                                </ItemTemplate>
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
                    <br />
                </td>
            </tr>
            <tr>
                <td>
                    <asp:GridView ID="grd_franch_deactive" runat="server" AutoGenerateColumns="False"
                        Width="100%" CellPadding="4" ForeColor="#333333" GridLines="None" OnRowDataBound="grd_franch_deactive_RowDataBound">
                        <RowStyle BackColor="#F7F6F3" ForeColor="#333333" />
                        <Columns>
                            <asp:TemplateField>
                                
                                <ItemTemplate>
                                    <asp:LinkButton ID="btn_deactivate" runat="server" OnClick="btn_deactivate_Click"
                                        PostBackUrl='<%# Eval("fid" ,"FranchiseeRegistration.aspx?fid={0}") %>'>Deactive</asp:LinkButton>
                                </ItemTemplate>
                            </asp:TemplateField>
                            <asp:TemplateField >
                                
                                <ItemTemplate>
                                    
                                    <asp:LinkButton ID="btn_vis" runat="server" OnClick="btn_vis_Click" PostBackUrl='<%# Eval("fid" ,"FranchiseeRegistration.aspx?fid={0}") %>'>Change Visible</asp:LinkButton>
                                </ItemTemplate>
                            </asp:TemplateField>
                            <asp:TemplateField>
                                <ItemTemplate>
                                    <asp:LinkButton ID="btn_block" runat="server" OnClick="btn_block_Click" PostBackUrl='<%# Eval("fid" ,"FranchiseeRegistration.aspx?fid={0}") %>'>Block</asp:LinkButton>
                                </ItemTemplate>
                            </asp:TemplateField>
                            <asp:TemplateField HeaderText="BlockStatus">
                                <ItemTemplate>
                                    <asp:Label ID="lblblockstatus" runat="server" Text='<%# BIND("BlockStatus") %>'></asp:Label>
                                </ItemTemplate>
                            </asp:TemplateField>
                            <asp:TemplateField HeaderText="visible" SortExpression="visible">
                                <ItemTemplate>
                                    <asp:Label ID="Label12" runat="server" Text='<%# Bind("visible") %>'></asp:Label>
                                    </ItemTemplate>
                                <EditItemTemplate>
                                    <asp:TextBox ID="TextBox12" runat="server" Text='<%# Bind("visible") %>'></asp:TextBox>
                                </EditItemTemplate>
                            </asp:TemplateField>
                            <asp:BoundField DataField="FId" HeaderText="FId" SortExpression="FId" Visible="true" />
                            <asp:TemplateField HeaderText="Password" SortExpression="Password">
                                <ItemTemplate>
                                    <asp:Label ID="Label2" runat="server" Text='<%# Bind("Password") %>'></asp:Label>
                                </ItemTemplate>
                                <EditItemTemplate>
                                    <asp:TextBox ID="TextBox2" runat="server" Text='<%# Bind("Password") %>'></asp:TextBox>
                                </EditItemTemplate>
                            </asp:TemplateField>
                            <asp:BoundField DataField="FranchiseeName" HeaderText="FranchiseeName" SortExpression="FranchiseeName" />
                            <asp:BoundField DataField="CompanyName" HeaderText="CompanyName" SortExpression="CompanyName" />
                            <asp:BoundField DataField="Address" HeaderText="Address" SortExpression="Address" />
                            <asp:BoundField DataField="MobileNo" HeaderText="MobileNo" SortExpression="MobileNo" />
                            <asp:BoundField DataField="RegisteredDate" HeaderText="RegisteredDate" SortExpression="RegisteredDate" />
                            <asp:BoundField DataField="Email" HeaderText="Email" SortExpression="Email" />
                            <asp:BoundField DataField="BankAC" HeaderText="BankAC" SortExpression="BankAC" />
                            <asp:TemplateField>
                                <ItemTemplate>
                                    <asp:LinkButton ID="btn_send" runat="server" OnClick="btn_send_Click" PostBackUrl='<%# Eval("fid","FranchiseeRegistration.aspx?fid={0}") %>'>Send</asp:LinkButton>
                                </ItemTemplate>
                            </asp:TemplateField>
                            <asp:TemplateField>
                                <ItemTemplate>
                                    <asp:LinkButton ID="btn_edit" runat="server" OnClick="btn_edit_Click" PostBackUrl='<%# Eval("fid","FranchiseeRegistration.aspx?fid={0}") %>'>Edit</asp:LinkButton>
                                </ItemTemplate>
                            </asp:TemplateField>
                            <asp:TemplateField HeaderText="Active Portal">
                                <ItemTemplate>
                                    <asp:LinkButton ID="btn_ap" runat="server" PostBackUrl='<%# Eval("fid","FranchiseeRegistration.aspx?fid={0}") %>'
                                        OnClick="btn_ap_Click">Active/Deactive</asp:LinkButton>
                                </ItemTemplate>
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
                    <br />
                </td>
            </tr>
            <tr>
                <td>
                    <asp:Panel ID="Panel1" Width="100%" runat="server">
                        <table border="1" frame="border" width="100%" style="color: #4b4b4b" cellspacing="8px"
                            width="100%">
                            <tr>
                                <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                    Franchisee Id
                                </td>
                                <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                    <asp:Label ID="fid" runat="server"></asp:Label>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Website Url:
                                </td>
                                <td>
                                    <asp:TextBox ID="txt_url" runat="server" Width="180px"></asp:TextBox>
                                    <asp:RequiredFieldValidator ID="RequiredFieldValidator1" runat="server" ControlToValidate="txt_url"
                                        ErrorMessage="Enter Franchisee Name" ValidationGroup="val"></asp:RequiredFieldValidator>
                                </td>
                            </tr>
                            <tr>
                                <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                </td>
                                <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                    <asp:Button ID="btn_active" runat="server" Text="Active" ValidationGroup="val" OnClick="btn_active_Click1" />
                                </td>
                            </tr>
                        </table>
                    </asp:Panel>
                </td>
            </tr>
            <tr>
                <td>
                    <br />
                </td>
            </tr>
            <tr>
                <td>
                    <table border="1" frame="border" width="100%" style="color: #4b4b4b" cellspacing="8px"
                        width="100%">
                        <tr>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                Franchisee Id
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:Label ID="lblid" runat="server"></asp:Label>
                            </td>
                        </tr>
                        <tr>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                Franchisee Name
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:TextBox ID="txt_frname" runat="server" Width="180px"></asp:TextBox>
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator3" runat="server" ControlToValidate="txt_frname"
                                    ErrorMessage="Enter Franchisee Name" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                            </td>
                        </tr>
                        <tr>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                Company Name
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:TextBox ID="txt_compname" runat="server" Width="180px"></asp:TextBox>
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator4" runat="server" ControlToValidate="txt_compname"
                                    ErrorMessage="Enter Company Name" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                            </td>
                        </tr>
                        <tr>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                Address
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px 4px 4px 44px">
                                <asp:TextBox ID="txt_add" runat="server" Height="103px" TextMode="MultiLine" Width="204px"></asp:TextBox>
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator5" runat="server" ControlToValidate="txt_add"
                                    ErrorMessage="Enter Address" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                            </td>
                        </tr>
                        <tr>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                Landline No
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:TextBox ID="txt_landno" runat="server" Width="180px"></asp:TextBox>
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator6" runat="server" ControlToValidate="txt_landno"
                                    ErrorMessage="Enter Landline No" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                            </td>
                        </tr>
                        <tr>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                Mobile No
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:TextBox ID="txt_mob" runat="server" Width="180px"></asp:TextBox>
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator7" runat="server" ControlToValidate="txt_mob"
                                    ErrorMessage="Enter Mobile" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                                <asp:RegularExpressionValidator ID="RegularExpressionValidator1" runat="server" ControlToValidate="txt_mob"
                                    ErrorMessage="InValid Mob." ValidationExpression="^[0-9]{10}" ValidationGroup="valgrp"></asp:RegularExpressionValidator>
                            </td>
                        </tr>
                        <tr>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                Email
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:TextBox ID="txt_email" runat="server" Width="180px"></asp:TextBox>
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator8" runat="server" ControlToValidate="txt_email"
                                    ErrorMessage="Enter Email" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                                <asp:RegularExpressionValidator ID="RegularExpressionValidator2" runat="server" ControlToValidate="txt_email"
                                    ErrorMessage="InValid Email Id" ValidationExpression="\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*"
                                    ValidationGroup="valgrp"></asp:RegularExpressionValidator>
                            </td>
                        </tr>
                        <tr>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                Bank A/C No
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:TextBox ID="txt_bankac" runat="server" Width="180px"></asp:TextBox>
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator9" runat="server" ControlToValidate="txt_bankac"
                                    ErrorMessage="Enter Bank A/C No" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                            </td>
                        </tr>
                        <tr>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                IFC Code
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:TextBox ID="txt_ifc" runat="server" Width="180px"></asp:TextBox>
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator10" runat="server" ControlToValidate="txt_ifc"
                                    ErrorMessage="Enter IFC Code" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                            </td>
                        </tr>
                        <tr>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:Button ID="btn_update" runat="server" Text="Update" OnClick="btn_update_Click"
                                    ValidationGroup="valgrp" />
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </td>
</asp:Content>
