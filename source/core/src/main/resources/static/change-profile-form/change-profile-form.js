angular.module('tools').controller('changeProfileInfoController', function ($scope, $rootScope, $http, $location) {
    const contextPath = 'http://localhost:8890/let-me-rent/api/v1/users';

    $scope.prepareEditProfileForm = function () {
        $http({
            url: contextPath + '/myUserInfo',
            method: 'GET'
        }).then(function (response) {
            $scope.updated_user = response.data;
        });
    };

    $scope.updateUserInfo = function () {
        $http.put(contextPath, $scope.updated_user)
            .then(function successCallback (response) {
                $scope.updated_user = null;
                alert('Личные данные успешно обновлены');
                $location.path('/profile');
            }, function failureCallback (response) {
                alert(response.data.messages);
            });
    };


    $scope.prepareEditProfileForm();

});