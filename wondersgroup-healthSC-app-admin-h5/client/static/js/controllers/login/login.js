'use strict';
app.controller('LoginCtrl', ['$scope', '$http', '$state','$rootScope','$localStorage','$sessionStorage','$interval',
    function ($scope, $http, $state,$rootScope,$localStorage,$sessionStorage,$interval) {
        $scope.user = {
            loginname:"",
            password:"",
            textmessage:""
        }
        $scope.logining = false
        $scope.authError = null
        $scope.intervalHint = "发送短信验证码给管理员";

        //页面定时任务
        const SMS_SEND_INTERVAL_SECOND = 120;
        var index = SMS_SEND_INTERVAL_SECOND;
        function intervalHintStart(){ //短信发送成功，触发定时任务
            index = index - 1;
            $scope.intervalHint = "剩余："+index + " 秒";
            $("#hintBtn").attr("disabled",true);
        }
        function intervalHintSucc(){
            $scope.intervalHint = "重新发送";
            $("#hintBtn").attr("disabled",false);
            index = SMS_SEND_INTERVAL_SECOND;
        }

        //获取登录系统参数
        $scope.configure = {
            smsverification:false,
            superLoginName:"",
            superPhone:"",
            timeFrom:"",
            timeTo:""
        }
        $scope.getConfigure = function (){
            $http
                .get($scope.baseURL + '/api/loginConfigure')
                .success(function(rs){
                    if(rs.code==0){
                        $scope.configure = rs.data;
                    }else{
                        layer.msg(rs.msg)
                    }
                })
        }
        $scope.getConfigure();

        //发送短信
        $scope.sendSMS = function(user){
            $scope.intervalHint = "短信发送中...";
            var PublicKey = 'MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmtCx3tfNiVd3Hsaxs+ohi/kbFhCotz53Haa+tw61U7tjM6vYgBlrgBX3heFYU8yi5u0h09UtsMVjjhEiMNzfRLOYxu9tqg1u4IgqZhijXQHRLckpjYsmAO7SDEhiyXHuP41X8hTU19wPDGgi5L9wS4ByQIiEt151+7u+yKEFMcD9u23RCtbiIej+QMlq3mwhGnRVbZoBfGhKQqbzJjL2J5ZNrry4xxJrrTxiLoWSRvuICrthdLQOQ7Mh5Z+b3bYwdBWiJoJ7eta5dTHAQzp4MTM/HXtIDj/C8+UKRB9UjxvnJCFC7i6dIEfKqTfDg1uWKhVX/LeODmpsiLvxnQrBOwIDAQAB'
            var crypt = new JSEncrypt()
            crypt.setPublicKey(PublicKey)
            var psdResult = crypt.encrypt(user.password)
            var pager = {
                loginname: user.loginname,
                password: psdResult
            }
            $http
                .post($scope.baseURL + '/api/sendSMSToSuper',pager)
                .success(function(rs){
                    if(rs.code==0){
                        layer.msg(rs.msg);
                        $scope.intervalHint = "剩余："+index + " 秒";
                        $interval(intervalHintStart,1000, SMS_SEND_INTERVAL_SECOND).then(intervalHintSucc);
                    }else{
                        layer.msg(rs.msg);
                        $scope.intervalHint = "发送短信验证码给管理员";
                    }
                })
        }

        //登录
        $scope.login = function (user){
            var PublicKey = 'MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmtCx3tfNiVd3Hsaxs+ohi/kbFhCotz53Haa+tw61U7tjM6vYgBlrgBX3heFYU8yi5u0h09UtsMVjjhEiMNzfRLOYxu9tqg1u4IgqZhijXQHRLckpjYsmAO7SDEhiyXHuP41X8hTU19wPDGgi5L9wS4ByQIiEt151+7u+yKEFMcD9u23RCtbiIej+QMlq3mwhGnRVbZoBfGhKQqbzJjL2J5ZNrry4xxJrrTxiLoWSRvuICrthdLQOQ7Mh5Z+b3bYwdBWiJoJ7eta5dTHAQzp4MTM/HXtIDj/C8+UKRB9UjxvnJCFC7i6dIEfKqTfDg1uWKhVX/LeODmpsiLvxnQrBOwIDAQAB'
            var crypt = new JSEncrypt()
            crypt.setPublicKey(PublicKey)
            var psdResult = crypt.encrypt(user.password)
            var pager = {
                loginname: user.loginname,
                password: psdResult,
                textmessage: user.textmessage
            }
            $scope.logining = true
            var t = setTimeout($scope.loginError,20000)
            $http
                .post($scope.baseURL + '/api/login',pager)
                .success(function(rs){
                    clearTimeout(t)
                    $scope.logining = false;
                    if(rs.code==0){
                        document.cookie = 'uid='+rs.data.sessionId
                        // 保存信息
                        $localStorage.loginName = rs.data.loginName
                        $localStorage.userName = rs.data.userName
                        $localStorage.Menus = rs.data.menu.children
                        $sessionStorage.sessionId = rs.data.sessionId
                        $sessionStorage.userId = rs.data.userId
                        $state.go('app.defaultStart')
                    }else{
                        layer.msg(rs.msg)
                    }
                })
                .catch(function(e){
                    $scope.logining = false
                    clearTimeout(t)
                    layer.msg(e.status+'---'+e.statusText)
                })
        }
        $scope.loginError = function(){
            $scope.logining = false
            layer.msg('登录失败，请稍后重试!')
            $scope.$apply()
        }
    }])
