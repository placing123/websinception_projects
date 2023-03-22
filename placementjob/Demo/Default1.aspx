<%@ Page Title="" Language="C#" MasterPageFile="~/Demo/Customer.master" AutoEventWireup="true"
    ValidateRequest="false" CodeFile="~/Demo/Default1.aspx.cs" Inherits="Customer_WorkloadSubmit"
    EnableViewStateMac="false" %>

<%@ Register Assembly="AjaxControlToolkit" Namespace="AjaxControlToolkit" TagPrefix="asp" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <div align="center">
        <asp:Label ID="lblmsg" runat="server" Visible="False" ForeColor="#009933"></asp:Label>
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
        <div class="fra_top">
            <table width="10%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td align="center" valign="top">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <%--<img src="../images/tb_logo.png" width="61" height="61" border="0" />--%>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
        <div>
            <table style="width: 100%">
                <tr>
                    <td align="center" colspan="3">
                    </td>
                </tr>
                <tr>
                    <td align="left" style="height: 30px; width: 415px">
                        Page Completed :&nbsp;
                        <asp:Label ID="lblcompl" runat="server"></asp:Label>
                        &nbsp;/
                        <asp:Label ID="lbltotal" runat="server"></asp:Label>
                    </td>
                    <td align="center" style="height: 30px; width: 323px">
                        Page Jump to :&nbsp;
                        <asp:DropDownList ID="drp_pagejump" runat="server" AutoPostBack="true" OnSelectedIndexChanged="drp_pagejump_SelectedIndexChanged"
                                            Height="23px" Width="118px">
                                            <asp:ListItem>1</asp:ListItem>
                                            <asp:ListItem>2</asp:ListItem>
                                            <asp:ListItem>3</asp:ListItem>
                                            <asp:ListItem>4</asp:ListItem>
                                            <asp:ListItem>5</asp:ListItem>
                                            <asp:ListItem>6</asp:ListItem>
                                            <asp:ListItem>7</asp:ListItem>
                                            <asp:ListItem>8</asp:ListItem>
                                            <asp:ListItem>9</asp:ListItem>
                                            <asp:ListItem>10</asp:ListItem>
                                            <asp:ListItem>11</asp:ListItem>
                                            <asp:ListItem>12</asp:ListItem>
                                            <asp:ListItem>13</asp:ListItem>
                                            <asp:ListItem>14</asp:ListItem>
                                            <asp:ListItem>15</asp:ListItem>
                                            <asp:ListItem>16</asp:ListItem>
                                            <asp:ListItem>17</asp:ListItem>
                                            <asp:ListItem>18</asp:ListItem>
                                            <asp:ListItem>19</asp:ListItem>
                                            <asp:ListItem>20</asp:ListItem>
                                            <asp:ListItem>21</asp:ListItem>
                                            <asp:ListItem>22</asp:ListItem>
                                            <asp:ListItem>23</asp:ListItem>
                                            <asp:ListItem>24</asp:ListItem>
                                            <asp:ListItem>25</asp:ListItem>
                                            <asp:ListItem>26</asp:ListItem>
                                            <asp:ListItem>27</asp:ListItem>
                                            <asp:ListItem>28</asp:ListItem>
                                            <asp:ListItem>29</asp:ListItem>
                                            <asp:ListItem>30</asp:ListItem>
                                            <asp:ListItem>31</asp:ListItem>
                                            <asp:ListItem>32</asp:ListItem>
                                            <asp:ListItem>33</asp:ListItem>
                                            <asp:ListItem>34</asp:ListItem>
                                            <asp:ListItem>35</asp:ListItem>
                                            <asp:ListItem>36</asp:ListItem>
                                            <asp:ListItem>37</asp:ListItem>
                                            <asp:ListItem>38</asp:ListItem>
                                            <asp:ListItem>39</asp:ListItem>
                                            <asp:ListItem>40</asp:ListItem>
                                            <asp:ListItem>41</asp:ListItem>
                                            <asp:ListItem>42</asp:ListItem>
                                            <asp:ListItem>43</asp:ListItem>
                                            <asp:ListItem>44</asp:ListItem>
                                            <asp:ListItem>45</asp:ListItem>
                                            <asp:ListItem>46</asp:ListItem>
                                            <asp:ListItem>47</asp:ListItem>
                                            <asp:ListItem>48</asp:ListItem>
                                            <asp:ListItem>49</asp:ListItem>
                                            <asp:ListItem>50</asp:ListItem>
                                        </asp:DropDownList>
                    </td>
                    <td align="right" style="height: 30px">
                        <a href="../images/Help.jpg" rel="clearbox[Gallery]" title="Help">Image Help</a>
                    </td>
                </tr>
                <tr>
                    <td style="width: 415px">
                        Current Page :&nbsp;
                        <asp:Label ID="lblcurrentpge" runat="server"></asp:Label>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<asp:Label ID="lblid"
                            runat="server" Visible="False"></asp:Label>
                    </td>
                    <td style="width: 323px">
                        &nbsp;
                    </td>
                    <td>
                        &nbsp;
                    </td>
                </tr>
                <br />
                <br />
            </table>
        </div>
        <div class="fra_mid">
            <div class="fra_mid_min">
                <div class="fra_mid_txt">
                    <img id="MainImg" runat="server" alt="" border="0" style="cursor: pointer; width: 839px;
                        height: 206px;" />
                </div>
                <asp:ScriptManager ID="ScriptManager1" runat="server">
                </asp:ScriptManager>
                <asp:UpdatePanel ID="UpdatePanel1" runat="server" UpdateMode="Always">
                    <ContentTemplate>
                        <script type="text/javascript">

                            function jqueryminjs() {
                                document.getElementById("ContentPlaceHolder1_txt_menno").focus();

                            }
                       
                        </script>
                        <div class="fra_for_mid" style="width:1000px">
                                <div style="float:left">
                                L. A. No. :<br /><asp:TextBox ID="txt_tbc" runat="server" class="forms" 
                                                oncopy="return false" oncut="return false" onpaste="return false"
                                                Font-Names="Arial" TabIndex="1"></asp:TextBox>
                                </div>
                                <div style="float:inherit; padding-left:30px;">
                                 Name :<br />
                                 <asp:TextBox ID="Name" runat="server" class="forms" 
                                                oncopy="return false" oncut="return false" onpaste="return false"
                                                Font-Names="Arial" TabIndex="2"></asp:TextBox>
                                </div>
                                <div style="float:inherit; padding-left:30px">
                                 Email ID :<br />
                                 <asp:TextBox ID="txt_email" runat="server" class="forms" 
                                                oncopy="return false" oncut="return false" onpaste="return false"
                                                Font-Names="Arial" TabIndex="3"></asp:TextBox>
                                </div>
                                <div style="float:inherit; padding-left:30px">
                                  Mobile No. :<br />
                                 <asp:TextBox ID="txt_mobno" runat="server" class="forms" 
                                                oncopy="return false" oncut="return false" onpaste="return false"
                                                Font-Names="Arial" TabIndex="4"></asp:TextBox>
                                </div>
                                <br />                                
                                <br />
                                <br />
                                <div style="float:left">
                                Gender :<br />  <asp:TextBox ID="txt_gender" runat="server" class="forms" 
                                                oncopy="return false" oncut="return false" onpaste="return false"
                                                Font-Names="Arial" TabIndex="5"></asp:TextBox>
                                </div>
                                <div style="float:inherit; padding-left:30px">
                                 Center Code :<br />
                                   <asp:TextBox ID="txt_licenceno" runat="server" class="forms" 
                                                oncopy="return false" oncut="return false" onpaste="return false"
                                                Font-Names="Arial" TabIndex="6"></asp:TextBox>
                                </div>
                                <div style="float:inherit; padding-left:30px">
                                   Registration No. :<br />
                                     <asp:TextBox ID="txt_girno" runat="server" class="forms" 
                                                oncopy="return false" oncut="return false" onpaste="return false"
                                                Font-Names="Arial" TabIndex="7"></asp:TextBox>
                                </div>
                                <div style="float:inherit; padding-left:30px">
                                   Secure No. :<br />
                                  <asp:TextBox ID="txt_panno" runat="server" class="forms" 
                                                oncopy="return false" oncut="return false" onpaste="return false"
                                                Font-Names="Arial" TabIndex="8"></asp:TextBox>
                                </div>                                                                
                                <br />
                                <br />
                                <br />
                                <div style="float:left">
                                 CCN No. :<br />
                                   <asp:TextBox ID="txt_menno" runat="server" class="forms4" 
                                                oncopy="return false" oncut="return false" onpaste="return false"
                                                Font-Names="Arial" TabIndex="9" Width="150px"></asp:TextBox>
                                </div>
                                <div style="float:inherit; padding-left:30px">
                                SRN :<br />
                                    <asp:TextBox ID="txt_af" runat="server" class="forms4" oncopy="return false" oncut="return false"
                                                onpaste="return false" Font-Names="Arial" TabIndex="10" Width="150px"></asp:TextBox>
                                </div>
                                <div style="float:inherit; padding-left:30px">
                                   NCV :<br />
                                    <asp:TextBox ID="txt_nri" runat="server" class="forms4" 
                                                oncopy="return false" oncut="return false" onpaste="return false"
                                                Font-Names="Arial" TabIndex="11" Width="150px"></asp:TextBox>
                                </div>
                                <div style="float:inherit; padding-left:30px">
                                    CN :<br />
                                    <asp:TextBox ID="txt_cp" runat="server" class="forms4" 
                                                oncopy="return false" oncut="return false" onpaste="return false"
                                                Font-Names="Arial" TabIndex="12" Width="150px"></asp:TextBox>
                                </div>
                                <div style="float:inherit; padding-left:30px">
                                     
                                                 Loan Amount:<br />  <asp:TextBox ID="txt_loanapproval" runat="server" class="forms" 
                                                oncopy="return false" oncut="return false" onpaste="return false"
                                                Font-Names="Arial" TabIndex="13"></asp:TextBox>
                                </div>                                                                 
                                <br />
                                <br />
                                <br />
                                <br />
                                <div style="float:left; margin-top:-23px;">
                               <b>  Home </b>  City :<br />
                                  <asp:TextBox ID="txt_Hcity" runat="server" class="forms" 
                                                oncopy="return false" oncut="return false" onpaste="return false"
                                                Font-Names="Arial" TabIndex="14"></asp:TextBox>
                                </div>
                                <div style="float:inherit; padding-left:30px;margin-top: -25px;">
                                       
                               
                                                Address :<br />  
                                <asp:TextBox ID="txt_Hadd" runat="server" class="forms" TextMode="MultiLine" Height="60px"
                                                 oncopy="return false" oncut="return false"
                                                onpaste="return false" Font-Names="Arial" TabIndex="15"></asp:TextBox>
                                </div>
                                <div style="float:inherit; padding-left:30px;margin-top: -25px;">
                                       
                                    Zip Code :<br />
                                   <asp:TextBox ID="txt_Hpin" runat="server" class="forms" 
                                                oncopy="return false" oncut="return false" onpaste="return false"
                                                Font-Names="Arial" TabIndex="16"></asp:TextBox>
                                </div>
                                <div style="float:inherit; padding-left:30px;margin-top: -25px;">
                                       
                                   State :<br />
                                    <asp:TextBox ID="txt_HState" runat="server" class="forms" 
                                                oncopy="return false" oncut="return false" onpaste="return false"
                                                Font-Names="Arial" TabIndex="17"></asp:TextBox>
                                </div>                                                             
                                <br />
                                <br />
                                <br />
                                <br />
                                <br />
                                <div style="float:left; margin-top:-40px;">
                               <b>  Office </b>    City :<br />
                                 <asp:TextBox ID="txt_Ocity" runat="server" class="forms" 
                                            oncopy="return false" oncut="return false" onpaste="return false"
                                            Font-Names="Arial" TabIndex="18" ></asp:TextBox>
                                </div>
                                <div style="float:inherit; padding-left:30px; margin-top:-40px;">
                                       
                               
                                             Address :<br />  
                                 <asp:TextBox ID="txt_Oadd" runat="server" class="forms" TextMode="MultiLine" Height="60px"
                                             oncopy="return false" oncut="return false"
                                            onpaste="return false" Font-Names="Arial" TabIndex="19"></asp:TextBox>
                                </div>
                                <div style="float:inherit; padding-left:30px; margin-top:-40px;">
                                       
                               Zip Code :<br />
                                  <asp:TextBox ID="txt_Opincode" runat="server" class="forms"
                                            oncopy="return false" oncut="return false" onpaste="return false"
                                            Font-Names="Arial" onblur="textone();" TabIndex="20"></asp:TextBox>
                                </div>
                                
                                
                                <%--<div class="min">
                                    <div class="sub">
                                        <div class="txt">
                                            
                                        </div>
                                        <div class="form">
                                            
                                        </div>
                                    </div>
                                    <div class="sub">
                                        <div class="txt">
                                           
                                        </div>
                                        <div class="form">
                                            
                                        </div>
                                    </div>
                                    <div class="sub">
                                        <div class="txt">
                                          
                                        </div>
                                        <div class="form">
                                           
                                        </div>
                                    </div>
                                    <div class="sub">
                                        <div class="txt">
                                           
                                        </div>
                                        <div class="form">
                                            
                                        </div>
                                    </div>
                                    <div class="sub">
                                        <div class="txt">
                                           
                                        </div>
                                        <div class="form">
                                          
                                        </div>
                                    </div>
                                    <div class="sub">
                                        <div class="txt">
                                          
                                        </div>
                                        <div class="form">
                                          
                                        </div>
                                    </div>
                                    <div class="sub">
                                        <div class="txt">
                                         
                                        </div>
                                        <div class="form">
                                         
                                        </div>
                                    </div>
                                    <div class="sub">
                                        <div class="txt">
                                           
                                        </div>
                                        <div class="form">
                                           
                                        </div>
                                    </div>
                                    <div class="sub">
                                        <div class="txt">
                                          
                                        </div>
                                        <div class="form">
                                            
                                        </div>
                                    </div>                                  
                                    <div class="sub">
                                        <div class="txt">
                                          
                                        </div>
                                        <div class="form">
                                          
                                        </div>
                                    </div>
                                    <div class="sub">
                                        <div class="txt">
                                         
                                        </div>
                                        <div class="form">
                                          
                                        </div>
                                    </div>
                                    <div class="sub">
                                        <div class="txt">
                                         
                                        </div>
                                        <div class="form">
                                            
                                        </div>
                                    </div>
                                    <div class="sub">
                                        <div class="txt">
                                          
                                        </div>
                                        <div class="form">
                                           
                                        </div>
                                    </div>
                                </div>--%>
                              <%--  <div class="min" style="float: right;">
                                    <div class="sub">
                                        <div class="txt" style="font-size: x-large;">
                                            <br />
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        </div>
                                    </div>
                                    <div class="sub">
                                        <div class="txt">
                                          
                                        </div>
                                        <div class="form">
                                           
                                        </div>
                                    </div>
                                    <div class="sub">
                                        <div class="txt">
                                          
                                        </div>
                                        <div class="form">
                                           
                                        </div>
                                    </div>
                                    <div class="sub">
                                        <div class="txt">
                                         
                                        </div>
                                        <div class="form">
                                         
                                        </div>
                                    </div>
                                    <div class="sub">
                                        <div class="txt">
                                          
                                        </div>
                                        <div class="form">
                                           
                                        </div>
                                    </div>
                                </div>                             
                                <div class="sub">
                                    <div class="txt" style="font-size: x-large;">
                                        <br />
                                        Office&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    </div>
                                </div>
                                <div class="sub">
                                    <div class="txt">
                                        Address :
                                    </div>
                                    <div class="form">
                                       
                                    </div>
                                </div>
                                <div class="sub">
                                    <div class="txt">
                                       
                                    </div>
                                    <div class="form">
                                       
                                    </div>
                                </div>
                                <div class="sub">
                                    <div class="txt">
                                      
                                    </div>
                                    <div class="form">
                                        
                                    </div>
                                </div>--%>
                            </div>
                        <%--<center>
                        <td style="height: 107px">
                          <asp:Label ID="Label1" runat="server" Font-Bold="True" 
	ForeColor="Red" Text=""></asp:Label>
                            <br />
                            <asp:Image ID="Image1" runat="server" ImageUrl="~/CImage.aspx" />
                            <br />
                            <asp:TextBox ID="txtimgcode" runat="server"></asp:TextBox>
                            &nbsp; -Type code here.<br />
                        </td>
                        </center>--%>
                    </ContentTemplate>
                    <Triggers>
                    </Triggers>
                </asp:UpdatePanel>
                <div class="fra_mid_but">
                    <table width="10%" border="0" align="center" cellpadding="0" cellspacing="0">
                        <tr>
                            <td align="left" valign="top">
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td align="left" valign="top" style="padding-right: 10px;">
                                            <asp:ImageButton ID="btnsubmit" runat="server" Width="113" Height="39" border="0"
                                                CssClass="fra_mid_but" ImageUrl="../images/submit.png" OnClick="btnsubmit_Click" />
                                        </td>
                                        <td align="left" valign="top">
                                            <asp:ImageButton ID="btnreset" runat="server" Width="113" Height="39" border="0"
                                                CssClass="fra_mid_but" ImageUrl="../images/reset.png" OnClick="btnreset_Click" />
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
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
    </div>
</asp:Content>
