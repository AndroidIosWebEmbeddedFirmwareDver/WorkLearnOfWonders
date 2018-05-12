'use strict';

/**
 * 公共类,添加一些常用的工具方法
 */
app.service('Common', ['$filter', function ($filter) {
    /**
     * 时间格式转换
     * @param date
     * @returns {*}
     */
    function timeFormat(date){
        return $filter('date')(date,"yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 将传入对象中的时间类型格式化
     * @param data
     */
    this.batchFormatting = function (data) {
        for(var i in data){
           if(data[i] instanceof Date){
               data[i] = timeFormat(data[i]);
           }
        }
        return data;
    }
}]);
