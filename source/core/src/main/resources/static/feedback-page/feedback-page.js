angular.module('tools').controller('feedbackController', function ($scope, $http, $routeParams, $location) {
    const contextPath = 'http://localhost:8890/let-me-rent/';

    $scope.showToolInfo = function () {
        $http.get(contextPath + 'api/v1/instruments/' +$routeParams.toolId)
            .then(function successCallback (response) {
                $scope.tool = response.data;
            }, function failureCallback (response) {
                // alert(response.data.messages); // это добавим, когда ошибки начнем с бэка передавать
                alert("Пресловутое 'Что-то пошло не так :('") //это пока,потом уберем
                $location.path('/tools-list');
            });
    }


    $scope.toolFeedback = function (toolId) {
       //добавить код , когда будет готов бэк для приема отзывов
    }


    $scope.ownerFeedback = function (ownerUsername) {
        //добавить код , когда будет готов бэк для приема отзывов
        //TODO: я бы предложила в InstrumentInfoDto добавить еще поле с id владельца. И тогда в этот метод можно будет передавать айдишник владельца, а не имя. Это будет удобнее дальше на бэке обрабтывать.
    }

    $scope.showToolInfo();

});