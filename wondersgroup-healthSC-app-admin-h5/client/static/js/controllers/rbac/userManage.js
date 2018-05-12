app.controller('accountManageController',['$scope','$http','$filter','$state',function($scope,$http,$filter,$state){
    $scope.pager = {
        "totalPages":0,     //总页数
        "totalElements":0,  //总条数
        "size": 10,         //页大小
        "number": 1,        //页码
        "parameter":{},
        "data":[]
    };
    $scope.accountObj={};

    $scope.getResponseFunction=function(pager){
        $http.post($scope.baseURL + '/api/basicInfo/user/list',pager)
            .then(function(response) {
                $scope.pager = response.data.data;
                for(var i=0;i<$scope.pager.data.length;i++){
                    $scope.pager.data[i].roleNameArr=[];
                    $scope.pager.data[i].roleIdsArr=[];
                    if($scope.pager.data[i].roleList.length>0){
                        for(var j=0;j<$scope.pager.data[i].roleList.length;j++){
                            $scope.pager.data[i].roleNameArr.push($scope.pager.data[i].roleList[j].name);
                            $scope.pager.data[i].roleIdsArr.push($scope.pager.data[i].roleList[j].roleId);
                        }
                        $scope.pager.data[i].roleName=$scope.pager.data[i].roleNameArr.join(',');
                        $scope.pager.data[i].roleIds=$scope.pager.data[i].roleIdsArr.join(',');
                    }else{
                        $scope.pager.data[i].roleName='';
                        $scope.pager.data[i].roleIds='';
                    }
                }

            },function(error) {
                layer.msg('Server Error');
            });
    };

    $scope.goPage = function(number){
        var pager = $scope.pager;
        pager.number = number;
        $scope.getResponseFunction(pager);
    };
    $scope.goPage(1); //初始化查询

    // 搜索
    $scope.search = function(accountObj){
        var pager = $scope.pager;
        pager.parameter = {
            username: accountObj.username?accountObj.username:'',
            delFlag:accountObj.delFlag
        };
        $scope.getResponseFunction(pager);
    };

    $scope.addAccount=function(){
        $state.go('app.rbac.userManageEdit');
    };

    $scope.editAccount=function(id){
        $state.go('app.rbac.userManageEdit',{id:id});
    };

    $scope.changeAccount=function(account){
        var mess=account.delFlag=='0'?'确定要禁用吗？':'确定要启用吗？';
        var lay = layer.confirm(mess,{
            btn: ['确定','取消']
        },function(){
            layer.close(lay);
            $scope.param={
                userId:account.userId,
                loginname:account.loginname,
                username:account.username,
                roleIds:account.roleIds,
                delFlag:account.delFlag==0?1:0
            };
            $http({
                method:"POST",
                url:$scope.baseURL + '/api/basicInfo/user/update',
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
                $scope.goPage($scope.pager.number);
            },function(error) {
                layer.msg(error.msg);
            });
        },function(){
            layer.close(lay);
        });
    };

    $scope.resetPassword=function(id){
        $http({
            method:"POST",
            url:$scope.baseURL + '/api/basicInfo/user/resetPassword',
            data:{userId:id},
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            transformRequest:function(obj){
                var str=[];
                for(var p in obj){
                    str.push(encodeURIComponent(p)+"="+encodeURIComponent(obj[p]));
                }
                return str.join("&");
            }
        }).then(function(response) {
            layer.msg(response.data.msg);
        },function(error) {
            layer.msg('Server Error');
        });
    };
}]);

