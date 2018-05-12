app.controller('roleManageController',['$scope','$http','$filter','$state',function($scope,$http,$filter,$state){
    $scope.name='';
    $scope.pager={};
    $scope.getResponseFunction=function(role){
        $http.post($scope.baseURL + '/api/basicInfo/role/list',role)
            .then(function(response) {
                if(response.data.code==0){
                    $scope.pager = response.data;
                }else{
                    layer.msg(response.data.msg);
                }
            },function(error) {
                layer.msg('Server Error');
            });
    };
    $scope.getResponseFunction();

    // 搜索
    $scope.search = function(name){
        $scope.pager={};
        $scope.getResponseFunction({name:name});
    };

    $scope.addRole=function(){
        $state.go('app.rbac.roleManageEdit');
    };

    $scope.editRole=function(id,name,flag){
        $state.go('app.rbac.roleManageEdit',{id:id,name:name,flag:flag});
    };

    $scope.changeRole=function(roleObj){
        var mess=roleObj.delFlag==0?'确定要禁用吗？':'确定要启用吗？';
        $scope.param={
            roleIds:roleObj.roleId,
            delFlag:roleObj.delFlag==0?1:0
        };
        var lay = layer.confirm(mess,{
            btn: ['确定','取消']
        },function(){
            layer.close(lay);
            $http({
                method:"POST",
                url:$scope.baseURL + '/api/basicInfo/role/enOrDisable',
                data:$scope.param,
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                transformRequest:function(obj){
                    var str=[];
                    for(var p in obj){
                        str.push(encodeURIComponent(p)+"="+encodeURIComponent(obj[p]));
                    }
                    return str.join("&");
                }
            }).then(function(response) {
                if(response.data.code == 0){
                    layer.msg(response.data.msg);
                    setTimeout(function(){
                        $scope.getResponseFunction();
                    },1000);
                }else{
                    layer.msg(response.data.msg);
                }
            },function(error) {
                layer.msg('Server Error');
            });
        },function(){
            layer.close(lay);
        });
    };
}]);

