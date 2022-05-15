angular.module('tools').controller('toolInfoController', function ($scope, $http, $routeParams, $location) {
    const contextPath = 'http://localhost:8890/let-me-rent';

    $scope.showToolInfo = function () {
        $http.get(contextPath + '/api/v1/instruments/'+ $routeParams.toolId)
            .then(function successCallback (response) {
                $scope.tool = response.data;
            }, function failureCallback (response) {
                alert(response.data.messages);
                $location.path('/tools-list');
            });
    }


    $scope.navToToolsList = function () {
        $location.path('/tools-list');
    }

    $scope.navToToolHistory = function (toolId) {
            $location.path('/tool-history/' + toolId);
    }

    $scope.toolComments = function (){
        $http({
            url: contextPath + '/api/v1/comments',
            method: 'GET',
            headers: {
                "about": "instrument"
            },
            params: {
                id: $routeParams.toolId
            }
        }).then(function successCallback(response) {
            $scope.tool_comments = response.data;
        }, function failureCallback(response) {
            alert(response.data.messages);
        });
    }

    $scope.toolGrade = function (){
        $http({
            url: contextPath + '/api/v1/comments/grade',
            method: 'GET',
            headers: {
                "about": "instrument"
            },
            params: {
                id: $routeParams.toolId
            }
        }).then(function successCallback(response) {
            if(response.data != 0){
                $scope.tool_grade = response.data;
            }else {
                $scope.tool_grade = "На настоящий момент никто из пользователь не выставлял оценки для расчета рейтинга";
            }
        }, function failureCallback(response) {
            alert(response.data.messages);
        });
    }

    $scope.showToolComments = function () {
        $scope.toolComments();
        $scope.toolGrade();
        $scope.ascToolGrade = true;
    }

    $scope.showToolInfo();

});