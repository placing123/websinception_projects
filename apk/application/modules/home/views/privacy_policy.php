
<!doctype html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<title><?php echo SITE_TITLE; ?></title>
	<link rel="stylesheet" href="<?php echo base_url('web_ast/css/bootstrap.css');?>">
	<link rel="stylesheet" href="<?php echo base_url('web_ast/css/style.css');?>">
	<link rel="stylesheet" href="<?php echo base_url('web_ast/css/font-awesome.min.css');?>">
	<link rel="stylesheet" href="<?php echo base_url('web_ast/css/owl.carousel.min.css');?>">
	<link rel="stylesheet" href="<?php echo base_url('web_ast/css/simpleLightbox.css');?>">
	<link rel="stylesheet" href="<?php echo base_url('web_ast/css/nice-select.css');?>">
	<link rel="stylesheet" href="<?php echo base_url('web_ast/css/animate.css');?>">
	<link rel="stylesheet" href="<?php echo base_url('web_ast/css/magnific-popup.css');?>">
	<link rel="stylesheet" href="<?php echo base_url('web_ast/css/responsive.css');?>">
</head>
<body data-spy="scroll" data-target="#mainNav" data-offset="70">

<header class="header_area">
	<div class="main_menu" id="mainNav">
		<nav class="navbar navbar-expand-lg navbar-light">
			<div class="container">
				<a class="navbar-brand logo_h" href="<?php echo base_url('home'); ?>"><img  height="80px" width="80px"  src="<?php echo base_url('web_ast/img/logo_splash.png');?>" alt=""></a>
				<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<div class="collapse navbar-collapse offset" id="navbarSupportedContent">
					<ul class="nav navbar-nav menu_nav ml-auto">
						<li class="nav-item active"><a class="nav-link" href="<?php echo base_url('home');?>">Home</a></li>
						<li class="nav-item"><a class="nav-link" href="<?php echo base_url('home');?>">FEATURES</a></li>
						<li class="nav-item"><a class="nav-link" href="<?php echo base_url('home');?>">SCREENS</a>
						<li class="nav-item submenu dropdown">
						<a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">More</a>
						<ul class="dropdown-menu">
							<li class="nav-item"><a class="nav-link" href="<?php echo base_url('about-us');?>">About Us</a></li>
							<li class="nav-item"><a class="nav-link" href="<?php echo base_url('terms-&-conditions');?>">Terms & Conditions</a></li>
							<li class="nav-item"><a class="nav-link" href="<?php echo base_url('privacy-policy');?>">Privacy Policy</a></li>
							<li class="nav-item"><a class="nav-link" href="<?php echo base_url('points-system');?>">Point System</a></li>
							<li class="nav-item"><a class="nav-link" href="<?php echo base_url('withdraw-cash');?>">Withdraw Cash</a></li>
							<li class="nav-item"><a class="nav-link" href="<?php echo base_url('legality');?>">Legality</a></li>
							<li class="nav-item"><a class="nav-link" href="<?php echo base_url('refund-policy');?>">Refund Policy</a></li>
							</ul>
						</li>
						<li class="nav-item"><a class="nav-link" href="<?php echo base_url('home');?>">Contact</a></li>
					</ul>
				</div>	
			</div>
		</nav>
	</div>
</header>

<section class="banner_area">
	<div class="banner_inner d-flex align-items-center">
		<div class="overlay bg-parallax" data-stellar-ratio="0.9" data-stellar-vertical-offset="0" data-background=""></div>
		<div class="container">
			<div class="banner_content text-center">
				<h2>Privacy Policy</h2>
				<div class="page_link">
					<a href="<?php echo base_url('home');?>">Home</a>
					<a href="<?php echo base_url('privacy-policy');?>">Privacy Policy</a>
				</div>
			</div>
		</div>
	</div>
</section>
<section class="sample-text-area">
	<div class="container">
		<p class="sample-text"><?php echo $content['content']; ?>
		</p>
	</div>
</section>

