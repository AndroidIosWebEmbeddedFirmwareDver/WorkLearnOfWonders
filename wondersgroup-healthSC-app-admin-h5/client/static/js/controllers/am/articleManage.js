app.controller('articleManageController',['$scope','$http','$state','$stateParams','$sessionStorage',function($scope,$http,$state,$stateParams,$sessionStorage){
    $scope.pager = {
        totalPages: 0,
        totalElements: 0,
        size: 20,
        number: 1,
        parameter: {},
        type: 0,
        data: [],
    }

    if ($sessionStorage.articleSearcher){
        $scope.pager.parameter = $sessionStorage.articleSearcher
    } else {
        $scope.pager.parameter = {}
    }

    $scope.goPage = function(number){
        $scope.pager.parameter.type = '0'
        var pager = $scope.pager
        pager.number = number
        $http
          .post($scope.baseURL + '/back/article/list', pager)
          .success(function(rs){
              $scope.pager = rs
          })
          .catch(function(e){
              layer.msg('Server Error',{icon: 5})
          })
    }
    $scope.goPage(1) //初始化查询

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
        pager.parameter.startTime = $scope.dateToStr($scope.pager.parameter.startTime)
        pager.parameter.endTime = $scope.dateToStr($scope.pager.parameter.endTime)
        for (var i in pager.parameter) {
            if (!pager.parameter[i]) {
                delete pager.parameter[i]
            }
        }
        pager.number = 1
        $http
            .post($scope.baseURL + '/back/article/list',pager)
            .success(function(rs) {
                $scope.pager = rs
                $sessionStorage.articleSearcher = pager.parameter
            })
            .catch(function(e) {
                layer.alert('Server Error', {
                    icon: 5
                })
            })
    }

    $scope.articleEdit = function(id){
        $state.go('app.am.articleManageEdit',{id: id})
    }

    $scope.dateToStr = function(datetime){
        if(datetime instanceof Date){
            var year = datetime.getFullYear()
            var month = datetime.getMonth() + 1
            var date = datetime.getDate()
            var hour = datetime.getHours()
            var minutes = datetime.getMinutes()
            var second = datetime.getSeconds()

            if(month < 10){
                month = "0" + month
            }
            if(date < 10){
                date = "0" + date
            }
            if(hour < 10){
                hour = "0" + hour
            }
            if(minutes < 10){
                minutes = "0" + minutes
            }
            if(second < 10){
                second = "0" + second
            }

            var time = year + "-" + month + "-" + date + " " + hour + ":" + minutes + ":" + second
            return time
        }else{
            return datetime
        }
    }
}])
