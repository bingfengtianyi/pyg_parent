//用户服务层
app.service('orderService',function($http){


    //查找未付款订单
    this.findOrderListUnPay= function(){
        return $http.get('order/findOrderListUnPay.action');
    }

    this.findOrderListByPageAndUserId = function () {
        return $http.get('order/findOrderListByPageAndUserId.action');
    }
});