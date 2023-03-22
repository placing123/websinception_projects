<div class="content-wrapper" style="min-height: 1147px;">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        Dashboard 
      </h1>
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li class="breadcrumb-item active">Dashboard </li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
      <!-- Info boxes -->
      <div class="row">
        <div class="col-12 col-md-6 col-lg-6">
          <div class="info-box">
            <span class="info-box-icon bg-blue"><i class="ion ion-stats-bars"></i></span>

            <div class="info-box-content">
              <span class="info-box-number"><?php echo $total_contest; ?></span>
              <span class="info-box-text">Total Contest</span>
            </div>
            <!-- /.info-box-content -->
          </div>
          <!-- /.info-box -->
        </div>
        <!-- /.col -->
        <!--<div class="col-12 col-md-6 col-lg-4">-->
        <!--  <div class="info-box">-->
        <!--    <span class="info-box-icon bg-green"><i class="ion ion-thumbsup"></i></span>-->

        <!--    <div class="info-box-content">-->
        <!--      <span class="info-box-number"><?php echo $total_usresjoin; ?></span>-->
        <!--      <span class="info-box-text">Total User Join</span>-->
        <!--    </div>-->
            <!-- /.info-box-content -->
        <!--  </div>-->
          <!-- /.info-box -->
        <!--</div>-->
        <!-- /.col -->

        <!-- fix for small devices only -->
        <div class="clearfix visible-sm-block"></div>
        <!-- /.col -->
        <div class="col-12 col-md-6 col-lg-6">
          <div class="info-box">
            <span class="info-box-icon bg-red"><i class="ion ion-person-stalker"></i></span>

            <div class="info-box-content">
              <span class="info-box-number"><?php echo $join_members->num_rows; ?></span>
              <span class="info-box-text">Total Join Members</span>
            </div>
            <!-- /.info-box-content -->
          </div>
          <!-- /.info-box -->
        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->
      <div class="row">
        <div class="col-12 col-md-6 col-lg-4">
          <div class="info-box">
            <span class="info-box-icon bg-blue"><i class="ion ion-stats-bars"></i></span>

            <div class="info-box-content">
              <span class="info-box-number"><?php echo isset($upcoming_match) ? $upcoming_match:0 ; ?></span>
              <span class="info-box-text">Total Fixture Match</span>
            </div>
            <!-- /.info-box-content -->
          </div>
          <!-- /.info-box -->
        </div>
        <!-- /.col -->
        <div class="col-12 col-md-6 col-lg-4">
          <div class="info-box">
            <span class="info-box-icon bg-green"><i class="ion ion-thumbsup"></i></span>

            <div class="info-box-content">
              <span class="info-box-number"><?php echo isset($Live_matches) ? $Live_matches:0; ?></span>
              <span class="info-box-text">Total Live Match</span>
            </div>
            <!-- /.info-box-content -->
          </div>
          <!-- /.info-box -->
        </div>
        <!-- /.col -->

        <!-- fix for small devices only -->
        <div class="clearfix visible-sm-block"></div>
        <!-- /.col -->
        <div class="col-12 col-md-6 col-lg-4">
          <div class="info-box">
            <span class="info-box-icon bg-red"><i class="ion ion-person-stalker"></i></span>

            <div class="info-box-content">
              <span class="info-box-number"><?php echo isset($result_matches) ? $result_matches:0; ?></span>
              <span class="info-box-text">Total Result Match</span>
            </div>
            <!-- /.info-box-content -->
          </div>
          <!-- /.info-box -->
        </div>
        <!-- /.col -->
      </div>
        <!-- /.col -->
        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->
    </section>
    <!-- /.content -->
  </div>
  