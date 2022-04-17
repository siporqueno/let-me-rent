angular.module('tool').controller('toolInfoController', function ($scope, $http, $routeParams, $location) {
    const contextPath = 'http://localhost:8890/let-me-rent/api/v1/instruments/';

    $scope.showToolInfo = function () {   //ЗАГОТОВОЧКА ДЛЯ ЗАГРУЗКИ ИНФО ПО конкретному инструменту
        $http.get(contextPath + $routeParams.toolId)
            .then(function successCallback (response) {
                $scope.tool = response.data;
            }, function failureCallback (response) {
                // alert(response.data.messages); // это добавим, когда ошибки начнем с бэка передавать
                alert("Пресловутое 'Что-то пошло не так :('") //это пока,потом уберем
                $location.path('/tools-list');
            });
    }


    $scope.showToolInfo();
});