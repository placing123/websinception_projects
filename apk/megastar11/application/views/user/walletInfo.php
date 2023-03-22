<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        Wallet info
      </h1>
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="#"><i class="fa fa-dashboard"></i> Dashboard</a></li>
        <li class="breadcrumb-item"><a href="#">user</a></li>
        <li class="breadcrumb-item active">Create</li>
      </ol>
    </section>
    
    <style>
        table, th, td
        {
            border: 0px solid black;
            padding: 10px;
        }
    </style>

    <!-- Main content -->
    <section class="content">
          
    <div class="box box-default">
        
        <!-- /.box-header -->
        <div class="box-body wizard-content">
                    <!-- <div class="ibox-title">
                        <?php //echo anchor(site_url('contest/create'),'Create', 'class="btn btn-primary"'); ?> 
                    </div> -->

                    <!--Total Balance 
                    <div class="ibox-title">
                     <?php //echo $wallets_amount['amount']; ?> Rs    
                    </div>
                    
                    Deposited 
                    <div class="ibox-title">
                     <?php //echo $wallets_amount['amount']; ?> Rs    
                    </div>-->
                    <table>
                        <tr>
                            <th>Total Balance</th>
                            <th>Deposited</th>
                            <th>Winning</th>
                        </tr>
                        <tr>
                            <td><?php echo $wallets_amount['amount']; ?> Rs</td>
                            <td><?php 
                                    $wallets_deposit = $wallets_credit['amount'] - $wallets_debit['amount'];
                                    echo $wallets_deposit; 
                                ?> Rs</td>
                            <td><?php 
                                    $wallets_win = $wallets_winning['amount'] - $wallets_winning_debit['amount'];
                                    echo $wallets_win; 
                                ?> Rs</td>
                        </tr>
                    </table>
                    </br>
                    <form action="<?php echo base_url('user/user_wallet_addcash'); ?>" method="post" enctype="multipart/form-data">
	    
                        <div class="form-group">
                            ADD CASH
                            </br>
                            <input type="number" name="txt_cash" min="1" max="99999" required/> 
                            <button type="submit" class="btn btn-success" name="btn_add_cash">ADD Cash</button>
                        </div>
                	     <?php
                	        $url = $_SERVER['REQUEST_URI'];
                	     ?>
                	    <input type="hidden" name="user_id" value="<?php echo end(explode("/", $url)); ?>" />
                        
                	</form>
                	<form action="<?php echo base_url('user/user_wallet_addbonus'); ?>" method="post" enctype="multipart/form-data">
	    
                        <div class="form-group">
                            ADD BONUS
                            </br>
                            <input type="number" name="txt_bonus" min="1" max="99999" required/> 
                            <button type="submit" class="btn btn-success" name="btn_add_bonus">ADD Bonus</button>
                        </div>
                	     <?php
                	        $url = $_SERVER['REQUEST_URI'];
                	     ?>
                	    <input type="hidden" name="user_id" value="<?php echo end(explode("/", $url)); ?>" />
                        
                	</form>
                    
                    
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
                                <th>Type</th>
                                <th>Status</th>
                                <th>Amount</th>
                                <th>Action</th>
                            </tr>
                        <tbody>
                            <?php
                                foreach ($wallets as $user)
                                { if($user->amount != "0.0"){ 
                                ?>
                            <tr>
                                <td width="80px"><?php echo ++$start ?></td>
                                <td><?php echo $user->name ?></td>
                                <td><?php echo $user->mobile ?></td>
                                <td><?php echo $user->email ?></td>
                                <td><?php echo ucfirst(str_replace("_", " ", $user->type)); ?></td>
                                <td><?php echo $user->transaction_status ?></td>
                                <td><?php echo $user->amount ?></td>
                                <td style="text-align:center" width="200px">
                                    <?php 
                                    echo anchor(site_url('user/wallet_update/'.$user->id),'<button style="border-radius: 6px;" type="button" class="btn btn-success" ><i class="fa fa-pencil" aria-hidden="true"></i></button>'); 
                                                            echo " "; 
                                   
                                    ?>
                                </td>
                            </tr>
                             <?php
                            }   }
                            ?>
                          </tbody>   
                    </table>
                </div>
            </div>         
        </div>
    </div>
    </section>
</div>
      
       
       