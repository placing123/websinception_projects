<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
          <h1>
            Match Player
          </h1>
          <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="#"><i class="fa fa-dashboard"></i> Dashboard</a></li>
            <li class="breadcrumb-item"><a href="#">Match Player</a></li>
            <li class="breadcrumb-item active">Create</li>
          </ol>
    </section>

    <!-- Main content -->
    <section class="content">
          
        <div class="box box-default"> 
            <!-- /.box-header -->
            <div class="box-body wizard-content">
                <!-- <div class="ibox-title">
                    <?php //echo anchor(site_url('match_players/create'),'Create', 'class="btn btn-primary"'); ?>
                </div> -->
                <div class="col-md-8 text-center">
                     <div style="margin-top: 8px" id="message">
                        <?php echo $this->session->userdata('message') <> '' ? $this->session->userdata('message') : ''; ?>
                     </div>
                </div>
                
                <div class="col-md-8 text-center">
                     <div style="margin-top: 8px" id="message">
                     <h2>   <?php echo $matchInfo['title']; echo " "; echo   $matchInfo['type'] . " Match"; ?></h2>
                     </div>
                </div>

                <div class="ibox-content">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover dataTables-example" >
                            <thead>
                                <tr>
                                    <th>No</th>
                                    <th>Team Name</th>
                                    <th>Player Name</th>
                                    <th>Player Image</th>
                                    <th>Playing 11</th>
                                    <th>Batting</th>
                                    <th>Bowling</th>
                                    <th>Fielding</th>
                                    <?php if($matchInfo['type'] == "Test") {  ?>
                                    <th>Second Batting</th>
                                    <th>Second Bowling</th>
                                    <th>Second Fielding</th>
                                    <?php } ?>
                                    <th>Update Points</th>
                                </tr>
                            </thead>
                            <?php
                            foreach ($match_players_data as $match_players)
                            {
                                ?>
                            <tr>
                                <td width="80px"><?php echo ++$start ?></td>
                                <td><?php echo $match_players->team_name ?></td>
                                <td><?php echo $match_players->name ?></td>
                                <td>
                                    <?php $player_image =$this->db->get_where('players',array('id'=>$match_players->playerid))->row(); ?>
                                    <img height="70px" width="70px" src="<?php echo $match_players->image; ?>"> </td>
                                
                                </td>
                                <td><?php echo $match_players->total_points ?></td>
                                <td><?php echo $match_players->batting_points ?></td>
                                <td><?php echo $match_players->bolling_points ?></td>
                                <td><?php echo $match_players->fielding_points ?></td>
                                <?php if($matchInfo['type'] =="Test" ) { ?>
                                
                                <td><?php echo $match_players->second_innings_batting ?></td>
                                <td><?php echo $match_players->second_innings_bolling ?></td>
                                <td><?php echo $match_players->second_innings_fielding ?></td>
                                <?php } ?>
                                <td><?php if($matchInfo['type'] =="Test"){ ?>
                                    <button data-toggle="modal" data-target="#tplayerModal" PName="<?php echo $match_players->name ?>"  mainID="<?php echo $match_players->id; ?>" class="btn btn-info TViewPoints">Update</button>
                                <?php  } else { ?>
                                <button data-toggle="modal" data-target="#playerModal" PName="<?php echo $match_players->name ?>"  mainID="<?php echo $match_players->id; ?>" class="btn btn-info ViewPoints">Update</button>
                                
                                
                                <?php  } ?> </td>
                            </tr>
                                <?php
                            }
                            ?>
                        </table>
                        <div class="row">
                            <div class="col-md-6">
                                <a href="#" class="btn btn-primary">Total Record : <?php echo $total_rows ?></a>
                            </div>
                            <!-- <div class="col-md-6 text-right">
                                <?php echo $pagination ?>
                            </div> -->
                        </div>
                    </div>
                </div>         
            </div>
        </div>
    </section>
</div>