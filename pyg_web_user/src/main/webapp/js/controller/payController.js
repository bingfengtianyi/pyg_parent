app.controller('payController' ,function($scope ,$location,payService){

    var out_trade_no = $location.search()["out_trade_no"];

	$scope.createNative=function(){
        alert(out_trade_no);
		payService.createNative(out_trade_no).success(
			function(response){
				
				//显示订单号和金额
				$scope.money=(response.total_fee/100).toFixed(2);
				$scope.out_trade_no=out_trade_no;
				
				//生成二维码
				 var qr=new QRious({
					    element:document.getElementById('qrious'),
						size:250,
						value:response.code_url,
						level:'H'
			     });
				 
				 queryPayStatus();//调用查询
				
			}	
		);	
	};
	
	//调用查询
	queryPayStatus=function(){
		alert("开始查询支付状态");
		payService.queryPayStatus(out_trade_no).success(
			function(response){
				alert("后端轮询方法已调用");
				if(response.flag){
					location.href="paysuccess.html#?money="+$scope.money;
				}else{
					if(response.message=='二维码超时'){
						//$scope.createNative();//重新生成二维码
                        location.href="payclose.html";
					}else{
						location.href="payfail.html";
					}
				}				
			}		
		);		
	};


	//获取金额
	$scope.getMoney=function(){
		return $location.search()['money'];
	}
	
});