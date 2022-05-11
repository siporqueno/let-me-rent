angular.module('tools').controller('feedbackAboutRenterController', function ($scope,$routeParams, $rootScope, $http,  $location) {
    const contextPath = 'http://localhost:8890/let-me-rent'

    // $scope.renter_comment.aboutUserId = $routeParams.renterId;
    // $scope.renter_comment.userId = $rootScope.myUserIdFromProfile;

    $scope.renter_comment= {userId: "1", aboutUserId: "2"};

    $scope.sendFeedbackAboutRenter = function () {
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
        },function failureCallback (response) {
                alert(response.data.messages);
            });
    };

    $scope.loadRenterInfo = function () {
        $http({
            url: contextPath + '/api/v1/users/'+ $routeParams.renterUsername, //пока в контроллере есть метод поиска юзера только по имени
            method: 'GET'
        }).then(function (response) {
            $scope.renterInfo = response.data;
        });
    };


     $scope.loadRenterInfo();

});