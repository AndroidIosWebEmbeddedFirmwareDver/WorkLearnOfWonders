app.controller('imformationClassifyController',['$scope','$http','$state','$filter',function($scope,$http,$state,$filter){
    $scope.pager = {}
    $scope.goPage = function(){
      $http
        .get($scope.baseURL + '/back/article/categoryList')
        .success(function(rs){
          if(rs.code == 0){
            $scope.pager.data = rs.data
          }else{
            layer.msg(rs.msg)
          }
        })
        .catch(function(e){
          layer.msg('Server Error', {icon: 5})
        })
    }
    $scope.goPage() //初始化查询

    $scope.classifyEdit = function(id){
        $state.go('app.im.informationClassifyEdit', {id:id})
    }

    $scope.checkInformationArticles = function(id){
        $state.go('app.im.informationArticles', {id:id})
    }

    $scope.changeVisable = function(classObj){
        var pager = {
            id: classObj.id,
            c_name: classObj.c_name,
            is_visable: classObj.is_visable==0 ? 1:0,
            rank: classObj.rank,
        }
        var tips = classObj.is_visable==0 ? '真的要启用吗':'真的要禁用吗'
        var _layer = layer.alert(tips,{
            btn: ['确定', '取消']
        },function(){
            $http
              .post($scope.baseURL + '/back/article/categorySave', pager)
              .success(function(rs){
                if(rs.code == 0){
                  layer.msg(rs.msg)
                  $scope.goPage()
                }else{
                  layer.msg(rs.msg)
                }
              })
              .catch(function(e){
                layer.msg('Server Error', {icon: 5})
              })
            layer.close(_layer)
        })
    }


$scope.cannotSort = false;
    $scope.sortableOptions = {
    // 数据有变化
    update: function(e, ui) {
     console.log("update");
     //需要使用延时方法，否则会输出原始数据的顺序，可能是BUG？
    //  $timeout(function() {
    //   var resArr = [];
    //   for (var i = 0; i < $scope.data.length; i++) {
    //    resArr.push($scope.data[i].i);
    //   }
    //   console.log(resArr);
    //  })


    },

    // 完成拖拽动作
    stop: function(e, ui) {
     //do nothing
    }
   }
}])
