<%@ Page Title="" Language="C#" MasterPageFile="~/Franchisee/Franchisee.master" AutoEventWireup="true"
    CodeFile="Plan.aspx.cs" Inherits="Franchisee_Plan" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <span class="mid_lef_tit" style="color: #16C0A7;">Plan Details</span></div>
    <table style="width: 100%">
        <tr>
            <td align="center">
                <asp:GridView ID="viewplan" runat="server" AllowPaging="True" AllowSorting="True"
                    AutoGenerateColumns="False" BackColor="White" BorderColor="#CCCCCC" BorderStyle="None"
                    BorderWidth="1px" CellPadding="3" EmptyDataText="&lt;center&gt;&lt;Font style=&quot;color: Red; text-decoration: blink;&quot;&gt;Sorry... No Records Found...!!!&lt;/Font&gt;&lt;center&gt;"
                    HeaderStyle-BackColor="Black" HeaderStyle-ForeColor="White" PageSize="200" Width="100%">
                    <Columns>
                        <asp:TemplateField HeaderText="PId" ItemStyle-HorizontalAlign="Center" Visible="false">
                            <ItemTemplate>
                                <asp:Label ID="lbl1" runat="server" Text='<%#Bind ("PId") %>'></asp:Label>
                            </ItemTemplate>
                            <ItemStyle HorizontalAlign="Center" />
                        </asp:TemplateField>
                        <asp:TemplateField HeaderText="Plan Name" ItemStyle-HorizontalAlign="Center" Visible="true">
                            <ItemTemplate>
                                <asp:Label ID="lbl2" runat="server" Text='<%#Bind ("PName") %>'></asp:Label>
                            </ItemTemplate>
                            <ItemStyle HorizontalAlign="Center" />
                        </asp:TemplateField>
                        <asp:TemplateField HeaderText="Form" ItemStyle-HorizontalAlign="Center" Visible="true">
                            <ItemTemplate>
                                <asp:Label ID="lblmonthhomesbl" runat="server" Text='<%#Bind ("Form") %>'></asp:Label>
                            </ItemTemplate>
                            <ItemStyle HorizontalAlign="Center" />
                        </asp:TemplateField>
                        <asp:TemplateField HeaderText="QcCutOff" ItemStyle-HorizontalAlign="Center" Visible="true">
                            <ItemTemplate>
                                <asp:Label ID="lblpending" runat="server" Text='<%#Bind ("QcCutOff") %>'></asp:Label>
                            </ItemTemplate>
                            <ItemStyle HorizontalAlign="Center" />
                        </asp:TemplateField>
                        <asp:TemplateField HeaderText="Days" ItemStyle-HorizontalAlign="Center" Visible="true">
                            <ItemTemplate>
                                <asp:Label ID="lbltotal" runat="server" Text='<%#Bind ("Days") %>'></asp:Label>
                            </ItemTemplate>
                            <ItemStyle HorizontalAlign="Center" />
                        </asp:TemplateField>
                        <asp:TemplateField HeaderText="Fees" ItemStyle-HorizontalAlign="Center" Visible="true">
                            <ItemTemplate>
                                <asp:Label ID="lbl3" runat="server" Text='<%#Bind ("Fees") %>'></asp:Label>
                            </ItemTemplate>
                            <ItemStyle HorizontalAlign="Center" />
                        </asp:TemplateField>
                    </Columns>
                    <FooterStyle BackColor="White" ForeColor="#000066" />
                    <HeaderStyle BackColor="#16C0A7" Font-Bold="True" ForeColor="White" />
                    <PagerStyle BackColor="White" ForeColor="#000066" HorizontalAlign="Left" />
                    <RowStyle ForeColor="#000066" />
                    <SelectedRowStyle BackColor="#669999" Font-Bold="True" ForeColor="White" />
                    <%--<SortedAscendingCellStyle BackColor="#F1F1F1" />
                    <SortedAscendingHeaderStyle BackColor="#007DBB" />
                    <SortedDescendingCellStyle BackColor="#CAC9C9" />
                    <SortedDescendingHeaderStyle BackColor="#00547E" />--%>
                </asp:GridView>
            </td>
        </tr>
    </table>   
</asp:Content>
