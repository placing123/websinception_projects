 <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>Fix Amount Credit Bonus Percentage
      </h1>
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="#"><i class="fa fa-dashboard"></i> Dashboard</a></li>
        <li class="breadcrumb-item"><a href="#">Credit Bonus</a></li>
        <li class="breadcrumb-item active">Create</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
          
     <div class="box box-default">
        
        <!-- /.box-header -->
        <div class="box-body wizard-content">
          <!-- <div class="ibox-title">
                        <?php// echo anchor(site_url('credit_bonus/create'),'Create', 'class="btn btn-primary"'); ?>
                     
                    </div> -->
                  
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
                            <th>Fix Amount</th>
                            <th>Percentage</th>
                             <th>Action</th> 
                                </tr>
                                </thead>
                                <?php
                                foreach ($bonuss as $bonus)
                                {
                                    ?>
                                    <tr>
                                <td width="80px"><?php echo ++$start ?></td>
                                
                                <td><?php echo $bonus->amount ?></td>
                                <td><?php echo $bonus->percentage ?></td>
                     
                                 <td width="200px">
                                    <?php 
                                    echo anchor(site_url('deposit_amount/update/'.$bonus->id),'<button style="border-radius: 6px;" type="button" class="btn btn-success" ><i class="fa fa-pencil" aria-hidden="true"></i></button>'); 
                                   // echo anchor(site_url('deposit_amount/delete/'.$bonus->id),'<button style="border-radius: 6px;" type="button" class="btn btn-danger"><i class="fa fa-trash" aria-hidden="true"></i></button>','onclick="javasciprt: return confirm(\'Are You Sure ?\')"'); 
                                    ?>
                                </td> 
                            </tr>
                        <?php
                    }
                    ?>
                        </table>
                       
                      </div>
                    </div>
                 
                </div>
              </div>
            </div>