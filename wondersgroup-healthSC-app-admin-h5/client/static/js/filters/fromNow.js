'use strict';

/* Filters */
// need load the moment.js to use this filter.
angular.module('app')
    .filter('fromNow', function () {
        return function (date) {
            return moment(date).fromNow();
        }
    })
    .filter('sPages', function () {
        return function (data) {
            var pages = [];
            var num = data[0];
            if (num) {
                for (var i = 1; i <= num; i++) {
                    pages.push(i)
                }
            }
            return pages;
        }
    })
    .filter('timeFormat', function () {
        return function (date) {
            var newstr = date.replace(/-/g,'/');
            var newDate =  new Date(newstr);
            var time_str = newDate.getTime().toString();
            return parseInt(time_str);
        }
    });
