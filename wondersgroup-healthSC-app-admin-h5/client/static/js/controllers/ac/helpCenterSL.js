var idList=[]
app.controller('helpCenterMoreController',['$scope','$http','$state',function($scope,$http,$state){
    $scope.pager={
        totalPages:1,
        totalElements:2,
        size: 20,
        number: 1,
        parameter:{},
        data:[]
    }

    $scope.goPage=function(number){
        idList=[]
        $http
          .get($scope.baseURL+'/api/helpCenter/find?page='+(number-1) + '&platform=2')
          .success(function(rs){
              if (rs.code == 0) {
                  var  data=rs.data
                  $scope.pager={
                      totalPages:data.total_pages,
                      totalElements:data.total_elements,
                      size: data.size,
                      number: data.number+1,
                      parameter:{},
                      data:data.content,
                  }
              }else{
                  layer.msg(rs.data.msg)
              }
          })
          .catch(function(e){
            layer.msg('Server Error', {icon: 5})
          })
    }
    $scope.goPage(1) //初始化查询

    $scope.deleteItems=function(){
        var idstr = idList.toString()
        if(idList.length==0){
          layer.msg('请选择列表项!')
          return
        }
        var _layer = layer.alert('真的要删除吗?',{
          btn: ['确定','取消']
        },function(){
            $http
              .delete($scope.baseURL+'/api/deleteHelpCenter?ids='+idstr)
              .success(function(rs){
                  if(rs.code == 0){
                      layer.msg(rs.msg)
                      $scope.goPage($scope.pager.number)
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

    $scope.questionEdit = function(id){
        $state.go('app.help.helpCenterSLEdit',{id:id})
    }
}])
