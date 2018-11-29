//11.28 itemCat控制层
app.controller('itemCatController', function ($scope, itemCatService) {
//查询商品分类集合
    $scope.findItemCatList=function () {
        itemCatService.findItemCatList().success(function (response) {
            $scope.list = response;
        })
    }
});