angular.module('tools').controller('toolHistoryController', function ($scope, $http, $rootScope, $routeParams, $location) {
    const contextPath = 'http://localhost:8890/let-me-rent/api/v1/instruments/';

    $scope.showToolInfo = function () {
        $http.get(contextPath + $rootScope.myToolIdFromProfile)
            .then(function successCallback(response) {
                $scope.tool = response.data;
            }, function failureCallback(response) {
                alert(response.data.messages);
                $location.path('/tool-info');
            });
    }

    $scope.showToolHistory = function () {
        $http.get(contextPath + 'rent?instrumentId=' + $rootScope.myToolIdFromProfile)
            .then(function successCallback(response) {
                $scope.tool_history = response.data;
            }, function failureCallback(response) {
                alert(response.data.messages);
                $location.path('/tool-info');
            });
    }

    $scope.navToFeedbackAboutRenter = function (renterId, renterUsername) {
        $rootScope.renterIdFromHistory = renterId;
        $location.path('/feedback-about-renter-page/' + renterUsername);
    }


    $scope.showToolInfo();
    $scope.showToolHistory();

});