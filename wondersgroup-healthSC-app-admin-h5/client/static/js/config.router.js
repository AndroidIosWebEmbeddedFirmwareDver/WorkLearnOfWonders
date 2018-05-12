'use strict';

/**
 * Config for the router
 */
angular.module('app')
    .run(
        ['$rootScope', '$state', '$stateParams',
            function ($rootScope, $state, $stateParams) {
                $rootScope.$state = $state;
                $rootScope.$stateParams = $stateParams;
                $rootScope.baseURL = server;
                $rootScope.h5server = h5server;
            }
        ]
    )
    .config(
        ['$stateProvider', '$urlRouterProvider','$httpProvider',
            function ($stateProvider, $urlRouterProvider) {
                var timer = new Date().getTime();
                $urlRouterProvider
                    .otherwise('/access/login');
                $stateProvider
                /*登录页面*/
                    .state('access', {
                        url: '/access',
                        template: '<div ui-view class="fade-in-right-big smooth"></div>'
                    })
                    .state('access.login', {
                        url: '/login',
                        templateUrl: 'tpl/page_signin.html',
                        resolve: {
                            deps: ['uiLoad',
                                function( uiLoad ){
                                    return uiLoad.load( ['js/controllers/login/login.js'] );
                                }]
                        }
                    })
                    .state('app', {
                        //abstract: true,
                        url: '/app',
                        templateUrl: 'tpl/app.html',
                        controllerProvider:function($rootScope,$http,$localStorage,$sessionStorage){
                            $rootScope.getCookie=function(name){
                                var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
                                if(arr=document.cookie.match(reg)){
                                    return arr[2];
                                } else{
                                    return null;
                                }
                            }
                            $rootScope.Menus = $localStorage.Menus;
                            $rootScope.loginName = $localStorage.loginName;
                            $rootScope.userName = $localStorage.userName;

                            app.headers['sessionId'] = typeof ($sessionStorage.sessionId)!='undefined'?$sessionStorage.sessionId:'';
                            app.headers['userId'] = typeof ($sessionStorage.userId) != 'undefined'?$sessionStorage.userId:'';
                            app.headers['admin-token'] = typeof ($sessionStorage.sessionId)!='undefined'?$sessionStorage.sessionId:'';

                            // $http.get($rootScope.baseURL+'/api/user/validate?token='+$rootScope.getCookie('uid'))
                            //     .success(function(res){
                            //         if(res.code!=0){
                            //             $rootScope.$state.go('access.login');
                            //         }
                            //     })
                            //     .error(function(res){
                            //         $rootScope.$state.go('access.login');
                            //     });
                        }
                    })
                    // 登陆后首页
                    .state('app.defaultStart',{
                        url: '/defaultStart',
                        templateUrl: 'tpl/blocks/defaultStart.html'
                    })
                    // 首页设置
                    .state('app.cps',{
                        url: '/cps',
                        template: '<div ui-view></div>'
                    })
                    .state('app.cps.bannerSetting',{
                        url: '/bannerSetting',
                        templateUrl: 'tpl/cps/bannerSetting.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function($ocLazyLoad){
                                    return $ocLazyLoad.load(['js/controllers/cps/bannerSetting.js?t='+timer]);
                                }]
                        }
                    })
                    .state('app.cps.bannerSettingEdit',{
                        url: '/bannerSettingEdit/:id',
                        templateUrl: 'tpl/cps/bannerSettingEdit.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function($ocLazyLoad){
                                    return $ocLazyLoad.load(['js/controllers/cps/bannerSettingEdit.js?t='+timer]);
                                }]
                        }
                    })
                    .state('app.cps.homePlateSetting',{
                        url: '/homePlateSetting',
                        templateUrl: 'tpl/cps/homePlateSetting.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function($ocLazyLoad){
                                    return $ocLazyLoad.load(['js/controllers/cps/homePlateSetting.js?t='+timer]);
                                }]
                        }
                    })
                    .state('app.cps.homePlateSettingEdit',{
                        url: '/homePlateSettingEdit/:id/:type',
                        templateUrl: 'tpl/cps/homePlateSettingEdit.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function($ocLazyLoad){
                                    return $ocLazyLoad.load(['js/controllers/cps/homePlateSettingEdit.js?t='+timer]);
                                }]
                        }
                    })
                    .state('app.cps.homeInformation',{
                        url: '/homeInformation',
                        templateUrl: 'tpl/cps/homeInformation.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function($ocLazyLoad){
                                    return $ocLazyLoad.load(['js/controllers/cps/homeInformation.js?t='+timer]);
                                }]
                        }
                    })
                    .state('app.cps.homeInformationEdit',{
                        url: '/homeInformationEdit/:id',
                        templateUrl: 'tpl/cps/homeInformationEdit.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function($ocLazyLoad){
                                    return $ocLazyLoad.load(['js/controllers/cps/homeInformationEdit.js?t='+timer]);
                                }]
                        }
                    })
                    // 健康设置
                    .state('app.hs',{
                        url: '/hs',
                        template: '<div ui-view></div>'
                    })
                    .state('app.hs.bannerSetting',{
                        url: '/bannerSetting',
                        templateUrl: 'tpl/hs/bannerSetting.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function($ocLazyLoad){
                                    return $ocLazyLoad.load(['js/controllers/hs/bannerSetting.js?t='+timer]);
                                }]
                        }
                    })
                    .state('app.hs.bannerSettingEdit',{
                        url: '/bannerSettingEdit/:id',
                        templateUrl: 'tpl/hs/bannerSettingEdit.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function($ocLazyLoad){
                                    return $ocLazyLoad.load(['js/controllers/hs/bannerSettingEdit.js?t='+timer]);
                                }]
                        }
                    })
                    .state('app.hs.homePlateSetting',{
                        url: '/homePlateSetting',
                        templateUrl: 'tpl/hs/homePlateSetting.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function($ocLazyLoad){
                                    return $ocLazyLoad.load(['js/controllers/hs/homePlateSetting.js?t='+timer]);
                                }]
                        }
                    })
                    .state('app.hs.homePlateSettingEdit',{
                        url: '/homePlateSettingEdit/:id/:type',
                        templateUrl: 'tpl/hs/homePlateSettingEdit.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function($ocLazyLoad){
                                    return $ocLazyLoad.load(['js/controllers/hs/homePlateSettingEdit.js?t='+timer]);
                                }]
                        }
                    })
                    // APP基础设置
                    .state('app.bs',{
                        url: '/bs',
                        template: '<div ui-view></div>'
                    })
                    .state('app.bs.about',{
                        url: '/about',
                        templateUrl: 'tpl/bs/about.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function($ocLazyLoad){
                                    return $ocLazyLoad.load(['js/controllers/bs/about.js?t='+timer]);
                                }]
                        }
                    })
                    .state('app.bs.bottomTabConfiguration',{
                        url: '/bottomTabConfiguration',
                        templateUrl: 'tpl/bs/bottomTabConfiguration.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function($ocLazyLoad){
                                    return $ocLazyLoad.load(['js/controllers/bs/bottomTabConfiguration.js?t='+timer]);
                                }]
                        }
                    })
                    .state('app.bs.scatteredConfiguration',{
                        url: '/scatteredConfiguration',
                        templateUrl: 'tpl/bs/scatteredConfiguration.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function($ocLazyLoad){
                                    return $ocLazyLoad.load(['js/controllers/bs/scatteredConfiguration.js?t='+timer]);
                                }]
                        }
                    })
                    .state('app.bs.upgrade',{
                        url: '/upgrade',
                        templateUrl: 'tpl/bs/upgrade.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function($ocLazyLoad){
                                    return $ocLazyLoad.load(['js/controllers/bs/upgrade.js?t='+timer]);
                                }]
                        }
                    })
                    .state('app.bs.switch',{
                        url: '/switch',
                        templateUrl: 'tpl/bs/switch.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function($ocLazyLoad){
                                    return $ocLazyLoad.load(['js/controllers/bs/switch.js?t='+timer]);
                                }]
                        }
                    })
                    // 文章管理
                    .state('app.am',{
                        url: '/am',
                        template: '<div ui-view></div>'
                    })
                    .state('app.am.articleManage',{
                        url: '/articleManage',
                        templateUrl: 'tpl/am/articleManage.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function($ocLazyLoad){
                                    return $ocLazyLoad.load(['js/controllers/am/articleManage.js?t='+timer]);
                                }]
                        }
                    })
                    .state('app.am.articleManageEdit',{
                        url: '/articleManageEdit/:id',
                        templateUrl: 'tpl/am/articleManageEdit.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function($ocLazyLoad){
                                    return $ocLazyLoad.load(['js/controllers/am/articleManageEdit.js?t='+timer]);
                                }]
                        }
                    })
                    // 资讯
                    .state('app.im',{
                        url: '/im',
                        template: '<div ui-view></div>'
                    })
                    .state('app.im.informationClassify',{
                        url: '/informationClassify',
                        templateUrl: 'tpl/im/informationClassify.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function($ocLazyLoad){
                                    return $ocLazyLoad.load(['js/controllers/im/informationClassify.js?t='+timer]);
                                }]
                        }
                    })
                    .state('app.im.informationClassifyEdit',{
                        url: '/informationClassifyEdit/:id',
                        templateUrl: 'tpl/im/informationClassifyEdit.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function($ocLazyLoad){
                                    return $ocLazyLoad.load(['js/controllers/im/informationClassifyEdit.js?t='+timer]);
                                }]
                        }
                    })
                    .state('app.im.informationArticles',{
                        url: '/informationArticles/:id',
                        templateUrl: 'tpl/im/informationArticles.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function($ocLazyLoad){
                                    return $ocLazyLoad.load(['js/controllers/im/informationArticles.js?t='+timer]);
                                }]
                        }
                    })
                    .state('app.im.informationArticlesEdit',{
                        url: '/informationArticlesEdit/:id/from:c_id',
                        templateUrl: 'tpl/im/informationArticlesEdit.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function($ocLazyLoad){
                                    return $ocLazyLoad.load(['js/controllers/im/informationArticlesEdit.js?t='+timer]);
                                }]
                        }
                    })
                    // 帮助中心
                    .state('app.help',{
                        url: '/ac',
                        template: '<div ui-view></div>'
                    })
                    .state('app.help.helpCenter',{
                        url: '/helpCenter',
                        templateUrl: 'tpl/ac/helpCenter.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function($ocLazyLoad){
                                    return $ocLazyLoad.load(['js/controllers/ac/helpCenter.js?t='+timer]);
                                }]
                        }
                    })
                    .state('app.help.helpCenterSL',{
                        url: '/helpCenterSL',
                        templateUrl: 'tpl/ac/helpCenterSL.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function($ocLazyLoad){
                                    return $ocLazyLoad.load(['js/controllers/ac/helpCenterSL.js?t='+timer]);
                                }]
                        }
                    })
                    .state('app.help.helpCenterEdit',{
                        url: '/helpCenterEdit/:id',
                        templateUrl: 'tpl/ac/helpCenterEdit.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function($ocLazyLoad){
                                    return $ocLazyLoad.load(['js/controllers/ac/helpCenterEdit.js?t='+timer]);
                                }]
                        }
                    })
                    .state('app.help.helpCenterSLEdit',{
                        url: '/helpCenterSLEdit/:id',
                        templateUrl: 'tpl/ac/helpCenterSLEdit.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function($ocLazyLoad){
                                    return $ocLazyLoad.load(['js/controllers/ac/helpCenterSLEdit.js?t='+timer]);
                                }]
                        }
                    })
                    // 实名认证审核
                    .state('app.authentication',{
                        url: '/authentication',
                        templateUrl: 'tpl/rna/authentication.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function($ocLazyLoad){
                                    return $ocLazyLoad.load(['js/controllers/rna/authentication.js?t='+timer]);
                                }]
                        }
                    })
                    .state('app.authenticationEdit',{
                        url: '/authenticationEdit/:id',
                        templateUrl: 'tpl/rna/authenticationEdit.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function($ocLazyLoad){
                                    return $ocLazyLoad.load(['js/controllers/rna/authenticationEdit.js?t='+timer]);
                                }]
                        }
                    })
                    // 系统通知
                    .state('app.systemNotification',{
                        url: '/systemNotification',
                        templateUrl: 'tpl/sn/systemNotification.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function($ocLazyLoad){
                                    return $ocLazyLoad.load(['js/controllers/sn/systemNotification.js?t='+timer]);
                                }]
                        }
                    })
                    .state('app.systemNotificationEdit',{
                        url: '/systemNotificationEdit/:id',
                        templateUrl: 'tpl/sn/systemNotificationEdit.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function($ocLazyLoad){
                                    return $ocLazyLoad.load(['js/controllers/sn/systemNotificationEdit.js?t='+timer]);
                                }]
                        }
                    })
                    // 医生评价审核
                    .state('app.doctorEvaluation',{
                        url: '/doctorEvaluation',
                        templateUrl: 'tpl/de/doctorEvaluation.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function($ocLazyLoad){
                                    return $ocLazyLoad.load(['js/controllers/de/doctorEvaluation.js?t='+timer]);
                                }]
                        }
                    })
                    .state('app.doctorEvaluationEdit',{
                        url: '/doctorEvaluationEdit/:id',
                        templateUrl: 'tpl/de/doctorEvaluationEdit.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function($ocLazyLoad){
                                    return $ocLazyLoad.load(['js/controllers/de/doctorEvaluationEdit.js?t='+timer]);
                                }]
                        }
                    })
                    // 医院评价审核
                    .state('app.hospitalEvaluation',{
                        url: '/hospitalEvaluation',
                        templateUrl: 'tpl/he/hospitalEvaluation.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function($ocLazyLoad){
                                    return $ocLazyLoad.load(['js/controllers/he/hospitalEvaluation.js?t='+timer]);
                                }]
                        }
                    })
                    .state('app.hospitalEvaluationEdit',{
                        url: '/hospitalEvaluationEdit/:id',
                        templateUrl: 'tpl/he/hospitalEvaluationEdit.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function($ocLazyLoad){
                                    return $ocLazyLoad.load(['js/controllers/he/hospitalEvaluationEdit.js?t='+timer]);
                                }]
                        }
                    })
                    // 权限管理
                    .state('app.rbac',{
                        url: '/rbac',
                        template: '<div ui-view></div>'
                    })
                    .state('app.rbac.roleManage', {
                        url: '/roleManage',
                        templateUrl: 'tpl/rbac/roleManage.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function( $ocLazyLoad ){
                                    return $ocLazyLoad.load('js/controllers/rbac/roleManage.js');
                                }]
                        }
                    })
                    .state('app.rbac.roleManageEdit', {
                        url: '/roleManageEdit/:id/:name/:flag',
                        templateUrl: 'tpl/rbac/roleManageEdit.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function( $ocLazyLoad ){
                                    return $ocLazyLoad.load('angularBootstrapNavTree').then(
                                        function(){
                                            return $ocLazyLoad.load('js/controllers/rbac/roleManageEdit.js');
                                        }
                                    );
                                }]
                        }
                    })
                    .state('app.rbac.userManage', {
                        url: '/userManage',
                        templateUrl: 'tpl/rbac/userManage.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function( $ocLazyLoad ){
                                    return $ocLazyLoad.load('js/controllers/rbac/userManage.js');
                                }]
                        }
                    })
                    .state('app.rbac.userManageEdit', {
                        url: '/userManageEdit/:id',
                        templateUrl: 'tpl/rbac/userManageEdit.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function( $ocLazyLoad ){
                                    return $ocLazyLoad.load('js/controllers/rbac/userManageEdit.js');
                                }]
                        }
                    })
                    .state('app.rbac.authManage', {
                        url: '/authManage',
                        templateUrl: 'tpl/rbac/menuManage.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function( $ocLazyLoad ){
                                    return $ocLazyLoad.load('angularBootstrapNavTree').then(
                                        function(){
                                            return $ocLazyLoad.load('js/controllers/rbac/menuManage.js');
                                        }
                                    );
                                }]
                        }
                    })
                    //区域代码对应地址配置
                    .state('app.areaAddressSetting',{
                        url: '/areaAddressSetting',
                        templateUrl: 'tpl/aas/areaAddressSetting.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function($ocLazyLoad){
                                    return $ocLazyLoad.load(['js/controllers/aas/areaAddressSetting.js?t='+timer]);
                                }]
                        }
                    })
                    .state('app.addAddressSetting',{
                        url: '/addAddressSetting/:id',
                        templateUrl: 'tpl/aas/addAddressSetting.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function($ocLazyLoad){
                                    return $ocLazyLoad.load(['js/controllers/aas/addAddressSetting.js?t='+timer]);
                                }]
                        }
                    })
                    // 医院维护
                    .state('app.ihd',{
                        url: '/ihd',
                        template: '<div ui-view></div>'
                    })
                    .state('app.ihd.hospital', {
                        url: '/hospital/:flag',
                        templateUrl: 'tpl/ihd/hospital.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function( $ocLazyLoad ){
                                    return $ocLazyLoad.load('js/controllers/ihd/hospital.js');
                                }]
                        }
                    })
                    .state('app.ihd.hospitalEdit', {
                        url: '/hospitalEdit/:id',
                        templateUrl: 'tpl/ihd/hospitalEdit.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function( $ocLazyLoad ){
                                    return $ocLazyLoad.load('js/controllers/ihd/hospitalEdit.js');
                                }]
                        }
                    })
                    .state('app.ihd.hospitalMerchantEdit', {
                        url: '/hospitalMerchantEdit/:id',
                        templateUrl: 'tpl/ihd/hospitalMerchantEdit.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function( $ocLazyLoad ){
                                    return $ocLazyLoad.load('js/controllers/ihd/hospitalMerchantEdit.js');
                                }]
                        }
                    })
                    .state('app.ihd.department', {
                        url: '/department/:hospitalCode',
                        templateUrl: 'tpl/ihd/department.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function( $ocLazyLoad ){
                                    return $ocLazyLoad.load('js/controllers/ihd/department.js');
                                }]
                        }
                    })
                    .state('app.ihd.departmentEdit', {
                        url: '/departmentEdit/:id/:hospitalCode',
                        templateUrl: 'tpl/ihd/departmentEdit.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function( $ocLazyLoad ){
                                    return $ocLazyLoad.load('js/controllers/ihd/departmentEdit.js');
                                }]
                        }
                    })
                    .state('app.ihd.doctor', {
                        url: '/doctor/:hospitalCode',
                        templateUrl: 'tpl/ihd/doctor.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function( $ocLazyLoad ){
                                    return $ocLazyLoad.load('js/controllers/ihd/doctor.js');
                                }]
                        }
                    })
                    .state('app.ihd.doctorEdit', {
                        url: '/doctorEdit/:id/:hospitalCode',
                        templateUrl: 'tpl/ihd/doctorEdit.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function( $ocLazyLoad ){
                                    return $ocLazyLoad.load('js/controllers/ihd/doctorEdit.js');
                                }]
                        }
                    })
            }
        ]
    );
