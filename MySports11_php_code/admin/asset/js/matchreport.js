app.controller('PageController', function ($scope, $http,$timeout,$rootScope){
    $scope.data.pageSize = 15;
    $rootScope.Status = 5;
    $scope.matchList=[];
    $scope.MatchGUID;
    $scope.getMatchList = function () {
        var data = 'SessionKey='+SessionKey+ '&MatchTypeByApi=Real&OrderBy=' + $scope.data.OrderBy + 
        '&Sequence=' + $scope.data.Sequence +'&OrderByToday=Yes&Params='+
        'SeriesGUID,SeriesName,MatchType,MatchNo,MatchStartDateTime,TeamNameLocal,TeamNameVisitor,TeamNameShortLocal,TeamNameShortVisitor,TeamFlagLocal,TeamFlagVisitor,MatchLocation,Status&PageNo=&StatusID='+$rootScope.Status;
        $http.post(API_URL+'admin/matches/getMatches', data, contentType).then(function(response) {
            var response = response.data;
            if(response.ResponseCode==200 && response.Data.Records){ /* success case */
                $scope.matchList=response.Data.Records;
            }
            $timeout(function () {
                $("select.chosen-select").chosen({width: '100%', "disable_search_threshold": 8}).trigger("chosen:updated");
            }, 300);       
        });
    }

    /*----------------*/
    $scope.getFilterData = function (){
        var StatusID = 5;
        var data = 'SessionKey='+SessionKey+ '&Params=SeriesName,StatusID,SeriesGUID,Status&'+$('#filterPanel form').serialize();
        $http.post(API_URL+'admin/matches/getFilterData', data, contentType).then(function(response) {
            var response = response.data;
            if(response.ResponseCode==200 && response.Data){ 
                /* success case */
             $scope.filterData =  response.Data;
             $timeout(function(){
                $("select.chosen-select").chosen({ width: '100%',"disable_search_threshold": 8}).trigger("chosen:updated");
            }, 300);          
         }
     });
    }

    // $scope.applyOrderedList = function(OrderBy, Sequence) {
    //     PSequence = $scope.data.Sequence;
    //     $scope.data = angular.copy($scope.orig); /*copy and reset from original scope*/

    //     $scope.data.OrderBy = OrderBy;
    //     if (PSequence == '' || PSequence == 'ASC' || typeof PSequence == 'undefined') {
    //         $scope.data.Sequence = 'DESC';
    //     } else {
    //         $scope.data.Sequence = 'ASC';
    //     }

    //     $scope.getList();
    // }

    $scope.getTeamData = function (SeriesGUID, StatusID, LocalTeamGUID='') {
        var data = 'SessionKey=' + SessionKey + '&LocalTeamGUID='+LocalTeamGUID+'&SeriesGUID='+ SeriesGUID+'&StatusID='+ StatusID +'&Params=TeamNameLocal,TeamNameVisitor,TeamGUID,StatusID&' + $('#filterPanel form').serialize();
        $http.post(API_URL + 'admin/matches/getTeamData', data, contentType).then(function (response) {
            var response = response.data;
            if (response.ResponseCode == 200 && response.Data) {
                /* success case */
                if (LocalTeamGUID != '') {
                    $scope.VisitorTeamData = response.Data;
                }else{
                    $scope.LocalTeamData = response.Data;
                }
                $timeout(function () {
                    $("select.chosen-select").chosen({width: '100%', "disable_search_threshold": 8}).trigger("chosen:updated");
                }, 300);
            }
        });
        $scope.matchList.filter((obj) => SeriesGUID === obj.SeriesGUID);
    }

    $scope.updateMatchGUID =function(MatchGUID) {
        $scope.MatchGUID = MatchGUID;
    }

    /*list*/
    $scope.applyFilter = function (Status)
    {
        $rootScope.Status = Status;
        $scope.data = angular.copy($scope.orig); /*copy and reset from original scope*/
        $scope.getList();
    }

    /*list append*/
    $scope.getList = function (){
        if ($scope.data.listLoading || $scope.data.noRecords) return;
        $scope.data.listLoading = true;
        var data = 'SessionKey='+SessionKey+ '&MatchGUID='+ $scope.MatchGUID + '&Params=ContestType,IsConfirm,ContestSize,EntryFee,AdminPercent,EntryType,NoOfWinners,WinningAmount,TotalJoined,TotalAmountReceived,TotalWinningAmount,TotalCashBonusUsed';
        $http.post(API_URL+'admin/contest/completedContestReport', data, contentType)
        .then(function(response) {
            var response = response.data;
            if(response.ResponseCode==200 && response.Data.Records){ /* success case */
                $scope.data.totalRecords = response.Data.TotalRecords;
                for (var i in response.Data.Records) {
                    $scope.data.dataList.push(response.Data.Records[i]);
                }
                $scope.data.pageNo++;               
            }else{
                $scope.data.noRecords = true;
            }
            $scope.data.listLoading = false;
        });
    }

    /*Export List*/
    $scope.ExportExcel = function () {
        var uri = 'data:application/vnd.ms-excel;base64,',
                template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>',
                base64 = function (s) {
                    return window.btoa(unescape(encodeURIComponent(s)))
                },
                format = function (s, c) {
                    return s.replace(/{(\w+)}/g, function (m, p) {
                        return c[p];
                    })
                }
        var toExcel = document.getElementById("MatchReportList").innerHTML;
        var ctx = {
            worksheet: name || '',
            table: toExcel
        };
        var link = document.createElement("a");
        link.download = "Match-Report-List.xls";
        link.href = uri + base64(format(template, ctx))
        link.click();
    }
}); 
