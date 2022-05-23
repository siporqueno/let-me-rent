angular
    .module('tools')
    .controller('editToolController', function ($scope, $http, $routeParams, $location) {
        const contextPath = 'http://localhost:8890/let-me-rent';

        let pictureUrlsToRemove = [];

        $scope.prepareToolForUpdate = function () {
            $http.get(contextPath + '/api/v1/instruments/' + $routeParams.toolId)
                .then(function successCallback(response) {
                    $scope.updated_tool = response.data;
                    console.log($scope.updated_tool);
                }, function failureCallback(response) {
                    console.log(response);
                    alert(response.data.messages);
                    $location.path('/profile');
                });
        }

        $scope.updateTool = function () {
            console.log('Last rent date: ' + $scope.updated_tool.lastRentDate);
            if ($scope.updated_tool.endDate < $scope.updated_tool.lastRentDate) {
                alert("Предоставление инструмента в аренду не может быть приостановлено раньше окончания последнего забронированного периода аренды");
            } else {
                console.log($scope.updated_tool);
                $scope.updated_tool.pictureUrls = pictureUrlsToRemove;

                $http.put(contextPath + '/api/v1/instruments', $scope.updated_tool)
                    .then(function successCallback(response) {
                        let instrumentId = $scope.updated_tool.id
                        pictureUrlsToRemove.splice(0, pictureUrlsToRemove.length);
                        $scope.updated_tool = null;
                        console.log('Инструмент и его имеющиеся фото успешно обновлены');

                        if ($scope.pictures) {

                            console.log($scope.pictures);
                            let formData = new FormData();
                            for (let i = 0; i < $scope.pictures.length; i++) {
                                formData.append('pictures', $scope.pictures[i]);
                                console.log('File name: ' + $scope.pictures[i].name + ', size: ' + $scope.pictures[i].size);
                            }
                            formData.append('instrumentId', instrumentId);
                            $http({
                                url: contextPath + "/api/v1/pictures/uploads",
                                method: 'POST',
                                headers: {'Content-Type': undefined},
                                data: formData
                            }).then(function successCallback() {
                                console.log('Новые фото успешно добавлены');
                            }, function failureCallback(response) {
                                alert(response.data.messages);
                            });

                        }

                        $location.path('/profile');

                    }, function failureCallback(response) {
                        alert(response.data.messages);
                    });
                alert('Инструмент и его имеющиеся фото успешно обновлены, новые фото добавлены');

            }

        }

        $scope.removePicture = function (url, pictureUrls) {
            console.log('Before: ' + $scope.updated_tool.pictureUrls);
            pictureUrlsToRemove.push(url);
            console.log(pictureUrlsToRemove);
            pictureUrls.splice(pictureUrls.indexOf(url), 1);
            console.log('Deleted picture');
            console.log('After: ' + $scope.updated_tool.pictureUrls);
        }

        $scope.prepareToolForUpdate();

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