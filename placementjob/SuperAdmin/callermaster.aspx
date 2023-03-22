<%@ Page Language="C#" MasterPageFile="~/SuperAdmin/AdminMaster.master" AutoEventWireup="true"
    CodeFile="callermaster.aspx.cs" Inherits="Admin_Plan" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td align="left" valign="top" class="pro_tit">
                    Caller Master
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
                            <td style="width: 138px">
                                Caller Name:
                            </td>
                            <td style="width: 165px">
                                <asp:TextBox ID="txt_callername" runat="server" ValidationGroup="vlg1"></asp:TextBox>
                            </td>
                            <td>
                                &nbsp;</td>
                        </tr>
                        <tr>
                            <td style="width: 29px">
                                &nbsp;
                            </td>
                            <td style="width: 138px">
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
                            <td style="width: 138px">
                                &nbsp;
                            </td>
                            <td style="width: 165px">
                                <asp:Button ID="btn_add" runat="server" Text="Add Caller" ValidationGroup ="vlg1" OnClick="btn_add_Click"  />
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td>
                    <br />
                    <br />
                </td>
            </tr>
            <tr>
                <td>
                    <asp:GridView ID="grd_plan" runat="server" AutoGenerateColumns="False" CellPadding="4"
                        ForeColor="#333333" GridLines="None" Width="50%" OnRowCommand="grd_plan_RowCommand">
                        <RowStyle BackColor="#F7F6F3" ForeColor="#333333" HorizontalAlign="Center" />
                        <Columns>
                           
                            <asp:BoundField DataField="srno" HeaderText="#" SortExpression="srno" />
                            <asp:TemplateField HeaderText="id" SortExpression="id" Visible="False">
                                <EditItemTemplate>
                                    <asp:TextBox ID="TextBox1" runat="server" Text='<%# Bind("id") %>'></asp:TextBox>
                                </EditItemTemplate>
                                <ItemTemplate>
                                    <asp:Label ID="Label1" runat="server" Text='<%# Bind("id") %>'></asp:Label>
                                </ItemTemplate>
                            </asp:TemplateField>
                            <asp:BoundField DataField="callername" HeaderText="Caller Name" SortExpression="callername" />
                           
                            <asp:TemplateField HeaderText="Delete">
                                <ItemTemplate>
                                    <asp:LinkButton ID="LinkButton1" runat="server" CommandArgument="<%# ((GridViewRow) Container).RowIndex %>" CommandName="deletecaller">Delete</asp:LinkButton>
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
                </td>
            </tr>
        </table>
    </td>
</asp:Content>
