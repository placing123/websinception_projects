  
	<!-- jQuery 3 -->
	<script src="<?php echo base_url();?>assets/vendor_components/jquery/dist/jquery.js"></script>
	
	<!-- jQuery UI 1.11.4 -->
	<script src="<?php echo base_url();?>assets/vendor_components/jquery-ui/jquery-ui.js"></script>
	
	<!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
	<script>
	  $.widget.bridge('uibutton', $.ui.button);
	</script>
	<script type="text/javascript"> var BASE_URL = "<?php echo base_url();?>"; </script>
    <script type="text/javascript"> var SITE_URL = "<?php echo site_url(); ?>"; </script>
    
	<!-- popper -->
	<script src="<?php echo base_url();?>assets/vendor_components/popper/dist/popper.min.js"></script>
	
	<!-- Bootstrap v4.0.0-beta -->
	<script src="<?php echo base_url();?>assets/vendor_components/bootstrap/dist/js/bootstrap.js"></script>
	
	<!-- Morris.js charts -->
	<script src="<?php echo base_url();?>assets/vendor_components/raphael/raphael.js"></script>
	<script src="<?php echo base_url();?>assets/vendor_components/morris.js/morris.js"></script>
	
	<!-- Sparkline -->
	<script src="<?php echo base_url();?>assets/vendor_components/jquery-sparkline/dist/jquery.sparkline.js"></script>
	
	<!-- jvectormap -->
	<script src="<?php echo base_url();?>assets/vendor_plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>	
	<script src="<?php echo base_url();?>assets/vendor_plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>
	
	<!-- jQuery Knob Chart -->
	<script src="<?php echo base_url();?>assets/vendor_components/jquery-knob/js/jquery.knob.js"></script>
	
	<!-- daterangepicker -->
	<script src="<?php echo base_url();?>assets/vendor_components/moment/min/moment.min.js"></script>
	<script src="<?php echo base_url();?>assets/vendor_components/bootstrap-daterangepicker/daterangepicker.js"></script>
	
	<!-- datepicker -->
	<script src="<?php echo base_url();?>assets/vendor_components/bootstrap-datepicker/dist/js/bootstrap-datepicker.js"></script>
	
	<!-- Bootstrap WYSIHTML5 -->
	<script src="<?php echo base_url();?>assets/vendor_plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.js"></script>
	
	<!-- Slimscroll -->
	<script src="<?php echo base_url();?>assets/vendor_components/jquery-slimscroll/jquery.slimscroll.js"></script>
	
	<!-- FastClick -->
	<script src="<?php echo base_url();?>assets/vendor_components/fastclick/lib/fastclick.js"></script>
	
	<!-- maximum_admin App -->
	<script src="<?php echo base_url();?>assets/js/template.js"></script>
	
	<!-- maximum_admin dashboard demo (This is only for demo purposes) -->
	<script src="<?php echo base_url();?>assets/js/pages/dashboard.js"></script>
	
	<!-- maximum_admin for demo purposes -->
	<script src="<?php echo base_url();?>assets/js/demo.js"></script>
 <script src="<?php echo base_url('assets/js/plugins/dataTables/jquery.dataTables.js');?>"></script>
    <script src="<?php echo base_url('assets/js/plugins/dataTables/datatables.min.js');?>"></script>

    <script src="<?php echo base_url('assets/js/plugins/dataTables/dataTables.select.js');?>"></script>
    <!-- Alertify -->
    <script src="<?php echo base_url('assets/js/plugins/alertify/alertify.min.js');?>"></script>
    <script src="<?php echo base_url('assets/js/custom.js');?>"></script>
    <script src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
    <?php if(isset($js_script)){?>
    <script src="<?php echo base_url('assets/js/pages/');?><?=$js_script?>.js"></script>
    <?php } ?>
	<script>
	 $(document).ready(function() {

        $('.dataTables-example').DataTable({
                pageLength: 10,
                lengthMenu: [ [10, 25, 50, 100, -1], [10, 25, 50, 100, "All"] ],
                responsive: true,
                dom: '<"html5buttons"B>lTfgitp',
                buttons: [
                    { extend: 'copy'},
                    {extend: 'csv'},
                    {extend: 'excel', title: 'ExampleFile'},
                    {extend: 'pdf', title: 'ExampleFile'},

                    {extend: 'print',
                     customize: function (win){
                            $(win.document.body).addClass('white-bg');
                            $(win.document.body).css('font-size', '10px');

                            $(win.document.body).find('table')
                                    .addClass('compact')
                                    .css('font-size', 'inherit');
                    }
                    }
                ]

            });
	 });
			</script>

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery-datetimepicker/2.5.20/jquery.datetimepicker.full.js"></script>
<script type="text/javascript">
      $('#datetimepicker').datetimepicker({
    format:'Y-m-d H:i',
});

 $('.toggleclass').change(function(){
	     var id    = $(this).data("id");
	     var status = $('#status').text();	     
        $.ajax({
          type:'POST',
          dataType:'JSON',
          url:'<?php echo base_url('maintenance/on_off'); ?>',
          data:{id : id},
          success:function(data)
          {
                if(data != "")
                {
                 	$('#status').html(data);
                 		
                    alertify.success("Update successfully");
                } 
                else
                {
                    alertify.error("Please try again");
                }
          }
        });
      });
      
      
       $('.toggleclassstatus').change(function(){
       var id    = $(this).data("id");
       var status = $('#status').text();       
        $.ajax({
          type:'POST',
          dataType:'JSON',
          url:'<?php echo base_url('popup_offer/on_off'); ?>',
          data:{id : id},
          success:function(data)
          {
                if(data != "")
                {
                  $('#status'+ id).html(data);
                    
                    alertify.success("Update successfully");
                } 
                else
                {
                    alertify.error("Please try again");
                }
          }
        });
      });
      
      $('.payment_confirmation_box').change(function(){
          var response = confirm("Are you sure ..");
            if (response) {
                 var id    = $(this).data("check");
                 var val = $(this).val();
        	    
                $.ajax({
                  type:'POST',
                  dataType:'JSON',
                  url:'<?php echo base_url('withdrow_request/change_status'); ?>',
                  data:{id : id, val:val},
                  success:function(data)
                  {
                        if(data != "")
                        {
                         	$('#msg'+id).html(data);
                         	$('#selmsg'+id).html('<option value="">Select</option>' );
                            alertify.success("Update successfully");
                        } 
                        else
                        {
                            alertify.error("Please try again");
                        }
                  }
                });
            }
      });
      
       $('.matchPublish').change(function(){
          var response = confirm("Are you sure ..");
            if (response) {
                 var id    = $(this).data("id");
                 var val = $(this).val();
        	    
                $.ajax({
                  type:'POST',
                  dataType:'JSON',
                  url:'<?php echo base_url('match/publishMatch'); ?>',
                  data:{id : id, val:val},
                  success:function(data)
                  {
                        if(data != "")
                        {
                         	$('#status'+id).html(data);
                            alertify.success("Update successfully");
                        } 
                        else
                        {
                            alertify.error("Please try again");
                        }
                  }
                });
            }
      });
      
      
    </script>
 <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
      <div class="modal-content">
      	<div class="modal-header">
          <h4 class="modal-title">Update Referral Code</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button><br>
        </div>
        <form autocomplete="OFF" action="javascript:void(0);" method="post" id="ReferralUpdate" novalidate="novalidate">
	        <div class="modal-body">
	        	<p id="RUserEmail"></p>
	        	<input class="form-control" type="hidden" id="RuserId" name="RuserId">
	          	<p><input onkeyup="ReferralExist();" class="form-control Rreferral" type="text" placeholder="Update Referral Code" id="Rreferral" name="Rreferral"></p>
	          	<p id="errorReferral"></p>
	        </div>
	        <div class="modal-body">
	          <p> <input class="btn btn-info" id="ReferralUpdate" class="ReferralUpdate" type="submit" name="submit" value="update" /> </p>
	        </div>
    	</form>
        <div class="modal-footer">
          <button type="button" class="btn btn-success" id="ReferralClose" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>
  
  <div class="modal fade" id="playerModal" role="dialog">
    <div class="modal-dialog">
      <div class="modal-content">
      	<div class="modal-header">
          <h4 class="modal-title">Update Player Points</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button><br>
        </div>
        <form autocomplete="OFF" action="javascript:void(0);" method="post" id="pointsUpdate" novalidate="novalidate">
	        <div class="modal-body">
	        <b>	<p id="PName"></p> </b>
	        	<label><b>Batting Points</b></label>
	        	<input class="form-control" type="hidden" id="playerId" name="playerId">
	          	<p><input  class="form-control Rreferral" type="number" placeholder="Batting Points" id="batting" name="batting"></p>
	          	<label><b>Bowling Points</b></label>
	          	<p><input  class="form-control Rreferral" type="number" placeholder="Bolling Points" id="bolling" name="bolling"></p>
	          	<label><b>Fielding Points</b></label>
	          	<p><input  class="form-control " type="number" placeholder="Fielding Points" id="fielding" name="fielding"></p>
	          	<p id="errorpoints"></p>
	        </div>
	        <div class="modal-body">
	          <p> <input class="btn btn-info playerModals" id="playerModals" type="submit" name="submit" value="update" /> </p>
	        </div>
    	</form>
        <div class="modal-footer">
          <button type="button" class="btn btn-success" id="PointsClose" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>
  
   <div class="modal fade" id="tplayerModal" role="dialog">
    <div class="modal-dialog">
      <div class="modal-content">
      	<div class="modal-header">
          <h4 class="modal-title">Update Player Points</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button><br>
        </div>
        <form autocomplete="OFF" action="javascript:void(0);" method="post" id="tpointsUpdate" novalidate="novalidate">
	        <div class="modal-body">
	        <b>	<p id="tpname"></p> </b>
	        	<label><b>Batting Points</b></label>
	        	<input class="form-control" type="hidden" id="tplayerId" name="playerId">
	          	<p><input  class="form-control " type="number" placeholder="Batting Points" id="tbatting" name="batting"></p>
	          	<label><b>Bowling Points</b></label>
	          	<p><input  class="form-control " type="number" placeholder="Bolling Points" id="tbolling" name="bolling"></p>
	          	<label><b>Fielding Points</b></label>
	          	<p><input  class="form-control " type="number" placeholder="Fielding Points" id="tfielding" name="fielding"></p>
	          	<label><b>Second Inning Batting Points</b></label>
	          	<p><input  class="form-control " type="number" placeholder="Batting Points" id="second_batting" name="second_batting"></p>
	          	<label><b>Second Inning Bolling Points</b></label>
	          	<p><input  class="form-control " type="number" placeholder="Bolling Points" id="second_bolling" name="second_bolling"></p>
	          	<label><b>Second Inning Fielding Points</b></label>
	          	<p><input  class="form-control " type="number" placeholder="Fielding Points" id="second_fielding" name="second_fielding"></p>
	          	<p id=""></p>
	        </div>
	        <div class="modal-body">
	          <p> <input class="btn btn-info tplayerModals" id="tplayerModals" type="submit" name="submit" value="update" /> </p>
	        </div>
    	</form>
        <div class="modal-footer">
          <button type="button" class="btn btn-success" id="tPointsClose" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>

</body>

</html>

 <script>
$('.playerModals').click(function(){
    var PId = $('#playerId').val();
    var batting = $('#batting').val();
    var bolling = $('#bolling').val();
    var fielding = $('#fielding').val();
    $.ajax({
            type : 'post', 
            url: SITE_URL+'match_players/p_update',
            data: {PId : PId,batting : batting,bolling : bolling,fielding : fielding},
            success : function(data) {
                location.reload();
              
        }
        });
});

$('.tplayerModals').click(function(){
    var PId = $('#tplayerId').val();
    var batting = $('#tbatting').val();
    var bolling = $('#tbolling').val();
    var fielding = $('#tfielding').val();
    var second_batting = $('#second_batting').val();
    var second_bolling = $('#second_bolling').val();
    var second_fielding = $('#second_fielding').val();
    $.ajax({
            type : 'post', 
            url: SITE_URL+'match_players/tp_update',
            data: {PId : PId,batting : batting,bolling : bolling,fielding : fielding,second_batting:second_batting,second_bolling:second_bolling,second_fielding:second_fielding},
            success : function(data) {
                location.reload();
              
        }
        });
});


</script>

   