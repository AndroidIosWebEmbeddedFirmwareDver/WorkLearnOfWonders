app.controller('hospitalMerchantEdit',['$scope','$http','$state','$stateParams',function($scope,$http,$state,$stateParams){
      $scope.hospitalObj={
          status:1
      };
      $http.get($scope.baseURL +'/api/hospital/view?id='+ $stateParams.id)
            .success(function(rs){
                if(rs.code ==0){
                    $scope.hospitalObj = rs.data;
                }else{
                    layer.msg(rs.msg)
                }
            })
            .catch(function(e){
                layer.msg(e.data.msg)
            })

      // 保存
      $scope.saveData=function(){
          $http.post($scope.baseURL + '/api/hospital/save',$scope.hospitalObj)
              .success(function(rs){
                  if(rs.code == 0){
                      var _layer = layer.alert(rs.msg,function(){
                          $state.go('app.ihd.hospital',{flag:1})
                          layer.close(_layer)
                      })
                  }else{
                      layer.msg(rs.msg)
                  }
              })
              .catch(function(e){
                  layer.msg(e.data.msg)
              })
      }

      $scope.goBack=function() {
          $state.go('app.ihd.hospital',{flag:1})
      };
}])
