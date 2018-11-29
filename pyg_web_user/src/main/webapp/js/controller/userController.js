//控制层
app.controller('userController',function($scope,$controller,userService,orderService){

    // AngularJS中的继承:伪继承
    $controller('indexController',{$scope:$scope});

    //注册
    $scope.reg=function(){
        if($scope.entity.password!=$scope.password) {
            alert("两次输入的密码不一致，请重新输入");
            return ;
        }
        userService.add( $scope.entity,$scope.smscode).success(
            function(response){
                alert(response.message);
            }
        );
    }


    //发送验证码
    $scope.sendCode=function(){
        if($scope.entity.phone==null){
            alert("请输入手机号！");
            return ;
        }
        userService.sendCode($scope.entity.phone).success(
            function(response){
                alert(response.message);
            }
        );
    }



    $scope.orderList=[];
    //查找未付款订单
    $scope.findOrderListUnPay=function() {
        orderService.findOrderListUnPay().success(
            function (response) {
                $scope.orderList=response;
            }
        )
    }



    $scope.orderItemList=[];
    //我的收藏
    $scope.showMyCollection=function () {
        userService.showMyCollection().success(
            function (response) {

                //$scope.orderItemList=response;
                if(response!=null) {
                    $scope.orderItemList=response;
                }else {
                    //如果该用户没有收藏商品
                    location.href="home-person-collectNone.html";
                }
            }
        )
    }


});