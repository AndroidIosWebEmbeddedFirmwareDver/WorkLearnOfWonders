app.controller('addRoleController',['$scope','$http','$state','$stateParams','$timeout',function($scope,$http,$state,$stateParams,$timeout){
    $scope.roleObj={};
    $scope.isShow=false;

    if ($stateParams.id) {
        $scope.title = "编辑角色";
        $scope.isShow=true;
        $scope.roleObj.id=$stateParams.id;
        $scope.roleObj.name=$stateParams.name;
    }else{
        $scope.title = "添加角色";
        $scope.isShow=false;
    }

    //ztree
    function zTreeOnCheck(event, treeId, treeNode) {
        if(treeNode.getParentNode().href == 'app.push'){
            if(treeNode.checked==false){
                var zTree = $.fn.zTree.getZTreeObj("treeDemo");
                zTree.checkNode(treeNode.getParentNode(),true,false);
                zTree.checkNode(treeNode.getParentNode().getParentNode(),true,false);
                zTree.checkNode(treeNode.getParentNode().getParentNode().getParentNode(),true,false);
            }
        }
    };
    var setting={
        check:{
            enable:true
        },
        data:{
            simpleData:{
                enable:true
            }
        },
        callback: {
            onCheck: zTreeOnCheck
        }
    };
    //ztree
    $http.get($scope.baseURL + '/api/basicInfo/roleAdd?rootMenuId=1&roleId='+$stateParams.id)
        .then(function(response) {
            //ztree
            response.data.data.treeMenu.open=true;
            var zNodes=response.data.data.treeMenu;
            $.fn.zTree.init($('#treeDemo'),setting,zNodes);
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            zTree.setting.check.chkboxType= { "Y":'ps', "N":'ps'};
        },function(error) {
            layer.msg('Server Error');
        });

    $scope.submitRole=function(roleObj){
        //ztree
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        var nodesArr= zTree.getCheckedNodes(true);
        var idArr=[];
        for(var i=0;i<nodesArr.length;i++){
            idArr.push(nodesArr[i].menuId);
        }
        $scope.param={
            name:roleObj.name,
            menuIds:idArr.join(','),
            delFlag:$stateParams.flag?$stateParams.flag:0
        };
        if($stateParams.id){
            $scope.param.roleId=$stateParams.id;
        }
        $http({
            method:"POST",
            url:$scope.baseURL + '/api/basicInfo/role/update',
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
                    $state.go('app.rbac.roleManage');
                },1000);
            }else{
                layer.msg(response.data.msg);
            }
        },function(error) {
            layer.msg('Server Error');
        });
    };
}]);



