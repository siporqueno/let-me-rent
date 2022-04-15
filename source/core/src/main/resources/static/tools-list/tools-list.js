angular.module('tools').controller('toolsListController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:8888/api/v1/instruments';
    let currentPageIndex = 1;

    $scope.loadTools = function (pageIndex = 1) { // ЗАГОТОВОЧКА ПО ЗАГРУЗКЕ СТРАНИЦЫ СО СПИСКОМ
        currentPageIndex = pageIndex;
        $http({
            url: contextPath + 'энд-поинт метода контролллера, если будет (но, наверное, здесь будет только contextPath)',
            method: 'GET',
            params: {
                p: pageIndex,
                title: $scope.filter ? $scope.filter.title : null,
                category: $scope.filter ? $scope.filter.categoryName : null,
                owner: $scope.filter ? $scope.filter.owner.userName : null,
                max_fee: $scope.filter ? $scope.filter.max_fee : null
            }
        }).then(function (response) {
            $scope.toolsPage = response.data;
            $scope.paginationArray = $scope.generatePagesIndexes(1, $scope.toolsPage.totalPages);
        });
    };


    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
    }


    $(document).ready(function() {
        $('#tools-list').DataTable( {
                             language: {
                                 url: 'https://cdn.datatables.net/plug-ins/1.11.5/i18n/ru.json'
                             }
                         });
    } );

    $(document).ready(function() {
       $('.datepicker').datepicker({
          format: 'mm-dd-yyyy'
        });
    });

    $scope.navToToolInfoPage = function (toolId) {
        $location.path('/tool-info/' + toolId);
    }


    $scope.tryToRent = function (toolId) {
    //    ТУТ ЕСЛИ НЕ АВТОРИЗОВАН, ТО АЛЕРТ, ЧТО НАДО АВТОРИЗОВАТЬСЯ СНАЧАЛА, И ПЕРЕБРОСКА НА
    // СТРАНИЦУ АВТОРИЗАЦИИ
    // $location.path('/authorisation');
    // И после авторизации уже отправка запроса о желании арендовать.
    $location.path('/rent-request-page/' + toolId);
    }

    $scope.loadTools();
});