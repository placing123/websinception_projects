 <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        Withdrow Request List
      </h1>
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="#"><i class="fa fa-dashboard"></i> Dashboard</a></li>
        <li class="breadcrumb-item"><a href="#">Request list</a></li>
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
                <th>User Name</th>
                <th>Amount</th>
                <th>User Account Name</th>
                <th>User Account Number</th>
                <th>User Bank Name</th>
                <th>User Bank IFSC</th>
                <th>User Bank Address</th>
                <th>Change Status</th>
                <th>Status</th>
                <th>Request Date</th>
            </tr>
            </thead>
            <?php
            foreach ($request_data as $request)
            {
                ?>
                <tr>
            <td width="80px"><?php echo ++$start ?></td>
             <td><?php $user = $this->User_model->get_by_id($request->user_id);
                   echo $user->name;?></td> 
            <td><?php echo $request->amount; ?></td> 
            <td><?php echo $user->user_acc_name; ?></td> 
            <td><?php echo $user->acc_no; ?></td> 
            <td><?php echo $user->bank_name; ?></td> 
            <td><?php echo $user->ifsc_code; ?></td> 
            <td><?php echo $user->branch_address; ?></td>
            <td>
                <select data-check="<?php echo $request->id ?>"  id="selmsg<?php echo $request->id ?>" class='payment_confirmation_box' >
                    <option value="">Select</option>
                   <?php if($request->withdrow_request == "1"){ ?>
                    <option value="2">Accept</option>
                    <option  value="3">Reject</option>
                   <?php  } ?>
                </select>
            </td>
            <td id="msg<?php echo $request->id ?>" ><?php if($request->withdrow_request == "1"){ ?> <button style="border-radius: 6px;" type="button" class="btn btn-info">Payment Pending</i></button> <?php } else if($request->withdrow_request == "2"){ ?> <button style="border-radius: 6px;" type="button" class="btn btn-success">Payment Accept</i></button> <?php } else if($request->withdrow_request == "3"){ ?> <button style="border-radius: 6px;" type="button" class="btn btn-danger">Payment Reject</i></button> <?php } ?></td>
            
        <td><?php echo $request->created_date; ?></td>
        </tr>
                <?php
            }
            ?>
                        </table>
                         <div class="row">
            <div class="col-md-6">
                <!-- <a href="#" class="btn btn-primary">Total Record : <?php echo $total_rows ?></a> -->
         </div>
            
        </div> 
                      </div>
                    </div>
                 
                </div>
              </div>
            </div>
      
       
       


       