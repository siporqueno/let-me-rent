angular.module('tools').controller('authorisationController', function ($scope, $rootScope, $http, $routeParams, $location, $localStorage) {
    const contextPath = 'http://localhost:8890/let-me-rent';

    $scope.tryToAuth = function () {
        $http.post(contextPath + '/api/v1/auth', $scope.user)
            .then(function successCallback(response) {
                $http.defaults.headers.common.Authorization = 'Bearer ' + response.headers.Authorization;
                    $localStorage.letMeRentUser = {username: $scope.user.username, token: response.headers.Authorization};

                $rootScope.user.username = null;
                $rootScope.user.password = null;

                    // $http.get(contextPath + '/api/v1/carts/' + $localStorage.letMeRentGuestCartId + '/merge')
                    //     .then(function successCallback(response) {
                    //     }); //это дополнительная функция для мержа корзины в Redis-надо раскоммитить, когда разберемся в корзиной в Redis

            }, function errorCallback(response) {
                alert(response.data.userMessage);
            });
    };

    $rootScope.tryToLogout = function () {
        $scope.clearUser();
        if ($scope.user.username) {
            $scope.user.username = null;
        }
        if ($scope.user.password) {
            $scope.user.password = null;
        }
        $location.path('/');
    };

    $scope.clearUser = function () {
        delete $localStorage.letMeRentUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $scope.navToRegistration = function () {
        $location.path('/registration');

    }


});