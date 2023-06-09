<?php

if (!isset($_SESSION['id'])){
    redirect(site_url());
}

$csrf = array(
    'name' => $this->security->get_csrf_token_name(),
    'hash' => $this->security->get_csrf_hash()
);

 $i =  $this->uri->segment(1);
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML Basic 1.1//EN"
    "http://www.w3.org/TR/xhtml-basic/xhtml-basic11.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><?php echo SITE_TITLE; ?></title>
    <link rel="icon" href="<?php echo base_url('assets/img/dd2.png');?>">
 <!-- Bootstrap v4.0.0-beta -->
  <link rel="stylesheet" href="<?php echo base_url();?>assets/vendor_components/bootstrap/dist/css/bootstrap.css">
  
  <!-- font awesome -->
  <link rel="stylesheet" href="<?php echo base_url();?>assets/vendor_components/font-awesome/css/font-awesome.css">
  
  <!-- ionicons -->
  <link rel="stylesheet" href="<?php echo base_url();?>assets/vendor_components/Ionicons/css/ionicons.css">
  
  <!-- theme style -->
  <link rel="stylesheet" href="<?php echo base_url('assets/');?>css/master_style.css">
  
  <!-- maximum_admin skins. choose a skin from the css/skins folder instead of downloading all of them to reduce the load. -->
  <link rel="stylesheet" href="<?php echo base_url('assets/');?>css/skins/_all-skins.css">
  
  <!-- morris chart -->
  <link rel="stylesheet" href="<?php echo base_url();?>assets/vendor_components/morris.js/morris.css">
  
  <!-- jvectormap -->
  <link rel="stylesheet" href="<?php echo base_url();?>assets/vendor_components/jvectormap/jquery-jvectormap.css">
  
  <!-- date picker -->
  <link rel="stylesheet" href="<?php echo base_url();?>assets/vendor_components/bootstrap-datepicker/dist/css/bootstrap-datepicker.css">
  
  <!-- daterange picker -->
  <link rel="stylesheet" href="<?php echo base_url();?>assets/vendor_components/bootstrap-daterangepicker/daterangepicker.css">
  
  <!-- bootstrap wysihtml5 - text editor -->
  <link rel="stylesheet" href="<?php echo base_url();?>assets/vendor_plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.css">
   <link href="<?php echo base_url('assets/css/plugins/dataTables/datatables.min.css');?>" rel="stylesheet">
    <link href="<?php echo base_url('assets/css/plugins/alertify/alertify.core.css');?>" rel="stylesheet">   
    <link href="<?php echo base_url('assets/css/plugins/dataTables/jquery.dataTables.css');?>" rel="stylesheet">
    <link href="<?php echo base_url('assets/css/plugins/dataTables/select.dataTables.min.css');?>" rel="stylesheet">
    <link href="<?php echo base_url('assets/css/plugins/alertify/alertify.default.css');?>" rel="stylesheet">

  <!-- google font -->
  <link href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-datetimepicker/2.5.20/jquery.datetimepicker.css">
     
  </head>

