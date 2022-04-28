angular.module('tools').controller('comparePageController', function ($scope, $http, $location,$routeParams, $localStorage) {
    const contextPath = 'http://localhost:8890/let-me-rent/api/v1/instruments';
//ТУТ ПОКА ЧЕРНОВЫЕ НАБРОСКИ КОДА ДЛ JS
    // $scope.loadToolsCompareList = function () {
    //     //видимо, здесь будет какой-то код, похожий на логику работу с корзиной
    //     $http({
    //         url: contextPath + 'api/v1/СПЕЦИАЛЬНЫЙ ЭНД-ПОИНТ/' + $localStorage.toolsForCompareId,
    //         method: 'GET'
    //     }).then(function (response) {
    //         $scope.toolsCompareList = response.data;
    //     });
    // };
    //
    // $scope.navToToolInfoPage = function (toolId) {
    //     $location.path('/tool-info/' + toolId);
    // }
    //
    // $scope.deleteItem = function (toolId) {
    //     //видимо, здесь будет какой-то код, похожий на логику работу с корзиной
    //     $http({
    //         url: contextPath + 'api/v1/СПЕЦИАЛЬНЫЙ ЭНД-ПОИНТ/' + $localStorage.toolsForCompareId + '/remove/' + toolId,
    //         method: 'GET'
    //     }).then(function (response) {
    //         $scope.loadToolsCompareList();
    //     });
    // };

    // $scope.sortByFeeIncrease = function () {
    //    // Может, можно вообще на фронте делать сортировку? Или каждый раз запришивать в бэка
    //      };
    //
    // $scope.sortByFeeDecrease = function () {
    //     // Может, можно вообще на фронте делать сортировку? Или каждый раз запришивать в бэка
    // };
    // $scope.sortByPriceIncrease = function () {
    //     // Может, можно вообще на фронте делать сортировку? Или каждый раз запришивать в бэка
    // };
    // $scope.sortByPriceDecrease = function () {
    //     // Может, можно вообще на фронте делать сортировку? Или каждый раз запришивать в бэка
    // };

    // $scope.loadToolsCompareList();

});