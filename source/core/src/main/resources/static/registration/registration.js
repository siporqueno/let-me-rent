angular.module('tools').controller('registrationController', function ($scope, $http, $routeParams, $location) {
    const contextPath = 'http://localhost:8890/let-me-rent/api/v1/users';


    $scope.registerUser = function () {
        $http.post(contextPath, $scope.new_user)
            .then(function successCallback(response) {
                $scope.new_user = null;
                alert('Вы успешно зарегистрированы, для продолжения работы вам необходимо авторизоваться');
                $location.path('/authorisation');
            }, function failureCallback(response) {
                if (response.data.userMessage == null) {
                    alert("Заполните информационные поля регистрации!");
                } else if (Array.isArray(response.data.userMessage)) {
                    var result = "Не заполнены обязательные поля для регистрации: ";
                    for (let i = 0; i < response.data.userMessage.length; i++) {
                        result += "\r\n\r\n" + response.data.userMessage[i].message
                    }
                    alert(result.toString());
                } else {
                    alert(response.data.userMessage);
                }
            });
    };


});