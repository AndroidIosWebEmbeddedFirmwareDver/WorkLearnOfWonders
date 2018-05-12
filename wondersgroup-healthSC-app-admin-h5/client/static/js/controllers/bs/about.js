//关于页面controller

app.controller('AboutController',['$scope','$http',function($scope, $http) {
    $scope.about={};
    $scope.about.key="app.common.aboutApp";
    $scope.pullData=function(){
        $http.get($scope.baseURL + '/api/appConfig/findSingleAppConfigByKeyWord?keyWord='+$scope.about.key)
            .then(function(response) {
                if (response.data.code==1001) {
                    layer.msg(response.data.msg);
                }else{
                    $scope.about.key= response.data.data.keyWord;
                    $scope.about.value= JSON.parse(response.data.data.data);
                    $scope.about.id= response.data.data.id;
                }
            },function(error) {
                layer.msg(error.data.msg);
            });
    };
    $scope.pullData();

    $scope.pushData=function(about){
        $scope.param={
            id: about.id,
            keyWord: about.key,
            data: JSON.stringify(about.value),
            discrete: about.discrete?1:0,
            remark: about.remark?about.remark:'',
            delFlag: about.delFlag?1:0
        };
        $http.post($scope.baseURL + '/api/appConfig/saveAppConfig', $scope.param)
            .then(function(response) {
                layer.msg(response.data.msg);
                $scope.pullData();
            }, function(error) {
                layer.msg(error.data.msg);
            });
    };
}]);
