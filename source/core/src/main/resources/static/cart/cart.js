angular.module('tools').controller('cartController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:8890/let-me-rent/';

    $scope.loadCart = function () {
        $http({
            url: contextPath + 'api/v1/carts/' + $localStorage.letMeRentGuestCartId,
            method: 'GET'
        }).then(function (response) {
            $scope.cart = response.data;
        });
    };

    $scope.deleteItem = function (toolId) {
           $http({
            url: contextPath + 'api/v1/carts/' + $localStorage.letMeRentGuestCartId + '/remove/' + toolId,
            method: 'GET'
        }).then(function (response) {
            $scope.loadCart();
        });
    };

    $scope.navToToolInfoPage = function (toolId) {
        $location.path('/tool-info/' + toolId);
    }

    $scope.navToToolsList = function () {
        $location.path('/tools-list');
    }

    // $scope.createOrder = function () {
    //     //доработать код заказа после того, как проработаем на бэке. Бэк должен получить информацию
    //     $http({
    //         url: contextPath + 'api/v1/orders',
    //         method: 'POST',
    //         data: $scope.period
    //     }).then(function (response) {
    //         alert('Инструменты успешно забронированы');
    //         $location.path('/profile');
    //     });
    // };

    $scope.disabledCreateOrder = function () {
        alert("Для оформления заказа необходимо авторизоваться");
    }

    $scope.loadCart();

});