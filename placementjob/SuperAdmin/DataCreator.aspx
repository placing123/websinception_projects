<%@ Page Language="C#" MasterPageFile="~/SuperAdmin/AdminMaster.master" AutoEventWireup="true"
    CodeFile="DataCreator.aspx.cs" Inherits="Admin_DataCreator" Title="Data Base" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <td align="left" valign="top" class="das_min">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td align="left" valign="top" class="pro_tit">
                    Data Creator
                </td>
            </tr>
            <tr>
                <td>
                    <table style="padding:10px 0px; ">
                        <tr>
                            <td style="width: 100px; height:50px; margin 50px 250px 150px 50px;">
                                <asp:Button ID="but_AllImage" runat="server" Text=":: Recreate ALL Image ::" 
                                    onclick="but_AllImage_Click" />
                                <asp:Button ID="Button1" runat="server" onclick="Button1_Click" 
                                    style="height: 26px" Text="Delete" />
                            </td>
                        </tr>
                    </table>
                    <table>                       
                        <tr>
                            <td style="width: 17px">
                                &nbsp;
                            </td>
                            <td style="width: 62px">
                                &nbsp;Name:
                            </td>
                            <td>
                                <asp:TextBox ID="txt_name" runat="server" Height="287px" TextMode="MultiLine" Width="289px"></asp:TextBox>
                            </td>
                            <td>
                                EmailId:
                            </td>
                            <td>
                                <asp:TextBox ID="txt_emailid" runat="server" Height="283px" TextMode="MultiLine"
                                    Width="245px"></asp:TextBox>
                            </td>
                            <td>
                                Mobile No:
                            </td>
                            <td>
                                <asp:TextBox ID="txt_mobileno" runat="server" Height="283px" TextMode="MultiLine"
                                    Width="160px"></asp:TextBox>
                            </td>
                        </tr>
                        <tr>
                            <td style="width: 17px">
                                <br />
                            </td>
                        </tr>
                        <table>
                            <tr>
                                <td style="width: 18px">
                                </td>
                                <td>
                                    Address:
                                </td>
                                <td>
                                    <asp:TextBox ID="txt_adress" runat="server" Height="306px" TextMode="MultiLine" Width="603px"></asp:TextBox>
                                </td>
                            </tr>
                        </table>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (State&nbsp; city&nbsp; pincode)&nbsp;
                </td>
            </tr>
            <table>
                <tr>
                    <td>
                        City:
                     </td>
                    <td>
                        <asp:TextBox ID="txt_SCP" runat="server" Height="265px" TextMode="MultiLine" Width="260px"></asp:TextBox>
                    </td>
                    <td>
                        Pincode:
                    </td>
                    <td>
                        <asp:TextBox ID="txt_pin" runat="server" Height="265px" TextMode="MultiLine" Width="260px"></asp:TextBox>
                    </td>
                    <td>
                        State:
                    </td>
                    <td>
                        <asp:TextBox ID="txt_state" runat="server" Height="265px" TextMode="MultiLine" Width="260px"></asp:TextBox>
                    </td>
                </tr>
                <tr>
                    <td style="width: 90px">
                        <br />
                    </td>
                </tr>
                <tr>
                    <td style="width: 90px">
                        No of Entries
                    </td>
                    <td>
                        <asp:TextBox ID="txt_entrys" runat="server"></asp:TextBox>
                    </td>
                </tr>
                <tr>
                    <td style="width: 90px">
                    </td>
                    <td style="width: 69px">
                        &nbsp;<asp:Button ID="btn_generate" runat="server" OnClick="btn_generate_Click" Text="Generate" />
                        <br />
                    </td>
                </tr>
            </table>
    </td>
</asp:Content>
