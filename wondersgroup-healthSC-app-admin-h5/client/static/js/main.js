'use strict';

/* Controllers */

angular.module('app')
  .controller('AppCtrl', ['$scope','$localStorage', '$window', '$http','$state','$rootScope','$modal',
    function($scope, $localStorage, $window, $http,$state,$rootScope,$modal) {
      // add 'ie' classes to html
      var isIE = !!navigator.userAgent.match(/MSIE/i);
      isIE && angular.element($window.document.body).addClass('ie');
      isSmartDevice( $window ) && angular.element($window.document.body).addClass('smart');
      // config
      $scope.app = {
        name: '微健康管理后台',
        version: '1.1.0',
        company: '万达信息股份有限公司',
        year: '2017',
        // for chart colors
        color: {
          primary: '#7266ba',
          info:    '#23b7e5',
          success: '#27c24c',
          warning: '#fad733',
          danger:  '#f05050',
          light:   '#e8eff0',
          dark:    '#3a3f51',
          black:   '#1c2b36'
        },
        settings: {
          themeID: 1,
          navbarHeaderColor: 'bg-black',
          navbarCollapseColor: 'bg-white-only',
          asideColor: 'bg-black',
          headerFixed: true,
          asideFixed: false,
          asideFolded: false,
          asideDock: false,
          container: false
        }
      }

      // save settings to local storage
      if ( angular.isDefined($localStorage.settings) ) {
        $scope.app.settings = $localStorage.settings;
      } else {
        $localStorage.settings = $scope.app.settings;
      }
      $scope.$watch('app.settings', function(){
        if( $scope.app.settings.asideDock  &&  $scope.app.settings.asideFixed ){
          // aside dock and fixed must set the header fixed.
          $scope.app.settings.headerFixed = true;
        }
        // save to local storage
        $localStorage.settings = $scope.app.settings;
      }, true);

      function isSmartDevice( $window )
      {
          // Adapted from http://www.detectmobilebrowsers.com
          var ua = $window['navigator']['userAgent'] || $window['navigator']['vendor'] || $window['opera'];
          // Checks for iOs, Android, Blackberry, Opera Mini, and Windows mobile devices
          return (/iPhone|iPod|iPad|Silk|Android|BlackBerry|Opera Mini|IEMobile/).test(ua);
      }

      $scope.getCookie=function(name){
        var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
        if(arr=document.cookie.match(reg)){
          return arr[2];
        } else{
          return null;
        }
      };
      $scope.removeLocalStorage=function () {
        delete $localStorage.parameter;
      }
      // 退出登录
      $scope.closeWindow = function(){
          $http({
                method:"GET",
                url:$scope.baseURL + '/api/logout',
                headers: {'admin-token': $localStorage.sessionId},
              })
              .success(function(rs) {
                if(rs.code==0){
                  delete $localStorage.areaList;
                  window.localStorage.clear();
                  window.sessionStorage.clear();
                  $state.go('access.login');
                }else {
                  layer.msg(rs.msg);
                }
              })
              .catch(function(e){
                layer.msg(error.data.msg)
              })
      };

      // $rootScope.$on('$stateChangeStart',function(event, toState, toParams, fromState, fromParams){
      //   if(toState.name=='access.login')return;// 如果是进入登录界面则允许
      //   // 如果用户不存在
      //   if(!$localStorage.sessionId || !$localStorage.userId || !$localStorage.loginName){
      //     event.preventDefault();// 取消默认跳转行为
      //     $state.go('access.login');//跳转到登录界面
      //   }
      // });

      var queryString = location.search;
      //$http.get('initSystemParam'+queryString).then(function(rs){
      //  if(rs.data.code == 0){
      //    $scope.menuInfo = rs.data.data;
      //  }else{
      //    layer.msg(rs.data.msg);
      //  }
      //});

      $scope.open = function () {
        var modalInstance = $modal.open({
          templateUrl: 'passwordModalContent.html',
          controller: 'passwordModalInstanceCtrl',
          backdrop: 'static',
        });

        modalInstance.result.then(function () {
          $scope.closeWindow();
        }, function () {

        });
      };
  }]);

app.controller('passwordModalInstanceCtrl',function($scope,$http,$modalInstance,$localStorage){
  $scope.issubmiting=false;
  $scope.checkPassword=function (newp,checkp) {
    // var reg=/^[a-zA-Z0-9]{6,18}$/;
    var reg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}$/;
    if(reg.test(newp)==false){
      layer.msg('密码应由6-18位数字和大小写字母组合而成');
      $scope.modify.new='';
    }
    if(!newp){return;};
    if(!checkp){return;};
    if(newp!=checkp){
      layer.msg('两次输入不一致,请重新输入!');
      $scope.modify.new='';
      $scope.modify.check='';
    }
  }
  $scope.checkAgain=function(newp,checkp){
    if(!newp){return;};
    if(!checkp){return;};
    if(newp!=checkp){
      layer.msg('两次输入不一致,请重新输入!');
      $scope.modify.check='';
    }
  }
  $scope.modifyFun=function () {
      $scope.issubmiting=true;
      var PublicKey = 'MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmtCx3tfNiVd3Hsaxs+ohi/kbFhCotz53Haa+tw61U7tjM6vYgBlrgBX3heFYU8yi5u0h09UtsMVjjhEiMNzfRLOYxu9tqg1u4IgqZhijXQHRLckpjYsmAO7SDEhiyXHuP41X8hTU19wPDGgi5L9wS4ByQIiEt151+7u+yKEFMcD9u23RCtbiIej+QMlq3mwhGnRVbZoBfGhKQqbzJjL2J5ZNrry4xxJrrTxiLoWSRvuICrthdLQOQ7Mh5Z+b3bYwdBWiJoJ7eta5dTHAQzp4MTM/HXtIDj/C8+UKRB9UjxvnJCFC7i6dIEfKqTfDg1uWKhVX/LeODmpsiLvxnQrBOwIDAQAB'
      var crypt = new JSEncrypt()
      crypt.setPublicKey(PublicKey)
      var oldResult = crypt.encrypt($scope.modify.origin)
      var newResult = crypt.encrypt($scope.modify.new)
      var data = {
          oldPassword: oldResult,
          newPassword: newResult,
          userId:$localStorage.userId,
      }
      $http({
          method:"POST",
          url:$scope.baseURL + '/api/basicInfo/user/update/password',
          data:data,
          headers: {'Content-Type': 'application/x-www-form-urlencoded'},
          transformRequest:function(obj){
            var str=[];
            for(var p in obj){
              str.push(encodeURIComponent(p)+"="+encodeURIComponent(obj[p]));
            }
            return str.join("&");
          }
        })
        .success(function(rs){
          if(rs.code==0){
            $scope.issubmiting=false;
            var index=layer.alert(rs.msg,function(){
              layer.close(index);
              $modalInstance.close();
            });
          }else {
            layer.msg(rs.data);
            $scope.modify.check='';
            $scope.issubmiting=false;
          }
        })
        .catch(function(e){
          layer.msg("Server Error")
          $scope.issubmiting=false;
        })
  }
  $scope.cancel = function(){
    $modalInstance.dismiss('cancel'); // 退出
  }
});
