app.controller('helpCenterMoreEditController',['$scope','$http','$state','$stateParams',function($scope, $http, $state,$stateParams){
    $scope.questionObj = {}
    if($stateParams.id){
        $scope.title = '编辑问题'
        $http
          .get($scope.baseURL + '/api/helpCenter/detail?platform=2&id=' + $stateParams.id)
          .success(function(rs){
            if(rs.code == 0){
              $scope.questionObj = rs.data
            }else{
              layer.msg(rs.msg)
            }
          })
          .catch(function(e){
            layer.msg('Server Error', {icon: 5})
          })
    }else{
      $scope.title = '添加问题'
      $scope.questionObj.is_visable = '1'
    }

    // 保存问题
    $scope.saveData = function(){
      if(!$scope.questionObj.content){
          layer.msg('请填写问题详情!')
          return
      }
      $scope.questionObj.platform = 2
      $scope.questionObj.isVisable = $scope.questionObj.is_visable
      delete $scope.questionObj.is_visable
      if($stateParams.id){
        $scope.questionObj.id = $stateParams.id
        var url = '/api/updateHelpCenter'
      }else{
        var url = '/api/saveHelpCenter'
      }
      $http
        .post($scope.baseURL + url,$scope.questionObj)
        .success(function(rs){
          if(rs.code == 0){
            var _layer = layer.alert(rs.msg,function(){
              $state.go('app.help.helpCenterSL')
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
}])
