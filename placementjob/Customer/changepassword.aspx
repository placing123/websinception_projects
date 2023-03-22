<%@ Page Language="C#" AutoEventWireup="true" CodeFile="~/Customer/changepassword.aspx.cs" Inherits="Admin_changepassword" MasterPageFile="~/Customer/Customer.master" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
     <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

    <h3><i class="fa fa-angle-right"></i>&nbsp Authentication</h3>
    <div class="col-lg-6 col-md-6 col-sm-12">
        <div class="form-panel">
            <h4 class="mb"><i class="fa fa-angle-right"></i> Change Password </h4>
            <div class="form-horizontal style-form">
                <div class="form-group">
                    <label class="col-sm-4 col-sm-4 control-label">Old Password::</label>
                    <div class="col-sm-8">
                        <asp:TextBox ID="txt_Old" runat="server" CssClass="form-control"
                            TextMode="Password"></asp:TextBox>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-4 col-sm-4 control-label">New Password::</label>
                    <div class="col-sm-8">
                        <asp:TextBox ID="txt_New" runat="server" CssClass="form-control"
                            TextMode="Password"></asp:TextBox>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-4 col-sm-4 control-label">Confirm Password::</label>
                    <div class="col-sm-8">
                        <asp:TextBox ID="txt_Confirm" CssClass="form-control" runat="server"
                            TextMode="Password"></asp:TextBox>
                    </div>
                </div>
                <div class="form-group">

                    <div class="col-sm-10">
                        <asp:Button ID="but_submit" class="btn btn-success" runat="server" Text="Submit"
                            OnClick="but_submit_Click" />
                    </div>
                </div>
            </div>



        </div>
    </div>

</asp:Content>
