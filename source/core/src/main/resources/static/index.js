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
            .when('/rent-request-page/:toolId', { //ПО ЛОГИКЕ ПРИЛОЖЕНИЯ ЭТО ПОКА ЛИШНЯЯ СТРАНИЦА. ПОТОМ УБЕРЕМ
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
            .when('/cart', {
                templateUrl: 'cart/cart.html',
                controller: 'cartController'
            })
            .when('/cart-confirmation', {
                templateUrl: 'cart-confirmation/cart-confirmation.html',
                controller: 'cartConfirmationController'
            })
            .otherwise({
                redirectTo: '/'
            });
    }

    function run($rootScope, $http, $localStorage) {
        const contextPath = 'http://localhost:8890/let-me-rent';
        if ($localStorage.letMeRentUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.letMeRentUser.token;
        }
        if (!$localStorage.letMeRentGuestCartId) {
            $http.get(contextPath + '/api/v1/carts/generate')
                .then(function successCallback(response) {
                    $localStorage.letMeRentGuestCartId = response.data.value;
                });
        }
    }
})();

angular.module('tools').controller('indexController', function ($rootScope, $scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8890/let-me-rent';

    $rootScope.isUserLoggedIn = function () {
        if ($localStorage.letMeRentUser) {
            return true;
        } else {
            return false;
        }
    };


});