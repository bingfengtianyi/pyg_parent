app.service('seckillGoodsOrderService',function ($http) {
    this.findList = function () {
        return $http.get("../seckillGoodsOrder/findOrderList.action");
    };
});