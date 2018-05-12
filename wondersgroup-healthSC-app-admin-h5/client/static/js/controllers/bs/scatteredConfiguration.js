app.controller('ConfigCtrl', ['$scope', '$http', '$state', function($scope, $http, $state) {
    $scope.pager = { "data":[]};
    $scope.configOBJ={};

    function getList(){
        $http.get($scope.baseURL +'/api/appConfig/findAllDiscreteAppConfig')
            .then(function(response) {
                if(response.data.code == 0){
                    $scope.pager = response.data;
                }else{
                    layer.msg(response.data.msg);
                }
            },function(error) {
                layer.msg(error.data.msg);
            });
    }

    getList();

    $scope.saveConfig = function(configOBJ){
        $scope.param = {
            keyWord:configOBJ.keyWord,
            discrete:"1",
            delFlag:"0",
            remark:configOBJ.remark,
            data:configOBJ.data
        };
        if(configOBJ.id){
            $scope.param.id=configOBJ.id;
        }
        $http.post($scope.baseURL +'/api/appConfig/saveAppConfig',$scope.param)
            .then(function(response) {
                if(response.data.code == 0){
                    $scope.configOBJ={};
                    layer.msg(response.data.msg);
                    getList();
                }else{
                    layer.msg(response.data.msg);
                }
            },function(error) {
                layer.msg(error.data.msg);
            });
    };

    $scope.updateConfig = function(id,num){
        var data = $scope.pager.data[num];
        if(data.isedit){
            $scope.saveConfig(data);
        }
        data.isedit = true;
    }
}]);