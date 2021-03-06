angular.module('tools').controller('toolsListController', function ($scope, $http, $location, $routeParams, $localStorage) {
    const contextPath = 'http://localhost:8890/let-me-rent/';
    let currentPageIndex = 1;
    let isApplyFiltersButtonPressed = false;

    $scope.size_data = {
        availablePageSizeOptions: [
            {pageSize: '10'},
            {pageSize: '25'},
            {pageSize: '50'}
        ]
    };

    if ($localStorage.currentSize != null) {
        $scope.size_data.model = $localStorage.currentSize;
    } else $scope.size_data.model = '10';

    $scope.sort_data = {
        availableSortOptions: [
            {sort: '', title: 'Сортировка'},
            {sort: '', title: 'Без сортировки'},
            {sort: 'fee,asc', title: 'По возрастанию цены аренды'},
            {sort: 'fee,desc', title: 'По убыванию цены аренды'},
            {sort: 'price,asc', title: 'По возрастанию стоимости залога'},
            {sort: 'price,desc', title: 'По убыванию стоимости залога'}
        ]
    };

    if ($localStorage.currentSort != null) {
        $scope.sort_data.model = $localStorage.currentSort;
    } else $scope.sort_data.model = '';

    $scope.filterTools = function (pageIndex = 0) {
        isApplyFiltersButtonPressed = true;
        $scope.loadTools(pageIndex);

    }

    $scope.loadTools = function (pageIndex = 0) {
        currentPageIndex = pageIndex;
        if ($localStorage.currentFilters != null) {
            $scope.filter = $localStorage.currentFilters;
        }

        $http({
            url: contextPath + "api/v1/instruments",
            method: 'GET',
            params: {
                page: pageIndex,
                size: $scope.size_data.model,
                sort: $scope.sort_data.model,
                title: $scope.filter ? $scope.filter.title : null,
                categoryName: $scope.filter ? $scope.filter.categoryName : null,
                ownerName: $scope.filter ? $scope.filter.ownerUsername : null,
                maxFee: $scope.filter ? $scope.filter.maxFee : null,
                startDate: $scope.filter ? $scope.filter.startDate : null,
                endDate: $scope.filter ? $scope.filter.endDate : null
            }
        }).then(function (response) {
            $scope.toolsPage = response.data;
            $scope.paginationArray = $scope.generatePagesIndexes(1, $scope.toolsPage.totalPages);
            $localStorage.currentFilters = $scope.filter;
            $localStorage.currentSize = $scope.size_data.model;
            $localStorage.currentSort = $scope.sort_data.model;
        });
    };


    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
    }

    $(document).ready(function () {

        $('.datepickerStart').datepicker({
            format: 'dd-mm-yyyy',
            startDate: '0d',
            clearBtn: true,
            language: 'ru'
        }).on('change', function () {

            isApplyFiltersButtonPressed = false;
            let startDate = $('.datepickerStart').datepicker('getDate');
            let endDate = $('.datepickerEnd').datepicker('getDate');

            if (isDateValid(startDate) && isDateValid(endDate) && !isStartDateNotLaterThanEndDate(startDate, endDate)) {
                $(this).datepicker('update', '');
                alert("Дата начала должна быть не позже даты окончания");
                $(this).datepicker('show');
                return;
            }

            $(this).datepicker('hide');
        });

        $('.datepickerEnd').datepicker({
            format: 'dd-mm-yyyy',
            startDate: '0d',
            clearBtn: true,
            language: 'ru'
        }).on("change", function () {

            isApplyFiltersButtonPressed = false;
            let startDate = $('.datepickerStart').datepicker('getDate');
            let endDate = $('.datepickerEnd').datepicker('getDate');

            if (isDateValid(startDate) && isDateValid(endDate) && !isStartDateNotLaterThanEndDate(startDate, endDate)) {
                $(this).datepicker('update', '');
                alert("Дата окончания должна быть не раньше даты начала");
                $(this).datepicker('show');
                return;
            }

            $(this).datepicker('hide');
        });

    });

    let isDateValid = function (date) {

        if (date == null) {
            return false;
        }

        if (date === '') {
            return false;
        }

        return typeof date !== undefined;

    }

    let isStartDateNotLaterThanEndDate = function (startDate, endDate) {

        if (startDate.getFullYear() > endDate.getFullYear()) {
            return false;
        }

        if (startDate.getFullYear() < endDate.getFullYear()) {
            return true;
        } else {

            if (startDate.getMonth() > endDate.getMonth()) {
                return false;
            }

            if (startDate.getMonth() < endDate.getMonth()) {
                return true;
            } else return startDate.getDate() <= endDate.getDate();
        }

    }

    $scope.navToToolInfoPage = function (toolId) {
        $location.path('/tool-info/' + toolId);
    }


    // $scope.putIntoCart = function (toolId) {
    //     if (typeof $scope.filter === 'undefined' || typeof $scope.filter.startDate === 'undefined' || typeof $scope.filter.endDate === 'undefined') {
    //         alert("Введите даты начала и окончания аренды");
    //         return
    //     }
    //     $http({
    //         url: contextPath + 'api/v1/carts/' + $localStorage.letMeRentGuestCartId + '/add/'
    //             + toolId + '/' + $scope.filter.startDate + '/' + $scope.filter.endDate,
    //         method: 'GET'
    //     }).then(function (response) {
    //         var x = document.getElementById("snackbar");
    //         x.className = "show";
    //         setTimeout(function () {
    //             x.className = x.className.replace("show", "");
    //         }, 3000);
    //     });
    // }

    $scope.putIntoCart = function (toolId) {
        if (isApplyFiltersButtonPressed && typeof $scope.filter != 'undefined' &&
            $scope.filter.startDate != null && $scope.filter.endDate != null &&
            $scope.filter.startDate !== '' && $scope.filter.endDate !== '' &&
            typeof $scope.filter.startDate != 'undefined' && typeof $scope.filter.endDate != 'undefined') {
            $http({
                url: contextPath + 'api/v1/carts/' + $localStorage.letMeRentGuestCartId + '/add/'
                    + toolId + '/' + $scope.filter.startDate + '/' + $scope.filter.endDate,
                method: 'GET'
            }).then(function (response) {
                var x = document.getElementById("snackbar");
                x.className = "show";
                setTimeout(function () {
                    x.className = x.className.replace("show", "");
                }, 3000);
            });
        } else {
            alert("Для добавления в корзину необходимо ввести в полях фильтрации даты начала и окончания аренды и применить фильтрацию");
        }
    }


    $scope.loadTools();
});