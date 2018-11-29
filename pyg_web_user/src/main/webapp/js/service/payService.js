app.service('payService',function($http){
	//本地支付

	this.createNative=function(out_trade_no){
		return $http.get('pay/createNative.action?out_trade_no='+out_trade_no);
	};
	
	//查询支付状态
	this.queryPayStatus=function(out_trade_no){
		return $http.get('pay/queryPayStatus.action?out_trade_no='+out_trade_no);
	}
});