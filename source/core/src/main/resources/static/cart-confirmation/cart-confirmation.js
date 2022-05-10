angular.module('tools').controller('cartConfirmationController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:8890/let-me-rent/';

    $scope.loadCart = function () {
        $http({
            url: contextPath + 'api/v1/carts/' + $localStorage.letMeRentGuestCartId,
            method: 'GET'
        }).then(function (response) {
            $scope.cart = response.data;
        });
    };

    $scope.navToCart = function () {
        $location.path('/cart');
    }

     $scope.processOrder = function () {
         $http({
             url: contextPath + 'api/v1/orders',
             method: 'POST',
             data: $scope.period
         }).then(function (response) {
             alert('Инструменты успешно арендованы');
             $location.path('/profile');
         });
     };

    $scope.loadCart();

});