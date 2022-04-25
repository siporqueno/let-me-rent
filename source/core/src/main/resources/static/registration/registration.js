angular.module('tools').controller('registrationController', function ($scope, $http, $routeParams, $location) {
    const contextPath = 'http://localhost:8890/let-me-rent/api/v1/users';


    $scope.registerUser = function () {
        $http.post(contextPath, $scope.new_user)
            .then(function successCallback (response) {
                $scope.new_user = null;
                alert('Вы успешно зарегистрированы, для продолжения работы вам необходимо авторизоваться');
                $location.path('/authorisation');
            }, function failureCallback (response) {
                alert(response.data.userMessage);
                // TODO: если возникают ошибки валидации, то на фронт летит сложный объект ApplicationError, в котором лежат Violation, а в них поля "ИМЯ поля" и "сообщение об ошибке". Сложно пока это отразить на фронте - или на бэке надо сделать лист строк,или на фронте научиться их отражать
            });
    };






});