//控制层
app.controller('orderController',function($scope,$controller,$location,orderService){
    // AngularJS中的继承:伪继承
    $controller('baseController',{$scope:$scope});

    $scope.reloadList = function(){
        // $scope.findByPage($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
        $scope.findPage($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
    }
    // 分页查询
    $scope.findPage = function(page,rows){
        // 向后台发送请求获取数据:
        orderService.findPage(page,rows).success(function(response){
            $scope.paginationConf.totalItems = response.total;
            $scope.list = response.rows;
        });
    };

    //运营商按照商家查询销售额
    $scope.findTotalMoneyBySellerId = function () {
        orderService.findTotalMoneyBySellerId().success(
            function (response) {
                $scope.BImageResult = response;
            }
        )
    };

    //运营商按照时间段查询订单数
    $scope.findOrderCountByTime = function (startTime,endTime) {
        orderService.findOrderCountByTime(startTime,endTime).success(
            function (response) {
                $scope.ZImageResult = response;
            }
        )
    }


});