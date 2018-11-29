app.controller('seckillGoodsOrderControllers',function ($scope,seckillGoodsOrderService){
    $scope.findOrderList = function () {
        seckillGoodsOrderService.findList().success(
            function (response) {
                $scope.list = response;
            }
        )
    };
});
