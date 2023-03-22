<%@ Page Language="C#" MasterPageFile="~/SuperAdmin/AdminMaster.master" AutoEventWireup="true" CodeFile="inquiry.aspx.cs" Inherits="Admin_inquiry" Title="Untitled Page" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" Runat="Server">
<td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td align="left" valign="top" class="pro_tit">
                    Customer Inquiry
                </td>
            </tr>
        </table>
        <table style="width: 100%">
        <br />
           <asp:GridView ID="grd_Inquiry" runat="server" CellPadding="4" ForeColor="#333333" 
                        GridLines="None" onrowcommand="grd_Inquiry_RowCommand" 
                        onselectedindexchanged="grd_Inquiry_SelectedIndexChanged" Width="219px" 
                        AutoGenerateColumns="False">
                        <RowStyle BackColor="#F7F6F3" ForeColor="#333333" />
                        <Columns>
                            <asp:TemplateField HeaderText="Check">
                                <ItemTemplate>
                                    <asp:LinkButton ID="btn_check" runat="server" onclick="btn_check_Click" CommandArgument="<%# ((GridViewRow) Container).RowIndex %>">Check</asp:LinkButton>
                                </ItemTemplate>
                            </asp:TemplateField>
                            <asp:TemplateField HeaderText="Id" SortExpression="Id" Visible="False">
                                <EditItemTemplate>
                                    <asp:TextBox ID="TextBox1" runat="server" Text='<%# Bind("Id") %>'></asp:TextBox>
                                </EditItemTemplate>
                                <ItemTemplate>
                                    <asp:Label ID="Label1" runat="server" Text='<%# Bind("Id") %>'></asp:Label>
                                </ItemTemplate>
                            </asp:TemplateField>
                            <asp:BoundField DataField="Date" HeaderText="Date" SortExpression="Date" />
                            <asp:BoundField DataField="Site" HeaderText="Site" SortExpression="Site" />
                            <asp:BoundField DataField="Type" HeaderText="Type" SortExpression="Type" />
                            <asp:BoundField DataField="Plan" HeaderText="Plan" SortExpression="Plan" />
                            <asp:BoundField DataField="Pages" HeaderText="Pages" SortExpression="Pages" />
                            <asp:BoundField DataField="Personal_Detail" HeaderText="Personal Detail" 
                                SortExpression="Personal_Detail" />
                            <asp:BoundField DataField="Full_Name" HeaderText="Full Name" 
                                SortExpression="Full_Name" />
                            <asp:BoundField DataField="EmailId" HeaderText="EmailId" 
                                SortExpression="EmailId" />
                            <asp:BoundField DataField="Address" HeaderText="Address" 
                                SortExpression="Address" />
                            <asp:BoundField DataField="City" HeaderText="City" SortExpression="City" />
                            <asp:BoundField DataField="State" HeaderText="State" SortExpression="State" />
                            <asp:BoundField DataField="Contact_No" HeaderText="Contact No" 
                                SortExpression="Contact_No" />
                            <asp:BoundField DataField="Ref" HeaderText="Reference" SortExpression="Ref" />
                        </Columns>
                        <FooterStyle BackColor="#5D7B9D" Font-Bold="True" ForeColor="White" />
                        <PagerStyle BackColor="#284775" ForeColor="White" HorizontalAlign="Center" />
                        <SelectedRowStyle BackColor="#E2DED6" Font-Bold="True" ForeColor="#333333" />
                        <HeaderStyle BackColor="#DE7037" Font-Bold="True" ForeColor="White" />
                        <EditRowStyle BackColor="#999999" />
                        <AlternatingRowStyle BackColor="White" ForeColor="#284775" />
                    </asp:GridView>
        </table>
    </td>
</asp:Content>

