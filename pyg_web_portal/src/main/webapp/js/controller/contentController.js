app.controller("contentController",function($scope,$controller,contentService){
	//开始
    // AngularJS中的继承:伪继承
    $controller('itemCatController',{$scope:$scope});
	//结束
	$scope.contentList = [];
	// 根据分类ID查询广告的方法:
	$scope.findByCategoryId = function(categoryId){
		contentService.findByCategoryId(categoryId).success(function(response){
			$scope.contentList[categoryId] = response;
		});
	}
	
	//搜索  （传递参数）
	$scope.search=function(){
		location.href="http://localhost:9103/search.html#?keywords="+$scope.keywords;
	}
	
});