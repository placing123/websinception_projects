<%@ Page Language="C#" AutoEventWireup="true" CodeFile="WorkSpace.aspx.cs" Inherits="Admin_WorkSpace"
    MasterPageFile="~/SuperAdmin/AdminMaster.master" Title="Work Space" %>

<%@ Register Assembly="AjaxControlToolkit" Namespace="AjaxControlToolkit" TagPrefix="asp" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <td>
        <asp:ToolkitScriptManager ID="ToolkitScriptManager1" runat="server">
        </asp:ToolkitScriptManager>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td align="left" valign="top" class="pro_tit">
                    Work Space
                </td>
            </tr>
            <tr>
                <td>
                    <br />
                </td>
            </tr>
            <tr>
                <td style="padding: 17px; border-style: dashed; border-color: Gray; border-width: thin;
                    margin: 4px" align="center">
                    <span style="border-style: dashed; border-color: Gray; border-width: thin; padding: 8px;
                        margin: 4px">CustomerID:: </span>&nbsp;&nbsp; <span style="border-style: dashed;
                            border-color: Gray; border-width: thin; padding: 8px; margin: 4px">
                            <asp:TextBox ID="txtCID" runat="server"></asp:TextBox></span> &nbsp;&nbsp;&nbsp;&nbsp;
                    <span style="border-style: dashed; border-color: Gray; border-width: thin; padding: 8px;
                        margin: 4px">Name::</span> &nbsp;&nbsp; <span style="border-style: dashed; border-color: Gray;
                            border-width: thin; padding: 8px; margin: 4px">
                            <asp:TextBox ID="txtNAME" runat="server"></asp:TextBox></span>&nbsp;&nbsp;&nbsp;&nbsp;
                    <span style="border-style: dashed; border-color: Gray; border-width: thin; padding: 8px;
                        margin: 4px">Email::</span> &nbsp;&nbsp; <span style="border-style: dashed; border-color: Gray;
                            border-width: thin; padding: 8px; margin: 4px">
                            <asp:TextBox ID="txt_emailid" runat="server"></asp:TextBox></span> &nbsp;&nbsp;&nbsp;&nbsp;
                    &nbsp;&nbsp;&nbsp;&nbsp; <span style="border-style: dashed; border-color: Gray;
                            border-width: thin; padding: 8px; margin: 4px">
                            <asp:Button ID="butSUBMIT" runat="server" Text="Submit" OnClick="butSUBMIT_Click" /></span>
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
                            <asp:TemplateField HeaderText="Logs">
                                <ItemTemplate>
                                    <asp:LinkButton ID="btn_log" runat="server" PostBackUrl='<%# Eval("Cid" ,"WorkSpace.aspx?Cid={0}") %>'
                                        OnClick="btn_log_Click">Log</asp:LinkButton>
                                </ItemTemplate>
                            </asp:TemplateField>
                            <asp:TemplateField HeaderText="Work Space">
                                <ItemTemplate>
                                    <asp:LinkButton ID="btn_OPEN" runat="server" PostBackUrl='<%# Eval("Cid" ,"WorkSpace.aspx?Cid={0}") %>'
                                        OnClick="btn_OPEN_Click">Open</asp:LinkButton>
                                </ItemTemplate>
                            </asp:TemplateField>
                            <asp:TemplateField HeaderText="CId" SortExpression="CId">
                                <ItemTemplate>
                                    <asp:Label ID="CId" runat="server" Text='<%# Bind("CId") %>'></asp:Label>
                                </ItemTemplate>
                                <EditItemTemplate>
                                    <asp:TextBox ID="CId" runat="server" Text='<%# Bind("CId") %>'></asp:TextBox>
                                </EditItemTemplate>
                                <ItemStyle HorizontalAlign="Center" />
                            </asp:TemplateField>
                            <asp:TemplateField HeaderText="Password" SortExpression="Password">
                                <ItemTemplate>
                                    <asp:Label ID="Password" runat="server" Text='<%# Bind("Password") %>'></asp:Label>
                                </ItemTemplate>
                                <ItemStyle HorizontalAlign="Center" />
                            </asp:TemplateField>
                            <asp:TemplateField HeaderText="Name" SortExpression="Name">
                                <ItemTemplate>
                                    <asp:Label ID="Name" runat="server" Text='<%# Bind("Name") %>'></asp:Label>
                                </ItemTemplate>
                                <EditItemTemplate>
                                    <asp:TextBox ID="Name" runat="server" Text='<%# Bind("Name") %>'></asp:TextBox>
                                </EditItemTemplate>
                                <ItemStyle HorizontalAlign="Center" />
                            </asp:TemplateField>
                            <asp:TemplateField HeaderText="Address" SortExpression="Address">
                                <ItemTemplate>
                                    <asp:Label ID="Address" runat="server" Text='<%# Bind("Address") %>'></asp:Label>
                                </ItemTemplate>
                                <EditItemTemplate>
                                    <asp:TextBox ID="Address" runat="server" Text='<%# Bind("Address") %>'></asp:TextBox>
                                </EditItemTemplate>
                                <ItemStyle HorizontalAlign="Center" />
                            </asp:TemplateField>
                            
                            
                            <asp:TemplateField HeaderText="MobileNo" SortExpression="MobileNo">
                                <ItemTemplate>
                                    <asp:Label ID="MobileNo" runat="server" Text='<%# Bind("MobileNo") %>'></asp:Label>
                                </ItemTemplate>
                                <EditItemTemplate>
                                    <asp:TextBox ID="MobileNo" runat="server" Text='<%# Bind("MobileNo") %>'></asp:TextBox>
                                </EditItemTemplate>
                                <ItemStyle HorizontalAlign="Center" />
                            </asp:TemplateField>
                            <asp:TemplateField HeaderText="EmailId" SortExpression="EmailId">
                                <ItemTemplate>
                                    <asp:Label ID="EmailId" runat="server" Text='<%# Bind("EmailId") %>'></asp:Label>
                                </ItemTemplate>
                                <EditItemTemplate>
                                    <asp:TextBox ID="EmailId" runat="server" Text='<%# Bind("EmailId") %>'></asp:TextBox>
                                </EditItemTemplate>
                                <ItemStyle HorizontalAlign="Center" />
                            </asp:TemplateField>
                            <asp:BoundField DataField="fid" HeaderText="Franchisee" SortExpression="fid" />
                            <asp:BoundField DataField="callername" HeaderText="Caller Name" SortExpression="callername" />
                            <asp:BoundField DataField="CompanyName" HeaderText="Company Name" SortExpression="CompanyName" />
                            <asp:BoundField DataField="ipaddress" HeaderText="IP Address" SortExpression="ipaddress" />
                            <asp:TemplateField HeaderText="Plan Name" SortExpression="planname">
                                <ItemTemplate>
                                    <asp:Label ID="planname" runat="server" Text='<%# Bind("planname") %>'></asp:Label>
                                </ItemTemplate>
                                <EditItemTemplate> 
                                    <asp:TextBox ID="planname" runat="server" Text='<%# Bind("planname") %>'></asp:TextBox>
                                </EditItemTemplate>
                                <ItemStyle HorizontalAlign="Center" />
                            </asp:TemplateField>
                            <asp:TemplateField HeaderText="Reg. Date" SortExpression="Reg_Date">
                                <ItemTemplate>
                                    <asp:Label ID="Reg_Date" runat="server" Text='<%# Bind("Reg_Date") %>'></asp:Label>
                                </ItemTemplate>
                                <EditItemTemplate>
                                    <asp:TextBox ID="Reg_Date" runat="server" Text='<%# Bind("Reg_Date") %>'></asp:TextBox>
                                </EditItemTemplate>
                                <ItemStyle HorizontalAlign="Center" />
                            </asp:TemplateField>
                            <asp:TemplateField HeaderText="Start Date" SortExpression="Start_Date">
                                <ItemTemplate>
                                    <asp:Label ID="Start_Date" runat="server" Text='<%# Bind("Start_Date") %>'></asp:Label>
                                </ItemTemplate>
                                <EditItemTemplate>
                                    <asp:TextBox ID="Start_Date" runat="server" Text='<%# Bind("Start_Date") %>'></asp:TextBox>
                                </EditItemTemplate>
                                <ItemStyle HorizontalAlign="Center" />
                            </asp:TemplateField>
                            <asp:TemplateField HeaderText="End Date" SortExpression="End_Date">
                                <ItemTemplate>
                                    <asp:Label ID="End_Date" runat="server" Text='<%# Bind("End_Date") %>'></asp:Label>
                                </ItemTemplate>
                                <EditItemTemplate>
                                    <asp:TextBox ID="End_Date" runat="server" Text='<%# Bind("End_Date") %>'></asp:TextBox>
                                </EditItemTemplate>
                                <ItemStyle HorizontalAlign="Center" />
                            </asp:TemplateField>
                            <asp:TemplateField HeaderText="Submited Date" SortExpression="WorkloadSub_date">
                                <ItemTemplate>
                                    <asp:Label ID="WorkloadSub_date" runat="server" Text='<%# Bind("WorkloadSub_date") %>'></asp:Label>
                                </ItemTemplate>
                                <EditItemTemplate>
                                    <asp:TextBox ID="WorkloadSub_date" runat="server" Text='<%# Bind("WorkloadSub_date") %>'></asp:TextBox>
                                </EditItemTemplate>
                                <ItemStyle HorizontalAlign="Center" />
                            </asp:TemplateField>
                            <asp:TemplateField HeaderText="Completed Form" SortExpression="completeform">
                                <ItemTemplate>
                                    <asp:Label ID="completeform" runat="server" Text='<%# Bind("completeform") %>'></asp:Label>
                                </ItemTemplate>
                                <EditItemTemplate>
                                    <asp:TextBox ID="completeform" runat="server" Text='<%# Bind("completeform") %>'></asp:TextBox>
                                </EditItemTemplate>
                                <ItemStyle HorizontalAlign="Center" />
                            </asp:TemplateField>
                            <asp:TemplateField HeaderText="Fail Form" SortExpression="failform">
                                <ItemTemplate>
                                    <asp:Label ID="failform" runat="server" Text='<%# Bind("failform") %>'></asp:Label>
                                </ItemTemplate>
                                <EditItemTemplate>
                                    <asp:TextBox ID="failform" runat="server" Text='<%# Bind("failform") %>'></asp:TextBox>
                                </EditItemTemplate>
                                <ItemStyle HorizontalAlign="Center" />
                            </asp:TemplateField>
                            <asp:TemplateField HeaderText="Pass Form" SortExpression="passform">
                                <ItemTemplate>
                                    <asp:Label ID="passform" runat="server" Text='<%# Bind("passform") %>'></asp:Label>
                                </ItemTemplate>
                                <EditItemTemplate>
                                    <asp:TextBox ID="passform" runat="server" Text='<%# Bind("passform") %>'></asp:TextBox>
                                </EditItemTemplate>
                                <ItemStyle HorizontalAlign="Center" />
                            </asp:TemplateField>
                            <asp:TemplateField HeaderText="Space Form" SortExpression="spaceform">
                                <ItemTemplate>
                                    <asp:Label ID="spaceform" runat="server" Text='<%# Bind("spaceform") %>'></asp:Label>
                                </ItemTemplate>
                                <EditItemTemplate>
                                    <asp:TextBox ID="spaceform" runat="server" Text='<%# Bind("spaceform") %>'></asp:TextBox>
                                </EditItemTemplate>
                                <ItemStyle HorizontalAlign="Center" />
                            </asp:TemplateField>
							<asp:TemplateField HeaderText="Form" SortExpression="form">
                                <ItemTemplate>
                                    <asp:Label ID="form" runat="server" Text='<%# Bind("form") %>'></asp:Label>
                                </ItemTemplate>
                                <EditItemTemplate>
                                    <asp:TextBox ID="form" runat="server" Text='<%# Bind("form") %>'></asp:TextBox>
                                </EditItemTemplate>
                                <ItemStyle HorizontalAlign="Center" />
                            </asp:TemplateField>
                            <asp:TemplateField HeaderText="On/Off" ItemStyle-HorizontalAlign="Center">
                                <ItemTemplate>
                                    <asp:Label ID="lbl" runat="server" Text='<%# BIND("Style") %>'></asp:Label>
                                </ItemTemplate>
                                <ItemStyle HorizontalAlign="Center"></ItemStyle>
                            </asp:TemplateField>
                            <asp:TemplateField HeaderText="Status" ItemStyle-HorizontalAlign="Center">
                                <ItemTemplate>
                                    <asp:Label ID="lblStatus" runat="server" Text='<%# BIND("Status") %>'></asp:Label>
                                </ItemTemplate>
                                <ItemStyle HorizontalAlign="Center"></ItemStyle>
                            </asp:TemplateField>
                            <asp:TemplateField HeaderText="Clear">
                                <ItemTemplate>
                                    <asp:LinkButton ID="btn_clr" runat="server" PostBackUrl='<%# Eval("Cid" ,"WorkSpace.aspx?Cid={0}") %>'
                                        OnClick="btn_clr_Click">Cleare</asp:LinkButton>
                                </ItemTemplate>
                            </asp:TemplateField>
                            <asp:TemplateField HeaderText="Change">
                                <ItemTemplate>
                                    <asp:LinkButton ID="btn_chg" runat="server" PostBackUrl='<%# Eval("Cid" ,"WorkSpace.aspx?Cid={0}") %>'
                                        OnClick="btn_chg_Click">Change Pass</asp:LinkButton>
                                </ItemTemplate>
                            </asp:TemplateField>
                            <asp:TemplateField HeaderText="Change">
                                <ItemTemplate>
                                    <asp:LinkButton ID="btn_chg_fl" runat="server" PostBackUrl='<%# Eval("Cid" ,"WorkSpace.aspx?Cid={0}") %>'
                                        OnClick="btn_chg_fl_Click">Change Fail</asp:LinkButton>
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
            <tr id="tbllog" runat="server" visible="false">
                <td>
                    <asp:Panel ID="Panel1" runat="server">
                    <asp:GridView ID="GridView1" runat="server" CellPadding="4" ForeColor="#333333" 
                        GridLines="None" AutoGenerateColumns="False" Width="100%">
                        <RowStyle BackColor="#F7F6F3" ForeColor="#333333" />
                        <FooterStyle BackColor="#5D7B9D" Font-Bold="True" ForeColor="White" />
                        <PagerStyle BackColor="#284775" ForeColor="White" HorizontalAlign="Center" />
                        <SelectedRowStyle BackColor="#E2DED6" Font-Bold="True" ForeColor="#333333" />
                        <HeaderStyle BackColor="#DE7037" Font-Bold="True" ForeColor="White" HorizontalAlign="Center" />
                        <Columns>
                            <asp:BoundField DataField="CId" HeaderText="Customer Id" SortExpression="CId" />
                            <asp:BoundField DataField="Work" HeaderText="Work" SortExpression="Work" />
                        </Columns>
                        <EditRowStyle BackColor="#999999" />
                        <AlternatingRowStyle BackColor="White" ForeColor="#284775" />
                    </asp:GridView>
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
                    <br />
                </td>
            </tr>
            <tr id="tblwork" runat="server" visible="false">
                <td>
                    <table border="1" frame="border" width="100%" style="color: #4b4b4b" cellspacing="8px">
                        <tr>
                            <td align="left" valign="top" class="pro_tit" colspan="4">
                                Report
                            </td>
                        </tr>
                        <tr>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px; width: 174px;">
                                Customer Id::
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:Label ID="lblCID" runat="server"></asp:Label>
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px; width: 174px;">
                                NAME::
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:Label ID="lblNAME" runat="server"></asp:Label>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="4">
                                <br />
                            </td>
                        </tr>
                        <tr>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px; width: 174px;
                                color: Blue">
                                Total Form::
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:Label ID="lblTFORM" runat="server" ForeColor="Blue"></asp:Label>
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px; width: 174px;">
                                &nbsp;
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                &nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td colspan="4" align="center">
                                <br />
                                <span style="border-style: dashed; border-color: Gray; border-width: thin; padding: 8px;
                                    margin: 4px">Open From:: </span>&nbsp;&nbsp; <span style="border-style: dashed; border-color: Gray;
                                        border-width: thin; padding: 8px; margin: 4px">
                                        <asp:TextBox ID="txtFORMNO" runat="server"></asp:TextBox></span> &nbsp;&nbsp;
                                <span style="border-style: dashed; border-color: Gray; border-width: thin; padding: 8px;
                                    margin: 4px">
                                    <asp:Button ID="butFORM" runat="server" Text="Open" OnClick="butFORM_Click" /></span>
                                Tbc </span> &nbsp;&nbsp; <span style="border-style: dashed; border-color: Gray; border-width: thin;
                                    padding: 8px; margin: 4px">
                                    <asp:TextBox ID="txt_tbc" runat="server"></asp:TextBox></span> &nbsp;&nbsp;
                                <span style="border-style: dashed; border-color: Gray; border-width: thin; padding: 8px;
                                    margin: 4px">
                                    <asp:Button ID="Button1" runat="server" Text="Search" OnClick="Button1_Click" /></span>
                                <span style="border-style: dashed; border-color: Gray; border-width: thin; padding: 8px;
                                    margin: 4px">
                                    <asp:Label ID="Label1" runat="server"></asp:Label>
                                </span>
                                <br />
                                <br />
                            </td>
                        </tr>
                        <tr>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px; width: 174px;
                                color: Green;">
                                Pass Form::
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:Label ID="lblPFORM" runat="server" ForeColor="Green"></asp:Label>
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px; width: 174px;">
                                PASS IMAGE NO::
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:Label ID="lblPFORMDetail" runat="server"></asp:Label>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="4">
                                <br />
                            </td>
                        </tr>
                        <tr>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px; width: 174px;
                                color: Red">
                                Fail Form::
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:Label ID="lblFForm" runat="server" ForeColor="Red"></asp:Label>
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px; width: 174px;">
                                FAIL IMAGE NO::
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:Label ID="lblFFORMDetail" runat="server"></asp:Label>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="4">
                                <br />
                            </td>
                        </tr>
                          <tr>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px; width: 174px;
                                color: Red">
                                Space Form::
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:Label ID="lblSForm" runat="server" ForeColor="Red"></asp:Label>
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px; width: 174px;">
                                Space IMAGE NO::
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:Label ID="lblSFORMDetail" runat="server"></asp:Label>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="4">
                                <br />
                            </td>
                        </tr>
                        <tr>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px; width: 174px;
                                color: black">
                                Empty Form::
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:Label ID="lblEFORM" runat="server" Style="color: #000000"></asp:Label>
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px; width: 174px;">
                                EMPTY IMAGE NO::
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:Label ID="lbl_emt_img_no" runat="server"></asp:Label>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="4">
                                <br />
                            </td>
                        </tr>
                        <tr>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px; width: 174px;
                                color: black">
                                Not Submited Form::
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:Label ID="LBLNOFORM" runat="server" Style="color: #000000"></asp:Label>
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px; width: 174px;">
                                EMPTY IMAGE NO::
                            </td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:Label ID="lbl_no_sub_form" runat="server"></asp:Label>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </td>
</asp:Content>
