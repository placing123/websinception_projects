<div ng-controller="PageController" ng-init="getFilterData()">
    <div class="modal-body">
        <div class="form-area">

            <form id="add_form" name="add_form" autocomplete="off" >

                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="filter-col" for="ParentCategory">League Type</label>
                            <select id="IsPaid" ng-model="LeagueType" name="LeagueType" ng-change="chnageSeries(LeagueType)" class="form-control chosen-select">
                                <option value="">Please Select</option>
                                <option value="Draft">Snake Draft</option>
                                <option value="Auction">Auction Draft</option>

                            </select>
                        </div>
                    </div>

                    <!-- <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label">League Join Date Time </label>
                            <input name="LeagueJoinDateTime" id="LeagueJoinDateTime" readonly="" ng-model="LeagueJoinDateTime" type="text" class="form-control" value="">
                            <small>Set league join date time on which time user can create a team.</small>
                        </div>
                    </div> -->
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label">Contest Name</label>
                            <input name="ContestName" type="text" class="form-control" placeholder="Contest Name" value="" maxlength="40">
                        </div>
                    </div>

                    <!-- <div class="col-md-3" ng-if="LeagueType == 'Draft'">
                        <div class="form-group">
                            <label class="filter-col" for="ParentCategory">Series</label>
                            <select id="Series" name="SeriesGUID" ng-model="SeriesGUID" class="form-control chosen-select" ng-change="getMatchesSeries(SeriesGUID, 'Pending')">
                                <option value="">Please Select</option>
                                <option ng-repeat="Series in filterData.SeiresData" value="{{Series.SeriesGUID}}">{{Series.SeriesName}}</option>
                            </select>
                            <input type="hidden" name="MatchStartDate" value="" id="MatchStartDate">
                        </div>
                    </div>

                    <div class="col-md-3" ng-if="LeagueType == 'Auction'">
                        <div class="form-group">
                            <label class="filter-col" for="ParentCategory">Series</label>
                            <select id="Series" name="RoundID" ng-model="RoundID" class="form-control chosen-select" ng-change="getRoundPlayers(RoundID);getMatches(RoundID, 'Pending');">
                                <option value="">Please Select</option>
                                <option ng-repeat="Series in filterData.SeiresData" value="{{Series.RoundID}}">{{Series.SeriesName}}</option>
                            </select>
                            <input type="hidden" name="MatchStartDate" value="" id="MatchStartDate">
                        </div>
                    </div>

                    <div class="col-md-9" ng-if="LeagueType == 'Draft'">
                        <div class="form-group">
                            <label class="filter-col" for="ParentCategory">Match</label>
                            <select id="MatchGUID" name="MatchGUID[]" ng-model="MatchGUID" ng-change="getID(MatchGUID)" class="form-control chosen-select1" >
                                <option value="">Please Select</option>
                                <option ng-repeat="match in MatchDataSeries" value="{{match.MatchGUID}}">{{match.TeamNameLocal}} Vs {{match.TeamNameVisitor}} ON {{match.MatchStartDateTime}}</option>
                            </select>
                            <small>Select this option to select match according to selected series.</small>
                        </div>
                    </div> -->

                    <!-- <div class="col-md-3" ng-if="LeagueType == 'Draft'">
                        <div class="form-group">
                            <label class="filter-col"></label><br>
                            <a href="javascript:void(0)" class="btn btn-secondary btn-sm" id ="all_matches">Select All</a>
                            <a href="javascript:void(0)" class="btn btn-secondary btn-sm" id ="clear_all">Clear All</a>
                        </div>
                    </div> -->
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="filter-col" for="ParentCategory">Is Paid Contest?</label>
                            <select id="IsPaid" ng-model="IsPaid" name="IsPaid" class="form-control chosen-select">
                                <option value="">Please Select</option>
                                <option value="Yes">Yes</option>
                                <option value="No">No</option>

                            </select>
                            <small>Select this option notifiy that contest is free or paid.</small>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label">Winning Amount</label>
                            <input name="WinningAmount" ng-model="custom.WinningAmount" placeholder="Winning Amount" type="text" class="form-control numeric" value="0" ng-if="IsPaid == 'Yes'">
                            <input name="WinningAmount" ng-model="custom.WinningAmount" ng-init="custom.WinningAmount = '0'" placeholder="Winning Amount" type="text" class="form-control numeric" value="0" ng-if="IsPaid == 'No'" >
                        </div>
                    </div>
                    <div class="col-md-3" ng-if="IsPaid == 'Yes'">
                        <div class="form-group">
                            <label class="control-label">Admin Charges (%)</label>
                            <input name="AdminPercent" ng-model="custom.AdminPercent" placeholder="Admin Charges in Percentage"  ng-init="custom.AdminPercent = '10'" type="text" class="form-control numeric" maxlength="3" ng-if="IsPaid == 'Yes'">
                        </div>
                    </div>
                    <div class="col-md-3" ng-if="IsPaid == 'Yes'">
                        <div class="form-group">
                            <label class="control-label">Cash Bonus Contribution (%)</label>
                            <input name="CashBonusContribution" ng-model="CashBonusContribution" placeholder="Cash Bonus Controbution in Percentage" type="text" class="form-control numeric" value="0" maxlength="3" ng-if="IsPaid == 'Yes'">
                        </div>
                    </div>


                    <input type="hidden" ng-model="ContestFormat" name="ContestFormat" value="Head to Head">
                    <input type="hidden" ng-model="EntryType" name="EntryType" value="Single">
                    <input type="hidden" ng-model="ContestType" name="ContestType" value="Head to Head">
                    <!-- <input ng-if="ContestFormat == 'Head to Head'" type="hidden" name="ContestSize" value="2"> -->
                    <input type="hidden" ng-model="ShowJoinedContest" name="ShowJoinedContest" value="Yes">
                    <input type="hidden" ng-model="MinimumUserJoined" name="MinimumUserJoined" value="1">

                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="filter-col" for="ParentCategory">Contest Format</label>
                            <select id="ContestFormat" ng-model="ContestFormat" name="ContestFormat" class="form-control">
                                <option value="">Please Select</option>
                                                           
                                <option  ng-if="LeagueType == 'Auction'" value="League">League</option>
                                <option ng-if="LeagueType == 'Draft'" value="Head to Head">Head to Head</option>
                            </select>
                            <small></small>
                        </div>
                    </div>

                    <div class="col-md-3" ng-if="ContestFormat != 'Head to Head'">
                        <div class="form-group">
                            <label class="filter-col" for="ParentCategory">Contest Type</label>
                            <select id="ContestType" name="ContestType" ng-model="ContestType" class="form-control chosen-select">
                                <option value="">Please Select</option>
                                <option value="Hot">Hot</option>
                                <option value="Champion">Champion</option>
                                <option value="Practice">Practice</option>
                                <option value="More">More</option>
                                <option value="Mega">Mega</option>
                                <!-- <option value="Smart Pool">Smart Pool</option>
                                <option value="Infinity Pool">Infinity Pool</option> -->
                                <option value="Winner Takes All">Winner Takes All</option>
                                <option value="Only For Beginners">Only For Beginners</option>
                            </select>
                            <small>Select option to notify contest type.</small>
                        </div>
                    </div>

                    <div class="col-md-3" ng-if="IsPaid == 'Yes'" >
                        <div class="form-group">
                            <label class="control-label">Entry Fee</label>
                            <input name="EntryFee" ng-model="EntryFee" type="text" placeholder="0" class="form-control numeric" value="" maxlength="40" ng-if="IsPaid == 'Yes'" >
                            <input name="EntryFee" ng-model="EntryFee" type="text" ng-init="EntryFee = '0'" placeholder="0" class="form-control numeric" value="0" maxlength="40" ng-if="IsPaid == 'No'">
                        </div>
                    </div>

                    <div class="col-md-3" >
                        <div class="form-group">
                            <label class="control-label">Contest Size</label>
                            <input  ng-if="LeagueType == 'Draft'"name="ContestSize" ng-model="custom.ContestSize" type="numeric" readonly class="form-control integer" min="2" max="2">
                            <input ng-if="LeagueType == 'Auction'" name="ContestSize" ng-model="custom.ContestSize" type="numeric" class="form-control integer" min="4" max="8">
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="filter-col" for="ParentCategory">Confirm Contest</label>
                            <select id="IsConfirm" name="IsConfirm" ng-model="IsConfirm" class="form-control chosen-select">
                                <option value="">Please Select</option>
                                <option value="Yes">Yes</option>
                                <option value="No">No</option>
                            </select>
                            <small>Select option to notify contest is confirm contest or not.</small>
                        </div>
                    </div>
