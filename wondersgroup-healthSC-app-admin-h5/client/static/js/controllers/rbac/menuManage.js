app.controller('addMenuController',['$scope','$http','$state','$stateParams','$timeout',function($scope,$http,$state,$stateParams,$timeout){
    $scope.menuObj={};
    $scope.menuObj.type="1";
    $scope.menuObj.sort=0;
    $scope.issubmiting=false;
    $scope.type=1;

    $scope.pullData=function () {
        $http.get($scope.baseURL+"/api/menu/list?rootMenuId=1")
            .then(function(rs){
                if(rs.data.code==0){
                    $scope.my_data=[rs.data.data];
                }else{
                    layer.msg(response.data.msg);
                }
            }, function(response) {
                layer.msg('Server Error');
            })
    };

    $scope.edit=function () {
        if(!$scope.menuObj.output){
            layer.msg("请填写完整!");
            return;
        }
        if($scope.menuObj.editType==""||!$scope.menuObj.editType){
            layer.msg("请选择类型!");
            return;
        }
        console.info(isPositiveInteger($scope.menuObj.sort));
        if(!isPositiveInteger($scope.menuObj.sort)){
            layer.msg("菜单序号须为正整数!");
            return;
        }
        $scope.param={
            menuId:$scope.menuObj.menuId,
            name:$scope.menuObj.output,
            // href:$scope.menuObj.href,
            type:$scope.menuObj.editType,
            sort:$scope.menuObj.sort,
            parentId: $scope.menuObj.parentId,
            delFlag:0
        };
        if($scope.menuObj.editType==2){
            if($scope.menuObj.href==""||!$scope.menuObj.href){
                layer.msg("请填写权限!");
                return;
            }
            $scope.param.permission=$scope.menuObj.href;
        }else{
            $scope.param.href=$scope.menuObj.href;
        }
        $scope.issubmiting=true;
        $http.post($scope.baseURL + '/api/menu/update',$scope.param)
            .then(function(response) {
                if(response.data.code == 0){
                    layer.msg(response.data.msg);
                    $scope.clear();
                    $scope.issubmiting=false;
                    $scope.isopen=0;
                }else{
                    layer.msg(response.data.msg);
                    $scope.issubmiting=false;
                }
            },function(error) {
                layer.msg('Server Error');
                $scope.issubmiting=false;
            });
    }
    $scope.save=function () {
        if(!$scope.menuObj.name||!$scope.menuObj.output){
            layer.msg("请填写完整!");
            return;
        }
        if($scope.menuObj.type==""||!$scope.menuObj.type){
            layer.msg("请选择类型!");
            return;
        }
        console.info(isPositiveInteger($scope.menuObj.sort));
        if(!isPositiveInteger($scope.menuObj.sort)){
            layer.msg("菜单序号须为正整数!");
            return;
        }
        $scope.param={
            name:$scope.menuObj.name,
            // href:$scope.menuObj.link,
            type:$scope.menuObj.type,
            sort:$scope.menuObj.sort,
            parentId: $scope.menuObj.menuId,
            delFlag:0
        };
        if($scope.menuObj.type==2){
            if($scope.menuObj.link==""||!$scope.menuObj.link){
                layer.msg("请填写权限!");
                return;
            }
            $scope.param.permission=$scope.menuObj.link;
        }else{
            $scope.param.href=$scope.menuObj.link;
        }
        $scope.issubmiting=true;
        $http.post($scope.baseURL + '/api/menu/update',$scope.param)
            .then(function(response) {
                if(response.data.code == 0){
                    layer.msg(response.data.msg);
                    $scope.clear();
                    $scope.issubmiting=false;
                    $scope.isopen=0;
                }else{
                    layer.msg(response.data.msg);
                    $scope.issubmiting=false;
                }
            },function(error) {
                layer.msg('Server Error');
                $scope.issubmiting=false;
            });
    }

    function isPositiveInteger(s){//是否为正整数
        var re = /^[0-9]+$/ ;
        return re.test(s)
    }

    $scope.delete=function () {
        if(!$scope.menuObj.parentId){
            layer.msg("根目录不能删除!");
            return;
        }
        $scope.deleteParam={
            menuIds:$scope.menuObj.menuId
        }
        $http({
            method:"POST",
            url:$scope.baseURL + '/api/menu/delete',
            data:$scope.deleteParam,
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
                $scope.clear();
                $scope.issubmiting=false;
                $scope.isopen=0;
            }else{
                layer.msg(response.data.msg);
                $scope.issubmiting=false;
            }
        },function(error) {
            layer.msg('Server Error');
            $scope.issubmiting=false;
        });
    }

    var tree;
    $scope.my_tree_handler = function(branch) {
        $scope.menuObj.menuId = branch.menuId;
        $scope.menuObj.parentId = branch.parentId;
        $scope.menuObj.editType = branch.type;
        $scope.menuObj.sort = branch.sort;
        if(branch.type == 1){
            $scope.menuObj.href = branch.href;
        }else{
            $scope.menuObj.href = branch.permission;
        }
        $scope.menuObj.output=branch.menuName;
    };

    $scope.my_tree = tree = {};
    $scope.try_async_load = function() {
        $scope.my_data = [];
        $scope.doing_async = true;
        return $timeout(function() {
            $scope.pullData();
            $scope.doing_async = false;
            return tree.expand_all();
        }, 100);
    };
    $scope.try_async_load();
    $scope.clear=function () {
        $scope.try_async_load();
        $scope.menuObj={};
        $scope.isopen=0;
    }
    $scope.isopen=0;
    $scope.fold=function () {
        if(!$scope.isopen){
            $scope.my_tree.expand_all();
            $scope.isopen=1;
        }else{
            $scope.my_tree.collapse_all();
            $scope.isopen=0;
        }
    }
}]);

