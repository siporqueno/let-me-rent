angular.module('tools').controller('editToolController', function ($scope, $http, $routeParams, $location) {
    const contextPath = 'http://localhost:8890/let-me-rent/api/v1/instruments/';

    $scope.prepareToolForUpdate = function () {
        $http.get(contextPath + 'наш эндпоинт' + $routeParams.toolId)
            .then(function successCallback (response) {
                $scope.updated_tool = response.data;
            }, function failureCallback (response) {
                console.log(response);
                alert(response.data.messages);
                $location.path('/profile');
            });
    }

    $scope.updateTool = function () {
        $http.put(contextPath + 'наш эндпоинт', $scope.updated_tool)
            .then(function successCallback (response) {
                $scope.updated_tool = null;
                alert('Продукт успешно обновлен');
                $location.path('/profile');
            }, function failureCallback (response) {
                alert(response.data.messages);
            });
    }

    $scope.prepareToolForUpdate();
});