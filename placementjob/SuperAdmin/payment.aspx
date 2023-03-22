<%@ Page Title="" Language="C#" MasterPageFile="~/SuperAdmin/AdminMaster.master" AutoEventWireup="true"
    CodeFile="payment.aspx.cs" Inherits="Control_payment" %>

<%@ Register Assembly="AjaxControlToolkit" Namespace="AjaxControlToolkit" TagPrefix="asp" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <td>
        <td>
            <td>
                <td>
                    <td>
                        <asp:ToolkitScriptManager ID="ToolkitScriptManager1" runat="server">
                        </asp:ToolkitScriptManager>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td align="left" valign="top" class="pro_tit">Customer payment Details:
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <br />
                                </td>
                            </tr>
                        </table>
                        <table>
                            <tr>
                                <td>Sort by:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <asp:TextBox ID="txt_fdate" runat="server"></asp:TextBox>
                                    <asp:CalendarExtender ID="txt_sdate_CalendarExtender" runat="server" TargetControlID="txt_fdate">
                                    </asp:CalendarExtender>
                                </td>
                                <td>To:&nbsp;&nbsp;&nbsp;
                                    <asp:TextBox ID="txt_tdate" runat="server"></asp:TextBox>
                                    <asp:CalendarExtender ID="CalendarExtender1" runat="server" TargetControlID="txt_tdate">
                                    </asp:CalendarExtender>
                                    &nbsp;&nbsp;&nbsp;<asp:Button ID="btn_sdate" runat="server" Text="Go" OnClick="btn_sdate_Click" />
                                </td>
                                <td style="border-style: none none none solid; border-top-width: thin;">&nbsp;&nbsp;Email id:&nbsp;&nbsp;<asp:TextBox ID="txt_esarch" runat="server" Width="203px"></asp:TextBox>
                                </td>
                                <td>&nbsp;&nbsp;&nbsp;
                                    <asp:Button ID="btn_semail" runat="server" Text="Search" OnClick="btn_semail_Click" />
                                </td>
                            </tr>
                        </table>
                        <table>
                            <tr>
                                <td>
                                    <br />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <asp:GridView ID="grdclient" runat="server" AutoGenerateColumns="False" Width="100%"
                                        CellPadding="4" ForeColor="Black" HeaderStyle-HorizontalAlign="Center" HeaderStyle-VerticalAlign="Middle"
                                        BackColor="#CCCCCC" BorderColor="#999999" BorderStyle="Solid" BorderWidth="3px"
                                        CellSpacing="2">
                                        <RowStyle BackColor="White" />
                                        <Columns>
                                            <asp:BoundField DataField="id" HeaderText="Id" SortExpression="id" ItemStyle-HorizontalAlign="Center">
                                                <ItemStyle HorizontalAlign="Center"></ItemStyle>
                                            </asp:BoundField>
                                            <asp:BoundField DataField="date" HeaderText="Registration Date" SortExpression="date"
                                                ItemStyle-HorizontalAlign="Center">
                                                <ItemStyle HorizontalAlign="Center"></ItemStyle>
                                            </asp:BoundField>
                                            <asp:BoundField DataField="quantity" HeaderText="Quantity" SortExpression="quantity"
                                                ItemStyle-HorizontalAlign="Center">
                                                <ItemStyle HorizontalAlign="Center"></ItemStyle>
                                            </asp:BoundField>
                                        </Columns>
                                        <FooterStyle BackColor="#CCCCCC" />
                                        <PagerStyle BackColor="#CCCCCC" ForeColor="Black" HorizontalAlign="Left" />
                                        <SelectedRowStyle BackColor="#000099" Font-Bold="True" ForeColor="White" />
                                        <HeaderStyle BackColor="#de7037" Font-Bold="True" ForeColor="White" />
                                        <SortedAscendingCellStyle BackColor="#F1F1F1" />
                                        <SortedAscendingHeaderStyle BackColor="#808080" />
                                        <SortedDescendingCellStyle BackColor="#CAC9C9" />
                                        <SortedDescendingHeaderStyle BackColor="#383838" />
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
                                    <asp:GridView ID="grd_client_active" runat="server" AutoGenerateColumns="False" Width="100%"
                                        CellPadding="4" ForeColor="Black" OnRowDataBound="grd_client_active_RowDataBound"
                                        HeaderStyle-HorizontalAlign="Center" HeaderStyle-VerticalAlign="Middle" BackColor="#CCCCCC"
                                        BorderColor="#999999" BorderStyle="Solid" BorderWidth="3px" CellSpacing="2" OnRowCommand="grd_client_active_RowCommand">
                                        <RowStyle BackColor="White" />
                                        <Columns>
                                            <asp:TemplateField HeaderText="Id" SortExpression="id">
                                                <ItemTemplate>
                                                    <asp:Label ID="CId" runat="server" Text='<%# Bind("Id") %>'></asp:Label>
                                                </ItemTemplate>
                                                <EditItemTemplate>
                                                    <asp:TextBox ID="CId" runat="server" Text='<%# Bind("Id") %>'></asp:TextBox>
                                                </EditItemTemplate>
                                                <ItemStyle HorizontalAlign="Center" />
                                            </asp:TemplateField>
                                            
                                            <asp:BoundField DataField="Name" HeaderText="Name" SortExpression="Name" ItemStyle-HorizontalAlign="Center">
                                                <ItemStyle HorizontalAlign="Center"></ItemStyle>
                                            </asp:BoundField>
                                            <asp:BoundField DataField="date" HeaderText="Registration Date" SortExpression="date"
                                                ItemStyle-HorizontalAlign="Center">
                                                <ItemStyle HorizontalAlign="Center"></ItemStyle>
                                            </asp:BoundField>
                                            <asp:BoundField DataField="Age" HeaderText="Age" SortExpression="Age" />
                                            <asp:BoundField DataField="Email" HeaderText="Email" SortExpression="Email" ItemStyle-HorizontalAlign="Center">
                                                <ItemStyle HorizontalAlign="Center"></ItemStyle>
                                            </asp:BoundField>
                                            <asp:BoundField DataField="password" HeaderText="Password" SortExpression="password"
                                                ItemStyle-HorizontalAlign="Center">
                                                <ItemStyle HorizontalAlign="Center"></ItemStyle>
                                            </asp:BoundField>
                                            <asp:BoundField DataField="address" HeaderText="Address" SortExpression="address"
                                                ItemStyle-HorizontalAlign="Center">
                                                <ItemStyle HorizontalAlign="Center"></ItemStyle>
                                            </asp:BoundField>
                                            <asp:BoundField DataField="Mobile" HeaderText="Mobile" SortExpression="Mobile" ItemStyle-HorizontalAlign="Center">
                                                <ItemStyle HorizontalAlign="Center"></ItemStyle>
                                            </asp:BoundField>
                                            <asp:BoundField DataField="BankAC" HeaderText="BankAC" SortExpression="BankAC" ItemStyle-HorizontalAlign="Center">
                                                <ItemStyle HorizontalAlign="Center"></ItemStyle>
                                            </asp:BoundField>
                                            <asp:BoundField DataField="BankIFSC" HeaderText="BankIFSC" SortExpression="BankIFSC"
                                                ItemStyle-HorizontalAlign="Center">
                                                <ItemStyle HorizontalAlign="Center"></ItemStyle>
                                            </asp:BoundField>
                                            <asp:TemplateField HeaderText="Status" ItemStyle-HorizontalAlign="Center" Visible="False">
                                                <ItemTemplate>
                                                    <asp:Label ID="lblStatus" runat="server" Text='<%# Bind("status") %>'></asp:Label>
                                                </ItemTemplate>
                                                <ItemStyle HorizontalAlign="Center"></ItemStyle>
                                            </asp:TemplateField>
                                            <asp:TemplateField HeaderText="status" SortExpression="status">
                                                <ItemTemplate>
                                                    <asp:Label ID="Label1" runat="server" Text='<%# Bind("status") %>'></asp:Label>
                                                </ItemTemplate>
                                                <EditItemTemplate>
                                                    <asp:TextBox ID="TextBox1" runat="server" Text='<%# Bind("status") %>'></asp:TextBox>
                                                </EditItemTemplate>
                                            </asp:TemplateField>
                                            <asp:BoundField DataField="followup" HeaderText="Follow Up Status" SortExpression="followup"></asp:BoundField>
                                            <asp:TemplateField HeaderText="Follow Up">
                                                <ItemTemplate>
                                                    <asp:TextBox ID="txt_time" TextMode="MultiLine" runat="server" Height="100px" Width="100px"></asp:TextBox>
                                                </ItemTemplate>
                                            </asp:TemplateField>
                                            <asp:TemplateField>
                                                <ItemTemplate>
                                                    <asp:LinkButton ID="Timechange" runat="server" PostBackUrl='<%# Eval("id","Payment.aspx?id={0}") %>'
                                                        CommandArgument="<%# ((GridViewRow) Container).RowIndex %>" CommandName="Time">Change</asp:LinkButton>
                                                </ItemTemplate>
                                            </asp:TemplateField>
                                            <asp:TemplateField>
                                                <ItemTemplate>
                                                    <asp:LinkButton ID="LinkButton3" runat="server" PostBackUrl='<%# Eval("Email","Payment.aspx?Email={0}") %>' OnClick="LinkButton3_Click">Paid</asp:LinkButton>
                                                </ItemTemplate>
                                            </asp:TemplateField>
                                            <asp:TemplateField>
                                                <ItemTemplate>
                                                    <asp:LinkButton ID="LinkButton1" runat="server" PostBackUrl='<%# Eval("Email","Payment.aspx?Email={0}") %>'
                                                        OnClick="LinkButton1_Click">All Order</asp:LinkButton>
                                                </ItemTemplate>
                                                <ItemStyle HorizontalAlign="Center"></ItemStyle>
                                            </asp:TemplateField>
                                            <asp:TemplateField HeaderText="Delete">
                                                <ItemTemplate>
                                                    <asp:LinkButton ID="LinkButton2" runat="server" PostBackUrl='<%# Eval("Email","Payment.aspx?Email={0}") %>'
                                                        OnClick="LinkButton2_Click">Delete</asp:LinkButton>
                                                </ItemTemplate>
                                            </asp:TemplateField>
                                        </Columns>
                                        <FooterStyle BackColor="#CCCCCC" />
                                        <PagerStyle BackColor="#CCCCCC" ForeColor="Black" HorizontalAlign="Left" />
                                        <SelectedRowStyle BackColor="#000099" Font-Bold="True" ForeColor="White" />
                                        <HeaderStyle BackColor="#de7037" Font-Bold="True" ForeColor="White" />
                                        <SortedAscendingCellStyle BackColor="#F1F1F1" />
                                        <SortedAscendingHeaderStyle BackColor="#808080" />
                                        <SortedDescendingCellStyle BackColor="#CAC9C9" />
                                        <SortedDescendingHeaderStyle BackColor="#383838" />
                                    </asp:GridView>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <br />
                                </td>
                            </tr>
                        </table>
                    </td>
                </td>
            </td>
        </td>
    </td>
</asp:Content>
