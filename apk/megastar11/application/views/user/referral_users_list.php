<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        Referral Users List
      </h1>
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="#"><i class="fa fa-dashboard"></i> Dashboard</a></li>
        <li class="breadcrumb-item"><a href="#">referral</a></li>
        <li class="breadcrumb-item active">referral users</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
        <div class="table-responsive">
            <table class="table table-striped table-bordered table-hover" >
                <tr>
                    <th>Name</th>
                    <th>Mobile No</th>
                    <th>Email</th>
                    <th>Image</th>
                </tr>
                <tr>
                    <td><?php echo $users_info->name; ?></td>
                    <td><?php echo $users_info->mobile; ?></td>
                    <td><?php echo $users_info->email; ?></td>
                    <td>
                        <?php if($user->image !=""){?>
                            <img src="<?php echo base_url('uploads/user/'.$user->image);?>" style="height: 42px; width: 60px;">
                        <?php }else{?>
                            <img src="<?php echo base_url('uploads/user/default.jpg');?>" style="height: 42px; width: 60px;">
                        <?php } ?>
                    </td>
                </tr>
            </table>
        </div>
          
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
                                <th>Name</th>
                                <th>Mobile No</th>
                                <th>Email</th>
                                <th>Image</th>
                                <th>Code</th>
                            </tr>
                        <tbody>
                            <?php
                                foreach ($user_data as $user)
                                {
                                ?>
                            <tr>
                                <td width="80px"><?php echo ++$start ?></td>
                                <td><?php echo $user->name ?></td>
                                <td><?php echo $user->mobile ?></td>
                                <td><?php echo $user->email ?></td>
                                <td>
                                    <?php if($user->image !=""){?>
                                        <img src="<?php echo base_url('uploads/user/'.$user->image);?>" style="height: 42px; width: 60px;">
                                    <?php }else{?>
                                        <img src="<?php echo base_url('uploads/user/default.jpg');?>" style="height: 42px; width: 60px;">
                                    <?php } ?>
                                </td>
                                <td><?php echo $user->code ?></td>
                            </tr>
                             <?php
                               }
                            ?>
                          </tbody>   
                    </table>
                </div>
            </div>         
        </div>
    </div>
    </section>
</div>