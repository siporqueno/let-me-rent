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

    $scope.showToolHistory = function () {
            $http.get(contextPath + 'rent?instrumentId=' + $routeParams.toolId)//для меня непривычная форма отправки на бэк в методе GET RequestParam. К Татьяне, как автору,  вопрос про работоспособность (я не тестила)
                .then(function successCallback (response) {
                    $scope.tool_history = response.data;
                }, function failureCallback (response) {
                    alert(response.data.messages);
                    $location.path('/tool-info');
                });
        }

    $scope.navToFeedbackAboutRenter = function (renterId, renterUsername) {
        $location.path('/feedback-about-renter-page/' + renterId + '/'+ renterUsername);
    }


    $scope.showToolInfo();
    $scope.showToolHistory();

});