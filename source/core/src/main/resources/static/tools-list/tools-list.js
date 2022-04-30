angular.module('tools').controller('toolsListController', function ($scope, $http, $location, $routeParams, $localStorage) {
    const contextPath = 'http://localhost:8890/let-me-rent/api/v1/instruments';
    let currentPageIndex = 1;

    $scope.loadTools = function (pageIndex = 0, sorting = null) {
        currentPageIndex = pageIndex;
        $http({
            url: contextPath,
            method: 'GET',
            params: {
                page: pageIndex,
                sort: sorting,
                title: $scope.filter ? $scope.filter.title : null,
                categoryName: $scope.filter ? $scope.filter.categoryName : null,
                ownerUserName: $scope.filter ? $scope.filter.ownerUserName : null,
                max_fee: $scope.filter ? $scope.filter.max_fee : null,
                startDate: $scope.filter ? $scope.filter.startDate : null,
                finishDate: $scope.filter ? $scope.filter.finishDate : null
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
        }).datepicker("setDate", new Date());
    });

    Date.prototype.addDays = function (days) {
        this.setDate(this.getDate() + days);
        return this;
    };

    $(document).ready(function () {
        var currentDate = new Date();
        var myDate = currentDate.addDays(28);
        $('.datepickerEnd').datepicker({
            format: 'dd-mm-yyyy'
        }).datepicker("setDate", myDate);
    });

    $scope.navToToolInfoPage = function (toolId) {
        $location.path('/tool-info/' + toolId);
    }


    $scope.tryToRent = function (toolId) {
        //    ТУТ ЕСЛИ НЕ АВТОРИЗОВАН, ТО АЛЕРТ, ЧТО НАДО АВТОРИЗОВАТЬСЯ СНАЧАЛА, И ПЕРЕБРОСКА НА
        // СТРАНИЦУ АВТОРИЗАЦИИ
        // $location.path('/authorisation');
        // А после авторизации/если авторизован, то уже формируем запрос на аренду на отдельной странице:
        $location.path('/rent-request-page/' + toolId);
    }

    $scope.putIntoCart = function (toolId) {
        // доработать запрос по энд-поинтам после добавления логики на бэке по Redis и авторизации
        if (!$scope.filter.startDate || !$scope.filter.endDate) {
            alert("Введите даты начала и окончания аренды");
            return
        }
        $http({
            url: contextPath + 'api/v1/cart/' + $localStorage.webMarketGuestCartId + '/add/'
                + toolId + '/' + $scope.filter.startDate + '/' + $scope.filter.endDate,
            method: 'GET'
        }).then(function (response) {
            // $location.path('/cart/'); //опционально. Я бы не стала здесь сразу перебрасывать в корзину.
        });
    }

    $scope.loadTools();
});