<!--                    <div class="col-md-3" ng-if="LeagueType == 'Draft'">
                        <div class="form-group">
                            <label class="control-label">Draft Team Player Limit</label>
                            <input name="DraftTeamPlayerLimit" id="DraftTeamPlayerLimit" ng-model="formData.DraftTeamPlayerLimit" type="text" class="form-control numeric">
                        </div>
                    </div>-->
                    <input name="DraftTeamPlayerLimit" id="DraftTeamPlayerLimit" ng-model="formData.DraftTeamPlayerLimit" type="hidden" class="form-control numeric" value="11">
                    <div class="col-md-12" ng-if="LeagueType == 'Draft'">
                        <div class="form-group">
                            <label class="control-label">Draft Player Selection Criteria</label>
                        </div>
                    </div>
                    <div class="col-md-3" ng-if="LeagueType == 'Draft'">
                        <div class="form-group">
                            <label class="control-label">WK</label>
                            <input name="DraftPlayerSelectionCriteria[]" type="text" class="form-control numeric" value="1" readonly="">
                            Total WK : {{filterDataRoundPlayers.WicketKeeper}}
                        </div>
                    </div>
                    <div class="col-md-3" ng-if="LeagueType == 'Draft'">
                        <div class="form-group">
                            <label class="control-label">BAT</label>
                            <input name="DraftPlayerSelectionCriteria[]" type="text" class="form-control numeric" value="4" readonly="">
                            Total BAT : {{filterDataRoundPlayers.Batsman}}
                        </div>
                    </div>
                    <div class="col-md-3" ng-if="LeagueType == 'Draft'">
                        <div class="form-group">
                            <label class="control-label">AR</label>
                            <input name="DraftPlayerSelectionCriteria[]" type="text" class="form-control numeric" value="2" readonly="">
                            Total AR : {{filterDataRoundPlayers.AllRounder}}
                        </div>
                    </div>
                    <div class="col-md-3" ng-if="LeagueType == 'Draft'">
                        <div class="form-group">
                            <label class="control-label">BOWL</label>
                            <input name="DraftPlayerSelectionCriteria[]" type="text" class="form-control numeric" value="4" readonly="">
                            Total BOWL : {{filterDataRoundPlayers.Bowler}}
                        </div>

                    </div>
