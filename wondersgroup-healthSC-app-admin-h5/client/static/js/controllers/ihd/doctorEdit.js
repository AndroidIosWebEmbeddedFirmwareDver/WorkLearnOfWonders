app.controller('doctorEditController',['$scope','$http','$stateParams','$state',function($scope,$http,$stateParams,$state){
    $scope.doctorObj = {};
    $scope.firstDepts = [];
    $scope.secondDeptsLab = [];
    $scope.secondDepts = [];

    // 获取科室列表
    $http.get($scope.baseURL + '/api/department/menuList?hospitalCode=' + $stateParams.hospitalCode)
        .success(function(rs){
            if(rs.code == 0){
              $scope.firstDepts = rs.data.firstDepts;
              $scope.secondDeptsLab = rs.data.secondDepts;
              $scope.secondDepts = $scope.secondDeptsLab[parseInt($scope.doctorObj.topDeptCode)];
            }else{
              layer.msg(rs.data);
            }
        })
        .catch(function(e){
          layer.msg('Server Error', {icon: 5})
        })

    // 获取医生详情
    $http.get($scope.baseURL + '/api/doctor/view?id=' + $stateParams.id)
        .success(function(rs){
            if(rs.code == 0){
              $scope.doctorObj = rs.data;
              $scope.secondDepts = $scope.secondDeptsLab[parseInt($scope.doctorObj.topDeptCode)];
            }else{
              layer.msg(rs.data)
            }
        })
        .catch(function(e){
            layer.msg('Server Error', {icon: 5})
        })

    // 监听主科室变化，改变副科室
    $scope.$watch('doctorObj.topDeptCode',function(newValue,oldValue){
        $scope.secondDepts = $scope.secondDeptsLab[newValue];
    })

    // 保存
    $scope.saveData = function(doctorObj){
        var data = doctorObj;
        if(!data.headphoto){
          return layer.msg('请上传头像')
        }
        data.id = $stateParams.id;
        $http.post($scope.baseURL +'/api/doctor/save',data)
            .success(function(rs){
                if(rs.code == 0){
                    var _layer = layer.alert(rs.msg,function(){
                        $state.go('app.ihd.doctor',{hospitalCode:$stateParams.hospitalCode});
                        layer.close(_layer)
                    })
                }
            })
            .catch(function(e){
                layer.msg('Server Error', {icon: 5})
            })
    }

    $scope.setPicurl = function(url){
        $scope.doctorObj.headphoto = url;
    };

    $scope.onClose = function(){
        $scope.doctorObj.headphoto = "";
    };

    $scope.backToList = function(){
      $state.go('app.ihd.doctor',{hospitalCode:$stateParams.hospitalCode});
    }
}])
