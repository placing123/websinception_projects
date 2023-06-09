<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        User List
      </h1>
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="#"><i class="fa fa-dashboard"></i> Dashboard</a></li>
        <li class="breadcrumb-item"><a href="#">user</a></li>
        <li class="breadcrumb-item active">list</li>
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
                                <th>Name</th>
                                <th>Mobile No</th>
                                <th>Email</th>
                                <th>Wallet</th>
                                <th>Image</th>
                                <th>Type</th>
                                <th>Referral Code</th>
                                <th>Update Code</th>
                                <th>Referral Users List</th>
                                <th>Referral Users Count</th>
                                <th>Team Name</th>
                                <th>Favouirte Team</th>
                                <th>Date of birth</th>
                                <th>Gender</th>
                                <th>Address</th>
                                <th>City</th>
                                <th>State</th>
                                <th>Country</th>
                                <th>Pincode</th>
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
                                <td><a href="<?php echo base_url('user/wallet/'.$user->user_id); ?>" ><button style="border-radius: 6px;" type="button" class="btn btn-info"><i class="fa fa-book" aria-hidden="true"></i></button></a> </td>
                                <td>
                                    <?php if($user->image !=""){?>
                                        <img src="<?php echo base_url('uploads/user/'.$user->image);?>" style="height: 42px; width: 60px;">
                                    <?php }else{?>
                                        <img src="<?php echo base_url('uploads/user/default.jpg');?>" style="height: 42px; width: 60px;">
                                    <?php } ?>
                                </td>
                                <td><?php echo $user->type ?></td>
                                <td id="NewReferral<?php echo $user->user_id?>"><?php echo $user->referral_code ?></td>
                                <td><button data-email="<?php echo $user->email ?>" data-id="<?php echo $user->user_id?>" data-code="<?php echo $user->referral_code?>" data-toggle="modal" data-target="#myModal" style="border-radius: 6px;" type="button" class="btn btn-info referral_codeModel"><i class="fa fa-edit" aria-hidden="true"></i></button></td>
                                <td><a target="_blank" href="<?php echo base_url('user/referral_users/'.base64_encode($user->user_id).'/'.base64_encode($user->referral_code)); ?>" ><button style="border-radius: 6px;" type="button" class="btn btn-success"><i class="fa fa-eye" aria-hidden="true"></i></button></a></td>
                                <td><?php  echo referral_code($user->referral_code); ?> </td>
                                <td><?php echo $user->teamName ?></td>
                                <td><?php echo $user->favriteTeam ?></td>
                                <td><?php echo $user->dob ?></td>
                                <td><?php echo $user->gender ?></td>
                                <td><?php echo $user->address ?></td>
                                <td><?php echo $user->city ?></td>
                                <td><?php echo $user->state ?></td>
                                <td><?php echo $user->country ?></td>
                                <td><?php echo $user->pincode ?></td>
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