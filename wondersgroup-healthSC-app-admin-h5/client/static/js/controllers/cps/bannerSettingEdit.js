app.controller('BannerAddCtrl', ['$scope', '$http', '$state', '$stateParams','$filter',function($scope, $http, $state,$stateParams,$filter) {
    $scope.bannerObj ={};

    if ($stateParams.id) {
        $scope.title = "Banner设置编辑";
        $scope.bannerObj.id=$stateParams.id;
        $http.get($scope.baseURL+'/api/imagetext/findImageTextById?id='+$stateParams.id)
            .then(function(rs) {
                var data = rs.data;
                if(data.code == 0) {
                    $scope.bannerObj = data.data;
                    $scope.bannerObj.startTime= $filter('date')(data.data.startTime, "yyyy-MM-dd HH:mm:ss");
                    $scope.bannerObj.endTime= $filter('date')(data.data.endTime, "yyyy-MM-dd HH:mm:ss");
                    $scope.bannerObj.sequence=Number($scope.bannerObj.sequence);
                }
            },function(error) {
                layer.msg(error.data.msg);
            });
    }else{
        $scope.title = "Banner设置添加";
    }

    $scope.setPicurl = function(url){
        $scope.bannerObj.imgUrl = url;
    };

    $scope.onClose = function(){
        $scope.bannerObj.imgUrl = "";
    };

    $scope.submitBanner = function(bannerObj){
        bannerObj.startTime = $filter('date')(bannerObj.startTime, "yyyy-MM-dd HH:mm:ss");
        bannerObj.endTime = $filter('date')(bannerObj.endTime, "yyyy-MM-dd HH:mm:ss");
        var startTime=bannerObj.startTime?$filter('timeFormat')(bannerObj.startTime):'';
        var endTime=bannerObj.endTime?$filter('timeFormat')(bannerObj.endTime):'';
        if(!bannerObj.imgUrl){
            layer.alert('请选择图片');
            return;
        }
        if(startTime && endTime && startTime>=endTime){
            layer.alert('展示开始时间应早于结束时间');
        }else{
            $scope.param={
                imgUrl:bannerObj.imgUrl,
                mainTitle:bannerObj.mainTitle,
                hoplink:bannerObj.hoplink?bannerObj.hoplink:'',
                startTime:bannerObj.startTime,
                endTime:bannerObj.endTime,
                sequence:bannerObj.sequence,
                adcode:0,
                delFlag:0
            };
            if($stateParams.id){
                $scope.param.id=$stateParams.id;
            }
            $http.post($scope.baseURL+'/api/imagetext/saveImageText',$scope.param)
                .then(function(response) {
                    if(response.data.code == 0){
                        layer.msg(response.data.msg);
                        setTimeout(function(){
                            $state.go('app.cps.bannerSetting');
                        },1000);
                    }else{
                        layer.msg(response.data.msg);
                    }
                },function(error) {
                    layer.msg(error.data.msg);
                });
        }
    };
}]);