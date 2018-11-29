app.service("contentService",function($http){
	this.findByCategoryId = function(categoryId){
		return $http.get("content/findByCategoryId.action?categoryId="+categoryId);
	}

    //我的收藏
    this.showMyCollection=function () {
        return $http.get("user/showMyCollection.action");
    }
});