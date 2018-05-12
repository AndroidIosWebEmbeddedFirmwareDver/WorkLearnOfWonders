app.controller('imformationArticlesController',['$scope','$http','$state','$stateParams',function($scope,$http,$state,$stateParams){
    $scope.pager = {
      total_pages: 0,
      total_elements: 0,
      size: 20,
      number: 1,
      parameter: {},
      data: [],
    }
    $scope.pager.parameter.type = '1'

    if($stateParams.id){
      $scope.pager.parameter.categoryId = $stateParams.id
    }

    $scope.goPage = function(number){
      $scope.pager.parameter.type = '1'
      var pager = $scope.pager
      pager.number = number
      $http
        .post($scope.baseURL + '/back/article/list',pager)
        .success(function(rs){
          $scope.pager = rs
        })
        .catch(function(e){
          layer.msg('Server Error', {icon: 5})
        })
    }
    $scope.goPage(1); //初始化查询

    // 搜索文章
    $scope.search = function(){
        var today = new Date()
        $scope.pager.parameter.startTime == null ? $scope.pager.parameter.startTime = undefined : $scope.pager.parameter.startTime = $scope.pager.parameter.startTime
        $scope.pager.parameter.endTime == null ? $scope.pager.parameter.endTime = undefined : $scope.pager.parameter.endTime = $scope.pager.parameter.endTime
        if($scope.pager.parameter.startTime > $scope.pager.parameter.endTime){
            layer.msg('开始日期应大于结束日期!')
            return
        }else if($scope.pager.parameter.startTime > today){
            layer.msg('开始日期大于今天，请重新输入')
            return
        }else if($scope.pager.parameter.endTime > today){
            layer.msg('结束日期大于今天，请重新输入')
            return
        }
        var pager = $scope.pager
        for(var i in pager.parameter){
          if(!pager.parameter[i]){
            delete pager.parameter[i]
          }
        }
        pager.parameter.startTime = $scope.dateToStr($scope.pager.parameter.startTime)
        pager.parameter.endTime = $scope.dateToStr($scope.pager.parameter.endTime)

        var pager = $scope.pager

        pager.number = 1
        $http
          .post($scope.baseURL + '/back/article/list',pager)
          .success(function(rs){
              $scope.pager = rs
          })
          .catch(function(e){
            layer.alert('Server Error', {icon: 5})
          })
    }

    $scope.articleAdd = function(){
      $state.go('app.im.informationArticlesEdit',{c_id:$stateParams.id})
    }

    $scope.articleEdit = function(id){
      $state.go('app.im.informationArticlesEdit',{id:id,c_id:$stateParams.id})
    }

    $scope.changeVisable = function(articleObj){
      var pager = {
        id: articleObj.id,
        is_visable: articleObj.is_visable==1 ? 0:1,
      }
      var tips = articleObj.state==0 ? '真的要启用吗':'真的要禁用吗'
      var _layer=layer.alert(tips,{
          btn: ['确定','取消']
        },function(){
          $http
            .post($scope.baseURL + '/back/article/areaArticleUpdate',pager)
            .success(function(rs){
              if(rs.code == 0){
                layer.msg(rs.msg)
                $scope.goPage(1)
              }else{
                layer.msg(rs.msg)
              }
            })
            .catch(function(e){
              layer.alert('Server Error', {icon: 5})
            })
          layer.close(_layer)
        })
    }

    $scope.dateToStr = function(datetime){
        if(datetime instanceof Date){
            var year = datetime.getFullYear()
             var month = datetime.getMonth()+1
             var date = datetime.getDate()
             var hour = datetime.getHours()
             var minutes = datetime.getMinutes()
             var second = datetime.getSeconds()

             if(month<10){
              month = "0" + month
             }
             if(date<10){
              date = "0" + date
             }
             if(hour <10){
              hour = "0" + hour
             }
             if(minutes <10){
              minutes = "0" + minutes
             }
             if(second <10){
              second = "0" + second
             }

             var time = year+"-"+month+"-"+date+" "+hour+":"+minutes+":"+second
             return time
        }else{
            return datetime
        }
    }
}])
