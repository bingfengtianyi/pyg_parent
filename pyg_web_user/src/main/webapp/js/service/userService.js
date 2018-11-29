//用户服务层
app.service('userService',function($http){
    this.add= function(user,smscode){
        return $http.post( 'user/add.action?smscode='+smscode,user);
    }

    //发送短信验证码
    this.sendCode= function(phone){
        return $http.get( 'user/sendCode.action?phone='+phone);
    }

    //我的收藏
    this.showMyCollection=function () {
        return $http.get("user/showMyCollection.action");
    }

});