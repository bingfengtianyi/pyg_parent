//地址服务层
app.service('addressService',function ($http) {
    //获取收货列表
    this.findListByUserName=function () {
        return $http.get("address/findListByUserName.action")
    };


    //添加地址信息
    this.addAddress=function (entity) {
        return $http.post("/address/addAddress.action?",entity);
    };


    //删除地址信息
    this.delete=function (id) {
        return $http.post("/address/delete.action?id="+id);
    };

    //修改
    this.update=function (id) {
        return $http.post("/address/update.action?id="+id);
    };

    //编辑地址信息
    this.editorAddress=function (id) {
        return $http.post("/address/editorAddress.action?id="+id);
    };
});