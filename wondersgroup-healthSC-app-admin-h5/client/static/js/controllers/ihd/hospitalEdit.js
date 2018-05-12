app.controller('hospitalEditController',['$scope','$http','$state','$stateParams',function($scope,$http,$state,$stateParams){
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
      $scope.setPicurl = function(url){
          $scope.hospitalObj.hosptialPhoto = url;
      };

      $scope.onClose = function(){
          $scope.hospitalObj.hosptialPhoto = "";
      };

      // 保存
      $scope.saveData=function(){
          if($scope.hospitalObj.isOpenEmails ==1 && $scope.hospitalObj.customEmails.length ==0){
            return layer.msg('请输入邮箱')
          }
          if($scope.hospitalObj.hosptialPhoto){
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
          }else{
              layer.msg('请上传图片');
          }
      }

      $scope.goBack=function() {
          $state.go('app.ihd.hospital',{flag:1})
      };
}])
