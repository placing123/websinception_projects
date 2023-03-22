<%@ Page Title="" Language="C#" MasterPageFile="~/SuperAdmin/AdminMaster.master" AutoEventWireup="true"
    CodeFile="Email.aspx.cs" Inherits="Control_Email" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <td>
        <td align="left" valign="top" class="das_min">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="left" valign="top" class="pro_tit">
                        Dashboard
                    </td>
                </tr>
                <tr>
                    <td>
                        <div>
                            <table style="border: 1px solid" align="left" width="100%">
                                <tr>
                                    <td style="width: 136px">
                                        To:
                                    </td>
                                    <td>
                                        <asp:TextBox ID="txtTo" runat="server" Width="100%"></asp:TextBox>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width: 136px">
                                        Subject:
                                    </td>
                                    <td>
                                        <asp:TextBox ID="txtSubject" runat="server" Width="100%" Style="margin-right: 284px"></asp:TextBox>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width: 136px">
                                        Attach a file:
                                    </td>
                                    <td>
                                        <asp:FileUpload ID="fileUpload1" runat="server" />
                                        <br />
                                        <asp:FileUpload ID="FileUpload2" runat="server" />
                                        <br />
                                        <asp:FileUpload ID="FileUpload3" runat="server" />
                                        <br />
                                        <asp:FileUpload ID="FileUpload4" runat="server" />
                                        <br />
                                        <asp:FileUpload ID="FileUpload5" runat="server" />
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        CC:
                                    </td>
                                    <td>
                                        <asp:TextBox ID="txt_cc" runat="server" Width="100%"></asp:TextBox>
                                    </td>
                                </tr>
                                 <tr>
                                    <td>
                                        BCC:
                                    </td>
                                    <td>
                                        <asp:TextBox ID="txt_bcc" runat="server" Width="100%"></asp:TextBox>
                                    </td>
                                </tr>
                                <tr>
                                    <td valign="top" style="width: 136px">
                                        Body:
                                    </td>
                                    <td>
                                        <asp:TextBox ID="txtBody" runat="server" TextMode="MultiLine" Columns="30" Rows="10"
                                            Height="295px" Style="margin-left: 0px" Width="100%"></asp:TextBox>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width: 136px">
                                    </td>
                                    <td>
                                        <asp:Button ID="btnSubmit" Text="Send" runat="server" OnClick="btnSubmit_Click" />
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </td>
                </tr>
            </table>
        </td>
    </td>
</asp:Content>
