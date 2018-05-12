app.controller('QiniuUploadCtrl', ['$scope', function ($scope) {
    $scope.imageUrl = "";
    $scope.getUrl = function(url){
        $scope.imageUrl = url;
    }
}]);