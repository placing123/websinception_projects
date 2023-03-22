<header class="panel-heading">
    <h1 class="h4"><?php echo $this->ModuleData['ModuleTitle']; ?></h1>
</header>
<div class="panel-body" ng-controller="PageController"><!-- Body -->

    <!-- Top container -->
    <div class="clearfix mt-2 mb-2">
        <span class="float-left records d-none d-sm-block">
            <span ng-if="data.dataList.length" class="h5">Total records: {{data.totalRecords}}</span>
        </span>
        <div class="float-right">
            <form id="filterForm" role="form" autocomplete="off" ng-submit="applyFilter()" class="ng-pristine ng-valid">
                <input type="text" class="form-control ml-1" name="Keyword" placeholder="Search">
            </form>
        </div>
        <div class="float-right">
            <button class="btn btn-default btn-secondary btn-sm ng-scope" data-toggle="modal" data-target="#filter_model"><img src="asset/img/filter.svg"></button>&nbsp;
        </div>
        <div class="float-right">
            <button class="btn btn-default btn-secondary btn-sm ng-scope" ng-click="reloadPage()"><img src="asset/img/reset.svg"></button>&nbsp;
        </div>
        <div class="float-right" ng-if="data.dataList.length">
            <button class="btn theme_btn btn-secondary btn-sm ng-scope" ng-click="ExportExcel()">Excel</button>&nbsp;
        </div>
    </div>
    <!-- Top container/ -->
    <!-- Data table -->
	<div class="table-responsive block_pad_md" > 
		<!-- loading -->
		<p ng-if="data.listLoading" class="text-center data-loader"><img src="asset/img/loader.svg"></p>

		<!-- data table -->
		<table id="MatchReportList" class="table table-striped table-condensed table-hover table-sortable">
			<!-- table heading -->
			<thead>
				<tr>
                    <th>Contest Name</th>
                    <th>Type</th>
                    <!-- <th style="width: 70px;">Private/Public</th> -->
                    <th>Size</th>
                    <th class="text-center">Fee</th>
                    <th class="text-center">Admin Percentage</th>
                    <th class="text-center">Entry Type</th>
                    <th class="text-center"># Winners</th>
                    <th class="text-center">Winning Amount</th>
                    <th class="text-center">Total Joined</th>
                    <th class="text-center">Amount Received</th>
                    <th class="text-center">Winning Distributed</th>
                    <th class="text-center">Cash Bonus Used</th>
				</tr>
			</thead>
			<!-- table body -->
			<tbody id="tabledivbody" ng-if="data.dataList.length">
				<tr scope="row" ng-repeat="(key, row) in data.dataList" id="sectionsid_{{row.MenuOrder}}.{{row.CategoryID}}">
                    <td>
                        <div class="content float-left user_table"><strong><a href="javascript:void(0)" ng-click="loadContestJoinedUser(key, row.ContestGUID, row.MatchGUID)">{{row.ContestName}}</a></strong>
                            <div ng-if="row.TeamNameLocal" class="user_table">({{row.TeamNameLocal}} v/s {{row.TeamNameVisitor}})</div><div ng-if="!row.TeamNameLocal">-</div>
                        </div>
                    </td>
                    <td>
                        <p>{{!row.ContestType ? '-' : row.ContestType }}</p>
                    </td>
                    <td>
                        <p>{{row.ContestSize}}</p>
                    </td>
                    <td>
                        <p>{{row.EntryFee}}</p>
                    </td>
                    <td>
                        <p align="center">{{row.AdminPercent}}%</p>
                    </td>
                    <td>
                        <p>{{row.EntryType}}</p>
                    </td>
                    <td>
                        <p class="text-center">{{row.NoOfWinners}}</p>
                    </td>
                    <td>
                        <p class="text-center">{{row.WinningAmount}}</p>
                    </td>
                    <td class="text-center">
                        <p>{{row.TotalJoined}}</p>
                    </td>
                    <td class="text-center">
                        <p>{{row.TotalAmountReceived}}</p>
                    </td>
                    <td class="text-center">
                        <p>{{row.TotalWinningAmount}}</p>
                    </td> 
                    <td class="text-center">
                        <p>{{row.TotalCashBonusUsed}}</p>
                    </td> 
				</tr>
			</tbody>
		</table>

		<!-- no record -->
		<p class="no-records text-center" ng-if="data.noRecords">
			<span ng-if="data.dataList.length">No more records found.</span>
			<span ng-if="!data.dataList.length">No records found.</span>
		</p>
	</div>
	<!-- Data table/ -->

     <!-- Filter Modal -->
     <div class="modal fade" id="filter_model"  ng-init="getMatchList();getFilterData()">
        <div class="modal-dialog modal-md" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title h5">Filters</h3>     	
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                </div>

                <!-- Filter form -->
                <form id="filterForm1" role="form" autocomplete="off" class="ng-pristine ng-valid">
                    <div class="modal-body">
                        <div class="form-area">

                            <div class="row">
                                <div class="col-md-8">
                                    <div class="form-group">
                                        <label class="filter-col" for="CategoryTypeName">Series</label>
                                        <select id="SeriesGUID" name="SeriesGUID" class="form-control chosen-select" ng-model="SeriesGUID" ng-change="getTeamData(SeriesGUID, 5)">
                                            <option value="">Please Select</option>
                                            <option ng-repeat="row in filterData.SeiresData" value="{{row.SeriesGUID}}">{{row.SeriesName}}</option>
                                        </select>   
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-8">
                                    <div class="form-group">
                                        <label class="filter-col">Local Team</label>
                                        <select name="LocalTeamGUID" class="form-control chosen-select" ng-model="LocalTeamGUID" ng-change="getTeamData(SeriesGUID, LocalTeamGUID, 5)">
                                            <option value="">Please Select</option>
                                            <option ng-repeat="row in LocalTeamData.TeamData" value="{{row.TeamGUID}}">{{row.TeamName}}</option>
                                        </select>   
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-8">
                                    <div class="form-group">
                                        <label class="filter-col">Visitor Team</label>
                                        <select name="VisitorTeamGUID" class="form-control chosen-select">
                                            <option value="">Please Select</option>
                                            <option ng-repeat="row in VisitorTeamData.TeamData" value="{{row.TeamGUID}}">{{row.TeamName}}</option>
                                        </select>   
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-8">
                                    <div class="form-group">
                                        <label class="filter-col" for="Status">Matches</label>
                                        <select id="MatchGUID" name="MatchGUID" class="form-control chosen-select" ng-model="MatchGUID" ng-change="getTeamData(MatchGUID)">
                                            <option value="">Please Select</option>
                                            <option ng-repeat="row in matchList" value="{{row.MatchGUID}}">{{row.TeamNameLocal}} Vs {{row.TeamNameVisitor}} ON {{row.MatchStartDateTime}}</option>
                                        </select>
                                    </div>
                                </div>
                                <input type="hidden" name="Status" ng-model="Status">
                            </div>

                        </div> <!-- form-area /-->
                    </div> <!-- modal-body /-->

                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary btn-sm" onclick="$('#filterForm1').trigger('reset'); $('.chosen-select').trigger('chosen:updated');">Reset</button>
                        <button type="submit" class="btn btn-success btn-sm" data-dismiss="modal" ng-disabled="editDataLoading" ng-click="applyFilter(Status)">Apply</button>
                    </div>

                </form>
                <!-- Filter form/ -->
            </div>
        </div>
    </div>

</div>