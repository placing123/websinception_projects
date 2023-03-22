<%@ Page Language="C#" MasterPageFile="~/SuperAdmin/AdminMaster.master" AutoEventWireup="true"
    CodeFile="Date_Change.aspx.cs" Inherits="Admin_Date_Change" Title="Change Date" %>

<%@ Register Assembly="AjaxControlToolkit" Namespace="AjaxControlToolkit" TagPrefix="asp" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <td>
        <asp:ToolkitScriptManager ID="ToolkitScriptManager1" runat="server">
        </asp:ToolkitScriptManager>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td align="left" valign="top" class="pro_tit">
                    Date Change
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
                        CellPadding="4" ForeColor="#333333" GridLines="None" OnRowDataBound="grd_client_active_RowDataBound"
                        HeaderStyle-HorizontalAlign="Center" HeaderStyle-VerticalAlign="Middle">
                        <RowStyle BackColor="#F7F6F3" ForeColor="#333333" />
                        <Columns>
                            <asp:BoundField DataField="CId" HeaderText="CId" SortExpression="CId" ItemStyle-HorizontalAlign="Center">
                                <ItemStyle HorizontalAlign="Center"></ItemStyle>
                            </asp:BoundField>
                            <asp:BoundField DataField="Name" HeaderText="Name" SortExpression="Name" ItemStyle-HorizontalAlign="Center">
                                <ItemStyle HorizontalAlign="Center"></ItemStyle>
                            </asp:BoundField>
                            <asp:TemplateField HeaderText="Franchisee" ItemStyle-HorizontalAlign="Center">
                                <ItemTemplate>
                                    <asp:Label ID="lblfid" runat="server" Text='<%# BIND("FId") %>'></asp:Label>
                                </ItemTemplate>
                                <ItemStyle HorizontalAlign="Center"></ItemStyle>
                            </asp:TemplateField>
                            <asp:BoundField DataField="Address" HeaderText="Address" SortExpression="Address"
                                ItemStyle-HorizontalAlign="Center">
                                <ItemStyle HorizontalAlign="Center"></ItemStyle>
                            </asp:BoundField>
                            <asp:BoundField DataField="CompanyName" HeaderText="CompanyName" SortExpression="CompanyName"
                                ItemStyle-HorizontalAlign="Center">
                                <ItemStyle HorizontalAlign="Center"></ItemStyle>
                            </asp:BoundField>
                            <asp:BoundField DataField="City" HeaderText="City" SortExpression="City" ItemStyle-HorizontalAlign="Center">
                                <ItemStyle HorizontalAlign="Center"></ItemStyle>
                            </asp:BoundField>
                            <asp:BoundField DataField="MobileNo" HeaderText="MobileNo" SortExpression="MobileNo"
                                ItemStyle-HorizontalAlign="Center">
                                <ItemStyle HorizontalAlign="Center"></ItemStyle>
                            </asp:BoundField>
                            <asp:BoundField DataField="EmailId" HeaderText="EmailId" SortExpression="EmailId"
                                ItemStyle-HorizontalAlign="Center">
                                <ItemStyle HorizontalAlign="Center"></ItemStyle>
                            </asp:BoundField>
                            <asp:BoundField DataField="PlanId" HeaderText="PlanId" SortExpression="PlanId" ItemStyle-HorizontalAlign="Center">
                                <ItemStyle HorizontalAlign="Center"></ItemStyle>
                            </asp:BoundField>
                            <asp:BoundField DataField="Reg_Date" HeaderText="Reg. Date" SortExpression="Reg_Date"
                                ItemStyle-HorizontalAlign="Center">
                                <ItemStyle HorizontalAlign="Center"></ItemStyle>
                            </asp:BoundField>
                            <asp:BoundField DataField="Start_Date" HeaderText="Start Date" SortExpression="Start_Date"
                                ItemStyle-HorizontalAlign="Center">
                                <ItemStyle HorizontalAlign="Center"></ItemStyle>
                            </asp:BoundField>
                            <asp:BoundField DataField="End_Date" HeaderText="End Date" SortExpression="End_Date"
                                ItemStyle-HorizontalAlign="Center">
                                <ItemStyle HorizontalAlign="Center"></ItemStyle>
                            </asp:BoundField>
                            <asp:BoundField DataField="WorkloadSub_date" HeaderText="Submited Date" SortExpression="WorkloadSub_date"
                                ItemStyle-HorizontalAlign="Center" Visible="False">
                                <ItemStyle HorizontalAlign="Center"></ItemStyle>
                            </asp:BoundField>
                            <asp:TemplateField HeaderText="On/Off" ItemStyle-HorizontalAlign="Center">
                                <ItemTemplate>
                                    <asp:Label ID="lblstyle" runat="server" Text='<%# BIND("Style") %>'></asp:Label>
                                </ItemTemplate>
                                <ItemStyle HorizontalAlign="Center"></ItemStyle>
                            </asp:TemplateField>
                            <asp:TemplateField HeaderText="Status" ItemStyle-HorizontalAlign="Center" Visible="False">
                                <ItemTemplate>
                                    <asp:Label ID="lblStatus" runat="server" Text='<%# BIND("Status") %>'></asp:Label>
                                </ItemTemplate>
                                <ItemStyle HorizontalAlign="Center"></ItemStyle>
                            </asp:TemplateField>
                            <asp:TemplateField>
                                <ItemTemplate>
                                    <asp:LinkButton ID="btn_change" runat="server" PostBackUrl='<%# Eval("Cid","Date_Change.aspx?Cid={0}") %>'
                                        OnClick="btn_change_Click">Date Extend</asp:LinkButton>
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
                    <table>
                        <tr>
                            <td style="height: 34px">
                                &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;
                            </td>
                            <td style="border-style: dashed; border-width: thin; height: 34px; width: 164px; padding-left:20px;">
                                Customer Id:
                            </td>
                            <td style="border-style: dashed; border-width: thin; height: 34px;">
                                <asp:Label ID="lbl_cid" runat="server"></asp:Label>
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
