<header class="panel-heading">
    <h1 class="h4">Cron List</h1>
</header>
<div class="panel-body" ng-controller="PageController" ><!-- Body -->

    <!-- Top container -->
    <div class="clearfix mt-2 mb-2 d_flex">
        
        <div>
            <!-- <div class="float-right">
                <form id="filterForm" role="form" autocomplete="off" ng-submit="applyFilter()" class="ng-pristine ng-valid">
                    <input type="text" class="form-control" name="Keyword" placeholder="Search">
                </form>
            </div>
            <div class="float-right">
                <button class="btn btn-default btn-secondary btn-sm ng-scope" data-toggle="modal" data-target="#filter_model"><img src="asset/img/filter.svg"></button>&nbsp;
            </div>
            <div class="float-right">
                <button class="btn btn-default btn-secondary btn-sm ng-scope" ng-click="reloadPage()"><img src="asset/img/reset.svg"></button>&nbsp;
            </div>
            <div class="float-right">
                <button class="btn theme_btn btn-secondary btn-sm ng-scope" ng-click="ExportList()">Export</button>&nbsp;
            </div> -->
        </div>	
    </div>
    <!-- Top container/ -->



    <!-- Data table -->
    <div class="table-responsive block_pad_md"> 

        <!-- loading -->
        <!-- <p ng-if="data.listLoading" class="text-center data-loader"><img src="asset/img/loader.svg"></p> -->
        <form name="records_form" id="records_form">
            <!-- data table -->
            <table class="table table-striped table-hover all-table-scroll cronEventTable" >
                <!-- table heading -->
                <thead>
                    <tr>
                        <th>Cron Name</th>
                        <th>Click URL</th>
                    </tr>
                </thead>
                <!-- table body -->
                <tbody>
                    <tr scope="row">
                        <td>get Series Live</td> 
                        <td><a target="_blank" href="<?php echo API_URL?>utilities/getSeriesLive">Click</a></td> 
                    </tr>
                    <tr scope="row">
                        <td>get Matches Live</td> 
                        <td><a target="_blank" href="<?php echo API_URL?>utilities/getMatchesLive">Click</a></td> 
                    </tr>
                    <tr scope="row">
                        <td>get Players Live</td> 
                        <td><a target="_blank" href="<?php echo API_URL?>utilities/getPlayersLive">Click</a></td> 
                    </tr>
                    <tr scope="row">
                        <td>get Match Score Live</td> 
                        <td><a target="_blank" href="<?php echo API_URL?>utilities/getMatchScoreLive">Click</a></td> 
                    </tr>
                    <tr scope="row">
                        <td>get Joined Contest Player Points</td> 
                        <td><a target="_blank" href="<?php echo API_URL?>utilities/getJoinedContestPlayerPoints">Click</a></td> 
                    </tr>
                    <tr scope="row">
                        <td>get Live Playing11 Match Player</td> 
                        <td><a target="_blank" href="<?php echo API_URL?>utilities/getLivePlaying11MatchPlayer">Click</a></td> 
                    </tr>

                    <tr scope="row">
                        <td>get Matches Live Matric101</td> 
                        <td><a target="_blank" href="<?php echo API_URL?>utilities/getMatchesLiveMatric101">Click</a></td> 
                    </tr>

                    <tr scope="row">
                        <td>get Players Live Matric101</td> 
                        <td><a target="_blank" href="<?php echo API_URL?>utilities/getPlayersLiveMatric101">Click</a></td> 
                    </tr>
                    <tr scope="row">
                        <td>get Match Score Live Matric101</td> 
                        <td><a target="_blank" href="<?php echo API_URL?>utilities/getMatchScoreLiveMatric101">Click</a></td> 
                    </tr>
                    <tr scope="row">
                        <td>get Live Playing11 Match Player Matric101</td> 
                        <td><a target="_blank" href="<?php echo API_URL?>utilities/getLivePlaying11MatchPlayerMatric101">Click</a></td> 
                    </tr>

                    <tr scope="row">
                        <td>auto Cancel Contest</td> 
                        <td><a target="_blank" href="<?php echo API_URL?>utilities/autoCancelContest">Click</a></td> 
                    </tr>
                    <tr scope="row">
                        <td>set Contest Winners</td> 
                        <td><a target="_blank" href="<?php echo API_URL?>utilities/setContestWinners">Click</a></td> 
                    </tr>
                    <tr scope="row">
                        <td>refund Amount Cancel Contest</td> 
                        <td><a target="_blank" href="<?php echo API_URL?>utilities/refundAmountCancelContest">Click</a></td> 
                    </tr>
                    <tr scope="row">
                        <td>amount Distribute Contest Winner</td> 
                        <td><a target="_blank" href="<?php echo API_URL?>utilities/amountDistributeContestWinner">Click</a></td> 
                    </tr>
                    <tr scope="row">
                        <td>tranfer Joined Contest Data</td> 
                        <td><a target="_blank" href="<?php echo API_URL?>utilities/tranferJoinedContestData">Click</a></td> 
                    </tr>
                    <tr scope="row">
                        <td>send Mail Contest Winners</td> 
                        <td><a target="_blank" href="<?php echo API_URL?>utilities/sendMailContestWinners">Click</a></td> 
                    </tr>
                </tbody>
            </table>
        </form>
        <!-- no record -->
        <p class="no-records text-center" ng-if="data.noRecords">
            <span ng-if="data.dataList.length">No more records found.</span>
            <span ng-if="!data.dataList.length">No records found.</span>
        </p>
    </div>
    <!-- Data table/ -->

</div><!-- Body/ -->



