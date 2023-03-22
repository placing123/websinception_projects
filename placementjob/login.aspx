<%@ Page Language="C#" AutoEventWireup="true" CodeFile="login.aspx.cs" Inherits="_Default" %>

<<<html lang="en">
<head>
	<title>Login</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
<!--===============================================================================================-->	
	<link rel="icon" type="image/png" href="Login/images/icons/favicon.ico"/>
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="Login/vendor/bootstrap/css/bootstrap.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="Login/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="Login/fonts/Linearicons-Free-v1.0.0/icon-font.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="Login/vendor/animate/animate.css">
<!--===============================================================================================-->	
	<link rel="stylesheet" type="text/css" href="Login/vendor/css-hamburgers/hamburgers.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="Login/vendor/animsition/css/animsition.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="Login/vendor/select2/select2.min.css">
<!--===============================================================================================-->	
	<link rel="stylesheet" type="text/css" href="Login/vendor/daterangepicker/daterangepicker.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="Login/css/util.css">
	<link rel="stylesheet" type="text/css" href="Login/css/main.css">
<!--===============================================================================================-->
</head>
<body>

    <div class="limiter">
		<div class="container-login100">
			<div class="wrap-login100 p-l-55 p-r-55 p-t-65 p-b-50">
                <form id="form1" class="form-login" runat="server">
				
					<span class="login100-form-title p-b-33">
						Account Login
					</span>
                
               <asp:TextBox ID="txt_Uname" runat="server" type="User Id" placeholder="User ID" class="form-control"></asp:TextBox>

                    <br>
                    <asp:TextBox ID="txt_pass" runat="server" type="password" placeholder="Password" class="form-control"></asp:TextBox>

                    <label class="checkbox">
                        <span class="pull-right"></span>
                    </label>
                     <div class="container-login100-form-btn m-t-20">
                    <asp:LinkButton ID="btn_submit" class="login100-form-btn" OnClick="btn_submit_Click" runat="server"><i class="fa fa-lock"></i>SIGN IN</asp:LinkButton>
                    </div>
                    <hr/>
               
               </form>
 </div>
        </div>
    </div>

    <!-- js placed at the end of the document so the pages load faster -->
    <script src="assets/js/jquery.js"></script>
    <script src="assets/js/bootstrap.min.js"></script>

    <!--BACKSTRETCH-->
    <!-- You can use an image of whatever size. This script will stretch to fit in any screen size.-->
    <script src="assets/js/jquery.backstretch.min.js" type="text/javascript"></script>
    <script>
        $.backstretch("assets/img/login-bg.jpg", { speed: 500 });
    </script>
    <div class="backstretch" style="left: 0px; top: 0px; overflow: hidden; margin: 0px; padding: 0px; height: 392px; width: 1349px; z-index: -999999; position: fixed;">
        <img style="position: absolute; margin: 0px; padding: 0px; border: medium none; width: 1349px; height: 899.503px; max-width: none; z-index: -999999; left: 0px; top: -253.752px;" src="assets/img/login-bg.jpg">
    </div>



</body>
</html>
