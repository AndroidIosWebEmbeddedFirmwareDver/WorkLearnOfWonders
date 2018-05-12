app.controller('departmentController',['$scope','$http','$state','$localStorage','$sessionStorage','$stateParams',function($scope,$http,$state,$localStorage,$sessionStorage,$stateParams){
    $scope.pager = {
        "totalPages":0,     //总页数
        "totalElements":0,  //总条数
        "size": 1000,         //页大小
        "number": 1,        //页码
        "parameter":{},
        "data":[]
    };

    if($sessionStorage.hospitalName){
      $scope.hospitalName = $sessionStorage.hospitalName;
    }

    $scope.searchObj={
        deptCode:'',
        deptName:''
    }

    $scope.getResponseFunction=function(pager){
        $http.post($scope.baseURL + '/api/department/list',pager)
            .then(function(response) {
                if(response.data.data && response.data.data.length){
                    $scope.pager = response.data;
                }else{
                    layer.msg('未查询到数据');
                }
            },function(error) {
                layer.msg(error.data.msg)
            });
    };

    $scope.goPage = function(number){
        var pager = $scope.pager;
        pager.parameter.hospitalCode = $stateParams.hospitalCode;

        if(isNotNull($scope.searchObj.deptCode)){
            pager.parameter.deptCode=$scope.searchObj.deptCode;
        }else{
            delete pager.parameter.deptCode;
        }

        if(isNotNull($scope.searchObj.deptName)){
            pager.parameter.deptName=$scope.searchObj.deptName;
        }else{
            delete pager.parameter.deptName;
        }

        pager.number = number;
        $scope.getResponseFunction(pager);
    };
    $scope.goPage(1); //初始化查询

    $scope.search=function(searchObj){
        $scope.goPage(1);
    };

    // 导入科室列表
    $scope.fileUpload = {
        url:$scope.baseURL+'/api/department/upload',
        content: '科室导入',
        goPage: $scope.goPage,
        name: 'file',
        uid:$localStorage.userId
    };

    $scope.edit = function(id){
      $state.go('app.ihd.departmentEdit',{id:id,hospitalCode:$stateParams.hospitalCode})
    }

    $scope.delete = function(id){
        var _layer = layer.alert('真的要删除吗?',{
          btn: ['确定','取消']
        },function(){
            $http.post($scope.baseURL + '/api/department/del?hospitalCode=' + $stateParams.hospitalCode + '&id=' + id)
                .success(function(rs){
                  if(rs.code == 0){
                    layer.msg('删除成功')
                    $scope.goPage(1)
                  }else{
                    layer.msg(rs.msg)
                  }
                })
                .catch(function(e){
                  layer.msg('Server Error', {icon: 5})
                })
            layer.close(_layer)
        })
    }

    function isNotNull(obj){
        return obj != null && obj != "";
    }
}])
