app.controller('NoticeSettingCtrl', ['$scope', '$http', '$state','$filter', function($scope, $http, $state,$filter) {
    $scope.pager = {
        "totalPages":0,     //总页数
        "totalElements":0,  //总条数
        "size": 10,         //页大小
        "number": 1,        //页码
        "parameter":{},
        "data":[]
    };
    $scope.goPage=function(number){
        $scope.pager.number=number;
        $http.post($scope.baseURL + '/message/queryList',{size:$scope.pager.size,number:number})
            .then(function(response) {
                $scope.pager = response.data.data;
            },function(error) {
                layer.msg('Server Error');
            });
    };
    $scope.goPage($scope.pager.number);


    $scope.editNotice = function(id){
        $state.go('app.systemNotificationEdit',{ id : id });
    };

    $scope.changeNotice = function(notice){
        var mess=notice.isShow?'确定不显示了吗？':'确定需要显示吗？';
        $scope.param={
            id:notice.id,
            content: notice.content,
            url: notice.url,
            isShow: !notice.isShow
        };
        var lay = layer.confirm(mess,{
            btn: ['确定','取消']
        },function(){
            layer.close(lay);
            $http.post($scope.baseURL + '/message/update',$scope.param)
                .then(function(response) {
                    layer.msg(response.data.msg);
                    $scope.goPage($scope.pager.number);
                },function(error) {
                    layer.msg(error.data.msg);
                });
        },function(){
            layer.close(lay);
        });
    };
}]);