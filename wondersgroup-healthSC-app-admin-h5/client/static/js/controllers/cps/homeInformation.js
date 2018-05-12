app.controller('InformationCtrl',['$scope', '$http', '$state', function($scope, $http, $state) {
    $scope.pager = {
        "totalPages":0,     //总页数
        "totalElements":0,  //总条数
        "size": 10,         //页大小
        "number": 1,        //页码
        "parameter":{status:'0'},
        "data":[]
    };
    $scope.infoObj={
        status:'0',
        articleId:''
    }

    $scope.statusArr={
        1:'未开始',
        2:'进行中',
        3:'已过期'
    };

    $scope.getResponseFunction=function(pager){
        pager.data=[];
        pager.totalPages=0;
        pager.totalElements=0;
        $http.post($scope.baseURL +'/back/home/article/list',pager)
            .then(function(response) {
                $scope.pager=response.data;
            },function(error) {
                layer.msg(error.data.msg);
            });
    };

    $scope.goPage = function(number){
        var pager = $scope.pager;
        pager.number = number;
        $scope.getResponseFunction(pager);
    };

    $scope.goPage(1); //初始化查询

    $scope.search=function(infoObj){
        var pager = $scope.pager;
        pager.parameter.articleId = infoObj.articleId?infoObj.articleId:'';
        pager.parameter.status=infoObj.status;
        pager.number=1;
        $scope.getResponseFunction(pager);
    };

    $scope.addInformation = function(){
        $state.go('app.cps.homeInformationEdit');
    };

    $scope.changeInfo=function(info){
        $scope.param={
            id:info.id,
            article_id: info.article_id,
            rank: info.rank,
            is_visable: info.is_visable==1?0:1,
            start_time:info.start_time,
            end_time:info.end_time
        };
        var mess=info.is_visable==0?'确定要上线吗？':'确定要下线吗？';
        var lay = layer.confirm(mess,{
            btn: ['确定','取消']
        },function(){
            layer.close(lay);
            $http.post($scope.baseURL + '/back/home/article/update',$scope.param)
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

    $scope.editInfo = function(id){
        $state.go('app.cps.homeInformationEdit',{ id : id });
    };
}]);