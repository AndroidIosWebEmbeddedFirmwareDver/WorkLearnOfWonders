app.controller('BannerSettingCtrl', ['$scope', '$http', '$state','$filter', function($scope, $http, $state,$filter) {
    $scope.pager = {
        "totalPages":0,     //总页数
        "totalElements":0,  //总条数
        "size": 10,         //页大小
        "number": 1,        //页码
        "parameter":{},
        "data":[]
    };
    $scope.bannerObj={};

    $scope.getResponseFunction=function(pager){
        pager.parameter.adcode=0;
        pager.totalPages=0;
        pager.totalElements=0;
        $http.post($scope.baseURL + '/api/imagetext/findImageTextByAdcode',pager)
            .then(function(response) {
                if(response.data.data.length){
                    $scope.pager = response.data;
                }else{
                    layer.msg('未查询到数据');
                }
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

    $scope.search = function(bannerObj){
        var pager = $scope.pager;
        bannerObj.startTime = bannerObj.startTime?$filter('date')(bannerObj.startTime, "yyyy-MM-dd HH:mm:ss"):'';
        bannerObj.endTime = bannerObj.endTime?$filter('date')(bannerObj.endTime, "yyyy-MM-dd HH:mm:ss"):'';
        var starttime=bannerObj.startTime?$filter('timeFormat')(bannerObj.startTime):'';
        var endtime=bannerObj.endTime?$filter('timeFormat')(bannerObj.endTime):'';
        if(starttime && endtime && starttime>=endtime){
            layer.msg('显示开始时间应早于结束时间');
        }else{
            pager.parameter = {
                delFlag: bannerObj.delFlag?bannerObj.delFlag:'',
                startTime: bannerObj.startTime?bannerObj.startTime:'',
                endTime:bannerObj.endTime?bannerObj.endTime:''
            };
            pager.data=[];
            $scope.getResponseFunction(pager);
        }
    };

    $scope.addBanner = function(){
        $state.go('app.cps.bannerSettingEdit');
    };

    $scope.editBanner = function(id){
        $state.go('app.cps.bannerSettingEdit',{ id : id });
    };

    $scope.changeBanner=function(bannerObj){
        var mess=bannerObj.del_flag==1?'确定要显示吗？':'确定要不显示吗？';
        $scope.param={
            id:bannerObj.id,
            imgUrl:bannerObj.imgUrl,
            mainTitle:bannerObj.mainTitle,
            hoplink:bannerObj.hoplink?bannerObj.hoplink:'',
            startTime:bannerObj.startTime,
            endTime:bannerObj.endTime,
            sequence:bannerObj.sequence,
            delFlag:bannerObj.delFlag==0?1:0,
            adcode:0,
        };
        var lay = layer.confirm(mess,{
            btn: ['确定','取消']
        },function(){
            layer.close(lay);
            $http.post($scope.baseURL + '/api/imagetext/saveImageText',$scope.param)
                .then(function(response) {
                    $scope.goPage($scope.pager.number);
                },function(error) {
                    layer.msg(error.data.msg);
                });
        },function(){
            layer.close(lay);
        });
    };
}]);