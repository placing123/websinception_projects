<%@ Page Language="C#" AutoEventWireup="true" CodeFile="agreement.aspx.cs" Inherits="agreement" ValidateRequest="false" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>

        <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>


    
    
    <script src="js1/jquery.min.js"></script>
    <script src="js1/sketch.min.js"></script>
   
    <script type="text/javascript">
        $(function () {
            $.each(['#f00', '#ff0', '#0f0'], function () {

                $('#colors_sketch').append("<a href='#colors_sketch' data-color='" + this + "' style='width: 30px;height: 30px;display:inline-block; background: " + this + ";'></a> ");
            });
            $('#colors_sketch').sketch();
            $('#colors_sketch').sketch({ defaultColor: "#ff0" });
        });
        function ConvertToImage(btn_tnc) {
            var base64 = $('#colors_sketch')[0].toDataURL();
            $("[id*=hfImageData]").val(base64);
            __doPostBack(btn_tnc.name, "");
        };
    </script>
    <script type="text/javascript">
        function RedirectAfterDelayFn() {
            var seconds = 5;
            var dvCountDown = document.getElementById("CountDown");
            var lblCount = document.getElementById("CountDownLabel");
            dvCountDown.style.display = "block";
            lblCount.innerHTML = seconds;
            setInterval(function () {
                seconds--;
                lblCount.innerHTML = seconds;
                if (seconds == 0) {
                    dvCountDown.style.display = "none";
                    window.location = "Default.aspx";
                }
            }, 1000);
        }
    </script>
</head>
<body>
    <form id="form1" runat="server">
        <div>
            <center>
            <iframe id="pdfiframe" runat="server" style="width:500px; height:500px;" frameborder="0"></iframe>
			
        </center>
		
        </div>
        <div>
            <center>
            <table>
                <tr>
                    <td>
                         <div id="CountDown" style="display: none">  
                You will be redirected after  
                <br />  
                <span id="CountDownLabel"></span> seconds.  
            </div>  <div class="tools">
                        <a href="#colors_sketch" data-tool="marker">Marker</a> <a href="#colors_sketch" data-tool="eraser">Eraser</a>
                     </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <canvas id="colors_sketch" width="800" height="300" style="border-color: black; border-width: medium; border-style: solid;"></canvas>
                        <asp:HiddenField ID="hfImageData" runat="server" />
                    </td>
                    <td>
                        <asp:Button ID="btn_tnc" runat="server" Text="Accept ALL T&C"  OnClientClick="return ConvertToImage(this)" OnClick="btn_tnc_Click" />
                    </td>
                </tr>
            </table>
        </center>
        </div>
        <div>
        </div>
    </form>
</body>
</html>
