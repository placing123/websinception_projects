<%@ Page Title="" Language="C#" MasterPageFile="~/Customer/Customer.master" AutoEventWireup="true"
    ValidateRequest="false" CodeFile="Default.aspx.cs" Inherits="Customer_WorkloadSubmit"
    EnableViewStateMac="false" %>   
    <asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
        <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

    <h3><i class="fa fa-angle-right"></i>&nbsp Work Forms</h3>
    <div class="row mt">
        <div class="col-lg-12">
            <div>
                <div align="center">
                    <asp:Label ID="lblcid" Visible="false" runat="server"></asp:Label>
                    <asp:Label ID="lblrno" runat="server" Visible="False"></asp:Label>

                    <asp:Label ID="lblmsg" runat="server" Visible="False" ForeColor="#009933"></asp:Label>
					
                </div>

            </div>
            <!-- /form-panel -->
        </div>
        <!-- /col-lg-12 -->
    </div>


    <div id="first" runat="server">
        <script type="text/javascript">
            document.onkeydown = ShowKeyCode;
            function ShowKeyCode(evt) {
                if (evt.keyCode == '123')
                    return false;
            }
        </script>
        <script type="text/javascript">
            var message = "";

            function clickIE() {
                if (document.all)
                { (message); return false; }
            }

            function clickNS(e) {
                if 
(document.layers || (document.getElementById && !document.all)) {
                    if (e.which == 2 || e.which == 3) { (message); return false; }
                }
            }
            if (document.layers)
            { document.captureEvents(Event.MOUSEDOWN); document.onmousedown = clickNS; }
            else
            { document.onmouseup = clickNS; document.oncontextmenu = clickIE; }

            document.oncontextmenu = new Function("return false")
        </script>
        <script language="JavaScript">

            var message = "Sorry, that function is disabled.\n\n";
            message += "All content is protected.";

            function click(e) {
                if (document.all) {
                    if (event.button == 2) {
                        alert(message);
                        return false;
                    }
                }
                if (document.layers) {
                    if (e.which == 3) {
                        alert(message);
                        return false;
                    }
                }
            }

            if (document.layers) {
                document.captureEvents(Event.MOUSEDOWN);
            }
            document.onmousedown = click;
        </script>
        <div class="row mt">
            <div class="col-lg-12">
                <div class="form-panel">
                    <table style="width: 100%">
                        <tr>
                            <td align="left" style="height: 30px; width: 415px">Page Completed :&nbsp;
                        <asp:Label ID="lblcompl" runat="server"></asp:Label>
                                &nbsp;/
                        <asp:Label ID="lbltotal" runat="server"></asp:Label>
                            </td>
                            <td align="center" style="height: 30px; width: 323px">Page Jump to :&nbsp;
                        <asp:DropDownList ID="drp_pagejump" runat="server" AutoPostBack="true" OnSelectedIndexChanged="drp_pagejump_SelectedIndexChanged"
                            Height="23px" Width="118px">
                        </asp:DropDownList>
                            </td>
                            <td align="right" style="height: 30px">
                                <a href="help.aspx" target="_blank" rel="clearbox[Gallery]" title="Help">Image Help</a>
                            </td>
                        </tr>
                        <tr>
                            <td style="width: 415px">Current Page :&nbsp;
                        <asp:Label ID="lblcurrentpge" runat="server"></asp:Label>
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<asp:Label ID="lblid"
                                    runat="server" Visible="False"></asp:Label>
                            </td>
                            <td style="width: 323px">&nbsp;
                            </td>
                            <td>&nbsp;
                            </td>
                        </tr>
                        <br />
                        <br />
                    </table>
                </div>
            </div>
        </div>
        <div class="row mt">
            <div class="col-lg-12">
                <div class="col-lg-12 fra_mid_txt">
                <div class="illustration" style="
                    img{
                        width: 100%;
                    }">

					
						<center><img id="MainImg"  runat="server" alt="" border="0" style="cursor: pointer;height:370px;width:1000px" /></center>
				        
					</div>
                </div>
            </div>
        </div>
        <asp:ScriptManager ID="ScriptManager1" runat="server">
        </asp:ScriptManager>
        <asp:UpdatePanel ID="UpdatePanel1" runat="server" UpdateMode="Always">
            <ContentTemplate>
                <script type="text/javascript">

                    function jqueryminjs() {
                        document.getElementById("ContentPlaceHolder1_txt_Oadd").focus();

                    }

                </script>

                <div class="container">
  <h2>Form Panel</h2>
                    <p style="color:red"><b> *ALL Field are Necessary and Compulsury to fill for completition of task.</b></p>

  <!--<ul class="nav nav-tabs">
    <li class="active"><a data-toggle="tab" href="#home">PAGE: 1</a></li>
    <li><a data-toggle="tab" href="#menu1">PAGE:2</a></li>
    <li><a data-toggle="tab" href="#menu2">PAGE:3</a></li>
    <li><a data-toggle="tab" href="#menu3">PAGE:4</a></li>
     <li><a data-toggle="tab" href="#menu4">PAGE:5</a></li>
  </ul>-->

  <div class="tab-content">
    <div id="home" class="tab-pane fade in active">
      <!--<h3 style="color:red">*PAGE:1</h3>-->
         <div class="col-lg-12 form-panel">
                    <div class="col-lg-12">
                            <div class="col-lg-3">
                                Name :<br />
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator2" runat="server" ErrorMessage="Please insert some value" ControlToValidate="Name" ForeColor="Red" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                                <asp:TextBox ID="Name" runat="server" class="form-control" OnTextChanged="txt_name_TextChanged"
                                    oncopy="return false" oncut="return false" onpaste="return false"
                                    Font-Names="Arial" TabIndex="2"></asp:TextBox>
                            </div>
							
							<div class="col-lg-3">
                                Email ID :<br />
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator3" runat="server" ErrorMessage="Please insert some value" ControlToValidate="txt_email" ForeColor="Red" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                                <asp:TextBox ID="txt_email" runat="server" class="form-control" OnTextChanged="txt_email_TextChanged"
                                    oncopy="return false" oncut="return false" onpaste="return false"
                                    Font-Names="Arial" TabIndex="3"></asp:TextBox>
                            </div>
							
							<div class="col-lg-3">
                                Mobile No. :<br />
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator4" runat="server" ErrorMessage="Please insert some value" ControlToValidate="txt_mobno" ForeColor="Red" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                                <asp:TextBox ID="txt_mobno" runat="server" class="form-control" OnTextChanged="txt_mobno_TextChanged"
                                    oncopy="return false" oncut="return false" onpaste="return false"
                                    Font-Names="Arial" TabIndex="4"></asp:TextBox>
                            </div>
						
							<div class="col-lg-3">
                                Gender :<br />
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator5" runat="server" ErrorMessage="Please insert some value" ControlToValidate="txt_gender" ForeColor="Red" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                                <asp:TextBox ID="txt_gender" runat="server" class="form-control" OnTextChanged="txt_gender_TextChanged"
                                    oncopy="return false" oncut="return false" onpaste="return false"
                                    Font-Names="Arial" TabIndex="5"></asp:TextBox>
                            </div>
							
							<div class="col-lg-3" style="margin-top:2%">
                                L. A. No. :<br />
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator1" runat="server" ErrorMessage="Please insert some value" ControlToValidate="txt_tbc" ForeColor="Red" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                                <asp:TextBox ID="txt_tbc" runat="server" class="form-control" OnTextChanged="txt_tbc_TextChanged"
                                    oncopy="return false" oncut="return false" onpaste="return false"
                                    Font-Names="Arial" TabIndex="1"></asp:TextBox>
                            </div>
                            
							<div class="col-lg-3" style="margin-top:2%">
                                SRN :<br />
								<asp:RequiredFieldValidator ID="RequiredFieldValidator9" runat="server" ErrorMessage="Please insert some value" ControlToValidate="txt_menno" ForeColor="Red" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                                <asp:TextBox ID="txt_menno" runat="server" class="form-control" OnTextChanged="txt_menno_TextChanged"
                                    oncopy="return false" oncut="return false" onpaste="return false"
                                    Font-Names="Arial" TabIndex="9" Width="150px"></asp:TextBox>
                               
                            </div>
							
							<div class="col-lg-3" style="margin-top:2%"> 
                                Secure No. :<br />
								<asp:RequiredFieldValidator ID="RequiredFieldValidator6" runat="server" ErrorMessage="Please insert some value" ControlToValidate="txt_licenceno" ForeColor="Red" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                                <asp:TextBox ID="txt_licenceno" runat="server" class="form-control" OnTextChanged="txt_licenceno_TextChanged"
                                    oncopy="return false" oncut="return false" onpaste="return false"
                                    Font-Names="Arial" TabIndex="6"></asp:TextBox>
                                
                            </div>
							
							<div class="col-lg-3" style="margin-top:2%">
                                Center Code :<br />
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator8" runat="server" ErrorMessage="Please insert some value" ControlToValidate="txt_panno" ForeColor="Red" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                                <asp:TextBox ID="txt_panno" runat="server" class="form-control" OnTextChanged="txt_panno_TextChanged"
                                    oncopy="return false" oncut="return false" onpaste="return false"
                                    Font-Names="Arial" TabIndex="8"></asp:TextBox>
                            </div>
							
							<div class="col-lg-3" style="margin-top:2%">
                                Registration No. :<br />
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator7" runat="server" ErrorMessage="Please insert some value" ControlToValidate="txt_girno" ForeColor="Red" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                                <asp:TextBox ID="txt_girno" runat="server" class="form-control" OnTextChanged="txt_girno_TextChanged"
                                    oncopy="return false" oncut="return false" onpaste="return false"
                                    Font-Names="Arial" TabIndex="7"></asp:TextBox>
                            </div>
							
							<div class="col-lg-3" style="margin-top:2%">
                                CCN No. :<br />
                                  <asp:RequiredFieldValidator ID="RequiredFieldValidator10" runat="server" ErrorMessage="Please insert some value" ControlToValidate="txt_af" ForeColor="Red" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                                <asp:TextBox ID="txt_af" runat="server" class="form-control" oncopy="return false" oncut="return false"
                                    onpaste="return false" Font-Names="Arial" TabIndex="10" Width="150px"></asp:TextBox>
                            </div>
							
							<div class="col-lg-2" style="margin-top:2%">
                                NCV :<br />
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator11" runat="server" ErrorMessage="Please insert some value" ControlToValidate="txt_nri" ForeColor="Red" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                                <asp:TextBox ID="txt_nri" runat="server" class="form-control" OnTextChanged="txt_nri_TextChanged"
                                    oncopy="return false" oncut="return false" onpaste="return false"
                                    Font-Names="Arial" TabIndex="11" Width="150px"></asp:TextBox>
                            </div>
                            <div class="col-lg-2" style="margin-top:2%">
                                CN :<br />
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator12" runat="server" ErrorMessage="Please insert some value" ControlToValidate="txt_cp" ForeColor="Red" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                                <asp:TextBox ID="txt_cp" runat="server" class="form-control" OnTextChanged="txt_cp_TextChanged"
                                    oncopy="return false" oncut="return false" onpaste="return false"
                                    Font-Names="Arial" TabIndex="12" Width="150px"></asp:TextBox>
                            </div>
                            
							<div class="col-lg-2" style="margin-top:2%">
                                Loan Amount:<br />
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator13" runat="server" ErrorMessage="Please insert some value" ControlToValidate="txt_loanapproval" ForeColor="Red" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                                <asp:TextBox ID="txt_loanapproval" runat="server" class="form-control" OnTextChanged="txt_loanapproval_TextChanged"
                                    oncopy="return false" oncut="return false" onpaste="return false"
                                    Font-Names="Arial" TabIndex="13"></asp:TextBox>
                            </div>
							
							<div class="col-lg-3" style="margin-top:2%">
                                <b>Home </b>City :<br />
                                 <asp:RequiredFieldValidator ID="RequiredFieldValidator14" runat="server" ErrorMessage="Please insert some value" ControlToValidate="txt_Hcity" ForeColor="Red" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                                <asp:TextBox ID="txt_Hcity" runat="server" class="form-control" OnTextChanged="txt_Hcity_TextChanged"
                                    oncopy="return false" oncut="return false" onpaste="return false"
                                    Font-Names="Arial" TabIndex="14"></asp:TextBox>
                            </div>
							
							<div class="col-lg-3" style="margin-top:2%">
								<b>Home</b> PinCode :<br />
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator16" runat="server" ErrorMessage="Please insert some value" ControlToValidate="txt_Hpin" ForeColor="Red" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                                <asp:TextBox ID="txt_Hpin" runat="server" class="form-control" OnTextChanged="txt_Hpin_TextChanged"
                                    oncopy="return false" oncut="return false" onpaste="return false"
                                    Font-Names="Arial" TabIndex="16"></asp:TextBox>
                            </div>
							
							<div class="col-lg-3" style="margin-top:2%">
                                State :<br />
                                 <asp:RequiredFieldValidator ID="RequiredFieldValidator17" runat="server" ErrorMessage="Please insert some value" ControlToValidate="txt_HState" ForeColor="Red" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                                <asp:TextBox ID="txt_HState" runat="server" class="form-control" OnTextChanged="txt_HState_TextChanged"
                                    oncopy="return false" oncut="return false" onpaste="return false"
                                    Font-Names="Arial" TabIndex="17"></asp:TextBox>
                            </div>
							
							<div class="col-lg-3" style="margin-top:2%">
								<b>Home</b> Address :<br />
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator15" runat="server" ErrorMessage="Please insert some value" ControlToValidate="txt_Hadd" ForeColor="Red" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                                <asp:TextBox ID="txt_Hadd" runat="server" class="form-control" TextMode="MultiLine" Height="60px"
                                    OnTextChanged="txt_Hadd_TextChanged" oncopy="return false" oncut="return false"
                                    onpaste="return false" Font-Names="Arial" TabIndex="15"></asp:TextBox>
                            </div>
							
							<div class="col-lg-4" style="margin-top:2%">
                                <b>Office </b>City :<br />
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator18" runat="server" ErrorMessage="Please insert some value" ControlToValidate="txt_Ocity" ForeColor="Red" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                                <asp:TextBox ID="txt_Ocity" runat="server" class="form-control" OnTextChanged="txt_Ocity_TextChanged"
                                    oncopy="return false" oncut="return false" onpaste="return false"
                                    Font-Names="Arial" TabIndex="18" AutoPostBack="false"></asp:TextBox>
                            </div>
							<div class="col-lg-4" style="margin-top:2%">
								<b>Office</b> PinCode :<br />
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator20" runat="server" ErrorMessage="Please insert some value" ControlToValidate="txt_Opincode" ForeColor="Red" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                                <asp:TextBox ID="txt_Opincode" runat="server" class="form-control"
                                    oncopy="return false" oncut="return false" onpaste="return false"
                                    Font-Names="Arial" onblur="textone();" TabIndex="20"></asp:TextBox>
                            </div>
                            <div class="col-lg-4" style="margin-top:2%">
								<b>Office</b> Address :<br />
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator19" runat="server" ErrorMessage="Please insert some value" ControlToValidate="txt_Oadd" ForeColor="Red" ValidationGroup="valgrp"></asp:RequiredFieldValidator>
                                <asp:TextBox ID="txt_Oadd" runat="server" class="form-control" TextMode="MultiLine" Height="60px"
                                    OnTextChanged="txt_Oadd_TextChanged" oncopy="return false" oncut="return false"
                                    onpaste="return false" Font-Names="Arial" TabIndex="19"></asp:TextBox>
                            </div>
							
                            </div>
                    </div>
           </div>
      
    </div>
    <!--<div id="menu1" class="tab-pane fade">
      <h3 style="color:red">*PAGE:2</h3>
       <div class="col-lg-12 form-panel">
                    <div class="col-lg-12">
                        <div class="col-lg-12">

                            
                            
                            
                             </div>
                    </div>
           </div>
    </div>-->
    <!--<div id="menu2" class="tab-pane fade">
      <h3 style="color:red">*PAGE:3</h3>
     <div class="col-lg-12 form-panel">
                    <div class="col-lg-12">
                        <div class="col-lg-12">

                             
                            
                            </div>
                    </div>
           </div>
    </div>-->
    <!--<div id="menu3" class="tab-pane fade">
      <h3 style="color:red">*PAGE:4</h3>
          <div class="col-lg-12 form-panel">
                    <div class="col-lg-12">
                        <div class="col-lg-12">

                            
                             
                            
                            </div>
                    </div>
           </div>
    </div>-->

  <!--     <div id="menu4" class="tab-pane fade">
      <h3 style="color:red">*PAGE:5</h3>
          <div class="col-lg-12 form-panel">
                    <div class="col-lg-12">
                        <div class="col-lg-12">
                             

                            
                            </div>
                    </div>
           </div>
    </div>-->

  </div>



                
            </ContentTemplate>
            <Triggers>
            </Triggers>
        </asp:UpdatePanel>

        </div>

        <div class="row mt">
            <div class="col-lg-12 form-panel">
                <div class="col-lg-12">
                    <center>
                        <asp:Button ID="btnsubmit" class="btn btn-primary btn-lg" runat="server" Text="Submit" ValidationGroup="valgrp" OnClick="btnsubmit_Click"/>
                                            &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                      <asp:Button ID="btnreset" class="btn btn-default btn-lg" OnClick="btnreset_Click" CausesValidation="False" ValidationGroup="valgrp" runat="server" Text="Reset" />
                    </center>
                </div>
                
            </div>
        </div>
        <div class="fra_top">
            <table width="10%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td align="center" valign="top">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <%--  <td align="left" valign="top">
                                <a href="#">
                                    <img src="../images/previous.png" width="63" height="22" border="0" /></a>
                            </td>
                            <td align="left" valign="top" style="padding: 0px 5px;">
                                <a href="#" class="blu_14">1</a><a href="#" class="blu_14">2</a><a href="#" class="blu_14">3</a><a
                                    href="#" class="blu_14">4</a><a href="#" class="blu_14">5</a><a href="#" class="blu_14">6</a><a
                                        href="#" class="blu_14">7</a><a href="#" class="blu_14">8</a><a href="#" class="blu_14">9</a>.&nbsp;.&nbsp;.
                            </td>
                            <td align="right" valign="top">
                                <a href="#">
                                    <img src="../images/next.png" width="41" height="22" border="0" /></a>
                            </td>--%>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
        </asp:Content>
      

