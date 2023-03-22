<%@ Page Language="C#" AutoEventWireup="true" CodeFile="Offline_Workload_Submit.aspx.cs" Inherits="Customer_Offline_Workload_Submit" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">



<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script src="../js/jquery.reveal.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $('#button').click(function (e) { // Button which will activate our modal
            $('#modal').reveal({ // The item which will be opened with reveal
                animation: 'fade',                   // fade, fadeAndPop, none
                animationspeed: 300,                       // how fast animtions are
                closeonbackgroundclick: true,              // if you click background will modal close?
                dismissmodalclass: 'close'    // the class of a button or element that will close an open modal
            });
            return false;
        });
    });
	</script>

</head>
<body>
    <form id="form1" runat="server">
<div class="main">
  <div class="top_bg">
    <div class="margin">
      <div class="top">
        <div class="top_min">
          <div class="top_logo"><a href="#"><img src="../images/logo.png" border="0" /></a></div>
          <div class="top_txt">Submission End Date: <asp:Label ID="lbl_end_date" 
                  runat="server"></asp:Label>&nbsp; |&nbsp;<a href="Logout.aspx" class="black_11">Logout</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;                 
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="menu_bg">
    <div class="margin">
      <div class="menu">
        <ul>
          <li><a href="Offline_Client.aspx">Home</a></li>
          <%--<li><a href="PersonalDetails.aspx">Personal Details</a></li>--%>
          <li><a href="Offline_Workload_Submit.aspx">WorkLoad Submit</a></li>          
          <li><a href="#">Contact Us</a></li>
        </ul>
      </div>
    </div>
  </div>
  <div class="mid_bg">
    <div class="margin">
      &nbsp;&nbsp;&nbsp;
      <div class="mid" style="border:1px solid #353535";>
        <div class="mid_min">
           
           Upload File:&nbsp;&nbsp;
            <asp:FileUpload ID="FileUpload1" runat="server" />
            &nbsp;
            &nbsp;<br />
            <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <asp:Button ID="btn_upload" runat="server" onclick="btn_upload_Click" 
                Text="Upload" />
                    </div>
      </div>
    
    </div>
  </div>
  <div class="bot_bg">
    <div class="margin">
      <div class="bot">
          Copyright © 2014 All rights reserved.</div>
    </div>
  </div>
</div>
    </form>
</body>
</html>