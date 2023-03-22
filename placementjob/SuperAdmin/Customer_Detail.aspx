<%@ Page Language="C#" AutoEventWireup="true" CodeFile="Customer_Detail.aspx.cs" Inherits="Admin_Customer_Detail" MasterPageFile="~/SuperAdmin/AdminMaster.master" Title="Customer Detail" %>

<%@ Register Assembly="AjaxControlToolkit" Namespace="AjaxControlToolkit" TagPrefix="asp" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <td>
        <asp:ToolkitScriptManager ID="ToolkitScriptManager1" runat="server">
        </asp:ToolkitScriptManager>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td align="left" valign="top" class="pro_tit">
                    Client Registration
                </td>
            </tr>
            <tr>
                <td><br /></td>
            </tr>
            <tr>
               <td style="padding:10px; border-style: dashed; border-width: thin; margin: 4px;">                    
                    Select Date:&nbsp;&nbsp;                    
                    <asp:TextBox ID="txt_datefrom" runat="server" ></asp:TextBox>
                    <asp:CalendarExtender ID="CalendarExtender1" runat="server" TargetControlID="txt_datefrom" DaysModeTitleFormat="dd-MM-yyyy" TodaysDateFormat="dd-MM-yyyy" Format="dd-MM-yyyy">
                    </asp:CalendarExtender>
                    
                    &nbsp;&nbsp;                    
                    <asp:TextBox ID="txt_dateto" runat="server"></asp:TextBox>
                    <asp:CalendarExtender ID="txt_date0_CalendarExtender" runat="server" 
                        TargetControlID="txt_dateto" DaysModeTitleFormat="dd-MM-yyyy" TodaysDateFormat="dd-MM-yyyy" Format="dd-MM-yyyy">
                    </asp:CalendarExtender>
                    
                    &nbsp;&nbsp;
                    <asp:Button ID="Button1" runat="server" onclick="Button1_Click" 
                        Text="Show Client" />
                    
                </td>
            </tr>
          <%--  <tr>
                <td>
                    Select Franchisee:&nbsp;&nbsp;&nbsp;&nbsp;
                    <asp:DropDownList ID="drd_fran" runat="server" AutoPostBack="True" OnSelectedIndexChanged="drd_fran_SelectedIndexChanged"
                        Style="height: 22px">
                    </asp:DropDownList>
                </td>
            </tr>--%>
            <tr>
                <td>
                    <br />
                </td>
            </tr>
            <tr>
                <td>
                    <asp:GridView ID="grd_client_active" runat="server" AutoGenerateColumns="False" Width="100%"
                        CellPadding="4" ForeColor="#333333" GridLines="None" 
                        onrowdatabound="grd_client_active_RowDataBound" HeaderStyle-HorizontalAlign="Center" HeaderStyle-VerticalAlign ="Middle" >
                        <RowStyle BackColor="#F7F6F3" ForeColor="#333333" />
                        <Columns>                            
                            
                            <asp:TemplateField HeaderText="CId" SortExpression="CId">
                                <ItemTemplate>
                                    <asp:Label ID="CId" runat="server" Text='<%# Bind("CId") %>'></asp:Label>
                                </ItemTemplate>
                                <EditItemTemplate>
                                    <asp:TextBox ID="CId" runat="server" Text='<%# Bind("CId") %>'></asp:TextBox>
                                </EditItemTemplate>
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
                            
                            <asp:TemplateField HeaderText="Franchisee" ItemStyle-HorizontalAlign ="Center" >
                                <ItemTemplate>
                                    <asp:Label ID="lblfid" runat="server" Text='<%# BIND("FId") %>'></asp:Label>
                                </ItemTemplate>

<ItemStyle HorizontalAlign="Center"></ItemStyle>
                            </asp:TemplateField>
                            
                            
                            <asp:BoundField DataField="callername" HeaderText="Caller Name" SortExpression="callername" />
                            
                            
                            <asp:TemplateField HeaderText="Address" SortExpression="Address">
                                <ItemTemplate>
                                    <asp:Label ID="Address" runat="server" Text='<%# Bind("Address") %>'></asp:Label>
                                </ItemTemplate>
                                <EditItemTemplate>
                                    <asp:TextBox ID="Address" runat="server" Text='<%# Bind("Address") %>'></asp:TextBox>
                                </EditItemTemplate>
                                <ItemStyle HorizontalAlign="Center" />
                            </asp:TemplateField>
                            <asp:TemplateField HeaderText="CompanyName" SortExpression="CompanyName">
                                <ItemTemplate>
                                    <asp:Label ID="CompanyName" runat="server" Text='<%# Bind("CompanyName") %>'></asp:Label>
                                </ItemTemplate>
                                <EditItemTemplate>
                                    <asp:TextBox ID="CompanyName" runat="server" Text='<%# Bind("CompanyName") %>'></asp:TextBox>
                                </EditItemTemplate>
                                <ItemStyle HorizontalAlign="Center" />
                            </asp:TemplateField>
                            <asp:BoundField DataField="ipaddress" HeaderText="Ip Address" SortExpression="ipaddress" />
                            <asp:TemplateField HeaderText="City" SortExpression="City">
                                <ItemTemplate>
                                    <asp:Label ID="City" runat="server" Text='<%# Bind("City") %>'></asp:Label>
                                </ItemTemplate>
                                <EditItemTemplate>
                                    <asp:TextBox ID="City" runat="server" Text='<%# Bind("City") %>'></asp:TextBox>
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
                            <asp:TemplateField HeaderText="PlanId" SortExpression="PlanId">
                                <ItemTemplate>
                                    <asp:Label ID="PlanId" runat="server" Text='<%# Bind("PlanId") %>'></asp:Label>
                                </ItemTemplate>
                                <EditItemTemplate>
                                    <asp:TextBox ID="PlanId" runat="server" Text='<%# Bind("PlanId") %>'></asp:TextBox>
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
                                    <asp:TextBox ID="WorkloadSub_date" runat="server" 
                                        Text='<%# Bind("WorkloadSub_date") %>'></asp:TextBox>
                                </EditItemTemplate>
                                <ItemStyle HorizontalAlign="Center" />
                            </asp:TemplateField>
                            <asp:TemplateField HeaderText="On/Off" ItemStyle-HorizontalAlign ="Center">
                                <ItemTemplate>
                                    <asp:Label ID="lbl" runat="server" Text='<%# BIND("Style") %>'></asp:Label>
                                </ItemTemplate>

