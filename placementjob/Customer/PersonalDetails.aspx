<%@ Page Title="" Language="C#" MasterPageFile="~/Customer/Customer.master" AutoEventWireup="true"
    CodeFile="PersonalDetails.aspx.cs" Inherits="Franchisee_PersonalDetails" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
     <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

    <h3><i class="fa fa-angle-right"></i>&nbsp Profile Details</h3>
    <div class="row mt">
        <div class="col-lg-12">
            <div class="form-panel">
                <table border="1" frame="border" width="100%" style="color: #4b4b4b" cellspacing="8px"
                    width="100%">
                    <tr>
                        <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px" colspan="2">
                            <div class="mid_lef">
                                <div class="mid_lef_tit">
                                    <span style="color: #16C0A7;">Personal & Banking Details</span>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">Customer Id
                        </td>
                        <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                            <asp:Label ID="lbl_cid" runat="server"></asp:Label>
                        </td>
                    </tr>
                    <tr>
                        <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">Customer Name
                        </td>
                        <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                            <asp:Label ID="lbl_cname" runat="server"></asp:Label>
                        </td>
                    </tr>
                    <tr>
                        <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">Address</td>
                        <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                            <asp:Label ID="lbl_address" runat="server"></asp:Label>
                        </td>
                    </tr>
                    <tr>
                        <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">Mobile No.</td>
                        <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                            <asp:Label ID="lbl_mob" runat="server"></asp:Label>
                        </td>
                    </tr>
                    <tr>
                        <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">Email</td>
                        <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                            <asp:Label ID="lbl_email" runat="server"></asp:Label>
                        </td>
                    </tr>
                    <tr>
                        <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">pageno</td>
                        <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                            <asp:Label ID="lbl_pageno" runat="server"></asp:Label>
                        </td>
                    </tr>
                    <tr>
                        <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">A/C Holder Name</td>
                        <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                            <asp:TextBox ID="txt_acname" runat="server"></asp:TextBox>
                        </td>
                    </tr>
                    <tr>
                        <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">A/C Number</td>
                        <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                            <asp:TextBox ID="txt_acnumber" runat="server"></asp:TextBox>
                        </td>
                    </tr>
                    <tr>
                        <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">Bank IFSC Code</td>
                        <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                            <asp:TextBox ID="txt_ifsccode" runat="server"></asp:TextBox>
                        </td>
                    </tr>
                    <tr>
                        <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px"></td>
                        <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                            <asp:Button ID="btn_update" runat="server" Text="Update" OnClick="btn_update_Click" />
                        </td>
                    </tr>

                     <tr>
                        <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px"></td>
                        <td style="border-style: dashed; border-width: thin; padding: 5px; margin: 4px">
                            <asp:Button ID="btn_download" runat="server" Text="Download Sign Agreement" OnClick="btn_download_Click" />
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>

</asp:Content>
