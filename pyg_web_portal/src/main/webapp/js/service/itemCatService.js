//11.28 itemCat service层
app.service("itemCatService",function($http){
    this.findItemCatList = function(){
        return $http.get("../itemCat/findItemCatList.action");
    }
});