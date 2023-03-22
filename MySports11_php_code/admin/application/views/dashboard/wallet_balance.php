<header class="panel-heading">
    <h1 class="h4">Balance Details</h1>
</header>
<div class="panel-body" ng-controller="PageController" ng-init="getWalletBalnaceList()"><!-- Body -->
    <div class="">
        <div class="wrapper wrapper-content">
            <div class="row mb-4 align-items-stretch">
                <div class="col-xl-4 col-sm-6 py-2">
                    <div class="card">
                        <div class="card-body custom-card-body">
                            <div class="rotate col-3">
                                <i class="fa fa-rupee font_icon"></i>
                            </div>
                            <div class="card-info col-9">
                                <h6>Total Winning Amount Available </h6>
                                <h4>{{data.dataList.TotalAvailableWinningAmount| number : 2 }}</h4>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xl-4 col-sm-6 py-2">
                    <div class="card">
                        <div class="card-body custom-card-body">
                            <div class="rotate col-3">
                                <i class="fa fa-rupee font_icon"></i>
                            </div>
                            <div class="card-info col-9">
                                <h6>Total Deposit Wallet Balance Available</h6>
                                <h4>{{data.dataList.TotalAvailableWalletAmount| number : 2 }}</h4>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xl-4 col-sm-6 py-2">
                    <div class="card">
                        <div class="card-body custom-card-body">
                            <div class="rotate col-3">
                                <i class="fa fa-rupee font_icon"></i>
                            </div>
                            <div class="card-info col-9">
                                <h6>Total Cash Bonus Available</h6>
                                <h4>{{data.dataList.TotalAvailableCashBonus| number : 2 }}</h4>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


</div><!-- Body/ -->

