app.controller('hospitalController',['$scope','$http','$state','$localStorage','$sessionStorage','$stateParams',function($scope,$http,$state,$localStorage,$sessionStorage,$stateParams){
    if($sessionStorage.hospitalListPager && $stateParams.flag){
        $scope.pager = $sessionStorage.hospitalListPager;
        $scope.pager.data=[];
        $scope.searchObj = $sessionStorage.hospitalListPager.parameter;
    }else{
        $scope.pager = {
            "totalPages":0,     //总页数
            "totalElements":0,  //总条数
            "size": 10,         //页大小
            "number": 1,        //页码
            "parameter":{},
            "data":[]
        };
        $scope.searchObj={};
    }
    $scope.areaArr=[];

    $scope.getArr=function (list) {
        angular.forEach(list,function (value) {
            $scope.areaArr[value.cityCode]=value.cityName;
        })
    };

    if($localStorage.areaList){
        $scope.areaList=$localStorage.areaList;
        $scope.getArr($scope.areaList);
    }else{
        $http.get($scope.baseURL+'/api/serverConfig/areaCode')
            .success(function(rs){
                if (rs.code == 0) {
                    $scope.areaList=rs.data;
                    $localStorage.areaList=$scope.areaList;
                    $scope.getArr($scope.areaList);
                }else{
                    layer.msg(rs.data.msg)
                }
            })
    }

    $scope.getResponseFunction=function(pager){
        pager.data=[];
        $http.post($scope.baseURL + '/api/hospital/list',pager)
            .then(function(response) {
                if(response.data.data.length){
                    $scope.pager = response.data;
                }else{
                    layer.msg('未查询到数据');
                    $scope.pager = response.data;
                }
            },function(error) {
                layer.msg(error.data.msg);
            });
    };

    $scope.goPage = function(number){
        var pager = $scope.pager;
        pager.number = number;
        $scope.getResponseFunction(pager);
    };
    $scope.goPage(1); //初始化查询

    $scope.search = function(searchObj){
        var pager = $scope.pager;
        pager.number=1;
        pager.parameter = {
            cityCode: searchObj.cityCode?searchObj.cityCode:undefined,
            hospitalName: searchObj.hospitalName?searchObj.hospitalName:undefined
        };
        $sessionStorage.hospitalListPager = pager;
        $scope.getResponseFunction(pager);
    };

    $scope.fileUpload = {
        url:$scope.baseURL+'/api/hospital/upload',
        content: '医院导入',
        goPage: $scope.goPage,
        name: 'file',
        uid:$localStorage.userId
    }

    $scope.hospitalEdit=function(id){
        $state.go('app.ihd.hospitalEdit',{id:id})
    }

    $scope.goDepartment = function(item){
        $sessionStorage.hospitalCode = item.hospitalCode;
        $sessionStorage.hospitalName = item.hospitalName;
        $state.go('app.ihd.department',{hospitalCode:item.hospitalCode})
    }

    $scope.doctorEdit = function(item){
        $sessionStorage.hospitalCode = item.hospitalCode;
        $sessionStorage.hospitalName = item.hospitalName;
        $state.go('app.ihd.doctor',{hospitalCode:item.hospitalCode})
    }

    $scope.hospitalMerchantEdit = function(id){
      $state.go('app.ihd.hospitalMerchantEdit',{id:id})
    }
}])
