angular.module('tool').controller('rentRequestController', function ($scope, $http, $routeParams, $location) {
    const contextPath = 'http://localhost:8890/let-me-rent/';

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
    // $scope.rentTool = function () {
    //    // Здесь надо на бэк отправить информацию о:
    //    // - ID инструмента - можно взять из $scope.tool
    //     // - Датах начала и конца аренды (из формы)
    //     // - ID арендатора (вытащить из localstorage
    //     //и на бэке тогда сформируется готовый объект Rent, соответственно, вся информация об аренде инструментов обновится
    // }

    // $scope.showToolInfo();
});