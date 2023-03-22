<%@ Page Title="" Language="C#" MasterPageFile="~/SuperAdmin/AdminMaster.master" AutoEventWireup="true"
    CodeFile="pdfread.aspx.cs" Inherits="Control_pdfread" %>

<asp:content id="Content1" contentplaceholderid="ContentPlaceHolder1" runat="Server">
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td align="left" valign="top" class="pro_tit">
                    Excel Read
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
                    <table>
                        <tr>
                            <td style="height: 34px">
                                &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;
                            </td>
                            <td style="border-style: dashed; border-width: thin; height: 34px; width: 164px;
                                padding-left: 20px;">
                                Select Excel :
                            </td>
                            <td style="border-style: dashed; border-width: thin; height: 34px;">
                                <asp:fileupload runat="server" id="FileUpload1">
                                </asp:fileupload>
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
                                <asp:button id="btn_read" runat="server" text="Read" onclick="btn_read_Click" style="height: 26px" />
                            </td>
                            <td>
                            
                                <asp:button runat="server" text="Update" onclick="Unnamed1_Click" />
                                
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
                            <td>
                                <asp:label id="cid" runat="server" text=""></asp:label>
                            </td>
                            <td>
                                <asp:label id="form" runat="server" text=""></asp:label>
                            </td>
                            <td>
                                <asp:label id="report" runat="server" text=""></asp:label>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <br />
                            </td>
                        </tr>
                         <tr>
                            <td>
                            </td>
                            <td>
                                Pass
                            </td>
                            <td>
                                Fail
                            </td>
                        </tr>
                        <tr>
                            <td>
                            </td>
                            <td>
                                <asp:label id="no_pass" runat="server" text=""></asp:label>
                            </td>
                            <td>
                                <asp:label id="no_fail" runat="server" text=""></asp:label>
                            </td>
                        </tr>
                         <tr>
                            <td>
                                <br />
                            </td>
                        </tr>
                         <tr>
                            <td>
                            </td>
                            <td>
                                &nbsp;</td>
                            <td>
                                
                                &nbsp;</td>
                        </tr>
                        <%--<tr>
                        <td>
                        </td>
                        <td>
                        QC
                        </td>
                        <td>
                                <asp:label id="result" runat="server" text=""></asp:label>
                        
                        </td>

                        </tr>--%>
                    </table>
                </td>
            </tr>
        </table>
    </td>
</asp:content>
