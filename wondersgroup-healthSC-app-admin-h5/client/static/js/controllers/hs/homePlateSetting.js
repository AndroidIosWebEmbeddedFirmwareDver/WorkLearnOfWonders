app.controller('HealthSettingCtrl', ['$scope', '$http', '$state','$filter', function($scope, $http, $state,$filter) {
    $scope.pager = {
        "totalPages":0,     //总页数
        "totalElements":0,  //总条数
        "size": 10,         //页大小
        "number": 1,        //页码
        "parameter":{gadcode:13},
        "data":[]
    };
    $scope.homeObj={};

    $scope.getVersionList=function(){
        $http.get($scope.baseURL + '/api/imagetext/findGImageTextVersions?gadcode=' + 13)
            .then(function(response) {
                $scope.versionList = response.data.data;
            },function(error) {
                layer.msg('Server Error');
            });
    };
    $scope.getVersionList();

    $scope.getHomeList=function(version){
        var pager=$scope.pager;
        pager.parameter.version=version;
        $http.post($scope.baseURL + '/api/imagetext/findGImageTextList',pager)
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
    $scope.getHomeList('');

    $scope.search = function(homeObj){
        $scope.getHomeList(homeObj.version);
    };

    $scope.editHome = function(id){
        $state.go('app.hs.homePlateSettingEdit',{ id : id,type:'edit' });
    };

    $scope.copyHome=function(id){
        $state.go('app.hs.homePlateSettingEdit',{ id:id,type:'copy' });
    };

}]);
