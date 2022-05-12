angular.module('tools').controller('feedbackController', function ($scope, $http, $rootScope, $routeParams, $location) {
    const contextPath = 'http://localhost:8890/let-me-rent/';

    $scope.tool_comment = {
        userId: $rootScope.myUserIdFromProfile,
        instrumentId: $rootScope.toolIdFromProfile
    };
    $scope.owner_comment = {
        userId: $rootScope.myUserIdFromProfile,
        aboutUserId: $rootScope.ownerIdFromProfile
    };

    $scope.showToolInfo = function () {
        $http.get(contextPath + 'api/v1/instruments/' + $rootScope.toolIdFromProfile)
            .then(function successCallback(response) {
                $scope.tool = response.data;
            }, function failureCallback(response) {
                alert(response.data.messages);
                $location.path('/tools-list');
            });
    }


    $scope.aboutToolFeedback = function () {
        console.log($scope.tool_comment);
        $http({
            url: contextPath + 'api/v1/comments',
            method: 'POST',
            headers: {
                "about": "instrument"
            },
            data: $scope.tool_comment
        }).then(function successCallback(response) {
            $scope.tool_comment = null;
            alert('Ваш отзыв об инструменте успешно сохранен');
        }, function failureCallback(response) {
            alert(response.data.messages);
        });
    };


    $scope.aboutOwnerFeedback = function () {
        console.log($scope.owner_comment);
        $http({
            url: contextPath + 'api/v1/comments',
            method: 'POST',
            headers: {
                "about": "user"
            },
            data: $scope.owner_comment
        }).then(function successCallback(response) {
            $scope.owner_comment = null;
            alert('Ваш отзыв о владельце успешно сохранен');
        }, function failureCallback(response) {
            alert(response.data.messages);
        });
    }

    $scope.sendOwnerFeedback = function () {
        $scope.aboutOwnerFeedback();
        $location.path('/profile');
    };

    $scope.sendToolFeedback = function () {
        $scope.aboutToolFeedback();
        $location.path('/profile');
    };

    $scope.sendBothFeedbacks = function () {
        $scope.aboutOwnerFeedback();
        $scope.aboutToolFeedback();
        $location.path('/profile');
    }

    $scope.navToProfile = function () {
        $location.path('/profile');
    }

    $scope.showToolInfo();

});