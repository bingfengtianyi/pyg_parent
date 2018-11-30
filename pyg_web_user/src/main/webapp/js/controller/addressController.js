//地址管理控制层
app.controller('addressController',function($scope,$location,addressService){


    //获取收货地址列表
    $scope.findListByUserName=function () {
        addressService.findListByUserName().success(function (response) {
            $scope.addressList=response;

        })
    };


    //修改(查询一个)
    $scope.update=function (id) {
        addressService.update(id).success(function (response) {
            $scope.entity=response
        })
    };

    //编辑地址
    $scope.editorAddress=function (id) {
        addressService.editorAddress(id).success(
            function (response) {
                if (response.flag){
                    $scope.findAddressList();
                }else {
                    alert(response.message)
                }
            }
        )
    };


    //删除地址
    $scope.delete=function (id) {
        addressService.delete(id).success(
            function (response) {
                confirm("是否删除?");
                if (response.flag){
                    alert(response.message);
                    $scope.findAddressList();
                }else {
                    alert(response.message)
                }
            }
        )
    };


    //新增收货地址
    $scope.entity={};
    $scope.submitAddress=function () {

        addressService.addAddress($scope.entity).success(function (response) {
            if (response.flag){
                alert(response.message);
                $scope.findAddressList();
            }else {
                alert(response.message)
            }
        })
    };



    //选择地址
    $scope.selectAddress=function (address) {
        $scope.address=address;
    };

    //判断某地址对象是不是当前的地址
    $scope.isSelectedAddress=function (address) {
        if ($scope.address==address){
            return true;
        }else{
            return false;
        }
    };
});