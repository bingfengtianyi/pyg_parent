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


    //读取列表数据绑定到表单中
    this.findAll=function(){
        return $http.get('../order/findAll.action');
    }





   //搜索
    this.search=function(page,rows,searchEntity){
        return $http.post('../order/search.action?page='+page+"&rows="+rows, searchEntity);
    }


});