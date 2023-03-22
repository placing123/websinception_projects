<header class="panel-heading">
    <h1 class="h4"><?php echo $this->ModuleData['ModuleTitle']; ?></h1>
</header>
<div class="panel-body" ng-controller="PageController"><!-- Body -->

    <!-- Top container -->
    <div class="clearfix mt-2 mb-2">
        <span class="float-left records d-none d-sm-block">
            <span ng-if="data.dataList.length" class="h5">Total records: {{data.totalRecords}}</span>
        </span>
        <!-- <div class="float-right">
            <form id="filterForm" role="form" autocomplete="off" ng-submit="applyFilter()" class="ng-pristine ng-valid">
                <input type="text" class="form-control ml-1" name="Keyword" placeholder="Search">
            </form>
        </div> -->
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
		<table id="InvoiceReportList" class="table table-striped table-condensed table-hover table-sortable">
			<!-- table heading -->
			<thead>
				<tr>
                    <th>Invoice Number</th>
                    <th>Billed to</th>
                    <th>Contest ID</th>
                    <th class="text-center">Taxable value</th>
                    <th class="text-center">SGST</th>
                    <th class="text-center">CGST</th>
                    <th class="text-center">Invoice Total</th>
                    <th class="text-center">Invoice Date</th>
				</tr>
			</thead>
			<!-- table body -->
			<tbody id="tabledivbody" ng-if="data.dataList.length">
				<tr scope="row" ng-repeat="(key, row) in data.dataList" id="sectionsid_{{row.InvoiceID}}">
                    
                    <td>
                        <p><strong>{{row.InvoiceNumber}}</strong></p>
                    </td>
                    <td>
                        <p>{{row.BilledTo}}</p>
                    </td>
                    <td>
                        <p>{{row.ContestGUID}}</p>
                    </td>
                    <td>
                        <p class="text-center">{{row.TaxableValue}}</p>
                    </td>
                    <td>
                        <p class="text-center">{{row.SGST}}</p>
                    </td>
                    <td class="text-center">
                        <p>{{row.CGST}}</p>
                    </td>
                    <td class="text-center">
                        <p>{{row.InvoiceTotal}}</p>
                    </td>
                    <td class="text-center">
                        <p>{{row.CreatedAt}}</p>
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
     <div class="modal fade" id="filter_model"  ng-init="getList();">
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
                                        <label class="filter-col">From Date</label> 
                                        <input type="date" name="CreatedAt" class="form-control"> 
                                    </div>
                                </div>
                            </div>
                            <!-- <input type="hidden" name="CreatedAt" ng-model="CreatedAt"> -->
                        </div> <!-- form-area /-->
                    </div> <!-- modal-body /-->

                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary btn-sm" onclick="$('#filterForm1').trigger('reset'); $('.chosen-select').trigger('chosen:updated');">Reset</button>
                        <button type="submit" class="btn btn-success btn-sm" data-dismiss="modal" ng-disabled="editDataLoading" ng-click="applyFilter()">Apply</button>
                    </div>

                </form>
                <!-- Filter form/ -->
            </div>
        </div>
    </div>

</div>