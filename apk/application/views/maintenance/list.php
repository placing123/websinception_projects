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
        Maintenance Status
      </h1>
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="#"><i class="fa fa-dashboard"></i> Dashboard</a></li>
        <li class="breadcrumb-item"><a href="#">maintenance</a></li>
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
                              <th>Name</th>
                              <th>Active Status</th>
                              <th>Status</th>
                            </tr>
                          </thead>
                          <tbody>
                        
                <tr>
            <td>Maintenance Status </td>            
             <td><?php if ($maintenance->maintenance_status == 0) 
                    {
                      ?>
                        <label class="switch">
                          <input type="checkbox" class="toggleclass" name="active" data-id="<?php echo $maintenance->id; ?>" value="1" checked>
                          <span class="slider round"></span>
                        </label>
                      <?php
                    }
                    else
                    {
                      ?>
                       <label class="switch">
                          <input type="checkbox" class="toggleclass" data-id="<?php echo $maintenance->id; ?>" name="unactive" value="0">
                          <span class="slider round"></span>
                        </label>
                    <?php
            }
            ?>    
            </td>
            <td id="status"><?php if($maintenance->maintenance_status == 0){
                            echo '<button style="border-radius: 6px;" type="button" class="btn btn-success">Active</button>'; 
                          } else if($maintenance->maintenance_status == 1) {
                            echo '<button style="border-radius: 6px;" type="button" class="btn btn-danger">Under Maintenance</button>';
                          } ?>
            </td>
        </tr>
                
                          </tbody>
                          
                        </table>
                      </div>
                    </div>
                 
                </div>
              </div>
            </div>
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>


       