angular.module('tools').controller('profileController', function ($scope, $http, $location) {
    const contextPath = 'http://localhost:8890/let-me-rent/';

    // $scope.loadRents = function () {
    //     $http({
    //         url: contextPath + 'api/v1/orders/' +$scope.userProfile.id, //TODO: если на бэке поменяется энд-поинт, надо будет изменить. Написала предложение по его смене в OrderController
    //         method: 'GET'
    //     }).then(function (response) {
    //         $scope.orders = response.data;
    //     });
    // };

    $scope.loadMyTools = function () {
        $http({
            url: contextPath + 'api/v1/instruments/lk',
            method: 'GET'
        }).then(function (response) {
            $scope.tools= response.data;
        });
    };

    $scope.loadMyProfile = function () {
        $http({
            url: contextPath + 'api/v1/users/myUserInfo',
            method: 'GET'
        }).then(function (response) {
            $scope.userProfile = response.data;
        });
    };

    $scope.changeTool = function (toolId) {
            $location.path('/edit-tool/' + toolId);
        }

    $scope.deleteTool = function (toolId) {
        $http({
            url: contextPath + 'api/v1/instruments/' + toolId,
            method: 'DELETE'
        }).then(function (response) {
            alert("Инструмент успешно удален из базы");
        });
    };

    $scope.navToAuthPage = function () {
        $location.path('/authorisation');
    }

    $scope.navToFeedbackPage = function (toolId) {
        $location.path('/feedback-page/' + toolId);
    }

    $scope.loadMyProfile();
    // $scope.loadRents();
    $scope.loadMyTools();
});