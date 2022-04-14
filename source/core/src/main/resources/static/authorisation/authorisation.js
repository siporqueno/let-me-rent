angular.module('tools').controller('authorisationController', function ($scope, $http, $routeParams, $location) {
    // const contextPath = 'http://localhost:8888/';

    // $scope.tryToAuth = function () {
    //     $http.post(contextPath + 'НАШ ЭНД ПОИНТ', $scope.user)
    //         .then(function successCallback(response) {
    //             if (response.data.token) {
    //                 $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
    //                 $localStorage.webMarketUser = {username: $scope.user.username, token: response.data.token};
    //
    //                 $scope.user.username = null;
    //                 $scope.user.password = null;
    //             }
    //         }, function errorCallback(response) {
    //         });
    // };
    //
    // $scope.tryToLogout = function () {
    //     $scope.clearUser();
    //     if ($scope.user.username) {
    //         $scope.user.username = null;
    //     }
    //     if ($scope.user.password) {
    //         $scope.user.password = null;
    //     }
    //     $location.path('/');
    // };
    //
    // $scope.clearUser = function () {
    //     delete $localStorage.webMarketUser;
    //     $http.defaults.headers.common.Authorization = '';
    // };

    $scope.navToRegistration = function () {
        $location.path('/registration');

    }


});