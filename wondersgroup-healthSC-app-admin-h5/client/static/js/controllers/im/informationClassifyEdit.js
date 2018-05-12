app.controller('informationClassifyEditController',['$scope','$http','$state','$stateParams',function($scope,$http,$state,$stateParams) {
  $scope.editObj = {}
  if($stateParams.id){
    $scope.title = '编辑文章分类'
    $http
      .get($scope.baseURL + '/back/article/categoryInfo?id=' + $stateParams.id)
      .success(function(rs){
        if(rs.code == 0){
          $scope.editObj = rs.data
        }else{
          layer.msg(rs.msg)
        }
      })
      .catch(function(e){
        layer.msg('Server Error', {icon: 5})
      })
  }else{
    $scope.title = '添加文章分类'
    $scope.editObj.is_visable = 0
  }

  // 保存分类
  $scope.saveData = function(){
    if($stateParams.id){
      $scope.editObj.id = $stateParams.id
      delete $scope.editObj.update_time
    }
    $http
      .post($scope.baseURL + '/back/article/categorySave',$scope.editObj)
      .success(function(rs){
        if(rs.code == 0){
          var _layer = layer.alert(rs.msg,function(){
              $state.go('app.im.informationClassify')
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
