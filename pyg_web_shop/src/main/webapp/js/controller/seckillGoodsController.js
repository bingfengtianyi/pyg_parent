app.controller('seckillGoodsController',function ($scope,seckillGoodsServices){
    $scope.findList = function () {
        seckillGoodsServices.findList().success(
            function (response) {
                $scope.list = response;
            }
        )
    };
});
