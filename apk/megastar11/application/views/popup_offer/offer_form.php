<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        Offer
      </h1>
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="#"><i class="fa fa-dashboard"></i> Dashboard</a></li>
        <li class="breadcrumb-item"><a href="#">offer</a></li>
        <li class="breadcrumb-item active">Create</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
          
	 <div class="box box-default">
   <div class="box-tools ">
                <a href="<?php echo site_url('popup_offer') ?>" class="btn btn-primary">Offer</a>
          </div>
        <!-- /.box-header -->
        <div class="box-body wizard-content">
        <h2 style="margin-top:0px">Offer  <?php echo $button ?></h2>
        <form action="<?php echo $action; ?>" enctype="multipart/form-data" method="post">
	    <div class="form-group">
            <label for="varchar">Name <?php echo form_error('title') ?></label>
            <input type="text" class="form-control" name="title" id="title" placeholder="Title" value="<?php echo $title; ?>" />
        </div>
        <div class="form-group">
             <label for="varchar">Image <?php echo form_error('image') ?></label>
            <input type="file" class="form-control" name="image" id="image" placeholder="Image" value="<?php echo $image; ?>" /> 
        </div>
	   
	    <input type="hidden" name="id" value="<?php echo $id; ?>" /> 
      <input type="hidden" name="old_image" value="<?php echo $old_image; ?>" /> 
	    <button type="submit" class="btn btn-primary"><?php echo $button ?></button> 
	    <a href="<?php echo site_url('popup_offer') ?>" class="btn btn-default">Cancel</a>
	</form>
</div>
        <!-- /.box-body -->
      </div>
      <!-- /.box -->
	  </section>
	  </div>