(function () {
    angular
        .module('tools', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'start-page/start-page.html',
                controller: 'startPageController'
            })
            .when('/tools-list', {
                templateUrl: 'tools-list/tools-list.html',
                controller: 'toolsListController'
            })
            .when('/tool-info/:toolId', {
                templateUrl: 'tool-info/tool-info.html',
                controller: 'toolInfoController'
            })
            .when('/rent-request-page/:toolId', {
                templateUrl: 'rent-request-page/rent-request-page.html',
                controller: 'rentRequestController'
            })
            .when('/profile', {
                templateUrl: 'profile/profile.html',
                controller: 'profileController'
            })
            .when('/edit-tool/:toolId', {
                templateUrl: 'edit-tool/edit-tool.html',
                controller: 'editToolController'
            })
            .when('/authorisation', {
                templateUrl: 'authorisation/authorisation.html',
                controller: 'authorisationController'
            })
            .when('/registration', {
                templateUrl: 'registration/registration.html',
                controller: 'registrationController'
            })
            .when('/add-tool-form', {
                templateUrl: 'add-tool-form/add-tool-form.html',
                controller: 'addToolFormController'
            })
            .otherwise({
                redirectTo: '/'
            });
    }

    function run($rootScope, $http, $localStorage) {
        // const contextPath = 'http://localhost:8888';
        // if ($localStorage.webMarketUser) {
        //     $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.webMarketUser.token;
        // }
        // if (!$localStorage.webMarketGuestCartId) {
        //     $http.get(contextPath + '/api/v1/cart/generate')
        //         .then(function successCallback(response) {
        //             $localStorage.webMarketGuestCartId = response.data.value;
        //         });
        // }
    }
})();

angular.module('tools').controller('indexController', function ($rootScope, $scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/tools';

    // $scope.tryToAuth = function () {
    //     $http.post(contextPath + '/auth', $scope.user)
    //         .then(function successCallback(response) {
    //             if (response.data.token) {
    //                 $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
    //                 $localStorage.webMarketUser = {username: $scope.user.username, token: response.data.token};
    //
    //                 $scope.user.username = null;
    //                 $scope.user.password = null;
    //
    //                 $http.get(contextPath + '/api/v1/cart/' + $localStorage.webMarketGuestCartId + '/merge')
    //                     .then(function successCallback(response) {
    //                     });
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
    //
    // $rootScope.isUserLoggedIn = function () {
    //     if ($localStorage.webMarketUser) {
    //         return true;
    //     } else {
    //         return false;
    //     }
    // };
});