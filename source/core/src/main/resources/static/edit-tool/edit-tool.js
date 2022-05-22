angular.module('tools').controller('editToolController', function ($scope, $http, $routeParams, $location) {
    const contextPath = 'http://localhost:8890/let-me-rent/api/v1/instruments';

    $scope.prepareToolForUpdate = function () {
        $http.get(contextPath + '/' + $routeParams.toolId)
            .then(function successCallback(response) {
                $scope.updated_tool = response.data;
                console.log($scope.updated_tool);
                $scope.earliestPossibleEndDate = response.data.lastRentDate;
            }, function failureCallback(response) {
                console.log(response);
                alert(response.data.messages);
                $location.path('/profile');
            });
    }

    $scope.updateTool = function () {
        if ($scope.updated_tool.endDate < $scope.earliestPossibleEndDate) {
            alert("Предоставление инструмента в аренду не может быть приостановлено раньше окончания последнего забронированного периода аренды");
        } else {
            console.log($scope.updated_tool);
            $http.put(contextPath, $scope.updated_tool)
                .then(function successCallback(response) {
                    $scope.updated_tool = null;
                    alert('Продукт успешно обновлен');
                    $location.path('/profile');
                }, function failureCallback(response) {
                    alert(response.data.messages);
                });
        }

    }

    $scope.prepareToolForUpdate();

});