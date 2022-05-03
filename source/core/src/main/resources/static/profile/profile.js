angular.module('tools').controller('profileController', function ($scope, $http, $location) {
    const contextPath = 'http://localhost:8890/let-me-rent/';

    $scope.loadRents = function () {
        $http({
            url: contextPath + 'api/v1/orders/' +$scope.userProfile.id, //TODO: если на бэке поменяется энд-поинт, надо будет изменить. Написала предложение по его смене в OrderController
            method: 'GET'
        }).then(function (response) {
            $scope.orders = response.data;
        });
    };

    // $scope.loadTools = function () {
    //     $http({
    //         url: contextPath + 'НАШ ЭНД ПОИНТ',
    //         method: 'GET'
    //     }).then(function (response) {
    //         $scope.tools= response.data;
    //     });
    // };

    $scope.loadMyProfile = function () {
        $http({
            url: contextPath + '/api/v1/users',
            method: 'GET'
        }).then(function (response) {
            $scope.userProfile = response.data;
        });
    };

    // $scope.changeTool = function (toolId) {
    //         $location.path('/edit-tool/' + toolId);
    //     }

    // $scope.deleteTool = function (toolId) {
    //         //здесь логика по удалению инструмента из базы доступных для аренды: по идее, надо перемещать
                // в какую-то архивную таблицу, наверное. Или флаг ставить в основной таблице
    //     }

    $scope.navToAuthPage = function () {
        $location.path('/authorisation');
    }

    $scope.navToFeedbackPage = function (toolId) {
        $location.path('/feedback-page/' + toolId);
    }

    $scope.loadMyProfile();
    $scope.loadRents();
    // $scope.loadTools();
});