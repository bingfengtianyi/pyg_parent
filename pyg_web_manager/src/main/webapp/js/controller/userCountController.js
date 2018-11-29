//控制层
app.controller('userCountController', function ($scope, userService) {
//用户统计
    $scope.findUser = function () {

        userService.findUser().success(function (response) {
            $scope.num = response;
            alert("当前用户总人数为:"+num);
        })
    };
});