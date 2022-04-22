angular.module('tools').controller('cartController', function ($scope, $http) {
    const contextPath = 'http://localhost:8890/let-me-rent/';

    $scope.loadCart = function () {
        //код доработать после подключения Reddis и формирования логики на бэке
        $http({
            url: contextPath + 'api/v1/cart/' + $localStorage.webMarketGuestCartId,
            method: 'GET'
        }).then(function (response) {
            $scope.cart = response.data;
        });
    };

    $scope.deleteItem = function (toolId) {
        //код доработать после подключения Reddis и формирования логики на бэке
        $http({
            url: contextPath + 'api/v1/cart/' + $localStorage.webMarketGuestCartId + '/remove/' + toolId,
            method: 'GET'
        }).then(function (response) {
            $scope.loadCart();
        });
    };

    $scope.tryToRent = function (toolId) {
        //    ТУТ ЕСЛИ НЕ АВТОРИЗОВАН, ТО АЛЕРТ, ЧТО НАДО АВТОРИЗОВАТЬСЯ СНАЧАЛА, И ПЕРЕБРОСКА НА
        // СТРАНИЦУ АВТОРИЗАЦИИ
        // $location.path('/authorisation');
        // А после авторизации/если авторизован, то уже формируем запрос на аренду на отдельной странице:
        $location.path('/rent-request-page/' + toolId);
    }

    $scope.navToToolInfoPage = function (toolId) {
        $location.path('/tool-info/' + toolId);
    }

    $scope.rentToolsList = function () {
        //доработать код заказа после того, как проработаем на бэке. Бэк должен получить информацию
        //о списке инструментов в корзине  +  о периоде аренды.
        $http({
            url: contextPath + 'api/v1/orders',
            method: 'POST',
            data: $scope.period
        }).then(function (response) {
            alert('Инструменты успешно забронированы');
            $location.path('/profile');
        });
    };

    $scope.loadCart();

});