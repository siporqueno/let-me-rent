angular.module('tools').controller('rentRequestController', function ($scope, $http, $routeParams, $location) {
    const contextPath = 'http://localhost:8890/let-me-rent/api/v1/instruments/';
    //ПОКА ПО ЛОГИКЕ ПРИЛОЖЕНИЯ ЭТА СТРАНИЦА НЕ НУЖНА, НО НЕ УБИРАЮ, ВДРУГ ПРИГОДИТСЯ ЕЩЕ

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

    //Код надо будет дописать, когда появится логика оформления закза на бэке
    // $scope.rentTool = function () {
    //    // Здесь надо на бэк отправить информацию о:
    //    // - ID инструмента - можно взять из $scope.tool
    //     // - Датах начала и конца аренды (из формы)
    //     // - ID арендатора (вытащить из localstorage
    //     //и на бэке тогда сформируется готовый объект Rent, соответственно, вся информация об аренде инструментов обновится
    // }

    $scope.showToolInfo();
});