<ItemStyle HorizontalAlign="Center"></ItemStyle>
                            </asp:TemplateField>
                            <asp:TemplateField HeaderText="Status" ItemStyle-HorizontalAlign ="Center">
                                <ItemTemplate>
                                    <asp:Label ID="lblStatus" runat="server" Text='<%# BIND("Status") %>'></asp:Label>
                                </ItemTemplate>

<ItemStyle HorizontalAlign="Center"></ItemStyle>
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
                    <%--<asp:GridView ID="grd_client_deactive" runat="server" AutoGenerateColumns="False"
                        Width="100%" CellPadding="4" ForeColor="#333333" GridLines="None">
                        <RowStyle BackColor="#F7F6F3" ForeColor="#333333" />
                        <Columns>
                            <asp:TemplateField Visible="False">
                                <ItemTemplate>
                                    <asp:LinkButton ID="btn_deactivate" runat="server" OnClick="btn_deactivate_Click"
                                        PostBackUrl='<%# Eval("cid" ,"ClientRegistration.aspx?cid={0}") %>'>Deactive</asp:LinkButton>
                                </ItemTemplate>
                            </asp:TemplateField>
                            <asp:BoundField DataField="CId" HeaderText="CId" SortExpression="CId" Visible="False" />
                            <asp:BoundField DataField="Name" HeaderText="Name" SortExpression="Name" />
                            <asp:BoundField DataField="Address" HeaderText="Address" SortExpression="Address" />
                            <asp:BoundField DataField="MobileNo" HeaderText="MobileNo" SortExpression="MobileNo" />
                            <asp:BoundField DataField="Reg_Date" HeaderText="Reg. Date" SortExpression="Reg_Date" />
                            <asp:BoundField DataField="EmailId" HeaderText="EmailId" SortExpression="EmailId" />
                            <asp:BoundField DataField="PlanId" HeaderText="PlanId" SortExpression="PlanId" />
                            <asp:TemplateField>
                                <EditItemTemplate>
                                    <asp:TextBox ID="TextBox1" runat="server"></asp:TextBox>
                                </EditItemTemplate>
                                <ItemTemplate>
                                    <asp:LinkButton ID="btn_aggriment" runat="server" OnClick="btn_aggriment_Click" PostBackUrl='<%# Eval("cid","ClientRegistration.aspx?cid={0}") %>'>Download</asp:LinkButton>
                                </ItemTemplate>
                            </asp:TemplateField>                            
                            <asp:TemplateField>
                                <ItemTemplate>
                                    <asp:LinkButton ID="btn_send" runat="server" OnClick="btn_send_Click" PostBackUrl='<%# Eval("cid","ClientRegistration.aspx?cid={0}") %>'>Send</asp:LinkButton>
                                </ItemTemplate>
                            </asp:TemplateField>
                            <asp:TemplateField>
                                <ItemTemplate>
                                    <asp:LinkButton ID="btn_edit" runat="server" onclick="btn_edit_Click" PostBackUrl='<%# Eval("cid","ClientRegistration.aspx?cid={0}") %>'>Edit</asp:LinkButton>
                                </ItemTemplate>
                            </asp:TemplateField>
                              <asp:TemplateField>
                                <ItemTemplate>
                                    <asp:LinkButton ID="btn_delete1" runat="server" 
                                        PostBackUrl='<%# Eval("Cid" ,"ClientRegistration.aspx?Cid={0}") %>' 
                                        OnClick="btn_delete1_Click">Delete</asp:LinkButton>
                                </ItemTemplate>
                            </asp:TemplateField>                           
                        </Columns>
                        <FooterStyle BackColor="#5D7B9D" Font-Bold="True" ForeColor="White" />
                        <PagerStyle BackColor="#284775" ForeColor="White" HorizontalAlign="Center" />
                        <SelectedRowStyle BackColor="#E2DED6" Font-Bold="True" ForeColor="#333333" />
                        <HeaderStyle BackColor="#DE7037" Font-Bold="True" ForeColor="White" />
                        <EditRowStyle BackColor="#999999" />
                        <AlternatingRowStyle BackColor="White" ForeColor="#284775" />
                    </asp:GridView>--%>
                </td>
            </tr>
            <tr>
                <td style="height: 18px">
                    <br />
                </td>
            </tr>
            <tr>
                <td>
                    <%--<table border="1" frame="border" width="100%" style="color: #4b4b4b" cellspacing="8px"
                        width="100%">
                        <tr>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                Customer Id                             </td>
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
                                &nbsp;</td>
                            <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                                <asp:Button ID="btn_update" runat="server" Text="Update" 
                                    ValidationGroup="valgrp" OnClick="btn_update_Click" />
                            </td>
                        </tr>
                    </table>--%>
                </td>
    </tr> </table> </td>
</asp:Content>

