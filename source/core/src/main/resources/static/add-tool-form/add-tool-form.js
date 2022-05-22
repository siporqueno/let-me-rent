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
                    console.log($scope.pictures);
                    let formData = new FormData();
                    for (let i = 0; i < $scope.pictures.length; i++) {
                        formData.append('pictures', $scope.pictures[i]);
                        console.log('File name: ' + $scope.pictures[i].name + ', size: ' + $scope.pictures[i].size);
                    }
                    formData.append('instrumentId', $scope.created_tool.id);
                    $http({
                        url: contextPath + "/api/v1/pictures/uploads",
                        method: 'POST',
                        headers: {'Content-Type': undefined},
                        data: formData
                    }).then(function successCallback(response) {
                        console.log('Added pictures');
                    }, function failureCallback(response) {
                        alert(response.data.messages);
                    });

                    alert('Инструмент успешно добавлен в базу');

                    $location.path('/profile');
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

