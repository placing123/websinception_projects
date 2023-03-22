<div class="panel-body" ng-controller="PageController" ><!-- Body -->

    <!-- Top container -->
    <div class="clearfix mt-2 mb-2">
    </div>
    <!-- Top container/ -->
    <!-- Data table -->
    <div class="table-responsive block_pad_md"> 

        <!-- loading -->

        <!-- data table -->
            <table class="table">
                <!-- table heading -->
                <thead>
                    <tr>
                        <th style="whrefth: 500px;">Cron Name</th>
                        <th style="whrefth: 200px;">Action</th>
                    </tr>

                <!-- table body -->
                <tbody>
                    <tr scope="row">
                        <td class="listed sm clearfix">Cricket: Get & Updates All Upcoming Matches Metric 100 & 100B</td>
                        <td>
                             <a target="_blank" href="<?php echo API_URL; ?>utilities/getMatchesLive"  class="btn btn-success">Click</a>
                        </td>
                    </tr>
                     <tr scope="row">

                        <td class="listed sm clearfix">Cricket: Get & Updates All Upcoming Players Metric 100  & 100B</td>
                        <td>
                              <a target="_blank" href="<?php echo API_URL; ?>utilities/getPlayersLive" class="btn btn-success">Click</a>
                        </td>
                     </tr>
                    <tr scope="row">
                        <td class="listed sm clearfix">Cricket: Get & Updates All Upcoming Series Metric 100</td>
                        <td>
                               <a target="_blank" href="<?php echo API_URL; ?>utilities/getSeriesLive" class="btn btn-success" >Click</a>
                        </td>
                    </tr>

                    <tr scope="row">
                        <td class="listed sm clearfix">Cricket: Get & Updates All Upcoming Matches Metric 101</td>
                        <td>
                            <a target="_blank" href="<?php echo API_URL; ?>utilities/getMatchesLiveMatric101" class="btn btn-success">Click</a>
                        </td>
                    </tr>
                     <tr scope="row">

                        <td class="listed sm clearfix">Cricket: Get & Updates All Upcoming Players Metric 101</td>
                        <td>
                            <a target="_blank" href="<?php echo API_URL; ?>utilities/getPlayersLiveMatric101" class="btn btn-success">Click</a>
                        </td>
                     </tr>

                            <tr scope="row">

                        <td class="listed sm clearfix">Cricket: Get Playing11 players in Metric 101</td>
                        <td>
                            <a target="_blank" href="<?php echo API_URL; ?>utilities/getLivePlaying11MatchPlayerMatric101" class="btn btn-success">Click</a>
                        </td>
                     </tr>

                      <tr scope="row">

                        <td class="listed sm clearfix">Cricket: Get Playing11 players in Metric 100 & 100B</td>
                        <td>
                            <a target="_blank" href="<?php echo API_URL; ?>utilities/getLivePlaying11MatchPlayer" class="btn btn-success">Click</a>
                        </td>
                     </tr>

                    <!-- <tr scope="row">
                        <td class="listed sm clearfix">Football: Get & Updates All Upcoming Series</td>
                        <td>
                            <a target="_blank" href="<?php echo API_URL; ?>football/utilities/getSeriesLive" class="btn btn-success">Click</a>
                        </td>
                     </tr>

                    <tr scope="row">
                        <td class="listed sm clearfix">Football: Get & Updates All Upcoming Rounds</td>
                        <td>
                            <a target="_blank" href="<?php echo API_URL; ?>football/utilities/getRoundsLive" class="btn btn-success">Click</a>
                        </td>
                     </tr>

                     <tr scope="row">
                        <td class="listed sm clearfix">Football: Get & Updates All Upcoming Matches</td>
                        <td>
                            <a target="_blank" href="<?php echo API_URL; ?>football/utilities/getMatchesLive" class="btn btn-success">Click</a>
                        </td>
                     </tr>

                     <tr scope="row">
                        <td class="listed sm clearfix">Football: Get & Updates All Upcoming Players</td>
                        <td>
                            <a target="_blank" href="<?php echo API_URL; ?>football/utilities/getPlayersLive" class="btn btn-success">Click</a>
                        </td>
                     </tr>

                    <tr scope="row"> -->
                        <td class="listed sm clearfix">Football: Get & Updates All Upcoming Players Salary</td>
                        <td>
                            <a target="_blank" href="<?php echo API_URL; ?>football/utilities/getPlayersSalaryLive" class="btn btn-success">Click</a>
                        </td>
                     </tr>


                      <tr scope="row">

                        <td class="listed sm clearfix">Football: Get Playing11 players</td>
                        <td>
                            <a target="_blank" href="<?php echo API_URL; ?>football/utilities/getLivePlaying11MatchPlayer" class="btn btn-success">Click</a>
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



