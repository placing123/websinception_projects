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
       Fixture Match
      </h1>
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="#"><i class="fa fa-dashboard"></i> Dashboard</a></li>
        <li class="breadcrumb-item"><a href="#">match</a></li>
        <li class="breadcrumb-item active">Create</li>
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
                              <th>Contest</th>
                              <th>Tean First</th>
                              <th>Tean Second</th>
                              <th>Match Status</th>
                              <th>Match Time</th>
                              <th>Match Type</th>
                              <th>Match Players</th>
                              <th>Status</th>
                              <th>Publish Status</th>
                              <th>Action</th>
                            </tr>
                          </thead>
                          <tbody>
                         <?php
            foreach ($match_data as $match)
            {
                ?>
                <tr>
            <td width="80px"><?php echo ++$start ?></td>
            <td width="80px"> <a href="<?php echo base_url('contest/index/'.base64_encode($match->match_id)) ?>"> <button style="border-radius: 6px;" type="button" class="btn btn-info">Contest</button> </a></td>
            <td><?php
               $teamone =  $this->Match_model->team_name($match->teamid1);
             echo $teamone->team_name; ?></td>
            <td><?php 
              $teamtwo = $this->Match_model->team_name($match->teamid2);
            echo $teamtwo->team_name; ?></td>
            <td><?php echo $match->match_status; ?></td>
            <td><?php echo $match->time; ?></td>
            <td><?php echo $match->type; ?></td>
            
            <td><?php echo anchor(site_url('todaymatch/players/'.base64_encode($match->match_id)),'<button style="border-radius: 6px;" type="button" class="btn btn-info"><i class="fa fa-book" aria-hidden="true"></i></button>'); ?></td>
            <td><?php if ($match->publish == 1) 
                    {
                      ?>
                        <label class="switch">
                          <input type="checkbox" class="matchPublish" name="active" data-id="<?php echo $match->match_id; ?>" value="1" checked>
                          <span class="slider round"></span>
                        </label>
                      <?php
                    }
                    else
                    {
                      ?>
                       <label class="switch">
                          <input type="checkbox" class="matchPublish" data-id="<?php echo $match->match_id; ?>" name="unactive" value="0">
                          <span class="slider round"></span>
                        </label>
                    <?php
            }
            ?>    
            </td>
            <td id="status<?php echo $match->match_id; ?>"><?php if($match->publish == 0){
                            echo '<button style="border-radius: 6px;" type="button" class="btn btn-success">Not published</button>'; 
                          } else if($match->publish == 1) {
                            echo '<button style="border-radius: 6px;" type="button" class="btn btn-danger">Match Published</button>';
                          } ?>
            </td>
            <td style="text-align:center" width="200px">
                <?php 
                echo anchor(site_url('match/read/'.$match->match_id),'<button style="border-radius: 6px;" type="button" class="btn btn-info"><i class="fa fa-book" aria-hidden="true"></i></button>'); 
                echo ' '; 
                echo anchor(site_url('match/update/'.$match->match_id),'<button style="border-radius: 6px;" type="button" class="btn btn-success" ><i class="fa fa-pencil" aria-hidden="true"></i></button>'); 
                echo ' '; 
                echo anchor(site_url('match/delete/'.$match->match_id),'<button style="border-radius: 6px;" type="button" class="btn btn-danger"><i class="fa fa-trash" aria-hidden="true"></i></button>','onclick="javasciprt: return confirm(\'Are You Sure ?\')"'); 
                ?>
            </td>
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
            </div>
      
       
       