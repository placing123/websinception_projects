<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        Match Contest List
      </h1>
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="#"><i class="fa fa-dashboard"></i> Dashboard</a></li>
        <li class="breadcrumb-item"><a href="#">match contest</a></li>
      </ol>
    </section>
    <!-- Main content -->
    <section class="content">
          
	 <div class="box box-default">
        
        <!-- /.box-header -->
        <div class="box-body wizard-content">
                    
                    
            <div class="col-md-4 text-center">
                <div style="margin-top: 8px" id="message">
                    <?php echo $this->session->userdata('message') <> '' ? $this->session->userdata('message') : ''; ?>
                </div>
            </div>
              <div class="ibox-content">
                      <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover dataTables-example" >
                      <thead>
                          <tr>
                            <th>No</th>
                              <th>Contest Name</th>
                              <th>Cancell Contest</th>
                              <th>Winners</th>
                              <th>Prize Pool</th>
                              <th>Total Team</th>
                              <th>Join Team</th>
                              <th>Entry</th>
                              <th>View Team</th>
                              <th>Contest Cancelled</th>
                              <th>Payment Status</th>
                              <th>Send Payment</th>
                          </tr>
                      <tbody>
                          <?php 
                          $i = 1;
                          foreach ($all_contest as $contest) { ?>
                           <tr>
                            <td><?php echo $i++;?></td>
                            <td><?php echo $contest['contest_name'];?></td>
                            <td>
                                <?php if($contest['cancelled'] =="1"){?>
                                    <button style="border-radius: 6px;" type="button" class="btn btn-success">Cancelled</button>
                
                                <?php } else { ?>
                                    <a href="<?php echo base_url('contest/contest_cancell/'.base64_encode($contest['contest_id']).'/'.base64_encode($contest['match_id'])); ?>" onclick="javasciprt: return confirm('Are You Sure you want to cancell Contest ?')"><button style="border-radius: 6px;" type="button" class="btn btn-info"><i class="fa fa-paper-plane" aria-hidden="true"></i></button></a>
                                <?php } ?>
                            </td>
                            <td><?php echo $contest['winners'];?></td>
                            <td><?php echo $contest['prize_pool'];?></td>
                            <td><?php echo $contest['total_team'];?> </td>
                            <td><?php echo $contest['join_team'] ; ?></td>
                            <td><?php echo $contest['entry'] ; ?></td>
                            <td><a href="<?php echo base_url('old_match/match_leaderboard/'.$contest['match_id'].'/'.$contest['contest_id']);?>"><button  style="border-radius: 6px;" type="button" class="btn btn-success">View</button></a></td>
                            <td><?php if($contest['cancelled'] =="1"){ echo '<button style="border-radius: 6px;" type="button" class="btn btn-danger">Contest Cancelled</button>' ;}else {echo '<button style="border-radius: 6px;" type="button" class="btn btn-success">Contest Complete</button>'; } ?></td>
                            <td><?php if($contest['payment_status'] =="1"){ echo '<button style="border-radius: 6px;" type="button" class="btn btn-success">Done</button>' ;}else if($contest['cancelled'] =="1") {echo '<button style="border-radius: 6px;" type="button" class="btn btn-danger">Done</button>'; } else {echo '<button style="border-radius: 6px;" type="button" class="btn btn-success">Not Done</button>'; } ?></td>
                            <td><?php if($contest['payment_status'] =="1"){ ?>
                            <button style="border-radius: 6px;" type="button" class="btn btn-success">Payment done</button>
                            
                            <?php  }else if($contest['payment_status'] =="0" && $contest['cancelled'] =="0" ) { echo anchor(site_url('payment/send/'.base64_encode($contest['contest_id']).'/'.base64_encode($contest['match_id']).'/'.base64_encode($contest['join_team'])),'<button style="border-radius: 6px;" type="button" class="btn btn-info"><i class="fa fa-paper-plane" aria-hidden="true"></i></button>','onclick="javasciprt: return confirm(\'Are You Sure ?\')"'); }  else{ ?>
                            <button style="border-radius: 6px;" type="button" class="btn btn-danger">Payment done</button>
                            
                            <?php  } ?></td>
                          </tr>
                           <?php 
                          } ?>
                        </tbody>   
                    </table>
          </div>
        </div>
     
    </div>
  </div>
</div>
      
       
       