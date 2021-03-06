//控制层
app.controller('userController',function($scope,$controller,$location,userService){
    // AngularJS中的继承:伪继承
    $controller('baseController',{$scope:$scope});


    $scope.reloadList = function(){
        // $scope.findByPage($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
        $scope.findPage($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
    }


    // 分页查询
    $scope.findPage = function(page,rows){
        // 向后台发送请求获取数据:
        userService.findPage(page,rows).success(function(response){
            $scope.paginationConf.totalItems = response.total;
            $scope.list = response.rows;
        });
    };


});