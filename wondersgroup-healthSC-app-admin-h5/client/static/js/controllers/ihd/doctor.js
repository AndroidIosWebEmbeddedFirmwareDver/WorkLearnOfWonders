app.controller('doctorController',['$scope','$http','$state','$stateParams','$localStorage','$sessionStorage',function($scope,$http,$state,$stateParams,$localStorage,$sessionStorage){
    $scope.pager={
        total_pages: 0,
        total_elements: 0,
        size: 10,
        number: 1,
        parameter:{},
        data:[]
    }

    if($sessionStorage.hospitalName){
      $scope.hospitalName = $sessionStorage.hospitalName
    }

    //获取医院二级科室列表
    $scope.getDeptListFunction=function(hospitalCode){
        $http.get($scope.baseURL +'/api/department/secondDepts?hospitalCode='+hospitalCode)
            .success(function(rs) {
                $scope.deptList=rs.data;
            },function(error) {
                layer.msg(error.data.msg);
            });
    };
    $scope.getDeptListFunction($stateParams.hospitalCode);

    $scope.searchObj={
        deptCode:'',
        doctorName:''
    }

    $scope.getResponseFunction=function(pager){
        pager.data=[];
        pager.totalPages=0;
        pager.totalElements=0;
        $http.post($scope.baseURL + '/api/doctor/list',pager)
            .then(function(response) {
                $scope.pager=response.data;
            },function(error) {
                layer.msg(error.data.msg);
            });
    };

    $scope.goPage = function(number){
        var pager = $scope.pager;
        pager.parameter.hospitalCode=$stateParams.hospitalCode;
        if(isNotNull($scope.searchObj.deptCode)){
            pager.parameter.deptCode=$scope.searchObj.deptCode;
        }else{
            delete pager.parameter.deptCode;
        }

        if(isNotNull($scope.searchObj.doctorName)){
            pager.parameter.doctorName=$scope.searchObj.doctorName;
        }else{
            delete pager.parameter.doctorName;
        }

        pager.number = number;
        $scope.getResponseFunction(pager);
    }
    $scope.goPage(1);//初始化查询

    $scope.search=function(searchObj){
        $scope.goPage(1);
    };

    // 导入医生列表
    $scope.fileUpload = {
        url:$scope.baseURL+'/api/doctor/upload',
        content: '医生导入',
        goPage: $scope.goPage,
        name: 'file',
        uid:$localStorage.userId
    }

    // 编辑医生
    $scope.doctorEdit = function(item){
        $state.go('app.ihd.doctorEdit',{id:item.id,hospitalCode:item.hospitalCode});
    }

    function isNotNull(obj){
        return obj != null && obj != "";
    }
}])
