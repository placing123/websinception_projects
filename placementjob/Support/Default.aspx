<%@ Page Language="C#" AutoEventWireup="true" CodeFile="Default.aspx.cs" Inherits="Admin_Default" %>

<head id="Head1" runat="server">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Admin Login</title>
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
    <link href="css/style.css" rel="stylesheet" type="text/css" />
</head>
<body class="adm_bg">
    <form id="form1" runat="server">
    <table width="10%" height="97%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td valign="middle">
                <table width="500" border="0" align="left" cellpadding="0" cellspacing="0">
                    <tr>
                        <td align="left" valign="top" class="adm_main">
                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td align="left" valign="top" style="padding:20px 0 20px 80px;">
                                       <br />
                                       <br />
                                       <br />
                                       <br />

                                    </td>
                                </tr>
                                <tr>
                                    <td align="left" valign="top" class="adm_user">
                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td align="left" valign="top" style="padding-bottom: 20px;">
                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                        <tr>
                                                            <td width="112" height="40" align="right" valign="middle" class="black_16">
                                                                Username :
                                                            </td>
                                                            <td align="left" valign="top" style="padding-left: 10px;">
                                                                <asp:TextBox CssClass="forms" ID="txt_userid" runat="server" Height="34px" Width="295px"></asp:TextBox>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="left" valign="top" style="padding-bottom: 20px;">
                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                        <tr>
                                                            <td width="112" height="40" align="right" valign="middle" class="black_16">
                                                                Password :
                                                            </td>
                                                            <td align="left" valign="top" style="padding-left: 10px;">
                                                                <asp:TextBox CssClass="forms" ID="txt_password" runat="server" Height="34px" Width="295px"
                                                                    TextMode="Password"></asp:TextBox>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="left" valign="top">
                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                        <tr>
                                                            <td width="112" align="right" valign="middle" class="black_16">
                                                                &nbsp;
                                                            </td>
                                                            <td align="left" valign="top" style="padding-left: 10px;">
                                                                <asp:Button ID="b_login" runat="server" CssClass="but" OnClick="b_login_Click" Height="42px"
                                                                    Width="107px" />
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <br />
                                                    <br />
                                                    <br />
                                                    <br />
                                                    <br />
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
    </form>
</body>
</html> 