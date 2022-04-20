angular.module('tools').controller('toolInfoController', function ($scope, $http, $routeParams, $location) {
    const contextPath = 'http://localhost:8890/let-me-rent/api/v1/instruments/';

    $scope.showToolInfo = function () {
        $http.get(contextPath + $routeParams.toolId)
            .then(function successCallback (response) {
                $scope.tool = response.data;
            }, function failureCallback (response) {
                // alert(response.data.messages); // это добавим, когда ошибки начнем с бэка передавать
                alert("Пресловутое 'Что-то пошло не так :('") //это пока,потом уберем
                $location.path('/tools-list');
            });
    }

    $scope.tryToRent = function () {
        //    ТУТ ЕСЛИ НЕ АВТОРИЗОВАН, ТО АЛЕРТ, ЧТО НАДО АВТОРИЗОВАТЬСЯ СНАЧАЛА, И ПЕРЕБРОСКА НА
        // СТРАНИЦУ АВТОРИЗАЦИИ
        // $location.path('/authorisation');
        // А после авторизации/если авторизован, то уже формируем запрос на аренду на отдельной странице:
        $location.path('/rent-request-page/' + $scope.tool.id);
    }

    $scope.putIntoCart = function () {
        // доработать запрос по энд-поинтам после добавления логики на бэке по Redis и авторизации
        $http({
            url: contextPath + 'api/v1/cart/' + $localStorage.webMarketGuestCartId + '/add/' + $scope.tool.id,
            method: 'GET'
        }).then(function (response) {
            // $location.path('/cart/'); //опционально. Я бы не стала здесь сразу перебрасывать в корзину.
        });
    }

    $scope.showToolInfo();

});