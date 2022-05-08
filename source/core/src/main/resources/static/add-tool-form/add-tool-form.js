angular
    .module('tools')
    .controller('addToolFormController', function ($scope, $http, $routeParams, $location, $localStorage) {
        const contextPath = 'http://localhost:8890/let-me-rent';

        $scope.addTool = function () {
            $http.post(contextPath + '/api/v1/instruments', $scope.new_tool)
                .then(function successCallback(response) {
                    $scope.new_tool = null;
                    $scope.created_tool = response.data;
                    console.log('Инструмент без фото успешно добавлен в базу');
                    let formData = new FormData();
                    console.log($scope.pictures);
                    for (let i = 0; i < $scope.pictures.length; i++) {
                        formData.set('picture', $scope.pictures[i], $scope.pictures[i].name);
                        console.log('File name: ' + $scope.pictures[i].name + ', size: ' + $scope.pictures[i].size)
                        $http({
                            url: contextPath + "/api/v1/pictures/upload",
                            method: 'POST',
                            headers: {'Content-Type': undefined},
                            params: {
                                instrumentId: $scope.created_tool.id
                            },
                            data: formData
                        }).then(function successCallback(response) {
                            console.log('Added picture: ' + $scope.pictures[i].name + ' for instrument with id ' + $scope.created_tool.id);
                        }, function failureCallback(response) {
                            alert(response.data.messages);
                        });
                    }

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
                        modelSetter(scope, element[0].files);
                    });
                });
            }
        };
    }]);

// If you want to add just one file use in the above directive: modelSetter(scope, element[0].files[0]);
// The idea of directive is taken from https://www.tutorialspoint.com/angularjs/angularjs_upload_file.htm




