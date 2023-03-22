<%@ Page Title="" Language="C#" MasterPageFile="~/SuperAdmin/AdminMaster.master" AutoEventWireup="true"
    CodeFile="error.aspx.cs" Inherits="Control_error" %>

<asp:content id="Content1" contentplaceholderid="ContentPlaceHolder1" runat="Server">
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td align="left" valign="top" class="pro_tit">
                    Error
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
                            <td style="width: 127px">
                                Correct Word :
                            </td>
                            <td style="width: 165px">
                                <asp:textbox id="txt_correct" runat="server" MaxLength="1" 
                                    ValidationGroup="valgrp" ></asp:textbox>
                            </td>
                            <td>
                                <asp:requiredfieldvalidator id="RequiredFieldValidator2" runat="server" controltovalidate="txt_correct"
                                    errormessage="Enter correct word" validationgroup="valgrp">
                                </asp:requiredfieldvalidator>
                            </td>
                        </tr>
                        <tr>
                            <td style="width: 29px">
                                &nbsp;
                            </td>
                            <td style="width: 127px">
                                Error Word :&nbsp;
                            </td>
                            <td style="width: 165px">
                                <asp:textbox id="txt_error" runat="server" MaxLength="1" ValidationGroup="x`"></asp:textbox>
                            </td>
                            <td>
                                <asp:requiredfieldvalidator id="RequiredFieldValidator1" runat="server" controltovalidate="txt_error"
                                    errormessage="Enter erroe word" validationgroup="valgrp">
                                </asp:requiredfieldvalidator>
                            </td>
                        </tr>
                        <tr>
                            <td style="width: 29px">
                                &nbsp;
                            </td>
                            <td style="width: 127px">
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
                            <td style="width: 127px">
                                &nbsp;
                            </td>
                            <td style="width: 165px">
                                <asp:button id="btn_submit" runat="server" onclick="btn_submit_Click" text="Submit"
                                    validationgroup="valgrp" style="height: 26px" />
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
                    <asp:gridview id="grd_plan" runat="server" autogeneratecolumns="False" cellpadding="4"
                        forecolor="#333333" gridlines="None" width="100%">
                        <rowstyle backcolor="#F7F6F3" forecolor="#333333" horizontalalign="Center" />
                        <columns>
                            <asp:TemplateField HeaderText="Id" SortExpression="Id" Visible="False" >
                                <ItemTemplate>
                                    <asp:Label ID="Label1" runat="server" Text='<%# Bind("Id") %>'></asp:Label>
                                </ItemTemplate>
                                <EditItemTemplate>
                                    <asp:TextBox ID="TextBox1" runat="server" Text='<%# Bind("Id") %>'></asp:TextBox>
                                </EditItemTemplate>
                            </asp:TemplateField>
                            <asp:BoundField DataField="Correct" HeaderText="Correct" 
                                SortExpression="Correct"></asp:BoundField>
                            <asp:BoundField DataField="Error" HeaderText="Error" SortExpression="Error">
                            </asp:BoundField>
                            
                            <asp:TemplateField>
                                <ItemTemplate>
                                    <asp:LinkButton ID="btn_delete" runat="server" onclick="btn_delete_Click" 
                                        PostBackUrl='<%# Eval("Id" ,"error.aspx?Id={0}") %>'>Delete</asp:LinkButton>
                                </ItemTemplate>
                            </asp:TemplateField>
                        </columns>
                        <footerstyle backcolor="#5D7B9D" font-bold="True" forecolor="White" />
                        <pagerstyle backcolor="#284775" forecolor="White" horizontalalign="Center" />
                        <selectedrowstyle backcolor="#E2DED6" font-bold="True" forecolor="#333333" />
                        <headerstyle backcolor="#DE7037" font-bold="True" forecolor="White" horizontalalign="Center" />
                        <editrowstyle backcolor="#999999" />
                        <alternatingrowstyle backcolor="White" forecolor="#284775" />
                    </asp:gridview>
                </td>
            </tr>
            <tr>
                <td>
                </td>
            </tr>
        </table>
    </td>
</asp:content>
