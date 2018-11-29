//控制层
app.controller('orderController',function($scope,$controller,$location,orderService){
    // AngularJS中的继承:伪继承
    $controller('baseController',{$scope:$scope});

    $scope.reloadList = function(){
        // $scope.findByPage($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
        $scope.search($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
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




    //读取列表数据绑定到表单中
    $scope.findAll=function(){
        orderService.findAll().success(
            function(response){
                $scope.list=response;
            }
        );
        $scope.search();
    }





    $scope.searchEntity={};//定义搜索对象
  //搜索
    $scope.search=function(page,rows){

        orderService.search(page,rows,$scope.searchEntity).success(
            function(response){
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//更新总记录数
            }
        );
    }



});