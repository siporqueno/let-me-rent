angular.module('tools').controller('toolsListController', function ($scope, $http, $location, $routeParams, $localStorage) {
    const contextPath = 'http://localhost:8890/let-me-rent/';
    let currentPageIndex = 1;

    $scope.loadTools = function (pageIndex = 0) {
        currentPageIndex = pageIndex;
        $http({
            url: contextPath + "api/v1/instruments",
            method: 'GET',
            params: {
                page: pageIndex,
                size: $scope.size,
                sort: $scope.sort,
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
            format: 'dd-mm-yyyy'
        });
    });

    Date.prototype.addDays = function (days) {
        this.setDate(this.getDate() + days);
        return this;
    };

    $(document).ready(function () {
        $('.datepickerEnd').datepicker({
            format: 'dd-mm-yyyy'
        });
    });

    $scope.navToToolInfoPage = function (toolId) {
        $location.path('/tool-info/' + toolId);
    }


    $scope.putIntoCart = function (toolId) {
        if (typeof $scope.filter === 'undefined' || typeof $scope.filter.startDate === 'undefined' || typeof $scope.filter.endDate === 'undefined') {
            alert("Введите даты начала и окончания аренды");
            return
        }
        $http({
            url: contextPath + 'api/v1/carts/' + $localStorage.letMeRentGuestCartId + '/add/'
                + toolId + '/' + $scope.filter.startDate + '/' + $scope.filter.endDate,
            method: 'GET'
        }).then(function (response) {
        });
    }


    $scope.loadTools();
});