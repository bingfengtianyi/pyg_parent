//用户服务层
app.service('userService',function($http){
    this.findPage = function(page,rows){
        return $http.get("../user/findUserList.action?pageNum="+page+"&pageSize="+rows);
    }

    this.findUser= function(){
        return $http.get("../userCount/findUser.action");
    }

});