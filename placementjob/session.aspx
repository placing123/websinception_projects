<%@ Page Language="C#" AutoEventWireup="true" CodeFile="session.aspx.cs" Inherits="session" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>
     <script>
         (function (i, s, o, g, r, a, m) {
             i['GoogleAnalyticsObject'] = r; i[r] = i[r] || function () {
                 (i[r].q = i[r].q || []).push(arguments)
             }, i[r].l = 1 * new Date(); a = s.createElement(o),
m = s.getElementsByTagName(o)[0]; a.async = 1; a.src = g; m.parentNode.insertBefore(a, m)
         })(window, document, 'script', '//www.google-analytics.com/analytics.js', 'ga');

         ga('create', 'UA-49728426-1', 'national-bpo.com');
         ga('send', 'pageview');

</script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
     <blink>
   <h1> <asp:Label ID="Label1" runat="server" ForeColor="Red"></asp:Label>
   </h1></blink>

    <br />
    <br />
    <asp:Button ID="Button1" runat="server"
            Text="Clear LogIn" onclick="Button1_Click" />
    </div>
    </form>
</body>
</html>
