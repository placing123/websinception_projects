app.controller('PageController', function ($scope, $http,$timeout,$rootScope){
    $scope.data.pageSize = 15;
    $rootScope.Status = 5;
    $scope.matchList=[];
    $scope.MatchGUID;
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

    /*list*/
    $scope.applyFilter = function ()
    {
        $rootScope.CreatedAt = '';
        $scope.data = angular.copy($scope.orig); /*copy and reset from original scope*/
        $scope.getList();
    }

    /*list append*/
    $rootScope.CreatedAt = 'Today';
    $scope.getList = function (){
        if ($scope.data.listLoading || $scope.data.noRecords) return;
        $scope.data.listLoading = true;
        var FilterDate =  ($rootScope.CreatedAt=='Today') ? 'CreatedAt='+$rootScope.CreatedAt : $('#filterForm1').serialize();
        var data = 'SessionKey='+SessionKey+'&'+FilterDate+'&OrderBy=InvoiceID&Sequence=DESC&';
        $http.post(API_URL+'admin/contest/dailyInvoiceReports', data, contentType)
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
        var toExcel = document.getElementById("InvoiceReportList").innerHTML;
        var ctx = {
            worksheet: name || '',
            table: toExcel
        };
        var link = document.createElement("a");
        link.download = "Invoice-Report-List.xls";
        link.href = uri + base64(format(template, ctx))
        link.click();
    }
}); 
