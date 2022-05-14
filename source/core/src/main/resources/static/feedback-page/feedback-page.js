angular.module('tools').controller('feedbackController', function ($scope, $http, $rootScope, $routeParams, $location) {
    const contextPath = 'http://localhost:8890/let-me-rent/';

    $scope.tool_comment = {
        userId: $rootScope.myUserIdFromProfile,
        instrumentId: $rootScope.toolIdFromProfile
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

    $scope.sendToolFeedback = function () {
        if (typeof $scope.tool_comment.description === 'undefined' || typeof $scope.tool_comment.grade === 'undefined') {
            alert("Для отправки отзыва необходимо заполнить описание и выбрать оценку для рейтинга");
            return;
        }
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
            $location.path('/profile');
        }, function failureCallback(response) {
            alert(response.data.messages);
        });
    };

    $scope.navToProfile = function () {
        $location.path('/profile');
    }

    $scope.showToolInfo();

});