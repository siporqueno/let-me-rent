angular.module('tools').controller('profileController', function ($scope, $rootScope, $http,  $location) {
     const contextPath = 'http://localhost:8890/let-me-rent/api/v1/instruments/';
    //
    // $scope.loadRents = function () { // здесь логика подгрузки заказов, в которых арендатор наш юзер.
    //     $http({
    //         url: contextPath + 'НАШ ЭНД ПОИНТ',
    //         method: 'GET'
    //     }).then(function (response) {
    //         $scope.tools_in_rent = response.data;
    //     });
    // };

    // $scope.loadTools = function () {
    //     $http({
    //         url: contextPath + 'НАШ ЭНД ПОИНТ',
    //         method: 'GET'
    //     }).then(function (response) {
    //         $scope.tools= response.data;
    //     });
    // };
    //
    // $scope.loadMyProfile = function () { // запрос для загрузки данных о пользователе
    //     $http({
    //         url: contextPath + 'НАШ ЭНД ПОИНТ',
    //         method: 'GET'
    //     }).then(function (response) {
    //         $scope.userProfile = response.data;
    //         $rootScope.myUserIdFromProfile = response.data.id;
    //     });
    // };

    // $scope.changeTool = function (toolId) {
    //         $location.path('/edit-tool/' + toolId);
    //     }

    // $scope.deleteTool = function (toolId) {
    //         //здесь логика по удалению инструмента из базы доступных для аренды: по идее, надо перемещать
                // в какую-то архивную таблицу, наверное. Или флаг ставить в основной таблице
    //     }

    $scope.navToToolHistory = function (toolId) {
        $location.path('/tool-history/' + toolId);
    }

    // $scope.loadMyProfile();
    // $scope.loadRents();
    // $scope.loadTools();
});