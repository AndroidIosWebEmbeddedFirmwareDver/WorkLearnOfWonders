app.controller('NoticeAddCtrl', ['$scope', '$http', '$state', '$stateParams','$filter',function($scope, $http, $state,$stateParams,$filter) {
    $scope.noticeObj ={
        isShow:false
    };
    $scope.url='/message/add';

    if ($stateParams.id) {
        $scope.title = "编辑通知栏设置";
        $scope.noticeObj.id=$stateParams.id;
        $scope.url='/message/update';
        $http.get($scope.baseURL+'/message/detail?id='+$stateParams.id)
            .then(function(rs) {
                var data = rs.data;
                if(data.code == 0) {
                    $scope.noticeObj = data.data;
                }
            },function(error) {
                layer.msg(error.data.msg);
            });
    }else{
        $scope.title = "添加通知栏设置";
    }
    $scope.sendFun=function (url) {
        $http.post($scope.baseURL+url,$scope.param)
            .then(function(response) {
                if(response.data.code == 0){
                    layer.msg(response.data.msg);
                    setTimeout(function(){
                        $state.go('app.systemNotification');
                    },1500);
                }else{
                    layer.msg(response.data.msg);
                }
            },function(error) {
                layer.msg(error.data.msg);
            });
    }

    $scope.submitNotice = function(noticeObj){
        $scope.param={
            content: noticeObj.content,
            url: noticeObj.url?noticeObj.url:'',
            isShow: noticeObj.isShow,
            title:noticeObj.title
        };
        if($stateParams.id){
            $scope.param.id=$stateParams.id;
        }
        $scope.sendFun($scope.url);
    };
}]);