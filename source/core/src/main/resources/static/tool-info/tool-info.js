angular.module('tool').controller('toolInfoController', function ($scope, $http, $routeParams, $location) {
    const contextPath = 'http://localhost:8888/';

    // $scope.showToolInfo = function () {   //ЗАГОТОВОЧКА ДЛЯ ЗАГРУЗКИ ИНФО ПО конкретному инструменту
    //     $http.get(contextPath + 'НАШ ЭНДПОИНТ' + $routeParams.toolId)
    //         .then(function successCallback (response) {
    //             $scope.tool = response.data;
    //         }, function failureCallback (response) {
    //             console.log(response);
    //             alert(response.data.messages);
    //             $location.path('/tools-list');
    //         });
    // }
    //
    //
    // $scope.showToolInfo();
});