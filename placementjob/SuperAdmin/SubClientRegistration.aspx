<%@ Page Language="C#" MasterPageFile="~/SuperAdmin/subAdminMaster.master" AutoEventWireup="true"
    CodeFile="SubClientRegistration.aspx.cs" Inherits="Admin_ClientRegistration"
    Title="Client Registration" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td align="left" valign="top" class="pro_tit">
                    New Customer
                </td>
            </tr>
            <tr>
                <td>
                    <br />
                </td>
            </tr>
            <tr>
                <td>
                    <br />
                </td>
            </tr>
            <tr>
                <td>
                    <asp:GridView ID="grd_client_active" runat="server" AutoGenerateColumns="False" Width="100%"
                        CellPadding="4" ForeColor="#333333" GridLines="None" OnRowDataBound="grd_client_active_RowDataBound" HeaderStyle-HorizontalAlign ="Center" HeaderStyle-VerticalAlign="Middle" >
                        <RowStyle BackColor="#F7F6F3" ForeColor="#333333" />
                        <Columns>
                            <asp:TemplateField ItemStyle-HorizontalAlign ="Center" >
                                <ItemTemplate>
                                    <asp:LinkButton ID="btn_active" runat="server" OnClick="btn_active_Click" PostBackUrl='<%# Eval("Cid" ,"SubClientRegistration.aspx?Cid={0}") %>'>Active</asp:LinkButton>
                                </ItemTemplate>
                            </asp:TemplateField>
                            <asp:BoundField DataField="CId" HeaderText="CId" SortExpression="CId" Visible="true" ItemStyle-HorizontalAlign ="Center" />
                            <asp:BoundField DataField="Name" HeaderText="Name" SortExpression="Name" ItemStyle-HorizontalAlign ="Center" />                           
                             
                            <asp:TemplateField HeaderText="Franchisee" ItemStyle-HorizontalAlign ="Center" >
                                <ItemTemplate>
                                    <asp:Label ID="lblfid" runat="server" Text='<%# BIND("FId") %>'></asp:Label>
                                </ItemTemplate>
                            </asp:TemplateField>
                            
                            <asp:BoundField DataField="Address" HeaderText="Address" SortExpression="Address" ItemStyle-HorizontalAlign ="Center"  />
                            <asp:BoundField DataField="MobileNo" HeaderText="MobileNo" SortExpression="MobileNo" ItemStyle-HorizontalAlign ="Center" />
                            <asp:BoundField DataField="Reg_Date" HeaderText="Reg. Date" SortExpression="Reg_Date" ItemStyle-HorizontalAlign ="Center" />
                            <asp:BoundField DataField="EmailId" HeaderText="EmailId" SortExpression="EmailId" ItemStyle-HorizontalAlign ="Center" />
                            <asp:BoundField DataField="PlanId" HeaderText="PlanId" SortExpression="PlanId" ItemStyle-HorizontalAlign ="Center" />
                         
                            <asp:TemplateField HeaderText="Online/Offline" ItemStyle-HorizontalAlign ="Center">
                                <ItemTemplate>
                                    <asp:Label ID="lbl" runat="server" Text='<%# BIND("Style") %>'></asp:Label>
                                </ItemTemplate>
                            </asp:TemplateField>
                            <asp:TemplateField ItemStyle-HorizontalAlign ="Center">
                                <ItemTemplate>
                                    <asp:LinkButton ID="btn_delete" runat="server" OnClick="btn_delete_Click" PostBackUrl='<%# Eval("Cid" ,"SubClientRegistration.aspx?Cid={0}") %>'>Delete</asp:LinkButton>
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
                    <br />
                </td>
            </tr>
            <tr>
                <td align="left" valign="top" class="pro_tit">
                    Old Customer
                </td>
            </tr>
            <tr>
                <td style="padding: 5px; border-style: dashed; border-width: thin; margin: 4px">
                    Select Franchisee :&nbsp;&nbsp;&nbsp;&nbsp;
                    <asp:DropDownList ID="drd_fran" runat="server" AutoPostBack="True" OnSelectedIndexChanged="drd_fran_SelectedIndexChanged"
                        Style="height: 22px">
                    </asp:DropDownList>
                </td>
            </tr>
            <tr>
                <td>
                    <asp:GridView ID="grd_client_deactive" runat="server" AutoGenerateColumns="False"
                        Width="100%" CellPadding="4" ForeColor="#333333" GridLines="None" 
                        onrowdatabound="grd_client_deactive_RowDataBound" HeaderStyle-HorizontalAlign ="Center" HeaderStyle-VerticalAlign="Middle" >
                        <RowStyle BackColor="#F7F6F3" ForeColor="#333333" />
                        <Columns>
                            <asp:TemplateField Visible="False" ItemStyle-HorizontalAlign ="Center">
                                <ItemTemplate>
                                    <asp:LinkButton ID="btn_deactivate" runat="server" OnClick="btn_deactivate_Click"
                                        PostBackUrl='<%# Eval("cid" ,"ClientRegistration.aspx?cid={0}") %>'>Deactive</asp:LinkButton>
                                </ItemTemplate>
                            </asp:TemplateField>
                            <asp:BoundField DataField="CId" HeaderText="CId" SortExpression="CId" Visible="true" ItemStyle-HorizontalAlign ="Center"/>
                            <asp:BoundField DataField="Name" HeaderText="Name" SortExpression="Name" ItemStyle-HorizontalAlign ="Center" />
                            <asp:BoundField DataField="Address" HeaderText="Address" SortExpression="Address" ItemStyle-HorizontalAlign ="Center" />
                            <asp:BoundField DataField="MobileNo" HeaderText="MobileNo" SortExpression="MobileNo" ItemStyle-HorizontalAlign ="Center" />
                            <asp:BoundField DataField="Reg_Date" HeaderText="Reg. Date" SortExpression="Reg_Date" ItemStyle-HorizontalAlign ="Center" />
                            <asp:BoundField DataField="EmailId" HeaderText="EmailId" SortExpression="EmailId" ItemStyle-HorizontalAlign ="Center" />
                            <asp:BoundField DataField="PlanId" HeaderText="PlanId" SortExpression="PlanId" ItemStyle-HorizontalAlign ="Center" />
                            <asp:TemplateField HeaderText="Online/Offline" ItemStyle-HorizontalAlign ="Center">
                                <ItemTemplate>
                                    <asp:Label ID="lbl1" runat="server" Text='<%# BIND("Style") %>'></asp:Label>
                                </ItemTemplate>
                            </asp:TemplateField>
                            <asp:TemplateField ItemStyle-HorizontalAlign ="Center">
                                <EditItemTemplate>
                                    <asp:TextBox ID="TextBox1" runat="server"></asp:TextBox>
                                </EditItemTemplate>
                                <ItemTemplate>
                                    <asp:LinkButton ID="btn_aggriment" runat="server" OnClick="btn_aggriment_Click" PostBackUrl='<%# Eval("cid","SubClientRegistration.aspx?cid={0}") %>'>Download</asp:LinkButton>
                                </ItemTemplate>
                            </asp:TemplateField>
                            <asp:TemplateField ItemStyle-HorizontalAlign ="Center">
                                <ItemTemplate>
                                    <asp:LinkButton ID="btn_send" runat="server" OnClick="btn_send_Click" PostBackUrl='<%# Eval("cid","SubClientRegistration.aspx?cid={0}") %>'>Send</asp:LinkButton>
                                </ItemTemplate>
                            </asp:TemplateField>
                            <asp:TemplateField ItemStyle-HorizontalAlign ="Center">
                                <ItemTemplate>
                                    <asp:LinkButton ID="btn_edit" runat="server" OnClick="btn_edit_Click" PostBackUrl='<%# Eval("cid","SubClientRegistration.aspx?cid={0}") %>'>Edit</asp:LinkButton>
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
                    <table border="1" frame="border" width="100%" style="color: #4b4b4b" cellspacing="8px"
                        width="100%">
                        <tr>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                Customer Id
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:Label ID="lblid" runat="server"></asp:Label>
                            </td>
                        </tr>
                        <tr>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                Name
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:TextBox ID="txt_name" runat="server" Width="210px"></asp:TextBox>
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator3" runat="server" ControlToValidate="txt_name"
                                    ErrorMessage="Enter Name" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                            </td>
                        </tr>
                        <tr>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                Address
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:TextBox ID="txt_add" runat="server" Height="92px" TextMode="MultiLine" Width="211px"></asp:TextBox>
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator4" runat="server" ControlToValidate="txt_add"
                                    ErrorMessage="Enter Address" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                            </td>
                        </tr>
                        <tr>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                Company Name
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:TextBox ID="txt_compname" runat="server" Width="210px"></asp:TextBox>
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator5" runat="server" ControlToValidate="txt_compname"
                                    ErrorMessage="Enter Company Name" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                            </td>
                        </tr>
                        <tr>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                City
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:TextBox ID="txt_city" runat="server" Width="210px"></asp:TextBox>
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator6" runat="server" ControlToValidate="txt_city"
                                    ErrorMessage="Enter City" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                            </td>
                        </tr>
                        <tr>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                Mobile No.
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
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                Email
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
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                Plan
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:Label ID="lbl_plan" runat="server"></asp:Label>
                            </td>
                        </tr>
                        <tr>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                &nbsp;
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:Button ID="btn_update" runat="server" Text="Update" ValidationGroup="valgrp"
                                    OnClick="btn_update_Click" />
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </td>
</asp:Content>
