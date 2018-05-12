app.controller('AreaAddressSettingCtrl', ['$scope', '$http', '$state', function($scope, $http, $state){
    $scope.search = function(){
        $http.get($scope.baseURL + '/api/serverConfig/queryArea')
            .then(function(response) {
                if(response.data.data.length){
                    $scope.pager = response.data;
                }else{
                    layer.msg('未查询到数据');
                }
            });
    };
    $scope.search();

    $scope.editAddressSetting = function(areaCode){
        $state.go('app.addAddressSetting',{ id : areaCode });
    };
}]);
