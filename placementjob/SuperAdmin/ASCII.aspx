<%@ Page Language="C#" AutoEventWireup="true" CodeFile="ASCII.aspx.cs" Inherits="Customer_ASCII" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>Report with ASCII Value</title>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <table>
            <thead>
                <tr>
                    <th>
                        Field Name
                    </th>
                    <th>
                        Normal Value
                    </th>
                    <th>
                        ASCII Value
                    </th>
                </tr>
            </thead>
            <tr>
                <td>
                  L. A. No.
                </td>
                <td>
                    <asp:Label ID="Tbc_No" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="Tbc_No_Ascii" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <asp:Label ID="Tbc_No_" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="Tbc_No_Ascii_" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                    Name:
                </td>
                <td>
                    <asp:Label ID="Name" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="Name_ASCII" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <asp:Label ID="Name_" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="Name_ASCII_" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                    Mobile No. :
                </td>
                <td>
                    <asp:Label ID="Mobile_No" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="Mobile_No_ASCII" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <asp:Label ID="Mobile_No_" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="Mobile_No_ASCII_" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                    Gender :
                </td>
                <td>
                    <asp:Label ID="Gender" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="Gender_ASCII" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <asp:Label ID="Gender_" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="Gender_ASCII_" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                    Center Code :
                </td>
                <td>
                    <asp:Label ID="License_No" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="License_No_ASCII" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <asp:Label ID="License_No_" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="License_No_ASCII_" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                    Home Address :
                </td>
                <td>
                    <asp:Label ID="H_Address" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="H_Address_ASCII" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <asp:Label ID="H_Address_" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="H_Address_ASCII_" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                    Home City :
                </td>
                <td>
                    <asp:Label ID="H_City" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="H_City_ASCII" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <asp:Label ID="H_City_" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="H_City_ASCII_" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                    Home State :
                </td>
                <td>
                    <asp:Label ID="H_State" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="H_State_ASCII" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <asp:Label ID="H_State_" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="H_State_ASCII_" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                    Home Pin Code No. :
                </td>
                <td>
                    <asp:Label ID="H_Pincode" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="H_Pincode_ASCII" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <asp:Label ID="H_Pincode_" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="H_Pincode_ASCII_" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                    Email ID :
                </td>
                <td>
                    <asp:Label ID="EmailID" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="EmailID_ASCII" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <asp:Label ID="EmailID_" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="EmailID_ASCII_" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                    Registration No. :
                </td>
                <td>
                    <asp:Label ID="Gir_No" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="Gir_No_ASCII" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <asp:Label ID="Gir_No_" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="Gir_No_ASCII_" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                    Secure No. :
                </td>
                <td>
                    <asp:Label ID="Pan_No" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="Pan_No_ASCII" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <asp:Label ID="Pan_No_" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="Pan_No_ASCII_" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                    Loan Amount :
                </td>
                <td>
                    <asp:Label ID="LAL" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="LAL_ASCII" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <asp:Label ID="LAL_" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="LAL_ASCII_" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                    Office Address :
                </td>
                <td>
                    <asp:Label ID="O_Address" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="O_Address_ASCII" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <asp:Label ID="O_Address_" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="O_Address_ASCII_" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                    Office City :
                </td>
                <td>
                    <asp:Label ID="O_City" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="O_City_ASCII" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <asp:Label ID="O_City_" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="O_City_ASCII_" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                    Office Pin Code No. :
                </td>
                <td>
                    <asp:Label ID="O_Pinno" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="O_Pinno_ASCII" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <asp:Label ID="O_Pinno_" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="O_Pinno_ASCII_" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                    CCN No. :
                </td>
                <td>
                    <asp:Label ID="Mrn_No" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="Mrn_No_ASCII" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <asp:Label ID="Mrn_No_" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="Mrn_No_ASCII_" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                    SRN  :
                </td>
                <td>
                    <asp:Label ID="AF" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="AF_ASCII" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <asp:Label ID="AF_" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="AF_ASCII_" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                    NCV :
                </td>
                <td>
                    <asp:Label ID="Nri" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="Nri_ASCII" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <asp:Label ID="Nri_" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="Nri_ASCII_" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                    CN :
                </td>
                <td>
                    <asp:Label ID="CP" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="CP_ASCII" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <asp:Label ID="CP_" runat="server" Text=""></asp:Label>
                </td>
                <td>
                    <asp:Label ID="CP_ASCII_" runat="server" Text=""></asp:Label>
                </td>
            </tr>
        </table>
    </div>
    </form>
</body>
</html>
