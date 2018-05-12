// config

var app = angular.module('app');

app.factory('sessionInjector', [function() {
    var session = {
        //request:function (request) {
        //    if(request.url.substring(request.url.length-5)!='.html'){
        //        request.url=request.url+';JSESSIONID='+ window.localStorage['sessionId'];
        //    }
        //    console.log(request.url.split('?'));
        //    return request;
        //},
        response: function (response) {
            if (response.data.code == -1000) {
                location.href = 'admin';
            }
            return response;
        }
    };
    return session;
}]);
app.config(
    ['$controllerProvider', '$compileProvider', '$filterProvider', '$provide', '$httpProvider',
        function($controllerProvider, $compileProvider, $filterProvider, $provide, $httpProvider) {
            // lazy controller, directive and service
            app.controller = $controllerProvider.register;
            app.directive = $compileProvider.directive;
            app.filter = $filterProvider.register;
            app.factory = $provide.factory;
            app.service = $provide.service;
            app.constant = $provide.constant;
            app.value = $provide.value;
            app.httpHeaders = $httpProvider.defaults.headers.common;
            app.headers=$httpProvider.defaults.headers.common;

            //监听接口请求
            $httpProvider.interceptors.push('sessionInjector');
        }
    ]);
