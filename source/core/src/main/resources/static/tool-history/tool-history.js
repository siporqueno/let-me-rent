angular.module('tools').controller('toolHistoryController', function ($scope, $http, $routeParams, $location) {
    const contextPath = 'http://localhost:8890/let-me-rent/api/v1/instruments/';

    $scope.showToolInfo = function () {
        $http.get(contextPath + $routeParams.toolId)
            .then(function successCallback (response) {
                $scope.tool = response.data;
            }, function failureCallback (response) {
                // alert(response.data.messages); // это добавим, когда ошибки начнем с бэка передавать
                alert("Пресловутое 'Что-то пошло не так :('") //это пока,потом уберем
                $location.path('/tool-info');
            });
    }


    $scope.showToolInfo();

});