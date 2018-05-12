app.controller('UpdateController',['$scope','$http',function($scope, $http) {
    $scope.update={};
    $scope.update.keyWord="app.common.appUpdate";
    $scope.update.updateType=0;
    $scope.pullData=function(){
        $http.get($scope.baseURL +'/api/appConfig/findSingleAppConfigByKeyWord?keyWord='+$scope.update.keyWord)
            .then(function(response) {
                if (response.data.code==1001) {
                    layer.msg(response.data.msg);
                }else{
                    $scope.update.data= JSON.parse(response.data.data.data);
                    $scope.update.id= response.data.data.id;
                    if($scope.update.data.enforceUpdate){
                        $scope.update.updateType=1;
                        $scope.forceArr=$scope.update.data.enforceUpdate.split(",");
                        $scope.update.lowVersion=$scope.forceArr[0];
                        $scope.update.highVersion=$scope.forceArr[1];
                    }else{
                        $scope.update.updateType=0;
                        $scope.update.lowVersion="";
                        $scope.update.highVersion="";
                    }
                }
            },function(response) {
                layer.msg('Server Error');
            });
    };
    $scope.pullData();

    $scope.pushData=function(update){
        if(update.updateType==0){
            delete update.data.enforceUpdate;
            update.lowVersion="";
            update.highVersion="";
        }else{
            if(!update.lowVersion||!update.highVersion){
                layer.msg('请将强制升级版本区间填写完整！');
                return;
            }
            update.data.enforceUpdate=$scope.update.lowVersion+","+$scope.update.highVersion;
        }
        $scope.param={
            "id": update.id,
            "keyWord":$scope.update.keyWord,
            "data": JSON.stringify(update.data),
            discrete: update.discrete?1:0,
            remark: update.remark?update.remark:'',
            delFlag: update.delFlag?1:0,
        };
        $http.post($scope.baseURL +'/api/appConfig/saveAppConfig',$scope.param)
            .then(function(response) {
                layer.msg(response.data.msg);
                setTimeout(function(){
                    $scope.pullData();
                },1000);
            }, function(error) {
                layer.msg(error.data.msg);
            });
    };
}]);