<body class="skin-blue">
<div class="wrapper">


  <header class="main-header">
    <!-- Logo -->
    <a href="<?php echo base_url();?>" class="logo">
      <!-- mini logo for sidebar mini 50x50 pixels -->
      <span class="logo-mini"></span>
      <!-- logo for regular state and mobile devices -->
      <span class="logo-lg"><b><?php echo SITE_TITLE; ?></b></span>
    </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
      <!-- Sidebar toggle button-->
      <a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button">
        <span class="sr-only">Toggle navigation</span>
      </a>

      <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">
          <!-- User Account: style can be found in dropdown.less -->
          <li class="dropdown user user-menu">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <img src="<?php echo base_url();?>uploads/default.jpg" class="user-image rounded-circle" alt="User Image">
              <span class="d-none d-sm-inline-block"><?php echo SITE_TITLE; ?></span>
            </a>
            <ul class="dropdown-menu scale-up">
              <!-- User image -->
              <li class="user-header">
                <img src="<?php echo base_url();?>uploads/default.jpg" class="img-fluid" alt="User Image">

                <p>
                 <?php echo SITE_TITLE; ?>
                  <!-- <small>Member since April . 2016</small> -->
                </p>
              </li>
             
              <!-- Menu Footer-->
              <div class="user-footer">
                <div class="pull-left">
                  <a href="#" class="btn btn-default btn-flat">Profile</a>
                </div>
                <div class="pull-right">
        <a class="btn btn-default btn-flat" href="<?php echo site_url('login/logout/'.$login);?>" onclick="return FB.logout()">
                    <i class="fa fa-sign-out"></i> Log out
                  </a>
                 
                </div>
              </div>
            </ul>
          </li>
         
         
        
          <!-- Control Sidebar Toggle Button -->
          <li>
            <a href="#" data-toggle="control-sidebar"><i class="fa fa-cog fa-spin"></i></a>
          </li>
        </ul>
      </div>
    </nav>
  </header>
  
  <!-- Left side column. contains the logo and sidebar -->
  <aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
      <!-- Sidebar user panel -->
      <div class="user-panel">
        <div class="image">
          <img height="50px" width="50px" src="<?php echo base_url();?>uploads/default.jpg" class="rounded-circle" alt="User Image">
        </div>
        
      </div>
      <!-- search form -->
      
      <!-- /.search form -->
      <!-- sidebar menu: : style can be found in sidebar.less -->
      <ul class="sidebar-menu" data-widget="tree">
        <li class="header">MAIN NAVIGATION</li>
        <li <?php if($i == "dashboard"){?>class="active" <?php } ?> >
          <a href="<?php echo site_url('dashboard'); ?>">
            <i class="fa fa-dashboard"></i> <span>Dashboard</span> 
          </a> 
        </li>
        <li class="">
          <a href="<?php  echo site_url('match_calender'); ?>">
            <i class="fa fa-id-card-o"></i> <span>Match Sync</span> 
          </a> 
        </li>
        <li <?php if($i == "banners"){?>class="active" <?php } ?>>
          <a href="<?php echo site_url('banners'); ?>">
            <i class="fa fa-id-card-o"></i> <span>Banners </span> 
          </a> 
        </li>
         <li <?php if($i == "match"){?>class="active" <?php } ?>>
          <a href="<?php echo site_url('match'); ?>">
            <i class="fa fa-id-card-o"></i> <span>Fixture Match</span> 
          </a> 
        </li>
        <li <?php if($i == "livematch"){?>class="active" <?php } ?>>
          <a href="<?php echo site_url('livematch'); ?>">
            <i class="fa fa-id-card-o"></i> <span>Live Match</span> 
          </a> 
        </li>
        <li <?php if($i == "old_match"){?>class="active" <?php } ?>>
          <a href="<?php echo site_url('old_match'); ?>">
            <i class="fa fa-id-card-o"></i> <span>Result Match</span> 
          </a> 
        </li>
        <li <?php if($i == "cancel"){?>class="active" <?php } ?>>
          <a href="<?php echo site_url('cancel'); ?>">
            <i class="fa fa-id-card-o"></i> <span>Cancelled Match</span> 
          </a> 
        </li>
         <li <?php if($i == "withdrow_request"){?>class="active" <?php } ?>>
          <a href="<?php echo site_url('withdrow_request'); ?>">
            <i class="fa fa-id-card-o"></i> <span>Withdrow request</span> 
          </a> 
        </li>
        <li <?php if($i == "bonus"){?>class="active" <?php } ?>>
          <a href="<?php echo site_url('bonus'); ?>">
            <i class="fa fa-id-card-o"></i> <span>Signup Bonus</span> 
          </a> 
        </li> 
        <li <?php if($i == "default_contest"){?>class="active" <?php } ?>>
          <a href="<?php echo site_url('default_contest'); ?>">
            <i class="fa fa-dashboard"></i> <span>Default Contest</span> 
          </a> 
        </li>
        <li <?php if($i == "notification"){?>class="active" <?php } ?>>
          <a href="<?php echo site_url('notification'); ?>">
            <i class="fa fa-laptop"></i> <span>Notification</span> 
          </a> 
        </li>
        <li <?php if($i == "user"){?>class="active" <?php } ?>>
          <a href="<?php echo site_url('user'); ?>">
            <i class="fa fa-user"></i> <span>Users</span> 
          </a> 
        </li>
         <li <?php if($i == "kyc"){?>class="active" <?php } ?>>
          <a href="<?php echo site_url('kyc'); ?>">
            <i class="fa fa-dashboard"></i> <span>KYC Status</span> 
          </a> 
        </li>

        <li <?php if($i == "points_distribution_rules"){?>class="active" <?php } ?>>
          <a href="<?php echo site_url('points_distribution_rules'); ?>">
            <i class="fa fa-percent"></i> <span>Point Distribution</span> 
          </a> 
        </li>

        <li <?php if($i == "players"){?>class="active" <?php } ?>>
          <a href="<?php echo site_url('players'); ?>">
            <i class="fa fa-user-secret"></i> <span>Players</span> 
          </a> 
        </li>

         <li <?php if($i == "team"){?>class="active" <?php } ?>>
          <a href="<?php echo site_url('team'); ?>">
            <i class="fa fa-id-card-o"></i> <span>Team</span> 
          </a> 
        </li>
        <li <?php if($i == "password"){?>class="active" <?php } ?> >
          <a href="<?php echo site_url('password'); ?>">
            <i class="fa fa-lock"></i> <span>Change Password</span> 
          </a> 
        </li>
        <li class="<?php if($i == "Website"){?> active<?php } ?> ">
          <a href="<?php echo site_url('Website'); ?>">
            <i class="fa fa-laptop"></i> <span>Website</span> 
          </a> 
        </li>
        <li class="<?php if($i == "maintenance"){?> active<?php } ?> ">
          <a href="<?php echo site_url('maintenance'); ?>">
            <i class="fa fa-laptop"></i> <span>Maintenance Status</span> 
          </a> 
        </li>
        <li class="<?php if($i == "deposit_amount"){?> active<?php } ?> ">
          <a href="<?php echo site_url('deposit_amount'); ?>">
            <i class="fa fa-laptop"></i> <span>Deposit Amount</span> 
          </a> 
        </li>
        <li class="<?php if($i == "popup_offer"){?> active<?php } ?> ">
          <a href="<?php echo site_url('popup_offer'); ?>">
            <i class="fa fa-laptop"></i> <span>Popup Offer</span> 
          </a> 
        </li>
      </ul>
    </section>
  </aside>