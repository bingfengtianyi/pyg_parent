//订单服务层
app.service('orderService',function($http){
    this.findPage = function(page,rows){
        return $http.get("../order/findOrderList.action?pageNum="+page+"&pageSize="+rows);
    }

    this.findTotalMoneyBySellerId = function () {
        return $http.get("../order/findTotalMoney.action")
    }

    this.findOrderCountByTime = function (startTime,endTime) {
        return $http.get("../order/findOrderCountByTime.action?startTime="+startTime+"&endTime="+endTime);
    }

    this.findTotalMoneyBySellerId = function () {
        return $http.get("../order/findTotalMoneyBySellerId.action");
    }

});