app.service('seckillGoodsServices',function ($http) {
    this.findList = function () {
        return $http.get("../seckillAppGoods/findList.action");
    };
});