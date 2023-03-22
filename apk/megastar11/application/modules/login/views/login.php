<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="<?php echo base_url();?>/assets/images/favicon.ico">

    <title><?php echo SITE_TITLE; ?></title>
   
    <link rel="stylesheet" href="<?php echo base_url();?>/assets/vendor_components/bootstrap/dist/css/bootstrap.min.css">

    <link rel="stylesheet" href="<?php echo base_url();?>/assets/vendor_components/font-awesome/css/font-awesome.min.css">

    <link rel="stylesheet" href="<?php echo base_url();?>/assets/vendor_components/Ionicons/css/ionicons.min.css">

    <link rel="stylesheet" href="<?php echo base_url();?>/assets/css/master_style.css">

    <link rel="stylesheet" href="<?php echo base_url();?>/assets/css/skins/_all-skins.css">   
<?php
$csrf = array(
        'name' => $this->security->get_csrf_token_name(),
        'hash' => $this->security->get_csrf_hash()
); ?>   
    <link href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700" rel="stylesheet">

</head>
<body class="hold-transition login-page">
<div class="login-box">
  <div class="login-logo">
    <a href="#"><b><?php echo SITE_TITLE; ?></b></a>
  </div>
  <!-- /.login-logo -->
  <div class="login-box-body">
    <p class="login-box-msg">Sign in to start your session</p>

    <form action="<?php echo site_url('login');?>" method="post" class="form-element">
      <div class="form-group has-feedback">
        <input type="email" name="loginname" class="form-control" placeholder="Email">
        <span class="ion ion-email form-control-feedback"></span>
      </div>
       <input type="hidden" name="<?=$csrf['name'];?>" value="<?=$csrf['hash'];?>" />
      <div class="form-group has-feedback">
        <input type="password" name="loginpassword" class="form-control" placeholder="Password">
        <span class="ion ion-locked form-control-feedback"></span>
      </div>
      <div class="row">
        <div class="col-12 text-center">
          <button type="submit" class="btn btn-info btn-block btn-flat margin-top-10">SIGN IN</button>
        </div>
      </div>
    </form>

  </div>
</div>

    <script src="<?php echo base_url();?>/assets/vendor_components/jquery/dist/jquery.min.js"></script>

    <script src="<?php echo base_url();?>/assets/vendor_components/popper/dist/popper.min.js"></script>

    <script src="<?php echo base_url();?>/assets/vendor_components/bootstrap/dist/js/bootstrap.min.js"></script>

</body>

</html>
