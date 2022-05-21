angular.module('tools').controller('feedbackAboutRenterController', function ($scope, $routeParams, $rootScope, $http, $location) {
    const contextPath = 'http://localhost:8890/let-me-rent'

    $scope.renter_comment = {
        userId: $rootScope.myUserIdFromProfile,
        aboutUserId: $rootScope.renterIdFromHistory
    };

    $scope.sendFeedbackAboutRenter = function () {
        if (typeof $scope.renter_comment.description === 'undefined' || typeof $scope.renter_comment.grade === 'undefined') {
            alert("Для отправки отзыва необходимо заполнить описание и выбрать оценку для рейтинга");
            return;
        }
        $http({
            url: contextPath + '/api/v1/comments',
            method: 'POST',
            headers: {
                "about": "user"
            },
            data: $scope.renter_comment
        }).then(function successCallback(response) {
            $scope.renter_comment = null;
            alert('Ваш отзыв успешно сохранен');
            $location.path('/profile');
        }, function failureCallback(response) {
            alert(response.data.messages);
        });
    };

    $scope.loadRenterInfo = function () {
        $http({
            url: contextPath + '/api/v1/users/' + $routeParams.renterUsername,
            method: 'GET'
        }).then(function (response) {
            $scope.renterInfo = response.data;
        });
    };

    $scope.navToToolHistory2 = function () {
            $location.path('/tool-history');
    }


    $scope.loadRenterInfo();

});