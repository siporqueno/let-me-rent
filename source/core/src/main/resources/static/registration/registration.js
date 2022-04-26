angular.module('tools').controller('registrationController', function ($scope, $http, $routeParams, $location) {
    const contextPath = 'http://localhost:8890/let-me-rent/api/v1/users';


    $scope.registerUser = function () {
        $http.post(contextPath, $scope.new_user)
            .then(function successCallback(response) {
                $scope.new_user = null;
                alert('Вы успешно зарегистрированы, для продолжения работы вам необходимо авторизоваться');
                $location.path('/authorisation');
            }, function failureCallback(response) {
                if (angular.isArray(response.data.userMessage)) {
                    for (let i = 0; i < response.data.userMessage.length; i++) {
                        alert(response.data.userMessage[i].message);
                    }
                } else {
                    alert(response.data.userMessage);
                }
            });
    };


});