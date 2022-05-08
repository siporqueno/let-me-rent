angular.module('tools').controller('addToolFormController', function ($scope, $http, $routeParams, $location, $localStorage) {
        const contextPath = 'http://localhost:8890/let-me-rent';

        $scope.addTool = function () {
            $http.post(contextPath + '/api/v1/instruments', $scope.new_tool)
                .then(function successCallback(response) {
                    $scope.new_tool = null;
                    $scope.created_tool = response.data;
                    alert('Инструмент без фото успешно добавлен в базу');

                    let formData = new FormData();
                    formData.append('picture', $scope.pictures, $scope.pictures.name);
                    // for (let i = 0; i < $scope.fileList.length; i++) {
                    //     console.log($scope.pictures.length);
                    //     console.log(i);
                        console.log($scope.pictures)
                        // console.log($scope.fileList.size)
                        $http({
                            url: contextPath + "/api/v1/pictures/upload",
                            method: 'POST',
                            headers: {'Content-Type': undefined},
                            params: {
                                instrumentId: $scope.created_tool.id
                            },
                            data: formData
                        }).then(function successCallback(response) {
                            console.log("Added picture: " + $scope.pictures.name);
                        }, function failureCallback(response) {
                            alert(response.data.messages);
                        });
                    // }

                    $location.path('/tools-list');
                }, function failureCallback(response) {
                    alert(response.data.messages);
                });
        };

    })
    .directive('filesModel', ['$parse', function ($parse) {
        return {
            restrict: 'A',
            link: function (scope, element, attrs) {
                var model = $parse(attrs.filesModel);
                var modelSetter = model.assign;

                element.bind('change', function () {
                    scope.$apply(function () {
                        modelSetter(scope, element[0].files[0]);
                    });
                });
            }
        };
    }]);


