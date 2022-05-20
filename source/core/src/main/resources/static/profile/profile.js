angular.module('tools').controller('profileController', function ($scope, $rootScope, $http, $location) {
    const contextPath = 'http://localhost:8890/let-me-rent/';


    $scope.loadRents = function () {
        $http({
            url: contextPath + 'api/v1/orders',
            method: 'GET'
        }).then(function (response) {
            $scope.orders = response.data;
        });
    };

    $scope.loadMyTools = function () {
        $http({
            url: contextPath + 'api/v1/instruments/lk',
            method: 'GET'
        }).then(function (response) {
            $scope.tools = response.data;
        });
    };

    $scope.loadMyProfile = function () {
        $http({
            url: contextPath + 'api/v1/users/myUserInfo',
            method: 'GET'
        }).then(function (response) {
            $scope.userProfile = response.data;
            $rootScope.myUserIdFromProfile = response.data.id;
        });
    };

    $scope.changeTool = function (toolId) {
        $location.path('/edit-tool/' + toolId);
    }

    $scope.navToAuthPage = function () {
        $location.path('/authorisation');
    }

    $scope.navToFeedbackPage = function (toolId, ownerId) {
        $rootScope.toolIdFromProfile = toolId;
        $rootScope.ownerIdFromProfile = ownerId;
        $location.path('/feedback-page');
    }

    $scope.failedNavToFeedbackPage = function () {
        alert("Владелец не может оставлять отзывы о своем инструменте :)");
    }

    $scope.renterIsOwner = function (ownerId){
        if(ownerId === $rootScope.myUserIdFromProfile){
            return true;
        }else {
            return false;
        }
    }

    $scope.navToToolHistory = function (toolId) {
        $rootScope.myToolIdFromProfile = toolId;
        $location.path('/tool-history');
    }

    $scope.navToChangeProfileForm = function () {
        $location.path('/change-profile-form');
    }

    $scope.loadMyProfile();
    $scope.loadRents();
    $scope.loadMyTools();
});