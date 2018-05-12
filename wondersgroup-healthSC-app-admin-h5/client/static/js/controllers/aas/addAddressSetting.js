app.controller('AddAddressSettingCtrl', ['$scope', '$http', '$state', '$stateParams','$filter',function($scope, $http, $state,$stateParams,$filter) {
    $scope.addressObj ={};
    $scope.areaArr=[];
    $http.get($scope.baseURL + '/api/serverConfig/areaCode')
        .then(function (response) {
            $scope.areaList=response.data.data;
            angular.forEach($scope.areaList,function (value) {
                $scope.areaArr[value.cityCode]=value.cityName;
            })
        })

    if($stateParams.id){
        $scope.isChoose=false;
        $scope.title = "配置编辑";
        $http.get($scope.baseURL+'/api/serverConfig/queryServerConfig?areaCode='+$stateParams.id)
            .then(function(rs) {
                var data = rs.data;
                if(data.code == 0) {
                    $scope.addressObj = data.data;
                }
            },function(error) {
                layer.msg(error.data.msg);
            });
    }else{
        $scope.title = "配置添加";
        $scope.isChoose=true;
    }
    $scope.hasSetting=false;
    $scope.changeArea=function (areaCode) {
        $http.get($scope.baseURL+'/api/serverConfig/queryServerConfig?areaCode='+areaCode)
            .then(function(rs) {
                if(rs.data.data.length>0){
                    layer.msg('该地区已配置，请重新选择');
                    $scope.hasSetting=true;
                }else{
                    $scope.hasSetting=false;
                }
            });
    };

    $scope.saveData = function(addressObj){
        if($stateParams.id){
            $scope.param=addressObj;
            $scope.dealFunction($scope.param);
        }else{
            if(!$scope.hasSetting){
                $scope.param=[
                    {
                        areaName:$scope.areaArr[addressObj[0].areaCode],
                        areaCode:addressObj[0].areaCode,
                        apiUrl:addressObj[0].apiUrl,
                        remark:'预约挂号平台地址',
                        type:'1'
                    },
                    {
                        areaName:$scope.areaArr[addressObj[0].areaCode],
                        areaCode:addressObj[0].areaCode,
                        apiUrl:addressObj[1].apiUrl,
                        remark:'健康档案地址',
                        type:'4'
                    },
                    {
                        areaName:$scope.areaArr[addressObj[0].areaCode],
                        areaCode:addressObj[0].areaCode,
                        apiUrl:addressObj[2].apiUrl,
                        remark:'提取报告',
                        type:'3'
                    },
                    {
                        areaName:$scope.areaArr[addressObj[0].areaCode],
                        areaCode:addressObj[0].areaCode,
                        apiUrl:addressObj[3].apiUrl,
                        remark:'诊间支付/电子处方',
                        type:'2'
                    },
                ];
                $scope.dealFunction($scope.param);
            }else{
                layer.msg('该地区已配置，请重新选择');
            }
        }
    };

    $scope.dealFunction=function (param) {
        $http.post($scope.baseURL+'/api/serverConfig/updateServerConfig',param)
            .then(function(response) {
                if(response.data.code == 0){
                    layer.msg('保存成功');
                    setTimeout(function(){
                        $state.go('app.areaAddressSetting');
                    },1000);
                }else{
                    layer.msg(response.data.msg);
                }
            },function(error) {
                layer.msg(error.data.msg);
            });
    };
}]);
