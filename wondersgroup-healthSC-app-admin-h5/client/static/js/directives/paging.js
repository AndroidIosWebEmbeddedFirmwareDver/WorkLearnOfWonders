"use strict";
angular.module("app")
    .directive("paging", [
        function () {
            return {
                restrict: 'A',
                scope: {
                    pagingPager: '=',
                    goPage: '&'
                },
                templateUrl: 'tpl/blocks/paging.html',
                link: function (scope, element) {
                    function goPage(number){
                        scope.goPage({number:number});
                    }

                    element.find('a[name="mostf"]').on('click', function () {//最前
                        if (!(scope.pagingPager.number == 1)) {
                            goPage(1);
                        }
                    });

                    element.find('a[name="first"]').on('click', function () {//前一页
                        if (!(scope.pagingPager.number == 1)) {
                            goPage(scope.pagingPager.number-1);
                        }
                    });

                    element.find('a[name="last"]').on('click', function () {//后一页
                        if (!(scope.pagingPager.number == scope.pagingPager.totalPages)) {
                            goPage(scope.pagingPager.number+1);
                        }
                    });

                    element.find('a[name="mostl"]').on('click', function () {//最后
                        if (!(scope.pagingPager.number == scope.pagingPager.totalPages)) {
                            goPage(scope.pagingPager.totalPages);
                        }
                    });

                    scope.$watch('pagingPager', function() {
                        var pagingList = [];
                        var stn = getStartNum(scope.pagingPager.number,scope.pagingPager.totalPages);
                        var total =getTotal(stn,scope.pagingPager.totalPages)
                        for(var i = stn; i<=total ; i++){
                            if(scope.pagingPager.number === i){
                                pagingList.push(new pagingObj(i,true))
                            }else{
                                pagingList.push(new pagingObj(i,false))
                            }
                        }
                        scope.pagingList = pagingList;
                    });

                    var pagingObj = function(num,active){
                        this.num = num;
                        this.active = active;
                    };

                    var getStartNum = function(num,total){
                        var stn = 1;
                        if(num>3&&num>=5&&total>5){
                            stn = num-2;
                        }
                        if(num>total-4&&total>5){
                            stn = total-4
                        }
                        return stn;
                    };

                    var getTotal = function(num,total){
                        if(total > 5){
                            return num+4;
                        }else{
                            return total;
                        }
                    }
                }
            };
        }
    ]);