<!--                    <div class="col-md-12 text-success" ng-if="LeagueType == 'Draft'">Total Players:{{parseInt(filterDataRoundPlayers.WicketKeeper) + parseInt(filterDataRoundPlayers.Batsman) + parseInt(filterDataRoundPlayers.AllRounder) + parseInt(filterDataRoundPlayers.Bowler)}}</div>       -->
                </div>

                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="filter-col" for="ParentCategory">Customize Winnings</label>
                            <!-- <input type="checkbox" ng-model="custom.winnings" ng-click="customizeWin()" > -->
                        </div>
                    </div>
                </div>
                <div class="row" >
                    <div class="col-md-10">
                        <div class="form-group">
                            <input type="text" class="form-control" ng-model="custom.NoOfWinners"  name="NoOfWinners" ng-change="changeWinners()" >
                        </div>
                    </div>
                    <div class="col-md-2">
                        <div class="form-group">
                            <a href="javascript:void(0)" class="btn btn-secondary btn-sm" ng-click="Showform()" >Set</a>
                        </div>
                    </div>
                    <div class="col-sm-12">
                        <div ng-show="showField" class="creatcontast_list">
                            <table style="width: 100%;">
                                <thead>
                                    <tr>
                                        <th>Rank</th>
                                        <th>Winning %</th>
                                        <th>Winning Amount</th>
                                        <th><button class="btn btn-submit" type="button" ng-click="addField()" >+</button></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr ng-repeat="r in custom.choices">
                                        <td>
                                            <table>
                                                <tr>
                                                    <td>
                                                        {{number}}

                                                        <label>From</label><select class="form-control" ng-model="r.From" ng-options="number for number in r.numbers" disabled="true"></select>
                                                    </td>
                                                    <td>
                                                        <label>To</label><select class="form-control"  ng-init="DataForm.To = r.numbers[0]" ng-change="changePercent($index)" ng-model="r.To" ng-options="number for number in r.numbers"></select>
                                                    </td>
                                                </tr>
                                            </table> 


                                        </td>
                                        <td>
                                            <label>Percent</label> <input type="text" ng-model="r.percent" name="percent" class="form-control" ng-change="changePercent($index)" valid-number>
                                        </td>
                                        <td><label>Amount</label> <input type="text" class="form-control" ng-model="r.amount"></td>
                                        <td><button type="button" class="btn btn-submit" ng-click="removeField($index)">-</button></td>
                                    </tr>

                                </tbody>
                            </table>
                        </div>
                        <div style="color:red" ng-show="percent_error">*Percent field is required</div>
                        <div style="color:red" ng-show="calculation_error">*{{calculation_error_msg}}</div>
                    </div>
                </div>

            </form>

        </div>
    </div>

    <div class="modal-footer">
        <!-- <button type="button" class="btn btn-secondary btn-sm" data-dismiss="modal">Cancel</button> -->
        <button type="submit" class="btn btn-success btn-sm" ng-disabled="addDataLoadingssss" ng-click="addData()">Save</button>
    </div>
</div>

