app.controller('PageController', function ($scope, $http, $timeout) {
    $scope.data.pageSize = 100;
    $scope.data.ParentCategoryGUID = ParentCategoryGUID;
    /*----------------*/
    $scope.getFilterData = function () {
        var data = 'SessionKey=' + SessionKey + '&ParentCategoryGUID=' + ParentCategoryGUID + '&' + $('#filterPanel form').serialize();
        $http.post(API_URL + 'admin/category/getFilterData', data, contentType).then(function (response) {
            var response = response.data;
            if (response.ResponseCode == 200 && response.Data) { /* success case */
                $scope.filterData = response.Data;
                $timeout(function () {
                    $("select.chosen-select").chosen({ width: '100%', "disable_search_threshold": 8 }).trigger("chosen:updated");
                }, 300);
            }
        });
    }

    /*list append*/
    $scope.getList = function () {
        if ($scope.data.listLoading || $scope.data.noRecords) return;
        $scope.data.listLoading = true;
        var data = 'SessionKey=' + SessionKey + '&PageNo=' + $scope.data.pageNo + '&PageSize=' + $scope.data.pageSize + '&Params=OfferType,OfferPercent,OfferDateTime,NoOfTeams,UserSelectionType,NoOfRandomUsers' + '&' + $('#filterForm').serialize();
        $http.post(API_URL + 'offers/getOffers', data, contentType).then(function (response) {
            var response = response.data;
            if (response.ResponseCode == 200 && response.Data.Records) { /* success case */
                $scope.data.totalRecords = response.Data.TotalRecords;
                for (var i in response.Data.Records) {
                    $scope.data.dataList.push(response.Data.Records[i]);
                }
                $scope.data.pageNo++;
            } else {
                $scope.data.noRecords = true;
            }
            $scope.data.listLoading = false;
            setTimeout(function () { tblsort(); }, 1000);
        });
    }


    /*load add form*/
    $scope.loadFormAdd = function (Position, CategoryGUID) {
        $scope.templateURLAdd = PATH_TEMPLATE + module + '/add_form.htm?' + Math.random();
        $('#add_model').modal({ show: true });
        $scope.getMatches();
        $timeout(function () {
            $(".chosen-select").chosen({ width: '100%', "disable_search_threshold": 8, "placeholder_text_multiple": "Please Select", }).trigger("chosen:updated");
        }, 200);
    }

    /*list append*/
    $scope.getMatches = function () {
        $scope.Status = 'Pending';
        var data = 'SessionKey=' + SessionKey + '&MatchTypeByApi=Real&Params=MatchNo,MatchStartDateTime,TeamNameLocal,TeamNameVisitor&OrderBy=MatchStartDateTime&Sequence=ASC&Status=' + $scope.Status;
        $http.post(API_URL + 'sports/getMatches', data, contentType).then(function (response) {
            var response = response.data;
            if (response.ResponseCode == 200 && response.Data.Records) { /* success case */
                $scope.data.totalRecords = response.Data.TotalRecords;
                $scope.data.matchesList = response.Data.Records;
                $timeout(function () {
                    $(".chosen-select").chosen({ width: '100%', "disable_search_threshold": 8, "placeholder_text_multiple": "Please Select", }).trigger("chosen:updated");
                }, 200);
            } else {
                $scope.data.noRecords = true;
            }

        });
    }
    /*list append*/
    $scope.getContests = function (MatchGUID, offer) {
        if (offer === 'Offer-2') {
            var entryType = 'Multiple';
        } else {
            var entryType = ''
        }
        $scope.Status = 'Pending';
        var data = 'SessionKey=' + SessionKey + '&EntryType=' + entryType + '&GUID=' + MatchGUID + '&Params=MatchGUID,IsVirtualUserJoined,IsConfirm,VirtualUserJoinedPercentage,GameType,GameTimeLive,AdminPercent,Privacy,IsPaid,WinningAmount,ContestSize,EntryFee,NoOfWinners,EntryType,TeamNameLocal,TeamNameVisitor,Status,CustomizeWinning,ContestType,MatchStartDateTime,TotalJoined,TotalAmountReceived,TotalWinningAmount,CashBonusContribution,UserJoinLimit&Privacy=No&OrderByList=Yes&' + "Status=" + $scope.Status;
        $http.post(API_URL + 'contest/getContests', data, contentType).then(function (response) {
            var response = response.data;
            if (response.ResponseCode == 200) { /* success case */
                $scope.data.totalRecords = response.Data.TotalRecords;
                $scope.data.contestsList = response.Data.Records;
                $timeout(function () {
                    $(".chosen-select").chosen({ width: '100%', "disable_search_threshold": 8 }).trigger("chosen:updated");
                }, 300);
            } else {
                $scope.data.noRecords = true;
            }
        });
    }

    $scope.getALLUsers = function (Keyword) {
        if (Keyword) {
            var Keyword = Keyword;
        } else {
            var Keyword = '';
        }
        var data = 'SessionKey=' + SessionKey + '&Keyword=' + Keyword + '&UserTypeID=2' + '&Status=Verified&IsAdmin=No&Params=UserTypeID,EmailForChange,FullName,Status,Email, PhoneNumber&' + $('#filterForm').serialize();
        $http.post(API_URL + 'admin/users', data, contentType).then(function (response) {
            var response = response.data;
            if (response.ResponseCode == 200) { /* success case */
                $scope.data.totalUsers = response.Data.TotalRecords;
                $scope.data.allUsersList = response.Data.Records;
                $timeout(function () {
                    $(".chosen-select").chosen({
                        width: '100%',
                        "disable_search_threshold": 8,
                        "placeholder_text_multiple": "Select User",
                    }).trigger("chosen:updated");
                }, 100);
            } else {
                $scope.data.noRecords = true;
            }
        });
    }

    $scope.Switch = 'AllUsers';
    $scope.SwitchCheck = function (Type) {
        if (Type === "SelectedUsers") {
            $scope.getALLUsers();
            $timeout(function () {
                $(".chosen-select").chosen({
                    width: '100%',
                    "disable_search_threshold": 8,
                    "placeholder_text_multiple": "Select User",
                }).trigger("chosen:updated");
            }, 50);
        }
    }
    $scope.getSelectedOfferType = function (OfferType) {
        $scope.OfferType = OfferType;
        $timeout(function () {
            $('#OfferDateTime').datetimepicker({
                format: 'Y-m-d H:i',
            });
            $(".chosen-select").chosen({ width: '100%', "disable_search_threshold": 8, "placeholder_text_multiple": "Please Select", }).trigger("chosen:updated");
        }, 200);
    }

    /*load edit form*/
    $scope.loadFormEdit = function (Position, ID) {
        $scope.data.Position = Position;
        $scope.templateURLEdit = PATH_TEMPLATE + module + '/edit_form.htm?' + Math.random();
        $scope.data.pageLoading = true;
        $http.post(API_URL + 'admin/subscription', 'SessionKey=' + SessionKey + '&ID=' + ID, contentType).then(function (response) {
            var response = response.data;
            if (response.ResponseCode == 200) { /* success case */
                $scope.data.pageLoading = false;
                $scope.formData = response.Data.Records;
                $('#edit_model').modal({ show: true });
                $timeout(function () {
                    $(".chosen-select").chosen({ width: '100%', "disable_search_threshold": 8, "placeholder_text_multiple": "Please Select", }).trigger("chosen:updated");
                }, 200);
            }
        });
    }

    /*load delete form*/
    $scope.loadFormDelete = function (Position, ID) {
        $scope.data.Position = Position;
        $scope.templateURLDelete = PATH_TEMPLATE + module + '/delete_form.htm?' + Math.random();
        $scope.data.pageLoading = true;
        $http.post(API_URL + 'admin/subscription', 'SessionKey=' + SessionKey + '&ID=' + ID, contentType).then(function (response) {
            //$http.post(API_URL+'category/getCategory', 'SessionKey='+SessionKey+'&CategoryGUID='+CategoryGUID, contentType).then(function(response) {
            var response = response.data;
            if (response.ResponseCode == 200) { /* success case */
                $scope.data.pageLoading = false;
                $scope.formData = response.Data.Records
                $('#delete_model').modal({ show: true });
                $timeout(function () {
                    $(".chosen-select").chosen({ width: '100%', "disable_search_threshold": 8, "placeholder_text_multiple": "Please Select", }).trigger("chosen:updated");
                }, 200);
            }
        });
    }

    /*add data*/
    $scope.addData = function () {

        var data = 'SessionKey=' + SessionKey + '&' + $("form[name='add_form']").serialize();
        $http.post(API_URL + 'admin/offers/add', data, contentType).then(function (response) {
            var response = response.data;
            if (response.ResponseCode == 200) { /* success case */
                alertify.success(response.Message);
                $('.modal-header .close').click();
            } else {
                alertify.error(response.Message);
            }
        });
    }


    /*edit data*/
    $scope.editData = function () {
        $scope.editDataLoading = true;
        var data = 'SessionKey=' + SessionKey + '&' + $("form[name='edit_form']").serialize();
        $http.post(API_URL + 'admin/subscription/edit', data, contentType).then(function (response) {
            var response = response.data;
            if (response.ResponseCode == 200) { /* success case */
                alertify.success(response.Message);
                $scope.data.dataList[$scope.data.Position] = response.Data;
                $('.modal-header .close').click();
                location.reload();
            } else {
                alertify.error(response.Message);
            }
            $scope.editDataLoading = false;
        });
    }

    /*edit data*/
    $scope.delete = function (ID) {

        $scope.editDataLoading = true;
        var data = 'SessionKey=' + SessionKey + '&ID=' + ID + '&' + $("form[name='edit_form']").serialize();
        $http.post(API_URL + 'admin/subscription/delete', data, contentType).then(function (response) {
            var response = response.data;
            if (response.ResponseCode == 200) { /* success case */
                alertify.success(response.Message);
                // $scope.data.dataList[$scope.data.Position] = response.Data;
                $('.modal-header .close').click();
                location.reload();
            } else {
                alertify.error(response.Message);
            }
            $scope.editDataLoading = false;
        });
    }


});




/* sortable - starts */
function tblsort() {

    var fixHelper = function (e, ui) {
        ui.children().each(function () {
            $(this).width($(this).width());
        });
        return ui;
    }

    $(".table-sortable tbody").sortable({
        placeholder: 'tr_placeholder',
        helper: fixHelper,
        cursor: "move",
        tolerance: 'pointer',
        axis: 'y',
        dropOnEmpty: false,
        update: function (event, ui) {
            sendOrderToServer();
        }
    }).disableSelection();
    $(".table-sortable thead").disableSelection();


    function sendOrderToServer() {
        var order = 'SessionKey=' + SessionKey + '&' + $("#tabledivbody").sortable("serialize");
        $.ajax({
            type: "POST", dataType: "json", url: API_URL + 'admin/entity/setOrder',
            data: order,
            stop: function (response) {
                if (response.status == "success") {
                    window.location.href = window.location.href;
                } else {
                    alert('Some error occurred');
                }
            }
        });
    }

}


/* sortable - ends */