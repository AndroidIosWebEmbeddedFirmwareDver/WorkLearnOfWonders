app.controller('articleManageEditController',['$scope','$http','$state','$stateParams',function($scope,$http,$state,$stateParams){
  $scope.articleObj = {}
  if($stateParams.id){
    $scope.title = '编辑文章'
    $http
      .get($scope.baseURL + '/back/article/info?id=' + $stateParams.id)
      .success(function(rs){
          if(rs.code == 0){
            $scope.articleObj = rs.data
          }else{
            layer.msg(rs.msg)
          }
      })
      .catch(function(e){
        layer.alert('Server Error', {icon: 5})
      })
  }else{
    $scope.title = '添加文章'
  }

  $scope.getUrl = function(url){
    $scope.articleObj.thumb = url
  }

  $scope.onClose = function(){
    $scope.articleObj.thumb =  ''
  };

  // $scope.titleCheck = true
  // $scope.checkBytes = function(msg,length){
  //   if(!msg){
  //     return
  //   }
  //   var len = msg.length
  //   var strLength = 0;
  //   var xx = ''
  //   for(var i=0;i<len;i++){
  //       if(msg.charCodeAt(i)>255){
  //         strLength +=2
  //       }else{
  //         strLength ++
  //       }
  //
  //       xx += msg[i]
  //       if(strLength >= (length/16)){
  //         if(length == 512){
  //           $scope.articleObj.title = xx
  //         }else{
  //           $scope.articleObj.brief = xx
  //         }
  //         // layer.msg('您输入的内容超过了'+length+'bytes')
  //         return
  //       }
  //   }
  // }

  $scope.saveData = function(){
      if($stateParams.id){
        $scope.articleObj.id = $stateParams.id
      }
      if(!$scope.articleObj.thumb){
        layer.msg('请上传图片')
        return
      }
      $http
        .post($scope.baseURL + '/back/article/save',$scope.articleObj)
        .success(function(rs){
            if(rs.code == 0){
              var _layer = layer.alert(rs.msg,function(){
                $state.go('app.am.articleManage')
                layer.close(_layer)
              })
            }else{
              layer.msg(rs.msg)
            }
        })
        .catch(function(e){
          layer.alert('Server Error', {icon: 5})
        })
  }
}])
