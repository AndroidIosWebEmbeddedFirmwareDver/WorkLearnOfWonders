app.controller('SwitchController',['$scope','$http',function($scope, $http) {
    $scope.configOBJ={};
    $scope.configOBJ.key='automaticVerification';
    $scope.pullData=function(){
        $http.get($scope.baseURL + '/api/appConfig/findSingleAppConfigByKeyWord?keyWord='+$scope.configOBJ.key)
            .then(function(response) {
                if (response.data.code==1001) {
                    layer.msg(response.data.msg);
                }else{
                    $scope.configOBJ.key= response.data.data.keyWord;
                    $scope.configOBJ.value= JSON.parse(response.data.data.data)==1?true:false;
                    $scope.configOBJ.id= response.data.data.id;
                }
            },function(error) {
                layer.msg(error.data.msg);
            });
    };
    $scope.pullData();

    $scope.saveConfig = function(configOBJ){
        $scope.param = {
            id: configOBJ.id,
            keyWord:$scope.configOBJ.key,
            discrete:"0",
            delFlag:"0",
            remark:configOBJ.remark?configOBJ.remark:'',
            data:configOBJ.value==true?1:0
        };
        $http.post($scope.baseURL +'/api/appConfig/saveAppConfig',$scope.param)
            .then(function(response) {
                if(response.data.code == 0){
                    layer.msg(response.data.msg);
                }else{
                    layer.msg(response.data.msg);
                }
            },function(error) {
                layer.msg(error.data.msg);
            });
    };
}]);
