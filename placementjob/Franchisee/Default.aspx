<%@ Page Language="C#" AutoEventWireup="true" CodeFile="Default.aspx.cs" Inherits="_Default" EnableEventValidation="false" %>

<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6 lt8"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7 lt8"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8 lt8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="UTF-8" />
    <!-- <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">  -->
    <title>Login and Registration </title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Login and Registration Form with HTML5 and CSS3" />
    <meta name="keywords" content="html5, css3, form, switch, animation, :target, pseudo-class" />
    <meta name="author" content="Codrops" />
    <link rel="shortcut icon" href="../favicon.ico">
    <script src="flash/jscripts/AC_RunActiveContent.js" type="text/javascript"></script>
    <script src="flash/jscripts/AC_ActiveX.js" type="text/javascript"></script>
    <script src="flash/jscripts/jquery142.js" type="text/javascript"></script>
    <link href="css/animate-custom.css" rel="stylesheet" />
    <link href="css/demo.css" rel="stylesheet" />
    <link href="css/style.css" rel="stylesheet" />
</head>
<body>
    <form runat="server">
    <div class="container">
        <!-- Codrops top bar -->
        			<br />
        			<br />
        			<br />
        			<br />
        			<br />
        			<br />
        			<br />
        			<br />
        			<br />
        			<br />
                <div id="container_demo" >
                    
                    <a class="hiddenanchor" id="toregister"></a>
                    <a class="hiddenanchor" id="tologin"></a>
                    <div id="wrapper">
                        <div id="login" class="animate form">
                            <form  action="mysuperscript.php" autocomplete="on"> 
                                <h1>Franchisee Log in</h1> 
                                <p> 
                                    <label for="username" class="uname" data-icon="u" > Your  username </label>
                                    
                                    <asp:TextBox ID="txt_Uname" runat="server" required="required" placeholder="Your Username"></asp:TextBox>
                                </p>
                                <p> 
                                    <label for="password" class="youpasswd" data-icon="p"> Your password </label>
                                   <asp:TextBox ID="txt_pass" runat="server" required="required" placeholder="Your Password" TextMode="Password"></asp:TextBox>
                                </p>
                                
                                <p class="login button"> 
                                    <asp:Button ID="Button4"  runat="server" Text="Log In" onclick="Button4_Click"/>
								</p>
                                
                            </form>
                        </div>

                        
						
                    </div>
                </div>  
            </section>
            <div style="color: White; width: 100%; font-size: 20px; background-color: #444444">
        <br />
        <div style="color: White; width: 100%; font-size: 20px; background-color: #444444">
       
    </div>
    </div>
    </div>
    </form>
</body>
</html>
