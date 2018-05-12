app.controller('departmentEditController',['$scope','$http','$state','$stateParams',function($scope,$http,$state,$stateParams){
    $scope.departmentObj = {}
    $scope.departmentList = {}
      $http.get($scope.baseURL + '/api/department/view?id=' + $stateParams.id)
          .success(function(rs){
            if(rs.code == 0){
              $scope.departmentObj = rs.data
              if($scope.departmentObj.upperDeptCode == -1){
                $scope.departmentObj.isTop = 1;
              }else{
                $scope.departmentObj.isTop = 0;
              }
            }else{
              layer.msg(rs.msg)
            }
          })
          .catch(function(e){
              layer.msg('Server Error', {icon: 5})
          })

    $http.get($scope.baseURL + '/api/department/topDepts?hospitalCode=' + $stateParams.hospitalCode)
        .success(function(rs){
          if(rs.code == 0){
            $scope.departmentList = rs.data
            // var data = rs.data
            // var resultList = []
            // for(var i = 0;i<data.length;i++){
            //   if(data[i].id != $stateParams.id){
            //     resultList.push(data[i])
            //   }
            // }
          }else{
            layer.msg(rs.msg)
          }
        })
        .catch(function(e){
            layer.msg('Server Error', {icon: 5})
        })

    $scope.saveData = function(departmentObj){
      var data = departmentObj
      data.id = $stateParams.id
      if(data.isTop==1){
        data.upperDeptCode = -1;
      }else{
        if(data.upperDeptCode == -1|| data.upperDeptCode ==''){
          return layer.msg('请选择上级科室')
        }
      }
      $http.post($scope.baseURL + '/api/department/save',data)
          .success(function(rs){
            if(rs.code == 0){
              var _layer = layer.alert(rs.msg,function(){
                  $scope.backToList()
                  layer.close(_layer)
              })
            }else{
              layer.msg(rs.msg)
            }
          })
          .catch(function(e){
            layer.msg('Server Error', {icon: 5})
          })
    }

    $scope.backToList = function(){
      $state.go('app.ihd.department',{hospitalCode: $stateParams.hospitalCode})
    }
}])
