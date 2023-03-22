<style>
.switch {
  position: relative;
  display: inline-block;
  width: 60px;
  height: 34px;
}

.switch input { 
  opacity: 0;
  width: 0;
  height: 0;
}

.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #ccc;
  -webkit-transition: .4s;
  transition: .4s;
}

.slider:before {
  position: absolute;
  content: "";
  height: 26px;
  width: 26px;
  left: 4px;
  bottom: 4px;
  background-color: white;
  -webkit-transition: .4s;
  transition: .4s;
}

input:checked + .slider {
  background-color: #2196F3;
}

input:focus + .slider {
  box-shadow: 0 0 1px #2196F3;
}

input:checked + .slider:before {
  -webkit-transform: translateX(26px);
  -ms-transform: translateX(26px);
  transform: translateX(26px);
}

/* Rounded sliders */
.slider.round {
  border-radius: 34px;
}

.slider.round:before {
  border-radius: 50%;
}
</style>

<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        Popup Offer
      </h1>
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="#"><i class="fa fa-dashboard"></i> Dashboard</a></li>
        <li class="breadcrumb-item"><a href="#">Popup offer</a></li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
          
     <div class="box box-default">
        
        <!-- /.box-header -->
        <div class="box-body wizard-content">
                   <div class="ibox-title">
                        <?php echo anchor(site_url('popup_offer/create'),'Create', 'class="btn btn-primary"'); ?>
                     
                    </div>
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
                              <th>S. No</th>
                              <th>Title</th>
                              <th>Image</th>
                              <th>Active Status</th>
                              <th>Status</th>
                              <th>Action</th>
                            </tr>
                          </thead>
                          <tbody>
                            <?php $i =1; foreach ($offers as $offer) {
                            
                             ?>
                <tr>
            <td><?php echo $i; ?> </td>
            <td><?php echo $offer->title ; ?> </td>      
            <td> <img height="50px" width="50px" src="<?php echo base_url('uploads/popup_offer/'.$offer->image ) ?>">  </td>            
             <td><?php if ($offer->status == 1) 
                    {
                      ?>
                        <label class="switch">
                          <input type="checkbox" class="toggleclassstatus" name="active" data-id="<?php echo $offer->id; ?>" value="1" checked>
                          <span class="slider round"></span>
                        </label>
                      <?php
                    }
                    else
                    {
                      ?>
                       <label class="switch">
                          <input type="checkbox" class="toggleclassstatus" data-id="<?php echo $offer->id; ?>" name="unactive" value="0">
                          <span class="slider round"></span>
                        </label>
                    <?php
            }
            ?>    
            </td>
            <td id="status<?php echo $offer->id; ?>"><?php if($offer->status == 1){
                            echo '<button style="border-radius: 6px;" type="button" class="btn btn-success">Active</button>'; 
                          } else if($maintenance->status == 0) {
                            echo '<button style="border-radius: 6px;" type="button" class="btn btn-danger">Not Active</button>';
                          } ?>
            </td>
            <td style="text-align:center" width="200px">
                <?php 
                
                echo anchor(site_url('popup_offer/update/'.$offer->id),'<button style="border-radius: 6px;" type="button" class="btn btn-success" ><i class="fa fa-pencil" aria-hidden="true"></i></button>'); 
                echo ' '; 
                echo anchor(site_url('popup_offer/delete/'.$offer->id),'<button style="border-radius: 6px;" type="button" class="btn btn-danger"><i class="fa fa-trash" aria-hidden="true"></i></button>','onclick="javasciprt: return confirm(\'Are You Sure ?\')"'); 
                ?>
            </td>
        </tr>
                <?php  $i++; } ?>
                          </tbody>
                          
                        </table>
                      </div>
                    </div>
                 
                </div>
              </div>
            </div>
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>


       