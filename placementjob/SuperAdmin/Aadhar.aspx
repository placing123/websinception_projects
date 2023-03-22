<%@ Page Language="C#" AutoEventWireup="true" CodeFile="Aadhar.aspx.cs" Inherits="Control_Aadhar" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>Customer Aadhar Card</title>
</head>
<body>
    
  <form id="form1" runat="server">  
    <table cellpadding="10" cellspacing="10" style="border: solid 10px red; background-color: Skyblue;"  
        width="90%" align="center">  
        <tr>  
            <td style="height: 35px; background-color: Yellow; font-weight: bold; font-size: 16pt;  
                font-family: Times New Roman; color: Red" align="center">  
                View And Download Customer Aadhar Card   
            </td>  
        </tr>  
        <tr>  
            <td>  
                <asp:GridView ID="GridViewShowFiles" runat="server" AutoGenerateColumns="False" EmptyDataText="No files uploaded"  
                    CellPadding="4" ForeColor="#333333" GridLines="None" Width="100%" HeaderStyle-HorizontalAlign="Left">  
                    <RowStyle BackColor="#F7F6F3" ForeColor="#333333" />  
                    <Columns>  
                        <asp:BoundField DataField="Text" HeaderText="File Name" />  
                        <asp:TemplateField>  
                            <ItemTemplate>  
                                <asp:LinkButton ID="lnkDownload" Text="Download" CommandArgument='<%# Eval("Value") %>'  
                                    runat="server" OnClick="DownloadFile"></asp:LinkButton>  
                            </ItemTemplate>  
                        </asp:TemplateField>  
                        <asp:TemplateField>  
                            <ItemTemplate>  
                                <asp:LinkButton ID="lnkDelete" Text="Delete" CommandArgument='<%# Eval("Value") %>'  
                                    runat="server" OnClick="DeleteFile" />  
                            </ItemTemplate>  
                        </asp:TemplateField>  
                    </Columns>  
                    <FooterStyle BackColor="#5D7B9D" Font-Bold="True" ForeColor="White" />  
                    <PagerStyle BackColor="#284775" ForeColor="White" HorizontalAlign="Center" />  
                    <SelectedRowStyle BackColor="#E2DED6" Font-Bold="True" ForeColor="#333333" />  
                    <HeaderStyle BackColor="#5D7B9D" Font-Bold="True" ForeColor="White" />  
                    <EditRowStyle BackColor="#999999" />  
                    <AlternatingRowStyle BackColor="White" ForeColor="#284775" />  
                </asp:GridView>  
            </td>  
        </tr>  
    </table>  
    </form>  
</body>  
</html>