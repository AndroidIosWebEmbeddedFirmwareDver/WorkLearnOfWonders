app.controller('addAccountController',['$scope','$http','$state','$stateParams',function($scope,$http,$state,$stateParams){
    $scope.accountObj={
        roleIds:""
    };
    $scope.isShow=true;
    $scope.getRoleList=function(){
        $http.post($scope.baseURL + '/api/basicInfo/role/list',{})
            .then(function(response) {
                $scope.roleList = response.data.data;
            },function(error) {
                layer.msg('Server Error');
            });
    };
    $scope.getRoleList();

    if ($stateParams.id) {
        $scope.title = "编辑账号";
        $scope.isShow=false;
        $http.get($scope.baseURL + '/api/basicInfo/userAdd?userId='+$stateParams.id)
            .then(function(response) {
                $scope.accountObj=response.data.data;
                $scope.accountObj.roleIds=response.data.data.roleList[0].roleId;
            },function(error) {
                layer.msg('Server Error');
            });
    }else{
        $scope.title = "添加账号";
        $scope.isShow=true;
    }

    $scope.checkName=function(name){
        $http.get($scope.baseURL + '/api/basicInfo/user/nameExist?loginname='+name)
            .then(function(response) {
                if(response.data.code==1000){
                    layer.msg(response.data.msg);
                }
            },function(error) {
                layer.msg('Server Error');
            });
    };

    $scope.checkPassword=function(psw){
        // var reg=/^[0-9a-zA-Z]{6,18}$/;
        var reg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}$/;
        if(reg.test(psw)==false){
            layer.msg('密码长度应为6-18位，由数字和大小写字母组合而成');
            $scope.accountObj.password='';
        }
    };

    $scope.submitAccount=function(accountObj){
        if(accountObj.password && accountObj.password2 && accountObj.password!=accountObj.password2){
            layer.msg('两次密码输入不一样');
        }else{
            var PublicKey = 'MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmtCx3tfNiVd3Hsaxs+ohi/kbFhCotz53Haa+tw61U7tjM6vYgBlrgBX3heFYU8yi5u0h09UtsMVjjhEiMNzfRLOYxu9tqg1u4IgqZhijXQHRLckpjYsmAO7SDEhiyXHuP41X8hTU19wPDGgi5L9wS4ByQIiEt151+7u+yKEFMcD9u23RCtbiIej+QMlq3mwhGnRVbZoBfGhKQqbzJjL2J5ZNrry4xxJrrTxiLoWSRvuICrthdLQOQ7Mh5Z+b3bYwdBWiJoJ7eta5dTHAQzp4MTM/HXtIDj/C8+UKRB9UjxvnJCFC7i6dIEfKqTfDg1uWKhVX/LeODmpsiLvxnQrBOwIDAQAB'
            var crypt = new JSEncrypt()
            crypt.setPublicKey(PublicKey)
            var psdResult = crypt.encrypt(accountObj.password)
            $scope.param={
                loginname:accountObj.loginname,
                username:accountObj.username,
                password:psdResult,
                delFlag:0,
                roleIds:accountObj.roleIds
            };

            if($stateParams.id){
                $scope.param.userId=$stateParams.id;
            }else{
                var psdResult2 = crypt.encrypt(accountObj.password2)
                $scope.param.password2=psdResult2;
            }
            $http({
                method:"POST",
                url:$scope.baseURL + '/api/basicInfo/user/update',
                data:$scope.param,
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                transformRequest:function(obj){
                    var str=[];
                    for(var p in obj){
                        str.push(encodeURIComponent(p)+"="+encodeURIComponent(obj[p]));
                    }
                    return str.join("&");
                }
            }).then(function(response) {
                if(response.data.code == 0){
                    layer.msg(response.data.msg);
                    setTimeout(function(){
                        $state.go('app.rbac.userManage');
                    },1000);
                }else{
                    layer.msg(response.data.msg);
                }
            },function(error) {
                layer.msg('Server Error');
            });
        }
    };
}]);
