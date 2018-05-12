app.controller('HomeAddCtrl', ['$scope', '$http', '$state', '$stateParams','$filter',function($scope, $http, $state,$stateParams,$filter) {
    $scope.homeObj ={
        version:'',
        delFlag:'1',
        remark:'',
        images:[{},{},{},{},{},{}],
        gadcode:1
    };

    if ($stateParams.id) {
        if($stateParams.type=='copy'){
            $scope.title = "复制首页板块设置";

        }else{
            $scope.title = "编辑首页板块设置";
        }
        $scope.homeObj.id=$stateParams.id;
        $scope.type=$stateParams.type;
        $http.get($scope.baseURL+'/api/imagetext/findGImageTextById?gid='+$stateParams.id)
            .then(function(rs) {
                var data = rs.data;
                if(data.code == 0) {
                    $scope.homeObj = data.data;
                    if($stateParams.type=='copy'){
                        $scope.homeObj.version ='';
                    }
                }
            },function(error) {
                layer.msg(error.data.msg);
            });
    }else{
        $scope.title = "添加首页板块设置";
    }

    $scope.setPicurl = function(url,i){
        $scope.homeObj.images[i].sequence = i+1;
        $scope.homeObj.images[i].imgUrl = url;
    };

    $scope.onClose = function(i){
        $scope.homeObj.images[i].imgUrl = "";
    };

    $scope.submitHome = function(homeObj){
        if(!homeObj.images[0].imgUrl||!homeObj.images[1].imgUrl||!homeObj.images[2].imgUrl||!homeObj.images[3].imgUrl||!homeObj.images[4].imgUrl||!homeObj.images[5].imgUrl){
            layer.alert('请选择图片!');
            return;
        }
        $scope.param={
            version:homeObj.version,
            delFlag:0,
            remark:homeObj.remark?homeObj.remark:'',
            images:homeObj.images,
            gadcode:1
        };
        if($stateParams.id && $stateParams.type=='edit'){
            $scope.param.id=$stateParams.id;
        }
        $http.post($scope.baseURL+'/api/imagetext/saveGImageText',$scope.param)
            .then(function(response) {
                if(response.data.code == 0){
                    layer.msg(response.data.msg);
                    setTimeout(function(){
                        $state.go('app.cps.homePlateSetting');
                    },1000);
                }else{
                    layer.msg(response.data.msg);
                }
            },function(response) {
                layer.msg(response.data.msg);
            });
    };
}]);