angular.module('tools')
    .controller('addToolFormController', function ($scope, $http, $routeParams, $location, $localStorage) {
        const contextPath = 'http://localhost:8890/let-me-rent';

        $scope.addTool = function () {
            $http.post(contextPath + '/api/v1/instruments', $scope.new_tool)
                .then(function successCallback(response) {
                    $scope.new_tool = null;
                    $scope.created_tool = response.data;
                    alert('Инструмент без фото успешно добавлен в базу');

                    for (let i = 0; i < $scope.pictures.length; i++) {
                        $http({
                            url: contextPath + "/api/v1/pictures/upload",
                            method: 'POST',
                            headers: {'Content-Type': 'multipart/form-data'},
                            params: {
                                picture: $scope.pictures[i],
                                instrumentId: $scope.created_tool.id
                            },
                            // data: {'file': new FormData($scope.pictures[i])}
                        }).then(function successCallback(response) {
                            console.log("Added picture: " + i);
                        }, function failureCallback(response) {
                            alert(response.data.messages);
                        });
                    }

                    $location.path('/tools-list');
                }, function failureCallback(response) {
                    alert(response.data.messages);
                });
        }

    })
    // .directive('filesModel', function () {
    //     return {
    //         restrict: 'A',
    //         controller: function ($parse, $element, $attrs, $scope) {
    //             var exp = $parse($attrs.filesModel);
    //             $element.on('change', function () {
    //                 exp.assign($scope, this.files);
    //                 $scope.$apply();
    //             })
    //         }
    //     }
    // })
    .directive('filesModel', ['$parse', function ($parse) {
        return {
            restrict: 'A',
            link: function (scope, element, attrs) {
                var model = $parse(attrs.filesModel);
                var modelSetter = model.assign;

                element.bind('change', function () {
                    scope.$apply(function () {
                        modelSetter(scope, element[0].files);
                    })
                })
            }
        }
    }]);


