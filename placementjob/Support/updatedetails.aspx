<%@ Page Language="C#" MasterPageFile="AdminMaster.master" AutoEventWireup="true" CodeFile="updatedetails.aspx.cs" Inherits="Admin_Plan" Title="Update Details" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <style type="text/css">
        .td {
            padding: 5px;
            margin: 5px;
            border: thin dashed #000000;
            width: 160px;
        }
    </style>
    <script type="text/javascript">
        function isNumberKey(evt) {

            var charCode = (evt.which) ? evt.which : evt.keyCode;
            //if (charCode > 31 && (charCode < 48 || charCode > 57) && charCode != 189)

            if ((charCode != 45 || $(element).val().indexOf('-') != -1) && (charCode != 46 || $(element).val().indexOf('.') != -1) && (charCode < 48 || charCode > 57) && charCode != 8 && charCode != 9 && charCode != 37 && charCode != 38 && charCode != 39 && charCode != 40)
                return false;
            return true;
        }
        function isKeyDisable(evt) {
            var charCode = (evt.which) ? evt.which : evt.keyCode;
            //if (charCode > 31 && (charCode < 48 || charCode > 57) && charCode != 189)

            if (charCode != 9)
                return false;
            return true;
        }
    </script>
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td align="left" valign="top" class="pro_tit">Update Details
                </td>
            </tr>
            <tr>
                <td>
                    <br />
                </td>
            </tr>
            <tr>
                <table>
                    <tr>
                        <td style="width: 29px"></td>
                        <td style="padding-right: 30px">Customer Id :
                        </td>
                        <td style="padding-right: 30px">
                            <asp:TextBox ID="txt_cid" runat="server"></asp:TextBox>
                        </td>
                        <td>
                            <asp:Button ID="btn_search" runat="server" Text="Search" Width="112px" OnClick="btn_search_Click" />
                        </td>
                    </tr>
                </table>
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
                            <td style="width: 29px"></td>
                            <td class="td">Customer Id :
                            </td>
                            <td class="td">
                                <asp:Label ID="lbl_cid" runat="server"></asp:Label>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <br />
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td class="td">Name :</td>
                            <td class="td">
                                <asp:TextBox ID="txt_name" runat="server" Width="250px"></asp:TextBox>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td>
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator1" runat="server" ErrorMessage="Enter Name" ValidationGroup="v1" ControlToValidate="txt_name" ForeColor="Red"></asp:RequiredFieldValidator>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td class="td">Address : </td>
                            <td class="td">
                                <asp:TextBox ID="txt_address" runat="server" Height="72px" Width="250px" TextMode="MultiLine"></asp:TextBox>
                            </td>
                        </tr>

                        <tr>
                            <td></td>
                            <td></td>
                            <td>
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator2" runat="server" ErrorMessage="Enter Address" ValidationGroup="v1" ControlToValidate="txt_address" ForeColor="Red"></asp:RequiredFieldValidator>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td class="td">Mobile No. :
                            </td>
                            <td class="td">
                                <asp:TextBox ID="txt_mobileno" runat="server" Width="250px"></asp:TextBox>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td>
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator4" runat="server" ErrorMessage="Enter Mobile No." ValidationGroup="v1" ControlToValidate="txt_mobileno" ForeColor="Red"></asp:RequiredFieldValidator>
                            </td>
                        </tr>

                        <tr>
                            <td></td>
                            <td class="td">Emailid :
                            </td>
                            <td class="td">
                                <asp:TextBox ID="txt_emailid" runat="server" Width="250px"></asp:TextBox>
                            </td>

                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td>
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator5" runat="server" ErrorMessage="Enter Emailid" ValidationGroup="v1" ControlToValidate="txt_emailid" ForeColor="Red"></asp:RequiredFieldValidator>
                            </td>

                        </tr>
                        <tr>
                            <td></td>
                            <td class="td">Plan ID :
                            </td>
                            <td class="td">
                                <asp:TextBox ID="txt_planid" runat="server" Width="250px"></asp:TextBox>
                            </td>

                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td>
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator7" runat="server" ErrorMessage="Enter Plan ID" ValidationGroup="v1" ControlToValidate="txt_planid" ForeColor="Red"></asp:RequiredFieldValidator>
                            </td>

                        </tr>
                        <tr>
                            <td></td>
                            <td class="td">Caller Name :
                            </td>
                            <td class="td">
                                <asp:TextBox ID="txt_callername" runat="server" Width="250px"></asp:TextBox>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td>
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator9" runat="server" ErrorMessage="Enter Caller Name" ValidationGroup="v1" ControlToValidate="txt_callername" ForeColor="Red"></asp:RequiredFieldValidator>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td class="td">Franchisee Name :
                            </td>
                            <td class="td">
                                <asp:TextBox ID="txt_franchiseename" runat="server" Width="250px"></asp:TextBox>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td>
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator11" runat="server" ErrorMessage="Enter Franchisee Name" ValidationGroup="v1" ControlToValidate="txt_franchiseename" ForeColor="Red"></asp:RequiredFieldValidator>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <br />
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td>
                                <asp:Button ID="btn_update" runat="server" Text="Update" OnClick="btn_update_Click" ValidationGroup="v1" />
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </td>
</asp:Content>
