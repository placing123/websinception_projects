<%@ Page Language="C#" MasterPageFile="Customer.Master" AutoEventWireup="true" CodeFile="walet.aspx.cs" Inherits="Customer_walet" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
     <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

    <div class="m-grid__item m-grid__item--fluid m-wrapper" style="min-height: 600px">
        <!-- BEGIN: Subheader -->
        <div class="m-subheader ">
            <div class="d-flex align-items-center">
                <div class="mr-auto">
                    <h3 class="m-subheader__title ">Dashboard</h3>
                </div>
            </div>
        </div>
        <!-- END: Subheader -->
        <div class="m-content">
            <div class="m-portlet">
                <div class="m-portlet__body  m-portlet__body--no-padding">
                    <div class="row m-row--no-padding m-row--col-separator-xl">
                        <div class="col-md-12 col-lg-6 col-xl-3">
                            <a href="taskreport.aspx">
                                <!--begin::Total Profit-->
                                <div class="m-widget24">
                                    <div class="m-widget24__item">
                                        <h4 class="m-widget24__title">Completed Task</h4>
                                        <br>
                                        
                                        <span class="m-widget24__stats m--font-brand">
                                            <asp:Label ID="lblcompl" runat="server"></asp:Label>/<asp:Label ID="lbltotal" runat="server"></asp:Label>
                                        </span>
                                        <div class="m--space-10"></div>
                                        <div class="progress m-progress--sm">
                                            <div class="progress-bar m--bg-brand" role="progressbar" style="width: 70%;" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100"></div>
                                        </div>
                                        <span class="m-widget24__change"></span>
                                        <span class="m-widget24__number"></span>
                                    </div>
                                </div>
                            </a>
                            <!--end::Total Profit-->
                        </div>
                        <div class="col-md-12 col-lg-6 col-xl-3">
                            <!--begin::New Feedbacks-->
                            <div class="m-widget24">
                                <div class="m-widget24__item">
                                    <h4 class="m-widget24__title">Rate
												</h4>
                                    <br>
                                    
                                    <span class="m-widget24__stats m--font-info">
                                        <asp:Label ID="lblrate" runat="server"></asp:Label>
                                        <span class="m-widget24__stats m--font-danger">35₹
                                   </span>
                                            </span>
                                    <div class="m--space-10"></div>
                                    <div class="progress m-progress--sm">
                                        <div class="progress-bar m--bg-info" role="progressbar" style="width: 70%;" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100"></div>
                                    </div>
                                    <span class="m-widget24__change"></span>
                                    <span class="m-widget24__number"></span>
                                </div>
                            </div>
                            <!--end::New Feedbacks-->
                        </div>
                        <div class="col-md-12 col-lg-6 col-xl-3">
                            <!--begin::New Orders-->
                            <div class="m-widget24">
                                <div class="m-widget24__item">
                                    <h4 class="m-widget24__title">Balance</h4>
                                    <br>
                                   
                                    <span class="m-widget24__desc">Pending for Approval</span>
                                    <span class="m-widget24__stats m--font-success"></span>
                                    <span class="m-widget24__stats m--font-danger">₹
                                        <asp:Label ID="lblbalance" runat="server"></asp:Label>*</span>
                                    <div class="m--space-10"></div>
                                    <div class="progress m-progress--sm">
                                        <div class="progress-bar m--bg-danger" role="progressbar" style="width: 70%;" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100"></div>
                                    </div>
                                    <span class="m-widget24__change"></span>
                                    <span class="m-widget24__number"></span>
                                </div>
                            </div>
                            <!--end::New Orders-->
                        </div>
                        <div class="col-md-12 col-lg-6 col-xl-3">
                            <!--begin::New Users-->
                            <div class="m-widget24">
                                <div class="m-widget24__item">
                                    <h4 class="m-widget24__title">End Date
												</h4>
                                    <br>
                         
                                    <span class="m-widget24__stats m--font-success">
                                        <asp:Label ID="lbl_end_date" runat="server"></asp:Label>
                                    </span>
                                    <div class="m--space-10"></div>
                                    <div class="progress m-progress--sm">
                                        <div class="progress-bar m--bg-success" role="progressbar" style="width: 70%;" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100"></div>
                                    </div>
                                    <span class="m-widget24__change"></span>
                                    <span class="m-widget24__number"></span>
                                </div>
                            </div>
                            <!--end::New Users-->

                        </div>
  
                    </div>
                </div>
            </div>
            <center><p style="font-color:red; align:center"><b>TERMS AND CONDITION</b> <br/><br/>
			<div style="color:#DC143C"><b>Amount is Subjected to Accuracy Level.Minimum Accuracy Required is 90% for Payment</b></div></p></center>
          

</asp:Content>
         