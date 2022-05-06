angular.module('tools').controller('addToolFormController', function ($scope, $http, $routeParams, $location, $localStorage) {
    const contextPath = 'http://localhost:8890/let-me-rent/api/v1/instruments';

    $scope.addTool = function () {
        $http.post(contextPath, $scope.new_tool)
            .then(function successCallback(response) {
                $scope.new_tool = null;
                alert('Инструмент успешно добавлен в базу');
                $location.path('/tools-list');
            }, function failureCallback(response) {
                alert(response.data.messages);
            });
    }

});