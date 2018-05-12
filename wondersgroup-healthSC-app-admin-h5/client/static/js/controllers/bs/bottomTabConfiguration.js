app.controller('AddTabCtrl', ['$scope', '$http',function ($scope, $http) {
    $scope.pictures=[{},{},{},{},{},{},{},{},{}];
    $scope.dataError=false;
    $scope.pager = {
        "total_pages":0,     //总页数
        "total_elements":0,  //总条数
        "size": 10,         //页大小
        "number": 1,        //页码
        "parameter":{adcode:3},
        "data":[]
    };

    function getIocn(pager){
        $http.post($scope.baseURL +'/api/imagetext/findImageTextByAdcode',pager)
            .success(function(response) {
                var content = response.data;
                if(content.length>0){
                    if(content.length!=9){
                        layer.msg("后台数据出错,请联系管理员清空数据后重传!");
                        $scope.dataError=true;
                        for(var i = 0 ;i < 9;i++){
                            $scope.pictures[i].img_url = "";
                        };
                    }else{
                        for(var i = 0 ;i < 9;i++){
                            $scope.pictures[Number(content[i].sequence)].img_url = content[i].imgUrl;
                            $scope.pictures[Number(content[i].sequence)].id = content[i].id;
                            $scope.pictures[Number(content[i].sequence)].sequence = content[i].sequence;
                        }
                    }
                }else{
                    for(var i = 0 ;i < 9;i++){
                        $scope.pictures[i].img_url = "";
                    }
                }
            });
    }
    getIocn($scope.pager);

    $scope.getUrl = function(url,flg){
        $scope.pictures[flg].sequence = flg;
        $scope.pictures[flg].img_url = url;
    };
    $scope.onClose = function(flg){
        $scope.pictures[flg].img_url = "";
    };

    $scope.savePictures = function(){
        if($scope.dataError){
            layer.msg("后台数据出错,请联系管理员清空数据后重传!");
            return;
        }
        if(imageIsNull()){
            layer.msg('您还有图片没有上传!');
            return;
        }
        for(var i = 0 ;i < 9;i++){
            $scope.pictures[i].del_flag="0";
            $scope.pictures[i].adcode="3";
        }
        $http.post($scope.baseURL +'/api/imagetext/saveBatchImageText',$scope.pictures)
            .success(function(response) {
                layer.msg(response.msg);
                getIocn($scope.pager);
            },function(error) {
                layer.msg(error.msg);
            });
    };

    function imageIsNull(){
        for(var i = 0 ; i < 9 ; i++){
            if($scope.pictures[i].img_url==""){
                return true;
            }
        }
        return false;
    };
}]);
