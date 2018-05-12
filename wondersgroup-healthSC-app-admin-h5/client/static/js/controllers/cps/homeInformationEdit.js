app.controller('AddInformationCtrl',['$scope','$http','$stateParams','$filter','$state',function($scope, $http,$stateParams, $filter,$state) {
    $scope.infoObj={};
    $scope.allowEdit=false;
    if ($stateParams.id) {
        $scope.title = "编辑首页资讯";
        $scope.infoObj.id=$stateParams.id;
        $scope.allowEdit=false;
        $http.get($scope.baseURL +'/back/home/article/detail?id='+$stateParams.id)
            .then(function(rs) {
                if(rs.data.code == 0) {
                    $scope.infoObj = rs.data.data[0];
                    $scope.infoObj.start_time=$filter('date')($scope.infoObj.start_time,'yyyy-MM-dd HH:mm:ss');
                    $scope.infoObj.end_time=$filter('date')($scope.infoObj.end_time,'yyyy-MM-dd HH:mm:ss');
                }
            },function(rs) {
                layer.msg(rs.data.msg);
            });
    }else{
        $scope.title = "新增首页资讯";
        $scope.allowEdit=true;
    }

    $scope.getArticleTitle=function(id){
        if (id) {
            $http.get($scope.baseURL +'/back/article/info?id='+id)
                .then(function(rs) {
                    if(rs.data.code == 0) {
                        if(rs.data.data){
                            $scope.infoObj=rs.data.data;
                            $scope.infoObj.article_id=$scope.infoObj.id;
                        }else{
                            layer.msg('您输入的文章ID不存在，请检查');
                            $scope.infoObj={};
                            $scope.infoObj.thumb="img/nopic.gif";
                        }
                    }
                },function(rs) {
                    layer.msg(rs.data.msg);
                });
        }else{
            layer.msg("请填写文章ID！")
        }
    }

    $scope.submitInformation=function(infoObj){
        var data = infoObj;
        data.start_time = $filter('date')(data.start_time, "yyyy-MM-dd HH:mm:ss");
        data.end_time = $filter('date')(data.end_time, "yyyy-MM-dd HH:mm:ss");
        var starttime=data.start_time?$filter('timeFormat')(data.start_time):'';
        var endtime=data.end_time?$filter('timeFormat')(data.end_time):'';
        if(starttime && endtime && starttime>=endtime){
            layer.msg('显示开始时间应早于结束时间');
        }else{
            $scope.param={
                article_id: infoObj.article_id,
                rank: infoObj.rank,
                is_visable: 1,
                start_time:data.start_time,
                end_time:data.end_time
            };
            if($stateParams.id){
                $scope.param.id= $stateParams.id;
                $scope.url='/back/home/article/update';
            }else{
                $scope.url='/back/home/article/save';
            }
            $http.post($scope.baseURL +$scope.url, $scope.param)
                .then(function(response) {
                    if(response.data.code==0){
                        layer.msg(response.data.msg);
                        setTimeout(function(){
                            $state.go('app.cps.homeInformation');
                        },1500);
                    }else{
                        layer.msg(response.data.msg);
                    }
                }, function(response) {
                    layer.msg(response.data.msg);
                });
        }
    };
}]);