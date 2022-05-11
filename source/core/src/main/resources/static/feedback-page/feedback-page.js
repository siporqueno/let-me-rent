angular.module('tools').controller('feedbackController', function ($scope, $http,$rootScope, $routeParams, $location) {
    const contextPath = 'http://localhost:8890/let-me-rent/';

    // $scope.tool_comment.userId = $rootScope.myUserIdFromProfile;
    // $scope.tool_comment.instrumentId = $routeParams.toolId;
    // $scope.owner_comment.userId = $rootScope.myUserIdFromProfile;
    // $scope.owner_comment.aboutUserId = $scope.tool.ownerId; //TODO: ожидаем, что это поле появится в InstrumentInfoDto

    $scope.tool_comment={userId: "1", instrumentId: "2"}; //временно для тестирования отправки отзывов
    $scope.owner_comment={userId: "1", aboutUserId: "2"}; //временно для тестирования отправки отзывов

    $scope.showToolInfo = function () {
        $http.get(contextPath + 'api/v1/instruments/' +$routeParams.toolId)
            .then(function successCallback (response) {
                $scope.tool = response.data;
            }, function failureCallback (response) {
                alert(response.data.messages);
                $location.path('/tools-list');
            });
    }


    $scope.aboutToolFeedback = function () {
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
        },function failureCallback (response) {
            alert(response.data.messages);
        });
    };


    $scope.aboutOwnerFeedback = function () {
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
        },function failureCallback (response) {
            alert(response.data.messages);
        });
        //TODO: Написала в InstrumentInfoDto просьбу добавить еще поле ownerId (с id владельца). Он нам нужен для формирования отзыва (в отзыве одно из полей именно айдишник)
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